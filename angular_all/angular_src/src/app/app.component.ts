import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  standalone:false,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']  // Corrected to styleUrls (plural form)
})
export class AppComponent {
  title = 'dentalapp';
}
