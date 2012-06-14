<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	05-Sep-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting settingName="supportMultiLanguage" id="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Send Marketing Email</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="helpsection" value="marketing-edit-email"/>
	<bean:define id="tabId" value="sendEmail"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>


<script type="text/javascript">
	
	function updateUrl(ctrl)
	{
		var url = document.getElementById('url').value;
		
		if (url=="")
		{
			alert ("Please paste a URL into the URL box to apply a filter");
		}
		else
		{
			//If it already contains changeSelectedFilterAndRedirect then it has already been generated once
			if (url.match("changeSelectedFilterAndRedirect"))
			{
				url = url.replace(/filterId=.*/,"filterId=") + document.getElementById('filterId0').value;
				document.getElementById('url').value = url;
			}	
			else
			{
				url = url.replace("?","&");
				url = url.replace("/action/","/action/changeSelectedFilterAndRedirect?action=") + "&filterId=" + document.getElementById('filterId0').value;
				document.getElementById('url').value = url;
				alert ("The URL has been updated with the selected filter. Please copy and paste it into the email body.");
			}
		}
	}

	</script>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>
	
	<h2>Send Marketing Email</h2>

	<logic:present  name="sendMarketingEmailForm">
		<logic:equal name="sendMarketingEmailForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="sendMarketingEmailForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<logic:notEmpty name="sendMarketingEmailForm" property="assetLink">
		<p>To include a link to the assets currently in your lightbox, either paste this url somewhere in the email body, replacing the domain if necessary:
		<strong><bean:write name="sendMarketingEmailForm" property="assetLink" filter="false"/></strong></p>
	</logic:notEmpty>
	
	<bright:refDataList id="filterGroups" transactionManagerName="DBTransactionManager" componentName="FilterManager" methodName="getFiltersByGroupFilters" passUserprofile="true"/>
	<logic:notEmpty name="filterGroups">
	
		<p>If you wish to change the filter used when viewing the link please paste the link into the box below, select a filter and then click "update link".</p>
		<table cellspacing="0" class="form" style="width:100%;" summary="Form for email content">
			<tr>
				<th>
					URL
				</th>
				<td>
					<input type="text" name="url" id="url" maxlength="255" size="80" value="<logic:notEmpty name="sendMarketingEmailForm" property="assetLink"><bean:write name="sendMarketingEmailForm" property="assetLink" filter="false"/></logic:notEmpty>"/>
				</td>
			</tr>
			<tr>
				<th>
					Filter
				</th>
				<td>
					<form name="filterForm" action="changeSelectedFilter" id="filterForm" method="get" style="display: inline;">
						<logic:iterate name="filterGroups" id="group" indexId="groupIndex">
							<bean:define id="filters" name="group" property="filters"/>
							<logic:notEmpty name="filters">
								<span class="filterset <c:if test='${groupIndex == 0}'> filterset_first</c:if>">
								<logic:notEmpty name="group" property="name">
									<label for="filterId<bean:write name='group' property='id'/>"><bean:write name="group" property="name"/>:</label>
								</logic:notEmpty> 
								
								<select name="filterId<bean:write name='group' property='id'/>" id="filterId<bean:write name='group' property='id'/>" >
									<option value="-1">[No filter]</option>
									<logic:iterate name="filters" id="currentFilter">
										<bean:define id="filterId" name="currentFilter" property="id"/>
										<bean:define id="selectedFilter" name="userprofile" property='<%= "isSelectedFilter(" + filterId + ")" %>'/>
										<option value="<bean:write name='currentFilter' property='id'/>" <c:if test='${selectedFilter}'>selected="selected"<c:set var="found" value="true"/></c:if>><bean:write name="currentFilter" property="name" filter="false"/></option>
									</logic:iterate>
								</select>
							</logic:notEmpty>
							</span>
						</logic:iterate>
					</form>
				</td>
			</tr>
			
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" name="send" value="Update URL" class="button flush" onclick="updateUrl(this);"/>
				</td>
			</tr>
		</table>
	
	</logic:notEmpty>
	
	
	<p>WARNING: The variable <strong>#recipients#</strong> is automatically replaced by the system with the addresses of the target users. 
	You may move this variable between address fields, but do not remove it completely.<p/>
	
	<p>Note, you can <logic:notEmpty name="sendMarketingEmailForm" property="assetLink">also</logic:notEmpty> use the variables <strong>#title#</strong>, <strong>#forename#</strong>, <strong>#surname#</strong> and <strong>#username#</strong> in order to personalise the message for the receiving user.</p>
	
	<html:form action="previewMarketingEmail" method="post">
		
		<html:hidden name="sendMarketingEmailForm" property="assetBoxId"/>
		
		<bean:parameter multiple="true" name="selectedUsers" id="selectedUsers"/>
		<logic:iterate name="selectedUsers" id="userId">
			<input type="hidden" name="selectedUsers" value="<bean:write name="userId"/>"/>
		</logic:iterate>
		
		<table cellspacing="0" class="form" style="width:100%;" summary="Form for email content">
			<tr>
				<th>
					From Address:
				</th>
				<td>
					<input type="hidden" name="mandatory_fromAddress" value="Please enter a from address" />
					<html:text name="sendMarketingEmailForm" property="fromAddress" maxlength="255" size="40"/>
				</td>
			</tr>
			<tr>
				<th>
					To Address:
				</th>
				<td>
					<c:choose>
						<c:when test="${empty sendMarketingEmailForm.toAddress && empty sendMarketingEmailForm.ccAddress && empty sendMarketingEmailForm.bccAddress}">
							<c:set var="toAddress">#recipients#</c:set>
						</c:when>
						<c:otherwise>
							<c:set var="toAddress"><bean:write name="sendMarketingEmailForm" property="toAddress" filter="false"/></c:set>
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="mandatory_toAddress" value="Please enter a to address" />
					<input type="hidden" name="toAddress" value="<bean:write name='toAddress' filter='false'/>" />
					<bean:write name='toAddress' filter='false'/>
					<!-- input type="text" name="toAddress" maxlength="255" size="40" value="<bean:write name="toAddress" filter="false"/>"/ -->
				</td>
			</tr>
			<tr>
				<th>
					CC Address:
				</th>
				<td>
					<html:text name="sendMarketingEmailForm" property="ccAddress" maxlength="255" size="40"/>
				</td>
			</tr>
			<tr>
				<th>
					BCC Address:
				</th>
				<td>
					<html:text name="sendMarketingEmailForm" property="bccAddress" maxlength="255" size="40"/>
				</td>
			</tr>
			<tr>
				<th>
					Email Subject:
				</th>
				<td>
					<input type="hidden" name="mandatory_emailContent.subject" value="Please enter a subject" />
					<html:text name="sendMarketingEmailForm" property="emailContent.subject" maxlength="80" size="40"/>
				</td>
			</tr>
			<logic:notEmpty name="sendMarketingEmailForm" property="emailContent.translations">
				<logic:iterate name="sendMarketingEmailForm" property="emailContent.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="subject<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].language.id"/>
								<html:hidden name="sendMarketingEmailForm" property="emailContent.translations[${tIndex}].language.name"/>
								<input type="text" name="emailContent.translations[<bean:write name='tIndex'/>].subject" maxlength="80" size="40" id="subject<bean:write name='tIndex'/>" value="<bean:write name="translation" property="subject" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
			<tr>
				<th>
					Email Body:
				</th>
				<td>
					<input type="hidden" name="mandatory_emailContent.body" value="Please enter a body" />
					<html:textarea styleClass="editor" name="sendMarketingEmailForm" property="emailContent.body"/>
				</td>
			</tr>
			<logic:notEmpty name="sendMarketingEmailForm" property="emailContent.translations">
				<logic:iterate name="sendMarketingEmailForm" property="emailContent.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="body<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<textarea class="editor" name="emailContent.translations[<bean:write name='tIndex'/>].body" id="body<bean:write name='tIndex'/>"><bean:write name="translation" property="body" filter="false"/></textarea>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
			<tr><td>&nbsp;</td></tr>
		</table>

		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="Preview &raquo;" /> 
		<a href="viewSendMarketingEmail" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>