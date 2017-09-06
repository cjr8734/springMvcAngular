<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>

		<!DOCTYPE HTML>

		<html lang="en" data-ng-app="myApp">

		<head>
		    <title>Angular Grid #1</title>

		    <%-- I N S E R T       C S S     F I L ES  --%>
		    <link rel="styleSheet" href="${contextPath}/resources/ui-grid-4.0.6/ui-grid.min.css"/>

		    <style type="text/css">
		        .myGrid {
		            width: 500px;
		            height: 250px;
		        }
		    </style>
		</head>

		<body data-ng-app="myApp" data-ng-controller="PageController as ctrl">

		<h2>grid1.jsp</h2>


		<%-- U I   G R I D     --%>
		<div ui-grid="{ data: scopedGridData }" class="myGrid"></div>

		<br/>
		<br/>


		<%-- L O A D    J A V A S C R I P T    F I L E S       --%>
		<script src="${contextPath}/resources/ui-grid-4.0.6/ui-grid.min.js"></script>

		<script type="text/javascript">

		    // include the ui.grid module as a dependency to this app
		    var myApp = angular.module('myApp', ['ui.grid']);

		    // Get the returned JSON string from the JSP page
		    var gListOfUsersAsJson = ${listOfUsersAsJson};


		    myApp.controller('PageController', function ($scope, $log)
		    {
		        var self = this;

		        // the ng-grid does not allow 'controller as' syntax for options.data
		        // -- So, we must use the $scope variable
		        $scope.scopedGridData = gListOfUsersAsJson;
		    });

		</script>

		</body>
</html>