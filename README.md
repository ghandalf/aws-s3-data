# README #

This project expects that you already have java 8+, and maven installed. The project has sub projects manage by a parent project. 

The aws-s3-domain could be use by third party application.
The sub project aws-s3-service use aws sdk to manage sqs service or s3 service.
At this state, it is easy to create a new sub project where REST is used.

### What is this repository for? ###

* aws-s3-data: the main container of the project, use maven capacities.
* aws-s3-domain: contain the objects of the domain used by the service project and third party according to aws api.
* aws-s3-service: this service implements the connections to sqs or s3 with springframework wrapper.
* Version: 0.0.9


### How do I get set up? ###

* git clone {url}
* Configuration: import in Eclipse the main project as a maven project.
			In aws-s3-service/src/main/resources rename sqs.template.properties to sqs.properties and fill the information needed.
* Dependencies: they are manage by maven, don't bother with moving from one directories to an other.
* How to run tests: open a command prompt, move under the parent project, execute mvn test
* Deployment instructions: Once (Jenkins) Pipeline will be configure, you will have nothing to do except push your code on BitBucket
* Retrieve binary code: the jar file must be taken from Binary Source Code as usual.

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner: Hi! it's me
* Community: Those who want a deep and simple example.
