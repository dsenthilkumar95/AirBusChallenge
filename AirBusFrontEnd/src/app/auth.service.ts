import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {environment} from '../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(username: string, password:string){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*'});
    const body = {"username": username, "password":password};
    return this.http.post<any>(environment.apiUrl + "/users/signup", body,{headers: headers, observe: "response" as "body"});
  }

  login(username: string, password:string){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*'});
    const body = {"username": username, "password":password};
    return this.http.post<any>(environment.apiUrl + "/login", body,{headers: headers, observe: "response" as "body"});
  }
}
