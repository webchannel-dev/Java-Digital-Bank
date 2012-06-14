<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Vivek Krishnan		08-Feb-2011		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>

<bright:applicationSetting id="sharedLightboxes" settingName="shared-lightboxes"/>
<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	

<head>
	<c:if test="${assetBoxForm.shared!=true}">
		<title><bright:cmsWrite identifier="title-edit-lightbox"/></title>
	</c:if> 
	<c:if test="${assetBoxForm.shared==true}">
		<title><bright:cmsWrite identifier="title-rename-lightbox"/></title>
	</c:if>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<c:set var="helpsection" value="manage-lightboxes"/>
	<bean:define id="tabId" value="manageAssetBoxes"/>
</head>

<bean:parameter id="url" name="url" value=""/>

<body id="editLightboxesPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_lightbox_tabs.jsp"%> 

	<logic:present name="assetBoxForm">
		<logic:equal name="assetBoxForm" property="hasErrors" value="true"> 
    		<div class="error">
    			<logic:iterate name="assetBoxForm" property="errors" id="errorText">
    				<bean:write name="errorText" filter="false"/><br />
    			</logic:iterate>
    		</div>
    	</logic:equal>
	</logic:present>
         
	<c:set var="prevName" value="${assetBoxForm.previousName}" />
	<c:if test="${assetBoxForm.shared!=true}">
		<title><bright:cmsWrite identifier="intro-edit-lightbox" filter="false" replaceVariables="true" /></title>
	</c:if> 
	<c:if test="${assetBoxForm.shared==true}">
		<title><bright:cmsWrite identifier="intro-rename-lightbox" filter="false" replaceVariables="true" /></title>
	</c:if>
	
	<html:form action="editAssetBox">
	<html:hidden name="assetBoxForm" property="currentAssetBoxId"/>
	<html:hidden name="assetBoxForm" property="shared"/>
	<html:hidden name="assetBoxForm" property="previousName"/>
	
		<table>
			<tr>
				<td><bright:cmsWrite identifier="label-new-name"/></td>
				<td><html:text property="name" size="20" maxlength="50"/></td>
			</tr>
			<c:if test="${!assetBoxForm.shared && publicLightboxes}">
				<tr>
					<td><bright:cmsWrite identifier="label-isPublic"/></td>
					<td><html:checkbox property="publicAvailabilty"/>(<bright:cmsWrite identifier="snippet-show-on-public-tab"/>)</td>
				</tr>
			</c:if>
		</table>
		
		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		
		<c:choose>
			<c:when test="${url != null && url != ''}">
				<a href="..<bean:write name='url'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			</c:when>
			<c:otherwise>
				<a href="../action/viewManageAssetBoxes" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			</c:otherwise>
		</c:choose>
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>