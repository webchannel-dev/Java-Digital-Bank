<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Incorporated categories
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	19-May-2005		Added javascript for selecting parent categories
	 d5		Matt Stevenson	14-Jun-2005		Modified parent category permission setting
	 d6      Steve Bryan    29-Dec-2005    Updated for org units
	 d7		Ben Browning	21-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	<logic:equal name="divisionForm" property="division.id" value="0">	
		<bean:define id="pagetitle" value="Add Division"/>
	</logic:equal>
	<logic:notEqual name="divisionForm" property="division.id" value="0">	
		<bean:define id="pagetitle" value="Edit Division"/>
	</logic:notEqual>

	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="divisions"/>	
	<script type="text/JavaScript">
		// give the division field the focus once the dom is ready
		$j(function () {
  			$j('#divisionDesc').focus();
		});
	</script>		
</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="divisionForm">
		<logic:equal name="divisionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="divisionForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form action="saveDivision" method="post" styleClass="floated">
		<html:hidden name="divisionForm" property="division.id"/>

		<label for="divisionDesc">Name:</label>
		<html:text styleClass="text" styleId="divisionDesc" name="divisionForm" property="division.description" size="30" maxlength="60"/>
		<br />
		
		<div class="hr"></div>
		

		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listDivisions" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>


	<%@include file="../inc/body_end.jsp"%>
</body>
</html>