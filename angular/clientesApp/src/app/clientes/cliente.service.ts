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

  create(cliente: Cliente): Observable<any>{
    console.log("cliente service: "+cliente)
    return this.http.post<any>(this.url,cliente,{headers: this.httpHeader});
  }

  getCliente(id: number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.url}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/clientes']);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(()=> new Error(e.error.mensaje));
      })
    );
  }

  update(cliente: Cliente): Observable<any>{
    return this.http.put<any>(`${this.url}/${cliente.id}`, cliente,{headers: this.httpHeader}).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(()=> new Error(e.error.mensaje));
      })
    );
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.url}/${id}`,{headers: this.httpHeader}).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(()=> new Error(e.error.mensaje));
      })
    );
  }
}
