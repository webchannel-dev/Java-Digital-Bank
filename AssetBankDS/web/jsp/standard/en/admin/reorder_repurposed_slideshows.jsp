<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		15-Dec-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>

	<bean:define id="section" value="repurposeSlideshows"/>
	<bean:define id="helpsection" value="repurposedSlideshows"/>
	<bean:define id="tabId" value="repurposeSlideshows"/>
	
	<title><bright:cmsWrite identifier="title-repurposed-slideshows" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<script src="../js/lib/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script>
	<link type="text/css" href="../css/jquery-ui/jquery-ui-1.8.5.custom.css" rel="Stylesheet" />
	
	<script type="text/javascript">
		$j(function(){
			//DOM ready, init sorting:
			initSorting('../action/reorderSlideshow');
		})
	</script>

</head>

<body id="adminPage">


	<%@include file="../inc/body_start.jsp"%> 
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-slideshows" filter="false" /></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<h2>Reorder Home Page Slideshows</h2>
	
	<logic:notEmpty name='repurposingForm' property='repurposedVersions'>

		<p>The following is a list of all the slideshows currently displayed on the homepage. To reorder this list please drag a slideshow to a new location in the list:</p>
	
		<table class="list highlight" cellspacing="0">
			<thead>
			<tr>
				<th>Name:</th>
				<th>Type:</th>
				<th>Created:</th>
			</tr>
			</thead>
			<tbody>
			<logic:iterate name="repurposingForm" property="repurposedVersions" id="version">
				<tr class="id<bean:write name="version" property="id"/>">
					<td class="sort"><bean:write name="version" property="description"/></td>
					<td><bean:write name="version" property="displayType"/></td>
					<td><bean:write name="version" property="createdDate" format="dd/MM/yyyy HH:mm"/> by<bean:write name="version" property="createdByUser.username"/></td>
				</tr>
			</logic:iterate>
			</tbody>
		</table>

	
	</logic:notEmpty>

	<logic:empty name='repurposingForm' property='repurposedVersions'>
		
		<p>There are currently no embedabble slideshows stored in the system.</p>
		
	</logic:empty>


	<div class="clearing">&nbsp;</div>
	
	<br />
	<a href="viewRepurposedSlideshows">&laquo; Back</a>  

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
