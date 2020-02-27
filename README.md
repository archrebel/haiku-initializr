# haiku-initializr
The purpose of Haiku Initializr is to put together Architectural Haiku (https://www.zeljkoobrenovic.com/tools/haiku/#) and Spring Initializr (https://start.spring.io). 

## What does it mean?

The Architectural Haiku provides a nice way of modeling and visualizing different software architectures: traditional web applications, microservices, lambda architectures, etc. 
It is very simple and very powerful and customizable - you can define your custom attribute types (and edit their values) for different kind of elements the Architectural Haiku supports, such as Nodes. 

On the other hand, Spring Initializr is probably the most used application generator in the world - it provides a quick means of bootstraping a Spring application. 

Haiku Initializr is just a simple attempt to put these two together. 

## How?

We started by assuming that there are several typical components that comprise microservice architectures, such as application-gateway, service-discovery or a service. 

Once you label Nodes in the Architectural Haiku with the values you need, you can define their mappings to Spring Initializr dependencies in the Haiku Initializr. 

For example, a an application gateway in our typical projects will use spring-cloud-zuul, spring-cloud-eureka and spring-boot-actuator dependencies. 

The Haiku Initializr takes as its input architectural model - exported as json from Architectural Haiku, along with mapping configuration and produces Spring starters created by the Spring Initializr. 

## ... to be continued
