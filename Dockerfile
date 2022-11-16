FROM openjdk:11
ARG PROJECT_NAME=dog-hole
WORKDIR /opt/deployments
COPY   $PROJECT_NAME/target/$PROJECT_NAME.jar /opt/deployments/
EXPOSE 80
ENV JAVA_OPTS="-Xmx1008M -Xms1008M -Xmn336M -XX:MaxMetaspaceSize=128M -XX:MetaspaceSize=128M -XX:OnOutOfMemoryError=$JAVA_HOME/bin/killjava.sh -XX:+ExitOnOutOfMemoryError"
ENV TZ=Asia/Shanghai
ENV PROJECT_NAME $PROJECT_NAME
CMD java ${JAVA_OPTS} -jar /opt/deployments/$PROJECT_NAME.jar