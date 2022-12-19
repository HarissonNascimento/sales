package br.com.harisson.sales.process.adapter.`in`

import br.com.harisson.sales.process.application.port.`in`.ProcessInputPort
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class SaleListener(
    private val processInputPort: ProcessInputPort
) {

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${spring.topics.consumer.sales-checked.name}"],
        containerFactory = "containerFactoryBean"
    )
    fun onEvent(consumerRecord: ConsumerRecord<String, SpecificRecord>, acknowledgment: Acknowledgment) {
        processInputPort.processSale(consumerRecord)
        acknowledgment.acknowledge()
    }

}