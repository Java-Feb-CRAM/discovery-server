package com.smoothstack.utopia.discoveryserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rob Maes
 * Apr 11 2021
 */
@Profile("dev")
@RestController
public class AbcController {

  @Value("${test.data}")
  private String testData;

  @GetMapping("/abc")
  public String abc() {
    return testData;
  }
}
