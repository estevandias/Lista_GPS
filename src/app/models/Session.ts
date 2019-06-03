import { User } from "./User";

export interface Session {
  id: string;
  initiatedAt: number;
  user: User;
  subject: string;
}
