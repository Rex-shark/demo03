import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'demo-angular3';

  constructor(private router: Router) {}

  goToProductPage() {
    this.router.navigate(['/product-list']);
  }
}
