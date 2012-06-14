
	<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>

	<c:if test="${usernameIsEmailaddress}"><bright:cmsWrite identifier="intro-forgot-pwd-isemail" filter="false" /></c:if>
	<c:if test="${!usernameIsEmailaddress}"><bright:cmsWrite identifier="intro-forgot-pwd" filter="false" /></c:if>

