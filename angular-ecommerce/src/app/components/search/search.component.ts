import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  imports: [],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css',
  standalone: true
})
export class SearchComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  doSearch(value: string) {
    console.log(`Hey! This is your data: ${value}`);
    this.router.navigateByUrl(`/search/${value.trim().toLowerCase()}`);
  }
}
