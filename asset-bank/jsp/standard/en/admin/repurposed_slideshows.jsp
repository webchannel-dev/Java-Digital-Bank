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

	<bean:define id="section" value="content"/>
	<bean:define id="helpsection" value="repurposedSlideshows"/>
	<bean:define id="tabId" value="repurposeSlideshows"/>

	<title><bright:cmsWrite identifier="title-repurposed-slideshows" filter="false"/></title> 
	
	<%@include file="../inc/head-elements.jsp"%>	

</head>

<body id="adminPage">


	<%@include file="../inc/body_start.jsp"%> 
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-slideshows" filter="false" /></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	
	<logic:notEmpty name='repurposingForm' property='repurposedVersions'>

		<p>The following is a list of all the embeddable slideshows currently stored in the system. To get the HTML to use for embedding click the [view code] link next to the slideshow you are interested in. You can also remove, download and preview the slideshows from this page:</p>
	

		<table class="list highlight" cellspacing="0">
			<thead>
			<tr>
				<th>Name:</th>
				<th>Type:</th>
				<th>Created:</th>
				<th>On homepage?:</th>
				<th>Refresh?:</th>
				<th>Actions:</th>
			</tr>
			</thead>
			<tbody>
			<logic:iterate name="repurposingForm" property="repurposedVersions" id="version">
				<tr>
					<td><bean:write name="version" property="description"/></td>
					<td><bean:write name="version" property="displayType"/></td>
					<td><bean:write name="version" property="createdDate" format="dd/MM/yyyy HH:mm"/> by <bean:write name="version" property="createdByUser.username"/></td>
					<td><c:choose><c:when test="${version.showInListOnHomepage}"><span class="green">Yes</span></c:when><c:otherwise>No</c:otherwise></c:choose><c:choose><c:when test="${version.defaultOnHomepage}"> (default)</c:when></c:choose> (<a href="viewEditRepurposedSlideshow?id=<bean:write name='version' property='id'/>">edit</a>)</td>
					<td><c:choose><c:when test="${version.searchCriteria != null}"><span class="green">Yes</span></c:when><c:otherwise>No</c:otherwise></c:choose></td>
					<td class="action">
						[<a href="viewRepurposedSlideshow?id=<bean:write name='version' property='id'/>&amp;editing=1">view code</a>] 

						

						[<a href="previewRepurposedSlideshow?id=<bean:write name='version' property='id'/>" target="_blank" class="help-popup">preview</a>] 
	
						[<a href="downloadRepurposedSlideshow?id=<bean:write name='version' property='id'/>">download</a>]
						
						[<a href="deleteRepurposedSlideshow?id=<bean:write name='version' property='id'/>" onclick="return confirm('Are you sure you want to delete this slideshow? This will affect all sites that the slideshow has been embedded into');" title="Delete">X</a>] 
					</td>
				</tr>
			</logic:iterate>
			</tbody>
		</table>
		<br />
		<p><a href="viewReorderSlideshows">Reorder homepage slideshows &raquo;</a></p>
	</logic:notEmpty>

	<logic:empty name='repurposingForm' property='repurposedVersions'>
		
		<p>There are currently no embedabble slideshows stored in the system.</p>
		
	</logic:empty>

	<!-- end of left col -->
	<div class="clearing">&nbsp;</div>

   	  
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
