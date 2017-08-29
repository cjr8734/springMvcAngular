<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
        <!DOCTYPE HTML>

        <html lang="en">

        <head>
            <%-- The angular $location service needs this to work propertly --%>
            <base href="${contextPath}">

            <title>Angular Lesson #7</title>
        </head>

        <body data-ng-app="myApp" data-ng-controller="BogusController1">

        <h2>angular7.jsp</h2>


        <div data-ng-controller="BogusController1 as ctrl1" style="border: 1px solid black">
            ctrl1.message=<textarea data-ng-model="ctrl1.message" rows="4" cols="40"></textarea> <br/>
            <button data-ng-click="ctrl1.addProductUsingService()">Add Message to Service</button>
            <button data-ng-click="ctrl1.clearProductsUsingService()">Clear Products</button>
            <br/><br/>

            Product Service List holds this: <br/>
            {{ ctrl1.showProductServiceList() }}
        </div>

        <br/>
        <br/>

        <div data-ng-controller="BogusController2 as ctrl2" style="border: 1px solid black">
            ctrl2.message=<textarea data-ng-model="ctrl2.message" rows="4" cols="40"></textarea> <br/>
            <button data-ng-click="ctrl2.getProductUsingService()">Get Message from Service</button>
        </div>

        <br/>

        <%-- Load jQuery --%>
        <script src="${contextPath}/resources/jquery-3.2.1/jquery.min.js"></script>

        <%-- Load Angular --%>
        <script src="${contextPath}/resources/angular-1.3.16/angular.min.js" type="text/javascript"></script>

        <script type="text/javascript">

            // NOTE:  The $location directive needs two things to work:
            //           1) <base href="${contextPath}">
            //           2) $locationProvider.html5Mode( {  enabled: true });
            var myApp = angular.module('myApp', [], function() {
                console.log('app initialization started');
            });


            myApp.controller('BogusController1', [ 'productService', function(aProductService)
            {
                var self = this;

                self.message = 'This is BogusController1';

                self.clearProductsUsingService = function()
                {
                    aProductService.clearProducts();
                };

                self.showProductServiceList = function()
                {
                    return aProductService.getProducts();
                };

                self.addProductUsingService = function()
                {
                    aProductService.addProduct(self.message);
                };
            }]);


            myApp.controller('BogusController2', [ 'productService', function(aProductService2)
            {
                var self = this;

                self.message = '';

                self.getProductUsingService = function()
                {
                    var products = aProductService2.getProducts();
                    self.message = products.toString();
                }
            }]);


            myApp.service('productService', function() {
                var productList = [];

                var addProduct = function(newObj) {
                    productList.push(newObj);
                };

                var getProducts = function(){
                    return productList;
                };
                var clearProducts = function() {
                     productList = [];
                };

                return {
                    addProduct: addProduct,
                    getProducts: getProducts,
                    clearProducts: clearProducts
                };

            });
        </script>

        </body>
</html>