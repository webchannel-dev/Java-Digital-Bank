<%-- Request on CD custom fields
Use this jsp to add fields to the request on CD page.
Enter the name of the field into the email template for it to be added to the email
e.g. add #deliveryDate# to the email template and uncomment the code below to enable a delivery date picker

	
<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<tr>
	<th><label for="deliveryDate">Delivery date:</label></th>
	<td>
		<input type="text" class="small date" name="deliveryDate" id="deliveryDate" size="20" maxlength="100" />

	</td>
</tr>

--%>