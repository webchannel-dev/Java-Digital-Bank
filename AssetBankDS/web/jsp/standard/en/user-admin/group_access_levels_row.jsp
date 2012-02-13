
	<tr id="catRow<bean:write name='category' property='id'/>" class="rowBorder 
		<logic:iterate name="category" property="allAncestorIds" id="ancestorId">ancestorOf<bean:write name='ancestorId'/> </logic:iterate>">
		<td class="catPermission<bean:write name='category' property='depth'/>">

			<c:choose>
				<c:when test="${groupForm.accessLevelsExpandable && category.hasRestrictiveChildCategories}">
					<span>
					<a id="expandCat<bean:write name='category' property='id'/>" style="js-enabled-show" href="javascript:;" onclick="if($j('.ancestorOf<bean:write name='category' property='id'/>').length>0) $j('.ancestorOf<bean:write name='category' property='id'/>').show(); else insertResponseAfter('catRow<bean:write name='category' property='id'/>','viewGroupSubAccessLevelPermissions?categoryId=<bean:write name='category' property='id'/>&id=<bean:write name="groupForm" property="group.id"/>&open=true'); $j('#expandCat<bean:write name='category' property='id'/>').hide(); $j('#collapseCat<bean:write name='category' property='id'/>').show();"><img src="../images/standard/icon/add.gif"  class="expander" /></a><a id="collapseCat<bean:write name='category' property='id'/>" style="display: none;" href="javascript:;" onclick="hideOnAjaxSuccess('ancestorOf<bean:write name='category' property='id'/>','viewGroupSubAccessLevelPermissions?categoryId=<bean:write name='category' property='id'/>&id=<bean:write name="groupForm" property="group.id"/>&open=false'); $j('#expandCat<bean:write name='category' property='id'/>').show(); $j('#collapseCat<bean:write name='category' property='id'/>').hide();"><img src="../images/standard/icon/subtract.gif" class="expander" /></a><bean:write name="category" property="name" filter="false"/>
				</c:when>
				<c:otherwise>
					<span class="empty"><bean:write name="category" property="name" filter="false"/>
				</c:otherwise>
			</c:choose>
			<c:if test="${not category.isRestrictive}">
				<em class="disabled">(Inherited from parent)</em>
			</c:if>
			</span>

		</td>
		<c:choose>
			<c:when test="${not category.isRestrictive}">
				<td class="radio"><input type="radio" class="radio" disabled="disabled"/></td>
				<td class="radio"><input type="radio" class="radio" disabled="disabled"/></td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup || ecommerce}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="radio"><input type="radio" class="radio" disabled="disabled"/></td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="spacer">&nbsp;</td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="radio"><c:if test="${!groupForm.group.isDefaultGroup}"><input type="radio" class="radio" disabled="disabled"/></c:if></td>
				<td class="spacer">&nbsp;</td>
				<td class="checkbox"><input type="checkbox" class="checkbox" disabled="disabled"/></td>
				<td class="checkbox"><input type="checkbox" class="checkbox" disabled="disabled"/></td>
				<td class="checkbox"><input type="checkbox" class="checkbox" disabled="disabled"/></td>
				<c:if test="${ratings || comments}">
					<td class="radio"><input type="checkbox" class="checkbox" disabled="disabled"/></td>
				</c:if>
				<c:if test="${hideRestrictedAssetImages}">
					<td class="radio"><input type="checkbox" class="checkbox" disabled="disabled"/></td>
				</c:if>
			</c:when>
			<c:otherwise>
				<td class="radio">
					<logic:match name="groupForm" property="selectedDownloadPermission[${category.id}]" value="0">
						<input type="radio" class="radio" id="catPermissionNone<bean:write name='category' property='id'/>" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:0" checked="checked" onclick="changeDownloadPermissionSet(this);" />
					</logic:match>
					<logic:notMatch name="groupForm" property="selectedDownloadPermission[${category.id}]" value="0">
						<input type="radio" class="radio" id="catPermissionNone<bean:write name='category' property='id'/>" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:0" onclick="changeDownloadPermissionSet(this);" />
					</logic:notMatch>
				</td>
				<td class="radio">
					<logic:match name="groupForm" property="selectedDownloadPermission[${category.id}]" value="1">
						<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:1" checked="checked" onclick="changeDownloadPermissionSet(this);" />
					</logic:match>
					<logic:notMatch name="groupForm" property="selectedDownloadPermission[${category.id}]" value="1">
						<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:1" onclick="changeDownloadPermissionSet(this);" />
					</logic:notMatch>
				</td>
		
				<c:choose>
					<c:when test="${!groupForm.group.isDefaultGroup || ecommerce}">
						<td class="radio">
							<logic:match name="groupForm" property="selectedDownloadPermission[${category.id}]" value="5">
								<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:5" checked="checked" onclick="changeDownloadPermissionSet(this);" />
							</logic:match>
							<logic:notMatch name="groupForm" property="selectedDownloadPermission[${category.id}]" value="5">
								<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:5" onclick="changeDownloadPermissionSet(this);" />
							</logic:notMatch>
						</td>
					</c:when>
					<c:otherwise>
						<td class="radio"></td>
					</c:otherwise>
				</c:choose>
		
				<td class="radio">
					<logic:match name="groupForm" property="selectedDownloadPermission[${category.id}]" value="2">
							<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:2" checked="checked" onclick="changeDownloadPermissionSet(this);"  />
					</logic:match>
					<logic:notMatch name="groupForm" property="selectedDownloadPermission[${category.id}]" value="2">
						<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:2" onclick="changeDownloadPermissionSet(this);" />
					</logic:notMatch>
				</td>
		
				<c:choose>
					<c:when test="${!groupForm.group.isDefaultGroup}">
						<td class="radio">
							<logic:match name="groupForm" property="selectedDownloadPermission[${category.id}]" value="7">
								<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:7" checked="checked" onclick="changeDownloadPermissionSet(this);"  />
							</logic:match>
						<logic:notMatch name="groupForm" property="selectedDownloadPermission[${category.id}]" value="7">
							<input type="radio" class="radio" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:7" onclick="changeDownloadPermissionSet(this);" />
							</logic:notMatch>
						</td>
					</c:when>
					<c:otherwise>
						<td class="radio"></td>
					</c:otherwise>
				</c:choose>
		
		
				<!-- Upload permissions -->
				<c:choose>
					<c:when test="${!groupForm.group.isDefaultGroup}">
						<td class="spacer">&nbsp;</td>
						<td class="radio">
							<logic:match name="groupForm" property="selectedUploadPermission[${category.id}]" value="0">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="0" checked="checked" />
							</logic:match>
							<logic:notMatch name="groupForm" property="selectedUploadPermission[${category.id}]" value="0">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="0" />
							</logic:notMatch>
						</td>
						<td class="radio">
							<logic:match name="groupForm" property="selectedUploadPermission[${category.id}]" value="6">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="6" checked="checked" />
							</logic:match>
							<logic:notMatch name="groupForm" property="selectedUploadPermission[${category.id}]" value="6">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="6" />
							</logic:notMatch>
						</td>
						<td class="radio">
							<logic:match name="groupForm" property="selectedUploadPermission[${category.id}]" value="3">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="3" checked="checked" />
							</logic:match>
							<logic:notMatch name="groupForm" property="selectedUploadPermission[${category.id}]" value="3">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="3" />
							</logic:notMatch>
						</td>
						<td class="radio">
							<logic:match name="groupForm" property="selectedUploadPermission[${category.id}]" value="4">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="4" checked="checked" />
							</logic:match>
							<logic:notMatch name="groupForm" property="selectedUploadPermission[${category.id}]" value="4">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="4" />
							</logic:notMatch>
						</td>
						<td class="radio">
							<logic:match name="groupForm" property="selectedUploadPermission[${category.id}]" value="12">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="12" checked="checked" />
							</logic:match>
							<logic:notMatch name="groupForm" property="selectedUploadPermission[${category.id}]" value="12">
								<input type="radio" class="radio" name="catUploadPermission<bean:write name='category' property='id'/>" value="12" />
							</logic:notMatch>
						</td>
					</c:when>
					<c:otherwise>
						<td class="spacer">&nbsp;</td>
						<td class="radio"></td>
						<td class="radio"></td>
						<td class="radio"></td>
						<td class="radio"></td>
						<td class="radio"></td>
					</c:otherwise>
				</c:choose>
		
					<!-- Advanced permissions -->
				
				<td class="spacer">&nbsp;</td>
				<td class="checkbox">
					<input type="hidden" name="advanced_permissions" value="true" />
					<logic:match name="groupForm" property="selectedDownloadAdvancedPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catDownloadAdvanced<bean:write name='category' property='id'/>" name="catDownloadAdvanced<bean:write name='category' property='id'/>" value="1" checked="checked" />
					</logic:match>
					<logic:notMatch name="groupForm" property="selectedDownloadAdvancedPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catDownloadAdvanced<bean:write name='category' property='id'/>" name="catDownloadAdvanced<bean:write name='category' property='id'/>" value="1" />
					</logic:notMatch>
				</td>
				<td class="checkbox">
					<logic:match name="groupForm" property="selectedDownloadOriginalPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catDownloadOriginal<bean:write name='category' property='id'/>" name="catDownloadOriginal<bean:write name='category' property='id'/>" value="1" checked="checked" />
					</logic:match>
					<logic:notMatch name="groupForm" property="selectedDownloadOriginalPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catDownloadOriginal<bean:write name='category' property='id'/>" name="catDownloadOriginal<bean:write name='category' property='id'/>" value="1" />
					</logic:notMatch>
				</td>
				<td class="checkbox">
					<logic:match name="groupForm" property="selectedHighResApprovalPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catHighResApproval<bean:write name='category' property='id'/>" name="catHighResApproval<bean:write name='category' property='id'/>" value="1" checked="checked" />
					</logic:match>
					<logic:notMatch name="groupForm" property="selectedHighResApprovalPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catHighResApproval<bean:write name='category' property='id'/>" name="catHighResApproval<bean:write name='category' property='id'/>" value="1" />
					</logic:notMatch>
				</td>
				<c:if test="${ratings || comments}">
					<td class="checkbox">
					<logic:match name="groupForm" property="selectedReviewAssetsPermission[${category.id}]" value="true">
						<input type="checkbox" class="checkbox" id="catReviewAssets<bean:write name='category' property='id'/>" name="catReviewAssets<bean:write name='category' property='id'/>" value="1" checked="checked" />
						</logic:match>
						<logic:notMatch name="groupForm" property="selectedReviewAssetsPermission[${category.id}]" value="true">
							<input type="checkbox" class="checkbox" id="catReviewAssets<bean:write name='category' property='id'/>" name="catReviewAssets<bean:write name='category' property='id'/>" value="1" />
						</logic:notMatch>
					</td>
				</c:if>
				<c:if test="${hideRestrictedAssetImages}">
					<td class="checkbox">
						<logic:match name="groupForm" property="selectedViewRestrictedAssetsPermission[${category.id}]" value="true">
							<input type="checkbox" class="checkbox" id="catViewRestrictedAssets<bean:write name='category' property='id'/>" name="catViewRestrictedAssets<bean:write name='category' property='id'/>" value="1" checked="checked" />
						</logic:match>
						<logic:notMatch name="groupForm" property="selectedViewRestrictedAssetsPermission[${category.id}]" value="true">
							<input type="checkbox" class="checkbox" id="catViewRestrictedAssets<bean:write name='category' property='id'/>" name="catViewRestrictedAssets<bean:write name='category' property='id'/>" value="1" />
						</logic:notMatch>
					</td>
				</c:if>
			</c:otherwise>
		</c:choose>
	</tr>