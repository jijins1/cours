import { ErrorHandler, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

export class Erreur {
  error: string

  constructor(error: string) {
    this.error = error;
  }
}

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  subjectError: Subject<Erreur> = new Subject();

  constructor() {
    console.log("Initialize ErrorService")

  }

  getErrors(): Observable<Erreur> {
    console.log("Add error observer")
    return this.subjectError.asObservable();
  }


  nextError(erreur: Erreur) {
    this.subjectError.next(erreur)
  }
}

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {


  constructor(private errorService: ErrorService) {
  }

  handleError(error: any): void {
    console.error(error.message)
    this.errorService.nextError(new Erreur(error.message))
  }


}