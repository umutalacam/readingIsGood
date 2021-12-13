# Reading is Good

This is an example implementation of a RestAPI that uses mongo db as persistent storage.

### Running the application (Docker)

For running the reading is good application using Docker, you need to run fallowing command in the root directory of the project.

`docker compose up`

The project requires a mongo db instance, therefore the docker compose file will create a container with mongodb image.

When the application is started, you can reach the endpoints through port 8080.



### API Security

The API uses jwt token for authentication. In each request, the token needs to be given in Authorization header.



#### Obtaining a JWT Token

For obtaining a JWT token, `/authenticate` endpoint is used. The docker image initializes some users automatically, you can use their credentials to obtain a JWT token. For example, you can use the credentials given below.

Credentials for alice user:

```json
{
  "username": "alice",
  "password": "123456"
}
```



### Endpoints

Endpoint definitions can be found at postman documentation. (https://documenter.getpostman.com/view/14145413/UVR5rUzQ)

It is also useful to check OpenAI enpoint by calling `/v2/apidocs` endpoint.
