<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>Register</title>
    <style>
    .error {
        color: #ff0000;
    }

    .errorblock {
        color: #000;
        background-color: #ffEEEE;
        border: 3px solid #ff0000;
        padding: 8px;
        margin: 16px;
    }
    </style>
</head>
<body>
<div class="container">
	<h1>
		Register user
	</h1>
	<div >
		<form:form action="registration" method="post" commandName="userRegistrationForm">
            <form:errors cssClass="errorblock" element="div" path="*" />
		  	<fieldset>
				<legend>User Fields</legend>
				<p>
					<form:label	for="name" path="name" cssErrorClass="error">Name</form:label><br/>
					<form:input path="name" /> <form:errors path="name" />
                    <form:errors path="name" cssClass="error" />
				</p>
				<p>
					<form:label for="email" path="email" cssErrorClass="error">Email</form:label><br/>
					<form:input path="email" /> <form:errors path="email" />
                    <form:errors path="email" cssClass="error" />
				</p>
				<p>
					<form:label for="password" path="password" cssErrorClass="error">Password</form:label><br/>
					<form:password path="password" /> <form:errors path="password" />
                    <form:errors path="password" cssClass="error" />
				</p>
				<p>
					<input type="submit" />
				</p>
			</fieldset>
		</form:form>
	</div>
	<hr>	
</div>
</body>
</html>