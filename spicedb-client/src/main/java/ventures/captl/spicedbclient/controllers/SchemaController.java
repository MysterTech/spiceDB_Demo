package ventures.captl.spicedbclient.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ventures.captl.spicedbclient.services.SchemaService;

@RestController
@RequestMapping("/spicedb")
public class SchemaController {
  String schema = """
            definition blog/user {}
                            
            definition blog/post {
                relation reader: blog/user
                relation writer: blog/user
                            
                permission read = reader + writer
                permission write = writer
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
