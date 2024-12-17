package pro.samcik.raydium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.samcik.raydium.model.Position;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DeserializationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeserializeValue() throws JsonProcessingException {
        var json = "{\"name\":\"Raydium Concentrated Liquidity\",\"symbol\":\"RCL\",\"description\":\"Raydium Concentrated Liquidity Position\",\"external_url\":\"https://raydium.io/\",\"collection\":{\"name\":\"Raydium Concentrated Liquidity\",\"family\":\"Raydium Concentrated Liquidity\"},\"image\":\"https://img-v1.raydium.io/share/ea1ef711-652b-422b-a593-04426f8f2a04.jpg\",\"poolInfo\":{\"type\":\"Concentrated\",\"programId\":\"CAMMCzo5YL8w4VFF8KVHrK22GGUsp5VTaW7grrKgrWqK\",\"id\":\"44YBnjSTkGxhPW8ZpYBFpfGb1tNP6Epzi83SpF2WcbnL\",\"mintA\":{\"chainId\":101,\"address\":\"So11111111111111111111111111111111111111112\",\"programId\":\"TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA\",\"logoURI\":\"https://img-v1.raydium.io/icon/So11111111111111111111111111111111111111112.png\",\"symbol\":\"WSOL\",\"name\":\"Wrapped SOL\",\"decimals\":9,\"tags\":[],\"extensions\":{}},\"mintB\":{\"chainId\":101,\"address\":\"CzLSujWBLFsSjncfkh59rUFqvafWcY5tzedWJSuypump\",\"programId\":\"TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA\",\"logoURI\":\"https://img-v1.raydium.io/icon/CzLSujWBLFsSjncfkh59rUFqvafWcY5tzedWJSuypump.png\",\"symbol\":\"GOAT\",\"name\":\"Goatseus Maximus\",\"decimals\":6,\"tags\":[],\"extensions\":{}},\"rewardDefaultPoolInfos\":\"Clmm\",\"rewardDefaultInfos\":[],\"price\":235.396383997263,\"mintAmountA\":2268.635980511,\"mintAmountB\":3055290.727133,\"feeRate\":0.0025,\"openTime\":\"1728643197\",\"tvl\":3649782.38,\"day\":{\"volume\":19108666.37662778,\"volumeQuote\":17834437.375455536,\"volumeFee\":47771.66594156943,\"apr\":477.75,\"feeApr\":477.75,\"priceMin\":190.8091100565171,\"priceMax\":247.63559054570464,\"rewardApr\":[]},\"week\":{\"volume\":277743634.12392145,\"volumeQuote\":282283930.31671894,\"volumeFee\":694359.0853098034,\"apr\":570.74,\"feeApr\":570.74,\"priceMin\":163.41532075271385,\"priceMax\":288.85803984531134,\"rewardApr\":[]},\"month\":{\"volume\":1028478633.1541854,\"volumeQuote\":1430809111.7860532,\"volumeFee\":2571196.582885463,\"apr\":845.38,\"feeApr\":845.38,\"priceMin\":163.41532075271385,\"priceMax\":635.5185920995697,\"rewardApr\":[]},\"pooltype\":[],\"farmUpcomingCount\":0,\"farmOngoingCount\":0,\"farmFinishedCount\":0,\"config\":{\"id\":\"E64NGkDLLCdQ2yFNPcavaKptrEgmiQaNykUuLC1Qgwyp\",\"index\":1,\"protocolFeeRate\":120000,\"tradeFeeRate\":2500,\"tickSpacing\":60,\"fundFeeRate\":40000,\"defaultRange\":0.1,\"defaultRangePoint\":[0.01,0.05,0.1,0.2,0.5]},\"burnPercent\":0.14},\"positionInfo\":{\"tvlPercentage\":0.24,\"usdValue\":9099.94563145758,\"amountA\":11.696464114,\"amountB\":6163.771298,\"unclaimedFee\":{\"amountA\":1.245697706,\"amountB\":291.03949,\"usdFeeValue\":596.2285399369697,\"reward\":[],\"usdRewardValue\":0,\"usdValue\":596.2285399369697}}}";

        var position = objectMapper.readValue(json, Position.class);

        assertThat(position).isNotNull();
    }
}
