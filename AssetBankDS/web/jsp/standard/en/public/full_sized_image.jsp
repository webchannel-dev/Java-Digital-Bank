<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	22-Feb-2006		Tidied up html
	 d4		Ben Browning	11-Dec-2007		Added javascript zoom
	 d5     Matt Woollard   6-Feb-2009      Stopped window closing on click for PDFs
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="largestDimension" settingName="zoom-initial-size"/>
<bright:applicationSetting id="zoomEnabled" settingName="large-image-has-zoom"/>

<head>
	<meta http-equiv="imagetoolbar" content="no" />
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Full-size Image</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/lib/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var imageZoom = {
		
			//initialise some variables
			dispWidth : 0,
			dispHeight : 0,	 
			storedWidth : 0,
			storedHeight : 0,
			verticalOffset : 0,
			topOffset : 0,		

			init : function(settings) {
				//defaul config
				imageZoom.config = {
					$imageObject : $j('#myImage')
				};
				//allow overriding of the default config
				$j.extend(imageZoom.config, settings);

        		<logic:present parameter="layer">
					<c:if test="${not empty numLayers && numLayers > 1}">
						// Need some extra vertical space when have paging links
						imageZoom.verticalOffset = 70;
						//imageZoom.topOffset = 20;
					</c:if>
				</logic:present>
				
				imageZoom.setup(); 
			},
			
			setup : function() {
				// get dimensions of browser window
				var window_size = {
					height: $j(window).height(),
					width: $j(window).width()
				}
				var largestDim = <bean:write name="largestDimension"/>;
				//Get dimensions of the image (as displayed in the page) - use old fashioned method for accurate results in IE
				imageZoom.dispWidth = document.getElementById('myImage').width;
				imageZoom.dispHeight = document.getElementById('myImage').height;
			
				<c:if test="${zoomEnabled == true}">
					// Adjust display dimensions to conform to the max initial dimension from the app settings
					if (imageZoom.dispWidth>imageZoom.dispHeight && imageZoom.dispWidth>largestDim){
						//landscape image
						ratio = imageZoom.dispWidth/largestDim;
						imageZoom.dispWidth = largestDim;
						imageZoom.dispHeight = Math.round(imageZoom.dispHeight/ratio);
					}
					else if (imageZoom.dispHeight>imageZoom.dispWidth && imageZoom.dispHeight>largestDim){
						//portrait image
						ratio = imageZoom.dispHeight/largestDim;
						imageZoom.dispHeight = largestDim;
						imageZoom.dispWidth = Math.round(imageZoom.dispWidth/ratio);
					}
					else if (imageZoom.dispHeight==imageZoom.dispWidth && imageZoom.dispHeight>largestDim){
						//square image
						imageZoom.dispHeight = largestDim;
						imageZoom.dispWidth = largestDim;
					}
					
				</c:if>
			
				//save the adjusted image dimensions into global variables
				imageZoom.storedWidth = imageZoom.dispWidth;
				imageZoom.storedHeight = imageZoom.dispHeight;
				
				// work out new window width and height:
				var newWindowWidth = imageZoom.dispWidth - window_size.width;
				if (newWindowWidth < -100) {
					newWindowWidth = -100;		// don't want the window to resize smaller than this
					}
				var newWindowHeight = imageZoom.dispHeight - window_size.height + imageZoom.verticalOffset;
				if (newWindowHeight < -100) {
					newWindowHeight = -100;		// don't want the window to resize smaller than this
					}

				//resize the window
				window.resizeBy(newWindowWidth,newWindowHeight); 
			
				<c:if test="${zoomEnabled == true}">
					//resize the image
					imageZoom.reset();
					
					$j('body').css('height',$j(document).height());
					
					//Handle mouse wheel events
					if(window.addEventListener)
						document.addEventListener('DOMMouseScroll', imageZoom.moveObject, false);

					//for IE/OPERA etc
					document.onmousewheel = imageZoom.moveObject;
				
				</c:if>

				self.focus(); 

			},
			
			changeCSS : function(newWidth,newHeight,newLeft,newTop) {
				imageZoom.config.$imageObject.css({
					width : newWidth,
					height : newHeight,
					left : newLeft,
					top : newTop
				});	
			},
			
			reset : function() {
				//revert the image to its display size
				imageZoom.dispWidth = imageZoom.storedWidth;
				imageZoom.dispHeight = imageZoom.storedHeight;
				imageZoom.changeCSS(imageZoom.dispWidth,imageZoom.dispHeight,'0',imageZoom.topOffset);
			},
			
			actual : function() {
				//revert the image to its actual size
				imageZoom.dispWidth = <bean:write name="originalWidth"/>;
				imageZoom.dispHeight = <bean:write name="originalHeight"/>;
				imageZoom.changeCSS(imageZoom.dispWidth,imageZoom.dispHeight,'0','0');
			},
			
			zoomIn : function(){
				//increase the image size by 10%
				imageZoom.dispWidth = imageZoom.dispWidth + (imageZoom.dispWidth*0.1);
				imageZoom.dispHeight = imageZoom.dispHeight + (imageZoom.dispHeight*0.1);
				imageZoom.config.$imageObject.css({	
					width : imageZoom.dispWidth,
					height : imageZoom.dispHeight,
					left : function(index,value) {
						return parseFloat(value) - (imageZoom.dispWidth*0.05);
					},
					top : function(index,value) {
						return parseFloat(value) - (imageZoom.dispHeight*0.05);
					}
				});
			},
			
			zoomOut : function(){
				//decrease the image size by 10%
				imageZoom.dispWidth = imageZoom.dispWidth - (imageZoom.dispWidth*0.1);
				imageZoom.dispHeight = imageZoom.dispHeight - (imageZoom.dispHeight*0.1);
				imageZoom.config.$imageObject.css({	
					width : imageZoom.dispWidth,
					height : imageZoom.dispHeight,
					left : function(index,value) {
						return parseFloat(value) + (imageZoom.dispWidth*0.05);
					},
					top : function(index,value) {
						return parseFloat(value) + (imageZoom.dispHeight*0.05);
					}
				});
			},
			
			moveObject : function(event) {
				var delta = 0;
				if (!event) event = window.event;

				// normalize the delta
				if (event.wheelDelta) {
					// IE & Opera
					delta = event.wheelDelta / 60;
				} else if (event.detail) {
					// W3C
					delta = -event.detail / 2;
				}
				
				if (delta > 0) {
					imageZoom.zoomIn()
				} else {
					imageZoom.zoomOut()
				}
				
			}
	
		}

	
		//when DOM loaded
		$j( function() {
			
			<c:if test="${zoomEnabled == true}">
				//enable dragging of image
				$j('#myImage').draggable();
			</c:if>
			
			<c:if test="${numLayers==1 && zoomEnabled != true}">
				$j('#myImage').click(function() {
					window.close();
				});
			</c:if>
			
			
		
		});
	</script>
	
	<c:if test="${zoomEnabled == true}">
		<style type="text/css">
				html, body { overflow: hidden; text-align:center; background:#ccc; }
				#myImage {
					cursor: move;
				}
		</style>
	</c:if>
	
	<style type="text/css" media="print">
		/* Hide control panel when printing */
		#zoomControlPanel {
			display: none !important;
			}
	</style>

