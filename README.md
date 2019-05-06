# Project Restful Dogs

A student that completes this project shows that they can:
* implement server sided routing and rendering for a Spring REST service
* implement industry standard exception handling including user friendly exception messages

# Introduction

We worked with dogs in plain Java. This project returns dog data using Rest APIs with appropriate exception handling and make use of Server Side Rendering.

# Instructions

The provided application exposes the following endpoints
* /dogs -> returns a list of all dogs
* /dogs/{id} -> returns a single dog based off of its id number
* /dogs/breeds/{breed} -> returns all dogs of the given breed
    
Starting with the dogs-initial application

Add appropriate exception handling routines. Required exceptions to handle are when
  * a resource is not found
  * the wrong data type is used for a path variable
  * a non-handled endpoint is accessed (a URL not found exception)

 Add server side rendering using Thymeleaf to 
  * display all dogs ordered by breed
  * display all dogs suitable for apartments ordered by breed
