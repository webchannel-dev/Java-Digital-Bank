<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
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
	<title><bright:cmsWrite identifier="title-copy-lightbox"/></title> 
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
	<bright:cmsWrite identifier="intro-copy-lightbox" replaceVariables="true" filter="false"/>
	
	<html:form action="copyAssetBox" styleClass="floated">
		<html:hidden name="assetBoxForm" property="currentAssetBoxId"/>
		<html:hidden name="assetBoxForm" property="previousName"/>
		<label for="name"><bright:cmsWrite identifier="label-new-name" filter="false"/></label>
		<input type="text" name="name" id="name" value="<bean:write name='assetBoxForm' property='name'/>"/> 
		<br />
		<div class="hr"></div>
		<html:submit styleClass="button flush"><bright:cmsWrite identifier="button-save" filter="false"/></html:submit>
		<a href ="../action/viewManageAssetBoxes" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
	   
	
	<p>
		
	</p>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>