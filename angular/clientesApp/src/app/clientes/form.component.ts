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

  constructor(private clienteService: ClienteService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.cargarCliente()
  }

  create(): void {
    this.clienteService.create(this.cliente).subscribe(
      json => {
        this.router.navigate(['/clientes'])
        Swal.fire('Nuevo cliente', `cliente ${json.cliente.nombre} creado con exito`, 'success')
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

  update() {
    this.clienteService.update(this.cliente).subscribe(json => {
      this.router.navigate(['/clientes'])
      Swal.fire('Cliente actualizado', `cliente ${json.cliente.nombre} actualizado con exito`, 'success')
    })
  }
}
