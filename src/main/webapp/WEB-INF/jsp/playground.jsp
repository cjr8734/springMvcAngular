<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
		    <title>Coding Playground</title>

		    <script src="${contextPath}/resources/d3/d3.js" type="text/javascript"></script>
		</head>

		<body data-ng-app="myApp" data-ng-controller="PageController as ctrl">

		<h2>playground.jsp</h2>
		<script>
            d3.select("#main-site-content")
              .selectAll("p")
              .data([4, 8, 15, 16, 23, 42])
              .enter().append("p")
                .text(function(d, i) { return "I'm number " + d + "! I'm in position " + (i+1) + "!"; });
		</script>

		<script type="text/javascript">
            var myApp = angular.module('myApp', []);

            myApp.controller('PageController', [ function ()
            {

            }]);

        </script>

		</body>
</html>