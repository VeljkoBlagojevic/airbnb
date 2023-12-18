import { StringLiteral } from "typescript";

export interface Location {
  locationId: number;
  country: string;
  city: string;
  zipcode: number;
  address: string;
  floor?: string;
}
