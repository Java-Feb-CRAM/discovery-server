package com.smoothstack.utopia.discoveryserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rob Maes
 * Apr 11 2021
 */
@RestController
public class TestController {

  @Value("${test.data}")
  private String testData;

  @GetMapping("/test")
  public String test() {
    return testData;
  }
}
