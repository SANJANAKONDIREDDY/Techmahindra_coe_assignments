// doctor.component.ts
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-doctor',
  standalone:false,
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent implements OnInit {
  doctors = [
    { id: 1, name: 'Dr. John Doe', specialization: 'Dentist', contact: '123-456-7890' },
    { id: 2, name: 'Dr. Jane Smith', specialization: 'Orthodontist', contact: '987-654-3210' },
    { id: 3, name: 'Dr. David Lee', specialization: 'Periodontist', contact: '555-123-4567' }
  ];

  constructor() {}

  ngOnInit(): void {}
}
