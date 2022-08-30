import { Component, OnInit } from '@angular/core';
import { Biere, BiereService } from '../../service/biere.service';

@Component({
  selector: 'app-bieres',
  templateUrl: './bieres.component.html',
  styleUrls: ['./bieres.component.scss']
})
export class BieresComponent implements OnInit {
  bieres: Biere[] = [];

  constructor(private biereService: BiereService) {
  }

  ngOnInit(): void {
    this.biereService.getAllBieres().subscribe(bieres => this.bieres = bieres)
  }

}
