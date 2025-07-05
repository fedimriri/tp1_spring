package com.tekup.tp1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.Comment;
import com.tekup.tp1.exception.CommentNotFoundException;
import com.tekup.tp1.services.ICommentService;

@RestController
@RequestMapping("/api/v0/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    
    private final ICommentService commentService;
    
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    
    // CRUD Operations
    
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            Comment updatedComment = commentService.updateComment(id, comment);
            return ResponseEntity.ok(updatedComment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Advanced Methods
    
    @GetMapping("/search")
    public ResponseEntity<List<Comment>> searchCommentsByKeyword(@RequestParam String keyword) {
        List<Comment> comments = commentService.searchCommentsByKeyword(keyword);
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Long userId) {
        List<Comment> comments = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/by-task/{taskId}")
    public ResponseEntity<List<Comment>> getCommentsByTaskSortedByDate(@PathVariable Long taskId) {
        List<Comment> comments = commentService.getCommentsByTaskSortedByDate(taskId);
        return ResponseEntity.ok(comments);
    }
    

    @GetMapping("/sorted-by-date")
    public ResponseEntity<List<Comment>> getAllCommentsSortedByDate() {
        List<Comment> comments = commentService.getAllCommentsSortedByDate();
        return ResponseEntity.ok(comments);
    }
}
