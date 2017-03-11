<%@page language="java" pageEncoding="UTF-8" session="false"%>
<%@ page import="org.angiedev.parentcomm.web.form.SchoolFinderForm" %>
<%@ page import="org.angiedev.parentcomm.model.*" %>
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
	<div class="page-header">Finding your school
	<c:choose>
		<c:when test='${finderForm.getSearchType() == SearchType.BY_NAME}'>
			<small>(matching name: ${finderForm.getSearchValue()})</small>
		</c:when>
		<c:otherwise>
			<small>(near address: ${finderForm.getSearchValue() })</small>
		</c:otherwise>
	</c:choose>
	</div>
	<sf:form id="selectSchoolForm" modelAttribute="finderForm" action="filter" method="POST" >    

		<div>
		  <div class="toggle_radio">
		    <sf:radiobutton class="toggle_option" path="radius" value='${SearchRadius.SMALL}' id="first_toggle" ></sf:radiobutton>
		    <sf:radiobutton class="toggle_option" path="radius" value='${SearchRadius.MEDIUM}' id="second_toggle" ></sf:radiobutton>
		    <sf:radiobutton class="toggle_option" path="radius" value='${SearchRadius.LARGE}' id="third_toggle"></sf:radiobutton>
		    <sf:radiobutton class="toggle_option" path="radius" value='${SearchRadius.UNRESTRICTED}' id="fourth_toggle"></sf:radiobutton>
		    <label for="first_toggle">${SearchRadius.SMALL.getValue()} miles</label>
		    <label for="second_toggle">${SearchRadius.MEDIUM.getValue()} miles</label>
		    <label for="third_toggle">${SearchRadius.LARGE.getValue()} miles</label>
		     <label for="fourth_toggle">Unrestricted</label>
		    <div class="toggle_option_slider">
		    </div>
		  </div>
	  </div>

		<div class="row padded">
		</div>

		<div class="row">
			<div class="col-xs-4 filter">
				<c:choose>
				    <c:when test='${finderForm.getSchoolLevel() == SchoolLevel.ELEMENTARY}'> 
				    	<c:set var="btnColorClass" value="btn-success"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="btnColorClass" value="btn-info"/>
				    </c:otherwise>
			    </c:choose>
			    
				<button type="submit" name="newSchoolLevel" class="btn ${btnColorClass} btn-med btn-block filter-school" value="ELEMENTARY">            
				Elementary <br> (K-5)
				</button>      
			</div>
		
			<div class="col-xs-4 filter">
				<c:choose>
				     <c:when test= '${finderForm.getSchoolLevel()  == SchoolLevel.INTERMEDIATE}'> 
				    	<c:set var="btnColorClass" value="btn-success"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="btnColorClass" value="btn-info"/>
				    </c:otherwise>
			    </c:choose>
				<button type="submit" name="newSchoolLevel" class="btn ${btnColorClass} btn-med btn-block filter-school" value="INTERMEDIATE">
				Intermediate <br> (6-8)
				</button>  
			</div>
		
			<div class="col-xs-4 filter">
				<c:choose>
				    <c:when test='${finderForm.getSchoolLevel()  == SchoolLevel.HIGH}'> 
				    	<c:set var="btnColorClass" value="btn-success"/>
				    </c:when>
				    <c:otherwise>
				       <c:set var="btnColorClass" value="btn-info"/>
				    </c:otherwise>
			    </c:choose>
				<button type="submit" name="newSchoolLevel" class="btn ${btnColorClass} btn-med btn-block filter-school" value="HIGH">   
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
		
		<sf:hidden name="searchValue" path="searchValue"/>
		<sf:hidden name="searchType" path="searchType"/>
		<sf:hidden name="schoolLevel" path="schoolLevel"/>
	</sf:form>
	</div>
</body>

<script src=<c:url value="/resources/js/jquery.js"/>></script>

<script>

$().ready( function() {
	 $("input[name='radius']").change( function() {
		$('#selectSchoolForm').submit();
	});
});

</script>
</html>