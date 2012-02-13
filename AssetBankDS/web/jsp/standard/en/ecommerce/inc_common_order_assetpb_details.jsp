<logic:equal name="pbIndx" value="0">
	<td><a href="viewAsset?id=<bean:write name='asset' property='assetId'/>"><bean:write name="asset" property="assetId"/></a></td>
	<td><bean:write name="asset" property="description"/></td> 
</logic:equal>
<logic:notEqual name="pbIndx" value="0">
	<td>&nbsp;</td><td>&nbsp;</td>
</logic:notEqual> 
<td>
	<bean:write name="priceBand" property="priceBand.name"/>
</td>
<td>
	<bean:write name="priceBand" property="priceBandType.name"/>
</td>