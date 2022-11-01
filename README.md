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
CREATE STREAM IF NOT EXISTS consumedEvent (`uuid` VARCHAR, `source` VARCHAR, `summary` VARCHAR, `detectedAt` VARCHAR)
WITH (kafka_topic='webinar-demo-inbound', TIMESTAMP = '`detectedAt`', TIMESTAMP_FORMAT = 'yyyy-MM-dd HH:mm:ss', value_format='AVRO', partitions=10);
```

```sql
CREATE STREAM IF NOT EXISTS producedEvent (`uuid` VARCHAR, `source` VARCHAR, `summary` VARCHAR, `detectedAt` VARCHAR)
WITH (kafka_topic='webinar-demo-outbound', TIMESTAMP = '`detectedAt`', TIMESTAMP_FORMAT = 'yyyy-MM-dd HH:mm:ss', value_format='AVRO', partitions=10);
```

```sql
INSERT INTO consumedEvent (`uuid`, `source`, `summary`, `detectedAt`) VALUES (UUID(), 'CCTV' , 'KSQLDBStream', '2022-11-08 18:00:00');
```

```sql
CREATE STREAM webinarDemoInboundAvro (uuid STRING, source STRING, summary STRING, detectedAt STRING)
WITH (kafka_topic='webinar-demo-inbound', TIMESTAMP = 'detectedAt', TIMESTAMP_FORMAT = 'yyyy-MM-dd HH:mm:ss', value_format='AVRO', partitions=10);
```