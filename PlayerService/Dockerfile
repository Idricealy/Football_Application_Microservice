FROM azul/zulu-openjdk:8

WORKDIR /app

COPY target/player-service.jar player-service.jar

EXPOSE 9002

CMD ["java", "-jar", "player-service.jar"]
