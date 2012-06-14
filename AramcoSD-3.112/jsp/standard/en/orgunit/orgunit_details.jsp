<%@include file="../inc/doctype_html_admin.jsp" %>
<%-- History:
	 d1	Matt Stevenson		17-Sep-2010		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | View Organisational Unit Details</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<c:if test="${empty returnUrl}"><bean:define id="section" value="orgunits"/></c:if>
	<bean:define id="pagetitle" value="Organisational Unit Details"/>
	<bean:define id="tabId" value="details"/>
	<bean:define id="helpsection" value="orgunit-details"/>
	<script type="text/javascript">
		$j(function() {
			//stripey tables
			//BB I have removed all other table stripey code from the application, as this covers everything
    		$j('dl.stripey').each(function() {
				$j(this).find('dd:even').add('dt:even',this).addClass('even');
			});
		});	
	</script>
	

	
</head>

<bright:applicationSetting id="useStructuredAddress" settingName="users-have-structured-address" />
<bean:parameter name="returnUrl" id="returnUrl" value="" />

<body <c:if test="${empty returnUrl}">id="adminPage"</c:if>>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="inc_orgunit_details_tabs.jsp"%>

	<h3>Admin user details</h3>
	
	

	<logic:notEmpty name="orgUnitForm" property="userList">
	
		<table class="list stripey wide" cellspacing="0" summary="Admin user details for <bean:write name='orgUnitForm' property='orgUnit.category.name'/> ">		<bean:define id="verticalWithLabels" value="true" />
			<%@include file="inc_orgunit_details_users.jsp"%>
		</table>

	</logic:notEmpty>
	<logic:empty name="orgUnitForm" property="userList">
		<p>This org unit doesn't currently have any admin users.</p>
		<div class="hr"></div>
	</logic:empty>
	
	<%@include file="inc_orgunit_details_back.jsp"%>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>