import { Pipe, PipeTransform } from '@angular/core';
import { TimeAgoPipe } from 'time-ago-pipe';

@Pipe({
  name: 'someTimeAgo',
  pure: false
})
export class SomeTimeAgoPipe extends TimeAgoPipe {

}
