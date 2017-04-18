# RonaBankWebSite
The user-facing component of the Rona Bank SOA applicaition.

## What you'll need

* JDK 1.8
* Maven 3.0+
* Service Registry application such as Eureka
* [RonaBankFinancialCalculatorWebService](https://github.com/ronalag/RonaBankFinancialCalculatorWebService)

## How to build and run the application

Run the following command:

```
mvn spring-boot:run
```

This will start the site on port 8080. If you want to use a different port number you can add the option `-Dserver.port=30622`.
