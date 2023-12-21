import { useEffect, useState } from "react";
import { Alert, Snackbar, TextField } from "@mui/material";
import { useParams } from "react-router";
import { Property } from "../../domain/Property";
import axios from "axios";
import { API_BASE_URL } from "../../api/apiConstants";
import LocationComponent from "./LocationComponent";

const PropertyDetailsComponent = () => {
  const { propertyId } = useParams();
  const [property, setProperty] = useState<Property>();
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
    setErrorMessage("");
  };

  useEffect(() => {
    const fetchPropertyDetails = async () => {
      try {
        const response = await axios.get(
          `${API_BASE_URL}properties/${propertyId}`
        );
        setProperty(response.data);
      } catch (error: any) {
        setErrorMessage(error.response.data.body.detail);
        setOpenSnackbar(true);
        console.error(error);
      }
    };
    fetchPropertyDetails();
  }, [propertyId]);

  return (
    <>
      {property && (
        <>
          <TextField
            disabled
            label="ID"
            type="number"
            value={property.propertyId}
          />
          <TextField disabled label="Name" type="text" value={property.name} />
          <TextField
            disabled
            label="Price"
            type="text"
            value={`${property.price} ${property.currency.name}`}
          />
          <LocationComponent location={property.location} />
          <TextField
            disabled
            label="Category"
            type="text"
            value={property.categoryName}
          />
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
        </>
      )}
    </>
  );
};

export default PropertyDetailsComponent;
