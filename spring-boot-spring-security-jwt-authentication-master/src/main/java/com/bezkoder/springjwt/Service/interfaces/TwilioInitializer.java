package com.bezkoder.springjwt.Service.interfaces;

import com.twilio.Twilio;

public class TwilioInitializer {
    public static void initializeTwilio(String accountSid, String authToken) {
        // Initialiser Twilio avec les valeurs SID et token passées en arguments
        Twilio.init(accountSid, authToken);
    }
}
