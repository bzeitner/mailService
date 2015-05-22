package mailservice

class Mailer {

    String email
    String firstName
    String lastName
    String apiKey

    List getTemplateFields() {
        return ['email', 'firstName', 'lastName']
    }

    static constraints = {
        email email: true, blank: false
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        apiKey blank: false, nullable: false, matches: "[a-zA-Z0-9]+"
    }
}
