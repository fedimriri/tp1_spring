package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tekup.tp1.entites.Comment;
import com.tekup.tp1.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements ICommentService {
    private CommentRepository commentRepository;
    
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    
    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
    
    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
    @Override
    public Comment updateComment(Long id, Comment updatedComment) {
        Optional<Comment> existingCommentOpt = commentRepository.findById(id);
        if (existingCommentOpt.isPresent()) {
            Comment existingComment = existingCommentOpt.get();
            existingComment.setContent(updatedComment.getContent());
            return commentRepository.save(existingComment);
        } else {
            throw new CommentNotFoundException("Comment not found with Id: " + id);
        }
    }
    
    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException("Comment not found with Id: " + id);
        }
        commentRepository.deleteById(id);
    }
}