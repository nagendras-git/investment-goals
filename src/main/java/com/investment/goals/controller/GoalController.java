package com.investment.goals.controller;

import com.investment.goals.dto.GoalRequestDTO;
import com.investment.goals.dto.GoalResponseDTO;
import com.investment.goals.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
@Slf4j
public class GoalController {

    private final GoalService goalService;

    // GET all goals
    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    // GET single goal
    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> getGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    // POST create goal
    @PostMapping
    public ResponseEntity<GoalResponseDTO> createGoal(@Valid @RequestBody GoalRequestDTO request) {
        GoalResponseDTO created = goalService.createGoal(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT update goal
    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody GoalRequestDTO request) {
        return ResponseEntity.ok(goalService.updateGoal(id, request));
    }

    // DELETE goal
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.ok(Map.of("message", "Goal deleted successfully", "id", id.toString()));
    }

    // PUT reorder goals
    @PutMapping("/reorder")
    public ResponseEntity<Map<String, String>> reorderGoals(@RequestBody List<Long> orderedIds) {
        goalService.reorderGoals(orderedIds);
        return ResponseEntity.ok(Map.of("message", "Goals reordered successfully"));
    }
}
