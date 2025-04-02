import { Component } from '@angular/core';

@Component({
  selector: 'app-patient',
  standalone:false,
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  patients: { id: number; name: string; age: number }[] = [];
  name: string = '';
  age: number | null = null;

  addPatient() {
    if (this.name.trim() && this.age !== null) {
      this.patients.push({
        id: this.patients.length + 1,
        name: this.name,
        age: this.age
      });

      this.name = '';
      this.age = null;
    }
  }

  deletePatient(index: number) {
    this.patients.splice(index, 1);
  }
}
