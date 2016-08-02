<%@ taglib uri="/WEB-INF/tld/portal.tld" prefix="portal" %>

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

<%@ page import="java.util.Iterator"%>

<%@ page import="org.cyberelay.portal.ui.UIPane"%>
<%@ page import="org.cyberelay.portal.ui.ContainerPane"%>
<%@ page import="org.cyberelay.portal.PortalConstants"%>

<%
	ContainerPane container = (ContainerPane) request.getAttribute(PortalConstants.PORTAL_RENDERING_COMPONENT);
	Iterator<UIPane> children = container.children();
	boolean vertical = ContainerPane.VERTICAL.equals(container.getOrientation());
%>

<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">

<% if (!vertical) {%> <tr height="100%"> <% } %>

<%	while (children != null && children.hasNext()) {
      	UIPane child = children.next();
	    String width = child.getWidth() == null ? "" : "width='" + child.getWidth() + "'";
		
	    if (vertical) {%> <tr> <% } %>
	
		<td <%=width%> valign="top">
			<portal:componentRender component="<%= child %>"/>
		</td>
	
	<% if (vertical) {%> </tr> <% } %>
	
<% 	} %>

<% if (!vertical) {%> </tr> <% } %>

</table>


<table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">

<% if (!vertical) {%> <tr height="100%"> <% } %>

<%	while (children != null && children.hasNext()) {
      	UIPane child = children.next();
	    String width = child.getWidth() == null ? "" : "width='" + child.getWidth() + "'";
		
	    if (vertical) {%> <tr> <% } %>
	
		<td <%=width%> valign="top">
			<portal:componentRender component="<%= child %>"/>
		</td>
	
	<% if (vertical) {%> </tr> <% } %>
	
<% 	} %>

<% if (!vertical) {%> </tr> <% } %>

</table>
