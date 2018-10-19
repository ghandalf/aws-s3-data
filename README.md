# README #

This project expects that you already have java 10+, and maven installed.

The aws-s3-domain could be use by third party application.
The sub project aws-s3-service use aws sdk to manage sqs service or s3 service.


### Repository content ###

**aws-s3-data**: the main container of the project, use maven capacities.
 - _aws-s3-domain_: contain the objects of the domain used by the service project and third party according to aws api.
 - _aws-s3-service_: this service implements the connections to sqs or s3 with springframework wrapper.

**Version**: 0.0.9


### Set up ###

You need to make sure you have the latest jdk install, take a look in the aws-s3-data pom.xml, 
to find out the current jdk in used.<br>
    `<java.version>jdk-11.0.1</java.version>`

I use [toolchains](https://maven.apache.org/guides/mini/guide-using-toolchains.html) to make use of differents jdk.

At compilation, you will see those lines:

```
[INFO] --- maven-toolchains-plugin:1.1:toolchain (default) @ parent ---
[INFO] Required toolchain: jdk [ vendor='oracle' version='jdk-11.0.1' ]
[INFO] Found matching toolchain for type jdk: JDK[/data/links/jdk]

```

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

To make the project usable, for your organization you need to provide the information in the aws-s3-service/src/main/resources/sqs.properties files.


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines


### Who do I talk to? ###

* Repo owner: Ghandalf
* Community: Those who want a deep and simple example.
