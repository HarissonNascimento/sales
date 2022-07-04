package br.com.harisson.sales.router.adapter.config

import br.com.harisson.sales.router.domain.IdempotencyEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
class DynamoDBConfig(
    @Value("\${dynamodb.tables.idempotency-table.name}") private val idempotencyTableName: String
) {
    @Bean
    fun dynamoDbClient(
        @Value("\${aws.region}") awsRegion: String,
        @Value("\${dynamodb.endpoint}") endpoint: String
    ) = DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .endpointOverride(URI(endpoint))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()

    @Bean
    fun dynamoDbEnhancedClient(dynamoDbClient: DynamoDbClient) =
        DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build()

    @Bean
    fun idempotencyTable(dynamoDbEnhancedClient: DynamoDbEnhancedClient) =
        dynamoDbEnhancedClient.table(idempotencyTableName, TableSchema.fromBean(IdempotencyEntity::class.java))
}