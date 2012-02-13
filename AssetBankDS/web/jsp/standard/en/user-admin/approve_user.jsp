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


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Approve User</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="helpsection" value="approve_users"/>
	<bean:define id="pagetitle" value="Approve User"/>

	<script src="../js/calendar.js" type="text/javascript"></script>
	<script type="text/javascript">
		<!--  
	  function setEmailSelection(emailChk)
	  {
	  	if(emailChk.checked)
	  	{
	  		document.getElementById('messageRow').style.display='inline';
	  		document.getElementById('passwordPrompt').style.display='inline';
	  	}
	  	else 
	  	{
	  		document.getElementById('messageRow').style.display='none'
	  		document.getElementById('passwordPrompt').style.display='none';
	  	}
	  }

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
		//-->
	</script>
	
</head>
<body id="adminPage" onLoad="setEmailSelection(document.getElementById('email'));">
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
				<tr>
					<th><label for="email">Email user?:</label></th>
					<td>
						<html:checkbox style="width: auto;" styleId="email" styleClass="checkbox" name="userForm" property="notifyUser" onclick="setEmailSelection(this);"/>
					</td>					
				</tr>
			</table>
			
			<table class="form" cellspacing="0" style="margin:0;">	
				<tr>
					<th><label for="expiry"><bright:cmsWrite identifier="label-user-expiry-date" filter="false"/></label></th>
					<td colspan="2">
						<input type="text" class="text" id="expiry" name="expiryDate" size="20" value="<bean:write name="userForm" property="expiryDate" filter="false"/>"/>				
			
						<script type="text/javascript">
							document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePickerSupportUS(document.getElementsByName(\'expiryDate\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
						</script>
					</td>													
				</tr>
			</table>
			
			<c:if test="${!userForm.user.remoteUser}">
				<div id="approvePassword">
					<table class="form" cellspacing="0" style="margin:0;">	
						<tr>
							<th>Password:</th>
							<td>
								<html:text styleClass="text" name="userForm" property="user.password" maxlength="100" size="25"/> <span id="passwordPrompt">(leave empty if you want a password to be generated automatically).</span>
							</td>	
						</tr>
					</table>	
				</div>					
			</c:if>
		
			<table class="form" cellspacing="0" style="margin:0;"  id="messageRow">		
				<tr>
					<th style="vertical-align: top"><label id="message">Optional Additional Message</label>:</th>
					<td><html:textarea name="userForm" property="message" rows="5" cols="40"></html:textarea></td>
				</tr>
				
			</table>	
			<script type="text/javascript">
				document.getElementById('messageRow').style.display="none";
				document.getElementById('passwordPrompt').style.display="none";
			</script>
			
			<div class="hr"></div>
	
		
			<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
			<a href="viewApproval" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			<br />
		
	</html:form>
	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>