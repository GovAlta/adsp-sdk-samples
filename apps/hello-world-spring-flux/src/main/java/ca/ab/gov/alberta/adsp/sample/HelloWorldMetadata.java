package ca.ab.gov.alberta.adsp.sample;

import org.springframework.context.annotation.Configuration;

import ca.ab.gov.alberta.adsp.sdk.metadata.AdspMetadata.Builder;
import ca.ab.gov.alberta.adsp.sdk.metadata.AdspMetadataSupport;

@Configuration
public class HelloWorldMetadata extends AdspMetadataSupport {
  @Override
  protected Builder customize(Builder builder) {    
    return builder.withApiPath("/hello-world/v1");
  }
}
