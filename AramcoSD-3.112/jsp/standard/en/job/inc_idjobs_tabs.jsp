<p class="tabHolderPopup clearfix">
	<a <c:if test='${jobgroup == "ACTIVE"}'>class="active"</c:if> href="viewJobQueue?jobgroup=ACTIVE">Active</a>
	<a <c:if test='${jobgroup == "FAILED"}'>class="active"</c:if> href="viewJobQueue?jobgroup=FAILED">Failed</a>
	<a <c:if test='${jobgroup == "COMPLETED"}'>class="active"</c:if> href="viewJobQueue?jobgroup=COMPLETED">Completed</a>					
</p>