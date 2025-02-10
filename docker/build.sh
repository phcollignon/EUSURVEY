#!/bin/bash
cd ..
mvn clean install -Denvironment=ossdocker
cp target/eusurvey.war docker/server/dist/eusurvey.war

cd docker

docker-compose build


