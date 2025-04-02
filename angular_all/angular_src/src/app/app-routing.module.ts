import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PatientComponent } from './components/patient/patient.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { TreatmentComponent } from './components/treatment/treatment.component';
import { DoctorComponent } from './components/doctor/doctor.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'patients', component: PatientComponent },
  { path: 'appointments', component: AppointmentComponent },
  { path: 'treatments', component: TreatmentComponent },
  { path: 'doctors', component: DoctorComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }  // Default route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
