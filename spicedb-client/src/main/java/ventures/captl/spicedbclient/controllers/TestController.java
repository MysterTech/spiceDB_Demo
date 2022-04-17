package ventures.captl.spicedbclient.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ventures.captl.spicedbclient.services.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

  private TestService testService;

  public TestController(TestService testService) {
    this.testService = testService;
  }

  @GetMapping
  public String hello() {
    return testService.helloWorld();
  }
}
