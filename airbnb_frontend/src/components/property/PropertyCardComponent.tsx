import { Button, Card, Container, TextField } from "@mui/material";
import { Property } from "../../domain/Property";
import { useNavigate } from "react-router";
import { FC } from "react";

const PropertyCardComponent: FC<{
  property: Property;
  handleDelete: Function;
  openDialogForEdit: Function
}> = ({ property, handleDelete, openDialogForEdit }) => {
  const navigate = useNavigate();

  return (
    <Container className="property-card">
      <Card className="property-info">
        <TextField className="property-name" disabled value={property.name} />
        <TextField
          className="property-location"
          disabled
          value={`${property.location.city}, ${property.location.country}`}
        />
        <TextField
          disabled
          value={`${property.categoryName}`}
        />
        <Button onClick={() => navigate(`/properties/${property.propertyId}`)}>
          See details
        </Button>
        <Button onClick={() => handleDelete(property)}>Delete</Button>
        <Button onClick={() => openDialogForEdit(property)}>Update</Button>
      </Card>
    </Container>
  );
};

export default PropertyCardComponent;
