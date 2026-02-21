package com.investment.goals.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalResponseDTO {

    private Long id;
    private String name;
    private String category;
    private String emoji;
    private String status;
    private String target;
    private Integer targetYear;
    private String current;
    private String note;
    private Integer sortOrder;
    private List<FundDTO> funds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class FundDTO {
        private Long id;
        private String name;
        private String amount;
    }
}
