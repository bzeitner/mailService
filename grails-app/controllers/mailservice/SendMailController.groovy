package mailservice

import grails.converters.JSON
import grails.rest.RestfulController

class SendMailController extends RestfulController {

    def mailService


    def index() {
        def errors =[]
        def model = [:]

        Mailer mail = new Mailer(request.JSON)
        //Using grails-spring validation framework
        if (!mail.validate()) {
            mail.errors.fieldErrors?.each {
                it.each { def err ->
                    errors.add(["type" : 'Invalid Field Value', "key": err.field, "rejectedValue": err.rejectedValue ])
                }
            }
            model.status="Error"
        } else {
            try {
                def emailResult = mailService.sendMail(mail, params.email?:request.JSON.email, "birthday" )
                println "er = $emailResult  : ${emailResult.success}"
                if (emailResult.success) {
                    model.status="Success"
                } else {
                    model.status = "Error"
                    errors.add(["type":emailResult.type, "key" : "Server", "rejectedValue":""])
                }
            } catch (Exception ex) {
                model.status = "Error"
                errors.add(["type": 'Server Error', "key":"Server", "rejectedValue":""])
                ex.printStackTrace()
            }
        }
        model.errors = errors

        render model as JSON
    }

    def test() {}


}
