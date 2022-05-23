package ventures.captl.spicedbclient.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ventures.captl.spicedbclient.services.SchemaService;

@RestController
@RequestMapping("/spicedb")
public class SchemaController {
  String schema = """
                definition user {}
                 
                 definition role {
                 	relation member: user
                 }
                 
                 /** resource */
                 definition page_type {
                 	relation reader: user | role#member
                 	relation editer: user | role#member
                 	relation except_reader: user | role#member
                 	relation except_editer: user | role#member
                 	permission read = (reader + editer - except_editer) - except_reader
                 	permission edit = editer - except_editer
                 }
                 
                 definition page {
                 	relation page_type: page_type
                 	relation reader: user | role#member
                 	relation editer: user | role#member
                 	relation except_reader: user | role#member
                 	relation except_editer: user | role#member
                 	permission read = (page_type->read + reader + editer - except_editer) - except_reader
                 	permission edit = page_type->edit + editer - except_editer
                 }
                 
                 definition doc_type {
                 	relation reader: user | role#member | doc_type#reader | doc_type#editer | doc_type#sharer
                 	relation editer: user | role#member | doc_type#editer
                 	relation sharer: user | role#member | doc_type#sharer
                 	relation except_reader: user | role#member | doc_type#reader | doc_type#editer | doc_type#sharer
                 	relation except_editer: user | role#member | doc_type#editer
                 	relation except_sharer: user | role#member | doc_type#sharer
                 	permission read = ((reader + sharer + editer - except_reader) - except_sharer) - except_editer
                 	permission edit = editer - except_editer
                 	permission share = sharer - except_sharer
                 }
                 
                 definition document {
                 	relation doc_type: doc_type
                 	relation reader: user | role#member
                 	relation editer: user | role#member
                 	relation sharer: user | role#member
                 	relation except_reader: user | role#member
                 	relation except_editer: user | role#member
                 	relation except_sharer: user | role#member
                 	permission read = ((doc_type->read + reader + sharer + editer - except_reader) - except_sharer) - except_editer
                 	permission edit = doc_type->edit + editer - except_editer
                 	permission share = doc_type->share + sharer - except_sharer
                 }
            """;
  private SchemaService schemaService;
  public SchemaController(SchemaService schemaService) {
    this.schemaService = schemaService;
  }

  @GetMapping("/createSchema")
  public String createSchema(){
    return schemaService.persistSchema(schema);
  }
}
