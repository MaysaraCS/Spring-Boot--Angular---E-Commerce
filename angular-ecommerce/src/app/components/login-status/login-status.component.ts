import { Component, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login-status',
  imports: [CommonModule,RouterLink],
  templateUrl: './login-status.component.html',
  styleUrl: './login-status.component.css',
  standalone: true
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated: boolean = false; 
  userFullName: string = '';

  constructor(public auth: AuthService) { } 

  ngOnInit(): void {
    // Subscribe to authentication state
    this.auth.isAuthenticated$.subscribe((authenticated) => {
      this.isAuthenticated = authenticated;
      if (authenticated) {
        this.getUserDetails();
      }
    });
  }

  getUserDetails() {
    this.auth.user$.subscribe((user) => {
      if (user) {
        this.userFullName = user.name || user.email || 'User';
        console.log('User details:', user); // Debug log
      }
    });
  }

  login() {
    this.auth.loginWithRedirect();
  }

  logout() {
    this.auth.logout({
      logoutParams: {
        returnTo: window.location.origin
      }
    });
  }
}