import { FC } from "react";
import { PropertyCategory } from "../../domain/PropertyCategory";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";

const PropertyCategoryViewTableComponent: FC<{
  data: PropertyCategory[];
  handleDelete: Function;
  openDialogForEdit: Function;
}> = ({ data, handleDelete, openDialogForEdit }) => {
  const columns = [
    { field: "propertyCategoryId", headerName: "Category ID", flex: 1 },
    { field: "categoryName", headerName: "Name", flex: 1 },
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
      <DataGrid
        rows={data}
        columns={columns}
        getRowId={(row: PropertyCategory) => row.propertyCategoryId}
        hideFooterPagination
      />
    </div>
  );
};

export default PropertyCategoryViewTableComponent;
