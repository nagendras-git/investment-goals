package com.investment.goals.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalRequestDTO {

    @NotBlank(message = "Goal name is required")
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

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class FundDTO {
        private Long id;
        @NotBlank(message = "Fund name is required")
        private String name;
        private String amount;
    }
}
