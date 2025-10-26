import { AbstractControl, ValidationErrors } from '@angular/forms';

export function trimValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value as string;
  if (value != null && value.trim() === '') {
    return { trimmedEmpty: true };
  }
  return null;
}
