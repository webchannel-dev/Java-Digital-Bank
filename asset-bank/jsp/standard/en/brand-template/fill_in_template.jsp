<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Francis Devereux	02-Dec-2008		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="title-create-doc-from-brand-template" filter="false" replaceVariables="true"/></title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="template"/>
</head>

<body id="brandtemplate">
	<%@include file="../inc/body_start.jsp"%>
	<%@include file="../customisation/payment_gateway_logo.jsp"%>

	<h1><bright:cmsWrite identifier="heading-create-doc-from-brand-template" filter="false"/></h1>

	<div class="head">
		<a href="../action/viewAsset?id=<c:out value="${asset.id}"/>"><bright:cmsWrite identifier="link-back-item" filter="false" /></a>
	</div>

	<bright:cmsWrite identifier="intro-create-doc-from-brand-template" filter="false"/>

	<form action="../action/fillInBrandTemplate" method="post" class="floated">
		<input type="hidden" name="assetId" value="<c:out value="${asset.id}"/>" />

		<c:forEach items="${templateFields}" var="templateField">
			<label for="label_${templateField.humanName}"><c:out value="${templateField.humanName}"/>:</label>
			<c:choose>
				<c:when test="${templateField.multiline}">
					<textarea name="<c:out value="tf_${templateField.fullyQualifiedName}"/>" cols="60" rows="4" id="label_${templateField.humanName}"><c:out value="${templateField.value}"/></textarea>
				</c:when>
				<c:otherwise>
					<input type="text" name="<c:out value="tf_${templateField.fullyQualifiedName}"/>" value="<c:out value="${templateField.value}"/>" id="label_${templateField.humanName}" />
				</c:otherwise>
			</c:choose>
			<br />
		</c:forEach>

		<div class="hr"></div>

		
		<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-create-from-brand-template-and-download" filter="false" />">
		<a href="viewAsset?id=<c:out value='${asset.id}'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</form>



<%@include file="../inc/body_end.jsp"%>
</body>
</html>
