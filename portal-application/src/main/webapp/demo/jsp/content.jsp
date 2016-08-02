<%@ page import="org.cyberelay.portal.demo.model.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%
	Candidate selected = (Candidate)request.getAttribute("candidate");
%>
<TABLE border="1">
	<TBODY>
<% if(selected == null) {%>
		<TR>
			<TD> No candidate selected. </TD>
		</TR>
<% } else {%>
		<TR>
			<TD width="37">Name:</TD>
			<TD><%= selected.getName() %></TD>
		</TR>
		<TR>
			<TD width="37">Status:</TD>
			<TD><%= selected.getStatus() %></TD>
		</TR>
		<TR>
			<TD width="37">Comments:</TD>
			<TD><%= selected.getComments() %></TD>
		</TR>
		<TR>
			<TD width="37">Language:</TD>
			<TD><%= selected.getLanguages() %></TD>
		</TR>
		<TR>
			<TD width="37">Skillset:</TD>
			<TD><%= selected.getSkillset() %></TD>
		</TR>
		<TR>
			<TD width="37">Experiences:</TD>
			<TD><%= selected.getExperiences() %></TD>
		</TR>
<% } %>
	</TBODY>
</TABLE>

