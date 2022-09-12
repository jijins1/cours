import { Component } from '@angular/core';
import { LoginService } from './service/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'open-id-angular';


  constructor(private loginService: LoginService) {
  }

  logout() {
    this.loginService.logout();
  }
}
