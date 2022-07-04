package br.com.harisson.sales.router.adapter.out

import br.com.harisson.sales.router.application.port.out.IdempotencyOutputPort
import br.com.harisson.sales.router.common.ColumnName.ID
import br.com.harisson.sales.router.domain.IdempotencyEntity
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Expression
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest

@Repository
class IdempotencyDatastore(
    private val idempotencyTable: DynamoDbTable<IdempotencyEntity>
): IdempotencyOutputPort {
    companion object{
        private val NOT_EXISTS_CONDITION_EXPRESSION = Expression.builder()
            .expression("attribute_not_exists($ID)").build()
    }

    override fun putIfNotExists(idempotencyEntity: IdempotencyEntity) = runCatching{
        val updateIdempotencyRequest = PutItemEnhancedRequest.builder(IdempotencyEntity::class.java)
            .conditionExpression(NOT_EXISTS_CONDITION_EXPRESSION)
            .item(idempotencyEntity)
            .build()

        idempotencyTable.putItem(updateIdempotencyRequest)
    }.getOrThrow()
}