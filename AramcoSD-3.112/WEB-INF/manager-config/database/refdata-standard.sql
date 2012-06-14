-- Reference data for the standard Asset bank --

-- Developers:
-- You should update refdata-standard.sql, but DO NOT manually update refdata-standard-sqlserver.sql 
-- To generate refdata-standard-sqlserver.sql, run the ant target db-sqlserver-refdata-standard (replaces "-- SQLSERVER " with "").

-- Reference data and settings --

-- SQLSERVER SET IDENTITY_INSERT Language ON;
INSERT INTO Language (Id, Name, Code, IsSuspended, IsDefault) VALUES (1,'English', 'en',0,1);
-- SQLSERVER SET IDENTITY_INSERT Language OFF;

-- SQLSERVER SET IDENTITY_INSERT AssetType ON;
INSERT INTO AssetType (Id, Name) VALUES (1,'File');
INSERT INTO AssetType (Id, Name) VALUES (2,'Image');
INSERT INTO AssetType (Id, Name) VALUES (3,'Video');
INSERT INTO AssetType (Id, Name) VALUES (4,'Audio');
-- SQLSERVER SET IDENTITY_INSERT AssetType OFF;


-- SQLSERVER SET IDENTITY_INSERT CM_CategoryType ON;
INSERT INTO CM_CategoryType (Id, Description, IsAlphabeticOrder, IsNameGloballyUnique) VALUES (1, 'Asset categories', 0, 0);
INSERT INTO CM_CategoryType (Id, Description, IsAlphabeticOrder, IsNameGloballyUnique) VALUES (2, 'Access levels', 0, 0);
-- SQLSERVER SET IDENTITY_INSERT CM_CategoryType OFF;


-- SQLSERVER SET IDENTITY_INSERT AssetDownloadType ON;
INSERT INTO AssetDownloadType (Id, Description) VALUES (1,'Preview');
INSERT INTO AssetDownloadType (Id, Description) VALUES (2,'Download converted');
INSERT INTO AssetDownloadType (Id, Description) VALUES (3,'Download original');
-- SQLSERVER SET IDENTITY_INSERT AssetDownloadType OFF;


-- SQLSERVER SET IDENTITY_INSERT ApprovalStatus ON;
INSERT INTO ApprovalStatus (Id,Name) VALUES (1,'Pending Approval');
INSERT INTO ApprovalStatus (Id,Name) VALUES (2,'Approved');
INSERT INTO ApprovalStatus (Id,Name) VALUES (3,'Rejected');
-- SQLSERVER SET IDENTITY_INSERT ApprovalStatus OFF;

-- SQLSERVER SET IDENTITY_INSERT OrderWorkflow ON;
INSERT INTO OrderWorkflow (Id,Name) VALUES (1,'Personal');
INSERT INTO OrderWorkflow (Id,Name) VALUES (2,'Commercial');
-- SQLSERVER SET IDENTITY_INSERT OrderWorkflow OFF;

-- SQLSERVER SET IDENTITY_INSERT OrderStatus ON;
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (1,'Fulfilled',1,0);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (2,'Awaiting Fulfillment',1,0);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (3,'Requires Approval',2,1);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (4,'Requires Online Payment',2,1);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (5,'Requires Offline Payment',2,1);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (6,'Paid For Online',2,0);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (7,'Paid For Offline',2,1);
INSERT INTO OrderStatus (Id,Name,OrderWorkflowId,ManualSelect) VALUES (8,'Declined',2,1);
-- SQLSERVER SET IDENTITY_INSERT OrderStatus OFF;

INSERT INTO SystemSetting (Id,Value) VALUES ('PurchaseId','1');
INSERT INTO SystemSetting (Id,Value) VALUES ('FeaturedImageIndex','0');
INSERT INTO SystemSetting (Id, Value) VALUES ('CurrentVersion' , '');
INSERT INTO SystemSetting (Id, Value) VALUES ('UpdateAvailable' , 'no');
INSERT INTO SystemSetting (Id, Value) VALUES ('lastRunSERules' , '');
INSERT INTO SystemSetting (Id, Value) VALUES ('lastRunCAVRules' , '');
INSERT INTO SystemSetting (Id,Value) VALUES ('DownloadId','1');


-- Formats supported --

