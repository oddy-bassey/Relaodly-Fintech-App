#Code Challenge

This Solution uses Axon Framework to implement a CQRS and Event-Sourcing design pattern for an Event-Driven System.
<br>This pattern allows me scale up the command and query sides independently. This provides an advantage in 
<br>systems where the reads out numbers the writes or vice versa. By seperating these two, it allows me optimize the
<br> application for high performance as this is required in Fintech applications.
<br>The application comprises of:
* API Gateway
* Bank  module
    * Account-service
    * Notification-service
* User Management