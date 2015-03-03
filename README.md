Realtime Commerce and Omni-Channel Design Patterns with GigaSpaces XAP
==============================
This is a set of sample applications to demonstrate using GigaSpaces XAP In-Memory Data Grid for implementing realtime commerce and omni-channel infrastructures

The sample applications makes use of the following XAP features:
- Data Grid / Space API
- HTTP Session Sharing
- Event Containers
- Executor-Based Remoting
- WAN Gateway
- MemoryXtend

Building
========
> mvn package

Running
=======
> mvn spring-boot:run

Browse to http://localhost:8080/index.html

Implementation
==============
Implementation notes:
- The event store is backed by a filesystem implementation which comes with Axon
- The query model is backed by a local ElasticSearch node (running in the same JVM) using Spring Data ElasticSearch
- The user interface is updated asynchronously via stompjs over websockets using Spring Websockets support

Documentation
=============
* Axon Framework - http://www.axonframework.org/
* Spring Boot - http://projects.spring.io/spring-boot/
* Spring Framework - http://projects.spring.io/spring-framework/
* Spring Data ElasticSearch - https://github.com/spring-projects/spring-data-elasticsearch
