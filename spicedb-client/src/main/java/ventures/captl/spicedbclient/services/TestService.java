package ventures.captl.spicedbclient.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class TestService {
  public String helloWorld() {
    return new String("Hello World!!");
  }
}
