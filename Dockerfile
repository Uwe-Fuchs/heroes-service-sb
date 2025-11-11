# docker build -t heroes-service .
# docker run -p 8083:8083 -d --name heroes-service heroes-service
# docker run -p 8083:8083 -d --network heroes-net --name heroes-service heroes-service

FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]