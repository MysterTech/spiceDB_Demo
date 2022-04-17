package ventures.captl.spicedbclient.controllers;

import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ventures.captl.spicedbclient.services.RelationshipService;

@RestController
@RequestMapping("/spicedb")
public class RelationshipController {
  private RelationshipService relationshipService;

  public RelationshipController(RelationshipService relationshipService) {
    this.relationshipService = relationshipService;
  }

  @GetMapping("/createRelationships")
  public void createRelationships() {
    relationshipService.persistRelationship();
  }

  @GetMapping("/checkRelationships")
  public Permissionship checkRelationships(){
    return relationshipService.checkRelationships();
  }
}
