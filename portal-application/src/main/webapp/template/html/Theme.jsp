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

<html> 
  
<head>
	<title><portal:pageTitle /></title>

	<link href='<%=portalCssDir%>/Styles.css' rel="styleSheet" type="text/css">
	<link href='<%=portalCssDir%>/HelpStyles.css' rel="styleSheet" type="text/css">
	
	<portal:pageHead />
</head>

<body marginwidth="0" marginheight="0" >
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		<tr>
			<td width="100%" height="100%" valign="top">
				<TABLE width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
				    <TR>
				        <TD valign="top">
							<portal:rootPaneRender />
				        </td>
				    </tr> 
				</table>
			</td>
		</tr>
	</table>	
</body>

</html>
