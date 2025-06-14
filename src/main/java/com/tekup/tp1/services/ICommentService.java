package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Comment;
import com.tekup.tp1.exception.CommentNotFoundException;

public interface ICommentService {
    // CRUD operations
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment) throws CommentNotFoundException;
    void deleteComment(Long id) throws CommentNotFoundException;

    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();

    // Advanced methods
    List<Comment> searchCommentsByKeyword(String keyword);
    List<Comment> getCommentsByUser(Long userId);
    List<Comment> getCommentsByTaskSortedByDate(Long taskId);
    List<Comment> getAllCommentsSortedByDate();
}
