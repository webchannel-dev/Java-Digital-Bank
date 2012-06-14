<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:present name="assetBoxDownloadForm"> 
	<logic:equal name="assetBoxDownloadForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetBoxDownloadForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
</logic:present>

<logic:present name="assetBoxForm">
	<logic:notEmpty name="assetBoxForm" property="updateMessage">
		<div class="info">
			<bean:write name="assetBoxForm" property="updateMessage"/>
		</div>
	</logic:notEmpty>
	<logic:equal name="assetBoxForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetBoxForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
</logic:present>