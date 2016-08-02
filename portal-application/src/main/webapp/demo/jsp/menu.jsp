<%@ page import="org.cyberelay.portal.demo.model.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%
	Iterator names = CandidateStore.getInstance().getCandidateNames();
%>
<TABLE border="1" width="100%">
	<TBODY>
<% while(names.hasNext()) { 
	String name = (String)names.next();
%>
		<TR><TD><A href='<portlet:actionURL><portlet:param name="pub.param.candidate" value="<%= name %>" /></portlet:actionURL>' ><%=name %></A></TD>
		</TR>
<% } %>

	</TBODY>
</TABLE>
