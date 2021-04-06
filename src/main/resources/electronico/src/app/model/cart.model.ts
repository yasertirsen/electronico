import {Item} from "./item.model";

export interface Cart {
  cartId: number;
  items: Item[];
  userId: number;
}
