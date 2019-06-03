import { Injectable } from "@angular/core";
import {
  AngularFirestoreCollection,
  AngularFirestore
} from "angularfire2/firestore";
import { Subject } from "src/app/models/Subject";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class SubjectService {
  private subjectCollection: AngularFirestoreCollection<Subject>;
  private subjects: Observable<Subject[]>;

  constructor(db: AngularFirestore) {
    this.subjectCollection = db.collection<Subject>("subject");

    this.subjects = this.subjectCollection.snapshotChanges().pipe(
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

  getSubjects() {
    return this.subjects;
  }
}
