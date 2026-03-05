# Portfolio API
> This API was developed for my personal website ([link](https://pedrao.tech/)).

It is based on Spring Boot (**Java**) and is quite simple.

These are some of the skills that I used to build it:
1. Object-oriented programming in Java
2. Use of MVC design pattern
3. Use of the Spring Boot framework
4. Use of Spring Boot ORM solution
5. Use of MongoDB to store data
6. Password encryption
7. Data transfer objects
8. API Security concepts
9. CORS configuration
10. Swagger documentation

*Note: not 100% of the source code is public due to safety reasons, since this API is currently running in production.*

## Local Docker Build and Run

This project now runs as a Docker container with a remote MongoDB database.

1. Fill `.env` values (or copy from `.env.example`).
2. Run `./build.ps1` from the repository root.

`build.ps1` validates required environment variables, builds the jar, builds the Docker image, and runs the container locally.

Required `.env` variables:
- `SPRING_DATA_MONGODB_URI`
- `SPRING_DATA_MONGODB_DATABASE`
- `SERVER_PORT`
- `SPRING_PROFILES_ACTIVE`

## GitHub Actions VPS Deployment

On every push to `main`, `.github/workflows/deploy-vps.yml` builds the jar and Docker image, uploads the image archive to the VPS, and recreates the API container.

Create these GitHub repository secrets:
- `VPS_HOST`
- `VPS_PORT`
- `VPS_USER`
- `VPS_PASSWORD`
- `VPS_DEPLOY_PATH`
- `SPRING_DATA_MONGODB_URI`
- `SPRING_DATA_MONGODB_DATABASE`

Notes:
- The database remains remote.
- The container exposes `8080` on the VPS.
- Docker must be installed on the VPS.
- Runtime variables are injected directly via `docker run -e` (no persisted `.env` file on the VPS).