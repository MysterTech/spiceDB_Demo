# SpiceDB Client App

## **Introduction**
This application demonstrates how to use the spicedb service to:

* [Create Schema](https://docs.authzed.com/guides/schema)
* [Persist Permissions](https://docs.authzed.com/guides/writing-relationships)
* Read Permissions

## **API Endpoints**
**localhost:8080/spicedb/createSchema** : Creates the following schema:- 
            
    definition user {}

    definition role {
        relation member: user
    }

    /** resource */
    definition page {
        relation editer_user: user
        relation reader_user: user
        relation except_reader_user: user
        relation except_editer_user: user
        relation reader_role: role
        relation editer_role: role
        relation except_reader_role: role
        relation except_editer_role: role
        permission read = (((reader_role->member + editer_role->member + reader_user + editer_user - except_reader_role->member) - except_reader_user) - except_editer_role->member) - except_editer_user
        permission edit = (editer_role->member + editer_user - except_editer_role->member) - except_editer_user
    }

    definition doc_type {
        relation reader_role: role
        relation editer_role: role
        relation sharer_role: role
        relation editer_user: user
        relation reader_user: user
        relation sharer_user: user
        relation except_reader_role: role
        relation except_editer_role: role
        relation except_sharer_role: role
        relation except_reader_user: user
        relation except_editer_user: user
        relation except_sharer_user: user
        permission read = (((((reader_role->member + editer_role->member + sharer_role->member + reader_user + editer_user + sharer_user - except_reader_role->member) - except_editer_role->member) - except_sharer_role->member) - except_reader_user) - except_editer_user) - except_sharer_user
        permission edit = (editer_role->member + editer_user - except_editer_role->member) - except_editer_user
        permission share = (sharer_role->member + sharer_user - except_sharer_role->member) - except_sharer_user
    }

    definition document {
        relation doc_type: doc_type
        relation reader_role: role
        relation editer_role: role
        relation sharer_role: role
        relation editer_user: user
        relation reader_user: user
        relation sharer_user: user
        relation except_reader_role: role
        relation except_editer_role: role
        relation except_sharer_role: role
        relation except_reader_user: user
        relation except_editer_user: user
        relation except_sharer_user: user
        permission read = (((((doc_type->read + reader_role->member + editer_role->member + sharer_role->member + reader_user + editer_user + sharer_user - except_reader_role->member) - except_editer_role->member) - except_sharer_role->member) - except_reader_user) - except_editer_user) - except_sharer_user
        permission edit = (doc_type->edit +editer_role->member + editer_user - except_editer_role->member) - except_editer_user
        permission share = (doc_type->share + sharer_role->member + sharer_user - except_sharer_role->member) - except_sharer_user
    }

**localhost:8080/spicedb/createRelationships** :  <br /> Creates two relationships: one making User#61fcd27f902ce74215afd2de a member of the role:61fcd27f902ce74215afd2de_ItAdmin and another making role : 61fcd27f902ce74215afd2de_ItAdmin a reader of the page:61fcd27f902ce74215afd2df_Network_Team.<br /><br />

**localhost:8080/spicedb/createRelationships** : <br />Checks if User#61fcd27f902ce74215afd2de has read permissions on page:61fcd27f902ce74215afd2df_Network_Team.

## **How to run demo**
**Steps**
1. Clone repo using `git clone git@github.com:MysterTech/spiceDB_Demo.git`
2. cd into demo directory `cd spiceDB_Demo`
3. run `docker-compose up` to initialise spicedb enviroment
4. cd into client directory and run app to launch client : `cd spicedb-client && gradlew bootrun`
5. checkout `localhost:5050` for pgadmin dashboard


## **Additional Links**
These additional references should also help you:

* [Authzed docs](https://docs.authzed.com/)

