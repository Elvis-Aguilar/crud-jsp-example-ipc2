import { Routes } from "@angular/router";

export const ADMIN_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./layout/admin-layer/admin-layer.component').then(m => m.AdminLayerComponent),
        children: [
            { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent) },
            { path: 'management/reports', loadComponent: () => import('./pages/reports/reports.component').then(m => m.ReportsComponent) },
            { path: 'management/users', loadComponent: () => import('./pages/list-users/list-users.component').then(m => m.ListUsersComponent) }, 
            { path: 'management/user/create', loadComponent: () => import('./pages/form-create-user/form-create-user.component').then(m => m.FormCreateUserComponent) }, 
            { path: 'management/user/edit', loadComponent: () => import('./pages/form-create-user/form-create-user.component').then(m => m.FormCreateUserComponent) }, 
            { path: '', redirectTo: 'dashboard', pathMatch: 'full' } 
        ]
    },
    { path: '**', redirectTo: 'dashboard', pathMatch: 'full' },
    
]