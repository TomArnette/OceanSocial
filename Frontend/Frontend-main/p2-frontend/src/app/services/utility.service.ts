import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  private _serverDomain: string = "http://18.221.238.224:9090"

  constructor() { }

  getServerDomain() {
    return this._serverDomain
  }
}


