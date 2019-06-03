import { Component, OnInit } from "@angular/core";
import { Chat } from "src/app/models/Chat";
import { NavExtrasService } from "src/app/service/navigation/nav-extras.service";
import { SessionService } from "src/app/service/session/session.service";
import { Session } from "src/app/models/Session";
import { ActivatedRoute, Router } from "@angular/router";
import { LoadingController, NavController } from "@ionic/angular";
import { Message } from "src/app/models/Message";
import { MessagesService } from "src/app/service/messages/messages.service";

@Component({
  selector: "app-chat",
  templateUrl: "./chat.page.html",
  styleUrls: ["./chat.page.scss"]
})
export class ChatPage implements OnInit {
  session: Session;

  newMessageText: String;
  title: String;
  messages: Message[];

  constructor(
    private _navExtras: NavExtrasService,
    private _messageService: MessagesService
  ) {
    this.session = this._navExtras.getExtras();
    this.title = this.session.subject;
  }

  ngOnInit() {
    this._messageService.index().subscribe(res => {
      this.messages = res.sort((a, b) => {
        if (a.sendAt < b.sendAt) return -1;
        return 1;
      });
    });
  }

  check(item) {
    if (item.user.id === this.session.user.id) {
      return true;
    }

    return false;
  }

  sendMessage() {
    const newMessage: Message = {
      id: Math.random(),
      sendAt: new Date().getTime(),
      user: this.session.user,
      text: this.newMessageText
    };

    this._messageService.store(newMessage);
    this.messages = [...this.messages, newMessage];
    this.newMessageText = "";
  }
}
