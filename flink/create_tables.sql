CREATE ORE REPLACE TABLE kafka WITH (
     'connector' = 'kafka',
     'properties.bootstrap.servers' = 'broker:29092',
     'scan.startup.mode' = 'earliest-offset',
     'topic' = 'mndwrk-zilla-request',
     'value.format' = 'avro-confluent',
     'value.avro-confluent.url' = 'http://schema-registy:8081'
);