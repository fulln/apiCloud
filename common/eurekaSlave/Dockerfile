FROM java:8
MAINTAINER : fulln <i@fulln.me>
VOLUME  /tmp
COPY eurekaSlave/target/eurekaSlave.jar  /opt/eurekaSlave.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/eurekaSlave.jar"]
