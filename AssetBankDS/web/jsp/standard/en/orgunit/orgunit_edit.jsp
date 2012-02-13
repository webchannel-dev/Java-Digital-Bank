<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1	Steve Bryan		09-Dec-2005		Created
	 d2	Ben Browning	22-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Edit Organisational Unit</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="orgunits"/>
	<bean:define id="pagetitle" value="Organisational Unit"/>
	<script type="text/javascript"> 
	<!--
		function toggleContentLinks(checker)
		{
			if (document.getElementById('editContentLinks') && checker.checked) 
			{ 
				document.getElementById('editContentLinks').style.display = 'block'; 
			} 
			else if (document.getElementById('editContentLinks')) 
			{
				document.getElementById('editContentLinks').style.display = 'none'; 
			}
		}
	-->
	</script>
</head>

<body id="adminPage" onload="toggleContentLinks(document.getElementById('contentPage'));">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present name="orgUnitForm">
		<logic:equal name="orgUnitForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="orgUnitForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	<p><span class="required">*</span> denotes a mandatory field.</p>
	<html:form action="saveOrgUnit" method="post" focus="orgUnit.category.name" styleClass="floated">
		<html:hidden name="orgUnitForm" property="orgUnit.id"/>
		<html:hidden name="orgUnitForm" property="orgUnit.category.id"/>

			<label for="orgname">Name: <span class="required">*</span></label>
			<html:text styleClass="text" styleId="orgname" name="orgUnitForm" property="orgUnit.category.name" size="30" maxlength="60"/>
			<br />
			<c:if test="${userprofile.isAdmin}">
				<label for="quota">Storage Quota:</label>
				<html:text styleClass="text small" styleId="quota" name="orgUnitForm" property="diskQuotaString" size="10" maxlength="10"/> <span>(Mb) Leave blank or set to 0 for an unlimited storage quota</span>
				<br />
				<label for="descCat">Root Category ID:</label>
				<html:text styleClass="text small" styleId="rootCat" name="orgUnitForm" property="rootDescriptiveCategoryId" size="10" maxlength="10"/>
				<br />
			</c:if>
		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listOrgUnits" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
				
	</html:form>


	<%@include file="../inc/body_end.jsp"%>


	
</body>
</html>