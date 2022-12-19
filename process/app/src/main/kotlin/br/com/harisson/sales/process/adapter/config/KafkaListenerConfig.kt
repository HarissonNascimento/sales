package br.com.harisson.sales.process.adapter.config

import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
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

    @Bean
    fun containerFactoryBean(
        consumerFactory: ConsumerFactory<String?, SpecificRecord>
    ): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String?, SpecificRecord?>>? {
        val configs =
            consumerFactory.configurationProperties.toMap() + mapOf(ConsumerConfig.CLIENT_ID_CONFIG to "process")

        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, SpecificRecord>()

        containerFactory.consumerFactory = DefaultKafkaConsumerFactory(configs)
        containerFactory.setConcurrency(1)
        containerFactory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        containerFactory.setRecordFilterStrategy {
            kafkaListenerFilter.shouldDiscardEvent(it)
        }
        containerFactory.setCommonErrorHandler(
            DefaultErrorHandler(FixedBackOff(10, 3)).apply { isAckAfterHandle = true }
        )
        containerFactory.setAckDiscarded(true)

        return containerFactory
    }

}