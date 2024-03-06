import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  public cliente: Cliente = new Cliente()
  public titulo: string = "Crear Cliente"
  public errores: string[] = [];
  public mostrarUl: boolean = false; 

  constructor(private clienteService: ClienteService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.cargarCliente()
  }

  create(): void {
    this.clienteService.create(this.cliente).subscribe(
      cliente => {
        this.mostrarUl= false;
        this.router.navigate(['/clientes'])
        Swal.fire('Nuevo cliente', `cliente ${cliente.nombre} creado con exito`, 'success')
      },
      err => {
        this.errores = err.error.errors as string [];
        console.error('Código de error desde el Backend: ' + err.status);
        console.error(this.errores);
      }
    );
  }

  cargarCliente(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params['id']
      if (id) {
        this.clienteService.getCliente(id).subscribe((cliente) => this.cliente = cliente)
      }
    })
  }

  update() :void{
    this.clienteService.update(this.cliente).subscribe(json => {
      this.router.navigate(['/clientes'])
      Swal.fire('Cliente actualizado', `cliente ${json.cliente.nombre} actualizado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors);
      console.error('Codigo del error desde el backend: '+err.error.errors);
    })
  }
}
