<bright:applicationSetting id="purchaseidStem" settingName="ecommerce-purchaseid-stem"/>


<html:hidden name="orderForm" property="orderWorkflow" />
<table class="form" cellspacing="0" cellpadding="0" >
	<tr>
		<th style="padding-top: 8px;">
			<label for="orderid_field">Order Id:</label>
		</th>
		<td style="padding-top: 8px;">
			<c:out value="${purchaseidStem}" /> <html:text styleClass="small text" name="orderForm" property="purchaseId" size="20"/>
		</td>
	</tr>
	<tr>
		<th style="padding-top: 8px;">
			<label for="placed_between_field">Placed between:</label>
		</th>
		<td style="padding-top: 8px;">
			<html:text styleClass="small text" name="orderForm" property="startDateString" size="20"/> 
			<script type="text/javascript">
				document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePicker(document.getElementsByName(\'startDateString\')[0])" width="16" height="15" style="padding-right: 0;" ></img></a>');
			</script>
			and
			<html:text styleClass="small text" name="orderForm" property="endDateString" size="20"/> 
			<script type="text/javascript">
				document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePicker(document.getElementsByName(\'endDateString\')[0])" width="16" height="15" style="padding-right: 0;" ></img></a>');
			</script>
		</td>
	</tr>
	<tr>
		<th style="padding-top: 8px;">
			<label for="orderstatus_field">Order status:</label>
		</th>
		<td style="padding-top: 8px;">
			<html:select name="orderForm" property="orderStatus">
				<html:option value="0">[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</html:option>
				<html:optionsCollection property="orderStatusList" label="name" value="id"/>					
			</html:select>
		</td>
	</tr>
	<logic:notEmpty name="orderForm" property="orderPriceBandTypeSearchList">
		<tr>
			<th style="padding-top: 8px;">
				<label for="purchase_type_field">Purchase type:</label>
			</th>
			<td>							
				<html:select name="orderForm" property="withPriceBandType">						
					<html:option value="0">[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</html:option>		
					<html:optionsCollection property="orderPriceBandTypeSearchList" label="name" value="id"/>																			
				</html:select>					
			</td>				
		</tr>
	</logic:notEmpty>
</table>	
<input type="submit" name="submitOrderSearch" class="button inline" value="<bright:cmsWrite identifier="button-search" filter="false" />" />