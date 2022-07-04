package br.com.harisson.sales.router.application.port.`in`

import br.com.harisson.sales.router.domain.IdempotencyEntity
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest

interface RouterInputPort {
    fun processSale(consumerRecord: ConsumerRecord<String, GenericRecord>)
}