import { FC } from "react";
import { Review } from "../../domain/Review";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";
import { useNavigate } from "react-router";

const ReviewTableComponent: FC<{
  data: Review[];
  handleDelete: Function;
  openDialogForEdit: Function;
}> = ({ data, handleDelete, openDialogForEdit }) => {
  const navigate = useNavigate();
  const columns = [
    { field: "reviewId", headerName: "Review ID", flex: 1 },
    { field: "description", headerName: "Description", flex: 1 },
    { field: "overallRating", headerName: "Average rating", flex: 1 },
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
    {
      field: "ratings",
      headerName: "Ratings",
      flex: 1,
      renderCell: (params: any) => (
        <>
          <Button onClick={() => navigate(`/reviews/${JSON.stringify(params.row.reviewId)}/ratings`)}>
            See Ratings
          </Button>
        </>
      ),
    },
  ];

  return (
    <div style={{ height: 400, width: "100%" }}>
      <DataGrid
        rows={data}
        columns={columns}
        getRowId={(row: Review) => row.reviewId}
        hideFooterPagination
      />
    </div>
  );
};

export default ReviewTableComponent;
