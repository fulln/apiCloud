FROM java:8
MAINTAINER : fulln <i@fulln.me>
VOLUME  /tmp
COPY config/target/config.jar  /opt/config.jar
COPY config/target/classes/config/ /opt/config/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/config.jar" ]
