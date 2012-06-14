<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="buttonWidth"	value="320" />


<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) {return;}
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div class="fb-like" 	data-href="${fbCompleteUrl}" 
						data-send="${facebookLikeButton.showSendButton}" 
						data-show-faces="${facebookLikeButton.showFaces}"
						data-action="${facebookLikeButton.verbToDisplay}" 
						data-layout="${facebookLikeButton.buttonLayout.name}"
						data-width="${buttonWidth}"
						data-colorscheme="light" >
</div>


<%--  I commented out the iframe version because is hard to manage. The height property should be tweaked every time the button layout change!
<c:choose>
	<c:when test="${facebookLikeButton.showSendButton}">
	</c:when>
	<c:otherwise>
		<iframe src="//www.facebook.com/plugins/like.php?href=${fbCompleteUrl}&amp;send=false&amp;layout=${facebookLikeButton.buttonLayout.name}&amp;width=${buttonWidth}&amp;show_faces=${facebookLikeButton.showFaces}&amp;action=${facebookLikeButton.verbToDisplay}&amp;colorscheme=light&amp;font&amp;height=80" 
				style="border:none; overflow:hidden; width:${buttonWidth}px; height:30px;" scrolling="no" frameborder="0" allowTransparency="true">
		</iframe>
	</c:otherwise>
</c:choose>
 --%>