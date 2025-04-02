// appointment.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-appointment',
  standalone: false,
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent {
  appointment = {
    name: '',
    date: '',
    time: ''
  };

  submitAppointment() {
    console.log('Appointment submitted:', this.appointment);
    // Here, you would typically send the appointment data to a backend server.
    // For demonstration, we'll just log it to the console.
    alert(`Appointment submitted for ${this.appointment.name} on ${this.appointment.date} at ${this.appointment.time}`);
    // Clear the form
    this.appointment = {
      name: '',
      date: '',
      time: ''
    };
  }
}