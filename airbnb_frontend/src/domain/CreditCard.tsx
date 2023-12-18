import { User } from "./User";

export interface CreditCard {
  creditCardNumber: string;
  user: User;
  expiryDate: Date;
  name: string;
}
