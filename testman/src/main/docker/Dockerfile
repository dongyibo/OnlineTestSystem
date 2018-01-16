FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD testman-0.0.1.jar testman.jar
RUN sh -c 'touch /testman.jar'
EXPOSE 8035
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/testman.jar" ]