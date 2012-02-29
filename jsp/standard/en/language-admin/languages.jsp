<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		31-Oct-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Languages</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="multi-language"/>
	<bean:define id="pagetitle" value="Languages"/>
	<bean:define id="tabId" value="languages"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>

	<p>
		This page lists the current languages. You may suspend, resume or edit any existing languages that you have added, plus add further languages using the relevant links.
	</p>

	<div class="hr"></div>

	<table cellspacing="0" class="list highlight" summary="List of marketing groups">	
		<thead>	
			<tr>
				<th>Name:</th>
				<th>Native Name:</th>
				<th>Code:</th>
				<th>Latin Alphabet?</th>
				<th>Right-to-Left?</th>
				<th>Suspended?</th>
				<th>Default?</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
		<logic:iterate name="languageForm" property="languages" id="language" indexId="languageIndex">
			<tr>
				<td><bean:write name="language" property="name"/></td>
				<td>
					<c:if test="${not empty language.nativeName}">	
						<bean:write name="language" property="nativeName" filter="false"/>
					</c:if>
					<c:if test="${empty language.nativeName}">
						<span class="disabled">none</span>
					</c:if>
				</td>
				<td><bean:write name="language" property="code"/></td>
				<td><c:if test="${language.usesLatinAlphabet}">yes</c:if><c:if test="${not language.usesLatinAlphabet}">no</c:if></td>
				<td><c:if test="${language.rightToLeft}">yes</c:if><c:if test="${not language.rightToLeft}">no</c:if></td>
				<c:if test="${language.default}">
					<td><c:if test="${language.suspended}">yes</c:if><c:if test="${not language.suspended}">no</c:if></td>
				</c:if>
				<c:if test="${not language.default}">
					<td><c:if test="${language.suspended}"><a href="../action/resumeLanguage?id=<bean:write name='language' property='id'/>" onclick="return confirm('Are you sure you want to activate this language?');">yes</a></c:if><c:if test="${not language.suspended}"><a href="../action/suspendLanguage?id=<bean:write name='language' property='id'/>" onclick="return confirm('Are you sure you want to suspend this language?');">no</a></c:if></td>
				</c:if>
				<td><c:if test="${language.default}">yes</c:if><c:if test="${not language.default}">no</c:if></td>
				<td>
					<a href="../action/viewEditLanguage?id=<bean:write name='language' property='id'/>">edit</a>
					<c:if test="${not language.default}">
						 | <a href="../action/deleteLanguage?id=<bean:write name='language' property='id'/>" onclick="return confirm('Are you sure you want to delete this language? All content created for this language will be deleted and cannot be recovered.\n\nAlso, be aware that any users currently viewing the site in this language may experience errors - it is advisable to suspend a language for a period of time before deleting it.')">delete</a>
					</c:if>
				</td>
			</tr>
		</logic:iterate>
		</tbody>
	</table>
	
	
	<p><a href="../action/viewAddLanguage">Add a new language &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>