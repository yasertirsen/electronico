import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../service/product.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Product} from "../model/product.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class HomeComponent implements OnInit {

  datasource = new MatTableDataSource();
  columnsToDisplay = ['name', 'manufacturer', 'category', 'price'];
  expandedElement: Product | null;
  loading = true;

  private paginator: MatPaginator;
  private sort: MatSort;

  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  setDataSourceAttributes() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
  }

  constructor(private productService: ProductService) {
    this.productService.getAll().subscribe(data => {
      this.datasource.data = data;
    },
      error => {
      console.log(error);
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit(): void {
  }

  getImg(image: string | SVGImageElement) {
    return 'data:image/jpeg;base64,' + image
  }

  onAddToCart(product: Product) {
    console.log(product);
  }
}
