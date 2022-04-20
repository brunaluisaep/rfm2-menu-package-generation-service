# Package Generation Decouple
 
The Package Generation Decouple will allow the legacy application to generate XML files (names-db.xml, product-db.xml, screen-db.xml, promotion-db.xml, store-db.xml) in a separate service, not overloading the server application and this will allow the user to continue using the application without performance problems
 
# Description

The package generation decouple was created as a part of the menu transformation initiative. 
This will allow us to generate the files in a separate service

[Menu Transformation Confluence Page](https://us-confluence.mcd.com/display/RFM/Menu+Transformation)

# Installation
(Gradle/Maven/NuGet dependency structure or CLI installation as applicable)

JDK: amazon corretto jdk

More information about the Technologies: https://us-confluence.mcd.com/pages/viewpage.action?spaceKey=RFM&title=Project+Technologies

Additionally, to run the service from an executable jar:

mvn clean package && java -jar target/rfm-package-generation-prototype-0.0.1-SNAPSHOT.jar

Docker container image build and run:

docker build -t "name" .

docker build -t packagegenerator .

docker run -d "name"

docker run -d packagegenerator

Example curl requests for generates a names-db.xml

`curl -v localhost:8080/package/generationByDTO`

# Project Setup for Local Development
 
## OSX / Linux
The following command launches the package decouple service locally on port 8080

`mvn clean spring-boot:run`


## Windows
Check your system variable "JAVA_HOME", and make sure it's set to amazon corretto JDK

The following command launches the package decouple service locally on port 8080 (note: you have to be on pom.xml directory)

`mvn clean spring-boot:run`

Run the project without the unit test:
`mvn install -Dmaven.test.skip=true`
 
# Contributor Guide
(link to Style Guide)

# Authors and Maintainers
[Lucas Oliveira](https://bitbucket.sharedtools.vet-tools.digitalecp.mcd.com/users/e7088034)
[Kaio Gabriel](https://bitbucket.sharedtools.vet-tools.digitalecp.mcd.com/users/ea675012)
[Tadeu Camargo](https://bitbucket.sharedtools.vet-tools.digitalecp.mcd.com/users/ea706427)


