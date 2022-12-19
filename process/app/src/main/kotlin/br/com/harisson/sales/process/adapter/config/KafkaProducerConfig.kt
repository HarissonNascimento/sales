package br.com.harisson.sales.process.adapter.config

import org.apache.avro.specific.SpecificRecord
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {
    @Bean("salesCluster")
    fun salesClusterTemplate(producerFactory: ProducerFactory<String, SpecificRecord>): KafkaTemplate<String, SpecificRecord> {
        return KafkaTemplate(producerFactory)
    }
}