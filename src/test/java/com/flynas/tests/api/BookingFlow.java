package com.flynas.tests.api;

import com.flynas.api.base.APIBase;
import org.testng.annotations.Test;

public class BookingFlow extends APIBase {
    @Test
    public void guestBookingFlow(){
       String token= getSessionToken();
       System.out.println(token);
    }
}
