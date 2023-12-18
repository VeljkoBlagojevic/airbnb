package rs.ac.bg.fon.airbnb_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.DocumentType;
import rs.ac.bg.fon.airbnb_backend.repository.DocumentTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentTypes")
@RequiredArgsConstructor
@CrossOrigin
public class DocumentTypeController {

    private final DocumentTypeRepository documentTypeRepository;

    @GetMapping
    public List<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }

    @GetMapping("/{documentTypeId}")
    public DocumentType findById(@PathVariable Long documentTypeId) {
        return documentTypeRepository.findById(documentTypeId);
    }
}
