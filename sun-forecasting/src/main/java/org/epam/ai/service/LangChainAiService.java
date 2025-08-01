package org.epam.ai.service;

import org.springframework.stereotype.Service;

// This is a stub. Replace with actual LangChain4j integration.
@Service
public class LangChainAiService {

    public String generateExplanation(String city, String sunrise, String sunset) {
        // In real implementation, call LangChain4j here.
        return String.format("In %s, the sun will rise tomorrow at %s and set at %s IST — a perfect time to enjoy the golden hour!", city, sunrise, sunset);
    }
}

