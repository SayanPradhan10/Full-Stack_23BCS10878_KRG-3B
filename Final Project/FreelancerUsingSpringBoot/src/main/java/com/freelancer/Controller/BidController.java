package com.freelancer.Controller;

import com.freelancer.Entity.Bid;
import com.freelancer.Entity.User;
import com.freelancer.Entity.Project;
import com.freelancer.POJO.BidDetail;
import com.freelancer.Service.BidService;
import com.freelancer.Service.UserService;
import com.freelancer.Service.ProjectService;
import com.freelancer.Utility.ResultObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BidController {

    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    private static final Logger logger = LoggerFactory.getLogger(BidController.class);

    // ✅ 1. Get all bids
    @GetMapping(path = "/allBids", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Bid> getAllBids() {
        return bidService.getAllBids();
    }

    // ✅ 2. Post or update a bid
    @PostMapping(path = "/postBid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postBid(@RequestBody String bidJson, HttpSession session) {
        ResultObject ro = new ResultObject("Error adding bid", "", null);
        try {
            JSONObject json = new JSONObject(bidJson);
            Bid bid = new Bid();

            // Sanitize and set all fields
            bid.setUserId(json.getLong("userId"));
            bid.setProjectId(json.getLong("projectId"));
            bid.setBid_amount(json.getString("bid_amount"));

            // ✅ Clean period to be only numbers
            String rawPeriod = json.getString("bid_period");
            String numericPeriod = rawPeriod.replaceAll("[^0-9]", "");  // keep only digits
            bid.setBid_period(numericPeriod);

            bid.setBid_status("BID_SENT");

            Bid saved = bidService.save(bid);

            ro.setErrorMsg("");
            ro.setSuccessMsg("Bid Added");
            ro.setData(saved);

            return new ResponseEntity<>(ro, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error adding bid: {}", e.getMessage());
            ro.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(ro, HttpStatus.BAD_REQUEST);
        }
    }


    // ✅ 3. Get all bids for a given project (with avg bid)
    @PostMapping(path = "/getProjectBids/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectBids(@PathVariable("id") long projectId) {
        ResultObject ro = new ResultObject("Error fetching bid", "", null);
        try {
            List<Bid> bids = bidService.findByProjectId(projectId);
            List<BidDetail> bidDetails = new ArrayList<>();

            // Compute average bid for this project
            double avgBid = 0.0;
            if (!bids.isEmpty()) {
                avgBid = bids.stream()
                        .mapToDouble(b -> Double.parseDouble(b.getBid_amount().replaceAll("[^0-9.]", "")))
                        .average()
                        .orElse(0.0);
            }

            if (!bids.isEmpty()) {
                for (Bid bid : bids) {
                    User bidder = userService.findById(bid.getUserId());
                    Project project = projectService.findById(bid.getProjectId());
                    User employer = userService.findById(project.getEmployerId());

                    bidDetails.add(new BidDetail(
                            bid.getId(),
                            bid.getUserId(),
                            bid.getProjectId(),
                            bid.getBid_period(),
                            bid.getBid_amount(),
                            bid.getBid_status(),
                            bidder != null ? bidder.getName() : "Unknown",
                            project != null ? project.getTitle() : "Untitled",
                            employer != null ? employer.getName() : "Unknown Employer",
                            avgBid
                    ));
                }
                ro.setErrorMsg("");
                ro.setSuccessMsg("Bids Found");
                ro.setData(bidDetails);
            } else {
                ro.setErrorMsg("No bids found for project ID " + projectId);
            }

            return new ResponseEntity<>(ro, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            ro.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(ro, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ 4. Get all bids placed by a specific user (Dashboard)
    @PostMapping(path = "/getUserBidProjects", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserBidProjects(@RequestBody String user) {
        ResultObject ro = new ResultObject("No Bids found", "", null);
        try {
            JSONObject jsonObject = new JSONObject(user);
            long userId = jsonObject.getLong("id");

            List<Bid> bids = bidService.findByUserId(userId);
            List<BidDetail> bidDetails = new ArrayList<>();

            if (!bids.isEmpty()) {
                for (Bid bid : bids) {
                    User bidder = userService.findById(bid.getUserId());
                    Project project = projectService.findById(bid.getProjectId());
                    User employer = userService.findById(project.getEmployerId());

                    // Compute avg bid for this project
                    List<Bid> allProjectBids = bidService.findByProjectId(bid.getProjectId());
                    double avgBid = 0.0;
                    if (!allProjectBids.isEmpty()) {
                        avgBid = allProjectBids.stream()
                                .mapToDouble(b -> Double.parseDouble(b.getBid_amount().replaceAll("[^0-9.]", "")))
                                .average()
                                .orElse(0.0);
                    }

                    bidDetails.add(new BidDetail(
                            bid.getId(),
                            bid.getUserId(),
                            bid.getProjectId(),
                            bid.getBid_period(),
                            bid.getBid_amount(),
                            bid.getBid_status(),
                            bidder != null ? bidder.getName() : "Unknown",
                            project != null ? project.getTitle() : "Untitled",
                            employer != null ? employer.getName() : "Unknown Employer",
                            avgBid
                    ));
                }

                ro.setErrorMsg("");
                ro.setSuccessMsg("Bids Found");
                ro.setData(bidDetails);
            } else {
                ro.setErrorMsg("No bids found for user ID " + userId);
            }

            return new ResponseEntity<>(ro, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            ro.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(ro, HttpStatus.BAD_REQUEST);
        }
    }

}
