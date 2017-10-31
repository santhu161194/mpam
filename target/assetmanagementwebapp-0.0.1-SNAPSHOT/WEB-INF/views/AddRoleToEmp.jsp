<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script  type="text/javascript">
function onSubmit() {
		
		var employeeId = document.getElementById("employeeId").value;
		
		
		var formValid = true;
		var formValid1 = false;
		
		if (IsEmpty(employeeId) == true) {
			
			document.getElementById("employeeId_error").innerHTML = "Please Enter employee id name";	
			formValid = false;
		}
		else if (employeeId.length >= 40) {
			document.getElementById("employeeId_error").innerHTML = "Value should not exceed 10";
			formValid1 = true;
		}
		else if (employeeId.length <= 1) {
			document.getElementById("employeeId_error").innerHTML = "Value should be greater than 1";
			formValid1 = true;
		} 
		else 
			{
			document.getElementById("employeeId_error").innerHTML =null;
			formValid=true;
			formValid1=false;
			}
		
		
		
		if (formValid1) {
			return false;
		}
		if (!formValid) {
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
.red{
color:red;

}
.body .form{
float:left;
clear:both;
}
span {
	color: red;
}
</style>
</head>
<body>
<form action="addRoleToEmp" method="post" onsubmit = "return onSubmit()">

	<div id="id2">
		<h3 align="center">${viewdetails}</h3>
		</div>

<table align="center">
<tr><td>employeeId</td><td><input name="employeeId" id="employeeId" type="text"><span id = "employeeId_error" ></span></td></tr>
<tr>
<td>Select Role:</td>
    <td>
         <select name="roleName" >
         <option value="admin" name="roleName" >Admin</option>
         <option value="edp" name="roleName">EDP</option>
         <option value="employee" name="roleName" checked>Employee</option>
        
       </select>
   </td>
   </tr>

<tr><td>addedBy</td><td><input type="text" readonly name="addedBy" id="addedBy" value="${username}"></td></tr>

<tr><td></td><td><input type="submit" value="submit" onclick = "return onSubmit()"></td><td></td></tr>
</table>

</form>
</body>
</html>