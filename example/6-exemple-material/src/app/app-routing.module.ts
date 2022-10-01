import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResearchComponent } from './research/research.component';
import { CardComponent } from './card/card.component';

const routes: Routes = [
  {path: "search", component: ResearchComponent},
  {path: "card", component: CardComponent},
  {path: "", redirectTo:"search", pathMatch: "full"}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
