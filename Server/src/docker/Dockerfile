FROM eclipse-temurin:17
RUN apt-get update
RUN apt-get install -y python3
EXPOSE 80 443 8008
COPY . /data
WORKDIR /data
RUN sh ./gradlew downloadDependencies
WORKDIR /data
ENTRYPOINT [ "python3", "start.py" ]
