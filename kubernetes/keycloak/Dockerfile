FROM bitnami/keycloak

WORKDIR /opt/bitnami/keycloak

ADD https://github.com/SnuK87/keycloak-kafka/releases/download/1.1.5/keycloak-kafka-1.1.5-jar-with-dependencies.jar /opt/bitnami/keycloak/providers/

USER root

RUN chmod -R 775 /opt/bitnami/keycloak/

RUN /opt/bitnami/keycloak/bin/kc.sh build

USER 1001