package br.com.harisson.sales.fraudcheck.application.port.out

import com.github.michaelbull.result.Result
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture

interface FraudCheckOutputPort {
    fun send(
        key: String? = null,
        record: SpecificRecord,
        topic: String,
        kafkaTemplate: KafkaTemplate<String, SpecificRecord>
    ): Result<ListenableFuture<SendResult<String?, SpecificRecord>>, Throwable>
}