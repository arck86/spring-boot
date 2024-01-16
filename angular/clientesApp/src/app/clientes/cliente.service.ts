import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { of, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable()
export class ClienteService {

  private url: string = "http://localhost:8080/api/clientes"; 

  private httpHeader = new HttpHeaders({'Content-Type': 'application/json'})
  constructor(private http: HttpClient){}

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
    return this.http.get<Cliente>(`${this.url}/${id}`)
  }

  update(cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.url}/${cliente.id}`, cliente,{headers: this.httpHeader})
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete(`${this.url}/${id}`, cliente,{headers: this.httpHeader})
  }
}
