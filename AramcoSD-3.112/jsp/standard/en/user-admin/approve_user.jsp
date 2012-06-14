<%@include file="../inc/doctype_html_admin.jsp" %>


<%-- History:
	 d1		Martin Wilson	02-Aug-2005		Created
	 d2		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	 d3	 	Matt Woollard	23-Sep-2009	    Added functionality to set a user expiry date
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showAddressFields" settingName="users-register-with-address" />
<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Approve User</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="helpsection" value="approve_users"/>
	<bean:define id="pagetitle" value="Approve User"/>

	
	<script type="text/javascript">
		<!--  
      function changedApproveStatus(ctrl)
      {
			if (ctrl != null)
			{
	      	if(ctrl.selectedIndex==ctrl.options.length-1)
		   	{
					document.getElementById('approvePassword').style.display="none";
					document.getElementById('message').innerHTML="Rejection Message";
				}
				else
				{
					document.getElementById('approvePassword').style.display="block";
					document.getElementById('message').innerHTML="Message";
				}
			}
      }

		// when dom is ready
		$j(function () {
			initDatePicker();
		});
		//-->
	</script>
	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="userForm">
		<logic:equal name="userForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bean:write name="error"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form action="approveUser" method="post">
	
			<%@include file="inc_user_fields_readonly.jsp"%>
		
			<%-- Register date --%>
		<tr>
			<th>
				<bright:cmsWrite identifier="label-user-register-date" filter="false"/>
			</th>
			<td class="padded">
				<fmt:formatDate value="${userForm.user.registerDate}" pattern="${dateFormat}" />
			</td>
		</tr>

			<%-- Show registration message if there is one --%>
			<c:if test="${not empty userForm.user.registrationInfo}">
				<tr>
					<th><bright:cmsWrite identifier="label-user-reg-message" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.registrationInfo"/>
					</td>
				</tr>		
			</c:if>
			</table>
		
			<div class="hr"></div>
			
			<h3>User Permissions</h3>
			<%@include file="inc_user_permissions.jsp"%>
			
			<div class="hr"></div>
			
			<h3>Approval Status</h3>
	
			<table cellspacing="0" class="form" summary="Form for user permissions" style="margin:0;">
				<tr>
					<th>Approve?:</th>
					<td>
						<select name="approved" style="width:100px;" onchange="changedApproveStatus(this);">
							<option value="1" selected="true">Approve</option>
							<option value="0">Reject</option>
						</select>
					</td>
				</tr>
			</table>
			
			<table class="form" cellspacing="0" style="margin:0;">	
				<tr>
					<th><label for="expiry"><bright:cmsWrite identifier="label-user-expiry-date" filter="false"/></label></th>
					<td colspan="2">
						<input type="text" class="text date small" id="expiry" name="expiryDate" size="20" value="<bean:write name="userForm" property="expiryDate" filter="false"/>"/>				
						<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>	
					
					</td>													
				</tr>
			</table>
				
			<table class="form" cellspacing="0" style="margin:0;"  id="messageRow">		
				<tr>
					<th style="vertical-align: top"><label id="message">Optional Additional Message</label>:</th>
					<td><html:textarea name="userForm" property="message" rows="5" cols="40"></html:textarea></td>
				</tr>
				
			</table>
			
			<div class="hr"></div>
	
		
			<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
			<a href="viewApproval" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			<br />
		
	</html:form>
	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>