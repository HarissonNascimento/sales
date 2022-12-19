package br.com.harisson.sales.process.adapter.config

import br.com.harisson.CheckedSale
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaListenerFilter {

    private val acceptedEventTypes = listOf(
        CheckedSale::class.java
    )

    fun shouldDiscardEvent(consumerRecord: ConsumerRecord<String, SpecificRecord>): Boolean {
        val recordType = consumerRecord.value()!!::class.java
        return recordType !in acceptedEventTypes
    }

}