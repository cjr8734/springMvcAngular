<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
        <!DOCTYPE HTML>

        <html lang="en">

        <head>
            <%-- The angular $location service needs this to work propertly --%>
            <base href="${contextPath}">

            <title>Angular Lesson #6</title>
        </head>

        <body data-ng-app="myApp" data-ng-controller="PhoneListCtrl as ctrl">

            <h2>angular6.jsp</h2>


            <br/>
            contextPath = ${contextPath} <br/>
            $location.absUrl()   = {{ ctrl.location.absUrl() }}     <br/>
            $location.path()     = {{ ctrl.location.path() }}       <br/>
            $location.url()      = {{ ctrl.location.url() }}       <br/>
            $location.host()     = {{ ctrl.location.host() }}      <br/>
            $location.protocol() = {{ ctrl.location.protocol() }}  <br/>
            <br/>
            <br/>

            <%-- Load jQuery --%>
            <script src="${contextPath}/resources/jquery-3.2.1/jquery.min.js"></script>

            <%-- Load Angular --%>
            <script src="${contextPath}/resources/angular-1.3.16/angular.min.js" type="text/javascript"></script>

            <script type="text/javascript">

                // NOTE:  The $location directive needs two things to work:
                //           1) <base href="${contextPath}">
                //           2) $locationProvider.html5Mode( {  enabled: true });
                var myApp = angular.module('myApp', [], function($locationProvider) {
                    console.log('app initialization started');

                    // Configuration the locationProvider so that $location calls work
                    $locationProvider.html5Mode( {  enabled: true });

                    console.log('app initialization finished');
                });


                myApp.controller('PhoneListCtrl', [ '$log', '$location', function ($log, $location)
                {
                    var self = this;

                    self.location = $location;

                    $log.debug ('using $log         $location.absUrl()=' + $location.absUrl() );
                    console.log('using console.log  $location.absUrl()=' + $location.absUrl() );
                }]);
            </script>

</body>
</html>