import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Cliente } from './cliente';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit{

  public cliente: Cliente = new Cliente()
  public titulo:string = "Crear Cliente"

  contructor(){}

  ngOnInit(){}

  public create():void{
    console.log("Clicked");
    console.log(this.cliente);
  }

}
