FROM openjdk:20-jdk

WORKDIR /app

# Копирование JAR-файла в контейнер
COPY target/scala-2.12/kafkastreamswordcount_2.12-0.1.0-SNAPSHOT.jar kafkastreamswordcount.jar

# Команда для запуска JAR-файла
# CMD ["java", "-jar", "consumer.jar"]