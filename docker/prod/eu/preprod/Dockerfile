FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MinRAMPercentage=50 -XX:MaxRAMPercentage=80"
ENTRYPOINT ["java", "-jar", "/app.jar", "$JAVA_TOOL_OPTIONS"]
RUN apk update
RUN apk add nfs-utils
RUN mkdir /data
RUN mkdir /RFM2
ADD docker/prod/eu/preprod/addhost tmp/
ADD docker/prod/eu/preprod/mountTSS.sh /tmp
RUN chmod u+x /tmp/mountTSS.sh
