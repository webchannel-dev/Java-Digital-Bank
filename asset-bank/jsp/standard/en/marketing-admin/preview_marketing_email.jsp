<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	05-Sep-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting settingName="supportMultiLanguage" id="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Send Marketing Email</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="helpsection" value="marketing-preview-email"/>
	<bean:define id="tabId" value="sendEmail"/>

	<link href="../css/emailPreview.css" rel="stylesheet" type="text/css" media="all" />
	<%@include file="../inc/inc_mce_editor.jsp"%>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>
	
	<h2>Send Marketing Email</h2>

	<logic:present  name="sendMarketingEmailForm">
		<logic:equal name="sendMarketingEmailForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="sendMarketingEmailForm" property="errors" id="error">	
						<bright:writeError name="error" />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<html:form action="sendMarketingEmail" method="post">
		
		<html:hidden name="sendMarketingEmailForm" property="assetBoxId"/>
		<html:hidden name="sendMarketingEmailForm" property="fromAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="toAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="ccAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="bccAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="emailContent.subject"/>
		<html:hidden name="sendMarketingEmailForm" property="emailContent.body"/>
		
		<logic:notEmpty name="sendMarketingEmailForm" property="emailContent.translations">
			<logic:iterate name="sendMarketingEmailForm" property="emailContent.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].language.id"/>
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].language.name"/>
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].subject"/>
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].body"/>
				</logic:greaterThan>
			</logic:iterate>	
		</logic:notEmpty>
		
		<bean:parameter multiple="true" name="selectedUsers" id="selectedUsers"/>
		<logic:iterate name="selectedUsers" id="userId">
			<input type="hidden" name="selectedUsers" value="<bean:write name="userId"/>"/>
		</logic:iterate>
		
		<logic:iterate name="sendMarketingEmailForm" property="previewEmails" id="email" indexId="index">
			<table class="email" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" class="language">
						<h2><c:out value="${sendMarketingEmailForm.languages[index].name}"/></h2>
					</td>
				</tr>
				<tr class="odd">
					<th>To:</th>
					<td>
						<logic:notEmpty name="email" property="toList">
							<logic:iterate name="email" property="toList" id="address" indexId="index"><c:if test="${index>0}">;&nbsp;</c:if><bean:write name="address" property="address" /></logic:iterate>
						</logic:notEmpty>
						<logic:empty name="email" property="toList">
							<span class="disabled">n/a</span>
						</logic:empty>
					</td>
				</tr>
				<tr>
					<th>CC:</th>
					<td>
						<logic:notEmpty name="email" property="ccList">
							<logic:iterate name="email" property="ccList" id="address" indexId="index"><c:if test="${index>0}">;&nbsp;</c:if><bean:write name="address" property="address" /></logic:iterate>
						</logic:notEmpty>
						<logic:empty name="email" property="ccList">
							<span class="disabled">n/a</span>
						</logic:empty>
					</td>
				</tr>
				<tr class="odd">
					<th>BCC:</th>
					<td>
						<logic:notEmpty name="email" property="bccList">
							<logic:iterate name="email" property="bccList" id="address" indexId="index"><c:if test="${index>0}">;&nbsp;</c:if><bean:write name="address" property="address" /></logic:iterate>
						</logic:notEmpty>
						<logic:empty name="email" property="bccList">
							<span class="disabled">n/a</span>
						</logic:empty>
					</td>
				</tr>
				<tr>
					<th>From:</th>
					<td><bean:write name="email" property="fromAddress.address" /></td>
				</tr>
				<tr class="odd">
					<th>Subject:</th>
					<td><bean:write name="email" property="subject" /></td>
				</tr>
				<tr>
					<td colspan="2" class="body"><bean:write name="email" property="htmlMsg" filter="false"/></td>
				</tr>		
			</table>
		</logic:iterate>

		<logic:greaterThan name="sendMarketingEmailForm" property="assetBoxId" value="0">
			<logic:match name="sendMarketingEmailForm" property="emailContent.body" value="#emailId#">
		
				<p>If you wish to give the collection of assets viewed through this email a title and/or an introduction, enter it here:</p>
			
				<table class="form" cellspacing="0" cellpadding="0">
					<tr>
						<th>
							Title:
						</th>
						<td>
							<html:text styleClass="text" name="sendMarketingEmailForm" property="marketingEmail.name" maxlength="100"/>
						</td>
					</tr>
					<logic:notEmpty name="sendMarketingEmailForm" property="marketingEmail.translations">
						<logic:iterate name="sendMarketingEmailForm" property="marketingEmail.translations" id="translation" indexId="tIndex">
							<logic:greaterThan name="translation" property="language.id" value="0">
								<tr>
									<th class="translation">
										<label for="name<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
									</th>
									<td>
										<html:hidden name="sendMarketingEmailForm" property="marketingEmail.translations[${tIndex}].language.id"/>
										<html:hidden name="sendMarketingEmailForm" property="marketingEmail.translations[${tIndex}].language.name"/>
										<input type="text" name="marketingEmail.translations[<bean:write name='tIndex'/>].name" maxlength="100" size="40" id="name<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" />"/>
									</td>
								</tr>
							</logic:greaterThan>
						</logic:iterate>	
					</logic:notEmpty>
					<tr>
						<th>
							Introduction:
						</th>
						<td>
							<html:textarea styleClass="editor" name="sendMarketingEmailForm" property="marketingEmail.introduction"/>
						</td>
					</tr>
					<logic:notEmpty name="sendMarketingEmailForm" property="marketingEmail.translations">
						<logic:iterate name="sendMarketingEmailForm" property="marketingEmail.translations" id="translation" indexId="tIndex">
							<logic:greaterThan name="translation" property="language.id" value="0">
								<tr>
									<th class="translation">
										<label for="introduction<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
									</th>
									<td>
										<textarea class="editor" name="marketingEmail.translations[<bean:write name='tIndex'/>].introduction" id="introduction<bean:write name='tIndex'/>"><bean:write name="translation" property="introduction" filter="false"/></textarea>
									</td>
								</tr>
							</logic:greaterThan>
						</logic:iterate>	
					</logic:notEmpty>		
				</table>
			</logic:match>
		</logic:greaterThan>

		<div class="hr"></div>
		
		<div class="buttonHolder">
			<input type="submit" class="button" value="Send" onclick="return confirm('Are you sure you want to send this email?');" /> 
		</div>
	</html:form>
	
	<form name="sendMarketingEmailForm" action="editMarketingEmail" method="post">
	
		<html:hidden name="sendMarketingEmailForm" property="assetBoxId"/>
		<html:hidden name="sendMarketingEmailForm" property="fromAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="toAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="ccAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="bccAddress"/>
		<html:hidden name="sendMarketingEmailForm" property="emailContent.subject"/>
		<html:hidden name="sendMarketingEmailForm" property="emailContent.body"/>
		
		<logic:notEmpty name="sendMarketingEmailForm" property="emailContent.translations">
			<logic:iterate name="sendMarketingEmailForm" property="emailContent.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].language.id"/>
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].language.name"/>
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].subject"/>
					<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].body"/>
				</logic:greaterThan>
			</logic:iterate>	
		</logic:notEmpty>
		
		<bean:parameter multiple="true" name="selectedUsers" id="selectedUsers"/>
		<logic:iterate name="selectedUsers" id="userId">
			<input type="hidden" name="selectedUsers" value="<bean:write name="userId"/>"/>
		</logic:iterate>
		
		<input type="submit" name="back" value="Back" class="button" id="backButton" />		
	</form>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>