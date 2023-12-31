
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="chu" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

 <div class="container">
 
 <h2 class="mt-5">Student_list</h2>
<a href="Student_reg.html" class="btn btn-primary my-4">Add student</a>
<a href="#" class="btn btn-danger my-4" style="float:right;">Logout </a>
<!-- Create Table to display list of students -->
<table class="table">
<thead class="table-dark">
<tr>
<th>sid</th>
<th>First Name</th>
<th>Last name</th>
<th>Email</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<chu:forEach var="theStudent" items="${vadas }">
<!-- Delete link -->
<chu:url var="deleteLink" value="StudentController">
<chu:param name="cmd" value="DELETE"/>
<chu:param name="sid" value="${theStudent.sid }"/>

</chu:url>

<!-- Update  link -->
<chu:url var="updateLink" value="StudentController">
<chu:param name="cmd" value="LOAD"/>
<chu:param name="sid" value="${theStudent.sid }"/>

</chu:url>

<tr>
<td>${theStudent.sid } </td>
<td>${theStudent.firstname }</td>
<td>${theStudent.lastname }</td>
<td>${theStudent.email }</td>
<td><a href="${updateLink }" class="btn btn-primary my-3 ">Update</a> 
 <a href="${deleteLink }" class="btn btn-danger" >Delete</a></td>
</tr>
</chu:forEach>
</tbody>

</table>
 
 </div>

</body>
</html>