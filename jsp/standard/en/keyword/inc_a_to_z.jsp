	<bean:parameter id="filter" name="filter" value="a"/>
	<table cellspacing="0" cellpadding="0" summary="Keyword navigation" class="keywordNav">
		<tr>
			<td <c:if test="${filter == 'a'}">class="current"</c:if>><a href="<c:out value='${sUrl}a' />">A</a></td>
			<td <c:if test="${filter == 'b'}">class="current"</c:if>><a href="<c:out value='${sUrl}b' />">B</a></td>
			<td <c:if test="${filter == 'c'}">class="current"</c:if>><a href="<c:out value='${sUrl}c' />">C</a></td>
			<td <c:if test="${filter == 'd'}">class="current"</c:if>><a href="<c:out value='${sUrl}d' />">D</a></td>
			<td <c:if test="${filter == 'e'}">class="current"</c:if>><a href="<c:out value='${sUrl}e' />">E</a></td>
			<td <c:if test="${filter == 'f'}">class="current"</c:if>><a href="<c:out value='${sUrl}f' />">F</a></td>
			<td <c:if test="${filter == 'g'}">class="current"</c:if>><a href="<c:out value='${sUrl}g' />">G</a></td>
			<td <c:if test="${filter == 'h'}">class="current"</c:if>><a href="<c:out value='${sUrl}h' />">H</a></td>
			<td <c:if test="${filter == 'i'}">class="current"</c:if>><a href="<c:out value='${sUrl}i' />">I</a></td>
			<td <c:if test="${filter == 'j'}">class="current"</c:if>><a href="<c:out value='${sUrl}j' />">J</a></td>
			<td <c:if test="${filter == 'k'}">class="current"</c:if>><a href="<c:out value='${sUrl}k' />">K</a></td>
			<td <c:if test="${filter == 'l'}">class="current"</c:if>><a href="<c:out value='${sUrl}l' />">L</a></td>
			<td <c:if test="${filter == 'm'}">class="current"</c:if>><a href="<c:out value='${sUrl}m' />">M</a></td>
			<td <c:if test="${filter == 'n'}">class="current"</c:if>><a href="<c:out value='${sUrl}n' />">N</a></td>
			<td <c:if test="${filter == 'o'}">class="current"</c:if>><a href="<c:out value='${sUrl}o' />">O</a></td>
			<td <c:if test="${filter == 'p'}">class="current"</c:if>><a href="<c:out value='${sUrl}p' />">P</a></td>
			<td <c:if test="${filter == 'q'}">class="current"</c:if>><a href="<c:out value='${sUrl}q' />">Q</a></td>
			<td <c:if test="${filter == 'r'}">class="current"</c:if>><a href="<c:out value='${sUrl}r' />">R</a></td>
			<td <c:if test="${filter == 's'}">class="current"</c:if>><a href="<c:out value='${sUrl}s' />">S</a></td>
			<td <c:if test="${filter == 't'}">class="current"</c:if>><a href="<c:out value='${sUrl}t' />">T</a></td>
			<td <c:if test="${filter == 'u'}">class="current"</c:if>><a href="<c:out value='${sUrl}u' />">U</a></td>
			<td <c:if test="${filter == 'v'}">class="current"</c:if>><a href="<c:out value='${sUrl}v' />">V</a></td>
			<td <c:if test="${filter == 'w'}">class="current"</c:if>><a href="<c:out value='${sUrl}w' />">W</a></td>
			<td <c:if test="${filter == 'x'}">class="current"</c:if>><a href="<c:out value='${sUrl}x' />">X</a></td>
			<td <c:if test="${filter == 'y'}">class="current"</c:if>><a href="<c:out value='${sUrl}y' />">Y</a></td>
			<td <c:if test="${filter == 'z'}">class="current"</c:if>><a href="<c:out value='${sUrl}z' />">Z</a></td>
			<td <c:if test="${filter == 'all'}">class="current"</c:if>><a href="<c:out value='${sUrl}all' />"><bright:cmsWrite identifier="snippet-all-keywords" filter="false"/></a></td>
		</tr>
	</table>