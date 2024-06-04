DROP CONNECTOR IF EXISTS "mndwrk-mongodb-connector";
CREATE SINK CONNECTOR IF NOT EXISTS "mndwrk-mongodb-connector" WITH (
    "connector.class" = 'com.mongodb.kafka.connect.MongoSinkConnector',
    "topics" = 'mndwrk-zilla-response',
    "connection.uri" = 'mongodb+srv://x:x@web-entity.2de8d77.mongodb.net/?retryWrites=true&w=majority&appName=web-entity',
    "connection.username" = 'x',
    "connection.password" = 'x',
    "database" = 'mndwrk-kafka-zilla',
    "key.converter" = 'org.apache.kafka.connect.storage.StringConverter',
    "key.converter.schemas.enable" = 'true',
    "key.converter.schema.registry.url" = 'http://schema-registry:8081',
    "value.converter" = 'io.confluent.connect.avro.AvroConverter',
    "value.converter.schemas.enable" = 'true',
    "value.converter.schema.registry.url" = 'http://schema-registry:8081',
    "collection" = 'mndwrk-sensor-data',
    "timeseries.timefield.auto.convert" = 'false',
    "errors.deadletterqueue.context.headers.enable" = 'false',
    "name" = 'mongodb-sink-connector-7jua',
    "tasks.max" = '1'
);

CREATE
OR REPLACE STREAM MNDWRK_ZILLA_REQUEST(UUID VARCHAR KEY) WITH (
    KAFKA_TOPIC = 'mndwrk-zilla-request',
    KEY_FORMAT = 'KAFKA',
    PARTITIONS = 1,
    VALUE_FORMAT = 'AVRO'
);


CREATE
OR REPLACE STREAM MNDWRK_ZILLA_RESPONSE WITH (
    KAFKA_TOPIC = 'mndwrk-zilla-response',
    KEY_FORMAT = 'KAFKA',
    PARTITIONS = 1,
    VALUE_FORMAT = 'AVRO'
) AS
SELECT
    '/api/v1/sensor-data/' + IFNULL(
        REPLACE(uuid, 'mndwrk-zilla-request', NULL),
        UUID()
    ),
    UUID as `uuid`,
    SOURCE as `source`,
    DESCRIPTION as `description`,
    TIMESTAMPTOSTRING(ROWTIME, 'yyyy-MM-dd HH:mm:ss.SSS') as `detectedAt`,
    'ksqldb' as `processedBy`
FROM
    MNDWRK_ZILLA_REQUEST PARTITION BY '/api/v1/sensor-data/' + IFNULL(
        REPLACE(uuid, 'mndwrk-zilla-request', NULL),
        UUID()
    ) EMIT CHANGES;