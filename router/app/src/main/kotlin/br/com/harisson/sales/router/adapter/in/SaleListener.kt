package br.com.harisson.sales.router.adapter.`in`

import br.com.harisson.sales.router.adapter.out.SaleProducer
import br.com.harisson.sales.router.adapter.out.mapper.toRouterResult
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class SaleListener(
    private val saleProducer: SaleProducer,
    private val template: KafkaTemplate<String, GenericRecord>,
    @Value("\${spring.topics.producer.sales-fraud-check.name}") val topic: String
) {

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${spring.topics.consumer.sales-to-be-processed.name}"],
        containerFactory = "registerContainerFactoryBean"
    )
    fun onEvent(consumerRecord: ConsumerRecord<String, GenericRecord>, acknowledgement: Acknowledgment) {
        saleProducer.send(record = consumerRecord.value().toRouterResult(), topic = topic, kafkaTemplate = template)
    }
}