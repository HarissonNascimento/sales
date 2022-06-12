package br.com.harisson.sales.tester.common

import br.com.harisson.sales.tester.common.ObjectMapper.instance
import br.com.harisson.sales.tester.controller.dto.Request
import org.apache.avro.generic.GenericRecord
import java.lang.Class.forName

fun generateRecord(request: Request): GenericRecord {
    val avroSchemaClass = forName(request.schema)

    return instance.readValue(request.avro.toString(), avroSchemaClass) as GenericRecord
}