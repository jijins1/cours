import { APP_INITIALIZER, ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BieresComponent } from './component/bieres/bieres.component';
import { LoginComponent } from './component/login/login.component';
import { FormsModule } from '@angular/forms';
import { GlobalErrorHandler } from './service/error.service';
import { ErrorListComponent } from './component/error-list/error-list.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HeaderInterceptor } from './interceptor/header.interceptor';
import { OAuthModule } from 'angular-oauth2-oidc';
import { GoogleService } from './service/google.service';
import { LoginService } from './service/login.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BandeauComponent } from './component/bandeau/bandeau.component';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    BieresComponent,
    ErrorListComponent,
    BandeauComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    OAuthModule.forRoot(),
    BrowserAnimationsModule,
    MatButtonModule

  ],
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: (loginService: LoginService)=> {
      return () => loginService.initConnect();
    },
    deps: [LoginService],
    multi: true
  }, {
    provide: ErrorHandler,
    useClass: GlobalErrorHandler,
    multi: false
  }, {
    provide: HTTP_INTERCEPTORS,
    useClass: HeaderInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
