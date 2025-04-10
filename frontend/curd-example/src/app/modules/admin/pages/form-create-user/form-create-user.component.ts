import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FetchBackService } from '../../services/fetch-back.service';
import { Role, UpdatUser, User } from '../../models/user.interface';
import { ModalMsgComponent } from '../../../../shared/components/modal-msg/modal-msg.component';
import { AdminServiceService } from '../../services/admin-service.service';

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
  private readonly adminService = inject(AdminServiceService)

  @ViewChild('modal1') modalRef!: ElementRef<HTMLDialogElement>;
  @ViewChild('modal2') modalRef2!: ElementRef<HTMLDialogElement>;

  userUpdate:User | undefined = this.adminService.userUpdate;

  postForm: FormGroup = this.formBuilder.group({
    name: [this.userUpdate?.name ||'', Validators.required],
    email: [this.userUpdate?.email ||'', Validators.required],
    address: [this.userUpdate?.address ||'', Validators.required],
    dpi: [this.userUpdate?.dpi ||'', Validators.required],
    roleId: [this.userUpdate?.roleId ||0, Validators.required],
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

  updateUser(){
    const roleId = Number(this.postForm.get('roleId')?.value)
    this.postForm.patchValue({ roleId: roleId });

    if (!this.postForm.valid) {
      this.messageModal = 'Llenar los campos de manera correcta';
      this.titleModal = 'Campos Invalidos'
      this.modalRef2.nativeElement.showModal();
      return;
    }

    if (!this.userUpdate) {
      this.messageModal = 'Primero debe elgir un usuario a editar';
      this.titleModal = 'Opcion de editar no habilitada'
      this.modalRef2.nativeElement.showModal();
      return
    }

    const update: UpdatUser = {
      address: this.postForm.get('address')?.value,
      dpi: this.postForm.get('dpi')?.value,
      email: this.postForm.get('email')?.value,
      name: this.postForm.get('name')?.value,
      password: this.postForm.get('password')?.value,
      roleId: Number(this.postForm.get('roleId')?.value),
      state: this.userUpdate.state
    }

    this.fetchBackService.updateStateUser(update, this.userUpdate?.id).subscribe({
      next: value => {
        this.messageModal = 'El usuario ha sido editado con exito, ahora ya puede editarlo o desactivarlo'
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
