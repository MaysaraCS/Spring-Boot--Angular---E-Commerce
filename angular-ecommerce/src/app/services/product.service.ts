import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Product } from '../common/product';
import { Api_URL } from '../app.constants';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl = `${Api_URL}/products`;
  private categoryUrl = `${Api_URL}/product-category`;

  constructor(private httpClient:HttpClient) { }

  getProductList(theCategoryId: number):Observable<Product[]> {

    // need to build URL based on category id passed from component
    const searchUrl = `${this.productsUrl}/search/findByCategoryId?id=${theCategoryId}`;

    return this.httpClient.get<GetResponseProducts>(searchUrl).pipe(
     map(response => response._embedded.products) 
    );
  }

  getProductCategories():Observable<ProductCategory[]>{
    return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(
     map(response => response._embedded.productCategory) 
    );
  }
}

interface GetResponseProducts {
  _embedded: {
    products: Product[];
  }
}

interface GetResponseProductCategory { 
  _embedded: { 
    productCategory: ProductCategory[]; 
  } 
} 