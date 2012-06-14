<%@include file="../inc/doctype_html.jsp"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright"%>

	<head>
		<title><bright:cmsWrite identifier="title-submit-asset-feedback" filter="false"/></title>
		<%@include file="../inc/head-elements.jsp"%>
		<bean:define id="helpsection" value="submit_feedback" />
	</head>

	<body>
		<%@include file="../inc/body_start.jsp"%>
		<h1><bright:cmsWrite identifier="heading-submit-asset-feedback" filter="false"/></h1>
		<div class="hr"></div>
		<bright:cmsWrite identifier="snippet-submit-asset-feedback-confirmation" filter="false"/>
		<div class="hr"></div>
		
		<a href="<bean:write name="submitAssetFeedbackForm" property="returnUrl"/>"><bright:cmsWrite identifier="link-back-item" filter="false"/></a>
		<%@include file="../inc/body_end.jsp"%>
	</body>
</html>

