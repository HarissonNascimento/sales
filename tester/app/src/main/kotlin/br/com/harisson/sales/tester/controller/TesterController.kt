package br.com.harisson.sales.tester.controller

import br.com.harisson.sales.tester.common.generateRecord
import br.com.harisson.sales.tester.common.randomUUIDString
import br.com.harisson.sales.tester.component.GenericProducer
import br.com.harisson.sales.tester.controller.dto.Request
import br.com.harisson.sales.tester.controller.dto.Response
import org.apache.avro.generic.GenericRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TesterController(
    private val genericProducer: GenericProducer,
    private val kafkaTemplate: KafkaTemplate<String?, GenericRecord>
) {

    @PostMapping("/router")
    private fun post(@RequestBody request: Request): Response {
        val generatedRecord = generateRecord(request)
        val key = randomUUIDString()
        val topic = request.topic

        genericProducer.send(
            topic = topic,
            key = key,
            record = generatedRecord,
            kafkaTemplate = kafkaTemplate
        )

        return Response(topic = topic, key = key, value = generatedRecord)
    }

}