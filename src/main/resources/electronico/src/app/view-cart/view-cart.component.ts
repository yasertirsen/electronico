import {Component, OnInit} from '@angular/core';
import {User} from "../model/user.model";
import {CartService} from "../service/cart.service";

@Component({
  selector: 'app-view-cart',
  templateUrl: './view-cart.component.html',
  styleUrls: ['./view-cart.component.css']
})
export class ViewCartComponent implements OnInit {
  user: User;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.user = JSON.parse(<string>localStorage.getItem('currentUser'));
  }

  onDelete(index: number) {
    if(index > -1) {
      this.user.cart.items.splice(index, 1);
      this.cartService.update(this.user.cart).subscribe(data =>{
        this.user.cart = data;
        localStorage.setItem('currentUser', JSON.stringify(this.user));
      })
    }
  }

  getImg(image: string | SVGImageElement) {
    return 'data:image/jpeg;base64,' + image
  }

  onPay() {
    console.log(this.user.cart);
  }
}
