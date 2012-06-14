<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:define id="asset" name="sendEcardForm" property="asset" />


<%@include file="../inc/asset_title.jsp"%>

<head>

	<title><bright:cmsWrite identifier="title-send-e-card" filter="false"/></title>
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="send_ecard"/>
	

	<logic:notPresent name="ecardSent">
		<script type="text/JavaScript">
			$j(function () {
	  			$j('#recipientEmail').focus();
	 		});
		</script>
	</logic:notPresent>
	
</head>
<%@include file="../inc/set_this_page_url.jsp"%>

<body>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-send-e-card"/></h1>
	
	<div class="head">
		<%@include file="../inc/inc_asset_type.jsp"%>
		<a href="../action/viewAsset?id=${asset.id}"><bright:cmsWrite identifier="link-back-image-details" filter="false" replaceVariables="true" /></a>
	</div>
	
	<logic:present name="ecardSent">
		<c:if test="${ecardSent}">
			<div class="confirm">
				<c:set var="recipientEmail"><bright:write name="sendEcardForm" property="recipientEmail" /></c:set>
				<bright:cmsWrite identifier="snippet-e-card-sent" filter="false" replaceVariables="true" />
				<bean:define id="ecardSent" value="false" />
			</div>
		</c:if>
	</logic:present>
	
	<h2><bean:write name="sendEcardForm" property="asset.searchName" filter="false"/></h2>
	
	<c:set var="thumbSrc" value="../servlet/display?file=${sendEcardForm.asset.displayPreviewImageFile.path}"/>	
	<p><img class="image" src="<bean:write name='thumbSrc'/>" /></p>
	
	<p>
		<logic:equal name="sendEcardForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="sendEcardForm" property="errors" id="errorText">
					<bean:write name="errorText" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</p>
	
	
	<h2><bright:cmsWrite identifier="heading-send-e-card"/> <bright:cmsWrite identifier="snippet-details"/></h2>
	
	<html:form action="sendEcard" method="post">
		<html:hidden name="sendEcardForm" property="asset.id"/>
		<html:hidden name="sendEcardForm" property="asset.displayPreviewImageFile.path"/>
		
		<table class="form" cellspacing="0" cellpadding="0" summary="Send as e-card form">
			
			<tr>
				<th><label for="recipientEmail"><bright:cmsWrite identifier="label-send-to"/><span class="required">*</span></label></th>
				<td><html:text property="recipientEmail" styleId="recipientEmail"  styleClass="text" size="16" maxlength="200" /></td>
			</tr>
		
			<tr>
				<th>
					<label for="senderName"><bright:cmsWrite identifier="label-your"/> <bright:cmsWrite identifier="label-name"/></label>
				</th> 
				<td>
				<c:choose>
					<c:when test="${userprofile.isLoggedIn}">
						<html:text property="senderName" styleClass="text" size="16" maxlength="200" value="${userprofile.user.forename}" />
					</c:when>
					<c:otherwise>
						<html:text property="senderName" styleClass="text" size="16" maxlength="200" /> 
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			
			<tr>
				<th>
					<label for="senderEmail"><bright:cmsWrite identifier="label-your"/> <bright:cmsWrite identifier="label-email"/><span class="required">*</span></label>
				</th>
				<td>
				<c:choose>
					<c:when test="${userprofile.isLoggedIn}">
						<html:text property="senderEmail" styleClass="text" size="16" maxlength="200" value="${userprofile.user.emailAddress}" />
					</c:when>
					<c:otherwise>
						<html:text property="senderEmail" styleClass="text" size="16" maxlength="200" />
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			
			<tr>
				<th>
					<label for="message"><bright:cmsWrite identifier="label-your"/> <bright:cmsWrite identifier="label-message"/></label>
				</th> 
				<td>
					<html:textarea property="message" styleClass="textarea" rows="3" cols="10" />
				</td>
			</tr>
			
			
			<tr><th>&nbsp;</th></tr>
				
			<tr>
				<th style="padding-top:10px;"><bright:cmsWrite identifier="label-captcha-security-check"/></th>
				<td colspan="2" style="padding-top:10px;">
					
					<img src="../captchaImg" style="width:203px; margin-bottom:0.4em; display:block;"/> 
					<label for="capAnswer"><bright:cmsWrite identifier="snippet-enter-captcha-letters" filter="false"/></label><br/>
					<input name="capAnswer" id="capAnswer" style="margin-top:0.4em;"/>
				</td>
			</tr>
			
			<tr>
				<th>&nbsp;</th>
				<td><input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-send" filter="false" />" /></td>
			</tr>

		</table>
	</html:form>
	
	<p><bright:cmsWrite identifier="snippet-e-card-privacy" filter="false"/></p>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>