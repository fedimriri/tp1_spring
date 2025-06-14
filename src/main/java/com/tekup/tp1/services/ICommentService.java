package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Comment;
import com.tekup.tp1.exception.CommentNotFoundException;

public interface ICommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment) throws CommentNotFoundException;
    void deleteComment(Long id) throws CommentNotFoundException;
    
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();
}
