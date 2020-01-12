import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {LocationService} from "./service/location.service";
import {LocationDto} from "./domain/location";
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'carlocation';

  formGroup = this.fb.group({
    immatriculation: ['', [Validators.required]],
    kilometrage: ['', [Validators.required]],
    dateDebut: ['', [Validators.required]],
    dateFin: ['', [Validators.required]],
    nomEmprunteur: ['', [Validators.required]],
    defaut: ['']
  });

  immatriculation: string;
  kilometrage: number;
  dateDebut: string;
  dateFin: string;
  nomEmprunteur: string;
  listDefaults: string[];
  defaut: string;

  constructor(private fb: FormBuilder,
              private locationService: LocationService) {
    this.listDefaults = [];
    this.defaut = null;
  }

  generate() {
    const location = new LocationDto();
    location.immatriculation = this.formGroup.get('immatriculation').value;
    location.kilometrage = this.formGroup.get('kilometrage').value;
    location.nomEmprunteur = this.formGroup.get('nomEmprunteur').value;
    location.dateDebut = this.formGroup.get('dateDebut').value;
    location.dateFin = this.formGroup.get('dateFin').value;
    location.listDefaults = this.listDefaults;

    this.locationService.generateXlsx(location)
      .subscribe(data => {
          const blob = new Blob([data]);
          saveAs(blob, "myFile.xlsx");
        },
        error => console.error(error)
      );
  }

  addDefaut() {
    if (this.defaut) {
      this.listDefaults.push(this.defaut);
      this.defaut = null;
    }
  }

  removeDefaut(index: number) {
    this.listDefaults.splice(index, 1);
  }

  reset() {
    this.immatriculation = null;
    this.kilometrage = null;
    this.nomEmprunteur = null;
    this.dateDebut = null;
    this.dateFin = null;
    this.listDefaults = [];
  }
}
