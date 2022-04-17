package ventures.captl.spicedbclient.services;

import com.authzed.api.v1.SchemaServiceGrpc;
import com.authzed.api.v1.SchemaServiceOuterClass.WriteSchemaRequest;
import com.authzed.api.v1.SchemaServiceOuterClass.WriteSchemaResponse;
import com.authzed.grpcutil.BearerToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class SchemaService {

  public void persistSchema(String schema) {
    ManagedChannel channel = ManagedChannelBuilder
        .forTarget("localhost:50051")
        // If SpiceDB requires TLS:
        // .useTransportSecurity()
        .usePlaintext()
        .build();

    BearerToken bearerToken = new BearerToken("somerandomkey");
    SchemaServiceGrpc.SchemaServiceBlockingStub schemaService = SchemaServiceGrpc.newBlockingStub(channel)
        .withCallCredentials(bearerToken);

    WriteSchemaRequest request = WriteSchemaRequest
        .newBuilder()
        .setSchema(schema)
        .build();

    WriteSchemaResponse response;
    try {
      response = schemaService.writeSchema(request);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
