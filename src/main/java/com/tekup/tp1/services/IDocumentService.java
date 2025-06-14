package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Document;
import com.tekup.tp1.exception.DocumentNotFoundException;

public interface IDocumentService {
    Document createDocument(Document document);
    Document updateDocument(Long id, Document document) throws DocumentNotFoundException;
    void deleteDocument(Long id) throws DocumentNotFoundException;
    
    Optional<Document> getDocumentById(Long id);
    List<Document> getAllDocuments();
}
