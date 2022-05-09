package ventures.captl.spicedbclient.services;

import com.authzed.api.v1.Core.ObjectReference;
import com.authzed.api.v1.Core.Relationship;
import com.authzed.api.v1.Core.RelationshipUpdate;
import com.authzed.api.v1.Core.SubjectReference;
import com.authzed.api.v1.Core.ZedToken;
import com.authzed.api.v1.PermissionService;
import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;
import com.authzed.api.v1.PermissionService.LookupResourcesResponse;
import com.authzed.api.v1.PermissionsServiceGrpc;
import com.authzed.grpcutil.BearerToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.Iterator;
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
                                            .setObjectId("61Sanjay902ce74215afd2de")
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
                                    .setObjectType("role")
                                    .setObjectId("61fcd27f902ce74215afd2de_ItAdmin")
                                    .build())
                            .setRelation("member")
                            .setSubject(
                                SubjectReference.newBuilder()
                                    .setObject(
                                        ObjectReference.newBuilder()
                                            .setObjectType("user")
                                            .setObjectId("61Sai123902ce74215afd2de")
                                            .build())
                                    .build())
                            .build())
                    .build())
            .build();
    PermissionService.WriteRelationshipsRequest request3 =
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

    PermissionService.WriteRelationshipsRequest request4 =
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
                            .setRelation("except_reader_user")
                            .setSubject(
                                SubjectReference.newBuilder()
                                    .setObject(
                                        ObjectReference.newBuilder()
                                            .setObjectType("user")
                                            .setObjectId("61Sanjay902ce74215afd2de")
                                            .build())
                                    .build())
                            .build())
                    .build())
            .build();

    PermissionService.WriteRelationshipsResponse response1;
    PermissionService.WriteRelationshipsResponse response2;
    PermissionService.WriteRelationshipsResponse response3;
    PermissionService.WriteRelationshipsResponse response4;

    try {
      response1 = permissionsService.writeRelationships(request1);
      System.out.println("response1 done");
      response2 = permissionsService.writeRelationships(request2);
      System.out.println("response2 done");
      response3 = permissionsService.writeRelationships(request3);
      System.out.println("response3 done");
      response4 = permissionsService.writeRelationships(request4);
      System.out.println("response4 done");
      String zedToken = response3.getWrittenAt().getToken();
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

  public String getRelTree() {
    ManagedChannel channel =
        ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext().build();

    BearerToken bearerToken = new BearerToken("somerandomkey");
    PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionsService =
        PermissionsServiceGrpc.newBlockingStub(channel).withCallCredentials(bearerToken);

    ZedToken zedToken = ZedToken.newBuilder().setToken("GgQKAjEw").build();

    PermissionService.LookupResourcesRequest expandRequest1 =
        PermissionService.LookupResourcesRequest.newBuilder()
            .setConsistency(
                PermissionService.Consistency.newBuilder().setAtLeastAsFresh(zedToken).build())
            .setResourceObjectType("page")
            .setPermission("read")
            .setSubject(
                SubjectReference.newBuilder()
                    .setObject(
                        ObjectReference.newBuilder()
                            .setObjectType("user")
                            .setObjectId("61Sanjay902ce74215afd2de")
                            .build())
                    .build())
            .build();
    PermissionService.LookupResourcesRequest expandRequest2 =
        PermissionService.LookupResourcesRequest.newBuilder()
            .setConsistency(
                PermissionService.Consistency.newBuilder().setAtLeastAsFresh(zedToken).build())
            .setResourceObjectType("page")
            .setPermission("read")
            .setSubject(
                SubjectReference.newBuilder()
                    .setObject(
                        ObjectReference.newBuilder()
                            .setObjectType("user")
                            .setObjectId("61Sai123902ce74215afd2de")
                            .build())
                    .build())
            .build();

    try {
      String stringResponse = new String();
      Iterator<LookupResourcesResponse> responseStream1 = permissionsService.lookupResources(expandRequest1);
      stringResponse = stringResponse.concat("Permissions for Sanjay:").concat("\n");
      while (responseStream1.hasNext()) {
        LookupResourcesResponse response = responseStream1.next();
        stringResponse = stringResponse.concat(response.toString()).concat("\n");
      }
      Iterator<LookupResourcesResponse> responseStream2 = permissionsService.lookupResources(expandRequest2);
      stringResponse = stringResponse.concat("Permissions for Sai:").concat("\n");
      while (responseStream2.hasNext()) {
        LookupResourcesResponse response = responseStream2.next();
        stringResponse = stringResponse.concat(response.toString());
      }
      return stringResponse;
    } catch (Exception e) {
      System.out.println("Error");
      e.printStackTrace();
    }
    return null;
  }
}
