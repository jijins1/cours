import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private password?: string;
  private username?: string;

  constructor(private httpClient: HttpClient) {
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

  isLogged(): boolean {
    return !!this.password && !!this.username;
  }

  getBasicAuthHeaderValue(): string {
    return this.createToken(this.username, this.password)
  }
}
