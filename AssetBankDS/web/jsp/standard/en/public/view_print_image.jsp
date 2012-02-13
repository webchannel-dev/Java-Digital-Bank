<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	20-Jun-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>


<head>
	<title><bright:cmsWrite identifier="title-print-image" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	
</head>
<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-print-image" filter="false"/></h1> 
	
	<html:form action="printImage" method="post" target="_blank" styleClass="floated">
		<html:hidden name="printImageForm" property="asset.id"/>

		<p><bright:cmsWrite identifier="intro-print-image" filter="false"/></p>

		<div class="hr"></div>
			

		<logic:iterate name="printImageForm" property="attributeList" id="attribute">
			<c:if test="${attribute.isVisible && attribute.fieldName!='file'}">
				<c:if test="${(attribute.fieldName != 'price' || ecommerce)}">
					
						<input type="checkbox" class="checkbox" name="requiredAttribute_<bean:write name='attribute' property='id'/>" value="true" id="requiredAttribute_<bean:write name='attribute' property='id'/>" /> <label for="requiredAttribute_<bean:write name='attribute' property='id'/>" class="after"><bean:write name="attribute" property="label"/></label>
						<br />
				</c:if>
			</c:if>
		</logic:iterate>

			
		<div class="hr"></div>
		
	
		<input type="submit" class="button flush" value="Print" /> 
		<a href="viewAsset?asset.id=<bean:write name='printImageForm' property='asset.id'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>