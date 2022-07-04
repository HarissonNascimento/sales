package br.com.harisson.sales.router.domain

import br.com.harisson.sales.router.common.ColumnName.ID
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class IdempotencyEntity(
    @get:DynamoDbAttribute(value = ID)
    @get:DynamoDbPartitionKey
    var id: String = ""
)
