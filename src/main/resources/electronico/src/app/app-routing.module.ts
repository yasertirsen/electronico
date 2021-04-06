import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {AddProductComponent} from "./add-product/add-product.component";
import {HomeComponent} from "./home/home.component";
import {SearchComponent} from "./search/search.component";
import {ProductDetailsComponent} from "./product-details/product-details.component";
import {UserSettingsComponent} from "./user-settings/user-settings.component";
import {AuthGuard} from "./_guards/auth.guard";
import {Authorities} from "./model/authorities.model";

const routes: Routes = [
  {
    path:'',
    component: RegisterComponent,
    pathMatch: 'full'
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'add-product',
    component: AddProductComponent,
    canActivate: [AuthGuard],
    data: {authorities: [Authorities.ADMIN]}
  },
  {
    path: 'search/:type/:keywords',
    component: SearchComponent
  },
  {
    path: 'product/:productId',
    component: ProductDetailsComponent
  },
  {
    path: 'settings',
    component: UserSettingsComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,  { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
