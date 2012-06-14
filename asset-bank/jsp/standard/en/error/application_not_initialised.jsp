<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1			Chris Preager 	16-May-2005		Created
	d2 		Ben Browning	21-Feb-2006		HTML/CSS Tidy Up
	d3			Steve Bryan		30-May-2011		Simplified to work without managers or DB.	
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="retryMins" settingName="startup-retry-after-mins" />

<%--  Steve 30-May-2011: NOTE: This page should work when manager initialisation and DB connection has failed 
	- so please do NOT add references to CMS items or other manager features! Could just add some CSS styling... --%>

<head>	
	<title>Asset Bank</title>

	<c:if test="${applicationErrorForm.notFinished}">
		<meta http-equiv="refresh" content="5">
	</c:if>                           
	<style type="text/css" media="screen"> 
		body { font:75%/1.5 tahoma, arial,sans-serif; 
			background: #eee;
		} 
		div.wrapper {
			width: 65%;
			margin: 50px auto;    
			border: 1px solid #ccc;  
			padding: 20px;   
			background: #fff;          
			-moz-border-radius: 12px; 
			-webkit-border-radius: 12px; 
			border-radius: 12px; 
			-moz-box-shadow: 4px 4px 10px #aaa; 
			-webkit-box-shadow: 4px 4px 10px #aaa; 
			box-shadow: 4px 4px 10px #aaa;                      
		}   
		h1 {
			margin: 0.5em 0 0.5em 0; 
		} 
		h2 {
			font-size: 1.2em;
		}   
		div.errorBox {  
			padding: 1em;
			border: 1px dashed #aaa;  
			font-family: courier, monospace;  
			background: #FCE4E4; 
			word-wrap: break-word;
		}     
		p.loading {
			background: url('../images/standard/misc/ajax_loader_small.gif') left center no-repeat;
			line-height: 20px;
			padding-left: 21px;
		}

	</style>
	
</head>

<body id="errorPage">
    
	<div class="wrapper">  
		
		<c:set var="showError" value="false" />
		
		<c:choose>
		
			<%--  Still starting up --%>
			<c:when test="${applicationErrorForm.notFinished}">
				<h1 class="underline">Application Initialising</h1>

				<h2>The application is still starting up, please try again in
				<c:choose>
					<c:when test="${!empty retryMins}"><c:out value="${retryMins}" /></c:when>
					<c:otherwise>a few</c:otherwise>
				</c:choose>
				minutes.</h2> 
				<c:if test="${!empty applicationErrorForm.startingManager}">
					<p class="loading">Currently initialising: <em><c:out value="${applicationErrorForm.startingManager}" /></em></p>
				</c:if>
			</c:when>
			
			<%--  Critical error - eg license validation failure or no settings file --%>
			<c:when test="${applicationErrorForm.existsCriticalError}">
				<c:set var="showError" value="true" />
				<h1>Critical Initialisation Error</h1>
				<p>There was a critical problem starting your Asset Bank, that prevents the application from working.</p>
				<p>Please check the message below for details and contact support if this cannot be resolved.</p>
			</c:when>
			
			<%--  Check whether to show the setup wizard --%>
			<c:when test="${applicationErrorForm.offerSetupWizard}">
				<h1>Setup Wizard</h1>
				<p>If this is the first time you have started Asset Bank, then you can use the Setup Wizard to review your settings.</p>
				<p>If you do not wish to run the Setup Wizard now, then you may <a href="turnoffSetupWizard">disable Setup Wizard and start Asset Bank normally</a>.
				<p><a href="viewSetupWizard?start=true">Setup Wizard &raquo;</a></p>
			</c:when>
						
			<%--  Other (non-critical) error --%>
			<c:when test="${applicationErrorForm.existsError}">
				<h1>Application not Initialised</h1>

				<%--  Check for DB error explicitly, else just display it --%>
				<c:choose>
					<c:when test="${applicationErrorForm.isDatabaseError}">
						<h2>There was a problem connecting to the database</h2>
						 <p>a) Make sure your database is running and available from the Asset Bank server.</p>
						 <p>b) Use the Setup Wizard to check and update your database connection settings.</p>
					</c:when>
				
					<c:otherwise>						
						<c:set var="showError" value="true" />
						<h2>There has been an initialisation error.</h2>		
						<p>Please check the message below for details and contact support if this cannot be resolved.</p>				
					</c:otherwise>					
				</c:choose>
				
				<p><a href="viewSetupWizard?start=true">Setup Wizard &raquo;</a></p> 
							
			</c:when>
			
			<c:otherwise>
				<%--  Should not get here, but it is possible to view this page directly via the action --%>	
				<p><a href="viewHome">Start Asset Bank</a></p>	
			</c:otherwise>
			
		</c:choose>	
		
		
		<%--  Display the error if appropriate --%>
		<c:if test="${showError}">
			<p><strong>Error details:</strong></p>
			
			<c:if test="${!empty applicationErrorForm.errorMessage}">
				<div class="errorBox"><c:out value="${applicationErrorForm.errorMessage}" escapeXml="false" /></div>
			</c:if>
			
			<br/>
		
			<c:if test="${!empty applicationErrorForm.errorDump}">
				<div class="errorBox">
					<c:out value="${applicationErrorForm.errorDump}" />
				</div>
			</c:if>
		</c:if>
		
     </div>             
	
</body>
</html>