</head>

<body onload="imageZoom.init();" id="fullSize">
	<bean:define id="rightClickMessage" value="Please do not attempt to download this image by using the right-click menu."/>


	<logic:present parameter="layer">
		<bean:parameter id="layer" name="layer"/>
		<bean:parameter id="size" name="size"/>
		<bean:parameter id="id" name="id"/>
		<c:if test="${not empty numLayers && numLayers > 1}">
			<div class="paging">
			
				<c:set var="currentPage" value="${layer}" />
				<c:set var="numPages" value="${numLayers}" />
				<c:set var="fullName" value="${assetApprovalForm.user.fullName}" />
				
				
				<c:if test="${layer==1}">
					<span class="disabled">&laquo;&nbsp;Prev</span>
				</c:if>
				<c:if test="${layer>1}">
					<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer-1}"/>"><bright:cmsWrite identifier="link-prev" filter="false"/></a>
				</c:if>
				&nbsp;&nbsp;<bright:cmsWrite identifier="snippet-page-x-of-y" filter="false" replaceVariables="true"/>&nbsp;&nbsp;
				<c:if test="${layer==numLayers}">
					<span class="disabled">Next&nbsp;&raquo;</span>
				</c:if>
				<c:if test="${layer<numLayers}">
					<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer+1}"/>"><bright:cmsWrite identifier="link-next" filter="false"/></a>
				</c:if>
			</div>
			
			
		</c:if>
	</logic:present>
	
	<div id="zoomControlPanel" class="<c:if test="${zoomEnabled == true}">js-enabled-show</c:if> printHide">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:35%"><strong>Click and drag</strong> to reposition image.</td>
				<td style="text-align:center;">
					<a href="#" onclick="imageZoom.zoomIn(); return false;" title="zoom out" ><img src="../images/standard/icon/zoom_in.gif" width="30" height="30" style="border:0; margin-right:10px;" alt="zoom in" title="zoom in" /></a> 
					<a href="#" onclick="imageZoom.zoomOut(); return false;" title="zoom out" ><img src="../images/standard/icon/zoom_out.gif" width="30" height="30" style="border:0;" alt="zoom out" /></a> </td>
				<td style="text-align:right; width:35%">
					[<a href="#" onclick="imageZoom.reset(); return false;">reset</a>] 
					[<a href="#" onclick="imageZoom.actual(); return false;">actual size</a>] 
					[<a href="#" onclick="window.print(); return false;">print</a>]
					<!-- [<a href="#" onclick="window.close()">close window</a>]  --><br />
				</td>
			</tr>
		</table>
	</div>

				
	<img name="myImage" id="myImage" src="../servlet/display?file=<bean:write name='file'/>" alt="" border="0" />

	 
		
	<logic:present parameter="layer">
		<c:if test="${not empty numLayers && numLayers > 1}">
			<div id="pageNumbers">
				<input type="button" class="button flush" onclick="window.close()" value="Close" style="float:right; margin: 0.3em 1em 0 0" />

				<c:if test="${layer==1}">
					<span class="disabled">[<bright:cmsWrite identifier="link-first" filter="false"/>]</span>
					<span class="disabled"><bright:cmsWrite identifier="link-prev" filter="false"/></span>
				</c:if>
				<c:if test="${layer>1}">
					[<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=1"><bright:cmsWrite identifier="link-first" filter="false"/></a>]
					<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer-1}"/>"><bright:cmsWrite identifier="link-prev" filter="false"/></a>
				</c:if>
				
				<span class="numbers">
				<c:if test="${layer>10}">
					[<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer-10}"/>">-10</a>] ...
				</c:if>
				<c:if test="${layer<=10 && layer>6}">
					[<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer-5}"/>">-5</a>] ...
				</c:if>
				
				<c:forEach begin="1" end="${numLayers}" step="1" var="layerNum">
					<c:if test="${(layer-layerNum<=5 || (numLayers-layer<5 && numLayers-layerNum<=10)) && (layerNum-layer<=5 || (layer<5 && layerNum<=11))}">
						<c:if test="${layerNum>1}">|</c:if>
						<c:if test="${layer!=layerNum}">
							<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<bean:write name="layerNum"/>"><bean:write name="layerNum"/></a>
						</c:if>
						<c:if test="${layer==layerNum}">
							<span class="disabled"><bean:write name="layerNum"/></span>
						</c:if>
					</c:if>
				</c:forEach>
				<c:if test="${numLayers-layer<10 && numLayers-layer>5}">
					... [<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer+5}"/>">+5</a>]
				</c:if>
				<c:if test="${numLayers-layer>=10}">
					... [<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer+10}"/>">+10</a>]
				</c:if>
				</span>
				
				<c:if test="${layer==numLayers}">
					<span class="disabled"><bright:cmsWrite identifier="link-next" filter="false"/></span>
					<span class="disabled">[<bright:cmsWrite identifier="link-last" filter="false"/>]</span>
				</c:if>
				<c:if test="${layer<numLayers}">
					<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${layer+1}"/>"><bright:cmsWrite identifier="link-next" filter="false"/></a>
					[<a href="../action/viewFullSizedImage?id=<bean:write name="id"/>&size=<bean:write name="size"/>&layer=<c:out value="${numLayers}"/>"><bright:cmsWrite identifier="link-last" filter="false"/></a>]
				</c:if>
				
			</div>
		</c:if>
	</logic:present>
				
	<bright:applicationSetting id="disableRightClick" settingName="disable-right-click"/>
	<c:if test="${disableRightClick == true}">
		<script type="text/javascript">
			var clickmessage="Please do not attempt to download this image by using the right-click menu."
			
			document.oncontextmenu = function (event) {
				event = event || window.event;
				var target = event.target || event.srcElement;
	
				if (target.tagName=="IMG"){
					if (event.preventDefault) {	
					event.preventDefault();
					}	
				alert(clickmessage);
				}								
	
				return false;
			}

		</script>
	</c:if>
		
		

</body>

</html>

