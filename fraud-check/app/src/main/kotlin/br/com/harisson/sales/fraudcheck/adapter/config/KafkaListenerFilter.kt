package br.com.harisson.sales.fraudcheck.adapter.config

import br.com.harisson.ValidatedSale
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaListenerFilter {

    private val acceptedEventTypes = listOf(
        ValidatedSale::class.java
    )

    fun shouldDiscardEvent(consumerRecord: ConsumerRecord<String, SpecificRecord>): Boolean {
        val recordType = consumerRecord.value()!!::class.java
        return recordType !in acceptedEventTypes
    }
}