import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { of, Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map , catchError} from 'rxjs';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';


@Injectable()
export class ClienteService {

  private url: string = "http://localhost:8080/api/clientes"; 

  private httpHeader = new HttpHeaders({'Content-Type': 'application/json'})
  constructor(private http: HttpClient, private router: Router){}

  getClientes(): Observable<Cliente[]>{
    return this.http.get(this.url).pipe(
      map(response => response as Cliente[])
    );
  }

  create(cliente: Cliente): Observable<Cliente>{
    console.log("cliente service: "+cliente)
    return this.http.post<Cliente>(this.url,cliente,{headers: this.httpHeader});
  }

  getCliente(id: number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.url}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/clientes']);
        console.error(e.error.mensaje);
        Swal.fire('Error al editar', e.error.mensaje, 'error')
        return throwError(()=> new Error(e.error.mensaje));
      })
    );
  }

  update(cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.url}/${cliente.id}`, cliente,{headers: this.httpHeader})
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.url}/${id}`,{headers: this.httpHeader})
  }
}
