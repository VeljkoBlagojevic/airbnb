import { FC } from "react";
import { Rating } from "../../domain/Rating";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";

const RatingTableComponent: FC<{
  data: Rating[];
  handleDelete: Function;
  openDialogForEdit: Function;
}> = ({ data, handleDelete, openDialogForEdit }) => {
  const columns = [
    {
      field: "ratingId.reviewId",
      headerName: "Review ID",
      flex: 1,
      valueGetter: (params: any) => params.row.ratingId.reviewId,
    },
    {
      field: "category.name",
      headerName: "Category Name",
      flex: 1,
      valueGetter: (params: any) => params.row.category.name,
    },
    { field: "value", headerName: "Value", flex: 1 },
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
        getRowId={(row: Rating) => row.ratingId.reviewCategoryId}
        hideFooterPagination
      />
    </div>
  );
};

export default RatingTableComponent;
