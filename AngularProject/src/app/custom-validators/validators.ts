import {AbstractControl, ValidatorFn} from '@angular/forms';

export function stringValidator(nameRe: RegExp): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const allowed = nameRe.test(control.value);
    return allowed ? null : {forbiddenName: {value: control.value}};
  };
}
