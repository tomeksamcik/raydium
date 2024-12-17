package pro.samcik.raydium.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface RangeChecker {

    void checkRange() throws JsonProcessingException;

    void checkPoolStatus() throws JsonProcessingException;
}
