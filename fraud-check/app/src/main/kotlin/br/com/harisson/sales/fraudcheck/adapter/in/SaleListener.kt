package br.com.harisson.sales.fraudcheck.adapter.`in`

import br.com.harisson.sales.fraudcheck.application.port.`in`.FraudCheckInputPort
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class SaleListener(
    private val fraudCheckInputPort: FraudCheckInputPort
) {

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${spring.topics.consumer.sales-fraud-check.name}"],
        containerFactory = "containerFactoryBean"
    )
    fun onEvent(consumerRecord: ConsumerRecord<String, SpecificRecord>, acknowledgment: Acknowledgment) {
        fraudCheckInputPort.processSale(consumerRecord)
        acknowledgment.acknowledge()
    }
}