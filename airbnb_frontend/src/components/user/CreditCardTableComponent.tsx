import { FC } from "react";
import { CreditCard } from "../../domain/CreditCard";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";

const CreditCardViewTableComponent: FC<{
  data: CreditCard[];
  handleDelete: Function;
  openDialogForEdit: Function;
  header: string
}> = ({ data, handleDelete, openDialogForEdit, header }) => {
  const columns = [
    { field: "creditCardNumber", headerName: "Credit Card Number", flex: 1 },
    { field: "name", headerName: "Name", flex: 1 },
    { field: "expiryDate", headerName: "Expiry Date", flex: 1 },
    {
      field: "user.userId",
      headerName: "User ID",
      flex: 1,
      valueGetter: (params: any) => params.row.user.userId,
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
      <h1 style={{ textAlign: "center", marginBottom: "20px" }}>
        {header}
      </h1>
      <DataGrid
        rows={data}
        columns={columns}
        getRowId={(row: CreditCard) => row.creditCardNumber}
        hideFooterPagination
      />
    </div>
  );
};

export default CreditCardViewTableComponent;
