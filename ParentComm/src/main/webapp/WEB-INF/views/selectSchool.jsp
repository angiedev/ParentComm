<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>Pick Your School</title>
   	
   	<!-- Bootstrap Core CSS -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/parentcomm.css" rel="stylesheet">
<style>
.schools {

}
</style>
</head>
<body>
<div class="container">
<div class="page-header">Pick your school</div>
<sf:form modelAttribute="selectSchoolForm" action="selectSchool" method="POST" >

<div class="row">
<div class="col-xs-3 school-filter">
<button type="submit" name="filterType" class="btn btn-info" value="ELEMENTARY">            
Elementary <br> (K-5)
</button>      
</div>
<div class="col-xs-3 school-filter">
<button type="submit" name="filterType" class="btn btn-info" value="INTERMEDIATE">
Intermediate <br> (6-8)
</button>  
</div>
<div class="col-xs-3 school-filter">
<button type="submit" name="filterType" class="btn btn-info" value="HIGH">   
High <br>(9-12)
</button>
</div>
<div class="col-xs-3 school-filter">
<button type="submit" name="filterType" class="btn btn-info"  value="ALL">   
All <br>
Schools
</button>  
</div>
</div>

<div class="row">
<c:forEach var="school" items="${schools}">
	<div class="col-lg-3 col-md-4 col-sm-6 school-select">
	<button type="submit" class="btn btn-primary btn-lg school-select" >
     <h5>${school.getName()}</h5>
	</button>
	</div>
</c:forEach>
</div>
</sf:form>
</div>
</body>
</html>