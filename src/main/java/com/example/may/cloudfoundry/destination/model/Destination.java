package com.example.may.cloudfoundry.destination.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evelina Tubalets
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Destination {

    private Owner owner;

    private DestinationConfiguration destinationConfiguration;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner {

        @JsonProperty("SubaccountId")
        private String subaccountId;
        @JsonProperty("InstanceId")
        private String instanceId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DestinationConfiguration {

        @JsonProperty("Name")
        private String name;
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Authentication")
        private String authentication;
        @JsonProperty("ProxyType")
        private String proxyType;
        @JsonProperty("Properties")
        private Map<String, String> properties = new HashMap<>();

        @JsonAnySetter
        public void setProperties(final String key, final String value) {
            properties.put(key, value);
        }
    }
}