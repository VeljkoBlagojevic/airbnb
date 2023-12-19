import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
} from "@mui/material";
import PropertyCategoryTableComponent from "./PropertyCategoryTableComponent";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../api/apiConstants";
import axios from "axios";
import { PropertyCategory } from "../../domain/PropertyCategory";

const PropertyCategoryComponent = () => {
  const [data, setData] = useState<PropertyCategory[]>([]);
  const [propertyCategoryName, setPropertyCategoryName] = useState<string>("");
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [propertyCategoryId, setPropertyCategoryId] = useState<number>(0);

  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);

  const emptyPropertyCategory = () => {
    setPropertyCategoryId(0);
    setPropertyCategoryName("");
  };

  const handleInsert = async () => {
    const propertyCategory: PropertyCategory = {
      propertyCategoryId,
      categoryName: propertyCategoryName,
    };
    try {
      await axios.post(`${API_BASE_URL}propertyCategories`, propertyCategory);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      emptyPropertyCategory();
      setModalOpen(false);
    }
  };

  const fetchData = async () => {
    try {
      const response = await axios.get<PropertyCategory[]>(
        `${API_BASE_URL}propertyCategories`
      );
      setData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const setModalInfo = (propertyCategoryToEdit: PropertyCategory) => {
    setPropertyCategoryId(propertyCategoryToEdit.propertyCategoryId);
    setPropertyCategoryName(propertyCategoryToEdit.categoryName);
  };

  const openDialogForEdit = (propertyCategoryToEdit: PropertyCategory) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(propertyCategoryToEdit);
  };

  const handleUpdate = async () => {
    const propertyCategory: PropertyCategory = {
      propertyCategoryId,
      categoryName: propertyCategoryName,
    };
    try {
      await axios.patch(
        `${API_BASE_URL}propertyCategories/${propertyCategory.propertyCategoryId}`,
        propertyCategory
      );
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      emptyPropertyCategory();
      setModalOpen(false);
    }
  };

  const handleDelete = async (propertyCategory: PropertyCategory) => {
    try {
      await axios.delete(
        `${API_BASE_URL}propertyCategories/${propertyCategory.propertyCategoryId}`
      );
      fetchData();
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleConfirm = () => {
    isModalEdit ? handleUpdate() : handleInsert();
  };

  return (
    <Container>
      <Button
        onClick={() => {
          setModalOpen(true);
          setModalEdit(false);
          emptyPropertyCategory();
        }}
      >
        Insert new Credit Card
      </Button>
      <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
        <DialogTitle>Insert New Credit Card</DialogTitle>
        <DialogContent>
          <TextField
            label="PropertyCategory Number"
            value={propertyCategoryId}
            disabled
            onChange={(e) => setPropertyCategoryId(parseInt(e.target.value))}
          />
          <TextField
            label="User Name"
            value={propertyCategoryName}
            onChange={(e) => setPropertyCategoryName(e.target.value)}
          />
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
      <PropertyCategoryTableComponent
        data={data}
        handleDelete={handleDelete}
        openDialogForEdit={openDialogForEdit}
      />
    </Container>
  );
};

export default PropertyCategoryComponent;
