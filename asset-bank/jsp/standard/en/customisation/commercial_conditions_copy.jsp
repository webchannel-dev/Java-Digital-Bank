	<!-- The conditions text: this comes from the database (change in the 'Content' area of Admin) -->
	<bright:refDataList id="conditions" componentName="ListManager" methodName="getListItem" argumentValue="commercial-conditions"/>	
	<bean:write name="conditions" property="body" filter="false"/>
