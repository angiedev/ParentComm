<%@page language="java" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>Find Your School</title>
   	
   	   <!-- Bootstrap -->
   <link href=<c:url value="/resources/css/bootstrap.min.css" /> rel="stylesheet">

 	<!-- Custom CSS -->    
    <link href=<c:url value="/resources/css/parentcomm.css"/> rel="stylesheet">
 
</head>
<body>
	<div class="container">
	<div class="page-header">Find your school</div>
	<form action="selectSchool" method="POST" >
		<div class="row">
			<div class="col-xs-4 filter">
				<c:choose>
				    <c:when test='${filterType == "ELEMENTARY"}'> 
				    	<c:set var="btnColorClass" value="btn-success"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="btnColorClass" value="btn-info"/>
				    </c:otherwise>
			    </c:choose>
			    
				<button type="submit" name="filterType" class="btn ${btnColorClass} btn-med btn-block filter-school" value="ELEMENTARY">            
				Elementary <br> (K-5)
				</button>      
			</div>
		
			<div class="col-xs-4 filter">
				<c:choose>
				     <c:when test='${filterType == "INTERMEDIATE"}'> 
				    	<c:set var="btnColorClass" value="btn-success"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="btnColorClass" value="btn-info"/>
				    </c:otherwise>
			    </c:choose>
				<button type="submit" name="filterType" class="btn ${btnColorClass} btn-med btn-block filter-school" value="INTERMEDIATE">
				Intermediate <br> (6-8)
				</button>  
			</div>
		
			<div class="col-xs-4 filter">
				<c:choose>
				    <c:when test='${filterType == "HIGH"}'> 
				    	<c:set var="btnColorClass" value="btn-success"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="btnColorClass" value="btn-info"/>
				    </c:otherwise>
			    </c:choose>
				<button type="submit" name="filterType" class="btn ${btnColorClass} btn-med btn-block filter-school" value="HIGH">   
				High <br>(9-12)
				</button>
			</div>
		
		</div>
	
		<div class="row">
			<c:forEach var="school" items="${schools}">
				<div class="col-lg-3 col-md-4 col-sm-6 select">
				
				<a href=<c:url value="/classes?schoolId=${school.id}"/> class="btn btn-primary btn-lg school">
				${school.name}
				</a>
			
				</div>
			</c:forEach>
		</div>
	</form>
	</div>
</body>
</html>