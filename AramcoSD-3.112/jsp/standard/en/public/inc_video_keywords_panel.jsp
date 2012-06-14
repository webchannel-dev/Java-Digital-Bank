<div id="keywordsWrapper">
	<c:if test="${isEdit}">    
		<strong><bright:cmsWrite identifier="label-add-new-keywords" filter="false"/>:</strong>    
		<div id="keywordsForm" title="Add new keywords">
			<bright:refDataList id="languages" transactionManagerName="DBTransactionManager" componentName="LanguageManager" methodName="getLanguages"/>	
			<div>
				<input type="hidden" id="id" name="id" value="<c:out value='${assetForm.asset.id}'/>"/>
				<c:forEach items ="${languages}" var="language">
					<label for="keywords"><bright:cmsWrite identifier="keyword-root" filter="false"/> (<c:out value="${language.nativeName})"/></label><br/>
					<input type="text" name="keywords" value="" id="keywords<c:out value="${language.id}"/>" class="text wide keywordsField" /><br />
				</c:forEach>
			</div>
			<div>
				<label for="start_point"><bright:cmsWrite identifier="label-start-point" filter="false" /></label><br /> 
				<input type="text" name="start_point" value="" id="start_point" class="text" /><a href="#" class="get start" title="Insert current point from video player"><bright:cmsWrite identifier="button-get-current" filter="false" /></a>
			</div>
			<div>
				<label for="end_point"><bright:cmsWrite identifier="label-end-point" filter="false" /></label><br /> 
				<input type="text" name="end_point" value="" id="end_point" class="text" /><a href="#" class="button get end" title="Insert current point from video player"><bright:cmsWrite identifier="button-get-current" filter="false" /></a>
			</div>
			
			<div style="margin-right:0">    
				<label>&nbsp;</label><br />
				<input type="submit" name="addKeywords" value="<bright:cmsWrite identifier="button-add" filter="false"/>" class="button flush" id="addKeywordForm" />
				<input type="submit" name="saveKeywords" value="<bright:cmsWrite identifier="button-save" filter="false"/>" class="button flush" id="saveKeywordForm" />  
			</div>
		</div>
	</c:if>               	
 
    <div id="keywordsList">
 	   	<c:set var="videoKeywords" scope="request" value="${assetForm.asset.videoKeywords}"/>  
		<%@include file="inc_video_keywords.jsp"%>     
    </div>

</div>