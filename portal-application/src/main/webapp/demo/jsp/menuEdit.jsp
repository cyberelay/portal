<%@ taglib uri="/WEB-INF/tld/portlet.tld" prefix="portletAPI" %>

<portletAPI:init />

<FORM name="test" method="post" action='<portletAPI:createURI><portletAPI:URIAction name="select" /></portletAPI:createURI>'>
<TABLE border="1" width="186">
	<TBODY>
		<TR>
			<TD>Name:</TD>
			<TD><SELECT name="name" size="1">
				<OPTION value="Roger" selected>Roger Tang</OPTION>
				<OPTION value="May">May Chen</OPTION>
				<OPTION value="Eric">Eric NG</OPTION>
				<OPTION value="Paul">Paul Cheng</OPTION>
				<OPTION value="Daniel">Terry Li</OPTION>
				</SELECT>
			</TD> 
		</TR>
		<tr>
			<td colspan = "2" align="left"> 
				<INPUT type="submit" name="submit" value="Select" />
			</td>
		</tr>
	</TBODY>
</TABLE>
</FORM>
