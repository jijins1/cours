import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Utilisateur } from '../../model/Utilisateur';

@Component({
  selector: 'app-bandeau',
  templateUrl: './bandeau.component.html',
  styleUrls: ['./bandeau.component.scss']
})
export class BandeauComponent implements OnInit {
  utilisateur?: Utilisateur;

  constructor(private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.loginService.isLogged().subscribe(value => {
      if (value) {
        this.loginService.getUtilisateur().subscribe(utilisateur => this.utilisateur = utilisateur);
      } else {
        this.utilisateur = undefined;
      }

    })
  }


  logout() {
    this.loginService.logout();
  }

}
