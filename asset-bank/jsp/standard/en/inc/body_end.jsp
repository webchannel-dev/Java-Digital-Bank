<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

			</div>   <!-- End of mainCol -->
		</div><!-- End of content -->
   
    <c:set var="section" scope="request" value ="${section}"/>   
    <jsp:include page="/jsp/standard/en/inc/menu.jsp"/>
    
      <div class="clearing"><!--  --></div>
   	<jsp:include flush="true" page="/jsp/standard/en/inc/foot.jsp"/>
	   
   </div><!-- End of pageWrapper -->
	<bright:applicationSetting id="disableRightClick" settingName="disable-right-click"/>
	<c:if test="${disableRightClick == true}">
		<script type="text/javascript">
			var clickmessage="Please do not attempt to download this image by using the right-click menu."
			
			document.oncontextmenu = function (event) {
				event = event || window.event;
				var target = event.target || event.srcElement;
	
				if (target.tagName=="IMG")
				{
					if (event.preventDefault) {	
					event.preventDefault();
					}	
					alert(clickmessage);
					return false;
				}								
				return true;
			}
		</script>
	</c:if>	