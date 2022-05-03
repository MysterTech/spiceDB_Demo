package ventures.captl.spicedbclient.services;

import com.authzed.api.v1.Core.ObjectReference;
import com.authzed.api.v1.Core.Relationship;
import com.authzed.api.v1.Core.RelationshipUpdate;
import com.authzed.api.v1.Core.SubjectReference;
import com.authzed.api.v1.Core.ZedToken;
import com.authzed.api.v1.PermissionService;
import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;
import com.authzed.api.v1.PermissionsServiceGrpc;
import com.authzed.grpcutil.BearerToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class RelationshipService {

  public void persistRelationship() {
    ManagedChannel channel =
        ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext().build();

    BearerToken bearerToken = new BearerToken("somerandomkey");
    PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionsService =
        PermissionsServiceGrpc.newBlockingStub(channel).withCallCredentials(bearerToken);

    PermissionService.WriteRelationshipsRequest request1 =
        PermissionService.WriteRelationshipsRequest.newBuilder()
            .addUpdates(
                RelationshipUpdate.newBuilder()
                    .setOperation(RelationshipUpdate.Operation.OPERATION_CREATE)
                    .setRelationship(
                        Relationship.newBuilder()
                            .setResource(
                                ObjectReference.newBuilder()
                                    .setObjectType("role")
                                    .setObjectId("61fcd27f902ce74215afd2de_ItAdmin")
                                    .build())
                            .setRelation("member")
                            .setSubject(
                                SubjectReference.newBuilder()
                                    .setObject(
                                        ObjectReference.newBuilder()
                                            .setObjectType("user")
                                            .setObjectId("61fcd27f902ce74215afd2de")
                                            .build())
                                    .build())
                            .build())
                    .build())
            .build();
    PermissionService.WriteRelationshipsRequest request2 =
        PermissionService.WriteRelationshipsRequest.newBuilder()
            .addUpdates(
                RelationshipUpdate.newBuilder()
                    .setOperation(RelationshipUpdate.Operation.OPERATION_CREATE)
                    .setRelationship(
                        Relationship.newBuilder()
                            .setResource(
                                ObjectReference.newBuilder()
                                    .setObjectType("page")
                                    .setObjectId("61fcd27f902ce74215afd2df_Network_Team")
                                    .build())
                            .setRelation("reader_role")
                            .setSubject(
                                SubjectReference.newBuilder()
                                    .setObject(
                                        ObjectReference.newBuilder()
                                            .setObjectType("role")
                                            .setObjectId("61fcd27f902ce74215afd2de_ItAdmin")
                                            .build())
                                    .build())
                            .build())
                    .build())
            .build();

    PermissionService.WriteRelationshipsResponse response1;
    PermissionService.WriteRelationshipsResponse response2;
    try {
      response1 = permissionsService.writeRelationships(request1);
      response2 = permissionsService.writeRelationships(request2);
      String zedToken = response1.getWrittenAt().getToken();
      System.out.println(zedToken);
    } catch (Exception e) {
      System.out.println("Error connecting to spiceDB" + e);
    }
  }

  public List<Permissionship> checkRelationships() {
    ManagedChannel channel =
        ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext().build();

    BearerToken bearerToken = new BearerToken("somerandomkey");

    PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionsService =
        PermissionsServiceGrpc.newBlockingStub(channel).withCallCredentials(bearerToken);

    ZedToken zedToken = ZedToken.newBuilder().setToken("GgMKATQ=").build();

    PermissionService.CheckPermissionRequest request1 =
        PermissionService.CheckPermissionRequest.newBuilder()
            .setConsistency(
                PermissionService.Consistency.newBuilder().setAtLeastAsFresh(zedToken).build())
            .setResource(
                ObjectReference.newBuilder()
                    .setObjectType("role")
                    .setObjectId("61fcd27f902ce74215afd2de_ItAdmin")
                    .build())
            .setSubject(
                SubjectReference.newBuilder()
                    .setObject(
                        ObjectReference.newBuilder()
                            .setObjectType("user")
                            .setObjectId("61fcd27f902ce74215afd2de")
                            .build())
                    .build())
            .setPermission("member")
            .build();

    PermissionService.CheckPermissionRequest request2 =
        PermissionService.CheckPermissionRequest.newBuilder()
            .setConsistency(
                PermissionService.Consistency.newBuilder().setAtLeastAsFresh(zedToken).build())
            .setResource(
                ObjectReference.newBuilder()
                    .setObjectType("page")
                    .setObjectId("61fcd27f902ce74215afd2df_Network_Team")
                    .build())
            .setSubject(
                SubjectReference.newBuilder()
                    .setObject(
                        ObjectReference.newBuilder()
                            .setObjectType("user")
                            .setObjectId("61fcd27f902ce74215afd2de")
                            .build())
                    .build())
            .setPermission("read")
            .build();

    try {
      Permissionship response1 = permissionsService.checkPermission(request1).getPermissionship();
      Permissionship response2 = permissionsService.checkPermission(request2).getPermissionship();
      List<Permissionship> permissionshipList = new ArrayList<>();
      permissionshipList.add(response1);
      permissionshipList.add(response2);

      System.out.println(permissionshipList);
      return permissionshipList;
      // Uh oh!
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
