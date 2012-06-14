<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		07-Jul-2008		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="orgUnitsEnabled" settingName="orgunit-use"/>

<head>
	<title><bright:cmsWrite identifier="title-agreements" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="agreements"/>
	
	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the agreementTitle field the focus once the dom is ready
		$j(function () {
  			$j('#agreementTitle').focus();
			initDatePicker();
		});
	</script>	
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-agreements" filter="false"/></h1> 

	<logic:present name="agreementsForm">
		<logic:equal name="agreementsForm" property="hasErrors" value="true">
			<logic:iterate name="agreementsForm" property="errors" id="error">
				<div class="error">
					<bright:writeError name="error" />
				</div>
			</logic:iterate>
		</logic:equal>
	</logic:present>											
	
	<h2><bright:cmsWrite identifier="subhead-edit-agreement" filter="false"/></h2>
	
	<html:form action="saveAgreement" method="post" styleClass="floated">	
		
		<html:hidden name="agreementsForm" property="agreement.id"/>
			
				
		<label for="agreementTitle"><bright:cmsWrite identifier="label-title" filter="false"/></label>
		<html:text styleClass="text" name="agreementsForm" styleId="agreementTitle" property="agreement.title" size="30" maxlength="60"/>		
		<br />
		
		<label for="agreementBody"><bright:cmsWrite identifier="label-agreement" filter="false"/></label>
		<textarea style="width:90%;" class="editor" name="agreement.body" id="agreementBody" cols="90" rows="20"/><bean:write name="agreementsForm" property="agreement.body" filter="false"/></textarea>	
		<br />
		<br />
		<label for="agreementexpiry"><bright:cmsWrite identifier="label-expiry" filter="false"/></label>
		<html:text styleClass="small text date" name="agreementsForm" property="agreement.expiryString" size="20" styleId="agreementexpiry" />
	
		<br />
		<c:if test="${orgUnitsEnabled}">
			<c:if test="${!userprofile.isAdmin}">
			
				<label for="availableToAll"><bright:cmsWrite identifier="label-shared-between-org-units" filter="false"/></label>
				<html:checkbox styleClass="checkbox" name="agreementsForm" property="agreement.isAvailableToAll" styleId="availableToAll"/>
				<br />
			</c:if>
		</c:if>

		<c:if test="${orgUnitsEnabled && userprofile.isAdmin}">
			<p>Please note: Agreements added by an admin user will be shared across all Organisational Units</p><br/>
		</c:if>
		
		<div class="hr"></div>
		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listAgreements" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>