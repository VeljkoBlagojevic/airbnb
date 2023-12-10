package rs.ac.bg.fon.airbnb_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentType {

    private Long documentTypeId;

    private String name;
}
