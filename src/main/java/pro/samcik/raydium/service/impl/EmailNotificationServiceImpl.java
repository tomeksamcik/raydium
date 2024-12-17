package pro.samcik.raydium.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.samcik.raydium.service.NotificationService;

@Service
@Qualifier("emailNotificationService")
public class EmailNotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String message) {

    }
}
