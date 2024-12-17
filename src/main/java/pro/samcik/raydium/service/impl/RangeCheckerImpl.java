package pro.samcik.raydium.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.samcik.raydium.service.NotificationService;
import pro.samcik.raydium.service.RangeChecker;
import pro.samcik.raydium.service.RaydiumService;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Service
public class RangeCheckerImpl implements RangeChecker {

    @Autowired
    private RaydiumService raydiumService;

    @Autowired
    @Qualifier("pushNotificationService")
    private NotificationService pushNotificationService;

    @Autowired
    @Qualifier("emailNotificationService")
    private NotificationService emailNotificationService;

    @Value("${config.position.range.min}")
    private Float min;

    @Value("${config.position.range.max}")
    private Float max;

    @Value("${config.position.range.warn}")
    private Float warnThreshold;

    @Autowired
    private ObjectMapper mapper;

    private final Supplier<Float> delta = () -> (max - min);

    private final Supplier<Float> warnDelta = () -> delta.get() * warnThreshold;

    private final Supplier<Float> maxThreshold = () -> max - warnDelta.get();

    private final Supplier<Float> minThreshold = () -> min + warnDelta.get();

    @Override
    public void checkRange() throws JsonProcessingException {
        var position = raydiumService.getPosition();
        var mintA = position.getPoolInfo().getMintA();
        var mintB = position.getPoolInfo().getMintB();
        var pricesInUsd = raydiumService.getPrices(List.of(mintA.getAddress(), mintB.getAddress()));
        var price = position.getPoolInfo().getPrice();

        var pricesInfo = String.format("%s%s : %,.2f, %sUSD : %,.2f, %sUSD : %,.2f", mintA.getSymbol(), mintB.getSymbol(), price,
                mintA.getSymbol(), pricesInUsd.getData().get(mintA.getAddress()),
                mintB.getSymbol(), pricesInUsd.getData().get(mintB.getAddress()));

        if (price > max || price < min) {
            var message = String.format("Price %f has gone outside the range (min: %,.2f, max: %,.2f). %s", price, min, max, pricesInfo);

            log.info(message);

            pushNotificationService.sendNotification(message);
            emailNotificationService.sendNotification(message);
        } else if (price > maxThreshold.get() || price < minThreshold.get()) {
            var threshold = price > min + delta.get() / 2 ? (max - price) * 100 / delta.get() : (price - min) * 100 / delta.get();
            var message = String.format("Price %f exceeded %,.1f%s range threshold (min: %,.2f, max: %,.2f). %s", price, threshold, "%", min, max, pricesInfo);

            log.info(message);

            pushNotificationService.sendNotification(message);
            emailNotificationService.sendNotification(message);
        } else {
            var message = String.format("Price %,.2f within the range (min: %,.2f, max: %,.2f). %s", price, min, max, pricesInfo);

            log.info(message);

            emailNotificationService.sendNotification(message);
        }
    }

    @Override
    public void checkPoolStatus() throws JsonProcessingException {
        var position = raydiumService.getPosition();
        var mintA = position.getPoolInfo().getMintA();
        var mintB = position.getPoolInfo().getMintB();
        var pricesInUsd = raydiumService.getPrices(List.of(mintA.getAddress(), mintB.getAddress()));
        var price = position.getPoolInfo().getPrice();
        var poolInfo = position.getPositionInfo();

        var message = String.format("Prices: %s%s : %,.2f (min: %,.2f, max: %,.2f), %sUSD : %,.2f, %sUSD : %,.2f, Pool value: $%.2f, Fees: $%.2f",
                mintA.getSymbol(), mintB.getSymbol(), price, min, max,
                mintA.getSymbol(), pricesInUsd.getData().get(mintA.getAddress()),
                mintB.getSymbol(), pricesInUsd.getData().get(mintB.getAddress()),
                poolInfo.getUsdValue(), poolInfo.getUnclaimedFee().getUsdValue());

        log.info(message);

        pushNotificationService.sendNotification(message);
    }
}
