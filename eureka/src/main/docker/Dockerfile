FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD eureka-0.0.1.jar eureka.jar
RUN sh -c 'touch /eureka.jar'
EXPOSE 8761
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/eureka.jar" ]