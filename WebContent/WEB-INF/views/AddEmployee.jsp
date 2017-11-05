<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
.red {
	color: red;
}

.body .form {
	float: left;
	clear: both;
}

th, td {
	text-align: left;
	padding: 8px;
}

span {
	color: red;
}
</style>
<script type="text/javascript">
	function onSubmit() {

		var employeeId = document.getElementById("employeeId").value;

		var firstName = document.getElementById("firstName").value;

		var lastName = document.getElementById("lastName").value;

		var password = document.getElementById("password").value;

		var mobileNumber = document.getElementById("mobileNumber").value;

		var dateOfBirth = document.getElementById("dateOfBirth").value;

		var dateOfJoin = document.getElementById("dateOfJoin").value;

		var address = document.getElementById("address").value;

		var formValid = true;

		var formValid1 = false;

		if (IsEmpty(employeeId) == true) {

			document.getElementById("id_error").innerHTML = "Please Enter employee id name";

			formValid = false;

		}
		else if (employeeId.length >= 10)

		{

			document.getElementById("id_error").innerHTML = "Value should not exceed 10";

			formValid1 = true;

		} else if (employeeId.length <= 3)

		{

			document.getElementById("id_error").innerHTML = "Value should be greater than 3";

			formValid1 = true;

		}
		else

		{
			document.getElementById("id_error").innerHTML = null;

			formValid = true;
			formValid1=false;
		}

		if (IsEmpty(firstName) == true)

		{

			document.getElementById("name_error").innerHTML = "Please Enter first name";

			formValid = false;

		} else if (firstName.length >= 40)

		{

			document.getElementById("name_error").innerHTML = "Value should not exceed 40";

			formValid1 = true;

		} else if (firstName.length <= 1)

		{

			document.getElementById("name_error").innerHTML = "Value should be greater than 1";

			formValid1 = true;

		}

		else

		{

			document.getElementById("name_error").innerHTML = null;

			formValid = true;

		}

		if (IsEmpty(lastName) == true)

		{

			document.getElementById("lname_error").innerHTML = "Please Enter last name";

			formValid = false;

		}

		else if (lastName.length >= 40)

		{

			document.getElementById("lname_error").innerHTML = "Value should not exceed 40";

			flag2 = true;

		}

		else if (lastName.length <= 2)

		{

			document.getElementById("lname_error").innerHTML = "Value should be greater than 2";

			formValid1 = true;

		}

		else

		{

			document.getElementById("lname_error").innerHTML = null;

			formValid = true;

		}

		if (IsEmpty(password) == true) {

			document.getElementById("pass_error").innerHTML = "Please Enter last name";

			formValid = false;

		}

		else if (password.length >= 40)

		{

			document.getElementById("pass_error").innerHTML = "Value should not exceed 40";

			formValid1 = true;

		} else if (password.length <= 5)

		{

			document.getElementById("pass_error").innerHTML = "Value should be greater than 5";

			formValid1 = true;

		}

		else

		{

			document.getElementById("pass_error").innerHTML = null;

			formValid = true;

		}

		if (IsEmpty(mobileNumber) == true)

		{

			document.getElementById("mobile_error").innerHTML = "Please Enter mobile number";

			formValid = false;

		} else if (mobileNumber.length > 10)

		{

			document.getElementById("mobile_error").innerHTML = "Mobile number should not be more than 10 digits";

			formValid1 = true;

		}

		else if (mobileNumber.length < 10)

		{

			document.getElementById("mobile_error").innerHTML = "Mobile number should contain 10 digits";

			formValid1 = true;

		} else

		{

			document.getElementById("mobile_error").innerHTML = null;

			formValid = true;

		}
		if (IsEmpty(dateOfBirth) == true)

		{

			document.getElementById("date_error").innerHTML = "Please Enter Date of birth";

			formValid = false;

		}

		else

		{

			document.getElementById("date_error").innerHTML = null;

			formValid = true;
		}

		if (IsEmpty(dateOfJoin) == true)

		{

			document.getElementById("dateJoin_error").innerHTML = "Please Enter Joined date";

			formValid = false;

		} else

		{

			document.getElementById("dateJoin_error").innerHTML = null;

			formValid = true;

		}

		if (IsEmpty(address) == true)

		{

			document.getElementById("address_error").innerHTML = "Please Enter address";

			formValid = false;

		}

		else if (address.length >= 255)

		{

			document.getElementById("address_error").innerHTML = "Address should not exceed 255";

			formValid1 = true;

		}

		else if (address.length < 6)

		{

			document.getElementById("address_error").innerHTML = "Address should be greater than 6";

			formValid1 = true;
		}

		else

		{

			document.getElementById("address_error").innerHTML = null;

			formValid = true;

		}

		if (formValid1)

		{

			return false;

		}
		if (!formValid)

		{

			return false;

		}

		return true;

	}

	function IsEmpty(input)

	{

		if (input.replace(/^\s+|\s+$/g, "") === "")

		{

			return true;

		}

	}
	
	
	  $(function() {
		     $( "#dateOfBirth").datepicker({ dateFormat: 'yy-m-dd' });
		     $( "#dateOfJoin").datepicker({ dateFormat: 'yy-m-dd' });
		    
		  });

