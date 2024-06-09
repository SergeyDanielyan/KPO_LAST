FROM openjdk:17

COPY mikroservice-auth/docker/auth.jar auth.jar

ENTRYPOINT ["java", "-jar", "auth.jar"]

EXPOSE 8085