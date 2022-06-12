package br.com.harisson.sales.router.adapter.config

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerConfig.CLIENT_ID_CONFIG
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff

@EnableKafka
@Configuration
class KafkaListenerConfig(
    private val kafkaListenerFilter: KafkaListenerFilter
) {

    @Bean("registerContainerFactoryBean")
    fun registerContainerFactoryBean(
        consumerFactory: ConsumerFactory<String?, GenericRecord>
    ): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String?, GenericRecord?>>? {
        val configs = consumerFactory.configurationProperties.toMap() + mapOf(CLIENT_ID_CONFIG to "router")

        val newConsumerFactory = DefaultKafkaConsumerFactory<String, GenericRecord>(configs)

        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, GenericRecord>()

        containerFactory.consumerFactory = newConsumerFactory
        containerFactory.setConcurrency(1)
        containerFactory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        containerFactory.setRecordFilterStrategy{
            kafkaListenerFilter.eventTypeIsValid(it)
        }
        containerFactory.setCommonErrorHandler(
            DefaultErrorHandler(FixedBackOff(10, 3)).apply { isAckAfterHandle = true })
        containerFactory.setAckDiscarded(true)

        return containerFactory
    }

}