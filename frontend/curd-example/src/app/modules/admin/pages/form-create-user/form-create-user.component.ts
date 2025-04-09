import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FetchBackService } from '../../services/fetch-back.service';
import { Role } from '../../models/user.interface';
import { ModalMsgComponent } from '../../../../shared/components/modal-msg/modal-msg.component';

@Component({
  selector: 'app-form-create-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ModalMsgComponent],
  templateUrl: './form-create-user.component.html',
  styleUrl: './form-create-user.component.css'
})
export class FormCreateUserComponent {

  private formBuilder = inject(FormBuilder)
  private readonly route = inject(Router)
  private readonly fetchBackService = inject(FetchBackService)

  @ViewChild('modal1') modalRef!: ElementRef<HTMLDialogElement>;
  @ViewChild('modal2') modalRef2!: ElementRef<HTMLDialogElement>;

  postForm: FormGroup = this.formBuilder.group({
    name: ['', Validators.required],
    email: ['', Validators.required],
    address: ['', Validators.required],
    dpi: ['', Validators.required],
    roleId: [0, Validators.required],
    password: ['', Validators.required]
  })

  roles: Role[] = []
  messageModal: string = '';
  titleModal: string = '';

  ngOnInit() {
    this.getAllRoles();
  }



  getAllRoles() {
    this.fetchBackService.getAllRoles().subscribe({
      next: value => {
        this.roles = value
      },
      error: err => {
        //TODO: manejar error
      }
    })
  }


  handleError(message: string) {
    this.messageModal = message;
    this.titleModal = 'Error en el servidor'
    this.modalRef2.nativeElement.showModal();
  }


  createUser() {
    const roleId = Number(this.postForm.get('roleId')?.value)
    this.postForm.patchValue({ roleId: roleId });

    if (!this.postForm.valid) {
      this.messageModal = 'Llenar los campos de manera correcta';
      this.titleModal = 'Campos Invalidos'
      this.modalRef2.nativeElement.showModal();
      return;
    }

    this.fetchBackService.createUser(this.postForm.value).subscribe({
      next: value => {
        this.messageModal = 'El usuario ha sido creado con exito, ahora ya puede editarlo o desactivarlo'
        this.titleModal = 'Operacion Exitosa'
        this.modalRef.nativeElement.showModal();
        this.modalRef.nativeElement.addEventListener('close', () => {
          this.route.navigate(['admin/management/users'])
        }, { once: true });
      },
      error: err => {
        this.handleError(err.error.message)
      }
    })
  }


}
