#
#
#--------------------------------------------------------------------------
# bocepay-java-bridge
#--------------------------------------------------------------------------
#

FROM --platform=linux/amd64 chinayin/maven:3-jdk-11-bullseye-slim AS builder

RUN set -eux \
  #&& install_packages tree vim \
  && mkdir -p ~/.m2 \
  #&& echo '<settings><mirrors><mirror><id>aliyun</id><mirrorOf>*</mirrorOf><name>aliyun</name><url>https://maven.aliyun.com/repository/public</url></mirror></mirrors></settings>' >> ~/.m2/settings.xml \
  && mkdir /app

WORKDIR /app
ADD . /app

RUN set -eux \
  && mvn -B package --file pom.xml \
  && ls -l target \
  && SDK_VERSION=$(grep '<version>' pom.xml |head -n1 |tr -cd "[0-9.]") \
  && echo $SDK_VERSION \
  && cp -f target/BOCEPayJavaBridge-${SDK_VERSION}.war target/BOCEPayJavaBridge.war

FROM --platform=linux/amd64 chinayin/openjdk:11-jre-bullseye-slim
ENV TZ=PRC
ENV PARAMS=""

COPY --from=builder /app/target/BOCEPayJavaBridge.war /app/BOCEPayJavaBridge.war
COPY --from=builder /app/bin/webapp-runner-*.jar /app/webapp-runner.jar
WORKDIR /app

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS webapp-runner.jar BOCEPayJavaBridge.war --port 8080 $PARAMS"]
