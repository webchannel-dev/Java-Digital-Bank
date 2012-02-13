<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		26-Nov-2009	   Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Import Keywords</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="importing_keywords"/>
	<bean:define id="pagetitle" value="Import Keywords"/>
	
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	
	<logic:equal name="metadataImportForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="metadataImportForm" property="errors" id="error">
				<bean:write name="error" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form enctype="multipart/form-data" action="importKeywords" method="post" styleClass="floated">
	
		<p>Please select the data file from which you want to import keywords. This should be a tab-delimited file, containing the following:
		<ul>
			<li>A header row, containing an identifier for each column. The identifiers can be in any order, and can be either 'Name' or 'Synonyms'.</li><br/>
			<li>The Name column should contain the full names of the keywords, in the following format: root-keyword-name/sub-keyword-name/sub-keyword-name/etc. Parent keywords will be added if they do not already exist.</li><br/> 
			<li>A row for each keyword, containing appropriate data for each column.</li><br/>
			<li>If you have problems formatting the file you can <a href="../tools/import/example_keyword_import.txt" target="_blank">download this example file</a> and modify it.</li><br/>
		</ul>		
		</p>
		<br/>
	
		
		<c:choose>
			<c:when test="${metadataImportForm.attribute.id > 0}">
				<p>
					<strong>Keyword picker:</strong> <c:out value="${metadataImportForm.attribute.label}"/>
					<input type="hidden" name="treeId" value="<c:out value="${treeId}"/>">
					<input type="hidden" name="attributeId" value="<c:out value="${metadataImportForm.attribute.id}"/>">
				</p>
			</c:when>
			<c:otherwise>
				<label for="keywordPicker">Keyword picker:<span class="required">*</span></label>
				
				<bean:define id="keywordPickers" name="metadataImportForm" property="attributes"/>
				<html:select name="metadataImportForm" property="treeId" styleId="keywordPicker">
					<html:options collection="keywordPickers" property="treeId" labelProperty="label"/>
				</html:select>
				<br/>
			</c:otherwise>
		</c:choose>
	
		<label for="file">Data File:<span class="required">*</span></label>
		<html:file name="metadataImportForm" property="file" styleClass="file" styleId="file" size="35"/>
		<br />

		<div class="hr"></div>
		
		<input type="submit" name="saveButton" class="button flush" id="submitButton" value="Submit">
		<a href="viewKeywordAdmin?categoryTypeId=<c:out value='${treeId}'/>&attributeId=<c:out value='${metadataImportForm.attribute.id}'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>