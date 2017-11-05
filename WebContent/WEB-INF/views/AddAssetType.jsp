<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


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
	<s:form commandName="asset" action="addAssetType" method="post">

		<div id="id2">
			<h3 align="center">${viewdetails}</h3>
		</div>

		<table align="center">
			
			<tr>
				<td>AssetTypeName</td>
				<td><input type="text" name="AssetTypeName" id="AssetTypeName" required="true">
	</td>
			</tr>
			

			<tr>
				<td></td>
				<td><input type="submit" value="submit"></td>
				<td></td>
			</tr>
		</table>

	</s:form>
</body>
</html>