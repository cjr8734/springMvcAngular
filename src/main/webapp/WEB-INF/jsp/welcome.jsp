<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
<!DOCTYPE HTML>

<html lang="en" data-ng-app="notesApp">

<head>
<meta charset="UTF-8">
    <title>Welcome Page</title>
    <style>
              .map {
                height: 400px;
                width: 100%;
              }
    </style>
    <link rel="stylesheet" href="https://openlayers.org/en/v4.3.1/css/ol.css" type="text/css">
</head>

<body data-ng-controller="MainCtrl as ctrl" data-ng-app="notesApp">

    <h2>welcome2.jsp</h2>

    Hello {{1 + 1}}nd time AngularJS
    <br/>
    <br/>
    Database time is ${currentTime} <br/>
    ctrl.helloMessage={{ctrl.helloMessage}}  <br/>
    ctrl.byeMessage={{ctrl.byeMessage}} <br/>

    ctrl.message={{ctrl.message}}<br/>

    <button ng-click="ctrl.clearMessage()">Clear Message</button>&nbsp;&nbsp;
    <button ng-click="ctrl.changeMessage()">Change Message</button>

    <br/>
    <br/>

    <table width="300" cellpadding="1" cellspacing="2" style="border: 1px solid black">
       <tr>
         <th>&nbsp;</th>
         <th>label</th>
         <th>status</th>
       </tr>

       <tr ng-repeat="note in ctrl.notes">
          <td>{{$index + 1}}.</td>
          <td>{{note.label}}</td>
          <td>{{note.done}}</td>
       </tr>
    </table>

    <br/><br/>

    <div ng-repeat="note in ctrl.notes">
       <span style="color:#000" class="label" ng-bind="note.label"></span>
       <span class="status" ng-bind="note.done"></span>
    </div>

    <br/><br/>

    <a href="${contextPath}/example/angular1">Angular example #1 - Simple Controller</a> <br/>
    <a href="${contextPath}/example/angular2">Angular example #2 - guessing game</a> <br/>
    <a href="${contextPath}/example/angular3">Angular example #3 - select2 dropdown</a> <br/>
    <a href="${contextPath}/example/angular4">Angular example #4 - page with a timer</a> <br/>
    <a href="${contextPath}/example/angular5">Angular example #5 - select2 page with a timer</a> <br/>
    <a href="${contextPath}/example/angular6">Angular example #6 - Show $location info</a> <br/>
    <a href="${contextPath}/example/angular7">Angular example #7 - Share Data Between 2 Controllers</a> <br/>

    <br/>

    <h2>My Map</h2>
    <div id="map" class="map"></div>

    <script src="${contextPath}/resources/OpenLayers/ol.js" type="text/javascript"></script>

    <script type="text/javascript">
        var notesApp = angular.module('notesApp', []);

        notesApp.controller('MainCtrl', [function ()
         {
             console.log('MainCtrl has been created.');
             var self = this;

             self.helloMessage = 'Hello';
             self.byeMessage = 'Goodbye';
             self.message = 'Hello!';

            self.notes = [
               {id: 1, label: 'First Note', done: false},
               {id: 2, label: 'Second Note', done: true},
               {id: 3, label: '3rd note', done: true},
               {id: 4, label: '4th note', done: false}
            ];

             self.changeMessage = function()
             {
                self.message = "Goodbye";
             };

             self.clearMessage = function()
             {
                self.message = "";
             };
         }
         ]);

    </script>
    <script type="text/javascript">
          var map = new ol.Map({
            target: 'map',
            layers: [
              new ol.layer.Tile({
                source: new ol.source.OSM()
              })
            ],
            view: new ol.View({
              center: ol.proj.fromLonLat([-77.037, 38.907]),
              zoom: 10
            })
          });
    </script>
</body>
</html>