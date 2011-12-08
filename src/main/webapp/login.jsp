<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script language="javascript">
        function forceHttpsOnSubmit(objForm) {
            objForm.action = objForm.action.replace("http:", "https:").replace("localhost:8080","localhost:8443");
        }
    </script>
</head>
<body>
<c:if test="${not empty param.login_error}">
  <p> Your login attempt was not successful, try again.<br/><br/>
    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>. </p>
</c:if>

    <form method="post" action="/j_spring_security_check" onsubmit="forceHttpsOnSubmit(this)">
        <div id="passwordLoginOption" class="form">
            <div class="row">
                <div class="label left">
                    <label for="j_username">login:</label>
                </div>
                <div class="right">
                    <div class="textWrapper">
                        <input type="text" name="j_username" id="j_username"/>
                    </div>
                </div>
                <div class="cl"></div>
            </div>
            <div class="row">
                <div class="label left">
                    <label for="j_password">password:</label>
                </div>
                <div class="right">
                    <div class="textWrapper">
                        <input type="password" name="j_password" id="j_password"/>
                    </div>
                </div>
                <div class="cl"></div>
            </div>
            <div class="row">
                <div class="right">
                    <label class="forCheckbox" for='_spring_security_remember_me'>
                        Remember me:
                        <input type='checkbox' name='_spring_security_remember_me' id='_spring_security_remember_me'/>
                    </label>
                </div>
                <div class="cl"></div>
            </div>
            <div class="buttons">
                <input type="submit" value="Login"/>
            </div>
        </div>
    </form>
    <form action="j_spring_openid_security_check" method="post" >
        <legend>OpenId</legend>
        <input name="openid_identifier" maxlength="100" type="text" />
        <input type="submit" value="Send"/>
    </form>
    <form action="j_spring_openid_security_check" method="post">
     <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id"/>
     <input type="image" src="/img/social/google.png" />
    </form>
</body>
</html>