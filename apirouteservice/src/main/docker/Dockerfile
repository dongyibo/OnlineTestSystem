FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD apirouteservice-0.0.1.jar apirouteservice.jar
RUN sh -c 'touch /apirouteservice.jar'
EXPOSE 8081
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/apirouteservice.jar" ]