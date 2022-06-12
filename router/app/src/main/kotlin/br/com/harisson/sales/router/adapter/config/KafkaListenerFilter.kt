package br.com.harisson.sales.router.adapter.config

import br.com.harisson.RequestSale
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaListenerFilter {

    private val acceptedEventTypes = setOf(
        RequestSale::class.qualifiedName!!
    )

    fun eventTypeIsValid(consumerRecord: ConsumerRecord<String?, GenericRecord>): Boolean {
        val recordType = consumerRecord.value()!!::class.qualifiedName!!
        return acceptedEventTypes.contains(recordType)
    }

}