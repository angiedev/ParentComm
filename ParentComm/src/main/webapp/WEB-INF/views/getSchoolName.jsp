<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Parents Connect</title>

    <!-- Bootstrap -->
    <link href=<c:url value="/resources/css/bootstrap.min.css" /> rel="stylesheet">

 	<!-- Custom CSS -->    
    <link href=<c:url value="/resources/css/parentcomm.css"/> rel="stylesheet">
    
    <!-- Custom Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <body>

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
        <div class="container topnav">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand topnav" href="#">Parents Connect</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#about">About</a>
                    </li>
                    <li>
                        <a href="#services">Sign In</a>
                    </li>
                    <li>
                        <a href="#contact">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
    <div class="intro-header">
        <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="intro-message">
                    <h1>Connect with School Parents</h1>
                    <h3>Enter the name of your school</h3>
                    <hr class="intro-divider">
                    
                    <sf:form modelAttribute="school" action="findByName" method="POST" >
                    	<div class="form-group">
                     	<sf:input list="schoolList" path="schoolName" class="form-control" 
                     		placeholder="School name"></sf:input>
                     	<datalist id="schoolList">
                    		</datalist>
                    	</div>
                    	<div class="form-group">
                   			<button type="submit" name="selectSchool" class="btn btn-primary">Select School</button>
                   		</div>                  
               		<sf:hidden path="longitude"/>
                     <sf:hidden path="latitude"/>
                    </sf:form>
                </div>
            </div>
        </div>
        </div>
        <!-- /.container -->
    </div>
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src=<c:url value="/resources/js/jquery.js"/>></script>

     <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src=<c:url value="/resources/js/bootstrap.min.js"/>></script>
    
    <script>
    
    // When page loads get user's geolocation from browser
    $(document).ready(function() {
       	if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(showNearbySchools);
		} else {
			alert("Please insure to enable your browser to share your geolocation.")
		}
    });
    
    // Once geolocation is retrieved populate list of schools nearby the user
	function showNearbySchools(position) {
		var latitudeVal = position.coords.latitude;
	    var longitudeVal = position.coords.longitude;
	    $("#latitude").val(latitudeVal);
	    $("#longitude").val(longitudeVal);
		$.ajax({type: "GET",
       			url: "http://localhost:8080/SchoolFinder/schools?lat=" + latitudeVal + "&long=" + 
       					longitudeVal + "&searchRadius=5&orderBy=BY_NAME",
       			success: function(data){
       				buildDropdown(data);
       			}});
	}
	
    // populates datalist with school names near user
   	function buildDropdown(result){
       	var dataList = $("#schoolList");
		dataList.empty();
    
        if (result != '')
        {
            // Loop through each of the results and append the option to the dropdown
            $.each(result, function(k, v) {
                dataList.append('<option value="' + v.name + '">' + v.name + '</option>');
            });
        }
    }
    </script>
	
</body>

</html>
