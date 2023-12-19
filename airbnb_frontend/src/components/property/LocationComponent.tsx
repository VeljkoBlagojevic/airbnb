import { FC } from "react";
import { Location } from "../../domain/Location";
import { Container, TextField } from "@mui/material";

const LocationComponent: FC<{ location: Location }> = ({ location }) => {
  return (
    <Container>
      <TextField disabled label="Id" value={location.locationId} />
      <TextField disabled label="City" value={location.city} />
      <TextField disabled label="Country" value={location.country} />
      <TextField disabled label="Address" value={location.address} />
    </Container>
  );
};

export default LocationComponent;
