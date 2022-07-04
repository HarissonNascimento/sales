package br.com.harisson.sales.router.adapter.`in`

import br.com.harisson.sales.router.application.port.`in`.RouterInputPort
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class SaleListener(
    private val routerInputPort: RouterInputPort
) {

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${spring.topics.consumer.sales-to-be-processed.name}"],
        containerFactory = "registerContainerFactoryBean"
    )
    fun onEvent(consumerRecord: ConsumerRecord<String, GenericRecord>, acknowledgement: Acknowledgment) {
        routerInputPort.processSale(consumerRecord)
        acknowledgement.acknowledge()
    }
}