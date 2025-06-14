package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tekup.tp1.entites.Document;
import com.tekup.tp1.repositories.DocumentRepository;

@Service
public class DocumentServiceImpl implements IDocumentService {
    private DocumentRepository documentRepository;
    
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
    
    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
    
    @Override
    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }
    
    @Override
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }
    
    @Override
    public Document updateDocument(Long id, Document updatedDocument) {
        Optional<Document> existingDocumentOpt = documentRepository.findById(id);
        if (existingDocumentOpt.isPresent()) {
            Document existingDocument = existingDocumentOpt.get();
            existingDocument.setFilename(updatedDocument.getFilename());
            existingDocument.setFileType(updatedDocument.getFileType());
            existingDocument.setS3Url(updatedDocument.getS3Url());
            return documentRepository.save(existingDocument);
        } else {
            throw new DocumentNotFoundException("Document not found with Id: " + id);
        }
    }
    
    @Override
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new DocumentNotFoundException("Document not found with Id: " + id);
        }
        documentRepository.deleteById(id);
    }
}