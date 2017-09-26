<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
        <!DOCTYPE HTML>

        <html lang="en">

        <head>
            <title>Angular #4</title>


        </head>

        <body data-ng-app="myApp" data-ng-controller="PhoneListCtrl as ctrl">

        <h2>angular4.jsp</h2>





        <%--   T I M E R     I S    H E R E  --%>
        Timer starts here<br/>
        <timer interval="1000" countdown="ctrl.countdownVal">{{countdown}}</timer> <br/>
        Timer finishes here<br/><br/>

        <button ng-disabled="! ctrl.showStopButton()"   ng-click="ctrl.stopTimer()">Stop Timer</button>
        <button ng-disabled="! ctrl.showStartButton()"    ng-click="ctrl.startTimer()">Start Timer</button>
        <button ng-click="ctrl.resetTimerAndStop()">Reset Timer</button>
        <br/><br/>

        ctrl.timerRunning={{ctrl.timerRunning}}<br/>
        ctrl.timerCountIsPositive={{ctrl.timerCountIsPositive}}<br/>
        <br/>

        <script type="text/javascript" src="${contextPath}/resources/moment-2.18.1/moment-with-locales.js"></script>

        <%-- Load angular-timer JS --%>
        <script src="${contextPath}/resources/angular-timer-1.3.5/dist/assets/js/angular-timer-all.min.js" type="text/javascript"></script>


        <script type="text/javascript">

            // CRUCIAL:  Pass-in the 'timer' module into your angular page
            var myApp = angular.module('myApp', ['timer'], function() {
                // Angular App Initialization
                console.log('App Initialization started');
             });


            myApp.controller('PhoneListCtrl', [ '$log', '$scope', function ($log, $scope)
            {
                var self = this;

                self.timerRunning = true;
                self.timerCountIsPositive = true;
                self.countdownVal = 15;              // Countdown is 15 seconds

                // -----------------------------------------------------------------------
                // Handle 'timer-stopped' event
                //   Case #1:  The timer expired and it called this
                //   Case #2:  The user pressed the "Stop" button
                // -----------------------------------------------------------------------
                $scope.$on('timer-stopped', function(event, data){
                    console.log('Timer Stopped event started:   data=', data);

                    if (data.millis == 0)
                    {
                        // The timer stopped because it's out of time
                        console.log('case #1:  Times is up!!');

                        // Because the timer module called this, I need to apply these changes manually here
                        $scope.$apply( function () {
                            self.timerRunning = false;
                            self.timerCountIsPositive = false;
                        })
                    }
                    else
                    {
                        // User pressed stop
                        console.log('case #2:  User pressed stop.');
                    }

                    console.log('Timer Stopped event finished.');
                });


                self.startTimer = function()
                {
                    console.log('startTimer() called');
                    self.timerRunning = true;

                    // Send a message to the timer directive to start up
                    $scope.$broadcast('timer-start');
                };


                self.stopTimer = function()
                {
                    console.log('stopTimer() called');
                    self.timerRunning = false;

                    // Send a message to the timer directive to stop
                    $scope.$broadcast('timer-stop');
                };


                self.resetTimerAndStop = function()
                {
                    console.log('restartTimer() called');
                    self.timerRunning = false;
                    self.timerCountIsPositive = true;

                    // Send a message to the timer directive to reset the count and stop
                    $scope.$broadcast('timer-reset');
                };


                self.showStartButton = function()
                {
                    if ((self.timerRunning == false) && (self.timerCountIsPositive))
                    {
                        // The timer is not running and the timer count is positive -- so enable the start button
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                };

                self.showStopButton = function()
                {
                    if (self.timerRunning)
                    {
                        // The timer is actively running -- so enable the stop button
                        return true;
                    }
                    else
                    {
                        // the timer is not actively running -- so disable the stop button
                        return false;
                    }
                };

            }]);


        </script>
        </body>
</html>