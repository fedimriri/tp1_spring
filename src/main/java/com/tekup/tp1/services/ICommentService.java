package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Comment;

public interface ICommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment);
    void deleteComment(Long id);
    
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();
}