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

  passwordMismatch: boolean = false;
  usernameMissing: boolean = false;
  roleMissing: boolean = false;
  passwordMissing: boolean = false;

  constructor(private registrationService: RegistrationService){}

  registerUser(){
    this.passwordMismatch = false;
    this.usernameMissing = false;
    this.roleMissing= false;
    this.passwordMissing = false;
    if((this.passwordInput === this.confirmPasswordInput) 
      && (this.passwordInput != "") 
      && (this.usernameInput != "") 
      && (this.roleInput != "")){      
      this.registrationService.registerUser(this.usernameInput, this.passwordInput, this.roleInput);
    }
  
    if(this.usernameInput == ""){
      this.usernameMissing = true;
    }

    if(this.roleInput == ""){
      this.roleMissing = true;
    }

    if(this.passwordInput == ""){
      this.passwordMissing = true;
    }

    if(this.passwordInput != this.confirmPasswordInput){
      this.passwordMismatch = true;
    }
  }

  cancelRegister(){
    this.registrationService.cancelRegistration();
  }
}
