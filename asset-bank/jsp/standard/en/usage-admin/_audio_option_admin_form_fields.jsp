
<p>
	Enter the label and bitrate (in bps) for this download option.
</p>
<p><span class="required">*</span> denotes a mandatory field.</p>
<table class="form" cellpadding="0" cellspacing="0">
	<tr>
		<th style="width:100px;">
			<label for="label">Label: <span class="required">*</span></label>
		</th>
		<td>
			<html:text styleClass="text" name="audioOptionForm" property="downloadOption.label" maxlength="250" styleId="label" />
		</td>
	</tr>
	<logic:notEmpty name="audioOptionForm" property="downloadOption.translations">
		<logic:iterate name="audioOptionForm" property="downloadOption.translations" id="translation" indexId="index">
			<logic:greaterThan name="translation" property="language.id" value="0">
				<tr>
					<th class="translation">
						<label for="downloadOption<bean:write name='index'/>">(<bean:write name="translation" property="language.name"/>):</label>
					</th>
					<td>
						<html:hidden name="audioOptionForm" property="downloadOption.translations[${index}].language.id"/>
						<html:hidden name="audioOptionForm" property="downloadOption.translations[${index}].language.name"/>
						<input type="text" class="text" name="downloadOption.translations[<bean:write name='index'/>].label" maxlength="250" id="downloadOption<bean:write name='index'/>" value="<bean:write name="translation" property="label" />" />
					</td>
				</tr>
			</logic:greaterThan>
		</logic:iterate>	
	</logic:notEmpty>
	
	<tr>
		<th>
			<label for="bitrate">Bitrate (bps): <span class="required">*</span></label>
		</th>
		<td>
			<html:text styleClass="text" name="audioOptionForm" property="downloadOption.bitrate.formNumber" styleId="bitrate" />
			<em>eg enter 128000 for 128 kbps</em>
		</td>
	</tr>

</table>