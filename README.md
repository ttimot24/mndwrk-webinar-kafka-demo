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
CREATE STREAM consumedEvent (uuid VARCHAR, source VARCHAR, summary VARCHAR, detectedAt VARCHAR)
WITH (kafka_topic='webinar-demo-inbound', value_format='json', partitions=10);
```

```sql
CREATE STREAM producedEvent (uuid VARCHAR, source VARCHAR, summary VARCHAR, detectedAt VARCHAR)
WITH (kafka_topic='webinar-demo-outbound', value_format='json', partitions=10);
```

```sql
INSERT INTO webinarDemoInbound (uuid, source, summary, detectedAt) VALUES ('e707450e-93d4-4c53-9f04-03aab3666b8b', 'CCTV' , 'KSQLDBStream', '2022');
```