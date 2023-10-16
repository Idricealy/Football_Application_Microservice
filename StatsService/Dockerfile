FROM azul/zulu-openjdk:8

WORKDIR /app

COPY target/stats-service.jar stats-service.jar

EXPOSE 9004

CMD ["java", "-jar", "stats-service.jar"]
