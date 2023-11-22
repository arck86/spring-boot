import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Bienvenido clientes';
  curso: string = 'Spring 5 con Angular';
  creador: string = 'Arck';
}
