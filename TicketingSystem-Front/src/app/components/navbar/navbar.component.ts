import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Button } from 'primeng/button';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, Button],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {}