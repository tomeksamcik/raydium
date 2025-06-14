package pro.samcik.raydium.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.samcik.raydium.service.RangeChecker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class Scheduler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private RangeChecker rangeChecker;

    @Scheduled(fixedRateString = "${config.check-range-rate}")
    public void checkRange() {
        try {
            log.info("Checking range {}", formatter.format(LocalDateTime.now()));

            rangeChecker.checkRange();
        } catch (Exception e) {
            log.error("Exception while checking range: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRateString = "${config.check-pool-status-rate}")
    public void checkPoolStatus() {
        try {
            log.info("Checking pool status {}", formatter.format(LocalDateTime.now()));

            rangeChecker.checkPoolStatus();
        } catch (Exception e) {
            log.error("Exception while checking pool status: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRateString = "${config.record-pool-details-rate}")
    public void recordPoolDetails() {
        log.info("Recording pool details {}", formatter.format(LocalDateTime.now()));
    }
}
