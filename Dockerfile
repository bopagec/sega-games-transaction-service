FROM openjdk:latest
ADD build/libs/sega-games-transaction-service-0.0.1-SNAPSHOT.jar docker-sega-games-transaction-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar" ,"docker-sega-games-transaction-service.jar"]