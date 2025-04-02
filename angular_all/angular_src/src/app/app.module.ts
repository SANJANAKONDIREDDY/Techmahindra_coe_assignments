import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { PatientComponent } from './components/patient/patient.component';
import { DoctorComponent } from './components/doctor/doctor.component';  // Make sure this is here
import { TreatmentComponent } from './components/treatment/treatment.component';
import { HomeComponent } from './components/home/home.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { AppRoutingModule } from './app-routing.module';
import { DoctorService } from './service/doctor.service';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    PatientComponent,
    DoctorComponent,  // Added missing comma here
    TreatmentComponent,
    HomeComponent,
    AppointmentComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,  // Added missing comma here
    FormsModule,
    AppRoutingModule
  ],
  providers: [DoctorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
