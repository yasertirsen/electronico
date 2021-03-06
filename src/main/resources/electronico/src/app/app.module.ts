import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MainNavComponent} from "./main-nav/main-nav.component";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatButtonModule} from '@angular/material/button';
import {LayoutModule} from '@angular/cdk/layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {MatPaginatorModule} from "@angular/material/paginator";
import {LoginComponent} from "./login/login.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FlexLayoutModule} from '@angular/flex-layout';
import {RegisterComponent} from './register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NotFoundComponent} from "./not-found/not-found.component";
import {AddProductComponent} from './add-product/add-product.component';
import {TokenInterceptor} from "./shared/interceptor/TokenInterceptor";
import {HomeComponent} from './home/home.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatSelectModule} from "@angular/material/select";
import {SearchToolbarComponent} from "./shared/search-toolbar/search-toolbar.component";
import {ProductsViewComponent} from './shared/products-view/products-view.component';
import {SearchComponent} from './search/search.component';
import {ProductDetailsComponent} from './product-details/product-details.component';
import {UserSettingsComponent} from './user-settings/user-settings.component';
import {ReviewDialogComponent} from './product-details/review-dialog/review-dialog.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MatDialogModule} from "@angular/material/dialog";
import {MatBadgeModule} from '@angular/material/badge';
import {SettingsDialogComponent} from './user-settings/settings-dialog/settings-dialog.component';
import {ViewCartComponent} from './view-cart/view-cart.component';
import {AddPaymentMethodDialogComponent} from './user-settings/add-payment-method-dialog/add-payment-method-dialog.component';
import {ChoosePaymentMethodDialogComponent} from './view-cart/choose-payment-method-dialog/choose-payment-method-dialog.component';
import {ViewUsersComponent} from './view-users/view-users.component';
import {EditProductDialogComponent} from './product-details/edit-product-dialog/edit-product-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    LoginComponent,
    RegisterComponent,
    NotFoundComponent,
    AddProductComponent,
    HomeComponent,
    SearchToolbarComponent,
    ProductsViewComponent,
    SearchComponent,
    ProductDetailsComponent,
    UserSettingsComponent,
    ReviewDialogComponent,
    SettingsDialogComponent,
    ViewCartComponent,
    AddPaymentMethodDialogComponent,
    ChoosePaymentMethodDialogComponent,
    ViewUsersComponent,
    EditProductDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatButtonModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatPaginatorModule,
    FormsModule,
    FlexLayoutModule,
    HttpClientModule,
    MatTableModule,
    MatSortModule,
    MatSelectModule,
    NgbModule,
    MatDialogModule,
    MatBadgeModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
