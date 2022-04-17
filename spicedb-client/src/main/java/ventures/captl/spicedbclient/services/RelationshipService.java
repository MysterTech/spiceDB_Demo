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

    PermissionService.WriteRelationshipsRequest request =
        PermissionService.WriteRelationshipsRequest.newBuilder()
            .addUpdates(
                RelationshipUpdate.newBuilder()
                    .setOperation(RelationshipUpdate.Operation.OPERATION_CREATE)
                    .setRelationship(
                        Relationship.newBuilder()
                            .setResource(
                                ObjectReference.newBuilder()
                                    .setObjectType("blog/post")
                                    .setObjectId("1")
                                    .build())
                            .setRelation("writer")
                            .setSubject(
                                SubjectReference.newBuilder()
                                    .setObject(
                                        ObjectReference.newBuilder()
                                            .setObjectType("blog/user")
                                            .setObjectId("emilia")
                                            .build())
                                    .build())
                            .build())
                    .build())
            .build();

    PermissionService.WriteRelationshipsResponse response;
    try {
      response = permissionsService.writeRelationships(request);
      String zedToken = response.getWrittenAt().getToken();
      System.out.println(zedToken);
    } catch (Exception e) {
      System.out.println("Error connecting to spiceDB" + e);
    }
  }

  public Permissionship checkRelationships() {
    ManagedChannel channel = ManagedChannelBuilder
        .forTarget("localhost:50051")
        .usePlaintext()
        .build();

    BearerToken bearerToken = new BearerToken("somerandomkey");

    PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionsService = PermissionsServiceGrpc.newBlockingStub(channel)
        .withCallCredentials(bearerToken);

    ZedToken zedToken = ZedToken.newBuilder()
        .setToken("GgMKATQ=")
        .build();

    PermissionService.CheckPermissionRequest request = PermissionService.CheckPermissionRequest.newBuilder()
        .setConsistency(
            PermissionService.Consistency.newBuilder()
                .setAtLeastAsFresh(zedToken)
                .build())
        .setResource(
            ObjectReference.newBuilder()
                .setObjectType("blog/post")
                .setObjectId("1")
                .build())
        .setSubject(
            SubjectReference.newBuilder()
                .setObject(
                    ObjectReference.newBuilder()
                        .setObjectType("blog/user")
                        .setObjectId("emilia")
                        .build())
                .build())
        .setPermission("read")
        .build();

    PermissionService.CheckPermissionResponse response;
    try {
      response = permissionsService.checkPermission(request);
      return response.getPermissionship();
    } catch (Exception e) {
      // Uh oh!
    }
    return null;
  }
}
