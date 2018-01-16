FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD qstlib-0.0.1.jar qstlib.jar
RUN sh -c 'touch /qstlib.jar'
EXPOSE 8041
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/qstlib.jar" ]