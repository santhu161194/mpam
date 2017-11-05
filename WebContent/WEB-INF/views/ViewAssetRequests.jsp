<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<title>vi</title>
<style>
table{
width:100%;
}
th, td {
    border-bottom: 1px solid #ddd;
    padding: 8px;
    text-align: center;
    vertical-align: center;
}
tr:nth-child(even) {background-color: #f2f2f2}
th {
    background-color: black;
    color: white;
}
</style>
<script>
$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>

<script>
  $(function() { // when DOM is ready
	    $(".showhide").click(function(){ 
	    	var toLoad=$(this).attr('id');// when #showhidecomment is clicked
	    	
	        $("#content").load(toLoad); // load the sample.jsp page in the #chkcomments element
	    }); 
	});
  </script>
  <script>
function fu(u,t) {
	$("#id").val(t);

	$("#id1").val(u);
	$("#empId").val(u);
	$("#type").val(t);
	
}
</script>
</head>
<body>
<div id="content" style="width:100%">
	<div id="id2">
		<h3 align="center">Asset Requests</h3>
		</div>
	<h4><j:out value="${message}"></j:out></h4>
		<input class="form-control" id="myInput" type="text" placeholder="Search Request here....">
	
	<div style="overflow:scroll;height:180px;width:100%;overflow:auto">
	<table border="2">
		<tr>
			<th> RequestedBy
			<th>EmployeeName
			<th>AssetType
			
			<th>RequestDate
			<th>Status
			<th colspan="2">Actions
			
			
		</tr>
		  <tbody id="myTable">
		<j:forEach var="assetReq" items="${assetRequests}">
			<tr>
				<td><j:out value="${assetReq.employeeId} "></j:out></td>
				<td><j:out value="${assetReq.employeeName} "></j:out></td>
				<td><j:out value="${assetReq.assetType} "></j:out></td>
				<td><j:out value="${assetReq.requestDate} "></j:out></td>
				<td><j:out value="${assetReq.status} "></j:out></td>
			<j:if test="${assetReq.status eq 'Requested'}">
				<td><a  class="showhide" id="viewAssetsByType?type=<j:out value="${assetReq.assetType}"></j:out>">Allocate</a></td>
                <td> <a type="button"  data-toggle="modal" data-target="#myModal" data-first_name="fname" data-last_name="lname" class="confirmDelete" onclick="fu('${assetReq.employeeId}','${assetReq.assetType}')">Reject </a></td>
                
              </j:if>
           <j:if test="${assetReq.status ne 'Requested'}">
           <td></td>
           <td></td>
           </j:if>
              
             
              
<%-- 				<td><a   class="showhide" id="removeAssetRequest?employeeId=<j:out value="${assetReq.employeeId}"></j:out>&type=<j:out value="${assetReq.assetType} "></j:out>">Reject</a></td>
 --%>		 
				</tr>
				</j:forEach>
				</tbody>
				</table>
				
			</div>
			
			<div id="id2">
			<hr>
		<h3 align="center">New Type Of Asset Requests</h3>
		</div><!--2 tbl  -->
			
			<div style="overflow:scroll;height:200px;width:100%;overflow:auto">
	<table border="2">
		<tr>
			<th> EmployeeId
			
			<th>AssetType
			
			<th>AssetName
			
			<th>Request Date
			<th>Actions
			
		</tr>
		  <tbody id="myTable">
		<j:forEach var="newAssetReq" items="${newAssetRequests}">
			<tr>
				<td><j:out value="${newAssetReq.employeeId} "></j:out></td>
				<td><j:out value="${newAssetReq.assetType} "></j:out></td>
				<td><j:out value="${newAssetReq.assetName} "></j:out></td>
				<td><j:out value="${newAssetReq.requestDate} "></j:out></td>
				<td> <a type="button"  data-toggle="modal" data-target="#myModal2" data-first_name="fname" data-last_name="lname" class="confirmDelete" onclick="fu('${newAssetReq.employeeId}','${newAssetReq.assetType}')">Remark </a></td>
				</tr>
				</j:forEach>
				</tbody>
				</table>
				
			</div>
			
			</div>
			<!-- <form id="assetform" name="assetform" method="post" action="assetrequest">
	 -->
	        <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
  <form id="assetform" name="assetform" method="get" action="reject-request">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Reason</h4>
        </div>
        <div class="modal-body">
        <p>"Enter Reason For Request Rejection.. "</p>
        <input id="id1" name="empId"/>
        <input id="id" name="type"/>
         <textarea rows="4" cols="50" name="reason">
          
          </textarea>
        </div>
        <div class="modal-footer">
           <button type="submit" value="Submit" class="btn btn-default" >Submit</button>
        </div>
      </div>
      
    </div>
    </form>
  </div>
  
  
  
       <!-- Modal 2-->
  <div class="modal fade" id="myModal2" role="dialog">
  <form id="assetform" name="assetform" method="get" action="reject-remark">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">REMARK</h4>
        </div>
        <div class="modal-body">
        <p>"Enter Remark.. "</p>
        <input id="empId" name="empId"/>
        <input id="type" name="type"/>
         <textarea rows="4" cols="50" name="reason">
          
          </textarea>
        </div>
        <div class="modal-footer">
           <button type="submit" value="Submit" class="btn btn-default" >Submit</button>
        </div>
      </div>
      
    </div>
    </form>
  </div>
	 
	 
				</body>
				</html>
				
	