<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

        <%-- Allow all JSPs to have the contextPath variable --%>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <!DOCTYPE HTML>
        <html lang="en-US">
            <head>
                <title><sitemesh:write property='title'/></title>

                <meta name="viewport" content="width=device-width, initial-scale=1.0">

                <%-- Load Bootstrap CSS --%>
                <link href="${contextPath}/resources/bootstrap-4.0.0-beta-dist/css/bootstrap.min.css" rel="stylesheet" media="screen">

                <%-- Load Bootstrap CSS Themes --%>
                <link href="${contextPath}/resources/bootstrap-4.0.0-beta-dist/css/bootstrap-theme.min.css" rel="stylesheet" media="screen">

                <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
                <!--[if lt IE 9]>
                    <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/assets/html5shiv-master/dist/html5shiv.js"></script>
                    <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/assets/Respond-master/dest/respond.min.js"></script>
                <![endif]-->

                <link rel="stylesheet" href="${contextPath}/resources/DataTables-1.10.15/media/css/jquery.dataTables.min.css?compile=false"  />

                <%-- Load Angular --%>
                <script src="${contextPath}/resources/angular-1.3.16/angular.min.js" type="text/javascript"></script>

                <%-- Load jQuery --%>
                <script src="${contextPath}/resources/jquery-3.2.1/jquery.min.js" type="text/javascript"></script>

                <%-- Load Bootstrap --%>
                <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/js/bootstrap.min.js" type="text/javascript"></script>

                <%-- P A G E      H E A D    S E C T I O N  --%>
                <sitemesh:write property='head'/>
            </head>
            <body data-ng-app='<sitemesh:write property="body.data-ng-app" />' data-ng-controller='<sitemesh:write property="body.data-ng-controller" />'>
                <%-- Standard Header is here  --%>
                <%@ include file="/WEB-INF/jsp/stdHeader.jsp" %>

                <%-- P A G E     B O D Y     --%>
                <sitemesh:write property='body'/>

                <%-- Standard Footer is here --%>
                <%@ include file="/WEB-INF/jsp/stdFooter.jsp" %>

                <%-- I N S E R T        P A G E        J A V A S C R I P T    using <content tag="bottom_js">  --%>
                <sitemesh:write property="page.bottom_js"/>
            </body>
</html>