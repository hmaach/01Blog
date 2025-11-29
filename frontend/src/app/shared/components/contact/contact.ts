import { Component, inject } from '@angular/core';
import { MatBottomSheetModule, MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { MatButtonModule } from '@angular/material/button';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastService } from '../../../core/services/toast.service';
import emailjs, { type EmailJSResponseStatus } from '@emailjs/browser';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-contact',
  imports: [
    MatButtonModule,
    MatBottomSheetModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './contact.html',
  styleUrl: './contact.scss',
})
export class Contact {
  private _bottomSheetRef = inject<MatBottomSheetRef<Contact>>(MatBottomSheetRef);

  private toast = inject(ToastService);

  contactForm!: FormGroup;

  ngOnInit(): void {
    this.contactForm = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      subject: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required),
    });
  }

  onSubmit() {
    if (!this.contactForm.valid) {
      this.toast.show('Please fill out all fields correctly.', 'error');
      return;
    }

    const formData = {
      name: this.contactForm.get('name')?.value || '',
      email: this.contactForm.get('email')?.value || '',
      subject: this.contactForm.get('subject')?.value || '',
      message: this.contactForm.get('message')?.value || '',
    };

    emailjs
      .send('service_zre7dod', 'template_nv001gf', formData, {
        publicKey: 'LKvBSb1l4zGQbi4Yy',
      })
      .then(
        () => {
          console.log('Message sent successfully!');
          this.toast.show('Message sent successfully!', 'success');
          this._bottomSheetRef.dismiss();
        },
        (error) => {
          this.toast.show('Error sending message.', 'error');
          console.log('FAILED...', (error as EmailJSResponseStatus).text);
        }
      );
  }

  openLink(event: MouseEvent): void {
    this._bottomSheetRef.dismiss();
    event.preventDefault();
  }
}
