<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="comments" settingName="comments"/>
<bright:applicationSetting id="ratings" settingName="ratings"/>

<logic:notEmpty name="subAccessLevels">
	<logic:iterate name="subAccessLevels" id="category" indexId="index"><%@include file="group_access_levels_row.jsp" %></logic:iterate>
</logic:notEmpty>