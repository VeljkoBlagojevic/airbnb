import { ChangeEvent, ReactNode, useEffect, useState } from "react";
import axios from "axios";
import PropertyCardComponent from "./PropertyCardComponent";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import { Property } from "../../domain/Property";
import { Currency } from "../../domain/Currency";
import { PropertyCategory } from "../../domain/PropertyCategory";
import { Location } from "../../domain/Location";
import { API_BASE_URL } from "../../api/apiConstants";

const PropertyListComponent = () => {
  const [locations, setLocations] = useState<Location[]>([]);
  const defaultLocation = locations[0];
  const [currencies, setCurrencies] = useState<Currency[]>([]);
  const defaultCurrency = currencies[0];
  const [propertyCategories, setPropertyCategories] = useState<
    PropertyCategory[]
  >([]);
  const defaultPropertyCategory = propertyCategories[0];
  const [properties, setProperties] = useState<Property[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [searchParam, setSearchParam] = useState<string>("viewAll");
  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [propertyName, setPropertyName] = useState<string>("");
  const [price, setPrice] = useState<number>(0);
  const [currency, setCurrency] = useState<Currency>(defaultCurrency);
  const [location, setLocation] = useState<Location>(defaultLocation);
  const [category, setCategory] = useState<PropertyCategory>(
    defaultPropertyCategory
  );
  const [propertyId, setPropertyId] = useState<number>(5);

  const fetchData = async () => {
    try {
      const url = `http://localhost:8080/api/v1/properties/${searchParam}`;

      const response = await axios.get<Property[]>(url, {
        params: {
          city: searchTerm,
          name: searchTerm,
        },
      });
      setProperties(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchCurrencies = async () => {
    try {
      const url = `http://localhost:8080/api/v1/currencies`;

      const response = await axios.get<Currency[]>(url);
      setCurrencies(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchCategories = async () => {
    try {
      const url = `http://localhost:8080/api/v1/propertyCategories`;
      const response = await axios.get<PropertyCategory[]>(url);
      setPropertyCategories(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchLocations = async () => {
    try {
      const url = `http://localhost:8080/api/v1/locations`;
      const response = await axios.get<Location[]>(url);
      setLocations(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchData();
    fetchCategories();
    fetchCurrencies();
    fetchLocations();
  }, [searchTerm, searchParam]);

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchParamChange = (
    event: SelectChangeEvent<string>,
    child: ReactNode
  ) => {
    setSearchParam(event.target.value);
  };

  const openInsertProperty = () => {
    setModalOpen(true);
    setModalEdit(false);
  };

  const handleCategorySelection = (
    event: SelectChangeEvent<number>,
    child: ReactNode
  ) => {
    const chosenCategory: PropertyCategory | undefined =
      propertyCategories.find(
        (c) => c.propertyCategoryId === event.target.value
      );
    if (chosenCategory) {
      setCategory(chosenCategory);
    }
  };

  const handleCurrencySelection = (
    event: SelectChangeEvent<number>,
    child: ReactNode
  ) => {
    const chosen: Currency | undefined = currencies.find(
      (c) => c.currencyId === event.target.value
    );
    if (chosen) {
      setCurrency(chosen);
    }
  };

  const handleLocationSelection = (
    event: SelectChangeEvent<number>,
    child: ReactNode
  ) => {
    const chosen: Location | undefined = locations.find(
      (c) => c.locationId === event.target.value
    );
    if (chosen) {
      setLocation(chosen);
    }
  };

  const emptyProperty = () => {
    setPropertyName("");
    setPrice(0);
    setCurrency(currencies[0]);
    setLocation(locations[0]);
    setCategory(propertyCategories[0]);
  };

  const handleUpdate = async () => {
    const property: Property = {
      propertyId,
      name: propertyName,
      location,
      currency,
      category,
      price,
      categoryName: "xxx",
    };
    try {
      await axios.patch(
        `${API_BASE_URL}properties/${property.propertyId}`,
        property
      );
      fetchData();
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      emptyProperty();
      setModalOpen(false);
    }
  };

  const handleInsert = async () => {
    const property: Property = {
      propertyId: 1,
      name: propertyName,
      location,
      currency,
      category,
      price,
      categoryName: "xxx",
    };
    try {
      await axios.post(`${API_BASE_URL}properties`, property);
      fetchData();
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      emptyProperty();
      setModalOpen(false);
      fetchData();
    }
  };

  const handleDelete = async (property: Property) => {
    try {
      await axios.delete(`${API_BASE_URL}properties/${property.propertyId}`);
      fetchData();
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleConfirm = () => {
    isModalEdit ? handleUpdate() : handleInsert();
  };

  const setModalInfo = (propertyToEdit: Property) => {
    setPropertyId(propertyToEdit.propertyId);
    setPropertyName(propertyToEdit.name);
    setPrice(propertyToEdit.price);
    setCategory(propertyToEdit.category);
    setCurrency(propertyToEdit.currency);
    setLocation(propertyToEdit.location);
  };

  const openDialogForEdit = (propertyToEdit: Property) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(propertyToEdit);
  };

  return (
    <div>
      <div className="search-container">
        <Button onClick={openInsertProperty}>Insert new Property</Button>

        <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
          <DialogTitle>Insert New Property</DialogTitle>
          <DialogContent>
            <TextField
              label="Name"
              value={propertyName}
              onChange={(e) => setPropertyName(e.target.value)}
            />
            <TextField
              label="Price"
              type="number"
              value={price}
              onChange={(e) => setPrice(parseFloat(e.target.value))}
            />

            <Select
              value={currency?.currencyId}
              label="Currency"
              onChange={handleCurrencySelection}
            >
              {currencies.map((c) => (
                <MenuItem value={c.currencyId} key={c.currencyId}>
                  {c.name}
                </MenuItem>
              ))}
            </Select>

            <Select
              value={location?.locationId}
              label="Location"
              onChange={handleLocationSelection}
            >
              {locations.map((l) => (
                <MenuItem value={l.locationId} key={l.locationId}>
                  {l.address}
                </MenuItem>
              ))}
            </Select>

            <Select
              value={category?.propertyCategoryId}
              label="Property Category"
              onChange={handleCategorySelection}
            >
              {propertyCategories.map((c) => (
                <MenuItem
                  value={c.propertyCategoryId}
                  key={c.propertyCategoryId}
                >
                  {c.categoryName}
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

        <Divider />
        <FormControl>
          <InputLabel id="select-property-serach-param-label">
            Search Parameter
          </InputLabel>
          <Select
            id="select-property-serach-param-label"
            value={searchParam}
            label="Search parameter"
            onChange={handleSearchParamChange}
          >
            <MenuItem value={"viewAll"}>View all</MenuItem>
            <MenuItem value={"name"}>Name</MenuItem>
            <MenuItem value={"city"}>City</MenuItem>
          </Select>
          {searchParam !== "viewAll" && (
            <TextField
              label={`Search by ${searchParam}`}
              value={searchTerm}
              onChange={handleSearchChange}
              variant="outlined"
            />
          )}
        </FormControl>
      </div>
      <div className="properties-container">
        {properties &&
          properties.map((property) => (
            <PropertyCardComponent
              property={property}
              key={property.propertyId}
              handleDelete={handleDelete}
              openDialogForEdit={openDialogForEdit}
            />
          ))}
      </div>
    </div>
  );
};

export default PropertyListComponent;
