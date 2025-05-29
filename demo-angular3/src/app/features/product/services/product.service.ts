import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
export interface Product {
  id: number;
  uuid: string;
  productName: string;
  status: number;
  price: number;
  ver:number;
}
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/product';
  constructor(private http: HttpClient) {}
  getProducts() {
    return this.http.get<Product[]>(this.apiUrl);
  }
  getProductById(id: number) {
    return this.http.get<Product>(`${this.apiUrl}${id}`);
  }
  createProduct(product: Product) {
    return this.http.post<Product>(`${this.apiUrl}create`,
      product);
  }
  updateProduct(product: Product) {
    return this.http.put<Product>(`${this.apiUrl}edit`, product);
  }
  deleteProduct(id: number) {
    return this.http.delete(`${this.apiUrl}${id}`);
  }
}
