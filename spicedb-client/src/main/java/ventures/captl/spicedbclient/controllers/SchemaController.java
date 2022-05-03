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
            """;
  private SchemaService schemaService;

  public SchemaController(SchemaService schemaService) {
    this.schemaService = schemaService;
  }

  @GetMapping("/createSchema")
  public void createSchema(){
    schemaService.persistSchema(schema);
  }
}
