<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>

		<!DOCTYPE HTML>

		<html lang="en" data-ng-app="myApp">

		<head>
		    <title>Angular Grid #2</title>

            <style>
		        .svg-container {
                    display: inline-block;
                    position: relative;
                    width: 100%;
                    vertical-align: top;
                    overflow: hidden;
                }
                .svg-content-responsive {
                    display: inline-block;
                    position: absolute;
                    top: 0px;
                    left: 0;
                }
            </style>

		</head>

		<body data-ng-app="myApp" data-ng-controller="PageController as ctrl">

		<h2>grid2.jsp</h2>
		 <div class="dropdown">
          <button onclick="myFunction()" class="dropbtn">Dropdown</button>
          <div id="myDropdown" class="dropdown-content">
            <a href="#">Link 1</a>
            <a href="#">Link 2</a>
            <a href="#">Link 3</a>
          </div>
        </div>

        <div class="chart"></div>

        <script type="text/javascript">
            var listIPs = ${PADataAsJson}[0];
            var protocol = ${PADataAsJson}[1];
            var count = ${PADataAsJson}[2];
            var direction = ${PADataAsJson}[3];

            var height = d3.max(count);
            var width = count.length*18;

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
                            .attr( "width", 18 )
                            .attr( "fill", function(d,i){
                                if (direction[i] == "SOURCE") {
                                    return "#0013a5";
                                }
                                else if (direction[i] == "DESTINATION") {
                                    return "#771010";
                                }
                                else {
                                    return "#00ff21";
                                }
                             })
                            .attr( "height", function(d){
                                return d;
                            })
                            .attr( "y", function(d){
                                return d3.max(count) - d;
                            });

                // remove any unused bars
                selection.exit()
                        .remove();
            }


            // Append an SVG element into which our bar chart goes into
            // Returns a pointer to the newly-created element
            var svg = d3.select(".chart")
                         .append("div")
                         .classed("svg-container", true) //container class to make it responsive
                         .style("padding-bottom", height/width*100 + "%")
                         .append("svg")
                         //responsive SVG needs these 2 attributes and no width and height attr
                         .attr("preserveAspectRatio", "xMinYMin meet")
                         .attr("viewBox", "0 0 " + width + " " + height)
                         //class to make it responsive
                         .classed("svg-content-responsive", true);


            // Draw the chart
            drawChart( svg, count );

        </script>

		<script type="text/javascript">
            var myApp = angular.module('myApp', []);

            myApp.controller('PageController', [ function ()
            {

            }]);

        </script>

		</body>
</html>