<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
    d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>






<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Export</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="export"/>
	<bean:define id="pagetitle" value="Export"/>
	<bean:define id="helpsection" value="export"/>
</head>

<body id="resultsPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
		
	<p>You have successfully exported your selected <bright:cmsWrite identifier="items" filter="false" />.</p>
	
	<ul>
		<li>
			Data File: <a href="downloadExportFile?file=<bean:write name="ExportResult" property="dataFile.filePath"/>&filename=<bean:write name="ExportResult" property="dataFile.fileName"/>"><bean:write name="ExportResult" property="dataFile.fileName"/></a>
		</li>
		<logic:iterate name="ExportResult" property="zipFiles" id="zipFile">
			<li>
				Zip File: <a href="downloadExportFile?file=<bean:write name="zipFile" property="filePath"/>&filename=<bean:write name="zipFile" property="fileName"/>"><bean:write name="zipFile" property="fileName"/></a>
			</li>
		</logic:iterate>
	</ul>
	<br/>
	
	<p>You may now <a href="deleteExportedAssets?filename=<bean:write name="ExportResult" property="dataFile.filePath"/>" onClick="return confirm('Are you sure you want to delete these <bright:cmsWrite identifier="items" filter="false" />? This operation cannot be undone!')">delete the exported <bright:cmsWrite identifier="items" filter="false" /></a> from the system.</p>
  	

	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>