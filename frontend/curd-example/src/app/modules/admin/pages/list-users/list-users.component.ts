import { Component, inject } from '@angular/core';
import { Role, User } from '../../models/user.interface';
import { FetchBackService } from '../../services/fetch-back.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-users',
  standalone: true,
  imports: [],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.css'
})
export class ListUsersComponent {

  private readonly fetchBackService = inject(FetchBackService)
  private readonly route = inject(Router)


  users: User[] = []
  roles: Role[] = []

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
    this.route.navigate(['admin/management/user/create'])
  }


}
