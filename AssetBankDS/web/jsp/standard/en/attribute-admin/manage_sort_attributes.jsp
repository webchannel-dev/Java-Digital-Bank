<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	27-Nov-2006		Created
	 d2		Matt Stevenson	28-Nov-2006		Work on browse and search ordering
	 d3		Matt Stevenson	29-Nov-2006		Work on browse and search ordering
	 d4		Matt Stevenson	30-Nov-2006		Updates to add form
	 d5		Matt Stevenson	05-Dec-2006		Added please select to attribute list
	 d6		Matt Stevenson	08-Dec-2006		Modified sort attribute listing
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attribute Sorting</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="sorting"/>
	<bean:define id="pagetitle" value="Attributes"/>
	<bean:define id="tabId" value="attributeSorting"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>From here you can change the sort ordering for <bright:cmsWrite identifier="item" filter="false" /> searching and category browsing. Select whether you are interested in search or browse ordering with the links below then you can add, edit, delete or re-order the sort criteria for your selected area.</p> 

	<p>If you do not add any sort criteria, then by default searches will be sorted by occurrence of keywords.</p>
	
	<p>If you are modifying sort attributes, then when you have finished you should <a href="../action/viewReindexStatus">reindex existing assets</a>.<br /> It is not necessary to do this if you are just reordering existing sort attributes.</p>
	
	<p>To add a new sort criteria use the form at the bottom of the list below:</p>
	
	<p class="tabHolderPopup">
		<c:choose>
			<c:when test="${browse == 0}">
				<a class="active" href="viewManageSortAttributes">Search Ordering</a>
				<a href="viewManageSortAttributes?browse=1">Browse Ordering</a>
			</c:when>
			<c:when test="${browse == 1}">
				<a href="viewManageSortAttributes">Search Ordering</a>
				<a class="active" href="viewManageSortAttributes?browse=1">Browse Ordering</a>
			</c:when>
		</c:choose>
	</p>
	<div id="tabContent">
  	<logic:notEmpty name="sortAttributeForm" property="sortAttributes">
		<table cellspacing="0" class="admin" summary="List of Attributes">
			<tr>
				<th>Sort Attribute</th>
				<th>Sort Type</th>
				<th>Reverse Sort?</th>
				<th colspan="4">&nbsp;</th>
			</tr>
			
			<logic:iterate name="sortAttributeForm" property="sortAttributes" id="sortAttribute" indexId="index">
			
				<tr>
					<td>
						<bean:write name="sortAttribute" property="attribute.label"/>
					</td>
					<td>
						<% if (((SortAttribute)sortAttribute).getType() == SortField.STRING) { %>
								Alphanumeric or date
						<% } else if (((SortAttribute)sortAttribute).getType() == SortField.FLOAT) { %>
								Numeric
						<% } %>
					</td>
					<td>
						<c:choose>
							<c:when test="${sortAttribute.reverse == true}">
								Yes
							</c:when>
							<c:otherwise>
								No
							</c:otherwise>
						</c:choose>
					</td>
					<td class="action">
						[<c:if test="${index != 0}"><a href="moveSortAttribute?id=<bean:write name='sortAttribute' property='attribute.id'/>&up=1&sortArea=<bean:write name='sortAttribute' property='sortAreaId'/>"></c:if>up<c:if test="${index != 0}"></a></c:if>]
					</td>
					<td class="action">
						[<c:if test="${index != (sortAttributeForm.noOfSortAttributes-1)}"><a href="moveSortAttribute?id=<bean:write name='sortAttribute' property='attribute.id'/>&sortArea=<bean:write name='sortAttribute' property='sortAreaId'/>"></c:if>down<c:if test="${index != (sortAttributeForm.noOfSortAttributes-1)}"></a></c:if>]
					</td>
					<td class="action">
						[<a href="viewSortAttribute?id=<bean:write name='sortAttribute' property='attribute.id'/>&sortArea=<bean:write name='sortAttribute' property='sortAreaId'/>">edit</a>]
					</td>	
					<td class="action">
						[<a href="deleteSortAttribute?id=<bean:write name='sortAttribute' property='attribute.id'/>&sortArea=<bean:write name='sortAttribute' property='sortAreaId'/>" onclick="return confirm('Are you sure you want to remove this sort criteria?');" title="Delete this sort field">X</a>]
					</td>	
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="sortAttributeForm" property="sortAttributes">
		<p>There are currently no <c:choose><c:when test="${browse == 1}">browse</c:when><c:otherwise>search</c:otherwise></c:choose> sort criteria setup.
		The default search is sorted by keyword density (best matches have highest occurrence of given keywords).</p>
	</logic:empty>
	</div>
	


	<logic:notEmpty name="sortAttributeForm" property="errors">
		<div class="error">
		<logic:iterate name="sortAttributeForm" property="errors" id="error">
			<bean:write name="error"/><br/>
		</logic:iterate>
		</div>
	</logic:notEmpty>

	<form name="addSortForm" action="saveSortAttribute" method="post" class="floated">
		<input type="hidden" name="new" value="1"/>
		<c:choose>
			<c:when test="${browse == 0}">
				<html:hidden name="sortAttributeForm" property="sortAttribute.sortAreaId" value="1"/>
			</c:when>
			<c:otherwise>
				<html:hidden name="sortAttributeForm" property="sortAttribute.sortAreaId" value="2"/>
			</c:otherwise>
		</c:choose>
		<label for="sortField">Sort field:</label>
		<html:select name="sortAttributeForm" property="sortAttribute.attribute.id" size="1" styleId="sortField">
			<option value="0">[Please select]</option>
			<logic:iterate name="sortAttributeForm" property="attributes" id="attribute" indexId="index">
				<option value="<bean:write name='attribute' property='id'/>">
					<c:choose>
						<c:when test="${attribute.label == ''}">
							[No Label]
						</c:when>
						<c:otherwise>
							<bean:write name="attribute" property="label"/>
						</c:otherwise>
					</c:choose>
				</option>
			</logic:iterate>
		</html:select>
		<br />

		<label for="sortType">Sort Type:</label>
		<html:select name="sortAttributeForm" property="sortAttribute.type" size="1" styleId="sortType">
			<option value="<%= SortField.STRING %>">Alphanumeric or date</option>
			<option value="<%= SortField.FLOAT %>">Numeric</option>
		</html:select>
		<br />
		
		<label for="reverse">Reverse sort?:</label>
		<html:checkbox name="sortAttributeForm" property="sortAttribute.reverse" styleClass="checkbox" styleId="reverse" />
		<br />
		
		<input type="submit" name="submit" class="button flush" value="<bright:cmsWrite identifier="button-add-arrow" filter="false" />" />
		
	</form>

	
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>