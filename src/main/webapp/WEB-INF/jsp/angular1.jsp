<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
        <!DOCTYPE HTML>

        <html lang="en">

        <head>
            <title>Angular #1</title>
        </head>

        <body data-ng-controller="PhoneListCtrl as ctrl" data-ng-app="myApp">

            <h2>angular1.jsp</h2>

            <br/>
            <br/>
            	<button data-ng-click="ctrl.makeValidRestCall()">Make Valid Rest Call</button>&nbsp;&nbsp;
            	<button data-ng-click="ctrl.makeInvalidRestCall()">Make Invalid Rest Call</button>&nbsp;&nbsp;
            	<button data-ng-click="ctrl.clearResults()()">Clear Results</button>
            <br/>
            	Status: {{ctrl.statusMessage}}<br/>
            	<textarea data-ng-model="ctrl.restCallResults" style="width: 1000px; height: 250px"></textarea>
            <br/>
            <br/>


              <ul>
                <li data-ng-repeat="phone in phones">
                  <span>{{phone.name}}</span>
                  <p>{{phone.snippet}}</p>
                </li>
              </ul>


             <p>Nothing here {{'yet' + '!'}}</p>

             <p>1 + 2 = {{ 1 + 2 }}</p>

            <br/>

            Hello ${userInfo.username} <br/>

            <br/>
            <br/>

            <script type="text/javascript">
                var gsContextPath = '${contextPath}';
                var phonecatApp = angular.module('myApp', []);

                phonecatApp.controller('PhoneListCtrl', function ($scope, $http, $log)
                {
                  var self = this;
                  self.restCallResults = '';
                  self.statusMessage = '';

                  $scope.phones = [
                    {'name': 'Nexus S',
                     'snippet': 'Fast just got faster with Nexus S.'},
                    {'name': 'Motorola XOOM\u2122  with Wi-Fi',
                     'snippet': 'The Next, Next Generation tablet.'},
                    {'name': 'MOTOROLA XOOM\u2122 ',
                     'snippet': 'The Next, Next Generation tablet.'}
                  ];
                  /*******************************************************************
                  * makeValidRestCall()
                  *
                  * Call a REST end point that will succeed
                  *******************************************************************/
                  self.makeValidRestCall = function()
                   {
                    var sUrl = gsContextPath + '/rest/PA';
                    $log.debug("makeRestCall() started making a call to ", sUrl);

   		            $http.get(sUrl)
                    .then(function(response) {
                        var data = response.data;
                        var status = response.status;
                        var statusText = response.statusText;
                        var headers = response.headers;
                        var config = response.config;
                        // I got a response from the end point
                        self.statusMessage = 'I got a response with status=' + status;

                        // Convert the list of Java objects into a string
                  	    var sFormattedResults = self.getDataAsString(data);

       	                self.restCallResults = self.restCallResults + sFormattedResults + "\n";
       	            })

                  	            .then(function() {
                  	                self.statusMessage = 'Angular Call Finished';
                  	            });

                  	    self.statusMessage = 'Made Rest Call....Waiting for results.';
                  	};


                  	/*******************************************************************
                  	 * makeInvalidRestCall()
                  	 *
                  	 * Call a REST end point that will throw an error
                  	 *******************************************************************/
                  	self.makeInvalidRestCall = function()
                  	{
                  	    // This url will throw a RunTime Exception
                  	    var sUrl = gsContextPath + '/rest/Pa/exception';
                  	    $log.debug("makeRestCall() started making a call to ", sUrl);

                  	    $http.get(sUrl)
                                            .then(function(response) {
                                                var data = response.data;
                                                var status = response.status;
                                                var statusText = response.statusText;
                                                var headers = response.headers;
                                                var config = response.config;
                                                // I got a response from the end point
                                                self.statusMessage = 'I got a response with status=' + status;

                                                // Convert the list of Java objects into a string
                                          	    var sFormattedResults = self.getUserListAsString(data);

                               	                self.restCallResults = self.restCallResults + sFormattedResults + "\n";
                               	            })

                  	    self.statusMessage = 'Made Rest Call....Waiting for results.';

                  	};


                  	self.getDataAsString = function(aData)
                  	{
                  	    var sResults = '';

                  	    for(var i=0; i<aData.length; i++)
                  	    {
                  	        var dataInstance = aData[i];
                  	        sResults = sResults + 'LogID=' + dataInstance.log_id + '   SourceIP=' + dataInstance.src_ip + "\n";
                  	    }

                  	    return sResults;
                  	};


                  	self.clearResults = function()
                  	{
                  	    $log.debug("clearResults() started");
                  	    self.restCallResults = '';
                  };
                });

            </script>
        </body>
</html>