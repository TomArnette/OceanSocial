import { HttpHeaders } from '@angular/common/http';

const headers= new HttpHeaders()
  .set('content-type', 'application/json')
  .set('Access-Control-Allow-Origin', '*')
  .set('jwt', 'token');

this.httpClient.get(this.baseURL + 'users/' + userName + '/repos', { 'headers': headers })
