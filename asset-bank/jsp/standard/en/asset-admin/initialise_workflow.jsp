<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		21-Aug-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="title-initialise-workflow" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="asset-approval"/>
	<bean:define id="pagetitle" value="Approve Assets"/>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-initialise-workflow" filter="false" /></h1> 
		
	<logic:equal name="assetForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>

	<bean:parameter id="wfRemoved" name="wfRemoved" value="false"/>
	<c:if test="${wfRemoved}">
		<div class="info"><bright:cmsWrite identifier="snippet-workflow-removed" filter="false" /></div>
	</c:if>

	<bright:cmsWrite identifier="snippet-workflow-init" filter="false" />
	<bean:parameter id="batch" name="batch" value="false"/>

	<c:choose>
		<c:when test="${batch}">
			<form action="initialiseWorkflowBatch" method="post">
		</c:when>
		<c:otherwise>
			<form action="initialiseWorkflow" method="post">
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>">
		<bean:define id="initialising" value="true"/>
		<%@include file="inc_submit_options_workflow.jsp"%>	
			
		<div class="hr"></div>
	
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		</div>
	</form>
	
	<form name="cancelForm" action="viewAsset" method="get">
		<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton" />
	</form>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>