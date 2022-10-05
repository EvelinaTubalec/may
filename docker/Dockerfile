FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} may-0.1.0.jar
ENTRYPOINT ["java", "-jar", "may-0.1.0.jar"]
