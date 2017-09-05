<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

        <%-- Allow all JSPs to have the contextPath variable --%>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <!DOCTYPE HTML>
        <html lang="en-US">
            <head>
                <title><sitemesh:write property='title'/></title>

                <meta name="viewport" content="width=device-width, initial-scale=1.0">

               <%-- I N S E R T       C S S     B U N D L E     --%>
               <jwr:style src="/id/bootstrap.css"/>


                <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
                <!--[if lt IE 9]>
                    <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/assets/html5shiv-master/dist/html5shiv.js"></script>
                    <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/assets/Respond-master/dest/respond.min.js"></script>
                <![endif]-->

                <%-- L O A D    J A V A S C R I P T     B U N D L E   (jquery, bootstrap, angular)  --%>
                <jwr:script src="/id/jquery.bootsrap.angular.js" />

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