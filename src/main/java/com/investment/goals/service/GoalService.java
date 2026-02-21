package com.investment.goals.service;

import com.investment.goals.dto.GoalRequestDTO;
import com.investment.goals.dto.GoalResponseDTO;
import com.investment.goals.entity.Fund;
import com.investment.goals.entity.Goal;
import com.investment.goals.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoalService {

    private final GoalRepository goalRepository;

    // ── READ ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<GoalResponseDTO> getAllGoals() {
        log.info("Fetching all goals");
        return goalRepository.findAllOrdered()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GoalResponseDTO getGoalById(Long id) {
        log.info("Fetching goal with id: {}", id);
        Goal goal = findGoalOrThrow(id);
        return toResponseDTO(goal);
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public GoalResponseDTO createGoal(GoalRequestDTO request) {
        log.info("Creating new goal: {}", request.getName());
        Goal goal = toEntity(request);
        Goal saved = goalRepository.save(goal);
        return toResponseDTO(saved);
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public GoalResponseDTO updateGoal(Long id, GoalRequestDTO request) {
        log.info("Updating goal with id: {}", id);
        Goal goal = findGoalOrThrow(id);

        goal.setName(request.getName());
        goal.setCategory(request.getCategory());
        goal.setEmoji(request.getEmoji());
        goal.setStatus(request.getStatus());
        goal.setTarget(request.getTarget());
        goal.setTargetYear(request.getTargetYear());
        goal.setCurrent(request.getCurrent());
        goal.setNote(request.getNote());
        goal.setSortOrder(request.getSortOrder());

        // Replace funds — orphanRemoval handles deletion
        List<Fund> newFunds = mapFunds(request, goal);
        goal.setFunds(newFunds);

        Goal updated = goalRepository.save(goal);
        return toResponseDTO(updated);
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void deleteGoal(Long id) {
        log.info("Deleting goal with id: {}", id);
        if (!goalRepository.existsById(id)) {
            throw new EntityNotFoundException("Goal not found with id: " + id);
        }
        goalRepository.deleteById(id);
    }

    // ── REORDER ──────────────────────────────────────────────────────────────

    @Transactional
    public void reorderGoals(List<Long> orderedIds) {
        log.info("Reordering {} goals", orderedIds.size());
        for (int i = 0; i < orderedIds.size(); i++) {
            Long goalId = orderedIds.get(i);
            Goal goal = findGoalOrThrow(goalId);
            goal.setSortOrder(i);
            goalRepository.save(goal);
        }
    }

    // ── HELPERS ──────────────────────────────────────────────────────────────

    private Goal findGoalOrThrow(Long id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + id));
    }

    private Goal toEntity(GoalRequestDTO dto) {
        Goal goal = Goal.builder()
                .name(dto.getName())
                .category(dto.getCategory() != null ? dto.getCategory() : "Other")
                .emoji(dto.getEmoji() != null ? dto.getEmoji() : "📌")
                .status(dto.getStatus() != null ? dto.getStatus() : "in-progress")
                .target(dto.getTarget())
                .targetYear(dto.getTargetYear())
                .current(dto.getCurrent())
                .note(dto.getNote())
                .sortOrder(dto.getSortOrder())
                .build();

        List<Fund> funds = mapFunds(dto, goal);
        goal.setFunds(funds);
        return goal;
    }

    private List<Fund> mapFunds(GoalRequestDTO dto, Goal goal) {
        if (dto.getFunds() == null) return List.of();
        return dto.getFunds().stream()
                .filter(f -> f.getName() != null && !f.getName().isBlank())
                .map(f -> Fund.builder()
                        .name(f.getName())
                        .amount(f.getAmount())
                        .goal(goal)
                        .build())
                .collect(Collectors.toList());
    }

    private GoalResponseDTO toResponseDTO(Goal goal) {
        List<GoalResponseDTO.FundDTO> fundDTOs = goal.getFunds().stream()
                .map(f -> new GoalResponseDTO.FundDTO(f.getId(), f.getName(), f.getAmount()))
                .collect(Collectors.toList());

        return GoalResponseDTO.builder()
                .id(goal.getId())
                .name(goal.getName())
                .category(goal.getCategory())
                .emoji(goal.getEmoji())
                .status(goal.getStatus())
                .target(goal.getTarget())
                .targetYear(goal.getTargetYear())
                .current(goal.getCurrent())
                .note(goal.getNote())
                .sortOrder(goal.getSortOrder())
                .funds(fundDTOs)
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .build();
    }
}
