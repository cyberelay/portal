<%--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>

<%@ taglib uri="/WEB-INF/tld/portal.tld" prefix="portal"%>

<%@page import="org.cyberelay.portletcontainer.PortletApplicationRegistry"%>
<%@page import="org.cyberelay.portletcontainer.PortletApplication"%>
<%@page import="java.util.Iterator"%>

<html>

<portal:constants />

<%
	Iterator<PortletApplication> apps = PortletApplicationRegistry.getApplications();
%>

<head>
<title>Portlet Invocation Utility</title>
<link href='<%=portalCssDir%>/Styles.css' rel="styleSheet"
	type="text/css">
<link href='<%=portalCssDir%>/HelpStyles.css' rel="styleSheet"
	type="text/css">
</head>

<body marginwidth="0" marginheight="0">
<form action="" method="post">
<table width="600">
	<thead>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
	</thead>
	<tr>
		<td align="right">Portlet Application&nbsp;</td>
		<td><select name="application">
	<% 
	PortletApplication app = null;
	while(apps.hasNext()) {
		app = apps.next();
	%>
			<option value="<%= app.getServletContext().getContextPath() %>" ><%= app.getServletContext().getServletContextName() %></option>
	<%} %>
		</select></td>
	</tr>
	<tr>
		<td style="width: 156px" align="right">Portlet Name&nbsp;</td>
		<td><select name="portlet">
			<option selected="selected" value=""></option>
		</select></td>
	</tr>
	<tr>
		<td align="right">Portlet Mode&nbsp;</td>
		<td><select name="mode">
			<option selected="selected" value=""></option>
		</select></td>
	</tr>
	<tr>
		<td align="right">Window State&nbsp;</td>
		<td><select name="state">
			<option selected="selected" value=""></option>
		</select></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td align="left"><input type="submit" name="submit" value="Render portlet"></td>
	</tr>
</table>
</form>
</body>

</html>
