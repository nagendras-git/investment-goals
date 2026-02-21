package com.investment.goals.repository;

import com.investment.goals.entity.Fund;
import com.investment.goals.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    // Fetch all goals ordered by sortOrder, then createdAt
    @Query("SELECT g FROM Goal g ORDER BY COALESCE(g.sortOrder, 9999) ASC, g.createdAt ASC")
    List<Goal> findAllOrdered();

    // Fetch goals by category
    List<Goal> findByCategoryOrderByCreatedAtAsc(String category);

    // Fetch goals by status
    List<Goal> findByStatusOrderByCreatedAtAsc(String status);
}
