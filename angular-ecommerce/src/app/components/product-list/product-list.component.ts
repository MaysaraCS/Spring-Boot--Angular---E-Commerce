import { Component, OnInit } from '@angular/core';
import { Product } from '../../common/product';
import { CurrencyPipe, NgFor } from '@angular/common';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-list',
  imports: [NgFor, CurrencyPipe],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {


  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.listProducts();
  }

  listProducts() {
    this.productService.getProductList().subscribe(
      data => {
        this.products = data;
      }
    );
  }

}
