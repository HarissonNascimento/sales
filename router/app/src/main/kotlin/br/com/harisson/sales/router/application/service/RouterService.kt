package br.com.harisson.sales.router.application.service

import br.com.harisson.sales.router.adapter.out.mapper.toIdempotencyEntity
import br.com.harisson.sales.router.adapter.out.mapper.toInvalidatedSale
import br.com.harisson.sales.router.adapter.out.mapper.toValidatedSale
import br.com.harisson.sales.router.application.port.`in`.RouterInputPort
import br.com.harisson.sales.router.application.port.out.IdempotencyOutputPort
import br.com.harisson.sales.router.application.port.out.RouterOutputPort
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class RouterService(
    private val routerOutputPort: RouterOutputPort,
    private val idempotencyOutputPort: IdempotencyOutputPort,
    private val template: KafkaTemplate<String, GenericRecord>,
    @Value("\${spring.topics.producer.sales-fraud-check.name}") val topicSalesFraudCheck: String,
    @Value("\${spring.topics.producer.sales-invalidated.name}") val topicInvalidatedSale: String
) : RouterInputPort {

    override fun processSale(consumerRecord: ConsumerRecord<String, GenericRecord>): Result<Unit> = runCatching {
        idempotencyOutputPort.putIfNotExists(consumerRecord.value().toIdempotencyEntity())
    }.onSuccess {
        routerOutputPort.send(
            key = consumerRecord.key(),
            record = consumerRecord.value().toValidatedSale(),
            topic = topicSalesFraudCheck,
            kafkaTemplate = template
        )
    }.onFailure {
        routerOutputPort.send(
            key = consumerRecord.key(),
            record = consumerRecord.value().toInvalidatedSale(it.message),
            topic = topicInvalidatedSale,
            kafkaTemplate = template
        )
    }

}