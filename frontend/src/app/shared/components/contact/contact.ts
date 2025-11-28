import { Component, inject } from '@angular/core';
import {
  MatBottomSheet,
  MatBottomSheetModule,
  MatBottomSheetRef,
} from '@angular/material/bottom-sheet';
import { MatButtonModule } from '@angular/material/button';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastService } from '../../../core/services/toast.service';
import emailjs, { type EmailJSResponseStatus } from '@emailjs/browser';

@Component({
  selector: 'app-contact',
  imports: [MatButtonModule, MatBottomSheetModule],
  templateUrl: './contact.html',
  styleUrl: './contact.scss',
})
export class Contact {
  private _bottomSheetRef = inject<MatBottomSheetRef<Contact>>(MatBottomSheetRef);

  private toast = inject(ToastService);
  private fb = inject(FormBuilder);

  contactForm!: FormGroup;

  personal = {
    email: 'personal.email@example.com',
    phone: '+1234567890',
    location: 'Somewhere, Earth',
    github: 'https://github.com',
    linkedin: 'https://linkedin.com/in',
  };

  ngOnInit() {
    this.contactForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      message: ['', Validators.required],
    });
  }

  onSubmit(e: Event) {
    e.preventDefault();

    emailjs
      .sendForm('YOUR_SERVICE_ID', 'YOUR_TEMPLATE_ID', e.target as HTMLFormElement, {
        publicKey: 'YOUR_PUBLIC_KEY',
      })
      .then(
        () => {
          console.log('SUCCESS!');
        },
        (error) => {
          console.log('FAILED...', (error as EmailJSResponseStatus).text);
        }
      );
    if (this.contactForm.valid) {
      // emailjs
      //   .sendForm(
      //     'service_c06655j',
      //     'template_yniiv5o',
      //     document.forms['contactForm'],
      //     'LKvBSb1l4zGQbi4Yy'
      //   )
      //   .then(
      //     (result) => {
      //       console.log(result.text);
      //       this.toast.show('Message sent successfully!', 'success');
      //       this.contactForm.reset(); // Reset form
      //     },
      //     (error) => {
      //       console.log(error.text);
      //       this.toast.show('Error sending message.', 'error');
      //     }
      //   );
    }
  }

  openLink(event: MouseEvent): void {
    this._bottomSheetRef.dismiss();
    event.preventDefault();
  }
}
