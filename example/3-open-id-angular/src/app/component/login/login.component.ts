import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username: string = "";
  password: string = "";

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(): void {
  }


  connect(): void {
    this.loginService.connect(this.username, this.password).subscribe(value => {
      this.router.navigate(["bieres"])
    });
  }

  connectWithGoogle() {
    this.loginService.connectWithGoogle()
  }
}
