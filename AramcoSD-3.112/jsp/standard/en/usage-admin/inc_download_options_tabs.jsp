
	<div class="adminTabs">
		<c:choose>
			<c:when test="${tabId == 'usage'}">
				<h2 class="current">Usage Types</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewUsageTypes">Usage Types</a></h2>
			</c:otherwise>		
		</c:choose>
	
		<c:choose>
			<c:when test="${tabId == 'masks'}">
				<h2 class="current">Mask Images</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewMasks">Mask Images</a></h2>
			</c:otherwise>		
		</c:choose>

		<div class="tabClearing">&nbsp;</div>
	</div>


	