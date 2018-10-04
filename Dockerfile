FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/spheres.jar /spheres/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/spheres/app.jar"]
