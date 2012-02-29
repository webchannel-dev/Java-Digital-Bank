<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	02-Dec-2005		Created
	 d2      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d3      Martin Wilson   23-May-2008    Updated for ExifTool
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | View Embedded Metadata</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Image Details"/>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-image-details" filter="false" /></h1> 

	<div class="head">
		<a href="viewAsset?id=<bean:write name='embeddedMetadataForm' property='assetId'/>"><bright:cmsWrite identifier="link-back" filter="false" /></a>
	</div>
					
		<h2><bright:cmsWrite identifier="subhead-embedded-metadata" filter="false"/></h2>
		<logic:notEmpty name="embeddedMetadataForm" property="metadata">
			<!-- Holding div required in case long strings in the embedded data break the layout -->
			<div style="width:100%; overflow:hidden">
				<table class="form" cellspacing="0" cellpadding="0">
				<logic:iterate name="embeddedMetadataForm" property="metadata" id="field">
					<tr>
						<th><bean:write name="field" property="key"/>:</th>
						<td class="padded"><bright:write name="field" property="value" formatCR="true" filter="false"/></td>
					</tr>
				</logic:iterate>
				</table>
			</div>
		</logic:notEmpty>
		<logic:empty name="embeddedMetadataForm" property="metadata">
			<p><bright:cmsWrite identifier="snippet-no-embedded-data" filter="false"/></p>
		</logic:empty>


	<%@include file="../inc/body_end.jsp"%>

</body>
</html>