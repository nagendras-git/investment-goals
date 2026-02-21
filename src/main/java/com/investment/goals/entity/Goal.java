package com.investment.goals.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Goal name is required")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String emoji;

    @Column(nullable = false)
    private String status;

    private String target;

    @Column(name = "target_year")
    private Integer targetYear;

    @Column(name = "current_status")
    private String current;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @OneToMany(
        mappedBy = "goal",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @OrderBy("id ASC")
    @Builder.Default
    private List<Fund> funds = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper to manage bidirectional relationship
    public void setFunds(List<Fund> funds) {
        this.funds.clear();
        if (funds != null) {
            funds.forEach(f -> f.setGoal(this));
            this.funds.addAll(funds);
        }
    }
}
