import { Injectable } from "@angular/core";
import {
  AngularFirestoreCollection,
  AngularFirestore
} from "angularfire2/firestore";
import { Message } from "src/app/models/Message";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class MessagesService {
  private messagesCollection: AngularFirestoreCollection<Message>;

  private messages: Observable<Message[]>;

  constructor(db: AngularFirestore) {
    this.messagesCollection = db.collection<Message>("messages");

    this.messages = this.messagesCollection.snapshotChanges().pipe(
      map(actions => {
        return actions.map(action => {
          const data = action.payload.doc.data();
          const id = action.payload.doc.id;

          return {
            id,
            ...data
          };
        });
      })
    );
  }

  index() {
    return this.messages;
  }

  show(id) {
    return this.messagesCollection.doc<Message>(id).valueChanges();
  }

  update(message: Message, id: string) {
    return this.messagesCollection.doc(id).update(message);
  }

  store(message: Message) {
    return this.messagesCollection.add(message);
  }

  destroy(id) {
    return this.messagesCollection.doc(id).delete();
  }
}
