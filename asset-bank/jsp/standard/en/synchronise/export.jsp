<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
    d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="bExportAppendMandatory" settingName="export-append-mandatory"/>
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Export</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="export"/>
	<bean:define id="pagetitle">Export <bright:cmsWrite identifier="items" filter="false" case="mixed"/></bean:define>
	<bean:define id="helpsection" value="export"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<p>You have chosen to export <strong><bean:write name="exportForm" property="numAssets"/> </strong><bright:cmsWrite identifier="items" filter="false" />.</p>
	
	<bright:refDataList id="mainCopyItem" componentName="ListManager" methodName="getListItem" argumentValue="export-copy"/>	
	<logic:notEmpty name="mainCopyItem">
		<bean:write name="mainCopyItem" property="body" filter="false"/>
	</logic:notEmpty>
	
	<logic:present  name="exportForm">
		<logic:equal name="exportForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="exportForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<html:form action="exportAssets">
		<c:if test="${bExportAppendMandatory}">
			<input type="hidden" name="mandatory_appendToFilename" value="Please enter a value for Append to filename." />
		</c:if>
	
		<table class="form">
			<tr>
				<th><label for="exportName">Export filename stem:</label></th>
				<td><html:hidden name="exportForm" property="name"/><bean:write name="exportForm" property="name"/></td>
			</tr>
			<tr>
				<th><label for="appendToFilename">Append to filename:</label></th>
				<td>
					<html:text name="exportForm" property="appendToFilename" size="80" maxlength="255" styleId="appendToFilename" />
					<c:if test="${bExportAppendMandatory}"><span class="required">*</span></c:if>
				</td>
			</tr>
			<tr>
				<th><label for="description">Description:</label></th>
				<td><html:text name="exportForm" property="description" size="80" maxlength="255" styleId="description" /></td>
			</tr>
			<c:if test="${supportMultiLanguage && exportForm.numLanguages>1}">
				<tr>
					<th><label for="langs">Export translations?</label></th>
					<td>
						<html:checkbox name="exportForm" property="exportAllLanguages" styleClass="checkbox" styleId="langs"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<th><label for="assetFiles">Export asset files?</label></th>
				<td>
					<html:checkbox name="exportForm" property="exportingAssetFiles" styleClass="checkbox" styleId="assetFiles" onclick="if(this.checked) $j('#exportFilesToZip').show(); else $j('#exportFilesToZip').hide();"/>
					<div style="margin-top: 3px;">
					Note, if you do not export the asset files then the exported data file will be in a format that can be imported directly using the 'Metadata Import' functionality. 
					If you do export the asset files then you will not be able to do this without first re-importing the files (as the structure of the data file will be different).	
					</div>
				</td>
			</tr>
			<tr id="exportFilesToZip">
				<th><label for="langs">Export files:</label></th>
				<td>
					<html:radio name="exportForm" property="exportFilesInZips" styleClass="checkbox" styleId="exportAsZips" value="true"/><label for="exportAsZips">as downloadable zip file(s)</label>
					<br/>
					<html:radio name="exportForm" property="exportFilesInZips" styleClass="checkbox" styleId="exportToDir" value="false"/><label for="exportToDir">to the export directory on your Asset Bank server
					(<bright:write name="exportForm" property="exportDirectory"/>)</label>
				</td>
			</tr>
			<script type="text/javascript"><!--
				if(!$j('#assetFiles').attr('checked'))
				{
					$j('#exportFilesToZip').hide();
				}
			//--></script>
		</table>
		<html:submit value="Run Export &raquo;" styleClass="button flush"/>
			
	</html:form>
  	
   
	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>