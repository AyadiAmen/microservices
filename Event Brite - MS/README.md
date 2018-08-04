# EventBrite-MS

This micros-service consumes the open source API from eventbrite to locate the events near you.

### description

this micro-service connects to the EventBrtie API:

    https://www.eventbrite.com/developer/v3/

and using the different location parameters (longitude, latitude, address) to locate the events near you and returns a the parameters "name", "id", "local" and description
### how to use

download or clone this project, open it with your favorite IDE, lunch the the Application.java file (run file as java project) and that's it.
now you can send the GET request using postman with this url:

    localhost:8080/search/event?latitude=50.467388&longitude=4.871985&within=10

and you're receve the information you seek about the events in the location you set the parametrs to.
