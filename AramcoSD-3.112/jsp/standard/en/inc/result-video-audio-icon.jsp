
						<c:choose>
							<c:when test="${asset.isVideo}">
								<%-- Icon for video  --%>
								<img src="../images/standard/icon/video.gif" border="0" width="15" height="11" alt="Video" class="media_icon" />
							</c:when>
							<c:when test="${asset.isAudio}">
								<%-- Icon for audio  --%>
								<img src="../images/standard/icon/audio.gif" border="0" width="13" height="12" alt="Audio" class="media_icon" />
							</c:when>
						</c:choose>	
						<br />		
