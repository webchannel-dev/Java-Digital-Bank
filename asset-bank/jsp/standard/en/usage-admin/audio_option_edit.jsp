<%@include file="../inc/doctype_html_admin.jsp" %>
<%@ page import="com.bright.assetbank.application.constant.AssetBankConstants" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	14-Dec-2011		Created
	 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<bean:define id="pagetitle" value="Audio Download Options"/>
	<title><bright:cmsWrite identifier="company-name" /> | <bean:write name="pagetitle" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="usage"/>
	<bean:define id="tabId" value="audio"/>
	<bean:define id="helpsection" value="downloadAudioOptions"/>

</head>

<c:set var="id" value="${audioOptionForm.downloadOption.id}"  />

<c:set var="actionName" value="Add" />
<c:if test="${id gt 0}">
	<c:set var="actionName" value="Edit" />
</c:if>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	
	<h1><bean:write name="pagetitle" /></h1>
	
	<logic:present name="audioOptionForm">
		<logic:equal name="audioOptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="audioOptionForm" property="errors" id="error">
					<bright:writeError name="error" /><br />	
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2><c:out value="${actionName}" /> Audio Download Option</h2>
	
	<logic:equal name="id" value="0">
		<form action="../action/addAudioOption" method="post" style="padding:1em 0">
			
			<%@include file="_audio_option_admin_form_fields.jsp"%>
			
			<div style="text-align:left; ">			
				<html:submit property="b_save" styleClass="button flush floated" ><bright:cmsWrite identifier="button-add" /></html:submit>
				<a href="../action/viewAudioOptions" class="cancelLink"><bright:cmsWrite identifier="button-cancel" /></a> 
			</div>
		</form>
	</logic:equal>
	<logic:greaterThan name="id" value="0">
		<form action="../action/updateAudioOption" method="post" style="padding:1em 0">
			<html:hidden name="audioOptionForm" property="downloadOption.id"/>
	
			<%@include file="_audio_option_admin_form_fields.jsp"%>
				
			<div style="text-align:left; ">			
				<html:submit property="b_save" styleClass="button flush floated" ><bright:cmsWrite identifier="button-save" /></html:submit>
				<a href="../action/viewAudioOptions" class="cancelLink"><bright:cmsWrite identifier="button-cancel" /></a> 
			</div>
			
		</form>
	</logic:greaterThan>
	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>