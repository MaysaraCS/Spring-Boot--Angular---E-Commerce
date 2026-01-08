import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Product } from '../common/product';
import { Api_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl = `${Api_URL}/products`;

  constructor(private httpClient:HttpClient) { }

  getProductList():Observable<Product[]> {
    return this.httpClient.get<GetResponseProducts>(this.productsUrl).pipe(
     map(response => response._embedded.products) 
    );
  }
}

interface GetResponseProducts {
  _embedded: {
    products: Product[];
  }
}