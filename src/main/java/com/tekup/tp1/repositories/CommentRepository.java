package com.tekup.tp1.repositories;

import com.tekup.tp1.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Advanced methods for Comment
    @Query("SELECT c FROM Comment c WHERE c.content LIKE %:keyword%")
    List<Comment> findByContentContaining(@Param("keyword") String keyword);

    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId")
    List<Comment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c WHERE c.task.id = :taskId ORDER BY c.createdAt DESC")
    List<Comment> findByTaskIdOrderByCreatedAtDesc(@Param("taskId") Long taskId);

    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    List<Comment> findAllOrderByCreatedAtDesc();
}