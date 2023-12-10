package rs.ac.bg.fon.airbnb_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.airbnb_backend.domain.DocumentType;
import rs.ac.bg.fon.airbnb_backend.repository.DocumentTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    public List<DocumentType> finaAll() {
        return documentTypeRepository.findAll();
    }

    public DocumentType findById(Long id) {
        return documentTypeRepository.findById(id);
    }

}
