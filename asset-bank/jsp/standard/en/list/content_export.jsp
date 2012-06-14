<%@page import="com.bright.framework.simplelist.form.ContentExportForm"%>
<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Content Export</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle">Content</bean:define>
	<bean:define id="helpsection" value="export-import"/>
	<bean:define id="tabId" value="export"/>

<script type="text/javascript">
		$j(function(){
			//DOM reday
			
			var $dynamicCheckboxes = $j('#languageCheckboxes input').not(':disabled');
			// check for non-default checkboxes
			$dynamicCheckboxes.click(function(){
				// if checked show radio buttons else hide radio buttons
				if ($dynamicCheckboxes.filter(':checked').length>0) {
					$j('#exportsOptions').show();
				} else {
					$j('#exportsOptions').hide();
				}
			});
			
			$j('#exportsOptions').hide();
			
			$dynamicCheckboxes.each(function(){
				if ($j(this).attr('checked')) {
					//show radio buttons
					$j('#exportsOptions').show();
				} 
			})
			
		});
	</script>

</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1><bean:write name="pagetitle" /></h1> 
	
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<logic:present  name="contentExportForm">
		<logic:equal name="contentExportForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="contentExportForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<h2>Export content</h2>
	<p>Here you can export all content manageable content in the application into a single Excel file. This allows you to work on the content outside of the application then <a href="viewContentImport">import it back in</a> when you are finished.</p>
	<html:form action="contentExport" styleClass="floated">
	
		<bean:size id="numOfLanguages" name="contentExportForm" property="languages"/>
		
		<c:if test="${numOfLanguages > 1}">
			<label>Languages to include:</label>
			
			<div id="languageCheckboxes" class="holder" style="width: 300px">
				<logic:iterate id="language" name="contentExportForm" property="languages" type="com.bright.framework.language.bean.Language" indexId="rowIndex">
					<label class="wrapping">
						<html:multibox name="contentExportForm" property="selectedLanguages" styleClass="checkbox" disabled="<%= language.isDefault() %>" >
							<bean:write name="language" property="id"/>
						</html:multibox>
						<bean:write name="language" property="name"/> <c:if test="${language.default}">(default language)</c:if>
					</label><br />
				</logic:iterate>
			</div>
			<br />
		</c:if>	
	
		<div id="exportsOptions">
		<label>Export options:</label>
		
		<label class="wrapping"><html:checkbox name="contentExportForm" property="exportMissingContentOnly" styleClass="checkbox" styleId="missingContent"/> Only export missing content</label>
		</div>
		<br />
	
		<label for="filename">Name of export file: <span class="required">*</span></label>
		<html:text name="contentExportForm" property="filename" size="80" maxlength="255"/><span class="note">.xls</span>
	
		<br />
		
		<div class="hr"></div>
		<html:submit value="Run Content Export" styleClass="button flush"/>
		
	</html:form>
  	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>