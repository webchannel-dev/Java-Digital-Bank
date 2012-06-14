<c:if test="${not empty featuredCopyItem.body}">
	<!-- No Featured Image -->
	<div class="noFeatured" style="padding-top:11px">
		<!-- Text to show when there is no featured image (change in the 'Content' area of Admin) -->
		<bright:cmsWrite identifier="homepage-no-featured-image" filter="false"/>
	</div>
</c:if>