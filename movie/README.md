# Application dependencies

- Java 21
- Postgres
- Docker

# 🗒️ How to run the service

The application directory already contains a docker-compose file,
the file has bundled the application service and the postgres database.
The application is configured to create the database schema automatically.

You can just run docker-compose up command(Please make sure you have already navigated into the application directory)
to run the application, and it will expose the application instance in port 9001.

✨ In case if the port 9001 is already in use in your computer, please change the port in docker-compose to a different
one.

# 📐 Access the application

When you run the application with docker-compose, you can access the application with following URL

http://localhost:9001

# 🗒️ API endpoints information

The swagger UI can be accessed with the following URL
http://localhost:9001/swagger-ui/index.html

Example curl commands for each API end point

- Create Movie :
  curl --location 'localhost:9001/movie' \
  --header 'Content-Type: application/json' \
  --data '{
  "title":"Pinbo",
  "description": "The story of pinbo",
  "producer": "pinbo",
  "language": "English",
  "durationInMinutes": 115
  }'

- View Movie :
  curl --location 'localhost:9001/movie/UUID_OF_THE_MOVIE'

- Create User :
  curl --location 'localhost:9001/user' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "userName":"user_name",
  "firstName": "John",
  "lastName": "Doe",
  "email":"testjon123@testets.com",
  "address":"jhons address"
  }'

- View User :
  curl --location 'localhost:9001/user/UUID_OF_THE_USER'

- Create Movie Rating :
  curl --location 'localhost:9001/rate/UUID_OF_THE_MOVIE/UUID_OF_THE_USER' \
  --header 'Content-Type: application/json' \
  --data '{
  "ratingValue":9,
  "comment": "nice movie"
  }'

- View Profile :
  curl --location 'localhost:9001/profile/UUID_OF_THE_USER'









