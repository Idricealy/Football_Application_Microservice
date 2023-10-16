FROM azul/zulu-openjdk:8

WORKDIR /app

COPY target/match-service.jar match-service.jar

EXPOSE 9003

CMD ["java", "-jar", "match-service.jar"]
