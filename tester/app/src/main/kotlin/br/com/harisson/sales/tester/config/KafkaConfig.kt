package br.com.harisson.sales.tester.config

import org.apache.avro.generic.GenericRecord
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@EnableKafka
@Configuration
class KafkaConfig {

    @Bean
    fun salesKafkaTemplate(producerFactory: ProducerFactory<String?, GenericRecord>)
    : KafkaTemplate<String?, GenericRecord> = KafkaTemplate(producerFactory)

}