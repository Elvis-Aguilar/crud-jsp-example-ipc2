import { Injectable } from '@angular/core';
import { User } from '../models/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AdminServiceService {

  userUpdate:User | undefined;

  constructor() { }


}
