import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

import { SubjectService } from "src/app/service/subject/subject.service";
import { Subject } from "src/app/models/Subject";
import { Session } from "src/app/models/Session";
import { SessionService } from "src/app/service/session/session.service";
import { NavExtrasService } from "src/app/service/navigation/nav-extras.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.page.html",
  styleUrls: ["./login.page.scss"]
})
export class LoginPage implements OnInit {
  username: String;
  subject: String;

  subjects: Subject[];
  session: Session = {
    id: Math.random().toString(),
    initiatedAt: new Date().getTime(),
    subject: null,
    user: {
      id: Math.random(),
      name: ""
    }
  };

  constructor(
    private _sessionService: SessionService,
    private _subjectService: SubjectService,
    private _navExtras: NavExtrasService,
    private _router: Router
  ) {}

  ngOnInit() {
    this._subjectService.getSubjects().subscribe(res => {
      this.subjects = res;
    });
  }

  login() {}

  goChat() {
    this._sessionService.store(this.session);
    this._navExtras.setExtras(this.session);
    this._router.navigate(["chat"]);
  }

  get options() {
    return this.subjects;
  }
}
