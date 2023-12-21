import { FC } from "react";
import { AmenityAdditional } from "../../domain/AmenityAdditional";
import { DataGrid } from "@mui/x-data-grid";

const AmenityAdditionalTableComponent: FC<{ data: AmenityAdditional[], header: string }> = ({
  data, header
}) => {
  const columns = [
    { field: "amenityId", headerName: "Amenity ID", flex: 1 },
    { field: "description", headerName: "Description", flex: 1 },
    { field: "categoryId", headerName: "Category ID", flex: 1 },
  ];

  return (
    <div style={{ height: 400, width: "100%" }}>
      <h1>{header}</h1>
      <DataGrid
        rows={data}
        columns={columns}
        getRowId={(row: AmenityAdditional) => row.amenityId}
        hideFooterPagination
      />
    </div>
  );
};

export default AmenityAdditionalTableComponent;
