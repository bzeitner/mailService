package com.bz

/**
 * Created by bzeitner on 5/21/15.
 */
interface ThirdPartyMailer {

    //TODO add method to check if available - in order to keep within limits

    Map sendEmail(String toEmail, String fromEmail, String emailSubject, String emailMessage)

    String getServiceName()
}
