<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 
<script>
  $(function() { // when DOM is ready
	    $(".showhide").click(function(){ 
	    	var toLoad=$(this).attr('id');// when #showhidecomment is clicked
	    	
	        $("#content").load(toLoad); // load the sample.jsp page in the #chkcomments element
	    }); 
	});

</script> 

  
<title>Welcome Home</title>
<style type="text/css">
<%@include file="css/common.css"%>
</style>

<style>
div.ex1 {
    width:500px;
    margin: auto;
    border: 3px solid #73AD21;
}
</style>

</head>

<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="home">Asset Management</a>
    </div>
    
     <ul class="nav navbar-nav">

      
    </ul>
    <ul class="nav navbar-nav navbar-right">
			<li><a href="#" data-placement="bottom" data-toggle="tooltip"
				href="#" data-original-title="Stats"><i
					class="fa fa-bar-chart-o"></i> </a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>${username} <b class="fa fa-angle-down"></b></a>
				<ul class="dropdown-menu">
					<!-- <li><a href="#"><i class="fa fa-fw fa-user"></i> My
							Profile</a></li> -->
				   <li><a class="showhide" id="empassets"><i class="fa fa-fw fa-cog"></i> My
							Assets</a></li>
							
					<li><a class="showhide" id="emprequest"><i class="fa fa-fw fa-cog"></i> My
							Requests</a></li>
					<li><a class="showhide" id="changePassword"><i class="fa fa-fw fa-cog"></i> Change
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
  
  <li><a id="postAssetRequests" class="showhide">Request Asset</a></li><br>
   <%if(request.getSession(false).getAttribute("role").equals("admin")){ %>
   <li><a id="ViewAssetRequests" class="showhide">Requests</a></li><br>
 <li><a class="showhide" id="viewAssets?role=admin">Assets</a></li><br>   
   <%} %>
  </ul>
  </nav>
  </div>
  
	<!-- the content is shown here -->
	
	
	<div id="content">
		<h4>${message}</h4>	
	     
	</div>
  

 </body>
</html>