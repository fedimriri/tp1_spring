package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Document;

public interface IDocumentService {
    Document createDocument(Document document);
    Document updateDocument(Long id, Document document);
    void deleteDocument(Long id);
    
    Optional<Document> getDocumentById(Long id);
    List<Document> getAllDocuments();
}