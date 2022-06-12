package br.com.harisson.sales.router.adapter.config

import org.apache.avro.generic.GenericRecord
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {

    @Bean("salesCluster")
    fun salesClusterTemplate(producerFactory: ProducerFactory<String, GenericRecord>): KafkaTemplate<String, GenericRecord> {
        return KafkaTemplate(producerFactory)
    }

}