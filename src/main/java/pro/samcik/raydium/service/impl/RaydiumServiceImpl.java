package pro.samcik.raydium.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.samcik.raydium.model.Position;
import pro.samcik.raydium.model.Prices;
import pro.samcik.raydium.service.RaydiumService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RaydiumServiceImpl implements RaydiumService {

    private final String CLMM_API_URL = "https://dynamic-ipfs.raydium.io/clmm/position?id={positionId}";

    private final String MINT_API_URL = "https://api-v3.raydium.io/mint/price?mints={mintIds}";

    @Value("${config.position.id}")
    private String positionId;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Position getPosition() throws JsonProcessingException {
        var response = restTemplate.getForEntity(CLMM_API_URL, String.class, Map.of("positionId", positionId));

        log.debug("Response code: {}, body: {}", response.getStatusCode(), response.getBody());

        return objectMapper.readValue(response.getBody(), Position.class);
    }

    public Prices getPrices(List<String> mintIds) throws JsonProcessingException {
        var response = restTemplate.getForEntity(MINT_API_URL, String.class, Map.of("mintIds", String.join(",", mintIds)));

        log.debug("Response code: {}, body: {}", response.getStatusCode(), response.getBody());

        return objectMapper.readValue(response.getBody(), Prices.class);
    }
}
