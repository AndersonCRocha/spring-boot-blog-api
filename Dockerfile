FROM maven:3.8.1-adoptopenjdk-15

COPY . /var/www
WORKDIR /var/www

RUN mvn clean install -DskipTests

CMD mvn spring-boot:run -DskipTests