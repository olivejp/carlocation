import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LocationService} from "./service/location.service";
import {LocationDto} from "./domain/location";
import {saveAs} from 'file-saver';

function validateDateRange(form: AbstractControl): { [key: string]: any } {
  const dateDebut = form.get('dateDebut').value;
  const dateFin = form.get('dateFin').value;
  const sup = dateDebut < dateFin;
  return !sup ? {"validateDateRange": sup} : null;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  listeDefauts: string[];
  currentDefaut: string;

  formGroup = new FormGroup({
    immatriculation: new FormControl('', Validators.required),
    kilometrage: new FormControl('', Validators.required),
    dateDebut: new FormControl('', Validators.required),
    dateFin: new FormControl('', Validators.required),
    nomEmprunteur: new FormControl('', Validators.required),
    defaut: new FormControl(''),
  }, validateDateRange);

  constructor(private fb: FormBuilder,
              private locationService: LocationService) {
    this.listeDefauts = [];
    this.currentDefaut = null;
  }

  generate() {
    const location = new LocationDto();
    location.immatriculation = this.formGroup.get('immatriculation').value;
    location.kilometrage = this.formGroup.get('kilometrage').value;
    location.nomEmprunteur = this.formGroup.get('nomEmprunteur').value;
    location.dateDebut = this.formGroup.get('dateDebut').value;
    location.dateFin = this.formGroup.get('dateFin').value;
    location.listeDefauts = this.listeDefauts;

    this.locationService.generateXlsx(location)
      .subscribe(
        data => saveAs(new Blob([data]), location.immatriculation + '.xlsx'),
        error => console.error(error)
      );
  }

  addDefaut() {
    if (this.currentDefaut) {
      this.listeDefauts.push(this.currentDefaut);
      this.currentDefaut = null;
    }
  }

  removeDefaut(index: number) {
    this.listeDefauts.splice(index, 1);
  }

  reset() {
    this.formGroup.reset();
    this.listeDefauts = [];
  }
}
