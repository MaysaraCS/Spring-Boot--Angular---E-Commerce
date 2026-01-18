import { Injectable } from '@angular/core';
import { CartItem } from '../common/cart-item';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItem: CartItem[] = [];

  // sessionStorage = Reference to web browser's session storage 
  storage : Storage = sessionStorage;
  //storage : Storage = localStorage;

  // Subject is a subclass of Observable
  // We can use Subject to publish events in our code.
  // The event will be sent to all of the subscribers
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  constructor() {
    // read data from storage
    // cartItem is the key
    const storedData = this.storage.getItem('cartItems');
    
    if (storedData != null) {
      // JSON.parse = Reads JSON string and converts to object
      let data = JSON.parse(storedData);
      this.cartItem = data;
      // compute totals based on the data that is read from storage 
      this.computeCartTotals();
    }
  }

  addToCart(theCartItem: CartItem) {

    // check if we already have the item in our cart

    let alreadyExistsInCart: boolean = false;
    let existingCartItem: CartItem | undefined;
    
    if(this.cartItem.length > 0) {
    // find the item in the cart based on item id
    // find return the first matching element , else returns undefined
    // test condition ->>> tempCartItem.id === theCartItem.id executes test for each element until a match is found
    // tempCartItem current array element being processed
      existingCartItem = this.cartItem.find( tempCartItem => tempCartItem.id === theCartItem.id );
      // check if we found it 
      //alreadyExistsInCart = (existingCartItem != undefined);
  }
  if(existingCartItem != undefined) {
      // increment the quantity
      existingCartItem.quantity++;
    } else {
      // just add the item to the array
      this.cartItem.push(theCartItem);
    }
     // compute cart quantity and cart total
    this.computeCartTotals(); 
  }
  //
  //
  //
  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for(let currentCartItem of this.cartItem) {
      // price of one item -> currentCartItem.unitPrice 
      // how many of that item -> currentCartItem.quantity
      // += means add and assign
      // subtotal goes to totalPriceValue
      totalPriceValue += currentCartItem.unitPrice * currentCartItem.quantity;
      totalQuantityValue += currentCartItem.quantity;
    }
    // publish the new values ... all subscribers will receive the new data
    // .next is used to publish the event
    // publish event for all subscribers
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);

    // log cart data for debugging purposes
    this.logCartData(totalPriceValue, totalQuantityValue);

    // persist cart data
    this.persistCartItems();
  }
  //
  //
  //

  persistCartItems(){
    // cartItems = key 
    // this.cartItem = value
    this.storage.setItem('cartItems', JSON.stringify(this.cartItem));
  }
  //
  //
  //
  logCartData(totalPriceValue: number, totalQuantityValue: number) {
    console.log('Contents of the cart');
    for(let tempCartItem of this.cartItem) {
      const subTotalPrice = tempCartItem.unitPrice * tempCartItem.quantity;
      console.log(`name: ${tempCartItem.name}, quantity=${tempCartItem.quantity}, 
        unitPrice=${tempCartItem.unitPrice}, subTotalPrice=${subTotalPrice}`);
    }
    // 2 digits after decimal point
    console.log(`totalPrice: ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`);
    console.log('----');
  }
  //
  //
  //
  decrementQuantity(theCartItem: CartItem) {

    theCartItem.quantity--;

    if (theCartItem.quantity === 0) {
      this.remove(theCartItem);
    }
    else {
      this.computeCartTotals();
    }
  }
  //
  //
  //
  remove(theCartItem: CartItem) {

    // get index of item in the array
    const itemIndex = this.cartItem.findIndex( tempCartItem => tempCartItem.id === theCartItem.id );

    // if found, remove the item from the array at the given index
    if (itemIndex > -1) {
      this.cartItem.splice(itemIndex, 1);

      this.computeCartTotals();
    }
  }

}


