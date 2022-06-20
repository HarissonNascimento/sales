package br.com.harisson.sales.router.adapter.config

import br.com.harisson.RequestSale
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaListenerFilter {

    private val acceptedEventTypes = listOf(
        RequestSale::class.java
    )

    fun shouldDiscardEvent(consumerRecord: ConsumerRecord<String?, GenericRecord>): Boolean {
        val recordType = consumerRecord.value()!!::class.java
        return recordType !in acceptedEventTypes
    }

}