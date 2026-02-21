package com.investment.goals.config;

import com.investment.goals.entity.Fund;
import com.investment.goals.entity.Goal;
import com.investment.goals.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final GoalRepository goalRepository;

    @Override
    public void run(String... args) {
        if (goalRepository.count() > 0) {
            log.info("Data already seeded. Skipping.");
            return;
        }

        log.info("Seeding default goals...");

        List<Goal> goals = List.of(
            buildGoal("Son's Intermediate", "Education", "🎓", "on-track",
                "₹16 Lakhs", 2038, "Corpus building",
                "⚠️ Exit Strategy: 2035 → Move corpus to HDFC BAF → Start SWP from 2038",
                1,
                List.of(
                    fund("Bandhan Small Cap", "₹8,000"),
                    fund("ICICI Nifty Next 50", "₹5,000")
                )),

            buildGoal("Son's Graduation", "Education", "🎓", "on-track",
                "₹1.3 Crore", 2040, "Corpus building",
                "⚠️ Exit Strategy: 2037 → Move corpus to HDFC BAF → Start SWP from 2040",
                2,
                List.of(
                    fund("HDFC Mid Cap", "₹15,000"),
                    fund("Bandhan Small Cap", "₹7,000")
                )),

            buildGoal("Son's Higher Education", "Education", "🎓", "auto-funded",
                "₹1.8 Crore", 2044, "₹26L @ 12% × 18 yrs = ~₹1.8Cr",
                "✅ Existing ₹26L corpus alone covers this goal entirely.\n⚠️ Exit: 2041 → Move to HDFC BAF → SWP from 2044",
                3,
                List.of(
                    fund("Parag Parikh Flexi Cap", "₹5,000 (buffer)")
                )),

            buildGoal("FIRE / Early Retirement", "FIRE / Retirement", "🔥", "in-progress",
                "₹2.5 Crore", 2033, "Accumulating",
                "⚠️ Exit: 2031–32 → Shift to HDFC BAF → SWP ₹60–70k/month from 2033.\n4% withdrawal rule.",
                4,
                List.of(
                    fund("Parag Parikh Flexi Cap", "₹20,000"),
                    fund("ICICI Nifty Next 50", "₹5,000"),
                    fund("HDFC Mid Cap", "₹5,000")
                )),

            buildGoal("House Purchase", "Housing", "🏠", "not-started",
                "₹25–30L down payment", 2032, "Starts after loan closes (2028)",
                "ℹ️ Personal loan closes by 2027–28. Freed EMI → ₹25k house fund + ₹28k extra SIP.",
                5,
                List.of(
                    fund("HDFC BAF (from 2028)", "₹25,000")
                )),

            buildGoal("Vacation Fund", "Vacation", "✈️", "on-track",
                "₹30–50k per trip", 0, "Ongoing",
                "✅ Redeem anytime. Capital protected. Instant withdrawal.",
                6,
                List.of(
                    fund("Liquid Fund", "₹5,000")
                )),

            buildGoal("Gold Hedge", "Gold / Hedge", "🥇", "in-progress",
                "5–10% of portfolio", 0, "Paused — restart needed",
                "⚠️ Restart SIP. Gold and equity move inversely — critical hedge during corrections.",
                7,
                List.of(
                    fund("Goldbees / SGB", "₹5,000")
                ))
        );

        goalRepository.saveAll(goals);
        log.info("✅ Seeded {} default goals successfully.", goals.size());
    }

    private Goal buildGoal(String name, String category, String emoji, String status,
                            String target, int year, String current, String note,
                            int sortOrder, List<Fund> funds) {
        Goal goal = Goal.builder()
                .name(name)
                .category(category)
                .emoji(emoji)
                .status(status)
                .target(target)
                .targetYear(year > 0 ? year : null)
                .current(current)
                .note(note)
                .sortOrder(sortOrder)
                .build();
        goal.setFunds(funds);
        return goal;
    }

    private Fund fund(String name, String amount) {
        return Fund.builder().name(name).amount(amount).build();
    }
}
