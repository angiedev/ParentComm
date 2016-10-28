<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<%@ page import="org.angiedev.parentcomm.model.SchoolGrade" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Select class</title>


   <!-- Bootstrap -->
   <link href=<c:url value="/resources/css/bootstrap.min.css" /> rel="stylesheet">

 	<!-- Custom CSS -->    
    <link href=<c:url value="/resources/css/parentcomm.css"/> rel="stylesheet">


</head>

<body>
<div class="container">
	<div class="page-header">Find your class  <span class="school-name">@ ${school.name}</span></div>


<c:if test="${ (school.lowGrade.value != SchoolGrade.UNGRADED.value) &&
               (school.highGrade.value != SchoolGrade.UNGRADED.value) }" >
    <div class="row">

	<c:choose>
	    <c:when test="${school.getNumGrades() <= 2}"> 
	    	<c:set var="colClass" value="col-xs-6 col-md-4"/>
	    </c:when>
	    <c:when test="${school.getNumGrades() <= 6}">
	    	<c:set var="colClass" value="col-xs-4 col-sm-2 "/>
	    </c:when>
	     <c:otherwise>
	    	<c:set var="colClass" value="col-xs-3"/>
	   	</c:otherwise>	
    </c:choose>
 
 
 	<c:if test="${school.lowGrade.value == SchooGrade.PREK.value}">
		<div class="${colClass}">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="PREK">            
			PK 
			</button>      
		</div>
	</c:if>

	<c:if test="${ (school.lowGrade.value <= SchoolGrade.KINDER.value) &&
		           (school.highGrade.value >= SchoolGrade.KINDER.value) }"> 
	    <div class="${colClass} filter" >
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="KINDER">            
			Kinder
			</button>      
		</div>
	</c:if>
	
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.FIRST.value) &&
		           (school.highGrade.value >= SchoolGrade.FIRST.value) }"> 
	    <div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="FIRST">            
			1st 
			</button>      
		</div>
	</c:if>
	
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.SECOND.value) &&
		           (school.highGrade.value >= SchoolGrade.SECOND.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="SECOND">            
			2nd
			</button>      
		</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.THIRD.value) &&
		           (school.highGrade.value >= SchoolGrade.THIRD.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="THIRD">            
			3rd 
			</button>      
		</div>

	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.FOURTH.value) &&
		           (school.highGrade.value >= SchoolGrade.FOURTH.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="FOURTH">            
			4th 
			</button>      
		</div>
	</c:if>
	
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.FIFTH.value) &&
		           (school.highGrade.value >= SchoolGrade.FIFTH.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="FIFTH">            
			5th 
			</button>      
		</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.SIXTH.value) &&
		           (school.highGrade.value >= SchoolGrade.SIXTH.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="SIXTH">            
			6th 
			</button>      
		</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.SEVENTH.value) &&
		           (school.highGrade.value >= SchoolGrade.SEVENTH.value) }"> 		           
	   <div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="SEVENTH">            
			7th 
			</button>      
		</div>

	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.EIGHTH.value) &&
		           (school.highGrade.value >= SchoolGrade.EIGHTH.value) }"> 
	   <div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="EIGHTH">            
			8th 
			</button>      
		</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.NINETH.value) &&
		           (school.highGrade.value >= SchoolGrade.NINETH.value) }"> 
	   <div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="NINETH">            
			9th 
			</button>      
		</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.TENTH.value) &&
		           (school.highGrade.value >= SchoolGrade.TENTH.value) }"> 
	 <div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="TENTH">            
			10th 
			</button>      
	</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.ELEVENTH.value) &&
		           (school.highGrade.value >= SchoolGrade.ELEVENTH.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="ELEVENTH">            
			11th 
			</button>      
		</div>
	</c:if>
	<c:if test="${ (school.lowGrade.value <= SchoolGrade.TWELFTH.value) &&
		           (school.highGrade.value >= SchoolGrade.TWELFTH.value) }"> 
		<div class="${colClass} filter">
			<button type="submit" name="filterType" class="btn btn-info btn-med btn-block" value="TWELFTH">            
			12th 
			</button>      
		</div>
	
	</c:if>
	</div>
</c:if>
</div>
</body>
</html>