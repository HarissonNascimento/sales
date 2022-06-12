package br.com.harisson.sales.tester.component

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture

@Component
class GenericProducer {

    fun send(
        topic: String,
        key: String? = null,
        record: GenericRecord,
        kafkaTemplate: KafkaTemplate<String?, GenericRecord>
    ): ListenableFuture<SendResult<String?, GenericRecord>> {
        val producerRecord = ProducerRecord(topic, key, record)

        val result = kafkaTemplate.send(producerRecord)

        result.addCallback({
            println("Mensagem enviada: topico=$topic, key=$key, partição=${it?.recordMetadata?.partition()}, offset=${it?.recordMetadata?.offset()}")
        }, {
            println("Erro ao enviar mensagem: topico=$topic, key=$key \n Erro: $it")
        })

        return result
    }

}