import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ModeComment } from '@material-ui/icons';
import { ProductService } from 'src/app/product.service';
import { ProductData } from '../ProductData';
import { ModalData } from './ModalData';

@Component({
  selector: 'product-modal',
  templateUrl: './product-modal.component.html',
  styleUrls:['./product-modal.component.css']
})
export class ProductModel implements OnInit{
  productForm : FormGroup;
  constructor(
    private dialogRef: MatDialogRef<ProductModel>,@Inject(MAT_DIALOG_DATA) private modalData:ModalData, private matDialog:MatDialog, private productService: ProductService, private formBuilder: FormBuilder) {
      this.opsButton = modalData.operation;
      this.errorMsg="";
      this.productForm = this.formBuilder.group({
        id: [this.modalData.id],
        category: [this.modalData.category, Validators.required],
        name: [this.modalData.name, Validators.required],
        description: [this.modalData.description],
        units: [this.modalData.units, [Validators.required, Validators.min(0), Validators.max(100)]]
      });
    }

    ngOnInit(){}
    get f() { return this.productForm.controls; }
  onNoClick(): void {
    this.dialogRef.close("cancel");
  }
  opsButton:string;
  errorMsg: string;

  upsertProduct(){
    if(this.productForm.valid){
      if(this.modalData.operation === "Update"){
        this.productService.updateProduct(this.productForm.value as ProductData).subscribe({
          next: output=>{
            this.dialogRef.close("done");
          },
          error: error =>{
            this.errorMsg = error.error.validationErrors;
          }
        });
      } else if (this.modalData.operation === "Create"){
        this.productService.createProduct(this.productForm.value as ProductData).subscribe({
          next: output=>{
            this.dialogRef.close("done");
          },
          error: error =>{
            this.errorMsg = error.error.validationErrors;
          }
        });
      }
    }  
  }
}