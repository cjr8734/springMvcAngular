<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
        <!DOCTYPE HTML>

        <html lang="en">

        <head>
            <title>Angular #1</title>
        </head>

        <body data-ng-controller="PhoneListCtrl" data-ng-app="myApp">

            <h2>angular1.jsp</h2>


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

            <%-- Load Angular --%>
            <script src="${contextPath}/resources/angular-1.3.16/angular.min.js" type="text/javascript"></script>

            <script type="text/javascript">
                var phonecatApp = angular.module('myApp', []);

                phonecatApp.controller('PhoneListCtrl', function ($scope)
                {
                  $scope.phones = [
                    {'name': 'Nexus S',
                     'snippet': 'Fast just got faster with Nexus S.'},
                    {'name': 'Motorola XOOM™ with Wi-Fi',
                     'snippet': 'The Next, Next Generation tablet.'},
                    {'name': 'MOTOROLA XOOM™',
                     'snippet': 'The Next, Next Generation tablet.'}
                  ];
                });

            </script>
        </body>
</html>