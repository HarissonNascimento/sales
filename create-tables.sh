aws dynamodb create-table --table-name router_idempotency --attribute-definitions AttributeName=sale_id,AttributeType=S --key-schema AttributeName=sale_id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=2,WriteCapacityUnits=1 --endpoint-url http://localhost:4566 --region sa-east-1