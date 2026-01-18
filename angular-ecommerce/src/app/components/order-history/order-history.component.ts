import { Component, OnInit } from '@angular/core';
import { OrderHistory } from '../../common/order-history';
import { OrderHistoryService } from '../../services/order-history.service';
import { CurrencyPipe, DatePipe, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-order-history',
  imports: [NgIf, NgFor, CurrencyPipe, DatePipe],
  templateUrl: './order-history.component.html',
  styleUrl: './order-history.component.css'
})
export class OrderHistoryComponent implements OnInit{

  orderHistoryList: OrderHistory[]=[];
  storage:Storage = sessionStorage;
  constructor(private orderHistoryService: OrderHistoryService){
  }
  ngOnInit(): void{
    this.handleOrderHistory();
  }

  handleOrderHistory(){
    // read the user's email address from browser storage
    const theEmail = JSON.parse(this.storage.getItem('userEmail')!);
    // retrive data from the service
    this.orderHistoryService.getOrderHistory(theEmail).subscribe(
      data =>{
        this.orderHistoryList = data._embedded.orders;
      }
    )
  }

}
