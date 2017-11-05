<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

<script>
  $(document).ready(function(){
	$("#count").change(function(){
      $("#assetmodal").hide();
      if (this.value == 'new')
      {
    	  $("#submit").hide();
           $("#assetmodal").show();
           
      }
      else
    	  {
      $("#submit").show();
    	  }
   });
  });
  $(function() {
	    // setTimeout() function will be fired after page is loaded
	    // it will wait for 5 sec. and then will fire
	    // $("#successMessage").hide() function
	    setTimeout(function() {
	        $("#Message").hide('blind', {}, 500)
	    }, 5000);
	});

  		
</script>

<title>Insert title here</title>
<style type="text/css">
div.ex1 {
    width:500px;
    margin: auto;
    border: 3px solid #73AD21;
}
</style>
</head>
<body>

<div id="content">
	
    <h5 id="Message" >${message}</h5>

    <form id="assetform1" name="assetform1" method="post" action="postAssetRequests">

<table>
<tr>
<td>
Select Type:
</td>
<td>
<select class="selectpicker form-control" id="count" name="type">
<j:forEach var="type" items="${types}">
     <option value="${type}">${type}</option></j:forEach>
     <!-- <option value="Laptop">Laptop</option>
     <option value="Mouse">Mouse</option>
      <option value="Keyboard">Keyboard </option>-->
      <option value="new">Other type </option> 
</select>
</td>
</tr>
<tr>
<td><label for="remark">Remark:</label></td>
    <td>  <input type="text" name="remark" id="remark" class="txtfield" tabindex="1"></td></tr>
<tr>
<td colspan="2"><input type="submit" value="Request" id="postAssetRequests" class="showhide">
</td>
<tr>
</table>
</form>
<div id="assetmodal" style="display:none" align="center" >
    <h1 align="center">Fill Asset Form</h1>
    <form id="assetform" name="assetform" method="post" action="assetrequest">
    
      <label for="assetName">Assetname:</label>
      <input type="text" name="assetName" id="assetName" class="txtfield" tabindex="1">
     <br>
      <label for="assetType">Asset Type:</label>
      <input  name="assetType" id="assetType" class="txtfield" tabindex="2">
      <br>
      <label for="remarks">Remarks:</label>
      <input  name="remarks" id="remarks" class="txtfield" tabindex="2">
      
 <div class="center"><input type="submit" name="loginbtn" id="assetrequest" class="flatbtn-blu hidemodal" class="showhide" value="Request" tabindex="3" align="center"></div>
    
  </div>
 </form>
     </div>
</body>
</html>