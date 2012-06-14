<%@page import="com.bright.framework.simplelist.form.ContentImportForm"%>
<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Content Import</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle">Content</bean:define>
	<bean:define id="helpsection" value="export-import"/>
	<bean:define id="tabId" value="import"/>

</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1><bean:write name="pagetitle" /></h1> 
	
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	
	<h2>Import content</h2>
	
	<logic:present  name="contentImportForm">
		<logic:equal name="contentImportForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="contentImportForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<html:form enctype="multipart/form-data" action="contentImport" method="post" styleClass="floated">
		
	<bean:size id="numOfLanguages" name="contentImportForm" property="languages"/>
	<c:if test="${numOfLanguages > 1}">
		<label>Language(s) to include in import:</label>
		
		<div id="languageCheckboxes" class="holder">

				<logic:iterate id="language" name="contentImportForm" property="languages" type="com.bright.framework.language.bean.Language" indexId="rowIndex">
					<label class="wrapping">
						<html:multibox name="contentImportForm" property="selectedLanguages" styleClass="checkbox" >
							<bean:write name="language" property="id"/>
						</html:multibox>

						<bean:write name="language" property="name"/>
					</label><br />
				</logic:iterate>
	
		</div>
		<br />
	</c:if>
		<label for="file">Content File: <span class="required">*</span></label>		
		<html:file name="contentImportForm" property="file" styleClass="file" styleId="file" size="35"></html:file>
		<br />


		<div class="hr"></div>
				
		<html:submit value="Run Content Import" styleClass="button flush"/>
	</html:form>
  	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>