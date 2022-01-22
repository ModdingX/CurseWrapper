FROM eclipse-temurin:17
EXPOSE 443
COPY . /data
WORKDIR /data
RUN sh ./gradlew downloadDependencies
WORKDIR /data
ENTRYPOINT [ "java", "-p", "server.jar", "-p", "dependencies", "-m", "io.github.noeppi_noeppi.tools.cursewrapper.server", "--docker" ]
