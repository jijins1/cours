import { ErrorHandler, NgModule } from '@angular/core';
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

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    BieresComponent,
    ErrorListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule

  ],
  providers: [{
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
