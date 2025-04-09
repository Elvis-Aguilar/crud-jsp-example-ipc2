import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ApiConfigService {
    private readonly API_BASE = environment.API_ROOT;

    /**users */
    API_USERS = `${this.API_BASE}/areaAdmin/users`;
    API_ROLES = `${this.API_BASE}/areaAdmin/roles`;
}