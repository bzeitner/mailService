package com.bz

import com.microtripit.mandrillapp.lutung.MandrillApi
import com.microtripit.mandrillapp.lutung.view.MandrillMessage
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus

/**
 * Created by bzeitner on 5/21/15.
 */
class MandrillMailer implements ThirdPartyMailer {


    static MandrillMailer mailInstance

    static MandrillApi mailApi = new MandrillApi("Your API Key Here")

    static MandrillMailer getInstance() {
        if (!mailInstance) {
            mailInstance = new MandrillMailer()
        }
        return mailInstance
    }
    Map sendEmail(String toEmail, String fromEmail, String emailSubject, String emailMessage)
    {

        MandrillMessage message = new MandrillMessage()
        message.setSubject("Hello World!")
        message.setText(emailMessage)
        message.setAutoText(true);
        message.setFromEmail(fromEmail);
        message.setFromName("Brad Zeitner");
        ArrayList<MandrillMessage.Recipient> recipients = new ArrayList<MandrillMessage.Recipient>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(toEmail);
        recipients.add(recipient);
        message.setTo(recipients);
        message.setPreserveRecipients(true);
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test");//Just in case
        message.setTags(tags);
        MandrillMessageStatus[] messageStatusReports = mailApi.messages().send(message, false);
        println "Messages : ${messageStatusReports}"
        return ["success":true, "code": 200, "message": "messageSent"]


    }


    String getServiceName()
    { return "Mandrill"}
}
