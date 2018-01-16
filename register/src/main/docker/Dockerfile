FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD register-0.0.1.jar register.jar
RUN sh -c 'touch /register.jar'
EXPOSE 8042
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/register.jar" ]