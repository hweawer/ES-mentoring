package com.epam.esm.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
public class AuthorizationProperties {
    @Value("{publicKey}")
    String publicKey;
    @Value("{privateKey}")
    String privateKey;
    @Value("{clientId}")
    String clientId;
    @Value("{clientSecret}")
    String clientSecret;

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
