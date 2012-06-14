<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1			Steve Bryan		30-May-2011		created to work without managers or DB.	
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%--  NOTE: This page should work when manager initialisation and DB connection has failed 
	- so please do NOT add references to CMS items or other manager features! Could just add some CSS styling... --%>

<head>	
	<title>Asset Bank</title>

	<%@include file="inc_styles.jsp" %>

	<script type="text/javascript">
	<!--
	
	<%--
	 * Hide settings depending on enabled/import on fly
	 --%>
	function checkEnabled(ctrl)
	{
		var bChecked = ctrl.checked;
		setEnabled(bChecked);
	}
	
	function setEnabled(bChecked)
	{
		if (bChecked)
		{
			changeStyleDisplay('enabledDiv', "block");
		}
		else
		{
			changeStyleDisplay('enabledDiv', "none");
		}
	}
	
	function checkOnFly(ctrl)
	{
		var bChecked = ctrl.checked;
		setOnFly(bChecked);
	}
	
	function setOnFly(bChecked)
	{
		if (bChecked)
		{
			changeStyleDisplay('synchDiv', "none");
		}
		else
		{
			changeStyleDisplay('synchDiv', "block");
		}
	}
	
	function changeStyleDisplay(elementName, state)
	{
		if(document.getElementById(elementName))
		{
			document.getElementById(elementName).style.display = state;
		}
	}
	
	-->
	</script>
	
</head>

<body id="errorPage" onload="setEnabled(<c:out value='${setupWizardForm.ldapConfig.enabled}'/>); setOnFly(<c:out value='${setupWizardForm.ldapConfig.importUsersOnTheFly}'/>);">
    
	<div class="wrapper">  

		<bean:define id="tabId" value="ldap"/>
		<%@include file="inc_breadcrumb.jsp" %>
		
		<h1>Edit LDAP Settings</h1>
	
		<p>Changes will not take effect until you click Submit at the end of the Setup Wizard.</p>
	
		<c:set var="delimiter" value="${setupWizardForm.ldapConfig.delimiter}" />
		
		<html:form action="setupWizardStepLdap" method="post">
			<html:hidden property="ldapConfig.updated" value="true" />
			<input type="hidden" name="updateLdapConfig" value="true" />		
	
			<label>Enabled:</label>
			<html:checkbox property="ldapConfig.enabled" onclick="checkEnabled(this)" />
			<span class="hint">Check this to enable Active Directory authentication</span>
			<br />

			<br/>
			<div id="enabledDiv">

				
				<label>Import users on the fly:</label> 
				<html:checkbox property="ldapConfig.importUsersOnTheFly" onclick="checkOnFly(this)" />
				<span class="hint">Check this box to synchronise users on the fly, ie when they first log in</span>
				<br />
	
				<div id="synchDiv">
				<label>OR Synch Period:</label> 
				<html:text property="ldapConfig.synchPeriod" />
				<span class="hint">How often to synchronise, in minutes</span>
				<br />
				</div>
				<br/>
		
				
				<label>Admin Username:</label>
				<html:text property="ldapConfig.adminUsername" />
				<span class="hint">This should be the distinguished name of a user that has permission to read from the LDAP server. You may want to create an AD user for this. Eg CN=LDAPAdmin,CN=Users,DC=domain,DC=bright-interactive,DC=com</span>
				<br />
				
				<label>Admin Password:</label>
				<html:text property="ldapConfig.adminPassword" />
					
				<br />
				
				<label>Server URL:</label>
				<html:text property="ldapConfig.serverUrl" />
				<span class="hint">eg ldap://localhost:389. If you want to specify more than one (ie you have a backup server), delimit using <c:out value="${delimiter}" /></span>
				<br />
				
				<label>Base List:</label>
				<html:text property="ldapConfig.baseList" />
				<span class="hint">The node at which you want to start searching for users, eg CN=Users,DC=domain,DC=bright-interactive,DC=com. If more than one node, delimit using <c:out value="${delimiter}" /></span>
				<br />
				<br />
			</div>
		
			
			<input type="submit" class="button" value="Save" /> 
			<a href="setupWizardStepLdap">Cancel</a> 
		</html:form>
	
	</div>         
	
</body>
</html>