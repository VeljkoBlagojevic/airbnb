import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Rating as RatingMaterialUi,
  Select,
  MenuItem,
  SelectChangeEvent,
  Alert,
  Snackbar,
} from "@mui/material";
import RatingTableComponent from "./RatingTableComponent";
import { ReactNode, useEffect, useState } from "react";
import { API_BASE_URL } from "../../api/apiConstants";
import axios from "axios";
import { Rating } from "../../domain/Rating";
import { ReviewCategory } from "../../domain/ReviewCategory";
import { useParams } from "react-router";
import { Review } from "../../domain/Review";
import ReviewCardComponent from "./ReviewCardComponent";

const RatingComponent = () => {
  const [data, setData] = useState<Rating[]>([]);
  const [value, setValue] = useState<number>(0);
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  //   const [reviewId, setReviewId] = useState<number>(0);
  const [reviewCategoryId, setReviewCategoryId] = useState<number>(0);
  const [categories, setCategories] = useState<ReviewCategory[]>([]);
  const defaultCategory: ReviewCategory = categories[0] || {
    reviewCategoryId: 1,
    name: "Food",
  };
  const [category, setCategory] = useState<ReviewCategory>(defaultCategory);
  const defaultReview: Review = {
    reviewId: 0,
    description: "",
    overallRating: 0,
  };
  const [review, setReview] = useState<Review>(defaultReview);
  const { reviewId } = useParams();

  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);

  const [errorMessage, setErrorMessage] = useState<string>("");
  const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);

  const fetchData = async () => {
    try {
      const response = await axios.get<Rating[]>(
        `${API_BASE_URL}ratings/review/${reviewId}`
      );
      setData(response.data);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get<ReviewCategory[]>(
        `${API_BASE_URL}reviewCategories`
      );
      setCategories(response.data);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
  };

  const fetchReview = async () => {
    try {
      const response = await axios.get<Review>(
        `${API_BASE_URL}reviews/${reviewId}`
      );
      setReview(response.data);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
    fetchCategories();
    fetchReview();
  }, []);

  const emptyRating = () => {
    setReviewCategoryId(0);
    setValue(0);
  };

  const handleInsert = async () => {
    const rating: Rating = {
      ratingId: {
        reviewId: reviewId ? parseInt(reviewId) : 0,
        reviewCategoryId: category.reviewCategoryId,
      },
      value,
      category,
    };
    try {
      await axios.post(`${API_BASE_URL}ratings`, rating);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      fetchReview();
      emptyRating();
      setModalOpen(false);
    }
  };

  const setModalInfo = (ratingToEdit: Rating) => {
    setReviewCategoryId(ratingToEdit.ratingId.reviewCategoryId);
    setValue(ratingToEdit.value);
    setCategory(ratingToEdit.category);
  };

  const openDialogForEdit = (ratingToEdit: Rating) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(ratingToEdit);
  };

  const handleUpdate = async () => {
    const rating: Rating = {
      ratingId: {
        reviewId: reviewId ? parseInt(reviewId) : 0,
        reviewCategoryId,
      },
      value,
      category,
    };
    try {
      await axios.patch(`${API_BASE_URL}ratings`, rating);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      fetchReview();
      emptyRating();
      setModalOpen(false);
    }
  };

  const handleDelete = async (rating: Rating) => {
    try {
      await axios.delete(
        `${API_BASE_URL}ratings/review/${rating.ratingId.reviewId}/category/${rating.ratingId.reviewCategoryId}`
      );
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
    fetchData();
    fetchReview();
  };

  const handleConfirm = () => {
    isModalEdit ? handleUpdate() : handleInsert();
  };

  const handleCategorySelection = (
    event: SelectChangeEvent<number>,
    child: ReactNode
  ) => {
    const chosenCategory: ReviewCategory | undefined = categories.find(
      (c) => c.reviewCategoryId === event.target.value
    );
    if (chosenCategory) {
      setCategory(chosenCategory);
    }
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
    setErrorMessage("");
  };

  return (
    <Container>
      <Button
        onClick={() => {
          setModalOpen(true);
          setModalEdit(false);
          emptyRating();
        }}
      >
        Insert new Rating
      </Button>
      <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
        <DialogTitle>Insert New Rating</DialogTitle>
        <DialogContent>
          <TextField label="Review ID" value={reviewId} disabled />
          <RatingMaterialUi
            name="Value"
            value={value}
            onChange={(event, newValue) => {
              setValue(newValue || 0);
            }}
          />
          {!isModalEdit && (
            <Select
              value={category.reviewCategoryId}
              label="Category"
              onChange={handleCategorySelection}
            >
              {categories.map((c) => (
                <MenuItem value={c.reviewCategoryId} key={c.reviewCategoryId}>
                  {c.name}
                </MenuItem>
              ))}
            </Select>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setModalOpen(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleConfirm} color="primary">
            {isModalEdit ? "Update" : "Insert"}
          </Button>
        </DialogActions>
      </Dialog>
      <RatingTableComponent
        data={data}
        handleDelete={handleDelete}
        openDialogForEdit={openDialogForEdit}
      />
      <ReviewCardComponent review={review} />
      <Snackbar
        open={openSnackbar}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
      >
        <Alert
          onClose={handleCloseSnackbar}
          severity="error"
          sx={{ width: "100%" }}
        >
          <strong>Error:</strong> {errorMessage}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default RatingComponent;
