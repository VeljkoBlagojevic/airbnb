import { Currency } from "./Currency";
import { Location } from "./Location";
import { PropertyCategory } from "./PropertyCategory";

export interface Property {
  propertyId: number;
  name: string;
  price: number;
  currency: Currency;
  location: Location;
  category: PropertyCategory;
  categoryName: string;
}
