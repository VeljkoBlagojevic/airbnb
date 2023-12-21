import { FC } from "react";
import { Amenity } from "../../domain/Amenity";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";

const AmenityViewTableComponent: FC<{
  data: Amenity[];
  handleDelete: Function;
  openDialogForEdit: Function;
  header: string;
}> = ({ data, handleDelete, openDialogForEdit, header }) => {
  const columns = [
    { field: "amenityId", headerName: "Amenity ID", flex: 1 },
    { field: "name", headerName: "Name", flex: 1 },
    { field: "description", headerName: "Description", flex: 1 },
    {
      field: "amenityCategory.amenityCategoryId",
      headerName: "Category ID",
      flex: 1,
      valueGetter: (params: any) =>
        params.row.amenityCategory.amenityCategoryId,
    },
    {
      field: "amenityCategory.name",
      headerName: "Category Name",
      flex: 1,
      valueGetter: (params: any) => params.row.amenityCategory.name,
    },
    {
      field: "delete",
      headerName: "Delete",
      flex: 1,
      renderCell: (params: any) => (
        <Button onClick={() => handleDelete(params.row)}>Delete</Button>
      ),
    },
    {
      field: "update",
      headerName: "Update",
      flex: 1,
      renderCell: (params: any) => (
        <Button onClick={() => openDialogForEdit(params.row)}>Update</Button>
      ),
    },
  ];

  return (
    <div style={{ height: 400, width: "100%" }}>
      <h1>{header}</h1>
      <DataGrid
        rows={data}
        columns={columns}
        getRowId={(row: Amenity) => row.amenityId}
        hideFooterPagination
      />
    </div>
  );
};

export default AmenityViewTableComponent;
