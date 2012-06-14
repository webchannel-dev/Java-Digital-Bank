<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	26-Oct-2005		Created
	 d2 	Ben Browning	21-Feb-2006		HTML/CSS Tidy Up
	 d3		Matt Stevenson	08-Mar-2007		Removed keyword check
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="ratings" settingName="ratings"/>
<bright:applicationSetting id="canCreateAssetVersions" settingName="can-create-asset-versions" />
<bright:applicationSetting id="showSensitivityFields" settingName="show-sensitivity-fields"/>
<bright:applicationSetting id="rating" settingName="ratings" />
<bright:applicationSetting id="agreementsEnabled" settingName="agreements-enabled" />
<bright:applicationSetting id="auditLoggingEnabled" settingName="enable-audit-logging" />

<%-- Create a Map containing the constants defined in AttributeConstants --%>
<un:useConstants var="attributeConstants" className="com.bright.assetbank.attribute.constant.AttributeConstants" />

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Group Attribute Visibility</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="groupAttributeVisibility"/>
	<bean:define id="pagetitle" value="Group Attribute Visibility"/>
</head>
<body id="adminPage">
	
	<bean:parameter id="groupName" name="groupName"/>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
	<h2>Manage Attribute Visibility for '<bean:write name="groupName"/>'</h2>
	
	<html:form action="saveGroupAttributeVisibility" method="post" styleClass="floated">
	
		<html:hidden name="groupAttributeVisibilityForm" property="groupId"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>

		<p>This page enables you to determine which attributes are visible to users in this group.</p>

		<p>Note that if a user is a member of another group for which an attribute has a higher level of visibility then that takes priority,
			and the user will be able to see the attribute.
		</p>

		<c:if test="${!userprofile.isAdmin}">
			<p>If you are making changes to your own group to allow you to see more attributes, then you will need to log in again
				for the changes to take effect.</p>				
		</c:if>

		<p>The attributes visible to members of this group are as follows:</p>

		<div class="hr"></div>
			

		<logic:iterate name="groupAttributeVisibilityForm" property="attributeList" id="attribute">

			<c:if test="${!(attribute.fieldName=='price' && ecommerce!='true') && !(attribute.fieldName=='version' && canCreateAssetVersions!='true') && !((attribute.id==attributeConstants.k_lAttributeId_SensitivityNotes || attribute.fieldName=='sensitive') && !showSensitivityFields) && !(attribute.fieldName=='rating' && rating!='true') && !(attribute.fieldName=='agreements' && !agreementsEnabled) && !(attribute.fieldName=='audit' && !auditLoggingEnabled)}">
		
				<label for="visibleAttribute_<bean:write name='attribute' property='id'/>"><c:if test="${attribute.isGroupHeading}"><u></c:if><bean:write name="attribute" property="label"/><c:if test="${attribute.typeId==10}"></u></c:if></label>
			
				<bean:define id="bCurrentlyPresent" value="false"/>
				<bean:define id="bCurrentlyWritable" value="false"/>
				<logic:iterate name="groupAttributeVisibilityForm" property="visibilityList" id="visibility">
					<c:if test="${visibility.attributeId==attribute.id}">
						<bean:define id="bCurrentlyPresent" value="true"/>
						<c:if test="${visibility.isWriteable}">
							<bean:define id="bCurrentlyWritable" value="true"/>
						</c:if>
					</c:if>
				</logic:iterate>
				<select name="visibleAttribute_<bean:write name='attribute' property='id'/>" id="visibleAttribute_<bean:write name='attribute' property='id'/>" style="width: 200px">
					<option value="">Not visible</option>
					<c:if test="${attribute.fieldName != 'file'}">
					<option value="false" <c:if test="${bCurrentlyPresent && !bCurrentlyWritable}">selected="selected"</c:if>>Visible when viewing</option>
					</c:if>
					<c:if test="${!attribute.readOnly}">
					<option value="true" <c:if test="${bCurrentlyWritable}">selected="selected"</c:if>>
						<c:choose>
							<c:when test="${attribute.fieldName!='file'}">
								Visible when editing and viewing
							</c:when>
							<c:otherwise>
								Visible when editing
							</c:otherwise>		
						</c:choose>	
					</option>
					</c:if>
				</select>
				<br />

		</c:if>

		</logic:iterate>

			
		<div class="hr"></div>
		
		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listGroups?name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>