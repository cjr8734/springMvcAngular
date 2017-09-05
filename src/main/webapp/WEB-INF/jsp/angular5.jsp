<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
        <!DOCTYPE HTML>

        <html lang="en">

        <head>
            <title>Angular #5</title>

            <%-- Load select2 CSS --%>
            <link href="${contextPath}/resources/select2-4.0.3/dist/css/select2.css" rel="stylesheet">

            <!-- Load angular-timer CSS -->
            <link rel="stylesheet" type="text/css" href="${contextPath}/resources/angular-timer-1.3.5/dist/assets/css/angular-timer-bower.css" />
        </head>

        <body data-ng-app="myApp" data-ng-controller="PhoneListCtrl as ctrl">

        <h2>angular5.jsp</h2>



        <%--   T I M E R     I S    H E R E  --%>
        Timer starts here<br/>
        <timer interval="1000" countdown="100">{{countdown}}</timer> <br/>
        Timer finishes here<br/><br/>



        <%-- Define a Select2 dropdown --%>
        <select select2
                initialization-json="{allowClear:true, placeholder:'-All Phones-'}"  style="width: 300px"
                data-ng-model="ctrl.selectedPhone"
                data-ng-change="ctrl.userSelectedPhone()">

            <%-- select2 replaces this blank option with a place holder so keep it --%>
            <option></option>

            <option value="">-All Phones-</option>

            <%-- Fill-in the dropdown the values found in search.phones --%>
            <option data-ng-repeat="option in ctrl.phones" value="{{option.name}}">{{option.name}}</option>
        </select>

        <br/>
        <br/>

        <%-- Load select2 JS --%>
        <script src="${contextPath}/resources/select2-4.0.3/dist/js/select2.min.js"></script>

        <script type="text/javascript" src="${contextPath}/resources/moment-2.18.1/moment-with-locales.js"></script>

        <%-- Load angular-timer JS --%>
        <script src="${contextPath}/resources/angular-timer-1.3.5/dist/assets/js/angular-timer-all.min.js" type="text/javascript"></script>


        <script type="text/javascript">

            // CRUCIAL:  Pass-in the 'timer' dependency into your angular page
            var myApp = angular.module('myApp', ['timer']);

            myApp.controller('PhoneListCtrl', [ function ()
            {
                var self = this;


                self.phones = [
                    {'name': 'Nexus S', 'snippet': 'Fast just got faster with Nexus S.'},
                    {'name': 'Nexus T', 'snippet': 'Nexus newest model.'},
                    {'name': 'Motorola XOOM� with Wi-Fi', 'snippet': 'The Next, Next Generation tablet.'},
                    {'name': 'MOTOROLA XOOM�', 'snippet': 'The Next, Next Generation tablet.'}
                ];

                self.userSelectedPhone = function()
                {
                    console.log('userSelectedPhone() called.  self.selectedPhone=', self.selectedPhone);
                };


            }]);

            myApp.directive('select2', function()
            {
                return{
                    restrict: 'A',
                    scope:
                    {
                        initializationJson: '=initializationJson'
                    },
                    link: function(scope, element, attrs)
                    {
                        // Make sure InitializationJson is atleast an empty json block
                        scope.initializationJson = scope.initializationJson || {};

                        // Apply the json to this select2 dropdown
                        $(element).select2(scope.initializationJson);
                    }
                }
            });
        </script>
        </body>
</html>