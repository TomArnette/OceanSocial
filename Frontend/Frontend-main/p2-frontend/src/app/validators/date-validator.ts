import { FormControl } from "@angular/forms";

export class DateValidator {
  static usDate(control: FormControl) {
    let pattern: RegExp = new RegExp('^(0?[1-9]|[1][0-2])-(0?[1-9]|[12][0-9]|3[01])-[0-9]{4}$');

    return pattern.test(control.value) ? null : {
      usDate: {
        valid: false
      }
    }
  }
}

