package com.benz.oauth2.api.entity;

import com.benz.oauth2.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "oauth_client_details",schema = Schema.SECURITY)
@Getter
@Setter
public class OAuthClient {

    @Id
    @Column(name = "client_id")
    private String clientId;
    @Column(name="client_secret")
    private String clientSecret;
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    @Column(name = "scope")
    private String scope;
    @Column(name = "access_token_validity")
    private int accessTokenValidity;
    @Column(name = "refresh_token_validity")
    private int refreshTokenValidity;
    @Column(name = "resource_ids")
    private String resourceId;
    @Column(name="authorized_grant_types")
    private String authorizedGrantType;
    @Column(name = "authorities")
    private String authorities;
    @Column(name = "additional_information")
    private String additionalInformation;
    @Column(name = "autoapprove")
    private String autoApprove;


}
