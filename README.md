# MNDWRK - Apache Kafka Webinar

## Getting started

```console
docker-compose up -d

docker exec -it  webinar-kafka-demo_broker_1 /bin/bash

kafka-console-producer --topic webinar-demo-inbound --bootstrap-server localhost:9092

```

#### Payload

```json
{"source": "TLC", "description":"Green signal"}
```

```json
{"source": "CCTV", "description":"People detected"}
```

```json
{"source": "SENSOR", "description":"Something happend"}
```

```console
kafka-console-consumer --topic webinar-demo-outbound --bootstrap-server localhost:9092
```

### Restart Broker

```console
sudo docker-compose down -v && sudo docker-compose up -d
```

### Register Schema
```
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" -d@src/main/resources/schemas/json/webinar-demo-outbound.json http://localhost:8081/subjects/webinar-demo-outbund/versions | jq
```

### ksqlDB
```sh
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
```

```sql
SET 'auto.offset.reset' = 'earliest';
```

```sql
CREATE STREAM IF NOT EXISTS consumedEvent (`uuid` VARCHAR, `source` VARCHAR, `summary` VARCHAR, `detectedAt` TIMESTAMP)
WITH (kafka_topic='webinar-demo-inbound', value_format='AVRO', partitions=10, VALUE_SCHEMA_FULL_NAME='com.mndwrk.webinar.demo.ksqldb.AvroConsumedEvent');
```

```sql
CREATE STREAM IF NOT EXISTS producedEvent (`uuid` VARCHAR, `source` VARCHAR, `summary` VARCHAR, `processedBy` VARCHAR, `detectedAt` TIMESTAMP)
WITH (kafka_topic='webinar-demo-outbound', value_format='AVRO', partitions=10, VALUE_SCHEMA_FULL_NAME='com.mndwrk.webinar.demo.ksqldb.AvroProducedEvent');
```

```sql
CREATE STREAM joined_streams WITH (KAFKA_TOPIC='webinar-demo-join') AS SELECT * FROM consumedEvent ce LEFT JOIN producedEvent pe WITHIN 10 SECONDS ON ce.`uuid` = pe.`uuid` EMIT CHANGES;
```

```sql
INSERT INTO consumedEvent (`uuid`, `source`, `summary`, `detectedAt`) VALUES (UUID(), 'CCTV' , 'KSQLDBStream', '2022-11-03T11:39:03.001');
INSERT INTO consumedEvent (`uuid`, `source`, `summary`, `detectedAt`) VALUES ('aad4374b-42dd-4876-bdd2-a4a8c836f7c3', 'CCTV' , 'KSQLDBStream', '2022-11-03T11:39:03.001');
```

```sql
CREATE OR REPLACE TABLE record_count_table AS SELECT ce.`uuid` PRIMAR KEY, count(*) as record_count FROM consumedEvent ce WINDOW TUMBLING(SIZE 10 SECONDS) GROUP BY ce.`uuid` EMIT CHANGES;

--CREATE TABLE record_count AS SELECT `source`, count(*) as record_count FROM consumedEvent WINDOW TUMBLING(SIZE 5 SECONDS) GROUP BY `source` EMIT CHANGES;
```

```sql
SELECT `uuid`, FORMAT_TIMESTAMP(FROM_UNIXTIME(windowstart), 'yyyy-MM-dd HH:mm:ss.SSS'), FORMAT_TIMESTAMP(FROM_UNIXTIME(windowend), 'yyyy-MM-dd HH:mm:ss.SSS'), record_count FROM record_count_table;
```

EXPLAIN | DESCRIBE;
POSTMAN REST CALL