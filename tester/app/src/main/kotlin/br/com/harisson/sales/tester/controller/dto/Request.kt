package br.com.harisson.sales.tester.controller.dto

import com.fasterxml.jackson.databind.JsonNode

data class Request(
    val topic: String,
    val key: String? = null,
    val avro: JsonNode,
    val schema: String
)