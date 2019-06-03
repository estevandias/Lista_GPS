import { User } from "./User";

export interface Message {
  sendAt: number;
  id: Number;
  user: User;
  text: String;
}
