FROM eclipse-temurin:21-jre
WORKDIR /app
COPY keystore.p12 /app/keystore.p12
COPY /build/libs/Documents-0.0.1-SNAPSHOT.jar /app/Documents-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/Documents-0.0.1-SNAPSHOT.jar"]