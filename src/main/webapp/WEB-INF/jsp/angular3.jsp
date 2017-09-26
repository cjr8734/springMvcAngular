<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
		<!DOCTYPE HTML>

		<html lang="en">

		<head>
		    <title>Angular #3</title>

		    <%-- Load select2 CSS --%>
		    <link href="${contextPath}/resources/select2-4.0.3/dist/css/select2.css" rel="stylesheet">

		</head>

		<body data-ng-app="myApp" data-ng-controller="PhoneListCtrl as ctrl">

		<h2>angular3.jsp</h2>


		<br/>

		updated with data tags<br/>


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

		<%-- Load select2 JS --%>
		<script src="${contextPath}/resources/select2-4.0.3/dist/js/select2.min.js"></script>

		<script type="text/javascript">
		    var myApp = angular.module('myApp', []);

		    myApp.controller('PhoneListCtrl', [ function ()
		    {
		        var self = this;


		        self.phones = [
		            {'name': 'Nexus S', 'snippet': 'Fast just got faster with Nexus S.'},
		            {'name': 'Nexus T', 'snippet': 'Nexus newest model.'},
		            {'name': 'Motorola XOOM\u2122  with Wi-Fi', 'snippet': 'The Next, Next Generation tablet.'},
		            {'name': 'MOTOROLA XOOM\u2122 ', 'snippet': 'The Next, Next Generation tablet.'}
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