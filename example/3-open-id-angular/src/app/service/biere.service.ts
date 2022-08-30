import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Biere {
  nom: string;
  degres: number;

}

@Injectable({
  providedIn: 'root'
})
export class BiereService {

  constructor(private http: HttpClient) {
  }

  getAllBieres(): Observable<Biere[]> {
    return this.http.get<Biere[]>('/api/bieres')
  }
}
