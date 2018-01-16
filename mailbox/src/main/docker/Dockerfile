FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD mailbox-0.0.1.jar mailbox.jar
RUN sh -c 'touch /mailbox.jar'
EXPOSE 8099
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/mailbox.jar" ]