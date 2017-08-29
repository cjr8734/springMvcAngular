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

                <link rel="stylesheet" href="${contextPath}/resources/DataTables-1.10.15/media/css/jquery.dataTables.min.css?compile=false"  />

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