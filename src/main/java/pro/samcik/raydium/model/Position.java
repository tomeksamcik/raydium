package pro.samcik.raydium.model;

import lombok.Data;

@Data
public class Position {

    private String name;

    private PoolInfo poolInfo;

    private PositionInfo positionInfo;
}
