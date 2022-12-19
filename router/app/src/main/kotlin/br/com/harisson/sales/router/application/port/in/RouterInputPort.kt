package br.com.harisson.sales.router.application.port.`in`

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord

interface RouterInputPort {
    fun processSale(consumerRecord: ConsumerRecord<String, GenericRecord>): Result<Unit>
}