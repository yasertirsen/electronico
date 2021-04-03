import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-search-toolbar',
  templateUrl: './search-toolbar.component.html',
  styleUrls: ['./search-toolbar.component.css']
})
export class SearchToolbarComponent implements OnInit {

  keywords: string;
  selectedOption: string;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onSearch() {
    if(this.selectedOption === undefined)
      this.selectedOption = 'name';
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
      this.router.navigateByUrl('/search/' + this.selectedOption + '/' + this.keywords));
  }
}