-- SQLSERVER SET IDENTITY_INSERT FileFormat ON;
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (1,'JPEG','JPEG - Presentations or Web','jpg',2,0,1,1,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (2,'JPEG','JPEG - Presentations or Web','jpeg',2,0,1,0,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (3,'JPEG','JPEG - Presentations or Web','jpe',2,0,1,0,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (4,'GIF','GIF - Diagrams or Web','gif',2,0,1,1,null,'image/gif');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (5,'PNG','PNG - Web','png',2,0,1,1,null,'image/png');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (6,'TIFF','TIFF - Print','tif',2,0,1,1,null,'image/tiff');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (7,'TIFF','TIFF - Print','tiff',2,0,1,0,null,'image/tiff');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (8,'BMP','Bitmap','bmp',2,0,1,0,null,null);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (9,'TGA','Truevision Targa','tga',2,0,1,0,null,null);

-- PSD conversion
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES (10,'PSD','Photoshop','psd',2,0,1,0,null,null,'com.bright.assetbank.converter.IMToJpgConverter');

INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (11,'PPM','PPM','ppm',2,0,1,0,null,null);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(12,'NEF','NEF','nef',2,0,1,0,null,null,'com.bright.assetbank.converter.RawToImageConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(13,'EPS','EPS','eps',2,0,1,0,null,null,'com.bright.assetbank.converter.IMToJpgConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(14,'AI','Adobe Illustrator','ai',2,0,1,0,null,null,null);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(15,'PS','PostScript','ps',2,0,1,0,null,null,null);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(16,'CR2','CR2','cr2',2,0,1,0,null,null,'com.bright.assetbank.converter.RawToImageConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(17,'DNG','Adobe Digital Negative','dng',2,0,1,0,null,null,null);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES   (18,'PCD','Kodak Photo CD','pcd',2,0,1,0,null,null,null);

-- The contents of PDF and Word documents are indexed by default. This may slow down searches, if you have very large documents. Set IsIndexable=0 to switch off.
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (100,'DOC','Word documents','doc',1,1,0,0,'thumbnails/word_icon.gif','application/msword');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (101,'TEXT','Text documents','txt',1,0,0,0,'thumbnails/text_icon.gif','text/plain');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (102,'XLS','Excel spreadsheets','xls',1,0,0,0,'thumbnails/excel_icon.gif','application/vnd.ms-excel');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (103,'PPT','Powerpoint presentations','ppt',1,0,0,0,'thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (104,'ZIP','Zip archives','zip',1,0,0,0,'thumbnails/zip_icon.gif','application/zip');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (105,'RTF','RTF documents','rtf',1,0,0,0,'thumbnails/rtf_icon.gif','text/rtf');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (106,'EXT','Excel templates','xlt',1,0,0,0,'thumbnails/excel_icon.gif','application/vnd.ms-excel');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ToTextConverterClass) VALUES (107,'PDF','Adobe PDF','pdf',2,1,1,0,'thumbnails/pdf_icon.gif','application/pdf','com.bright.assetbank.converter.PDFToTextConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (108,'HTML','Text - HTML','html',1,0,0,0,'thumbnails/html_icon.gif','text/html');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (109,'MHT','Web archive - MHT','mht',1,0,0,0,null,'application/mht');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (200,'WAV','Audio - WAV format','wav',4,0,0,0,'thumbnails/audio_icon.gif','audio/x-wav');

INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (350, 4, 'mp3', 'MP3', 'Audio - MPEG Format', 'thumbnails/audio_icon.gif', 'audio/mpeg');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (351, 4, 'aiff', 'AIFF', 'Audio - AIFF Format', 'thumbnails/audio_icon.gif', 'audio/x-aiff');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (352, 4, 'aif', 'AIF', 'Audio - AIFF Format', 'thumbnails/audio_icon.gif', 'audio/x-aiff');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (353, 4, 'au', 'AU', 'Audio - AU Format', 'thumbnails/audio_icon.gif', 'audio/basic');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (354, 4, 'wma', 'WMA', 'Audio - WMA Format', 'thumbnails/audio_icon.gif', 'audio/x-ms-wma');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (355, 4, 'aac', 'AAC', 'Audio - AAC Format', 'thumbnails/audio_icon.gif', 'audio/x-aac');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (356, 4, 'm4a', 'M4A', 'Audio - M4A Format', 'thumbnails/audio_icon.gif', 'audio/mp4');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (357, 4, 'mid', 'MID', 'Audio - MIDI Format', 'thumbnails/audio_icon.gif', 'audio/midi');
INSERT INTO FileFormat (Id, AssetTypeId, FileExtension, Name, Description, ThumbnailFileLocation, ContentType) VALUES (358, 4, 'midi', 'MIDI', 'Audio - MIDI Format', 'thumbnails/audio_icon.gif', 'audio/midi');

INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (300,'MPEG','Video - MPEG format','mpg',3,0,1,1,null,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (301,'MPEG','Video - MPEG format','mpeg',3,0,1,0,null,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (302,'AVI','Video - AVI format','avi',3,0,1,0,null,'video/x-msvideo','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (303,'MOV','Video - Quicktime format','mov',3,0,1,0,null,'video/quicktime','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (304,'WMV','Video - WMV format','wmv',3,0,1,1,null,'video/x-ms-wmv','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (305,'ASF','Video - ASF format','asf',3,0,1,0,null,'video/x-ms-asf','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (306,'MP4','Video - MP4 format','mp4',3,0,1,0,null,'video/mp4','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (307,'ASX','Video - ASF format','asx',3,0,1,0,null,'video/x-ms-asf','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (308,'VOB','Video - DVD format','vob',3,0,1,0,null,'video/dvd','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (309,'FLV','Video - FLV Format','flv',3,0,1,1,null,'video/x-flv','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (310,'DV','Video - DV Format','dv',3,0,1,0,null,'video/x-dv','com.bright.assetbank.converter.VideoToPngConverter');

-- Uncomment the following if you want to create thumbnails from Flash files. Requires SwfToImage to be installed.
-- INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(400,'SWF','Flash','swf',2,0,1,0,null,null,'com.bright.assetbank.converter.SwfToImageConverter');

-- SQLSERVER SET IDENTITY_INSERT FileFormat OFF;


-- Attribute specifications --

-- Attribute types
-- SQLSERVER SET IDENTITY_INSERT AttributeType ON;
INSERT INTO AttributeType (Id,Name) VALUES (1,'Textfield');
INSERT INTO AttributeType (Id,Name) VALUES (2,'Textarea');
INSERT INTO AttributeType (Id,Name) VALUES (3,'Datepicker');
INSERT INTO AttributeType (Id,Name) VALUES (4,'Dropdown');
INSERT INTO AttributeType (Id,Name) VALUES (5,'Checklist');
INSERT INTO AttributeType (Id,Name) VALUES (6,'Optionlist');
INSERT INTO AttributeType (Id,Name) VALUES (7,'Keyword picker');
INSERT INTO AttributeType (Id,Name) VALUES (8,'Datetime');
INSERT INTO AttributeType (Id,Name) VALUES (9,'Hyperlink');
-- SQLSERVER SET IDENTITY_INSERT AttributeType OFF;


-- Static attributes
-- SQLSERVER SET IDENTITY_INSERT Attribute ON;
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField) VALUES (1, 1, 1, 'file', 'File', 1, 0);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (2, 2, 1, 'assetId', 'ID', 0, 1, 1);
INSERT INTO Attribute (Id, AttributeTypeId, SequenceNumber, IsStatic, Label, IsMandatory, IsSearchField, NameAttribute, IsKeywordSearchable) VALUES (3, 1, 3, 0, 'Title', 1, 0, 1, 1);
INSERT INTO Attribute (Id, AttributeTypeId, SequenceNumber, IsStatic, Label, IsMandatory, IsSearchField, DefaultValue, IsKeywordSearchable) VALUES (4, 2, 4, 0, 'Description', 1, 0,'', 1);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (5, 5, 2, 'Keywords',1, 0, 0);

INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (6, 6, 1, 'originalFilename', 'Original Filename', 0, 0, 1);

INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (7, 7, 3, 'Date Created', 0,0,0);

INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (8, 8, 1, 'dateAdded', 'Date Added', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (9, 9, 1, 'dateLastModified', 'Date Last Modified', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (10, 10, 1, 'addedBy', 'Added By', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (11, 11, 1, 'lastModifiedBy', 'Last Modified By', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (12, 12, 1, 'size', 'Size', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (13, 13, 1, 'orientation', 'Orientation', 0, 1, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField) VALUES (14, 14, 1, 'price', 'Price', 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (15, 22, 1, 'embeddedData', 'Embedded Data', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (16, 23, 1, 'usage', 'Asset usage', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (17, 24, 1, 'categories', 'Categories', 0, 1, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (18, 25, 1, 'accessLevels', 'Access Levels', 0, 1, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (19, 26, 1, 'version', 'Version', 0, 0, 1);

-- Flexible attributes
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory,DefaultValue,ValueIfNotVisible) VALUES (20, 15, 4, 'Metadata status', 0, 1, 1, 'Complete', 'Complete');
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory,DefaultValue,ValueIfNotVisible) VALUES (21, 16, 4, 'Active status', 0, 1, 1, 'Active', 'Active');
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (22, 17, 3, 'Activation date', 0,0,0);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (23, 18, 3, 'Expiry date', 0,0,0);

INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (30, 19, 4, 'Usage rights', 0, 1, 0);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (31, 20, 1, ' ' ,0, 1, 0);
-- SQLSERVER SET IDENTITY_INSERT Attribute OFF;


-- SQLSERVER SET IDENTITY_INSERT AttributeValue ON;
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (1,30,'External Use',1,1);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (2,30,'Internal Use',1,2);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (3,30,'Other... (please specify below)',0,3);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (4,20,'Incomplete',0,1);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (5,20,'Complete',0,3);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (6,21,'Inactive',0,1);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (7,21,'Active',0,2);
INSERT INTO AttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (8,21,'Expired',0,3);
-- SQLSERVER SET IDENTITY_INSERT AttributeValue OFF;


-- Create attribute rules
-- SQLSERVER SET IDENTITY_INSERT ChangeAttributeValueDateRule ON;
INSERT INTO ChangeAttributeValueDateRule (Id, AttributeId, RuleName, ChangeAttributeId, NewAttributeValue, Enabled) VALUES (1,22,'Activate asset',21,'Active','1');
INSERT INTO ChangeAttributeValueDateRule (Id, AttributeId, RuleName, ChangeAttributeId, NewAttributeValue, Enabled) VALUES (2,23,'Expire asset',21,'Expired','1');
-- SQLSERVER SET IDENTITY_INSERT ChangeAttributeValueDateRule OFF;


-- Attribute Sort areas
-- SQLSERVER SET IDENTITY_INSERT SortArea ON;
INSERT INTO SortArea (Id, Name) VALUES (1, 'Search');
INSERT INTO SortArea (Id, Name) VALUES (2, 'Browse');
-- SQLSERVER SET IDENTITY_INSERT SortArea OFF;

INSERT INTO SortAttribute (AttributeId, SortAreaId, Sequence, Type, Reverse) VALUES (3, 2, 1, 3, 0);

-- Which attributes are display attributes
INSERT INTO DisplayAttribute (AttributeId, SequenceNumber, ShowLabel) VALUES (2, 1, 1);
INSERT INTO DisplayAttribute (AttributeId, SequenceNumber, ShowLabel) VALUES (3, 2, 0);


-- Groups, users and permissions --

-- Universal access level
-- SQLSERVER SET IDENTITY_INSERT CM_Category ON;
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted, IsRestrictive) 
VALUES (1, 2, 'Universal', 1, 1, 1, 1);
-- SQLSERVER SET IDENTITY_INSERT CM_Category OFF;

-- Default groups
-- In standard we have two default groups, public and logged-in users
-- Permissions are applied to the logged-in users group

-- SQLSERVER SET IDENTITY_INSERT UserGroup ON;
INSERT INTO UserGroup (Id, Name, Description, IsDefaultGroup) VALUES (1,'*Logged-in Users', 'Determines the default permissions applied to logged in users', 0);
INSERT INTO UserGroup (Id, Name, Description, IsDefaultGroup) VALUES (2,'*Public', 'Determines the default permissions applied to users who have not logged in', 1);
-- SQLSERVER SET IDENTITY_INSERT UserGroup OFF;

-- Add default permission on Universal access level
INSERT INTO CategoryVisibleToGroup (UserGroupId, CategoryId, CanUpdateAssets, CanDownloadAssets, CanUpdateWithApproval) VALUES (1,1,0,1,0);

-- Add permissions and exclusions on attributes
-- Logged in users
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,1,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,2,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,3,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,4,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,5,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,6,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,7,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,12,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,13,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,30,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,31,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,17,0);

-- Public
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,1,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,2,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,3,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,4,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,5,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,7,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,12,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,13,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,30,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,31,0);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,17,0);

-- Exclusions
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (20,1,'Incomplete');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,1,'Inactive');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,1,'Expired');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (20,2,'Incomplete');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,2,'Inactive');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,2,'Expired');

-- Default users
-- SQLSERVER SET IDENTITY_INSERT AssetBankUser ON;
INSERT INTO AssetBankUser (Id,Username,Password,Forename,Surname,EmailAddress,IsAdmin,ReceiveAlerts,LanguageId) VALUES (1,'admin','password','Admin','User','',1,1,1);
INSERT INTO AssetBankUser (Id,Username,Password,Forename,Surname,EmailAddress,IsAdmin,LanguageId) VALUES (2,'guest','password','Guest','User','',0,1);
UPDATE AssetBankUser SET NotActiveDirectory=1, NotApproved=0, Hidden=0;
INSERT INTO AssetBankUser (Id,Username,Password,Forename,Surname,EmailAddress,IsAdmin,Hidden,NotActiveDirectory,LanguageId) VALUES (3,'application','f5uyu9Ra','Application','User','',1,1,1,1);
-- SQLSERVER SET IDENTITY_INSERT AssetBankUser OFF;

-- SQLSERVER SET IDENTITY_INSERT AssetBox ON;
INSERT INTO AssetBox (Id,UserId,Name,SequenceNumber) VALUES (1,1,'My Lightbox',1);
INSERT INTO AssetBox (Id,UserId,Name,SequenceNumber) VALUES (2,2,'My Lightbox',1);
-- SQLSERVER SET IDENTITY_INSERT AssetBox OFF;

INSERT INTO UserInGroup (UserId, UserGroupId) VALUES (2,1);


-- Usage types and formats --

-- SQLSERVER SET IDENTITY_INSERT UsageType ON;
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId, CanEnterDetails, DetailsMandatory) VALUES (1,'Other',99999,3,1,1);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (2,'Advertisement',1,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (3,'MS Word',2,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (4,'MS PowerPoint',3,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (5,'Publication',4,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (6,'Website',5,3);
-- SQLSERVER SET IDENTITY_INSERT UsageType OFF;


-- SQLSERVER SET IDENTITY_INSERT UsageTypeFormat ON;
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (1, 1, NULL, 'Small', 240,240);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (2, 1, NULL, 'Medium', 500,500);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (3, 1, NULL, 'Large', 1024,1024);

INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (4, 1, 3, 'Small', 400,400);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (5, 1, 3, 'Full width', 800,800);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (6, 1, 4, 'Small', 200,200);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (7, 1, 4, 'Full width', 600,600);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (8, 1, 2, 'Small ad', 500,500);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (9, 1, 2, 'Large ad', 1000,1000);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (10, 1, 5, 'Small pub.', 500,500);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (11, 1, 5, 'Large pub.', 1000,1000);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (12, 1, 6, 'Small web', 500,500);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (13, 1, 6, 'Large web', 1000,1000);
-- SQLSERVER SET IDENTITY_INSERT UsageTypeFormat OFF;


-- Example set of Descriptive categories --

-- SQLSERVER SET IDENTITY_INSERT CM_Category ON;
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (2,1,'Art',1,2,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (3,1,'Concepts',1,3,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (4,1,'Diagrams',1,4,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (5,1,'Documents',1,5,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (6,1,'Events',1,6,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (7,1,'Graphics',1,7,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (8,1,'Logos',1,8,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (9,1,'People',1,9,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (10,1,'Publications',1,10,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (11,1,'Screenshots',1,11,0);
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted) VALUES (12,1,'Video clips',1,12,0);
-- SQLSERVER SET IDENTITY_INSERT CM_Category OFF;



-- Lists reference data - content items are in refdata-content.sql

-- SQLSERVER SET IDENTITY_INSERT ListItemTextType ON;
INSERT INTO ListItemTextType (Id,Name) VALUES (1,'Html');
INSERT INTO ListItemTextType (Id,Name) VALUES (2,'Plain - Short');
INSERT INTO ListItemTextType (Id,Name) VALUES (3,'Plain - Long');
-- SQLSERVER SET IDENTITY_INSERT ListItemTextType OFF;

-- Content lists - note that 4, 5, 6 are used by the ecommerce script
-- SQLSERVER SET IDENTITY_INSERT List ON;
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (1, 'copy', 'Page Copy', 'Copy for application pages', 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (2, 'help', 'Help Copy', 'Copy for the help pages', 1);
-- Currently normal (i.e. non ecommerce) email copy is never used.  Ecommerce email copy list is added in refdata_ecommerce_upodate.sql
-- INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (3, 'email', 'E-Mail Copy', 'Copy for system e-mails',1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (7, 'menu', 'Menu Copy', 'Copy for menu items',1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (8, 'terms', 'Common Terms', 'Common terms such as lightbox and item',1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (9, 'labels', 'Form Labels', 'Labels for form elements',1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (10, 'headings', 'Page Headings', 'Copy for page headings', 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup) VALUES (11, 'buttons', 'Button Values', 'Copy for buttons', 1, 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (12, 'links', 'Links Text', 'Copy for links throughout the application', 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (13, 'snippets', 'Snippets', 'Miscellaneous snippets of copy', 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup) VALUES (14, 'javascript', 'Javascript Messages', 'Copy in javascript pop-ups', 1, 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup) VALUES (15, 'system', 'System Messages', 'Copy for system messages', 1, 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup) VALUES (16, 'titles', 'Broswer Titles', 'Copy for browser titles', 1, 1);
-- SQLSERVER SET IDENTITY_INSERT List OFF;


-- Embedded data value mapping directions
-- SQLSERVER SET IDENTITY_INSERT MappingDirection ON;
INSERT INTO MappingDirection (Id, Name) VALUES (1, 'Upload');
INSERT INTO MappingDirection (Id, Name) VALUES (2, 'Download');
-- SQLSERVER SET IDENTITY_INSERT MappingDirection OFF;


-- Embedded data types
-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataType ON;
INSERT INTO EmbeddedDataType (Id, Name) VALUES (1, 'EXIF');
INSERT INTO EmbeddedDataType (Id, Name) VALUES (2, 'IPTC');
INSERT INTO EmbeddedDataType (Id, Name) VALUES (3, 'XMP');
-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataType OFF;


-- Embedded data values
-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataValue ON;

INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (1, 1, 'Artist', 'Artist');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (2, 1, 'BitsPerSample', 'BitsPerSample');    
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (3, 1, 'Compression', 'Compression');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (4, 1, 'ColorSpace', 'ColorSpace');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (5, 1, 'ComponentsConfiguration', 'ComponentsConfiguration');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (6, 1, 'CompressedBitsPerPixel', 'CompressedBitsPerPixel');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (7, 1, 'Contrast', 'Contrast');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (8, 1, 'Copyright', 'Copyright');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (9, 1, 'CustomRendered', 'CustomRendered');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (10, 1, 'DateTime', 'DateTime');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (11, 1, 'DateTimeDigitized', 'DateTimeDigitized');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (12, 1, 'DateTimeOriginal', 'DateTimeOriginal');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (13, 1, 'DigitalZoomRatio', 'DigitalZoomRatio');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (14, 1, 'ExifVersion', 'ExifVersion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (15, 1, 'ExifImageLength', 'ExifImageLength');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (16, 1, 'ExifImageWidth', 'ExifImageWidth');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (17, 1, 'ExifOffset', 'ExifOffset');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (18, 1, 'ExposureBiasValue', 'ExposureBiasValue');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (19, 1, 'ExposureMode', 'ExposureMode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (20, 1, 'ExposureProgram', 'ExposureProgram');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (21, 1, 'ExposureTime', 'ExposureTime');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (22, 1, 'FileSource', 'FileSource');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (23, 1, 'Flash', 'Flash');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (24, 1, 'FlashpixVersion', 'FlashpixVersion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (25, 1, 'FNumber', 'FNumber');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (26, 1, 'FocalLength', 'FocalLength');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (27, 1, 'GainControl', 'GainControl');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (28, 1, 'JPEGInterchangeFormat', 'JPEGInterchangeFormat');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (29, 1, 'JPEGInterchangeFormatLength', 'JPEGInterchangeFormatLength');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (30, 1, 'ImageDescription', 'ImageDescription');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (31, 1, 'ImageLength', 'ImageLength');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (32, 1, 'ImageWidth', 'ImageWidth');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (33, 1, 'ImageUniqueID', 'ImageUniqueID');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (34, 1, 'InteroperabilityOffset', 'InteroperabilityOffset');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (35, 1, 'InteroperabilityIndex', 'InteroperabilityIndex');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (36, 1, 'InteroperabilityVersion', 'InteroperabilityVersion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (37, 1, 'ISOSpeedRatings', 'ISOSpeedRatings');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (38, 1, 'Orientation', 'Orientation');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (39, 1, 'LightSource', 'LightSource');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (40, 1, 'Make', 'Make');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (41, 1, 'MakerNote', 'MakerNote');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (42, 1, 'MaxApertureValue', 'MaxApertureValue');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (43, 1, 'MeteringMode', 'MeteringMode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (44, 1, 'Model', 'Model');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (45, 1, 'PhotometricInterpretation', 'PhotometricInterpretation');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (46, 1, 'PixelXDimension', 'PixelXDimension');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (47, 1, 'PixelYDimension', 'PixelYDimension');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (48, 1, 'PlanarConfiguration', 'PlanarConfiguration');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (49, 1, 'PrimaryChromaticities', 'PrimaryChromaticities');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (50, 1, 'PrintImageMatching', 'PrintImageMatching');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (51, 1, 'ReferenceBlackWhite', 'ReferenceBlackWhite');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (52, 1, 'RelatedSoundFile', 'RelatedSoundFile');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (53, 1, 'ResolutionUnit', 'ResolutionUnit');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (54, 1, 'RowsPerStrip', 'RowsPerStrip');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (55, 1, 'SamplesPerPixel', 'SamplesPerPixel');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (56, 1, 'Saturation', 'Saturation');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (57, 1, 'SceneCaptureType', 'SceneCaptureType');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (58, 1, 'Sharpness', 'Sharpness');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (59, 1, 'Software', 'Software');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (60, 1, 'StripByteCounts', 'StripByteCounts');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (61, 1, 'StripOffsets', 'StripOffsets');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (62, 1, 'SubSecTime', 'SubSecTime');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (63, 1, 'SubSecTimeDigitized', 'SubSecTimeDigitized');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (64, 1, 'SubSecTimeOriginal', 'SubSecTimeOriginal');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (65, 1, 'Tainted', 'Tainted');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (66, 1, 'TransferFunction', 'TransferFunction');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (67, 1, 'UserComment', 'UserComment');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (68, 1, 'WhitePoint', 'WhitePoint');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (69, 1, 'WhiteBalance', 'WhiteBalance');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (70, 1, 'WinXP-Author', 'WinXP-Author');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (71, 1, 'WinXP-Comments', 'WinXP-Comments');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (72, 1, 'WinXP-Keywords', 'WinXP-Keywords');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (73, 1, 'WinXP-Subject', 'WinXP-Subject');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (74, 1, 'WinXP-Title', 'WinXP-Title');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (75, 1, 'XResolution', 'XResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (76, 1, 'YCbCrCoefficients', 'YCbCrCoefficients');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (77, 1, 'YCbCrSubSampling', 'YCbCrSubSampling');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (78, 1, 'YCbCrPositioning', 'YCbCrPositioning');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (79, 1, 'YResolution', 'YResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (80, 2, 'Object Type Reference', '2:03');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (81, 2, 'Object Name (Title)', '2:05');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (82, 2, 'Edit Status', '2:07');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (83, 2, 'Editorial Update', '2:08');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (84, 2, 'Urgency', '2:10');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (85, 2, 'Subject Reference', '2:12');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (86, 2, 'Category', '2:15');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (87, 2, 'Supplemental Category', '2:20');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (88, 2, 'Fixture Identifier', '2:22');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (89, 2, 'Keywords', '2:25');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (90, 2, 'Content Location Code', '2:26');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (91, 2, 'Content Location Name', '2:27');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (92, 2, 'Release Date', '2:30');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (93, 2, 'Release Time', '2:35');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (94, 2, 'Expiration Date', '2:37');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (95, 2, 'Expiration Time', '2:35');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (96, 2, 'Special Instructions', '2:40');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (97, 2, 'Action Advised', '2:42');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (98, 2, 'Reference Service', '2:45');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (99, 2, 'Reference Date', '2:47');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (100, 2, 'Reference Number', '2:50');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (101, 2, 'Date Created', '2:255');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (102, 2, 'Time Created', '2:60');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (103, 2, 'Digital Creation Date', '2:62');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (104, 2, 'Digital Creation Time', '2:63');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (105, 2, 'Originating Program', '2:65');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (106, 2, 'Program Version', '2:70');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (107, 2, 'Object Cycle', '2:75');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (108, 2, 'By-Line', '2:80');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (109, 2, 'By-Line Title', '2:85');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (110, 2, 'City', '2:90');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (111, 2, 'Sub-Location', '2:92');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (112, 2, 'Province/State', '2:95');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (113, 2, 'Country/Primary Location Code', '2:100');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (114, 2, 'Country/Primary Location Name', '2:101');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (115, 2, 'Original Transmission Reference', '2:103');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (116, 2, 'Headline', '2:105');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (117, 2, 'Credit', '2:110');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (118, 2, 'Source', '2:115');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (119, 2, 'Copyright Notice', '2:116');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (120, 2, 'Contact', '2:118');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (121, 2, 'Caption/Abstract', '2:120');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (122, 2, 'Caption Writer/Editor', '2:122');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (123, 2, 'Rasterized Caption', '2:125');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (124, 2, 'Image Type', '2:130');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (125, 2, 'Image Orientation', '2:131');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (126, 2, 'Language Identifier', '2:135');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (127, 2, 'Audio Type', '2:150');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (128, 2, 'Audio Sampling Rate', '2:151');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (129, 2, 'Audio Sampling Resolution', '2:152');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (130, 2, 'Audio Duration', '2:153');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (131, 2, 'Audio Outcue', '2:154');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (132, 2, 'ObjectData Preview File Format', '2:200');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (133, 2, 'ObjectData Preview File Format Version', '2:201');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (134, 2, 'ObjectData Preview Data', '2:202');

INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (200, 3, 'dc:format', '/*/rdf:RDF/rdf:Description/dc:format|/*/rdf:RDF/rdf:Description/@dc:format');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (201, 3, 'dc:title', '/*/rdf:RDF/rdf:Description/dc:title/rdf:Alt/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (202, 3, 'dc:creator', '/*/rdf:RDF/rdf:Description/dc:creator/rdf:Seq/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (203, 3, 'dc:description', '/*/rdf:RDF/rdf:Description/dc:description/rdf:Alt/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (204, 3, 'dc:subject', '/*/rdf:RDF/rdf:Description/dc:subject/rdf:Bag/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (205, 3, 'dc:rights', '/*/rdf:RDF/rdf:Description/dc:rights/rdf:Alt/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (206, 3, 'xap:CreatorTool', '/*/rdf:RDF/rdf:Description/xap:CreatorTool|/*/rdf:RDF/rdf:Description/@xap:CreatorTool');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (207, 3, 'xap:CreateDate', '/*/rdf:RDF/rdf:Description/xap:CreateDate|/*/rdf:RDF/rdf:Description/@xap:CreateDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (208, 3, 'xap:ModifyDate', '/*/rdf:RDF/rdf:Description/xap:ModifyDate|/*/rdf:RDF/rdf:Description/@xap:ModifyDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (209, 3, 'xap:MetadataDate', '/*/rdf:RDF/rdf:Description/xap:MetadataDate|/*/rdf:RDF/rdf:Description/@xap:MetadataDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (210, 3, 'xapMM:DocumentID', '/*/rdf:RDF/rdf:Description/xapMM:DocumentID|/*/rdf:RDF/rdf:Description/@xapMM:DocumentID');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (211, 3, 'xapMM:InstanceID', '/*/rdf:RDF/rdf:Description/xapMM:InstanceID|/*/rdf:RDF/rdf:Description/@xapMM:InstanceID');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (212, 3, 'photoshop:ColorMode', '/*/rdf:RDF/rdf:Description/photoshop:ColorMode|/*/rdf:RDF/rdf:Description/@photoshop:ColorMode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (213, 3, 'photoshop:ICCProfile', '/*/rdf:RDF/rdf:Description/photoshop:ICCProfile|/*/rdf:RDF/rdf:Description/@photoshop:ICCProfile');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (214, 3, 'photoshop:AuthorsPosition', '/*/rdf:RDF/rdf:Description/photoshop:AuthorsPosition|/*/rdf:RDF/rdf:Description/@photoshop:AuthorsPosition');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (215, 3, 'photoshop:CaptionWriter', '/*/rdf:RDF/rdf:Description/photoshop:CaptionWriter|/*/rdf:RDF/rdf:Description/@photoshop:CaptionWriter');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (216, 3, 'photoshop:Category', '/*/rdf:RDF/rdf:Description/photoshop:Category|/*/rdf:RDF/rdf:Description/@photoshop:Category');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (217, 3, 'photoshop:Headline', '/*/rdf:RDF/rdf:Description/photoshop:Headline|/*/rdf:RDF/rdf:Description/@photoshop:Headline');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (218, 3, 'photoshop:DateCreated', '/*/rdf:RDF/rdf:Description/photoshop:DateCreated|/*/rdf:RDF/rdf:Description/@photoshop:DateCreated');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (219, 3, 'photoshop:City', '/*/rdf:RDF/rdf:Description/photoshop:City|/*/rdf:RDF/rdf:Description/@photoshop:City');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (220, 3, 'photoshop:State', '/*/rdf:RDF/rdf:Description/photoshop:State|/*/rdf:RDF/rdf:Description/@photoshop:State');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (221, 3, 'photoshop:Country', '/*/rdf:RDF/rdf:Description/photoshop:Country|/*/rdf:RDF/rdf:Description/@photoshop:Country');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (222, 3, 'photoshop:TransmissionReference', '/*/rdf:RDF/rdf:Description/photoshop:TransmissionReference|/*/rdf:RDF/rdf:Description/@photoshop:TransmissionReference');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (223, 3, 'photoshop:Instructions', '/*/rdf:RDF/rdf:Description/photoshop:Instructions|/*/rdf:RDF/rdf:Description/@photoshop:Instructions');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (224, 3, 'photoshop:Credit', '/*/rdf:RDF/rdf:Description/photoshop:Credit|/*/rdf:RDF/rdf:Description/@photoshop:Credit');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (225, 3, 'photoshop:Source', '/*/rdf:RDF/rdf:Description/photoshop:Source|/*/rdf:RDF/rdf:Description/@photoshop:Source');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (226, 3, 'photoshop:SupplementalCategories', '/*/rdf:RDF/rdf:Description/photoshop:SupplementalCategories/rdf:Bag/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (227, 3, 'photoshop:History', '/*/rdf:RDF/rdf:Description/photoshop:History|/*/rdf:RDF/rdf:Description/@photoshop:History');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (228, 3, 'xapRights:Marked', '/*/rdf:RDF/rdf:Description/xapRights:Marked|/*/rdf:RDF/rdf:Description/@xapRights:Marked');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (229, 3, 'xapRights:WebStatement', '/*/rdf:RDF/rdf:Description/xapRights:WebStatement|/*/rdf:RDF/rdf:Description/@xapRights:WebStatement');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (230, 3, 'xapRights:UsageTerms', '/*/rdf:RDF/rdf:Description/xapRights:UsageTerms/rdf:Alt/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (231, 3, 'Iptc4xmpCore:IntellectualGenre', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:IntellectualGenre|/*/rdf:RDF/rdf:Description/@Iptc4xmpCore:IntellectualGenre');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (232, 3, 'Iptc4xmpCore:Location', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:Location|/*/rdf:RDF/rdf:Description/@Iptc4xmpCore:Location');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (233, 3, 'Iptc4xmpCore:CountryCode', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CountryCode|/*/rdf:RDF/rdf:Description/@Iptc4xmpCore:CountryCode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (234, 3, 'Iptc4xmpCore:CreatorContactInfo:CiAdrExtadr', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiAdrExtadr|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiAdrExtadr');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (235, 3, 'Iptc4xmpCore:CreatorContactInfo:CiAdrCity', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiAdrCity|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiAdrCity');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (236, 3, 'Iptc4xmpCore:CreatorContactInfo:CiAdrRegion', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiAdrRegion|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiAdrRegion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (237, 3, 'Iptc4xmpCore:CreatorContactInfo:CiAdrPcode', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiAdrPcode|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiAdrPcode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (238, 3, 'Iptc4xmpCore:CreatorContactInfo:CiAdrCtry', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiAdrCtry|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiAdrCtry');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (239, 3, 'Iptc4xmpCore:CreatorContactInfo:CiTelWork', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiTelWork|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiTelWork');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (240, 3, 'Iptc4xmpCore:CreatorContactInfo:CiEmailWork', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiEmailWork|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiEmailWork');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (241, 3, 'Iptc4xmpCore:CreatorContactInfo:CiUrlWork', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/Iptc4xmpCore:CiUrlWork|/*/rdf:RDF/rdf:Description/Iptc4xmpCore:CreatorContactInfo/@Iptc4xmpCore:CiUrlWork');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (242, 3, 'Iptc4xmpCore:SubjectCode', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:SubjectCode/rdf:Bag/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (243, 3, 'Iptc4xmpCore:Scene', '/*/rdf:RDF/rdf:Description/Iptc4xmpCore:Scene/rdf:Bag/rdf:li');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (244, 3, 'tiff:Orientation', '/*/rdf:RDF/rdf:Description/tiff:Orientation|/*/rdf:RDF/rdf:Description/@tiff:Orientation');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (245, 3, 'tiff:XResolution', '/*/rdf:RDF/rdf:Description/tiff:XResolution|/*/rdf:RDF/rdf:Description/@tiff:XResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (246, 3, 'tiff:YResolution', '/*/rdf:RDF/rdf:Description/tiff:YResolution|/*/rdf:RDF/rdf:Description/@tiff:YResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (247, 3, 'tiff:ResolutionUnit', '/*/rdf:RDF/rdf:Description/tiff:ResolutionUnit|/*/rdf:RDF/rdf:Description/@tiff:ResolutionUnit');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (248, 3, 'tiff:NativeDigest', '/*/rdf:RDF/rdf:Description/tiff:NativeDigest|/*/rdf:RDF/rdf:Description/@tiff:NativeDigest');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (249, 3, 'exif:PixelXDimension', '/*/rdf:RDF/rdf:Description/exif:PixelXDimension|/*/rdf:RDF/rdf:Description/@exif:PixelXDimension');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (250, 3, 'exif:PixelYDimension', '/*/rdf:RDF/rdf:Description/exif:PixelYDimension|/*/rdf:RDF/rdf:Description/@exif:PixelYDimension');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (251, 3, 'exif:ColorSpace', '/*/rdf:RDF/rdf:Description/exif:ColorSpace|/*/rdf:RDF/rdf:Description/@exif:ColorSpace');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (252, 3, 'exif:NativeDigest', '/*/rdf:RDF/rdf:Description/exif:NativeDigest|/*/rdf:RDF/rdf:Description/@exif:NativeDigest');

-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataValue OFF;

-- Setup the one embedded data mapping
INSERT INTO EmbeddedDataMapping (EmbeddedDataValueId, AttributeId, MappingDirectionId) VALUES (12, 7, 1);

-- SQLSERVER SET IDENTITY_INSERT EmailTemplateType ON;
INSERT INTO EmailTemplateType (Id,Name) VALUES (1,'General');
INSERT INTO EmailTemplateType (Id,Name) VALUES (2,'Marketing');
-- SQLSERVER SET IDENTITY_INSERT EmailTemplateType OFF;

-- Email Templates
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert approvals','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested approval on assets','#name# has requested approval to download a set of images.<br><br>Their email address: #email#<br><br>Please log in to Asset Bank and goto the Approve Images section to see the request and approve the images.<br><br>IDs of assets requested: #assetids#','admin_alert_approvals');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alertcommercial order submitted','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested approval for commercial use.','#greetname#(#username#) has placed an order (#ordernum#) requesting approval for commercial use of the following assets:<br><br>#orderDetails#<br><br>The following usage notes were provided by the user.<br>#UserNotes#<br><br>Please log in to Asset Bank and go to the Orders -> Commercial Orders section to review and approve the order.<br><br>#OfflinePaymentRequest#','admin_alert_commercial_order_submitted');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert personal print order submitted','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested prints.','#greetname#(#username#) has placed an order (#ordernum#) requesting prints of the following assets:<br><br>#orderDetails#<br><br>Please log in to Asset Bank and go to the Orders -> Personal Orders section to review  the order.<br>When the order has been shipped you can change the status to &#39;Fulfilled&#39;.','admin_alert_personal_print_order_submitted');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert update request','noreply@noreply.com','#adminEmailAddresses#;','','','User has requested permissions','This is an automated message generated by Asset Bank.<br><br>A user has requested you to update their permissions.<br><br>Requested Org Unit: #orgunitname#<br><br>User notes: <br>#usernotes#<br><br>You can see the details of this request, in the &#39;Admin > Approve Users&#39; area.','admin_alert_update_request');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert upload approvals','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: assets require approval','#name# has uploaded or updated assets. The new or updated assets require approval.<br><br>Please log in to Asset Bank andgo to the Approve Assets section to approve the changes','admin_alert_upload_approvals');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'ad user approved','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','Dear #name#<br><br>You have been approved as a user of the Asset Bank. <br>Your login details are the same as you use to log in to your Windows PC.<br><br>Kind regards,<br>Asset Bank Admin','ad_user_approved');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'approver work done','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User asset request has been approved','Download approvals for user #name# have been processed, for the following assets:<br>#assetids#','approver_work_done');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'assets approved','noreply@noreply.com','#email#;','','','Download request processed','Dear #name#<br><br>Your request to download one or more assets has been processed.<br><br>Please log in to the Asset Bank and go to &#39;My Folder&#39; for details.<br><br>Kind regards,<br>Asset Bank Admin','assets_approved');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'asset emailed (attached)','noreply@noreply.com','#recipients#;','','','Asset Bank: #filename#','This message was sent to you from #username# via Asset Bank.<br><br>#message#','asset_attached');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'asset emailed (linked)','#from#','#recipients#;','','','Asset Bank: #filename#','This message was sent to you from #username# via Asset Bank.<br><br>#message#<br><br>Click the link below to download:<br><br>#url#','asset_linked');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'attribute rule email','noreply@noreply.com','#recipients#;','','','Asset Bank Automatic Email','Dear Asset Bank user<br><br>This email has been automatically sent to you by Asset Bank because a date specified on one or more assets has been reached.<br><br>The asset attribute is: #rule_attribute#.<br>The rule that has triggered is: #rule_name#.<br>Message:<br>#rule_message#<br><br>Asset IDs:<br>#asset_list#<br><br>Regards,<br>Asset Bank Admin','attribute_rule_email');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'commercial option changed','noreply@noreply.com','#email#;','','','Assetbank: alteration to request for commercial use of image/s','Dear #username#<br><br>IMPORTANT INFORMATION ABOUT YOUR REQUEST FOR COMMERCIAL USE OF IMAGE/S<br><br>Your order number: #ordernum#<br>#assetDetails#<br><br>Having assessed your request, and from the additional information you have provided us, we have felt it necessary to change the commercial option you had initially selected from &#39;#oldOption#&#39;  to &#39;#newOption#&#39;. This has resulted in a change of pricefor the purchase of the image/s.<br><br>You can view the change of use by logging into Assetbank, and proceed to the MY PURCHASES - MY ORDERS section. <br><br>If you do not agree with our assessment of your intended use of the image/s, please contact us and we will be happy to discuss it further with you.<br><br>YOUR LOGIN DETAILS<br><br>Your username: #username#<br>Your password: #password#<br><br>Thank you for using Assetbank.co.uk.<br><br>Regards,<br>assetbank.com, admin','commercial_option_changed');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'contact','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank Contact','Name: #name#<br>Email: #email#<br>Tel no: #telephone#<br><br>Message:<br>#message#','contact');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'new watched files','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: New files uploaded','New files have been added to the the following directories: #directories#','new_watched_files');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'order approved pay offline','noreply@noreply.com','#email#;','','','Commercial purchase approved.  Offline Payment required.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your commercial purchase order (#ordernum#) placed on #datePlaced# has been approved and now requires off line payment.<br><br>You will be contacted shortly by a member of our admin team to complete payment.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and proceed to the &#39;My Purchases-> My Orders&#39; section to view your approved order.<br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>Your order details, commercial options approved and terms and conditions are <br><br>#orderDetails#<br><br>#terms# <br><br>Thank you for purchasing images from AssetBank.<br><br>Regards,<br>assetbank.com, admin','order_approved_pay_offline');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'orderapproved pay online','noreply@noreply.com','#email#;','','','Commercial purchase approved.  Online Payment required.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your commercial purchase order (#ordernum#) placed on #datePlaced# has been approved and now requires on line payment.<br><br>Please login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and proceed to the &#39;My Purchases-> My Orders&#39; section to view the order and complete payment.<br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>Your order details and commercial options approved are <br><br>#orderDetails#<br><br>#terms# <br><br>Thank you for purchasing images from AssetBank.<br><br>Regards,<br>assetbank.com, admin','order_approved_pay_online');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'order declined','noreply@noreply.com','#email#;','','','Commercial purchase declined.','Dear #greetname# (#username#)<br><br>Your commercial purchase order (#ordernum#) placed on #datePlaced# has been declined.<br><br>You may be contacted shortly by a member ofour admin team to discuss the issue with your request.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and go to the &#39;My Purchases-> My Orders&#39; section to view the declined order.<br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>Your declined order details, commercial options and terms and conditions are <br><br>#orderDetails#<br><br>#terms# <br><br>Regards,<br>assetbank.com, admin','order_declined');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'order shipped confirmation','noreply@noreply.com','#email#;','','','Your order has been shipped.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your order (#ordernum#) placed on #datePlaced# has been shipped to<br><br>#address#<br><br>Your order details are<br><br>#orderDetails#<br><br>Thank you for purchasing images from Asset Bank.<br><br>This is a test email from Asset Bank. The text of allemails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin','order_shipped_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'password change','noreply@noreply.com','#email#;','','','Your Asset Bank password has been reset','Dear #name#<br><br>Your password for the Asset Bank application has beenchanged. <br>When you next log in, please use password: #newPassword#<br><br>Please note that your username (#username#) has not been changed.<br><br>Kind regards,<br>Asset Bank Admin','password_change');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'password reminder','noreply@noreply.com','#email#;','','','Your Asset Bank password reminder','Dear #name#<br><br>Here are your login details for Asset Bank.<br><br>Your username: #username#<br>Your password: #password#<br><br>Regards,<br>Asset Bank Admin','password_reminder');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'purchase confirmation','noreply@noreply.com','#email#;','','','Asset Bank Payment Confirmation','Dear #greetname# (#username#)<br><br>This email confirms your purchase of assets from Asset Bank.<br>Order/Purchase ID: #ordernum#  <br><br>Your order/purchase details are<br>#orderDetails#<br><br>#purchaseCommercialReceiptNote#<br><br>#purchaseDownloadInstructions#<br>#purchasePrintInstructions#<br><br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>To view the terms and conditions of your purchase go to #purchaseTermsLink#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>AssetBank Admin','purchase_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'requestAssetbox','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: Items requested on CD','-- Contact details --<br>Name: #name#<br>Division: #division#<br>Email: #email#<br>Tel no: #telephone#<br>Address:<br>#address#<br>Additional comments:<br>#message#<br><br>--Requested items--<br>The user has requested the following assets on CD: <br>Assets the user has permission to download: #downloadableIds#','requestAssetbox');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'subscription confirmation','noreply@noreply.com','#email#;','','','Asset Bank Subscription Confirmation','Dear #username#<br><br>This email confirms that you purchased the following subscription on Asset Bank.<br><br>Description: #description#<br>Amount: #amount#.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/) to download images. <br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin','subscription_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'subscription expiry alert','noreply@noreply.com','#email#;','','','Subscription expiry alert','Dear #name#<br><br>The following subscription to Asset Bank will expire in #days_left# day(s): <br>#description#<br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br><br>Kind regards,<br>Asset Bank Admin','subscription_expiry_alert');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user added by admin','noreply@noreply.com','#email#;','','','You are now an Asset Bank user!','Dear #name#<br><br>You have been added as a user of Asset Bank. <br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br>You can change your password after logging in.<br><br>Kindregards,<br>Asset Bank Admin','user_added_by_admin');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user approved','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','Dear #name#<br><br>You have been approved as a user of Asset Bank. <br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br><br>You can change your password after logging in: click &#39;Your Profile&#39; in the top right of the screen, then click &#39;change your password&#39;.<br><br>#adminMessage#<br><br>Kind regards,<br>Asset Bank Admin','user_approved');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user message','noreply@noreply.com','#email#','','','#subject#','#body#','user_message');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user registered','noreply@noreply.com','#adminEmailAddresses#;','','','New user registration','This is an automated message generated by Asset Bank.<br><br>A user has registered to use the Asset Bank.<br><br>#orgunitname# <br><br>You can see their details, and approve or reject their application, in the &#39;Admin > Approve Users&#39; area.','user_registered');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user rejected','noreply@noreply.com','#email#;','','','Your Asset Bank registration has not been approved','Dear #name#<br><br>Sorry, your application to become a user of Asset Bank has not been approved.<br><br>#adminMessage#<br><br>Kind regards,<br>Asset Bank Admin','user_rejected');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user updated','noreply@noreply.com','#email#;','','','Your Asset Bank permissions have been updated','Dear #name#<br><br>You recently requested your Asset Bank permissions to be updated. <br><br>The administrator has now updated your permissions.<br><br>Message: #adminMessage#<br><br>Kind regards,<br>Asset Bank Admin','user_updated');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'send report','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Automated Report Generated: #reportdescription#','The report: #reportdescription#, has been generated automatically from Asset Bank.<br><br>Please find the report attached to this email.<br><br>To disable report generation please login to the admin area on asset-bank.<br><br>Kind regards,<br>Asset Bank Admin','send_report');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'send report no data','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Automated Report Generated','A report has been generated automatically from Asset Bank.<br><br>There was no data available for the requested report.<br><br>To disable report generation please login to the admin area on asset-bank.<br><br>Kind regards,<br>Asset Bank Admin','send_report_no_data');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'shared assetbox','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: You''ve been granted access to a shared #lightboxname#','Dear #greetname# (#username#)<br><br>You have been granted access to a shared #lightboxname#.<br><br>To view the #lightboxname# click the link below:<br><br>#url#<br>#message#<br>Kind regards,<br>Asset Bank Admin','shared_assetbox');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user details updated by admin','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Your details have been updated','Dear #greetname# (#username#)<br><br>Your user details have been updated by an admin user.<br><br>#message#<br><br>Kind regards,<br>Asset Bank Admin','user_updated_admin');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user registered admin','noreply@noreply.com','#adminEmailAddresses#','','','An Asset Bank user has registered','The following Asset Bank user has just registered:<br/><br/>Id: #id#<br/>Name: #name#<br/>Username: #username#<br/>Password: #password#<br/>Email: #email#<br/><br/>','user_registered_admin');

-- SQLSERVER SET IDENTITY_INSERT CustomFieldUsageType ON;
INSERT INTO CustomFieldUsageType (Id, Description) VALUES (1, 'Asset Bank User Field');
-- SQLSERVER SET IDENTITY_INSERT CustomFieldUsageType OFF;


-- SQLSERVER SET IDENTITY_INSERT CustomFieldType ON;
INSERT INTO CustomFieldType (Id, Description) VALUES (1, 'Textfield');
INSERT INTO CustomFieldType (Id, Description) VALUES (2, 'Textarea');
INSERT INTO CustomFieldType (Id, Description) VALUES (3, 'Dropdown list');
INSERT INTO CustomFieldType (Id, Description) VALUES (4, 'Checkbox list');
-- SQLSERVER SET IDENTITY_INSERT CustomFieldType OFF;

-- SQLSERVER GO
