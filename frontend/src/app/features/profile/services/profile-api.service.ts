import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { UserResponse } from '../models/user-response.model';
import { ToastService } from '../../../core/services/toast.service';
import { Router } from '@angular/router';
import { TokenService } from '../../../core/services/token.service'; // adjust path if needed

@Injectable({ providedIn: 'root' })
export class ProfileApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private toast = inject(ToastService);
  private tokenService = inject(TokenService);

  constructor(private http: HttpClient) {}

  fetchUserProfile(username: string): Observable<UserResponse> {
    const token = this.tokenService.getAccessToken();

    return this.http
      .get<UserResponse>(`${this.apiUrl}/user/${username}`, {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }

  fetchUserReadme(userId: string): Observable<string> {
    // const token = this.tokenService.getAccessToken();

    // return this.http
    //   .get<string>(`${this.apiUrl}/user/${userId}/readme`, {
    //     headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    //   })
    //   .pipe(
    //     tap({
    //       error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
    //     })
    //   );

    return of(`
      # Welcome to My Profile!

        Hello! 👋 I'm [Your Name], a passionate [Your Profession].

        ## About Me
        I specialize in [mention your skills, experience, or areas of interest]. I love working on [types of projects], and I'm always open to learning new technologies.
        - 🔭 Currently working on: [Current Project]
        - 🌱 Currently learning: [Skills you're currently learning]
        - 💬 Ask me about: [Topics you're open to discuss]
        - 📫 How to reach me: [Your Contact Information]

        ## Skills
        - 🖥️ [Skill 1]
        - 🔧 [Skill 2]
        - 🎨 [Skill 3]

        ## Projects
        Check out some of my awesome work below:

        - [Project 1 Name](#) – Brief description of the project.
        - [Project 2 Name](#) – Brief description of the project.

        Thanks for visiting my profile! Feel free to connect with me.`)
  }
}
