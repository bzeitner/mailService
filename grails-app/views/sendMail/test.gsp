<%--
  Created by IntelliJ IDEA.
  User: bzeitner
  Date: 5/20/15
  Time: 1:54 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <style>
    .button {
        font: bold 11px Arial;
        text-decoration: none;
        background-color: #EEEEEE;
        color: #333333;
        padding: 2px 6px 2px 6px;
        border-top: 1px solid #CCCCCC;
        border-right: 1px solid #333333;
        border-bottom: 1px solid #333333;
        border-left: 1px solid #CCCCCC;
    }
    </style>
    <title>Mail Service Test Page</title>
</head>

<body>
<div>

<g:form action="test">
 <div>
    First Name:  <g:textField name="firstName" id="firstName"/>
    <br/>

  Last Name:  <g:textField name="lastName" id="lastName"/>
     <br/>

  Email Address:  <g:textField name="email" id="email"/>
     <br/>
  API Key:  <g:textField name="apiKey" id="apiKey"/>
</div>
    <a  onclick="sendEmail();" class="button">Send Test Email</a>

</g:form>
</div>

<div id="Results"></div>
<div id="Errors"></div>
<g:javascript>

    function getBaseUrl() {
        var re = new RegExp(/^.*\//);
        return re.exec(window.location.href);
    }

    var sendEmail = function () {
        $("#Results").text("");
        $("#Errors").text("");
        var email=$("#email").val();
        var firstName=$("#firstName").val();
        var lastName=$("#lastName").val();
        var apiKey=$("#apiKey").val();
        var args = "{ 'email': '" +email + "', 'firstName': '" + firstName + "', 'lastName': '" +lastName + "', 'apiKey': '" + apiKey + "' }";
        $.ajax({
            url: getBaseUrl()+"/",
            contentType: "application/json; charset=utf-8",
            method: "POST",
            data: args,
            dataType: "json"
        }).done(function(json) {
            $("#Results").text(json.status);
            if (json.errors != null) {
                json.errors.forEach(function(err) {
                    $("#Errors").append("<div>" + err.key + " : " + err.type + " (" + err.rejectedValue + ")</div>");
                });
            }

        });
    }
</g:javascript>

</body>
</html>