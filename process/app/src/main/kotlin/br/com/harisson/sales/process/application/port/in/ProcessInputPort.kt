package br.com.harisson.sales.process.application.port.`in`

import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord

interface ProcessInputPort {
    fun processSale(consumerRecord: ConsumerRecord<String, SpecificRecord>): Result<Unit>
}