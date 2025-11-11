package com.freelancer.Scheduler;

import com.freelancer.Entity.Project;
import com.freelancer.Entity.Bid;
import com.freelancer.Service.ProjectService;
import com.freelancer.Service.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
public class BidScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BidScheduler.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BidService bidService;

    // 🔁 Run every 10 seconds for testing
    @Scheduled(fixedRate = 10 * 1000)
    public void closeExpiredBids() {
        logger.info("Running scheduled bid closing job...");

        try {
            List<Project> projects = (List<Project>) projectService.getAllProjects();

            for (Project project : projects) {
                // ✅ skip if no deadline or already closed
                if (project.getBidDeadline() == null || !"OPEN".equalsIgnoreCase(project.getStatus()))
                    continue;

                // ✅ if current time is after deadline → close it
                if (LocalDateTime.now().isAfter(project.getBidDeadline())) {
                    logger.info("Closing project: {}", project.getTitle());

                    List<Bid> bids = bidService.findByProjectId(project.getId());

                    if (!bids.isEmpty()) {
                        // ✅ choose best (lowest) bid
                        Bid bestBid = bids.stream()
                                .min(Comparator.comparingDouble(b ->
                                        Double.parseDouble(b.getBid_amount().replaceAll("[^0-9.]", "")))
                                ).orElse(null);

                        if (bestBid != null) {
                            logger.info("Awarding project {} to freelancer {}", project.getId(), bestBid.getUserId());
                            project.setFreelancerId(bestBid.getUserId());
                            project.setStatus("CLOSED");
                            projectService.save(project);

                            // Update bid statuses
                            for (Bid bid : bids) {
                                bid.setBid_status(bid.equals(bestBid) ? "BID_ACCEPTED" : "BID_REJECTED");
                                bidService.save(bid);
                            }
                        }
                    } else {
                        logger.info("No bids found for project {}", project.getId());
                        project.setStatus("CLOSED_NO_BIDS");
                        projectService.save(project);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Error during bid closing job: {}", e.getMessage());
        }

        logger.info("Bid closing job completed.");
    }
}
