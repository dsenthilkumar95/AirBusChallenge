# AirBusChallenge
This project contains both the frontend and backend part of AirBusChallenge.

# AirBusFrontEnd
This project is built with Angular 11.2.13 and Angular Material. It has a login page and products page.   

In Login page, if we are using the application for the first time we need to register with a username and password. This registers the user with spring security. Then we can login with the same details and jwt token for the user gets saved in the session storage and redirection to products page occurs.  

In Product page, we have the list of products available in angular mat table and we can sort the products, search through the products present in the table. We can even search for a particular category, which makes API call for products with particular category. Creating or Updating an existing product in the table is made available with mat dialog.   

It is also designed with proxy settings to only allow API request to given endpoint. Authguard is used to prevent users from accessing products page without login.   

To run application locally, pull the main branch and use the command ```ng serve --proxy-config proxy.conf.json```   

AWS deployed version is present in ```aws``` branch.

# AirbusBackEnd
This project is built with Springboot 2.4.5 , Spring data jpa, Spring security, in-memory H2 database.   

This project exposes APIs for getting all products, getting products by category, creating new product and update an existing product. Exceptions from controller layer are handled by Controller Advice.   

API Security is taken care by Authentication filter, Authorization filter and WebSecurityConfiguration for ensuring only requests with valid JWT token in the header can get access to the APIs. Also it has CORS filter enabled which prevents request from unknown origins. Allowed origins are mentioned in the application.properties file.    

Wise use of lombok and mapstruct is made, which helps in getting rid of boilerplate codes like getters, setters, constructors, object mapping codes.   

To run application locally, pull the main branch and run ```AirbusBackEndApplication``` Java class.
