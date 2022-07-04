package br.com.harisson.sales.router.adapter.out

import br.com.harisson.sales.router.application.port.out.RouterOutputPort
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture

@Component
class SaleProducer: RouterOutputPort {

    override fun send(
        key: String?,
        record: GenericRecord,
        topic: String,
        kafkaTemplate: KafkaTemplate<String, GenericRecord>
    ): Result<ListenableFuture<SendResult<String?, GenericRecord>>, Throwable> {
        return runCatching {
            val producerRecord = ProducerRecord(topic, null, null, key, record)

            val sentMessage = kafkaTemplate.send(producerRecord)

            sentMessage.addCallback({
                println("Mensagem enviada: topico=$topic, key=$key, partição=${it?.recordMetadata?.partition()}, offset=${it?.recordMetadata?.offset()}")
            }, {
                println("Erro ao enviar mensagem: topico=$topic, key=$key \n Erro: $it")
            })

            sentMessage
        }
    }

}