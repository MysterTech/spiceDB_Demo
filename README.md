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
                definition page_type {
                	relation reader_role: role
                	relation editer_role: role
                	relation reader_user: user
                	relation editer_user: user
                	relation except_reader_role: role
                	relation except_editer_role: role
                	relation except_reader_user: user
                	relation except_editer_user: user
                	permission read = (((reader_role->member - except_reader_role->member + reader_user) - except_reader_user + editer_role->member) - except_editer_role->member + editer_user) - except_editer_user
                	permission edit = (editer_role->member - except_editer_role + editer_user) - except_editer_user
                	permission read_role = ((reader_role - except_reader_role) + editer_role) - except_editer_role
                	permission edit_role = editer_role - except_editer_role
                }
                
                definition page {
                	relation page_type: page_type
                	relation reader_role: role
                	relation editer_role: role
                	relation reader_user: user
                	relation editer_user: user
                	relation except_reader_role: role
                	relation except_editer_role: role
                	relation except_reader_user: user
                	relation except_editer_user: user
                	permission read = (((page_type->read + reader_role->member - except_reader_role->member + reader_user) - except_reader_user + editer_role->member) - except_editer_role->member + editer_user) - except_editer_user
                	permission edit = (page_type->edit + editer_role->member - except_editer_role + editer_user) - except_editer_user
                	permission read_role = ((page_type->read_role + reader_role - except_reader_role) + editer_role) - except_editer_role
                	permission edit_role = page_type->edit_role + editer_role - except_editer_role
                }
                
                definition doc_type {
                	relation reader_role: role
                	relation editer_role: role
                	relation sharer_role: role
                	relation reader_user: user
                	relation editer_user: user
                	relation sharer_user: user
                	relation except_reader_role: role
                	relation except_editer_role: role
                	relation except_sharer_role: role
                	relation except_reader_user: user
                	relation except_editer_user: user
                	relation except_sharer_user: user
                	permission read = (((((reader_role->member - except_reader_role->member + reader_user) - except_reader_user + editer_role->member) - except_editer_role->member + editer_user) - except_editer_user + sharer_role->member) - except_sharer_role->member + sharer_user) - except_sharer_user
                	permission edit = (editer_role->member - except_editer_role->member + editer_user) - except_editer_user
                	permission share = (sharer_role->member - except_sharer_role->member + sharer_user) - except_sharer_user
                	permission read_role = ((reader_role - except_reader_role) + editer_role) - except_editer_role
                	permission edit_role = editer_role - except_editer_role
                	permission share_role = sharer_role - except_sharer_role
                }
                
                definition document {
                	relation doc_type: doc_type
                	relation reader_role: role
                	relation editer_role: role
                	relation sharer_role: role
                	relation reader_user: user
                	relation editer_user: user
                	relation sharer_user: user
                	relation except_reader_role: role
                	relation except_editer_role: role
                	relation except_sharer_role: role
                	relation except_reader_user: user
                	relation except_editer_user: user
                	relation except_sharer_user: user
                	permission read = (((((doc_type->read + reader_role->member - except_reader_role->member + reader_user) - except_reader_user + editer_role->member) - except_editer_role->member + editer_user) - except_editer_user + sharer_role->member) - except_sharer_role->member + sharer_user) - except_sharer_user
                	permission edit = (doc_type->edit + editer_role->member - except_editer_role->member + editer_user) - except_editer_user
                	permission share = (doc_type->share + sharer_role->member - except_sharer_role->member + sharer_user) - except_sharer_user
                	permission read_role = ((reader_role - except_reader_role) + editer_role) - except_editer_role
                	permission edit_role = editer_role - except_editer_role
                	permission share_role = sharer_role - except_sharer_role
                }

**localhost:8080/spicedb/createRelationships** :  <br /> Creates two relationships: one making User#61fcd27f902ce74215afd2de a member of the role:61fcd27f902ce74215afd2de_ItAdmin and another making role : 61fcd27f902ce74215afd2de_ItAdmin a reader of the page:61fcd27f902ce74215afd2df_Network_Team.<br /><br />

**localhost:8080/spicedb/checkRelationships** : <br />Checks if User#61fcd27f902ce74215afd2de has read permissions on page:61fcd27f902ce74215afd2df_Network_Team.

**localhost:8080/spicedb/getRelTree** : <br />gets all objects of numspace for particular subject

## **How to run demo**
**Steps**
1. Clone repo using `git clone git@github.com:MysterTech/spiceDB_Demo.git`
2. cd into demo directory `cd spiceDB_Demo`
3. run `docker-compose up` to initialise spicedb enviroment
4. cd into client directory and run app to launch client : `cd spicedb-client && ./gradlew bootrun`
5. checkout `localhost:5050` for pgadmin dashboard


## **Additional Links**
These additional references should also help you:

* [Authzed docs](https://docs.authzed.com/)
* [PGAdmin dashboard](localhost:5050)

