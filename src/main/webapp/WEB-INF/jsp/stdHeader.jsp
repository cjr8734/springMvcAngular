<!-- Standard Header -->
		<table style="width: 100%; border: 1px solid black;">
		    <tr>
		        <td>
		            <nav role="navigation" class="navbar navbar-inverse" style="margin: 0">
		                <!-- Grouping Brand with Toggle for better mobile display -->
		                <div class="navbar-header">
		                    <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
		                        <span class="sr-only">Toggle navigation</span>
		                        <span class="icon-bar"></span>
		                        <span class="icon-bar"></span>
		                        <span class="icon-bar"></span>
		                    </button>
		                    <a href="#" class="navbar-brand">Brand</a>
		                </div>

		                <!-- Next nav links in the Navbar -->
		                <div id="navbarCollapse" class="collapse navbar-collapse">
		                    <ul class="nav navbar-nav">
		                        <li ><a href="${contextPath}"><span class="glyphicon glyphicon-home"></span> Home</a></li>
		                        <li class="dropdown">
		                            <a data-toggle="dropdown" class="dropdown-toggle" href="#"><span class="glyphicon glyphicon-console"></span> Angular <b class="caret"></b></a>
		                            <ul role="menu" class="dropdown-menu">
		                                <li><a href="${contextPath}/example/angular1" target="_top">1 - Simple Controller</a></li>
		                                <li><a href="${contextPath}/example/angular2" target="_top">2 - Guessing Game</a></li>
		                                <li><a href="${contextPath}/example/angular3" target="_top">3 - select2 Dropdown</a></li>
		                                <li><a href="${contextPath}/example/angular4" target="_top">4 - Page with a Timer</a></li>
                                        <li><a href="${contextPath}/example/angular5" target="_top">5 - select2 Page with a Timer</a></li>
                                        <li><a href="${contextPath}/example/angular6" target="_top">6 - Show $location Info</a></li>
                                        <li><a href="${contextPath}/example/angular7" target="_top">7 - Share Data Between 2 Controllers</a></li>
		                                <li class="divider"></li>
		                            </ul>
		                        </li>
		                        <li class="dropdown">
		                            <a data-toggle="dropdown" class="dropdown-toggle" href="#"><span class="glyphicon glyphicon-folder-close"></span> REST <b class="caret"></b></a>
		                            <ul role="menu" class="dropdown-menu">
		                                <li><a href="${contextPath}/rest/users" target="_top">users</a></li>
		                                <li><a href="${contextPath}/rest/users2" target="_top">users2</a></li>
		                                <li class="divider"></li>
		                            </ul>
		                        </li>
		                    </ul>

		                    <ul class="nav navbar-nav navbar-right">
		                        <li><a href="#">Contact</a></li>
		                    </ul>
		                </div>
		            </nav>
		        </td>
		    </tr>
</table>