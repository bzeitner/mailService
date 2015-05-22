package com.bz

import com.sendgrid.SendGrid

/**
 * Created by bzeitner on 5/21/15.
 */
class SendGridMailer implements ThirdPartyMailer{

    static SendGridMailer mailInstance

    static SendGrid sendGrid = new SendGrid("Your Api Key Here")

    static SendGridMailer getInstance() {
        if (!mailInstance) {
            mailInstance = new SendGridMailer()
        }
        return mailInstance
    }

    Map sendEmail(String toEmail, String fromEmail, String emailSubject, String emailMessage) {
//        return ["success":false, "code":404, "message": "Unable to find service"]
        SendGrid.Email email = new SendGrid.Email()
        email.addTo(toEmail);
        email.setFrom(fromEmail);
        email.setSubject(emailSubject);
        email.setText(emailMessage);
        SendGrid.Response response = sendGrid.send(email);
        println "Response = $response"
        println "success  ${response.getStatus()}, code: ${response.getCode()}, message: ${response.getMessage()}"
        return ["success":response.getStatus(), "code": response.getCode(), "message": response.getMessage()]
    }

    String getServiceName() {
        return "SendGrid"
    }
}
