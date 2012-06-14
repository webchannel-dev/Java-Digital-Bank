<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<meta property="og:title" content="${fbPageTitle}" />
<meta property="og:type" content="website" />
<meta property="og:url" content="${fbCompleteUrl}" />
<meta property="og:image" content="${fbPreviewImageUrl}" />
<meta property="og:site_name" content="<bright:cmsWrite identifier="company-name" filter="false" />" />
<c:choose>
	<c:when test="${not empty facebookLikeButton.fbAdminIds}">
		<meta property="fb:admins" content="${facebookLikeButton.fbAdminIds}" />
	</c:when>
	<c:otherwise>
		<meta property="fb:app_id" content="${facebookLikeButton.fbApplicationId}" />
	</c:otherwise>
</c:choose>

