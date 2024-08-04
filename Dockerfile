FROM jelastic/maven:3.9.5-openjdk-21

ENV WORKDIR=/opt/backend

RUN mkdir -p ${WORKDIR}
WORKDIR ${WORKDIR}

ADD . ${WORKDIR}

CMD ["sh", "-c", "mvn clean && mvn -B -Dmaven.test.skip=true package && java -jar target/backend-DEV-SNAPSHOT.jar"]
