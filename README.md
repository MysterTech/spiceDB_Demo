# SpiceDB Client App

## **Introduction**
This application demonstrates how to use the spicedb service to:

* [Create Schema](https://docs.authzed.com/guides/schema)
* [Persist Permissions](https://docs.authzed.com/guides/writing-relationships)
* Read Permissions

## **API Endpoints**
**localhost:8080/spicedb/createSchema** : Creates the following schema:- 
            
    definition blog/user {}
                            
    definition blog/post {
        relation reader: blog/user
        relation writer: blog/user
                            
        permission read = reader + writer
        permission write = writer
    }

**localhost:8080/spicedb/createRelationships** : Creates two relationships: one making Emilia a writer of the first post and another making Beatrice a reader of the first post.

**localhost:8080/spicedb/createRelationships** : Checks if emilia has read permissions on blog/post#1.

## **How to run demo**
**Steps**
1. Clone repo using `git clone git@github.com:MysterTech/spiceDB_Demo.git`
2. cd into demo directory `cd spiceDB_Demo`
3. run `docker-compose up` to initialise spicedb enviroment
4. cd into client directory and run app to launch client : `cd spicedb-client && gradlew bootrun`


## **Additional Links**
These additional references should also help you:

* [Authzed docs](https://docs.authzed.com/)

