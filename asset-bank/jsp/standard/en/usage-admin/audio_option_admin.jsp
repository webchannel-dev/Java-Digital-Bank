<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	14-Dec-2011		Created
	 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<bean:define id="pagetitle" value="Audio Download Options"/>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" /> </title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="usage"/>
	<bean:define id="tabId" value="audio"/>
	<bean:define id="helpsection" value="downloadAudioOptions"/>
	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1>
	<%@include file="inc_download_options_tabs.jsp"%>
	
	<logic:present name="audioOptionForm">
		<logic:equal name="audioOptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="audioOptionForm" property="errors" id="error">
					<bright:writeError name="error" /><br />	
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2>Manage Audio Download Options</h2>
	
	<logic:notEmpty name="audioOptionForm" property="listDownloadOptions">
		<table cellspacing="0" class="list highlight" summary="List of Audio Download Options">
			<thead>
				<tr>
					<th>Label</th>
					<th>Bitrate (bps)</th>
					<th colspan="4">Actions</th>
				</tr>		
			</thead>
			<tbody>
			<logic:iterate name="audioOptionForm" property="listDownloadOptions" id="option">
				<tr>
					<td><bean:write name="option" property="label"/></td>
					<td><bean:write name="option" property="bitrate.displayNumber"/></td>
					<td class="action">
						[<a href="moveAudioOption?id=<bean:write name='option' property='id'/>&amp;up=true">up</a>]
					</td>
					<td class="action">
						[<a href="moveAudioOption?id=<bean:write name='option' property='id'/>&amp;up=false">down</a>]
					</td>
					<td class="action">
						[<a href="viewUpdateAudioOption?id=<bean:write name='option' property='id'/>">edit</a>]
					</td>				
					<td>
						[<a href="deleteAudioOption?id=<bean:write name='option' property='id'/>" onclick="return confirm('Are you sure you want to delete this audio option from the list?');" title="Delete this audio option">X</a>]
					</td>
				</tr>
			</logic:iterate>
		
			</tbody>
		</table>		
	</logic:notEmpty>
	
	<logic:empty name="audioOptionForm" property="listDownloadOptions">
		<p>There are currently no Audio Download Options.</p>
	</logic:empty>
	
	<br/>
	
	<p><a href="../action/viewUpdateAudioOption">Add a new option &raquo;</a></p>
		

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>