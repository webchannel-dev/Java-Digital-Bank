<%@include file="../inc/doctype_html.jsp"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright"%>

<head>
<title><bright:cmsWrite identifier="title-submit-asset-feedback" filter="false"/></title>
<%@include file="../inc/head-elements.jsp"%>
<bean:define id="helpsection" value="submit_feedback" />
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-submit-asset-feedback" filter="false"/></h1>
	<html:form action="saveSubmitAssetFeedback" method="post" styleClass="floated">
		<input type="hidden" name="returnUrl" value="<bean:write name='submitAssetFeedbackForm' property='returnUrl'/>">
		<c:set var="downloadForm" value="${submitAssetFeedbackForm}" />
		<c:set var="assetTypeName"><bright:cmsWrite identifier="image" case="mixed" filter="false" /></c:set>
		<%@include file="inc_download_top.jsp"%>
		
		<div>
			<c:set var="assetForm" value="${submitAssetFeedbackForm}" />
			<bean:define id="asset" name="assetForm" property="asset" />
			<bean:define id="resultImgClass" value="image" />
			<bean:define id="ignoreCheckRestrict" value="yes" />
			<bean:define id="disablePreview" value="true" />
			<%@include file="../inc/view_thumbnail.jsp"%>
		</div>
		<br />

		<%-- Allow the user's organisation to be included in the snippet (for Adventist HealthCare) --%>
		<c:set var="user-organisation" value="${userprofile.user.organisation}" />
		<p><bright:cmsWrite identifier="snippet-submit-asset-feedback-request" replaceVariables="true"/></p>
		
	
		<label style="width: 100%"><bright:cmsWrite identifier="label-submit-feedback"/></label><br />
		<html:textarea property="userMessage" rows="10" cols="50" />
	
		<br />
	
			<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>">
		<input type="submit" name="saveButton" class="button flush"	value="<bright:cmsWrite identifier="button-submit"/>" />
		<a href="../action/viewAsset?id=<bean:write name='assetForm' property='asset.id' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>