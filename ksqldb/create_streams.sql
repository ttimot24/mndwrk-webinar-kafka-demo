CREATE OR REPLACE STREAM MNDWRK_ZILLA_REQUEST(UUID VARCHAR KEY) 
WITH (
    KAFKA_TOPIC = 'mndwrk-zilla-request',
    KEY_FORMAT = 'KAFKA',
    PARTITIONS = 1,
    VALUE_FORMAT = 'AVRO'
);

CREATE OR REPLACE STREAM MNDWRK_ZILLA_RESPONSE 
WITH (
    KAFKA_TOPIC = 'mndwrk-zilla-response',
    KEY_FORMAT = 'KAFKA',
    PARTITIONS = 1,
    VALUE_FORMAT = 'AVRO'
) AS
SELECT
    'mndwrk-zilla-response/'+ uuid,
    UUID as `uuid`,
    SOURCE as `source`,
    DESCRIPTION as `description`,
    TIMESTAMPTOSTRING(ROWTIME, 'yyyy-MM-dd HH:mm:ss.SSS') as `detectedAt`
FROM
    MNDWRK_ZILLA_REQUEST
PARTITION BY 'mndwrk-zilla-response/'+ uuid
EMIT CHANGES;
