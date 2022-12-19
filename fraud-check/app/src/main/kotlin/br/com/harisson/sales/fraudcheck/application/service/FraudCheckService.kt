package br.com.harisson.sales.fraudcheck.application.service

import br.com.harisson.sales.fraudcheck.adapter.out.mapper.toCheckedSale
import br.com.harisson.sales.fraudcheck.adapter.out.mapper.toInvalidatedSale
import br.com.harisson.sales.fraudcheck.application.port.`in`.FraudCheckInputPort
import br.com.harisson.sales.fraudcheck.application.port.out.FraudCheckOutputPort
import br.com.harisson.sales.fraudcheck.exception.SaleIsFraudException
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class FraudCheckService(
    private val fraudeCheckOutputPort: FraudCheckOutputPort,
    private val template: KafkaTemplate<String, SpecificRecord>,
    @Value("\${spring.topics.producer.sales-checked.name}") val topicSalesChecked: String,
    @Value("\${spring.topics.producer.sales-invalidated.name}") val topicInvalidatedSale: String,
) : FraudCheckInputPort {
    override fun processSale(consumerRecord: ConsumerRecord<String, SpecificRecord>): Result<Unit> = runCatching {
        checkForFraud()
    }.onSuccess {
        fraudeCheckOutputPort.send(
            key = consumerRecord.key(),
            record = consumerRecord.value().toCheckedSale(),
            topic = topicSalesChecked,
            kafkaTemplate = template
        )
    }.onFailure {
        fraudeCheckOutputPort.send(
            key = consumerRecord.key(),
            record = consumerRecord.value().toInvalidatedSale(it.message),
            topic = topicInvalidatedSale,
            kafkaTemplate = template
        )
    }

    fun checkForFraud() {
        if ((0..100).random() <= 5)
            throw SaleIsFraudException("Tentativa de fraude detectada")
    }
}