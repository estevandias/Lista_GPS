import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { Chat } from "src/app/models/Chat";
import { NavExtrasService } from "src/app/service/navigation/nav-extras.service";

@Component({
  selector: "app-home",
  templateUrl: "home.page.html",
  styleUrls: ["home.page.scss"]
})
export class HomePage {
  private _options: any[] = [
    {
      value: 1,
      label: "Cinema"
    },
    {
      value: 2,
      label: "Curiosidades"
    },
    {
      value: 3,
      label: "Esportes"
    }
  ];

  chat: Chat = {
    subject: null,
    username: ""
  };

  constructor(private _router: Router, private _navExtra: NavExtrasService) {}

  goChat() {
    this._navExtra.setExtras(this.chat);
    this._router.navigate(["chat"]);
  }

  get options() {
    return this._options;
  }
}
