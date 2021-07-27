package com.airtel.cs.model.response.consolelog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkHeaders {
        @JsonProperty("Accept")
        public String accept;
        @JsonProperty("Accept-Encoding")
        public String acceptEncoding;
        @JsonProperty("Accept-Language")
        public String acceptLanguage;
        @JsonProperty("Authorization")
        public String authorization;
        @JsonProperty("Connection")
        public String connection;
        @JsonProperty("Content-Type")
        public String contentType;
        @JsonProperty("Host")
        public String host;
        @JsonProperty("Opco")
        public String opco;
        @JsonProperty("Referer")
        public String referer;
        @JsonProperty("User-Agent")
        public String userAgent;
        public String lang;
        @JsonProperty("sr-client-id")
        public String srClientId;
        @JsonProperty("x-api-key")
        public String xApiKey;
        @JsonProperty("x-app-name")
        public String xAppName;
        @JsonProperty("x-app-type")
        public String xAppType;
        @JsonProperty("x-app-version")
        public String xAppVersion;
        @JsonProperty("x-channel")
        public String xChannel;
        @JsonProperty("x-client-id")
        public String xClientId;
        @JsonProperty("x-login-module")
        public String xLoginModule;
        @JsonProperty("x-service-id")
        public String xServiceId;
}
