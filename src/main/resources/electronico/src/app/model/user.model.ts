import {Cart} from "./cart.model";
import {Payment} from "./payment.model";
import {Product} from "./product.model";

export interface User {
  userId: number;
  fullName: string;
  email: string;
  password: string;
  address: string;
  enabled: boolean;
  role: string;
  authorities: string[];
  isLocked: boolean;
  expiresIn: number;
  token: string;
  cart: Cart;
  paymentMethods: Payment[];
  purchaseHistory: Product[];
}
