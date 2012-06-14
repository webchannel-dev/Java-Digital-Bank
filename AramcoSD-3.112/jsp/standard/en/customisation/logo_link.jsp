<bright:applicationSetting id="useBrands" settingName="multiple-brands"/>
<bright:applicationSetting id="logoDefaultHeight" settingName="logo-image-height"/>
<bright:applicationSetting id="logoDefaultWidth" settingName="logo-image-width"/>

<c:choose>
	<c:when test="${useBrands && userprofile.brand.id gt 0}">
		
		<c:set var="logoPath" value="../images/standard/${userprofile.brand.logoFile}" />
		<c:set var="logoAlt" value="${userprofile.brand.logoAlt}" />
		<c:set var="logoHeight" value="${userprofile.brand.logoHeight}" />
		<c:set var="logoWidth" value="${userprofile.brand.logoWidth}" />
		
		<a href="../action/viewHome">
			<img src="<c:out value='${logoPath}' />" alt="&lt;c:out value='${logoAlt}' /&gt;" width="<c:out value='${logoWidth}' />px" height="<c:out value='${logoHeight}' />px" class="logo" />		</a>	</c:when>
	<c:otherwise><a href="../action/viewHome"><img src="../images/standard/Saudi_Aramco_logo.png" alt="Saudi Aramco" width="160" height="60" class="logo" /></a></c:otherwise>
</c:choose>
	 