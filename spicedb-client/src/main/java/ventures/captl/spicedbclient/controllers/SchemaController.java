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
