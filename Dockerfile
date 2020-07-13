FROM asuprun/opencv-java

ARG ADDITIONAL_OPTS
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

VOLUME /tmp
COPY target/pixel-api*.jar pixel-api.jar

EXPOSE 8080
EXPOSE 5005

CMD java ${ADDITIONAL_OPTS} -jar pixel-api.jar
