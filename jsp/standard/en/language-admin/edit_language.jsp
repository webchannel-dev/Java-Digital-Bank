<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		31-Oct-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Edit Language</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="multi-language"/>
	<bean:define id="pagetitle" value="Languages"/>
	<bean:define id="tabId" value="languages"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<h2>Edit Language</h2>
	
	<logic:equal name="languageForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="languageForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<c:if test="${not languageForm.language.default}">
		<p>Please note, if you change a language's code, any existing assets will be reindexed, which may take some time.</p>
	</c:if>
	<br />
	<html:form action="editLanguage" method="post" styleClass="floated">
		<html:hidden name="languageForm" property="language.id"/>
		<html:hidden name="languageForm" property="language.suspended"/>
		<html:hidden name="languageForm" property="language.default"/>
		<c:if test="${languageForm.language.default}">
			<html:hidden name="languageForm" property="language.name"/>
			<html:hidden name="languageForm" property="language.nativeName"/>
			<html:hidden name="languageForm" property="language.code"/>
			<html:hidden name="languageForm" property="language.rightToLeft"/>
			<html:hidden name="languageForm" property="language.usesLatinAlphabet"/>
		</c:if>
		
		<c:set var="nativeName"><bean:write name="languageForm" property="language.nativeName" filter="false"/></c:set>
		
			
		<c:if test="${not empty languageForm.language.iconFilePath}">
				<c:set var="iconFilePath"><bean:write name="languageForm" property="language.iconFilePath" filter="false"/></c:set>
				<label>Current Icon:</label>
				<div class="holder"><html:img page="${iconFilePath}" alt="icon" /></div>
				<br />
		</c:if>
		
		<label for="langName">Name:</label>
			
		<c:if test="${not languageForm.language.default}">
			<html:text name="languageForm" property="language.name" styleId="langName" maxlength="40"/>
		</c:if>
		<c:if test="${languageForm.language.default}">
			<div class="holder"><bean:write name="languageForm" property="language.name"/></div>
		</c:if>
		<br />
			
		
		<c:if test="${not languageForm.language.default}">
			<label for="langName">Native Name:</label>
			<input type="text" name="language.nativeName" maxlength="40" value='<bean:write name="languageForm" property="language.nativeName" filter="false"/>' styleId="langName" />
			<br />
		
			<label for="langCode">Code:</label>
			<html:text name="languageForm" property="language.code" size="2" maxlength="2" styleId="langCode" />
			<br />
			
			<label for="latin">Uses Latin Alphabet:</label>
			<html:checkbox name="languageForm" property="language.usesLatinAlphabet" styleClass="checkbox" styleId="latin" />
			<br />
			<label for="rtl">Right-to-Left:</label>
			<html:checkbox name="languageForm" property="language.rightToLeft" styleClass="checkbox" styleId="rtl" />
			<br />
		</c:if>
		
		<label for="iconFile">Icon Filename:</label>
		<html:text name="languageForm" property="language.iconFilename" styleId="iconFile" maxlength="255" />
		<br />

		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="Save" /> 
		<a href="viewLanguages" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>