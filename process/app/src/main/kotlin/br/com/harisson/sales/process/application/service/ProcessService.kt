package br.com.harisson.sales.process.application.service

import br.com.harisson.StatusEnum
import br.com.harisson.sales.process.adapter.out.mapper.toSaleToBeDispatched
import br.com.harisson.sales.process.application.port.`in`.ProcessInputPort
import br.com.harisson.sales.process.application.port.out.ProcessOutputPort
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ProcessService(
    private val processOutputPort: ProcessOutputPort,
    private val template: KafkaTemplate<String, SpecificRecord>,
    @Value("\${spring.topics.producer.sales-to-be-dispatched.name}") val topicSalesToBeDispatched: String
) : ProcessInputPort {
    override fun processSale(consumerRecord: ConsumerRecord<String, SpecificRecord>): Result<Unit> = runCatching {
        val saleStatus = getSaleStatus()

        processOutputPort.send(
            key = consumerRecord.key(),
            record = consumerRecord.value().toSaleToBeDispatched(saleStatus),
            topic = topicSalesToBeDispatched,
            kafkaTemplate = template
        )
    }

    fun getSaleStatus(): StatusEnum {
        if ((0..100).random() <= 5)
            return StatusEnum.FAIL
        return StatusEnum.DONE
    }
}