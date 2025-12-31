import { Component } from '@angular/core';
import { RegistrationService } from '../../services/registration-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registration',
  imports: [FormsModule],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration {

  usernameInput: string = "";
  passwordInput: string = "";  
  confirmPasswordInput: string = "";
  roleInput: string = "";
  buttonRegistration = "Register"
  buttonCancel = "Cancel"

  constructor(private registrationService: RegistrationService){}

  registerUser(){
    if(this.passwordInput === this.confirmPasswordInput)
    this.registrationService.registerUser(this.usernameInput, this.passwordInput, this.roleInput);
  }

  cancelRegistration(){
    this.registrationService.cancelRegistration();
  }
}
