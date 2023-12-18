import { AmenityCategory } from "./AmenityCategory";

export interface Amenity {
  amenityId: number;
  name: string;
  description: string;
  amenityCategory: AmenityCategory;
}
