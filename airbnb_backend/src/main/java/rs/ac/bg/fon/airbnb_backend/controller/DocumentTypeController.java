package rs.ac.bg.fon.airbnb_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.airbnb_backend.domain.DocumentType;
import rs.ac.bg.fon.airbnb_backend.service.DocumentTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentTypes")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @GetMapping
    public List<DocumentType> findAll() {
        return documentTypeService.finaAll();
    }

    @GetMapping("/{documentTypeId}")
    public DocumentType findById(@PathVariable Long documentTypeId) {
        return documentTypeService.findById(documentTypeId);
    }
}
