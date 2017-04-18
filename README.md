# RonaBankWebSite
The user-facing component of the Rona Bank SOA applicaition.

## What you'll need

* JDK 1.8
* Maven 3.0+
* Service Registry application such as [Eureka Server](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance). You can learn more about setting a server by looking at this [guide](https://spring.io/guides/gs/service-registration-and-discovery/) on [Spring Cloud Netflix](https://cloud.spring.io/spring-cloud-netflix/).
* [RonaBankFinancialCalculatorWebService](https://github.com/ronalag/RonaBankFinancialCalculatorWebService)

## How to build and run the application

The Service Registry application should be running before you start this service.

Run the following command:

```
mvn spring-boot:run
```

This will start the site on port 8080. If you want to use a different port number you can add the option `-Dserver.port=30622`.
