FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD account-0.0.1.jar account.jar
RUN sh -c 'touch /account.jar'
EXPOSE 8024
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/account.jar" ]