import {
  AngularFirestore,
  AngularFirestoreCollection
} from "angularfire2/firestore";
import { Injectable } from "@angular/core";
import { Session } from "src/app/models/Session";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class SessionService {
  private sessionCollection: AngularFirestoreCollection<Session>;
  private sessions: Observable<Session[]>;

  constructor(db: AngularFirestore) {
    this.sessionCollection = db.collection<Session>("session");

    this.sessions = this.sessionCollection.snapshotChanges().pipe(
      map(actions => {
        return actions.map(a => {
          const data = a.payload.doc.data();
          const id = a.payload.doc.id;
          return {
            id,
            ...data
          };
        });
      })
    );
  }

  store(session: Session) {
    return this.sessionCollection.add(session);
  }

  index() {
    return this.sessions;
  }

  show(id) {
    return this.sessionCollection.doc<Session>(id).get();
  }
}
