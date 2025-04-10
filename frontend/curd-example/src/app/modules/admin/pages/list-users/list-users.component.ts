import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { Role, User } from '../../models/user.interface';
import { FetchBackService } from '../../services/fetch-back.service';
import { Router } from '@angular/router';
import { ModalMsgComponent } from "../../../../shared/components/modal-msg/modal-msg.component";
import { AdminServiceService } from '../../services/admin-service.service';

@Component({
  selector: 'app-list-users',
  standalone: true,
  imports: [ModalMsgComponent],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.css'
})
export class ListUsersComponent {

  @ViewChild('modal1') modalRef!: ElementRef<HTMLDialogElement>;
  @ViewChild('modal2') modalRef2!: ElementRef<HTMLDialogElement>;

  private readonly fetchBackService = inject(FetchBackService)
  private readonly route = inject(Router)
  private readonly adminService = inject(AdminServiceService)

  users: User[] = []
  roles: Role[] = []

  messageModal: string = '';
  titleModal: string = '';

  ngOnInit() {
    this.getAllRoles();
    this.getAllUsers();
  }


  getAllUsers() {
    this.fetchBackService.getAllUsers().subscribe({
      next: value => {
        this.users = value
      },
      error: err => {
        //TODO: manejar error
      }
    })
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

  equivalentRole(idRole:number): string{
    return this.roles.find(ro => ro.id === idRole)?.name || ''
  }

  goToCreateUser(){
    this.adminService.userUpdate = undefined;
    this.route.navigate(['admin/management/user/create'])
  }

  updateState(idUser:number, user:User, state: string){
    const statTmp = user.state;
    user.state = state;
    this.fetchBackService.updateStateUser(user, idUser).subscribe({
      next: value =>{
        this.messageModal = 'Estado del usuario actulizado con exito'
        this.titleModal = 'operacion exitosa!'
        this.modalRef.nativeElement.showModal();
      },
      error: err =>{
        user.state = statTmp;
        this.messageModal = 'No se ha podido actulizar el estado del usuario'
        this.titleModal = 'error al realizar la operciona!'
        this.modalRef2.nativeElement.showModal();
      }
    })
  }

  editUser(user:User){
    this.adminService.userUpdate = user;
    this.route.navigate(['admin/management/user/edit'])
  }


}
