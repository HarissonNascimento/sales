docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --list

echo -e 'Creating kafka topics'
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --create --if-not-exists --topic sales-to-be-processed --replication-factor 1 --partitions 1
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --create --if-not-exists --topic sales-fraud-check --replication-factor 1 --partitions 1
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --create --if-not-exists --topic sales-checked --replication-factor 1 --partitions 1
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --create --if-not-exists --topic sales-to-be-dispatched --replication-factor 1 --partitions 1
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --create --if-not-exists --topic sales-processed --replication-factor 1 --partitions 1
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --create --if-not-exists --topic sales-invalidated --replication-factor 1 --partitions 3

echo -e 'Successfully created the following topics:'
docker exec sales-kafka kafka-topics --bootstrap-server sales-kafka:29092 --list