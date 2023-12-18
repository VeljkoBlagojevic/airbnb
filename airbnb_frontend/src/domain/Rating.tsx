import { ReviewCategory } from "./ReviewCategory";

export interface Rating {
  ratingId: {
    reviewId: number;
    reviewCategoryId: number;
  };
  value: number;
  category: ReviewCategory;
}
