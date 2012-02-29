
<%-- History:
	 d1		Ben Browning		31-Mar-2009			Created for Ajax data import
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

	
	
	<logic:notEmpty name="importForm" property="messages">
		<div class="logHeading"><bright:cmsWrite identifier="subhead-bulk-upload-log" filter="false"/></div>
		<ul class="log stripey">
		<logic:iterate name="importForm" property="messages" id="message" indexId="count">
			<li <c:if test="${count% 2 != 0}">class="even"</c:if>><bean:write name="message" /></li>
		</logic:iterate>
		</ul>		
		<c:if test="${importForm.parentId>0}">
			<br/>
			<a href="viewAsset?id=<bean:write name="importForm" property="parentId"/>#children"><bright:cmsWrite identifier="link-back-to" filter="false"/> <bright:cmsWrite identifier="snippet-parent" filter="false"/> <bright:cmsWrite identifier="item" filter="false"/></a>
		</c:if>
	</logic:notEmpty>		
	
	<logic:equal name="importForm" property="isImportInProgress" value="false">
		<!-- Import Complete -->
	</logic:equal>		
