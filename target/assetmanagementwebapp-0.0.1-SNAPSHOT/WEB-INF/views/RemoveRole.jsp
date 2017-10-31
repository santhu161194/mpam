<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function onSubmit() {

		var employeeId = document.getElementById("employeeId").value;
		

		var validForm = true;
		var validForm1 = false;

		if (IsEmpty(employeeId) == true) {
			document.getElementById("id_error").innerHTML = "Please Enter employee id";

			validForm = false;
		} else {
			document.getElementById("id_error").innerHTML = null;
			validForm = true;
		}
		

		if (validForm1) {
			return false;
		}
		if (!validForm) {
			return false;
		}

		return true;

	}
	function IsEmpty(input) {
		if (input.replace(/^\s+|\s+$/g, "") === "") {
			return true;
		}

	}
</script>

<style type="text/css">
.red {
	color: red;
}

.body .form {
	float: left;
	clear: both;
}

span {
	color: red;
}
</style>
</head>
<body>
	<s:form commandName="employee" action="removeRole" method="post"
		onsubmit="return onSubmit()">


		<div id="id2">
			<h3 align="center">${viewdetails}</h3>
		</div>

		<table align="center">
			<tr>
				<td>employeeId</td>
				<td><input type="text" name="employeeId" id="employeeId"><span
					id="id_error"></span></td>
			</tr>
			<td>Select Role:</td>
    <td>
         <select name="roleName" >
         <option value="admin" name="roleName" >Admin</option>
         <option value="edp" name="roleName">EDP</option>
         
        
       </select>
   </td>
			<tr>
				<td>removedBy</td>
				<td><input type="text" name="removedBy" id="removedBy" readonly="true"
					value="${username}"></td>
			</tr>


			<tr>
				<td></td>
				<td><input type="submit" value="submit" onclick="return onSubmit()"></td>
				<td></td>
			</tr>
		</table>
	</s:form>
</body>
</html>