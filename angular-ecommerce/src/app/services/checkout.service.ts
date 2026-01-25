import { HttpClient } from '@angular/common/http';
import { Api_URL } from '../app.constants';
import { Purchase } from './../common/purchase';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { PaymentInfo } from '../common/payment-info';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private purchaseUrl = `${Api_URL}/checkout/purchase`;

  private paymentIntentUrl = `${Api_URL}/checkout/payment-intent`;
  
  constructor(private httpClient :HttpClient) { }

  placeOrder(purchase: Purchase): Observable<any>{
    return this.httpClient.post<Purchase>(this.purchaseUrl, purchase);
  }

  createPaymentIntent(paymentInfo: PaymentInfo):Observable<any>{
    return this.httpClient.post<PaymentInfo>(this.paymentIntentUrl, paymentInfo);
  }
}
