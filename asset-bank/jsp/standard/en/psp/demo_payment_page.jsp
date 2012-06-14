<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1	16-Nov-2005		Steve Bryan 			Created dummy payment page
	d2		Ben Browning	24-Mar-2006		Tidied up HTML/CSS
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bean:parameter id="cart_id" name="cart_id" value="0" />
<bean:parameter id="amount" name="amount" value="0" />
<bean:parameter id="callback" name="callback" value="" />
<bean:parameter id="merchant" name="merchant" value="" />
<bean:parameter id="desc" name="desc" value="" />
<bean:parameter id="MC_signature" name="MC_signature" value="" />
			


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Payment</title> 
	<%@include file="../inc/head-elements.jsp"%>
   
   <style type="text/css">
    body { text-align: center; }
   div.container { 
      width: 560px; 
      border: 1px solid gray; 
      text-align: left; 
      padding: 20px; 
      margin: 20px auto;
      }
	th {  width: 200px; }

   </style>
</head>
<body id="paymentPage">
<div class="container">

		<img src="../images/standard/asset_bank_logo.gif" alt="Asset Bank" width="140" height="57" border="0" />
      &nbsp;&nbsp;&nbsp;&nbsp;
		<img src="../images/standard/credit-card-logos.gif" width="200" height="30" alt="credit-card-logos" style="margin-bottom:3px" />

		   <br /><br /><br />
         <h1>Payment</h1> 
		   <p><strong>This is a dummy payment page. Just press "submit details" to continue.</strong></p>
			
			<p>
				Merchant: <c:out value="${merchant}" />	
			</p>

			<p>
				Description: <c:out value="${desc}" />	
			</p>
			
			<p>
			Please enter your card details.
			</p>

			<p class="note">
			Please complete all fields marked with an asterisk (<span class="required">*</span>).
			</p>
         
	      
			<div class="hr"></div>
			
			<form action="<bean:write name='callback' />" method="post">
				<input type="hidden" name="cart_id" value="<bean:write name='cart_id' />" />
				<input type="hidden" name="amount" value="<bean:write name='amount' />" />
				<input type="hidden" name="valid" value="true" />
				<input type="hidden" name="MC_signature" value="<bean:write name='MC_signature' />" />
				<input type="hidden" name="transId" value="001" />
				
				<table cellspacing="5" cellpadding="0">
					<tr>
						<th>
							Name of cardholder:<span class="required">*</span>
						</th>
						<td>
							<input type="text" name="customer" value="" size=20>
						</td>
					</tr>
					
					 <tr>
						<th>
							Card type: 
						</th>
						<td>
							<select name="card_type">
								<option value="Master Card" >Master Card</option>
								<option value="Delta" >Visa Debit, Delta or Connect</option>
								<option value="Visa" >Visa</option>				
								<option value="Switch" >Switch</option>
								<option value="Solo" >Solo</option>
					
							</select>
						</td>
					</tr>

					<tr>
						<th>Card Number:<span class="required">*</span></th>
						<td><input type="text" name="card_no" size=19></td>
					</tr>
					<tr>
						<th>Expiry Date (MM/YY):<span class="required">*</span></th>
						<td><input type="text" name="expiry" value="" maxlength=5 size=5></td>
					</tr>

					<tr>
						<th>Issue No. (Switch use 0 if not known)</th>
						<td><input type="text" name="issue" value="" maxlength=2 size=2></td>
					</tr>

					<tr>
						<th>Last 3 digits on signature strip (security code)</th>
						<td><input type="text" name="cv2" value="" maxlength=4 size=4></td>
					</tr>
					<tr>
						<th>Start Date (MM/YY)</th>
						<td><input type="text" name="start_date" value="" maxlength=5 size=5></td>
					</tr>
					<tr>
						<th>Amount</th>
						<td><strong><bright:writeMoney name="displayamount" /></strong></td>
					</tr>					
					

				</table>
				
				<div style="text-align: right; margin: 1em 0;">        
               
					<input class="button" type="submit" value="Submit Details">
				</div>
				
			</form>
			
			<div class="clearing">&nbsp;</div>
			
			<p>
				Please do not press Submit Details more than once. 
			</p>
			<p>
				Please wait while your card details are authorised.
				This transaction is encrypted for your privacy.
				Do not exit or press back until confirmation received. 				
			</p>

   </div>

</body>
</html>