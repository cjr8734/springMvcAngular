/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.6.v20170531
 * Generated at: 2017-09-07 13:29:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class angular1_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(8);
    _jspx_dependants.put("file:/C:/Users/cjrey/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar", Long.valueOf(1504634054500L));
    _jspx_dependants.put("jar:file:/C:/Users/cjrey/.m2/repository/org/springframework/spring-webmvc/4.3.10.RELEASE/spring-webmvc-4.3.10.RELEASE.jar!/META-INF/spring-form.tld", Long.valueOf(1500565206000L));
    _jspx_dependants.put("/WEB-INF/jsp/stdJspIncludes.jsp", Long.valueOf(1504621195078L));
    _jspx_dependants.put("file:/C:/Users/cjrey/.m2/repository/net/jawr/jawr-core/3.8/jawr-core-3.8.jar", Long.valueOf(1504620884065L));
    _jspx_dependants.put("jar:file:/C:/Users/cjrey/.m2/repository/org/springframework/spring-webmvc/4.3.10.RELEASE/spring-webmvc-4.3.10.RELEASE.jar!/META-INF/spring.tld", Long.valueOf(1500565206000L));
    _jspx_dependants.put("jar:file:/C:/Users/cjrey/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar!/META-INF/c.tld", Long.valueOf(1425993070000L));
    _jspx_dependants.put("file:/C:/Users/cjrey/.m2/repository/org/springframework/spring-webmvc/4.3.10.RELEASE/spring-webmvc-4.3.10.RELEASE.jar", Long.valueOf(1503943127432L));
    _jspx_dependants.put("jar:file:/C:/Users/cjrey/.m2/repository/net/jawr/jawr-core/3.8/jawr-core-3.8.jar!/META-INF/jawr.tld", Long.valueOf(1440798472000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, false, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("        ");
      out.write("\r\n");
      out.write("        \r\n");
      out.write("        \r\n");
      out.write("        \r\n");
      out.write("\r\n");
      out.write("        ");
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("        <!DOCTYPE HTML>\r\n");
      out.write("\r\n");
      out.write("        <html lang=\"en\">\r\n");
      out.write("\r\n");
      out.write("        <head>\r\n");
      out.write("            <title>Angular #1</title>\r\n");
      out.write("        </head>\r\n");
      out.write("\r\n");
      out.write("        <body data-ng-controller=\"PhoneListCtrl as ctrl\" data-ng-app=\"myApp\">\r\n");
      out.write("\r\n");
      out.write("            <h2>angular1.jsp</h2>\r\n");
      out.write("\r\n");
      out.write("            <br/>\r\n");
      out.write("            <br/>\r\n");
      out.write("            \t<button data-ng-click=\"ctrl.makeValidRestCall()\">Make Valid Rest Call</button>&nbsp;&nbsp;\r\n");
      out.write("            \t<button data-ng-click=\"ctrl.makeInvalidRestCall()\">Make Invalid Rest Call</button>&nbsp;&nbsp;\r\n");
      out.write("            \t<button data-ng-click=\"ctrl.clearResults()()\">Clear Results</button>\r\n");
      out.write("            <br/>\r\n");
      out.write("            \tStatus: {{ctrl.statusMessage}}<br/>\r\n");
      out.write("            \t<textarea data-ng-model=\"ctrl.restCallResults\" style=\"width: 400px; height: 100px\"></textarea>\r\n");
      out.write("            <br/>\r\n");
      out.write("            <br/>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("              <ul>\r\n");
      out.write("                <li data-ng-repeat=\"phone in phones\">\r\n");
      out.write("                  <span>{{phone.name}}</span>\r\n");
      out.write("                  <p>{{phone.snippet}}</p>\r\n");
      out.write("                </li>\r\n");
      out.write("              </ul>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("             <p>Nothing here {{'yet' + '!'}}</p>\r\n");
      out.write("\r\n");
      out.write("             <p>1 + 2 = {{ 1 + 2 }}</p>\r\n");
      out.write("\r\n");
      out.write("            <br/>\r\n");
      out.write("\r\n");
      out.write("            Hello ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${userInfo.username}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(" <br/>\r\n");
      out.write("\r\n");
      out.write("            <br/>\r\n");
      out.write("            <br/>\r\n");
      out.write("\r\n");
      out.write("            <script type=\"text/javascript\">\r\n");
      out.write("                var gsContextPath = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("';\r\n");
      out.write("                var phonecatApp = angular.module('myApp', []);\r\n");
      out.write("\r\n");
      out.write("                phonecatApp.controller('PhoneListCtrl', function ($scope, $http, $log)\r\n");
      out.write("                {\r\n");
      out.write("                  var self = this;\r\n");
      out.write("                  self.restCallResults = '';\r\n");
      out.write("                  self.statusMessage = '';\r\n");
      out.write("\r\n");
      out.write("                  $scope.phones = [\r\n");
      out.write("                    {'name': 'Nexus S',\r\n");
      out.write("                     'snippet': 'Fast just got faster with Nexus S.'},\r\n");
      out.write("                    {'name': 'Motorola XOOMâ¢ with Wi-Fi',\r\n");
      out.write("                     'snippet': 'The Next, Next Generation tablet.'},\r\n");
      out.write("                    {'name': 'MOTOROLA XOOMâ¢',\r\n");
      out.write("                     'snippet': 'The Next, Next Generation tablet.'}\r\n");
      out.write("                  ];\r\n");
      out.write("                  /*******************************************************************\r\n");
      out.write("                  * makeValidRestCall()\r\n");
      out.write("                  *\r\n");
      out.write("                  * Call a REST end point that will succeed\r\n");
      out.write("                  *******************************************************************/\r\n");
      out.write("                  self.makeValidRestCall = function()\r\n");
      out.write("                   {\r\n");
      out.write("                    var sUrl = gsContextPath + '/rest/users';\r\n");
      out.write("                    $log.debug(\"makeRestCall() started making a call to \", sUrl);\r\n");
      out.write("\r\n");
      out.write("   \t\t            $http.get(sUrl)\r\n");
      out.write("                    .then(function(response) {\r\n");
      out.write("                        var data = response.data;\r\n");
      out.write("                        var status = response.status;\r\n");
      out.write("                        var statusText = response.statusText;\r\n");
      out.write("                        var headers = response.headers;\r\n");
      out.write("                        var config = response.config;\r\n");
      out.write("                        // I got a response from the end point\r\n");
      out.write("                        self.statusMessage = 'I got a response with status=' + status;\r\n");
      out.write("\r\n");
      out.write("                        // Convert the list of Java objects into a string\r\n");
      out.write("                  \t    var sFormattedResults = self.getUserListAsString(data);\r\n");
      out.write("\r\n");
      out.write("       \t                self.restCallResults = self.restCallResults + sFormattedResults + \"\\n\";\r\n");
      out.write("       \t            })\r\n");
      out.write("\r\n");
      out.write("                  \t            .then(function() {\r\n");
      out.write("                  \t                self.statusMessage = 'Angular Call Finished';\r\n");
      out.write("                  \t            });\r\n");
      out.write("\r\n");
      out.write("                  \t    self.statusMessage = 'Made Rest Call....Waiting for results.';\r\n");
      out.write("                  \t};\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("                  \t/*******************************************************************\r\n");
      out.write("                  \t * makeInvalidRestCall()\r\n");
      out.write("                  \t *\r\n");
      out.write("                  \t * Call a REST end point that will throw an error\r\n");
      out.write("                  \t *******************************************************************/\r\n");
      out.write("                  \tself.makeInvalidRestCall = function()\r\n");
      out.write("                  \t{\r\n");
      out.write("                  \t    // This url will throw a RunTime Exception\r\n");
      out.write("                  \t    var sUrl = gsContextPath + '/rest/users/exception';\r\n");
      out.write("                  \t    $log.debug(\"makeRestCall() started making a call to \", sUrl);\r\n");
      out.write("\r\n");
      out.write("                  \t    $http.get(sUrl)\r\n");
      out.write("                                            .then(function(response) {\r\n");
      out.write("                                                var data = response.data;\r\n");
      out.write("                                                var status = response.status;\r\n");
      out.write("                                                var statusText = response.statusText;\r\n");
      out.write("                                                var headers = response.headers;\r\n");
      out.write("                                                var config = response.config;\r\n");
      out.write("                                                // I got a response from the end point\r\n");
      out.write("                                                self.statusMessage = 'I got a response with status=' + status;\r\n");
      out.write("\r\n");
      out.write("                                                // Convert the list of Java objects into a string\r\n");
      out.write("                                          \t    var sFormattedResults = self.getUserListAsString(data);\r\n");
      out.write("\r\n");
      out.write("                               \t                self.restCallResults = self.restCallResults + sFormattedResults + \"\\n\";\r\n");
      out.write("                               \t            })\r\n");
      out.write("\r\n");
      out.write("                  \t    self.statusMessage = 'Made Rest Call....Waiting for results.';\r\n");
      out.write("\r\n");
      out.write("                  \t};\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("                  \tself.getUserListAsString = function(aUserList)\r\n");
      out.write("                  \t{\r\n");
      out.write("                  \t    var sResults = '';\r\n");
      out.write("\r\n");
      out.write("                  \t    for(var i=0; i<aUserList.length; i++)\r\n");
      out.write("                  \t    {\r\n");
      out.write("                  \t        var user = aUserList[i];\r\n");
      out.write("                  \t        sResults = sResults + 'username=' + user.username + '   IsAdministrator=' + user.isAdministrator + \"\\n\";\r\n");
      out.write("                  \t    }\r\n");
      out.write("\r\n");
      out.write("                  \t    return sResults;\r\n");
      out.write("                  \t};\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("                  \tself.clearResults = function()\r\n");
      out.write("                  \t{\r\n");
      out.write("                  \t    $log.debug(\"clearResults() started\");\r\n");
      out.write("                  \t    self.restCallResults = '';\r\n");
      out.write("                  };\r\n");
      out.write("                });\r\n");
      out.write("\r\n");
      out.write("            </script>\r\n");
      out.write("        </body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    try {
      _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fset_005f0.setParent(null);
      // /WEB-INF/jsp/stdJspIncludes.jsp(10,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fset_005f0.setVar("contextPath");
      // /WEB-INF/jsp/stdJspIncludes.jsp(10,0) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
      _jspx_th_c_005fset_005f0.setValue(new org.apache.jasper.el.JspValueExpression("/WEB-INF/jsp/stdJspIncludes.jsp(10,0) '${pageContext.request.contextPath}'",_jsp_getExpressionFactory().createValueExpression(_jspx_page_context.getELContext(),"${pageContext.request.contextPath}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
      int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
      if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } finally {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    }
    return false;
  }
}
