-- Reference data for the standard Asset bank --

-- You should update refdata-video.sql, but DO NOT manually update refdata-video-sqlserver.sql 
-- To generate refdata-video-sqlserver.sql, run the ant target db-sqlserver-refdata-video (replaces "-- SQLSERVER " with "").

-- Run this script if you want Asset Bank to support Video. Note: see ApplicationSetting.properties for details of the command-line converter that is needed.

-- SQLSERVER SET IDENTITY_INSERT FileFormat ON;
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

-- SQLSERVER SET IDENTITY_INSERT FileFormat OFF;

-- SQLSERVER GO