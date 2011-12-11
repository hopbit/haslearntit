<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
    <c:if test="${not empty param.login_error}">
      <p> Your login attempt was not successful, try again.<br/><br/>
        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>. </p>
    </c:if>
    <form action="j_spring_openid_security_check" method="post" >
        <legend>OpenId</legend>
        <input name="openid_identifier" maxlength="100" type="text" />
        <input type="submit" value="Send"/>
    </form>
    <form action="j_spring_openid_security_check" method="post">
     <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id"/>
     <input type="image" src="/resources/img/social/google.png" />
    </form>
</body>
</html>