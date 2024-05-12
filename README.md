![cover](./src/main/resources/img/cover.jpg)
# MNDWRK Kafka Webinar



Welcome to the MNDWRK webinar demo repository! In this webinar, we'll explore the powerful Kafka ecosystem in conjunction with Spring Boot, showcasing how to build scalable, event-driven applications with ease.
we'll demonstrate the integration of Kafka, a distributed streaming platform, with Spring Boot, a popular Java framework for building microservices. Through practical examples, we'll illustrate how Kafka facilitates real-time data processing and messaging within an application ecosystem.

[`#kafka`](#) [`#kstreams`](#) [`#ksqldb`](#) [`#connect`](#) [`#schema-registry`](#) [`#zilla`](#)

## Webinar links

 - [Apache Kafka Ecosystem](https://www.mndwrk.com/events/digitalk-webinar-apache-kafka-ecosystem)

## Getting started

#### Architecture

![architecture](./architecture/mndwrk-demo.drawio.png)

#### Docker

```sh
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

```sh
kafka-console-consumer --topic webinar-demo-outbound --bootstrap-server localhost:9092
```

#### Restart Broker

```sh
sudo docker-compose down -v && sudo docker-compose up -d
```

#### Register Schema
```sh
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" -d@src/main/resources/schemas/json/webinar-demo-outbound.json http://localhost:8081/subjects/webinar-demo-outbund/versions | jq
```

#### ksqlDB
```sh
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
```

```sql
SET 'auto.offset.reset' = 'earliest';
```

```sql
CREATE OR REPLACE STREAM MNDWRK_ZILLA_REQUEST (SOURCE STRING, DESCRIPTION STRING) WITH (KAFKA_TOPIC='mndwrk-zilla-request', KEY_FORMAT='KAFKA', PARTITIONS=1, VALUE_FORMAT='AVRO', V
ALUE_SCHEMA_FULL_NAME='com.mndwrk.webinar.demo.ksqldb.AvroConsumedEvent');
```

```sql
CREATE STREAM IF NOT EXISTS producedEvent (`uuid` VARCHAR, `source` VARCHAR, `summary` VARCHAR, `processedBy` VARCHAR, `detectedAt` TIMESTAMP)
WITH (kafka_topic='webinar-demo-outbound', value_format='AVRO', partitions=10, VALUE_SCHEMA_FULL_NAME='com.mndwrk.webinar.demo.ksqldb.AvroProducedEvent');
```

```sql
CREATE STREAM joined_streams WITH (KAFKA_TOPIC='webinar-demo-join') AS SELECT * FROM consumedEvent ce LEFT JOIN producedEvent pe WITHIN 10 SECONDS ON ce.`uuid` = pe.`uuid` EMIT CHANGES;
```

```sql
CREATE OR REPLACE TABLE record_count_table AS SELECT ce.`uuid` PRIMAR KEY, count(*) as record_count FROM consumedEvent ce WINDOW TUMBLING(SIZE 10 SECONDS) GROUP BY ce.`uuid` EMIT CHANGES;

--CREATE TABLE record_count AS SELECT `source`, count(*) as record_count FROM consumedEvent WINDOW TUMBLING(SIZE 5 SECONDS) GROUP BY `source` EMIT CHANGES;
```

```sql
INSERT INTO consumedEvent (`uuid`, `source`, `summary`, `detectedAt`) VALUES (UUID(), 'CCTV' , 'KSQLDBStream', '2022-11-03T11:39:03.001');
INSERT INTO consumedEvent (`uuid`, `source`, `summary`, `detectedAt`) VALUES ('aad4374b-42dd-4876-bdd2-a4a8c836f7c3', 'CCTV' , 'KSQLDBStream', '2022-11-03T11:39:03.001');
```

```sql
SELECT `uuid`, FORMAT_TIMESTAMP(FROM_UNIXTIME(windowstart), 'yyyy-MM-dd HH:mm:ss.SSS') as wstart, FORMAT_TIMESTAMP(FROM_UNIXTIME(windowend), 'yyyy-MM-dd HH:mm:ss.SSS') wend, record_count FROM record_count_table;

-- WHERE record_count > 1 EMIT CHANGES;

```

#### Other

```sql
DROP STREAM IF EXISTS <stream_name> DELETE TOPIC;
DROP TABLE IF EXISTS <table_name> DELETE TOPIC;
EXPLAIN | DESCRIBE;
POSTMAN REST CALL
```

##

Project by Timot Tarjani [(@ttimot24)](https://github.com/ttimot24)