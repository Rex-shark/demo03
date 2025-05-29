import { Routes } from '@angular/router';
import {ProductListComponent} from './features/product/pages/product-list/product-list.component';

export const routes: Routes = [

  // 配置 Product 頁面路由
  {
    path: '',
    component: ProductListComponent,
  }

];

