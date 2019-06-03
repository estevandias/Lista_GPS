import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class NavExtrasService {
  private _extras: any;

  constructor() {}

  public getExtras() {
    return this._extras;
  }

  public setExtras(data) {
    this._extras = data;
  }
}
