import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { GoogleService } from './google.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private isLoggedSubject: Subject<boolean> = new Subject();


  private password?: string;
  private username?: string;

  constructor(private httpClient: HttpClient, private googleService: GoogleService, private router: Router) {
  }

  connect(username: string, password: string): Observable<any> {
    let token = this.createToken(username, password);
    let options = {
      headers: {
        'Authorization': token
      }
    };
    return this.httpClient.get<string>('/api/login', options).pipe(map(value => {
      this.password = password;
      this.username = username;
      console.log("Connected")
    }))
  }

  private createToken(username?: string, password?: string) {
    let token = `Basic ` + btoa(`${username}:${password}`);
    return token;
  }


  isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  getBasicAuthHeaderValue(): string {
    return this.createToken(this.username, this.password)
  }


  initConnect(): void {
    this.googleService.initConnectGoogle().then(value => this.isLoggedSubject.next(this.googleService.isConnect()))
  }

  connectWithGoogle() {
    return this.googleService.connect();

  }

  authHasBasic(): boolean {
    return !!this.password && !!this.username;
  }

  logout() {
    console.log("Logout")
    if (this.authHasBasic()) {
      this.password = undefined;
      this.username = undefined;
    } else if (this.googleService.isConnect()) {
      this.googleService.logout();
    }
    this.isLoggedSubject.next(false);
    this.router.navigateByUrl("/login").then(console.log).catch(console.error)
  }

  authHasGoogle() {
    return this.googleService.isConnect();
  }
}
