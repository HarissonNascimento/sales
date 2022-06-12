package br.com.harisson.sales.router.adapter.`in`

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class SaleListener {

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${spring.topics.consumer.sales-to-be-processed.name}"],
        containerFactory = "registerContainerFactoryBean"
    )
    fun onEvent(consumerRecord: ConsumerRecord<String, GenericRecord>, acknowledgement: Acknowledgment) {
        val record = consumerRecord.value()

        println(record)


    }
}