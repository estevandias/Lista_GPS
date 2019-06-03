import { User } from "./User";
import { Message } from "./Message";

export interface Chat {
  subject: String;
  username: String;
  messages?: Message[];
}
