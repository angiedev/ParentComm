<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>Select Your School</title>
   	
   	   <!-- Bootstrap -->
   <link href=<c:url value="/resources/css/bootstrap.min.css" /> rel="stylesheet">

 	<!-- Custom CSS -->    
    <link href=<c:url value="/resources/css/parentcomm.css"/> rel="stylesheet">
 
</head>
<body>
	<div class="container">
	<div class="page-header">Pick your school</div>
	<form action="selectSchool" method="POST" >
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
				
				<a href=<c:url value="/classes?schoolId=${school.getId()}"/> class="btn btn-primary btn-lg button active">
				${school.getName()}
				</a>
			
				</div>
			</c:forEach>
		</div>
	</form>
	</div>
</body>
</html>