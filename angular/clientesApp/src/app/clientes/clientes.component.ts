import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import { of, Observable } from 'rxjs';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit{

  clientes!: Cliente[];

  constructor(private clienteService: ClienteService){
  }

  ngOnInit(){
    this.clienteService.getClientes().subscribe(
      clientes => this.clientes = clientes
    );
  }

  delete(cliente: Cliente): void{
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: "btn btn-success",
        cancelButton: "btn btn-danger"
      },
      buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
      title: "Está seguro?",
      text: `¿Seguro que quiee eliminar a cliente ${cliente.nombre} ${cliente.apellido}?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Si, eliminar",
      cancelButtonText: "No, cancelar",
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.clienteService.delete(cliente.id).subscribe(
          response => {
            this.clientes = this.clientes.filter(cli => cli !== cliente)
            swalWithBootstrapButtons.fire({
              title: "Eliminado",
              text: `Cliente ${cliente.nombre} ${cliente.apellido} eliminado`,
              icon: "success"
            })
          }
        )
      }
    });
  }
}
