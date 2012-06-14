<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-asset-file-editor"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="client-side-edit"/>
	<bean:parameter id="readonly" name="readonly" value="false"/>

</head>

<body id="clientSideEditPage">

	<%@include file="../inc/body_start.jsp"%>

	<html:form action="clientSideEditPrepAction" enctype="multipart/form-data" method="post" styleClass="floated">
		
		<div style="min-height:300px">
			<jsp:include flush="true" page="${clientSideEditPrepForm.includePage}"/>

			<input type="hidden" name="assetId" value="<bean:write name='clientSideEditPrepForm' property='asset.id'/>" />
			<html:hidden name="clientSideEditPrepForm" property="step"/>
			<html:hidden name="clientSideEditPrepForm" property="checkedOutToUserId"/>
			<html:hidden name="clientSideEditPrepForm" property="returnUrl"/>
			<input type="hidden" name="readonly" value="<bean:write name='readonly'/>" />
			
		</div>
		
		<div class="hr"></div>
		<a href="<bean:write name='clientSideEditPrepForm' property='returnUrl'/>" <c:if test="${clientSideEditPrepForm.isNextStep || clientSideEditPrepForm.isPreviousStep}">class="backLink"</c:if>><bright:cmsWrite identifier="link-back-to" filter="false"/> <bright:cmsWrite identifier="item"/></a>
		
		<c:choose>
			<c:when test="${clientSideEditPrepForm.isPreviousStep}">
				<input type="submit" class="button flush floated" name="back" value="Prev" /> 
			</c:when>
			<c:otherwise>
				<input type="submit" class="button flush floated" name="back" value="Prev" disabled="disabled" />
			</c:otherwise>
		</c:choose>
		
		
		<c:choose>
			<c:when test="${clientSideEditPrepForm.isNextStep}">
				<input type="submit" class="button flush floated" name="next" id="btnNext" value="Next" /> 
			</c:when>
			<c:otherwise>
				<input type="submit" class="button flush floated" name="next" id="btnNext" value="Next" disabled="disabled" />
			</c:otherwise>
		</c:choose>
		
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>