FROM java:8
MAINTAINER : fulln <i@fulln.me>
VOLUME  /tmp
COPY config/target/config.jar  /opt/config.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/config.jar"]
