Realtime Inventory (for Oracle ATG)
===================================
This is a a sample application to implementing a realtime inventory that is compatible with the Oracle ATG Inventory API

The sample applications makes use of the following XAP features:
- Data Grid / Space API
- Executor-based Remoting


Building
========
> mvn package

Running the Data Grid
=====================
> mvn os:run-standalone -Dmodule=space

Running the ATG REST API
================================
> mvn spring-boot:run

Point Oracle ATG to http://localhost:8080/

Implementation
==============
Implementation notes:
- Building and runinng the project requires installing the OpenSpace maven plugin via XAP_HOME/tools/maven/installmavenrep.sh

Reference Documentation
=======================
* Executor-based Remoting - http://docs.gigaspaces.com/xap100/executor-based-remoting.html

