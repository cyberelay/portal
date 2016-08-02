<%@ page session="false" buffer="none" %>

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

<%@ taglib uri="/WEB-INF/tld/portal.tld" prefix="portal" %>

<portal:constants/>

<%
	String borderColor="#CFD9E5"; 
	String tableHeight = "";
%>

<table border="0" width="100%" <%=tableHeight%> cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" <%=tableHeight%> cellpadding="0" cellspacing="0" class="portalPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<td bgcolor="<%=borderColor%>" width="1" height="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
					<td bgcolor="<%=borderColor%>" height="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
					<td bgcolor="<%=borderColor%>" width="1" height="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
				</tr>
				<tr height="1">
					<!-- left border -->
					<td bgcolor="<%=borderColor%>" width="1" height="12"><img alt="" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td class="portalPortletTitle" width="100%" nowrap align="left" valign="middle">
									&nbsp;<img alt="" border="0" width="1" height="12" align="absmiddle" src='<%=portalImageDir%>/minHeight.gif'>
								</td>
									<portal:portletEdit>
										<td class="portalPortletTitleIconBackground"><a href='<%=portletEditURL%>'><img border="0" align="absmiddle" class="portalPortletTitleIcon" src="<%=portalImageDir%>/edit.gif" alt=''	title=''></a></td>
									</portal:portletEdit>
									<portal:portletHelp>
										<td class="portalPortletTitleIconBackground"><a href='<%=portletHelpURL%>' target="portletHelpWindow" onClick="javascript: window.open('<%=portletHelpURL%>','portletHelpWindow','resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=450,height=260,screenX=200,screenY=200,top=200,left=200').focus();	return	false;">
										<img border="0" align="absmiddle" class="portalPortletTitleIcon" src="<%=portalImageDir%>/help.gif" alt='' title=''></a></td>
									</portal:portletHelp>
									<portal:portletMinimize>
										<td class="portalPortletTitleIconBackground"><a href='<%=portletMinimizeURL%>'><img border="0" align="absmiddle" class="portalPortletTitleIcon" src="<%=portalImageDir%>/minimize.gif" alt=''	title=''></a></td>
									</portal:portletMinimize>
									<portal:portletRestore>
										<td class="portalPortletTitleIconBackground"><a href='<%=portletRestoreURL%>'><img border="0" align="absmiddle" class="portalPortletTitleIcon" src="<%=portalImageDir%>/restore.gif" alt=''	title=''></a></td>
									</portal:portletRestore>
									<portal:portletMaximize>
										<td class="portalPortletTitleIconBackground"><a href='<%=portletMaximizeURL%>'><img border="0" align="absmiddle" class="portalPortletTitleIcon" src="<%=portalImageDir%>/maximize.gif" alt=''	title=''></a></td>
									</portal:portletMaximize>
							</tr>
						</table>
					</td>
					<td bgcolor="<%=borderColor%>" width="1" height="12"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
				</tr>
				 <!-- portlet body -->
					<tr height="100%">
						<td bgcolor="<%=borderColor%>" height="100%" width="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
						<td width="100%" valign="top">
							<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
								<tr>
									<td valign="top">
										<portal:portletRender /> 
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="<%=borderColor%>" width="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
					</tr>
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="<%=borderColor%>" width="1" height="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
					<td bgcolor="<%=borderColor%>" height="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
					<td bgcolor="<%=borderColor%>" width="1" height="1"><img alt="" border="0" width="1" height="1" src='<%=portalImageDir%>/dot.gif'></td>
				</tr>
			</table>

		</td>
	</tr>
</table>
