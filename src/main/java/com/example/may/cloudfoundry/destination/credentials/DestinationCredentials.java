package com.example.may.cloudfoundry.destination.credentials;

import com.example.may.cloudfoundry.xsuaa.model.XsuaaClientCredentials;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
@ConfigurationProperties(prefix = "destination-service")
public class DestinationCredentials extends XsuaaClientCredentials {
}
