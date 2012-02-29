<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		31-Oct-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Add Language</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="multi-language"/>
	<bean:define id="pagetitle" value="Languages"/>
	<bean:define id="tabId" value="languages"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<h2>Add Language</h2>
	
	<logic:equal name="languageForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="languageForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>

	<p>
		You can add a language to the application by entering a name and code for it and clicking the Save button. 
		Initially the new language will contain a copy of the default language (English) and will be suspended.
		Once translations have been provided, you can un-suspend the language on the main Languages page to allow users to access it.
	</p>
	<p>
		Please note, creating a new language will result in any existing assets being reindexed, which may take some time.
	</p>
	
	<html:form action="addLanguage" method="post" styleClass="floated">
	
		<label for="langName">Name:</label>
		<html:text name="languageForm" property="language.name" styleId="langName" maxlength="40" />
		<br />
		
		<label for="nativeName">Native Name:</label>
		<html:text name="languageForm" property="language.nativeName" styleId="nativeName" maxlength="40" />
		<br />
		
		<label for="langCode">Code:</label>
		<html:text name="languageForm" property="language.code" styleId="langCode" size="2" maxlength="2" />
		<br />
		
		<label for="latin">Uses Latin Alphabet:</label>
		<html:checkbox name="languageForm" property="language.usesLatinAlphabet" styleId="latin" styleClass="checkbox" />
		<br />
		
		<label for="rtl">Right-to-Left:</label>
		<html:checkbox name="languageForm" property="language.rightToLeft" styleId="rtl" styleClass="checkbox" />
		<br />
		
		<label for="icon">Icon Filename:</label>
		<html:text name="languageForm" property="language.iconFilename" styleId="icon" maxlength="255" />
		<br />
	
		<div class="hr"></div>
		
		<input type="submit" class="button flush" value="Save" /> 
		<a href="viewLanguages" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>