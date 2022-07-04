package br.com.harisson.sales.router.application.service

import br.com.harisson.sales.router.adapter.out.mapper.toIdempotencyEntity
import br.com.harisson.sales.router.adapter.out.mapper.toRouterResult
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
    @Value("\${spring.topics.producer.sales-fraud-check.name}") val topic: String
): RouterInputPort {

    override fun processSale(consumerRecord: ConsumerRecord<String, GenericRecord>) = runCatching{
        idempotencyOutputPort.putIfNotExists(consumerRecord.value().toIdempotencyEntity())
    }.onSuccess {
        routerOutputPort.send(key = consumerRecord.key(), record = consumerRecord.value().toRouterResult(), topic = topic, kafkaTemplate = template)
    }.getOrThrow()

}