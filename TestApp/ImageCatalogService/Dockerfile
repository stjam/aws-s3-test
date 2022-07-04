FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:1.10
WORKDIR /home/demo
COPY target/ImageCatalogService.jar app.jar
EXPOSE 9191
ENTRYPOINT ["java","-jar","app.jar"]