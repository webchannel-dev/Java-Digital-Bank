asset-bank 3.1221.15 (upgraded)  using updater commandline tool.
Added 360 support

Changes made for 360 (IV not included)


1.copied  3 files to jsp/custom
	inc_360_jpg_preview.jsp
	inc_360_preview.jsp
	inc_iv_preview.jsp  - for .iv


2.In the asset-bank/flash/custom directory place the flsh player js files & resources.
	Makesure that .js files included in jsp are in the location as mentioned in jsp. (Had to fix them!)



3.DB -> table fileformat 
	Remove index fileExtension sothat jpg & flv file extensions can be duplicated thus can be used for normal as well as 360 versions.
	Added entries for 360 jpg, 360 flv in the fileformat table.

9998	1	jpg	0	1	0	0	Photint 360 JPG	Image - Photint 360 JPG 	Image - Photint 360 JPG Format	thumbnails/oyoon.gif	image/jpg			../../../custom/inc_360_jpg_preview.jsp			0	0	
9999	1	flv	0	1	0	0	Photoint 360 FLV	Video - Photoint 360 FLV Format	thumbnails/oyoon.gif	video/x-flv	com.bright.assetbank.converter.VideoToPngConverter		../../../custom/inc_360_preview.jsp			0	0	




.


Note
---
**For FLV360 fileformat the 'isConvertable' attribute of the table is set to 1 and com.bright.assetbank.converter.VideoToPngConverter is used for generating thumbnails. 

** Change in inc_360_preview.jsp

%-- Referring to OriginalFileLocation to get the path to source FLV --%>

<c:set scope="request" var="fileUrl2" value="../../servlet/display/${assetForm.encryptedOriginalFilePath}"/>

Fixed the path forAC_OETags.js