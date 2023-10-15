FROM openjdk:17-slim
WORKDIR /coffee-store/app
COPY target/coffee-store-0.0.1-SNAPSHOT.jar coffee-store.jar
EXPOSE 8080
CMD ["java", "-jar", "coffee-store.jar"]