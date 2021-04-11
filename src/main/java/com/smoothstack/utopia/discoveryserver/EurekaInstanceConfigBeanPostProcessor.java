package com.smoothstack.utopia.discoveryserver;

import com.netflix.appinfo.EurekaInstanceConfig;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Rob Maes
 * Apr 10 2021
 */
@Component
@Profile("dev")
public class EurekaInstanceConfigBeanPostProcessor
  implements BeanPostProcessor {

  @Value("${spring.application.name}")
  private String serviceName;

  @Value("${server.port}")
  private int port;

  private String fargateIp;

  {
    try {
      fargateIp = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      System.out.println("could not get fargate instance ip");
    }
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    if (bean instanceof EurekaInstanceConfigBean) {
      EurekaInstanceConfigBean instanceConfigBean =
        ((EurekaInstanceConfigBean) bean);
      instanceConfigBean.setInstanceId(
        fargateIp + ":" + serviceName + ":" + port
      );
      instanceConfigBean.setIpAddress(fargateIp);
      instanceConfigBean.setHostname(fargateIp);
      instanceConfigBean.setStatusPageUrl(
        "http://" + fargateIp + ":" + port + "/actuator/info"
      );
      instanceConfigBean.setHealthCheckUrl(
        "http://" + fargateIp + ":" + port + "/actuator/health"
      );
    }
    return bean;
  }
}
