import { Component } from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html'
})
export class DirectivaComponent {

  listaCurso: string[] = ['typeScript','JavaScrip','Java','C#','PHP'];

  habilitar: boolean = true;

  boton: string = 'Ocultar';

  constructor(){

  }

  modificarVisualizacion(){
    if(this.habilitar){
      this.habilitar = false;
      this.boton = 'Mostrar';
    }else{
      this.habilitar = true;
      this.boton = 'Ocultar';
    }
  }
}
