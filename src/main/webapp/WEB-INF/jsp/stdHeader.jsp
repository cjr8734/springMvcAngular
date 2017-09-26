<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

        <style>
            .nav-side-menu {
                overflow: auto;
                font-family: courier;
                font-size: 16px;
                font-weight: 200;
                background-color: #2e353d;
                position: fixed;
                top: 0px;
                width: 300px;
                height: 100%;
                color: #e1ffff;
            }

            .nav-side-menu .brand {
                background-color: #23282e;
                line-height: 50px;
                display: block;
                text-align: center;
                font-size: 14px;
            }

            .nav-side-menu .toggle-btn {
                display: none;
            }

            .nav-side-menu ul,
            .nav-side-menu li {
                list-style: none;
                padding-left: 5px;
                margin-left: 0px;
                line-height: 35px;
                cursor: pointer;
            }

            .nav-side-menu ul :not(collapsed) .arrow:before,
            .nav-side-menu li :not(collapsed) .arrow:before {
                font-family: FontAwesome;
                content: "\f078";
                display: inline-block;
                padding-left: 10px;
                padding-right: 10px;
                vertical-align: middle;
                float: right;
            }

            .nav-side-menu ul .active,
            .nav-side-menu li .active {
                color: #d19b3d;
            }

            .nav-side-menu ul .sub-menu li.active,
            .nav-side-menu li .sub-menu li.active {
                color: #d19b3d;
            }

            .nav-side-menu ul .sub-menu li.active a,
            .nav-side-menu li .sub-menu li.active a {
                color: #d19b3d;
            }

            .nav-side-menu ul .sub-menu a.active li,
            .nav-side-menu li .sub-menu a.active li {
                color: #d19b3d;
            }

            .nav-side-menu ul .sub-menu li,
            .nav-side-menu li .sub-menu li {
                background-color: #181c20;
                border: none;
                line-height: 28px;
                border-bottom: 1px solid #23282e;
                margin-left: 0px;
            }

            .nav-side-menu ul .sub-menu li:hover,
            .nav-side-menu li .sub-menu li:hover {
                padding-left: 10px;
                background-color: #020203;
            }

            .nav-side-menu ul .sub-menu li:before,
            .nav-side-menu li .sub-menu li:before {
                font-family: FontAwesome;
                content: "\f105";
                display: inline-block;
                padding-left: 10px;
                padding-right: 10px;
                vertical-align: middle;
            }

            .nav-side-menu li {
                padding-left: 0px;
                border-left: 3px solid #2e353d;
                border-bottom: 1px solid #23282e;
                transition: all 1s ease;
            }

            .nav-side-menu li a {
                text-decoration: none;
                color: #e1ffff;
            }

            .nav-side-menu a li {
                text-decoration: none;
                color: #e1ffff;
            }

            .nav-side-menu li a i {
                padding-left: 0px;
                width: 20px;
                padding-right: 20px;
            }


            .nav-side-menu li:hover {
                padding-left: 10px;
                border-left: 3px solid #d19b3d;
                background-color: #4f5b69;
                -webkit-transition: all 1s ease;
                -mox-transition: all 1s ease;
                -o-transition: all 1s ease;
                -ms-transition: all 1s ease;
                transition: all 1s ease;
            }

            @media (max-width: 767px) {
                .nav-side-menu {
                    position: relative;
                    width: 100%;
                    margin-bottom: 10px;
                }

                .nav-side-menu .toggle-btn {
                    display: block;
                    cursor: pointer;
                    position: absolute;
                    right: 10px;
                    top: 10px;
                    z-index: 10 !important;
                    padding: 3px;
                    background-color: #ffffff;
                    color: #000;
                    width: 40px;
                    text-align: center;
                }

                .brand {
                    text-align: left !important;
                    font-size: 22px;
                    padding-left: 20px;
                    line-height: 50px !important;
                }
            }

            @media (min-width: 767px) {
                .nav-side-menu .menu-list .menu-content {
                    display: block;
                }
            }

            body {
                margin: 0px;
                padding: 0px;
            }
        </style>

        <script>
            $(function() {
                var hrefloc = window.location.href;
                if (hrefloc.indexOf("?") > -1) {
                    hrefloc = hrefloc.substring(0, hrefloc.indexOf('?'));
                }
                $('a').each(function() {
                    if($(this).prop('href')==hrefloc) {
                        $(this).addClass('active');
                        $(this).parent().addClass('in');
                    }
                });
            });
        </script>

        <script>
            jQuery(document).on('click', '.collapsed', function (e)
            {
                jQuery('.collapse').collapse('hide');
            });
        </script>

        <script>
            jQuery(document).on('click', '.sub-menu', function (e)
            {
                $(this).parent().toggleClass('open');
            });
        </script>

        <div class="nav-side-menu">
            <div class="brand">Brand Logo</div>
            <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>

                <div class="menu-list">

                    <ul id="menu-content" class="menu-content collapse out">
                        <li>
                          <a href="https://localhost:8443/springMvcAngular/">
                          <i class="fa fa-home fa-fw fa-lg"></i> Home
                          </a>
                        </li>

                        <li data-toggle="collapse" data-target="#angular" class="collapsed">
                          <i class="fa fa-shield fa-fw fa-lg"></i> Angular Exercises
                          <span class="arrow"></span>
                        </li>
                        <ul class="sub-menu collapse" id="angular">
                            <a href="${contextPath}/example/angular1" target="_top"><li>1 - Simple Controller</li></a>
                            <a href="${contextPath}/example/angular2" target="_top"><li>2 - Guessing Game</li></a>
                            <a href="${contextPath}/example/angular3" target="_top"><li>3 - select2 Dropdown</li></a>
                            <a href="${contextPath}/example/angular4" target="_top"><li>4 - Page with a Timer</li></a>
                            <a href="${contextPath}/example/angular5" target="_top"><li>5 - select2 with Timer</li></a>
                            <a href="${contextPath}/example/angular6" target="_top"><li>6 - Show Location Info</li></a>
                            <a href="${contextPath}/example/angular7" target="_top"><li>7 - Share Controller Data</li></a>
                        </ul>

                        <li data-toggle="collapse" data-target="#practice" class="collapsed">
                          <i class="fa fa-file-code-o fa-fw fa-lg"></i> Practice Programs <span class="arrow"></span>
                        </li>
                        <ul class="sub-menu collapse" id="practice">
                          <a href="${contextPath}/grid1" target="_top"><li>PA Data Grid #1</li></a>
                          <a href="${contextPath}/map1" target="_top"><li>D3 Map #1</li></a>
                          <a href="${contextPath}/playground" target="_top"><li>Coding Playground</li></a>
                          <a href="${contextPath}/grid2" target="_top"><li>PA Data Grid #2</li></a>
                        </ul>

                        <li data-toggle="collapse" data-target="#rest" class="collapsed">
                          <i class="fa fa-database fa-fw fa-lg"></i> REST
                          <span class="arrow"></span>
                        </li>
                        <ul class="sub-menu collapse" id="rest">
                            <a href="${contextPath}/rest/PA" target="_top"><li>PA Data</li></a>
                        </ul>

                        <li data-toggle="collapse" data-target="#new" class="collapsed">
                          <i class="fa fa-file-o fa-fw fa-lg"></i> New <span class="arrow"></span>
                        </li>
                        <ul class="sub-menu collapse" id="new">
                          <li>New New 1</li>
                          <li>New New 2</li>
                          <li>New New 3</li>
                        </ul>

                    </ul>
             </div>
        </div>
