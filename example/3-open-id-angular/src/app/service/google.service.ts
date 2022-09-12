import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  // Url of the Identity Provider
  issuer: 'https://accounts.google.com',

  // URL of the SPA to redirect the user to after login
  redirectUri: window.location.origin,

  // The SPA's id. The SPA is registerd with this id at the auth-server
  // clientId: 'server.code',
  clientId: '866645626978-q8kpvoja0c6f3oub3kpooh92p0u1tukq.apps.googleusercontent.com',


  scope: 'openid',

  showDebugInformation: true,
  sessionChecksEnabled: true,
  strictDiscoveryDocumentValidation: false
};


@Injectable({
  providedIn: 'root'
})
export class GoogleService {


  constructor(private oauthService: OAuthService) {
  }

  connect() {
    this.oauthService.configure(authCodeFlowConfig)
    this.oauthService.loadDiscoveryDocument()
    this.oauthService.initCodeFlow()
  }


  initConnectGoogle(): Promise<any> {
    this.oauthService.configure(authCodeFlowConfig)
    return this.oauthService.loadDiscoveryDocument().then(() => {
      return this.oauthService.tryLogin()
    }).then(value => {
      console.log("Google token state", this.oauthService.hasValidAccessToken() ? "Connected" : "Not Connected");
    })
  }

  isConnect() {
    return this.oauthService.hasValidAccessToken()
  }

  logout() {
    this.oauthService.logOut()
  }

  getToken(): string {
    let token = this.oauthService.getIdToken();
    console.log("Google auth token is :", token)
    return token
  }

}
