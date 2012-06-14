<%@include file="../inc/doctype_html_admin.jsp" %>
<%@ page import="com.bright.assetbank.application.constant.AssetBankConstants" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Added link to add group
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4    	Ben Browning   21-Feb-2006    HTML/CSS tidy up
	 d5		Matt Stevenson	23-Feb-2006		Added link to edit formats
	 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<%-- See if usage before download is enabled --%>
<bright:applicationSetting id="showUseDropdownBeforeDownload" settingName="showUseDropdownBeforeDownload"/>
<bright:applicationSetting id="simpleConvertOptionsForDownload" settingName="simpleConvertOptionsForDownload" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Usage Types</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="usage"/>
	<bean:define id="pagetitle" value="Download Options"/>
	<bean:define id="tabId" value="usage"/>

	<script type="text/javascript">
	<!--
	<%--
	 * Called when the asset type is changed.
	 * ctrl should be the select element containing the asset type options
	 --%>
	function mediaTypeChanged(ctrl)
	{
		var typeId = ctrl.value;
		updateHighRes(typeId);
	}
	
	function updateHighRes(typeid)
	{
		var eltHighRes = document.getElementById('highRes');
		
		if (eltHighRes != null)
		{
			// This high res box is only active media type is all or image
			if (typeid == 0 || typeid == <%= AssetBankConstants.k_lAssetTypeId_Image %>)
			{
				eltHighRes.disabled = false;
			}
			else
			{
				eltHighRes.checked = false;
				eltHighRes.disabled = true;
			}	
		}
	}
	
	-->
	</script>
	
</head>

<c:set var="parentId" value="${usageForm.parentUsageType.id}"  />
<c:set var="id" value="${usageForm.usageType.id}"  />

<c:set var="actionName" value="Add" />
<c:if test="${id gt 0}">
	<c:set var="actionName" value="Edit" />
</c:if>

<body id="adminPage" onload="updateHighRes(<c:out value='${usageForm.usageType.assetTypeId}'/>);">
	<%@include file="../inc/body_start.jsp"%>
	
	
	<h1><bean:write name="pagetitle" /></h1>
	
	<logic:present name="usageForm">
		<logic:equal name="usageForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="usageForm" property="errors" id="error">
					<bright:writeError name="error" /><br />	
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2><c:out value="${actionName}" /> Usage Type</h2>
	
	<logic:equal name="id" value="0">
		<form action="../action/addUsageType" method="post" style="padding:1em 0">
			<input type="hidden" name="parentId" value="<bean:write name='parentId' />" />
			<html:hidden name="usageForm" property="parentUsageType.id"/>
			
			<%@include file="_usage_admin_form_fields.jsp"%>
			
			<div style="text-align:left; ">			
				<html:submit property="b_save" styleClass="button flush floated" ><bright:cmsWrite identifier="button-add" filter="false" /></html:submit>
				<a href="../action/viewUsageTypes?parentId=<bean:write name='parentId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a> 
			</div>
		</form>
	</logic:equal>
	<logic:greaterThan name="usageForm" property="usageType.id" value="0">
		<form action="../action/updateUsageType" method="post" style="padding:1em 0">
			<html:hidden name="usageForm" property="usageType.id"/>
			<input type="hidden" name="parentId" value="<bean:write name='parentId' />" />
			<html:hidden name="usageForm" property="parentUsageType.id" />
	
			<%@include file="_usage_admin_form_fields.jsp"%>
				
			<div style="text-align:left; ">			
				<html:submit property="b_save" styleClass="button flush floated" ><bright:cmsWrite identifier="button-save" filter="false" /></html:submit>
				<a href="../action/viewUsageTypes?parentId=<bean:write name='parentId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a> 
			</div>
			
		</form>
	</logic:greaterThan>
	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>