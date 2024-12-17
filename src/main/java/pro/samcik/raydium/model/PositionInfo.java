package pro.samcik.raydium.model;

import lombok.Data;

@Data
public class PositionInfo {

    private Float usdValue;

    private Float amountA;

    private Float amountB;

    private UnclaimedFee unclaimedFee;
}
