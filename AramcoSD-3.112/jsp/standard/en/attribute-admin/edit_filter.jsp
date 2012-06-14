<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	24-Sep-2007		Created
	 d2     Matt Woollard   23-May-2008     Created
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


<head>
	<c:if test="${filterForm.type == 1}">
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Filters</title> 
	</c:if>
	<c:if test="${filterForm.type == 2}">
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Templates</title> 
	</c:if>
	
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="filters"/>

	<c:if test="${filterForm.type == 1}">
		<bean:define id="type" value="Filter"/>
		<c:choose>
			<c:when test="${filterForm.filter.id > 0}">
				<bean:define id="pagetitle" value="Edit Filter"/>
			</c:when>
			<c:otherwise>
				<bean:define id="pagetitle" value="Add Filter"/>
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${filterForm.type == 2}">
		<bean:define id="type" value="Template"/>
		<c:choose>
			<c:when test="${filterForm.filter.id > 0}">
				<bean:define id="pagetitle" value="Edit Template"/>
			</c:when>
			<c:otherwise>
				<bean:define id="pagetitle" value="Add Template"/>
			</c:otherwise>
		</c:choose>
	</c:if>
	<bean:define id="tabId" value="filters"/>
	
	<script type="text/javascript">
	<!--
		function showCats ()
		{
			document.getElementById('cats').style.display = 'block';
			document.getElementById('filterGroups').style.display = 'none';
			document.getElementById('catsLink').style.display = 'none';
		}

		function showAls ()
		{
			document.getElementById('als').style.display = 'block';
			document.getElementById('filterGroups').style.display = 'none';
			document.getElementById('alsLink').style.display = 'none';
		}

		$j(function(){
			initDatePicker();
		})

	//-->

	</script>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<c:if test="${filterForm.type == 1}">
		<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	</c:if>
	<c:if test="${filterForm.type == 2}">
		<%@include file="../user-admin/inc_user_profile_tabs.jsp"%>
	</c:if>

	<logic:notEmpty name="filterForm" property="errors">
		<div class="error">
			<logic:iterate name="filterForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:notEmpty>
	
	<p>Enter the name of this <c:out value="${type}"/> and the values to match for the selected attributes in the form below and click the 'Save' button:</p> 

	<form name="filterForm" action="saveFilter" method="post" enctype="multipart/form-data">
		
		<html:hidden name="filterForm" property="filter.id"/>
		<html:hidden name="filterForm" property="filter.isDefault"/>
		<html:hidden name="filterForm" property="type"/>
	
		<table cellspacing="0" class="form" summary="Form for filter details" style="margin:0;">
			<tr>
				<th>
					<label for="name"><c:out value="${type}"/> name:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="filterForm" property="filter.name" maxlength="255" styleId="name" />
				</td>
			</tr>
		
			<logic:notEmpty name="filterForm" property="filter.translations">
				<logic:iterate name="filterForm" property="filter.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="name<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="filterForm" property="filter.translations[${tIndex}].language.id"/>
								<html:hidden name="filterForm" property="filter.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="filter.translations[<bean:write name='tIndex'/>].name" maxlength="255" id="name<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
		</table>	
		<c:set var="matchedCat" value="false"/>
		<div id="cats">
			<table cellspacing="0" class="form" summary="Form for filter details" style="margin:0;">
				<tr>
					<th>
						<label for="categories">Linked to Category:</label> 
					</th>
		
					<td style="padding-bottom: 0.5em">
						<c:set var="categories" value="${filterForm.categories}"/>
						<%@include file="inc_filter_categories.jsp"%>
					</td>
				</tr>
			</table>
		</div>
		<div id="catsLink" style="display: none; margin: 0.5em 0 0.5em 110px; "><a href='#' onclick="showCats();">Show in category &raquo;</a>
		</div>
		<div id="als">
			<table cellspacing="0" class="form" summary="Form for filter details" style="margin:0;">
				<tr>
					<th>
						<label for="categories">Linked to Access Level:</label> 
					</th>

					<td>
						<c:set var="categories" value="${filterForm.accessLevels}"/>
						<%@include file="inc_filter_categories.jsp"%>
					</td>
				</tr>
			</table>
		</div>
		<div id="alsLink" style="display: none; margin-left: 110px;"><a href='#' onclick="showAls();">Show in access level &raquo;</a>
		</div>
		<div id="filterGroups">
			<table cellspacing="0" class="form" summary="Form for filter details" style="margin:0;">
				<c:if test="${filterForm.type == 1}">
				<logic:notEmpty name="filterForm" property="filterGroups">
				<tr>
					<th>
						<label for="name">Filter groups:</label> 
					</th>
					
					<td>
						<logic:iterate name="filterForm" property="filterGroups" id="group" indexId="index">
							<bean:define id="groupId" name="group" property="id"/>
							<bean:define id="hasValue" name="filterForm" property="<%= \"filter.filterInGroup(\" + groupId + \")\" %>"/>
							<input type="radio" name="group" id="group<bean:write name='index'/>" value="<bean:write name='group' property='id'/>" class="checkbox" <logic:equal name="hasValue" value="true">checked</logic:equal>><label for="group<bean:write name='index'/>"><bean:write name="group" property="name"/></label><br/>
						</logic:iterate>
					</td>
				</tr>
				</logic:notEmpty>
				</c:if>
			</table>
		</div>
		<script type="text/javascript">
			<!--
				<c:choose>
					<c:when test="${!matchedCat}">
						document.getElementById('cats').style.display = 'none';
						document.getElementById('catsLink').style.display = 'block';
						document.getElementById('als').style.display = 'none';
						document.getElementById('alsLink').style.display = 'block';
					</c:when>
					<c:otherwise>
						document.getElementById('filterGroups').style.display = 'none';
					</c:otherwise>
				</c:choose>
			-->
		</script>
		<div class="hr">&nbsp;</div>
		<h2>Filter Criteria</h2>	
		<table cellspacing="0" class="form" summary="Form for filter details" style="margin:0;">
		


			
			<logic:iterate name="filterForm" property="availableAttributes" id="attribute" indexId="index">
				<bean:define id="attributeId" name="attribute" property="id"/>
				<bean:define id="attributeValue" name="filterForm" property="<%= \"filter.attributeValueForAttribute(\" + attributeId + \")\" %>"/>
				<bean:define id="setAttributeValue" value="false"/>
				<bean:define id="currentFilter" name="filterForm" property="filter"/>
				<bean:define id="editingFilter" value="true"/>
				<%@include file="../asset-admin/inc_attribute_field.jsp"%>
			</logic:iterate>


			<tr>
				<th>
					<label for="categories">Categories:</label> 
				</th>
				<td></td>
				<td>
					<logic:notEmpty name="filterForm" property="categories">
						<logic:notEmpty name="filterForm" property="categories.categories">
							<logic:iterate name="filterForm" property="categories.categories" id="cat">
								<logic:notEqual name="cat" property="depth" value="0">
									<bean:define id="categoryId" name="cat" property="id"/>
									<bean:define id="hasValue" name="filterForm" property="<%= \"filter.hasSelectedCategory(\" + categoryId + \")\" %>"/>
									<c:if test="${cat.depth > 1}"><c:forEach begin="2" end="${cat.depth}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach></c:if><input type="checkbox" name="cat<bean:write name='cat' property='id'/>" id="cat<bean:write name='cat' property='id'/>" class="checkbox" value="<bean:write name='cat' property='id'/>" <logic:equal name="hasValue" value="true">checked</logic:equal>/><label for="cat<bean:write name='cat' property='id'/>"><bean:write name="cat" property="name"/></label><br/>

								</logic:notEqual>
							</logic:iterate>
						</logic:notEmpty>
					</logic:notEmpty>
					
				</td>
			</tr>
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>

			<tr>
				<th>
					<label for="accessLevels">Access Levels:</label> 
				</th>
				<td></td>
				<td>
					<logic:notEmpty name="filterForm" property="accessLevels">
						<logic:notEmpty name="filterForm" property="accessLevels.categories">
							<logic:iterate name="filterForm" property="accessLevels.categories" id="cat">
								<logic:notEqual name="cat" property="depth" value="0">
									
									<bean:define id="categoryId" name="cat" property="id"/>
									<bean:define id="hasValue" name="filterForm" property="<%= \"filter.hasSelectedAccessLevel(\" + categoryId + \")\" %>"/>
									<c:if test="${cat.depth > 1}"><c:forEach begin="2" end="${cat.depth}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach></c:if><input type="checkbox" name="al<bean:write name='cat' property='id'/>" id="al<bean:write name='cat' property='id'/>" class="checkbox" value="<bean:write name='cat' property='id'/>" <logic:equal name="hasValue" value="true">checked</logic:equal>/><label for="al<bean:write name='cat' property='id'/>"><bean:write name="cat" property="name"/></label><br/>

								</logic:notEqual>
							</logic:iterate>
						</logic:notEmpty>
					</logic:notEmpty>
	
				</td>
			</tr>

		</table>

		<br/>If multiple categories/access levels are selected then assets will only be returned if they are in all of the selected categories/access levels.

		<div class="hr"></div>
		

		<input class="button floated flush" type="submit" value="Save" />
		<a href="../action/viewManageFilters?type=1" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</form>
	
		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>