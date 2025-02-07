# Generate a Docker Image

First, generate a Docker Image of the Quadras_SMC project using Cloud Native Buildpacks. In the Quadras_SMC directory, run the command:

```bash
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=qsmc/spring-api
```

This will generate a Docker Image with the name qsmc/spring-api. After the build finishes, we should now have a Docker image for our application, which we can check with the following command:

```bash
docker images qsmc/spring-api
```

Now we can start the container image and make sure it works:

```bash
docker run -p 8080:8080 --name qsmc-spring-api -t qsmc/spring-api
```

Before moving on be sure to stop the running container.

```bash
docker stop qsmc-spring-api
```
