import { HttpClient } from '@angular/common/http';
import { Api_URL } from '../app.constants';
import { Purchase } from './../common/purchase';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private purchaseUrl = `${Api_URL}/checkout/purchase`;
  constructor(private httpClient :HttpClient) { }

  placeOrder(purchase: Purchase): Observable<any>{
    return this.httpClient.post<Purchase>(this.purchaseUrl, purchase);
  }
}
