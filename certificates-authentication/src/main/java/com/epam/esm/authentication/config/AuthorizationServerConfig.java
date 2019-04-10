package com.epam.esm.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@ConfigurationProperties(prefix = "auth")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static final String CLIENT_ID = "client-id";
    private static final String CLIENT_SECRET = "$2a$10$kVuyPCSQNnrTgExnJp1O3uhFBpFDa/8PEPNMY3V15GG1LKA.4u.CC";
    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";

    private static final String privateKey ="-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIJKQIBAAKCAgEAzD063GK6ULCi+WRdYTxYVxstcivqjgHQvPgF9lnz6WUQRQdT\n" +
            "4qk4e0h1V1XmbPsXW3g/6noWqLCCl/4+uMH53mFjP+AwbaWnZ5Ep9QV8vhHssPi0\n" +
            "pXcf9hYvE1sk8j80+RJ4c+wwpnD2GCLKs+ABx9N73J1h9ogJCvEX+AsHT0B9nzL4\n" +
            "DiwDwL0HOnrdm19v/YtQ8Dcm0ExSAeUsd3Kg8FL6obWI7ncmtW+TIGalFCHWPlJ4\n" +
            "MYclcVzxedAUt5f1+cX5VQW53Whmirh5tw5Flc1a4pCF4M5cR1XGd4PirXMoJGPn\n" +
            "LESMWWHpx4WoVuGlX8GnnxdHvmiuoNL6scOEx6FKpLN4dl5Ctyx2GypTCuxfDoAR\n" +
            "WlGyEuUASolnlyp0n9/FwSk5jb/GUlsVfL8nN/JuPf36ABSTpIYuJmsEHVYn8kxI\n" +
            "RgtTdXsBzCqyT8dkubAPflrg2Za6zy0AV3+6JmEMTo81tanJBnP06K3o46gUX6Yx\n" +
            "aYvxKbn9kcdcgsJopgwWqqWEHfSS2fnpeapKDmD+UkL9bIoqjqX6MTRYknkNGYfs\n" +
            "b+KYwR755RqKO46aMM/zCGnivzPImWQoHOp52KXHdmRY8QNNOg/PltwW+XOdbHbb\n" +
            "o74cjhEUEsl9KmIqA64HML8n/CvtP0mJLIKHGLPA6YVWJpGSTXgAYqQmw10CAwEA\n" +
            "AQKCAgAfLF1WlA8iD8PgUuxZ0g8dxH0sRv23DRniLe0jC9nJzu4ryxGHsBJ55rZa\n" +
            "TTctfSm0gWcoD9hziPV1442JP7o2siCm+D4MDlO94A9KYbsHe87nuB5Cfys2sW4g\n" +
            "AzJhczpPpf0SnINUdDtrtavAxrke2e2236K7MUpIDEJ/TpegBhTmfq4T463y9icr\n" +
            "SuOWOnvqdCA3egOHmY6O0X73AF0GMXQlyIKM+0zCNrR7Jo+dwghkXMwRqzIQ2oHU\n" +
            "+OigmSYnKIuE6anT3IKCO+QAB8KhOcXcfPiiNn0pOjNfvJaVqNUAsOrw+8l8PxCi\n" +
            "rojiMfC+69z80F3X9zxoOaugBFq4o90+jNpHA/95raLOZULYyzSlWZefRlMNxLCU\n" +
            "XdQ716eRwws+NY01++gCpSA0zl2xw59JImQwy4YcgPlIpQ3coXMpxi7sd4kbDk8D\n" +
            "tgqTTxDOnfTejVNrDMC330thrtkM2JUIjvl1q9piI3wQRkEcAEKDtO8rIoSRwn+l\n" +
            "pBt1LAvMlkY6fN+K5pz5yUyl2MAWF87wp8i7Xt54/OIdMx3KgG1jTlbhq3VwEVIP\n" +
            "i2x/Z0wHEY8P209TMTdz1kRemVvGK7Vgrl7ZsGEqrFvdlo2tZxeXxRK/zbSu1Zjy\n" +
            "yAkmZwpDOHj64cA38BWEgiFMeRrwO4ou9eU5uyt+9OihLZXvgQKCAQEA5UZSC/mJ\n" +
            "lIyqDi5PJjIk5qmXgEjXYnVHAnkT1ykV30qDbM5qjot2sNdazo/2VNwjzbPcGcQE\n" +
            "84ieAvKEk0rbDDp5AWErxEuztU5zbZgOvjxhYjWcQq5thLgnEu+IYzaq9l+PIoNo\n" +
            "2ZlxIkltTcUKA7eDWL8Fczrx+WAsE2sZTIuWQVcATnK0KT5xBECzU9xsMSQe+1Uj\n" +
            "snTmdhqAlXhzftZArx8dMI7RjpESTFTTvBotPfaK4vhcIvsYS2iCvD3CtwyFbpfk\n" +
            "MHB+rfDicI6JirMSWtFK5Us7w8MHUCE6pSFxU+GwSyZqNsf9qg63zk2ydBIcY6hq\n" +
            "JQtZpHGYkGXzYQKCAQEA5AvWHptchFfnxrd7JIWt/9ZCjex42j5N0ttVjNaiXJug\n" +
            "xXM5aJxXRDeB0hxkvyUFAqNHXEgfBE4nRMR77on/fB6UaV2G+11fqpInI6tT9SrC\n" +
            "PJ1yWntZxHi5P5PXoLRsJcH8XBYrnKrI8S5+xWy0ePh2dY2elUBowoyoBNZ7T5qO\n" +
            "f2jcwR8wDdJdfLl1C9QO2shZwJI4UffXptMFBKgITAklX/nT7ZBVTL118bQ+2q22\n" +
            "ZPdPX+H0ScfFLe8Ar1AV1+nXjaGE0fhezSotiayGAHxvvlv20ROKZKmPGIUAPZeg\n" +
            "R/iG6MgFXwjvolmAzIA0aIR8AHsMFSy8fEXf5uMNfQKCAQAO04NOvu5Equv8SsZX\n" +
            "g/qa5/CiHRPAjDDxWf8MdN2zONBS4u5FFYOxX9vBa9HUHwA5tx3OlLx1Oypsql4k\n" +
            "YDzWB5n3mt8HrJWMbexM8/5QEvuBLFJUhY8TfA0azl6Ve92GXRuWccT//Uoxm2n0\n" +
            "E3/YSa+2t3OUxgULyUpQXyadqpEm3Idqi0RXPnB0g/KUw7ZDX0CZpCHQ0npsvL3/\n" +
            "7HAbjaNVL7/lefbXL05tnTar/U1bqmvEzaa3pGEnzzRVE/FZgPfH9Q6Oi1fFy8P4\n" +
            "317Jl1bihJJBTaIpwJzic6OlNgpsEDYkRfGsZTZkHtuH/cJCe0FWuHdUue0sEyR4\n" +
            "gf9BAoIBAQCKOrZ+dPPecJ5YcK1fFPzEf+51sqoKuAtaTYMMzKk780W/ibl5tJKd\n" +
            "EfxKDNXwmxLvZ2OBB2JtrKXW5Dau/8zyQmL5fyyrvwyXCuJYl6hbiqCUN2eyeuSN\n" +
            "2BOJ+yaQDe0j8fLzKqki3M7SirljGCJI1WqQlNhUZedaTgImdI/oT6oihsCQ+0OY\n" +
            "jTQHvy64jcC3Uww8UyV6Yf3A7FY1XVkyJbUW+QtCC+P4kTsl2Hjk+rj9eH8Pgpoe\n" +
            "As59qkrC+YtUBjWXbxlgYOmt+OwDcKUFNGhTfdEdKHgRzkmjtjLQItGP7pBwF5ZR\n" +
            "5cvDn/cXfnrGA9BaRjurtiNGPOCcRnLNAoIBAQC0glD14YXKE/IopKykg7yq1IKl\n" +
            "Wvbp+FqgYxO9DSB5YSgqBERE2m9zEzkWSioshsrJFi2vFnQYttFutZUIEeLjMDcZ\n" +
            "5alqUmo6Jg6HVgH9jpLbDwjxLNcyiNljGdqHcbXh5gQ7vVZghfl5+J1b2vfuK+po\n" +
            "mQ25jMIO3JIb08ksBE2ojioirqiOL+xKGuNppxwtqtBzC6Mkm/ditSOcCSqcBIfV\n" +
            "T3atsHValn+FFqwpRgc+tZRENQ1nEJqdnifMIx+gjvM6umc7u9K8hHsZoa54viQt\n" +
            "4SL3umPmFfiKyNQYQ1GlAtwfyRcyAO/OCrrRLIrUYffd2HQxtpWPIo1TbGmB\n" +
            "-----END RSA PRIVATE KEY-----\n";
    private static final String publicKey ="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDMPTrcYrpQsKL5ZF1hPFhXGy1yK+qOAdC8+AX2WfPpZRBFB1PiqTh7SHVXVeZs+xdbeD/qehaosIKX/j64wfneYWM/4DBtpadnkSn1BXy+Eeyw+LSldx/2Fi8TWyTyPzT5Enhz7DCmcPYYIsqz4AHH03vcnWH2iAkK8Rf4CwdPQH2fMvgOLAPAvQc6et2bX2/9i1DwNybQTFIB5Sx3cqDwUvqhtYjudya1b5MgZqUUIdY+UngxhyVxXPF50BS3l/X5xflVBbndaGaKuHm3DkWVzVrikIXgzlxHVcZ3g+KtcygkY+csRIxZYenHhahW4aVfwaefF0e+aK6g0vqxw4THoUqks3h2XkK3LHYbKlMK7F8OgBFaUbIS5QBKiWeXKnSf38XBKTmNv8ZSWxV8vyc38m49/foAFJOkhi4mawQdVifyTEhGC1N1ewHMKrJPx2S5sA9+WuDZlrrPLQBXf7omYQxOjzW1qckGc/TorejjqBRfpjFpi/Epuf2Rx1yCwmimDBaqpYQd9JLZ+el5qkoOYP5SQv1siiqOpfoxNFiSeQ0Zh+xv4pjBHvnlGoo7jpowz/MIaeK/M8iZZCgc6nnYpcd2ZFjxA006D8+W3Bb5c51sdtujvhyOERQSyX0qYioDrgcwvyf8K+0/SYksgocYs8DphVYmkZJNeABipCbDXQ== alexa@DESKTOP-LLMKGF0";

    private AuthenticationManager authenticationManager;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(CLIENT_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
                .scopes(SCOPE_READ, SCOPE_WRITE);
    }
}