</script>

</head>

<body>

	<s:form commandName="employee" action="addEmployee" method="post"
		onsubmit="return onSubmit()">

		<h2 id="id1" align="center">EMPLOYEE DATA</h2>

		<div id="id2">

			<h3 align="center"></h3>

		</div>

		<table align="center">

			<tr>
				<td>EmployeeId</td>
				<td><s:input path="employeeId" cssClass="form" id="employeeId" /><span
					id="id_error"></span></td>
			</tr>

			<tr>
				<td>Firstname</td>
				<td><s:input path="firstName" cssClass="form" id="firstName" pattern="[A-Za-z]+" /><span
					id="name_error"></span></td>
			</tr>

			<tr>
				<td>LastName</td>
				<td><s:input path="lastName" cssClass="form" id="lastName" /><span
					id="lname_error"></span></td>
			</tr>
			
			<tr>
				<td>Enter Password</td>
				<td><s:input path="password" cssClass="form" id="password" type="password"/><span
					id="pass_error"></span></td>
			</tr>

			<tr>
				<td>Select Gender</td>
				<td><input type="radio" name="gender" value="MALE" checked>Male
					<input type="radio" name="gender" value="FEMALE">Female</td>
			</tr>

			<tr>
				<td>Enter MobileNumber</td>
				<td><s:input path="mobileNumber" cssClass="form"
						id="mobileNumber" pattern="[789][0-9]{9}" title="Phone number with 7-9 and remaing 9 digit with 0-9" required="true"/><span id="mobile_error"></span></td>
			</tr>

			<tr>
				<td>Enter DateOfBirth</td>
				<td><s:input path="dateOfBirth" cssClass="form"
						id="dateOfBirth" placeholder="YYYY-MM-DD"/><span id="date_error"></span></td>
						
			</tr>

			<tr>
				<td>Enter DateOfJoining</td>
				<td><s:input path="dateOfJoin" cssClass="form"
						id="dateOfJoin" placeholder="YYYY-MM-DD" /><span id="dateJoin_error"></span></td>
			</tr>

			<tr>
				<td>Enter Address</td>
				<td><s:textarea path="address" cssClass="form" id="address" /><span
					id="address_error" ></span></td>
			</tr>
			<%
				session.setAttribute("userid",
							(String) pageContext.getAttribute("userid"));
			%>
			
			<tr>
				<td></td>
			
				<td><input type="submit" value="Submit" onclick="return onSubmit()"></td>
			</tr>

			<tr>
				<td></td>
			</tr>

		</table>

	</s:form>

</body>

</html>