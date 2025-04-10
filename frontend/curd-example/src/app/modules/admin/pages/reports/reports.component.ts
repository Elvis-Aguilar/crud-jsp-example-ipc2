import { Component } from '@angular/core';
import { Role, User } from '../../models/user.interface';
import { FetchBackService } from '../../services/fetch-back.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent {

  constructor(private _userService:FetchBackService){}

  users: User[] = []
  roles: Role[] = []

  selectedRoleId: number | 'todos' = 'todos';


  ngOnInit() {
    this.getAllRoles();
    this.getAllUsers();
  }


  getAllUsers() {
    this._userService.getAllUsers().subscribe({
      next: value => {
        this.users = value
      },
      error: err => {
        //TODO: manejar error
      }
    })
  }

  getAllRoles() {
    this._userService.getAllRoles().subscribe({
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

  get filteredUsers(): User[] {
    if (this.selectedRoleId === 'todos') {
      return this.users;
    }
    return this.users.filter(user => user.roleId === this.selectedRoleId);
  }
  
  generateReport() {
    this._userService.downloadUserReport().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'usuarios.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: err => {
        console.error('Error al descargar el reporte:', err);
      }
    });
  }

}
