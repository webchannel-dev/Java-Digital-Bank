<%-- Fields for named colour add/edit form --%>
<input type="hidden" name="mandatory_name" value="Please enter a name for the colour"/>
<input type="hidden" name="mandatory_hexString" value="Please choose a colour"/>

<label for="name">Name:</label>
<html:text styleId="name" property="name" size="30" maxlength="255" />
<br />

<label for="hexString">Colour:<br /><span style="font-weight: normal">(Hex value)</span></label>
<html:text styleId="hexString" property="hexString" size="7" maxlength="7" styleClass="small" />
<a href="#" class="pickerLink js-enabled-show">Colour picker...</a><br />
<div id="pickerHolder">
	<label>&nbsp;</label>
	<div id="colorPicker"></div>
	<a href="#" class="closePicker">Close</a>
	<br />
</div>
