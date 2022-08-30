import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from '../service/login.service';

@Injectable()
export class HeaderInterceptor implements HttpInterceptor {


  constructor(private loginService: LoginService) {
  }

  intercept(httpRequest: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let headers :any= {
      'x-requested-with': 'XMLHttpRequest',
      'Content-type': 'application/json',
    }

    if(!httpRequest.headers.has('Authorization')){
      headers['Authorization'] = this.loginService.getBasicAuthHeaderValue()
    }
    return next.handle(httpRequest.clone({setHeaders: headers}));
  }
}