<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Set the print DPI and screen DPI here --%>
<c:set var="printDPI" value="${300}"/>
<c:set var="screenDPI" value="${96}"/>

<bright:applicationSetting id="useMetric" settingName="show-sizes-in-metric" />
<bright:applicationSetting id="useImperial" settingName="show-sizes-in-imperial" />




<c:if test="${useMetric}">
	<c:set var="printHeight" value="${height/printDPI*25.4}"/>
	<c:set var="printWidth" value="${width/printDPI*25.4}"/>
	<c:set var="screenHeight" value="${height/screenDPI*25.4}"/>
	<c:set var="screenWidth" value="${width/screenDPI*25.4}"/>
	<bean:write name="printWidth" format="0"/> x <bean:write name="printHeight" format="0"/> 
	<bright:cmsWrite identifier="snippet-mm-print" filter="false" replaceVariables="true"/>
	<bean:write name="screenWidth" format="0"/> x <bean:write name="screenHeight" format="0"/> 
	<bright:cmsWrite identifier="snippet-mm-screen" filter="false" replaceVariables="true"/>
</c:if>

<c:if test="${useImperial}">
	<c:set var="printHeight" value="${height/printDPI}"/>
	<c:set var="printWidth" value="${width/printDPI}"/>
	<c:set var="screenHeight" value="${height/screenDPI}"/>
	<c:set var="screenWidth" value="${width/screenDPI}"/>	
	<bean:write name="printWidth" format="0.0"/> x <bean:write name="printHeight" format="0.0"/> 	
	<bright:cmsWrite identifier="snippet-inches-print" filter="false" replaceVariables="true" />
	<bean:write name="screenWidth" format="0.0"/> x <bean:write name="screenHeight" format="0.0"/> 
	<bright:cmsWrite identifier="snippet-inches-screen" filter="false" replaceVariables="true" />
</c:if>