# RSS-Camel-MS

This micros-service consumes RSS feeds to extract informations about disturbance in public transportation and stores it in a mongodb database.

### description

this micro-service receives rss feeds from these two URIs:

http://trafiroutes.wallonie.be/trafiroutes/Evenements_FR.rss

http://www.wegeninfo.be/rssfr.php


and then stores them as Binaray JSON in two different mongodb databes, the databases are called cameldbn1 and cameldbn2, the collection in both databes is called test, and the file model of the received data can be seen in the model.json file in this repository.
### how to use


    download or clone this project, open it with your favorite IDE, lunch the the Application.java file (run file as java project) and that's it.
    this application will sort the entires by updated date, and will only receive new feeds.
