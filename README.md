# Web-Socket Inventory

## How to run
    gradle run

To see the page that shows the inventory go to http://localhost:8406

## Technologies used
Embedded Jetty, Spring Framework, AngularJS, stomp.js

## How it works
Upon page loading, JavaScript registers for inventory updates (sent via websockets) and gets the full state from the server via REST.
WebSockets are sending only incremental updates (diff from previous state)

STOMP protocol is used on top of web sockets 
(as suggested by Spring guys: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-stomp).

StateUpdateJob fetches the data from the DAO and puts it into StateCache. 
StateCache is a singleton that works as a cache. 
Upon state change, StateCache triggers all listeners (all spring beans implementing StateUpdateListener).

StorageStateBroadcaster is an implementation of StateUpdateListener that broadcasts the inventory update to all clients connected to a specific STOMP topic.

## Known issues
- REST endpoint and websocket updates are separate. 
REST response can come back later than an update that was generated after the endpoint read the state (we have a race condition). 
Client (js) could enqueue incoming updates and apply them the REST response to solve it.
- UI is minimalistic and uses some anti-patterns (i.e.$scope.$digest)
- there are no tests - sorry (to write nice functional test, we could change the mock dao to expose a rest interface to set specific values and wait for them to appear on the page)
- cache is implemented as a singleton. If the call to fetch the data is heavy and we have multiple servers, it would be better to have one server make the call and put the value in a shared ehcache.