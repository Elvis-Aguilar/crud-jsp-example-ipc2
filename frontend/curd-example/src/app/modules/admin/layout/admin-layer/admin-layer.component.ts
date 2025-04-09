import { Component } from '@angular/core';
import { NavBarComponent } from '../../../../shared/components/nav-bar/nav-bar.component';
import { SideBarComponent } from '../../../../shared/components/side-bar/side-bar.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-layer',
  standalone: true,
  imports: [NavBarComponent, SideBarComponent, RouterModule],
  templateUrl: './admin-layer.component.html',
  styleUrl: './admin-layer.component.css'
})
export class AdminLayerComponent {

}
