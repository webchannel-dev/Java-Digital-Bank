<%@ page isErrorPage="true" %>
<%@ page import="com.bn2web.common.exception.Bn2Exception" %>
<%@ page import="com.bn2web.common.service.GlobalApplication" %>


<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>

<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="webmasterEmail" settingName="webmaster-email"/>
<%-- App is initialised, so we'll show a pretty page --%>
<head>			
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Error</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="error"/>
</head>
	<body id="errorPage">
		<%@include file="../inc/body_start.jsp"%>
		<h1 class="underline">Error</h1> 	
		<bright:cmsWrite identifier="category-conflict-error-intro" filter="false" />
		<table class="form">
			<tr>
				<th>Asset Type: </th>
				<td>${assetForm.asset.entity.name} (id: ${assetForm.asset.entity.id})</td>
			</tr>
			<c:set var="units" value="${userprofile.user.allOrgUnits}" />
			<tr>
				<th>Org Units: </th>
				<td>
					<table class="form">
						<logic:iterate name="units" id="unit">
							<tr>
								<td>
									${unit.name} (id: ${unit.id})
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
			</tr>
			<tr>
				<th>Reason:</th>
				<td>
					Category ${assetForm.asset.entity.rootCategoryId} is not one of (or a descendant of) any of the org units' root categories
				</td>
			</tr>
		</table>
			
		<%@include file="../inc/body_end.jsp"%>		
	</body>
</html>
