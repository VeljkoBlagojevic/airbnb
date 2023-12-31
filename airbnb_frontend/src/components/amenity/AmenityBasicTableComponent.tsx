import { FC } from "react";
import { AmenityBasic } from "../../domain/AmenityBasic";
import { DataGrid } from "@mui/x-data-grid";

const AmenityBasicTableComponent: FC<{
  data: AmenityBasic[];
  header: string;
}> = ({ data, header }) => {
  const columns = [
    { field: "amenityId", headerName: "Amenity ID", flex: 1 },
    { field: "name", headerName: "Name", flex: 1 },
  ];

  return (
    <div style={{ height: 400, width: "100%" }}>
      <h1>{header}</h1>
      <DataGrid
        rows={data}
        columns={columns}
        getRowId={(row: AmenityBasic) => row.amenityId}
        hideFooterPagination
      />
    </div>
  );
};

export default AmenityBasicTableComponent;
