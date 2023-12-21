import { Container, TextField } from "@mui/material";
import { Review } from "../../domain/Review";
import { FC } from "react";

const ReviewCardComponent: FC<{
  review: Review;
}> = ({ review }) => {
  return (
    <Container className="review-card">
      <h1>Review</h1>
      <TextField
        label="Review ID"
        className="review-id"
        disabled
        value={review.reviewId}
      />
      <TextField
        label="Description"
        className="review-description"
        disabled
        value={review.description}
      />
      <TextField
        label="Average rating"
        className="review-overallRating"
        disabled
        value={review.overallRating}
      />
    </Container>
  );
};

export default ReviewCardComponent;
