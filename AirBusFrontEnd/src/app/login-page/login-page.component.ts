import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.createResult = "";
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
    sessionStorage.setItem("jwtToken", "");
   }

  ngOnInit(): void {
  }
  loginForm;
  createResult:String;
  regSuccess: Boolean = false; 
  get f() { return this.loginForm.controls; }
  register() {
    if (this.loginForm.valid) {
      const data = this.loginForm.value;
      this.authService.register(data.username,data.password).subscribe({
        next: data => {
          if(data.status === 200){
            this.regSuccess = true;
            this.createResult = "User Registration Successful";
            setTimeout(()=>{
              this.createResult = "";
            },3000);
          }
      },
      error: error => {
        this.createResult = "User Registration failed";
            setTimeout(()=>{
              this.createResult = "";
            },3000);
      }
      });
  }}

  login(){
    if (this.loginForm.valid) {
      const data = this.loginForm.value;
      this.authService.login(data.username,data.password).subscribe({
        next: data => {
          if(data.status === 200){
            sessionStorage.setItem("jwtToken", data.headers.get('Authorization'));
           this.router.navigate(['/products']);
          }
      },
      error: error => {
        this.createResult = "User Login failed";
            setTimeout(()=>{
              this.createResult = "";
            },3000);
      }
      }
    );
  }
  }

}
