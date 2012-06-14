<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>

<c:set var="hideActions" value="true" scope="request" />

<logic:notEmpty name="homepageForm" property="currentMessagesBox">  
	<div class="leftShadow">
		<div class="rightShadow">
			<div class="browsePanel messagePanel">
				<bean:define id="mgroup" value="CURRENT"/>
				<bean:define id="messageSummaries" name="homepageForm" property="currentMessagesBox.latestMessageSummaries" /> 
				<h3><bright:cmsWrite identifier="heading-new-user-messages" filter="false"/> (<bean:write name="homepageForm" property="currentMessagesBox.messagesTotal"/>)</h3>
				<%@include file="../public/inc_messages_table.jsp"%>
				<p><a id="messagesLink" href="../action/viewUserMessages?mgroup=CURRENT"><bright:cmsWrite identifier="link-view-all-messages" filter="false"/></a></p>
			</div>
		</div>
	</div>
</logic:notEmpty>

<logic:notEmpty name="homepageForm" property="acknowledgedMessagesBox">  
	<div class="leftShadow">
		<div class="rightShadow">
			<div class="browsePanel messagePanel">
				<bean:define id="mgroup" value="ARCHIVE"/>
				<bean:define id="messageSummaries" name="homepageForm" property="acknowledgedMessagesBox.latestMessageSummaries" /> 
				<h3><bright:cmsWrite identifier="heading-acknowledged-user-messages" filter="false"/></h3>
				<%@include file="../public/inc_messages_table.jsp"%>
				<p><a id="messagesLink" href="../action/viewUserMessages?mgroup=ARCHIVE"><bright:cmsWrite identifier="link-view-archived-messages" filter="false"/></a></p>
			</div>
		</div>
	</div>
</logic:notEmpty>