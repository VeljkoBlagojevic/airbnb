import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import AmenityBasicTableComponent from "./AmenityBasicTableComponent";
import AmenityAdditionalTableComponent from "./AmenityAdditionalComponent";
import AmenityViewTableComponent from "./AmenityViewTableComponent";
import { ReactNode, useEffect, useState } from "react";
import { AmenityAdditional } from "../../domain/AmenityAdditional";
import { API_BASE_URL } from "../../api/apiConstants";
import axios from "axios";
import { AmenityBasic } from "../../domain/AmenityBasic";
import { Amenity } from "../../domain/Amenity";
import { AmenityCategory } from "../../domain/AmenityCategory";

const AmenityComponent = () => {
  const defaultCategory = {
    amenityCategoryId: 1,
    name: "General",
  };
  const [viewData, setViewData] = useState<Amenity[]>([]);
  const [basicData, setBasicData] = useState<AmenityBasic[]>([]);
  const [additionalData, setAdditionalData] = useState<AmenityAdditional[]>([]);
  const [nextAmenityId, setNextAmenityId] = useState<number>(1);
  const [amenityName, setAmenityName] = useState<string>("");
  const [amenityDescription, setAmenityDescription] = useState<string>("");
  const [amenityCategory, setAmenityCategory] =
    useState<AmenityCategory>(defaultCategory);
  const [amenityCategories, setAmenityCategories] = useState<AmenityCategory[]>(
    []
  );
  const [isModalOpen, setModalOpen] = useState<boolean>(false);

  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);

  const emptyAmenity = () => {
    updateNextId();
    setAmenityName("");
    setAmenityDescription("");
    setAmenityCategory(defaultCategory);
  };

  const handleInsert = async () => {
    const amenity: Amenity = {
      amenityId: nextAmenityId,
      name: amenityName,
      description: amenityDescription,
      amenityCategory: amenityCategory,
    };
    try {
      await axios.post(`${API_BASE_URL}amenities/view`, amenity);
      fetchView();
      fetchBasic();
      fetchAdditional();
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      emptyAmenity();
      setModalOpen(false);
    }
  };

  const fetchView = async () => {
    try {
      const response = await axios.get<Amenity[]>(
        `${API_BASE_URL}amenities/view`
      );
      setViewData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const fetchBasic = async () => {
    try {
      const response = await axios.get<AmenityBasic[]>(
        `${API_BASE_URL}amenities/basic`
      );
      setBasicData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const fetchAdditional = async () => {
    try {
      const response = await axios.get<AmenityAdditional[]>(
        `${API_BASE_URL}amenities/additional`
      );
      setAdditionalData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get<AmenityCategory[]>(
        `${API_BASE_URL}amenityCategories`
      );
      setAmenityCategories(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const updateNextId = () => {
    fetchView();
    const lastIndex = viewData[viewData.length - 1]?.amenityId;
    const nextIndex = lastIndex ? lastIndex + 1 : 1;
    setNextAmenityId(nextIndex);
  };

  useEffect(() => {
    fetchView();
    fetchBasic();
    fetchAdditional();
    fetchCategories();
    updateNextId();
  }, []);

  const setModalInfo = (amenityToEdit: Amenity) => {
    setNextAmenityId(amenityToEdit.amenityId);
    setAmenityName(amenityToEdit.name);
    setAmenityDescription(amenityToEdit.description);
    setAmenityCategory(amenityToEdit.amenityCategory);
  };

  const openDialogForEdit = (amenityToEdit: Amenity) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(amenityToEdit);
  };

  const handleUpdate = async () => {
    const amenity: Amenity = {
      amenityId: nextAmenityId,
      name: amenityName,
      description: amenityDescription,
      amenityCategory: amenityCategory,
    };
    try {
      await axios.patch(
        `${API_BASE_URL}amenities/view/${amenity.amenityId}`,
        amenity
      );
      fetchView();
      fetchBasic();
      fetchAdditional();
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      emptyAmenity();
      setModalOpen(false);
    }
  };

  const handleDelete = async (amenity: Amenity) => {
    try {
      await axios.delete(`${API_BASE_URL}amenities/view/${amenity.amenityId}`);
      fetchView();
      fetchBasic();
      fetchAdditional();
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleCategorySelection = (
    event: SelectChangeEvent<number>,
    child: ReactNode
  ) => {
    const chosenCategory: AmenityCategory | undefined = amenityCategories.find(
      (c) => c.amenityCategoryId === event.target.value
    );
    if (chosenCategory) {
      setAmenityCategory(chosenCategory);
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
          updateNextId();
        }}
      >
        Insert new Amenity
      </Button>
      <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
        <DialogTitle>Insert New Amenity</DialogTitle>
        <DialogContent>
          <TextField label="Amenity ID" disabled value={nextAmenityId} />
          <TextField
            label="Amenity Name"
            value={amenityName}
            onChange={(e) => setAmenityName(e.target.value)}
          />
          <TextField
            label="Amenity Description"
            value={amenityDescription}
            onChange={(e) => setAmenityDescription(e.target.value)}
          />
          <Select
            value={amenityCategory.amenityCategoryId}
            label="Amenity Category"
            onChange={handleCategorySelection}
          >
            {amenityCategories.map((c) => (
              <MenuItem value={c.amenityCategoryId} key={c.amenityCategoryId}>
                {c.name}
              </MenuItem>
            ))}
          </Select>
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
      <AmenityBasicTableComponent data={basicData} />
      <AmenityAdditionalTableComponent data={additionalData} />
      <AmenityViewTableComponent
        data={viewData}
        handleDelete={handleDelete}
        openDialogForEdit={openDialogForEdit}
      />
    </Container>
  );
};

export default AmenityComponent;
