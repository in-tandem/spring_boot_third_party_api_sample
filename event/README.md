Event Ful API

1. Spring boot project that connects to downstream eventful api
2. Built in the form of a micro service
3. UI will be separately hosted/ separate application
4. Backend services may be scaled up by deploying multiple instances
5. load balancer to reverse proxy
6. client ui to point to load balancer

Could not find time to build client ui. please use http://localhost:9000/swagger-ui.html once application has been started


application can be started using

mvn clean install
mvn spring-boot:run