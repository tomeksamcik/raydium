package pro.samcik.raydium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.samcik.raydium.service.NotificationService;

import java.util.Map;

@Service
@Qualifier("pushNotificationService")
public class PushNotificationServiceImpl implements NotificationService {

    private final String NTFY_API_URL = "https://ntfy.sh/{topic}";

    @Value("${config.topic}")
    private String topic;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendNotification(String message) {
        restTemplate.postForEntity(NTFY_API_URL, message, String.class, Map.of("topic", topic));
    }
}
