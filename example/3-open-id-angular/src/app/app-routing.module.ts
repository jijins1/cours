import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { BieresComponent } from './component/bieres/bieres.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'bieres', component: BieresComponent, canActivate: [AuthGuard]},
  {path: '', redirectTo: '/bieres', pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
