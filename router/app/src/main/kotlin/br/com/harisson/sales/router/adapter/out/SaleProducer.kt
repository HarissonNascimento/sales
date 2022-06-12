package br.com.harisson.sales.router.adapter.out

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import java.time.Instant.now

@Component
class SaleProducer {

    fun send(
        key: String? = null,
        record: GenericRecord,
        topic: String,
        kafkaTemplate: KafkaTemplate<String, GenericRecord>
    ): Result<ListenableFuture<SendResult<String?, GenericRecord>>, Throwable> {
        return runCatching {
            val producerRecord = ProducerRecord(topic, null, null, key, record)
            val partition = TopicPartition(topic, -1)
            val recordMetadata = RecordMetadata(partition, -1, -1, now().toEpochMilli(), -1, -1)
            val sendResult = SendResult(producerRecord, recordMetadata)

            AsyncResult.forValue(sendResult)
        }
    }

}