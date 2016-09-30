<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Parents Connect</title>

    <!-- Bootstrap -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

    <!-- Custom CSS -->
    
    <link href="resources/css/parentcomm.css" rel="stylesheet">
    
    <!-- Custom Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
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


    <!-- Header -->
    <div class="intro-header">
        <div class="container">

            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>Connect with School Parents</h1>
                        <h3>Enter you address to find the schools closest to you</h3>
                        <hr class="intro-divider">
                        
                        <sf:form modelAttribute="address" action="findSchools" method="POST" >
                        <div class="form-group">
                       	<sf:input path="streetAddress" class="form-control" placeholder="Enter your street address"/>
                       	</div>
                       	<div>
                       	<img src="resources/img/google/powered_by_google_on_non_white.png" class="pull-right" >
                       	</div>
                       	<div class="form-group">
                       		<button type="submit" class="btn btn-primary">Find Schools</button>
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
    <script src="resources/js/jquery.js"></script>

     <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="resources/js/bootstrap.min.js"></script>
    
    <script>
    function initAutocomplete() {
       		 // Create the autocomplete object, restricting the search to geographical
       		 // location types.
       		 autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */(document.getElementById('streetAddress')),
            {types: ['geocode']}); 
       		 
       		 autocomplete.addListener('place_changed', collectLongitudeAndLatitude);
    	}
    
    function collectLongitudeAndLatitude() {
    	var place = autocomplete.getPlace();
    	$("#latitude").val(place.geometry.location.lat());
    	$("#longitude").val(place.geometry.location.lng());
    }
    </script>
    
    <script>
    $(document).ready(function(){
     	$(document).on("focus", '#streetAddress', function() {
     	    // Bias the autocomplete object to the user's geographical location,
            // as supplied by the browser's 'navigator.geolocation' object.
     		if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                  var geolocation = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                  };
                  var circle = new google.maps.Circle({
                    center: geolocation,
                    radius: position.coords.accuracy
                  });
                  autocomplete.setBounds(circle.getBounds());
                });
              }
     	});
    });
    </script>
    
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAzyqOF3t-dgy3uun3Y_ZI7r3SK0nxxlro&libraries=places&callback=initAutocomplete"
        async defer></script>

</body>

</html>
