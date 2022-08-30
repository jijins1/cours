import { Component, OnInit } from '@angular/core';
import { Erreur, ErrorService } from '../../service/error.service';

@Component({
  selector: 'app-error-list',
  templateUrl: './error-list.component.html',
  styleUrls: ['./error-list.component.scss']
})
export class ErrorListComponent implements OnInit {
  errors: Erreur[] = [];

  constructor(private errorService: ErrorService) {
  }

  ngOnInit(): void {
    this.errorService.getErrors().subscribe((error: Erreur) => {
      console.log("Error")
      this.errors = [...this.errors, error];
      console.log("Error", this.errors)

    })

  }

}
