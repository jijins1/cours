import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-research',
  templateUrl: './research.component.html',
  styleUrls: ['./research.component.scss']
})
export class ResearchComponent implements OnInit {
  searchValue: string = "";
  tableOfResult: Array<{ nom: String, prenom: String, index: number }> = [];
  displayedColumns = ["index","nomColonne", "prenomColonne"];
  datasource = new MatTableDataSource(this.tableOfResult);

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  constructor() {
  }

  ngAfterViewInit() {
    this.datasource.paginator = this.paginator;
  }

  ngOnInit(): void {
    //On peuple le tableau pour l'exemple
    for (let i = 0; i < 1000; i = i + 4) {
      this.tableOfResult.push({prenom: "Camille", nom: "Simon" + i, index: i})
      this.tableOfResult.push({prenom: "Léo", nom: "Gerberon" + i, index: i + 1})
      this.tableOfResult.push({prenom: "Mathys", nom: "Gerberon" + i, index: i + 2})
      this.tableOfResult.push({prenom: "Margaux", nom: "Simon" + i, index: i + 3})
    }

  }

  applyFilter($event: string) {
    this.datasource.filter = $event;
    if (this.datasource.paginator) {
      this.datasource.paginator.firstPage();
    }
  }
}
