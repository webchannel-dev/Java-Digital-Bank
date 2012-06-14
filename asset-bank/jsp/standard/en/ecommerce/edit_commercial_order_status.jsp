<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		08-Jan-2007		Created
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bean:define id="pagetitle" value="Commercial Option Details"/>

<head>
	
	<bean:define id="section" value="orders"/>
	<title>Commercial Order Details</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" language="JavaScript">
		function confirmUpdateStatus()
		{
			var msg="";
			var status = document.forms["statusForm"].elements["statusSelect"].value;
			switch(status)
			{
				case "4":
					msg = "<bright:cmsWrite identifier="e-js-change-status-online-payment" filter="false"/>"
					break;
				case "5":
					msg = "<bright:cmsWrite identifier="e-js-change-status-requires-offline-payment" filter="false"/>"
					break;		
				case "7":
					msg = "<bright:cmsWrite identifier="e-js-change-status-paid-offline" filter="false"/>"
					break;
				case "8":
					msg = "<bright:cmsWrite identifier="e-js-change-status-declined" filter="false"/>"
					break;
				default:
					msg = "<bright:cmsWrite identifier="e-js-change-status" filter="false"/>"
					break;
			}
			return confirm(msg);
		}
	</script>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bean:write name="pagetitle" /></h1>
	<html:form action="updateCommercialOrderStatus" method="post" styleId="statusForm" onsubmit="return confirmUpdateStatus();">
		<input type ="hidden" name="orderId" value="<bean:write name="orderForm" property="order.id"/>"></input>
		<p>
			<table class="report" cellspacing="0" border="0">
				<%@include file="../ecommerce/inc_common_order_details.jsp"%>
				<tr>				
						<th><bright:cmsWrite identifier="e-status" filter="false"/>:</th>
						<td>
							<html:select name="orderForm" property="order.status.id" styleId="statusSelect">						
								<html:optionsCollection property="orderStatusList" label="name" value="id"/>	
							</html:select>
						</td>
				</tr>
			</table>
			<%@include file="../ecommerce/inc_vat_receipt_address.jsp"%>
		</p>

		

		<div class="hr"></div>	
	
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="e-button-change-status" filter="false"/>" > 
		</div>		
	</html:form>
				
	
				
	<form name="cancelForm" action="viewCommercialOrderDetails" method="get">
		<input type ="hidden" name="orderId" value="<bean:write name="orderForm" property="order.id"/>"></input>
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>