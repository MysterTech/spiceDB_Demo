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

  public String persistSchema(String schema) {
    ManagedChannel channel =
        ManagedChannelBuilder.forTarget("api-staging.captl.ventures:50051")
            // If SpiceDB requires TLS:
            // .useTransportSecurity()
            .usePlaintext()
            .build();

    BearerToken bearerToken = new BearerToken("some-random-key");
    SchemaServiceGrpc.SchemaServiceBlockingStub schemaService = SchemaServiceGrpc.newBlockingStub(channel)
        .withCallCredentials(bearerToken);

    WriteSchemaRequest request = WriteSchemaRequest
        .newBuilder()
        .setSchema(schema)
        .build();

    WriteSchemaResponse response;
    try {
      response = schemaService.writeSchema(request);
      return "Schema written to service.";
    } catch (Exception e) {
      return e.toString();
    }
  }
}
