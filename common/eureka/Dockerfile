FROM java:8
MAINTAINER : fulln <i@fulln.me>
VOLUME  /tmp
COPY target/eureka.jar  /opt/eureka.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/eureka.jar"]
