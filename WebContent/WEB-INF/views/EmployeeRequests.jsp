<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
  $(function() { // when DOM is ready
        $(".showhide").click(function(){
            var toLoad=$(this).attr('id');// when #showhidecomment is clicked
            
            $("#content").load(toLoad); // load the sample.jsp page in the #chkcomments element
        });
    });
  </script>
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
</head>
<body>
    


    <div id="content">
        <h3 align="center">${viewdetails}</h3>
        </div>
    <h4><j:out value="${updatestatus}"></j:out></h4>
    <input class="form-control" id="myInput" type="text" placeholder="Search Request here....">
    <div id="div1">
    <table border="2">
          <table id="table" border="2" margin: 10px; >
        <tr id="th">
          <!--   <th id="th"> Employee ID -->
            <th id="th">Asset-Type
            <th id="th">Requested-Date  
            <th id="th">Status
            <th id="th">Remark 
        </tr>
        <tbody id="myTable">
        <j:forEach var="requestList" items="${requestList}">
            <tr id="th">
<%--                 <td id="th"><j:out value="${requestList.employeeId} "></j:out></td>
 --%>                <td id="th"><j:out value="${requestList.assetType} "></j:out></td>
                <td id="th"><j:out value="${requestList.requestDate} "></j:out></td>
                <td id="th"><j:out value="${requestList.status} "></j:out></td>
                
                <%-- <td><j:out value="${asss.createdDate} "></j:out></td>
                <td><j:out value="${asss.createdBy} "></j:out></td>
                <td><j:out value="${asss.modifiedBy} "></j:out></td>
                <td><a href=postAssetRequests?type=<j:out value="${asss.assetType}"></j:out>>Asset Request</a></td>
                <td><a href="UpdateAsset?code=<j:out value="${asss.assetId}"></j:out>">Update</a></td>--%>
                </tr>
                </j:forEach>
                </tbody>
                </table>
                </div>
                <div id="div2">
                <div id="id2">
			<hr>
		<h3 align="center">New Type Of Asset Requests</h3>
		</div><!--2 tbl  -->
			
			<div style="overflow:scroll;height:200px;width:100%;overflow:auto">
	<table border="2">
		<tr>
			
			
			<th>assetType
			
			<th>assetName
			
			<th>Request Date
			
			<th>Remark
			
		</tr>
		  <tbody id="myTable">
		<j:forEach var="newAssetReq" items="${newAssetRequests}">
			<tr>
				<td><j:out value="${newAssetReq.assetType} "></j:out></td>
				<td><j:out value="${newAssetReq.assetName} "></j:out></td>
				<td><j:out value="${newAssetReq.requestDate} "></j:out></td>
				<td></td>
				
				</tr>
				</j:forEach>
				</tbody>
				</table>
				
			</div>
                
                </div>

</div>
            
            
                </body>
                </html>