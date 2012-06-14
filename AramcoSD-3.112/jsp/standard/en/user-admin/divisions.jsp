<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Added link to add group
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	03-Jan-2005		Added organisation units
	 d5		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Manage Divisions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="divisions"/>
	<bean:define id="pagetitle" value="Divisions"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="userForm">
		<logic:equal name="userForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bean:write name="error" filter="false"/>
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2>Manage Divisions</h2>
	
		<table cellspacing="0" class="admin" summary="List of divisions">
		<tr>
			<th>Division Name</th>
			<th colspan="2">&nbsp;</th>
		</tr>
		<logic:iterate name="divisionForm" property="listDivisions" id="division">
			<tr>
				<td><bean:write name="division" property="description"/></td>
				<td class="action">
					[<a href="viewDivision?id=<bean:write name='division' property='id'/>">edit</a>]
				</td>
				<td class="action">
					[<a href="deleteDivision?id=<bean:write name='division' property='id'/>" onclick="return confirm('Are you sure you want to delete this division?');">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>		
	
	<div class="hr"></div>
	
	<p><a href="viewDivision">Add a division &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>