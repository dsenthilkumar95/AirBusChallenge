import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator} from '@angular/material/paginator';
import { MatTableDataSource} from '@angular/material/table';
import { MatSort } from '@angular/material/sort'
import { ProductData } from './ProductData';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import { ProductModel } from './product-modal/product-model.component';
import { ProductService } from '../product.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ModalData } from './product-modal/ModalData';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  dataSource: MatTableDataSource<ProductData>;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  errorMessage:String="";
  constructor(private dialog: MatDialog, private productService: ProductService, private authService: AuthService, private router:Router) {
    this.productService.getAllProducts().subscribe({
      next: data => {
        this.dataSource = new MatTableDataSource(data as ProductData[]);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: error => {
        this.dataSource = new MatTableDataSource([{}] as ProductData[]);
          console.error('There was an error!', error);
      }
  });
  }

  ngOnInit(): void {}
  ngAfterViewInit() {}

  displayedColumns = ["id", "category", "name", "description", "units", "actions"]

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  applySearchByCategory(event: Event) {
    const category = (event.target as HTMLInputElement).value;
    if(category.trim() === ""){
      this.productService.getAllProducts().subscribe({
        next: data => {
          this.dataSource = new MatTableDataSource(data as ProductData[]);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        error: error => {
          this.dataSource = new MatTableDataSource([{}] as ProductData[]);
            console.error('There was an error!', error);
        }
    });
    }
    this.productService.getAllProductsByCategory(category.trim()).subscribe({
      next: data => {
        this.errorMessage = "";
        this.dataSource = new MatTableDataSource(data as ProductData[]);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: error => {
        this.errorMessage = error.error.message;
      }
    })
  }

  createProduct(): void {
    const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.position = {
          'top': '25vh',
          'left': '25vw'
        };
        dialogConfig.height='45vh';
        dialogConfig.width='50vw';
        dialogConfig.data = {"operation":"Create"};

    const dialogRef = this.dialog.open(ProductModel, dialogConfig);
      dialogRef.afterClosed().subscribe(data =>{
        if(data === "done"){
          this.productService.getAllProducts().subscribe({
            next: allProducts =>{
              this.dataSource = new MatTableDataSource(allProducts as ProductData[]);
              this.dataSource.paginator = this.paginator;
              this.dataSource.sort = this.sort;
            },
            error: errorAllProducts => {
              this.dataSource = new MatTableDataSource([] as ProductData[]);
              this.dataSource.paginator = this.paginator;
              this.dataSource.sort = this.sort;
            }
          });
        }
        });
  }

  updateProduct(row:ProductData): void {
    const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.position = {
          'top': '25vh',
          'left': '25vw'
        };
        dialogConfig.height='45vh';
        dialogConfig.width='50vw';
        dialogConfig.data = {"operation":"Update", "id":row.id, "category":row.category, "name":row.name, "description":row.description,"units":row.units} as ModalData;
    const dialogRef = this.dialog.open(ProductModel, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      if(result === "done"){
        this.productService.getAllProducts().subscribe({
          next: allProducts =>{
            this.dataSource = new MatTableDataSource(allProducts as ProductData[]);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
          },
          error: errorAllProducts => {
            this.dataSource = new MatTableDataSource([] as ProductData[]);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
          }
        });
      }
    });
  }

  logout(){
    sessionStorage.setItem("jwtToken", "");
    this.router.navigate(['/login']);
  }

  
}