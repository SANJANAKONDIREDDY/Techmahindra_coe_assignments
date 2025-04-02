import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private doctors = [
    { id: 1, name: 'Dr. John Doe', specialization: 'Dentist', contact: '123-456-7890' },
    { id: 2, name: 'Dr. Jane Smith', specialization: 'Orthodontist', contact: '987-654-3210' },
    { id: 3, name: 'Dr. David Lee', specialization: 'Periodontist', contact: '555-123-4567' }
  ];

  getDoctors() {
    return this.doctors;
  }

  getDoctorById(id: number) {
    return this.doctors.find(doctor => doctor.id === id);
  }

  addDoctor(doctor: { id: number; name: string; specialization: string; contact: string }) {
    this.doctors.push(doctor);
  }

  updateDoctor(updatedDoctor: { id: number; name: string; specialization: string; contact: string }) {
    const index = this.doctors.findIndex(doctor => doctor.id === updatedDoctor.id);
    if (index !== -1) {
      this.doctors[index] = updatedDoctor;
    }
  }

  deleteDoctor(id: number) {
    this.doctors = this.doctors.filter(doctor => doctor.id !== id);
  }
}
