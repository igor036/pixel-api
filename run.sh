mvn -DskipTests=true -Pbuild; docker container prune -f; docker image prune -f;  docker-compose up --build -d