FROM openjdk:14-alpine
COPY target/mn-security-jwt-*.jar mn-security-jwt.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "mn-security-jwt.jar"]