package pro.samcik.raydium.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import pro.samcik.raydium.model.Position;
import pro.samcik.raydium.model.Prices;

import java.util.List;

public interface RaydiumService {

    Position getPosition() throws JsonProcessingException;

    Prices getPrices(List<String> mintIds) throws JsonProcessingException;
}
