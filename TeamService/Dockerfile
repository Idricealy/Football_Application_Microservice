FROM azul/zulu-openjdk:8

WORKDIR /app

COPY target/team-service.jar team-service.jar

EXPOSE 9001

CMD ["java", "-jar", "team-service.jar"]
