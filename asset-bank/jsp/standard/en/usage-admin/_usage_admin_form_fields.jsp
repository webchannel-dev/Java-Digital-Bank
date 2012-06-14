
<p>
	Select the Asset type(s) and Download tab(s) for which this usage type will appear to downloaders.
	Image Download tab only applies when the Asset type is Image or All (since only Image downloads have an advanced tab).
</p>
<p><span class="required">*</span> denotes a mandatory field.</p>
<table class="form" cellpadding="0" cellspacing="0">
	<tr>
		<th style="width:100px;">
			<label for="usageType">Usage type name: <span class="required">*</span></label>
		</th>
		<td>
			<html:text styleClass="text" name="usageForm" property="usageType.description" maxlength="45" styleId="usageType" />
		</td>
	</tr>
	<logic:notEmpty name="usageForm" property="usageType.translations">
		<logic:iterate name="usageForm" property="usageType.translations" id="translation" indexId="index">
			<logic:greaterThan name="translation" property="language.id" value="0">
				<tr>
					<th class="translation">
						<label for="usageType<bean:write name='index'/>">(<bean:write name="translation" property="language.name"/>):</label>
					</th>
					<td>
						<html:hidden name="usageForm" property="usageType.translations[${index}].language.id"/>
						<html:hidden name="usageForm" property="usageType.translations[${index}].language.name"/>
						<input type="text" class="text" name="usageType.translations[<bean:write name='index'/>].description" maxlength="45" id="usageType<bean:write name='index'/>" value="<bean:write name="translation" property="description" filter="false"/>" />
					</td>
				</tr>
			</logic:greaterThan>
		</logic:iterate>	
	</logic:notEmpty>
	
	<c:if test="${usageForm.usageType.parentId<=0}">
	<tr>
		<bright:refDataList componentName="AssetManager" methodName="getAssetTypes" id="assetTypes"/>
		<th>
			<label for="assetType">Media type:</label>
		</th>
		<td>
				<html:select name="usageForm" property="usageType.assetTypeId" styleId="assetType" onchange="mediaTypeChanged(this)">
					<html:option value="0">All</html:option>
					<html:options collection="assetTypes" property="id" labelProperty="description"/>
				</html:select>		
		</td>
	</tr>
	<tr>
		<th>
			<label for="downloadType">Image Download Tab:</label>
		</th>
		<td >
				<html:select name="usageForm" property="usageType.downloadTabId" styleId="downloadType">
					<html:option value="3">Both</html:option>
					<html:option value="1">Simple only</html:option>
					<html:option value="2">Advanced only</html:option>
				</html:select>		
		</td>
	</tr>
	</c:if>
	<tr>
		<th>
			<label for="message">Download Message:</label>
		</th>
		<td>
			<input type="hidden" name="maxlength_formUsageType.message" value="2000">
			<input type="hidden" name="maxlengthmessage_formUsageType.message" value="The Download Message field has exceeded its 2000 character maximum length.">
			<html:textarea name="usageForm" property="usageType.message" styleClass="text" styleId="message" />
		</td>
	</tr>
	<logic:notEmpty name="usageForm" property="usageType.translations">
		<logic:iterate name="usageForm" property="usageType.translations" id="translation" indexId="index">
			<logic:greaterThan name="translation" property="language.id" value="0">
				<tr>
					<th class="translation">
						<label for="message<bean:write name='index'/>">(<bean:write name="translation" property="language.name"/>):</label>
					</th>
					<td>
						<input type="hidden" name="maxlength_formUsageType<bean:write name='index'/>.message" value="2000">
						<input type="hidden" name="maxlengthmessage_formUsageType<bean:write name='index'/>.message" value="The Download Message (<bean:write name="translation" property="language.name"/>) field has exceeded its 2000 character maximum length.">
						<html:hidden name="usageForm" property="usageType.translations[${index}].language.id"/>
						<textarea class="text" name="usageType.translations[<bean:write name='index'/>].message" id="message<bean:write name='index'/>"><bean:write name="translation" property="message" filter="false"/></textarea>
					</td>
				</tr>
			</logic:greaterThan>
		</logic:iterate>
	</logic:notEmpty>
	<tr>
		<th>
			<label for="csnDetails">Can specify details:</label>
		</th>
		<td>
			<html:checkbox name="usageForm" property="usageType.canEnterDetails" styleClass="checkbox" styleId="csnDetails" />
		</td>
	</tr>
	<tr>
		<th>
			<label for="mandDetails">Details mandatory:</label>
		</th>
		<td>
			<html:checkbox name="usageForm" property="usageType.detailsMandatory" styleClass="checkbox" styleId="mandDetails" />
		</td>
	</tr>
	<tr>
		<th>
			<label for="downloadorig">Download Original:</label>
		</th>
		<td>
			<html:checkbox name="usageForm" property="usageType.downloadOriginal" styleClass="checkbox" styleId="downloadorig" />
		</td>
	</tr>
	<tr>
		<th>
			<label for="highRes">Is considered high resolution:</label>
		</th>
		<td>
			<html:checkbox name="usageForm" property="usageType.highResolution" styleClass="checkbox" styleId="highRes" />
		</td>
	</tr>
	<c:if test="${pptTemplateCategoryId!=''}">
		<tr>
			<th>
				<label for="canDlAsPPT">Can download as PPT/PPTX:</label>
			</th>
			<td>
				<html:checkbox name="usageForm" property="usageType.canDownloadAsPPT" styleClass="checkbox" styleId="canDlAsPPT" />
			</td>
		</tr>
	</c:if>
</table>