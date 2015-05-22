package mailservice

import com.bz.MandrillMailer
import com.bz.SendGridMailer
import com.bz.ThirdPartyMailer
import com.mongodb.BasicDBObject
import grails.transaction.Transactional
import com.bz.MongoSource
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

import java.text.SimpleDateFormat
import java.util.regex.Pattern

@Transactional
class MailService {

    MongoSource mongoSource

    def serviceMethod() {

    }

    List<ThirdPartyMailer> getMailServices() {
        List<ThirdPartyMailer> mailers = new ArrayList<ThirdPartyMailer>()
        //Need a better way to dynamically add them but  until then....
        mailers.add(SendGridMailer.getInstance())
        mailers.add(MandrillMailer.getInstance())

        return mailers
    }

    def sendMail( Mailer params,def email, def templateName) {
        def result = [:]
        MongoTemplate mt = mongoSource.mongoTemplate()
        def apiUserCollection = mt.getCollection("apiUsers")
        def templateCollection = mt.getCollection("templates")
        def apiUser = apiUserCollection.findOne(new BasicDBObject("_id", params.apiKey))?.toMap()
        if (apiUser != null) {
            def templateRecord = templateCollection.findOne(new BasicDBObject("_id": templateName))?.toMap()
            def emailMessage = templateRecord.template.toString()
            params.templateFields.each { def fieldName ->
                def keyMatcher =  Pattern.quote("{" + fieldName + "}")
                def replacementValue = params."${fieldName}"
                emailMessage = emailMessage.replaceAll(keyMatcher, replacementValue)
            }

            for (ThirdPartyMailer mservice in getMailServices()) {
                def res = mservice.sendEmail(email, "bzeitner@gmail.com", templateRecord.subject, emailMessage)
                if (res.success) {
                    //Log Send email
                    result = ['success': res.success, 'service': mservice.getServiceName()]
                    logMailSuccess(mservice.getServiceName())
                    return result
                }
            }
        }
        result = ['success': false, 'Error': 'Unable to send email']
        return result
    }

    def logMailSuccess(String mailService) {
        MongoTemplate mt = mongoSource.mongoTemplate()
        def logCollection = mt.getCollection("mailLog")
        def date = new Date()
        def sdf = new SimpleDateFormat("MM_dd_yyyy")
        def dateString = sdf.format(date)
        //If the exact count is really necessary, I'd actually switch the log to by apiKey instead of by day
        //(or do a synchronized block)
        def dailyLog = logCollection.findOne(new BasicDBObject("_id":dateString ))?.toMap()
        if (dailyLog) {
            int count = 1
            if (dailyLog.services.get(mailService))
            {
                count = dailyLog.services.get(mailService)
                count++
            }
            logCollection.update(new BasicDBObject("_id": dateString), new BasicDBObject('$set', new BasicDBObject("services.${mailService}".toString(), count)))
        } else {
            def logEntry = new Update()
            def services = ["$mailService": 1]
            logEntry.set("services", services)
            logEntry.set("_id", dateString)
            mt.upsert(new Query(new Criteria("_id").is(dateString)), logEntry, "mailLog")
        }

    }
}
