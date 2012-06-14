<c:if test="${bValidateMandatories && !editingFilter}">
	<c:choose>
		<c:when test="${ (attribute.mandatory && !bIsImport) || (attribute.mandatoryBulkUpload && bIsImport)}">
			 <span class="required">*</span>
		</c:when>
		<c:when test="${ attribute.requiredForCompleteness }">
			 <span class="required">&dagger;</span>
		</c:when>
	</c:choose>
</c:if>