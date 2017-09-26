<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
		    <title>D3 Map</title>
		</head>

		<body data-ng-app="myApp" data-ng-controller="PageController as ctrl">

		<h2>map1.jsp</h2>

		<br/>

		<!--  B I G      M A P -->

		<br/>
		<br/>

		<div class="chart"></div>

		<script src="${contextPath}/resources/d3/d3.js" type="text/javascript"></script>

		<script type="text/javascript">

		    function drawChart( aSvg, dataArray )
		    {
		        // create a selection
		        var selection = aSvg.selectAll( "rect" )
		                .data( dataArray );

		        // create new elements wherever needed
		        selection.enter()
		                    .append( "rect" )
		                    .attr( "x", function(d,i){
		                        return i*18;
		                    })
		                    .attr( "width", 15 )
		                    .attr( "fill", function(d,i){
		                        if (i % 2 == 1) {
                                    return "#0013a5";
                                }
                                else {
                                    return "#771010";
                                }
		                     })
		                    .attr( "height", function(d){
		                        return d/10 * 1.5;
		                    })
		                    .attr( "y", function(d){
		                        return 150 - d/10 * 1.5;
		                    });

		        // remove any unused bars
		        selection.exit()
		                .remove();
		    }




		    var ratData = [];
		    for (i=0; i<40; i++) {
		        if (i % 2 == 1) {
		            ratData[i] = 25*i+25;
		        }
		        else {
		            ratData[i] = 25*(40-i-1)+25;
		        }
		    }

		    // Append an SVG element into which our bar chart goes into
		    // Returns a pointer to the newly-created element
		    var svg = d3.select("#main-site-content")
		            .append("svg")
		            .attr("width", 1200)
		            .attr("height", 150);


		    // Draw the chart
		    drawChart( svg, ratData );


		</script>

		<script type="text/javascript">
            var myApp = angular.module('myApp', []);

            myApp.controller('PageController', [ function ()
            {

            }]);

        </script>

		</body>
</html>