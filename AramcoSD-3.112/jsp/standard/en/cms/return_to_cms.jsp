<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		21-Sep-2006		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="cmsType" settingName="cms-type"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Return to CMS</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/cms-integration.js" type="text/javascript"></script>
	<bean:define id="section" value="download"/>
	<bean:define id="pagetitle" value="Return to CMS"/>
	
	<%-- If no callback url has been set then use the old JS functionality --%>
	<c:if test="${userprofile.cmsInfo.callbackUrl==''}">
		<script type="text/javascript" language="JavaScript">
			function doReturnToCMS()
			{
				<c:if test="${not empty downloadForm.cmsCallbackElementId}">
					try
					{
						window.opener.document.getElementById('<bean:write name='downloadForm' property='cmsCallbackElementId'/>').value = '<bean:write name='downloadForm' property='cmsCallbackElementValue'/>';
					}
					catch(e)
					{
						if(!confirm(e + '\n\nCannot pass identifier \'<bean:write name='downloadForm' property='cmsCallbackElementValue'/>\' to the calling page.'))
						{
							return false;
						}
					}
				</c:if>
				selectCMSFile('<bean:write name='downloadForm' property='cmsReturnUrl'/>', '<bean:write name='cmsType'/>');
		</script>
	</c:if>
	
</head>
<body id="downloadPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 			
	
	<logic:equal name="downloadForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="downloadForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	
	<logic:equal name="downloadForm" property="hasErrors" value="false"> 
			
		<p>
		Your image has been resized and moved into the CMS repository. You must now click the button below to return to the CMS.
		</p>		
		
		<c:choose>
			<%-- If a callback URL has been set post the data to that URL --%>
			<c:when test="${userprofile.cmsInfo.callbackUrl != ''}">
				<form action="<c:out value='${userprofile.cmsInfo.callbackUrl}'/>" onclick="clearCmsMode();" method="post">
					<input type="hidden" name="placeholder" value="<c:out value='${userprofile.cmsInfo.placeholder}'/>" />
					<input type="hidden" name="xml" value="<bean:write name='downloadForm' property='attributeXml'/>" />
					<input type="submit" name="returnButton" class="button" value="Return to CMS"/>
				</form>
				
			</c:when>
			<%-- If a callback URL has not been set then use Javascript to send the data back (original functionality) --%>
			<c:otherwise>
			<p>
				<input type="submit" name="returnButton" class="button flush" value="Return to CMS" onclick="doReturnToCMS();">
			</p>
			</c:otherwise>
		</c:choose>
	</logic:equal>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>