import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Product, ProductService} from '../../services/product.service';

@Component({
  selector: 'app-product-list',
  imports: [],
  templateUrl: './product-list.component.html',
  standalone: true,
  styleUrl: './product-list.component.css'
})

export class ProductListComponent implements OnInit {
  products: Product[] = [];
  constructor(private router: Router, private productService:
    ProductService) {}
  ngOnInit(): void {
    this.getProductList();
  }
// 取得產品清單
  getProductList() {
    this.productService.getProducts().subscribe((res) => {
      this.products = res;
    });
  }
}
