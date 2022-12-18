package br.com.harisson.sales.fraudcheck.application.port.`in`

import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord

interface FraudCheckInputPort {
    fun processSale(consumerRecord: ConsumerRecord<String, SpecificRecord>): Result<Unit>
}