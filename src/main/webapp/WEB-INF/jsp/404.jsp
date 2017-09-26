<%@ include file="/WEB-INF/jsp/stdJspIncludes.jsp" %>

		<!DOCTYPE HTML>

		<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
		    <title>Error</title>
		    <%-- I N S E R T       C S S     B U N D L E     --%>
            <jwr:style src="/id/bootstrap.css"/>


            <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
            <!--[if lt IE 9]>
                <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/assets/html5shiv-master/dist/html5shiv.js"></script>
                <script src="${contextPath}/resources/bootstrap-4.0.0-beta-dist/assets/Respond-master/dest/respond.min.js"></script>
            <![endif]-->

            <%-- L O A D    J A V A S C R I P T     B U N D L E   (jquery, bootstrap, angular)  --%>
            <jwr:script src="/id/jquery.bootsrap.angular.js" />
		</head>

		<body>

		<%-- S T A N D A R D       H E A D E R  --%>
		<%@ include file="/WEB-INF/jsp/stdHeader.jsp" %>
		<div style="margin-left:300px; padding:1px 16px; height:1000px;">

		<h1>This is Embarrassing.</h1>

		<br/>
		<br/>
		<h2>This is Embarrassing....But, I could not find your page.</h2>

		<br/>
		<a href="${contextPath}/">Start Over</a> <br/>
		<br/>

		<br/>
		<br/>

		<%-- S T A N D A R D       F O O T E R  --%>
		<%@ include file="/WEB-INF/jsp/stdFooter.jsp" %>
		</div>

		</body>
</html>