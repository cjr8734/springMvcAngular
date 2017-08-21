<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
		<!DOCTYPE HTML>

		<html lang="en">

		<head>
		    <title>Angular #2</title>
		</head>

		<body data-ng-app="myApp" data-ng-controller="PageController as ctrl">

		<h2>angular2.jsp</h2>


		Guess the number between 1 and 100<br/>

		Your Guess: <input type="text" data-ng-model="ctrl.guessedNumber"
		                   data-on-enter="ctrl.verifyNumber()"
		                   data-ng-disabled="ctrl.gameIsOver"
		                   data-ng-focus="ctrl.gameIsOver == false" />

		<button data-ng-click="ctrl.verifyNumber()" data-ng-disabled="ctrl.gameIsOver">Verify</button>&nbsp;&nbsp;
		<button data-ng-click="ctrl.restartGame()">Restart</button><br/>
		<br/>
		No of guesses: {{ctrl.totalGuessesMade}} <br/>
		<b>{{ctrl.messageHint}}</b>


		<%-- Load jQuery --%>
		<script src="${contextPath}/resources/jquery-3.2.1/jquery.min.js"></script>

		<%-- Load Angular --%>
        <script src="${contextPath}/resources/angular-1.3.16/angular.min.js" type="text/javascript"></script>

		<script type="text/javascript">
		    var myApp = angular.module('myApp', []);


		    myApp.controller('PageController', [ function ()
		    {
		        var self = this;

		        self.messageHint = 'Starting Game';
		        self.guessedNumber = '';
		        self.totalGuessesMade = 0;
		        self.hiddenNumber = generateRandomNumber();
		        self.gameIsOver = false;


		        /************************************************
		         * restartGame()
		         *************************************************/
		        self.restartGame = function()
		        {
		            console.log("restartGame()");

		            self.totalGuessesMade = 0;
		            self.messageHint = "Restarting Game";
		            self.hiddenNumber = generateRandomNumber();
		            self.guessedNumber = '';
		            self.gameIsOver = false;

		            // Put the mouse on the textbox
		        };

		        /************************************************
		         * verifyNumber()
		         *************************************************/
		        self.verifyNumber = function()
		        {
		            self.totalGuessesMade++;

		            if (self.guessedNumber > this.hiddenNumber)
		            {
		                self.messageHint = "You're too high";
		            }
		            else if (self.guessedNumber < this.hiddenNumber)
		            {
		                self.messageHint = "You're too low";
		            }
		            else
		            {
		                self.messageHint = "You got it!!!";

		                // The user got it -- so set a flag indicating the game is over
		                // NOTE:  This will cause certain buttons to be disabled
		                self.gameIsOver = true;
		            }
		        };


		        function generateRandomNumber ()
		        {
		            var iRndNumber = Math.ceil(Math.random()*100);
		            console.log("generateRandomNumber() returns " + iRndNumber);
		            return iRndNumber;
		        };
		    }]);


		    myApp.directive('onEnter', function () {
		        return function (scope, element, attrs) {
		            element.bind("keydown keypress", function (event) {
		                if(event.which === 13) {
		                    // The user clicked enter in this form field -- so run the action in data-on-enter="  "
		                    scope.$apply(function (){
		                        scope.$eval(attrs.onEnter);
		                    });

		                    event.preventDefault();
		                }
		            });
		        };
		    });

		</script>

		</body>
</html>