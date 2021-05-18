import { Injectable, OnInit } from '@angular/core';
import { ProductData } from './products/ProductData';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AuthService } from './auth.service';
import {environment} from '../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class ProductService implements OnInit{

  constructor(private http: HttpClient, private authService: AuthService) {
    // this.jwtToken = "";
    // this.authService.getJwtToken.subscribe(data =>{
    //   this.jwtToken = data;
    //   console.log("JwtToken value changed in product service -> " + this.jwtToken);
    // });
  }

  ngOnInit(){
  }


  getAllProducts(){
    const headers = new HttpHeaders({ 'Authorization': sessionStorage.getItem("jwtToken") as string, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin':"*"});
    return this.http.get<ProductData[]>(environment.apiUrl + "/api/getAllProducts",{headers});
  }

  getAllProductsByPageAndSort(pageNo:string, pageSize:string, sort:string ){
    const headers = new HttpHeaders({ 'Authorization': sessionStorage.getItem("jwtToken") as string, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin':"*"});
    let params = new HttpParams();
    params = params.append('pageNo', pageNo);
    params = params.append('pageSize', pageSize);
    params = params.append('sort', sort);
    return this.http.get<ProductData[]>(environment.apiUrl + "/api/getAllProductsByPageAndSort",{headers, params});
  }

  getAllProductsByCategory(category: string){
    const headers = new HttpHeaders({ 'Authorization': sessionStorage.getItem("jwtToken") as string, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin':"*"});
    return this.http.get<ProductData[]>(environment.apiUrl + `/api/getAllProductsByCategory/${category}`,{headers});
  }

  countAllProducts(){
    const headers = new HttpHeaders({ 'Authorization': sessionStorage.getItem("jwtToken") as string, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin':"*"});
    return this.http.get<Number>(environment.apiUrl + "/api/countAllProducts",{headers});
  }

  createProduct(data:ProductData){
    const headers = new HttpHeaders({ 'Authorization': sessionStorage.getItem("jwtToken") as string, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin':"*"});
    return this.http.post<ProductData>(environment.apiUrl + "/api/createNewProduct",data,{headers});
  }

  updateProduct(data:ProductData){
    const headers = new HttpHeaders({ 'Authorization': sessionStorage.getItem("jwtToken") as string, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin':"*"});
    return this.http.put<ProductData>(environment.apiUrl + "/api/updateExistingProduct",data,{headers});
  }
}
