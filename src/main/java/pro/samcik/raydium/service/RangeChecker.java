package pro.samcik.raydium.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RangeChecker {

    void checkRange() throws JsonProcessingException;

    void checkPoolStatus() throws JsonProcessingException;
}
