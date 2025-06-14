package com.tekup.tp1.repositories;

import com.tekup.tp1.entites.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Advanced methods for Project
    List<Project> findByStatus(String status);

    @Query("SELECT p FROM Project p WHERE p.startDate > :currentDate")
    List<Project> findUpcomingProjects(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT p FROM Project p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Project> findByNameContainingOrDescriptionContaining(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p FROM Project p JOIN p.tasks t JOIN t.assignedUsers u WHERE u.id = :userId")
    List<Project> findProjectsByUserParticipation(@Param("userId") Long userId);
}