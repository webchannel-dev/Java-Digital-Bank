<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Marco Primiceri	18-Apr-2011		Created from old manage_display_attributes.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@page import="org.apache.lucene.search.SortField"%>
<%@page import="com.bright.assetbank.attribute.bean.SortAttribute"%>


<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="canCreateSearchRss" settingName="can-create-search-rss" />
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
<bright:applicationSetting id="useParentMetadata" settingName="include-metadata-from-parents-for-search"/>
<bright:applicationSetting id="categoryExtensionAssetsEnabled" settingName="category-extension-assets-enabled"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Name Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="name_attributes"/>
	<bean:define id="pagetitle" value="Name Attributes"/>
	<bean:define id="tabId" value="nameAttributes"/>
	<bean:define id="descriptionAttributeBean" value="nameAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1>Attributes</h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	

	<p>Please select from the list below the attributes you would like to use for your <bright:cmsWrite identifier="item" filter="false" /> names.</p>

	<p>The <bright:cmsWrite identifier="item" filter="false" /> names are displayed on certain reports and used to construct certain filenames. Descriptive attributes such as title are best used for the <bright:cmsWrite identifier="item" filter="false" /> names. The first selected name attribute that has a value will be used by an <bright:cmsWrite identifier="item" filter="false" />.</p>
	<p>If you don't select a name attribute then the id will be used.</p>
	<br/>
	<div style=" float: left; width: 350px">
	<form name="nameAttributeForm" action="setNameAttribute" method="get">
		<table cellspacing="0" cellpadding="4">
			<!-- html:select name="displayAttributeForm" property="nameAttribute" size="1" -->
				<logic:iterate name="nameAttributes" id="att" indexId="index">
					<c:if test="${!(att.fieldName=='price' && ecommerce!='true')}" >
						<tr>
							<td><input type="checkbox" value="<bean:write name='att' property='id'/>" name="nameAtt<bean:write name='index'/>" id="nameAtt<bean:write name='index'/>" <c:if test='${att.isNameAttribute}'>checked</c:if>/></td>
							<td><label for="nameAtt<bean:write name='index'/>"><bean:write name='att' property='label'/></label></td>
						</tr>
					</c:if>
				</logic:iterate>
		</table>
		<br/><br/><input type="submit" name="submit" value="Set name attributes &raquo;" class="button"/>
	</form>
	</div>
	
	<c:if test="${canCreateSearchRss}">
	<div class="hr"></div>
		
		<br/><h3>Description Attribute</h3>
	
		<p>Please select from the list below the attribute you would like to use for your <bright:cmsWrite identifier="item" filter="false" /> description.</p>
	
		<p>The <bright:cmsWrite identifier="item" filter="false" /> description is included as the description field in RSS feeds created from saved searches. </p>
		<br/>
		<form name="descriptionAttributeForm" action="setDescriptionAttribute" method="get">
			<select name="descriptionAttributeId"  size="1">
				<option value="-1">[none]</option>
				<logic:iterate name="nameAttributes" id="att">
					<c:if test="${!(att.fieldName=='price' && ecommerce!='true')}" >
						<option value="<bean:write name='att' property='id'/>" <c:if test="${att.id == descriptionAttributeId}">selected</c:if>><bean:write name='att' property='label'/></option>
					</c:if>
				</logic:iterate>
			</select>&nbsp;&nbsp;<input type="submit" name="submit" value="Set as description &raquo;" class="button"/>
		
		</form>
	</c:if>
		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>