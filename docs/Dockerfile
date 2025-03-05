FROM amazoncorretto:17.0.13-alpine

WORKDIR /app

COPY demo-api-Final.jar /app/root.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Taipei /etc/localtime

RUN mkdir -p /springboot_log && chmod 777 /springboot_log

ENV LOG_DIR=/springboot_log

CMD ["java", "-jar", "/app/root.jar"]

