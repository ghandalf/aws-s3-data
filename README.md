# README #

This project expects that you already have java 16, and maven installed. To use it you also need to have an AWS account and full access to S3. Without the AWS access you won't be able to execute the tests.

The domain could be use by third party application.
The sub project service use aws sdk to manage sqs service or s3 service.


### Repository content ###

**aws-s3-data**: the main container of the project, use maven capacities.
 - domain_: contain the objects of the domain used by the service project and third party according to aws api.
 - service_: this service implements the connections to sqs or s3 with springframework wrapper.

**Version**: 0.0.9


**Local Configuration**

```
	open a terminal
	move in your workspace where you want the project
	execute those commands:
	    git clone https://github.com/ghandalf/aws-s3-data.git
	    mvn compile
	    mvn eclipse:eclipse | mvn idea:idea // Eclipse or IntelliJ GUI configuration
	    mvn clean install -DskipTests // will deploy each artifacts in your local repository
```

Unless you have a AWS account and configure the S3 access you won't be able to execute the tests.
Sorry, I am against Mock tests. I never saw any values except for the first creation, they don't survive to development changes.

To make the project usable, for your organization you need to provide the information in the service/src/main/resources/sqs.properties files.


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines


### Who do I talk to? ###

* Repo owner: Ghandalf
* Community: Those who want a deep and simple example.
