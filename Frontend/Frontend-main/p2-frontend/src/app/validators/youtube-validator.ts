import { FormControl } from "@angular/forms";

export class YouTubeValidator {
  static youtubeUrl(control: FormControl) {
    let pattern: RegExp = new RegExp('^(http(s)?:\/\/)?((w){3}.)?youtu(be|.be)?(\.com)?\/.+');

    return pattern.test(control.value) ? null : {
      youtubeUrl: {
        valid: false
      }
    }
  }
}
