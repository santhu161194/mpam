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

		var serialNumber = document.getElementById("serialNumber").value;
		var assetName = document.getElementById("assetName").value;
		var cost = document.getElementById("cost").value;
       
		var formValid = true;
		var formValid1 = false;

		if (IsEmpty(serialNumber) == true) {
			document.getElementById("name_error").innerHTML = "Please Enter serial number";
			formValid = false;
		} else {
			document.getElementById("name_error").innerHTML = null;
			formValid = true;
		}
		if (IsEmpty(assetName) == true) {
			document.getElementById("assetName_error").innerHTML = "Please Enter Asset name";
			formValid = false;
		}
		
		else{
			document.getElementById("assetName_error").innerHTML = null;	
			formValid = true;
			formValid1=false;
		}
		if (IsEmpty(cost) == true) {
			document.getElementById("cost_error").innerHTML = "Please Enter cost";
			formValid = false;		
		}
		else if (cost<= 100) {
			document.getElementById("cost_error").innerHTML = "Cost should be greater 100";
			formValid1 = true;
			formValid=false;
		}
		else if (cost >= 1000000) {
			document.getElementById("cost_error").innerHTML = "Cost should not exceed 10000000";
			formValid1 = true;
			formValid=false;
			
		} 
		else{
			document.getElementById("cost_error").innerHTML = null;	
			formValid = true;
			formValid1=false;
		}

		if (formValid1) {
			return false;
		}
		if (!formValid) {
			return false;
		}
		
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

	<s:form commandName="asset" action="UpdateAsset" method="post" 
		>
		<div id="id2">
			<h3 align="center">${viewdetails}</h3>

		</div>

		<table align="center">
			<tr>
				<td>AssetId</td>
				<td><s:input path="assetId" cssClass="form" readonly="true" required="true"/></td>
			</tr>
			<tr>
				<td>SerialNumber</td>
				<td><s:input path="serialNumber" cssClass="form"
						id="serialNumber" required="true"/><span id="name_error" ></span></td>
			</tr>
			<tr>
				<td>AssetName</td>
				<td><s:input path="assetName" cssClass="form" id="assetName" required="true"/><span
					id="assetName_error"></span></td>
			</tr>
			<tr>
				<td>AssetType</td>
				<td><s:input path="assetType" cssClass="form" id="assetType" required="true" readonly="true"/></td>
			</tr>

			<tr>
				<td>cost</td>
				<td><s:input path="cost" cssClass="form" id="cost" required="true"/><span
					id="cost_error"></span></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" value="update" onclick="return onSubmit();"></td>
				<td></td>
			</tr>

		</table>
	</s:form>

</body>
</html>