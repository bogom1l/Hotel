Hotel Project: project with mircoservice architecture (Hotel, Comments, Authentication, HotelBff)
<br>

Spring <br>
Maven <br>
PostgreSQL <br>
Git <br>
Docker (5 containers - Postgre DB Server; Hotel microservice; Comments microservice; Authentication microservice; BFF microservice) <br>
 * docker-compose.yaml <br>
 * Dockerfile (creates images) <br>
<br>

Logic:
We authenticate in Authentication (with JWT) [Spring Security] -> then access the endpoints in BFF

<br>
<hr>
<br>

Microservice:
-multimoduled architecture (api, core, persistence, domain, rest, restexport)
  rest depends on: core; 
  core depends on: api, persistence, domain;
  
<br>
<hr>
<br>

► api:

base Operation's interface:
	public interface OperationProcessor<I extends OperationInput, O extends OperationOutput> {
  	  Either<ErrorsWrapper, O> process(I input);
	}

Operations' input/output models and the operation's extension of the OperationProcessor

RestApiRoutes

Custom Errors (for error handling)

Custom Exceptions

Custom Validations

<hr>

► core:

Custom ErrorHandler

Converters

OperationProcessors

<hr>

► domain:

n/a (used for getting exposed endpoints from other microservices [BFF receives from Hotel, Comments, Auth]

<hr>

► persistence:

DataSeeder - Responsible for seeding initial data into the database when the application starts

Entities - Database tables created with JPA

Repositories - Queries for the database

<hr>

► rest:

Configurations - ObjectMapper

Controllers

HotelApplication - main method

application.properties

Tests

<hr>

► restexport:

Exposing endpoints (from the controllers), so other microservice can access them (using FeignClient)





