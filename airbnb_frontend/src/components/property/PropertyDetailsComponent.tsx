import { useEffect, useState } from "react";
import { TextField } from "@mui/material";
import { useParams } from "react-router";
import { Property } from "../../domain/Property";
import LocationComponent from "../location/LocationComponent";
import axios from "axios";
import { API_BASE_URL } from "../../api/apiConstants";

const PropertyDetailsComponent = () => {
  const { propertyId } = useParams();
  const [property, setProperty] = useState<Property>();

  useEffect(() => {
    const fetchPropertyDetails = async () => {
      try {
        const response = await axios.get(
          `${API_BASE_URL}properties/${propertyId}`
        );
        setProperty(response.data);
      } catch (error) {
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
        </>
      )}
    </>
  );
};

export default PropertyDetailsComponent;
