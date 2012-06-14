<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan			07-Mar-2006		Created
	 d2		Matt Stevenson		09-Mar-2007		Modified to use different trees for keyword report
	 d3		Matt Stevenson		02-Nov-2007		Added duplicate asset report
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	<c:set var="reportForm" value="${assetReportForm}" />
	<bean:define id="tabId" value="assetReports"/>
	<script type="text/JavaScript">
		$j(function() {
			initDatePicker();
		});
	</script>		
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../report/inc_report_tabs.jsp"%>
	
		<logic:present name="assetReportForm">
			<c:set var="form" value="${assetReportForm}"/>
		</logic:present>
		<logic:present name="searchForm">
			<c:set var="form" value="${searchForm}"/>
		</logic:present>
		
		<logic:present name="form">
			<logic:equal name="form" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="form" property="errors" id="errorText">
						<bean:write name="errorText" filter="false"/><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		
		<p>Please choose from the reports below.</p>
		<div class="hr"></div>
		
		<h3>All <bright:cmsWrite identifier="items" filter="false" case="mixed" /> by Category</h3>
		<bright:refDataList componentName="AttributeManager" methodName="getAllDropdownAttributes" id="attDropdownList"/>
		
		<p>Shows the tree of descriptive categories, with the <bright:cmsWrite identifier="items" filter="false" /> in each shown ordered by the given attribute.</p>
		
		<html:form action="viewAssetReport" method="get">	
			<html:hidden name="assetReportForm" property="reportType" value="1" />

			<html:select name="assetReportForm" property="attribute.id">
				<html:option value="0">-- All assets --</html:option>
				<html:optionsCollection name="attDropdownList" value="id" label="name"/>
			</html:select>
			
			<html:checkbox styleId="sortAscending" name="reportForm" property="showCategoryTotalsOnly" style="margin-bottom:0" /><label for="sortAscending">Show category totals only</label>
		
			<br /><br />
			
			<input type="submit" name="submit" class="button flush" value="Get Report">
		</html:form>

		<div class="hr"></div>
		
		<h3>All <bright:cmsWrite identifier="items" filter="false" case="mixed"/> by Date and/or File Size</h3>
		
		<p>Shows all <bright:cmsWrite identifier="items" filter="false" /> uploaded/last modified in the given date range.</p>
		
		<html:form action="viewAssetReport" method="get">	
			<html:hidden name="assetReportForm" property="reportType" value="2" />

			<div id="reportSelect">
				<label for="startDate">From: </label><html:text styleClass="date" name="reportForm" property="startDateString" size="20" styleId="startDate" /> 

				
				&nbsp;
				<label for="endDate">To: </label><html:text styleClass="date" name="reportForm" property="endDateString" size="20" styleId="endDate" /> 
				

				&nbsp;by&nbsp;
			
				<html:select name="assetReportForm" property="dateType">
					<html:option value="1">Upload date</html:option>
					<html:option value="2">Modified date</html:option>
				</html:select>				
				
				&nbsp;
				of size:
				<html:select name="assetReportForm" property="fileSizeOperator">					
					<html:option value="2">more than</html:option>
					<html:option value="1">less than</html:option>
				</html:select>
				
				<html:text name="assetReportForm" property="fileSize" size="4" maxlength="9"/>
				
				<html:select name="assetReportForm" property="fileSizeMultiplier">
					<html:option value="1">bytes</html:option>
					<html:option value="1024">Kb</html:option>
					<html:option value="1048576">Mb</html:option>
					<html:option value="1073741824">Gb</html:option>
				</html:select>

			</div>							
			<br />			
			<input type="submit" name="submit" class="button flush" value="Get Report">
		</html:form>

		<c:if test="${!empty assetReportForm.keywordPickers}">
			<div class="hr"></div>
			<h3>All <bright:cmsWrite identifier="items" filter="false" case="mixed" /> by Keyword</h3>		
			<p>Shows all assets, by keyword. Select the keyword set you are interested in, then click the Get Report button:</p>		
			<html:form action="viewAssetReport" method="get">	
				<html:hidden name="assetReportForm" property="reportType" value="3" />	
				<select name="categoryTypeId" size="1">
					<logic:iterate name="assetReportForm" property="keywordPickers" id="picker">
						<option value="<bean:write name='picker' property='treeId'/>"><bean:write name="picker" property="label"/></option>
					</logic:iterate>
				</select>
				<input type="submit" name="submit" class="button flush" value="Get Report">
			</html:form>
		</c:if>


		<div class="hr"></div>
		
		<h3>Duplicate Assets</h3>
		
		<p>Shows all potentially duplicate assets currently stored in the system.</p>
		
		<html:form action="viewAssetReport" method="get">	
			<html:hidden name="assetReportForm" property="reportType" value="4" />
			<input type="submit" name="submit" class="button flush" value="Get Report">
		
		</html:form>
		
		<div class="hr"></div>
		
		<h3>Total Assets in your Asset Bank</h3>
		
		<p>Shows total number of assets currently stored in your Asset Bank.</p>
		
		<html:form action="viewAssetReport" method="get">	
			<html:hidden name="assetReportForm" property="reportType" value="5" />
			<input type="submit" name="submit" class="button flush" value="Get Report">
		
		</html:form>
		
		<div class="hr"></div>
		
		<h3><bright:cmsWrite identifier="items" filter="false" case="mixed"/> by Popularity</h3>
		<html:form action="viewAssetsByPopularity" method="get">
			<logic:present parameter="reportType"><bean:parameter id="reportType" name="reportType"/></logic:present>
			<logic:present parameter="numAssets"><bean:parameter id="numAssets" name="numAssets"/></logic:present>
			<logic:notPresent parameter="numAssets"><bean:define id="numAssets" value="100"/></logic:notPresent>
			<input type="hidden" name="page" value="0"/>
			<input type="hidden" name="pageSize" value="5"/>
			<input type="radio" name="reportType" value="1" id="report1" onclick="document.getElementById('reportName').value = 'Most popular <bright:cmsWrite identifier="items" filter="false"/> by view';" <c:if test="${reportType == 1}">checked</c:if>/> <label for="report1">Most popular <bright:cmsWrite identifier="items" filter="false"/> by view</label><br />
			<input type="radio" name="reportType" value="2" id="report2" onclick="document.getElementById('reportName').value = 'Least popular <bright:cmsWrite identifier="items" filter="false"/> by view';" <c:if test="${reportType == 2}">checked</c:if>/> <label for="report2">Least popular <bright:cmsWrite identifier="items" filter="false"/> by view</label><br />
			<input type="radio" name="reportType" value="3" id="report3" onclick="document.getElementById('reportName').value = 'Most popular <bright:cmsWrite identifier="items" filter="false"/> by download';" <c:if test="${reportType == 3}">checked</c:if>/> <label for="report3">Most popular <bright:cmsWrite identifier="items" filter="false"/> by download</label><br />
			<input type="radio" name="reportType" value="4" id="report4" onclick="document.getElementById('reportName').value = 'Least popular <bright:cmsWrite identifier="items" filter="false"/> by download';" <c:if test="${reportType == 4}">checked</c:if>/> <label for="report4">Least popular <bright:cmsWrite identifier="items" filter="false"/> by download</label><br />	
			<br/>
			Max results: <input type="text" name="numAssets" class="text" value="<bean:write name="numAssets"/>" size="4" maxlength="4"/>
			<br/><br/>
			<input type="submit" name="submit" class="button flush" value="View Assets">
		</html:form>
		<br />
					
		<%@include file="../inc/body_end.jsp"%>
			
</body>
</html>