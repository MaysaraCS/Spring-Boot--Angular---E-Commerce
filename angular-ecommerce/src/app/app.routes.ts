import { Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';

export const routes: Routes = [

    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    },
    {
        path:'product-list',
        component:ProductListComponent
    },
];
