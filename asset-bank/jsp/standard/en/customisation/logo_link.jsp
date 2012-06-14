<bright:applicationSetting id="useBrands" settingName="multiple-brands"/>
<bright:applicationSetting id="logoDefaultHeight" settingName="logo-image-height"/>
<bright:applicationSetting id="logoDefaultWidth" settingName="logo-image-width"/>
<bright:applicationSetting id="categoryImageUsedForLogo" settingName="category-image-used-for-logo"/>

<c:choose>
	<c:when test="${useBrands && userprofile.brand.id gt 0}">
		
		<c:set var="logoPath" value="../images/standard/${userprofile.brand.logoFile}" />
		<c:set var="logoAlt" value="${userprofile.brand.logoAlt}" />
		<c:set var="logoHeight" value="${userprofile.brand.logoHeight}" />
		<c:set var="logoWidth" value="${userprofile.brand.logoWidth}" />
		
		<a href="../action/viewHome">
			<img src="<c:out value='${logoPath}' />" alt="<c:out value='${logoAlt}' />" width="<c:out value='${logoWidth}' />" height="<c:out value='${logoHeight}' />" class="logo" />
		</a> 

	</c:when>
	<c:otherwise>
		
		<c:choose>
			<c:when test="${userprofile.categoryIdForLogo > 0}">
				<c:set var="logoPath" value="../images/standard/categories/${userprofile.categoryIdForLogo}logo.jpg" />
			</c:when>
			<c:when test="${userprofile.accessLevelIdForLogo > 0}">
				<c:set var="logoPath" value="../images/standard/categories/${userprofile.accessLevelIdForLogo}logo.jpg" />
			</c:when>
			<c:otherwise>
				<c:set var="logoPath" value="../images/standard/ab_logo.png" />
			</c:otherwise>
		</c:choose>
		
		<a href="../action/viewHome"><img src="<c:out value='${logoPath}' />" alt="Asset Bank" width="<c:out value='${logoDefaultWidth}' />" height="<c:out value='${logoDefaultHeight}' />" class="logo" /></a> 
		
	</c:otherwise>
</c:choose>
	  