<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		26-Aug-2007		Created.
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
	
	<title><bright:cmsWrite identifier="title-rename-lightbox" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<c:set var="helpsection" value="manage-lightboxes"/>
	<bean:define id="tabId" value="manageAssetBoxes"/>
</head>

<body id="renameLightboxesPage">

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
	<bright:cmsWrite identifier="intro-rename-lightbox" filter="false" replaceVariables="true" />

	
	<html:form action="renameAssetBox">
	<html:hidden name="assetBoxForm" property="currentAssetBoxId"/>
	<html:hidden name="assetBoxForm" property="shared"/>
	<html:hidden name="assetBoxForm" property="previousName"/>
	
		<table>
			<tr>
				<td><bright:cmsWrite identifier="label-new-name" filter="false"/></td>
				<td><html:text property="name" size="20" maxlength="50"/></td>
				<td><html:submit styleClass="button flush"><bright:cmsWrite identifier="button-save" filter="false"/></html:submit></td>
			</tr>
		</table>
	</html:form>
	   
	<div class="hr"></div>
	<p>
		<a href ="../action/viewManageAssetBoxes"><bright:cmsWrite identifier="link-back" filter="false" /></a>
	</p>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>