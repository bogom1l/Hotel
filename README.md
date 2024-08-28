Hotel Project: project with mircoservice architecture

Spring
Maven
PostgreSQL
Git
Docker (5 containers - Postgre DB Server; Hotel microservice; Comments microservice; Authentication microservice; BFF microservice)
 * docker-compose.yaml
 * Dockerfile (creates images)

Logic:
We authenticate in Authentication (with JWT) [Spring Security] -> then access the endpoints in BFF

--------------------------------------------

Hotel microservice
-multimoduled architecture (api, core, persistence, domain, rest, restexport)
  rest depends on: core; 
  core depends on: api, persistence, domain;

_______________________________________________________________________________
► api:

base Operation's interface:
	public interface OperationProcessor<I extends OperationInput, O extends OperationOutput> {
  	  Either<ErrorsWrapper, O> process(I input);
	}

Operations' input/output models and the operation's extension of the OperationProcessor

RestApiRoutes

Custom Errors (for errorhandling)

Custom Exceptions

Custom Validations

_______________________________________________________________________________

► core:

Custom ErrorHandler

Converters

OperationProcessors

_______________________________________________________________________________

► domain:

n/a (used for getting exposed endpoints from other microservices [BFF receives from Hotel, Comments, Auth]

_______________________________________________________________________________

► persistence:

DataSeeder - Responsible for seeding initial data into the database when the application starts

Entities - Database tables created with JPA

Repositories - Queries for the database

_______________________________________________________________________________

► rest:

Configurations - ObjectMapper

Controllers

HotelApplication - main method

application.properties

Tests

_______________________________________________________________________________

► restexport:

Exposing endpoints (from the controllers), so other microservice can access them (using FeignClient)





