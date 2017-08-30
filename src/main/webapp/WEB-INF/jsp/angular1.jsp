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
            	<textarea data-ng-model="ctrl.restCallResults" style="width: 400px; height: 100px"></textarea>
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

            Hello ${userInfo.userName} <br/>
            Are you an administrator: ${userInfo.isAdministrator }

            <br/>
            <br/>

            <%-- Load jQuery --%>
            <script src="${contextPath}/resources/jquery-3.2.1/jquery.min.js" type="text/javascript"></script>

            <%-- Load Angular --%>
            <script src="${contextPath}/resources/angular-1.3.16/angular.min.js" type="text/javascript"></script>

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
                    {'name': 'Motorola XOOM™ with Wi-Fi',
                     'snippet': 'The Next, Next Generation tablet.'},
                    {'name': 'MOTOROLA XOOM™',
                     'snippet': 'The Next, Next Generation tablet.'}
                  ];
                  /*******************************************************************
                  * makeValidRestCall()
                  *
                  * Call a REST end point that will succeed
                  *******************************************************************/
                  self.makeValidRestCall = function()
                   {
                    var sUrl = gsContextPath + '/rest/users';
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
                  	    var sUrl = gsContextPath + '/rest/users/exception';
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


                  	self.getUserListAsString = function(aUserList)
                  	{
                  	    var sResults = '';

                  	    for(var i=0; i<aUserList.length; i++)
                  	    {
                  	        var user = aUserList[i];
                  	        sResults = sResults + 'username=' + user.username + '   IsAdministrator=' + user.isAdministrator + "\n";
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