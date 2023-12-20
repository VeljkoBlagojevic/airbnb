import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
} from "@mui/material";
import ReviewTableComponent from "./ReviewTableComponent";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../api/apiConstants";
import axios from "axios";
import { Review } from "../../domain/Review";

const ReviewComponent = () => {
  const [data, setData] = useState<Review[]>([]);
  const [reviewDescription, setReviewDescription] = useState<string>("");
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [reviewId, setReviewId] = useState<number>(0);
  const [overallRating, setOverallRating] = useState<number>(0);

  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);

  const emptyReview = () => {
    setReviewId(0);
    setReviewDescription("");
    setOverallRating(0);
  };

  const handleInsert = async () => {
    const review: Review = {
      reviewId,
      description: reviewDescription,
      overallRating,
    };
    try {
      await axios.post(`${API_BASE_URL}reviews`, review);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      emptyReview();
      setModalOpen(false);
    }
  };

  const handleInsertWithOverallRating = async () => {
    const review: Review = {
      reviewId,
      description: reviewDescription,
      overallRating,
    };
    try {
      await axios.post(`${API_BASE_URL}reviews/withOverallRating`, review);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      emptyReview();
      setModalOpen(false);
    }
  };

  const fetchData = async () => {
    try {
      const response = await axios.get<Review[]>(`${API_BASE_URL}reviews`);
      setData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const setModalInfo = (reviewToEdit: Review) => {
    setReviewId(reviewToEdit.reviewId);
    setReviewDescription(reviewToEdit.description);
    setOverallRating(reviewToEdit.overallRating);
  };

  const openDialogForEdit = (reviewToEdit: Review) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(reviewToEdit);
  };

  const handleUpdate = async () => {
    const review: Review = {
      reviewId,
      description: reviewDescription,
      overallRating,
    };
    try {
      await axios.patch(`${API_BASE_URL}reviews/${review.reviewId}`, review);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      emptyReview();
      setModalOpen(false);
    }
  };

  const handleUpdateWithOverallRating = async () => {
    const review: Review = {
      reviewId,
      description: reviewDescription,
      overallRating,
    };
    try {
      await axios.patch(
        `${API_BASE_URL}reviews/${review.reviewId}/withOverallRating`,
        review
      );
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      emptyReview();
      setModalOpen(false);
    }
  };

  const handleDelete = async (review: Review) => {
    try {
      await axios.delete(`${API_BASE_URL}reviews/${review.reviewId}`);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
    fetchData();
  };

  const handleConfirm = () => {
    isModalEdit ? handleUpdate() : handleInsert();
  };

  const handleConfirmWithOverallRating = () => {
    isModalEdit
      ? handleUpdateWithOverallRating()
      : handleInsertWithOverallRating();
  };

  return (
    <Container>
      <Button
        onClick={() => {
          setModalOpen(true);
          setModalEdit(false);
          emptyReview();
        }}
      >
        Insert new Review
      </Button>
      <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
        <DialogTitle>Insert New Review</DialogTitle>
        <DialogContent>
          <TextField label="Review ID" value={reviewId} disabled />
          <TextField
            label="Description"
            value={reviewDescription}
            type="text"
            onChange={(e) => setReviewDescription(e.target.value)}
          />
          <TextField
            label="Overall Rating"
            value={overallRating}
            type="number"
            onChange={(e) => setOverallRating(parseFloat(e.target.value))}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setModalOpen(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleConfirm} color="primary">
            {isModalEdit ? "Update" : "Insert"}
          </Button>
          <Button onClick={handleConfirmWithOverallRating} color="primary">
            {isModalEdit ? "Update with overall rating" : "Insert with overall rating"}
          </Button>
        </DialogActions>
      </Dialog>
      <ReviewTableComponent
        data={data}
        handleDelete={handleDelete}
        openDialogForEdit={openDialogForEdit}
      />
    </Container>
  );
};

export default ReviewComponent;
