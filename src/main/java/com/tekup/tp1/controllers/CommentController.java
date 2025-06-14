package com.tekup.tp1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.Comment;
import com.tekup.tp1.exception.CommentNotFoundException;
import com.tekup.tp1.services.ICommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
@Tag(name = "Comment Management", description = "APIs for managing comments including CRUD operations and advanced search")
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
    
    @Operation(summary = "Create a new comment", description = "Add a comment to a task with validation (max 1000 characters)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
        @ApiResponse(responseCode = "400", description = "Invalid comment data")
    })
    @PostMapping
    public ResponseEntity<Comment> createComment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Comment object to be created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class),
                    examples = @ExampleObject(value = "{\n  \"content\": \"This is a comment\",\n  \"user\": {\"id\": 1},\n  \"task\": {\"id\": 1}\n}")
                )
            )
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
    
    @Operation(summary = "Get all comments sorted by date", description = "Retrieve all comments sorted by creation date (newest first)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
    })
    @GetMapping("/sorted-by-date")
    public ResponseEntity<List<Comment>> getAllCommentsSortedByDate() {
        List<Comment> comments = commentService.getAllCommentsSortedByDate();
        return ResponseEntity.ok(comments);
    }
}
