import {Item} from "./item.model";
import {Payment} from "./payment.model";

export class Order {
  orderId: number;
  paymentMethod: Payment;
  items: Item[];
  total: number;
  date: string;
}
