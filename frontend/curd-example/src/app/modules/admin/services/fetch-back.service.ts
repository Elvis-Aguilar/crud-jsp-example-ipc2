import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { ApiConfigService } from '../../../shared/services/api-config.service';
import { Observable } from 'rxjs';
import { CreateUser, Role, UpdatUser, User } from '../models/user.interface';

@Injectable({
  providedIn: 'root'
})
export class FetchBackService {

  private readonly _http = inject(HttpClient)
  private readonly apiConfigService = inject(ApiConfigService);
  private readonly API_USERS = this.apiConfigService.API_USERS
  private readonly API_ROLES = this.apiConfigService.API_ROLES
  private readonly API_REPORTS = this.apiConfigService.API_REPORTS


  constructor() { }

  getAllUsers(): Observable<User[]> {
    return this._http.get<User[]>(`${this.API_USERS}`)
  }

  getAllRoles(): Observable<Role[]> {
    return this._http.get<Role[]>(`${this.API_ROLES}`)
  }

  createUser(newUser: CreateUser): Observable<void>{
    return this._http.post<void>(`${this.API_USERS}`,newUser)
  }

  downloadUserReport() {
    return this._http.get(`${this.API_REPORTS}`, {
      responseType: 'blob'
    })
  }
  
  updateStateUser(us: User | UpdatUser, idUser:number): Observable<void>{
    return this._http.put<void>(`${this.API_USERS}/${idUser}`,us)
  }
}
