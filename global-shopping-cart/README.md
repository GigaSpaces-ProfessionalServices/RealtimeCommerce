Global Shopping Cart
==============================
This is a a sample application to demonstrate building a global and highly avaialable shopping cart

The sample applications makes use of the following XAP features:
- Data Grid / Space API
- HTTP Session Sharing
- WAN Gateway

Building
========
> mvn package

Running the Data Grid
=====================
> cd space
> mvn os:run-standalone -Dmodule=space

Running the Cart Web Application
================================
> cd cart
> mvn jetty:run

Browse to http://localhost:8080/GigaShopping

Implementation
==============
Implementation notes:
- The session sharing/replication is implmemented through a replication filter
- Building and runinng the project requires installing the OpenSpace maven plugin via XAP_HOME/tools/maven/installmavenrep.sh

Reference Documentation
=======================
* XAP HTTP Session Sharing - http://docs.gigaspaces.com/xap100/global-http-session-sharing-overview.html

