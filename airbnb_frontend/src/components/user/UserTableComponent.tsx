import { FC } from "react";
import { User } from "../../domain/User";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";

const UserTableComponent: FC<{
  data: User[];
  handleDelete: Function;
  openDialogForEdit: Function;
}> = ({ data, handleDelete, openDialogForEdit }) => {
  const columns = [
    { field: "userId", headerName: "User ID", flex: 1 },
    { field: "name", headerName: "Name", flex: 1 },
    { field: "gender", headerName: "Gender", flex: 1 },
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
        getRowId={(row: User) => row.userId}
        hideFooterPagination
      />
    </div>
  );
};

export default UserTableComponent;
