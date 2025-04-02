import { Component } from '@angular/core';
import { DoctorService } from '../../service/doctor.service';


@Component({
  selector: 'app-treatment',
  standalone:false,
  templateUrl: './treatment.component.html',
  styleUrls: ['./treatment.component.css']
})
export class TreatmentComponent {
  doctors: any[];

  treatments = [
    { id: 1, name: 'Root Canal', cost: 5000, doctorId: 1 },
    { id: 2, name: 'Teeth Whitening', cost: 3000, doctorId: 2 },
    { id: 3, name: 'Braces', cost: 15000, doctorId: 1 }
  ];

  constructor(private doctorService: DoctorService) {
    this.doctors = this.doctorService.getDoctors();
  }

  removeTreatment(index: number) {
    this.treatments.splice(index, 1);
  }

  getDoctorName(doctorId: number): string {
    const doctor = this.doctorService.getDoctorById(doctorId);
    return doctor ? doctor.name : 'Unknown';
  }
}
