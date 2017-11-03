<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
  <script>
  $(function() { // when DOM is ready
	    $(".showhide").click(function(){ 
	    	var toLoad=$(this).attr('id');// when #showhidecomment is clicked
	    	
	        $("#content").load(toLoad); // load the sample.jsp page in the #chkcomments element
	    }); 
	});

  $(function() {
	     $( "#dateOfBirth").datepicker();
	     
	  });
  </script>
<title>Welcome Home</title>
<style type="text/css">
<%@include file="css/common.css"%>
</style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="home">Asset Management</a>
    </div>
    
     <ul class="nav navbar-nav">

     
      <li class="active"><a href="#">AdminHome</a></li>


      
    </ul>
    <ul class="nav navbar-nav navbar-right">
			<li><a href="#" data-placement="bottom" data-toggle="tooltip"
				href="#" data-original-title="Stats"><i
					class="fa fa-bar-chart-o"></i> </a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>${username} <b class="fa fa-angle-down"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#"><i class="fa fa-fw fa-user"></i> Edit
							Profile</a></li>
					<li><a href="#"><i class="fa fa-fw fa-cog"></i> Change
							Password</a></li>
					<li class="divider"></li>
					<li><a href="invalidate"><i class="fa fa-fw fa-power-off"></i>
							Logout</a></li>
				</ul></li>
		</ul>
  
  </div>
  </nav>
  <div class="wrapper">

        <nav id="sidebar">

                
          
 <ul class="list-unstyled components">
  
  <li><a href="EDPHome">Requests</a></li><br>
   <li><a href="#">Assets</a></li><br>
   
  </ul>
  </nav>
  </div>
  
	<!-- the content is shown here -->
	
	
	<div id="content">
		<h3 align="center">${viewdetails}</h3>
		
	<h4><j:out value="${updatestatus}"></j:out></h4>
	<input class="form-control" id="myInput" type="text" placeholder="Search Request here....">
	<div style="overflow:scroll;height:400px;width:100%;overflow:auto">
	<table border="2">
		<tr>
			<th> assetId
			<th>serialNumber
			<th>assetName
			<th>assetType
			<th>cost
			<th> status
		
			
		</tr>
		<tbody id="myTable">
		<j:forEach var="asset" items="${assets}">
			<tr>
				<td><j:out value="${asset.assetId} "></j:out></td>
				<td><j:out value="${asset.serialNumber} "></j:out></td>
				<td><j:out value="${asset.assetName} "></j:out></td>
				<td><j:out value="${asset.assetType} "></j:out></td>
				<td><j:out value="${asset.cost} "></j:out></td>
				<td><j:out value="${asset.status} "></j:out></td>
								
				<j:if test="${viewdetails eq 'Available Assets'}">
				<td><a class="showhide" id="allocateAsset?assetID=<j:out value="${asset.assetId}"></j:out>">Allocate Asset</a></td>
				</j:if>
				<j:if test="${viewdetails eq 'Allocated Assets'}">
				<td><a class="showhide" id="deallocateAsset?assetID=<j:out value="${asset.assetId}"></j:out>">DeAllocate Asset</a></td>
				</j:if>
				<j:if test="${viewdetails eq 'All Assets'}">
				<j:if test="${requestrole eq 'admin'}">
				<td><a class="showhide" id="UpdateAsset?assetID=<j:out value="${asset.assetId}"></j:out>">Update</a>
				</j:if>
				</j:if>
				</tr>
				</j:forEach>
				</tbody>
				</table>
				</div>
			
			</div>
				</body>
  

</html>