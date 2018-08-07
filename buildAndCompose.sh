#!bin/sh
./mvnw install dockerfile:build &&
docker-compose up
