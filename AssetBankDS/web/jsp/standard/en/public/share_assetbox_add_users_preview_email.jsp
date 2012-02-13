<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	22-Feb-2006		Tidied up html
	 d4		Ben Browning	11-Dec-2007		Added javascript zoom
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="largestDimension" settingName="zoom-initial-size"/>
<bright:applicationSetting id="zoomEnabled" settingName="large-image-has-zoom"/>

<head>
	<meta http-equiv="imagetoolbar" content="no" />
	<title><bright:cmsWrite identifier="company-name" filter="false" />| Preview Email</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link href="../css/emailPreview.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
		body { background-color: #d1d1d1;  }
	</style>
</head>

<body>

	<div class="content">

	<h1>Email Preview</h1>
	
		<logic:iterate name="shareAssetBoxForm" property="previewEmails" id="email" indexId="index">
			<table class="email" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" class="language">
						<h2><c:out value="${shareAssetBoxForm.languages[index].name}"/></h2>
					</td>
				</tr>
				<tr class="odd">
					<th>To:</th>
					<td>
						<logic:notEmpty name="email" property="toList">
							<logic:iterate name="email" property="toList" id="address" indexId="index"><c:if test="${index>0}">;&nbsp;</c:if><bean:write name="address" property="address" filter="false"/></logic:iterate>
						</logic:notEmpty>
						<logic:empty name="email" property="toList">
							<span class="disabled">n/a</span>
						</logic:empty>
					</td>
				</tr>
				<tr>
					<th>CC:</th>
					<td>
						<logic:notEmpty name="email" property="ccList">
							<logic:iterate name="email" property="ccList" id="address" indexId="index"><c:if test="${index>0}">;&nbsp;</c:if><bean:write name="address" property="address" filter="false"/></logic:iterate>
						</logic:notEmpty>
						<logic:empty name="email" property="ccList">
							<span class="disabled">n/a</span>
						</logic:empty>
					</td>
				</tr>
				<tr class="odd">
					<th>BCC:</th>
					<td>
						<logic:notEmpty name="email" property="bccList">
							<logic:iterate name="email" property="bccList" id="address" indexId="index"><c:if test="${index>0}">;&nbsp;</c:if><bean:write name="address" property="address" filter="false"/></logic:iterate>
						</logic:notEmpty>
						<logic:empty name="email" property="bccList">
							<span class="disabled">n/a</span>
						</logic:empty>
					</td>
				</tr>
				<tr>
					<th>From:</th>
					<td><bean:write name="email" property="fromAddress.address" filter="false"/></td>
				</tr>
				<tr class="odd">
					<th>Subject:</th>
					<td><bean:write name="email" property="subject" filter="false"/></td>
				</tr>
				<tr>
					<td colspan="2" class="body"><bean:write name="email" property="htmlMsg" filter="false"/></td>
				</tr>		
			</table>
		</logic:iterate>

		<div style="text-align:right; margin-top:2em;">
			<script type="text/javascript">
					document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
			</script>
		</div>
	
	</div>

</body>
</html>
