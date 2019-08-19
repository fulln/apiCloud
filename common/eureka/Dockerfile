FROM java:8
MAINTAINER : fulln <i@fulln.me>
VOLUME  /tmp
COPY eureka/target/eurekaMaster.jar  /opt/eurekaMaster.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/eurekaMaster.jar"]
