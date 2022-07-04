package br.com.harisson.sales.router.application.port.out

import com.github.michaelbull.result.Result
import org.apache.avro.generic.GenericRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture

interface RouterOutputPort {
    fun send(
        key: String? = null,
        record: GenericRecord,
        topic: String,
        kafkaTemplate: KafkaTemplate<String, GenericRecord>
    ): Result<ListenableFuture<SendResult<String?, GenericRecord>>, Throwable>
}