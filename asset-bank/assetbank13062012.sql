-- MySQL dump 10.11
--
-- Host: localhost    Database: assetbank
-- ------------------------------------------------------
-- Server version	5.0.37-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aborder`
--

DROP TABLE IF EXISTS `aborder`;
CREATE TABLE `aborder` (
  `Id` int(11) NOT NULL auto_increment,
  `UserId` bigint(20) NOT NULL,
  `DatePlaced` datetime default NULL,
  `SubTotal` int(11) default NULL,
  `VATPercent` float(9,3) default NULL,
  `Total` int(11) default NULL,
  `PSPTransId` varchar(255) default NULL,
  `PurchaseId` varchar(255) default NULL,
  `OrderStatusId` bigint(20) NOT NULL,
  `ShippingAddressId` bigint(20) default NULL,
  `RecipientName` varchar(255) default NULL,
  `DateFulfilled` datetime default NULL,
  `UserNotes` mediumtext,
  `PrefersOfflinePayment` tinyint(1) NOT NULL default '0',
  `ShippingCost` int(11) default NULL,
  `BasketCost` int(11) default NULL,
  `DiscountPercentage` int(11) default NULL,
  `PSPUserId` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship78` (`UserId`),
  KEY `IX_Relationship139` (`ShippingAddressId`),
  KEY `IX_Relationship138` (`OrderStatusId`),
  CONSTRAINT `Relationship138` FOREIGN KEY (`OrderStatusId`) REFERENCES `orderstatus` (`Id`),
  CONSTRAINT `Relationship139` FOREIGN KEY (`ShippingAddressId`) REFERENCES `address` (`Id`),
  CONSTRAINT `Relationship78` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `aborder`
--

LOCK TABLES `aborder` WRITE;
/*!40000 ALTER TABLE `aborder` DISABLE KEYS */;
/*!40000 ALTER TABLE `aborder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actiononasset`
--

DROP TABLE IF EXISTS `actiononasset`;
CREATE TABLE `actiononasset` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  `Description` varchar(255) default NULL,
  `ActionClass` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `actiononasset`
--

LOCK TABLES `actiononasset` WRITE;
/*!40000 ALTER TABLE `actiononasset` DISABLE KEYS */;
INSERT INTO `actiononasset` VALUES (1,'Restrict Asset','Restricts an asset, meaning that it cannot be downloaded by non-admin users and may have it\'s preview hidden.','com.bright.assetbank.actiononasset.action.RestrictAssetAction'),(2,'Unrestrict Asset','Unrestricts a restricted asset, which will make it visible to non-admin users (depending on other permissions).','com.bright.assetbank.actiononasset.action.UnrestrictAssetAction'),(3,'Removed Repurposed Images','Removes any repurposed (embeddable) images for an asset.','com.bright.assetbank.actiononasset.action.RemoveRepurposedImagesAction'),(4,'Mark as Sensitive','Marks an asset as sensitive.','com.bright.assetbank.actiononasset.action.MakeAssetSensitiveAction'),(5,'Clear Sensitive Status','Clears the sensitite status for an asset.','com.bright.assetbank.actiononasset.action.MakeAssetInsensitiveAction'),(6,'Allow Feedback On Asset','Allows the user to submit feedback for an asset.','com.bright.assetbank.actiononasset.action.AllowFeedbackOnAssetAction'),(7,'Do not Allow Feedback On Asset','Does not allow the user to submit feedback for an asset.','com.bright.assetbank.actiononasset.action.DoNotAllowFeedbackOnAssetAction');
/*!40000 ALTER TABLE `actiononasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `Id` bigint(20) NOT NULL auto_increment,
  `CountryId` bigint(20) default NULL,
  `Address1` varchar(200) default NULL,
  `Address2` varchar(200) default NULL,
  `Town` varchar(100) default NULL,
  `County` varchar(100) default NULL,
  `Postcode` varchar(50) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship109` (`CountryId`),
  CONSTRAINT `Relationship109` FOREIGN KEY (`CountryId`) REFERENCES `country` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agreement`
--

DROP TABLE IF EXISTS `agreement`;
CREATE TABLE `agreement` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Title` varchar(255) default NULL,
  `Body` mediumtext,
  `Expiry` datetime default NULL,
  `AvailableToAll` tinyint(1) default NULL,
  `SharedWithOU` tinyint(1) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `agreement`
--

LOCK TABLES `agreement` WRITE;
/*!40000 ALTER TABLE `agreement` DISABLE KEYS */;
/*!40000 ALTER TABLE `agreement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allowableentityassettype`
--

DROP TABLE IF EXISTS `allowableentityassettype`;
CREATE TABLE `allowableentityassettype` (
  `AssetEntityId` bigint(20) NOT NULL,
  `AssetTypeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`AssetEntityId`,`AssetTypeId`),
  KEY `IX_Relationship254` (`AssetTypeId`),
  KEY `IX_Relationship23822` (`AssetEntityId`),
  CONSTRAINT `Relationship23822` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `Relationship254` FOREIGN KEY (`AssetTypeId`) REFERENCES `assettype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `allowableentityassettype`
--

LOCK TABLES `allowableentityassettype` WRITE;
/*!40000 ALTER TABLE `allowableentityassettype` DISABLE KEYS */;
/*!40000 ALTER TABLE `allowableentityassettype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allowableentityrelationship`
--

DROP TABLE IF EXISTS `allowableentityrelationship`;
CREATE TABLE `allowableentityrelationship` (
  `AssetEntityId` bigint(20) NOT NULL,
  `RelatesToAssetEntityId` bigint(20) NOT NULL,
  `DefaultRelationshipCategoryId` bigint(20) default NULL,
  `RelationshipTypeId` bigint(20) NOT NULL,
  `RelationshipDescriptionLabel` varchar(255) default NULL,
  PRIMARY KEY  (`AssetEntityId`,`RelatesToAssetEntityId`,`RelationshipTypeId`),
  KEY `IX_CAT_AER_FK` (`DefaultRelationshipCategoryId`),
  KEY `IX_AssetEntity_AER_2_FK` (`AssetEntityId`),
  KEY `IX_AssetEntity_AER_FK` (`RelatesToAssetEntityId`),
  KEY `IX_RelationshipType_AER_FK` (`RelationshipTypeId`),
  CONSTRAINT `AssetEntity_AER_2_FK` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `AssetEntity_AER_FK` FOREIGN KEY (`RelatesToAssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `CAT_AER_FK` FOREIGN KEY (`DefaultRelationshipCategoryId`) REFERENCES `cm_category` (`Id`),
  CONSTRAINT `RelationshipType_AER_FK` FOREIGN KEY (`RelationshipTypeId`) REFERENCES `relationshiptype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `allowableentityrelationship`
--

LOCK TABLES `allowableentityrelationship` WRITE;
/*!40000 ALTER TABLE `allowableentityrelationship` DISABLE KEYS */;
/*!40000 ALTER TABLE `allowableentityrelationship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `approvalstatus`
--

DROP TABLE IF EXISTS `approvalstatus`;
CREATE TABLE `approvalstatus` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `approvalstatus`
--

LOCK TABLES `approvalstatus` WRITE;
/*!40000 ALTER TABLE `approvalstatus` DISABLE KEYS */;
INSERT INTO `approvalstatus` VALUES (1,'Pending Approval'),(2,'Approved'),(3,'Rejected'),(4,'Approved for high res');
/*!40000 ALTER TABLE `approvalstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset` (
  `Id` bigint(20) NOT NULL auto_increment,
  `CurrentVersionId` bigint(20) default NULL,
  `AssetTypeId` bigint(20) default NULL,
  `AddedByUserId` bigint(20) default NULL,
  `LastModifiedByUserId` bigint(20) default NULL,
  `FileFormatId` bigint(20) default NULL,
  `FileLocation` varchar(255) default NULL,
  `OriginalFileLocation` varchar(255) default NULL,
  `FileSizeInBytes` bigint(20) NOT NULL,
  `SmallFileLocation` varchar(255) default NULL,
  `ThumbnailFileLocation` varchar(255) default NULL,
  `MediumFileLocation` varchar(255) default NULL,
  `EmbeddedPreviewClipLocation` varchar(255) default NULL,
  `DateAdded` datetime default NULL,
  `DateLastModified` datetime default NULL,
  `ExpiryDate` date default NULL,
  `Author` varchar(255) default NULL,
  `Code` varchar(255) default NULL,
  `Price` int(11) default NULL,
  `ImportedAssetId` varchar(255) default NULL,
  `Synchronised` tinyint(1) NOT NULL default '0',
  `IsPreviewRestricted` tinyint(1) NOT NULL default '0',
  `BulkUploadTimestamp` datetime default NULL,
  `VersionNumber` int(11) NOT NULL default '1',
  `NumViews` int(11) NOT NULL default '0',
  `NumDownloads` int(11) NOT NULL default '0',
  `LastDownloaded` datetime default NULL,
  `AssetEntityId` bigint(20) default NULL,
  `OriginalFilename` varchar(255) default NULL,
  `IsSensitive` tinyint(1) NOT NULL default '0',
  `AgreementTypeId` int(11) default NULL,
  `HasSubstituteFile` tinyint(1) NOT NULL default '0',
  `IsUnsubmitted` tinyint(1) NOT NULL default '0',
  `IsBrandTemplate` tinyint(1) NOT NULL default '0',
  `CanEmbedFile` tinyint(1) NOT NULL default '0',
  `ThumbnailGenerationFailed` tinyint(1) NOT NULL default '0',
  `IsNotDuplicate` tinyint(1) NOT NULL default '0',
  `AdvancedViewing` tinyint(1) NOT NULL default '0',
  `FileCheckedOutByUserId` bigint(20) default NULL,
  `FileCheckOutTime` datetime default NULL,
  `SubmittedByUserId` bigint(20) default NULL,
  `AllowFeedback` tinyint(1) NOT NULL default '0',
  `DateSubmitted` datetime default NULL,
  `HasNeverBeenApproved` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `ImportedAssetId_Index` (`ImportedAssetId`),
  KEY `IX_Relationship219` (`CurrentVersionId`),
  KEY `IX_Asset_User_Added_FK` (`AddedByUserId`),
  KEY `IX_Asset_User_Modified_FK` (`LastModifiedByUserId`),
  KEY `IX_FileCheckedOutByUser_FK` (`FileCheckedOutByUserId`),
  KEY `Asset_User_Submitted_FK` (`SubmittedByUserId`),
  KEY `IX_Relationship27` (`AssetTypeId`),
  KEY `IX_Relationship60` (`FileFormatId`),
  KEY `IX_Relationship256` (`AssetEntityId`),
  CONSTRAINT `Asset_User_Added_FK` FOREIGN KEY (`AddedByUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Asset_User_Modified_FK` FOREIGN KEY (`LastModifiedByUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Asset_User_Submitted_FK` FOREIGN KEY (`SubmittedByUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `FileCheckedOutByUser_FK` FOREIGN KEY (`FileCheckedOutByUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship219` FOREIGN KEY (`CurrentVersionId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship256` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `Relationship27` FOREIGN KEY (`AssetTypeId`) REFERENCES `assettype` (`Id`),
  CONSTRAINT `Relationship60` FOREIGN KEY (`FileFormatId`) REFERENCES `fileformat` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (6,NULL,1,1,1,9998,'2:0/153783.jpg','',3029567,'3:0/153783-t.jpg','3:0/153783-s.jpg','3:0/153783-m.jpg','','2012-06-10 15:47:33','2012-06-10 15:48:03',NULL,NULL,NULL,0,'15378',0,0,NULL,1,6,0,NULL,NULL,'15378.jpg',0,NULL,0,0,0,0,0,0,0,NULL,NULL,NULL,0,NULL,0),(13,NULL,1,1,1,9999,'2:0/ds1.flv','',3255227,'3:0/153781-t.jpg','3:0/153781-s.jpg','3:0/153781-m.jpg','','2012-06-12 15:39:48','2012-06-12 15:43:29',NULL,NULL,NULL,0,NULL,0,0,NULL,1,5,0,NULL,NULL,'ds.flv',0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,NULL,0),(14,NULL,1,1,1,9999,'3:0/dstotest.png','2:0/dstotest.flv',3255227,'3:0/dstotest-t.jpg','3:0/dstotest-s.jpg','3:0/dstotest-m.jpg','','2012-06-12 16:09:58','2012-06-12 16:10:21',NULL,NULL,NULL,0,'dstotest',0,0,NULL,1,4,0,NULL,NULL,'dstotest.flv',0,NULL,0,0,0,0,0,0,0,NULL,NULL,NULL,0,NULL,0),(15,NULL,3,1,1,309,'3:1/sh1.png','2:1/sh1.flv',126646,'3:1/sh1-l-t.jpg','3:1/sh1-l-s.jpg','3:1/sh1-l-m.jpg','3:1/sh1-p.flv','2012-06-12 16:26:20','2012-06-12 16:26:58',NULL,NULL,NULL,0,'sh1',0,0,NULL,1,2,0,NULL,NULL,'sh1.flv',0,NULL,0,0,0,0,0,0,0,NULL,NULL,NULL,0,NULL,0),(16,NULL,1,1,1,9999,'3:0/dstotest1.png','2:0/dstotest1.flv',3255227,'3:0/dstotest1-t.jpg','3:0/dstotest1-s.jpg','3:0/dstotest1-m.jpg','','2012-06-12 16:54:53','2012-06-12 16:56:03',NULL,NULL,NULL,0,'dstotest',0,0,NULL,1,1,0,NULL,NULL,'dstotest.flv',0,NULL,0,0,0,0,0,0,0,NULL,NULL,NULL,0,NULL,0);
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetagreement`
--

DROP TABLE IF EXISTS `assetagreement`;
CREATE TABLE `assetagreement` (
  `AgreementId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `DateActivated` datetime default NULL,
  `IsCurrent` tinyint(1) default NULL,
  KEY `IX_Relationship273` (`AssetId`),
  KEY `IX_Relationship272` (`AgreementId`),
  CONSTRAINT `Relationship272` FOREIGN KEY (`AgreementId`) REFERENCES `agreement` (`Id`),
  CONSTRAINT `Relationship273` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetagreement`
--

LOCK TABLES `assetagreement` WRITE;
/*!40000 ALTER TABLE `assetagreement` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetagreement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetapproval`
--

DROP TABLE IF EXISTS `assetapproval`;
CREATE TABLE `assetapproval` (
  `AssetId` bigint(20) NOT NULL,
  `UsageTypeId` bigint(20) default NULL,
  `UserId` bigint(20) NOT NULL,
  `ApprovalStatusId` bigint(20) NOT NULL,
  `DateSubmitted` datetime default NULL,
  `DateApproved` datetime default NULL,
  `DateExpires` datetime default NULL,
  `AdminNotes` mediumtext,
  `UserNotes` mediumtext,
  `IsHighResApproval` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`AssetId`,`UserId`),
  KEY `IX_Relationship66` (`AssetId`),
  KEY `IX_Relationship67` (`UserId`),
  KEY `IX_Relationship70` (`UsageTypeId`),
  KEY `IX_Relationship68` (`ApprovalStatusId`),
  CONSTRAINT `Relationship66` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship67` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship68` FOREIGN KEY (`ApprovalStatusId`) REFERENCES `approvalstatus` (`Id`),
  CONSTRAINT `Relationship70` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetapproval`
--

LOCK TABLES `assetapproval` WRITE;
/*!40000 ALTER TABLE `assetapproval` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetapproval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetattributevalues`
--

DROP TABLE IF EXISTS `assetattributevalues`;
CREATE TABLE `assetattributevalues` (
  `AssetId` bigint(20) NOT NULL,
  `title` mediumtext,
  `description` mediumtext,
  `keywords` mediumtext,
  `datecreated` datetime default NULL,
  `activationdate` date default NULL,
  `expirydate` date default NULL,
  `sensitivitynotes` mediumtext,
  PRIMARY KEY  (`AssetId`),
  CONSTRAINT `AAV_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetattributevalues`
--

LOCK TABLES `assetattributevalues` WRITE;
/*!40000 ALTER TABLE `assetattributevalues` DISABLE KEYS */;
INSERT INTO `assetattributevalues` VALUES (6,'test6','test6','',NULL,NULL,NULL,NULL),(13,'test2','test2','',NULL,NULL,NULL,NULL),(14,'dstotest.flv','dstotest.flv','',NULL,NULL,NULL,NULL),(15,'sh1','sh1','',NULL,NULL,NULL,NULL),(16,'test for filepath','test for filepath','',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `assetattributevalues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetbankuser`
--

DROP TABLE IF EXISTS `assetbankuser`;
CREATE TABLE `assetbankuser` (
  `Id` bigint(20) NOT NULL auto_increment,
  `LastModifiedById` bigint(20) default NULL,
  `DateLastModified` datetime default NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) default NULL,
  `EmailAddress` varchar(255) default NULL,
  `Title` varchar(100) default NULL,
  `Forename` varchar(100) default NULL,
  `Surname` varchar(100) default NULL,
  `Organisation` varchar(255) default NULL,
  `Address` text,
  `RegistrationInfo` mediumtext,
  `IsDeleted` tinyint(1) NOT NULL default '0',
  `IsAdmin` tinyint(1) NOT NULL default '0',
  `IsSuspended` tinyint(1) default NULL,
  `DisplayName` varchar(255) default NULL,
  `CN` varchar(255) default NULL,
  `DistinguishedName` varchar(255) default NULL,
  `Mailbox` varchar(255) default NULL,
  `NotActiveDirectory` tinyint(1) default NULL,
  `Hidden` tinyint(1) default '0',
  `NotApproved` tinyint(1) default '0',
  `RequiresUpdate` tinyint(1) default '0',
  `CanBeCategoryAdmin` tinyint(1) default '0',
  `ExpiryDate` date default NULL,
  `RegisterDate` date default NULL,
  `JobTitle` varchar(255) default NULL,
  `RequestedOrgUnitId` bigint(20) default NULL,
  `DivisionId` bigint(20) default NULL,
  `AddressId` bigint(20) default NULL,
  `VATNumber` varchar(100) default NULL,
  `LDAPServerIndex` smallint(6) default NULL,
  `ReceiveAlerts` tinyint(1) default NULL,
  `CanLoginFromCms` tinyint(1) default NULL,
  `DateChangedPassword` datetime default NULL,
  `LanguageId` bigint(20) NOT NULL default '1',
  `AdminNotes` mediumtext,
  `InvitedByUserId` bigint(20) default NULL,
  `AdditionalApprovalName` varchar(255) default NULL,
  `SearchResultsPerPage` int(11) default NULL,
  `DateLastLoggedIn` datetime default NULL,
  `ReactivationPending` tinyint(1) NOT NULL default '0',
  `RequestFulfiller` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship187` (`LastModifiedById`),
  KEY `IX_Relationship285` (`InvitedByUserId`),
  KEY `IX_Relationship101` (`RequestedOrgUnitId`),
  KEY `IX_Relationship103` (`DivisionId`),
  KEY `IX_Relationship110` (`AddressId`),
  KEY `IX_Relationship180` (`LanguageId`),
  CONSTRAINT `Relationship101` FOREIGN KEY (`RequestedOrgUnitId`) REFERENCES `orgunit` (`Id`),
  CONSTRAINT `Relationship103` FOREIGN KEY (`DivisionId`) REFERENCES `division` (`Id`),
  CONSTRAINT `Relationship110` FOREIGN KEY (`AddressId`) REFERENCES `address` (`Id`),
  CONSTRAINT `Relationship180` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `Relationship187` FOREIGN KEY (`LastModifiedById`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship285` FOREIGN KEY (`InvitedByUserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetbankuser`
--

LOCK TABLES `assetbankuser` WRITE;
/*!40000 ALTER TABLE `assetbankuser` DISABLE KEYS */;
INSERT INTO `assetbankuser` VALUES (1,NULL,NULL,'admin','4IEBzDoNpwZqgYKYMkJKXW0AdVKrT+RInoUwb3yfPXQv9oYKyAUF0g==','',NULL,'Admin','User',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,1,0,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,1,NULL,NULL,NULL,NULL,'2012-06-12 16:54:20',0,0),(3,NULL,NULL,'application','CZ0CugbrmqA6LHnvQvHHNjbL5CvSi6A+ikfaDSCdmp+Mn0rJhA2xhA==','',NULL,'Application','User',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,1,1,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,0,0);
/*!40000 ALTER TABLE `assetbankuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetbox`
--

DROP TABLE IF EXISTS `assetbox`;
CREATE TABLE `assetbox` (
  `Id` bigint(20) NOT NULL auto_increment,
  `UserId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  `SequenceNumber` int(11) NOT NULL default '1',
  `IsPublic` tinyint(1) NOT NULL default '0',
  `TimeAdded` datetime default NULL,
  `ModifiedDateTime` datetime default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship52` (`UserId`),
  CONSTRAINT `Relationship52` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetbox`
--

LOCK TABLES `assetbox` WRITE;
/*!40000 ALTER TABLE `assetbox` DISABLE KEYS */;
INSERT INTO `assetbox` VALUES (1,1,'My Lightbox',1,0,NULL,NULL);
/*!40000 ALTER TABLE `assetbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetboxasset`
--

DROP TABLE IF EXISTS `assetboxasset`;
CREATE TABLE `assetboxasset` (
  `AssetBoxId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `AddedByUserId` bigint(20) default NULL,
  `TimeAdded` datetime NOT NULL,
  `SequenceNumber` int(11) default NULL,
  PRIMARY KEY  (`AssetBoxId`,`AssetId`),
  KEY `IX_Relationship6` (`AssetId`),
  KEY `IX_Relationship5` (`AssetBoxId`),
  KEY `IX_Relationship164` (`AddedByUserId`),
  CONSTRAINT `Relationship164` FOREIGN KEY (`AddedByUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship5` FOREIGN KEY (`AssetBoxId`) REFERENCES `assetbox` (`Id`),
  CONSTRAINT `Relationship6` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetboxasset`
--

LOCK TABLES `assetboxasset` WRITE;
/*!40000 ALTER TABLE `assetboxasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetboxasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetboxpriceband`
--

DROP TABLE IF EXISTS `assetboxpriceband`;
CREATE TABLE `assetboxpriceband` (
  `AssetBoxId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `PriceBandId` bigint(20) NOT NULL,
  `Quantity` int(11) default '0',
  PRIMARY KEY  (`AssetBoxId`,`AssetId`,`PriceBandId`),
  KEY `IX_Relationship135` (`AssetId`),
  KEY `IX_Relationship134` (`AssetBoxId`),
  KEY `IX_Relationship136` (`PriceBandId`),
  CONSTRAINT `Relationship134` FOREIGN KEY (`AssetBoxId`) REFERENCES `assetbox` (`Id`),
  CONSTRAINT `Relationship135` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship136` FOREIGN KEY (`PriceBandId`) REFERENCES `priceband` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetboxpriceband`
--

LOCK TABLES `assetboxpriceband` WRITE;
/*!40000 ALTER TABLE `assetboxpriceband` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetboxpriceband` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetboxshare`
--

DROP TABLE IF EXISTS `assetboxshare`;
CREATE TABLE `assetboxshare` (
  `AssetBoxId` bigint(20) NOT NULL,
  `UserId` bigint(20) NOT NULL,
  `CanEdit` tinyint(1) NOT NULL default '0',
  `SequenceNumber` int(11) NOT NULL default '0',
  `Alias` varchar(255) default NULL,
  PRIMARY KEY  (`AssetBoxId`,`UserId`),
  KEY `IX_Relationship158` (`AssetBoxId`),
  KEY `IX_Relationship159` (`UserId`),
  CONSTRAINT `Relationship158` FOREIGN KEY (`AssetBoxId`) REFERENCES `assetbox` (`Id`),
  CONSTRAINT `Relationship159` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetboxshare`
--

LOCK TABLES `assetboxshare` WRITE;
/*!40000 ALTER TABLE `assetboxshare` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetboxshare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetchangelog`
--

DROP TABLE IF EXISTS `assetchangelog`;
CREATE TABLE `assetchangelog` (
  `ChangeTime` datetime NOT NULL,
  `AssetId` bigint(20) default NULL,
  `LogEntry` longtext,
  `LogTypeId` bigint(20) NOT NULL,
  `SessionLogId` bigint(20) default NULL,
  `VersionNumber` int(11) default NULL,
  `Id` bigint(20) NOT NULL auto_increment,
  `AssetDescription` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship241` (`AssetId`),
  KEY `IX_Relationship229` (`LogTypeId`),
  KEY `IX_Relationship239` (`SessionLogId`),
  CONSTRAINT `Relationship229` FOREIGN KEY (`LogTypeId`) REFERENCES `logtype` (`Id`),
  CONSTRAINT `Relationship239` FOREIGN KEY (`SessionLogId`) REFERENCES `sessionlog` (`Id`),
  CONSTRAINT `Relationship241` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetchangelog`
--

LOCK TABLES `assetchangelog` WRITE;
/*!40000 ALTER TABLE `assetchangelog` DISABLE KEYS */;
INSERT INTO `assetchangelog` VALUES ('2012-06-10 13:06:41',NULL,NULL,2,70,1,1,'1 153781.jpg360'),('2012-06-10 13:50:47',NULL,NULL,2,132,1,2,'2 15378.jpg'),('2012-06-10 14:11:48',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<change date=\"Sun Jun 10 14:11:48 GST 2012\" user=\"admin\">\n	<attribute id=\"4\" label=\"Description\">\n		<before>360</before>\n		<after>360 changed</after>\n	</attribute>\n	<attribute id=\"9\" label=\"Date Last Modified\">\n		<before>10/06/2012 13:50:45</before>\n		<after>10/06/2012 14:11:48</after>\n	</attribute>\n</change>\n\n',1,132,1,3,'2 15378.jpg'),('2012-06-10 14:23:29',NULL,NULL,2,174,1,4,'3 153781.jpg'),('2012-06-10 14:43:22',NULL,NULL,2,174,1,5,'4 15378.jpg'),('2012-06-10 14:55:39',NULL,NULL,2,229,1,6,'5 153782.jpg'),('2012-06-10 15:48:08',6,NULL,2,305,1,7,'6 153783.jpg'),('2012-06-10 16:39:10',NULL,NULL,2,382,1,8,'7 153784.jpg'),('2012-06-11 10:22:17',NULL,NULL,2,2258,1,9,'8 dubaisouk1.flv'),('2012-06-11 10:32:11',NULL,NULL,2,2328,1,10,'9 dubaisouk11.flv'),('2012-06-11 10:48:10',NULL,NULL,2,2356,1,11,'10 dubaisouk12.flv'),('2012-06-11 11:13:25',NULL,NULL,2,2380,1,12,'11 dubaisouk1.flv'),('2012-06-11 11:51:51',NULL,NULL,2,2490,1,13,'12 dubaisouk14.flv'),('2012-06-11 12:21:06',NULL,NULL,2,2579,1,14,'13 ds.flv'),('2012-06-11 12:36:54',NULL,NULL,2,2613,1,15,'14 ds.flv'),('2012-06-11 12:40:20',NULL,NULL,2,2618,1,16,'15 ds1.flv'),('2012-06-11 13:00:31',NULL,NULL,3,2674,1,17,'14 ds.flv'),('2012-06-11 13:00:48',NULL,NULL,3,2674,1,18,'5 153782.jpg'),('2012-06-11 13:01:00',NULL,NULL,3,2674,1,19,'2 15378.jpg'),('2012-06-11 13:01:06',NULL,NULL,3,2674,1,20,'1 153781.jpg360'),('2012-06-11 13:01:12',NULL,NULL,3,2674,1,21,'4 15378.jpg'),('2012-06-11 13:01:19',NULL,NULL,3,2674,1,22,'3 153781.jpg'),('2012-06-11 13:02:25',NULL,NULL,3,2674,1,23,'11 dubaisouk1.flv'),('2012-06-11 13:02:46',NULL,NULL,3,2674,1,24,'10 dubaisouk12.flv'),('2012-06-11 13:02:55',NULL,NULL,3,2674,1,25,'12 dubaisouk14.flv'),('2012-06-11 14:11:28',NULL,NULL,2,2833,1,26,'16 15.flv'),('2012-06-11 14:32:26',NULL,NULL,3,2865,1,27,'13 ds.flv'),('2012-06-11 14:33:00',NULL,NULL,3,2865,1,28,'15 ds1.flv'),('2012-06-11 14:33:32',NULL,NULL,3,2865,1,29,'9 dubaisouk11.flv'),('2012-06-11 14:33:40',NULL,NULL,3,2865,1,30,'8 dubaisouk1.flv'),('2012-06-11 14:34:01',NULL,NULL,3,2865,1,31,'16 15.flv'),('2012-06-11 14:34:38',NULL,NULL,3,2865,1,32,'7 153784.jpg'),('2012-06-11 14:42:28',NULL,NULL,2,2882,1,33,'7 152.flv'),('2012-06-11 15:37:15',NULL,NULL,2,2978,1,34,'8 153.flv'),('2012-06-11 16:59:54',NULL,NULL,2,3055,1,35,'9 ds1.flv'),('2012-06-12 08:59:18',NULL,NULL,2,3056,1,36,'10 ds2.flv'),('2012-06-12 11:27:27',NULL,NULL,2,3062,1,37,'11 sh1.flv'),('2012-06-12 14:04:23',NULL,NULL,3,3073,1,38,'10 ds2.flv'),('2012-06-12 14:11:55',NULL,NULL,3,3074,1,39,'9 ds1.flv'),('2012-06-12 14:12:03',NULL,NULL,3,3074,1,40,'11 sh1.flv'),('2012-06-12 14:22:52',NULL,NULL,2,3075,1,41,'12 ds1.flv'),('2012-06-12 15:28:47',NULL,NULL,3,3079,1,42,'12 ds1.flv'),('2012-06-12 15:40:49',13,NULL,2,3082,1,43,'13 ds1.flv'),('2012-06-12 15:43:33',13,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<change date=\"Tue Jun 12 15:43:33 GST 2012\" user=\"admin\">\n	<attribute id=\"9\" label=\"Date Last Modified\">\n		<before>12/06/2012 15:40:47</before>\n		<after>12/06/2012 15:43:29</after>\n	</attribute>\n</change>\n\n',1,3082,1,44,'13 ds1.flv'),('2012-06-12 16:02:14',NULL,NULL,3,3084,1,45,'8 153.flv'),('2012-06-12 16:02:22',NULL,NULL,3,3084,1,46,'7 152.flv'),('2012-06-12 16:10:22',14,NULL,2,3085,1,47,'14 dstotest.flv'),('2012-06-12 16:27:01',15,NULL,2,3086,1,48,'15 sh1.flv'),('2012-06-12 16:56:06',16,NULL,2,3089,1,49,'16 dstotest1.flv');
/*!40000 ALTER TABLE `assetchangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetdownloadtype`
--

DROP TABLE IF EXISTS `assetdownloadtype`;
CREATE TABLE `assetdownloadtype` (
  `Id` bigint(20) NOT NULL,
  `Description` varchar(60) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetdownloadtype`
--

LOCK TABLES `assetdownloadtype` WRITE;
/*!40000 ALTER TABLE `assetdownloadtype` DISABLE KEYS */;
INSERT INTO `assetdownloadtype` VALUES (1,'Preview'),(2,'Download converted'),(3,'Download original'),(4,'Export'),(5,'Email'),(6,'Repurpose'),(7,'Generated from Brand Template');
/*!40000 ALTER TABLE `assetdownloadtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetentity`
--

DROP TABLE IF EXISTS `assetentity`;
CREATE TABLE `assetentity` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(100) NOT NULL,
  `TermForSiblings` varchar(100) default NULL,
  `IsSearchable` tinyint(1) NOT NULL default '1',
  `IsQuickSearchable` tinyint(1) NOT NULL default '0',
  `IncludedFileFormats` varchar(255) default NULL,
  `ExcludedFileFormats` varchar(255) default NULL,
  `SequenceNumber` int(11) default NULL,
  `MustHaveParent` tinyint(1) NOT NULL default '0',
  `ThumbnailFilename` varchar(100) default NULL,
  `CanCopyAssets` tinyint(1) NOT NULL default '0',
  `DefaultCategoryId` bigint(20) default NULL,
  `OnlyAllowDefaultCategory` tinyint(1) NOT NULL default '0',
  `RootCategoryId` bigint(20) default NULL,
  `UnrestAgreementId` bigint(20) default NULL,
  `RestAgreementId` bigint(20) default NULL,
  `ShowAttributeLabels` tinyint(1) NOT NULL default '1',
  `TermForSibling` varchar(100) default NULL,
  `CanDownloadChildren` tinyint(1) NOT NULL default '0',
  `MatchOnAttributeId` bigint(20) default NULL,
  `ShowOnDescendantCategories` tinyint(1) NOT NULL default '0',
  `IsCategoryExtension` tinyint(1) NOT NULL default '0',
  `IsArticle` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_AssetEntity_Cat_Root_FK` (`RootCategoryId`),
  KEY `IX_Relationship292` (`MatchOnAttributeId`),
  CONSTRAINT `AssetEntity_Cat_Root_FK` FOREIGN KEY (`RootCategoryId`) REFERENCES `cm_category` (`Id`),
  CONSTRAINT `Relationship292` FOREIGN KEY (`MatchOnAttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetentity`
--

LOCK TABLES `assetentity` WRITE;
/*!40000 ALTER TABLE `assetentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetentityattribute`
--

DROP TABLE IF EXISTS `assetentityattribute`;
CREATE TABLE `assetentityattribute` (
  `AssetEntityId` bigint(20) NOT NULL,
  `AttributeId` bigint(20) NOT NULL,
  `IsVisibleOnAdd` tinyint(1) NOT NULL default '1',
  `ValueIfNotVisible` mediumtext,
  PRIMARY KEY  (`AssetEntityId`,`AttributeId`),
  KEY `IX_Relationship253` (`AttributeId`),
  KEY `IX_Relationship23522` (`AssetEntityId`),
  CONSTRAINT `Relationship23522` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `Relationship253` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetentityattribute`
--

LOCK TABLES `assetentityattribute` WRITE;
/*!40000 ALTER TABLE `assetentityattribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetentityattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetfile`
--

DROP TABLE IF EXISTS `assetfile`;
CREATE TABLE `assetfile` (
  `Uri` varchar(255) NOT NULL,
  `FileData` longblob,
  `DateCreated` datetime NOT NULL,
  PRIMARY KEY  (`Uri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetfile`
--

LOCK TABLES `assetfile` WRITE;
/*!40000 ALTER TABLE `assetfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetinbatchrelease`
--

DROP TABLE IF EXISTS `assetinbatchrelease`;
CREATE TABLE `assetinbatchrelease` (
  `AssetId` bigint(20) NOT NULL,
  `BatchReleaseId` bigint(20) NOT NULL,
  `ChangeDescription` mediumtext,
  PRIMARY KEY  (`AssetId`,`BatchReleaseId`),
  KEY `IX_AIBR_Asset_FK` (`AssetId`),
  KEY `IX_AIBR_BatchRel_FK` (`BatchReleaseId`),
  CONSTRAINT `AIBR_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `AIBR_BatchRel_FK` FOREIGN KEY (`BatchReleaseId`) REFERENCES `batchrelease` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetinbatchrelease`
--

LOCK TABLES `assetinbatchrelease` WRITE;
/*!40000 ALTER TABLE `assetinbatchrelease` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetinbatchrelease` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetinmarketingemail`
--

DROP TABLE IF EXISTS `assetinmarketingemail`;
CREATE TABLE `assetinmarketingemail` (
  `MarketingEmailId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  PRIMARY KEY  (`MarketingEmailId`,`AssetId`),
  KEY `IX_Relationship214` (`AssetId`),
  KEY `IX_Relationship2162` (`MarketingEmailId`),
  CONSTRAINT `Relationship214` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship2162` FOREIGN KEY (`MarketingEmailId`) REFERENCES `marketingemail` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetinmarketingemail`
--

LOCK TABLES `assetinmarketingemail` WRITE;
/*!40000 ALTER TABLE `assetinmarketingemail` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetinmarketingemail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetlistattributevalue`
--

DROP TABLE IF EXISTS `assetlistattributevalue`;
CREATE TABLE `assetlistattributevalue` (
  `AssetId` bigint(20) NOT NULL,
  `ListAttributeValueId` bigint(20) NOT NULL,
  PRIMARY KEY  (`AssetId`,`ListAttributeValueId`),
  KEY `IX_ALAV_Asset_FK` (`AssetId`),
  KEY `IX_ALAV_LAV_FK` (`ListAttributeValueId`),
  CONSTRAINT `ALAV_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `ALAV_LAV_FK` FOREIGN KEY (`ListAttributeValueId`) REFERENCES `listattributevalue` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetlistattributevalue`
--

LOCK TABLES `assetlistattributevalue` WRITE;
/*!40000 ALTER TABLE `assetlistattributevalue` DISABLE KEYS */;
INSERT INTO `assetlistattributevalue` VALUES (6,7),(13,7),(14,7),(15,7),(16,7);
/*!40000 ALTER TABLE `assetlistattributevalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetpurchaselog`
--

DROP TABLE IF EXISTS `assetpurchaselog`;
CREATE TABLE `assetpurchaselog` (
  `ABOrderId` int(11) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `Description` varchar(200) default NULL,
  `Price` int(11) default NULL,
  `ShippingCost` int(11) default NULL,
  PRIMARY KEY  (`ABOrderId`,`AssetId`),
  KEY `IX_Relationship79` (`ABOrderId`),
  CONSTRAINT `Relationship79` FOREIGN KEY (`ABOrderId`) REFERENCES `aborder` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetpurchaselog`
--

LOCK TABLES `assetpurchaselog` WRITE;
/*!40000 ALTER TABLE `assetpurchaselog` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetpurchaselog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetpurchasepriceband`
--

DROP TABLE IF EXISTS `assetpurchasepriceband`;
CREATE TABLE `assetpurchasepriceband` (
  `PriceBandId` bigint(20) NOT NULL,
  `ABOrderId` int(11) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `Quantity` int(11) default NULL,
  `PriceBandTypeId` int(11) default NULL,
  `PriceBandName` varchar(255) default NULL,
  `PriceBandTypeName` varchar(255) default NULL,
  `Cost` int(11) default NULL,
  `ShippingCost` int(11) default NULL,
  PRIMARY KEY  (`PriceBandId`,`ABOrderId`,`AssetId`),
  KEY `IX_Relationship140` (`ABOrderId`,`AssetId`),
  CONSTRAINT `Relationship140` FOREIGN KEY (`ABOrderId`, `AssetId`) REFERENCES `assetpurchaselog` (`ABOrderId`, `AssetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetpurchasepriceband`
--

LOCK TABLES `assetpurchasepriceband` WRITE;
/*!40000 ALTER TABLE `assetpurchasepriceband` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetpurchasepriceband` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetrating`
--

DROP TABLE IF EXISTS `assetrating`;
CREATE TABLE `assetrating` (
  `Rating` int(11) default NULL,
  `UserId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `FeedbackComment` text,
  `Id` bigint(20) NOT NULL auto_increment,
  `FeedbackSubject` varchar(200) default NULL,
  `DateOfFeedback` datetime default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship278` (`AssetId`),
  KEY `IX_Relationship279` (`UserId`),
  CONSTRAINT `Relationship278` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship279` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetrating`
--

LOCK TABLES `assetrating` WRITE;
/*!40000 ALTER TABLE `assetrating` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetrating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assettype`
--

DROP TABLE IF EXISTS `assettype`;
CREATE TABLE `assettype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assettype`
--

LOCK TABLES `assettype` WRITE;
/*!40000 ALTER TABLE `assettype` DISABLE KEYS */;
INSERT INTO `assettype` VALUES (1,'File'),(2,'Image'),(3,'Video'),(4,'Audio');
/*!40000 ALTER TABLE `assettype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetuse`
--

DROP TABLE IF EXISTS `assetuse`;
CREATE TABLE `assetuse` (
  `Id` bigint(20) NOT NULL auto_increment,
  `UserId` bigint(20) default NULL,
  `SessionLogId` bigint(20) default NULL,
  `UsageTypeId` bigint(20) default NULL,
  `TimeOfDownload` datetime NOT NULL,
  `OriginalDescription` varchar(255) default NULL,
  `OtherDescription` varchar(255) default NULL,
  `AssetDownloadTypeId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship49` (`AssetId`),
  KEY `IX_Relationship51` (`UserId`),
  KEY `IX_Relationship50` (`UsageTypeId`),
  KEY `IX_Relationship57` (`AssetDownloadTypeId`),
  KEY `IX_Relationship237` (`SessionLogId`),
  CONSTRAINT `Relationship237` FOREIGN KEY (`SessionLogId`) REFERENCES `sessionlog` (`Id`),
  CONSTRAINT `Relationship49` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship50` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`),
  CONSTRAINT `Relationship51` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship57` FOREIGN KEY (`AssetDownloadTypeId`) REFERENCES `assetdownloadtype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetuse`
--

LOCK TABLES `assetuse` WRITE;
/*!40000 ALTER TABLE `assetuse` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetuse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetview`
--

DROP TABLE IF EXISTS `assetview`;
CREATE TABLE `assetview` (
  `AssetId` bigint(20) NOT NULL,
  `Time` datetime NOT NULL,
  `UserId` bigint(20) default NULL,
  `SessionLogId` bigint(20) default NULL,
  KEY `IX_Relationship77` (`AssetId`),
  KEY `IX_Relationship63` (`UserId`),
  KEY `IX_Relationship238` (`SessionLogId`),
  CONSTRAINT `Relationship238` FOREIGN KEY (`SessionLogId`) REFERENCES `sessionlog` (`Id`),
  CONSTRAINT `Relationship63` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship77` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assetview`
--

LOCK TABLES `assetview` WRITE;
/*!40000 ALTER TABLE `assetview` DISABLE KEYS */;
INSERT INTO `assetview` VALUES (6,'2012-06-10 15:48:24',1,305),(6,'2012-06-10 15:50:22',1,313),(6,'2012-06-11 14:34:20',1,2865),(6,'2012-06-11 14:59:24',1,2882),(6,'2012-06-12 11:28:14',1,3062),(6,'2012-06-12 15:04:57',1,3078),(13,'2012-06-12 15:42:01',1,3082),(13,'2012-06-12 15:42:50',1,3082),(13,'2012-06-12 15:43:33',1,3082),(13,'2012-06-12 15:44:00',1,3082),(13,'2012-06-12 16:02:29',1,3084),(14,'2012-06-12 16:12:13',1,3085),(14,'2012-06-12 16:13:34',1,3085),(14,'2012-06-12 16:19:20',1,3086),(15,'2012-06-12 16:27:34',1,3086),(15,'2012-06-12 16:52:31',1,3087),(14,'2012-06-12 16:52:39',1,3087),(16,'2012-06-12 16:56:11',1,3089);
/*!40000 ALTER TABLE `assetview` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AttributeTypeId` bigint(20) default NULL,
  `Label` varchar(255) default NULL,
  `IsKeywordSearchable` tinyint(1) NOT NULL default '0',
  `IsMandatory` tinyint(1) NOT NULL default '0',
  `DefaultValue` varchar(255) default NULL,
  `ValueIfNotVisible` varchar(255) default NULL,
  `IsStatic` tinyint(1) default NULL,
  `StaticFieldName` varchar(200) default NULL,
  `IsReadOnly` tinyint(1) default NULL,
  `MaxDisplayLength` int(11) default '-1',
  `TreeId` bigint(20) default NULL,
  `HelpText` varchar(255) default NULL,
  `Highlight` tinyint(1) default NULL,
  `NameAttribute` tinyint(1) default '0',
  `DefaultKeywordFilter` varchar(5) default NULL,
  `OnChangeScript` mediumtext,
  `IsHtml` tinyint(1) default NULL,
  `IsRequiredForCompleteness` tinyint(1) default '0',
  `RequiresTranslation` tinyint(1) default '0',
  `DisplayName` varchar(255) default NULL,
  `BaseUrl` varchar(255) default NULL,
  `AltText` varchar(255) default NULL,
  `HasSearchTokens` tinyint(1) NOT NULL default '0',
  `TokenDelimiterRegex` varchar(20) default NULL,
  `IsDescriptionAttribute` tinyint(1) default NULL,
  `Height` int(11) default NULL,
  `Width` int(11) default NULL,
  `MaxSize` int(11) default NULL,
  `IsFiltered` tinyint(1) default NULL,
  `Seed` bigint(20) default NULL,
  `IncrementAmount` int(11) default NULL,
  `IsExtendableList` tinyint(1) default NULL,
  `GetDataFromChildren` tinyint(1) NOT NULL default '0',
  `InputMask` varchar(250) default NULL,
  `IsMandatoryBulkUpload` tinyint(1) NOT NULL default '0',
  `ShowOnChild` tinyint(1) NOT NULL default '0',
  `IncludeInSearchForChild` tinyint(1) NOT NULL default '0',
  `Prefix` varchar(40) default NULL,
  `HideForSort` tinyint(1) NOT NULL default '0',
  `IsAutoComplete` tinyint(1) NOT NULL default '0',
  `PluginClass` varchar(250) default NULL,
  `PluginParamsAttributeIds` varchar(250) default NULL,
  `ValueColumnName` varchar(255) default NULL,
  `ShowOnDownload` tinyint(1) NOT NULL default '0',
  `MaxDecimalPlaces` int(11) default NULL,
  `MinDecimalPlaces` int(11) default NULL,
  `SinglePointOnly` tinyint(1) NOT NULL default '0',
  `DateFormatType` smallint(6) NOT NULL default '0',
  `AutoCompletePlugin` varchar(255) default NULL,
  `EnforceUniqueness` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship19` (`AttributeTypeId`),
  CONSTRAINT `Relationship19` FOREIGN KEY (`AttributeTypeId`) REFERENCES `attributetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,NULL,'File',0,1,NULL,NULL,1,'file',NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(2,NULL,'ID',0,0,NULL,NULL,1,'assetId',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(3,1,'Title',1,1,NULL,NULL,0,NULL,NULL,-1,NULL,NULL,NULL,1,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,4000,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'title',0,NULL,NULL,0,0,NULL,0),(4,2,'Description',1,1,'',NULL,0,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,4,NULL,4000,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'description',0,NULL,NULL,0,0,NULL,0),(5,2,'Keywords',1,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,4,NULL,4000,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'keywords',0,NULL,NULL,0,0,NULL,0),(6,NULL,'Original Filename',1,0,NULL,NULL,1,'originalFilename',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(7,8,'Date Created',0,0,NULL,NULL,0,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'datecreated',0,NULL,NULL,0,0,NULL,0),(8,8,'Date Added',0,0,NULL,NULL,1,'dateAdded',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(9,8,'Date Last Modified',0,0,NULL,NULL,1,'dateLastModified',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(10,NULL,'Added By',0,0,NULL,NULL,1,'addedBy',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(11,NULL,'Last Modified By',0,0,NULL,NULL,1,'lastModifiedBy',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(12,NULL,'Size',0,0,NULL,NULL,1,'size',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(13,NULL,'Orientation',0,0,NULL,NULL,1,'orientation',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(14,NULL,'Price',0,0,NULL,NULL,1,'price',NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(15,NULL,'Embedded Data',0,0,NULL,NULL,1,'embeddedData',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(16,NULL,'Asset Usage',0,0,NULL,NULL,1,'usage',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(17,NULL,'Categories',0,1,NULL,NULL,1,'categories',0,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(18,NULL,'Access Levels',0,0,NULL,NULL,1,'accessLevels',0,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(19,NULL,'Version',0,0,NULL,NULL,1,'version',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(21,4,'Active Status',0,1,'Active','Active',NULL,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(22,3,'Activation Date',0,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'activationdate',0,NULL,NULL,0,0,NULL,0),(23,3,'Expiry Date',0,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'expirydate',0,NULL,NULL,0,0,NULL,0),(30,4,'Usage Rights',0,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(200,NULL,'Audit Logging',0,0,NULL,NULL,1,'audit',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(300,NULL,'Is Sensitive?',0,0,NULL,NULL,1,'sensitive',NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(301,2,'Sensitivity Notes',0,0,NULL,NULL,0,NULL,NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,4,NULL,4000,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,'sensitivitynotes',0,NULL,NULL,0,0,NULL,0),(400,NULL,'Agreement Type',0,0,NULL,NULL,1,'agreements',NULL,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(600,NULL,'Average Rating',0,0,NULL,NULL,1,'rating',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0),(700,8,'Date Last Downloaded',0,0,NULL,NULL,1,'dateLastDownloaded',1,-1,NULL,NULL,NULL,0,NULL,NULL,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,NULL,0,0,NULL,NULL,NULL,0,NULL,NULL,0,0,NULL,0);
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributeinindesigndocument`
--

DROP TABLE IF EXISTS `attributeinindesigndocument`;
CREATE TABLE `attributeinindesigndocument` (
  `InDesignDocumentId` bigint(20) NOT NULL,
  `AttributeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`InDesignDocumentId`,`AttributeId`),
  KEY `IX_AIInDD_Attribute_FK` (`AttributeId`),
  KEY `IX_AIInDD_InDD_FK` (`InDesignDocumentId`),
  CONSTRAINT `AIInDD_Attribute_FK` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `AIInDD_InDD_FK` FOREIGN KEY (`InDesignDocumentId`) REFERENCES `indesigndocument` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attributeinindesigndocument`
--

LOCK TABLES `attributeinindesigndocument` WRITE;
/*!40000 ALTER TABLE `attributeinindesigndocument` DISABLE KEYS */;
/*!40000 ALTER TABLE `attributeinindesigndocument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributestoragetype`
--

DROP TABLE IF EXISTS `attributestoragetype`;
CREATE TABLE `attributestoragetype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attributestoragetype`
--

LOCK TABLES `attributestoragetype` WRITE;
/*!40000 ALTER TABLE `attributestoragetype` DISABLE KEYS */;
INSERT INTO `attributestoragetype` VALUES (1,'Value per Asset'),(2,'Lookup Table'),(3,'Stored as Categories'),(100,'Not Stored');
/*!40000 ALTER TABLE `attributestoragetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributetype`
--

DROP TABLE IF EXISTS `attributetype`;
CREATE TABLE `attributetype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  `AttributeStorageTypeId` bigint(20) NOT NULL,
  `SequenceNumber` int(11) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_AttributeType_StorageT_FK` (`AttributeStorageTypeId`),
  CONSTRAINT `AttributeType_StorageT_FK` FOREIGN KEY (`AttributeStorageTypeId`) REFERENCES `attributestoragetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attributetype`
--

LOCK TABLES `attributetype` WRITE;
/*!40000 ALTER TABLE `attributetype` DISABLE KEYS */;
INSERT INTO `attributetype` VALUES (1,'Text field (long)',1,10),(2,'Text area (long)',1,20),(3,'Datepicker',1,30),(4,'Dropdown',2,40),(5,'Checklist',2,50),(6,'Optionlist',2,60),(7,'Keyword picker',3,70),(8,'Datetime',1,80),(9,'Hyperlink',1,90),(10,'Group header',100,100),(11,'Autoincrement',1,110),(12,'External dictionary',1,120),(13,'Data lookup button',100,130),(14,'Text field',1,7),(15,'Text area',1,17),(16,'Numeric',1,140),(17,'Spatial area',1,150),(18,'File',1,160);
/*!40000 ALTER TABLE `attributetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributevisibletogroup`
--

DROP TABLE IF EXISTS `attributevisibletogroup`;
CREATE TABLE `attributevisibletogroup` (
  `UserGroupId` bigint(20) NOT NULL,
  `AttributeId` bigint(20) NOT NULL,
  `IsWriteable` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`UserGroupId`,`AttributeId`),
  KEY `IX_Relationship45` (`AttributeId`),
  KEY `IX_Relationship44` (`UserGroupId`),
  CONSTRAINT `Relationship44` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`),
  CONSTRAINT `Relationship45` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attributevisibletogroup`
--

LOCK TABLES `attributevisibletogroup` WRITE;
/*!40000 ALTER TABLE `attributevisibletogroup` DISABLE KEYS */;
INSERT INTO `attributevisibletogroup` VALUES (1,1,1),(1,2,0),(1,3,1),(1,4,1),(1,5,1),(1,6,0),(1,7,1),(1,12,0),(1,13,0),(1,17,1),(1,18,0),(1,30,1),(2,1,0),(2,2,0),(2,3,0),(2,4,0),(2,5,0),(2,7,0),(2,12,0),(2,13,0),(2,17,0),(2,30,0);
/*!40000 ALTER TABLE `attributevisibletogroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audioasset`
--

DROP TABLE IF EXISTS `audioasset`;
CREATE TABLE `audioasset` (
  `AssetId` bigint(20) NOT NULL,
  `PreviewClipLocation` varchar(255) default NULL,
  `Duration` bigint(20) default NULL,
  `Width` int(11) default NULL,
  `Height` int(11) default NULL,
  PRIMARY KEY  (`AssetId`),
  CONSTRAINT `Relationship151` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `audioasset`
--

LOCK TABLES `audioasset` WRITE;
/*!40000 ALTER TABLE `audioasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `audioasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audiodownloadoption`
--

DROP TABLE IF EXISTS `audiodownloadoption`;
CREATE TABLE `audiodownloadoption` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Bitrate` int(11) default NULL,
  `Label` varchar(255) default NULL,
  `Sequence` int(11) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `audiodownloadoption`
--

LOCK TABLES `audiodownloadoption` WRITE;
/*!40000 ALTER TABLE `audiodownloadoption` DISABLE KEYS */;
INSERT INTO `audiodownloadoption` VALUES (10001,96000,'Low (96 kbps, ~FM Radio Quality)',3),(10002,128000,'Standard (128 kbps, Standard MP3)',4),(10003,256000,'High (256 kbps, Near CD Quality)',5);
/*!40000 ALTER TABLE `audiodownloadoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batchrelease`
--

DROP TABLE IF EXISTS `batchrelease`;
CREATE TABLE `batchrelease` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `Description` mediumtext,
  `CreatedByUserId` bigint(20) NOT NULL,
  `CreationDate` datetime default NULL,
  `Notes` mediumtext,
  `ReleaseDate` datetime default NULL,
  `Status` int(11) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_BatchRel_User_FK` (`CreatedByUserId`),
  CONSTRAINT `BatchRel_User_FK` FOREIGN KEY (`CreatedByUserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `batchrelease`
--

LOCK TABLES `batchrelease` WRITE;
/*!40000 ALTER TABLE `batchrelease` DISABLE KEYS */;
/*!40000 ALTER TABLE `batchrelease` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batchreleaseerror`
--

DROP TABLE IF EXISTS `batchreleaseerror`;
CREATE TABLE `batchreleaseerror` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AssetId` bigint(20) default NULL,
  `BatchReleaseId` bigint(20) NOT NULL,
  `ErrorText` mediumtext,
  `SequenceNumber` int(11) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `Asset_BRE_FK` (`AssetId`),
  KEY `BatchRelease_BRE_FK` (`BatchReleaseId`),
  CONSTRAINT `Asset_BRE_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `BatchRelease_BRE_FK` FOREIGN KEY (`BatchReleaseId`) REFERENCES `batchrelease` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `batchreleaseerror`
--

LOCK TABLES `batchreleaseerror` WRITE;
/*!40000 ALTER TABLE `batchreleaseerror` DISABLE KEYS */;
/*!40000 ALTER TABLE `batchreleaseerror` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(100) default NULL,
  `Code` varchar(50) NOT NULL,
  `CssFile` varchar(100) default NULL,
  `LogoFile` varchar(100) default NULL,
  `LogoWidth` int(11) default NULL,
  `LogoHeight` int(11) default NULL,
  `LogoAlt` varchar(100) default NULL,
  `ContentListIdentifier` varchar(200) default NULL,
  `EmailDomains` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Code` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoryvisibletogroup`
--

DROP TABLE IF EXISTS `categoryvisibletogroup`;
CREATE TABLE `categoryvisibletogroup` (
  `UserGroupId` bigint(20) NOT NULL,
  `CategoryId` bigint(20) NOT NULL,
  `CanDownloadAssets` tinyint(1) NOT NULL default '0',
  `CanDownloadWithApproval` tinyint(1) NOT NULL default '0',
  `CanUpdateAssets` tinyint(1) NOT NULL default '0',
  `CanUpdateWithApproval` tinyint(1) NOT NULL default '0',
  `CanDeleteAssets` tinyint(1) NOT NULL default '0',
  `CanApproveAssets` tinyint(1) NOT NULL default '0',
  `CantDownloadOriginal` tinyint(1) NOT NULL default '0',
  `CantDownloadAdvanced` tinyint(1) NOT NULL default '0',
  `CanEditSubcategories` tinyint(1) NOT NULL default '0',
  `CantReviewAssets` tinyint(1) NOT NULL default '0',
  `CanApproveAssetUploads` tinyint(1) NOT NULL default '0',
  `CanViewRestrictedAssets` tinyint(1) NOT NULL default '0',
  `ApprRequiredForHighRes` tinyint(1) NOT NULL default '0',
  `CanUploadAssets` tinyint(1) NOT NULL default '0',
  `CanUploadWithApproval` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`UserGroupId`,`CategoryId`),
  KEY `IX_Relationship35` (`CategoryId`),
  KEY `IX_Relationship34` (`UserGroupId`),
  CONSTRAINT `Relationship34` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`),
  CONSTRAINT `Relationship35` FOREIGN KEY (`CategoryId`) REFERENCES `cm_category` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categoryvisibletogroup`
--

LOCK TABLES `categoryvisibletogroup` WRITE;
/*!40000 ALTER TABLE `categoryvisibletogroup` DISABLE KEYS */;
INSERT INTO `categoryvisibletogroup` VALUES (1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `categoryvisibletogroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changeattributevaluedaterule`
--

DROP TABLE IF EXISTS `changeattributevaluedaterule`;
CREATE TABLE `changeattributevaluedaterule` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AttributeId` bigint(20) NOT NULL,
  `RuleName` varchar(200) default NULL,
  `ChangeAttributeId` bigint(20) NOT NULL,
  `NewAttributeValue` varchar(255) default NULL,
  `Enabled` tinyint(1) NOT NULL default '0',
  `ActionOnAssetId` bigint(20) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship86` (`ChangeAttributeId`),
  KEY `IX_Relationship89` (`AttributeId`),
  KEY `IX_Relationship266` (`ActionOnAssetId`),
  CONSTRAINT `Relationship266` FOREIGN KEY (`ActionOnAssetId`) REFERENCES `actiononasset` (`Id`),
  CONSTRAINT `Relationship86` FOREIGN KEY (`ChangeAttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `Relationship89` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `changeattributevaluedaterule`
--

LOCK TABLES `changeattributevaluedaterule` WRITE;
/*!40000 ALTER TABLE `changeattributevaluedaterule` DISABLE KEYS */;
INSERT INTO `changeattributevaluedaterule` VALUES (1,22,'Activate asset',21,'Active',1,NULL),(2,23,'Expire asset',21,'Expired',1,NULL);
/*!40000 ALTER TABLE `changeattributevaluedaterule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cm_category`
--

DROP TABLE IF EXISTS `cm_category`;
CREATE TABLE `cm_category` (
  `Id` bigint(20) NOT NULL auto_increment,
  `ParentId` bigint(20) default NULL,
  `CategoryTypeId` bigint(20) NOT NULL,
  `Name` text NOT NULL,
  `Description` mediumtext,
  `SequenceNumber` int(11) default NULL,
  `Summary` varchar(255) default NULL,
  `IsBrowsable` tinyint(1) NOT NULL,
  `IsRestrictive` tinyint(1) NOT NULL default '0',
  `BitPosition` int(11) default NULL,
  `IsListboxCategory` tinyint(1) default '0',
  `CannotBeDeleted` tinyint(1) NOT NULL,
  `Synchronised` tinyint(1) NOT NULL default '0',
  `SelectedOnLoad` tinyint(1) default '0',
  `AllowAdvancedOptions` tinyint(1) default '1',
  `IsExpired` tinyint(1) NOT NULL default '0',
  `WorkflowName` varchar(200) default NULL,
  `CanAssignIfNotLeaf` tinyint(1) NOT NULL default '0',
  `ExtensionAssetId` bigint(20) default NULL,
  `BrowseAsPanels` tinyint(1) default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Asset_Category_FK` (`ExtensionAssetId`),
  KEY `IX_Relationship10` (`ParentId`),
  KEY `IX_Relationship7` (`CategoryTypeId`),
  CONSTRAINT `Asset_Category_FK` FOREIGN KEY (`ExtensionAssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship10` FOREIGN KEY (`ParentId`) REFERENCES `cm_category` (`Id`),
  CONSTRAINT `Relationship7` FOREIGN KEY (`CategoryTypeId`) REFERENCES `cm_categorytype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cm_category`
--

LOCK TABLES `cm_category` WRITE;
/*!40000 ALTER TABLE `cm_category` DISABLE KEYS */;
INSERT INTO `cm_category` VALUES (1,NULL,2,'Universal',NULL,1,NULL,1,1,NULL,0,1,0,0,1,0,'default',0,NULL,0),(2,NULL,1,'Art',NULL,2,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(3,NULL,1,'Concepts',NULL,3,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(4,NULL,1,'Diagrams',NULL,4,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(5,NULL,1,'Documents',NULL,5,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(6,NULL,1,'Events',NULL,6,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(7,NULL,1,'Graphics',NULL,7,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(8,NULL,1,'Logos',NULL,8,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(9,NULL,1,'People',NULL,9,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(10,NULL,1,'Publications',NULL,10,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(11,NULL,1,'Screenshots',NULL,11,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0),(12,NULL,1,'Video clips',NULL,12,NULL,1,0,NULL,0,0,0,0,1,0,NULL,0,NULL,0);
/*!40000 ALTER TABLE `cm_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cm_categorytype`
--

DROP TABLE IF EXISTS `cm_categorytype`;
CREATE TABLE `cm_categorytype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Description` varchar(50) default NULL,
  `IsAlphabeticOrder` tinyint(1) NOT NULL,
  `IsNameGloballyUnique` tinyint(1) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cm_categorytype`
--

LOCK TABLES `cm_categorytype` WRITE;
/*!40000 ALTER TABLE `cm_categorytype` DISABLE KEYS */;
INSERT INTO `cm_categorytype` VALUES (1,'Asset categories',0,0),(2,'Access levels',0,0);
/*!40000 ALTER TABLE `cm_categorytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cm_itemincategory`
--

DROP TABLE IF EXISTS `cm_itemincategory`;
CREATE TABLE `cm_itemincategory` (
  `CategoryId` bigint(20) NOT NULL,
  `ItemId` bigint(20) NOT NULL,
  `IsApproved` tinyint(1) default NULL,
  `LastApprovedByUserId` bigint(20) default NULL,
  PRIMARY KEY  (`CategoryId`,`ItemId`),
  KEY `IX_Relationship8` (`ItemId`),
  KEY `IIC_ABU_FK` (`LastApprovedByUserId`),
  KEY `IX_Relationship9` (`CategoryId`),
  CONSTRAINT `IIC_ABU_FK` FOREIGN KEY (`LastApprovedByUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship8` FOREIGN KEY (`ItemId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship9` FOREIGN KEY (`CategoryId`) REFERENCES `cm_category` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cm_itemincategory`
--

LOCK TABLES `cm_itemincategory` WRITE;
/*!40000 ALTER TABLE `cm_itemincategory` DISABLE KEYS */;
INSERT INTO `cm_itemincategory` VALUES (1,6,1,1),(1,13,1,1),(1,14,1,1),(1,15,1,1),(1,16,1,1),(2,6,NULL,NULL),(2,13,NULL,NULL),(2,14,NULL,NULL),(2,15,NULL,NULL),(3,16,NULL,NULL);
/*!40000 ALTER TABLE `cm_itemincategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colorspace`
--

DROP TABLE IF EXISTS `colorspace`;
CREATE TABLE `colorspace` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Description` varchar(255) default NULL,
  `ShowOnDownload` tinyint(1) default '1',
  `FileLocation` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `colorspace`
--

LOCK TABLES `colorspace` WRITE;
/*!40000 ALTER TABLE `colorspace` DISABLE KEYS */;
INSERT INTO `colorspace` VALUES (1,'RGB',1,'WEB-INF/manager-config/color-profiles/RGB/sRGB.icm'),(2,'CMYK',0,'WEB-INF/manager-config/color-profiles/CMYK/USWebCoatedSWOP.icc'),(3,'Greyscale',0,'WEB-INF/manager-config/color-profiles/Greyscale/ISOcoated_v2_grey1c_bas.icc');
/*!40000 ALTER TABLE `colorspace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercialoption`
--

DROP TABLE IF EXISTS `commercialoption`;
CREATE TABLE `commercialoption` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  `Description` mediumtext,
  `Price` int(11) default NULL,
  `Terms` mediumtext,
  `IsDisabled` tinyint(1) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commercialoption`
--

LOCK TABLES `commercialoption` WRITE;
/*!40000 ALTER TABLE `commercialoption` DISABLE KEYS */;
/*!40000 ALTER TABLE `commercialoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercialoptionpurchase`
--

DROP TABLE IF EXISTS `commercialoptionpurchase`;
CREATE TABLE `commercialoptionpurchase` (
  `CommercialOptionId` bigint(20) default NULL,
  `PriceBandId` bigint(20) NOT NULL,
  `ABOrderId` int(11) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `Price` int(11) default NULL,
  `Description` mediumtext,
  PRIMARY KEY  (`PriceBandId`,`ABOrderId`,`AssetId`),
  KEY `IX_Relationship145` (`CommercialOptionId`),
  CONSTRAINT `Relationship145` FOREIGN KEY (`CommercialOptionId`) REFERENCES `commercialoption` (`Id`),
  CONSTRAINT `Relationship146` FOREIGN KEY (`PriceBandId`, `ABOrderId`, `AssetId`) REFERENCES `assetpurchasepriceband` (`PriceBandId`, `ABOrderId`, `AssetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commercialoptionpurchase`
--

LOCK TABLES `commercialoptionpurchase` WRITE;
/*!40000 ALTER TABLE `commercialoptionpurchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `commercialoptionpurchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contentpurpose`
--

DROP TABLE IF EXISTS `contentpurpose`;
CREATE TABLE `contentpurpose` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contentpurpose`
--

LOCK TABLES `contentpurpose` WRITE;
/*!40000 ALTER TABLE `contentpurpose` DISABLE KEYS */;
INSERT INTO `contentpurpose` VALUES (1,'Extra Content Page');
/*!40000 ALTER TABLE `contentpurpose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `counter`
--

DROP TABLE IF EXISTS `counter`;
CREATE TABLE `counter` (
  `Code` varchar(50) NOT NULL,
  `Value` bigint(20) NOT NULL,
  PRIMARY KEY  (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `counter`
--

LOCK TABLES `counter` WRITE;
/*!40000 ALTER TABLE `counter` DISABLE KEYS */;
INSERT INTO `counter` VALUES ('BluebooksECName',0),('CustomListIdentifier',0),('DownloadId',0),('PurchaseId',0),('Test',0);
/*!40000 ALTER TABLE `counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  `SequenceNumber` int(11) default NULL,
  `NativeName` varchar(255) default NULL,
  `Code` varchar(2) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Afghanistan',10,NULL,'AF'),(2,'Albania',10,NULL,'AL'),(3,'Algeria',10,NULL,'DZ'),(4,'Andorra',10,NULL,'AD'),(5,'Angola',10,NULL,'AO'),(6,'Antigua and Barbuda',10,NULL,'AG'),(7,'Argentina',10,NULL,'AR'),(8,'Armenia',10,NULL,'AM'),(9,'Australia',10,NULL,'AU'),(10,'Austria',10,NULL,'AT'),(11,'Azerbaijan',10,NULL,'AZ'),(12,'Bahamas',10,NULL,'BS'),(13,'Bahrain',10,NULL,'BH'),(14,'Bangladesh',10,NULL,'BD'),(15,'Barbados',10,NULL,'BB'),(16,'Belarus',10,NULL,'BY'),(17,'Belgium',10,NULL,'BE'),(18,'Belize',10,NULL,'BZ'),(19,'Benin',10,NULL,'BJ'),(20,'Bhutan',10,NULL,'BT'),(21,'Bolivia',10,NULL,'BO'),(22,'Bosnia and Herzegovina',10,NULL,'BA'),(23,'Botswana',10,NULL,'BW'),(24,'Brazil',10,NULL,'BR'),(25,'Brunei',10,NULL,'BN'),(26,'Bulgaria',10,NULL,'BG'),(27,'Burkina Faso',10,NULL,'BF'),(28,'Burundi',10,NULL,'BI'),(29,'Cambodia',10,NULL,'KH'),(30,'Cameroon',10,NULL,'CM'),(31,'Canada',10,NULL,'CA'),(32,'Cape Verde',10,NULL,'CV'),(33,'Central African Republic',10,NULL,'CF'),(34,'Chad',10,NULL,'ID'),(35,'Chile',10,NULL,'CL'),(36,'China',10,NULL,'CN'),(37,'Colombia',10,NULL,'CO'),(38,'Comoros',10,NULL,'KM'),(39,'Congo',10,NULL,'CG'),(40,'Congo, Democratic Republic of the',10,NULL,'CD'),(41,'Costa Rica',10,NULL,'CR'),(42,'Croatia',10,NULL,'HR'),(43,'Cuba',10,NULL,'CU'),(44,'Cyprus',10,NULL,'CY'),(45,'Czech Republic',10,NULL,'CZ'),(46,'Cote d Ivoire',10,NULL,'CI'),(47,'Denmark',10,NULL,'DK'),(48,'Djibouti',10,NULL,'DJ'),(49,'Dominica',10,NULL,'DM'),(50,'Dominican Republic',10,NULL,'DO'),(51,'East Timor',10,NULL,'TP'),(52,'Ecuador',10,NULL,'EC'),(53,'Egypt',10,NULL,'EG'),(54,'El Salvador',10,NULL,'SV'),(55,'Equatorial Guinea',10,NULL,'GQ'),(56,'Eritrea',10,NULL,'ER'),(57,'Estonia',10,NULL,'EE'),(58,'Ethiopia',10,NULL,'ET'),(59,'Fiji',10,NULL,'FJ'),(60,'Finland',10,NULL,'FI'),(61,'France',10,NULL,'FR'),(62,'Gabon',10,NULL,'GA'),(63,'Gambia, The',10,NULL,'GM'),(64,'Georgia',10,NULL,'GE'),(65,'Germany',10,NULL,'DE'),(66,'Ghana',10,NULL,'GH'),(67,'Greece',10,NULL,'GR'),(68,'Grenada',10,NULL,'GD'),(69,'Guatemala',10,NULL,'GT'),(70,'Guinea',10,NULL,'GN'),(71,'Guinea-Bissau',10,NULL,'GW'),(72,'Guyana',10,NULL,'GY'),(73,'Haiti',10,NULL,'HT'),(74,'Honduras',10,NULL,'HN'),(75,'Hungary',10,NULL,'HU'),(76,'Iceland',10,NULL,'IS'),(77,'India',10,NULL,'IN'),(78,'Indonesia',10,NULL,'ID'),(79,'Iran',10,NULL,'IR'),(80,'Iraq',10,NULL,'IQ'),(81,'Ireland',10,NULL,'IE'),(82,'Israel',10,NULL,'IL'),(83,'Italy',10,NULL,'IT'),(84,'Jamaica',10,NULL,'JM'),(85,'Japan',10,NULL,'JP'),(86,'Jordan',10,NULL,'JO'),(87,'Kazakhstan',10,NULL,'KZ'),(88,'Kenya',10,NULL,'KE'),(89,'Kiribati',10,NULL,'KI'),(90,'Korea, North',10,NULL,'KP'),(91,'Korea, South',10,NULL,'KR'),(92,'Kuwait',10,NULL,'KW'),(93,'Kyrgyzstan',10,NULL,'KG'),(94,'Laos',10,NULL,'LA'),(95,'Latvia',10,NULL,'LV'),(96,'Lebanon',10,NULL,'LB'),(97,'Lesotho',10,NULL,'LS'),(98,'Liberia',10,NULL,'LR'),(99,'Libya',10,NULL,'LY'),(100,'Liechtenstein',10,NULL,'LI'),(101,'Lithuania',10,NULL,'LT'),(102,'Luxembourg',10,NULL,'LU'),(103,'Macedonia, Former Yugoslav Republic of',10,NULL,'MK'),(104,'Madagascar',10,NULL,'MG'),(105,'Malawi',10,NULL,'MW'),(106,'Malaysia',10,NULL,'MY'),(107,'Maldives',10,NULL,'MV'),(108,'Mali',10,NULL,'ML'),(109,'Malta',10,NULL,'MT'),(110,'Marshall Islands',10,NULL,'MH'),(111,'Mauritania',10,NULL,'MR'),(112,'Mauritius',10,NULL,'MU'),(113,'Mexico',10,NULL,'MX'),(114,'Micronesia, Federated States of',10,NULL,'FM'),(115,'Moldova',10,NULL,'MD'),(116,'Monaco',10,NULL,'MC'),(117,'Mongolia',10,NULL,'MN'),(118,'Morocco',10,NULL,'MA'),(119,'Mozambique',10,NULL,'MZ'),(120,'Myanmar',10,NULL,'MM'),(121,'Namibia',10,NULL,'MA'),(122,'Nauru',10,NULL,'NR'),(123,'Nepal',10,NULL,'NP'),(124,'Netherlands',10,NULL,'AN'),(125,'New Zealand',10,NULL,'NZ'),(126,'Nicaragua',10,NULL,'NI'),(127,'Niger',10,NULL,'ME'),(128,'Nigeria',10,NULL,'NG'),(129,'Norway',10,NULL,'NO'),(130,'Oman',10,NULL,'OM'),(131,'Pakistan',10,NULL,'PK'),(132,'Palau',10,NULL,'PW'),(133,'Panama',10,NULL,'PA'),(134,'Papua New Guinea',10,NULL,'PG'),(135,'Paraguay',10,NULL,'PY'),(136,'Peru',10,NULL,'PE'),(137,'Philippines',10,NULL,'PH'),(138,'Poland',10,NULL,'PL'),(139,'Portugal',10,NULL,'PT'),(140,'Qatar',10,NULL,'QA'),(141,'Romania',10,NULL,'RO'),(142,'Russia',10,NULL,'RU'),(143,'Rwanda',10,NULL,'RW'),(144,'Saint Kitts and Nevis',10,NULL,'KN'),(145,'Saint Lucia',10,NULL,'LC'),(146,'Saint Vincent and The Grenadines',10,NULL,'VC'),(147,'Samoa',10,NULL,'WS'),(148,'San Marino',10,NULL,'SM'),(149,'Sao Tome and Principe',10,NULL,'ST'),(150,'Saudi Arabia',10,NULL,'SA'),(151,'Senegal',10,NULL,'SN'),(152,'Serbia and Montenegro',10,NULL,'RS'),(153,'Seychelles',10,NULL,'SC'),(154,'Sierra Leone',10,NULL,'SL'),(155,'Singapore',10,NULL,'SG'),(156,'Slovakia',10,NULL,'SK'),(157,'Slovenia',10,NULL,'SI'),(158,'Solomon Islands',10,NULL,'SB'),(159,'Somalia',10,NULL,'SO'),(160,'South Africa',10,NULL,'ZA'),(161,'Spain',10,NULL,'ES'),(162,'Sri Lanka',10,NULL,'LK'),(163,'Sudan',10,NULL,'SD'),(164,'Suriname',10,NULL,'SR'),(165,'Swaziland',10,NULL,'SZ'),(166,'Sweden',10,NULL,'SE'),(167,'Switzerland',10,NULL,'CH'),(168,'Syria',10,NULL,'SY'),(169,'Taiwan',10,NULL,'TW'),(170,'Tajikistan',10,NULL,'TJ'),(171,'Tanzania',10,NULL,'TZ'),(172,'Thailand',10,NULL,'TH'),(173,'Togo',10,NULL,'TG'),(174,'Tonga',10,NULL,'TO'),(175,'Trinidad and Tobago',10,NULL,'TT'),(176,'Tunisia',10,NULL,'TN'),(177,'Turkey',10,NULL,'TR'),(178,'Turkmenistan',10,NULL,'TM'),(179,'Tuvalu',10,NULL,'TV'),(180,'Uganda',10,NULL,'UG'),(181,'Ukraine',10,NULL,'UA'),(182,'United Arab Emirates',10,NULL,'AE'),(183,'United Kingdom',10,NULL,'GB'),(184,'United States',10,NULL,'US'),(185,'Uruguay',10,NULL,'UY'),(186,'Uzbekistan',10,NULL,'UZ'),(187,'Vanuatu',10,NULL,'VU'),(188,'Vatican City',10,NULL,'VA'),(189,'Venezuela',10,NULL,'VE'),(190,'Vietnam',10,NULL,'VN'),(191,'Western Sahara',10,NULL,'EH'),(192,'Yemen',10,NULL,'YE'),(193,'Zambia',10,NULL,'ZM'),(194,'Zimbabwe',10,NULL,'ZW'),(207,'Hong Kong',10,NULL,'HK'),(208,'Puerto Rico',10,NULL,'PR'),(300,'Dubai',10,NULL,'AE');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countryintaxregion`
--

DROP TABLE IF EXISTS `countryintaxregion`;
CREATE TABLE `countryintaxregion` (
  `TaxRegionId` bigint(20) NOT NULL,
  `CountryId` bigint(20) NOT NULL,
  PRIMARY KEY  (`TaxRegionId`,`CountryId`),
  KEY `IX_Relationship118` (`CountryId`),
  KEY `IX_Relationship117` (`TaxRegionId`),
  CONSTRAINT `Relationship117` FOREIGN KEY (`TaxRegionId`) REFERENCES `taxregion` (`Id`),
  CONSTRAINT `Relationship118` FOREIGN KEY (`CountryId`) REFERENCES `country` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `countryintaxregion`
--

LOCK TABLES `countryintaxregion` WRITE;
/*!40000 ALTER TABLE `countryintaxregion` DISABLE KEYS */;
/*!40000 ALTER TABLE `countryintaxregion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customfield`
--

DROP TABLE IF EXISTS `customfield`;
CREATE TABLE `customfield` (
  `FieldName` varchar(100) default NULL,
  `UsageTypeId` bigint(20) NOT NULL,
  `TypeId` bigint(20) NOT NULL,
  `Id` bigint(20) NOT NULL auto_increment,
  `Required` tinyint(1) default '0',
  `OrgUnitId` bigint(20) default NULL,
  `SequenceNumber` int(11) NOT NULL default '999',
  `IsSubtype` tinyint(1) NOT NULL default '0',
  `LdapProperty` varchar(255) default NULL,
  `ShowOnProfile` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship293` (`OrgUnitId`),
  KEY `IX_Relationship190222` (`UsageTypeId`),
  KEY `IX_Relationship187222` (`TypeId`),
  CONSTRAINT `Relationship187222` FOREIGN KEY (`TypeId`) REFERENCES `customfieldtype` (`Id`),
  CONSTRAINT `Relationship190222` FOREIGN KEY (`UsageTypeId`) REFERENCES `customfieldusagetype` (`Id`),
  CONSTRAINT `Relationship293` FOREIGN KEY (`OrgUnitId`) REFERENCES `orgunit` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customfield`
--

LOCK TABLES `customfield` WRITE;
/*!40000 ALTER TABLE `customfield` DISABLE KEYS */;
INSERT INTO `customfield` VALUES ('Telephone',1,1,1001,0,NULL,999,0,NULL,1),('Fax',1,1,1002,0,NULL,999,0,NULL,1),('Website',1,1,1003,0,NULL,999,0,NULL,1);
/*!40000 ALTER TABLE `customfield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customfieldtype`
--

DROP TABLE IF EXISTS `customfieldtype`;
CREATE TABLE `customfieldtype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Description` varchar(50) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customfieldtype`
--

LOCK TABLES `customfieldtype` WRITE;
/*!40000 ALTER TABLE `customfieldtype` DISABLE KEYS */;
INSERT INTO `customfieldtype` VALUES (1,'Textfield'),(2,'Textarea'),(3,'Dropdown list'),(4,'Checkbox list');
/*!40000 ALTER TABLE `customfieldtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customfieldusagetype`
--

DROP TABLE IF EXISTS `customfieldusagetype`;
CREATE TABLE `customfieldusagetype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Description` varchar(100) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customfieldusagetype`
--

LOCK TABLES `customfieldusagetype` WRITE;
/*!40000 ALTER TABLE `customfieldusagetype` DISABLE KEYS */;
INSERT INTO `customfieldusagetype` VALUES (1,'Asset Bank User Field');
/*!40000 ALTER TABLE `customfieldusagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customfieldvalue`
--

DROP TABLE IF EXISTS `customfieldvalue`;
CREATE TABLE `customfieldvalue` (
  `Id` bigint(20) NOT NULL auto_increment,
  `CustomFieldId` bigint(20) NOT NULL,
  `Value` varchar(200) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship191222` (`CustomFieldId`),
  CONSTRAINT `Relationship191222` FOREIGN KEY (`CustomFieldId`) REFERENCES `customfield` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customfieldvalue`
--

LOCK TABLES `customfieldvalue` WRITE;
/*!40000 ALTER TABLE `customfieldvalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `customfieldvalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customfieldvaluemapping`
--

DROP TABLE IF EXISTS `customfieldvaluemapping`;
CREATE TABLE `customfieldvaluemapping` (
  `ListValue` bigint(20) default NULL,
  `CustomFieldId` bigint(20) NOT NULL,
  `ItemId` bigint(20) NOT NULL,
  `TextValue` text,
  UNIQUE KEY `UniqueCheck` (`ListValue`,`CustomFieldId`,`ItemId`),
  KEY `IX_Relationship201222` (`CustomFieldId`),
  KEY `IX_Relationship200222` (`ListValue`),
  CONSTRAINT `Relationship200222` FOREIGN KEY (`ListValue`) REFERENCES `customfieldvalue` (`Id`),
  CONSTRAINT `Relationship201222` FOREIGN KEY (`CustomFieldId`) REFERENCES `customfield` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customfieldvaluemapping`
--

LOCK TABLES `customfieldvaluemapping` WRITE;
/*!40000 ALTER TABLE `customfieldvaluemapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `customfieldvaluemapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `displayattribute`
--

DROP TABLE IF EXISTS `displayattribute`;
CREATE TABLE `displayattribute` (
  `DisplayAttributeGroupId` bigint(20) NOT NULL,
  `AttributeId` bigint(20) NOT NULL,
  `DisplayLength` int(11) default NULL,
  `SequenceNumber` int(11) default NULL,
  `ShowLabel` tinyint(1) default '0',
  `IsLink` tinyint(1) default '0',
  `ShowOnChild` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`DisplayAttributeGroupId`,`AttributeId`),
  KEY `IX_DA_Attribute_FK` (`AttributeId`),
  KEY `IX_DA_DAG_FK` (`DisplayAttributeGroupId`),
  CONSTRAINT `DA_Attribute_FK` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `DA_DAG_FK` FOREIGN KEY (`DisplayAttributeGroupId`) REFERENCES `displayattributegroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `displayattribute`
--

LOCK TABLES `displayattribute` WRITE;
/*!40000 ALTER TABLE `displayattribute` DISABLE KEYS */;
INSERT INTO `displayattribute` VALUES (1,2,NULL,1,1,0,0),(1,3,NULL,2,0,0,0),(2,2,NULL,1,1,0,0),(2,3,NULL,2,0,0,0),(3,2,NULL,1,1,0,0),(3,3,NULL,2,0,0,0),(6,2,NULL,1,0,0,0),(6,13,NULL,2,0,0,0),(6,17,NULL,6,0,0,0),(6,18,NULL,7,0,0,0),(6,21,NULL,4,0,0,0),(6,30,NULL,5,0,0,0),(7,1,NULL,1,0,0,0),(7,2,NULL,2,0,0,0),(7,3,NULL,3,0,0,0),(7,4,NULL,4,0,0,0),(7,5,NULL,5,0,0,0),(7,7,NULL,6,0,0,0),(7,8,NULL,7,0,0,0),(7,9,NULL,8,0,0,0),(7,10,NULL,9,0,0,0),(7,11,NULL,10,0,0,0),(7,12,NULL,11,0,0,0),(7,13,NULL,12,0,0,0),(7,17,NULL,16,0,0,0),(7,18,NULL,17,0,0,0),(7,21,NULL,14,0,0,0),(7,30,NULL,15,0,0,0),(7,300,NULL,18,0,0,0),(7,301,NULL,19,0,0,0),(7,400,NULL,20,0,0,0),(7,600,NULL,21,0,0,0),(7,700,NULL,22,0,0,0),(8,1,NULL,1,0,0,0),(8,2,NULL,2,0,0,0),(8,3,NULL,3,0,0,0),(8,4,NULL,4,0,0,0),(8,5,NULL,5,0,0,0),(8,6,NULL,6,0,0,0),(8,7,NULL,7,0,0,0),(8,8,NULL,8,0,0,0),(8,9,NULL,9,0,0,0),(8,10,NULL,10,0,0,0),(8,11,NULL,11,0,0,0),(8,12,NULL,12,0,0,0),(8,13,NULL,13,0,0,0),(8,15,NULL,18,0,0,0),(8,16,NULL,19,0,0,0),(8,17,NULL,20,0,0,0),(8,18,NULL,21,0,0,0),(8,19,NULL,22,0,0,0),(8,21,NULL,14,0,0,0),(8,22,NULL,15,0,0,0),(8,23,NULL,16,0,0,0),(8,30,NULL,17,0,0,0),(8,200,NULL,23,0,0,0),(8,300,NULL,-1,0,0,0),(8,301,NULL,24,0,0,0),(8,400,NULL,-1,0,0,0),(8,600,NULL,-1,0,0,0),(8,700,NULL,25,0,0,0),(10,2,NULL,1,0,0,0),(10,6,NULL,2,0,0,0),(10,8,NULL,3,0,0,0),(10,9,NULL,4,0,0,0),(10,10,NULL,5,0,0,0),(10,11,NULL,6,0,0,0),(10,700,NULL,7,0,0,0);
/*!40000 ALTER TABLE `displayattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `displayattributegroup`
--

DROP TABLE IF EXISTS `displayattributegroup`;
CREATE TABLE `displayattributegroup` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `SequenceNumber` int(11) NOT NULL default '1',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `displayattributegroup`
--

LOCK TABLES `displayattributegroup` WRITE;
/*!40000 ALTER TABLE `displayattributegroup` DISABLE KEYS */;
INSERT INTO `displayattributegroup` VALUES (1,'Search Result',4),(2,'Browse',5),(3,'Lightbox',7),(4,'Category Extensions',9),(5,'Browse List View',6),(6,'Advanced Search',2),(7,'Search Builder',3),(8,'View / Edit',1),(9,'Download Filename',8),(10,'Print Details',10),(11,'Browse Compact View',11),(12,'Publish Filename',12);
/*!40000 ALTER TABLE `displayattributegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division`
--

DROP TABLE IF EXISTS `division`;
CREATE TABLE `division` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `division`
--

LOCK TABLES `division` WRITE;
/*!40000 ALTER TABLE `division` DISABLE KEYS */;
/*!40000 ALTER TABLE `division` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailtemplate`
--

DROP TABLE IF EXISTS `emailtemplate`;
CREATE TABLE `emailtemplate` (
  `TextId` varchar(255) default NULL,
  `LanguageId` bigint(20) NOT NULL,
  `TypeId` bigint(20) NOT NULL,
  `Code` varchar(50) default NULL,
  `AddrFrom` varchar(255) default NULL,
  `AddrTo` varchar(255) default NULL,
  `AddrCc` varchar(255) default NULL,
  `AddrBcc` varchar(255) default NULL,
  `Subject` varchar(255) default NULL,
  `Body` mediumtext,
  `Hidden` tinyint(1) default '0',
  `IsEnabled` tinyint(1) NOT NULL default '1',
  KEY `IX_Relationship156` (`LanguageId`),
  KEY `IX_Relationship198` (`TypeId`),
  CONSTRAINT `Relationship156` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `Relationship198` FOREIGN KEY (`TypeId`) REFERENCES `emailtemplatetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `emailtemplate`
--

LOCK TABLES `emailtemplate` WRITE;
/*!40000 ALTER TABLE `emailtemplate` DISABLE KEYS */;
INSERT INTO `emailtemplate` VALUES ('admin_alert_approvals',1,1,'admin alert approvals','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested approval on assets','#name# has requested approval to download a set of images.<br><br>Their email address: #email#<br><br>Please log in to Asset Bank and go to the Approve Images section to see the request and approve the images.<br><br>IDs of assets requested: #assetids#',0,1),('admin_alert_commercial_order_submitted',1,1,'admin alertcommercial order submitted','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested approval for commercial use.','#greetname#(#username#) has placed an order (#ordernum#) requesting approval for commercial use of the following assets:<br><br>#orderDetails#<br><br>The following usage notes were provided by the user.<br>#UserNotes#<br><br>Please log in to Asset Bank and go to the Orders -> Commercial Orders section to review and approve the order.<br><br>#OfflinePaymentRequest#',0,1),('admin_alert_personal_print_order_submitted',1,1,'admin alert personal print order submitted','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested prints.','#greetname#(#username#) has placed an order (#ordernum#) requesting prints of the following assets:<br><br>#orderDetails#<br><br>Please log in to Asset Bank and go to the Orders -> Personal Orders section to review  the order.<br>When the order has been shipped you can change the status to &#39;Fulfilled&#39;.',0,1),('admin_alert_update_request',1,1,'admin alert update request','noreply@noreply.com','#adminEmailAddresses#;','','','User has requested permissions','This is an automated message generated by Asset Bank.<br><br>A user has requested you to update their permissions.<br><br>Requested Org Unit: #orgunitname#<br><br>User notes: <br>#usernotes#<br><br>You can see the details of this request, in the &#39;Admin > Approve Users&#39; area.',0,1),('admin_alert_upload_approvals',1,1,'admin alert upload approvals','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: items require approval','#name# has uploaded or updated items. The new or updated items require approval.<br><br>Please log in to Asset Bank and go to the Approve Items section to approve the changes.',0,1),('ad_user_approved',1,1,'ad user approved','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','Dear #name#<br><br>You have been approved as a user of the Asset Bank. <br>Your login details are the same as you use to log in to your Windows PC.<br><br>Kind regards,<br>Asset Bank Admin',0,1),('approver_work_done',1,1,'approver work done','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User asset request has been approved','Download approvals for user #name# have been processed, for the following assets:<br>#assetids#',0,1),('assets_approved',1,1,'assets approved','noreply@noreply.com','#email#;','','','Download request processed','Dear #name#<br><br>Your request to download one or more assets has been processed.<br><br>Please log in to Asset Bank and view your lightbox for details.<br><br>Kind regards,<br>Asset Bank Admin',0,1),('asset_attached',1,1,'asset emailed (attached)','noreply@noreply.com','#recipients#;','','','Asset Bank: #filename#','This message was sent to you from #username# via Asset Bank.<br><br>#message#',0,1),('asset_linked',1,1,'asset emailed (linked)','#from#','#recipients#;','','','Asset Bank: #filename#','This message was sent to you from #username# via Asset Bank.<br /><br />#message#<br /><br /><br /> <a href=\"#url#\">Click here to download</a>',0,1),('attribute_rule_email',1,1,'attribute rule email','noreply@noreply.com','#recipients#;','','','Asset Bank Automatic Email','Dear Asset Bank user<br><br>This email has been automatically sent to you by Asset Bank because a date specified on one or more assets has been reached.<br><br>The asset attribute is: #rule_attribute#.<br>The rule that has triggered is: #rule_name#.<br>Message:<br>#rule_message#<br><br>Asset IDs:<br>#asset_list#<br><br>Regards,<br>Asset Bank Admin',0,1),('commercial_option_changed',1,1,'commercial option changed','noreply@noreply.com','#email#;','','','Assetbank: alteration to request for commercial use of image/s','Dear #username#<br><br>IMPORTANT INFORMATION ABOUT YOUR REQUEST FOR COMMERCIAL USE OF IMAGE/S<br><br>Your order number: #ordernum#<br>#assetDetails#<br><br>Having assessed your request, and from the additional information you have provided us, we have felt it necessary to change the commercial option you had initially selected from &#39;#oldOption#&#39;  to &#39;#newOption#&#39;. This has resulted in a change of pricefor the purchase of the image/s.<br><br>You can view the change of use by logging into Assetbank, and proceed to the MY PURCHASES - MY ORDERS section. <br><br>If you do not agree with our assessment of your intended use of the image/s, please contact us and we will be happy to discuss it further with you.<br><br>Your username is: #username#<br><br>Thank you for using Assetbank.co.uk.<br><br>Regards,<br>assetbank.com, admin',0,1),('contact',1,1,'contact','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank Contact','Name: #name#<br>Email: #email#<br>Tel no: #telephone#<br><br>Message:<br>#message#',0,1),('new_watched_files',1,1,'new watched files','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: New files uploaded','New files have been added to the the following directories: #directories#',0,1),('order_approved_pay_offline',1,1,'order approved pay offline','noreply@noreply.com','#email#;','','','Commercial purchase approved.  Offline Payment required.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your commercial purchase order (#ordernum#) placed on #datePlaced# has been approved and now requires off line payment.<br><br>You will be contacted shortly by a member of our admin team to complete payment.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and proceed to the &#39;My Purchases-> My Orders&#39; section to view your approved order.<br>Your username is: #username#<br><br>Your order details, commercial options approved and terms and conditions are <br><br>#orderDetails#<br><br>#terms# <br><br>Thank you for purchasing images from AssetBank.<br><br>Regards,<br>assetbank.com, admin',0,1),('order_approved_pay_online',1,1,'orderapproved pay online','noreply@noreply.com','#email#;','','','Commercial purchase approved.  Online Payment required.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your commercial purchase order (#ordernum#) placed on #datePlaced# has been approved and now requires on line payment.<br><br>Please login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and proceed to the &#39;My Purchases-> My Orders&#39; section to view the order and complete payment.<br>Your username is: #username#<br><br>Your order details and commercial options approved are <br><br>#orderDetails#<br><br>#terms# <br><br>Thank you for purchasing images from AssetBank.<br><br>Regards,<br>assetbank.com, admin',0,1),('order_declined',1,1,'order declined','noreply@noreply.com','#email#;','','','Commercial purchase declined.','Dear #greetname# (#username#)<br><br>Your commercial purchase order (#ordernum#) placed on #datePlaced# has been declined.<br><br>You may be contacted shortly by a member ofour admin team to discuss the issue with your request.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and go to the &#39;My Purchases-> My Orders&#39; section to view the declined order.<br>Your username is: #username#<br><br>Your declined order details, commercial options and terms and conditions are <br><br>#orderDetails#<br><br>#terms# <br><br>Regards,<br>assetbank.com, admin',0,1),('order_shipped_confirmation',1,1,'order shipped confirmation','noreply@noreply.com','#email#;','','','Your order has been shipped.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your order (#ordernum#) placed on #datePlaced# has been shipped to<br><br>#address#<br><br>Your order details are<br><br>#orderDetails#<br><br>Thank you for purchasing images from Asset Bank.<br><br>This is a test email from Asset Bank. The text of allemails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin',0,1),('password_change',1,1,'password change','noreply@noreply.com','#email#;','','','Your Asset Bank password has been reset','Dear #name#<br><br>Your password for the Asset Bank application has been changed. <br>When you next log in, please use password: #newPassword#<br><br>Please note that your username (#username#) has not been changed.<br><br>Kind regards,<br>Asset Bank Admin',0,1),('password_reminder',1,1,'password reminder','noreply@noreply.com','#email#;','','','Your Asset Bank password','Dear #name#<br><br>Here are the details to reset your Asset Bank password.<br><br>You now need to follow the link below to reset your password and set a new one:<br><a href=\"#baseUrl#/#link#=#authId#\">#baseUrl#/#link#=#authId#</a><br><br>Kind Regards,<br>Asset Bank Admin',0,1),('purchase_confirmation',1,1,'purchase confirmation','noreply@noreply.com','#email#;','','','Asset Bank Payment Confirmation','Dear #greetname# (#username#)<br><br>This email confirms your purchase of assets from Asset Bank.<br>Order/Purchase ID: #ordernum#  <br><br>Your order/purchase details are<br>#orderDetails#<br><br>#purchaseCommercialReceiptNote#<br><br>#purchaseDownloadInstructions#<br>#purchasePrintInstructions#<br><br>Your username is: #username#<br>Your password: #password#<br><br>To view the terms and conditions of your purchase go to #purchaseTermsLink#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>AssetBank Admin',0,1),('requestAssetbox',1,1,'requestAssetbox','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: Items requested on CD','-- Contact details --<br>Name: #name#<br>Division: #division#<br>Email: #email#<br>#customFieldName#: #customField#<br>Address:<br>#address#<br>Additional comments:<br>#message#<br><br>-- Requested items --<br>The user has requested the following assets on CD: <br>Assets the user has permission to download: #downloadableIds#',0,1),('subscription_confirmation',1,1,'subscription confirmation','noreply@noreply.com','#email#;','','','Asset Bank Subscription Confirmation','Dear #username#<br><br>This email confirms that you purchased the following subscription on Asset Bank.<br><br>Description: #description#<br>Amount: #amount#.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/) to download images. <br>Your username is: #username#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin',0,1),('subscription_and_registration_confirmation',1,1,'subscription and registration confirmation','noreply@noreply.com','#email#;','','','Asset Bank Subscription Confirmation','Dear #username#<br><br>This email confirms that you purchased the following subscription on Asset Bank.<br><br>Description: #description#<br>Amount: #amount#.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/) to download images. <br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin',0,1),('subscription_expiry_alert',1,1,'subscription expiry alert','noreply@noreply.com','#email#;','','','Subscription expiry alert','Dear #name#<br><br>The following subscription to Asset Bank will expire in #days_left# day(s): <br>#description#<br><br>Your username is: #username#<br><br>Kind regards,<br>Asset Bank Admin',0,1),('user_added_by_admin',1,1,'user added by admin','noreply@noreply.com','#email#;','','','You are now an Asset Bank user!','Dear #name#<br><br>You have been added as a user of Asset Bank. <br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br>You can change your password after logging in.<br><br>Kind regards,<br>Asset Bank Admin',0,1),('user_approved',1,1,'user approved','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','<p>Dear #name#<br /><br />You have been approved as a user of Asset Bank. <br /><br />You now need to follow the link below to complete your registration process and set a secure password:<br /><a href=\"#baseUrl#/#link#=#authId#\" target=\"_blank\">Click here &gt;&gt;</a><br /><br />#adminMessage#<br /><br />Kind regards,<br />Asset Bank Admin</p>',0,1),('user_message',1,1,'user message','noreply@noreply.com','#email#','','','#subject#','#body#',0,1),('user_registered',1,1,'user registered','noreply@noreply.com','#adminEmailAddresses#;','','','New user registration','This is an automated message generated by Asset Bank.<br><br>A user has registered to use the Asset Bank.<br><br>#orgunitname# <br><br>You can see their details, and approve or reject their application, in the &#39;Admin > Approve Users&#39; area.',0,1),('user_rejected',1,1,'user rejected','noreply@noreply.com','#email#;','','','Your Asset Bank registration has not been approved','Dear #name#<br><br>Sorry, your application to become a user of Asset Bank has not been approved.<br><br>#adminMessage#<br><br>Kind regards,<br>Asset Bank Admin',0,1),('user_updated',1,1,'user updated','noreply@noreply.com','#email#;','','','Your Asset Bank permissions have been updated','Dear #name#<br><br>You recently requested your Asset Bank permissions to be updated. <br><br>The administrator has now updated your permissions.<br><br>Message: #adminMessage#<br><br>Kind regards,<br>Asset Bank Admin',0,1),('send_report',1,1,'send report','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Automated Report Generated: #reportdescription#','The report: #reportdescription#, has been generated automatically from Asset Bank.<br><br>Please find the report attached to this email.<br><br>To disable report generation please login to the admin area on asset-bank.<br><br>Kind regards,<br>Asset Bank Admin',0,1),('send_report_no_data',1,1,'send report no data','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Automated Report Generated','A report has been generated automatically from Asset Bank.<br><br>There was no data available for the requested report.<br><br>To disable report generation please login to the admin area on asset-bank.<br><br>Kind regards,<br>Asset Bank Admin',0,1),('shared_assetbox',1,1,'shared assetbox','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: You\'ve been granted access to a shared #lightboxname#','Dear #greetname# (#username#)<br><br>You have been granted access to a shared #lightboxname#.<br><br>To view the #lightboxname# click the link below:<br><br>#url#<br>#message#<br>Kind regards,<br>Asset Bank Admin',0,1),('user_updated_admin',1,1,'user details updated by admin','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Your details have been updated','Dear #greetname# (#username#)<br><br>Your user details have been updated by an admin user.<br><br>#message#<br><br>Kind regards,<br>Asset Bank Admin',0,1),('user_registered_admin',1,1,'user registered admin','noreply@noreply.com','#adminEmailAddresses#','','','An Asset Bank user has registered','The following Asset Bank user has just registered:<br/><br/>Id: #id#<br/>Name: #name#<br/>Username: #username#<br/>Password: #password#<br/>Email: #email#<br/><br/>',0,1),('repurposed_image_updated',1,1,'repurposed image updated','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Please check embedded image','<p>Dear #name# (#username#)&nbsp;</p><p>On #date# you created an embeddable version of an image file in Asset Bank. This file has recently been replaced, and while the version you created is still usable, it may no longer be relevant. </p><p>Links are provided below in order that you can check that the version you created against the current image file in Asset Bank. If necessary, you can create a new embeddable version of the current image, in which case you will also have to update any external links using the previous embaddable version. </p><p>The embeddable image you created can be seen here:<br />#versionUrl#<br /></p><p>The current image can be seen here:<br />#assetUrl#<br /></p><p>Please contact an admin user if you have any queries regarding this email.</p><p>Kind regards,<br />Asset Bank Admin</p>',0,1),('workflow_asset_approved',1,1,'workflow asset approved','noreply@noreply.com','#emailAddresses#;','','','Asset Bank: asset approved','<p>Dear #name#<br /><br />The following asset you uploaded has been approved:</p><p>#assetDescription#</p><p>Kind regards,<br />Asset Bank Admin</p>',0,1),('workflow_asset_rejected',1,1,'workflow asset rejected','noreply@noreply.com','#emailAddresses#;','','','Asset Bank: asset rejected','<p>Dear #name#<br /><br />The following asset you uploaded has been rejected. Please log in to Asset Bank and go to the Upload Submitted screen to review:</p><p>#assetDescription#</p><p>Message:<br/>#message#</p><p>Kind regards,<br />Asset Bank Admin</p>',0,1),('invitation_to_register',1,1,'invitation to register','noreply@noreply.com','#email#;',NULL,NULL,'Asset Bank Invitation','<p>You have been invited to join Asset Bank. Please click the link below to register: </p><p>#url#</p><p>The user who invited you included this message: </p><p>#message#</p><p>Kind regards,<br />Asset Bank Admin</p>',0,1),('saved_search_alert_email',1,1,'saved search alert email','noreply@noreply.com','#recipients#;','','','New Assets in Saved Searches','Dear Asset Bank user<br><br>This email has been automatically sent to you by Asset Bank because the following searches that you have saved have new assets available:<br><br>#searches#<br><br>Regards,<br>Asset Bank Admin',0,1),('additional_user_approval',1,1,'additional user approval','noreply@noreply.com','#recipients#;','','','User requesting approval','Dear #name#<br><br>This email has been automatically sent to you by Asset Bank because the following user has registered and requested that you authorise them :<br><br>Forename: #forename#<br>Surname: #surname#<br>Email Address: #email#<br>Username: #username#<br><br>To approve this user please click the link below:<br>#approve#<br><br>To reject this user please click the link below:<br>#reject#<br><br>Regards,<br>Asset Bank Admin',0,1),('email_this_page',1,1,'email this page','noreply@noreply.com','','','','A page you might be interested in','Dear [recipient name],<br /><br />I thought you might be interested in the following page:<br /><br /><a href=\"#url#\">#link#</a><br /><br />Regards,<br />#name#',0,1),('user_reactivation',1,1,'user reactivation','noreply@noreply.com','#email#;','','','Your Asset Bank account will expire soon','Dear #name#,<br /><br />You have not logged into your Asset Bank account for #notLoggedInFor# days. Please click the link below to reactivate your account. If you have not reactivated your account within #reactivationPeriod# days it will be deleted.<br /><br /><a href=\"#url#\">Reactivate your account</a><br /><br />Regards,<br />Asset Bank Admin',0,1),('user_deleted_admin',1,1,'user deleted by admin','imagebank@bright-interactive.com','#recipients#;',NULL,NULL,'Asset Bank: Your account has been deleted','Dear #greetname# (#username#)<br><br>Your account has been deleted. The admin user included this message:<br><br>#message#<br><br>Kind regards,<br>Asset Bank Admin',0,1),('ecommerce_user_registered',1,1,'ecommerce user registered','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','Dear #name#<br><br>You have been approved as a user of Asset Bank. <br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br><br>You can change your password after logging in: click &#39;Your Profile&#39; in the top right of the screen, then click &#39;change your password&#39;.<br><br>Kind regards,<br>Asset Bank Admin',1,0),('admin_alert_user_feedback',1,1,'admin alert user feedback','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has submitted feedback','This is an automated message generated by Asset Bank.<br><br>A user has submitted feedback. <br><br>Username: #username#<br>User Email Address: #email#<br>Asset ID: #assetid#<br><br>User Notes:<br> #message#',0,1),('user_feedback_confirmation',1,1,'user feedback confirmation','noreply@noreply.com','#email#;','','','Assetbank: User asset feedback','Dear #username# <br><br> This email confirms that your feedback has been submitted successfully.<br><br>Username: #username#<br>Asset ID: #assetid#<br><br>User Notes:<br> #message# <br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin',0,1),('workflow_asset_approved_multiple',1,1,'workflow assets approved','noreply@noreply.com','#emailAddresses#;','','','Asset Bank: assets approved','<p>Dear #name#<br /><br />The following assets you uploaded have been approved:</p><p>#description#</p><p>Kind regards,<br />Asset Bank Admin</p>',0,1),('workflow_asset_rejected_multiple',1,1,'workflow assets rejected','noreply@noreply.com','#emailAddresses#;','','','Asset Bank: assets rejected','<p>Dear #name#<br /><br />The following assets you uploaded have been rejected. Please log in to Asset Bank and go to the Upload > My Uploads screen to review:</p><p>#description#</p><p>Kind regards,<br />Asset Bank Admin</p>',0,1),('send_ecard',1,1,'send as e-card','#from#','#email#;','','','#company-name# e-card','<body style=\"background:#009BC6;\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#009BC6\"><tr height=\"20\"><td height=\"20\">&nbsp;</td></tr><tr><td><table width=\"700\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"><tr><td colspan=\"3\"><img src=\"#url#/images/standard/misc/ecard_header.gif\" width=\"700\" height=\"30\" style=\"display:block;\"></td></tr><tr><td bgcolor=\"#ffffff\" width=\"30\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"30\" height=\"1\" style=\"display:block;\"></td><td bgcolor=\"#ffffff\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><a href=\"#url#\"><img src=\"#url#/images/standard/asset_bank_logo.gif\" alt=\"#company-name#\" border=\"0\"></a></td></tr><tr><td height=\"15\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"15\" style=\"display:block;\"></td></tr><tr><td height=\"5\" bgcolor=\"#009BC6\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"5\" style=\"display:block;\"></td></tr><tr><td height=\"15\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"15\" style=\"display:block;\"></td></tr><tr><td><p style=\"font-family:Arial, sans-serif; font-size:16px; margin-top:0px;\">#name# has sent you an image from the <a href=\"#url#\" style=\"color:#009BC6; text-decoration:none;\">#company-name#</a> site.</p><p style=\"font-family:Arial, sans-serif; font-size:13px; margin-top:0px;\"><a href=\"#url#\"style=\"color:#009BC6; text-decoration:none;\">#url#.</a></p></td></tr><tr><td height=\"15\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"15\" style=\"display:block;\"></td></tr><tr><td><table bgcolor=\"#afacac\" cellpadding=\"4\" cellspacing=\"0\" align=\"center\"><tr><td><img src=\"#image#\" style=\"display:block;\"></td></tr></table></td></tr><tr><td height=\"20\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"20\" style=\"display:block;\"></td></tr><tr><td><table cellpadding=\"0\" cellspacing=\"0\" align=\"center\"><tr><td height=\"25\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"25\" style=\"display:block;\"></td><td width=\"54\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"20\" style=\"display:block;\"><p style=\"line-height:10px; color:#009BC6; font-family:Georgia, Times, serif; font-size:60px; margin-top:0px; font-weight:bold;\">&#8220;</p></td><td valign=\"top\"><p style=\"font-family:Arial,sans-serif;font-size:15px;margin-top:0px;font-weight:bold;\">#message#</p></td><td width=\"54\" valign=\"bottom\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"20\" style=\"display:block;\"><p style=\"line-height:10px; color:#009BC6; font-family:Georgia, Times, serif; font-size:60px; margin-top:0px; font-weight:bold; text-align:right;\">&#8221;</p></td></tr></table></td></tr><tr><td height=\"20\" align=\"center\"><a href=\"#url#\"><img src=\"#url#/images/standard/misc/ecard_button.gif\" width=\"157\" height=\"42\" border=\"0\" style=\"display:block;\"></a></td></tr></table></td><td bgcolor=\"#ffffff\" width=\"30\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"30\" height=\"1\" style=\"display:block;\"></td></tr><tr><td colspan=\"3\"><img src=\"#url#/images/standard/misc/ecard_footer.gif\" width=\"700\" height=\"30\" style=\"display:block;\"></td></tr><tr><td colspan=\"3\" height=\"10\"><img src=\"#url#/images/standard/misc/x.gif\" width=\"1\" height=\"10\" style=\"display:block;\"></td></tr><tr><td colspan=\"3\"><p style=\"font-family:Arial,sans-serif; font-size:11px; margin-top:0px; font-weight:bold; color:#fff;\">&copy; 2011 <a href=\"www.bright-interactive.com\" style=\"color:#fff; text-decoration:none;\">BrightInteractive</a>.&nbsp;&nbsp;<a href=\"#url#/action/viewConditions\"style=\"color:#fff; text-decoration:none;\">Terms &amp;Conditions</a> &nbsp;| &nbsp;<a href=\"#url#/action/viewPrivacy\" style=\"color:#fff; text-decoration:none;\">Privacy Policy</a>&nbsp;|&nbsp;<a href=\"#url#/action/viewAbout\" style=\"color:#fff; text-decoration:none;\">About Asset Bank</a></p></td></tr></table></td></tr></table></body>',0,1),('request_requires_approval',1,1,'request requires approval','noreply@noreply.com','#toAddress#;','','','Asset Bank: Request requires approval','#requestUserName# has submitted an asset request for fulfillment. The request requires approval before it can be assigned a fulfiller.<br><br>Please log in to Asset Bank and go to the Requests section to approve the request.',0,1),('request_requires_fulfillment',1,1,'request requires fulfillment','noreply@noreply.com','#toAddress#;','','','Asset Bank: Request requires fulfillment','#requestUserName# has submitted an asset request for fulfillment.<br><br>Please log in to Asset Bank and go to the Requests section to fulfill the request.',0,1),('request_fulfilled',1,1,'request fulfilled','noreply@noreply.com','#toAddress#;','','','Asset Bank: Request fulfilled','A request that you submitted has been fulfilled.<br><br>Please log in to Asset Bank and go to the Requests section to view your fulfilled request.',0,1);
/*!40000 ALTER TABLE `emailtemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailtemplatetype`
--

DROP TABLE IF EXISTS `emailtemplatetype`;
CREATE TABLE `emailtemplatetype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `emailtemplatetype`
--

LOCK TABLES `emailtemplatetype` WRITE;
/*!40000 ALTER TABLE `emailtemplatetype` DISABLE KEYS */;
INSERT INTO `emailtemplatetype` VALUES (1,'General'),(2,'Marketing');
/*!40000 ALTER TABLE `emailtemplatetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `embeddeddatamapping`
--

DROP TABLE IF EXISTS `embeddeddatamapping`;
CREATE TABLE `embeddeddatamapping` (
  `EmbeddedDataValueId` bigint(20) NOT NULL,
  `AttributeId` bigint(20) NOT NULL,
  `MappingDirectionId` bigint(20) NOT NULL,
  `DataDelim` varchar(50) default NULL,
  `Sequence` int(11) default NULL,
  `IsBinary` tinyint(1) default '0',
  PRIMARY KEY  (`EmbeddedDataValueId`,`AttributeId`,`MappingDirectionId`),
  KEY `IX_Relationship163` (`AttributeId`),
  KEY `IX_Relationship1642` (`EmbeddedDataValueId`),
  KEY `IX_Relationship1652` (`MappingDirectionId`),
  CONSTRAINT `Relationship163` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `Relationship1642` FOREIGN KEY (`EmbeddedDataValueId`) REFERENCES `embeddeddatavalue` (`Id`),
  CONSTRAINT `Relationship1652` FOREIGN KEY (`MappingDirectionId`) REFERENCES `mappingdirection` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `embeddeddatamapping`
--

LOCK TABLES `embeddeddatamapping` WRITE;
/*!40000 ALTER TABLE `embeddeddatamapping` DISABLE KEYS */;
INSERT INTO `embeddeddatamapping` VALUES (12,7,1,NULL,NULL,0),(603,13,1,NULL,NULL,0);
/*!40000 ALTER TABLE `embeddeddatamapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `embeddeddatatype`
--

DROP TABLE IF EXISTS `embeddeddatatype`;
CREATE TABLE `embeddeddatatype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `embeddeddatatype`
--

LOCK TABLES `embeddeddatatype` WRITE;
/*!40000 ALTER TABLE `embeddeddatatype` DISABLE KEYS */;
INSERT INTO `embeddeddatatype` VALUES (1,'EXIF'),(2,'IPTC'),(3,'XMP'),(4,'Photoshop'),(5,'File'),(6,'[Any]'),(7,'[Other]');
/*!40000 ALTER TABLE `embeddeddatatype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `embeddeddatavalue`
--

DROP TABLE IF EXISTS `embeddeddatavalue`;
CREATE TABLE `embeddeddatavalue` (
  `Id` bigint(20) NOT NULL auto_increment,
  `EmbeddedDataTypeId` bigint(20) NOT NULL,
  `Name` varchar(100) default NULL,
  `Expression` varchar(250) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship15822` (`EmbeddedDataTypeId`),
  CONSTRAINT `Relationship15822` FOREIGN KEY (`EmbeddedDataTypeId`) REFERENCES `embeddeddatatype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `embeddeddatavalue`
--

LOCK TABLES `embeddeddatavalue` WRITE;
/*!40000 ALTER TABLE `embeddeddatavalue` DISABLE KEYS */;
INSERT INTO `embeddeddatavalue` VALUES (1,1,'Artist','Artist'),(2,1,'BitsPerSample','BitsPerSample'),(3,1,'Compression','Compression'),(4,1,'ColorSpace','ColorSpace'),(5,1,'ComponentsConfiguration','ComponentsConfiguration'),(6,1,'CompressedBitsPerPixel','CompressedBitsPerPixel'),(7,1,'Contrast','Contrast'),(8,1,'Copyright','Copyright'),(9,1,'CustomRendered','CustomRendered'),(10,1,'DateTime','DateTime'),(11,1,'DateTimeDigitized','DateTimeDigitized'),(12,1,'DateTimeOriginal','DateTimeOriginal'),(13,1,'DigitalZoomRatio','DigitalZoomRatio'),(14,1,'ExifVersion','ExifVersion'),(15,1,'ExifImageLength','ExifImageLength'),(16,1,'ExifImageWidth','ExifImageWidth'),(17,1,'ExifOffset','ExifOffset'),(18,1,'ExposureBiasValue','ExposureBiasValue'),(19,1,'ExposureMode','ExposureMode'),(20,1,'ExposureProgram','ExposureProgram'),(21,1,'ExposureTime','ExposureTime'),(22,1,'FileSource','FileSource'),(23,1,'Flash','Flash'),(24,1,'FlashpixVersion','FlashpixVersion'),(25,1,'FNumber','FNumber'),(26,1,'FocalLength','FocalLength'),(27,1,'GainControl','GainControl'),(28,1,'JPEGInterchangeFormat','JPEGInterchangeFormat'),(29,1,'JPEGInterchangeFormatLength','JPEGInterchangeFormatLength'),(30,1,'ImageDescription','ImageDescription'),(31,1,'ImageLength','ImageLength'),(32,1,'ImageWidth','ImageWidth'),(33,1,'ImageUniqueID','ImageUniqueID'),(34,1,'InteroperabilityOffset','InteroperabilityOffset'),(35,1,'InteroperabilityIndex','InteroperabilityIndex'),(36,1,'InteroperabilityVersion','InteroperabilityVersion'),(37,1,'ISOSpeedRatings','ISOSpeedRatings'),(38,1,'Orientation','Orientation'),(39,1,'LightSource','LightSource'),(40,1,'Make','Make'),(41,1,'MakerNote','MakerNote'),(42,1,'MaxApertureValue','MaxApertureValue'),(43,1,'MeteringMode','MeteringMode'),(44,1,'Model','Model'),(45,1,'PhotometricInterpretation','PhotometricInterpretation'),(46,1,'PixelXDimension','PixelXDimension'),(47,1,'PixelYDimension','PixelYDimension'),(48,1,'PlanarConfiguration','PlanarConfiguration'),(49,1,'PrimaryChromaticities','PrimaryChromaticities'),(50,1,'PrintImageMatching','PrintImageMatching'),(51,1,'ReferenceBlackWhite','ReferenceBlackWhite'),(52,1,'RelatedSoundFile','RelatedSoundFile'),(53,1,'ResolutionUnit','ResolutionUnit'),(54,1,'RowsPerStrip','RowsPerStrip'),(55,1,'SamplesPerPixel','SamplesPerPixel'),(56,1,'Saturation','Saturation'),(57,1,'SceneCaptureType','SceneCaptureType'),(58,1,'Sharpness','Sharpness'),(59,1,'Software','Software'),(60,1,'StripByteCounts','StripByteCounts'),(61,1,'StripOffsets','StripOffsets'),(62,1,'SubSecTime','SubSecTime'),(63,1,'SubSecTimeDigitized','SubSecTimeDigitized'),(64,1,'SubSecTimeOriginal','SubSecTimeOriginal'),(65,1,'Tainted','Tainted'),(66,1,'TransferFunction','TransferFunction'),(67,1,'UserComment','UserComment'),(68,1,'WhitePoint','WhitePoint'),(69,1,'WhiteBalance','WhiteBalance'),(70,1,'WinXP-Author','WinXP-Author'),(71,1,'WinXP-Comments','WinXP-Comments'),(72,1,'WinXP-Keywords','WinXP-Keywords'),(73,1,'WinXP-Subject','WinXP-Subject'),(74,1,'WinXP-Title','WinXP-Title'),(75,1,'XResolution','XResolution'),(76,1,'YCbCrCoefficients','YCbCrCoefficients'),(77,1,'YCbCrSubSampling','YCbCrSubSampling'),(78,1,'YCbCrPositioning','YCbCrPositioning'),(79,1,'YResolution','YResolution'),(80,2,'Object Type Reference','ObjectTypeReference'),(81,2,'Object Name (Title)','ObjectName'),(82,2,'Edit Status','EditStatus'),(83,2,'Editorial Update','EditorialUpdate'),(84,2,'Urgency','Urgency'),(85,2,'Subject Reference','SubjectReference'),(86,2,'Category','Category'),(87,2,'Supplemental Categories','SupplementalCategories'),(88,2,'Fixture Identifier','FixtureIdentifier'),(89,2,'Keywords','Keywords'),(90,2,'Content Location Code','ContentLocationCode'),(91,2,'Content Location Name','ContentLocationName'),(92,2,'Release Date','ReleaseDate'),(93,2,'Release Time','ReleaseTime'),(94,2,'Expiration Date','ExpirationDate'),(95,2,'Expiration Time','ExpirationTime'),(96,2,'SpecialInstructions','SpecialInstructions'),(97,2,'Action Advised','ActionAdvised'),(98,2,'Reference Service','ReferenceService'),(99,2,'Reference Date','ReferenceDate'),(100,2,'Reference Number','ReferenceNumber'),(101,2,'Date Created','DateCreated'),(102,2,'Time Created','TimeCreated'),(103,2,'Digital Creation Date','DigitalCreationDate'),(104,2,'Digital Creation Time','DigitalCreationTime'),(105,2,'Originating Program','OriginatingProgram'),(106,2,'Program Version','ProgramVersion'),(107,2,'Object Cycle','ObjectCycle'),(108,2,'By-line','By-line'),(109,2,'By-Line Title','By-LineTitle'),(110,2,'City','cITY'),(111,2,'Sub-Location','Sub-Location'),(112,2,'Province/State','Province-State'),(113,2,'Country/Primary Location Code','Country-PrimaryLocationCode'),(114,2,'Country/Primary Location Name','Country-PrimaryLocationName'),(115,2,'Original Transmission Reference','OriginalTransmissionReference'),(116,2,'Headline','Headline'),(117,2,'Credit','Credit'),(118,2,'Source','Source'),(119,2,'Copyright Notice','CopyrightNotice'),(120,2,'Contact','Contact'),(121,2,'Caption/Abstract','Caption-Abstract'),(122,2,'Writer-Editor','Writer-Editor'),(123,2,'Rasterized Caption','RasterizedCaption'),(124,2,'Image Type','ImageType'),(125,2,'Image Orientation','ImageOrientation'),(126,2,'Language Identifier','LanguageIdentifier'),(127,2,'Audio Type','AudioType'),(128,2,'Audio Sampling Rate','AudioSamplingRate'),(129,2,'Audio Sampling Resolution','AudioSamplingResolution'),(130,2,'Audio Duration','AudioDuration'),(131,2,'Audio Outcue','AudioOutcue'),(132,2,'ObjectData Preview File Format','ObjectDataPreviewFileFormat'),(133,2,'ObjectData Preview File Format Version','ObjectDataPreviewFileFormatVersion'),(134,2,'ObjectData Preview Data','ObjectDataPreviewData'),(200,3,'Format','Format'),(201,3,'Title','Title'),(202,3,'Creator','Creator'),(203,3,'Description','Description'),(204,3,'Subject','Subject'),(205,3,'Rights','Rights'),(206,3,'CreatorTool','CreatorTool'),(207,3,'CreateDate','CreateDate'),(208,3,'ModifyDate','ModifyDate'),(209,3,'MetadataDate','MetadataDate'),(210,3,'DocumentID','DocumentID'),(211,3,'InstanceID','InstanceID'),(212,3,'ColorMode','ColorMode'),(213,3,'ICCProfileName','ICCProfileName'),(214,3,'AuthorsPosition','AuthorsPosition'),(215,3,'CaptionWriter','CaptionWriter'),(216,3,'Category','Category'),(217,3,'Headline','Headline'),(218,3,'DateCreated','DateCreated'),(219,3,'City','City'),(220,3,'State','State'),(221,3,'Country','Country'),(222,3,'TransmissionReference','TransmissionReference'),(223,3,'Instructions','Instructions'),(224,3,'Credit','Credit'),(225,3,'Source','Source'),(226,3,'SupplementalCategories','SupplementalCategories'),(227,3,'History','History'),(228,3,'Marked','Marked'),(229,3,'WebStatement','WebStatement'),(230,3,'UsageTerms','UsageTerms'),(231,3,'IntellectualGenre','IntellectualGenre'),(232,3,'Location','Location'),(233,3,'CountryCode','CountryCode'),(234,3,'CreatorContactInfoCiAdrExtadr','CreatorContactInfoCiAdrExtadr'),(235,3,'CreatorContactInfoCiAdrCity','CreatorContactInfoCiAdrCity'),(236,3,'CreatorContactInfoCiAdrRegion','CreatorContactInfoCiAdrRegion'),(237,3,'CreatorContactInfoCiAdrPcode','CreatorContactInfoCiAdrPcode'),(238,3,'CreatorContactInfoCiAdrCtry','CreatorContactInfoCiAdrCtry'),(239,3,'CreatorContactInfoCiTelWork','CreatorContactInfoCiTelWork'),(240,3,'CreatorContactInfoCiEmailWork','CreatorContactInfoCiEmailWork'),(241,3,'CreatorContactInfoCiUrlWork','CreatorContactInfoCiUrlWork'),(242,3,'SubjectCode','SubjectCode'),(243,3,'Scene','Scene'),(244,3,'Orientation','Orientation'),(245,3,'XResolution','XResolution'),(246,3,'YResolution','YResolution'),(247,3,'ResolutionUnit','ResolutionUnit'),(248,3,'NativeDigest','NativeDigest'),(249,3,'ExifImageWidth','ExifImageWidth'),(250,3,'ExifImageHeight','ExifImageHeight'),(251,3,'ColorSpace','ColorSpace'),(300,5,'File Name','FileName'),(301,5,'File Size','FileSize'),(302,5,'File Modify Date/Time','FileModifyDate'),(303,5,'File Type','FileType'),(304,5,'MIME Type','MIMEType'),(305,5,'Exif Byte Order','ExifByteOrder'),(306,5,'Image Width','ImageWidth'),(307,5,'Image Height','ImageHeight'),(308,5,'Encoding Process','EncodingProcess'),(309,5,'Bits Per Sample','BitsPerSample'),(310,5,'Color Components','ColorComponents'),(311,5,'Y Cb Cr Sub Sampling','YCbCrSubSampling'),(400,4,'DisplayedUnitsX','DisplayedUnitsX'),(401,4,'DisplayedUnitsY','DisplayedUnitsY'),(402,4,'GlobalAngle','GlobalAngle'),(403,4,'GlobalAltitude','GlobalAltitude'),(404,4,'CopyrightFlag','CopyrightFlag'),(405,4,'URL','URL'),(406,4,'PhotoshopQuality','PhotoshopQuality'),(407,4,'PhotoshopFormat','PhotoshopFormat'),(408,4,'ProgressiveScans','ProgressiveScans'),(500,6,'Keywords','Keywords'),(501,6,'XResolution','XResolution'),(502,6,'YResolution','YResolution'),(600,7,'MAX(width,height)','MAX(width,height)'),(601,7,'MIN(width,height)','MIN(width,height)'),(602,7,'Pixels (width*height)','width*height'),(603,7,'Auto-rotate from camera','Auto-rotate from camera'),(604,7,'GPSLatitude Signed','GPSLatitude Signed'),(605,7,'GPSLongitude Signed','GPSLongitude Signed'),(606,7,'GPSMapDatum Mapped','GPSMapDatum Mapped'),(607,7,'Filename Extension','Filename Extension'),(608,7,'GPSPosition Signed','GPSPosition Signed'),(700,3,'Rating','Rating'),(850,1,'GPSAltitude','GPSAltitude'),(851,1,'GPSAltitudeRef','GPSAltitudeRef'),(852,1,'GPSLatitude','GPSLatitude'),(853,1,'GPSLatitudeRef','GPSLatitudeRef'),(854,1,'GPSLongitude','GPSLongitude'),(855,1,'GPSLongitudeRef','GPSLongitudeRef'),(856,1,'GPSMapDatum','GPSMapDatum'),(857,1,'GPSDateStamp','GPSDateStamp'),(858,1,'GPSTimeStamp','GPSTimeStamp'),(859,1,'GPSSatellites','GPSSatellites');
/*!40000 ALTER TABLE `embeddeddatavalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entityrelationshipdescription`
--

DROP TABLE IF EXISTS `entityrelationshipdescription`;
CREATE TABLE `entityrelationshipdescription` (
  `AssetEntityId` bigint(20) NOT NULL,
  `RelationshipTypeId` bigint(20) NOT NULL,
  `FromName` varchar(100) default NULL,
  `FromNamePlural` varchar(100) default NULL,
  `ToName` varchar(100) default NULL,
  `ToNamePlural` varchar(100) default NULL,
  PRIMARY KEY  (`AssetEntityId`,`RelationshipTypeId`),
  KEY `IX_AssetEntity_ERD_FK` (`AssetEntityId`),
  KEY `IX_RelationshipType_ERD_FK` (`RelationshipTypeId`),
  CONSTRAINT `AssetEntity_ERD_FK` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `RelationshipType_ERD_FK` FOREIGN KEY (`RelationshipTypeId`) REFERENCES `relationshiptype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `entityrelationshipdescription`
--

LOCK TABLES `entityrelationshipdescription` WRITE;
/*!40000 ALTER TABLE `entityrelationshipdescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `entityrelationshipdescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facebooklikebuttonsettings`
--

DROP TABLE IF EXISTS `facebooklikebuttonsettings`;
CREATE TABLE `facebooklikebuttonsettings` (
  `IsEnabled` tinyint(1) NOT NULL default '0',
  `FbAdminIds` varchar(255) default NULL,
  `FbApplicationId` varchar(255) default NULL,
  `ButtonLayoutId` int(11) NOT NULL default '1',
  `VerbToDisplayId` int(11) NOT NULL default '1',
  `ShowSendButton` tinyint(1) NOT NULL default '0',
  `ShowFaces` tinyint(1) NOT NULL default '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `facebooklikebuttonsettings`
--

LOCK TABLES `facebooklikebuttonsettings` WRITE;
/*!40000 ALTER TABLE `facebooklikebuttonsettings` DISABLE KEYS */;
INSERT INTO `facebooklikebuttonsettings` VALUES (0,'','',1,1,0,1);
/*!40000 ALTER TABLE `facebooklikebuttonsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featuredasset`
--

DROP TABLE IF EXISTS `featuredasset`;
CREATE TABLE `featuredasset` (
  `AssetId` bigint(20) NOT NULL,
  `DateFeatured` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`AssetId`),
  CONSTRAINT `FeaturedAsset_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `featuredasset`
--

LOCK TABLES `featuredasset` WRITE;
/*!40000 ALTER TABLE `featuredasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `featuredasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featuredassetinbrand`
--

DROP TABLE IF EXISTS `featuredassetinbrand`;
CREATE TABLE `featuredassetinbrand` (
  `BrandId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  PRIMARY KEY  (`BrandId`,`AssetId`),
  KEY `IX_Relationship150` (`AssetId`),
  KEY `IX_Relationship147` (`BrandId`),
  CONSTRAINT `Relationship147` FOREIGN KEY (`BrandId`) REFERENCES `brand` (`Id`),
  CONSTRAINT `Relationship150` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `featuredassetinbrand`
--

LOCK TABLES `featuredassetinbrand` WRITE;
/*!40000 ALTER TABLE `featuredassetinbrand` DISABLE KEYS */;
/*!40000 ALTER TABLE `featuredassetinbrand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fileformat`
--

DROP TABLE IF EXISTS `fileformat`;
CREATE TABLE `fileformat` (
  `Id` bigint(20) NOT NULL,
  `AssetTypeId` bigint(20) NOT NULL,
  `FileExtension` varchar(20) NOT NULL,
  `IsIndexable` tinyint(1) NOT NULL default '0',
  `IsConvertable` tinyint(1) NOT NULL default '0',
  `IsConversionTarget` tinyint(1) NOT NULL default '0',
  `IsRotatable` tinyint(1) NOT NULL default '1',
  `Name` varchar(255) default NULL,
  `Description` varchar(255) default NULL,
  `ThumbnailFileLocation` varchar(255) default NULL,
  `ContentType` varchar(100) default NULL,
  `ConverterClass` varchar(255) default NULL,
  `ToTextConverterClass` varchar(255) default NULL,
  `PreviewInclude` varchar(255) default NULL,
  `PreviewHeight` int(11) default NULL,
  `PreviewWidth` int(11) default NULL,
  `ConvertIndividualLayers` tinyint(1) default '0',
  `CanViewOriginal` tinyint(1) NOT NULL default '0',
  `ViewFileInclude` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship58` (`AssetTypeId`),
  CONSTRAINT `Relationship58` FOREIGN KEY (`AssetTypeId`) REFERENCES `assettype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fileformat`
--

LOCK TABLES `fileformat` WRITE;
/*!40000 ALTER TABLE `fileformat` DISABLE KEYS */;
INSERT INTO `fileformat` VALUES (1,2,'jpg',0,1,1,1,'JPEG','JPEG - Presentations or Web',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(2,2,'jpeg',0,1,0,1,'JPEG','JPEG - Presentations or Web',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(3,2,'jpe',0,1,0,1,'JPEG','JPEG - Presentations or Web',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(4,2,'gif',0,1,1,1,'GIF','GIF - Diagrams or Web',NULL,'image/gif',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(5,2,'png',0,1,1,1,'PNG','PNG - Web',NULL,'image/png',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(6,2,'tif',0,1,1,1,'TIFF','TIFF - Print',NULL,'image/tiff',NULL,NULL,NULL,NULL,NULL,1,0,NULL),(7,2,'tiff',0,1,0,1,'TIFF','TIFF - Print',NULL,'image/tiff',NULL,NULL,NULL,NULL,NULL,1,0,NULL),(8,2,'bmp',0,1,0,1,'BMP','Bitmap',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(9,2,'tga',0,1,0,1,'TGA','Truevision Targa',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(10,2,'psd',0,1,0,1,'PSD','Photoshop',NULL,NULL,'com.bright.assetbank.converter.IMToJpgConverter',NULL,NULL,NULL,NULL,0,0,NULL),(11,2,'ppm',0,1,0,1,'PPM','PPM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(12,2,'nef',0,1,0,1,'NEF','NEF',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(13,2,'eps',0,1,0,1,'EPS','EPS',NULL,NULL,'com.bright.assetbank.converter.IMToJpgConverter',NULL,NULL,NULL,NULL,0,0,NULL),(14,2,'ai',0,1,0,1,'AI','Adobe Illustrator',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(15,2,'ps',0,1,0,1,'PS','PostScript',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(16,2,'cr2',0,1,0,1,'CR2','CR2',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(17,2,'dng',0,1,0,1,'DNG','Adobe Digital Negative',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(18,2,'pcd',0,1,0,1,'PCD','Kodak Photo CD',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(19,2,'jp2',0,1,0,1,'JPEG 2000','JPEG2000',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(20,2,'j2k',0,1,0,1,'JPEG 2000','JPEG2000',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(21,2,'dcr',0,1,0,1,'DCR','Kodak Digital Camera Raw Image File',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(22,2,'raf',0,1,0,1,'RAF','FUJI photo RAW File',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(23,2,'wmf',0,1,0,1,'WMF','WMF - Windows MetaFile',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(24,2,'pcx',0,1,0,1,'PCX','PCX - Personal Computer eXchange bitmap',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(100,1,'doc',1,0,0,1,'DOC','Word documents','thumbnails/word_icon.gif','application/msword',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(101,1,'txt',0,0,0,1,'TEXT','Text documents','thumbnails/text_icon.gif','text/plain',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(102,1,'xls',0,0,0,1,'XLS','Excel spreadsheets','thumbnails/excel_icon.gif','application/vnd.ms-excel',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(103,1,'ppt',1,0,0,1,'PPT','Powerpoint presentations','thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint','com.bright.assetbank.converter.PptToJpgConverter','com.bright.assetbank.converter.PptToTextConverter',NULL,NULL,NULL,0,0,NULL),(104,1,'zip',0,0,0,1,'ZIP','Zip archives','thumbnails/zip_icon.gif','application/zip',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(105,1,'rtf',0,0,0,1,'RTF','RTF documents','thumbnails/rtf_icon.gif','text/rtf',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(106,1,'xlt',0,0,0,1,'EXT','Excel templates','thumbnails/excel_icon.gif','application/vnd.ms-excel',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(107,2,'pdf',1,1,0,1,'PDF','Adobe PDF','thumbnails/pdf_icon.gif','application/pdf',NULL,'com.bright.assetbank.converter.PDFToTextConverter',NULL,NULL,NULL,1,0,NULL),(108,1,'html',0,0,0,1,'HTML','Text - HTML','thumbnails/html_icon.gif','text/html',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(109,1,'mht',0,0,0,1,'MHT','Web archive - MHT',NULL,'application/mht',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(200,4,'wav',0,0,0,1,'WAV','Audio - WAV format','thumbnails/audio_icon.gif','audio/x-wav',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(201,1,'docx',1,0,0,1,'DOCX','Word 2007 documents','thumbnails/word_icon.gif','application/msword',NULL,'com.bright.assetbank.converter.DOCXToTextConverter',NULL,NULL,NULL,0,0,NULL),(202,1,'xlsx',0,0,0,1,'XLS','Excel 2007 spreadsheets','thumbnails/excel_icon.gif','application/vnd.ms-excel',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(203,1,'pptx',0,0,0,1,'PPT','Powerpoint 2007 presentations','thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(210,1,'indd',0,0,0,1,'INDD','Adobe InDesign Document','thumbnails/indesign_icon.gif','application/indesign','com.bright.assetbank.converter.InddToJpgConverter',NULL,NULL,NULL,NULL,0,0,NULL),(300,3,'mpg',0,1,1,1,'MPEG','Video - MPEG format',NULL,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(301,3,'mpeg',0,1,0,1,'MPEG','Video - MPEG format',NULL,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(302,3,'avi',0,1,0,1,'AVI','Video - AVI format',NULL,'video/x-msvideo','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(303,3,'mov',0,1,0,1,'MOV','Video - Quicktime format',NULL,'video/quicktime','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(304,3,'wmv',0,1,1,1,'WMV','Video - WMV format',NULL,'video/x-ms-wmv','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(305,3,'asf',0,1,0,1,'ASF','Video - ASF format',NULL,'video/x-ms-asf','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(306,3,'mp4',0,1,0,1,'MP4','Video - MP4 format',NULL,'video/mp4','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(307,3,'asx',0,1,0,1,'ASX','Video - ASF format',NULL,'video/x-ms-asf','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(308,3,'vob',0,1,0,1,'VOB','Video - DVD format',NULL,'video/dvd','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(309,3,'flv',0,1,1,1,'FLV','Video - FLV Format',NULL,'video/x-flv','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,'../inc/preview_flv.jsp'),(310,3,'dv',0,1,0,1,'DV','Video - DV Format',NULL,'video/x-dv','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(311,3,'f4v',0,1,1,1,'F4V','Video - F4V Format',NULL,'video/mp4','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(350,4,'mp3',0,0,0,1,'MP3','Audio - MPEG Format','thumbnails/audio_icon.gif','audio/mpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(351,4,'aiff',0,0,0,1,'AIFF','Audio - AIFF Format','thumbnails/audio_icon.gif','audio/x-aiff',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(352,4,'aif',0,0,0,1,'AIF','Audio - AIFF Format','thumbnails/audio_icon.gif','audio/x-aiff',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(353,4,'au',0,0,0,1,'AU','Audio - AU Format','thumbnails/audio_icon.gif','audio/basic',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(354,4,'wma',0,0,0,1,'WMA','Audio - WMA Format','thumbnails/audio_icon.gif','audio/x-ms-wma',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(355,4,'aac',0,0,0,1,'AAC','Audio - AAC Format','thumbnails/audio_icon.gif','audio/x-aac',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(356,4,'m4a',0,0,0,1,'M4A','Audio - M4A Format','thumbnails/audio_icon.gif','audio/mp4',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(357,4,'mid',0,0,0,1,'MID','Audio - MIDI Format','thumbnails/audio_icon.gif','audio/midi',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(358,4,'midi',0,0,0,1,'MIDI','Audio - MIDI Format','thumbnails/audio_icon.gif','audio/midi',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(400,1,'swf',0,0,0,1,'SWF','Adobe Shockwave/Flash','thumbnails/flash_icon.gif','application/x-shockwave-flash',NULL,NULL,NULL,NULL,NULL,0,0,'loadDirectlyInBrowser'),(500,3,'m4v',0,1,1,1,'M4V','Video - M4V format',NULL,'video/x-m4v','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(600,3,'m2v',0,1,1,1,'M2V','Video - M2V format',NULL,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(9998,1,'jpg',0,1,0,0,'Photint 360 JPG	Image - Photint 360 JPG ','Image - Photint 360 JPG Format','thumbnails/oyoon.gif','image/jpg',NULL,NULL,'../../../custom/inc_360_jpg_preview.jsp',NULL,NULL,0,0,NULL),(9999,1,'flv',0,1,0,0,'Photoint 360 FLV','Video - Photoint 360 FLV Format','thumbnails/oyoon.gif','video/x-flv','com.bright.assetbank.converter.VideoToPngConverter',NULL,'../../../custom/inc_360_preview.jsp',NULL,NULL,0,0,NULL);
/*!40000 ALTER TABLE `fileformat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fileformat_copy`
--

DROP TABLE IF EXISTS `fileformat_copy`;
CREATE TABLE `fileformat_copy` (
  `Id` bigint(20) NOT NULL,
  `AssetTypeId` bigint(20) NOT NULL,
  `FileExtension` varchar(20) NOT NULL,
  `IsIndexable` tinyint(1) NOT NULL default '0',
  `IsConvertable` tinyint(1) NOT NULL default '0',
  `IsConversionTarget` tinyint(1) NOT NULL default '0',
  `IsRotatable` tinyint(1) NOT NULL default '1',
  `Name` varchar(255) default NULL,
  `Description` varchar(255) default NULL,
  `ThumbnailFileLocation` varchar(255) default NULL,
  `ContentType` varchar(100) default NULL,
  `ConverterClass` varchar(255) default NULL,
  `ToTextConverterClass` varchar(255) default NULL,
  `PreviewInclude` varchar(255) default NULL,
  `PreviewHeight` int(11) default NULL,
  `PreviewWidth` int(11) default NULL,
  `ConvertIndividualLayers` tinyint(1) default '0',
  `CanViewOriginal` tinyint(1) NOT NULL default '0',
  `ViewFileInclude` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `FileExtension` (`FileExtension`),
  KEY `IX_Relationship58` (`AssetTypeId`),
  CONSTRAINT `fileformat_copy_ibfk_1` FOREIGN KEY (`AssetTypeId`) REFERENCES `assettype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fileformat_copy`
--

LOCK TABLES `fileformat_copy` WRITE;
/*!40000 ALTER TABLE `fileformat_copy` DISABLE KEYS */;
INSERT INTO `fileformat_copy` VALUES (1,2,'jpg',0,1,1,1,'JPEG','JPEG - Presentations or Web',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(2,2,'jpeg',0,1,0,1,'JPEG','JPEG - Presentations or Web',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(3,2,'jpe',0,1,0,1,'JPEG','JPEG - Presentations or Web',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(4,2,'gif',0,1,1,1,'GIF','GIF - Diagrams or Web',NULL,'image/gif',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(5,2,'png',0,1,1,1,'PNG','PNG - Web',NULL,'image/png',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(6,2,'tif',0,1,1,1,'TIFF','TIFF - Print',NULL,'image/tiff',NULL,NULL,NULL,NULL,NULL,1,0,NULL),(7,2,'tiff',0,1,0,1,'TIFF','TIFF - Print',NULL,'image/tiff',NULL,NULL,NULL,NULL,NULL,1,0,NULL),(8,2,'bmp',0,1,0,1,'BMP','Bitmap',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(9,2,'tga',0,1,0,1,'TGA','Truevision Targa',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(10,2,'psd',0,1,0,1,'PSD','Photoshop',NULL,NULL,'com.bright.assetbank.converter.IMToJpgConverter',NULL,NULL,NULL,NULL,0,0,NULL),(11,2,'ppm',0,1,0,1,'PPM','PPM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(12,2,'nef',0,1,0,1,'NEF','NEF',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(13,2,'eps',0,1,0,1,'EPS','EPS',NULL,NULL,'com.bright.assetbank.converter.IMToJpgConverter',NULL,NULL,NULL,NULL,0,0,NULL),(14,2,'ai',0,1,0,1,'AI','Adobe Illustrator',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(15,2,'ps',0,1,0,1,'PS','PostScript',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(16,2,'cr2',0,1,0,1,'CR2','CR2',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(17,2,'dng',0,1,0,1,'DNG','Adobe Digital Negative',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(18,2,'pcd',0,1,0,1,'PCD','Kodak Photo CD',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(19,2,'jp2',0,1,0,1,'JPEG 2000','JPEG2000',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(20,2,'j2k',0,1,0,1,'JPEG 2000','JPEG2000',NULL,'image/jpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(21,2,'dcr',0,1,0,1,'DCR','Kodak Digital Camera Raw Image File',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(22,2,'raf',0,1,0,1,'RAF','FUJI photo RAW File',NULL,NULL,'com.bright.assetbank.converter.RawToImageConverter',NULL,NULL,NULL,NULL,0,0,NULL),(23,2,'wmf',0,1,0,1,'WMF','WMF - Windows MetaFile',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(24,2,'pcx',0,1,0,1,'PCX','PCX - Personal Computer eXchange bitmap',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL),(100,1,'doc',1,0,0,1,'DOC','Word documents','thumbnails/word_icon.gif','application/msword',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(101,1,'txt',0,0,0,1,'TEXT','Text documents','thumbnails/text_icon.gif','text/plain',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(102,1,'xls',0,0,0,1,'XLS','Excel spreadsheets','thumbnails/excel_icon.gif','application/vnd.ms-excel',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(103,1,'ppt',1,0,0,1,'PPT','Powerpoint presentations','thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint','com.bright.assetbank.converter.PptToJpgConverter','com.bright.assetbank.converter.PptToTextConverter',NULL,NULL,NULL,0,0,NULL),(104,1,'zip',0,0,0,1,'ZIP','Zip archives','thumbnails/zip_icon.gif','application/zip',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(105,1,'rtf',0,0,0,1,'RTF','RTF documents','thumbnails/rtf_icon.gif','text/rtf',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(106,1,'xlt',0,0,0,1,'EXT','Excel templates','thumbnails/excel_icon.gif','application/vnd.ms-excel',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(107,2,'pdf',1,1,0,1,'PDF','Adobe PDF','thumbnails/pdf_icon.gif','application/pdf',NULL,'com.bright.assetbank.converter.PDFToTextConverter',NULL,NULL,NULL,1,0,NULL),(108,1,'html',0,0,0,1,'HTML','Text - HTML','thumbnails/html_icon.gif','text/html',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(109,1,'mht',0,0,0,1,'MHT','Web archive - MHT',NULL,'application/mht',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(200,4,'wav',0,0,0,1,'WAV','Audio - WAV format','thumbnails/audio_icon.gif','audio/x-wav',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(201,1,'docx',1,0,0,1,'DOCX','Word 2007 documents','thumbnails/word_icon.gif','application/msword',NULL,'com.bright.assetbank.converter.DOCXToTextConverter',NULL,NULL,NULL,0,0,NULL),(202,1,'xlsx',0,0,0,1,'XLS','Excel 2007 spreadsheets','thumbnails/excel_icon.gif','application/vnd.ms-excel',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(203,1,'pptx',0,0,0,1,'PPT','Powerpoint 2007 presentations','thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(210,1,'indd',0,0,0,1,'INDD','Adobe InDesign Document','thumbnails/indesign_icon.gif','application/indesign','com.bright.assetbank.converter.InddToJpgConverter',NULL,NULL,NULL,NULL,0,0,NULL),(300,3,'mpg',0,1,1,1,'MPEG','Video - MPEG format',NULL,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(301,3,'mpeg',0,1,0,1,'MPEG','Video - MPEG format',NULL,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(302,3,'avi',0,1,0,1,'AVI','Video - AVI format',NULL,'video/x-msvideo','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(303,3,'mov',0,1,0,1,'MOV','Video - Quicktime format',NULL,'video/quicktime','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(304,3,'wmv',0,1,1,1,'WMV','Video - WMV format',NULL,'video/x-ms-wmv','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(305,3,'asf',0,1,0,1,'ASF','Video - ASF format',NULL,'video/x-ms-asf','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(306,3,'mp4',0,1,0,1,'MP4','Video - MP4 format',NULL,'video/mp4','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(307,3,'asx',0,1,0,1,'ASX','Video - ASF format',NULL,'video/x-ms-asf','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(308,3,'vob',0,1,0,1,'VOB','Video - DVD format',NULL,'video/dvd','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(309,3,'flv',0,1,1,1,'FLV','Video - FLV Format',NULL,'video/x-flv','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,'../inc/preview_flv.jsp'),(310,3,'dv',0,1,0,1,'DV','Video - DV Format',NULL,'video/x-dv','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(311,3,'f4v',0,1,1,1,'F4V','Video - F4V Format',NULL,'video/mp4','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(350,4,'mp3',0,0,0,1,'MP3','Audio - MPEG Format','thumbnails/audio_icon.gif','audio/mpeg',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(351,4,'aiff',0,0,0,1,'AIFF','Audio - AIFF Format','thumbnails/audio_icon.gif','audio/x-aiff',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(352,4,'aif',0,0,0,1,'AIF','Audio - AIFF Format','thumbnails/audio_icon.gif','audio/x-aiff',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(353,4,'au',0,0,0,1,'AU','Audio - AU Format','thumbnails/audio_icon.gif','audio/basic',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(354,4,'wma',0,0,0,1,'WMA','Audio - WMA Format','thumbnails/audio_icon.gif','audio/x-ms-wma',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(355,4,'aac',0,0,0,1,'AAC','Audio - AAC Format','thumbnails/audio_icon.gif','audio/x-aac',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(356,4,'m4a',0,0,0,1,'M4A','Audio - M4A Format','thumbnails/audio_icon.gif','audio/mp4',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(357,4,'mid',0,0,0,1,'MID','Audio - MIDI Format','thumbnails/audio_icon.gif','audio/midi',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(358,4,'midi',0,0,0,1,'MIDI','Audio - MIDI Format','thumbnails/audio_icon.gif','audio/midi',NULL,NULL,NULL,NULL,NULL,0,0,NULL),(400,1,'swf',0,0,0,1,'SWF','Adobe Shockwave/Flash','thumbnails/flash_icon.gif','application/x-shockwave-flash',NULL,NULL,NULL,NULL,NULL,0,0,'loadDirectlyInBrowser'),(500,3,'m4v',0,1,1,1,'M4V','Video - M4V format',NULL,'video/x-m4v','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(600,3,'m2v',0,1,1,1,'M2V','Video - M2V format',NULL,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter',NULL,NULL,NULL,NULL,0,0,NULL),(9998,1,'jpg360',0,1,0,0,'Photint 360 JPG	Image - Photint 360 JPG ','Image - Photint 360 JPG Format','thumbnails/oyoon.gif','image/jpg',NULL,NULL,'../../../custom/inc_360_jpg_preview.jsp',NULL,NULL,0,0,NULL),(9999,1,'flv360',0,0,0,0,'Photoint 360 FLV','Video - Photoint 360 FLV Format','thumbnails/oyoon.gif','video/x-flv',NULL,NULL,'../../../custom/inc_360_preview.jsp',NULL,NULL,0,0,NULL);
/*!40000 ALTER TABLE `fileformat_copy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filter`
--

DROP TABLE IF EXISTS `filter`;
CREATE TABLE `filter` (
  `Id` bigint(20) NOT NULL auto_increment,
  `UserId` bigint(20) default NULL,
  `Name` varchar(200) default NULL,
  `IsDefault` tinyint(1) default '0',
  `FilterTypeId` bigint(20) NOT NULL,
  `CategoryIds` varchar(200) default NULL,
  `AccessLevelIds` varchar(200) default NULL,
  `SequenceNumber` int(11) default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship259` (`UserId`),
  KEY `IX_Relationship258` (`FilterTypeId`),
  CONSTRAINT `Relationship258` FOREIGN KEY (`FilterTypeId`) REFERENCES `filtertype` (`Id`),
  CONSTRAINT `Relationship259` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filter`
--

LOCK TABLES `filter` WRITE;
/*!40000 ALTER TABLE `filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `filter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filterassetattributevalue`
--

DROP TABLE IF EXISTS `filterassetattributevalue`;
CREATE TABLE `filterassetattributevalue` (
  `AttributeId` bigint(20) NOT NULL,
  `FilterId` bigint(20) NOT NULL,
  `Value` mediumtext,
  `DateValue` date default NULL,
  `DateTimeValue` datetime default NULL,
  PRIMARY KEY  (`AttributeId`,`FilterId`),
  KEY `IX_FAAV_Attribute_FK` (`AttributeId`),
  KEY `IX_Filter_FAAV_FK` (`FilterId`),
  CONSTRAINT `FAAV_Attribute_FK` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `Filter_FAAV_FK` FOREIGN KEY (`FilterId`) REFERENCES `filter` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filterassetattributevalue`
--

LOCK TABLES `filterassetattributevalue` WRITE;
/*!40000 ALTER TABLE `filterassetattributevalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `filterassetattributevalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filterforcategory`
--

DROP TABLE IF EXISTS `filterforcategory`;
CREATE TABLE `filterforcategory` (
  `FilterId` bigint(20) NOT NULL,
  `CategoryId` bigint(20) NOT NULL,
  `SequenceNumber` int(11) default NULL,
  PRIMARY KEY  (`FilterId`,`CategoryId`),
  KEY `IX_Relationship290` (`CategoryId`),
  KEY `IX_Relationship289` (`FilterId`),
  CONSTRAINT `Relationship289` FOREIGN KEY (`FilterId`) REFERENCES `filter` (`Id`),
  CONSTRAINT `Relationship290` FOREIGN KEY (`CategoryId`) REFERENCES `cm_category` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filterforcategory`
--

LOCK TABLES `filterforcategory` WRITE;
/*!40000 ALTER TABLE `filterforcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `filterforcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filtergroup`
--

DROP TABLE IF EXISTS `filtergroup`;
CREATE TABLE `filtergroup` (
  `Id` bigint(20) NOT NULL auto_increment,
  `FilterTypeId` bigint(20) NOT NULL,
  `Name` varchar(200) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship260` (`FilterTypeId`),
  CONSTRAINT `Relationship260` FOREIGN KEY (`FilterTypeId`) REFERENCES `filtertype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filtergroup`
--

LOCK TABLES `filtergroup` WRITE;
/*!40000 ALTER TABLE `filtergroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `filtergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filteringroup`
--

DROP TABLE IF EXISTS `filteringroup`;
CREATE TABLE `filteringroup` (
  `FilterId` bigint(20) NOT NULL,
  `GroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`FilterId`,`GroupId`),
  KEY `IX_Relationship261` (`FilterId`),
  KEY `IX_Relationship262` (`GroupId`),
  CONSTRAINT `Relationship261` FOREIGN KEY (`FilterId`) REFERENCES `filter` (`Id`),
  CONSTRAINT `Relationship262` FOREIGN KEY (`GroupId`) REFERENCES `filtergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filteringroup`
--

LOCK TABLES `filteringroup` WRITE;
/*!40000 ALTER TABLE `filteringroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `filteringroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filterlistattributevalue`
--

DROP TABLE IF EXISTS `filterlistattributevalue`;
CREATE TABLE `filterlistattributevalue` (
  `FilterId` bigint(20) NOT NULL,
  `ListAttributeValueId` bigint(20) NOT NULL,
  PRIMARY KEY  (`FilterId`,`ListAttributeValueId`),
  KEY `IX_Filter_FLAV_FK` (`FilterId`),
  KEY `IX_FLAV_LAV_FK` (`ListAttributeValueId`),
  CONSTRAINT `Filter_FLAV_FK` FOREIGN KEY (`FilterId`) REFERENCES `filter` (`Id`),
  CONSTRAINT `FLAV_LAV_FK` FOREIGN KEY (`ListAttributeValueId`) REFERENCES `listattributevalue` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filterlistattributevalue`
--

LOCK TABLES `filterlistattributevalue` WRITE;
/*!40000 ALTER TABLE `filterlistattributevalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `filterlistattributevalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filtertype`
--

DROP TABLE IF EXISTS `filtertype`;
CREATE TABLE `filtertype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `filtertype`
--

LOCK TABLES `filtertype` WRITE;
/*!40000 ALTER TABLE `filtertype` DISABLE KEYS */;
INSERT INTO `filtertype` VALUES (1,'Filter'),(2,'Template');
/*!40000 ALTER TABLE `filtertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupattributeexclusion`
--

DROP TABLE IF EXISTS `groupattributeexclusion`;
CREATE TABLE `groupattributeexclusion` (
  `AttributeId` bigint(20) NOT NULL,
  `UserGroupId` bigint(20) NOT NULL,
  `Value` varchar(255) NOT NULL,
  PRIMARY KEY  (`AttributeId`,`UserGroupId`,`Value`),
  KEY `IX_Relationship40` (`AttributeId`),
  KEY `IX_Relationship41` (`UserGroupId`),
  CONSTRAINT `Relationship40` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `Relationship41` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupattributeexclusion`
--

LOCK TABLES `groupattributeexclusion` WRITE;
/*!40000 ALTER TABLE `groupattributeexclusion` DISABLE KEYS */;
INSERT INTO `groupattributeexclusion` VALUES (21,1,'Expired'),(21,1,'Inactive'),(21,2,'Expired'),(21,2,'Inactive');
/*!40000 ALTER TABLE `groupattributeexclusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupemailrule`
--

DROP TABLE IF EXISTS `groupemailrule`;
CREATE TABLE `groupemailrule` (
  `RuleId` bigint(20) NOT NULL,
  `UserGroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`RuleId`,`UserGroupId`),
  KEY `IX_Relationship88` (`UserGroupId`),
  KEY `IX_Relationship87` (`RuleId`),
  CONSTRAINT `Relationship87` FOREIGN KEY (`RuleId`) REFERENCES `sendemaildaterule` (`Id`),
  CONSTRAINT `Relationship88` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupemailrule`
--

LOCK TABLES `groupemailrule` WRITE;
/*!40000 ALTER TABLE `groupemailrule` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupemailrule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupfilterexclusion`
--

DROP TABLE IF EXISTS `groupfilterexclusion`;
CREATE TABLE `groupfilterexclusion` (
  `FilterId` bigint(20) NOT NULL,
  `UserGroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`FilterId`,`UserGroupId`),
  KEY `IX_Relationship288` (`UserGroupId`),
  KEY `IX_Relationship287` (`FilterId`),
  CONSTRAINT `Relationship287` FOREIGN KEY (`FilterId`) REFERENCES `filter` (`Id`),
  CONSTRAINT `Relationship288` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupfilterexclusion`
--

LOCK TABLES `groupfilterexclusion` WRITE;
/*!40000 ALTER TABLE `groupfilterexclusion` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupfilterexclusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupinsubscriptionmodel`
--

DROP TABLE IF EXISTS `groupinsubscriptionmodel`;
CREATE TABLE `groupinsubscriptionmodel` (
  `SubscriptionModelId` bigint(20) NOT NULL,
  `UserGroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`SubscriptionModelId`,`UserGroupId`),
  KEY `IX_Relationship108` (`UserGroupId`),
  KEY `IX_Relationship107` (`SubscriptionModelId`),
  CONSTRAINT `Relationship107` FOREIGN KEY (`SubscriptionModelId`) REFERENCES `subscriptionmodel` (`Id`),
  CONSTRAINT `Relationship108` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupinsubscriptionmodel`
--

LOCK TABLES `groupinsubscriptionmodel` WRITE;
/*!40000 ALTER TABLE `groupinsubscriptionmodel` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupinsubscriptionmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupisrole`
--

DROP TABLE IF EXISTS `groupisrole`;
CREATE TABLE `groupisrole` (
  `UserGroupId` bigint(20) NOT NULL,
  `RoleId` bigint(20) NOT NULL,
  PRIMARY KEY  (`UserGroupId`,`RoleId`),
  KEY `IX_Relationship154` (`UserGroupId`),
  KEY `IX_Relationship155` (`RoleId`),
  CONSTRAINT `Relationship154` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`),
  CONSTRAINT `Relationship155` FOREIGN KEY (`RoleId`) REFERENCES `role` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupisrole`
--

LOCK TABLES `groupisrole` WRITE;
/*!40000 ALTER TABLE `groupisrole` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupisrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupusageexclusion`
--

DROP TABLE IF EXISTS `groupusageexclusion`;
CREATE TABLE `groupusageexclusion` (
  `UsageTypeId` bigint(20) NOT NULL,
  `UserGroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`UsageTypeId`,`UserGroupId`),
  KEY `IX_Relationship100` (`UserGroupId`),
  KEY `IX_Relationship99` (`UsageTypeId`),
  CONSTRAINT `Relationship100` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`),
  CONSTRAINT `Relationship99` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupusageexclusion`
--

LOCK TABLES `groupusageexclusion` WRITE;
/*!40000 ALTER TABLE `groupusageexclusion` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupusageexclusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imageasset`
--

DROP TABLE IF EXISTS `imageasset`;
CREATE TABLE `imageasset` (
  `AssetId` bigint(20) NOT NULL,
  `Height` int(11) default NULL,
  `Width` int(11) default NULL,
  `ColorSpace` int(11) default NULL,
  `LargeFileLocation` varchar(255) default NULL,
  `FeaturedFileLocation` varchar(255) default NULL,
  `NumLayers` int(11) default '1',
  `UnwatermarkedLargeFileLocation` varchar(255) default NULL,
  PRIMARY KEY  (`AssetId`),
  CONSTRAINT `Relationship29` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `imageasset`
--

LOCK TABLES `imageasset` WRITE;
/*!40000 ALTER TABLE `imageasset` DISABLE KEYS */;
INSERT INTO `imageasset` VALUES (15,512,1024,1,'3:1/sh1-l.jpg',NULL,1,'');
/*!40000 ALTER TABLE `imageasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesignassetentity`
--

DROP TABLE IF EXISTS `indesignassetentity`;
CREATE TABLE `indesignassetentity` (
  `AssetEntityId` bigint(20) NOT NULL,
  `InDesignAssetEntityTypeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`AssetEntityId`),
  KEY `IX_InDAssEnt_InDAssEntType_FK` (`InDesignAssetEntityTypeId`),
  CONSTRAINT `InDAssEnt_AssEnt_FK` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `InDAssEnt_InDAssEntType_FK` FOREIGN KEY (`InDesignAssetEntityTypeId`) REFERENCES `indesignassetentitytype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesignassetentity`
--

LOCK TABLES `indesignassetentity` WRITE;
/*!40000 ALTER TABLE `indesignassetentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `indesignassetentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesignassetentitytype`
--

DROP TABLE IF EXISTS `indesignassetentitytype`;
CREATE TABLE `indesignassetentitytype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesignassetentitytype`
--

LOCK TABLES `indesignassetentitytype` WRITE;
/*!40000 ALTER TABLE `indesignassetentitytype` DISABLE KEYS */;
INSERT INTO `indesignassetentitytype` VALUES (1,'InDesign Document'),(2,'Symbol'),(3,'Image');
/*!40000 ALTER TABLE `indesignassetentitytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesigndocument`
--

DROP TABLE IF EXISTS `indesigndocument`;
CREATE TABLE `indesigndocument` (
  `Id` bigint(20) NOT NULL auto_increment,
  `InDesignPDFQualityId` int(11) NOT NULL,
  `FileLocation` varchar(255) NOT NULL,
  `OriginalFilename` varchar(255) NOT NULL,
  `FileSizeInBytes` bigint(20) NOT NULL,
  `NumSymbols` int(11) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_InDD_InDPQ_FK` (`InDesignPDFQualityId`),
  CONSTRAINT `InDD_InDPQ_FK` FOREIGN KEY (`InDesignPDFQualityId`) REFERENCES `indesignpdfquality` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesigndocument`
--

LOCK TABLES `indesigndocument` WRITE;
/*!40000 ALTER TABLE `indesigndocument` DISABLE KEYS */;
/*!40000 ALTER TABLE `indesigndocument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesigndocumentasset`
--

DROP TABLE IF EXISTS `indesigndocumentasset`;
CREATE TABLE `indesigndocumentasset` (
  `AssetId` bigint(20) NOT NULL,
  `TemplateAssetId` bigint(20) default NULL,
  `InDesignDocumentId` bigint(20) default NULL,
  `NewInDesignDocumentId` bigint(20) default NULL,
  `PDFStatusId` int(11) default NULL,
  `PDFStatusChangeDate` datetime default NULL,
  PRIMARY KEY  (`AssetId`),
  KEY `IX_InDDAsset_TemplInDAsset_FK` (`TemplateAssetId`),
  KEY `IX_InDDAsset_InDD_FK` (`InDesignDocumentId`),
  KEY `IX_InDDAsset_NewInDD_FK` (`NewInDesignDocumentId`),
  KEY `IX_InDDAsset_InDPDFStatus_FK` (`PDFStatusId`),
  CONSTRAINT `InDDAsset_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `InDDAsset_InDD_FK` FOREIGN KEY (`InDesignDocumentId`) REFERENCES `indesigndocument` (`Id`),
  CONSTRAINT `InDDAsset_InDPDFStatus_FK` FOREIGN KEY (`PDFStatusId`) REFERENCES `indesignpdfstatus` (`Id`),
  CONSTRAINT `InDDAsset_NewInDD_FK` FOREIGN KEY (`NewInDesignDocumentId`) REFERENCES `indesigndocument` (`Id`),
  CONSTRAINT `InDDAsset_TemplInDAsset_FK` FOREIGN KEY (`TemplateAssetId`) REFERENCES `indesigndocumentasset` (`AssetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesigndocumentasset`
--

LOCK TABLES `indesigndocumentasset` WRITE;
/*!40000 ALTER TABLE `indesigndocumentasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `indesigndocumentasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesignlinkedresource`
--

DROP TABLE IF EXISTS `indesignlinkedresource`;
CREATE TABLE `indesignlinkedresource` (
  `AssetId` bigint(20) NOT NULL,
  `LinkedAssetId` bigint(20) NOT NULL,
  PRIMARY KEY  (`AssetId`,`LinkedAssetId`),
  KEY `InDLinked_Asset_Asset_FK` (`LinkedAssetId`),
  CONSTRAINT `InDLinked_Asset_Asset_FK` FOREIGN KEY (`LinkedAssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `InDLinked_Asset_Doc_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesignlinkedresource`
--

LOCK TABLES `indesignlinkedresource` WRITE;
/*!40000 ALTER TABLE `indesignlinkedresource` DISABLE KEYS */;
/*!40000 ALTER TABLE `indesignlinkedresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesignpdfgenerror`
--

DROP TABLE IF EXISTS `indesignpdfgenerror`;
CREATE TABLE `indesignpdfgenerror` (
  `AssetId` bigint(20) NOT NULL,
  `SequenceNumber` int(11) NOT NULL,
  `Message` mediumtext NOT NULL,
  PRIMARY KEY  (`AssetId`,`SequenceNumber`),
  KEY `IX_InDPDFGenError_InDDAsset_FK` (`AssetId`),
  CONSTRAINT `InDPDFGenError_InDDAsset_FK` FOREIGN KEY (`AssetId`) REFERENCES `indesigndocumentasset` (`AssetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesignpdfgenerror`
--

LOCK TABLES `indesignpdfgenerror` WRITE;
/*!40000 ALTER TABLE `indesignpdfgenerror` DISABLE KEYS */;
/*!40000 ALTER TABLE `indesignpdfgenerror` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesignpdfquality`
--

DROP TABLE IF EXISTS `indesignpdfquality`;
CREATE TABLE `indesignpdfquality` (
  `Id` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesignpdfquality`
--

LOCK TABLES `indesignpdfquality` WRITE;
/*!40000 ALTER TABLE `indesignpdfquality` DISABLE KEYS */;
INSERT INTO `indesignpdfquality` VALUES (1,'Normal'),(2,'Barcode');
/*!40000 ALTER TABLE `indesignpdfquality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indesignpdfstatus`
--

DROP TABLE IF EXISTS `indesignpdfstatus`;
CREATE TABLE `indesignpdfstatus` (
  `Id` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `indesignpdfstatus`
--

LOCK TABLES `indesignpdfstatus` WRITE;
/*!40000 ALTER TABLE `indesignpdfstatus` DISABLE KEYS */;
INSERT INTO `indesignpdfstatus` VALUES (10,'New Asset'),(20,'Generation Queued'),(30,'Generation In Progress'),(40,'Generation Failed'),(50,'Up to Date');
/*!40000 ALTER TABLE `indesignpdfstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AssetId` bigint(20) default NULL,
  `JobStatusId` int(11) NOT NULL,
  `JobType` varchar(50) NOT NULL,
  `Priority` int(11) NOT NULL default '5',
  `DateCreated` datetime NOT NULL,
  `DateStarted` datetime default NULL COMMENT 'The date that the job started running',
  `ClientReference` varchar(255) NOT NULL,
  `Message` mediumtext,
  `ClientReferenceType` int(11) default NULL,
  `RefData` longtext,
  PRIMARY KEY  (`Id`),
  KEY `JobContext_Asset_FK` (`AssetId`),
  KEY `IX_InDJob_JobStatus_FK` (`JobStatusId`),
  CONSTRAINT `InDJob_JobStatus_FK` FOREIGN KEY (`JobStatusId`) REFERENCES `jobstatus` (`Id`),
  CONSTRAINT `JobContext_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobstatus`
--

DROP TABLE IF EXISTS `jobstatus`;
CREATE TABLE `jobstatus` (
  `Id` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jobstatus`
--

LOCK TABLES `jobstatus` WRITE;
/*!40000 ALTER TABLE `jobstatus` DISABLE KEYS */;
INSERT INTO `jobstatus` VALUES (1,'Pending'),(2,'Running'),(3,'Completed'),(4,'Failed');
/*!40000 ALTER TABLE `jobstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
CREATE TABLE `language` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `NativeName` varchar(255) default NULL,
  `Code` varchar(20) NOT NULL,
  `IsSuspended` tinyint(1) NOT NULL,
  `IsDefault` tinyint(1) NOT NULL default '0',
  `IsRightToLeft` tinyint(1) NOT NULL default '0',
  `IconFilename` varchar(255) default NULL,
  `UsesLatinAlphabet` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Name` (`Name`),
  UNIQUE KEY `Code` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'English',NULL,'en',0,1,0,NULL,1);
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lastattributevalue`
--

DROP TABLE IF EXISTS `lastattributevalue`;
CREATE TABLE `lastattributevalue` (
  `AttributeId` bigint(20) NOT NULL,
  `NumberValue` bigint(20) default NULL,
  PRIMARY KEY  (`AttributeId`),
  CONSTRAINT `LastAV_Attribute_FK` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `lastattributevalue`
--

LOCK TABLES `lastattributevalue` WRITE;
/*!40000 ALTER TABLE `lastattributevalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `lastattributevalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list`
--

DROP TABLE IF EXISTS `list`;
CREATE TABLE `list` (
  `Id` bigint(20) NOT NULL,
  `Identifier` varchar(100) default NULL,
  `Name` varchar(255) default NULL,
  `Description` varchar(255) default NULL,
  `CannotAddNew` tinyint(1) default NULL,
  `NoHTMLMarkup` tinyint(1) default '0',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `list`
--

LOCK TABLES `list` WRITE;
/*!40000 ALTER TABLE `list` DISABLE KEYS */;
INSERT INTO `list` VALUES (1,'copy','Page Copy','Copy for application pages',1,0),(2,'help','Help Copy','Copy for the help pages',1,0),(7,'menu','Menu Copy','Copy for menu items',1,0),(8,'terms','Common Terms','Common terms such as lightbox and item',1,0),(9,'labels','Form Labels','Labels for form elements',1,0),(10,'headings','Page Headings','Copy for page headings',1,0),(11,'buttons','Button Values','Copy for buttons',1,1),(12,'links','Links Text','Copy for links throughout the application',1,0),(13,'snippets','Snippets','Miscellaneous snippets of copy',1,0),(14,'javascript','Javascript Messages','Copy in javascript pop-ups',1,1),(15,'system','System Messages','Copy for system messages',1,1),(16,'titles','Browser Titles','Copy for browser titles',1,1),(17,'technical','Technical content','Content with a technical purpose',1,0),(18,'feedback_subject','Feedback Subject','List items to be used for the feedback subject',0,1),(19,'tooltip_text','Tooltip Text','Content used within tooltips',1,1);
/*!40000 ALTER TABLE `list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listattributevalue`
--

DROP TABLE IF EXISTS `listattributevalue`;
CREATE TABLE `listattributevalue` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AttributeId` bigint(20) NOT NULL,
  `IsEditable` tinyint(1) NOT NULL default '1',
  `Value` varchar(255) NOT NULL,
  `AdditionalValue` mediumtext,
  `SequenceNumber` int(11) default NULL,
  `ActionOnAssetId` bigint(20) default NULL,
  `MapToFieldValue` varchar(255) default NULL,
  `IconFile` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_LAV_Attribute_FK` (`AttributeId`),
  KEY `IX_LAV_AOA_FK` (`ActionOnAssetId`),
  CONSTRAINT `LAV_AOA_FK` FOREIGN KEY (`ActionOnAssetId`) REFERENCES `actiononasset` (`Id`),
  CONSTRAINT `LAV_Attribute_FK` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `listattributevalue`
--

LOCK TABLES `listattributevalue` WRITE;
/*!40000 ALTER TABLE `listattributevalue` DISABLE KEYS */;
INSERT INTO `listattributevalue` VALUES (1,30,1,'External Use',NULL,1,NULL,NULL,NULL),(2,30,1,'Internal Use',NULL,2,NULL,NULL,NULL),(3,30,1,'Other... (please specify below)',NULL,3,NULL,NULL,NULL),(6,21,1,'Inactive',NULL,1,NULL,NULL,NULL),(7,21,1,'Active',NULL,2,NULL,NULL,NULL),(8,21,1,'Expired',NULL,3,NULL,NULL,NULL);
/*!40000 ALTER TABLE `listattributevalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listitem`
--

DROP TABLE IF EXISTS `listitem`;
CREATE TABLE `listitem` (
  `Identifier` varchar(200) NOT NULL,
  `LanguageId` bigint(20) NOT NULL default '1',
  `ListId` bigint(20) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `Summary` mediumtext,
  `Body` mediumtext,
  `DateAdded` datetime default NULL,
  `CannotBeDeleted` tinyint(1) default NULL,
  `ListItemTextTypeId` bigint(20) NOT NULL default '1',
  PRIMARY KEY  (`Identifier`,`LanguageId`),
  KEY `IX_Relationship102` (`ListId`),
  KEY `IX_Relationship152` (`ListItemTextTypeId`),
  KEY `IX_Relationship179` (`LanguageId`),
  CONSTRAINT `Relationship102` FOREIGN KEY (`ListId`) REFERENCES `list` (`Id`),
  CONSTRAINT `Relationship152` FOREIGN KEY (`ListItemTextTypeId`) REFERENCES `listitemtexttype` (`Id`),
  CONSTRAINT `Relationship179` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `listitem`
--

LOCK TABLES `listitem` WRITE;
/*!40000 ALTER TABLE `listitem` DISABLE KEYS */;
INSERT INTO `listitem` VALUES ('a-lightbox',1,8,'Term for the Lightbox',NULL,'Lightbox',NULL,1,2),('access-level-node',1,8,'Access level node name',NULL,'access level',NULL,1,2),('access-level-nodes',1,8,'Access level node name plural',NULL,'access levels',NULL,1,2),('access-level-root',1,8,'Access level root name',NULL,'Access Levels',NULL,1,2),('accessLevelErrorBlankName',1,15,'accessLevelErrorBlankName',NULL,'An access level may not have a blank name.',NULL,1,2),('add-empty-related-assets-main',1,1,'Add empty related assets lead in text',NULL,'<p>Listed below are the relationships that assets of this type can have. Select the number of empty related assets that you would like to add to this asset for each listed relationship and click the save button at the bottom to generate. You can optionally provide a name for the empty assets that will be used to populate the name attribute of each asset created (if suitable):</p>',NULL,1,1),('add-new-value',1,13,'Snippet - Add new value',NULL,'Or, add new value',NULL,1,2),('addingFile',1,15,'addingFile',NULL,'Adding file',NULL,1,2),('adminUserCannotBeInGroups',1,15,'adminUserCannotBeInGroups',NULL,'If this user is an administrator then they cannot be in a group.',NULL,1,2),('advanced-search-intro',1,1,'Intro - Advanced search',NULL,'',NULL,1,1),('agreement-checkbox',1,1,'Agreement - Checkbox',NULL,'I have read and accept the <a href=\"../action/viewAgreeementPopup?Id=?AgreementId?\" target=\"_blank\" onclick=\"popupViewAgreement(?AgreementId?); return false;\" />Agreement</a>',NULL,1,2),('agreement-checkbox-lightbox',1,1,'Agreement checkbox for lightbox',NULL,'I have read and accept the agreement for each #item#',NULL,1,2),('agreementNameInUse',1,15,'agreementNameInUse',NULL,'The name you have entered is already in use.',NULL,1,2),('app-name',1,8,'Application Name',NULL,'Asset Bank',NULL,1,2),('approval-granted',1,13,'Snippet - approval granted',NULL,'Approval granted',NULL,1,2),('approval-rejected',1,13,'Snippet - approval rejected',NULL,'Approval rejected',NULL,1,2),('asset-background-edit-in-progress',1,1,'View asset: background edit in progress',NULL,'<strong>This #item# is currently being updated and cannot be edited.</strong><br/>If you wish to make changes to this asset please try again in a few minutes.',NULL,1,1),('assetBoxErrorPermission',1,15,'assetBoxErrorPermission',NULL,'You do not have download permission on any of the items in your lightbox.',NULL,1,2),('assetBoxesOutOfDate',1,15,'assetBoxesOutOfDate',NULL,'Your available [0] have changed since the page was loaded - please try again.',NULL,1,2),('assetboxNameNotUnique',1,15,'assetboxNameNotUnique',NULL,'The name you have entered is already in use.',NULL,1,2),('assetboxNameRequired',1,15,'assetboxNameRequired',NULL,'Please enter a name.',NULL,1,2),('AssetBoxNoDownloadsLeft',1,15,'AssetBoxNoDownloadsLeft',NULL,'Sorry, these downloads take you over your daily limit. Please remove some items or try again tomorrow.',NULL,1,2),('assetBoxNotAvailable',1,15,'assetBoxNotAvailable',NULL,'The shared [0] you selected is no longer available.',NULL,1,2),('assetBoxRemoved',1,15,'assetBoxRemoved',NULL,'The shared [0] you were using is no longer available.',NULL,1,2),('assetEntityDefaultCategoryDoesNotExist',1,15,'assetEntityDefaultCategoryDoesNotExist',NULL,'The default category that you have entered, [0] does not exist or is not a [1] category.',NULL,1,2),('assetEntityDefaultCategoryInWrongTree',1,15,'assetEntityDefaultCategoryInWrongTree',NULL,'The default category that you have entered, [0] is not a [1] category. Please enter the ID of a [1] category (not a [2] category).',NULL,1,2),('assetEntityDefaultCategoryNotDescendantOfRootCategory',1,15,'assetEntityDefaultCategoryNotDescendantOfRootCategory',NULL,'The default category that you have entered is not a descendant of the root category.',NULL,1,2),('assetEntityDefaultCategoryNotSet',1,15,'assetEntityDefaultCategoryNotSet',NULL,'The default category must be set to a valid category ID in order to only allow assets of this type to be in that category.',NULL,1,2),('assetEntityHasRelationships',1,15,'assetEntityHasRelationships',NULL,'This type cannot currently be deleted because it is referenced by one or more other type\'s relationships. Please reconfigure or remove any parent types first.',NULL,1,2),('assetEntityRelationshipNamesRequired',1,15,'assetEntityRelationshipNamesRequired',NULL,'Please enter terms for all enabled relationships.',NULL,1,2),('assetEntityRootCategoryDoesNotExist',1,15,'assetEntityRootCategoryDoesNotExist',NULL,'The root category that you have entered, [0], does not exist or is not a descriptive category.',NULL,1,2),('assetsExcludedFromDownload',1,15,'assetsExcludedFromDownload',NULL,'You do not have permission to download ?count? of the assets in your #a-lightbox# in your selected format. Any that you don\'t have permission on will be excluded from your zip file.',NULL,1,2),('assetsLockedWarning',1,15,'assetsLockedWarning',NULL,'Note that ?numLockedMatches? matching #items# are currently locked by other users and so have not been added to your batch.',NULL,1,2),('audio',1,8,'Term for audio',NULL,'audio',NULL,1,2),('audio-conversion-advanced-copy',1,1,'Copy - Audio Conversion Advanced Copy',NULL,'<em><p>You can use the following form to adjust the audio sample rate (Hz) of the converted audio clip. Note that selecting a value higher than the input clip\'s value will result in no change.</p><p>You can also select to download a particular chunk of the audio clip by setting the offset start position and the duration you want to download.</p></em>',NULL,1,1),('available-access-levels-label',1,1,'Available access levels label','Label for the \"Available access levels\" in the \"Access Levels\" section on the \"Add/Edit Asset\" page','Available access levels (remember to click Add to add the access level):',NULL,1,1),('available-categories-label',1,1,'Available categories label','Label for the \"Available categories\" in the \"Categories\" section on the \"Add/Edit Asset\" page','Available categories (remember to click Add to add the category):',NULL,1,1),('batch-release',1,8,'Batch release',NULL,'batch release',NULL,1,2),('batch-releases',1,8,'Batch releases',NULL,'batch releases',NULL,1,2),('browse-intro',1,1,'Intro - Browse',NULL,'',NULL,1,1),('browse-results-restricted',1,13,'Snippet - Number of browse results (max exceeded)',NULL,'more than ?numberHits? #items#',NULL,1,3),('bulk-applet-instruct-1',1,1,'Bulk Upload - applet instructions 1',NULL,'<p>To upload files to the server click browse below and select your files. You can select multiple files at once then click upload.</p>',NULL,1,1),('bulk-applet-instruct-2',1,1,'Bulk Upload - applet instructions 2',NULL,'<p>Once files have finished uploading you will be redirected to the import page. To access the import page directly, <a href=\"../action/viewStartImport?uploaded=1\">click here</a> - and then select files from the dropdowns for processing.</p>',NULL,1,1),('bulk-otherwise-ftp',1,1,'Bulk - Otherwise ftp',NULL,'Otherwise, you need to connect using an FTP client.',NULL,1,1),('bulk-otherwise-win',1,1,'Bulk - Otherwise windows',NULL,'Otherwise, you need to connect using Windows Explorer.',NULL,1,1),('bulk-update-skipping-asset-cant-leave-live',1,13,'Snippet - Bulk Update Skipping asset - Can\'t leave live',NULL,'Skipping asset: [0] you do not have permission to edit this asset and leave it live, you must submit it for approval',NULL,1,2),('bulk-update-skipping-asset-cant-submit-live',1,13,'Snippet - Bulk Update Skipping asset - Can\'t submit to live',NULL,'Skipping asset: [0] you do not have permission to edit this asset and put it live, you must submit it for approval',NULL,1,2),('bulk-update-skipping-asset-conflicting-workflow',1,13,'Snippet - Bulk Update Skipping asset - Conflicting workflow',NULL,'Skipping asset: [0] this asset is currently involved in another approval process and can\'t be edited via bulk update until that is complete',NULL,1,2),('bulk-upload-help',1,1,'Bulk Upload - upload help',NULL,'<p>An easy way to open your bulk upload directory in Windows Explorer is as follows:</p><ul><li>Go to the Windows &quot;Start&quot; menu.</li><li>Select &quot;Run ...&quot;</li><li>Copy the following, and paste into the &quot;Open&quot; field:<br/><strong>explorer ?FTPPath?</strong></li><li>Click OK.</li></ul>',NULL,1,1),('bulk-upload-help-ftp1',1,1,'Bulk Upload - upload help ftp 1',NULL,'<h3>Windows</h3><p>If you are using a browser that does not provide read-write access to FTP sites then you can use Windows Explorer instead.</p>',NULL,1,1),('bulk-upload-help-ftp2',1,1,'Bulk Upload - upload help ftp 2',NULL,'<p><strong>Note:</strong> by default Internet Explorer 7 gives you access to an FTP site in read-only mode.</p><h3>Mac Users</h3><p>You need to install an FTP client (or use the command line if you know how) and connect using the following details:</p><ul><li><strong>Host:</strong> ?ftpHost?</li><li><strong>Username:</strong> ?ftpUsername?</li><li><strong>Password:</strong> ?ftpPassword?</li></ul><br /><p>Once connected, move into your personal upload directory - i.e. the directory with the same name as your username.</p>',NULL,1,1),('bulk-upload-in-progress',1,1,'Bulk upload - Import in progress',NULL,'<p>The import is in progress.</p><p>This page will automatically refresh every 20 seconds until the import process finishes. If for some reason this does not happen you may <a href=\"?url?\">update the page</a> manually to check the status.</p>',NULL,1,1),('button-acknowledge',1,11,'Button - Acknowledge',NULL,'Acknowledge',NULL,1,2),('button-add',1,11,'Button - Add',NULL,'Add',NULL,1,2),('button-add-arrow',1,11,'Button - Add arrow',NULL,'Add &raquo;',NULL,1,2),('button-add-selected-users',1,11,'Button - Add Selected Users',NULL,'Add Selected Users',NULL,1,2),('button-apply',1,11,'Button - Apply',NULL,'Apply &raquo;',NULL,1,2),('button-apply-and-close',1,11,'Button - Apply and close',NULL,'Apply and close',NULL,1,2),('button-apply-changes',1,11,'Button - Apply changes',NULL,'Apply changes',NULL,1,2),('button-archive',1,11,'Button - Archive',NULL,'Archive',NULL,1,2),('button-back',1,11,'Button - Back',NULL,'&laquo; Back',NULL,1,2),('button-cancel',1,11,'Button - Cancel',NULL,'Cancel',NULL,1,2),('button-cancel-upload',1,11,'Button - Cancel Upload',NULL,'Cancel Upload',NULL,1,2),('button-cancel-uploads',1,11,'Button - Cancel Uploads',NULL,'Cancel Uploads',NULL,1,2),('button-close',1,11,'Button - Close',NULL,'Close',NULL,1,2),('button-copy-items',1,11,'Button - Copy items',NULL,'Copy items',NULL,1,2),('button-create',1,11,'Button - Create',NULL,'Create',NULL,1,2),('button-create-copy',1,11,'Button - Create Copy',NULL,'Create Copy',NULL,1,2),('button-create-doc-from-brand-template',1,11,'Button - Create Document From Brand Template',NULL,'Create Document',NULL,1,2),('button-create-from-brand-template-and-download',1,11,'Button - Create Doc from Brand Template and Download',NULL,'Create and Download',NULL,1,2),('button-create-now',1,11,'Button - Create now',NULL,'Create now',NULL,1,2),('button-crop',1,11,'Button - Crop & Close',NULL,'Crop & Close',NULL,1,2),('button-delete',1,11,'Button - Delete',NULL,'Delete',NULL,1,2),('button-delete-assets',1,11,'Button - Delete assets',NULL,'Delete assets',NULL,1,2),('button-delete2',1,11,'Button - Delete with arrow',NULL,'Delete &raquo;',NULL,1,2),('button-dictionary-chooser',1,11,'Button - Dictionary Chooser',NULL,'Select...',NULL,1,2),('button-download',1,11,'Button - Download',NULL,'Download',NULL,1,2),('button-download-all',1,11,'Button - Download all',NULL,'Download all',NULL,1,2),('button-download-high-res',1,11,'Button - Download high res',NULL,'Download high-res',NULL,1,2),('button-download-now',1,11,'Button - Download now',NULL,'Download now',NULL,1,2),('button-download-original',1,11,'Button - Download Original',NULL,'Download Original',NULL,1,2),('button-download-originals',1,11,'Button - Download Originals',NULL,'Download Originals',NULL,1,2),('button-edit',1,11,'Button - Edit',NULL,'Edit',NULL,1,2),('button-email',1,11,'Button - Email',NULL,'Email',NULL,1,2),('button-email-now',1,11,'Button - Email now',NULL,'Email now',NULL,1,2),('button-get-current',1,11,'Button - get current','Button for \"get current\" on the \"Update video keywords\" page','get current',NULL,1,2),('button-go',1,11,'Button - Go',NULL,'Go',NULL,1,2),('button-go-arrow',1,11,'Heading - Go with arrow',NULL,'Go &raquo;',NULL,1,2),('button-indesign-download-indd',1,11,'Button - InDesign: Download InDesign Document',NULL,'Download InDD File',NULL,1,2),('button-indesign-make-templated',1,11,'Button - InDesign: Make Templated',NULL,'Make Templated',NULL,1,2),('button-indesign-override-template',1,11,'Button - InDesign: Override Template',NULL,'Override Template',NULL,1,2),('button-indesign-regenerate-pdf',1,11,'Button - InDesign: Regenerate PDF',NULL,'Regenerate PDF',NULL,1,2),('button-keyword-chooser',1,11,'Button - Keyword Chooser',NULL,'Keyword Chooser...',NULL,1,2),('button-lightbox-download',1,11,'Button - Lightbox download',NULL,'Download',NULL,1,2),('button-lightbox-more-actions',1,11,'Button - Lightbox more actions',NULL,'More actions',NULL,1,2),('button-link-assets',1,11,'Button - Link Assets',NULL,'Link #items#',NULL,1,2),('button-login',1,11,'Button - Login',NULL,'Login',NULL,1,2),('button-manage-master',1,11,'Button - Manage Master List',NULL,'Manage Master List...',NULL,1,2),('button-move-items',1,11,'Button - Move items',NULL,'Move items',NULL,1,2),('button-move-keyword',1,11,'Button - Move Keyword',NULL,'Move Keyword',NULL,1,2),('button-next',1,11,'Button - Next',NULL,'Next &raquo;',NULL,1,2),('button-preview',1,11,'Button - Preview',NULL,'Preview &raquo;',NULL,1,2),('button-preview-agreement',1,11,'Button - Preview agreement',NULL,'Preview agreement',NULL,1,2),('button-refresh-view',1,11,'Button - Refresh view',NULL,'Refresh view',NULL,1,2),('button-regenerate-all-pdfs-in-batch-release',1,11,'Button - InDesign: Regenerate all PDFs in batch release',NULL,'Regenerate PDFs for all InDesign Documents',NULL,1,2),('button-register',1,11,'Button - Register',NULL,'Register',NULL,1,2),('button-remove',1,11,'Button - Remove',NULL,'Remove',NULL,1,2),('button-remove-file',1,11,'Button - Remove File',NULL,'Remove File',NULL,1,2),('button-request-approval',1,11,'Button - Request Approval',NULL,'Request Approval',NULL,1,2),('button-request-cd',1,11,'Button - Request on CD',NULL,'Request all on CD',NULL,1,2),('button-reset',1,11,'Button - Reset',NULL,'Reset',NULL,1,2),('button-reset-password',1,11,'Button - Reset Password',NULL,'Reset Password',NULL,1,2),('button-resubmit',1,11,'Button - Resubmit',NULL,'Resubmit',NULL,1,2),('button-save',1,11,'Button - Save',NULL,'Save',NULL,1,2),('button-save-continue',1,11,'Button - Save and Continue',NULL,'Save & Continue &raquo;',NULL,1,2),('button-search',1,11,'Button - Search',NULL,'Search',NULL,1,2),('button-select',1,11,'Button - Select',NULL,'Select',NULL,1,2),('button-select-all-on-page',1,11,'Button - Select all on page',NULL,'Select All on Page',NULL,1,2),('button-select-all-results',1,11,'Button - Select all results',NULL,'Select All Results',NULL,1,2),('button-select-for-cms',1,11,'Button - Select for CMS',NULL,'Select for CMS',NULL,1,2),('button-send',1,11,'Button - Send',NULL,'Send',NULL,1,2),('button-skip',1,11,'Button - Skip',NULL,'Skip &raquo;',NULL,1,2),('button-start-import',1,11,'Button - Start Import',NULL,'Start Import',NULL,1,2),('button-start-new-bulk-upload',1,11,'Button - Start new bulk upload',NULL,'Start new bulk upload &raquo;',NULL,1,2),('button-submit',1,11,'Button - Submit',NULL,'Submit',NULL,1,2),('button-update',1,11,'Button - Update',NULL,'Update',NULL,1,2),('button-upload',1,11,'Button - Upload',NULL,'Upload',NULL,1,2),('button-upload-file',1,11,'Button - Upload file',NULL,'Upload file',NULL,1,2),('button-upload-files',1,11,'Button - Upload files',NULL,'Upload files',NULL,1,2),('button-upload-more-files',1,11,'Button - Upload more files',NULL,'Upload more files',NULL,1,2),('cannotDeleteAssetEntity',1,15,'cannotDeleteAssetEntity',NULL,'This type cannot be deleted as there are #items# currently assigned to it. Please export and delete the #items# first.',NULL,1,2),('category-conflict-error-intro',1,9,'Category Conflict Error Intro',NULL,'<p>You are unable to edit assets of this type due to org unit restrictions.</p><p>Please contact your IT support team or use the <a href=\"viewContact\">contact page</a>, giving the details below for reference.</p>',NULL,1,1),('category-node',1,8,'Category node name',NULL,'category',NULL,1,2),('category-nodes',1,8,'Category node name plural',NULL,'categories',NULL,1,2),('category-root',1,8,'Category root name',NULL,'Categories',NULL,1,2),('categoryErrorBlankName',1,15,'categoryErrorBlankName',NULL,'The name cannot be blank.',NULL,1,2),('categoryErrorDuplicateAccessLevelName',1,15,'categoryErrorDuplicateAccessLevelName',NULL,'An access level already exists at this level with the same name.',NULL,1,2),('categoryErrorDuplicateName',1,15,'categoryErrorDuplicateName',NULL,'A subcategory already exists in this category with the same name.',NULL,1,2),('categoryErrorIdParam',1,15,'categoryErrorIdParam',NULL,'The Id parameter is missing (used as root category when forward to CategoryAdminAction).',NULL,1,2),('categoryErrorIdToDelete',1,15,'categoryErrorIdToDelete',NULL,'The IdToDelete parameter is missing.',NULL,1,2),('categoryErrorInvalidId',1,15,'categoryErrorInvalidId',NULL,'The ID of the category or category tree to be displayed is invalid.',NULL,1,2),('categoryErrorMissingAsset',1,15,'categoryErrorMissingAsset',NULL,'This #item# could not be added to your #a-lightbox#. It may have been deleted recently or you do not have sufficient permission.',NULL,1,2),('categoryErrorMissingId',1,15,'categoryErrorMissingId',NULL,'Category or CategoryTree Id is missing from form parameters.',NULL,1,2),('categoryErrorParentIdMissing',1,15,'categoryErrorParentIdMissing',NULL,'The parent id of the category to be added is missing.',NULL,1,2),('categoryExtensionNameError',1,15,'categoryExtensionNameError',NULL,'The asset\'s name could not be assigned to the category because there is an existing category with the same name.',NULL,1,2),('changestooltip-view-workflow-audit',1,19,'Tooltip - View workflow audit popup',NULL,'View asset workflow audit in a new window',NULL,1,2),('cmsSessionError',1,15,'cmsSessionError',NULL,'If you wish to select another image please close this window and return to the content management system.',NULL,1,2),('commercialOptionInvalidName',1,15,'commercialOptionInvalidName',NULL,'Please enter a name for the Commercial Option.',NULL,1,2),('commercialOptionInvalidPrice',1,15,'commercialOptionInvalidPrice',NULL,'Please enter a valid value for the price.',NULL,1,2),('commercialPurchaseNoUserNotes',1,15,'commercialPurchaseNoUserNotes',NULL,'You must enter some notes describing your intended uses.',NULL,1,2),('company-name',1,8,'Company Name',NULL,'Asset Bank',NULL,1,2),('conditions',1,1,'Terms and Conditions','The copy for the Terms and Conditions','<h1 class=\"underline\">Terms and Conditions</h1> <p>Before you download an asset, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style=\"color: #888888;\">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('conditions-extra',1,1,'Terms and Conditions (Extra)','Extra Terms and conditions, if you need them.','<p><span style=\"color: #888888;\">(These extra terms and conditions can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('conditions-file',1,1,'Terms and Conditions (Files)','File download Terms and Conditions copy.','<h1>Terms and Conditions</h1> <p>Before you download a file, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style=\"color: #888888;\">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('conditions-image',1,1,'Terms and Conditions (Images)','Image download Terms and Conditions copy.','<h1>Terms and Conditions</h1> <p>Before you download an image, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style=\"color: #888888;\">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('conditions-shared-file',1,1,'Terms and Conditions (Shared Files)',NULL,'<h1>Terms and Conditions</h1> <p>Before you download a file, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style=\"color: #888888;\">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('conditions-upload',1,1,'Terms and Conditions for uploading','The copy for the Terms and Conditions for uploading','<p>This page shows the Terms and Conditions that you want users to read and agree to before they upload an image. </p><p>The rest of this page shows latin text as a place holder for your own Terms and Conditions. To change this text, log in an an admin user and go to Admin-&gt;Content. </p><p>Consequat dolore molestie veniam veniam consequat lorem vero. Laoreet hendrerit delenit magna in suscipit. </p><h2>Another header</h2><p>Commodo consequat in dolore, tincidunt exerci iusto eum amet wisi iusto nostrud dolore:</p><ul><li>Ad erat dolore qui, blandit sit sed veniam blandit. </li><li>Te ex vero, in consequat et augue dignissim at eu ullamcorper dignissim dolor veniam. </li><li>Iriure esse luptatum feugait vel esse eu, qui aliquip, praesent autem. </li><li>Enim praesent praesent in tation hendrerit nibh lobortis ut. </li></ul><h3>Another header</h3><p>Dolor suscipit, at velit commodo lobortis. In autem augue duis exerci nisl esse laoreet ex vero adipiscing aliquip ut vel vel aliquip.</p><h4>Another header</h4><p>Dolor suscipit, at velit commodo lobortis. In autem augue duis exerci nisl esse laoreet ex vero adipiscing aliquip ut vel vel aliquip.</p>',NULL,1,1),('conditions-video',1,1,'Terms and Conditions (Video)','Video download Terms and Conditions copy.','<h1>Terms and Conditions</h1> <p>Before you download a video, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style=\"color: #888888;\">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('contact-details',1,1,'Contact details','Contact detailed displayed on the Contact Us page','<p>Note to admin users: your contact details can be added here via the admin area.</p><p>Please use the form below to send us your comments and suggestions.</p>',NULL,1,1),('contact-sheet-extra-label',1,1,'Snippet - Contact sheet extra label',NULL,'',NULL,1,2),('contact-sheet-extra-value',1,1,'Snippet - Contact sheet extra value',NULL,'',NULL,1,2),('copy-about-asset-bank',1,1,'Copy - About Asset Bank',NULL,'',NULL,1,1),('copy-bulk-delete-warning',1,1,'Bulk delete warning',NULL,'Warning: If you proceed the assets in the selected batch will be permanently deleted from the system.',NULL,1,3),('copy-bulk-update-1',1,1,'Copy - Bulk Update 1',NULL,'<div class=\"info\"><strong>You currently have a bulk update batch defined.</strong><br />Please note that #items# in this batch are locked by you, so that other users cannot perform bulk updates on them until you click &quot;Finish with this batch&quot;.</div><p>There are <strong>?numInBatch?</strong> #items# in the batch.<br />There are <strong>?numSelUpdate?</strong> #items# selected for update.</p>',NULL,1,1),('copy-bulk-update-permissions-warning',1,1,'Copy - Bulk Update Permissions Warning',NULL,'<div class=\"warning\">You do not have permission to edit all of the selected assets. <strong>?numUpdatePermissionDenied?</strong> #items# have been removed from the batch.</div>',NULL,1,1),('copy-contact-success',1,1,'Contact Success copy',NULL,'<p>Thank you for getting in touch. </p><p>If your message requires a response we shall be in contact as soon as possible. </p><p>Please use the menu on the left to continue using #app-name#. </p>',NULL,1,1),('copy-copy-asset-step1',1,1,'Copy - Copy asset step 1',NULL,'<p>Select how you would like each child relationship from the source asset to be dealt with from the list below then click Next to move on to updating the copy\'s metadata:</p>',NULL,1,1),('copy-copy-asset-step1-warning',1,1,'Copy - Copy asset step 1 Warning',NULL,'Please note: Peer relationships are maintained outside of the copy asset process (so any peer relationships on the source asset will not be present on the resulting copy and should be reinstated manually if they are needed)',NULL,1,1),('copy-download-attributes',1,1,'Copy - Download attributes intro',NULL,'',NULL,1,1),('copy-download-attributes-lightbox',1,1,'Copy - Download attributes intro on lightbox',NULL,'',NULL,1,1),('copy-incomplete-metadata-warning',1,1,'Copy - Incomplete Metadata Warning',NULL,'This item has incomplete metadata',NULL,1,2),('copy-incomplete-metadata-warning-tooltip',1,1,'Copy - Incomplete Metadata Warning Tooltip',NULL,'Edit the item to add missing metadata',NULL,1,2),('current-file',1,13,'Snippet - Current File',NULL,'Current file',NULL,1,2),('custom-header-content',1,1,'Custom header content','Custom content to go in header','',NULL,1,3),('customFieldName',1,15,'customFieldName',NULL,'You need to provide a name for this custom field',NULL,1,2),('customFieldType',1,15,'customFieldType',NULL,'You need to provide a type for this custom field',NULL,1,2),('customFieldUsageType',1,15,'customFieldUsageType',NULL,'You need to provide a usage type for this custom field',NULL,1,2),('customFieldValueValue',1,15,'customFieldValueValue',NULL,'You need to provide a value',NULL,1,2),('default-related-assets-empty-child',1,1,'Default related assets no missing child assets text',NULL,'<p>This asset isn\'t currently missing any default child relationships.</p>',NULL,1,1),('default-related-assets-empty-peer',1,1,'Default related assets no missing peer assets text',NULL,'<p>This asset isn\'t currently missing any default peer relationships.</p>',NULL,1,1),('default-related-assets-main',1,1,'Default related assets lead in text',NULL,'<p>Listed below are the default relationships for this asset that need to be created. Uncheck any in the list that you don\'t want to be setup and click the save button...</p>',NULL,1,1),('displayAttributeNotSelected',1,15,'displayAttributeNotSelected',NULL,'You need to select the attribute that you want to display',NULL,1,2),('download',1,1,'Download page copy','The copy shown on the download page.','',NULL,1,1),('download-lightbox',1,1,'Download Lightbox text','Text shown on the \"Download Lightbox\" page','<p><em>If you are not able to unzip zip files then please return to the previous page and download the items individually.</em></p>',NULL,1,1),('duplicateAssetsInfoOnView',1,15,'duplicateAssetsInfoOnView',NULL,'<h3>Duplicate #items#</h3><p>The following linked resources refer to multiple #items# in the system. You will be asked to resolve these conflicts when editing the source file.</p>',NULL,1,2),('duplicateAssetsInLightboxWarning',1,15,'duplicateAssetsInLightboxWarning',NULL,'<h3>Duplicate #items#</h3><p>Your #a-lightbox# contains #items# which have the same filename.</p><p>Proceeding with the download will result in matching assets being overwritten in the working folder. Please remove the duplicate #items#.</p>',NULL,1,2),('duplicateAssetsWarningOnEdit',1,15,'duplicateAssetsWarningOnEdit',NULL,'<h3>Duplicate #items#</h3><p>This document contains linked resources which refer to more than one possible #item# in the system. Please select which one(s) you want to use:</p>',NULL,1,2),('duplicateInDesignLinkedImage',1,15,'duplicateInDesignLinkedImage',NULL,'Warning: the InDesign document contains a linked file, [0], that has multiple matches in #app-name#.',NULL,1,2),('duplicateSavedSearchForNewSavedSearch',1,15,'duplicateSavedSearchForNewSavedSearch',NULL,'There is already a saved search with the same name',NULL,1,2),('ecommerceValidationSelectUsage',1,15,'ecommerceValidationSelectUsage',NULL,'Please select either personal use or commercial use to continue.',NULL,1,2),('edmAlreadyExists',1,15,'edmAlreadyExists',NULL,'This mapping already exists - please edit the existing entry',NULL,1,2),('edmSelectAttribute',1,15,'edmSelectAttribute',NULL,'You need to select the attribute for this mapping',NULL,1,2),('edmSelectValue',1,15,'edmSelectValue',NULL,'You need to select the embedded data value for this mapping',NULL,1,2),('edmUploadsDownloads',1,15,'edmUploadsDownloads',NULL,'You need to select whether this mapping applies to uploads or downloads',NULL,1,2),('email-linked-text',1,1,'Email Link Text',NULL,'This files exceeds the maximum file size for email attachments. Your email will include a link to the file instead.',NULL,1,2),('email-used-for-username',1,1,'Copy - Email used for username',NULL,'<p>Please note that your email address is used for your unique username. <br />You cannot change your email address to one which is already used by another user.</p>',NULL,1,1),('error-intro',1,9,'Intro - Error Page',NULL,'<p>Sorry, an error has occurred. Please contact your IT support team or use the <a href=\"viewContact\">contact page</a>.</p><p>Include as many relevant details as you can, for example:</p><ul><li>Your name and username. </li><li>The time of the error.</li><li>The URL of this page (shown in the address bar at the top of your browser).</li><li>What you were trying to do when the error occurred.</li><li>A screenshot.<br/><li>Copy the error text if there is some shown below.</li></ul>',NULL,1,1),('establishment-name',1,1,'Name of establishment field','The field name of the establishment/division/company displayed on the contact page','Establishment',NULL,1,2),('export-copy',1,1,'Export page copy','Export page copy','<p>If you enter a reference in the &#39;Append to filename&#39; field below then this will be appended to export filenames.<br />If you enter text in the &#39;Description&#39; field then this will appear in the header of the exported spreadsheet.<br />Check &#39;Export asset files&#39; if you want file data to be exported as well as metadata.</p>',NULL,1,1),('failedAddingAssetsToAssetBox',1,15,'failedAddingAssetsToAssetBox',NULL,'[0] asset(s) were successfully added to the #a-lightbox# but [1] asset(s) could not be added (it may have been deleted recently or you do not have sufficient permission)',NULL,1,2),('failedDataLookup',1,15,'failedDataLookup',NULL,'Error performing data lookup: ',NULL,1,2),('failedDeleteBatchRelease',1,15,'failedDeleteBatchRelease',NULL,'Unable to delete this #batch-release# because there are assets associated with it.',NULL,1,2),('failedEditingRequestWorkflowItem',1,15,'failedEditingRequestWorkflowItem',NULL,'You cannot edit the request in this status.',NULL,1,2),('failedEmailSend',1,15,'failedEmailSend',NULL,'The email could not be sent to the administrator, please try again. If this error continues please contact the administrator.',NULL,1,2),('failedLoginAsUser',1,15,'failedLoginAsUser',NULL,'Unable to login as the given user',NULL,1,2),('failedLoginIPBlock',1,15,'failedLoginIPBlock',NULL,'You have been prevented from logging in, since your IP address is not in the accepted range. Please contact your administrator.',NULL,1,2),('failedSecurityAnswers',1,15,'failedSecurityAnswers',NULL,'The provided answers are not valid.',NULL,1,2),('failedSelectBatchRelease',1,15,'failedSelectBatchRelease',NULL,'Please select a #batch-release# from the list',NULL,1,2),('failedValidation',1,15,'failedValidation',NULL,'Please complete all mandatory fields.',NULL,1,2),('failedValidationAccessLevel',1,15,'failedValidationAccessLevel',NULL,'You need to make sure this image is in at least one access level.',NULL,1,2),('failedValidationAccessLevels',1,15,'failedValidationAccessLevels',NULL,'You must put the asset in at least one #access-level-node#.',NULL,1,2),('failedValidationAcknowledge',1,15,'failedValidationAcknowledge',NULL,'Please indicate that you acknowledge this message.',NULL,1,2),('failedValidationActiveDirectoryUsernamePatternMatch',1,15,'failedValidationActiveDirectoryUsernamePatternMatch',NULL,'The format of that username cannot be used since it is reserved for Active Directory users, please try a different username.',NULL,1,2),('failedValidationAgreement',1,15,'failedValidationAgreement',NULL,'Please indicate that you accept the Agreement.',NULL,1,2),('failedValidationAgreementTitle',1,15,'failedValidationAgreementTitle',NULL,'Please enter a title for the agreement',NULL,1,2),('failedValidationAgreementTypeRequired',1,15,'failedValidationAgreementTypeRequired',NULL,'You must specify an agreement type for this #item#.',NULL,1,2),('failedValidationAssetBoxImageSizes',1,15,'failedValidationAssetBoxImageSizes',NULL,'Please ensure that either the height or width fields (or both) contain a value. ',NULL,1,2),('failedValidationAssetsLocked',1,15,'failedValidationAssetsLocked',NULL,'One or more of the assets you are trying to update is locked. You will need to wait until other users have finished updating the assets you want to update.',NULL,1,2),('failedValidationAutoincrement',1,15,'failedValidationAutoincrement',NULL,'One of your selected autoincrement values has been used since you started working with this asset. Please try to save the asset again. The value has been updated for the field',NULL,1,2),('failedValidationAutoincrementNumber',1,15,'failedValidationAutoincrementNumber',NULL,'One of your autoincrement values is not numeric',NULL,1,2),('failedValidationAutoincrementUniqueness',1,15,'failedValidationAutoincrementUniqueness',NULL,'The value already exists for the unique Autoincrement field',NULL,1,2),('failedValidationBrandNotGiven',1,15,'failedValidationBrandNotGiven',NULL,'Please select the group that you would like to register for.',NULL,1,2),('failedValidationBrandTemplate',1,15,'failedValidationBrandTemplate',NULL,'This asset cannot be used as a brand template because it does not contain an Acrobat PDF Form.',NULL,1,2),('failedValidationCannotExcludeAndInclude',1,15,'failedValidationCannotExcludeAndInclude',NULL,'You cannot add file formats to both the included and excluded list - once the included list has been populated, all other formats are implicitly excluded.',NULL,1,2),('failedValidationCannotGeneratePassword',1,15,'failedValidationCannotGeneratePassword',NULL,'You must specify a password if the user is not to be notified via email.',NULL,1,2),('failedValidationCaptcha',1,15,'failedValidationCaptcha',NULL,'The characters you entered do not match those shown in the image',NULL,1,2),('failedValidationCategories',1,15,'failedValidationCategories',NULL,'You must put the asset in at least one #category-node#.',NULL,1,2),('failedValidationConditions',1,15,'failedValidationConditions',NULL,'Please indicate that you accept the Conditions of Use.',NULL,1,2),('failedValidationDateFormat',1,15,'failedValidationDateFormat',NULL,'Please ensure dates are provided in the correct format.',NULL,1,2),('failedValidationDateOrder',1,15,'failedValidationDateOrder',NULL,'You need to make sure the start date is before the end date.',NULL,1,2),('failedValidationDefRelCatIdNAN',1,15,'failedValidationDefRelCatIdNAN',NULL,'One of the default relationship category ids was not a number',NULL,1,2),('failedValidationDefRelCatInvalid',1,15,'failedValidationDefRelCatInvalid',NULL,'One of the default relationship categories was not valid - please check against your categories',NULL,1,2),('failedValidationDuplicateUsername',1,15,'failedValidationDuplicateUsername',NULL,'The selected username is already in use - please try a different one, or contact us for a password reminder.',NULL,1,2),('failedValidationEmailAddress',1,15,'failedValidationEmailAddress',NULL,'Please provide a valid email address.',NULL,1,2),('failedValidationEmailNotValidForUser',1,15,'failedValidationEmailNotValidForUser',NULL,'The email address supplied for verification does not belong to a registered user.',NULL,1,2),('failedValidationEmailRuleDaysBefore',1,15,'failedValidationEmailRuleDaysBefore',NULL,'The \'Number of days\' field must be a number zero or greater.',NULL,1,2),('failedValidationEmptyClause',1,15,'failedValidationEmptyClause',NULL,'Please enter a value for all clauses.',NULL,1,2),('failedValidationExistingPassword',1,15,'failedValidationExistingPassword',NULL,'Please provide the existing password as a security check.',NULL,1,2),('failedValidationFile',1,15,'failedValidationFile',NULL,'You need to select a valid, non-empty file to upload.',NULL,1,2),('failedValidationFileExtension',1,15,'failedValidationFileExtension',NULL,'Files added to the Image Bank must have a file extension.',NULL,1,2),('failedValidationFileFormatsRequireMediaTypes',1,15,'failedValidationFileFormatsRequireMediaTypes',NULL,'File formats can only be specified when at least one media format is selected.',NULL,1,2),('failedValidationFileRequired',1,15,'failedValidationFileRequired',NULL,'You must specify a file for this type of #item#.',NULL,1,2),('failedValidationFileTypeChange',1,15,'failedValidationFileTypeChange',NULL,'You cannot change the file type of this asset.',NULL,1,2),('failedValidationForAddress',1,15,'failedValidationForAddress',NULL,'Please enter your address.',NULL,1,2),('failedValidationForCDCustomField',1,15,'failedValidationForCDCustomField',NULL,'Please enter your telephone number.',NULL,1,2),('failedValidationForename',1,15,'failedValidationForename',NULL,'Please provide a valid forename.',NULL,1,2),('failedValidationForField',1,15,'failedValidationForField',NULL,'Please enter a value for the field:',NULL,1,2),('failedValidationForName',1,15,'failedValidationForName',NULL,'Please enter your name.',NULL,1,2),('failedValidationForTitle',1,15,'failedValidationForTitle',NULL,'Please enter a value for the field: Title',NULL,1,2),('failedValidationFractionDigits',1,15,'failedValidationFractionDigits',NULL,'Field \'%\' can accept numbers with up to % decimal places.',NULL,1,2),('failedValidationFulfillerMustBeUploader',1,15,'failedValidationFulfillerMustBeUploader',NULL,'You can only mark a user as a request fulfiller if they have the ability to upload #items#. Please mark the user as an admin user or put them into a group that has upload rights.',NULL,1,2),('failedValidationGroupDiscount',1,15,'failedValidationGroupDiscount',NULL,'You need to specify the discount as a valid whole number.',NULL,1,2),('failedValidationGroupDiscountPercent',1,15,'failedValidationGroupDiscountPercent',NULL,'You need to specify the discount as a valid percentage between 0 and 100.',NULL,1,2),('failedValidationGroupName',1,15,'failedValidationGroupName',NULL,'You need to provide a name for the group.',NULL,1,2),('failedValidationGroupNotGiven',1,15,'failedValidationGroupNotGiven',NULL,'Please select the group that you would like to register for.',NULL,1,2),('failedValidationGroupSize',1,15,'failedValidationGroupSize',NULL,'You have exceeded the maximum download dimensions for the groups you are in',NULL,1,2),('failedValidationHexColour',1,15,'failedValidationHexColour',NULL,'[0] cannot be parsed as a hex colour, please enter a colour in the format #rrggbb, for example #ffffff for white.',NULL,1,2),('failedValidationInDesignERAError',1,15,'failedValidationInDesignERAError',NULL,'Unable to create empty related assets: There was an error during the validation of your selected templates',NULL,1,2),('failedValidationInDesignERATemplate',1,15,'failedValidationInDesignERATemplate',NULL,'Unable to create empty related assets: One or more of your asset types does not have a valid template selected',NULL,1,2),('failedValidationInDesignResolveConflicts',1,15,'failedValidationInDesignResolveConflicts',NULL,'Please select which #item# you want to use for each duplicate.',NULL,1,2),('failedValidationInDesignTemplate',1,15,'failedValidationInDesignTemplate',NULL,'Please select a template.',NULL,1,2),('failedValidationInsufficientOrgUnitQuota',1,15,'failedValidationInsufficientOrgUnitQuota',NULL,'You cannot save this file because the Org Unit &#39;[0]&#39; would exceed its file storage quota - please contact an administrator to resolve this issue.',NULL,1,2),('failedValidationInsufficientQuota',1,15,'failedValidationInsufficientQuota',NULL,'You cannot upload this file because your Org Unit(s) would exceed their file storage quotas - please contact an administrator to resolve this issue.',NULL,1,2),('failedValidationInvalidFormatForCms',1,15,'failedValidationInvalidFormatForCms',NULL,'The selected file is not of a format suitable for use in the CMS',NULL,1,2),('failedValidationInvalidFromRelationship',1,15,'failedValidationInvalidFromRelationship',NULL,'The #item# with id &#39;[0]&#39; is missing or of the wrong type to be added as a &#39;[1]&#39;.',NULL,1,2),('failedValidationInvalidIdList',1,15,'failedValidationInvalidIdList',NULL,'Please ensure that where a list of ids is provided, it is formatted as a list of numbers separated by commas.',NULL,1,2),('failedValidationInvalidJpegQuality',1,15,'failedValidationInvalidJpegQuality',NULL,'The JPEG Quality must be a number between 1 and 100',NULL,1,2),('failedValidationInvalidToRelationship',1,15,'failedValidationInvalidToRelationship',NULL,'The #item# with id &#39;[0]&#39; is not allowed to participate in relationship &#39;[1]&#39;.',NULL,1,2),('failedValidationLatitude',1,15,'failedValidationLatitude',NULL,'Field \'%\' is not in the valid range for Latitude (-90.0 to 90.0).',NULL,1,2),('failedValidationListValue',1,15,'failedValidationListValue',NULL,'The list value cannot be blank.',NULL,1,2),('failedValidationLongitude',1,15,'failedValidationLongitude',NULL,'Field \'%\' is not in the valid range for Longitude (-180.0 to 180.0).',NULL,1,2),('failedValidationMarketingGroups',1,15,'failedValidationMarketingGroups',NULL,'You need to select a marketing group.',NULL,1,2),('failedValidationMaxRepurposedImageSize',1,15,'failedValidationMaxRepurposedImageSize',NULL,'The maximum height or width of an embeddable image cannot exceed ',NULL,1,2),('failedValidationMustAddGroup',1,15,'failedValidationMustAddGroup',NULL,'You must assign the user to an org unit group so that you can manage them.',NULL,1,2),('failedValidationNegativeSpatialArea',1,15,'failedValidationNegativeSpatialArea',NULL,'Attribute \'%\' represents a spatial area of negative size. Please check the bounds so that west is less than east, and south less than north.',NULL,1,2),('failedValidationNoActionSelectedOnAccessLevel',1,15,'failedValidationNoActionSelectedOnAccessLevel',NULL,'Please note that no action will be taken on #access-level-root# unless you change the corresponding drop down to something other than \"#snippet-skip#\".',NULL,1,2),('failedValidationNoActionSelectedOnCategory',1,15,'failedValidationNoActionSelectedOnCategory',NULL,'Please note that no action will be taken on #category-root# unless you change the corresponding drop down to something other than \"#snippet-skip#\".',NULL,1,2),('failedValidationNoApprovalUsers',1,15,'failedValidationNoApprovalUsers',NULL,'You need to select at least one approval user for this #batch-release#',NULL,1,2),('failedValidationNoAssetActionSelected',1,15,'failedValidationNoAssetActionSelected',NULL,'You must select an action to apply from the drop down list',NULL,1,2),('failedValidationNoAssetsFound',1,15,'failedValidationNoAssetsFound',NULL,'That search did not find any assets that you can update. Please search again.',NULL,1,2),('failedValidationNoAssetsSelected',1,15,'failedValidationNoAssetsSelected',NULL,'Please tick one or more of the assets you would like to include in a bulk update, or click cancel to return to the batch update home screen.',NULL,1,2),('failedValidationNoAssetsSelectedLightbox',1,15,'failedValidationNoAssetsSelected',NULL,'You must select one or more #items# to perform an action on',NULL,1,2),('failedValidationNoAttributes',1,15,'failedValidationNoAttributes',NULL,'Please select one or more attributes that you would like to update, or click cancel to return to the batch update home screen.',NULL,1,2),('failedValidationNoFilesToAdd',1,15,'failedValidationNoFilesToAdd',NULL,'There are no files to add - please upload some files to the server.',NULL,1,2),('failedValidationNoLayerSupport',1,15,'failedValidationNoLayerSupport',NULL,'The output file format doesn&#39;t support multiple layers - please choose a single layer.',NULL,1,2),('failedValidationNoName',1,15,'failedValidationNoName',NULL,'Please enter a name.',NULL,1,2),('failedValidationNoTextToFind',1,15,'failedValidationNoTextToFind',NULL,'Please enter the text to find, or click cancel to return to the batch update home screen.',NULL,1,2),('failedValidationNotWholeNumber',1,15,'failedValidationNotWholeNumber',NULL,'Field \'%\' can only accept whole numbers.',NULL,1,2),('failedValidationNoType',1,15,'failedValidationNoType',NULL,'This type of media is not supported in the current system configuration.',NULL,1,2),('failedValidationNoUserDetailsSupplied',1,15,'failedValidationNoUserDetailsSupplied',NULL,'You must supply the name and email address of an existing user to verify your registration.',NULL,1,2),('failedValidationNumberFormat',1,15,'failedValidationNumberFormat',NULL,'Please ensure that numeric values are provided where appropriate.',NULL,1,2),('failedValidationNumberFormatField',1,15,'failedValidationNumberFormatField',NULL,'Field \'%\' can only accept numeric values.',NULL,1,2),('failedValidationNumPlaceholders',1,15,'failedValidationNumPlaceholders',NULL,'Please enter a valid number for Number to Add.',NULL,1,2),('failedValidationOneAssetSelectedLightbox',1,15,'failedValidationOneAssetSelectedLightbox',NULL,'You must select two or more images to compare',NULL,1,2),('failedValidationPassword',1,15,'failedValidationPassword',NULL,'You need to provide a password.',NULL,1,2),('failedValidationPasswords',1,15,'failedValidationPasswords',NULL,'The password cannot be empty - please enter a value in all fields.',NULL,1,2),('failedValidationPasswordsDontMatch',1,15,'failedValidationPasswordsDontMatch',NULL,'The two new passwords you have entered do not match.',NULL,1,2),('failedValidationPointDistance',1,15,'failedValidationPointDistance',NULL,'For point and radius search, please enter a valid point coordinate and a positive radius value.',NULL,1,2),('failedValidationPrice',1,15,'failedValidationPrice',NULL,'Please enter a valid value for price.',NULL,1,2),('failedValidationRecipients',1,15,'failedValidationRecipients',NULL,'Please enter one or more recipients for this email, separated by semi-colons.',NULL,1,2),('failedValidationReportType',1,15,'failedValidationReportType',NULL,'You need to select a report type.',NULL,1,2),('failedValidationResetPasswordAuthId',1,15,'failedValidationResetPasswordAuthId',NULL,'The provided link is not valid.',NULL,1,2),('failedValidationSearchCriteria',1,15,'failedValidationSearchCriteria',NULL,'Please specify at least one search criteria.',NULL,1,2),('failedValidationSecurePassword',1,15,'failedValidationSecurePassword',NULL,'The password must contain at least one number, one capital letter and be a minimum of 8 characters long.',NULL,1,2),('failedValidationSecurityAnswers',1,15,'failedValidationSecurityAnswers',NULL,'The Security Answers cannot be empty - please enter a value in all fields.',NULL,1,2),('failedValidationSecurityQuestions',1,15,'failedValidationSecurityQuestions',NULL,'Please select two Security Questions.',NULL,1,2),('failedValidationSensitivityNotesRequired',1,15,'failedValidationSensitivityNotesRequired',NULL,'Please enter a reason for this item being sensitive.',NULL,1,2),('failedValidationSizesPercentages',1,15,'failedValidationSizesPercentages',NULL,'The height or width provided are not valid percentages',NULL,1,2),('failedValidationSizesPositive',1,15,'failedValidationSizesPositive',NULL,'The height and width should be greater than zero',NULL,1,2),('failedValidationSizesPositiveNumber',1,15,'failedValidationSizesPositiveNumber',NULL,'You need to provide a valid positive number for the height and width of the images',NULL,1,2),('failedValidationSSSizes',1,15,'failedValidationSSSizes',NULL,'Please check that the slideshow height and width are numeric',NULL,1,2),('failedValidationSubjectBody',1,15,'failedValidationSubjectBody',NULL,'If you enter either a subject or body, you must enter both.',NULL,1,2),('failedValidationSurname',1,15,'failedValidationSurname',NULL,'Please provide a valid surname.',NULL,1,2),('failedValidationTALength',1,15,'failedValidationTALength',NULL,'The text area fields have a maximum length of [0]. Please adjust the content you are trying to save.',NULL,1,2),('failedValidationTemplateExists',1,15,'failedValidationTemplateExists',NULL,'A template with that identifier already exists.',NULL,1,2),('failedValidationUsage',1,15,'failedValidationUsage',NULL,'Please select your intended usage from the list.',NULL,1,2),('failedValidationUsageType',1,15,'failedValidationUsageType',NULL,'The Usage Type value cannot be blank.',NULL,1,2),('failedValidationUsageTypeFormat',1,15,'failedValidationUsageTypeFormat',NULL,'Please make sure you have provided a description and that the width and height sizes are numbers (greater than zero).',NULL,1,2),('failedValidationUse',1,15,'failedValidationUse',NULL,'Please enter your intended use in the box provided.',NULL,1,2),('failedValidationUsernameExists',1,15,'failedValidationUsernameExists',NULL,'The selected username is already in use. Please select another.',NULL,1,2),('failedValidationValid',1,15,'failedValidationValid',NULL,'Please enter a valid value for the field:',NULL,1,2),('failedValidationValidFields',1,15,'failedValidationValidFields',NULL,'Please enter valid values for the fields on the form',NULL,1,2),('failedValidationWrongType',1,15,'failedValidationWrongType',NULL,'This #item# type ([0]) cannot accept this type of media.',NULL,1,2),('failedValidationZipDir',1,15,'failedValidationZipDir',NULL,'You must select a zip file OR a directory.',NULL,1,2),('feedbackComments',1,15,'feedbackComments',NULL,'You need to provide comments for your feedback entry.',NULL,1,2),('file',1,8,'Term for file',NULL,'file',NULL,1,2),('fileError',1,15,'fileError',NULL,'There was a problem processing the file you provided. It is possible that the file is corrupt or has the wrong file extension.',NULL,1,2),('fileErrorPreview',1,15,'fileErrorPreview',NULL,'It is not possible to preview this file format in a web browser. To view the converted image you must first download it to your computer.',NULL,1,2),('filter-search-text',1,1,'Filter search text',NULL,'<p>The filter <b>?filterName?</b> has been applied to the form below. Select from the list box underneath the menu or edit the form below to change the filtering.</p>',NULL,1,1),('finishedImport',1,15,'finishedImport',NULL,'Finished import',NULL,1,2),('footer-copy',1,1,'Footer copy',NULL,'<p><span>&copy; #snippet-current-year# <a href=\"http://www.bright-interactive.com\" target=\"_blank\">Bright Interactive</a>. <a href=\"../action/viewConditions\">Terms &amp; Conditions</a> | <a href=\"../action/viewPrivacy\">Privacy Policy</a> | <a href=\"../action/viewAbout\">About Asset Bank</a></span></p>',NULL,1,1),('format-help',1,1,'Image Format Help','Text for a pop-up that can be shown on the download page to explain the different format options.','<h2>Image Format Help</h2><p>Sed tempor massa vitae risus. Nam pellentesque lorem ut dui. Etiam ut velit. Donec tincidunt ligula eu odio. Praesent tempus odio sit amet velit. Duis lacus. Nulla dictum. Fusce volutpat, turpis sed mollis vestibulum, massa leo sodales turpis, id volutpat lacus est ut est. In posuere nulla vitae nulla. Vestibulum molestie ornare turpis.</p>',NULL,1,1),('fulfillerUserMandatory',1,15,'fulfillerUserMandatory',NULL,'You need to select a Fulfiller user.',NULL,1,2),('GroupNoDownloadsLeft',1,15,'GroupNoDownloadsLeft',NULL,'Sorry, you don\'t have any downloads left for today. Please try tomorrow.',NULL,1,2),('heading-about',1,10,'Heading - About',NULL,'About',NULL,1,2),('heading-account-reactivated',1,10,'Heading - Account reactivated',NULL,'Account reactivated',NULL,1,2),('heading-acknowledged-user-messages',1,10,'Heading - Acknowledged User Messages',NULL,'Recently Acknowledged User Messages',NULL,1,2),('heading-add-asset',1,10,'Heading - Add Asset',NULL,'Add #item#',NULL,1,2),('heading-add-asset-to-batch-release',1,10,'Heading - Add Asset to Batch Release',NULL,'Add Asset to #batch-release#',NULL,1,2),('heading-add-copy-to-batch-release',1,10,'Heading - Add Copy to Batch Release',NULL,'Add Copy to #batch-release#',NULL,1,2),('heading-add-empty-related-assets',1,10,'Heading - Add empty related assets',NULL,'Add empty related assets',NULL,1,2),('heading-add-placeholders',1,10,'Heading - Add Empty Assets',NULL,'Add Assets Without Files',NULL,1,2),('heading-admin-help',1,10,'Heading - Admin help',NULL,'Online Admin Help',NULL,1,2),('heading-agreements',1,10,'Heading - Agreements',NULL,'Agreements',NULL,1,2),('heading-all-cats',1,10,'Heading - All Categories',NULL,'All Categories',NULL,1,2),('heading-all-requests',1,10,'Heading - Pending requests',NULL,'Pending',NULL,1,2),('heading-approval-notes',1,10,'Heading - Approval Notes',NULL,'Approval Notes',NULL,1,2),('heading-approve-items',1,10,'Heading - Approve Items',NULL,'Approve Items',NULL,1,2),('heading-asset-change-descriptions',1,10,'Heading - Asset Change Descriptions',NULL,'#item# Change Descriptions',NULL,1,2),('heading-asset-details',1,10,'Heading - Asset Details',NULL,'Asset Details',NULL,1,2),('heading-asset-versions',1,10,'Heading - Asset Versions',NULL,'#item# versions',NULL,1,2),('heading-assign-a-fulfiller',1,10,'Heading - Assign a fulfiller',NULL,'Assign a Fulfiller',NULL,1,2),('heading-audio-conversion-status',1,10,'Heading - Audio Conversion Status',NULL,'Audio Conversion Status',NULL,1,2),('heading-audio-details',1,10,'Heading - Audio Details',NULL,'Audio Details',NULL,1,2),('heading-batch-approval-update',1,10,'Heading - Batch Approval Update',NULL,'Batch Approval Update for ?workflowName?',NULL,1,2),('heading-batch-release-search',1,10,'Heading - Batch Release Search',NULL,'#batch-release# Search',NULL,1,2),('heading-batch-update',1,10,'Heading - Batch Update',NULL,'Batch Update',NULL,1,2),('heading-batch-update-finished',1,10,'Heading - Batch Update Finished',NULL,'Batch Update Finished',NULL,1,2),('heading-br-outstanding-acknowledgements-report',1,10,'Heading - Batch Release Outstanding Acknowledgements Report',NULL,'batch-release# Outstanding Acknowledgements Report',NULL,1,2),('heading-brand-template-details',1,10,'Heading - Brand Template Details',NULL,'Brand Template Details',NULL,1,2),('heading-browse',1,10,'Heading - Browse',NULL,'Browse',NULL,1,2),('heading-browse-keywords',1,10,'Heading - Browse Keywords',NULL,'Browse Keywords',NULL,1,2),('heading-bulk-delete-confirm',1,10,'Heading - Bulk delete confirm',NULL,'Confirm bulk delete',NULL,1,2),('heading-bulk-find-replace',1,10,'Heading - Bulk Find and Replace',NULL,'Bulk Find and Replace',NULL,1,2),('heading-bulk-update',1,10,'Heading - Bulk Update',NULL,'Bulk Update',NULL,1,2),('heading-bulk-update-batch',1,10,'Heading - Bulk Update: Batch Selection',NULL,'Bulk Update: Batch Selection',NULL,1,2),('heading-bulk-update-run-find-replace',1,10,'Heading - Bulk Update: Bulk Update: Run a Find and Replace',NULL,'Bulk Update: Run a Find and Replace',NULL,1,2),('heading-bulk-update-run-update',1,10,'Heading - Bulk Update: Bulk Update: Run an Update',NULL,'Bulk Update: Run an Update',NULL,1,2),('heading-bulk-update-search',1,10,'Heading - Bulk Update: Create Batch Using Search',NULL,'Bulk Update: Create Batch Using Search',NULL,1,2),('heading-bulk-update-status',1,10,'Heading - Bulk Update: Status',NULL,'Bulk Update: Status',NULL,1,2),('heading-bulk-upload',1,10,'Heading - Bulk Upload',NULL,'Bulk Upload',NULL,1,2),('heading-checked-out-items',1,10,'Heading - Checked Out Items',NULL,'Checked Out #Items#',NULL,1,2),('heading-choose-bg-color',1,10,'Heading - Choose background colour',NULL,'Choose mask background colour (optional) ',NULL,1,2),('heading-choose-mask',1,10,'Heading - Choose a mask ',NULL,'Choose a mask ',NULL,1,2),('heading-choose-type',1,10,'Heading - Choose Type',NULL,'Choose Type',NULL,1,2),('heading-compare-images',1,10,'Heading - Compare images',NULL,'Compare Images',NULL,1,2),('heading-compose-invite',1,10,'Heading - Compose invite',NULL,'Compose invite',NULL,1,2),('heading-contact',1,10,'Heading - Contact Us',NULL,'Contact Us',NULL,1,2),('heading-contact-sheet',1,10,'Heading - Contact Sheet',NULL,'Contact Sheet',NULL,1,2),('heading-copy-asset-step1',1,10,'Heading - Copy asset step 1',NULL,'Copy asset : Step 1 Child Relationships',NULL,1,2),('heading-copy-move',1,10,'Heading - Copy or Move Assets',NULL,'Copy or Move #items#',NULL,1,2),('heading-create-copy',1,10,'Heading - Create Copy',NULL,'Create Copy',NULL,1,2),('heading-create-doc-from-brand-template',1,10,'Heading - Create Document from Template',NULL,'Create Document from Template',NULL,1,2),('heading-create-new-password',1,10,'Heading - Create New Password',NULL,'Create New Password',NULL,1,2),('heading-current-batch-release',1,10,'Heading - Current Batch Release',NULL,'Current #batch-release#',NULL,1,2),('heading-default-related-assets',1,10,'Heading - Default related assets',NULL,'Edit default relationships',NULL,1,2),('heading-define-security-questions',1,10,'Heading - Define Your Security Questions',NULL,'Define Your Security Questions',NULL,1,2),('heading-delete-asset',1,10,'Heading - Delete Asset',NULL,'Delete Asset',NULL,1,2),('heading-download',1,10,'Heading - Download',NULL,'Download',NULL,1,2),('heading-download-audio',1,10,'Heading - Download Audio',NULL,'Download Audio',NULL,1,2),('heading-download-child-assets',1,10,'Heading - download child assets',NULL,'Download child assets',NULL,1,2),('heading-download-failure',1,10,'Heading - Download Failure',NULL,'Download Failure',NULL,1,2),('heading-download-file',1,10,'Heading - Download File',NULL,'Download File',NULL,1,2),('heading-download-image',1,10,'Heading - Download Image',NULL,'Download Image',NULL,1,2),('heading-download-image-preview',1,10,'Heading - Download Image Preview',NULL,'Download Image Preview',NULL,1,2),('heading-download-video',1,10,'Heading - Download Video',NULL,'Download Video',NULL,1,2),('heading-edit-metadata',1,10,'Heading - Edit metadata',NULL,'Edit metadata',NULL,1,2),('heading-edit-relationship',1,10,'Heading - Edit Relationship',NULL,'Edit Relationship',NULL,1,2),('heading-edit-relationships',1,10,'Heading - Edit Relationships',NULL,'Edit Relationships',NULL,1,2),('heading-email-failed',1,10,'Heading - Email Failed',NULL,'Failed to Send Email',NULL,1,2),('heading-email-in-progress',1,10,'Heading - Email In Progress',NULL,'Sending Email',NULL,1,2),('heading-email-item',1,10,'Heading - Email Item',NULL,'Email Item',NULL,1,2),('heading-email-sent',1,10,'Heading - Email Sent',NULL,'Email Sent',NULL,1,2),('heading-email-this-page',1,10,'Heading - Email This Page',NULL,'Email This Page',NULL,1,2),('heading-embed-file',1,10,'Heading - Embed file',NULL,'Embed file',NULL,1,2),('heading-featured-searches',1,10,'Heading - Featured Searches',NULL,'Featured Searches',NULL,1,2),('heading-file-transfer-step-1',1,10,'Heading - File Transfer Step 1',NULL,'File Transfer Step 1: Upload File',NULL,1,2),('heading-file-transfer-step-2',1,10,'Heading - File Transfer Step 2',NULL,'File Transfer Step 2: Choose Recipients',NULL,1,2),('heading-filter',1,10,'Heading - Filter',NULL,'Filter',NULL,1,2),('heading-filters',1,10,'Heading - Filters',NULL,'Filters',NULL,1,2),('heading-forgotten-password',1,10,'Heading - Forgotten Password',NULL,'Forgotten Password',NULL,1,2),('heading-fulfilled-requests',1,10,'Heading - Fulfilled requests',NULL,'Fulfilled',NULL,1,2),('heading-help',1,10,'Heading - Help',NULL,'Help',NULL,1,2),('heading-image-details',1,10,'Heading - Image Details',NULL,'Image Details',NULL,1,2),('heading-indesign-linked-assets',1,10,'heading-indesign-linked-assets',NULL,'Assets that ?linkedAssetName? links to',NULL,1,2),('heading-indesign-linking-assets',1,10,'heading-indesign-linking-assets',NULL,'InDesign assets with links to ?linkedAssetName?',NULL,1,2),('heading-initialise-workflow',1,10,'Heading - Initialise Workflow',NULL,'Initialise Workflow',NULL,1,2),('heading-invite-users',1,10,'Heading - Invite users',NULL,'Invite Users',NULL,1,2),('heading-keyword-chooser',1,10,'Heading - Keyword Chooser',NULL,'Keyword Chooser',NULL,1,2),('heading-keywords',1,10,'Heading - Keywords',NULL,'Keywords',NULL,1,2),('heading-last-batch',1,10,'Heading - last in current batch',NULL,'last in current batch',NULL,1,2),('heading-least-downloaded',1,10,'Heading - Least Downloaded',NULL,'Least Downloaded #items#',NULL,1,2),('heading-least-viewed',1,10,'Heading - Least Viewed',NULL,'Least Viewed #items#',NULL,1,2),('heading-left-batch',1,10,'Heading - left in batch',NULL,'left in batch',NULL,1,2),('heading-make-templated',1,10,'Heading - Make Templated',NULL,'Make Templated',NULL,1,2),('heading-manage-batch-releases',1,10,'Heading - Manage Batch Releases',NULL,'Manage #batch-releases#',NULL,1,2),('heading-mask-configuration',1,10,'Heading - Mask configuration',NULL,'Mask configuration',NULL,1,2),('heading-matching-users',1,10,'Heading - Matching users',NULL,'Matching Users',NULL,1,2),('heading-most-downloaded',1,10,'Heading - Most Downloaded',NULL,'Most Downloaded #items#',NULL,1,2),('heading-most-viewed',1,10,'Heading - Most Viewed',NULL,'Most Viewed #items#',NULL,1,2),('heading-move-keyword',1,10,'Heading - Move Keyword',NULL,'Move Keyword',NULL,1,2),('heading-my-messages',1,10,'Heading - My Messages',NULL,'My Messages',NULL,1,2),('heading-my-requests',1,10,'Heading - My requests',NULL,'My Requests',NULL,1,2),('heading-my-searches',1,10,'Heading - My saved searches',NULL,'My saved searches',NULL,1,2),('heading-my-uploads',1,10,'Heading - My Uploads',NULL,'My Uploads/Edits',NULL,1,2),('heading-new-user-messages',1,10,'Heading - New User Messages',NULL,'New User Messages',NULL,1,2),('heading-news',1,10,'Heading - News',NULL,'News &amp; Announcements',NULL,1,2),('heading-not-found',1,10,'Heading - Asset Not Found',NULL,'Asset Not Found',NULL,1,2),('heading-online-help',1,10,'Heading - Online help',NULL,'Online Help',NULL,1,2),('heading-password-changed',1,10,'Heading - Password Changed',NULL,'Password Changed',NULL,1,2),('heading-password-reset',1,10,'Heading - Password Reset',NULL,'Password Reset',NULL,1,2),('heading-password-updated',1,10,'Heading - Password Updated',NULL,'Your password has been updated, please login again.',NULL,1,2),('heading-position-mask',1,10,'Heading - Position your mask ',NULL,'Position and resize your mask',NULL,1,2),('heading-preparing',1,10,'Heading - Preparing',NULL,'Preparing',NULL,1,2),('heading-print-image',1,10,'Heading - Print Image',NULL,'Print Image',NULL,1,2),('heading-profile-changed',1,10,'Heading - Profile Changed',NULL,'Profile Changed',NULL,1,2),('heading-profile-updated',1,10,'Heading - Profile Updated',NULL,'Your profile has been updated, please login again.',NULL,1,2),('heading-promoted',1,10,'Heading - Promoted Items',NULL,'Promoted Items',NULL,1,2),('heading-purchase-notes',1,10,'Heading - Purchase Notes',NULL,'Purchase Notes',NULL,1,2),('heading-recent',1,10,'Heading - Recent Items',NULL,'Recent Items',NULL,1,2),('heading-recent-searches',1,10,'Heading - Recent Searches',NULL,'Recent Searches',NULL,1,2),('heading-recently-released-batch-releases',1,10,'Heading - Recently released batch releases',NULL,'Recently released #batch-releases#',NULL,1,2),('heading-recommended-searches',1,10,'Heading - Recommended searches',NULL,'Recommended searches',NULL,1,2),('heading-register',1,10,'Heading - Register',NULL,'Register',NULL,1,2),('heading-rejected-requests',1,10,'Heading - Rejected requests',NULL,'Rejected',NULL,1,2),('heading-related-items',1,10,'Heading - Related Items',NULL,'Related #items#',NULL,1,2),('heading-repurpose-slideshow',1,10,'Heading - Create Embeddable Slideshow',NULL,'Create Embeddable Slideshow',NULL,1,2),('heading-repurposed-audio-clip',1,10,'Heading - Embeddable Audio Clip',NULL,'Embeddable Audio Clip',NULL,1,2),('heading-repurposed-audio-clips',1,10,'Heading - Embeddable  Audio Clips',NULL,'Embeddable Audio Clips',NULL,1,2),('heading-repurposed-image',1,10,'Heading - Embeddable Image',NULL,'Embeddable Image',NULL,1,2),('heading-repurposed-images',1,10,'Heading - Embeddable Image Versions',NULL,'Embeddable Image Versions',NULL,1,2),('heading-repurposed-slideshow',1,10,'Heading - Embeddable Slideshow',NULL,'Embeddable Slideshow',NULL,1,2),('heading-repurposed-slideshows',1,10,'Heading - Embeddable Slideshows',NULL,'Embeddable Slideshows',NULL,1,2),('heading-repurposed-video',1,10,'Heading - Embeddable Video',NULL,'Embeddable Video',NULL,1,2),('heading-repurposed-videos',1,10,'Heading - Embeddable Video Versions',NULL,'Embeddable Video Versions',NULL,1,2),('heading-request-approval',1,10,'Heading - Request Approval',NULL,'Request Approval',NULL,1,2),('heading-request-cd',1,10,'Heading - Request Images on CD',NULL,'Request Images on CD',NULL,1,2),('heading-request-details',1,10,'Heading - Request details',NULL,'Request Details',NULL,1,2),('heading-request-not-found',1,10,'Heading - Request Not Found',NULL,'Request Not Found',NULL,1,2),('heading-requests',1,10,'Heading - Requests',NULL,'Requests',NULL,1,2),('heading-save-search',1,10,'Heading - Save Search',NULL,'Save Search',NULL,1,2),('heading-saved-searches',1,10,'Heading - Saved Searches',NULL,'Saved Searches',NULL,1,2),('heading-search',1,10,'Heading - Advanced Search',NULL,'Advanced Search',NULL,1,2),('heading-search-results',1,10,'Heading - Search Results',NULL,'Search Results',NULL,1,2),('heading-search-section',1,10,'Heading - Search section',NULL,'Search',NULL,1,2),('heading-security-questions',1,10,'Heading - Security Questions',NULL,'Security Questions',NULL,1,2),('heading-security-questions-changed',1,10,'Heading - Security Questions',NULL,'Security Questions Changed',NULL,1,2),('heading-select-crop-area',1,10,'Heading - Select crop area',NULL,'Select crop area',NULL,1,2),('heading-select-video-thumbnail',1,10,'Heading - Select Video Thumbnail',NULL,'Select Video Thumbnail',NULL,1,2),('heading-send-e-card',1,10,'Heading - Send e-card',NULL,'Send E-Card',NULL,1,2),('heading-slideshow',1,10,'Heading - Slideshow',NULL,'Slideshow',NULL,1,2),('heading-start-import',1,10,'Heading - Start Import',NULL,'Start Import',NULL,1,2),('heading-step-1-upload',1,10,'Heading - Step 1: Upload File',NULL,'Step 1: Upload File',NULL,1,2),('heading-step-2-metadata',1,10,'Heading - Step 2: Add Metadata',NULL,'Step 2: Add Metadata',NULL,1,2),('heading-step-2-thumbnail',1,10,'Heading - Step 2: Select preview thumbnail',NULL,'Step 2: Select preview thumbnail',NULL,1,2),('heading-step-3-metadata',1,10,'Heading - Step 3: Add Metadata',NULL,'Step 3: Add Metadata',NULL,1,2),('heading-submit-asset-feedback',1,10,'Heading - Submit Feedback',NULL,'Submit Feedback',NULL,1,2),('heading-submit-feedback',1,10,'Heading - Submit Feedback',NULL,'Submit ?type?',NULL,1,2),('heading-submitted-items',1,10,'Heading - Submitted Items',NULL,'Submitted Items',NULL,1,2),('heading-unreleased-batch-releases',1,10,'Heading - Unreleased batch releases',NULL,'Unreleased #batch-releases#',NULL,1,2),('heading-unsubmitted-items',1,10,'Heading - Unsubmitted Items',NULL,'Unsubmitted Items',NULL,1,2),('heading-update',1,10,'Heading - Update',NULL,'Update',NULL,1,2),('heading-update-assets',1,10,'Heading - Update Assets',NULL,'Update #items#',NULL,1,2),('heading-update-profile',1,10,'Heading - Update Profile',NULL,'Update Your Profile',NULL,1,2),('heading-update-success',1,10,'Heading - Upload/Update Success',NULL,'Upload/Update Success',NULL,1,2),('heading-upload',1,10,'Heading - Upload',NULL,'Upload',NULL,1,2),('heading-upload-log',1,10,'Heading - Upload Log',NULL,'Upload Log:',NULL,1,2),('heading-upload-success',1,10,'Heading - Upload Success',NULL,'Upload Success',NULL,1,2),('heading-user-approval',1,10,'Heading - User approval',NULL,'User approval',NULL,1,2),('heading-video-conversion-status',1,10,'Heading - Video Conversion Status',NULL,'Video Conversion Status',NULL,1,2),('heading-video-details',1,10,'Heading - Video Details',NULL,'Video Details',NULL,1,2),('heading-view-agreement',1,10,'Heading - View agreement',NULL,'View agreement',NULL,1,2),('heading-your-password',1,10,'Heading - Your Password',NULL,'Your Password',NULL,1,2),('heading-your-profile',1,10,'Heading - Your Profile',NULL,'Your Profile',NULL,1,2),('homepage-bottom',1,1,'Home Page Text Bottom','Text at bottom of home page.','',NULL,1,1),('homepage-main',1,1,'Home Page Welcome text','The welcome text shown on the home page','<h1>Welcome to Asset Bank</h1><p>Welcome to Asset Bank - a library of images and other digital assets.&nbsp; </p><p>You can find images and documents by entering a keyword into the Quick Search, performing an advanced search or by browsing the categories.</p><p>You can change the text that appears here by logging in as an admin user and going to&nbsp;the Content section in the Admin&nbsp;menu.</p>',NULL,1,1),('homepage-message',1,1,'Home Page Message Board','The message board text on the homepage','',NULL,1,1),('homepage-no-featured-image',1,1,'Home Page featured asset placeholder','The copy shown on the home page when there is no featured asset set.','<h3>Featured asset</h3><p>Log in as an admin user and find the asset you want. Click Edit, check &#39;Featured Asset?&#39; and save.</p><p>To change this text go to the Content section in the Admin menu.',NULL,1,1),('homepage-not-logged-in',1,1,'Home Page not logged in text','The text shown on the home page for users that are not logged in','You are not currently logged in. To view the full catalogue of assets please <a href=\"viewLogin\">login</a> or <a href=\"viewRegister\">register</a>.',NULL,1,1),('homepage-view-more',1,1,'Home Page Feature Search View More','Home Page Feature Search View More','view more &#187;',NULL,1,1),('image',1,8,'Term for image',NULL,'image',NULL,1,2),('imageErrorMax',1,15,'imageErrorMax',NULL,'The maximum height or width of a downloaded image cannot exceed ',NULL,1,2),('imageErrorZero',1,15,'imageErrorZero',NULL,'The image height and width must be numeric and greater than 0',NULL,1,2),('imageFileError',1,15,'imageFileError',NULL,'There was a problem processing the file you provided. Please ensure the file is a valid, supported image file.',NULL,1,2),('importingFilesInDirectory',1,15,'importingFilesInDirectory',NULL,'Importing files in directory',NULL,1,2),('included-categories-label',1,1,'Included categories label','Label for the \"Included categories\" in the \"Categories\" section on the \"Add/Edit Asset\" page','Categories to be included:',NULL,1,1),('intro-all-cats',1,1,'Intro - All Categories',NULL,'<p>Listed below are all of the categories in the system in alphabetical order. Alternatively return to <a href=\"browseItems?categoryId=-1&categoryTypeId=1\">category tree view &raquo;</a></p>',NULL,1,1),('intro-approve-items',1,1,'Intro - Approve Items',NULL,'<p>There are ?uploadedAssets? #items# awaiting approval. You can approve #items# by <a href=\"../action/createNewBatch?restart=true&approvalStatus=3&approval=true&finishedUrl=/action/viewAssetApproval&cancelUrl=../action/viewAssetApproval\">starting a new batch update &raquo;</a></p>',NULL,1,1),('intro-approve-items-none',1,1,'Intro - Approve Items (no items)',NULL,'<p>There are no uploaded or changed #items# awaiting approval.</p>',NULL,1,1),('intro-approve-items-p2',1,1,'Intro - Approve Items Page 2',NULL,'<p>Approval requests from ?fullName?</p><p>Select Approval Status &quot;Unapproved&quot; to reject the request, or &quot;Approved&quot; to approve it. Optionally you may add a message to the user when approving or rejecting the request.</p>',NULL,1,1),('intro-batch-finished-1',1,1,'Intro - Batch Finished 1',NULL,'<p>Your search did not return any #items# that you can update.</p>',NULL,1,1),('intro-batch-finished-2',1,1,'Intro - Batch Finished 2',NULL,'<p>Some of the #items# you searched for are locked by another user - you will be able to see those #items# once the other user has finished with them.</p>',NULL,1,1),('intro-batch-finished-3',1,1,'Intro - Batch Finished 3',NULL,'<p>You have finished updating the #items# in your batch.</p>',NULL,1,1),('intro-batch-specify-items',1,1,'Intro - Batch update specify items',NULL,'<p>Specify the criteria for the #items# that will be included in the batch using the form below.</p>',NULL,1,1),('intro-batch-update',1,1,'Intro - Batch Update',NULL,'<p>The batch update feature enables you to search for #items# and then update their metadata quickly.</p><p>If another user has started a batch update then you will not see the #items# that are in their batch, to stop you from updating the same #item#.</p><p>Each batch is limited to ?maxBatchUpdateResults? #items# &#8211; once you have finished updating the #items# in your batch you can start a new one.</p>',NULL,1,1),('intro-batch-use-search',1,1,'Intro - Batch use search results',NULL,'<p>Alternatively, you can <a href=\"../action/createNewBatch?restart=true&amp;admin=1&amp;cachedCriteria=1\">use your last search results</a>.</p>',NULL,1,1),('intro-bulk-find-replace',1,1,'Intro - Bulk Find and Replace',NULL,'<p>There are <strong>?numForUpdate?</strong> #items# for update in your batch.</p>',NULL,1,1),('intro-bulk-update',1,1,'Intro - Bulk Update',NULL,'<p>The bulk update feature enables you to create a batch of #items# and then update their metadata in bulk using specified values.</p>',NULL,1,1),('intro-bulk-update-finished',1,1,'Intro - Bulk Update finished',NULL,'<p>The bulk update has finished.</p>',NULL,1,1),('intro-bulk-update-in-progress',1,1,'Intro - Bulk Update in progress',NULL,'<p>The bulk update is in progress.</p><p>This page will automatically refresh every 10 seconds until the update process finishes. If for some reason this does not happen you may <a href=\"bulkUpdateViewStatus\">update the page</a> manually to check the status.</p>',NULL,1,1),('intro-bulk-update-metadata',1,1,'Intro - Bulk Update Metadata',NULL,'<p>There are <strong>?numForUpdate?</strong> #items# for update in your batch. Please enter the metadata below for the fields that you want to update in bulk.</p>	',NULL,1,1),('intro-bulk-upload',1,1,'Intro - Bulk upload page',NULL,'<p>A bulk upload enables you to upload multiple #items# at once.</p>',NULL,1,1),('intro-bulk-upload-1',1,1,'Intro - Bulk Upload step1',NULL,'<h2>Step 1: Upload files</h2><p>To import files in bulk, you first need to transfer the files to the server. Click the Upload files button below and then browse to and select the files you want to import. When you have finished uploading files, click the next button to enter metadata. If you upload files by mistake you can <a href=\"cancelImport\" onclick=\"return confirm(\'This will delete all uploaded file. Do you want to continue?\');\">delete all uploaded files</a>.</p>',NULL,1,1),('intro-bulk-upload-1-noapp',1,1,'Intro - Bulk Upload step 1 (no applet)',NULL,'<p>If it supports it, you can <a href=\"?FTPPath?\" target=\"_blank\">use your browser to transfer the files</a> simply drag and drop the files you want to upload into the popup window.',NULL,1,1),('intro-bulk-upload-2',1,1,'Intro - Bulk Upload step 2',NULL,'<h2>Step 2: Start import</h2><p>Select the zip file or directory that contains the files, and (optionally) attributes that will be added to all of the #items#.<br/>You must select at least one category for the #items#.</p>',NULL,1,1),('intro-change-password',1,1,'Intro - Change Password',NULL,'<p>To change your password, please enter your existing password and new password in the fields provided below.</p>',NULL,1,1),('intro-change-profile',1,1,'Intro - Change Profile',NULL,'<p>Change your profile details in the form below. </p>',NULL,1,1),('intro-compare-images',1,1,'Intro - Compare images',NULL,'The images that you selected for comparison are shown below.',NULL,1,1),('intro-copy-lightbox',1,1,'Intro - Copy Lightbox',NULL,'<p>Copy #a-lightbox# <strong>?prevName?</strong></p>',NULL,1,1),('intro-create-doc-from-brand-template',1,9,'Intro - Create Document From Brand Template',NULL,'<p>Please fill in the fields below.  The text you enter will be used to fill in the template when you download it.</p>',NULL,1,2),('intro-define-security-questions',1,1,'Intro - Define Security Questions',NULL,'<p>Please choose and complete the two security questions below. These will be used if you need to reset your password should you forget it.</p>',NULL,1,1),('intro-edit-lightbox',1,1,'Intro - Edit lightbox',NULL,'<p>Edit #a-lightbox# <strong>?prevName?</strong></p>',NULL,1,1),('intro-forgot-pwd',1,1,'Intro - Forgotten Password',NULL,'<p>Please enter your details below completing at least your username or email address.</p>',NULL,1,1),('intro-forgot-pwd-isemail',1,1,'Intro - Forgotten Password username is email',NULL,'<p>Please enter your details below completing at least your username (your email address).</p>',NULL,1,1),('intro-indesign-make-templated',1,1,'Intro - InDesign - Make Templated',NULL,'<p>Make Templated will make this asset revert to using a template document by deleting the asset-specific document that is overriding the template.</p> <p>You can optionally change the asset\'s type and template at the same time as making it templated.</p>',NULL,1,1),('intro-least-downloaded',1,1,'Intro - Least Downloaded',NULL,'',NULL,1,1),('intro-least-viewed',1,1,'Intro - Least Viewed',NULL,'',NULL,1,1),('intro-manage-lightboxes',1,1,'Intro - Manage Lightboxes',NULL,'<p>Here you may add, remove, rename and reorder #lightboxes#.</p><p>Note, when you only have one #a-lightbox# it will appear as &quot;#my-lightbox#&quot;.</p>',NULL,1,1),('intro-manage-lightboxes-shared',1,1,'Intro - Manage Lightboxes (shared)',NULL,'<p>Here you may add, remove, rename and reorder #lightboxes#. You can also share your #lightboxes# with other users.</p><p>Note, when you only have one #a-lightbox# it will appear as &quot;#my-lightbox#&quot;. You may rename it if you wish any sharing users to see a different name.</p>',NULL,1,1),('intro-most-downloaded',1,1,'Intro - Most Downloaded',NULL,'',NULL,1,1),('intro-most-viewed',1,1,'Intro - Most Viewed',NULL,'',NULL,1,1),('intro-password-changed',1,1,'Intro - Password Changed',NULL,'<p>Your password has successfully been updated.</p><p>Please use the left hand menu to continue using the Image Bank.</p>',NULL,1,1),('intro-password-reset',1,1,'Intro - Password Reset',NULL,'<p>An email has been sent to the address associated with this account.<br/>Please follow the secure link within to progress.</p>',NULL,1,1),('intro-preview-image',1,1,'Intro - Preview download image',NULL,'<p>Click the download button to save the image to your computer.</p> ',NULL,1,1),('intro-print-image',1,1,'Intro - Print Image',NULL,'This page enables you to choose which attributes are printed alongside the image.',NULL,1,1),('intro-profile-changed',1,1,'Intro - Profile Changed',NULL,'<p>Your profile has successfully been updated.</p><p>Please use the left hand menu to continue using the Image Bank.</p>',NULL,1,1),('intro-rename-lightbox',1,1,'Intro - Rename lightbox',NULL,'<p>Rename #a-lightbox# <strong>?prevName?</strong></p>',NULL,1,1),('intro-request-approval',1,1,'Intro - Request Approval',NULL,'<p>You are about to request the #items# listed.	Use the notes field to provide relevant information, e.g. intended use.</p>',NULL,1,1),('intro-request-approval-success',1,1,'Intro - Request Approval Success',NULL,'<p>Your request has been sent to the administrator. <br />Check your #a-lightbox# when you next log in to track the approval status of the images.</p>',NULL,1,1),('intro-request-cd',1,1,'Intro - Request on Cd',NULL,'<p>Please complete the details below to request the items in your #a-lightbox# on a CD.</p>',NULL,1,1),('intro-request-high-res',1,12,'Intro - Request high res',NULL,'You are requesting approval to download the high resolution version of the #item#(s) below.',NULL,1,1),('intro-security-questions-changed',1,1,'Intro - Security Questions',NULL,'<p>Your security questions have successfully been updated.</p><p>Please use the left hand menu to continue using the Image Bank.</p>',NULL,1,1),('intro-select-vid-thumb',1,1,'Intro - Select Video Thumbnail',NULL,'<p>Click the frame you would like to use as your preview thumbnail from the selection below.</p><p>Use the &#39;next&#39; and &#39;previous&#39; links to move through the movie.</p>',NULL,1,1),('intro-single-upload',1,1,'Intro - Single upload page',NULL,'<p>Please click &quot;Browse&quot; to locate the file you want to add, and then click &quot;Upload&quot; to upload the file.<br />Once the file has been uploaded you will be able to enter metadata about the #item#, and add it to categories.</p>',NULL,1,1),('intro-update-result-1',1,1,'Intro - Update Result 1',NULL,'<p>The #item# has been submitted to an admin user for approval - <a href=\"../action/viewOwnerAssetApproval\">view approval progress</a>.</p>',NULL,1,1),('intro-update-result-1-2',1,1,'Intro - Update Result 1 (alternate)',NULL,'<p>The #item# has been updated. <a href=\"../action/viewOwnerAssetApproval\">View the submitted page</a> to find out it\'s current approval status.</p>',NULL,1,1),('intro-update-result-1-3',1,1,'Intro - Update Result 1 (alternate 2)',NULL,'<p>The #item# has been submitted to an admin user for approval. You will not be able to further edit this asset until it has been approved or rejected for resubmission.</p>',NULL,1,1),('intro-update-result-2',1,1,'Intro - Update Result 2',NULL,'<p>Please select an option from the left-hand menu, or <a href=\"../action/viewHome\">return to the home page</a>.</p>',NULL,1,1),('intro-upload-success',1,1,'Intro - Upload Success page',NULL,'<p>Your file has been added, with an ID of <strong>?assetId?</strong>.</p><p>You can <a href=\"?viewAssetUrl?\">view the new asset</a>, <a href=\"?addAnotherFileUrl?\">add another file</a> or use the site navigation to continue.</p>',NULL,1,1),('invitations-intro',1,1,'Copy - Invitations introduction',NULL,'From here you can invite people to Asset Bank. Enter the email addresses of people you wish to invite and a message to be added to the invitation.',NULL,1,1),('item',1,8,'Term for item',NULL,'item',NULL,1,2),('items',1,8,'Plural term for item',NULL,'items',NULL,1,2),('job-cancelling',1,8,'Cancelling',NULL,'Cancelling...',NULL,1,2),('js-add-cat-items-lightbox',1,14,'Javascript - Add all items in category to lightbox',NULL,'Are you sure you want to add all #items# in this category to your #a-lightbox#?',NULL,1,3),('js-add-search-lightbox',1,14,'Javascript - Add all search results to lightbox',NULL,'Are you sure you want to add all #items# in your search results to your #a-lightbox#?',NULL,1,3),('js-add-x-cat-items-lightbox',1,14,'Javascript - Add x items in category to lightbox',NULL,'Are you sure you want to add all the #items# in this category (limited to the first ?count?) to your #a-lightbox#?',NULL,1,3),('js-add-x-search-lightbox',1,14,'Javascript - Add x search results to lightbox',NULL,'Are you sure you want to add all the #items# in your search results (limited to the first ?count?) to your #a-lightbox#?',NULL,1,3),('js-bulk-delete-confirm',1,9,'Javascript - Bulk delete confirm',NULL,'You must confirm you understand that assets will be permanently deleted.',NULL,1,3),('js-confirm-add-user-with-notification',1,14,'Javascript - Confirm add a new user with notification',NULL,'You have chosen to send a notification to the new user. Click ok to continue.',NULL,1,3),('js-confirm-add-user-without-notification',1,14,'Javascript - Confirm add a new user without notification',NULL,'You have chosen not to notify the new user. Click ok to continue.',NULL,1,3),('js-confirm-add-users',1,14,'Javascript - Confirm add all selected users',NULL,'Are you sure you want to add all selected users?',NULL,1,3),('js-confirm-cancel-file-update',1,14,'Javascript - Confirm cancel file update',NULL,'Are you sure you want to cancel the file update?',NULL,1,3),('js-confirm-copy-asset',1,14,'Javascript - Confirm copy asset?',NULL,'Are you sure you want to copy this #item#? Clicking this button will create a copy of the #item# and redirect you to the new #items# edit page.',NULL,1,3),('js-confirm-copy-asset-limit-exceeded',1,14,'Javascript - Confirm copy asset limit exceeded',NULL,'Are you sure you want to copy this #item#? Clicking this button will create a copy of the #item# and setup relationships to all of the source #items# children.',NULL,1,3),('js-confirm-delete-agreement',1,14,'Javascript - Confirm delete agreement',NULL,'Are you sure you want to delete this agreement?',NULL,1,2),('js-confirm-delete-asset',1,14,'Javascript - Confirm delete asset?',NULL,'Are you sure you want to permanantly delete this #item#?',NULL,1,3),('js-confirm-delete-asset-and-children',1,14,'Javascript - Confirm delete asset and children?',NULL,'Are you sure you want to permanantly delete this ?entityName?? Any ?childNamePlural? that this ?entityName? has may also be deleted.',NULL,1,3),('js-confirm-delete-batch-release',1,14,'Javascript - confirm delete batch release',NULL,'Are you sure you want to delete this #batch-release#?',NULL,1,3),('js-confirm-delete-extension-asset',1,14,'Javascript - Confirm delete extension asset?',NULL,'Are you sure you want to permenantly delete this #item#? This #item# extends a category, removing it will convert the category to a standard category. To remove the category and the extension asset together you need to go to category admin and delete the category itself.',NULL,1,3),('js-confirm-delete-image-version',1,14,'Javascript - confirm delete image version',NULL,'Are you sure you want to delete this version?',NULL,1,3),('js-confirm-delete-keyword',1,14,'Javascript - confirm delete keyword',NULL,'Are you sure you want to delete this keyword?',NULL,1,3),('js-confirm-delete-lightbox',1,14,'Javascript - Confirm delete lightbox',NULL,'Are you sure you want to delete this #a-lightbox#?',NULL,1,3),('js-confirm-delete-relationship',1,14,'Javascript - confirm delete relationship',NULL,'Are you sure you want to delete this relationship?',NULL,1,3),('js-confirm-delete-saved-search',1,14,'Javascript - confirm delete saved search',NULL,'Are you sure you want to delete this search?',NULL,1,3),('js-confirm-indd-override-template',1,14,'Javascript - Confirm InDesign Document Override Template',NULL,'If you override the template, then this document will not be automatically updated when the template is edited.',NULL,1,3),('js-confirm-link-assets',1,14,'Javascript - Confirm link assets',NULL,'Are you sure you want to link these assets?',NULL,1,3),('js-confirm-lose-changes',1,14,'Javascript - Confirm lose changes',NULL,'Clicking this link after making changes to the details below will cause you to lose the changes you have made. Continue?',NULL,1,3),('js-confirm-remove-all-lightbox',1,14,'Javascript - Confirm remove all from lightbox',NULL,'Are you sure you want to remove all the #items# from your #a-lightbox#?',NULL,1,3),('js-confirm-remove-all-promoted',1,14,'Javascript - Confirm remove all promoted',NULL,'Are you sure you want to remove all promoted #items#?',NULL,1,3),('js-confirm-remove-all-users',1,14,'Javascript - confirm remove all users',NULL,'Are you sure you want to remove all users?',NULL,1,3),('js-confirm-remove-lightbox',1,14,'Javascript - Confirm remove lightbox',NULL,'Are you sure you want to remove this #a-lightbox#?',NULL,1,3),('js-confirm-remove-selected-lightbox',1,14,'Javascript - Confirm remove selected from lightbox',NULL,'Are you sure you want to remove the selected #items# from your #a-lightbox#?',NULL,1,3),('js-confirm-reset-embed-status',1,14,'Javascript - Confirm reset embed status',NULL,'Are you sure you want to prevent this file from being embedded?',NULL,1,3),('js-google-map-search-fail',1,14,'Javascript - Google map search not successful',NULL,'Your search was not successful for the following reason:',NULL,1,3),('js-make-sure-items-selected',1,14,'Javascript - make sure items selected',NULL,'Please make sure you have some #items# selected.',NULL,1,3),('keyword-help',1,1,'Keyword Search Help','Keywords help text.','',NULL,1,1),('keyword-node',1,8,'Keyword node name',NULL,'keyword',NULL,1,2),('keyword-nodes',1,8,'Keyword node name plural',NULL,'keywords',NULL,1,2),('keyword-root',1,8,'Keyword root name',NULL,'Keywords',NULL,1,2),('keywordErrorDuplicateName',1,15,'keywordErrorDuplicateName',NULL,'That keyword is already in the list.',NULL,1,2),('keywordErrorKeywordIsSynonym',1,15,'keywordErrorKeywordIsSynonym',NULL,'\'%\' is a synonym of the following keyword(s): ',NULL,1,2),('keywordErrorKeywordNotInList',1,15,'keywordErrorKeywordNotInList',NULL,'\'%\' is not in the keyword list. Please change the keyword or add it to the master list. ',NULL,1,2),('keywordMissingTree',1,15,'keywordMissingTree',NULL,'Unable to find the keyword tree to browse',NULL,1,2),('label-access-type',1,9,'Label - Access Type',NULL,'Access Type',NULL,1,2),('label-acknowledgement-recipient',1,9,'Label - Acknowledgement recipient',NULL,'Acknowledgement recipient',NULL,1,2),('label-actions',1,9,'Label - Actions',NULL,'Actions',NULL,1,2),('label-add-comments',1,9,'Label - Additional comments',NULL,'Additional comments:',NULL,1,2),('label-add-file-later',1,9,'Label - add file later',NULL,'Add file later:',NULL,1,2),('label-add-lightbox',1,9,'Label - Add new lightbox',NULL,'Add a new #a-lightbox#:',NULL,1,2),('label-add-new-keyword',1,9,'Label - Add new keyword',NULL,'Add new keyword:',NULL,1,2),('label-add-new-keywords',1,9,'Label - Add new keywords','Label for \"Add new keywords\" on the \"Update video keywords\" page','Add new keywords',NULL,1,2),('label-added',1,9,'Label -Added',NULL,'Added -',NULL,1,2),('label-address',1,9,'Label - Address',NULL,'Address:',NULL,1,2),('label-address1',1,9,'Label - Address 1',NULL,'Address 1:',NULL,1,2),('label-address2',1,9,'Label - Address 2',NULL,'Address 2:',NULL,1,2),('label-advanced-viewing',1,9,'Label - Advanced Viewing',NULL,'Advanced viewing when unapproved?',NULL,1,2),('label-agree-to-terms',1,9,'Label - Agree to terms',NULL,'I agree to comply with the <a href=\"../action/viewConditionsPopup?extra=true\" class=\"help-popup\" target=\"_blank\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a> of the use of #app-name#.',NULL,1,1),('label-agreement',1,9,'Label - Agreement',NULL,'Agreement:',NULL,1,2),('label-agreement-actions',1,9,'Label - Agreement actions',NULL,'Actions',NULL,1,2),('label-agreement-expiry',1,9,'Label - Agreement expiry',NULL,'Expiry',NULL,1,2),('label-agreement-text',1,9,'Label - Agreement text',NULL,'Agreement Text:',NULL,1,2),('label-agreement-text-nc',1,9,'Label - Agreement text (no colon)',NULL,'Agreement text',NULL,1,2),('label-agreement-title',1,9,'Label - Agreement title',NULL,'Title',NULL,1,2),('label-agreement-type',1,9,'Label - Agreement Type',NULL,'Agreement type:',NULL,1,2),('label-agreements',1,9,'Label - Agreements',NULL,'Agreements',NULL,1,2),('label-approval-expires',1,9,'Label - Approval expires on',NULL,'Approval expires on:',NULL,1,2),('label-approval-status',1,9,'Label - Approval Status',NULL,'Approval Status:',NULL,1,2),('label-approval-status-nc',1,9,'Label - Approval status (no colon)',NULL,'Approval Status',NULL,1,2),('label-approval-user',1,9,'Label - Approval user',NULL,'Approval user:',NULL,1,2),('label-approved',1,9,'Label - Approved?',NULL,'Approved?',NULL,1,2),('label-aspect-maintain',1,9,'Label - Lock Aspect Maintain',NULL,'<em>(aspect ratio is maintain)</em>',NULL,1,2),('label-asset-type',1,9,'Label - Asset Type',NULL,'Type:',NULL,1,2),('label-assign-to',1,9,'Label - Assign to',NULL,'Assign to',NULL,1,2),('label-audio-url',1,9,'Label - Audio Url',NULL,'Audio url:',NULL,1,2),('label-audioSampleRate',1,9,'Label - Audio Sample Rate',NULL,'Audio Sample Rate (Hz)',NULL,1,2),('label-auto-login',1,9,'Label - Auto login checkbox',NULL,'Log in automatically next time',NULL,1,2),('label-autorotated',1,9,'Label - Image has been auto-rotated',NULL,'This image has been auto-rotated by ?autoRotateAngle?&deg;',NULL,1,2),('label-available',1,9,'Label - available',NULL,'Available:',NULL,1,2),('label-available-to-all',1,9,'Label - Available To All',NULL,'Show for all users:',NULL,1,2),('label-batch-release-notes',1,9,'Label - Batch Release Notes',NULL,'Notes:',NULL,1,2),('label-bulk-delete-confirm',1,9,'Label - Bulk delete confirm',NULL,'I understand that the selected assets will be permanently deleted.',NULL,1,3),('label-bulk-upload-date',1,9,'Label - Bulk Upload Date',NULL,'Bulk Upload Date:',NULL,1,2),('label-by',1,9,'Label - By',NULL,'by:',NULL,1,2),('label-can-edit',1,9,'Label - Can edit',NULL,'Can edit?',NULL,1,2),('label-captcha-security-check',1,9,'Label - Captcha Security Check',NULL,'Security Check:',NULL,1,2),('label-cd-address',1,9,'Label - Request CD address',NULL,'Address:',NULL,1,2),('label-cd-custom-field',1,9,'Label - Request CD custom field',NULL,'Telephone',NULL,1,2),('label-change-description',1,9,'Label - asset change description',NULL,'Change description:',NULL,1,2),('label-change-preview-start',1,9,'Label - Change Video Preview Start',NULL,'Also change the preview clip to start from the selected frame?',NULL,1,2),('label-child-assets',1,9,'Label - Child assets',NULL,'Child assets',NULL,1,2),('label-clip-url',1,9,'Label - Clip Url',NULL,'Clip url:',NULL,1,2),('label-color-space',1,9,'Label - Color space',NULL,'Color space: ',NULL,1,2),('label-comments',1,9,'Label - Comments',NULL,'Comments',NULL,1,2),('label-completeness',1,9,'Label - Completeness',NULL,'Completeness:',NULL,1,2),('label-completeness-nc',1,9,'Label - Completeness (no colon)',NULL,'Completeness',NULL,1,2),('label-compress-file',1,9,'Label - Compress file before download',NULL,'Compress (ZIP) the file before it is downloaded',NULL,1,3),('label-confirm-password',1,9,'Label - Confirm New Password',NULL,'Confirm New Password:',NULL,1,2),('label-contents',1,9,'Label - Contents',NULL,'Contents:',NULL,1,2),('label-convert-rgb',1,9,'Label - Convert to RGB',NULL,'Convert to RGB:',NULL,1,2),('label-copy-items',1,9,'Label - Copy Items',NULL,'copy the #items#',NULL,1,2),('label-copymove-identifier',1,9,'Label - CopyMove Identifier',NULL,'I would like to:',NULL,1,2),('label-country',1,9,'Label - Country',NULL,'Country:',NULL,1,2),('label-county',1,9,'Label - County',NULL,'County:',NULL,1,2),('label-create-a-copy',1,9,'Label - Create a copy',NULL,'Create a copy',NULL,1,2),('label-created-by',1,9,'Label - Created by',NULL,'Created by:',NULL,1,2),('label-created-date',1,9,'Label - Created date',NULL,'Created date:',NULL,1,2),('label-created-timestamp',1,9,'Label - Created Timestamp',NULL,'Date Added',NULL,1,2),('label-current-image',1,9,'Label - Current Image',NULL,'Current image:',NULL,1,2),('label-current-password',1,9,'Label - Current Password',NULL,'Current Password:',NULL,1,2),('label-date',1,9,'Label - Date',NULL,'Date:',NULL,1,2),('label-date-added-to-lightbox',1,9,'Label - Date added to lightbox',NULL,'Date/time added to lightbox',NULL,1,2),('label-date-submitted',1,9,'Label - Date Submitted',NULL,'Date Submitted',NULL,1,2),('label-date-time',1,9,'Label - Date and Time',NULL,'Date &amp; Time',NULL,1,2),('label-default-ordering',1,9,'Label - Default ordering',NULL,'Default ordering',NULL,1,2),('label-defer-thumbnails',1,9,'Label - Defer Thumbnail Generation',NULL,'Defer thumbnail creation?',NULL,1,2),('label-delete-existing-cats',1,9,'Label - Delete existing categories?',NULL,'Remove from all categories',NULL,1,2),('label-delete-peer-relationships',1,9,'Label - Delete peer relationships',NULL,'Unrelate assets:',NULL,1,2),('label-density',1,9,'Label - Density (DPI)',NULL,'Density (DPI):',NULL,1,2),('label-description',1,9,'Label - Description',NULL,'Description',NULL,1,2),('label-division',1,9,'Label - Division',NULL,'Division:',NULL,1,2),('label-do-not-embed-mapped-data',1,9,'Label - do not embed mapped data',NULL,'Don\'t embed mapped data',NULL,1,2),('label-download-approval-notes',1,9,'Label - Download approval notes',NULL,'Download approval notes:',NULL,1,2),('label-download-as-file',1,9,'Label - Download as file',NULL,'Download as a file',NULL,1,2),('label-download-as-image',1,9,'Label - Download as image',NULL,'Download as an image',NULL,1,2),('label-download-as-ppt',1,9,'Label - Download as PPT/PPTX',NULL,'Download as PowerPoint Presentation',NULL,1,2),('label-duration',1,9,'Label - Duration',NULL,'Duration:',NULL,1,2),('label-editable',1,9,'Label - Editable',NULL,'Editable',NULL,1,2),('label-editable-languages',1,9,'Label - Editable Languages',NULL,'Languages visible when editing assets',NULL,1,2),('label-email',1,9,'Label - Email Address',NULL,'Email Address:',NULL,1,2),('label-email-addresses',1,9,'Label - Email addresses',NULL,'Email addresses:',NULL,1,2),('label-email-file',1,9,'Label - Email file',NULL,'Send this file in an email',NULL,1,3),('label-email-nc',1,9,'Label - Email Address (no colon)',NULL,'Email Address',NULL,1,2),('label-end-point',1,9,'Label - End point','Label for \"End point\" on the \"Update video keywords\" page','End point (optional)',NULL,1,2),('label-expired',1,9,'Label - Expired',NULL,'Expired?',NULL,1,2),('label-expiry',1,9,'Label - Expiry',NULL,'Expiry:',NULL,1,2),('label-extract-import-zip',1,9,'Label - Extract and import zip',NULL,'Extract and import the contents of a zip file',NULL,1,2),('label-fax',1,9,'Label - Fax number',NULL,'Fax:',NULL,1,2),('label-featured-image',1,9,'Label - Featured Asset?',NULL,'Featured Asset?',NULL,1,2),('label-featured-image-caption',1,1,'Label - Featured Asset Caption',NULL,'',NULL,1,1),('label-file',1,9,'Label - file',NULL,'File:',NULL,1,2),('label-file-format',1,9,'Label - File Format',NULL,'File Format:',NULL,1,2),('label-file-formats',1,9,'Label - File Formats',NULL,'File Formats:',NULL,1,2),('label-file-size',1,9,'Label - File Size',NULL,'File Size:',NULL,1,2),('label-file-status',1,9,'Label - File Status',NULL,'File Status:',NULL,1,2),('label-file-type',1,9,'Label - File type',NULL,'File type:',NULL,1,2),('label-file-url',1,9,'Label - File url',NULL,'File url:',NULL,1,2),('label-filename',1,9,'Label - Filename',NULL,'Filename:',NULL,1,2),('label-find-what',1,9,'Label - Find what',NULL,'Find what:',NULL,1,2),('label-force-unapproved',1,9,'Label - force asset unapproved',NULL,'Get Approved?',NULL,1,2),('label-forename',1,9,'Label - Forename',NULL,'Forename:',NULL,1,2),('label-format-preference',1,9,'Label - Format preference',NULL,'Format preference:',NULL,1,2),('label-frameRate',1,9,'Label - Frame Rate',NULL,'Frame Rate (fps)',NULL,1,2),('label-from',1,9,'Label - From',NULL,'From',NULL,1,2),('label-fulfiller',1,9,'Label - Fulfiller',NULL,'Fulfiller',NULL,1,2),('label-generate-thumbnail',1,9,'Label - Generate thumbnail',NULL,'Generate thumbnail from original file',NULL,1,2),('label-group-edit',1,9,'Label - Group edit',NULL,'Group edit',NULL,1,2),('label-groups',1,9,'Label - Groups',NULL,'Groups:',NULL,1,2),('label-h',1,9,'Label - Height (short)',NULL,'H:',NULL,1,2),('label-height',1,9,'Label - Height',NULL,'Height:',NULL,1,2),('label-history',1,9,'Label - History',NULL,'History',NULL,1,2),('label-html-code',1,9,'Label - Html code',NULL,'HTML code:',NULL,1,2),('label-id',1,9,'Label -Id',NULL,'Id:',NULL,1,2),('label-ids',1,9,'Label - Ids',NULL,'Ids:',NULL,1,2),('label-image',1,9,'Label - Image',NULL,'Image:',NULL,1,2),('label-image-url',1,9,'Label - Image Url',NULL,'Image url:',NULL,1,2),('label-import-all-files',1,9,'Label - Import all files inc zips',NULL,'Import all files as #items# - including zips (?fileCount? files)',NULL,1,2),('label-import-from-directory',1,9,'Label - Import assets from a directory',NULL,'Import assets from a directory',NULL,1,2),('label-include-lightbox-contents',1,9,'Label - Include Lightbox contents',NULL,'Include #a-lightbox# contents?',NULL,1,2),('label-include-previous-versions',1,9,'Label - Include previous versions',NULL,'Include previous versions?',NULL,1,2),('label-include-this-item',1,9,'Label - Include this item?',NULL,'Include this #item#?',NULL,1,2),('label-included-in-search',1,9,'Label - included in search',NULL,'To be included in your search:',NULL,1,3),('label-indesign-pdf-quality',1,9,'Label - PDF Quality',NULL,'PDF Quality',NULL,1,2),('label-indesign-template-asset',1,9,'Label - Template Asset',NULL,'Template',NULL,1,2),('label-indesign-template-filename',1,9,'Label - Template Asset Filename',NULL,'Template Filename',NULL,1,2),('label-individual-edit',1,9,'Label - Individual edit',NULL,'Individual edit',NULL,1,2),('label-intended-usage',1,9,'Label - Intended Usage',NULL,'Intended Usage:',NULL,1,2),('label-intended-usage-details',1,9,'Label - Intended Usage Details',NULL,'Please enter details of your intended usage:',NULL,1,2),('label-intended-use',1,9,'Label - Intended Use',NULL,'Intended Use:',NULL,1,2),('label-ip-address',1,9,'Label - IP address',NULL,'IP Address',NULL,1,2),('label-is-brand-template',1,9,'Label - Is Brand Template',NULL,'Brand Template?',NULL,1,2),('label-is-featured',1,9,'Label - Is Featured?',NULL,'Is Featured?',NULL,1,2),('label-is-promoted',1,9,'Label - Is Promoted?',NULL,'Is Promoted?',NULL,1,2),('label-isPublic',1,9,'Label - Is Public?',NULL,'Visible to other users',NULL,1,2),('label-items-not-in-cat',1,9,'Label -  Find only items not in a category',NULL,'Find only #items# that are not in any category',NULL,1,2),('label-job-status',1,9,'Label - Job status',NULL,'Job status:',NULL,1,2),('label-job-title',1,9,'Label - Job Title',NULL,'Job Title:',NULL,1,2),('label-jpeg-quality',1,9,'Label - JPEG Quality',NULL,'JPEG Quality:',NULL,1,2),('label-keyword',1,9,'Label - Keyword',NULL,'Keyword',NULL,1,2),('label-keywords',1,9,'Label - Keywords',NULL,'Keywords:',NULL,1,2),('label-language',1,9,'Label - Language',NULL,'Language:',NULL,1,2),('label-last-downloaded-nc',1,9,'Label - Last downloaded (no colon)',NULL,'Date last downloaded',NULL,1,2),('label-latitude',1,9,'Label - Latitude',NULL,'Latitude',NULL,1,2),('label-layer',1,9,'Label - Layer',NULL,'Layer/Page:',NULL,1,2),('label-link-items',1,9,'Label - Link Items',NULL,'Link the items to each other?',NULL,1,2),('label-lock-aspect',1,9,'Label - Lock Aspect Ratio',NULL,'Lock Aspect Ratio:',NULL,1,2),('label-lock-aspect-nc',1,9,'Label - Lock Aspect Ratio (no colon)',NULL,'Lock Aspect Ratio',NULL,1,2),('label-longitude',1,9,'Label - Longitude',NULL,'Longitude',NULL,1,2),('label-match-case',1,9,'Label - Match case',NULL,'Match case',NULL,1,2),('label-match-whole-word',1,9,'Label - Match whole word',NULL,'Match whole word',NULL,1,2),('label-max-height',1,9,'Label - or Max Height',NULL,'OR Max Height:',NULL,1,2),('label-max-width',1,9,'Label - Max Width',NULL,'Max Width:',NULL,1,2),('label-message',1,9,'Label - Message',NULL,'Message:',NULL,1,2),('label-message-from-admin',1,9,'Label - Message from administrator',NULL,'Message from administrator:',NULL,1,2),('label-message-from-user',1,9,'Label - Message from the user',NULL,'Message from the user:',NULL,1,2),('label-message-nc',1,9,'Label - Message',NULL,'Message',NULL,1,2),('label-message-to-user',1,9,'Label - Message to the user',NULL,'Your message to the user:',NULL,1,2),('label-method',1,9,'Label - Method',NULL,'Method:',NULL,1,2),('label-modified-timestamp',1,9,'Label - Modified Timestamp',NULL,'Date Last Modified',NULL,1,2),('label-move-items',1,9,'Label - Move Items',NULL,'move the #items#',NULL,1,2),('label-my-alias',1,9,'Label - My Alias',NULL,'My Alias',NULL,1,2),('label-name',1,9,'Label - Name',NULL,'Name:',NULL,1,2),('label-name-nc',1,9,'Label - Name (no colon)',NULL,'Name',NULL,1,2),('label-new-keyword',1,9,'Label - New keyword',NULL,'New keyword:',NULL,1,2),('label-new-name',1,9,'Label - New Name',NULL,'New Name:',NULL,1,2),('label-new-password',1,9,'Label - New Password',NULL,'New Password:',NULL,1,2),('label-notes',1,9,'Label - Notes',NULL,'Notes:',NULL,1,2),('label-notify-when-downloaded',1,9,'Label - Notify when downloaded (File Transfer)',NULL,'Notify me when the file is downloaded:',NULL,1,2),('label-num-downloads',1,9,'Label - Number of downloads',NULL,'Number of Downloads',NULL,1,2),('label-num-placeholders',1,9,'Label - Number',NULL,'Number:',NULL,1,2),('label-num-sharing',1,9,'Label - No. sharing',NULL,'# Sharing',NULL,1,2),('label-num-uses',1,9,'Label - Number of Uses',NULL,'Uses',NULL,1,2),('label-num-views',1,9,'Label - Number of views',NULL,'Number of Views',NULL,1,2),('label-num-votes',1,9,'Label - Number of votes',NULL,'Number of Votes',NULL,1,2),('label-number-of-symbols',1,9,'Label - Number of labels',NULL,'Number of labels:',NULL,1,2),('label-on',1,9,'Label - On',NULL,'on:',NULL,1,2),('label-open-id',1,9,'Label - Open ID',NULL,'OpenID:',NULL,1,2),('label-option-any',1,1,'Label - Any',NULL,'[any]',NULL,1,2),('label-option-any-searchable-type',1,9,'Label - Any searchable type',NULL,'[any searchable type]',NULL,1,2),('label-option-any-type',1,9,'Label - Any type',NULL,'[any type]',NULL,1,2),('label-optional-message',1,9,'Label - Message (optional)',NULL,'Message (optional)',NULL,1,2),('label-or-directory',1,9,'Label - Or directory',NULL,'Or directory:',NULL,1,2),('label-org-unit',1,9,'Label - Organisational Unit',NULL,'Organisational Unit:',NULL,1,2),('label-org-units',1,9,'Label - Organisational Units',NULL,'Organisational Units:',NULL,1,2),('label-organisation',1,9,'Label - Organisation',NULL,'Organisation:',NULL,1,2),('label-outstanding-acknowledgements',1,9,'Label - Outstanding acknowledgements',NULL,'Outstanding acknowledgements',NULL,1,2),('label-owner',1,9,'Label - Owner',NULL,'Owner',NULL,1,2),('label-parent',1,9,'Label - Parent',NULL,'Parent:',NULL,1,2),('label-password',1,9,'Label - Password',NULL,'Password:',NULL,1,2),('label-peer-assets',1,9,'Label - Peer assets',NULL,'Peer assets',NULL,1,2),('label-pixel-height',1,9,'Label - Height (pixels)',NULL,'Height (pixels):',NULL,1,2),('label-pixel-width',1,9,'Label - Width (pixels)',NULL,'Width (pixels):',NULL,1,2),('label-please-choose',1,9,'Label - Please choose',NULL,'[Please choose]',NULL,1,2),('label-populate-name',1,9,'Label - Populate name',NULL,'Populate name from filename:',NULL,1,2),('label-position',1,9,'Label - Position',NULL,'Position:',NULL,1,2),('label-postcode',1,9,'Label - Postcode',NULL,'Postcode:',NULL,1,2),('label-preview-restricted',1,9,'Label - Preview Restricted?',NULL,'Preview Restricted?',NULL,1,2),('label-previous-agreements',1,9,'Label - Previous agreements',NULL,'Previous agreements',NULL,1,2),('label-promoted-item',1,9,'Label - Promoted Item?',NULL,'Promoted Item?',NULL,1,2),('label-quantity',1,9,'Label - Quantity',NULL,'Quantity',NULL,1,2),('label-rating',1,9,'Label - Rating',NULL,'Rating',NULL,1,2),('label-release-date',1,9,'Label - Release date',NULL,'Release date:',NULL,1,2),('label-relevance',1,9,'Label - Relevance',NULL,'Relevance',NULL,1,2),('label-remove-current-image',1,9,'Label - Remove Current Image',NULL,'Remove current image:',NULL,1,2),('label-remove-id',1,9,'Label - Remove id',NULL,'Remove id from filename:',NULL,1,2),('label-remove-thumbnail',1,9,'Label - Remove thumbnail',NULL,'Remove thumbnail',NULL,1,2),('label-remove-working-file',1,9,'Label - Remove working file',NULL,'Remove working file',NULL,1,2),('label-replace-with',1,9,'Label - Replace with',NULL,'Replace with:',NULL,1,2),('label-repurpose',1,9,'Label - Repurpose',NULL,'Create embeddable version?',NULL,1,2),('label-request-notes',1,9,'Label - Request Notes',NULL,'Your request notes:',NULL,1,2),('label-restricted',1,9,'Label - Is Restricted',NULL,'Restricted?',NULL,1,2),('label-results-per-page',1,9,'Label - Results per page',NULL,'Results per page:',NULL,1,2),('label-rotate',1,9,'Label - Rotate',NULL,'Rotate:',NULL,1,2),('label-rotate-images',1,9,'Label - Rotate Images',NULL,'Rotate the images in this batch?:',NULL,1,2),('label-saved-search-alert',1,9,'Label - Saved Search Alert',NULL,'Alert me when new images are added to this search:',NULL,1,2),('label-search',1,9,'Label - home search box',NULL,'Search...',NULL,1,2),('label-search-radius',1,9,'Label - Search radius',NULL,'Search radius',NULL,1,2),('label-search-subcategories',1,9,'Label -  Search subcategories checkbox on search',NULL,'Search subcategories of the selected categories',NULL,1,2),('label-security-questions-one',1,9,'Label - Security Question One',NULL,'Security Question One',NULL,1,2),('label-security-questions-two',1,9,'Label - Security Question Two',NULL,'Security Question Two',NULL,1,2),('label-select-item',1,9,'Label - Select item',NULL,'Select #item#',NULL,1,2),('label-select-usage',1,9,'Label - Select Usage',NULL,'Please select your intended usage for this asset',NULL,1,2),('label-selected-workflow',1,9,'Label - Selected workflow',NULL,'Selected workflow:',NULL,1,2),('label-send-to',1,9,'Label - Send To',NULL,'Send To:',NULL,1,2),('label-sensitive',1,9,'Label - Sensitive',NULL,'Sensitive?',NULL,1,2),('label-shared-between-org-units',1,9,'Label - Shared between org units',NULL,'Shared between Org Units:',NULL,1,2),('label-shared-with-org-unit',1,9,'Label - Shared with org unit',NULL,'Shared with current Org Unit:',NULL,1,2),('label-show-on-homepage',1,9,'Label - Show On Homepage',NULL,'Show on homepage:',NULL,1,2),('label-simple-indesign-include-lightbox-contents',1,9,'Label - Include Lightbox contents',NULL,'Include #a-lightbox# contents?',NULL,1,2),('label-size',1,9,'Label - Size',NULL,'Size:',NULL,1,2),('label-sort-by',1,9,'Label - Sort by',NULL,'Sort by:',NULL,1,2),('label-spatial-area-east',1,9,'Label - Spatial Area East Bounding Longitude',NULL,'East bounding longitude',NULL,1,2),('label-spatial-area-north',1,9,'Label - Spatial Area North Bounding Latitude',NULL,'North bounding latitude',NULL,1,2),('label-spatial-area-south',1,9,'Label - Spatial Area South Bounding Latitude',NULL,'South bounding latitude',NULL,1,2),('label-spatial-area-west',1,9,'Label - Spatial Area West Bounding Longitude',NULL,'West bounding longitude',NULL,1,2),('label-start-point',1,9,'Label - Start point','Label for \"Start point\" on the \"Update video keywords\" page','Start point',NULL,1,2),('label-start-time',1,9,'Label - Start time',NULL,'Start time:',NULL,1,2),('label-started-by',1,9,'Label - Started By',NULL,'Started by:',NULL,1,2),('label-status',1,9,'Label - Status',NULL,'Status:',NULL,1,2),('label-status-nc',1,9,'Label - Status (no colon)',NULL,'Status',NULL,1,2),('label-status-sans-colon',1,9,'Label - Status (sans colon)',NULL,'Status',NULL,1,2),('label-strip',1,9,'Label - Strip',NULL,'Strip:',NULL,1,2),('label-subject',1,9,'Label - Subject',NULL,'Subject',NULL,1,2),('label-submit-action',1,9,'Label - Submit Action',NULL,'Submit action',NULL,1,2),('label-submit-feedback',1,9,'Label - Submit Feedback',NULL,'Details of your feedback:',NULL,1,2),('label-submitted-by',1,9,'Label - Submitted by',NULL,'Submitted by',NULL,1,2),('label-subscriptions',1,9,'Label - Subscriptions',NULL,'Subscriptions:',NULL,1,2),('label-surname',1,9,'Label - Surname',NULL,'Surname:',NULL,1,2),('label-synonyms',1,9,'Label - Synonyms',NULL,'Synonyms',NULL,1,2),('label-tax-number',1,9,'Label - Tax number',NULL,'VAT Number:',NULL,1,2),('label-tel',1,9,'Label - Telephone',NULL,'Telephone:',NULL,1,2),('label-template',1,9,'Label - Template',NULL,'Template:',NULL,1,2),('label-thumbnail',1,9,'Label - Thumbnail',NULL,'Thumbnail:',NULL,1,2),('label-time',1,9,'Label - Time','Label for \"Time\" on the video keywords table','Time',NULL,1,2),('label-tint',1,9,'Label - Tint',NULL,'Apply Tint:',NULL,1,2),('label-title',1,9,'Label - Title',NULL,'Title:',NULL,1,2),('label-to',1,9,'Label - To',NULL,'To:',NULL,1,2),('label-to-lightbox',1,9,'Label - To lightbox',NULL,'To #a-lightbox#:',NULL,1,2),('label-total',1,9,'Label - Total',NULL,'Total:',NULL,1,2),('label-total-size',1,9,'Label - Total Size',NULL,'Total Size:',NULL,1,2),('label-totals',1,9,'Label - Totals',NULL,'Totals:',NULL,1,2),('label-town',1,9,'Label - Town',NULL,'Town:',NULL,1,2),('label-transition',1,9,'Label - Transition',NULL,'Transition',NULL,1,2),('label-transitioning',1,9,'Label - Transitioning',NULL,'Transitioning',NULL,1,2),('label-type',1,9,'Label - Type',NULL,'Type:',NULL,1,2),('label-update-workflow-status',1,9,'Label - Update workflow status',NULL,'Update workflow status:',NULL,1,2),('label-uploaded-on',1,9,'Label - Uploaded on',NULL,'Uploaded on:',NULL,1,2),('label-usage-description',1,9,'Label - Usage Description',NULL,'Usage Description',NULL,1,2),('label-use-template',1,9,'Label - Use template',NULL,'Use an attribute template (page will refresh):',NULL,1,2),('label-use-the-original',1,9,'Label - Use the original',NULL,'Use the original',NULL,1,2),('label-user',1,9,'Label - User',NULL,'User',NULL,1,2),('label-user-expiry-date',1,9,'Label - User expiry date',NULL,'Expiry date:',NULL,1,2),('label-user-group',1,9,'Label - User Group',NULL,'User Group:',NULL,1,2),('label-user-receive-alerts',1,9,'Label - Receive Alerts',NULL,'Receive Alerts:',NULL,1,2),('label-user-receive-alerts-note',1,9,'Label - Receive Alerts Note',NULL,'Check this to receive alert emails generated by the system.',NULL,1,2),('label-user-reg-message',1,9,'Label - User Registration Message',NULL,'Intended Use:',NULL,1,2),('label-user-register-date',1,9,'Label - Register Date',NULL,'Register Date:',NULL,1,2),('label-username',1,9,'Label - Username',NULL,'Username:',NULL,1,2),('label-username-email',1,9,'Label - Username or Email',NULL,'Username or Email:',NULL,1,2),('label-username-nc',1,9,'Label - Username (no colon)',NULL,'Username',NULL,1,2),('label-validity',1,9,'Label - Validity',NULL,'Validity:',NULL,1,2),('label-verify-links',1,9,'Label - Verify link(s)',NULL,'Verify...',NULL,1,2),('label-video-quality',1,9,'Label - Video quality',NULL,'Video quality:',NULL,1,2),('label-video-url',1,9,'Label - Video Url',NULL,'Video url:',NULL,1,2),('label-visible-on-search',1,9,'Label - Visible on search',NULL,'Visible on search:',NULL,1,2),('label-w',1,9,'Label - Width (short)',NULL,'W:',NULL,1,2),('label-watermark-email-asset',1,9,'Label - Watermark image',NULL,'Watermark image',NULL,1,2),('label-website',1,9,'Label - Website',NULL,'Website:',NULL,1,2),('label-width',1,9,'Label - Width',NULL,'Width:',NULL,1,2),('label-workflow-submit-options',1,9,'Label - Workflow submit options',NULL,'Workflow submit options',NULL,1,2),('label-working-file',1,9,'Label - Working file',NULL,'Working file',NULL,1,2),('label-x',1,9,'Label - X dimension',NULL,'X:',NULL,1,2),('label-y',1,9,'Label - Y dimension',NULL,'Y:',NULL,1,2),('label-your',1,9,'Label - Your',NULL,'Your',NULL,1,2),('label-your-answer',1,9,'Label - Your Answer',NULL,'Your Answer:',NULL,1,2),('label-your-notes',1,9,'Label - Your notes',NULL,'Your notes:',NULL,1,2),('label-your-username',1,9,'Label - Your Username',NULL,'Your Username is',NULL,1,2),('label-zip-file',1,9,'Label - Zip file',NULL,'Zip file:',NULL,1,2),('languageCodeNotUnique',1,15,'languageCodeNotUnique',NULL,'The code you entered for the language is already in use.',NULL,1,2),('languageCodeRequired',1,15,'languageCodeRequired',NULL,'Please enter a code for the language.',NULL,1,2),('languageNameNotUnique',1,15,'languageNameNotUnique',NULL,'The name you entered for the language is already in use.',NULL,1,2),('languageNameRequired',1,15,'languageNameRequired',NULL,'Please enter a name for the language.',NULL,1,2),('lb-item-lightbox',1,13,'Snippet - Item in Your Lightbox',NULL,'There is <strong>?numItems?</strong> #item# in your #a-lightbox#.',NULL,1,3),('lb-item-lightboxes',1,13,'Snippet - Item in This Lightbox',NULL,'There is <strong>?numItems?</strong> #item# in this #a-lightbox#.',NULL,1,3),('lb-items-lightbox',1,13,'Snippet - Items in Your Lightbox',NULL,'There are <strong>?numItems?</strong> #items# in your #a-lightbox#',NULL,1,3),('lb-items-lightboxes',1,13,'Snippet - Items in This Lightbox',NULL,'There are <strong>?numItems?</strong> #items# in this #a-lightbox#.',NULL,1,3),('lightbox-approval-rejected',1,1,'Lightbox - Approval rejected copy',NULL,'<p>#items# for which download approval has been rejected.</p><p>Click on &quot;Approval details&quot; for further information about approval status. <br />To request approval again click the &quot;Resubmit&quot; button.</p>',NULL,1,1),('lightbox-downloadable',1,1,'Lightbox - Downloadable items',NULL,'',NULL,1,1),('lightbox-downloadable-with-approval',1,1,'Lightbox - Downloadable items after approval',NULL,'<p>If there is further information about approval status, then you can click on &quot;Approval details&quot; to see it, (access may be temporary or subject to certain conditions).</p>',NULL,1,1),('lightbox-intro',1,1,'Intro - Lightbox',NULL,'',NULL,1,1),('lightbox-pending-approval',1,1,'Lightbox - Pending approval copy',NULL,'<p>These #items# are pending approval from the administrator before you can download them.</p>',NULL,1,1),('lightbox-require-approval',1,1,'Lightbox - Require approval copy',NULL,'<p>#items# requiring approval to download.</p>',NULL,1,1),('lightbox-update-actions',1,1,'Lightbox - Update Actions copy',NULL,'<p>These actions apply to all #items# in the #a-lightbox# that you have permission to update.</p>',NULL,1,1),('lightbox-view-only',1,1,'Lightbox - View only copy',NULL,'<p>These #items# are available to view only.</p>',NULL,1,1),('lightboxes',1,8,'Plural term for the Lightbox',NULL,'Lightboxes',NULL,1,2),('link-add',1,12,'Link - Add',NULL,'Add',NULL,1,2),('link-add-agreement',1,12,'Link - Add an agreement',NULL,'Add an agreement &raquo;',NULL,1,2),('link-add-all',1,12,'Link - Add all to lightbox',NULL,'Add all to #a-lightbox#',NULL,1,2),('link-add-an-item',1,12,'Link - Add an item',NULL,'Add an #item#',NULL,1,2),('link-add-asset-to-batch-release',1,12,'Link - Add asset to batch release',NULL,'Add to #batch-release#',NULL,1,2),('link-add-empty-related-assets',1,12,'Link - Add empty related assets',NULL,'Add empty related #items#',NULL,1,2),('link-add-more-users',1,12,'Link - Add more users',NULL,'Add more users &raquo;',NULL,1,2),('link-add-multiple',1,12,'Link - Add multiple',NULL,'Add multiple',NULL,1,2),('link-add-new-batch-release',1,12,'Link - Add new batch release',NULL,'Add new #batch-release# &raquo;',NULL,1,2),('link-add-search-builder-clause',1,12,'Link - Add search builder clause',NULL,'add clause',NULL,1,2),('link-add-single',1,12,'Link - Add single',NULL,'Add single',NULL,1,2),('link-add-to-lightbox',1,12,'Link - Add to lightbox',NULL,'add to #a-lightbox#',NULL,1,2),('link-add-to-mylightbox',1,12,'Link - Add to My lightbox',NULL,'add to #my-lightbox#',NULL,1,2),('link-add-users',1,12,'Link - Add users',NULL,'Add users &raquo;',NULL,1,2),('link-add-x',1,12,'Link - Add x to lightbox',NULL,'Add the first ?count? #items# to your #a-lightbox#',NULL,1,2),('link-advanced-search',1,12,'Link - Advanced Search',NULL,'Advanced search',NULL,1,2),('link-all-as-slideshow',1,12,'Link - View all as a slideshow',NULL,'View all as a slideshow',NULL,1,2),('link-all-versions',1,12,'Link - All versions',NULL,'View all versions',NULL,1,2),('link-alphabetise-lightboxes',1,12,'Link - Alphabetise lightboxes',NULL,'Alphabetise #lightboxes# &raquo;&quot;',NULL,1,2),('link-alternative-uploader',1,12,'Link - alternative uploader',NULL,'uploader popup',NULL,1,2),('link-approval-details',1,12,'Link - Approval Details',NULL,'Approval details',NULL,1,2),('link-approval-messages',1,12,'Link - View approval messages',NULL,'View approval messages',NULL,1,2),('link-back',1,12,'Link - Back',NULL,'&laquo; Back',NULL,1,2),('link-back-all-cats',1,12,'Link - Back to all cats',NULL,'&laquo; Back to all categories',NULL,1,2),('link-back-dl-image',1,12,'Link - Back to Download Image',NULL,'&laquo; Back to Download Image',NULL,1,2),('link-back-image-details',1,12,'Link - Back to image details',NULL,'&laquo; Back to ?assetTypeName? Details',NULL,1,2),('link-back-item',1,12,'Link - Back to item details',NULL,'&laquo; Back to #item# details',NULL,1,2),('link-back-lightbox',1,12,'Link - Back to lightbox',NULL,'&laquo; Back to #a-lightbox#',NULL,1,2),('link-back-login',1,12,'Link - Back to login',NULL,'&laquo; Back to login',NULL,1,2),('link-back-my-lightbox',1,12,'Link - Back to my lightbox',NULL,'&laquo; Back to #my-lightbox#',NULL,1,2),('link-back-to',1,12,'Link - Back to',NULL,'&laquo; Back to',NULL,1,2),('link-back-to-all-requests',1,12,'Link - Back to Pending Requests',NULL,'&laquo; Back to Pending Requests',NULL,1,2),('link-back-to-batch-release-search',1,12,'Link - Back to batch release search',NULL,'&laquo; #batch-release# search',NULL,1,2),('link-back-to-children',1,12,'Link - back to children',NULL,'&laquo; Back to child assets',NULL,1,2),('link-back-to-fulfilled-requests',1,12,'Link - Back to Fulfilled Requests',NULL,'&laquo; Back to Fulfilled Requests',NULL,1,2),('link-back-to-homepage',1,12,'Link - Back to homepage',NULL,'&laquo; Back to homepage',NULL,1,2),('link-back-to-my-requests',1,12,'Link - Back to My Requests',NULL,'&laquo; Back to My Requests',NULL,1,2),('link-back-to-rejected-requests',1,12,'Link - Back to Rejected Requests',NULL,'&laquo; Back to Rejected Requests',NULL,1,2),('link-back-to-reports',1,12,'Link - Back to reports',NULL,'&laquo; Back to reports',NULL,1,2),('link-back-to-repurposed-audio-clips',1,12,'Link - Back to embeddable audio clips',NULL,'Back to embeddable audio clips',NULL,1,2),('link-back-to-repurposed-images',1,12,'Link - Back to embeddable images',NULL,'Back to embeddable images',NULL,1,2),('link-back-to-repurposed-videos',1,12,'Link - Back to embeddable videos',NULL,'Back to embeddable videos',NULL,1,2),('link-batch-approve-assets',1,12,'Link - Batch approve assets',NULL,'Batch update',NULL,1,2),('link-batch-release-search',1,12,'Link - Batch release search',NULL,'#batch-release# search',NULL,1,2),('link-batch-revise-assets',1,12,'Link - Batch revise assets',NULL,'Batch revise these #items# &raquo;',NULL,1,2),('link-batch-update-assets',1,12,'Link - Batch update assets',NULL,'Batch update these assets &raquo;',NULL,1,2),('link-batch-update-batch',1,12,'Link - Batch update last batch',NULL,'Batch update the last imported batch &raquo;',NULL,1,2),('link-batch-update-lowercase',1,12,'Link - batch update (lowercase)',NULL,'batch update',NULL,1,2),('link-batch-update-these-items',1,12,'Link - Batch update these items',NULL,'Batch update these #items#',NULL,1,2),('link-bulk-approve-assets',1,12,'Link - Bulk approve assets',NULL,'Bulk  update',NULL,1,2),('link-bulk-delete-assets',1,12,'Link - Bulk delete assets',NULL,'Delete the selected #items# in this batch &raquo;',NULL,1,2),('link-bulk-update-batch',1,12,'Link - Bulk update last batch',NULL,'Bulk update the last imported batch &raquo;',NULL,1,2),('link-bulk-update-lowercase',1,12,'Link - bulk update (lowercase)',NULL,'bulk update',NULL,1,2),('link-bulk-update-these-items',1,12,'Link - Bulk update these items',NULL,'Bulk update these #items#',NULL,1,2),('link-cancel-current-batch',1,12,'Link - Cancel current batch update &raquo;',NULL,'Cancel current batch update &raquo;',NULL,1,2),('link-cancel-file-update',1,12,'Link - Cancel file update',NULL,'cancel file update',NULL,1,2),('link-change-crop',1,12,'Link - Change Crop Area',NULL,'Change crop area...',NULL,1,2),('link-choose-diff-frame',1,12,'Link - Choose different frame',NULL,'Choose a different frame for thumbnail &raquo;',NULL,1,2),('link-clear-crop',1,12,'Link - Clear Crop Area',NULL,'Clear crop area...',NULL,1,2),('link-clear-mask',1,12,'Link - Clear Mask',NULL,'Clear mask',NULL,1,2),('link-clearing-current-filter',1,12,'Link - Clear current filter',NULL,'Clear current filter &raquo;',NULL,1,2),('link-colour-picker',1,12,'Link - Colour picker',NULL,'Colour picker...',NULL,1,2),('link-compact-view',1,12,'Link - Compact View',NULL,'Compact View',NULL,1,2),('link-configure-contact-sheet',1,12,'Link- Configure contact sheet',NULL,'Configure Page Layout',NULL,1,2),('link-configure-mask',1,12,'Link - Configure Mask',NULL,'Configure mask...',NULL,1,2),('link-copy',1,12,'Link - Copy',NULL,'copy',NULL,1,2),('link-copy-move-items',1,12,'Link - Copy or move items',NULL,'Copy or move #items#',NULL,1,2),('link-copy-these-values-to-all',1,12,'Link - Copy these values to all',NULL,'Copy these values to all items &raquo;',NULL,1,2),('link-copy-to-all-items',1,12,'Link - Copy this value to all items',NULL,'Copy this value to all items &raquo;',NULL,1,2),('link-create-new-version',1,12,'Link - Create new version',NULL,'Create new version...',NULL,1,2),('link-current-version',1,12,'Link - Current version',NULL,'View current version',NULL,1,2),('link-default-relationships',1,12,'Link - Default relationships',NULL,'Default relationships',NULL,1,2),('link-delete',1,12,'Link - delete',NULL,'delete',NULL,1,2),('link-down',1,12,'Link - down',NULL,'down',NULL,1,2),('link-download-as-file',1,12,'Link - Download as file',NULL,'&laquo; Download as file',NULL,1,2),('link-download-as-image',1,12,'Link - Download as image',NULL,'Download as image &raquo;',NULL,1,2),('link-download-now',1,12,'Link - Download now',NULL,'Download now &raquo;',NULL,1,2),('link-edit',1,12,'Link - Edit',NULL,'edit',NULL,1,2),('link-edit-agreement',1,12,'Link - Copy selected agreement',NULL,'Copy selected agreement &raquo;',NULL,1,2),('link-edit-help-text',1,12,'Link - Edit help text',NULL,'Edit help text for this page',NULL,1,2),('link-edit-relationships',1,12,'Link - Edit Relationships',NULL,'Edit Relationships',NULL,1,2),('link-email-this-page',1,12,'Link - Email This Page',NULL,'Email This Page',NULL,1,2),('link-embed',1,12,'Link - Embed',NULL,'embed',NULL,1,2),('link-embed-as-file',1,12,'Link - Embed as file',NULL,'embed as file',NULL,1,2),('link-embed-as-image',1,12,'Link - Embed as image',NULL,'embed as image',NULL,1,2),('link-find',1,12,'Link - Find',NULL,'Find',NULL,1,2),('link-find-multiple',1,12,'Link - Find multiple',NULL,'Find multiple',NULL,1,2),('link-find-nearby-items',1,12,'Link - Fine nearby items',NULL,'Find nearby #items#',NULL,1,2),('link-finish-batch',1,12,'Link - Finish with batch',NULL,'Finish with this batch &raquo;',NULL,1,2),('link-first',1,12,'Link - First',NULL,'First',NULL,1,2),('link-fit-to-page',1,12,'Link - Fit to page',NULL,'fit to page',NULL,1,2),('link-follow-link',1,12,'Link - Follow link',NULL,'view...',NULL,1,2),('link-forgot-password',1,12,'Link - Forgotten Password',NULL,'Forgotten your password?',NULL,1,2),('link-hide-help',1,12,'Link - Hide help',NULL,'&laquo; Hide help',NULL,1,2),('link-hide-keywords',1,12,'Link - Hide keyword list',NULL,'&laquo; Hide keyword list',NULL,1,2),('link-in-lightbox',1,12,'Link - In Lightbox',NULL,'In #a-lightbox#',NULL,1,2),('link-in-my-lightbox',1,12,'Link - In My Lightbox',NULL,'In #my-lightbox#',NULL,1,2),('link-invite-more-users',1,12,'Link - Invite more users',NULL,'&laquo; Invite more users',NULL,1,2),('link-item-change-descriptions',1,12,'Link - Item change descriptions',NULL,'#item# change descriptions',NULL,1,2),('link-large-image-popup',1,12,'Link - large image popup',NULL,'large image popup',NULL,1,2),('link-last',1,12,'Link - Last',NULL,'Last',NULL,1,2),('link-list-view',1,12,'Link - List View',NULL,'List View',NULL,1,2),('link-make-new-request',1,12,'Link - Make new request',NULL,'Make a new request',NULL,1,2),('link-manage-batch-releases',1,12,'Link - Manage batch releases',NULL,'Manage #batch-releases#',NULL,1,2),('link-map',1,12,'Link - Map',NULL,'Map',NULL,1,2),('link-more',1,12,'Link - More',NULL,'More &raquo;',NULL,1,2),('link-more-like-this',1,12,'Link - More like this',NULL,'more like this...',NULL,1,2),('link-more-news',1,12,'Link - More news',NULL,'More news',NULL,1,2),('link-move',1,12,'Link - Move',NULL,'move',NULL,1,2),('link-next',1,12,'Link - Next',NULL,'Next &raquo;',NULL,1,2),('link-no',1,12,'Link - no',NULL,'no',NULL,1,2),('link-open',1,12,'Link - Open',NULL,'open',NULL,1,2),('link-panel-view',1,12,'Link - Panel View',NULL,'Panel View',NULL,1,2),('link-prev',1,12,'Link - Prev',NULL,'&laquo; Prev',NULL,1,2),('link-previous-messages',1,12,'Link - View previous messages',NULL,'View previous messages',NULL,1,2),('link-print',1,12,'Link - Print',NULL,'Print',NULL,1,2),('link-print-image',1,12,'Link - Print Image',NULL,'print image details',NULL,1,2),('link-print-pdf',1,12,'Link - Print As PDF',NULL,'Print As PDF',NULL,1,2),('link-process-request',1,12,'Link - Process request',NULL,'process request',NULL,1,2),('link-read',1,12,'Link - Read',NULL,'read',NULL,1,2),('link-refresh-the-log',1,12,'Link - Refresh the log',NULL,'Refresh the log',NULL,1,2),('link-refresh-view',1,12,'Link - Refresh view',NULL,'Refresh my view &raquo;',NULL,1,2),('link-remove',1,12,'Link - Remove',NULL,'Remove',NULL,1,2),('link-remove-all',1,12,'Link - Remove All',NULL,'Remove all',NULL,1,2),('link-remove-all-promoted',1,12,'Link - Remove all from promoted',NULL,'Remove all from promoted &raquo;',NULL,1,2),('link-remove-all-users',1,12,'Link - Remove all users',NULL,'Remove all users',NULL,1,2),('link-remove-from-promoted',1,12,'Link - Remove from promoted',NULL,'Remove from promoted',NULL,1,2),('link-remove-lightbox',1,12,'Link - Remove from lightbox',NULL,'Remove from #a-lightbox#',NULL,1,2),('link-remove-link',1,12,'Link - Remove Link',NULL,'Remove Link',NULL,1,2),('link-remove-lowercase',1,12,'Link - remove (lowercase)',NULL,'remove',NULL,1,2),('link-remove-my-lightbox',1,12,'Link - Remove from my lightbox',NULL,'Remove from #my-lightbox#',NULL,1,2),('link-remove-share',1,12,'Link - remove share',NULL,'remove share',NULL,1,2),('link-rename',1,12,'Link - rename',NULL,'rename',NULL,1,2),('link-return-cms',1,12,'Link - Return to CMS',NULL,'&laquo; Return to CMS',NULL,1,2),('link-return-current-batch',1,12,'Link - Return to current batch update',NULL,'Return to your current batch update &raquo;',NULL,1,2),('link-return-to-browse',1,12,'Link - Return to the category you were browsing',NULL,'&laquo; Return to the ?type? you were browsing',NULL,1,2),('link-return-to-original-user',1,12,'Link - Return to Original User',NULL,'Return to Original User',NULL,1,2),('link-revalidate-batch-release',1,12,'Link - Revalidate (batch release)',NULL,'Revalidate',NULL,1,2),('link-run',1,12,'Link - run',NULL,'run',NULL,1,2),('link-run-another-find-replace-update',1,12,'Link - Run another find and replace on batch',NULL,'Run another Find and Replace on this batch &raquo;',NULL,1,2),('link-run-another-update',1,12,'Link - Run another update on batch',NULL,'Run another update on this batch &raquo;',NULL,1,2),('link-run-find-replace-batch',1,12,'Link - Run find and replace on batch',NULL,'Run a Find and Replace on the selected #items# in this batch &raquo;',NULL,1,2),('link-run-last-search',1,12,'Link - Run last search',NULL,'Run last search &raquo;',NULL,1,2),('link-run-update-batch',1,12,'Link - Run update on batch',NULL,'Run an update on the selected #items# in this batch &raquo;',NULL,1,2),('link-save',1,12,'Link - save',NULL,'save',NULL,1,2),('link-save-as-pdf',1,12,'Link - Save as PDF',NULL,'Save as PDF',NULL,1,2),('link-save-search',1,12,'Link - Save search',NULL,'Save this search',NULL,1,2),('link-search-again',1,12,'Link - Search again',NULL,'New Search',NULL,1,2),('link-search-this-accesslevel',1,12,'Link - Search this access level',NULL,'Search this access level',NULL,1,2),('link-search-this-category',1,12,'Link - Search this category',NULL,'Search this category',NULL,1,2),('link-see-promoted',1,12,'Link - See Promoted Items',NULL,'See all promoted #items# &raquo;',NULL,1,2),('link-see-recent',1,12,'Link - See Recent Items',NULL,'See all recent #items# &raquo;',NULL,1,2),('link-select-all',1,12,'Link - Select all',NULL,'Select all',NULL,1,2),('link-select-crop',1,12,'Link - Select Crop Area',NULL,'Select crop area...',NULL,1,2),('link-select-items-bulk',1,12,'Link - select items for bulk update',NULL,'View and Select #items# in batch for update &raquo;',NULL,1,2),('link-select-items-bulk-again',1,12,'Link - Select items for another update',NULL,'Select #items# in batch for another update &raquo;',NULL,1,2),('link-select-keyword',1,12,'Link - Select another keyword',NULL,'Select another keyword &raquo;',NULL,1,2),('link-send-e-card',1,12,'Link - Send as E-Card',NULL,'send as e-card',NULL,1,2),('link-share',1,12,'Link - share',NULL,'share',NULL,1,2),('link-show-all',1,12,'Link - Show all',NULL,'Show all',NULL,1,2),('link-show-help',1,12,'Link - Show help ',NULL,'Show help for using Windows Explorer to upload your files &raquo;',NULL,1,3),('link-show-help-ftp',1,12,'Link - Show help (ftp true)',NULL,'Show help for using an FTP client to upload your files &raquo;',NULL,1,3),('link-simple-indesign-show-duplicate-assets',1,12,'Link - Show duplicate items',NULL,'Show duplicate #items#',NULL,1,3),('link-sorting-options',1,12,'Link - Sorting options',NULL,'Sorting  options',NULL,1,2),('link-specify-colour',1,12,'Link - Specify custom colour',NULL,'Specify custom colour',NULL,1,2),('link-sso-login',1,12,'Link - Login with SSO',NULL,'Login using SSO',NULL,1,2),('link-start-data-import',1,12,'Link - Start new data import',NULL,'Start a new data import &raquo;',NULL,1,2),('link-start-new-batch-update',1,12,'Link - Start new batch update',NULL,'Start a new batch update &raquo;',NULL,1,2),('link-start-new-bulk-update',1,12,'Link - Start new bulk update',NULL,'Start a new bulk update &raquo;',NULL,1,2),('link-submit-feedback',1,12,'Submit Feedback',NULL,'Submit Feedback',NULL,1,2),('link-this-page-as-slideshow',1,12,'Link - View this page as a slideshow',NULL,'View these images as a slideshow',NULL,1,2),('link-unselect-all',1,12,'Link - Unselect all',NULL,'Unselect all',NULL,1,2),('link-up',1,12,'Link - up',NULL,'up',NULL,1,2),('link-update-asset-change-descriptions',1,12,'Link - Update asset change descriptions',NULL,'Update asset change descriptions',NULL,1,2),('link-upload-more-files',1,12,'Link - Upload more files',NULL,'&laquo; Upload more files',NULL,1,2),('link-use-search-builder',1,12,'Link - Use search builder',NULL,'Use the search builder',NULL,1,2),('link-use-search-form',1,12,'Link - Use search form',NULL,'Use the search form',NULL,1,2),('link-validate-batch-release',1,12,'Link - Validate (batch release)',NULL,'Validate',NULL,1,2),('link-view',1,12,'Link - View',NULL,'view',NULL,1,2),('link-view-all-cats',1,12,'Link - View all categories',NULL,'View all categories &raquo;',NULL,1,2),('link-view-all-messages',1,12,'Link - View all messages',NULL,'View all messages &raquo;',NULL,1,2),('link-view-all-news',1,12,'Link - View all news',NULL,'&laquo; View all news',NULL,1,2),('link-view-archived-messages',1,12,'Link - View archived messages',NULL,'View archived messages &raquo;',NULL,1,2),('link-view-asset',1,12,'Link - View asset',NULL,'View asset &raquo;',NULL,1,2),('link-view-asset-audit',1,12,'Link - View asset audit',NULL,'View log...',NULL,1,2),('link-view-asset-usage',1,12,'Link - View asset usage',NULL,'View #item# usage...',NULL,1,2),('link-view-asset-workflow-audit',1,12,'Link - View asset workflow audit',NULL,'View workflow history...',NULL,1,2),('link-view-assets-in-batch-release',1,12,'Link - View assets in batch release',NULL,'View assets in #batch-release#',NULL,1,2),('link-view-contents',1,12,'Link - View contents (of lightbox)',NULL,'View contents &raquo;',NULL,1,2),('link-view-details',1,12,'Link - View details',NULL,'View details',NULL,1,2),('link-view-details-resize',1,12,'Link - View details or resize',NULL,'View details or resize',NULL,1,2),('link-view-edit-details',1,12,'Link - View/Edit details',NULL,'View/Edit details',NULL,1,2),('link-view-embedded',1,12,'Link - View Embedded Data',NULL,'View embedded data &raquo;',NULL,1,2),('link-view-full-size',1,12,'Link - view full size',NULL,'view full size',NULL,1,2),('link-view-full-version',1,12,'Link - View full version',NULL,'view full version &raquo;',NULL,1,2),('link-view-item',1,12,'Link - View item',NULL,'View #item#',NULL,1,2),('link-view-items',1,12,'Link - view items',NULL,'view #items#',NULL,1,2),('link-view-larger',1,12,'Link - view larger size',NULL,'view larger size',NULL,1,2),('link-view-linked-assets',1,12,'link-view-linked-assets',NULL,'View linked images',NULL,1,2),('link-view-linking-assets',1,12,'link-view-linking-assets',NULL,'View linking indds',NULL,1,2),('link-view-log',1,12,'Link - View log',NULL,'View log',NULL,1,2),('link-view-older-approved-items',1,12,'Link - View older approved items',NULL,'View your older live #items# &raquo;',NULL,1,2),('link-view-on-map',1,12,'Link - View on map',NULL,'View on map',NULL,1,2),('link-view-owner-approved-items',1,12,'Link - View owners approved items',NULL,'View your approved #items#',NULL,1,2),('link-view-rss',1,12,'Link - View Rss Feed',NULL,'View RSS feed',NULL,1,2),('link-view-status',1,12,'Link - View status',NULL,'View status &raquo;',NULL,1,2),('link-view-these-items',1,12,'Link - view these items',NULL,'view these #items#',NULL,1,2),('link-view-this-item',1,12,'Link - view this item',NULL,'view this #item#',NULL,1,2),('link-view-unsubmitted',1,12,'Link - View unsubmitted assets',NULL,'View unsubmitted #items# &raquo;',NULL,1,2),('link-view-whats-changed',1,12,'Link - View what&#39;s changed',NULL,'View what&#39;s changed',NULL,1,2),('link-view-your-lightbox',1,12,'Link - View your lightbox',NULL,'View your #a-lightbox# &raquo;',NULL,1,2),('link-visit-the-page',1,12,'Link - Visit The Page',NULL,'Visit the page &raquo;',NULL,1,2),('link-yes',1,12,'Link - yes',NULL,'yes',NULL,1,2),('login-as-no-permission',1,1,'Login copy - user needs permission','Copy shown on the login page when the user requires extra permissions','<p>You need to log in to access that page (your session may have expired). Please login below, or try returning to the <a href=\"viewHome\" title=\"Home page\">home page</a>.</p>',NULL,1,1),('login-page-copy',1,1,'Login page copy','Text to display on the login page.','<p>Please enter your username and password to log in, or <a href=\"../action/viewRegisterUser\">register here</a></p>',NULL,1,1),('login-page-copy-external-users',1,1,'Text to display on the login page when external users have additional fields.',NULL,'<p>Please enter your username and password to log in, or register as an <a href=\"../action/viewRegisterUser\">internal</a> or <a href=\"../action/viewRegisterUser?externalUser=true\">external</a> user.</p>',NULL,1,1),('login-page-copy-sso',1,1,'Login page copy (SSO)','Text to display on the SSO login page.','<p>Please enter your OpenID to log in.</p>',NULL,1,1),('login-title',1,10,'Heading - Login Page',NULL,'Login',NULL,1,2),('marketingGroupNameRequired',1,15,'marketingGroupNameRequired',NULL,'Please enter a name for the marketing group.',NULL,1,2),('maxSavedSearchesReached',1,15,'maxSavedSearchesReached',NULL,'You have reached the maximum number of saved searches - please delete one before proceeding.',NULL,1,2),('maxUploadSizeExceeded',1,15,'maxUploadSizeExceeded',NULL,'The file you are trying to upload exceeds the maximum upload size.',NULL,1,2),('menu-approve',1,7,'Main menu - Approve Items',NULL,'Approve #items#',NULL,1,2),('menu-browse',1,7,'Main menu - Browse',NULL,'Browse',NULL,1,2),('menu-bulk',1,7,'Main menu - Bulk Upload',NULL,'Bulk Upload',NULL,1,2),('menu-contact',1,7,'Main menu - Contact Us',NULL,'Contact Us',NULL,1,2),('menu-file-transfer',1,7,'Main menu - File Transfer',NULL,'File Transfer',NULL,1,2),('menu-help',1,7,'Header links - Help',NULL,'Help for this page',NULL,1,2),('menu-home',1,7,'Main menu - Home',NULL,'Home',NULL,1,2),('menu-log-in',1,7,'Header links - Log In',NULL,'Log In',NULL,1,2),('menu-log-out',1,7,'Header links - Log Out',NULL,'Log Out',NULL,1,2),('menu-messages',1,7,'Header links - Messages',NULL,'Messages',NULL,1,2),('menu-my-edits',1,7,'Menu - My Edits',NULL,'My Edits',NULL,1,2),('menu-my-uploads',1,7,'Menu - My uploads',NULL,'My Uploads/Edits',NULL,1,2),('menu-news',1,7,'Menu - News',NULL,'News',NULL,1,2),('menu-profile',1,7,'Header links - Your Profile',NULL,'Your Profile',NULL,1,2),('menu-purchases',1,7,'Main menu - My Purchases',NULL,'My Purchases',NULL,1,2),('menu-register',1,7,'Header links - Register',NULL,'Register',NULL,1,2),('menu-requests',1,7,'Main menu - Requests',NULL,'Requests',NULL,1,2),('menu-search',1,7,'Main menu - Advanced Search',NULL,'Advanced Search',NULL,1,2),('menu-single',1,7,'Main menu - Single Upload',NULL,'Single Upload',NULL,1,2),('menu-submitted',1,7,'Main menu - Submitted assets',NULL,'Submitted',NULL,1,2),('menu-subscribe',1,7,'Header links - Subscribe',NULL,'Subscribe',NULL,1,2),('menu-subscriptions',1,7,'Header links - Subscriptions',NULL,'Subscriptions',NULL,1,2),('menu-unsubmitted',1,7,'Main menu - Unsubmitted assets',NULL,'Unsubmitted',NULL,1,2),('menu-update-assets',1,7,'Main Menu - Update Assets',NULL,'Update #items#',NULL,1,2),('menu-upload',1,7,'Main menu - Upload',NULL,'Upload',NULL,1,2),('menu-welcome',1,7,'Header links - Welcome',NULL,'Welcome',NULL,1,2),('missingInDesignLinkedImage',1,15,'missingInDesignLinkedImage',NULL,'Warning: the InDesign document contains a linked image, [0], that could not be found in #app-name#.',NULL,1,2),('missingInDesignLinkedResourceOnView',1,15,'missingInDesignLinkedResourceOnView',NULL,'The InDesign document contains linked resources that could not be found in #app-name#:',NULL,1,2),('my-lightbox',1,8,'Term for My Lightbox',NULL,'My Lightbox',NULL,1,2),('no-empty-related-assets-child',1,1,'No empty related child assets to add text',NULL,'<p>This asset can not have child related assets.</p>',NULL,1,1),('no-empty-related-assets-peer',1,1,'No empty related peer assets to add text',NULL,'<p>This asset can not have peer related assets.</p>',NULL,1,1),('no-items-to-browse',1,1,'Copy - No items to browse',NULL,'There are no #items# in this ?browseableName?.',NULL,1,1),('no-search-results',1,13,'Snippet - No Search Results',NULL,'Your search did not match any #items#',NULL,1,1),('no-search-results-query',1,13,'Snippet - No Search Results (query)',NULL,'Your search for &quot;?sQuery?&quot; did not match any #items#',NULL,1,1),('noAssetFilesToDownload',1,15,'noAssetFilesToDownload',NULL,'None of the #items# in your #a-lightbox# have files that can be downloaded.',NULL,1,2),('noAssetsInBatchRelease',1,15,'noAssetsInBatchRelease',NULL,'This batch release can\'t be sent for approval because it doesn\'t have any assets assigned to it',NULL,1,2),('noAssetsSelectedForExport',1,15,'noAssetsSelectedForExport',NULL,'There are no assets currently selected for export.',NULL,1,2),('noBatchReleasesBeingCreated',1,15,'noBatchReleasesBeingCreated',NULL,'You need at least one Batch Release in the \'Being Created\' state to be able to upload/edit assets.',NULL,1,2),('noFilterName',1,15,'noFilterName',NULL,'You need to provide a name for this filter',NULL,1,2),('nonNumericDisplayLength',1,15,'nonNumericDisplayLength',NULL,'You need to input a numeric display length',NULL,1,2),('NoPermissionToDownload',1,15,'NoPermissionToDownload',NULL,'You do not have permission to download this asset.',NULL,1,3),('noPermissionUsageType',1,15,'noPermissionUsageType',NULL,'There are no preset download options for the selected usage type.',NULL,1,2),('noSearchResultsToExport',1,15,'noSearchResultsToExport',NULL,'There are no search results to export.',NULL,1,2),('noUsersSelectedForExport',1,15,'noUsersSelectedForExport',NULL,'There are no users currently selectede for export.',NULL,1,2),('pending-approval',1,13,'Snippet - pending approval',NULL,'Pending approval',NULL,1,2),('pixels',1,8,'Term for pixels',NULL,'pixels',NULL,1,2),('priceBandInvalidBasePrice',1,15,'priceBandInvalidBasePrice',NULL,'Please enter a valid value for the base price.',NULL,1,2),('priceBandInvalidMaxQuantity',1,15,'priceBandInvalidMaxQuantity',NULL,'Please enter a valid value for max quantity.',NULL,1,2),('priceBandInvalidName',1,15,'priceBandInvalidName',NULL,'Please enter a name for the Price Band.',NULL,1,2),('priceBandInvalidQuantity',1,15,'priceBandInvalidQuantity',NULL,'Please enter a valid quantity.',NULL,1,2),('priceBandInvalidShippingCost',1,15,'priceBandInvalidShippingCost',NULL,'Please enter valid values for the shipping cost entries.',NULL,1,2),('priceBandInvalidUnitPrice',1,15,'priceBandInvalidUnitPrice',NULL,'Please enter a valid value for the unit price.',NULL,1,2),('priceBandMaxQuantity',1,15,'priceBandMaxQuantity',NULL,'You can only request a maximum of % prints for each format',NULL,1,2),('priceBandMixedUsages',1,15,'priceBandMixedUsages',NULL,'You cannot proceed with both commercial and non-commercial usages for this image. Please purchase images for commercial use separately than those from non-commercial use.',NULL,1,2),('priceBandNoneSelected',1,15,'priceBandNoneSelected',NULL,'Please select one of the options.',NULL,1,2),('priceBandNoQuantity',1,15,'priceBandNoQuantity',NULL,'Please choose an option to purchase.',NULL,1,2),('privacy',1,1,'Privacy Policy','Privacy Policy','<p><span style=\"color: #888888;\">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>',NULL,1,1),('profile-change-password',1,1,'Profile change password',NULL,'You can also <a href=\"viewChangePassword\">change your password</a>',NULL,1,3),('profile-change-security-questions',1,1,'Profile change security questions',NULL,'or <a href=\"viewChangeSecurityQuestions\">change your security questions</a>.',NULL,1,3),('profile-modified-by',1,9,'Label - Last modified by',NULL,'Last Modified By: ',NULL,1,2),('promoted-intro',1,1,'Promoted Images intro text','The intro text shown on the promoted items page','<p>The items shown on this page are the promoted items.</p><p>Note to admin users: you can change this intro text from the admin area.</p>',NULL,1,1),('recent-intro',1,1,'Recent Assets intro text','The intro text shown on the recent assets page','<p>The items shown on this page are assets recently added to Asset Bank.</p><p>Note to admin users: you can change this intro text from the admin area.</p>',NULL,1,1),('refine-search-results',1,12,'Link - Refine results',NULL,'Refine results',NULL,1,2),('register-success-intro',1,1,'Post registration message','Text to display when the user has regsitered.','Thank you for registering.',NULL,1,1),('register-success-with-approval',1,1,'Post registration next steps, approval required','Next steps copy when regsitration approval is required.','We will be in contact shortly with your account details when your request has been approved.',NULL,1,1),('register-success-without-approval',1,1,'Post registration next steps, no approval','Next steps copy when regsitration approval is not required.','Your account details have been emailed to you.',NULL,1,1),('registration',1,1,'Registration form copy','The copy shown on the registration page.','<p>Please complete the short form below to register for access to Asset Bank.</p>',NULL,1,1),('relatedAssetNoPermission',1,15,'relatedAssetNoPermission',NULL,'The links could not be setup because you need to have permission to update at least 2 of the assets in your folder.',NULL,1,2),('relatedAssets',1,15,'relatedAssets',NULL,'The links between the assets have been successfully created. This will be reflected when you view the detail page for any of the linked assets.',NULL,1,2),('relatedAssetsFailure',1,15,'relatedAssetsFailure',NULL,'You need more than 1 #item# to setup links.',NULL,1,2),('relatedAssetSubset1',1,15,'relatedAssetSubset1',NULL,'Your links have been setup.',NULL,1,2),('relatedAssetSubset2',1,15,'relatedAssetSubset2',NULL,'of the assets were not included in the links because you don\'t have permission to update them.',NULL,1,2),('relatedAssetSubset2Singular',1,15,'relatedAssetSubset2Singular',NULL,'of the assets was not included in the links because you don\'t have permission to update it.',NULL,1,2),('request-user-permissions-update-intro',1,1,'Request user permissions update page intro text','Intro text for the request user permissions update page.','<p>Use this form if you would like to make a request to have your access permissions updated.</p>',NULL,1,1),('requestMessageMandatory',1,15,'requestMessageMandatory',NULL,'Please enter the request description.',NULL,1,2),('requestUserUpdateOrgUnitLine',1,15,'requestUserUpdateOrgUnitLine',NULL,'Selected Organisational Unit: ',NULL,1,2),('requestWorkflowMessageMandatory',1,15,'requestWorkflowMessageMandatory',NULL,'Please enter a message when changing the state of a request.',NULL,1,2),('search-builder-intro',1,1,'Intro - Search builder',NULL,'',NULL,1,1),('search-no-cats-selected',1,1,'No categories selected on search',NULL,'<em>*If no #category-nodes# are selected, all #category-nodes# will be searched.</em>',NULL,1,1),('search-no-results-copy',1,1,'Search no results text','Copy to show on search results when there are no matches.','',NULL,1,1),('search-results',1,13,'Snippet - Number of search results',NULL,'Your search returned ?numberHits? results',NULL,1,3),('search-results-max',1,13,'Snippet - Search Results (max)',NULL,'Your search returned over ?numberHits? results',NULL,1,3),('search-results-one',1,13,'Snippet - Search Results (one)',NULL,'Your search returned ?numberHits? result',NULL,1,3),('search-results-query',1,13,'Snippet - Search Results (query)',NULL,'Your search for &quot;?sQuery?&quot; returned ?numberHits? results ',NULL,1,3),('search-results-query-max',1,13,'Snippet - Search Results (query max)',NULL,'Your search for &quot;?sQuery?&quot; returned over ?numberHits? results ',NULL,1,3),('search-results-query-one',1,13,'Snippet - Search Results (query one)',NULL,'Your search for &quot;?sQuery?&quot; returned ?numberHits? result	',NULL,1,3),('search-results-restricted',1,13,'Snippet - Number of search results (restricted)',NULL,'Your search returned ?totalHits? results, of which ?numberHits? are shown below',NULL,1,3),('searchCriteriaIdRangeTooLarge',1,15,'searchCriteriaIdRangeTooLarge',NULL,'The id range is too large. The difference between the lower and upper id should be a maximum of',NULL,1,2),('snippet-1-image-per-page',1,13,'Snippet - 1 image per page',NULL,'1 image per page',NULL,1,2),('snippet-1-item-checked-out',1,13,'Snippet - 1 item checked out',NULL,'You have <a href=\"../action/viewCheckedOutAssets\">1 #item#</a> checked out for editing.',NULL,1,3),('snippet-1-item-incomplete-metadata',1,13,'Snippet - 1 uploaded items incomplete metadata',NULL,'?numIncompleteItems? of your uploaded #items# has incomplete metadata',NULL,1,2),('snippet-1-live-item-incomplete',1,13,'Snippet -1 live item incomplete',NULL,'1 of the #items# you uploaded is live but has incomplete metadata.',NULL,1,3),('snippet-16-images-per-page',1,13,'Snippet - 16 images per page',NULL,'16 images per page',NULL,1,2),('snippet-180-deg',1,13,'Snippet - 180 deg',NULL,'180&deg;',NULL,1,2),('snippet-2-images-per-page',1,13,'Snippet - 2 images per page',NULL,'2 images per page',NULL,1,2),('snippet-20-images-per-page',1,13,'Snippet - 20 images per page',NULL,'20 images per page',NULL,1,2),('snippet-4-images-per-page',1,13,'Snippet - 4 images per page',NULL,'4 images per page',NULL,1,2),('snippet-8-images-per-page',1,13,'Snippet - 8 images per page',NULL,'8 images per page',NULL,1,2),('snippet-90-deg-anticlockwise',1,13,'Snippet - 90 deg anticlockwise',NULL,'90&deg; anti-clockwise',NULL,1,2),('snippet-90-deg-clockwise',1,13,'Snippet - 90 deg clockwise',NULL,'90&deg; clockwise',NULL,1,2),('snippet-a',1,13,'Snippet - a',NULL,'a',NULL,1,2),('snippet-account-locked-out',1,13,'Snippet - Account Locked Out',NULL,'Sorry, your account has been locked out. Please use the <a href=\"../action/viewPasswordReminder\">forgotten password</a><br/>functionality to restore your password.',NULL,1,2),('snippet-account-suspended',1,13,'Snippet - Account suspended',NULL,'Sorry, your account has been suspended.',NULL,1,2),('snippet-acknowledged',1,1,'Snippet - Acknowledged',NULL,'Acknowledged',NULL,1,1),('snippet-acknowledgement-matrix-description',1,13,'Snippet - Acknowledgement matrix description',NULL,'A report showing a list of all the released #batch-releases# and the acknowledgement status of users that were sent acknowledgement requests for each.',NULL,1,3),('snippet-acknowledgement-matrix-intro',1,13,'Snippet - Acknowledgement matrix intro',NULL,'The following report shows details of all the released #batch-releases# and the acknowledgement status of users that were sent acknowledgement requests:',NULL,1,3),('snippet-acknowledgement-summary-description',1,13,'Snippet - Acknowledgement summary description',NULL,'A report detailing a list of the #batch-release# acknowledgement users and the number of oustanding acknowledgements they currently have.',NULL,1,3),('snippet-acknowledgement-summary-intro',1,13,'Snippet - Acknowledgement summary intro',NULL,'The following report details a list of the #batch-release# acknowledgement users and the number of oustanding acknowledgements they currently have:',NULL,1,3),('snippet-actions',1,13,'Snippet - Actions...',NULL,'More actions...',NULL,1,2),('snippet-actions-on-selected-title-attr',1,13,'Snippet - Actions on selected title attr',NULL,'More actions on selected #items#',NULL,1,3),('snippet-add-as-attachment',1,13,'Snippet - Add item as attachment',NULL,'add this #item# as an attachment',NULL,1,2),('snippet-add-asset-to-batch-release',1,13,'Snippet - Add Asset to Batch Release',NULL,'<p>Select the #batch-release# you want to add this asset to from the list below and click Add &raquo;</p>',NULL,1,1),('snippet-add-copy-to-batch-release',1,13,'Snippet - Add Copy to Batch Release',NULL,'<p>Select the #batch-release# you want to add this copy to from the list below and click Add &raquo;</p>',NULL,1,1),('snippet-add-file-later',1,13,'Snippet - add file later',NULL,'Alternatively check the &#39;Add file later&#39; checkbox and click the button to move to the next step and enter metadata for a file that you plan to upload at a later date.',NULL,1,2),('snippet-add-files-to-existing-assets',1,13,'Snippet - Add files to existing assets',NULL,'Add files to existing assets',NULL,1,2),('snippet-add-keyword-to',1,13,'Snippet - Add keyword to',NULL,'Add new keyword to',NULL,1,2),('snippet-add-new-child-assets-to-parent',1,13,'Snippet - Add new child assets to parent',NULL,'Add new child assets to existing parents',NULL,1,2),('snippet-add-placeholders',1,13,'Snippet - Add Empty Assets',NULL,'Add assets without files',NULL,1,2),('snippet-admin-notes',1,13,'Snippet - Administrator approval notes',NULL,'The administrator has added the following notes regarding usage for this image:',NULL,1,3),('snippet-agreement-applies',1,13,'Snippet - Agreement applies',NULL,'Agreement applies',NULL,1,2),('snippet-alert-revised-asset',1,1,'Snippet - Alert you have an asset requiring revision',NULL,'You have <strong>1</strong> <a href=\"viewOwnerAssetApproval\">#item# requiring revision</a>.',NULL,1,2),('snippet-alert-revised-assets',1,1,'Snippet - Alert you have some assets requiring revision',NULL,'You have <strong>?lNumUnsubmittedWorkflowedAssets?</strong> <a href=\"viewOwnerAssetApproval\">#items# requiring revision</a>.',NULL,1,2),('snippet-alert-unsubmitted-asset',1,1,'Snippet - Alert you have an unsubmitted asset',NULL,'You have <strong>1</strong> <a href=\"viewUnsubmittedAssets\">unsubmitted #item#</a>.',NULL,1,2),('snippet-alert-unsubmitted-assets',1,1,'Snippet - Alert you have some unsubmitted assets',NULL,'You have <strong>?lNumUnsubmittedAssets?</strong> <a href=\"viewUnsubmittedAssets\">unsubmitted #items#</a>.',NULL,1,2),('snippet-all',1,13,'Snippet - all (dropdown)',NULL,'all',NULL,1,2),('snippet-all-keywords',1,13,'Snippet - all (keywords)',NULL,'All',NULL,1,2),('snippet-all-users',1,13,'Snippet - All users',NULL,'All users',NULL,1,2),('snippet-already-voted',1,13,'Snippet - Already voted',NULL,'You have voted for this #item#',NULL,1,2),('snippet-always-use-watermark',1,13,'Snippet - Always use watermark',NULL,'Hide watermark (if permissions allow)',NULL,1,2),('snippet-and',1,13,'Snippet - and',NULL,'and',NULL,1,2),('snippet-any',1,13,'Snippet - any (dropdown)',NULL,'any',NULL,1,2),('snippet-any-type',1,13,'Snippet - any type (dropdown)',NULL,'any type',NULL,1,2),('snippet-append',1,13,'Snippet - append (dropdown)',NULL,'Append',NULL,1,1),('snippet-applet-error',1,13,'Snippet - Applet Error',NULL,'Applet failed to load. Please check you have Java enabled.',NULL,1,3),('snippet-applies-to-language',1,13,'Snippet - Applies to language',NULL,'Applies to the current language only',NULL,1,2),('snippet-approval-expires-on',1,13,'Snippet - approval expires on',NULL,'Approval for this item expires on',NULL,1,3),('snippet-approved',1,13,'Snippet - Approved',NULL,'Approved',NULL,1,2),('snippet-approved-for-high-res',1,13,'Snippet - Approved for high-res',NULL,'Approved for high-res download',NULL,1,2),('snippet-aprroved-items-you-uploaded',1,13,'Snippet - Approved Items you uploaded',NULL,'The following is a record of any uploads or edits you have made in the last ?numDaysShown? days that are now live.',NULL,1,3),('snippet-as-external-user',1,13,'Snippet - (External User)',NULL,'(External User)',NULL,1,2),('snippet-ascending',1,13,'Snippet - Ascending',NULL,'Ascending',NULL,1,2),('snippet-asset-is-category-extension',1,13,'Snippet - Asset is category extension',NULL,'<a href=\"?link?\">Browse ?catName?</a>',NULL,1,2),('snippet-asset-is-indesign-template',1,13,'Snippet - This asset is a template',NULL,'This asset is a template',NULL,1,2),('snippet-asset-requested',1,13,'Snippet - Asset Requested',NULL,'Asset Requested',NULL,1,2),('snippet-assets-ready',1,13,'Snippet - assets ready for download',NULL,'Your assets are ready to download!',NULL,1,2),('snippet-assets-without-messages',1,13,'Snippet - Assets without messages',NULL,'Assets without messages',NULL,1,2),('snippet-attributes-for-indesign-document',1,13,'Snippet - Attributes for Indesign Document',NULL,'Select the attributes you want to be available for use in your InDesign document.',NULL,1,3),('snippet-audio-conversion-error',1,13,'Snippet - audio conversion error',NULL,'An error occurred during your audio conversion. If you specified the audio sampling rate you may have entered values that are incompatible with the output file format.',NULL,1,2),('snippet-audio-conversion-finished',1,13,'Snippet - Audio conversion finished',NULL,'Your audio conversion has finished',NULL,1,2),('snippet-autocomplete-prompt',1,13,'Snippet - Autocomplete prompt ',NULL,'Keep typing to refine results...',NULL,1,2),('snippet-awaiting-approval',1,13,'Snippet - Awaiting Approval',NULL,'Awaiting approval',NULL,1,2),('snippet-batch-bulk-note',1,13,'Snippet - Batch and bulk update note',NULL,'Please note that you can only run one Batch Update or Bulk Update operation at once. These functions lock the #items# in the batch so that they cannot be used in another user&quot;s batch.',NULL,1,3),('snippet-batch-exceeds-size',1,13,'Snippet - Batch exceeds max size',NULL,'There is no option to select #items# in this batch since the batch size exceeds ?maxSizeForSelection?.',NULL,1,3),('snippet-batch-in-progress',1,13,'Snippet - Batch Update in progress',NULL,'You have a <a href=\"../action/viewManageBatchUpdate\">Batch Update</a> in progress.',NULL,1,1),('snippet-batch-meta-instruct',1,13,'Snippet - Batch Metadata instructions',NULL,'<p><em>Please fill in attributes to be applied to all #items# in the batch.</em> </p>	',NULL,1,1),('snippet-batch-not-in-progress',1,13,'Snippet - batch not in progress',NULL,'You currently do not have a batch update in progress.',NULL,1,3),('snippet-batch-release-invalid',1,13,'Snippet - Batch release invalid',NULL,'The #batch-release# is currently invalid. The errors that stopped the #batch-release# from being validated are shown in the error log below. If you think the errors below have been corrected <a href=\"?url?\">revalidate the #batch-release#</a> to check it again prior to release (validating will add any missing dependents that can be added to the release).',NULL,1,1),('snippet-batch-release-not-released',1,13,'Snippet - Batch release not released',NULL,'This #batch-release# couldn\'t be released because it is not in a valid state (or it is currently being validated). Please either <a href=\"?url?\">run the validation</a> or wait for the current validation to finish before attempting to release this #batch-release#.',NULL,1,1),('snippet-batch-release-pending',1,13,'Snippet - Batch release pending',NULL,'The #batch-release# is currently being validated. There are ?queueCount? jobs in the validation queue. <a href=\"?url?\">Refresh this page</a> for status updates or <a href=\'viewBatchReleaseJobs\'>view the job list</a> to see the status of individual validation jobs.',NULL,1,1),('snippet-batch-release-valid',1,13,'Snippet - Batch release valid',NULL,'The #batch-release# is currently valid and ready for release when it\'s fully approved.',NULL,1,3),('snippet-batch-release-validation',1,13,'Snippet - Batch Release Validation',NULL,'#batch-release# Validation',NULL,1,2),('snippet-batch-update-skipping-old-version',1,13,'Snippet - Batch Update Skipping old version of an asset',NULL,'This asset cannot be saved because there is a more recent version of it that is currently unapproved. Please click \'Skip\' to move to the next asset.',NULL,1,2),('snippet-batch-update-summary',1,13,'Snippet - batch update summary',NULL,'add metadata to each item individually',NULL,1,2),('snippet-batch-update-uploaded',1,13,'Snippet - batch update uploaded items',NULL,'Batch update your uploaded items',NULL,1,2),('snippet-being-validated',1,13,'Snippet - Currently being validated',NULL,'Currently being validated',NULL,1,2),('snippet-best',1,13,'Snippet - best',NULL,'Best',NULL,1,2),('snippet-between',1,13,'Snippet - between',NULL,'between',NULL,1,2),('snippet-blank-30-days',1,13,'Snippet - Leave blank for 30 days',NULL,'(leave blank for the default of 30 days)',NULL,1,3),('snippet-boolean-and',1,13,'Snippet - Boolean AND',NULL,'AND',NULL,1,2),('snippet-boolean-or',1,13,'Snippet - Boolean OR',NULL,'OR',NULL,1,2),('snippet-br-asset-change-intro',1,13,'Snippet - Batch release asset change descriptions intro',NULL,'Use an #items# change description field to describe how each #item# has changed due to the #batch-release#.',NULL,1,1),('snippet-browse-file-again',1,13,'Snippet - Note: need to browse for file again',NULL,'(Please note: for browser security reasons you will need to browse to your file again.)',NULL,1,1),('snippet-bulk-executing',1,13,'Snippet - Bulk Update executing',NULL,'A bulk update is executing.',NULL,1,2),('snippet-bulk-find-replace-attribute-instances',1,13,'Snippet - Bulk Find Replace Instances per attribute',NULL,'[0] ([1] instances)',NULL,1,2),('snippet-bulk-find-replace-total-instances',1,13,'Snippet - Bulk Find Replace Total Instances',NULL,'[0] instances of \'[1]\' have been found and replaced with \'[2]\'',NULL,1,2),('snippet-bulk-finished',1,13,'Snippet - Last Bulk update finished',NULL,'Your last bulk update operation has finished.',NULL,1,2),('snippet-bulk-in-progress',1,13,'Snippet - Bulk Update in progress',NULL,'You have a <a href=\"../action/viewManageBulkUpdate\">Bulk Update</a> in progress.',NULL,1,1),('snippet-bulk-meta-instruct',1,13,'Snippet - Bulk Metadata instructions',NULL,'<p>For each attribute that you want to be updated in bulk, choose to either replace or append the current value, then fill in the new value in the right-hand column. Attributes will not be updated unless the corresponding drop-down is selected.</p> <p>Please note that if replace is selected and the attribute has a blank field value in the right-hand column, then the attribute value will be set to empty on all #items# in the batch.</p>',NULL,1,1),('snippet-bulk-not-in-progress',1,13,'Snippet - Bulk not in progress',NULL,'You currently do not have a bulk update in progress.',NULL,1,2),('snippet-bulk-selected-title-attr',1,13,'Snippet - Bulk edit selected title attr',NULL,'Bulk edit the selected #items#',NULL,1,3),('snippet-bulk-update-deleting-asset',1,13,'Snippet - Bulk Update Deleting',NULL,'Deleting asset: [0]',NULL,1,2),('snippet-bulk-update-extension-asset',1,13,'Snippet - Bulk Update Extension Asset',NULL,'Asset: [0] is an extension asset. The name of the associated category won\'t be updated. If the category name needs updating please change it manually from the Admin section.',NULL,1,2),('snippet-bulk-update-finished',1,13,'Snippet - Bulk Update Finished',NULL,'Finished bulk update.',NULL,1,2),('snippet-bulk-update-sending-for-approval',1,13,'Snippet - Bulk Update Sending For Approval',NULL,'Asset [0]: Unable to submit to live, sending for approval',NULL,1,2),('snippet-bulk-update-skipping-asset-batch',1,13,'Snippet - Bulk Update Skipping asset for Batch Release',NULL,'Skipping asset: [0] it is not in a batch release and you have not selected a release to add it to',NULL,1,2),('snippet-bulk-update-skipping-asset-category',1,13,'Snippet - Bulk Update Skipping asset deletion',NULL,'Categories for this asset cannot be updated because an asset must be in at least one category',NULL,1,2),('snippet-bulk-update-skipping-asset-default-category',1,13,'Snippet - Bulk Update Skipping asset with default category',NULL,'Categories for this asset cannot be updated because the asset type has a default category assigned.',NULL,1,2),('snippet-bulk-update-skipping-asset-deletion',1,13,'Snippet - Bulk Update Skipping asset deletion',NULL,'You do not have permission to delete asset: [0]',NULL,1,2),('snippet-bulk-update-skipping-asset-extension',1,13,'Snippet - Bulk Update Skipping asset for Extension',NULL,'Skipping asset: [0] because it is an extension asset',NULL,1,2),('snippet-bulk-update-skipping-asset-level',1,13,'Snippet - Bulk Update Skipping asset deletion',NULL,'Access levels for this asset cannot be updated because an asset must be in at least one access level',NULL,1,2),('snippet-bulk-update-skipping-asset-permission',1,13,'Snippet - Bulk Update Skipping asset for Permission',NULL,'You do not have permission to update asset: [0] ([1])',NULL,1,2),('snippet-bulk-update-skipping-background-edit',1,13,'Snippet - Bulk Update Skipping because background edit in progress',NULL,'Skipping asset: [0] because it is being edited in the background',NULL,1,2),('snippet-bulk-update-skipping-due-to-category-conflict',1,13,'Snippet - Bulk Update Skipping due to category conflict',NULL,'Skipping asset: [0] because of an org unit root category conflict with this asset\'s type',NULL,1,2),('snippet-bulk-update-skipping-old-version',1,13,'Snippet - Bulk Update Skipping old version of an asset',NULL,'Skipping asset: [0] because it has a newer unapproved version',NULL,1,2),('snippet-bulk-update-starting',1,13,'Snippet - Bulk Update Starting',NULL,'Starting bulk update...',NULL,1,2),('snippet-bulk-update-summary',1,13,'Snippet - bulk update summary',NULL,'make changes to multiple items at once',NULL,1,2),('snippet-bulk-update-updating-asset',1,13,'Snippet - Bulk Update Updating',NULL,'Updating asset: [0]',NULL,1,2),('snippet-bulk-update-uploaded',1,13,'Snippet - Bulk update uploaded items',NULL,'Bulk update your uploaded items',NULL,1,2),('snippet-bulk-upload-cancel',1,13,'Snippet - Bulk upload cancel link',NULL,'Cancel this import',NULL,1,2),('snippet-bulk-upload-cancel-areyousure',1,13,'Snippet - Bulk upload cancel link are you sure text',NULL,'Are you sure you want to abort this import?',NULL,1,2),('snippet-bulk-upload-cancel-note',1,13,'Snippet - Bulk upload cancel note',NULL,'(the import will abort after the next item has finished)',NULL,1,2),('snippet-bulk-upload-existing-files',1,13,'Snippet - Bulk upload existing files',NULL,'<h2>Uploaded files already exist</h2> You have already uploaded some files, but not completed the import process.  The existing uploaded files will also be imported, or you can <a href=\"cancelImport\" onclick=\"return confirm(\'This will delete all uploaded files. Do you want to continue?\');\">delete all uploaded files</a>.</p>',NULL,1,1),('snippet-bulk-upload-unsubmitted',1,1,'Snippet - Bulk Upload page unsubmitted asset',NULL,'The #items# from the last bulk upload were not submitted.',NULL,1,2),('snippet-can-delete-version',1,13,'Snippet - can delete version',NULL,'If you have created this version in error, you may delete it by clicking the button below.',NULL,1,2),('snippet-can-edit',1,13,'Snippet - Users can edit',NULL,'Added users can edit the ',NULL,1,2),('snippet-can-still-edit-x',1,1,'Snippet - of which you can still edit X',NULL,'(of which you can still edit ?numEditableItems?)',NULL,1,2),('snippet-cannot-after-leaving-page',1,13,'Snippet - cannot after leaving page',NULL,'Note that you will not be able to do this after leaving this page.',NULL,1,2),('snippet-category-explorer',1,13,'Snippet - Category explorer',NULL,'Select from the explorer on the left',NULL,1,2),('snippet-category-extension-asset',1,13,'Snippet - Category extension asset',NULL,'You are creating an extended category. Add an asset to associate with the category by completing the standard asset upload process below. Once complete you will be redirected back to the list of categories.',NULL,1,2),('snippet-change-access-levels',1,13,'Snippet - Change access levels',NULL,'Change #access-level-root#:',NULL,1,2),('snippet-change-categories',1,13,'Snippet - Change categories',NULL,'Change #category-root#:',NULL,1,2),('snippet-change-entity-warning',1,13,'Snippet - Change Asset Entity Warning',NULL,'Any relationships with other assets that are not valid for the new type will be deleted.',NULL,1,3),('snippet-changed-batch-release-warning',1,13,'Snippet - Changed batch release warning',NULL,'You have moved this #item# to a new #batch-release#. If it has any dependents they will not be moved to the new #batch-release# automatically, you will need to update them manually if necessary.',NULL,1,2),('snippet-check-item-update',1,13,'Snippet - Check images to update',NULL,'Check the #items# you want to update and click &quot;Next &raquo;&quot;.',NULL,1,3),('snippet-check-item-update-again',1,13,'Snippet - Check images to update again',NULL,'Check the #items# you want to update again and click &quot;Next &raquo;&quot;.',NULL,1,3),('snippet-checkout-changes-lost',1,13,'Snippet - Checkout Changes Lost',NULL,'The user with this checked out will not be able to check-in any changes they have made, i.e. these changes will be lost!',NULL,1,3),('snippet-checkout-confirmation',1,13,'Snippet - Checkout Confirmation',NULL,'Are you sure you want to cancel the checkout of this item\'s source file?',NULL,1,3),('snippet-choose-diff-filter',1,13,'Snippet - Choose another filter',NULL,'<p>Choose another filter from the options above.</p>',NULL,1,1),('snippet-choose-template',1,13,'Snippet - Choose template',NULL,'[Choose template]',NULL,1,2),('snippet-choose-upload-method',1,13,'Snippet - Choose upload method',NULL,'Choose your upload method:',NULL,1,3),('snippet-choose-what-to-upload',1,13,'Snippet - Choose what to upload',NULL,'Choose what you want to upload:',NULL,1,2),('snippet-click-map-define-point',1,13,'Snippet - Click on map to define a point',NULL,'Click on the map to define a point:',NULL,1,3),('snippet-click-once-point-twice-area',1,13,'Snippet - Click once for point twice for area',NULL,'Click once to draw a point on the map, and twice to define an area:',NULL,1,3),('snippet-close-browser',1,13,'Snippet - CMS close browser window',NULL,'CMS mode is currently enabled which allows you to select an image for use in a content management system. If you do not want to select an image you can <a href=\"clearCmsMode\">exit CMS mode ï¿½</a>',NULL,1,2),('snippet-comment',1,13,'Snippet - Comment',NULL,'Comment',NULL,1,2),('snippet-comments',1,13,'Snippet - Comments',NULL,'Comments',NULL,1,2),('snippet-compare',1,13,'Snippet - Compare',NULL,'View as contact sheet',NULL,1,2),('snippet-compare-assets-text',1,13,'Snippet - Compare assets text',NULL,'',NULL,1,3),('snippet-complete',1,13,'Snippet - Complete',NULL,'Complete',NULL,1,2),('snippet-confirm-batch-release-transition',1,13,'Snippet - Confirm batch release transition',NULL,'Are you sure you want to transition this #batch-release#?',NULL,1,3),('snippet-contact-selected-title-attr',1,13,'Snippet - Contact sheet selected title attr',NULL,'View the selected #items# as a contact sheet',NULL,1,3),('snippet-contains-x-item',1,13,'Snippet - Contains x item',NULL,'contains <strong>?numAssetsLb?</strong> #item#',NULL,1,3),('snippet-contains-x-items',1,13,'Snippet - Contains x items',NULL,'contains <strong>?numAssetsLb?</strong> #items#',NULL,1,3),('snippet-converted-file-size',1,13,'Snippet - Converted file size',NULL,'Current converted file size:',NULL,1,2),('snippet-converting-audio',1,13,'Snippet - Converting audio',NULL,'Converting your audio clip. This may take a few minutes...',NULL,1,2),('snippet-converting-video',1,13,'Snippet - Converting video',NULL,'Converting your video. This may take a few minutes...',NULL,1,2),('snippet-copied-asset',1,13,'Snippet - Copied Asset',NULL,'#item# was successfully copied to ',NULL,1,2),('snippet-copied-assets',1,13,'Snippet - Copied Assets',NULL,'#items# were successfully copied to ',NULL,1,2),('snippet-copy-move-title-attr',1,13,'Snippet - Copy or move title attr',NULL,'Copy or move the selected #items# to another #a-lightbox#',NULL,1,3),('snippet-copy-of',1,13,'Snippet - Copy of',NULL,'Copy of',NULL,1,2),('snippet-create-custom-slideshow',1,13,'Snippet - Create custom slideshow',NULL,'Create custom slideshow',NULL,1,2),('snippet-create-new',1,13,'Snippet - Create new',NULL,'Create new...',NULL,1,2),('snippet-creating-zip-files',1,13,'Snippet - Creating zip files',NULL,'Creating zip file(s)...',NULL,1,3),('snippet-crop-defined-prefix',1,13,'Snippet - Crop defined prefix',NULL,'Crop defined',NULL,1,1),('snippet-crop-image-intro',1,13,'Snippet - Crop image intro',NULL,'<p>Leave default values if you do not want to crop</p>',NULL,1,1),('snippet-crop-instructions',1,13,'Snippet - Crop instructions',NULL,'Drag with your mouse to select the crop area. When you have finished, click &quot;Crop &amp; Close&quot;.',NULL,1,3),('snippet-crop-undefined',1,13,'Snippet - Crop undefined',NULL,'Crop undefined',NULL,1,1),('snippet-curr-filter-removed',1,13,'Snippet - Current filter removed',NULL,'<p>Your filter is currently set to <strong>&quot;?currFilter?&quot;</strong> which has now been removed from the system.</p>',NULL,1,1),('snippet-current',1,13,'Snippet - current',NULL,'current',NULL,1,2),('snippet-current-year',1,13,'Snippet - Most recent year that the code was changed (for copyright message)',NULL,'2012',NULL,1,2),('snippet-currently-batch-progress',1,13,'Snippet - Currenlty have a batch update in progress',NULL,'You currently have a batch update in progress.',NULL,1,3),('snippet-currently-editing',1,13,'Snippet - You are currently editing',NULL,'You are currently editing:',NULL,1,2),('snippet-currently-x-image-pp',1,13,'Snippet - currently x image per page',NULL,'(currently <strong>?numPerPage?</strong> image per page)',NULL,1,3),('snippet-currently-x-images-pp',1,13,'Snippet - currently x images per page',NULL,'(currently <strong>?numPerPage?</strong> images per page)',NULL,1,3),('snippet-decide-later',1,13,'Snippet - Decide later',NULL,'Decide later',NULL,1,2),('snippet-default',1,13,'Snippet - default',NULL,'default',NULL,1,2),('snippet-default-option',1,13,'Snippet - \"Default\" for use in <option> tags',NULL,'- Default -',NULL,1,2),('snippet-defer-thumbnails-note',1,13,'Snippet - defer thumbnail generation note',NULL,'<p><em>Checking this means your #items# will be imported more quickly, but you may not see the thumbnails until later.</em></p>',NULL,1,1),('snippet-define-languages-profile',1,13,'Snippet - Define which languages in profile',NULL,'You can define which languages are shown on this page in <a href=\"../action/viewChangeProfile\">your profile</a>.',NULL,1,1),('snippet-define-point-radius',1,13,'Snippet - Define search point and radius',NULL,'Define a search point and radius',NULL,1,2),('snippet-define-search-area',1,13,'Snippet - Define search area',NULL,'Define a search area',NULL,1,2),('snippet-delete-asset-batch-release-warning',1,13,'Snippet - Delete asset batch release warning',NULL,'<p>#batch-releases# are currently enabled on your Asset Bank. Ideally asset\'s shouldn\'t be deleted when #batch-releases# are enabled because then there is no approval process for the asset being removed.</p><p>Ideally a \'deleted\' attribute should be created and attribute visibility rules should be used to hide \'deleted\' assets from user\'s views.</p><p>If you understand the problem with deleting this asset outside of the #batch-release# process and want to continue, click the button below:</p>',NULL,1,1),('snippet-delete-request-confirmation',1,13,'Snippet - Delete Request Confirmation',NULL,'Are you sure you want to delete this request?',NULL,1,3),('snippet-denotes-required-field',1,13,'Snippet - Denotes required field',NULL,'denotes a required field',NULL,1,2),('snippet-dependency-job',1,13,'Snippet - Dependency Job',NULL,'Dependency Job',NULL,1,2),('snippet-descending',1,13,'Snippet - Descending',NULL,'Descending',NULL,1,2),('snippet-describe-request',1,13,'Snippet - Describe request',NULL,'Please provide details of the asset you require',NULL,1,1),('snippet-details',1,13,'Snippet - Details',NULL,'details',NULL,1,2),('snippet-display-time',1,13,'Snippet - Display time',NULL,'Picture display time (seconds):',NULL,1,2),('snippet-download',1,13,'Snippet - Download',NULL,'Download',NULL,1,2),('snippet-download-all-children-text',1,13,'Snippet - Download all children text',NULL,'Click the \'#button-download-all#\' button to download all ?termForChildren? of this #item# as a zip file.',NULL,1,3),('snippet-download-preview',1,13,'Snippet - download preview',NULL,'download preview',NULL,1,2),('snippet-download-requests-pending-approval',1,13,'Snippet - download requests pending approval',NULL,'However, there are <a href=\"viewAssetApproval\">download requests pending approval &raquo;</a>',NULL,1,2),('snippet-downloads',1,13,'Snippet - Downloads',NULL,'Downloads',NULL,1,2),('snippet-e-card-privacy',1,13,'Snippet - E-card privacy policy',NULL,'<em>We will not send you unwanted email messages or junk mail and your details will not be used in any way and will not be stored or passed to any third party.</em>',NULL,1,1),('snippet-e-card-sent',1,13,'Snippet - E-card Sent Confirmation',NULL,'Your e-card has been sent to: ?recipientEmail?',NULL,1,1),('snippet-edit-file-in-applet',1,13,'Snippet - Edit File in an Applet',NULL,'Use the applet below to check-out and edit the file.',NULL,1,3),('snippet-edit-source-file',1,13,'Snippet - Edit source file',NULL,'Edit source file',NULL,1,3),('snippet-edit-source-file-in',1,13,'Snippet - Edit source file in',NULL,'Edit in ?cseEditorName?',NULL,1,3),('snippet-email-addresses-comment',1,13,'Snippet - Email asset addresses comment',NULL,'(Separate addresses with \'?separator?\')',NULL,1,2),('snippet-email-asset-intro',1,13,'Snippet - Email asset(s) page intro',NULL,'',NULL,1,1),('snippet-email-failed',1,13,'Snippet - Email Failed',NULL,'There was a failure trying to send your email...',NULL,1,2),('snippet-email-sent',1,13,'Snippet - Email Sent Confirmation',NULL,'<p>Your email has been sent.</p>',NULL,1,1),('snippet-email-success',1,13,'Snippet - Email Success',NULL,'<p>Your email has been successfully sent.</p>',NULL,1,1),('snippet-email-this-page',1,13,'Snippet - Email This Page',NULL,'<p>You can check and edit the email you are about to send in the preview below. Once you are happy with it just enter the email address to send to and click the send button. </p>',NULL,1,1),('snippet-email-upload-in-progress',1,13,'Snippet - Email Upload In Progress',NULL,'Upload in progress...',NULL,1,2),('snippet-email-upload-navigate-away',1,13,'Snippet - Email Upload Navigate Away',NULL,'you can safely navigate away from this page and the upload will continue.',NULL,1,2),('snippet-emailed-asset-available',1,13,'Snippet - Emailed asset available',NULL,'The file that was sent to you in an email is available for download.',NULL,1,2),('snippet-emailed-asset-expired',1,13,'Snippet - Emailed asset expired',NULL,'The file that was sent to you in an email has now expired.<br />If you need to download the file, please contact the person who sent it to you.',NULL,1,2),('snippet-embed-file-intro',1,1,'Snippet - Embed file introduction',NULL,'The link and/or url below can be used to embed the file in an external website or application.',NULL,1,2),('snippet-empty',1,13,'Snippet - Empty',NULL,'Empty',NULL,1,3),('snippet-empty-assets-only',1,13,'Snippet - Empty assets only',NULL,'Empty assets only',NULL,1,1),('snippet-empty-related-assets-complete',1,13,'Snippet - Empty related assets complete',NULL,'<p>The empty assets have all been created and linked to the source asset. <a href=\"?link?\">Return to the source asset &raquo;</a></p>',NULL,1,2),('snippet-empty-related-assets-status',1,13,'Snippet - Empty related assets status',NULL,'The creation of the empty related assets is in progress... <span>Follow the status in the log below:</span>',NULL,1,2),('snippet-ensure-access-item',1,13,'Snippet - ensure access item',NULL,'so please ensure that you access the item before then.',NULL,1,3),('snippet-enter-captcha-letters',1,1,'Snippet - enter the characters shown in the captcha image',NULL,'Enter the characters you see in the image',NULL,1,1),('snippet-error-occurred',1,13,'Snippet - An error occurred',NULL,'An error occurred',NULL,1,3),('snippet-error-processing-item-for-download',1,13,'Snippet - Error processing item for download',NULL,'Error processing #item# \'[0]\' - the file will be excluded from the archive.',NULL,1,3),('snippet-error-warning',1,13,'Snippet - error warning',NULL,'Note, you should only enter values you know are valid for the format.',NULL,1,2),('snippet-errors-during-import',1,13,'Snippet - Errors during import',NULL,'The following errors occurred during import:',NULL,1,3),('snippet-errors-starting-import',1,9,'Snippet - Errors starting import',NULL,'The following errors occurred when trying to start the import:',NULL,1,2),('snippet-expired',1,13,'Snippet - expired',NULL,'expired',NULL,1,2),('snippet-extended-category',1,13,'Snippet - Extended category',NULL,'extended category',NULL,1,2),('snippet-extended-sub-category',1,13,'Snippet - Extended sub category',NULL,'extended sub category',NULL,1,2),('snippet-extension-asset-bulk-update',1,13,'Snippet - extension assets bulk update',NULL,'<p>Any category extension assets will be excluded from the update.</p>',NULL,1,2),('snippet-failed-to-unpack-spatial-area',1,13,'Snippet - Failed to unpack spatial area',NULL,'There is a problem in the format of the stored spatial area, it cannot be displayed: ?oldBadValue?',NULL,1,2),('snippet-feedback-present',1,13,'Snippet - Feedback Present',NULL,'There are ?feedbackCount? reviews for this #item#.',NULL,1,2),('snippet-feedback-present-single',1,13,'Snippet - Feedback Present Single',NULL,'There is 1 review for this #item#.',NULL,1,2),('snippet-file-downloading',1,13,'Snippet - File downloading',NULL,'(Your file should start downloading automatically, if it does not please click on the link above)',NULL,1,2),('snippet-file-selection-warning',1,13,'Snippet - File selection warning',NULL,'Please note: for browser security reasons if you wish to change the image file, you may need to browse and select it again.',NULL,1,2),('snippet-file-too-big',1,13,'Snippet - File too big for email',NULL,'Sorry, the file is too big to be emailed as an attachment.',NULL,1,2),('snippet-file-transfer-flashjava-upload-instructions',1,13,'Snippet - Flash/Java Upload Instructions',NULL,'<p>First, upload your file using the controls below, then click Next to choose who to send it to.</p>',NULL,1,1),('snippet-file-transfer-simple-upload-instructions',1,13,'Snippet - Simple Upload Instructions',NULL,'<p>Please click &quot;Browse&quot; to locate the file you want to send, and then click &quot;Upload&quot; to upload the file.<br /> Once the file has been uploaded you will be able to enter the email addresses of the people you want to send it to.</p>',NULL,1,1),('snippet-file-uploading',1,13,'Snippet - File uploading',NULL,'Please wait while your changes are saved. If you are adding a large file this may take a few minutes.<br/>If there is no further activity then please check that the value in the File field is empty or represents a valid file.',NULL,1,2),('snippet-finished-items-new-upload',1,13,'Snippet - finished with items, start new upload',NULL,'If you have finished with your uploaded #items#, you can start a new upload:',NULL,1,3),('snippet-finished-preparing-download',1,13,'Snippet - Finished preparing download',NULL,'Finished preparing download.',NULL,1,3),('snippet-flash-unsupported',1,13,'Snippet - Flash not supported',NULL,'Sorry your browser does not support flash. Please choose a different upload method.',NULL,1,2),('snippet-for',1,13,'Snippet - For',NULL,'for',NULL,1,2),('snippet-full-page',1,13,'Snippet - full page',NULL,'full page',NULL,1,2),('snippet-general-keywords',1,13,'Snippet - General keywords',NULL,'General Keywords',NULL,1,2),('snippet-group-edit',1,13,'Snippet - Group edit',NULL,'Use the same values for each item in the list.',NULL,1,2),('snippet-has',1,13,'Snippet - has',NULL,'has',NULL,1,2),('snippet-has-parent',1,13,'Snippet - has parent',NULL,'',NULL,1,2),('snippet-hidden-clause',1,13,'Snippet - Hidden Search Builder Clause',NULL,'<p>This search has hidden clauses that you do not have permission to edit. You can refine the search by adding your own clauses below:</p><p>Hidden clauses...</p>',NULL,1,1),('snippet-high',1,13,'Snippet - high',NULL,'High',NULL,1,2),('snippet-high-res-pending',1,13,'Snippet - High-res approval pending',NULL,'High-res approval pending',NULL,1,2),('snippet-high-resolution-approval',1,13,'Snippet - High resolution approval',NULL,'High-res approval is pending - check your lightbox',NULL,1,3),('snippet-how-dl-pdf',1,13,'Snippet - How to download pdf',NULL,'Please select how you want to download <em>?assetName?</em>:',NULL,1,1),('snippet-ie6-not-supported',1,13,'Snippet - IE6 not supported',NULL,'Note: This feature does not support Internet Explorer 6. Please try a different browser.',NULL,1,3),('snippet-if-applicable',1,13,'Snippet - If applicable',NULL,'if applicable',NULL,1,2),('snippet-if-changed',1,13,'Snippet - (if changed)',NULL,'(if changed)',NULL,1,2),('snippet-if-no-bg',1,13,'Snippet - If no bg colour selected',NULL,'If no background colour is specified then the final image will adopt the original background colour of the mask (as seen above).',NULL,1,3),('snippet-image-expired',1,13,'Snippet - image expired',NULL,'This asset cannot be downloaded by non-admin users as it has expired.',NULL,1,2),('snippet-image-restricted',1,13,'Snippet - image restricted',NULL,'This asset cannot be downloaded by non-admin users as it is restricted.',NULL,1,2),('snippet-image-should-downloading',1,13,'Snippet - Image should start downloading',NULL,'(Your image should start downloading automatically, if it does not please click on the link above)',NULL,1,3),('snippet-image-shown-actual-size',1,13,'Snippet - Shown actual size',NULL,'shown actual size',NULL,1,2),('snippet-import-in-progress',1,13,'Snippet - Import in progress',NULL,'The import is in progress...',NULL,1,2),('snippet-import-not-started',1,13,'Snippet - Import not started',NULL,'Your data import could not be started. Please check that an import is not in progress already.',NULL,1,3),('snippet-in',1,13,'Snippet - in',NULL,'in',NULL,1,3),('snippet-in-batch-release',1,13,'Snippet - In Batch Release',NULL,'This #item# is part of the #batch-release#: <a href=\"viewBatchRelease?brId=?brId?&assetId=?assetId?\">?brName?</a>. It is currently <strong>unreleased</strong>.',NULL,1,1),('snippet-in-batch-release-released',1,13,'Snippet - In Batch Release (released version)',NULL,'This asset was released as part of the #batch-release#: <a href=\"viewBatchRelease?brId=?brId?&assetId=?assetId?\">?brName?</a> on ?brReleaseDate?',NULL,1,1),('snippet-in-progress',1,13,'Snippet - In progress',NULL,'In progress',NULL,1,2),('snippet-inches-print',1,13,'Snippet - Inches print',NULL,'inches (print at ?printDPI? DPI);',NULL,1,2),('snippet-inches-screen',1,13,'Snippet - Inches screen',NULL,'inches (screen at ?screenDPI? DPI);',NULL,1,2),('snippet-include-lightbox-contents',1,13,'Snippet - Include Lightbox contents',NULL,'This will download all the #items# in your  #a-lightbox#  to your working directory for use in the document',NULL,1,3),('snippet-incomplete',1,13,'Snippet - Incomplete',NULL,'Incomplete',NULL,1,2),('snippet-incorrect-login-details',1,13,'Snippet - Incorrect login',NULL,'The details you entered are incorrect. Please try again.',NULL,1,2),('snippet-indd-make-templated-warning',1,13,'Snippet - Make InDesign Document Templated Warning',NULL,'Making this asset templated will delete this asset\'s InDesign document.',NULL,1,3),('snippet-indesign-asset-overrides-template',1,13,'Snippet - Asset overrides template',NULL,'(overridden)',NULL,1,2),('snippet-indesign-create-new-template',1,13,'Snippet - Create this asset as a new InDesign template asset',NULL,'Create new template',NULL,1,2),('snippet-indesign-data-intro',1,13,'Snippet - Indesign data intro',NULL,'Please wait while the file is prepared for editing. This page will be refreshed automatically every 5 seconds to check progress. To check manually please click the \'Next\' button.',NULL,1,3),('snippet-indesign-edit-file-info',1,13,'Snippet - InDesign Edit File Info',NULL,'Choosing a file will <strong>overwrite</strong> the generated InDesign Document. Usually an InDesign Document is generated from source file.',NULL,1,2),('snippet-indesign-no-templates-for-asset-entity',1,13,'Snippet - InDesign - No templates for asset type',NULL,'No templates found. Please try a different Type.',NULL,1,2),('snippet-indesign-pdf-errors',1,13,'Snippet - InDesign PDF Errors',NULL,'Generation Errors',NULL,1,2),('snippet-indesign-pdf-status',1,13,'Snippet - InDesign PDF Status',NULL,'PDF status',NULL,1,2),('snippet-individual-edit',1,13,'Snippet - Individual edit',NULL,'Allow different values for each item in the list.',NULL,1,2),('snippet-insert-page-breaks',1,13,'Snippet - Insert page breaks',NULL,'Insert page breaks',NULL,1,2),('snippet-invalid',1,13,'Snippet - Invalid',NULL,'Invalid',NULL,1,2),('snippet-invalid-spatial-area',1,13,'Snippet - Invalid spatial area',NULL,'One or more of the coordinates in this Spatial Area attribute are invalid longitude/latitude values.',NULL,1,2),('snippet-invitations-sent',1,13,'Snippet - Invitations sent',NULL,'Your invitations were sent',NULL,1,2),('snippet-item-not-dl',1,13,'Snippet - Item not downloaded yet',NULL,'This #item# has not been downloaded yet.',NULL,1,3),('snippet-items-selected',1,13,'Snippet - items selected',NULL,'items selected.',NULL,1,2),('snippet-job-log-intro',1,13,'Snippet - Job log intro',NULL,'The following log shows the messages for the selected currently running job:',NULL,1,2),('snippet-kb',1,13,'snippet - Kilobytes',NULL,'Kb',NULL,1,2),('snippet-keyword-autocomplete-hint',1,13,'Snippet - Keyword autocomplete hint',NULL,'',NULL,1,3),('snippet-landscape',1,13,'Snippet - Landscape',NULL,'Landscape',NULL,1,2),('snippet-language-specific-overrides',1,13,'Snippet - Language Specific Overrides',NULL,'Language-specific overrides',NULL,1,3),('snippet-layers',1,13,'Snippet - Layers',NULL,'layers',NULL,1,2),('snippet-lb-not-shared',1,13,'Snippet - lightbox not shared',NULL,'This #a-lightbox# is not currently shared with any other users.',NULL,1,3),('snippet-lightbox-links',1,13,'Snippet - Lightbox links',NULL,'<a href=\"../action/viewAssetBox\">#link-back-my-lightbox#</a>',NULL,1,1),('snippet-lightbox-panel-label',1,13,'Snippet - Lightbox panel label',NULL,'',NULL,1,2),('snippet-lightbox-ready',1,13,'Snippet - Lightbox ready to download',NULL,'Your #a-lightbox# is ready to download!',NULL,1,2),('snippet-lightbox-too-large',1,13,'Snippet - Lightbox too large',NULL,'This lightbox is too large to download. You should remove some items or use a smaller size.',NULL,1,1),('snippet-lightbox-too-large-request-cd',1,13,'Snippet - Lightbox too large request CD',NULL,'You may also <a href=\"viewRequestAssetBox\">request these assets on CD</a>',NULL,1,1),('snippet-link-selected-title-attr',1,13,'Snippet - Link selected title attr',NULL,'Link the selected #items# together',NULL,1,3),('snippet-link-to-item',1,13,'Snippet - Include link to item',NULL,'include a link to this #item#',NULL,1,2),('snippet-linking-items-note',1,13,'Snippet - linking items note',NULL,'<p><em>Linking #items# means that when an #item# is viewed, all other #items# from the batch will be shown as related #items#.</em></p>',NULL,1,1),('snippet-loading',1,13,'Snippet - Loading',NULL,'Loading...',NULL,1,2),('snippet-logged-out',1,13,'Snippet - Logged out remotely',NULL,'You are not currently authenticated with ?remoteDirectoryName? - please log in again, or contact an administrator if you continue to see this message.',NULL,1,2),('snippet-login-request-approval',1,13,'Snippet - Need to login to request approval',NULL,'<p>You need to log in to your account before you can request approval for these #items#.</p>',NULL,1,1),('snippet-low',1,13,'Snippet - low',NULL,'Low',NULL,1,2),('snippet-low-res-only',1,13,'Snippet - Low-res only',NULL,'Low-res download only',NULL,1,2),('snippet-made-request-on',1,13,'Snippet - made request on',NULL,'You made a request for this item on',NULL,1,3),('snippet-matching-keywords',1,13,'Snippet - Matching Keywords',NULL,'',NULL,1,2),('snippet-max-saved-searches',1,13,'Snippet - max saved searches reached',NULL,'The maximum number of saved searches has been reached - you must replace or delete an existing search.',NULL,1,2),('snippet-mb',1,13,'snippet - Megabytes',NULL,'Mb',NULL,1,2),('snippet-me',1,13,'Snippet - me (first person singular objective pronoun)',NULL,'me',NULL,1,2),('snippet-media-download-links',1,13,'Snippet - Media download links',NULL,'If your download doesn&#39;t start automatically please <a href=\"?downloadUrl?\">right click here</a> and select &#39;Save As&#39;. Once you have finished downloading the file you can either <a href=\"?viewAssetUrl?\">view the asset page</a> or select another option from the menu on the left hand side.',NULL,1,2),('snippet-media-email-conversion',1,13,'Snippet - Media email conversion',NULL,'If your browser doesn\'t automatically redirect you to the send email page then <a href=\"?emailUrl?\">click here</a>',NULL,1,2),('snippet-message',1,13,'Snippet - Message',NULL,'Message',NULL,1,2),('snippet-message-acknowledgement-text',1,13,'Snippet - Message acknowledgement text',NULL,'I acknowledge',NULL,1,2),('snippet-message-from-previous',1,13,'Snippet - Message from previous',NULL,'Message from previous user:',NULL,1,2),('snippet-message-optional',1,13,'Snippet - Workflow transition message optional',NULL,'please add a message below',NULL,1,3),('snippet-message-required',1,13,'Snippet - Workflow transition message required',NULL,'message required',NULL,1,3),('snippet-message-to-next',1,13,'Snippet - Message to next',NULL,'Message to next user:',NULL,1,2),('snippet-minute',1,13,'Snippet - Minute',NULL,'minute',NULL,1,2),('snippet-minutes',1,13,'Snippet - Minutes',NULL,'minutes',NULL,1,2),('snippet-mixed-res-lightbox',1,13,'Snippet - Mixed res lightbox',NULL,'Some of the #items# in your selection are not approved for high-res download. If you would like to select a high-res format below then you can either:<br/>a) Click on the Request approval button to get approval for these #items#, or<br/>b) Go back to your #a-lightbox# and remove from your selection the #items# that do not have high-res approval.',NULL,1,3),('snippet-mm-print',1,13,'snippet - mm print',NULL,'mm (print at ?printDPI? DPI);',NULL,1,2),('snippet-mm-screen',1,13,'snippet - mm screen',NULL,'mm (screen at ?screenDPI? DPI);',NULL,1,2),('snippet-moved-asset',1,13,'Snippet - Moved Asset',NULL,'#item# was successfully moved to ',NULL,1,2),('snippet-moved-assets',1,13,'Snippet - Moved Assets',NULL,'#items# were successfully moved to ',NULL,1,2),('snippet-navigate-away',1,13,'Snippet - Navigate away',NULL,'You can navigate away from this page and return later without affecting the import.',NULL,1,3),('snippet-need-js-enabled',1,13,'Snippet - Need javascript for uploader',NULL,'<strong>Note:</strong> You need javascript enabled for this uploader to work',NULL,1,2),('snippet-new-assets-with-metadata',1,13,'Snippet - New assets with metadata',NULL,'Standard import (add new assets with metadata)',NULL,1,2),('snippet-next',1,13,'Snippet - Next:',NULL,'Next:',NULL,1,2),('snippet-no',1,13,'Snippet - No',NULL,'No',NULL,1,2),('snippet-no-aprroved-items-you-uploaded',1,13,'Snippet - No approved Items you uploaded',NULL,'You do not have any live uploads or edits from the last ?numDaysShown? days.',NULL,1,3),('snippet-no-assets-in-batch-release',1,13,'Snippet - No assets in batch release',NULL,'<p>There are no assets currently associated with this #batch-release#.</p>',NULL,1,1),('snippet-no-assets-visible-for-email',1,13,'Snippet - No Assets Visible in Marketing Email',NULL,'You don&#39;t have permission to see any of the #items# for this email - please contact an application administrator in order to resolve this issue.',NULL,1,2),('snippet-no-audio-conversion',1,13,'Snippet - no audio conversion',NULL,'You currently have no audio conversions in progress.</p><p>If you were in the middle of converting an audio clip then an error may have occurred with your conversion. Please return to the asset and try again.',NULL,1,2),('snippet-no-batch-releases-to-add-to',1,13,'Snippet - No batch releases to add to',NULL,'<p>There are no unreleased #batch-releases# to add this asset to.</p>',NULL,1,1),('snippet-no-cats-selected',1,13,'Snippet - No cats selected',NULL,'<em>*If no categories are selected, all categories will be searched. </em>',NULL,1,1),('snippet-no-conversion',1,13,'Snippet - No conversion',NULL,'No Conversion',NULL,1,2),('snippet-no-edits-to-approve',1,13,'Snippet - No edits to approve',NULL,'There are currently no edits for you to approve.',NULL,1,2),('snippet-no-embedded-data',1,13,'Snippet - No embedded data',NULL,'This image contains no embedded data (e.g. EXIF, IPTC, XMP).',NULL,1,3),('snippet-no-featured-searches',1,13,'Snippet - no featured searches',NULL,'You currently don&#39;t have any featured searches.',NULL,1,2),('snippet-no-feedback',1,13,'Snippet - No Feedback',NULL,'There is currently no feedback',NULL,1,2),('snippet-no-filtering',1,13,'Snippet - No filtering',NULL,'No filtering',NULL,1,2),('snippet-no-fulfillers-defined',1,13,'Snippet - No fulfillers defined',NULL,'<p>There aren\'t any fulfiller users available. An admin user will need to make 1 or more users fulfillers by ticking the box on their Admin > Users > [edit] page.</p>',NULL,1,1),('snippet-no-help',1,10,'Snippet - No help for this page',NULL,'Sorry, there is no help for this page.',NULL,1,2),('snippet-no-items-approval',1,13,'Snippet - No items awaiting approval',NULL,'<p>There are no #items# awaiting approval.</p>',NULL,1,1),('snippet-no-items-batch',1,13,'Snippet - No items in batch',NULL,'There are no #items# in your batch.',NULL,1,3),('snippet-no-items-for-keyword',1,13,'Snippet - No items found for keyword',NULL,'<strong>#subhead-no-items-found#</strong> - Please choose another keyword:',NULL,1,1),('snippet-no-jobs-running',1,13,'Snippet - No jobs running',NULL,'There are currently no batch release jobs running',NULL,1,2),('snippet-no-keywords',1,13,'Snippet - No keywords',NULL,'There are currently no keywords at this level.',NULL,1,2),('snippet-no-keywords-for-letter',1,13,'Snippet - no keywords for letter',NULL,'There are no keywords beginning with the letter',NULL,1,2),('snippet-no-mask-selected',1,13,'Snippet - No mask selected',NULL,'No mask selected.',NULL,1,2),('snippet-no-matching-batch-releases',1,13,'Snippet - No matching batch releases found',NULL,'No matching #batch-releases# found.',NULL,1,3),('snippet-no-message',1,13,'Snippet -No message',NULL,'No message',NULL,1,2),('snippet-no-message-items',1,13,'Snippet - No messages',NULL,'There are currently no messages.',NULL,1,2),('snippet-no-messages-for-job',1,13,'Snippet - No messages for job',NULL,'There are currently no messages for this job.',NULL,1,2),('snippet-no-of-requests',1,13,'Snippet - No. of requests',NULL,'You have made ?noOfRequests? requests',NULL,1,1),('snippet-no-oustanding-acknowledgements',1,13,'Snippet - No outstanding acknowledgements for Batch Release',NULL,'There are no #batch-release# with outstanding acknowledgements.',NULL,1,2),('snippet-no-permission-feedback',1,13,'Snippet - No Permission on Feedback',NULL,'You do not have permission to submit feedback for this #item#.',NULL,1,2),('snippet-no-public-lightboxes',1,13,'Snippet - No Public Lightboxes',NULL,'There are no public lightboxes',NULL,1,1),('snippet-no-recent-searches',1,13,'Snippet - no recent searches',NULL,'You haven&#39;t run any searches since logging in.',NULL,1,2),('snippet-no-recommended-searches',1,13,'Snippet - no recommended searches',NULL,'You currently don&#39;t have any recommended searches.',NULL,1,2),('snippet-no-repurposed-audio-clips',1,13,'Snippet - no embeddable audio clips',NULL,'There are currently no embeddable versions of this audio file - you may <a href=\"?url?\">create the first embeddable version</a> if you wish.',NULL,1,2),('snippet-no-repurposed-images',1,13,'Snippet - no repurposed versions',NULL,'There are currently no embeddable versions of this image - you may <a href=\"?url?\">create the first embeddable version</a> if you wish.',NULL,1,2),('snippet-no-repurposed-videos',1,13,'Snippet - embeddable videos intro (no embeddable versions)',NULL,'There are currently no embeddable versions of this video - you may <a href=\"?url?\">create the first embeddable version</a> if you wish.',NULL,1,2),('snippet-no-requests',1,13,'Snippet - No requests',NULL,'You don\'t currently have any requests.',NULL,1,1),('snippet-no-requests-in-state',1,13,'Snippet - No requests in state',NULL,'Currently there are no requests in this status.',NULL,1,1),('snippet-no-rotation',1,13,'Snippet - No rotation',NULL,'No rotation',NULL,1,2),('snippet-no-saved-searches',1,13,'Snippet - no saved searches',NULL,'You currently don&#39;t have any saved searches.',NULL,1,2),('snippet-no-search-term',1,13,'Snippet - no search term',NULL,'no keywords entered',NULL,1,2),('snippet-no-submitted-items',1,13,'Snippet - No submitted items in approval',NULL,'You currently have no #item#s being approved for this workflow.',NULL,1,2),('snippet-no-uploadable-types',1,13,'Snippet - no uploadable types',NULL,'There are currently no asset types that you may bulk-upload outside of the context of a parent asset.',NULL,1,2),('snippet-no-uploaded-items',1,13,'Snippet - No uploaded items to approve',NULL,'There are currently no uploads for you to approve.',NULL,1,2),('snippet-no-users-found',1,13,'Snippet - No users found',NULL,'There are no users in the system whose details match your criteria.',NULL,1,3),('snippet-no-value',1,13,'Snippet - No Value',NULL,'no value',NULL,1,2),('snippet-no-video-conversion',1,13,'Snippet - no video conversion',NULL,'You currently have no video conversions in progress.</p><p>If you were in the middle of converting a video then an error may have occurred with your conversion. Please return to the asset and try again.',NULL,1,2),('snippet-no-workflow-audit',1,13,'Snippet - No workflow audit',NULL,'No workflow audit for this asset.',NULL,1,3),('snippet-non-empty-assets-only',1,13,'Snippet - Non-empty assets only',NULL,'Non-empty assets only',NULL,1,1),('snippet-none',1,13,'Snippet - none',NULL,'none',NULL,1,2),('snippet-not-applicable',1,13,'Snippet - Not applicable',NULL,'n/a',NULL,1,2),('snippet-not-approve-all',1,13,'Snippet - No permission to approve all',NULL,'<p>You may not have permission to approve all or any of these #items#.</p>',NULL,1,2),('snippet-not-approved',1,13,'Snippet - Not Approved',NULL,'Not Approved',NULL,1,2),('snippet-not-yet-acknowledged',1,1,'Snippet - Not yet acknowledged',NULL,'not yet acknowledged',NULL,1,1),('snippet-not-yet-rated',1,13,'Snippet - not yet rated',NULL,'Not yet rated',NULL,1,2),('snippet-note-about-approval-update-bulk',1,13,'Snippet - Note about setting approved/unapproved on bulk update',NULL,'Note:<br/>Setting assets unapproved will add them to their configured approval workflow.<br/>Setting assets approved will remove them from any existing workflow.',NULL,1,2),('snippet-note-about-approval-update-set',1,13,'Snippet - Note about setting approved',NULL,'Approving this asset will remove it from its approval workflow.',NULL,1,2),('snippet-note-about-approval-update-unset',1,13,'Snippet - Note about setting unapproved',NULL,'Unapproving this asset will add the asset to the configured workflow (and it will appear under Approve Items for approvers).',NULL,1,2),('snippet-note-about-force-unapproved',1,13,'Snippet - Note about forcing unapproved',NULL,'Check to add the #item# to the approval workflow.',NULL,1,2),('snippet-note-cancel-batch',1,13,'Snippet - Note this will cancel current batch',NULL,'<p>Please note: this will cancel your current batch update.</p>',NULL,1,1),('snippet-notes-for',1,13,'Snippet - Notes for',NULL,'Notes for',NULL,1,2),('snippet-now',1,13,'Snippet - now',NULL,'now',NULL,1,2),('snippet-num-items-incomplete-metadata',1,13,'Snippet - Num uploaded items incomplete metadata',NULL,'?numIncompleteItems? of your uploaded #items# have incomplete metadata',NULL,1,2),('snippet-number-items-shown',1,13,'Snippet - Note about max assets number on approval list',NULL,'<strong>Note</strong>: for performance reasons only ?numberAssets? #item#s are shown. More will be shown as you process the #item#s below.',NULL,1,2),('snippet-number-uploaded-items',1,13,'Snippet - Number of uploaded items to approve',NULL,'There are ?totalSize? #item#s in total',NULL,1,2),('snippet-number-uploaded-items2',1,13,'Snippet - Number of uploaded items to approve part 2',NULL,', of which ?returnSize? are shown.',NULL,1,2),('snippet-of',1,13,'Snippet - of',NULL,'of',NULL,1,3),('snippet-of-selected-cats',1,13,'Snippet - of selected cats',NULL,'of the selected #category-nodes#',NULL,1,3),('snippet-of-selected-cats-als',1,13,'Snippet - Of selected categories/access levels',NULL,'of the selected categories/access levels',NULL,1,2),('snippet-only-id-shown',1,13,'Snippet - only id attribute can be shown',NULL,'(Please note that only the ID attribute can be shown with your currently selected page layout.)',NULL,1,3),('snippet-op-after',1,13,'Snippet - Operator: after',NULL,'after',NULL,1,2),('snippet-op-before',1,13,'Snippet - Operator: before',NULL,'before',NULL,1,2),('snippet-op-contains',1,13,'Snippet - Operator: contains',NULL,'contains word',NULL,1,2),('snippet-op-equal-to',1,13,'Snippet - Operator: equal to',NULL,'equal to',NULL,1,2),('snippet-op-less-than',1,13,'Snippet - Operator: less than',NULL,'less than',NULL,1,2),('snippet-op-more-than',1,13,'Snippet - Operator: more than',NULL,'more than',NULL,1,2),('snippet-op-not-contains',1,13,'Snippet - Operator: doesn\'t contain',NULL,'doesn\'t contain word',NULL,1,2),('snippet-op-not-equal-to',1,13,'Snippet - Operator: not equal to',NULL,'not equal to',NULL,1,2),('snippet-optional',1,13,'Snippet - optional',NULL,'optional',NULL,1,2),('snippet-original',1,13,'Snippet - original',NULL,'original',NULL,1,2),('snippet-originals',1,13,'Snippet - originals',NULL,'originals',NULL,1,2),('snippet-other',1,13,'Snippet - Other',NULL,'other',NULL,1,2),('snippet-oustanding-acknowledgements-intro',1,13,'Snippet - Batch Release outstanding acknowledgements report intro',NULL,'The report below details all the batch releases with outstanding acknowledgements and the users that still need to acknowledge the release. You can filter the report by the batch release owner or by the acknowledgement recipient below:',NULL,1,2),('snippet-outstanding-acknowledgement-total',1,13,'Snippet - Outstanding acknowledgement',NULL,'<p>There is <a href=\"viewBatchReleaseAcknowledgementReport\">?totalCount? outstanding acknowledgement</a> overall.</p>',NULL,1,1),('snippet-outstanding-acknowledgement-user',1,13,'Snippet - Outstanding acknowledgement for user',NULL,'<p>There is <a href=\"viewBatchReleaseAcknowledgementReport?userId=?userId?\">?userCount? outstanding acknowledgement</a> for batch releases you have released.</p>',NULL,1,1),('snippet-outstanding-acknowledgements-description',1,13,'Snippet - Outstanding acknowledgements description',NULL,'A report showing a list of all oustanding acknowledgments for each #batch-release#. Optionally filtered to specific batch release owners and acknowledgement recipients.',NULL,1,3),('snippet-outstanding-acknowledgements-total',1,13,'Snippet - Outstanding acknowledgements',NULL,'<p>There are <a href=\"viewBatchReleaseAcknowledgementReport\">?totalCount? outstanding acknowledgements</a> overall.</p>',NULL,1,1),('snippet-outstanding-acknowledgements-user',1,13,'Snippet - Outstanding acknowledgements for user',NULL,'<p>There are <a href=\"viewBatchReleaseAcknowledgementReport?userId=?userId?\">?userCount? outstanding acknowledgements</a> for batch releases you have released.</p>',NULL,1,1),('snippet-page-break',1,13,'Snippet - Page Break',NULL,'Page Break',NULL,1,2),('snippet-page-x-of-y',1,13,'Snippet - Page x of y',NULL,'Page ?currentPage? of ?numPages?',NULL,1,2),('snippet-parent',1,13,'Snippet - Parent',NULL,'parent',NULL,1,2),('snippet-parents',1,13,'Snippet - Parents',NULL,'parents',NULL,1,2),('snippet-partial-approval',1,13,'Snippet - Partial Approval',NULL,'Partially approved',NULL,1,2),('snippet-password-hidden',1,13,'Snippet - Password hidden',NULL,'Password hidden for security reasons',NULL,1,2),('snippet-password-reminder-failed',1,13,'Snippet - Failed password reminder',NULL,'Sorry, we could not find a user matching the details you entered. Please try again.',NULL,1,1),('snippet-password-reminder-failed-ldap-user',1,13,'Snippet - Failed password reminder LDAP user',NULL,'Your password cannot be changed using Asset Bank because your authentication is handled by an LDAP server. Please contact your IT department to find out how to change your username and password.',NULL,1,1),('snippet-pending',1,13,'Snippet - Pending',NULL,'Pending',NULL,1,2),('snippet-performing-data-lookup',1,13,'Snippet - Performing Data Lookup',NULL,'performing data lookup',NULL,1,1),('snippet-please-select',1,13,'Snippet - Please select',NULL,'Please Select',NULL,1,2),('snippet-please-wait-file',1,13,'Snippet - Please wait while file processed',NULL,'<p>Please wait while your file is processed.<br />If there is no further activity then please check that the value in the File field represents a valid file.</p>',NULL,1,1),('snippet-please-wait-update',1,13,'Snippet - Please wait while changes are saved',NULL,'<p>Please wait while your changes are saved. If you are adding a large file this may take a few minutes.<br />If there is no further activity then please check that the value in the File field is empty or represents a valid file.</p>',NULL,1,1),('snippet-please-wait-upload',1,13,'Snippet - Please wait while file uploaded',NULL,'<p>Please wait while your file is uploaded. If you are uploading a large file this may take a few minutes.<br />If there is no further activity then please check that the value in the File field represents a valid file and click upload again.</p>',NULL,1,1),('snippet-portrait',1,13,'Snippet - Portrait',NULL,'Portrait',NULL,1,2),('snippet-preparing-download',1,13,'Snippet - Preparing download...',NULL,'Preparing download...',NULL,1,3),('snippet-prepend',1,13,'Snippet - prepend (dropdown)',NULL,'Prepend',NULL,1,1),('snippet-preview-being-generated',1,13,'Snippet - Preview being generated',NULL,'The preview for this video is currently being generated.',NULL,1,2),('snippet-preview-clip',1,13,'Snippet - preview clip',NULL,'preview clip',NULL,1,2),('snippet-processing-item-for-download',1,13,'Snippet - Processing item for download',NULL,'Processing #item# for download',NULL,1,3),('snippet-progress',1,13,'Snippet - Progress',NULL,'Progress...',NULL,1,2),('snippet-provide-sensitivity-notes',1,13,'Snippet - Provide sensitivity notes',NULL,'Please provide reasons below',NULL,1,2),('snippet-rating',1,13,'Snippet - Rating',NULL,'Rating',NULL,1,2),('snippet-ratings',1,13,'Snippet - Ratings',NULL,'Ratings',NULL,1,2),('snippet-refreshes-subscriptions',1,13,'Snippet - refreshes subscriptions',NULL,'(Refreshes available subscriptions)',NULL,1,2),('snippet-register-tsandcs',1,13,'Snippet - Register page terms & conditions link',NULL,'I agree to comply with the <a href=\"../action/viewConditionsPopup?extra=true\" class=\"help-popup\" target=\"_blank\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a> of the use of #company-name#.',NULL,1,1),('snippet-rejected-for-high-res',1,13,'Snippet - Rejected for high-res',NULL,'Rejected for high-res download',NULL,1,2),('snippet-related-asset-lead-in',1,13,'Snippet - Related asset lead in',NULL,'This #item# has',NULL,1,2),('snippet-related-items',1,13,'Snippet - Has related items',NULL,'This #item# has <a href=\"#related\" title=\"go to related #items#\">related #items#</a>.',NULL,1,1),('snippet-related-latest-version',1,13,'Snippet - Related latest version',NULL,'<p>Related #item#s are only shown for the most recent version of this #item#.</p>',NULL,1,1),('snippet-relationship-name-child',1,13,'Snippet - Child',NULL,'Child',NULL,1,2),('snippet-relationship-name-peer',1,13,'Snippet - Peer',NULL,'Peer',NULL,1,2),('snippet-relationship-to-asset',1,13,'Snippet - Relationship to asset',NULL,'?relationshipType? relationship to asset',NULL,1,2),('snippet-relationships-limit',1,13,'Snippet - Relationship Limit',NULL,'There are too many results to use the select all functionality.',NULL,1,2),('snippet-released',1,13,'Snippet - Released',NULL,'Released',NULL,1,2),('snippet-remove',1,13,'Snippet - remove (dropdown)',NULL,'Remove',NULL,1,2),('snippet-remove-from-lightbox',1,13,'Snippet - Remove from lightbox',NULL,'Remove from #a-lightbox#',NULL,1,2),('snippet-remove-selected-title-attr',1,13,'Snippet - Remove selected title attr',NULL,'Remove the selected #items# from this #a-lightbox#',NULL,1,3),('snippet-replace',1,13,'Snippet - replace (dropdown)',NULL,'Replace',NULL,1,1),('snippet-report-in-progress',1,13,'Snippet - Report generation in progress',NULL,'Generating Report...',NULL,1,2),('snippet-repurposed-audio-clip-intro',1,13,'Snippet - embeddable audio clip intro',NULL,'',NULL,1,2),('snippet-repurposed-audio-clips-intro',1,13,'Snippet - embeddable audio clips intro',NULL,'Below are embeddable versions that have already been created for this audio file. You may reuse any of these versions if they are appropriate, or if not (for example if the duration needs to change) you may <a href=\"?url?\">create your own version</a>.',NULL,1,2),('snippet-repurposed-image-intro',1,13,'Snippet - embeddable image',NULL,'Your version has been successfully created and is shown below. This version will remain accessible until the original item is removed, expired or made restricted.',NULL,1,2),('snippet-repurposed-images-intro',1,13,'Snippet - embeddable images intro',NULL,'Below are embeddable versions that have already been created for this image. You may reuse any of these versions if they are appropriate, or if not you may <a href=\"?url?\">create your own version</a>.',NULL,1,2),('snippet-repurposed-video-intro',1,13,'Snippet - embeddable video intro',NULL,'',NULL,1,2),('snippet-repurposed-videos-intro',1,13,'Snippet - embeddable videos intro',NULL,'Below are embeddable versions that have already been created for this video. You may reuse (and resize) any of these versions if they are appropriate, or if not (for example if the duration needs to change) you may <a href=\"?url?\">create your own version</a>.',NULL,1,2),('snippet-req-fields',1,13,'Snippet - Complete required fields',NULL,'<em>Please complete all fields marked with an asterisk (<span class=\"required\">*</span>).</em>',NULL,1,1),('snippet-req-for-completeness-fields',1,13,'Snippet - Fields required for completeness',NULL,'<em>Those fields marked with a dagger (<span class=\"required\">&dagger;</span>) are additionally required for the data to be considered complete.</em>',NULL,1,1),('snippet-request-approved-on',1,13,'Snippet - request approved on',NULL,'Your request was approved on',NULL,1,3),('snippet-request-dl-add-lightbox',1,13,'Snippet - Request permission DL add lightbox',NULL,'You can request permission to download this #item# by adding it to your #a-lightbox#.',NULL,1,3),('snippet-request-dl-via-lightbox',1,13,'Snippet - Request permission DL via lightbox',NULL,'You can request permission to download this #item# from your <a href=\"../action/viewAssetBox\">#a-lightbox#</a>',NULL,1,3),('snippet-request-high-res-download',1,13,'Snippet - Request high-res download',NULL,'',NULL,1,2),('snippet-request-high-res-view',1,13,'Snippet - Request high-res view',NULL,'You may download a low-res version of this image but will have to request approval for a high-res version',NULL,1,2),('snippet-request-instructions',1,13,'Snippet - Request instructions',NULL,'Please include as much detail as possible, including the desired file format.',NULL,1,1),('snippet-request-not-found',1,13,'Snippet - request not found',NULL,'Sorry, the request could not be found. It is possible that it has been deleted recently',NULL,1,1),('snippet-request-rejected-on',1,13,'Snippet - request rejected on',NULL,'Your request was rejected on',NULL,1,3),('snippet-restricted',1,13,'Snippet - Restricted',NULL,'Restricted',NULL,1,2),('snippet-resume-editing-source-file',1,13,'Snippet - Resume editing source file',NULL,'Resume editing',NULL,1,3),('snippet-retract-vote',1,13,'Snippet - Retract vote',NULL,'Retract vote &raquo;',NULL,1,2),('snippet-review',1,13,'Snippet - Review',NULL,'Review',NULL,1,2),('snippet-reviews',1,13,'Snippet - Reviews',NULL,'Reviews',NULL,1,2),('snippet-root',1,13,'Snippet - Root',NULL,'Root',NULL,1,2),('snippet-save-as-new-version',1,13,'Snippet - Save as new version',NULL,'Save as new version',NULL,1,2),('snippet-save-changes',1,13,'Snippet - Save changes',NULL,'Save changes',NULL,1,2),('snippet-save-changes-option',1,13,'Snippet - Save changes option',NULL,'Save changes',NULL,1,2),('snippet-save-most-recent-search',1,13,'Snippet - save most recent search',NULL,'Save your most recent search...',NULL,1,2),('snippet-save-new-version-option',1,13,'Snippet - Save new version option',NULL,'Save as a new version',NULL,1,2),('snippet-save-search',1,13,'Snippet - save search',NULL,'Save search',NULL,1,2),('snippet-save-search-warning',1,13,'Snippet - save search warning',NULL,'Note, if this search is saved with the same name as an existing saved search, the existing saved search will be replaced.',NULL,1,2),('snippet-save-searches',1,13,'Snippet - save searches',NULL,'Save search',NULL,1,2),('snippet-saved-search-slots',1,13,'Snippet - save search slots',NULL,'You can save up to ?savedSearchSlots? more searches.',NULL,1,2),('snippet-search-builder-query-changed',1,13,'Snippet - Search builder query changed',NULL,'When searching using a date or numeric value, the clauses from this value onwards in the query must use the \"AND\" operator. The query has been changed to reflect this.',NULL,1,2),('snippet-search-results',1,13,'Snippet - Search Results',NULL,'Search Results',NULL,1,3),('snippet-second',1,13,'Snippet - Second',NULL,'second',NULL,1,2),('snippet-secondary-usage-intro',1,13,'Snippet - Secondary usage intro',NULL,'Please specify any further intended uses for this asset:',NULL,1,3),('snippet-secondary-usage-note',1,13,'Snippet - Secondary usage note',NULL,'<p><strong>Please note:</strong> the sizes available for your chosen intended usage may not be appropriate for other uses.</p>',NULL,1,1),('snippet-seconds',1,13,'Snippet - Seconds',NULL,'seconds',NULL,1,2),('snippet-select',1,13,'Snippet - Select',NULL,'Select',NULL,1,2),('snippet-select-file-type',1,13,'Snippet - Select file type',NULL,'The file extension for this file is used by more than one format - please select the format from the list below.',NULL,1,2),('snippet-select-ppt-template-info',1,13,'Snippet - select ppt template info',NULL,'Select the template you want to use. The images in your lightbox will be inserted one per slide into this template to create your presentation, which you will then be able to download.',NULL,1,2),('snippet-separate-multiple-emails',1,13,'Snippet - Separate multiple emails',NULL,'Separate multiple email addresses with a comma.',NULL,1,2),('snippet-separate-with-semicolon',1,13,'Snippet - Seperate addresses with ;',NULL,'(Separate addresses with \';\')',NULL,1,2),('snippet-share-lb-other-users',1,13,'Snippet - share lightbox with users',NULL,'<p>Share #a-lightbox# <b>?lightboxName?</b> with other users</p>',NULL,1,1),('snippet-show',1,13,'Snippet - Show',NULL,'Show',NULL,1,2),('snippet-show-attribute-labels',1,13,'Snippet - Show attribute labels',NULL,'Show attribute labels:',NULL,1,2),('snippet-show-items-in',1,13,'Snippet - Show items that are in',NULL,'Show #items# that are in',NULL,1,3),('snippet-show-on-public-tab',1,13,'Snippet - Show on Public Tab',NULL,'If checked, this lightbox will be shown on the Other Users\' Lightboxes tab.',NULL,1,1),('snippet-shows-most-recent-modified-public-lightboxes',1,13,'Snippet - Shows Most Recent Modified Public Lightboxes',NULL,'This page shows the most recently modified lightboxes that are visible to all users.',NULL,1,1),('snippet-simple-indesign-include-lightbox-contents',1,13,'Snippet - Include Lightbox contents',NULL,'This will download all the #items# in your #a-lightbox# to your working directory for use in the InDesign document',NULL,1,3),('snippet-simple-indesign-resolved-conflicts',1,13,'Snippet - Resolved Conflicts',NULL,'Note: You have already resolved duplicate #items# for this document',NULL,1,3),('snippet-single-flash-upload',1,13,'Snippet - Single file flash upload',NULL,'First, upload your file using the controls below, then click Next to enter the metadata for this item.',NULL,1,3),('snippet-single-flash-upload-types',1,13,'Snippet - Single file flash upload with types',NULL,'First, upload your file using the controls below, then select a type and click Next to enter the metadata for this item.',NULL,1,3),('snippet-skip',1,13,'Snippet - skip (dropdown)',NULL,'Skip',NULL,1,1),('snippet-slideshow-not-found',1,13,'Snippet - Slideshow not found',NULL,'The requested slideshow could not be found (it may have been deleted recently).',NULL,1,2),('snippet-slideshows-hopepage',1,13,'Snippet - slideshows',NULL,'Slideshows:',NULL,1,2),('snippet-smaller-then-actual-size',1,13,'Snippet - smaller than actual size',NULL,'Shown smaller than actual size.',NULL,1,2),('snippet-so-items-available',1,13,'Snippet - so items can be updated',NULL,'(so that the #items# can be updated by other users).',NULL,1,3),('snippet-source-file',1,13,'Snippet - Source file',NULL,'Source file:',NULL,1,3),('snippet-source-file-checked-out-to',1,13,'Snippet - Source file already checked out to',NULL,'File checked out to ?forename? ?surname? for editing.',NULL,1,3),('snippet-square',1,13,'Snippet - Square',NULL,'Square',NULL,1,2),('snippet-standard',1,13,'Snippet - standard',NULL,'Standard',NULL,1,2),('snippet-step-1',1,13,'Snippet - Step 1',NULL,'Step 1:',NULL,1,2),('snippet-step-2',1,13,'Snippet - Step 2',NULL,'Step 2:',NULL,1,2),('snippet-step-3',1,13,'Snippet - Step 3',NULL,'Step 3:',NULL,1,2),('snippet-submit-asset-feedback-confirmation',1,13,'Snippet - Thank you for submitting your feedback.',NULL,'Thank you for submitting your feedback.',NULL,1,2),('snippet-submit-asset-feedback-request',1,13,'Snippet - Use the form below to submit your feedback.',NULL,'Use the form below to submit your feedback',NULL,1,2),('snippet-submit-option-approval',1,13,'Snippet - Submit for approval',NULL,'Submit for approval',NULL,1,2),('snippet-submit-option-live',1,13,'Snippet - Submit to live',NULL,'Submit to live',NULL,1,2),('snippet-submit-option-live-if-permitted',1,13,'Snippet - Submit to live if permissions allow',NULL,'(if permissions allow)',NULL,1,2),('snippet-submit-option-unsubmitted',1,13,'Snippet - Do not submit yet',NULL,'Do not submit yet',NULL,1,2),('snippet-submitted',1,13,'Snippet - Submitted',NULL,'Submitted',NULL,1,2),('snippet-text-rtl',1,13,'Snippet - Text right-to-left',NULL,'Display text right-to-left?',NULL,1,2),('snippet-thank-you-for-reactivating',1,13,'Snippet - Thank you for reactivating',NULL,'Thank you for reactivating your account',NULL,1,2),('snippet-this',1,13,'Snippet - this',NULL,'this',NULL,1,2),('snippet-this-br-contains',1,13,'Snippet This batch release contains',NULL,'This #batch-release# contains',NULL,1,3),('snippet-thumbnail-bulk-update',1,13,'Snippet - thumbnail on bulk update',NULL,'(thumbnail will only be applied to non-image assets)',NULL,1,2),('snippet-too-many-related-assets-to-show',1,13,'Snippet - text shown when there are too many related items to show on the View Asset page',NULL,'<p>Not all related #items# have been shown, as there are too many. If you want to see all of them, please run a search.</p>',NULL,1,1),('snippet-top-level-1-file',1,13,'Snippet - Top-level (1 file)',NULL,'Top-level (?numFiles? file)',NULL,1,3),('snippet-top-level-x-files',1,13,'Snippet - Top-level (X files)',NULL,'Top-level (?numFiles? files)',NULL,1,3),('snippet-unapproved',1,13,'Snippet - Unapproved',NULL,'Unapproved',NULL,1,2),('snippet-uncompressed',1,13,'Snippet - Uncompressed',NULL,'uncompressed',NULL,1,2),('snippet-unrestricted',1,13,'Snippet - Unrestricted',NULL,'Unrestricted',NULL,1,2),('snippet-unsubmitted-asset',1,1,'Snippet - You have an unsubmitted asset',NULL,'You have <strong>1</strong> unsubmitted #item#.',NULL,1,2),('snippet-unsubmitted-assets',1,1,'Snippet - You have some unsubmitted assets',NULL,'You have <strong>?lNumUnsubmittedAssets?</strong> unsubmitted #items#.',NULL,1,2),('snippet-upload-browser',1,13,'Snippet - Upload with browser',NULL,'Upload files with your browser',NULL,1,2),('snippet-upload-ftp',1,13,'Snippet - Upload with ftp',NULL,'Upload files with FTP',NULL,1,2),('snippet-upload-large-file',1,13,'Snippet - upload a large file',NULL,'(to upload a large file you may use the ?link? instead)',NULL,1,2),('snippet-upload-no-flash',1,13,'Snippet - Upload No Flash',NULL,'<strong>Error:</strong> You need to have the <a href=\"http://get.adobe.com/flashplayer/\" target=\"_blank\">Adobe Flash Plugin</a> installed and javascript enabled for the uploader to work.',NULL,1,1),('snippet-upload-outdated-flash',1,13,'Snippet - Upload Outdated Flash',NULL,'<strong>Error:</strong> The Flash Uploader will not work with the version of Flash Player you have installed, as your Asset Bank is also configured to support Integrated Windows Authentication. Please upgrade your version of Flash Player.',NULL,1,2),('snippet-upload-success-unsubmitted',1,1,'Snippet - Upload Success page unsubmitted asset',NULL,'Your #item# has not yet been submitted.',NULL,1,2),('snippet-uploaded-not-submitted',1,13,'Snippet - Uploaded items not submitted',NULL,'your uploaded items were not submitted',NULL,1,2),('snippet-uploading',1,13,'Snippet - Uploading...',NULL,'Uploading...',NULL,1,2),('snippet-us-state-cant-be-null',1,13,'snippet-us-state-cant-be-null',NULL,'Please enter a value for US State',NULL,1,2),('snippet-use-alternative-upload',1,13,'Snippet - use alternative upload',NULL,'(for larger files you may use the <a href=\"?url?\">flash/java uploader</a> instead)',NULL,1,1),('snippet-use-simple-upload',1,13,'Snippet - use simple upload',NULL,'(for smaller files you may use the <a href=\"?url?\">simple upload form</a> instead)',NULL,1,2),('snippet-user-expiry-date',1,13,'Snippet - User expiry date',NULL,'Please note that your login will expire on [0]<br>',NULL,1,1),('snippet-username-in-use',1,13,'Snippet - Username in use',NULL,'Your ?remoteDirectoryName? username is already taken in this application - please contact an administrator to resolve this issue.',NULL,1,2),('snippet-users-items-approval',1,13,'Snippet - Users submitted items for approval',NULL,'<p>The following users have submitted #items# for download approval:</p>',NULL,1,1),('snippet-valid',1,13,'Snippet - Valid',NULL,'Valid',NULL,1,2),('snippet-version-created-on',1,13,'Snippet - Version created on',NULL,'Version created on',NULL,1,2),('snippet-versioning',1,13,'Snippet - Versioning',NULL,'Versioning',NULL,1,2),('snippet-video-changes-page-only',1,13,'Snippet - video changes page only',NULL,'Note, changes to the size will only affect this page, and will not affect the way this video appears elsewhere.',NULL,1,2),('snippet-video-conversion-error',1,13,'Snippet - video conversion error',NULL,'An error occurred during your video conversion. If you specified the frame rate and/or audio sampling rate you may have entered values that are incompatible with the output file format.',NULL,1,2),('snippet-video-conversion-finished',1,13,'Snippet - Video conversion finished',NULL,'Your video conversion has finished',NULL,1,2),('snippet-view',1,13,'Snippet - View',NULL,'View',NULL,1,2),('snippet-view-as-slideshow',1,13,'Snippet - View as slideshow',NULL,'View as slideshow',NULL,1,2),('snippet-view-only-permission',1,13,'Snippet - view only permission',NULL,'',NULL,1,3),('snippet-view-preview',1,13,'Snippet - view preview',NULL,'view preview',NULL,1,2),('snippet-view-selected-slideshow-title-attr',1,13,'Snippet - View selected as slideshow title attr',NULL,'View the selected #items# as a slideshow',NULL,1,3),('snippet-view-source-file',1,13,'Snippet - View source file',NULL,'View source file',NULL,1,3),('snippet-view-source-file-in',1,13,'Snippet - View source file in',NULL,'View in ?cseEditorName?',NULL,1,3),('snippet-view-your-unsubmitted',1,13,'Snippet - View your unsubmitted items',NULL,'View your unsubmitted items',NULL,1,2),('snippet-views',1,13,'Snippet - Views',NULL,'Views',NULL,1,2),('snippet-vote',1,13,'Snippet - Vote',NULL,'Vote',NULL,1,2),('snippet-vote-for-this-item',1,13,'Snippet - Vote for this item',NULL,'Vote for this #item# &raquo;',NULL,1,2),('snippet-votes',1,13,'Snippet - Votes',NULL,'Votes',NULL,1,2),('snippet-with-attributes',1,13,'Snippet - with attributes',NULL,'with attributes',NULL,1,2),('snippet-without-attributes',1,13,'Snippet - without attributes',NULL,'without attributes',NULL,1,2),('snippet-workflow-init',1,13,'Snippet - Workflow Initialisation',NULL,'<p>The asset was added to access levels associated with the following workflows, please select a submit option for each workflow and click the Save button:</p>',NULL,1,1),('snippet-workflow-items-await-review',1,13,'Snippet - Workflow review screen - these items are currently awaiting approval',NULL,'These #items# are currently awaiting approval.',NULL,1,3),('snippet-workflow-removed',1,13,'Snippet - Workflow Removed',NULL,'You have removed one or more access levels from the asset you just saved. This resulted in the asset being removed from workflows associated with the access levels in question.',NULL,1,1),('snippet-x-items-checked-out',1,13,'Snippet - x items checked out',NULL,'You have <a href=\"../action/viewCheckedOutAssets\">?numCheckedOutAssets? #items#</a> checked out for editing.',NULL,1,3),('snippet-x-live-items-incomplete',1,13,'Snippet -x live items incomplete',NULL,'?numIncompleteItems? of the #items# you uploaded are live but have incomplete metadata.',NULL,1,3),('snippet-yes',1,13,'Snippet - Yes',NULL,'Yes',NULL,1,2),('snippet-you-have',1,13,'Snippet - You have',NULL,'You have',NULL,1,2),('snippet-your-name',1,13,'Snippet - Your Name',NULL,'[your name]',NULL,1,1),('snippet-your-notes',1,13,'Snippet - Your approval notes',NULL,'You added the following notes about intended usage for this image:',NULL,1,3),('snippet-zip-noperm-advanced',1,13,'Snippet - Zip no perm advanced',NULL,'<p><em>Please note: for some images you do not have permission to perform an advanced conversion, hence this option will not be available for this batch.</em></p>',NULL,1,1),('snippet-zip-noperm-original',1,13,'Snippet - Zip no perm dl original',NULL,'<p><em>Please note: for some images you do not have permission to download the original, hence this option will not be available for this batch.</em></p>',NULL,1,1),('snippet-zip-perm-items',1,13,'Snippet - Zip only include permissable items',NULL,'<p><em>Please note: the zip file will only include items you have permission to download.</em></p>',NULL,1,1),('sortAttributeNotSelected',1,15,'sortAttributeNotSelected',NULL,'You need to select the attribute that you want to sort by.',NULL,1,2),('sso-enc-url-failed-copy',1,1,'Encrypted Url SSO Login Failed Copy','Encrypted Url SSO Login Failed Copy','<p>Your login attempt failed, this could be because the link you used has expired. Please return to your referrer, refresh the page that you came from and try the link again.</p><p>If the link still fails, please contact <a href=\"mailto:?email?\">?email?</a>.</p><p>Alternatively if you have credentials you can <a href=\"viewLogin\">login directly</a>.</p>',NULL,1,1),('strong-password-guidance',1,15,'strong-password-guidance',NULL,'Please note that the password:<ul><li>Must contain at least one number, one capital letter and be a minimum of 8 characters long.</li><li>Should be easy to remember.</li><li>Should not be based on anything someone else could easily guess or obtain using easily known information.</li><li>Should be confidential and it is your responsibility keeping that status.</li></ul>',NULL,1,2),('subcategory-root',1,8,'Term for subcategory',NULL,'Subcategory',NULL,1,2),('subhead-acknowledgements',1,10,'Subheading - Acknowledgements',NULL,'Acknowledgements',NULL,1,2),('subhead-acknowledgements-matrix',1,10,'Subheading - Acknowledgements Matrix',NULL,'Acknowledgements Matrix',NULL,1,2),('subhead-acknowledgements-summary',1,10,'Subheading - Acknowledgements Summary',NULL,'Acknowledgements Summary',NULL,1,2),('subhead-actions',1,10,'Subheading - Actions',NULL,'Actions',NULL,1,2),('subhead-adv-options',1,10,'Subhead - Advanced Options',NULL,'Advanced Options',NULL,1,2),('subhead-all-checked-out-items',1,10,'Subheading - All assets checked out by any user:',NULL,'All #items# checked out by any user',NULL,1,2),('subhead-all-checked-out-items-empty',1,10,'Subheading - There are no checked out assets',NULL,'There are no checked out #items#',NULL,1,2),('subhead-all-uploaded-complete-metadata',1,10,'Subhead - All uploaded complete metadata',NULL,'All your uploaded #items# have complete metadata',NULL,1,2),('subhead-already-updated',1,10,'Subhead - Already updated',NULL,'Already updated',NULL,1,2),('subhead-approval-rejected',1,10,'Subhead - Approval rejected',NULL,'Approval rejected',NULL,1,2),('subhead-approval-required',1,10,'Subhead - Approval required',NULL,'Approval required',NULL,1,2),('subhead-approval-users',1,10,'Subhead - Approval Users',NULL,'Approval Users',NULL,1,2),('subhead-approve-items-download',1,10,'Subhead - Approve items for download',NULL,'Approve #items# for download',NULL,1,2),('subhead-approve-uploaded-items',1,10,'Subhead - Approve uploaded items',NULL,'Approve uploaded #items#',NULL,1,2),('subhead-asset-approval-messages',1,10,'Label - Asset approval messages',NULL,'Asset approval messages',NULL,1,2),('subhead-asset-relationships',1,10,'Subhead - Asset Relationships',NULL,'#item# Relationships',NULL,1,2),('subhead-associated-assets',1,10,'Subhead - Associated assets',NULL,'Associated assets',NULL,1,2),('subhead-audit-info',1,10,'Subhead - Audit Info',NULL,'Audit Information for &quot;?assetTitle?&quot;',NULL,1,2),('subhead-batch-release-asset-delete',1,10,'Subheading - Batch release asset delete',NULL,'Warning, this may affect #batch-releases#',NULL,1,2),('subhead-batch-releases-not-released',1,10,'Subheading - Batch releases not released',NULL,'#batch-releases# that have yet to be released:',NULL,1,2),('subhead-batch-releases-released',1,10,'Subheading - Batch releases released',NULL,'#batch-releases# that have been released over the past ?days? days:',NULL,1,2),('subhead-batch-update',1,10,'Subheading - Batch Update',NULL,'Update a group of assets, one at a time',NULL,1,3),('subhead-bulk-update',1,10,'Subheading - Bulk Update',NULL,'Update a group of assets, all at once',NULL,1,3),('subhead-bulk-upload-complete',1,10,'Subheading - Bulk upload complete',NULL,'Bulk upload completed',NULL,1,2),('subhead-bulk-upload-log',1,10,'Subhead - Bulk upload log',NULL,'Bulk upload log:',NULL,1,2),('subhead-change-approval-status',1,10,'Subhead - Change Approval Status',NULL,'Change Approval Status',NULL,1,2),('subhead-change-description',1,10,'Subheading - Change description',NULL,'Change description',NULL,1,2),('subhead-check-out-date',1,10,'Subheading - Check-Out Date',NULL,'Check-Out Date',NULL,1,2),('subhead-choose-page-layout',1,10,'Subheading - Choose page layout',NULL,'Choose page layout:',NULL,1,2),('subhead-convert',1,10,'Subhead - Convert',NULL,'Convert',NULL,1,2),('subhead-copy-agreement',1,10,'Subhead - Copy agreement',NULL,'Copy Agreement',NULL,1,2),('subhead-crop',1,10,'Subhead - Crop',NULL,'Crop',NULL,1,2),('subhead-current-filter',1,10,'Subhead - Current filter',NULL,'Current filter:',NULL,1,2),('subhead-date-of-upload',1,10,'Subhead - Date of upload',NULL,'Date of upload',NULL,1,2),('subhead-define-find-replace',1,10,'Subhead - Define Find and Replace',NULL,'Define a Find and Replace',NULL,1,2),('subhead-dl-lightbox-zip',1,10,'Subhead - dl lightbox as zip file',NULL,'Download items in your #a-lightbox# as a compressed zip file',NULL,1,2),('subhead-download-child-assets',1,10,'Subhead - download child assets',NULL,'Download child assets as a compressed zip file',NULL,1,2),('subhead-download-convert-images',1,10,'Subhead -Download &amp; Convert Images',NULL,'Download &amp; Convert Images',NULL,1,2),('subhead-download-file',1,10,'Subhead - Download File',NULL,'Download File',NULL,1,2),('subhead-download-original',1,10,'Subhead - Download Original',NULL,'Download Original',NULL,1,2),('subhead-download-originals',1,10,'Subhead - Download Originals',NULL,'Download Originals',NULL,1,2),('subhead-edit-agreement',1,10,'Subhead - Edit agreement',NULL,'Edit Agreement',NULL,1,2),('subhead-embedded-metadata',1,10,'Subhead - Embedded Metadata',NULL,'Embedded Metadata',NULL,1,2),('subhead-enter-metadata',1,10,'Subhead - Enter Metadata',NULL,'Enter Metadata',NULL,1,2),('subhead-error-log',1,10,'Subheading - Error log',NULL,'Error log',NULL,1,2),('subhead-file-check-out',1,10,'Subheading - File Check-Out',NULL,'File Check-Out',NULL,1,2),('subhead-find-users',1,10,'Subhead - Find Users',NULL,'Find Users',NULL,1,2),('subhead-id',1,10,'Subheading - Id',NULL,'Id',NULL,1,2),('subhead-image-ready-download',1,10,'Subhead - Your image ready to download',NULL,'Your image is ready to download!',NULL,1,2),('subhead-import-log',1,10,'Subhead - Import Log',NULL,'Import Log:',NULL,1,2),('subhead-item-in-cat',1,10,'Subhead - One Item in Category',NULL,'?browseHits? #item# in ?catName?',NULL,1,2),('subhead-items-in-cat',1,10,'Subhead - Multiple Items in Category',NULL,'?browseHits? #items# in ?catName?',NULL,1,2),('subhead-log',1,10,'Subhead - Log',NULL,'Log:',NULL,1,2),('subhead-manage-agreements',1,10,'Subhead - Manage agreements',NULL,'Manage Agreements',NULL,1,2),('subhead-mask',1,10,'Subhead - Mask',NULL,'Mask',NULL,1,2),('subhead-my-checked-out-items',1,10,'Subheading - All assets checked out by you:',NULL,'All #items# checked out by you',NULL,1,2),('subhead-my-checked-out-items-empty',1,10,'Subheading - You do not have any checked out assets',NULL,'You do not have any checked out #items#',NULL,1,2),('subhead-my-purchase',1,10,'Subhead - My Purchase',NULL,'My Purchase',NULL,1,2),('subhead-name',1,10,'Subheading - Name',NULL,'Name',NULL,1,2),('subhead-no-items-found',1,10,'Subhead - No items found',NULL,'No #items# found',NULL,1,2),('subhead-no-of-items',1,10,'Subhead - No. of items',NULL,'No. of items',NULL,1,2),('subhead-not-yet-updated',1,10,'Subhead - Not yet updated',NULL,'Not yet updated',NULL,1,2),('subhead-num-placeholders',1,10,'Subhead - Number of Empty Assets',NULL,'Number of Empty Assets',NULL,1,2),('subhead-num-uploaded-items',1,10,'Subhead - Num uploaded items',NULL,'?numUploadedItems? #items# have been uploaded',NULL,1,2),('subhead-options',1,10,'Subheading - Options',NULL,'Options',NULL,1,2),('subhead-pending-approval',1,10,'Subhead - Pending approval',NULL,'Pending approval',NULL,1,2),('subhead-please-wait-image-prepared',1,10,'Subhead - Please wait while image prepared',NULL,'Please wait while your image is prepared...',NULL,1,2),('subhead-prev-added',1,10,'Subhead - Previously added',NULL,'Previously added',NULL,1,2),('subhead-promoted-items',1,10,'Subheading - Promoted Items',NULL,'Promoted #items#',NULL,1,2),('subhead-promoted-listed-below',1,10,'Subhead - Promoted items listed below',NULL,'There are ?numPromotedItems? promoted #items# listed below',NULL,1,3),('subhead-purchase-rejected',1,10,'Subhead - Purchase rejected',NULL,'Purchase rejected',NULL,1,2),('subhead-purchase-required',1,10,'Subhead - Purchase required',NULL,'Purchase required',NULL,1,2),('subhead-ready-download',1,10,'Subhead - Ready to Download',NULL,'Ready to download',NULL,1,2),('subhead-recent-listed-below',1,10,'Subhead - Recent items listed below',NULL,'There are ?numRecentItems? recently added #items# listed below',NULL,1,3),('subhead-recently-added',1,10,'Subheading - Recently added',NULL,'Recently added',NULL,1,2),('subhead-resize',1,10,'Subhead - Resize',NULL,'Resize',NULL,1,2),('subhead-search-by',1,10,'Subhead - Search by',NULL,'Search by',NULL,1,2),('subhead-select-attributes-display',1,10,'Subheading - Select attributes to display',NULL,'Select attributes to display',NULL,1,2),('subhead-select-attributes-find-replace',1,10,'Subhead - Select attributes for Find and Replace',NULL,'Select the text attributes to be searched across',NULL,1,2),('subhead-subcats-cats',1,10,'Subhead - Subcategories of categories',NULL,'Sub?browseablesName? of ?catName?',NULL,1,2),('subhead-submitted-items',1,10,'Subheading - Items not yet approved',NULL,'Items not yet approved',NULL,1,2),('subhead-tint',1,10,'Subhead - Tint',NULL,'Tint',NULL,1,2),('subhead-total-outstanding-acknowledgments',1,10,'Subheading - Total outstanding acknowledgments',NULL,'Total # of outstanding acknowledgements',NULL,1,2),('subhead-update-actions',1,10,'Subhead - Update Actions',NULL,'Update Actions',NULL,1,2),('subhead-upload-file',1,10,'Subhead - Upload file',NULL,'Upload file',NULL,1,2),('subhead-upload-files',1,10,'Subhead - Upload files',NULL,'Upload files',NULL,1,2),('subhead-upload-help',1,10,'Subhead - Upload Help',NULL,'Upload Help',NULL,1,2),('subhead-uploading-complete',1,1,'Subhead - Uploading complete',NULL,'Uploading complete',NULL,1,2),('subhead-uploading-please-wait',1,10,'Subhead - Uploading (please wait)',NULL,'Uploading (please wait)',NULL,1,2),('subhead-usage-info',1,10,'Subhead - Usage Info',NULL,'Usage Information for &quot;?assetTitle?&quot;',NULL,1,2),('subhead-view-only',1,10,'Subhead - View only',NULL,'View only',NULL,1,2),('subhead-your-approved-items',1,10,'Subhead - Your Approved Items',NULL,'Your Live Items',NULL,1,2),('subhead-your-details',1,10,'Subhead - Your details',NULL,'Your details:',NULL,1,2),('subInvalidCountry',1,15,'subInvalidCountry',NULL,'You do not have a valid country in your address - please update your profile.',NULL,1,2),('subInvalidDate',1,15,'subInvalidDate',NULL,'Please enter a valid start date, either today or in the future.',NULL,1,2),('subInvalidEmailAddress',1,15,'subInvalidEmailAddress',NULL,'You do not have a valid email address - please update your profile.',NULL,1,2),('subInvalidModel',1,15,'subInvalidModel',NULL,'Please select a subscription model.',NULL,1,2),('subInvalidTaxLookup',1,15,'subInvalidTaxLookup',NULL,'Tax details could not be found for your home country. Please contact us to resolve this.',NULL,1,2),('subInvalidTsandcs',1,15,'subInvalidTsandcs',NULL,'You must accept the conditions of the subscription to continue.',NULL,1,2),('submit-feedback-comment',1,1,'Submit Feedback Comment',NULL,'Please enter a subject and comment to complete your review.',NULL,1,1),('submit-feedback-main',1,1,'Submit Feedback Intro',NULL,'Please select your rating by clicking on one of the stars below, and then enter a subject and comment to complete your review.',NULL,1,1),('submit-feedback-rating',1,1,'Submit Feedback Rating',NULL,'Please select your rating by clicking on one of the stars below.',NULL,1,1),('submitOption',1,15,'submitOption',NULL,'You need to select a submit option for this asset',NULL,1,2),('submitOptionAllWorkflows',1,15,'submitOptionAllWorkflows',NULL,'You need to provide submit options for all workflows when submitting an asset',NULL,1,2),('subModelInvalidDuration',1,15,'subModelInvalidDuration',NULL,'Please enter a valid number for duration.',NULL,1,2),('subModelInvalidNumDownloads',1,15,'subModelInvalidNumDownloads',NULL,'Please enter a valid number for number of downloads.',NULL,1,2),('subModelInvalidPrice',1,15,'subModelInvalidPrice',NULL,'Please enter a valid price.',NULL,1,2),('subNoDownloadsLeft',1,15,'subNoDownloadsLeft',NULL,'You do not have enough downloads left on your subscription! Click the \'Subscriptions\' link to manage your subscriptions.',NULL,1,2),('successfulAddedAssetsToAssetBox',1,15,'successfulAddedAssetsToAssetBox',NULL,'[0] asset(s) were successfully added to the #a-lightbox#',NULL,1,2),('sys-asset-stale-info',1,15,'System - Asset Stale info',NULL,'The asset has changed since the page was loaded - please try again.',NULL,1,2),('sys-error-duplicate-filename',1,15,'System - Error Duplicate Filename',NULL,'The system already contains a file named \'[0]\'. Please rename the new file to load and try again.',NULL,1,2),('tab-advanced-download',1,7,'Tab - Advanced Download',NULL,'Advanced Download',NULL,1,2),('tab-advanced-search',1,7,'Tab - Advanced Search',NULL,'Advanced Search',NULL,1,2),('tab-all-checked-out',1,7,'Tab - All Checked Out Items',NULL,'All Checked Out #Items#',NULL,1,2),('tab-alphabetical',1,7,'Tab - Alphabetical',NULL,'Alphabetical',NULL,1,2),('tab-approve-downloads',1,7,'Tab - Approve downloads',NULL,'Downloads',NULL,1,2),('tab-approve-edits',1,7,'Tab - Approve edits',NULL,'Edits',NULL,1,2),('tab-approve-uploads',1,7,'Tab - Approve uploads',NULL,'Uploads',NULL,1,2),('tab-approved-items',1,7,'Tab - Approved Items',NULL,'Approved Items',NULL,1,2),('tab-awaiting-approval',1,7,'Tab - Awaiting Approval',NULL,'Awaiting Approval',NULL,1,2),('tab-batch-release-search',1,7,'Tab - Batch Release Search',NULL,'#batch-release# Search',NULL,1,2),('tab-batch-update',1,7,'Tabs - Batch Update',NULL,'Batch Update',NULL,1,2),('tab-browse-cats',1,7,'Tabs - Browse Categories',NULL,'Browse Categories',NULL,1,2),('tab-browse-keywords',1,7,'Tabs - Browse Keywords',NULL,'Browse Keywords',NULL,1,2),('tab-browse-popularity',1,7,'Tab - Browse by Popularity',NULL,'Browse by Popularity',NULL,1,2),('tab-bulk-update',1,7,'Tabs - Bulk Update',NULL,'Bulk Update',NULL,1,2),('tab-current-lightbox',1,7,'Tab - Current Lightbox',NULL,'Current Lightbox',NULL,1,2),('tab-download',1,7,'Tab - Download',NULL,'Download',NULL,1,2),('tab-hierarchical',1,7,'Tab - Hierarchical',NULL,'Hierarchical',NULL,1,2),('tab-keyword-list',1,7,'Tab - Keyword list',NULL,'Keyword list',NULL,1,2),('tab-large-image-view',1,7,'Tab - Large image view',NULL,'Large image view',NULL,1,2),('tab-live',1,7,'Tab - Live',NULL,'Live',NULL,1,2),('tab-manage-lightboxes',1,7,'Tab - Manage Lightboxex',NULL,'Manage Lightboxes',NULL,1,2),('tab-messages-archive',1,7,'Tab - Messages Archive',NULL,'Archive',NULL,1,2),('tab-messages-current',1,7,'Tab - Messages Current',NULL,'Current',NULL,1,2),('tab-metadata-import',1,7,'Tabs - Metadata Import',NULL,'Metadata Import',NULL,1,2),('tab-my-checked-out',1,7,'Tab - My Checked Out Items',NULL,'My Checked Out #Items#',NULL,1,2),('tab-normal-view',1,7,'Tab - Normal view',NULL,'Normal view',NULL,1,2),('tab-outstanding-acknowledgements',1,7,'Tab - Outstanding Acknowledgements',NULL,'Outstanding Acknowledgements',NULL,1,2),('tab-public-lightboxes',1,7,'Tab - Public Lightboxes',NULL,'Other Users\' Lightboxes',NULL,1,2),('tab-quick-add',1,7,'Tab - Quick add',NULL,'Quick add',NULL,1,2),('tab-quick-download',1,7,'Tab - Quick Download',NULL,'Quick Download',NULL,1,2),('tab-recent-searches',1,7,'Tab - Recent Searches',NULL,'Recent Searches',NULL,1,2),('tab-reports',1,7,'Tab - Reports',NULL,'Reports',NULL,1,2),('tab-saved-searches',1,7,'Tab - Saved Searches',NULL,'Saved Searches',NULL,1,2),('taxInvalidPercent',1,15,'taxInvalidPercent',NULL,'Please enter a valid number for tax percent.',NULL,1,2),('technical-embeddable-audio-html',1,17,'Embeddable audio HTML snippet','This is a snippet of html that may be provided for embedding an audio clip. The snippet should include the variable: ?url?','<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"300\" height=\"24\"><param name=\"movie\" value=\"?flvPlayerUrl?/player.swf\" /><param name=\"flashvars\" value=\"file=?url?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=none&provider=video&duration=?duration?\"><param name=\"scale\" value=\"default\" /><param name=\"bgcolor\" value=\"111111\" /><!--[if !IE]>--><object type=\"application/x-shockwave-flash\" data=\"?flvPlayerUrl?/player.swf?file=?url?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=none&provider=video&duration=?duration?\" width=\"300\" height=\"24\"><!--<![endif]--><p>Your browser does not have the necessary plugin to display this content.</p><!--[if !IE]>--></object><!--<![endif]--></object>',NULL,1,3),('technical-embeddable-image-html',1,17,'Embeddable image HTML snippet','This is a snippet of html that may be provided for embedding an image. The snippet should include the variable: ?url?, and may also include ?height? and/or ?width?','<img src=\"?url?\" height=\"?height?\" width=\"?width?\"/>',NULL,1,3),('technical-embeddable-slideshow-download-html-footer',1,17,'Embeddable slideshow download HTML footer','This is a snippet of html used as a footer for an example html file on download of an embeddable slideshow','</body></html>',NULL,1,3),('technical-embeddable-slideshow-download-html-header',1,17,'Embeddable slideshow download HTML header','This is a snippet of html used as a header for an example html file on download of an embeddable slideshow','<html><head><title>Embedded Slideshow Example</title></head><body>',NULL,1,3),('technical-embeddable-slideshow-html',1,17,'Embeddable slideshow HTML snippet','This is a snippet of html that may be provided for embedding a slideshow. The snippet should include the variable: ?url?','<script type=\"text/javascript\" src=\"?commonUrlStub?swfobject.js\"></script><div id=\"ssflashcontent\">The slideshow requires JavaScript and the Flash Player. <a href=\"http://www.macromedia.com/go/getflashplayer/\">Get Flash here.</a></div><script type=\"text/javascript\">var flashvars = {data:\"?urlStub??url?\", displayTime:\"?displayTime?\", onExternalSite:\"?onExternalSite?\", textDirection: \"?rtl?\", type:\"?type?\", modules:\"AssetBank\", modulePath:\"?commonUrlStub?modules/\"}; if (flashvars.type==\"photoEssay\") { flashvars.controlMenuOptions = \"AssetBank_viewAsset,AssetBank_addToLightbox,AssetBank_addAllToLightbox,thumbnailBar,titleBar,infoBar,autoResize,embiggen\"; flashvars.title = \"?title?\";}; var params = {wmode:\"transparent\",allowFullScreen:\"true\"}; var attributes = {}; swfobject.embedSWF(\"?commonUrlStub?gallery.swf?latest\", \"ssflashcontent\", \"?width?\", \"?height?\", \"9.0.0\", \"?commonUrlStub?expressinstall.swf\", flashvars, params, attributes); </script>',NULL,1,3),('technical-embeddable-video-html',1,17,'Embeddable video HTML snippet','This is a snippet of html that may be provided for embedding a video. The snippet should include the variable: ?url?, and may also include ?height? and/or ?width?','<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"?width?\" height=\"?height?\"><param name=\"movie\" value=\"?flvPlayerUrl?/player.swf\" /><param name=\"flashvars\" value=\"file=?url?&image=?previewFileLocation?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=uniform&provider=video&duration=?duration?\"><param name=\"allowfullscreen\" value=\"true\" /><param name=\"scale\" value=\"default\" /><param name=\"bgcolor\" value=\"111111\" /><!--[if !IE]>--><object type=\"application/x-shockwave-flash\" data=\"?flvPlayerUrl?/player.swf?file=?url?&image=?previewFileLocation?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=uniform&provider=video&duration=?duration?\" width=\"?width?\" height=\"?height?\"><param name=\"allowfullscreen\" value=\"true\" /><param name=\"scale\" value=\"default\" /><param name=\"bgcolor\" value=\"111111\" /><!--<![endif]--><p>Your browser does not have the necessary plugin to display this content.</p><!--[if !IE]>--></object><!--<![endif]--></object>',NULL,1,3),('term-for-default-language',1,8,'Term for Default Language',NULL,'English',NULL,1,2),('thank-you-for-approving',1,1,'Thank you for approving text',NULL,'Thank you for approving this user. Their details have been sent to an admin user for final approval.',NULL,1,3),('thank-you-for-rejecting',1,1,'Thank you for rejecting text',NULL,'Thank you for rejecting this user. Their details have been removed from the system.',NULL,1,3),('thumbnailFileNotImage',1,15,'thumbnailFileNotImage',NULL,'The thumbnail file is not in a valid format. Please upload an image file, for example JPEG, GIF, PNG.',NULL,1,2),('thumbnailsNotYetGenerated',1,15,'thumbnailsNotYetGenerated',NULL,'You cannot download a converted version of this image at present as its thumbnails have not yet been generated. Please try again later.',NULL,1,2),('title-about',1,16,'Title - About',NULL,'#company-name# | #heading-about#',NULL,1,2),('title-acknowledgements-matrix',1,16,'Title - Acknowledgements Matrix',NULL,'#company-name# | #subhead-acknowledgements-matrix#',NULL,1,2),('title-add-asset-to-batch-release',1,16,'Title - Add Asset to Batch Release',NULL,'#company-name# | #heading-add-asset-to-batch-release#',NULL,1,2),('title-add-copy-to-batch-release',1,16,'Title - Add Copy to Batch Release',NULL,'#company-name# | #heading-add-copy-to-batch-release#',NULL,1,2),('title-add-empty-related-assets',1,16,'Title - Add empty related assets',NULL,'#company-name# | #heading-add-empty-related-assets#',NULL,1,2),('title-agreements',1,16,'Title - Agreements',NULL,'#company-name# | #heading-agreements#',NULL,1,2),('title-all-cats',1,16,'Title - All categories',NULL,'#company-name# | #heading-all-cats#',NULL,1,2),('title-all-requests',1,16,'Title - All requests',NULL,'#company-name# | #heading-all-requests#',NULL,1,2),('title-approval-notes',1,16,'Title - Approval Notes',NULL,'#company-name# | #heading-approval-notes#',NULL,1,2),('title-approve-items',1,16,'Title - Approve Items',NULL,'#company-name# | #heading-approve-items#',NULL,1,2),('title-asset-details',1,16,'Title - Asset Details',NULL,'#company-name# | #heading-asset-details# - ?assetName?',NULL,1,2),('title-asset-file-editor',1,16,'Title - Asset File Editor',NULL,'Asset File Editor',NULL,1,2),('title-asset-versions',1,16,'Title - Asset Versions',NULL,'#company-name# | #heading-asset-versions#',NULL,1,2),('title-assign-a-fulfiller',1,16,'Title - Assign a fulfiller',NULL,'#company-name# | #heading-assign-a-fulfiller#',NULL,1,2),('title-audio-conversion-status',1,16,'Title - Audio Conversion Status',NULL,'Audio Conversion Status',NULL,1,2),('title-audio-details',1,16,'Title - Audio Details',NULL,'#company-name# | #heading-audio-details# - ?assetName?',NULL,1,2),('title-audit-info',1,16,'Title - Usage info',NULL,'#company-name# | View Audit Information',NULL,1,2),('title-batch-release-search',1,16,'Title - Batch release search',NULL,'#company-name# | #heading-batch-release-search#',NULL,1,2),('title-batch-update',1,16,'Title - Batch Update',NULL,'#company-name# | #heading-batch-update#',NULL,1,2),('title-batch-update-finished',1,16,'Title - Batch Update Finished',NULL,'#company-name# | #heading-batch-update-finished#',NULL,1,2),('title-br-outstanding-acknowledgements-report',1,16,'Title - Batch Release Outstanding Acknowledgements Report',NULL,'#company-name# | #heading-br-outstanding-acknowledgements-report#',NULL,1,2),('title-browse',1,16,'Title - Browse',NULL,'#company-name# | #heading-browse# ?categoryName?',NULL,1,2),('title-browse-by-popularity',1,16,'Title - Browse by popularity',NULL,'#company-name# | #tab-browse-popularity#',NULL,1,2),('title-bulk-delete-confirm',1,16,'Title - Bulk delete confirm',NULL,'#company-name# | #heading-bulk-delete-confirm#',NULL,1,2),('title-bulk-find-replace',1,16,'Title - Bulk Find and Replace',NULL,'#company-name# | #heading-bulk-find-replace#',NULL,1,2),('title-bulk-update',1,16,'Title - Bulk Update',NULL,'#company-name# | #heading-bulk-update#',NULL,1,2),('title-bulk-upload',1,16,'Title - Bulk Upload',NULL,'#company-name# | #heading-bulk-upload#',NULL,1,2),('title-cancel-checkout',1,16,'Title - Cancel Checkout',NULL,'Cancel Checkout',NULL,1,2),('title-checked-out-items',1,16,'Title - Checked Out Items',NULL,'#company-name# | #heading-checked-out-items#',NULL,1,2),('title-compare-images',1,16,'Title - Compare images',NULL,'#company-name# | #heading-compare-images#',NULL,1,2),('title-conditions',1,1,'Title - Conditions',NULL,'#company-name# | Terms and Conditions',NULL,1,2),('title-contact',1,16,'Title - Contact',NULL,'#company-name# | #heading-contact#',NULL,1,2),('title-contact-sheet',1,16,'Title - Contact Sheet',NULL,'#company-name# | #heading-contact-sheet#',NULL,1,2),('title-content',1,16,'Title - Content',NULL,'#company-name# | ?extraContentPageName?',NULL,1,2),('title-copy-asset-step1',1,16,'Title - Copy asset step 1',NULL,'#company-name# | #heading-copy-asset-step1#',NULL,1,2),('title-copy-lightbox',1,16,'Title - Copy Lightbox',NULL,'#company-name# | Copy #a-lightbox#',NULL,1,2),('title-copy-move',1,16,'Title - Copy or Move Assets',NULL,'#company-name# | Copy or Move #items#',NULL,1,2),('title-create-copy',1,16,'Title - Create copy',NULL,'#company-name# | #heading-create-copy#',NULL,1,2),('title-create-doc-from-brand-template',1,16,'Title - Create Document From Brand Template',NULL,'#company-name# | #heading-create-doc-from-brand-template#',NULL,1,2),('title-create-new-password',1,16,'Title - Create New Password',NULL,'#company-name# | #heading-create-new-password#',NULL,1,2),('title-default-related-assets',1,16,'Title - Default related-assets',NULL,'#company-name# | #heading-default-related-assets#',NULL,1,2),('title-delete-asset',1,16,'Title - Delete Asset',NULL,'#company-name# | #heading-delete-asset#',NULL,1,2),('title-download-audio',1,16,'Title - Download Audio',NULL,'#company-name# | #heading-download-audio#',NULL,1,2),('title-download-child-assets',1,16,'Title - download child assets',NULL,'#company-name# | #heading-download-child-assets#',NULL,1,2),('title-download-failure',1,16,'Title - Download Failure',NULL,'#company-name# | #heading-download-failure#',NULL,1,2),('title-download-file',1,16,'Title - Download File',NULL,'#company-name# | #heading-download-file#',NULL,1,2),('title-download-image',1,16,'Title - Download Image',NULL,'#company-name# | #heading-download-image#',NULL,1,2),('title-download-lightbox',1,16,'Title - Download Lightbox',NULL,'#company-name# | #heading-download# #a-lightbox#',NULL,1,2),('title-download-lightbox-result',1,16,'Title - Download Lightbox  Result',NULL,'#company-name# | #my-lightbox#',NULL,1,2),('title-download-video',1,16,'Title - Download Video',NULL,'#company-name# | #heading-download-video#',NULL,1,2),('title-edit-content-item',1,16,'Title - Edit Content Item',NULL,'#company-name# | Edit Content Item',NULL,1,2),('title-edit-lightbox',1,16,'Title - Edit Lightbox',NULL,'#company-name# | Edit #a-lightbox#',NULL,1,2),('title-edit-relationship',1,16,'Title - Edit Relationship',NULL,'#company-name# | #heading-edit-relationship#',NULL,1,2),('title-edit-relationships',1,16,'Title - Edit Relationships',NULL,'#company-name# | #heading-edit-relationships#',NULL,1,2),('title-email-failed',1,16,'Title - Email Failed',NULL,'#company-name# | #heading-email-failed#',NULL,1,2),('title-email-in-progress',1,16,'Title - Email in progress',NULL,'#company-name# | #heading-email-in-progress#',NULL,1,2),('title-email-item',1,16,'Title - Email item',NULL,'#company-name# | #heading-email-item#',NULL,1,2),('title-email-sent',1,16,'Title - Email sent',NULL,'#company-name# | #heading-email-sent#',NULL,1,2),('title-email-this-page',1,16,'Title - Email This Page',NULL,'#company-name# | #heading-email-this-page#',NULL,1,2),('title-embed-file',1,16,'Title - Embed File',NULL,'#company-name# | #heading-embed-file#',NULL,1,2),('title-file-transfer-step-1',1,16,'Title - File Transfer Step 1',NULL,'#company-name# | #heading-file-transfer-step-1#',NULL,1,2),('title-file-transfer-step-2',1,16,'Title - File Transfer Step 2',NULL,'#company-name# | #heading-file-transfer-step-2#',NULL,1,2),('title-forgotten-password',1,16,'Title - Forgotten Password',NULL,'#company-name# | #heading-forgotten-password#',NULL,1,2),('title-help',1,16,'Title - Help',NULL,'#company-name# | #heading-help#',NULL,1,2),('title-home-page',1,16,'Title - Home page',NULL,'#company-name# | Home Page',NULL,1,2),('title-image-details',1,16,'Title - Image Details',NULL,'#company-name# | #heading-image-details# - ?assetName?',NULL,1,2),('title-indesign-linked-assets',1,16,'title-indesign-linked-assets',NULL,'#company-name# | InDesign Linked Assets',NULL,1,2),('title-indesign-linking-assets',1,16,'title-indesign-linking-assets',NULL,'#company-name# | InDesign Linking indds',NULL,1,2),('title-initialise-workflow',1,16,'Title - Initialise Workflow',NULL,'#company-name# | #heading-initialise-workflow#',NULL,1,2),('title-keyword-chooser',1,16,'Title - Keyword Chooser',NULL,'#company-name# | #heading-keyword-chooser#',NULL,1,2),('title-keywords',1,16,'Title - Keywords',NULL,'#company-name# | #heading-keywords#',NULL,1,2),('title-least-downloaded',1,16,'Title - Least Downloaded',NULL,'#company-name# | #heading-least-downloaded#',NULL,1,2),('title-least-viewed',1,16,'Title - Least Viewed',NULL,'#company-name# | #heading-least-viewed#',NULL,1,2),('title-lightbox',1,16,'Title - Lightbox',NULL,'#company-name# | #a-lightbox#: ?lightBoxName?',NULL,1,2),('title-login-page',1,16,'Title - Login page',NULL,'#company-name# | #login-title#',NULL,1,2),('title-make-templated',1,16,'Title - Make Templated',NULL,'#company-name# | #heading-make-templated#',NULL,1,2),('title-manage-batch-releases',1,16,'Title - Manage Batch Releases',NULL,'#company-name# | #heading-manage-batch-releases#',NULL,1,2),('title-manage-content',1,16,'Title - Manage content',NULL,'#company-name# | Manage Content',NULL,1,2),('title-manage-lightboxes',1,16,'Title - Manage Lightboxes',NULL,'#company-name# | Manage #lightboxes#',NULL,1,2),('title-map-popup',1,16,'Title - Map Popup',NULL,'Map Popup',NULL,1,2),('title-marketing-email-assets',1,16,'Title - Marketing Email Assets',NULL,'Email Attachments',NULL,1,2),('title-most-downloaded',1,16,'Title - Most Downloaded',NULL,'#company-name# | #heading-most-downloaded#',NULL,1,2),('title-most-viewed',1,16,'Title - Most Viewed',NULL,'#company-name# | #heading-most-viewed#',NULL,1,2),('title-my-messages',1,16,'Title - My Messages',NULL,'#company-name# | #heading-my-messages#',NULL,1,2),('title-my-requests',1,16,'Title - My requests',NULL,'#company-name# | #heading-my-requests#',NULL,1,2),('title-my-uploads',1,16,'Title - My uploads',NULL,'#company-name# | #heading-my-uploads#',NULL,1,2),('title-not-found',1,16,'Title - Asset not found',NULL,'#company-name# | #heading-not-found#',NULL,1,2),('title-password-changed',1,16,'Title - Password Changed',NULL,'#company-name# | #heading-password-changed#',NULL,1,2),('title-preparing-file-wait',1,13,'Title - preparing file, please wait',NULL,'Preparing file, <span>please wait.</span>',NULL,1,2),('title-preview-file',1,16,'Title - Preview File',NULL,'#company-name# | Preview File',NULL,1,2),('title-preview-image',1,16,'Title - Preview Image',NULL,'#company-name# | #heading-download-image-preview#',NULL,1,2),('title-print-image',1,16,'Title - Print Image',NULL,'#company-name# | #heading-print-image#',NULL,1,2),('title-profile-changed',1,16,'Title - Profile Changed',NULL,'#company-name# | #heading-profile-changed#',NULL,1,2),('title-promoted-images',1,16,'Title - Promoted Images',NULL,'#company-name# | #heading-promoted#',NULL,1,2),('title-public-lightboxes',1,16,'Title - Public Lightboxes',NULL,'#company-name# | Public #lightboxes#',NULL,1,2),('title-recent-images',1,16,'Title - Recent Images',NULL,'#company-name# | #heading-recent#',NULL,1,2),('title-recent-searches',1,16,'Title - Recent Searches',NULL,'#company-name# | #heading-recent-searches#',NULL,1,2),('title-register',1,16,'Title - Register',NULL,'#company-name# | #heading-register#',NULL,1,2),('title-reindex',1,16,'Title - Reindex',NULL,'#company-name# | #heading-reindex#',NULL,1,2),('title-rename-lightbox',1,16,'Title - Rename Lightbox',NULL,'#company-name# | Rename #a-lightbox#',NULL,1,2),('title-repurpose-slideshow',1,16,'Title - Create Embeddable Slideshow',NULL,'#company-name# | #heading-repurpose-slideshow#',NULL,1,2),('title-repurposed-audio-clip',1,16,'Title - Embeddable Audio Clip',NULL,'#company-name# | #heading-repurposed-audio-clip#',NULL,1,2),('title-repurposed-audio-clips',1,16,'Title - Embeddable Audio Clips',NULL,'#company-name# | #heading-repurposed-audio-clips#',NULL,1,2),('title-repurposed-image',1,16,'Title - Embeddable Image',NULL,'#company-name# | #heading-repurposed-image#',NULL,1,2),('title-repurposed-images',1,16,'Title - Embeddable Image Versions',NULL,'#company-name# | #heading-repurposed-image#',NULL,1,2),('title-repurposed-slideshow',1,16,'Title - Embeddable Slideshow',NULL,'#company-name# | #heading-repurposed-slideshow#',NULL,1,2),('title-repurposed-slideshows',1,16,'Title - Embeddable Slideshows',NULL,'#company-name# | #heading-repurposed-slideshows#',NULL,1,2),('title-repurposed-video',1,16,'Title - Embeddable Video',NULL,'#company-name# | #heading-repurposed-video#',NULL,1,2),('title-repurposed-videos',1,16,'Title - Embeddable Video Versions',NULL,'#company-name# | #heading-repurposed-videos#',NULL,1,2),('title-request-approval',1,16,'Title - Request Approval',NULL,'#company-name# | #heading-request-approval#',NULL,1,2),('title-request-cd',1,16,'Title - Request Cd',NULL,'#company-name# | #heading-request-cd#',NULL,1,2),('title-request-details',1,16,'Title - Request details',NULL,'#company-name# | #heading-request-details#',NULL,1,2),('title-save-search',1,16,'Title - Save Search',NULL,'#company-name# | #heading-save-search#',NULL,1,2),('title-saved-searches',1,16,'Title - Saved Searches',NULL,'#company-name# | #heading-saved-searches#',NULL,1,2),('title-search',1,16,'Title - Search',NULL,'#company-name# | #heading-search#',NULL,1,2),('title-search-results',1,16,'Title - Search Results',NULL,'#company-name# | #heading-search-results#',NULL,1,2),('title-security-questions',1,16,'Title - Security Questions',NULL,'#company-name# | #heading-security-questions#',NULL,1,2),('title-security-questions-changed',1,16,'Title - Security Questions',NULL,'#company-name# | #heading-security-questions-changed#',NULL,1,2),('title-send-e-card',1,16,'Title - Send e-card',NULL,'#company-name# | #heading-send-e-card#',NULL,1,2),('title-share-lightbox',1,16,'Title - Share Lightbox',NULL,'#company-name# | Share #a-lightbox#',NULL,1,2),('title-slideshow',1,16,'Title - Slideshow',NULL,'#company-name# | #heading-slideshow#',NULL,1,2),('title-start-import',1,16,'Title - Start Import',NULL,'#company-name# | #heading-start-import#',NULL,1,2),('title-step1-upload',1,16,'Title - Step 1 Upload',NULL,'#company-name#  | #heading-step-1-upload#',NULL,1,2),('title-submit-asset-feedback',1,16,'Title - Submit Feedback',NULL,'#company-name# | #heading-submit-asset-feedback#',NULL,1,2),('title-submit-feedback',1,16,'Title - Submit Feedback',NULL,'#company-name# | #heading-submit-feedback#',NULL,1,2),('title-update',1,16,'Title - Update',NULL,'#company-name# | #heading-update#',NULL,1,2),('title-upload',1,16,'Title - Upload',NULL,'#company-name# | #heading-upload#',NULL,1,2),('title-upload-success',1,16,'Title - Upload Success',NULL,'#company-name# | #heading-upload-success#',NULL,1,2),('title-usage-info',1,16,'Title - Usage info',NULL,'#company-name# | View Usage Information',NULL,1,2),('title-video-conversion-status',1,16,'Title - Video Conversion Status',NULL,'Video Conversion Status',NULL,1,2),('title-video-details',1,16,'Title - Video Details',NULL,'#company-name# | #heading-video-details# - ?assetName?',NULL,1,2),('title-your-password',1,16,'Title - Your Password',NULL,'#company-name# | #heading-your-password#',NULL,1,2),('title-your-profile',1,16,'Title - Your Profile',NULL,'#company-name# | #heading-your-profile#',NULL,1,2),('tooltip-disabled-link',1,19,'Tooltip - Disabled Link',NULL,'This option is currently unavailable',NULL,1,2),('tooltip-download-as-ppt',1,19,'Tooltip -  Download as PPT',NULL,'Each image will be added to the presentation on a new slide in the order they are shown in your lightbox. If your lightbox contains PowerPoint files then these will be merged into the presentation, although note that PPTX files cannot be merged into a PPT file and vice versa.',NULL,1,2),('tooltip-email-page',1,19,'Tooltip - Email this page',NULL,'Email this page',NULL,1,2),('tooltip-empty-lightbox',1,19,'Tooltip - Empty this lightbox',NULL,'Empty this #a-lightbox#',NULL,1,2),('tooltip-go-to-this-lightbox',1,19,'Tooltip -  Go to this lightbox',NULL,'Go to this #a-lightbox#',NULL,1,2),('tooltip-help-link',1,13,'Tooltip - Help link title attribute',NULL,'View help text in new window',NULL,1,2),('tooltip-search-help',1,19,'Tooltip - search help',NULL,'Click for search help in a new window',NULL,1,2),('tooltip-switch-compact-view',1,19,'Tooltip - Switch to compact view',NULL,'Switch to compact view',NULL,1,2),('tooltip-switch-lightbox',1,19,'Tooltip - Switch lightbox',NULL,'Switch #a-lightbox#',NULL,1,2),('tooltip-switch-list-view',1,19,'Tooltip - Switch to list view',NULL,'Switch to list view',NULL,1,2),('tooltip-switch-panel-view',1,19,'Tooltip - Switch to panel view',NULL,'Switch to panel view',NULL,1,2),('tooltip-view-asset-audit-popup',1,19,'Tootltip - View asset audit popup',NULL,'View asset audit in a new window',NULL,1,2),('tooltip-view-asset-popup',1,19,'Tooltip - View asset in popup',NULL,'View asset in popup window',NULL,1,2),('tooltip-view-asset-usage-popup',1,19,'Tootltip - View asset usage popup',NULL,'View asset usage in a new window',NULL,1,2),('tooltip-view-full-image-new-window',1,19,'Tooltip - View full image in a new window',NULL,'View full size image in a new window',NULL,1,2),('tooltip-view-image-new-window',1,19,'Tooltip - View image in a new window',NULL,'View image in a new window',NULL,1,2),('tooltip-workflow-audit-popup',1,19,'Tooltip - View workflow audit popup',NULL,'View asset workflow audit in a new window',NULL,1,3),('tsandcs-checkbox',1,1,'Ts and Cs checkbox text','The text shown to the right of the ts and cs checkbox on the download page','I have read and accept the <a href=\"../action/viewConditionsPopup\" target=\"_blank\" onClick=\"popupConditions(\'../action/viewConditionsPopup\', 1); return false;\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a>',NULL,1,1),('tsandcs-checkbox-upload',1,1,'Ts and Cs checkbox text (upload)','The text shown to the right of the ts and cs checkbox on the upload page','I have read and accept the <a href=\"../action/viewUploadConditionsPopup\" target=\"_blank\" onClick=\"popupConditions(\'../action/viewUploadConditionsPopup\', 1); return false;\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a> of uploading.',NULL,1,1),('tsandcs-file-checkbox',1,1,'Ts and Cs checkbox text for file downloads','The text shown to the right of the ts and cs checkbox on the download file page','I have read and accept the <a href=\"../action/viewFileConditionsPopup\" target=\"_blank\" onClick=\"popupConditions(\'../action/viewFileConditionsPopup\', 1); return false;\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a>',NULL,1,1),('tsandcs-image-checkbox',1,1,'Ts and Cs checkbox text for image downloads','The text shown to the right of the ts and cs checkbox on the download image page','I have read and accept the <a href=\"../action/viewImageConditionsPopup\" target=\"_blank\" onClick=\"popupConditions(\'../action/viewImageConditionsPopup\', 1); return false;\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a>',NULL,1,1),('tsandcs-shared-file-checkbox',1,1,'Ts and Cs checkbox text for shared file downloads',NULL,'I have read and accept the <a href=\"../action/viewSharedFileConditionsPopup\" target=\"_blank\" onClick=\"popupConditions(\'../action/viewSharedFileConditionsPopup\', 1); return false;\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a>',NULL,1,1),('tsandcs-video-checkbox',1,1,'Ts and Cs checkbox text for video downloads','The text shown to the right of the ts and cs checkbox on the download video page','I have read and accept the <a href=\"../action/viewVideoConditionsPopup\" target=\"_blank\" onClick=\"popupConditions(\'../action/viewVideoConditionsPopup\', 1); return false;\" title=\"View Terms and Conditions in a new window\">Terms and Conditions</a>',NULL,1,1),('update-profile',1,1,'Update profile form copy','The copy shown on the update profile page.','<p><strong>Security Update</strong></p><p>The security on this site has now been upgraded.<br/>Please complete the short form below to update your profile.</p>',NULL,1,1),('upload-conversion-warning',1,1,'Upload single asset - warning if conversion failed',NULL,'The preview thumbnails could not be generated, either because of a problem with the file or because of an application error. You can go ahead and add the file anyway, in which case it will be added as an #item# of type \'file\'. By default a generic icon will be used for the thumbnails, although you may want to use the \'Working file\' field below to upload a JPEG version of your file, which will then be used instead to generate the thumbnails.',NULL,1,1),('upload-success-parent',1,1,'Intro - Upload Success page for parent types',NULL,'<p>You may also add a ?childName? or multiple ?childrenName? to this ?typeName?.',NULL,1,1),('uploadedFileNotFoundError',1,15,'uploadedFileNotFoundError',NULL,'The uploaded file could not be found, as it has been deleted. This is likely to have happen because the time between uploading the file and submitting the metadata was too long.',NULL,1,2),('usage-label',1,1,'Label for the usage dropdown','The text shown above the usage dropdown, for example on the download page','Please select your intended usage for this asset:',NULL,1,1),('usage-selected-summary',1,1,'Selected usage summary','The text shown once a user has selected a usage, for example on the download page','You have selected usage:',NULL,1,1),('user-to-verify-with',1,1,'Supply details of user to verify with',NULL,'To register you must supply the details of an authorised person to verify you.',NULL,1,2),('userErrorInvalidOldPassword',1,15,'userErrorInvalidOldPassword',NULL,'The old password you entered is incorrect.',NULL,1,2),('userErrorPasswordNotificationFailed',1,15,'userErrorPasswordNotificationFailed',NULL,'Unable to send password notification - the email address may be invalid. The password has not been updated.',NULL,1,2),('video',1,8,'Term for video',NULL,'video',NULL,1,2),('video-conversion-advanced-copy',1,1,'Copy - Video Conversion Advanced Copy',NULL,'<em><p>You can use the following form to adjust the frame rate (fps) and audio sample rate (Hz) of the converted video.</p><p>In both cases, selecting a value higher than the input video\'s value will result in no change.</p><p>You can also select to download a particular chunk of the video by setting the offset start position and the duration you want to download.</p></em>',NULL,1,1),('view-file-copy',1,1,'Asset details copy (File)','Copy for file details page.','',NULL,1,1),('view-image-copy',1,1,'Asset details copy (Images)','Copy for image details page.','',NULL,1,1),('view-video-copy',1,1,'Asset details copy (Video)','Copy for video details page.','',NULL,1,1),('waitingQueuedDownloads',1,15,'waitingQueuedDownloads',NULL,'Waiting for queued downloads to complete. You are in the queue at position ',NULL,1,2),('waitingQueuedImports',1,15,'waitingQueuedImports',NULL,'Other imports are currently in progress. Your import has been queued and will be started as soon as possible. You are in the queue at position ',NULL,1,2),('workflowMessageMandatory',1,15,'workflowMessageMandatory',NULL,'Please enter a message when changing the state of an asset.',NULL,1,2),('you-are-here',1,8,'Start of Breadcrumb',NULL,'You are here:',NULL,1,2);
/*!40000 ALTER TABLE `listitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listitemtexttype`
--

DROP TABLE IF EXISTS `listitemtexttype`;
CREATE TABLE `listitemtexttype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `listitemtexttype`
--

LOCK TABLES `listitemtexttype` WRITE;
/*!40000 ALTER TABLE `listitemtexttype` DISABLE KEYS */;
INSERT INTO `listitemtexttype` VALUES (1,'Html'),(2,'Plain - Short'),(3,'Plain - Long');
/*!40000 ALTER TABLE `listitemtexttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logtype`
--

DROP TABLE IF EXISTS `logtype`;
CREATE TABLE `logtype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `logtype`
--

LOCK TABLES `logtype` WRITE;
/*!40000 ALTER TABLE `logtype` DISABLE KEYS */;
INSERT INTO `logtype` VALUES (1,'Modified'),(2,'Added'),(3,'Deleted'),(4,'Exported'),(5,'Status Change'),(6,'Agreement Change'),(7,'Added to Batch Release');
/*!40000 ALTER TABLE `logtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mappingdirection`
--

DROP TABLE IF EXISTS `mappingdirection`;
CREATE TABLE `mappingdirection` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(100) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mappingdirection`
--

LOCK TABLES `mappingdirection` WRITE;
/*!40000 ALTER TABLE `mappingdirection` DISABLE KEYS */;
INSERT INTO `mappingdirection` VALUES (1,'Upload'),(2,'Download');
/*!40000 ALTER TABLE `mappingdirection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marketingemail`
--

DROP TABLE IF EXISTS `marketingemail`;
CREATE TABLE `marketingemail` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  `Introduction` mediumtext,
  `TimeSent` datetime NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `marketingemail`
--

LOCK TABLES `marketingemail` WRITE;
/*!40000 ALTER TABLE `marketingemail` DISABLE KEYS */;
/*!40000 ALTER TABLE `marketingemail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marketinggroup`
--

DROP TABLE IF EXISTS `marketinggroup`;
CREATE TABLE `marketinggroup` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(60) NOT NULL,
  `Description` varchar(255) default NULL,
  `Purpose` varchar(255) default NULL,
  `IsHiddenInDefaultLanguage` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `marketinggroup`
--

LOCK TABLES `marketinggroup` WRITE;
/*!40000 ALTER TABLE `marketinggroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `marketinggroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mask`
--

DROP TABLE IF EXISTS `mask`;
CREATE TABLE `mask` (
  `Id` int(11) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  `Filename` varchar(255) default NULL,
  `Width` int(11) NOT NULL,
  `Height` int(11) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mask`
--

LOCK TABLES `mask` WRITE;
/*!40000 ALTER TABLE `mask` DISABLE KEYS */;
/*!40000 ALTER TABLE `mask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `Id` bigint(20) NOT NULL auto_increment,
  `TextId` varchar(60) NOT NULL,
  `TypeId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `SenderId` bigint(20) NOT NULL,
  `RecipientId` bigint(20) NOT NULL,
  `StatusId` int(11) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `DateModified` datetime default NULL,
  `Subject` varchar(255) default NULL,
  `Body` mediumtext,
  `AcknowledgeRequired` tinyint(1) NOT NULL,
  `RefEntityId` bigint(20) default NULL,
  `RefName` varchar(255) default NULL,
  `RefLink` varchar(255) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `Message_ToUser_FK` (`RecipientId`),
  KEY `Message_FromUser_FK` (`SenderId`),
  KEY `Message_MessTemplate_FK` (`TextId`,`TypeId`,`LanguageId`),
  KEY `Message_Status_FK` (`StatusId`),
  CONSTRAINT `Message_FromUser_FK` FOREIGN KEY (`SenderId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Message_MessTemplate_FK` FOREIGN KEY (`TextId`, `TypeId`, `LanguageId`) REFERENCES `messagetemplate` (`TextId`, `TypeId`, `LanguageId`),
  CONSTRAINT `Message_Status_FK` FOREIGN KEY (`StatusId`) REFERENCES `messagestatus` (`Id`),
  CONSTRAINT `Message_ToUser_FK` FOREIGN KEY (`RecipientId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagestatus`
--

DROP TABLE IF EXISTS `messagestatus`;
CREATE TABLE `messagestatus` (
  `Id` int(11) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `messagestatus`
--

LOCK TABLES `messagestatus` WRITE;
/*!40000 ALTER TABLE `messagestatus` DISABLE KEYS */;
INSERT INTO `messagestatus` VALUES (4,'ACKNOWLEDGED'),(5,'DELETED'),(3,'DISMISSED'),(1,'NEW'),(2,'READ');
/*!40000 ALTER TABLE `messagestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagetemplate`
--

DROP TABLE IF EXISTS `messagetemplate`;
CREATE TABLE `messagetemplate` (
  `TextId` varchar(60) NOT NULL,
  `TypeId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Code` varchar(60) NOT NULL,
  `Subject` varchar(255) default NULL,
  `Body` mediumtext,
  `Hidden` tinyint(1) NOT NULL,
  `isEnabled` tinyint(1) NOT NULL,
  `AcknowledgeRequired` tinyint(1) NOT NULL,
  PRIMARY KEY  (`TextId`,`TypeId`,`LanguageId`),
  KEY `MessTemplate_Lang_FK` (`LanguageId`),
  KEY `MessTemplate_Type_FK` (`TypeId`),
  CONSTRAINT `MessTemplate_Lang_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `MessTemplate_Type_FK` FOREIGN KEY (`TypeId`) REFERENCES `messagetemplatetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `messagetemplate`
--

LOCK TABLES `messagetemplate` WRITE;
/*!40000 ALTER TABLE `messagetemplate` DISABLE KEYS */;
INSERT INTO `messagetemplate` VALUES ('batch_release_acknowledge',2,1,'alert batch release requires acknowledgement','Batch release requires acknowledgement','<p>The batch release #name# has just been released and requires your acknowledgement.</p><p>Please indicate your acknowledgement of this batch release by checking the box below and clicking the Acknowledge button.</p>',0,1,1),('batch_release_alert_approvers',2,1,'alert batch release approvers','Batch release requires approval','The batch release #name# is ready for your approval. <a href=\'#referenceLink#\'>View details of the batch release</a> to approve it.',0,1,0);
/*!40000 ALTER TABLE `messagetemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagetemplatetype`
--

DROP TABLE IF EXISTS `messagetemplatetype`;
CREATE TABLE `messagetemplatetype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `messagetemplatetype`
--

LOCK TABLES `messagetemplatetype` WRITE;
/*!40000 ALTER TABLE `messagetemplatetype` DISABLE KEYS */;
INSERT INTO `messagetemplatetype` VALUES (2,'Batch Release Notification'),(1,'General');
/*!40000 ALTER TABLE `messagetemplatetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `namedcolour`
--

DROP TABLE IF EXISTS `namedcolour`;
CREATE TABLE `namedcolour` (
  `Id` int(11) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `Red` tinyint(3) unsigned NOT NULL,
  `Green` tinyint(3) unsigned NOT NULL,
  `Blue` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `namedcolour`
--

LOCK TABLES `namedcolour` WRITE;
/*!40000 ALTER TABLE `namedcolour` DISABLE KEYS */;
/*!40000 ALTER TABLE `namedcolour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newsitem`
--

DROP TABLE IF EXISTS `newsitem`;
CREATE TABLE `newsitem` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Title` varchar(255) NOT NULL,
  `Content` mediumtext NOT NULL,
  `DateCreated` datetime default NULL,
  `IsPublished` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `newsitem`
--

LOCK TABLES `newsitem` WRITE;
/*!40000 ALTER TABLE `newsitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `newsitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderstatus`
--

DROP TABLE IF EXISTS `orderstatus`;
CREATE TABLE `orderstatus` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  `OrderWorkflowId` bigint(20) NOT NULL,
  `ManualSelect` tinyint(1) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship141` (`OrderWorkflowId`),
  CONSTRAINT `Relationship141` FOREIGN KEY (`OrderWorkflowId`) REFERENCES `orderworkflow` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orderstatus`
--

LOCK TABLES `orderstatus` WRITE;
/*!40000 ALTER TABLE `orderstatus` DISABLE KEYS */;
INSERT INTO `orderstatus` VALUES (1,'Fulfilled',1,0),(2,'Awaiting Fulfillment',1,0),(3,'Requires Approval',2,1),(4,'Requires Online Payment',2,1),(5,'Requires Offline Payment',2,1),(6,'Paid For Online',2,0),(7,'Paid For Offline',2,1),(8,'Declined',2,1);
/*!40000 ALTER TABLE `orderstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderworkflow`
--

DROP TABLE IF EXISTS `orderworkflow`;
CREATE TABLE `orderworkflow` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(20) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orderworkflow`
--

LOCK TABLES `orderworkflow` WRITE;
/*!40000 ALTER TABLE `orderworkflow` DISABLE KEYS */;
INSERT INTO `orderworkflow` VALUES (1,'Personal'),(2,'Commercial');
/*!40000 ALTER TABLE `orderworkflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orgunit`
--

DROP TABLE IF EXISTS `orgunit`;
CREATE TABLE `orgunit` (
  `Id` bigint(20) NOT NULL auto_increment,
  `RootOrgUnitCategoryId` bigint(20) NOT NULL,
  `AdminGroupId` bigint(20) NOT NULL,
  `StandardGroupId` bigint(20) NOT NULL,
  `RootDescriptiveCategoryId` bigint(20) default NULL,
  `DiskQuotaMb` int(11) default NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `RootOrgUnitCategoryId` (`RootOrgUnitCategoryId`),
  KEY `IX_ORGUNIT_DESC_CAT_FK` (`RootDescriptiveCategoryId`),
  KEY `IX_Relationship73` (`AdminGroupId`),
  KEY `IX_Relationship81` (`StandardGroupId`),
  CONSTRAINT `ORGUNIT_DESC_CAT_FK` FOREIGN KEY (`RootDescriptiveCategoryId`) REFERENCES `cm_category` (`Id`),
  CONSTRAINT `Relationship73` FOREIGN KEY (`AdminGroupId`) REFERENCES `usergroup` (`Id`),
  CONSTRAINT `Relationship76` FOREIGN KEY (`RootOrgUnitCategoryId`) REFERENCES `cm_category` (`Id`),
  CONSTRAINT `Relationship81` FOREIGN KEY (`StandardGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orgunit`
--

LOCK TABLES `orgunit` WRITE;
/*!40000 ALTER TABLE `orgunit` DISABLE KEYS */;
/*!40000 ALTER TABLE `orgunit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orgunitcontent`
--

DROP TABLE IF EXISTS `orgunitcontent`;
CREATE TABLE `orgunitcontent` (
  `OrgUnitId` bigint(20) NOT NULL,
  `ContentPurposeId` bigint(20) NOT NULL,
  `ContentListItemIdentifier` varchar(200) NOT NULL,
  `MenuListItemIdentifier` varchar(200) default NULL,
  PRIMARY KEY  (`OrgUnitId`,`ContentListItemIdentifier`),
  KEY `IX_OrgUnit_OUC_FK` (`OrgUnitId`),
  KEY `IX_ContentPurpose_OUC_FK` (`ContentPurposeId`),
  CONSTRAINT `ContentPurpose_OUC_FK` FOREIGN KEY (`ContentPurposeId`) REFERENCES `contentpurpose` (`Id`),
  CONSTRAINT `OrgUnit_OUC_FK` FOREIGN KEY (`OrgUnitId`) REFERENCES `orgunit` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orgunitcontent`
--

LOCK TABLES `orgunitcontent` WRITE;
/*!40000 ALTER TABLE `orgunitcontent` DISABLE KEYS */;
/*!40000 ALTER TABLE `orgunitcontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orgunitforagreement`
--

DROP TABLE IF EXISTS `orgunitforagreement`;
CREATE TABLE `orgunitforagreement` (
  `AgreementId` bigint(20) NOT NULL,
  `OrgUnitId` bigint(20) NOT NULL,
  KEY `IX_Relationship275` (`OrgUnitId`),
  KEY `IX_Relationship274` (`AgreementId`),
  CONSTRAINT `Relationship274` FOREIGN KEY (`AgreementId`) REFERENCES `agreement` (`Id`),
  CONSTRAINT `Relationship275` FOREIGN KEY (`OrgUnitId`) REFERENCES `orgunit` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orgunitforagreement`
--

LOCK TABLES `orgunitforagreement` WRITE;
/*!40000 ALTER TABLE `orgunitforagreement` DISABLE KEYS */;
/*!40000 ALTER TABLE `orgunitforagreement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orgunitgroup`
--

DROP TABLE IF EXISTS `orgunitgroup`;
CREATE TABLE `orgunitgroup` (
  `UserGroupId` bigint(20) NOT NULL,
  `OrgUnitId` bigint(20) NOT NULL,
  PRIMARY KEY  (`UserGroupId`,`OrgUnitId`),
  KEY `IX_Relationship75` (`UserGroupId`),
  KEY `IX_Relationship74` (`OrgUnitId`),
  CONSTRAINT `Relationship74` FOREIGN KEY (`OrgUnitId`) REFERENCES `orgunit` (`Id`),
  CONSTRAINT `Relationship75` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orgunitgroup`
--

LOCK TABLES `orgunitgroup` WRITE;
/*!40000 ALTER TABLE `orgunitgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `orgunitgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `priceband`
--

DROP TABLE IF EXISTS `priceband`;
CREATE TABLE `priceband` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AssetTypeId` bigint(20) NOT NULL,
  `PriceBandTypeId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  `Description` varchar(255) default NULL,
  `BasePrice` int(11) default NULL,
  `UnitPrice` int(11) default NULL,
  `DownloadOriginal` tinyint(1) default NULL,
  `MaxQuantity` int(11) NOT NULL default '0',
  `IsCommercial` tinyint(1) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship223` (`AssetTypeId`),
  KEY `IX_Relationship128` (`PriceBandTypeId`),
  CONSTRAINT `Relationship128` FOREIGN KEY (`PriceBandTypeId`) REFERENCES `pricebandtype` (`Id`),
  CONSTRAINT `Relationship223` FOREIGN KEY (`AssetTypeId`) REFERENCES `assettype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `priceband`
--

LOCK TABLES `priceband` WRITE;
/*!40000 ALTER TABLE `priceband` DISABLE KEYS */;
/*!40000 ALTER TABLE `priceband` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pricebandtype`
--

DROP TABLE IF EXISTS `pricebandtype`;
CREATE TABLE `pricebandtype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pricebandtype`
--

LOCK TABLES `pricebandtype` WRITE;
/*!40000 ALTER TABLE `pricebandtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `pricebandtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pricebandusage`
--

DROP TABLE IF EXISTS `pricebandusage`;
CREATE TABLE `pricebandusage` (
  `UsageTypeId` bigint(20) NOT NULL,
  `PriceBandId` bigint(20) NOT NULL,
  PRIMARY KEY  (`UsageTypeId`,`PriceBandId`),
  KEY `IX_Relationship129` (`UsageTypeId`),
  KEY `IX_Relationship130` (`PriceBandId`),
  CONSTRAINT `Relationship129` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`),
  CONSTRAINT `Relationship130` FOREIGN KEY (`PriceBandId`) REFERENCES `priceband` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pricebandusage`
--

LOCK TABLES `pricebandusage` WRITE;
/*!40000 ALTER TABLE `pricebandusage` DISABLE KEYS */;
/*!40000 ALTER TABLE `pricebandusage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotedasset`
--

DROP TABLE IF EXISTS `promotedasset`;
CREATE TABLE `promotedasset` (
  `AssetId` bigint(20) NOT NULL,
  `DatePromoted` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`AssetId`),
  CONSTRAINT `Relationship104` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `promotedasset`
--

LOCK TABLES `promotedasset` WRITE;
/*!40000 ALTER TABLE `promotedasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotedasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishaction`
--

DROP TABLE IF EXISTS `publishaction`;
CREATE TABLE `publishaction` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `Path` varchar(255) NOT NULL,
  `RunDaily` tinyint(1) NOT NULL,
  `AutoUnpublish` tinyint(1) NOT NULL default '0',
  `FileTransferType` varchar(20) default NULL,
  `ServerName` varchar(255) default NULL,
  `Username` varchar(255) default NULL,
  `Password` varchar(255) default NULL,
  `UsageTypeId` bigint(20) default NULL,
  `AttributeId` bigint(20) default NULL,
  `LastPublished` bigint(20) NOT NULL default '0',
  `PortNumber` bigint(20) NOT NULL default '0',
  `SearchCriteriaId` bigint(20) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `PA_Attribute_FK` (`AttributeId`),
  KEY `PA_UsageType_FK` (`UsageTypeId`),
  KEY `IX_PublishAction_SearchCrit_FK` (`SearchCriteriaId`),
  CONSTRAINT `PA_Attribute_FK` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `PA_UsageType_FK` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`),
  CONSTRAINT `PublishAction_SearchCrit_FK` FOREIGN KEY (`SearchCriteriaId`) REFERENCES `searchcriteria` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `publishaction`
--

LOCK TABLES `publishaction` WRITE;
/*!40000 ALTER TABLE `publishaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `publishaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishedasset`
--

DROP TABLE IF EXISTS `publishedasset`;
CREATE TABLE `publishedasset` (
  `AssetId` bigint(20) NOT NULL,
  `PublishActionId` bigint(20) NOT NULL,
  PRIMARY KEY  (`AssetId`,`PublishActionId`),
  KEY `IX_PublishedAsset_PA_FK` (`PublishActionId`),
  CONSTRAINT `PublishedAsset_PA_FK` FOREIGN KEY (`PublishActionId`) REFERENCES `publishaction` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `publishedasset`
--

LOCK TABLES `publishedasset` WRITE;
/*!40000 ALTER TABLE `publishedasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `publishedasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishedassetfile`
--

DROP TABLE IF EXISTS `publishedassetfile`;
CREATE TABLE `publishedassetfile` (
  `AssetId` bigint(20) NOT NULL,
  `PublishActionId` bigint(20) NOT NULL,
  `RelativePath` varchar(255) NOT NULL,
  KEY `IX_PubAssetFile_PubAsset_FK` (`AssetId`,`PublishActionId`),
  CONSTRAINT `PubAssetFile_PubAsset_FK` FOREIGN KEY (`AssetId`, `PublishActionId`) REFERENCES `publishedasset` (`AssetId`, `PublishActionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `publishedassetfile`
--

LOCK TABLES `publishedassetfile` WRITE;
/*!40000 ALTER TABLE `publishedassetfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `publishedassetfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishinglog`
--

DROP TABLE IF EXISTS `publishinglog`;
CREATE TABLE `publishinglog` (
  `Id` bigint(20) NOT NULL auto_increment,
  `PublishActionId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `IsPublished` tinyint(1) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_PublishingLog_PA_FK` (`PublishActionId`),
  CONSTRAINT `PublishingLog_PA_FK` FOREIGN KEY (`PublishActionId`) REFERENCES `publishaction` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `publishinglog`
--

LOCK TABLES `publishinglog` WRITE;
/*!40000 ALTER TABLE `publishinglog` DISABLE KEYS */;
/*!40000 ALTER TABLE `publishinglog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quantityrange`
--

DROP TABLE IF EXISTS `quantityrange`;
CREATE TABLE `quantityrange` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(200) default NULL,
  `LowerLimit` int(11) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `quantityrange`
--

LOCK TABLES `quantityrange` WRITE;
/*!40000 ALTER TABLE `quantityrange` DISABLE KEYS */;
/*!40000 ALTER TABLE `quantityrange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relatedasset`
--

DROP TABLE IF EXISTS `relatedasset`;
CREATE TABLE `relatedasset` (
  `ParentId` bigint(20) NOT NULL,
  `ChildId` bigint(20) NOT NULL,
  `RelationshipTypeId` bigint(20) NOT NULL default '1',
  `SequenceNumber` int(11) default NULL,
  `RelationshipDescription` varchar(255) default NULL,
  PRIMARY KEY  (`ParentId`,`ChildId`,`RelationshipTypeId`),
  KEY `IX_Relationship95` (`ParentId`),
  KEY `IX_Relationship96` (`ChildId`),
  KEY `IX_Relationship255` (`RelationshipTypeId`),
  CONSTRAINT `Relationship255` FOREIGN KEY (`RelationshipTypeId`) REFERENCES `relationshiptype` (`Id`),
  CONSTRAINT `Relationship95` FOREIGN KEY (`ParentId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Relationship96` FOREIGN KEY (`ChildId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `relatedasset`
--

LOCK TABLES `relatedasset` WRITE;
/*!40000 ALTER TABLE `relatedasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `relatedasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relatedassethistory`
--

DROP TABLE IF EXISTS `relatedassethistory`;
CREATE TABLE `relatedassethistory` (
  `ParentId` bigint(20) NOT NULL,
  `ChildId` bigint(20) NOT NULL,
  `RelationshipTypeId` bigint(20) NOT NULL,
  `SequenceNumber` int(11) default NULL,
  `RelationshipDescription` varchar(255) default NULL,
  PRIMARY KEY  (`ParentId`,`ChildId`,`RelationshipTypeId`),
  KEY `Asset_RAH_Child_FK` (`ChildId`),
  KEY `RelationshipType_RAH_FK` (`RelationshipTypeId`),
  CONSTRAINT `Asset_RAH_Child_FK` FOREIGN KEY (`ChildId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `Asset_RAH_Parent_FK` FOREIGN KEY (`ParentId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `RelationshipType_RAH_FK` FOREIGN KEY (`RelationshipTypeId`) REFERENCES `relationshiptype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `relatedassethistory`
--

LOCK TABLES `relatedassethistory` WRITE;
/*!40000 ALTER TABLE `relatedassethistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `relatedassethistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationshiptype`
--

DROP TABLE IF EXISTS `relationshiptype`;
CREATE TABLE `relationshiptype` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `relationshiptype`
--

LOCK TABLES `relationshiptype` WRITE;
/*!40000 ALTER TABLE `relationshiptype` DISABLE KEYS */;
INSERT INTO `relationshiptype` VALUES (1,'Peer'),(2,'Child');
/*!40000 ALTER TABLE `relationshiptype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repurposedasset`
--

DROP TABLE IF EXISTS `repurposedasset`;
CREATE TABLE `repurposedasset` (
  `Duration` bigint(20) default NULL,
  `FileFormatId` bigint(20) NOT NULL,
  `AssetId` bigint(20) NOT NULL,
  `PreviewFileLocation` varchar(255) default NULL,
  `RepurposedVersionId` bigint(20) NOT NULL,
  `Regenerable` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`RepurposedVersionId`),
  KEY `IX_RepurposedAsset_Asset_FK` (`AssetId`),
  KEY `IX_RepurposedAsset_FileFormat_FK` (`FileFormatId`),
  CONSTRAINT `RepurposedAsset_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `RepurposedAsset_FileFormat_FK` FOREIGN KEY (`FileFormatId`) REFERENCES `fileformat` (`Id`),
  CONSTRAINT `RepurposedAsset_RV_FK` FOREIGN KEY (`RepurposedVersionId`) REFERENCES `repurposedversion` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `repurposedasset`
--

LOCK TABLES `repurposedasset` WRITE;
/*!40000 ALTER TABLE `repurposedasset` DISABLE KEYS */;
/*!40000 ALTER TABLE `repurposedasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repurposedslideshow`
--

DROP TABLE IF EXISTS `repurposedslideshow`;
CREATE TABLE `repurposedslideshow` (
  `DisplayTime` int(11) default NULL,
  `SlideShowTypeId` bigint(20) NOT NULL default '1',
  `InfoBar` tinyint(1) default NULL,
  `RepurposedVersionId` bigint(20) NOT NULL,
  `MaintainAspectRatio` tinyint(1) default '1',
  `IncludeLabels` tinyint(1) NOT NULL default '0',
  `CaptionIds` varchar(255) default NULL,
  `JpgConversionQuality` int(11) default NULL,
  `LanguageCode` varchar(20) default NULL,
  `ImageHeight` int(11) default NULL,
  `ImageWidth` int(11) default NULL,
  `Description` varchar(255) default NULL,
  `ShowOnHomepage` tinyint(1) NOT NULL default '0',
  `IsDefaultOnHomepage` tinyint(1) NOT NULL default '0',
  `SequenceNumber` int(11) default NULL,
  `SearchCriteriaId` bigint(20) default NULL,
  `TextRtl` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`RepurposedVersionId`),
  KEY `RSlideshow_SlideShowType_FK` (`SlideShowTypeId`),
  KEY `IX_RSlideshow_SearchCrit_FK` (`SearchCriteriaId`),
  CONSTRAINT `RepurposedSlideshow_RV_FK` FOREIGN KEY (`RepurposedVersionId`) REFERENCES `repurposedversion` (`Id`),
  CONSTRAINT `RSlideshow_SearchCrit_FK` FOREIGN KEY (`SearchCriteriaId`) REFERENCES `searchcriteria` (`Id`),
  CONSTRAINT `RSlideshow_SlideShowType_FK` FOREIGN KEY (`SlideShowTypeId`) REFERENCES `slideshowtype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `repurposedslideshow`
--

LOCK TABLES `repurposedslideshow` WRITE;
/*!40000 ALTER TABLE `repurposedslideshow` DISABLE KEYS */;
/*!40000 ALTER TABLE `repurposedslideshow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repurposedversion`
--

DROP TABLE IF EXISTS `repurposedversion`;
CREATE TABLE `repurposedversion` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Url` varchar(255) NOT NULL,
  `Height` varchar(5) default NULL,
  `Width` varchar(5) default NULL,
  `CreatedDate` datetime NOT NULL,
  `CreatedByUserId` bigint(20) default NULL,
  `HeightMax` varchar(5) default NULL,
  `WidthMax` varchar(5) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship310` (`CreatedByUserId`),
  CONSTRAINT `Relationship310` FOREIGN KEY (`CreatedByUserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `repurposedversion`
--

LOCK TABLES `repurposedversion` WRITE;
/*!40000 ALTER TABLE `repurposedversion` DISABLE KEYS */;
/*!40000 ALTER TABLE `repurposedversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
  `Id` bigint(20) NOT NULL auto_increment,
  `FulfillmentUserId` bigint(20) default NULL,
  `RequestUserId` bigint(20) NOT NULL,
  `AssetId` bigint(20) default NULL,
  `Description` mediumtext,
  `DateAdded` date NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `Asset_Request_FK` (`AssetId`),
  KEY `RequestUser_Request_FK` (`RequestUserId`),
  KEY `FulfillmentUser_Request_FK` (`FulfillmentUserId`),
  CONSTRAINT `Asset_Request_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `FulfillmentUser_Request_FK` FOREIGN KEY (`FulfillmentUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `RequestUser_Request_FK` FOREIGN KEY (`RequestUserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(200) NOT NULL,
  `Description` varchar(200) default NULL,
  `Identifier` varchar(50) NOT NULL,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Identifier` (`Identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savedsearch`
--

DROP TABLE IF EXISTS `savedsearch`;
CREATE TABLE `savedsearch` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `UserId` bigint(20) NOT NULL,
  `Keywords` varchar(255) default NULL,
  `IsRssFeed` tinyint(1) NOT NULL default '0',
  `IsBuilderSearch` tinyint(1) NOT NULL default '0',
  `IsDescending` tinyint(1) NOT NULL default '0',
  `SortAttributeId` bigint(20) default NULL,
  `Promoted` tinyint(1) NOT NULL default '0',
  `AvailableToAll` tinyint(1) NOT NULL default '0',
  `Featured` tinyint(1) default '0',
  `SearchImage` varchar(250) default NULL,
  `NewAssetAlert` tinyint(1) NOT NULL default '0',
  `Description` longtext,
  `SearchCriteriaId` bigint(20) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `AI_Id` (`Id`),
  KEY `IX_Relationship195` (`UserId`),
  KEY `IX_SavedSearch_SearchCrit_FK` (`SearchCriteriaId`),
  CONSTRAINT `Relationship195` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `SavedSearch_SearchCrit_FK` FOREIGN KEY (`SearchCriteriaId`) REFERENCES `searchcriteria` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `savedsearch`
--

LOCK TABLES `savedsearch` WRITE;
/*!40000 ALTER TABLE `savedsearch` DISABLE KEYS */;
/*!40000 ALTER TABLE `savedsearch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduledreport`
--

DROP TABLE IF EXISTS `scheduledreport`;
CREATE TABLE `scheduledreport` (
  `Id` bigint(20) NOT NULL auto_increment,
  `NextSendDate` datetime default NULL,
  `ReportPeriod` varchar(100) default NULL,
  `ReportType` varchar(100) default NULL,
  `ReportName` varchar(200) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `scheduledreport`
--

LOCK TABLES `scheduledreport` WRITE;
/*!40000 ALTER TABLE `scheduledreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduledreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduledreportgroup`
--

DROP TABLE IF EXISTS `scheduledreportgroup`;
CREATE TABLE `scheduledreportgroup` (
  `ReportId` bigint(20) NOT NULL,
  `GroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`ReportId`,`GroupId`),
  KEY `IX_Relationship167` (`GroupId`),
  KEY `IX_Relationship165` (`ReportId`),
  CONSTRAINT `Relationship165` FOREIGN KEY (`ReportId`) REFERENCES `scheduledreport` (`Id`),
  CONSTRAINT `Relationship167` FOREIGN KEY (`GroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `scheduledreportgroup`
--

LOCK TABLES `scheduledreportgroup` WRITE;
/*!40000 ALTER TABLE `scheduledreportgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduledreportgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `searchcriteria`
--

DROP TABLE IF EXISTS `searchcriteria`;
CREATE TABLE `searchcriteria` (
  `Id` bigint(20) NOT NULL auto_increment,
  `SearchCriteriaFile` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `searchcriteria`
--

LOCK TABLES `searchcriteria` WRITE;
/*!40000 ALTER TABLE `searchcriteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `searchcriteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `searchreport`
--

DROP TABLE IF EXISTS `searchreport`;
CREATE TABLE `searchreport` (
  `SearchTerm` varchar(250) default NULL,
  `FullQuery` text,
  `Success` tinyint(1) default NULL,
  `SearchDate` date default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `searchreport`
--

LOCK TABLES `searchreport` WRITE;
/*!40000 ALTER TABLE `searchreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `searchreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondarydownloadusagetype`
--

DROP TABLE IF EXISTS `secondarydownloadusagetype`;
CREATE TABLE `secondarydownloadusagetype` (
  `AssetUseId` bigint(20) NOT NULL,
  `UsageTypeId` bigint(20) NOT NULL,
  `OriginalDescription` varchar(255) default NULL,
  PRIMARY KEY  (`AssetUseId`,`UsageTypeId`),
  KEY `IX_SDUT_UsageTypeId_FK` (`UsageTypeId`),
  KEY `IX_SDUT_AssetUseId_FK` (`AssetUseId`),
  CONSTRAINT `SDUT_AssetUseId_FK` FOREIGN KEY (`AssetUseId`) REFERENCES `assetuse` (`Id`),
  CONSTRAINT `SDUT_UsageTypeId_FK` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `secondarydownloadusagetype`
--

LOCK TABLES `secondarydownloadusagetype` WRITE;
/*!40000 ALTER TABLE `secondarydownloadusagetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondarydownloadusagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securityquestion`
--

DROP TABLE IF EXISTS `securityquestion`;
CREATE TABLE `securityquestion` (
  `Id` bigint(20) NOT NULL,
  `GroupId` int(11) NOT NULL,
  `Value` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `securityquestion`
--

LOCK TABLES `securityquestion` WRITE;
/*!40000 ALTER TABLE `securityquestion` DISABLE KEYS */;
INSERT INTO `securityquestion` VALUES (1,1,'What was your childhood nickname?'),(2,1,'In what city did you meet your spouse/partner?'),(3,1,'What is the name of your favourite childhood friend?'),(4,1,'What street did you live on when you were eight?'),(5,1,'What is the name of the first school you attended?'),(6,2,'What is your oldest sibling\'s birthday month and year? (e.g. January 1980)'),(7,2,'What is the middle name of your youngest child?'),(8,2,'What is your oldest sibling\'s middle name?'),(9,2,'What was your childhood phone number including area code? (e.g. 01273 669 490)'),(10,2,'What is your oldest cousin\'s first and last name?');
/*!40000 ALTER TABLE `securityquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sendemaildaterule`
--

DROP TABLE IF EXISTS `sendemaildaterule`;
CREATE TABLE `sendemaildaterule` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AttributeId` bigint(20) NOT NULL,
  `RuleName` varchar(200) default NULL,
  `DaysBefore` int(11) default '0',
  `EmailText` mediumtext,
  `Enabled` tinyint(1) NOT NULL default '0',
  `EmailAssetDownloaders` tinyint(1) NOT NULL default '0',
  `EmailAssetUploader` tinyint(1) NOT NULL default '0',
  `EmailAdminUsers` tinyint(1) NOT NULL default '1',
  `EmailLastApprover` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship90` (`AttributeId`),
  CONSTRAINT `Relationship90` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sendemaildaterule`
--

LOCK TABLES `sendemaildaterule` WRITE;
/*!40000 ALTER TABLE `sendemaildaterule` DISABLE KEYS */;
/*!40000 ALTER TABLE `sendemaildaterule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessionlog`
--

DROP TABLE IF EXISTS `sessionlog`;
CREATE TABLE `sessionlog` (
  `Id` bigint(20) NOT NULL auto_increment,
  `UserId` bigint(20) default NULL,
  `IpAddress` varchar(255) default NULL,
  `StartDateTime` datetime default NULL,
  `LoginDateTime` datetime default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship243` (`UserId`),
  CONSTRAINT `Relationship243` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sessionlog`
--

LOCK TABLES `sessionlog` WRITE;
/*!40000 ALTER TABLE `sessionlog` DISABLE KEYS */;
INSERT INTO `sessionlog` VALUES (1,1,NULL,'2012-06-10 11:05:24','2012-06-10 11:08:28'),(2,NULL,NULL,'2012-06-10 11:05:24',NULL),(3,NULL,NULL,'2012-06-10 11:05:38',NULL),(4,NULL,NULL,'2012-06-10 11:06:25',NULL),(5,NULL,NULL,'2012-06-10 11:07:12',NULL),(6,NULL,NULL,'2012-06-10 11:07:59',NULL),(7,NULL,NULL,'2012-06-10 11:08:46',NULL),(8,NULL,NULL,'2012-06-10 11:09:01',NULL),(9,NULL,'0:0:0:0:0:0:0:1','2012-06-10 11:09:02',NULL),(10,1,NULL,'2012-06-10 11:09:14','2012-06-10 11:09:25'),(11,NULL,NULL,'2012-06-10 11:09:33',NULL),(12,NULL,NULL,'2012-06-10 11:10:20',NULL),(13,NULL,NULL,'2012-06-10 11:11:08',NULL),(14,1,NULL,'2012-06-10 11:18:44','2012-06-10 11:18:55'),(15,NULL,NULL,'2012-06-10 11:18:44',NULL),(16,NULL,NULL,'2012-06-10 11:18:44',NULL),(17,NULL,NULL,'2012-06-10 11:18:58',NULL),(18,NULL,NULL,'2012-06-10 11:19:46',NULL),(19,NULL,NULL,'2012-06-10 11:20:33',NULL),(20,NULL,NULL,'2012-06-10 11:21:20',NULL),(21,NULL,NULL,'2012-06-10 11:23:23',NULL),(22,NULL,NULL,'2012-06-10 11:23:41',NULL),(23,NULL,NULL,'2012-06-10 11:24:28',NULL),(24,1,NULL,'2012-06-10 11:24:48','2012-06-10 11:24:58'),(25,NULL,NULL,'2012-06-10 11:25:15',NULL),(26,NULL,NULL,'2012-06-10 11:26:03',NULL),(27,NULL,NULL,'2012-06-10 11:26:50',NULL),(28,NULL,NULL,'2012-06-10 11:27:37',NULL),(29,NULL,NULL,'2012-06-10 11:28:24',NULL),(30,NULL,NULL,'2012-06-10 11:29:11',NULL),(31,NULL,NULL,'2012-06-10 11:29:58',NULL),(32,NULL,NULL,'2012-06-10 11:37:16',NULL),(33,1,NULL,'2012-06-10 11:37:19','2012-06-10 11:37:48'),(34,NULL,NULL,'2012-06-10 11:37:49',NULL),(35,NULL,NULL,'2012-06-10 11:38:36',NULL),(36,1,'0:0:0:0:0:0:0:1','2012-06-10 11:39:23','2012-06-10 11:39:32'),(37,NULL,NULL,'2012-06-10 11:39:23',NULL),(38,NULL,NULL,'2012-06-10 11:40:10',NULL),(39,NULL,NULL,'2012-06-10 11:40:58',NULL),(40,NULL,NULL,'2012-06-10 11:41:45',NULL),(41,NULL,NULL,'2012-06-10 11:42:32',NULL),(42,NULL,NULL,'2012-06-10 11:43:16',NULL),(43,NULL,NULL,'2012-06-10 11:43:21',NULL),(44,NULL,NULL,'2012-06-10 11:44:06',NULL),(45,NULL,NULL,'2012-06-10 11:44:53',NULL),(46,NULL,NULL,'2012-06-10 11:45:40',NULL),(47,NULL,NULL,'2012-06-10 12:39:55',NULL),(48,NULL,NULL,'2012-06-10 12:40:39',NULL),(49,NULL,NULL,'2012-06-10 12:41:26',NULL),(50,NULL,NULL,'2012-06-10 12:43:00',NULL),(51,1,NULL,'2012-06-10 12:43:10','2012-06-10 12:44:41'),(52,NULL,NULL,'2012-06-10 12:43:47',NULL),(53,NULL,NULL,'2012-06-10 12:44:35',NULL),(54,NULL,NULL,'2012-06-10 12:45:22',NULL),(55,NULL,NULL,'2012-06-10 12:46:09',NULL),(56,NULL,NULL,'2012-06-10 12:46:56',NULL),(57,NULL,NULL,'2012-06-10 12:47:43',NULL),(58,NULL,NULL,'2012-06-10 12:48:30',NULL),(59,NULL,NULL,'2012-06-10 12:49:17',NULL),(60,NULL,NULL,'2012-06-10 12:50:04',NULL),(61,NULL,NULL,'2012-06-10 12:50:52',NULL),(62,NULL,NULL,'2012-06-10 12:51:39',NULL),(63,NULL,NULL,'2012-06-10 12:52:14',NULL),(64,NULL,NULL,'2012-06-10 12:52:26',NULL),(65,NULL,NULL,'2012-06-10 12:52:58',NULL),(66,NULL,NULL,'2012-06-10 12:53:13',NULL),(67,NULL,NULL,'2012-06-10 12:54:00',NULL),(68,NULL,NULL,'2012-06-10 12:54:47',NULL),(69,NULL,NULL,'2012-06-10 12:57:09',NULL),(70,1,NULL,'2012-06-10 12:57:12','2012-06-10 12:57:28'),(71,NULL,NULL,'2012-06-10 12:57:19',NULL),(72,NULL,NULL,'2012-06-10 12:57:55',NULL),(73,NULL,NULL,'2012-06-10 12:58:43',NULL),(74,NULL,NULL,'2012-06-10 12:58:46',NULL),(75,NULL,NULL,'2012-06-10 12:59:30',NULL),(76,NULL,NULL,'2012-06-10 12:59:56',NULL),(77,NULL,NULL,'2012-06-10 13:00:17',NULL),(78,NULL,NULL,'2012-06-10 13:01:04',NULL),(79,NULL,NULL,'2012-06-10 13:01:51',NULL),(80,NULL,NULL,'2012-06-10 13:02:38',NULL),(81,NULL,NULL,'2012-06-10 13:03:25',NULL),(82,NULL,NULL,'2012-06-10 13:04:12',NULL),(83,NULL,NULL,'2012-06-10 13:04:59',NULL),(84,NULL,NULL,'2012-06-10 13:05:47',NULL),(85,NULL,NULL,'2012-06-10 13:06:34',NULL),(86,NULL,NULL,'2012-06-10 13:07:21',NULL),(87,NULL,NULL,'2012-06-10 13:08:08',NULL),(88,NULL,NULL,'2012-06-10 13:08:55',NULL),(89,NULL,NULL,'2012-06-10 13:09:42',NULL),(90,NULL,NULL,'2012-06-10 13:10:29',NULL),(91,NULL,NULL,'2012-06-10 13:11:16',NULL),(92,NULL,NULL,'2012-06-10 13:11:18',NULL),(93,NULL,NULL,'2012-06-10 13:12:04',NULL),(94,NULL,NULL,'2012-06-10 13:12:51',NULL),(95,NULL,NULL,'2012-06-10 13:12:52',NULL),(96,NULL,NULL,'2012-06-10 13:13:38',NULL),(97,NULL,NULL,'2012-06-10 13:14:25',NULL),(98,NULL,NULL,'2012-06-10 13:15:12',NULL),(99,NULL,NULL,'2012-06-10 13:15:59',NULL),(100,NULL,NULL,'2012-06-10 13:16:46',NULL),(101,NULL,NULL,'2012-06-10 13:17:33',NULL),(102,NULL,NULL,'2012-06-10 13:18:03',NULL),(103,NULL,NULL,'2012-06-10 13:18:20',NULL),(104,NULL,NULL,'2012-06-10 13:18:51',NULL),(105,NULL,NULL,'2012-06-10 13:19:08',NULL),(106,NULL,NULL,'2012-06-10 13:19:55',NULL),(107,NULL,NULL,'2012-06-10 13:20:42',NULL),(108,NULL,NULL,'2012-06-10 13:21:29',NULL),(109,NULL,NULL,'2012-06-10 13:22:16',NULL),(110,NULL,NULL,'2012-06-10 13:23:03',NULL),(111,NULL,NULL,'2012-06-10 13:23:50',NULL),(112,NULL,NULL,'2012-06-10 13:24:38',NULL),(113,NULL,NULL,'2012-06-10 13:25:25',NULL),(114,NULL,NULL,'2012-06-10 13:26:12',NULL),(115,NULL,NULL,'2012-06-10 13:26:59',NULL),(116,NULL,NULL,'2012-06-10 13:27:46',NULL),(117,NULL,NULL,'2012-06-10 13:28:33',NULL),(118,NULL,NULL,'2012-06-10 13:29:20',NULL),(119,NULL,NULL,'2012-06-10 13:30:07',NULL),(120,NULL,NULL,'2012-06-10 13:30:55',NULL),(121,NULL,NULL,'2012-06-10 13:31:42',NULL),(122,NULL,NULL,'2012-06-10 13:32:29',NULL),(123,NULL,NULL,'2012-06-10 13:33:16',NULL),(124,NULL,NULL,'2012-06-10 13:34:03',NULL),(125,NULL,NULL,'2012-06-10 13:34:50',NULL),(126,NULL,NULL,'2012-06-10 13:35:37',NULL),(127,NULL,NULL,'2012-06-10 13:36:24',NULL),(128,NULL,NULL,'2012-06-10 13:37:12',NULL),(129,NULL,NULL,'2012-06-10 13:46:44',NULL),(130,NULL,NULL,'2012-06-10 13:47:24',NULL),(131,NULL,NULL,'2012-06-10 13:48:11',NULL),(132,1,'0:0:0:0:0:0:0:1','2012-06-10 13:49:13','2012-06-10 13:49:18'),(133,NULL,NULL,'2012-06-10 13:49:16',NULL),(134,NULL,NULL,'2012-06-10 13:49:45',NULL),(135,NULL,NULL,'2012-06-10 13:50:33',NULL),(136,NULL,NULL,'2012-06-10 13:51:20',NULL),(137,NULL,NULL,'2012-06-10 13:52:07',NULL),(138,NULL,NULL,'2012-06-10 13:52:54',NULL),(139,NULL,NULL,'2012-06-10 13:53:41',NULL),(140,NULL,NULL,'2012-06-10 13:54:01',NULL),(141,NULL,NULL,'2012-06-10 13:54:28',NULL),(142,NULL,NULL,'2012-06-10 13:55:15',NULL),(143,NULL,NULL,'2012-06-10 13:56:03',NULL),(144,NULL,NULL,'2012-06-10 13:56:50',NULL),(145,NULL,NULL,'2012-06-10 13:57:37',NULL),(146,NULL,NULL,'2012-06-10 13:58:24',NULL),(147,NULL,NULL,'2012-06-10 13:59:11',NULL),(148,NULL,NULL,'2012-06-10 13:59:58',NULL),(149,NULL,NULL,'2012-06-10 14:00:45',NULL),(150,NULL,NULL,'2012-06-10 14:01:32',NULL),(151,NULL,NULL,'2012-06-10 14:02:19',NULL),(152,NULL,NULL,'2012-06-10 14:03:07',NULL),(153,NULL,NULL,'2012-06-10 14:03:27',NULL),(154,NULL,NULL,'2012-06-10 14:03:30',NULL),(155,NULL,NULL,'2012-06-10 14:03:54',NULL),(156,NULL,NULL,'2012-06-10 14:04:41',NULL),(157,NULL,NULL,'2012-06-10 14:05:27',NULL),(158,NULL,NULL,'2012-06-10 14:05:29',NULL),(159,NULL,NULL,'2012-06-10 14:06:08',NULL),(160,NULL,NULL,'2012-06-10 14:06:15',NULL),(161,NULL,NULL,'2012-06-10 14:07:02',NULL),(162,NULL,NULL,'2012-06-10 14:07:49',NULL),(163,NULL,NULL,'2012-06-10 14:08:24',NULL),(164,NULL,NULL,'2012-06-10 14:08:36',NULL),(165,NULL,NULL,'2012-06-10 14:09:24',NULL),(166,NULL,NULL,'2012-06-10 14:09:40',NULL),(167,NULL,NULL,'2012-06-10 14:10:11',NULL),(168,NULL,NULL,'2012-06-10 14:10:42',NULL),(169,NULL,NULL,'2012-06-10 14:10:58',NULL),(170,NULL,NULL,'2012-06-10 14:11:45',NULL),(171,NULL,NULL,'2012-06-10 14:12:32',NULL),(172,NULL,NULL,'2012-06-10 14:12:34',NULL),(173,NULL,NULL,'2012-06-10 14:21:11',NULL),(174,1,NULL,'2012-06-10 14:21:52','2012-06-10 14:22:05'),(175,NULL,NULL,'2012-06-10 14:21:58',NULL),(176,NULL,NULL,'2012-06-10 14:22:45',NULL),(177,NULL,NULL,'2012-06-10 14:23:32',NULL),(178,NULL,NULL,'2012-06-10 14:24:19',NULL),(179,NULL,NULL,'2012-06-10 14:25:06',NULL),(180,NULL,NULL,'2012-06-10 14:25:53',NULL),(181,NULL,NULL,'2012-06-10 14:26:06',NULL),(182,NULL,NULL,'2012-06-10 14:26:09',NULL),(183,NULL,NULL,'2012-06-10 14:26:16',NULL),(184,NULL,NULL,'2012-06-10 14:26:18',NULL),(185,NULL,NULL,'2012-06-10 14:26:20',NULL),(186,NULL,NULL,'2012-06-10 14:26:23',NULL),(187,NULL,NULL,'2012-06-10 14:26:40',NULL),(188,NULL,NULL,'2012-06-10 14:27:28',NULL),(189,NULL,NULL,'2012-06-10 14:28:15',NULL),(190,NULL,NULL,'2012-06-10 14:29:02',NULL),(191,NULL,NULL,'2012-06-10 14:29:04',NULL),(192,NULL,NULL,'2012-06-10 14:29:05',NULL),(193,NULL,NULL,'2012-06-10 14:29:07',NULL),(194,NULL,NULL,'2012-06-10 14:29:09',NULL),(195,NULL,NULL,'2012-06-10 14:29:11',NULL),(196,NULL,NULL,'2012-06-10 14:29:25',NULL),(197,NULL,NULL,'2012-06-10 14:29:49',NULL),(198,NULL,NULL,'2012-06-10 14:30:36',NULL),(199,NULL,NULL,'2012-06-10 14:31:23',NULL),(200,NULL,NULL,'2012-06-10 14:31:38',NULL),(201,NULL,NULL,'2012-06-10 14:31:45',NULL),(202,NULL,NULL,'2012-06-10 14:32:10',NULL),(203,NULL,NULL,'2012-06-10 14:32:57',NULL),(204,NULL,NULL,'2012-06-10 14:33:02',NULL),(205,NULL,NULL,'2012-06-10 14:33:44',NULL),(206,NULL,NULL,'2012-06-10 14:34:26',NULL),(207,NULL,NULL,'2012-06-10 14:34:31',NULL),(208,NULL,NULL,'2012-06-10 14:34:32',NULL),(209,NULL,NULL,'2012-06-10 14:34:42',NULL),(210,NULL,NULL,'2012-06-10 14:35:16',NULL),(211,NULL,NULL,'2012-06-10 14:35:18',NULL),(212,NULL,NULL,'2012-06-10 14:35:28',NULL),(213,NULL,NULL,'2012-06-10 14:36:06',NULL),(214,NULL,NULL,'2012-06-10 14:36:53',NULL),(215,NULL,NULL,'2012-06-10 14:37:05',NULL),(216,NULL,NULL,'2012-06-10 14:37:40',NULL),(217,NULL,NULL,'2012-06-10 14:38:27',NULL),(218,NULL,NULL,'2012-06-10 14:39:14',NULL),(219,NULL,NULL,'2012-06-10 14:40:01',NULL),(220,NULL,NULL,'2012-06-10 14:40:18',NULL),(221,NULL,NULL,'2012-06-10 14:40:49',NULL),(222,NULL,NULL,'2012-06-10 14:41:36',NULL),(223,NULL,NULL,'2012-06-10 14:42:20',NULL),(224,NULL,NULL,'2012-06-10 14:42:23',NULL),(225,NULL,NULL,'2012-06-10 14:43:10',NULL),(226,NULL,NULL,'2012-06-10 14:43:57',NULL),(227,NULL,NULL,'2012-06-10 14:44:44',NULL),(228,NULL,NULL,'2012-06-10 14:51:49',NULL),(229,1,NULL,'2012-06-10 14:52:17','2012-06-10 14:52:24'),(230,NULL,NULL,'2012-06-10 14:52:35',NULL),(231,NULL,NULL,'2012-06-10 14:53:23',NULL),(232,NULL,NULL,'2012-06-10 14:53:27',NULL),(233,NULL,NULL,'2012-06-10 14:54:10',NULL),(234,NULL,NULL,'2012-06-10 14:54:57',NULL),(235,NULL,NULL,'2012-06-10 14:55:44',NULL),(236,NULL,NULL,'2012-06-10 14:56:31',NULL),(237,NULL,NULL,'2012-06-10 14:57:18',NULL),(238,NULL,NULL,'2012-06-10 14:58:05',NULL),(239,NULL,NULL,'2012-06-10 14:58:52',NULL),(240,NULL,NULL,'2012-06-10 14:59:40',NULL),(241,NULL,NULL,'2012-06-10 15:00:27',NULL),(242,NULL,NULL,'2012-06-10 15:01:14',NULL),(243,NULL,NULL,'2012-06-10 15:02:01',NULL),(244,NULL,NULL,'2012-06-10 15:02:48',NULL),(245,NULL,NULL,'2012-06-10 15:03:35',NULL),(246,NULL,NULL,'2012-06-10 15:04:22',NULL),(247,NULL,NULL,'2012-06-10 15:05:09',NULL),(248,NULL,NULL,'2012-06-10 15:05:57',NULL),(249,NULL,NULL,'2012-06-10 15:06:44',NULL),(250,NULL,NULL,'2012-06-10 15:07:31',NULL),(251,NULL,NULL,'2012-06-10 15:08:18',NULL),(252,NULL,NULL,'2012-06-10 15:09:05',NULL),(253,NULL,NULL,'2012-06-10 15:09:52',NULL),(254,NULL,NULL,'2012-06-10 15:10:39',NULL),(255,NULL,NULL,'2012-06-10 15:11:25',NULL),(256,NULL,NULL,'2012-06-10 15:11:27',NULL),(257,NULL,NULL,'2012-06-10 15:12:14',NULL),(258,NULL,NULL,'2012-06-10 15:13:01',NULL),(259,NULL,NULL,'2012-06-10 15:13:48',NULL),(260,NULL,NULL,'2012-06-10 15:14:35',NULL),(261,NULL,NULL,'2012-06-10 15:14:57',NULL),(262,NULL,NULL,'2012-06-10 15:15:22',NULL),(263,NULL,NULL,'2012-06-10 15:16:09',NULL),(264,NULL,NULL,'2012-06-10 15:16:56',NULL),(265,NULL,NULL,'2012-06-10 15:17:39',NULL),(266,NULL,NULL,'2012-06-10 15:17:40',NULL),(267,NULL,NULL,'2012-06-10 15:17:43',NULL),(268,NULL,NULL,'2012-06-10 15:17:52',NULL),(269,NULL,NULL,'2012-06-10 15:18:42',NULL),(270,NULL,NULL,'2012-06-10 15:19:18',NULL),(271,NULL,NULL,'2012-06-10 15:19:19',NULL),(272,NULL,NULL,'2012-06-10 15:19:26',NULL),(273,NULL,NULL,'2012-06-10 15:20:05',NULL),(274,NULL,NULL,'2012-06-10 15:20:52',NULL),(275,NULL,NULL,'2012-06-10 15:21:39',NULL),(276,NULL,NULL,'2012-06-10 15:22:26',NULL),(277,NULL,NULL,'2012-06-10 15:23:13',NULL),(278,NULL,NULL,'2012-06-10 15:24:00',NULL),(279,NULL,NULL,'2012-06-10 15:24:48',NULL),(280,NULL,NULL,'2012-06-10 15:25:35',NULL),(281,NULL,NULL,'2012-06-10 15:26:22',NULL),(282,NULL,NULL,'2012-06-10 15:27:09',NULL),(283,NULL,NULL,'2012-06-10 15:27:56',NULL),(284,NULL,NULL,'2012-06-10 15:28:43',NULL),(285,NULL,NULL,'2012-06-10 15:29:30',NULL),(286,NULL,NULL,'2012-06-10 15:30:17',NULL),(287,NULL,NULL,'2012-06-10 15:31:15',NULL),(288,NULL,NULL,'2012-06-10 15:31:52',NULL),(289,NULL,NULL,'2012-06-10 15:32:39',NULL),(290,NULL,NULL,'2012-06-10 15:33:26',NULL),(291,NULL,NULL,'2012-06-10 15:34:13',NULL),(292,NULL,NULL,'2012-06-10 15:35:00',NULL),(293,NULL,NULL,'2012-06-10 15:35:47',NULL),(294,NULL,NULL,'2012-06-10 15:36:34',NULL),(295,NULL,NULL,'2012-06-10 15:37:22',NULL),(296,NULL,NULL,'2012-06-10 15:38:09',NULL),(297,NULL,NULL,'2012-06-10 15:38:56',NULL),(298,NULL,NULL,'2012-06-10 15:39:04',NULL),(299,NULL,NULL,'2012-06-10 15:39:43',NULL),(300,NULL,NULL,'2012-06-10 15:40:30',NULL),(301,NULL,NULL,'2012-06-10 15:41:17',NULL),(302,NULL,NULL,'2012-06-10 15:42:04',NULL),(303,NULL,NULL,'2012-06-10 15:42:51',NULL),(304,NULL,NULL,'2012-06-10 15:46:28',NULL),(305,1,'0:0:0:0:0:0:0:1','2012-06-10 15:46:29','2012-06-10 15:46:40'),(306,NULL,NULL,'2012-06-10 15:46:47',NULL),(307,NULL,NULL,'2012-06-10 15:47:34',NULL),(308,NULL,NULL,'2012-06-10 15:48:21',NULL),(309,NULL,NULL,'2012-06-10 15:48:48',NULL),(310,NULL,NULL,'2012-06-10 15:48:54',NULL),(311,NULL,NULL,'2012-06-10 15:49:08',NULL),(312,NULL,NULL,'2012-06-10 15:49:56',NULL),(313,1,NULL,'2012-06-10 15:50:03','2012-06-10 15:50:18'),(314,NULL,NULL,'2012-06-10 15:50:43',NULL),(315,NULL,NULL,'2012-06-10 15:51:29',NULL),(316,NULL,NULL,'2012-06-10 15:52:15',NULL),(317,NULL,NULL,'2012-06-10 15:53:03',NULL),(318,NULL,NULL,'2012-06-10 15:53:48',NULL),(319,NULL,NULL,'2012-06-10 15:54:35',NULL),(320,NULL,NULL,'2012-06-10 15:55:21',NULL),(321,NULL,NULL,'2012-06-10 15:55:24',NULL),(322,NULL,NULL,'2012-06-10 15:56:07',NULL),(323,NULL,NULL,'2012-06-10 15:56:53',NULL),(324,NULL,NULL,'2012-06-10 15:57:39',NULL),(325,NULL,NULL,'2012-06-10 15:58:25',NULL),(326,NULL,NULL,'2012-06-10 15:58:51',NULL),(327,NULL,NULL,'2012-06-10 15:59:11',NULL),(328,NULL,NULL,'2012-06-10 15:59:47',NULL),(329,NULL,NULL,'2012-06-10 15:59:57',NULL),(330,NULL,NULL,'2012-06-10 16:00:44',NULL),(331,NULL,NULL,'2012-06-10 16:01:30',NULL),(332,NULL,NULL,'2012-06-10 16:02:16',NULL),(333,NULL,NULL,'2012-06-10 16:03:02',NULL),(334,NULL,NULL,'2012-06-10 16:03:48',NULL),(335,NULL,NULL,'2012-06-10 16:04:34',NULL),(336,NULL,NULL,'2012-06-10 16:05:20',NULL),(337,NULL,NULL,'2012-06-10 16:06:06',NULL),(338,NULL,NULL,'2012-06-10 16:06:52',NULL),(339,NULL,NULL,'2012-06-10 16:07:38',NULL),(340,NULL,NULL,'2012-06-10 16:08:24',NULL),(341,NULL,NULL,'2012-06-10 16:09:11',NULL),(342,NULL,NULL,'2012-06-10 16:10:01',NULL),(343,NULL,NULL,'2012-06-10 16:10:43',NULL),(344,NULL,NULL,'2012-06-10 16:11:29',NULL),(345,NULL,NULL,'2012-06-10 16:12:15',NULL),(346,NULL,NULL,'2012-06-10 16:13:01',NULL),(347,NULL,NULL,'2012-06-10 16:13:47',NULL),(348,NULL,NULL,'2012-06-10 16:14:33',NULL),(349,NULL,NULL,'2012-06-10 16:15:19',NULL),(350,NULL,NULL,'2012-06-10 16:16:05',NULL),(351,NULL,NULL,'2012-06-10 16:16:52',NULL),(352,NULL,NULL,'2012-06-10 16:17:38',NULL),(353,NULL,NULL,'2012-06-10 16:18:24',NULL),(354,NULL,NULL,'2012-06-10 16:19:10',NULL),(355,NULL,NULL,'2012-06-10 16:19:56',NULL),(356,NULL,NULL,'2012-06-10 16:20:44',NULL),(357,NULL,NULL,'2012-06-10 16:21:02',NULL),(358,NULL,NULL,'2012-06-10 16:21:28',NULL),(359,NULL,NULL,'2012-06-10 16:22:02',NULL),(360,NULL,NULL,'2012-06-10 16:22:14',NULL),(361,NULL,NULL,'2012-06-10 16:23:00',NULL),(362,NULL,NULL,'2012-06-10 16:23:46',NULL),(363,NULL,NULL,'2012-06-10 16:24:33',NULL),(364,NULL,NULL,'2012-06-10 16:25:19',NULL),(365,NULL,NULL,'2012-06-10 16:25:25',NULL),(366,NULL,NULL,'2012-06-10 16:26:05',NULL),(367,NULL,NULL,'2012-06-10 16:26:51',NULL),(368,NULL,NULL,'2012-06-10 16:27:37',NULL),(369,NULL,NULL,'2012-06-10 16:28:23',NULL),(370,NULL,NULL,'2012-06-10 16:29:09',NULL),(371,NULL,NULL,'2012-06-10 16:29:24',NULL),(372,NULL,NULL,'2012-06-10 16:29:55',NULL),(373,NULL,NULL,'2012-06-10 16:30:41',NULL),(374,NULL,NULL,'2012-06-10 16:31:27',NULL),(375,NULL,NULL,'2012-06-10 16:32:13',NULL),(376,NULL,NULL,'2012-06-10 16:33:00',NULL),(377,NULL,NULL,'2012-06-10 16:33:46',NULL),(378,NULL,NULL,'2012-06-10 16:34:32',NULL),(379,NULL,NULL,'2012-06-10 16:35:12',NULL),(380,NULL,NULL,'2012-06-10 16:35:18',NULL),(381,NULL,NULL,'2012-06-10 16:37:06',NULL),(382,1,NULL,'2012-06-10 16:37:14','2012-06-10 16:37:23'),(383,NULL,NULL,'2012-06-10 16:37:36',NULL),(384,NULL,NULL,'2012-06-10 16:37:45',NULL),(385,NULL,NULL,'2012-06-10 16:37:52',NULL),(386,NULL,NULL,'2012-06-10 16:38:23',NULL),(387,NULL,NULL,'2012-06-10 16:38:34',NULL),(388,NULL,NULL,'2012-06-10 16:38:58',NULL),(389,NULL,NULL,'2012-06-10 16:39:08',NULL),(390,NULL,NULL,'2012-06-10 16:39:54',NULL),(391,NULL,NULL,'2012-06-10 16:40:41',NULL),(392,NULL,NULL,'2012-06-10 16:41:27',NULL),(393,NULL,'0:0:0:0:0:0:0:1','2012-06-10 16:42:08',NULL),(394,NULL,NULL,'2012-06-10 16:42:10',NULL),(395,NULL,NULL,'2012-06-10 16:42:13',NULL),(396,NULL,NULL,'2012-06-10 16:42:16',NULL),(397,1,NULL,'2012-06-10 16:42:21','2012-06-10 16:42:39'),(398,NULL,NULL,'2012-06-10 16:42:59',NULL),(399,NULL,NULL,'2012-06-10 16:43:45',NULL),(400,NULL,NULL,'2012-06-10 16:44:08',NULL),(401,NULL,NULL,'2012-06-10 16:44:15',NULL),(402,NULL,NULL,'2012-06-10 16:44:31',NULL),(403,NULL,NULL,'2012-06-10 16:45:17',NULL),(404,NULL,NULL,'2012-06-10 16:46:17',NULL),(405,NULL,NULL,'2012-06-10 16:46:49',NULL),(406,NULL,NULL,'2012-06-10 16:47:35',NULL),(407,NULL,NULL,'2012-06-10 16:47:42',NULL),(408,NULL,NULL,'2012-06-10 16:48:22',NULL),(409,NULL,NULL,'2012-06-10 16:49:08',NULL),(410,NULL,NULL,'2012-06-10 16:49:54',NULL),(411,NULL,NULL,'2012-06-10 16:50:40',NULL),(412,NULL,NULL,'2012-06-10 16:50:56',NULL),(413,NULL,NULL,'2012-06-10 16:51:02',NULL),(414,NULL,NULL,'2012-06-10 16:51:26',NULL),(415,NULL,NULL,'2012-06-10 16:52:12',NULL),(416,NULL,NULL,'2012-06-10 16:52:58',NULL),(417,NULL,NULL,'2012-06-10 16:53:44',NULL),(418,NULL,NULL,'2012-06-10 16:54:00',NULL),(419,NULL,NULL,'2012-06-10 16:54:30',NULL),(420,NULL,NULL,'2012-06-10 16:55:16',NULL),(421,NULL,NULL,'2012-06-10 16:56:02',NULL),(422,NULL,NULL,'2012-06-10 16:56:49',NULL),(423,NULL,NULL,'2012-06-10 16:57:35',NULL),(424,NULL,NULL,'2012-06-10 16:58:21',NULL),(425,NULL,NULL,'2012-06-10 16:59:07',NULL),(426,NULL,NULL,'2012-06-10 16:59:53',NULL),(427,NULL,NULL,'2012-06-10 17:00:39',NULL),(428,NULL,NULL,'2012-06-10 17:01:25',NULL),(429,NULL,NULL,'2012-06-10 17:02:11',NULL),(430,NULL,NULL,'2012-06-10 17:02:25',NULL),(431,NULL,NULL,'2012-06-10 17:02:36',NULL),(432,NULL,NULL,'2012-06-10 17:02:57',NULL),(433,NULL,NULL,'2012-06-10 17:02:59',NULL),(434,NULL,NULL,'2012-06-10 17:03:43',NULL),(435,NULL,NULL,'2012-06-10 17:04:29',NULL),(436,NULL,NULL,'2012-06-10 17:05:16',NULL),(437,NULL,NULL,'2012-06-10 17:06:02',NULL),(438,NULL,NULL,'2012-06-10 17:06:03',NULL),(439,NULL,NULL,'2012-06-10 17:07:34',NULL),(440,NULL,NULL,'2012-06-10 17:08:20',NULL),(441,NULL,NULL,'2012-06-10 17:09:06',NULL),(442,NULL,NULL,'2012-06-10 17:09:52',NULL),(443,NULL,NULL,'2012-06-10 17:10:38',NULL),(444,NULL,NULL,'2012-06-10 17:11:24',NULL),(445,NULL,NULL,'2012-06-10 17:12:30',NULL),(446,NULL,NULL,'2012-06-10 17:12:56',NULL),(447,NULL,NULL,'2012-06-10 17:13:43',NULL),(448,NULL,NULL,'2012-06-10 17:14:29',NULL),(449,NULL,NULL,'2012-06-10 17:15:15',NULL),(450,NULL,NULL,'2012-06-10 17:16:01',NULL),(451,NULL,NULL,'2012-06-10 17:16:39',NULL),(452,NULL,NULL,'2012-06-10 17:16:47',NULL),(453,NULL,NULL,'2012-06-10 17:17:33',NULL),(454,NULL,NULL,'2012-06-10 17:18:19',NULL),(455,NULL,NULL,'2012-06-10 17:19:05',NULL),(456,NULL,NULL,'2012-06-10 17:19:51',NULL),(457,NULL,NULL,'2012-06-10 17:20:37',NULL),(458,NULL,NULL,'2012-06-10 17:21:24',NULL),(459,NULL,NULL,'2012-06-10 17:22:10',NULL),(460,NULL,NULL,'2012-06-10 17:22:56',NULL),(461,NULL,NULL,'2012-06-10 17:23:42',NULL),(462,NULL,NULL,'2012-06-10 17:24:28',NULL),(463,NULL,NULL,'2012-06-10 17:25:14',NULL),(464,NULL,NULL,'2012-06-10 17:25:33',NULL),(465,NULL,NULL,'2012-06-10 17:25:39',NULL),(466,NULL,NULL,'2012-06-10 17:25:45',NULL),(467,NULL,NULL,'2012-06-10 17:25:49',NULL),(468,NULL,NULL,'2012-06-10 17:26:00',NULL),(469,NULL,'0:0:0:0:0:0:0:1','2012-06-10 17:26:16',NULL),(470,NULL,NULL,'2012-06-10 17:26:46',NULL),(471,NULL,NULL,'2012-06-10 17:27:32',NULL),(472,NULL,NULL,'2012-06-10 17:28:04',NULL),(473,NULL,NULL,'2012-06-10 17:28:10',NULL),(474,NULL,NULL,'2012-06-10 17:28:18',NULL),(475,NULL,NULL,'2012-06-10 17:29:05',NULL),(476,NULL,NULL,'2012-06-10 17:29:11',NULL),(477,NULL,NULL,'2012-06-10 17:29:51',NULL),(478,NULL,NULL,'2012-06-10 17:30:37',NULL),(479,NULL,NULL,'2012-06-10 17:31:23',NULL),(480,NULL,NULL,'2012-06-10 17:32:09',NULL),(481,NULL,NULL,'2012-06-10 17:32:55',NULL),(482,NULL,NULL,'2012-06-10 17:33:41',NULL),(483,NULL,NULL,'2012-06-10 17:34:27',NULL),(484,NULL,NULL,'2012-06-10 17:35:13',NULL),(485,NULL,NULL,'2012-06-10 17:35:59',NULL),(486,NULL,NULL,'2012-06-10 17:36:45',NULL),(487,NULL,NULL,'2012-06-10 17:37:32',NULL),(488,NULL,NULL,'2012-06-10 17:38:18',NULL),(489,NULL,NULL,'2012-06-10 17:39:04',NULL),(490,NULL,NULL,'2012-06-10 17:39:50',NULL),(491,NULL,NULL,'2012-06-10 17:40:36',NULL),(492,NULL,NULL,'2012-06-10 17:41:22',NULL),(493,NULL,NULL,'2012-06-10 17:42:08',NULL),(494,NULL,NULL,'2012-06-10 17:42:16',NULL),(495,NULL,NULL,'2012-06-10 17:42:54',NULL),(496,NULL,NULL,'2012-06-10 17:43:40',NULL),(497,NULL,NULL,'2012-06-10 17:44:26',NULL),(498,NULL,NULL,'2012-06-10 17:45:12',NULL),(499,NULL,NULL,'2012-06-10 17:45:59',NULL),(500,NULL,NULL,'2012-06-10 17:46:45',NULL),(501,NULL,NULL,'2012-06-10 17:47:31',NULL),(502,NULL,NULL,'2012-06-10 17:48:10',NULL),(503,NULL,NULL,'2012-06-10 17:48:17',NULL),(504,NULL,NULL,'2012-06-10 17:48:50',NULL),(505,NULL,NULL,'2012-06-10 17:49:03',NULL),(506,NULL,NULL,'2012-06-10 17:49:49',NULL),(507,NULL,NULL,'2012-06-10 17:50:48',NULL),(508,NULL,NULL,'2012-06-10 17:51:21',NULL),(509,NULL,NULL,'2012-06-10 17:51:49',NULL),(510,NULL,NULL,'2012-06-10 17:52:07',NULL),(511,NULL,NULL,'2012-06-10 17:52:42',NULL),(512,NULL,NULL,'2012-06-10 17:52:53',NULL),(513,NULL,NULL,'2012-06-10 17:53:40',NULL),(514,NULL,NULL,'2012-06-10 17:54:26',NULL),(515,NULL,NULL,'2012-06-10 17:55:12',NULL),(516,NULL,NULL,'2012-06-10 17:55:58',NULL),(517,NULL,NULL,'2012-06-10 17:56:44',NULL),(518,NULL,NULL,'2012-06-10 17:57:30',NULL),(519,NULL,NULL,'2012-06-10 17:58:16',NULL),(520,NULL,NULL,'2012-06-10 17:59:02',NULL),(521,NULL,NULL,'2012-06-10 17:59:48',NULL),(522,NULL,NULL,'2012-06-10 18:00:34',NULL),(523,NULL,NULL,'2012-06-10 18:01:20',NULL),(524,NULL,NULL,'2012-06-10 18:02:07',NULL),(525,NULL,NULL,'2012-06-10 18:02:53',NULL),(526,NULL,NULL,'2012-06-10 18:03:39',NULL),(527,NULL,NULL,'2012-06-10 18:04:25',NULL),(528,NULL,NULL,'2012-06-10 18:05:11',NULL),(529,NULL,NULL,'2012-06-10 18:05:57',NULL),(530,NULL,NULL,'2012-06-10 18:06:43',NULL),(531,NULL,NULL,'2012-06-10 18:07:29',NULL),(532,NULL,NULL,'2012-06-10 18:07:54',NULL),(533,NULL,NULL,'2012-06-10 18:08:15',NULL),(534,NULL,NULL,'2012-06-10 18:09:01',NULL),(535,NULL,NULL,'2012-06-10 18:09:48',NULL),(536,NULL,NULL,'2012-06-10 18:10:34',NULL),(537,NULL,NULL,'2012-06-10 18:11:20',NULL),(538,NULL,NULL,'2012-06-10 18:12:06',NULL),(539,NULL,NULL,'2012-06-10 18:12:52',NULL),(540,NULL,NULL,'2012-06-10 18:13:38',NULL),(541,NULL,NULL,'2012-06-10 18:14:24',NULL),(542,NULL,NULL,'2012-06-10 18:15:10',NULL),(543,NULL,NULL,'2012-06-10 18:15:56',NULL),(544,NULL,NULL,'2012-06-10 18:16:42',NULL),(545,NULL,NULL,'2012-06-10 18:17:29',NULL),(546,NULL,NULL,'2012-06-10 18:18:15',NULL),(547,NULL,NULL,'2012-06-10 18:19:01',NULL),(548,NULL,NULL,'2012-06-10 18:19:12',NULL),(549,NULL,NULL,'2012-06-10 18:19:47',NULL),(550,NULL,NULL,'2012-06-10 18:20:33',NULL),(551,NULL,NULL,'2012-06-10 18:21:19',NULL),(552,NULL,NULL,'2012-06-10 18:22:05',NULL),(553,NULL,NULL,'2012-06-10 18:22:51',NULL),(554,NULL,NULL,'2012-06-10 18:23:37',NULL),(555,NULL,NULL,'2012-06-10 18:24:23',NULL),(556,NULL,NULL,'2012-06-10 18:25:09',NULL),(557,NULL,NULL,'2012-06-10 18:25:56',NULL),(558,NULL,NULL,'2012-06-10 18:26:42',NULL),(559,NULL,NULL,'2012-06-10 18:27:28',NULL),(560,NULL,NULL,'2012-06-10 18:28:20',NULL),(561,NULL,NULL,'2012-06-10 18:29:00',NULL),(562,NULL,NULL,'2012-06-10 18:29:46',NULL),(563,NULL,NULL,'2012-06-10 18:30:32',NULL),(564,NULL,NULL,'2012-06-10 18:31:18',NULL),(565,NULL,NULL,'2012-06-10 18:31:33',NULL),(566,NULL,NULL,'2012-06-10 18:32:04',NULL),(567,NULL,NULL,'2012-06-10 18:32:50',NULL),(568,NULL,NULL,'2012-06-10 18:33:31',NULL),(569,NULL,NULL,'2012-06-10 18:33:36',NULL),(570,NULL,NULL,'2012-06-10 18:34:23',NULL),(571,NULL,NULL,'2012-06-10 18:34:53',NULL),(572,NULL,NULL,'2012-06-10 18:34:55',NULL),(573,NULL,NULL,'2012-06-10 18:34:57',NULL),(574,NULL,NULL,'2012-06-10 18:34:59',NULL),(575,NULL,NULL,'2012-06-10 18:35:08',NULL),(576,NULL,NULL,'2012-06-10 18:35:55',NULL),(577,NULL,NULL,'2012-06-10 18:36:41',NULL),(578,NULL,NULL,'2012-06-10 18:37:27',NULL),(579,NULL,NULL,'2012-06-10 18:38:13',NULL),(580,NULL,NULL,'2012-06-10 18:38:59',NULL),(581,NULL,NULL,'2012-06-10 18:39:45',NULL),(582,NULL,NULL,'2012-06-10 18:40:31',NULL),(583,NULL,NULL,'2012-06-10 18:41:18',NULL),(584,NULL,NULL,'2012-06-10 18:42:04',NULL),(585,NULL,NULL,'2012-06-10 18:42:50',NULL),(586,NULL,NULL,'2012-06-10 18:43:36',NULL),(587,NULL,NULL,'2012-06-10 18:44:22',NULL),(588,NULL,NULL,'2012-06-10 18:45:08',NULL),(589,NULL,NULL,'2012-06-10 18:45:54',NULL),(590,NULL,NULL,'2012-06-10 18:45:56',NULL),(591,NULL,NULL,'2012-06-10 18:46:40',NULL),(592,NULL,NULL,'2012-06-10 18:47:26',NULL),(593,NULL,NULL,'2012-06-10 18:48:12',NULL),(594,NULL,NULL,'2012-06-10 18:48:59',NULL),(595,NULL,NULL,'2012-06-10 18:49:45',NULL),(596,NULL,NULL,'2012-06-10 18:50:31',NULL),(597,NULL,NULL,'2012-06-10 18:51:17',NULL),(598,NULL,NULL,'2012-06-10 18:52:03',NULL),(599,NULL,NULL,'2012-06-10 18:52:09',NULL),(600,NULL,NULL,'2012-06-10 18:52:49',NULL),(601,NULL,NULL,'2012-06-10 18:53:35',NULL),(602,NULL,NULL,'2012-06-10 18:54:21',NULL),(603,NULL,NULL,'2012-06-10 18:55:07',NULL),(604,NULL,NULL,'2012-06-10 18:55:53',NULL),(605,NULL,NULL,'2012-06-10 18:56:40',NULL),(606,NULL,NULL,'2012-06-10 18:57:26',NULL),(607,NULL,NULL,'2012-06-10 18:58:12',NULL),(608,NULL,NULL,'2012-06-10 18:58:58',NULL),(609,NULL,NULL,'2012-06-10 18:59:08',NULL),(610,NULL,NULL,'2012-06-10 18:59:36',NULL),(611,NULL,NULL,'2012-06-10 19:00:21',NULL),(612,NULL,NULL,'2012-06-10 19:00:40',NULL),(613,NULL,NULL,'2012-06-10 19:01:07',NULL),(614,NULL,NULL,'2012-06-10 19:01:53',NULL),(615,NULL,NULL,'2012-06-10 19:02:39',NULL),(616,NULL,NULL,'2012-06-10 19:03:26',NULL),(617,NULL,NULL,'2012-06-10 19:04:12',NULL),(618,NULL,NULL,'2012-06-10 19:04:58',NULL),(619,NULL,NULL,'2012-06-10 19:05:44',NULL),(620,NULL,NULL,'2012-06-10 19:06:30',NULL),(621,NULL,NULL,'2012-06-10 19:07:17',NULL),(622,NULL,NULL,'2012-06-10 19:08:03',NULL),(623,NULL,NULL,'2012-06-10 19:08:49',NULL),(624,NULL,NULL,'2012-06-10 19:08:59',NULL),(625,NULL,NULL,'2012-06-10 19:09:08',NULL),(626,NULL,NULL,'2012-06-10 19:09:19',NULL),(627,NULL,NULL,'2012-06-10 19:09:38',NULL),(628,NULL,NULL,'2012-06-10 19:09:57',NULL),(629,NULL,NULL,'2012-06-10 19:10:16',NULL),(630,NULL,NULL,'2012-06-10 19:10:35',NULL),(631,NULL,NULL,'2012-06-10 19:10:49',NULL),(632,NULL,NULL,'2012-06-10 19:11:05',NULL),(633,NULL,NULL,'2012-06-10 19:11:22',NULL),(634,NULL,NULL,'2012-06-10 19:11:38',NULL),(635,NULL,NULL,'2012-06-10 19:11:51',NULL),(636,NULL,NULL,'2012-06-10 19:12:05',NULL),(637,NULL,NULL,'2012-06-10 19:12:20',NULL),(638,NULL,NULL,'2012-06-10 19:12:34',NULL),(639,NULL,NULL,'2012-06-10 19:12:49',NULL),(640,NULL,NULL,'2012-06-10 19:13:03',NULL),(641,NULL,NULL,'2012-06-10 19:13:18',NULL),(642,NULL,NULL,'2012-06-10 19:13:32',NULL),(643,NULL,NULL,'2012-06-10 19:13:45',NULL),(644,NULL,NULL,'2012-06-10 19:13:58',NULL),(645,NULL,NULL,'2012-06-10 19:14:11',NULL),(646,NULL,NULL,'2012-06-10 19:14:25',NULL),(647,NULL,NULL,'2012-06-10 19:14:38',NULL),(648,NULL,NULL,'2012-06-10 19:14:51',NULL),(649,NULL,NULL,'2012-06-10 19:15:05',NULL),(650,NULL,NULL,'2012-06-10 19:15:18',NULL),(651,NULL,NULL,'2012-06-10 19:15:32',NULL),(652,NULL,NULL,'2012-06-10 19:15:45',NULL),(653,NULL,NULL,'2012-06-10 19:15:58',NULL),(654,NULL,NULL,'2012-06-10 19:16:12',NULL),(655,NULL,NULL,'2012-06-10 19:16:25',NULL),(656,NULL,NULL,'2012-06-10 19:16:38',NULL),(657,NULL,NULL,'2012-06-10 19:17:05',NULL),(658,NULL,NULL,'2012-06-10 19:17:19',NULL),(659,NULL,NULL,'2012-06-10 19:17:32',NULL),(660,NULL,NULL,'2012-06-10 19:17:45',NULL),(661,NULL,NULL,'2012-06-10 19:17:56',NULL),(662,NULL,NULL,'2012-06-10 19:17:59',NULL),(663,NULL,NULL,'2012-06-10 19:18:12',NULL),(664,NULL,NULL,'2012-06-10 19:18:25',NULL),(665,NULL,NULL,'2012-06-10 19:18:37',NULL),(666,NULL,NULL,'2012-06-10 19:18:39',NULL),(667,NULL,NULL,'2012-06-10 19:18:52',NULL),(668,NULL,NULL,'2012-06-10 19:19:06',NULL),(669,NULL,NULL,'2012-06-10 19:19:19',NULL),(670,NULL,NULL,'2012-06-10 19:19:33',NULL),(671,NULL,NULL,'2012-06-10 19:19:46',NULL),(672,NULL,NULL,'2012-06-10 19:19:48',NULL),(673,NULL,NULL,'2012-06-10 19:20:00',NULL),(674,NULL,NULL,'2012-06-10 19:20:13',NULL),(675,NULL,NULL,'2012-06-10 19:20:27',NULL),(676,NULL,NULL,'2012-06-10 19:20:40',NULL),(677,NULL,NULL,'2012-06-10 19:20:54',NULL),(678,NULL,NULL,'2012-06-10 19:21:07',NULL),(679,NULL,NULL,'2012-06-10 19:21:21',NULL),(680,NULL,NULL,'2012-06-10 19:21:34',NULL),(681,NULL,NULL,'2012-06-10 19:21:48',NULL),(682,NULL,NULL,'2012-06-10 19:22:02',NULL),(683,NULL,NULL,'2012-06-10 19:22:15',NULL),(684,NULL,NULL,'2012-06-10 19:22:29',NULL),(685,NULL,NULL,'2012-06-10 19:22:42',NULL),(686,NULL,NULL,'2012-06-10 19:22:56',NULL),(687,NULL,NULL,'2012-06-10 19:23:10',NULL),(688,NULL,NULL,'2012-06-10 19:23:23',NULL),(689,NULL,NULL,'2012-06-10 19:23:37',NULL),(690,NULL,NULL,'2012-06-10 19:23:51',NULL),(691,NULL,NULL,'2012-06-10 19:24:04',NULL),(692,NULL,NULL,'2012-06-10 19:24:18',NULL),(693,NULL,NULL,'2012-06-10 19:24:32',NULL),(694,NULL,NULL,'2012-06-10 19:24:45',NULL),(695,NULL,NULL,'2012-06-10 19:24:47',NULL),(696,NULL,NULL,'2012-06-10 19:24:59',NULL),(697,NULL,NULL,'2012-06-10 19:25:13',NULL),(698,NULL,NULL,'2012-06-10 19:25:27',NULL),(699,NULL,NULL,'2012-06-10 19:25:41',NULL),(700,NULL,NULL,'2012-06-10 19:25:54',NULL),(701,NULL,NULL,'2012-06-10 19:26:08',NULL),(702,NULL,NULL,'2012-06-10 19:26:22',NULL),(703,NULL,NULL,'2012-06-10 19:26:36',NULL),(704,NULL,NULL,'2012-06-10 19:26:50',NULL),(705,NULL,NULL,'2012-06-10 19:27:03',NULL),(706,NULL,NULL,'2012-06-10 19:27:17',NULL),(707,NULL,NULL,'2012-06-10 19:27:31',NULL),(708,NULL,NULL,'2012-06-10 19:27:45',NULL),(709,NULL,NULL,'2012-06-10 19:27:47',NULL),(710,NULL,NULL,'2012-06-10 19:27:59',NULL),(711,NULL,NULL,'2012-06-10 19:28:13',NULL),(712,NULL,NULL,'2012-06-10 19:28:27',NULL),(713,NULL,NULL,'2012-06-10 19:28:42',NULL),(714,NULL,NULL,'2012-06-10 19:28:49',NULL),(715,NULL,NULL,'2012-06-10 19:29:03',NULL),(716,NULL,NULL,'2012-06-10 19:29:09',NULL),(717,NULL,NULL,'2012-06-10 19:29:13',NULL),(718,NULL,NULL,'2012-06-10 19:29:15',NULL),(719,NULL,NULL,'2012-06-10 19:29:19',NULL),(720,NULL,NULL,'2012-06-10 19:29:22',NULL),(721,NULL,NULL,'2012-06-10 19:29:37',NULL),(722,NULL,NULL,'2012-06-10 19:29:38',NULL),(723,NULL,NULL,'2012-06-10 19:29:39',NULL),(724,NULL,NULL,'2012-06-10 19:29:42',NULL),(725,NULL,NULL,'2012-06-10 19:29:47',NULL),(726,NULL,NULL,'2012-06-10 19:29:48',NULL),(727,NULL,NULL,'2012-06-10 19:29:49',NULL),(728,NULL,NULL,'2012-06-10 19:29:51',NULL),(729,NULL,NULL,'2012-06-10 19:29:53',NULL),(730,NULL,NULL,'2012-06-10 19:30:05',NULL),(731,NULL,NULL,'2012-06-10 19:30:19',NULL),(732,NULL,NULL,'2012-06-10 19:30:33',NULL),(733,NULL,NULL,'2012-06-10 19:30:47',NULL),(734,NULL,NULL,'2012-06-10 19:31:01',NULL),(735,NULL,NULL,'2012-06-10 19:31:04',NULL),(736,NULL,NULL,'2012-06-10 19:31:15',NULL),(737,NULL,NULL,'2012-06-10 19:31:24',NULL),(738,NULL,NULL,'2012-06-10 19:31:26',NULL),(739,NULL,NULL,'2012-06-10 19:31:29',NULL),(740,NULL,NULL,'2012-06-10 19:31:43',NULL),(741,NULL,NULL,'2012-06-10 19:31:58',NULL),(742,NULL,NULL,'2012-06-10 19:32:12',NULL),(743,NULL,NULL,'2012-06-10 19:32:26',NULL),(744,NULL,NULL,'2012-06-10 19:32:38',NULL),(745,NULL,NULL,'2012-06-10 19:32:40',NULL),(746,NULL,NULL,'2012-06-10 19:32:54',NULL),(747,NULL,NULL,'2012-06-10 19:33:09',NULL),(748,NULL,NULL,'2012-06-10 19:33:23',NULL),(749,NULL,NULL,'2012-06-10 19:33:37',NULL),(750,NULL,NULL,'2012-06-10 19:33:43',NULL),(751,NULL,NULL,'2012-06-10 19:33:52',NULL),(752,NULL,NULL,'2012-06-10 19:34:06',NULL),(753,NULL,NULL,'2012-06-10 19:34:20',NULL),(754,NULL,NULL,'2012-06-10 19:34:25',NULL),(755,NULL,NULL,'2012-06-10 19:34:35',NULL),(756,NULL,NULL,'2012-06-10 19:34:49',NULL),(757,NULL,NULL,'2012-06-10 19:35:04',NULL),(758,NULL,NULL,'2012-06-10 19:35:18',NULL),(759,NULL,NULL,'2012-06-10 19:35:35',NULL),(760,NULL,NULL,'2012-06-10 19:38:04',NULL),(761,NULL,NULL,'2012-06-10 19:38:07',NULL),(762,NULL,NULL,'2012-06-10 19:38:09',NULL),(763,NULL,NULL,'2012-06-10 19:38:17',NULL),(764,NULL,NULL,'2012-06-10 19:39:14',NULL),(765,NULL,NULL,'2012-06-10 19:39:15',NULL),(766,NULL,NULL,'2012-06-10 19:39:42',NULL),(767,NULL,NULL,'2012-06-10 19:41:52',NULL),(768,NULL,NULL,'2012-06-10 19:42:02',NULL),(769,NULL,NULL,'2012-06-10 19:42:03',NULL),(770,NULL,NULL,'2012-06-10 19:42:41',NULL),(771,NULL,NULL,'2012-06-10 19:43:01',NULL),(772,NULL,NULL,'2012-06-10 19:43:10',NULL),(773,NULL,NULL,'2012-06-10 19:43:20',NULL),(774,NULL,NULL,'2012-06-10 19:43:21',NULL),(775,NULL,NULL,'2012-06-10 19:43:31',NULL),(776,NULL,NULL,'2012-06-10 19:43:41',NULL),(777,NULL,NULL,'2012-06-10 19:43:51',NULL),(778,NULL,NULL,'2012-06-10 19:44:01',NULL),(779,NULL,NULL,'2012-06-10 19:44:12',NULL),(780,NULL,NULL,'2012-06-10 19:44:22',NULL),(781,NULL,NULL,'2012-06-10 19:44:32',NULL),(782,NULL,NULL,'2012-06-10 19:44:42',NULL),(783,NULL,NULL,'2012-06-10 19:44:53',NULL),(784,NULL,NULL,'2012-06-10 19:45:03',NULL),(785,NULL,NULL,'2012-06-10 19:45:13',NULL),(786,NULL,NULL,'2012-06-10 19:45:24',NULL),(787,NULL,NULL,'2012-06-10 19:45:34',NULL),(788,NULL,NULL,'2012-06-10 19:45:44',NULL),(789,NULL,NULL,'2012-06-10 19:45:55',NULL),(790,NULL,NULL,'2012-06-10 19:46:05',NULL),(791,NULL,NULL,'2012-06-10 19:46:16',NULL),(792,NULL,NULL,'2012-06-10 19:46:27',NULL),(793,NULL,NULL,'2012-06-10 19:46:37',NULL),(794,NULL,NULL,'2012-06-10 19:46:47',NULL),(795,NULL,NULL,'2012-06-10 19:46:58',NULL),(796,NULL,NULL,'2012-06-10 19:47:08',NULL),(797,NULL,NULL,'2012-06-10 19:47:18',NULL),(798,NULL,NULL,'2012-06-10 19:47:29',NULL),(799,NULL,NULL,'2012-06-10 19:47:39',NULL),(800,NULL,NULL,'2012-06-10 19:47:50',NULL),(801,NULL,NULL,'2012-06-10 19:48:01',NULL),(802,NULL,NULL,'2012-06-10 19:48:11',NULL),(803,NULL,NULL,'2012-06-10 19:48:22',NULL),(804,NULL,NULL,'2012-06-10 19:48:33',NULL),(805,NULL,NULL,'2012-06-10 19:48:34',NULL),(806,NULL,NULL,'2012-06-10 19:48:43',NULL),(807,NULL,NULL,'2012-06-10 19:48:54',NULL),(808,NULL,NULL,'2012-06-10 19:49:06',NULL),(809,NULL,NULL,'2012-06-10 19:49:15',NULL),(810,NULL,NULL,'2012-06-10 19:49:26',NULL),(811,NULL,NULL,'2012-06-10 19:49:37',NULL),(812,NULL,NULL,'2012-06-10 19:49:48',NULL),(813,NULL,NULL,'2012-06-10 19:49:58',NULL),(814,NULL,NULL,'2012-06-10 19:50:09',NULL),(815,NULL,NULL,'2012-06-10 19:50:20',NULL),(816,NULL,NULL,'2012-06-10 19:50:22',NULL),(817,NULL,NULL,'2012-06-10 19:50:31',NULL),(818,NULL,NULL,'2012-06-10 19:50:42',NULL),(819,NULL,NULL,'2012-06-10 19:50:53',NULL),(820,NULL,NULL,'2012-06-10 19:51:04',NULL),(821,NULL,NULL,'2012-06-10 19:51:15',NULL),(822,NULL,NULL,'2012-06-10 19:51:20',NULL),(823,NULL,NULL,'2012-06-10 19:51:26',NULL),(824,NULL,NULL,'2012-06-10 19:51:36',NULL),(825,NULL,NULL,'2012-06-10 19:51:38',NULL),(826,NULL,NULL,'2012-06-10 19:51:49',NULL),(827,NULL,NULL,'2012-06-10 19:51:52',NULL),(828,NULL,NULL,'2012-06-10 19:51:59',NULL),(829,NULL,NULL,'2012-06-10 19:52:00',NULL),(830,NULL,NULL,'2012-06-10 19:52:11',NULL),(831,NULL,NULL,'2012-06-10 19:52:22',NULL),(832,NULL,NULL,'2012-06-10 19:52:34',NULL),(833,NULL,NULL,'2012-06-10 19:52:45',NULL),(834,NULL,NULL,'2012-06-10 19:52:57',NULL),(835,NULL,NULL,'2012-06-10 19:53:08',NULL),(836,NULL,NULL,'2012-06-10 19:53:20',NULL),(837,NULL,NULL,'2012-06-10 19:53:31',NULL),(838,NULL,NULL,'2012-06-10 19:53:43',NULL),(839,NULL,NULL,'2012-06-10 19:53:54',NULL),(840,NULL,NULL,'2012-06-10 19:54:06',NULL),(841,NULL,NULL,'2012-06-10 19:54:18',NULL),(842,NULL,NULL,'2012-06-10 19:54:30',NULL),(843,NULL,NULL,'2012-06-10 19:54:42',NULL),(844,NULL,NULL,'2012-06-10 19:54:54',NULL),(845,NULL,NULL,'2012-06-10 19:55:06',NULL),(846,NULL,NULL,'2012-06-10 19:55:19',NULL),(847,NULL,NULL,'2012-06-10 19:55:21',NULL),(848,NULL,NULL,'2012-06-10 19:55:23',NULL),(849,NULL,NULL,'2012-06-10 19:55:31',NULL),(850,NULL,NULL,'2012-06-10 19:55:35',NULL),(851,NULL,NULL,'2012-06-10 19:55:44',NULL),(852,NULL,NULL,'2012-06-10 19:55:56',NULL),(853,NULL,NULL,'2012-06-10 19:56:09',NULL),(854,NULL,NULL,'2012-06-10 19:56:22',NULL),(855,NULL,NULL,'2012-06-10 19:56:44',NULL),(856,NULL,NULL,'2012-06-10 19:56:53',NULL),(857,NULL,NULL,'2012-06-10 19:56:58',NULL),(858,NULL,NULL,'2012-06-10 19:57:14',NULL),(859,NULL,NULL,'2012-06-10 19:57:31',NULL),(860,NULL,NULL,'2012-06-10 19:57:52',NULL),(861,NULL,NULL,'2012-06-10 19:58:25',NULL),(862,NULL,NULL,'2012-06-10 19:59:15',NULL),(863,NULL,NULL,'2012-06-10 20:00:51',NULL),(864,NULL,NULL,'2012-06-10 20:02:27',NULL),(865,NULL,NULL,'2012-06-10 20:04:03',NULL),(866,NULL,NULL,'2012-06-10 20:04:07',NULL),(867,NULL,NULL,'2012-06-10 20:04:18',NULL),(868,NULL,NULL,'2012-06-10 20:04:38',NULL),(869,NULL,NULL,'2012-06-10 20:04:39',NULL),(870,NULL,NULL,'2012-06-10 20:05:39',NULL),(871,NULL,NULL,'2012-06-10 20:06:01',NULL),(872,NULL,NULL,'2012-06-10 20:07:15',NULL),(873,NULL,NULL,'2012-06-10 20:08:51',NULL),(874,NULL,NULL,'2012-06-10 20:10:26',NULL),(875,NULL,NULL,'2012-06-10 20:12:02',NULL),(876,NULL,NULL,'2012-06-10 20:13:38',NULL),(877,NULL,NULL,'2012-06-10 20:14:48',NULL),(878,NULL,NULL,'2012-06-10 20:15:14',NULL),(879,NULL,NULL,'2012-06-10 20:16:00',NULL),(880,NULL,NULL,'2012-06-10 20:16:50',NULL),(881,NULL,NULL,'2012-06-10 20:17:03',NULL),(882,NULL,NULL,'2012-06-10 20:17:09',NULL),(883,NULL,NULL,'2012-06-10 20:17:21',NULL),(884,NULL,NULL,'2012-06-10 20:17:24',NULL),(885,NULL,NULL,'2012-06-10 20:17:48',NULL),(886,NULL,NULL,'2012-06-10 20:17:52',NULL),(887,NULL,NULL,'2012-06-10 20:17:57',NULL),(888,NULL,NULL,'2012-06-10 20:18:03',NULL),(889,NULL,NULL,'2012-06-10 20:18:14',NULL),(890,NULL,NULL,'2012-06-10 20:18:24',NULL),(891,NULL,NULL,'2012-06-10 20:18:26',NULL),(892,NULL,NULL,'2012-06-10 20:18:28',NULL),(893,NULL,NULL,'2012-06-10 20:18:32',NULL),(894,NULL,NULL,'2012-06-10 20:20:02',NULL),(895,NULL,NULL,'2012-06-10 20:21:38',NULL),(896,NULL,NULL,'2012-06-10 20:22:10',NULL),(897,NULL,NULL,'2012-06-10 20:22:15',NULL),(898,NULL,NULL,'2012-06-10 20:22:16',NULL),(899,NULL,NULL,'2012-06-10 20:22:17',NULL),(900,NULL,NULL,'2012-06-10 20:22:35',NULL),(901,NULL,NULL,'2012-06-10 20:22:36',NULL),(902,NULL,NULL,'2012-06-10 20:23:14',NULL),(903,NULL,NULL,'2012-06-10 20:26:16',NULL),(904,NULL,NULL,'2012-06-10 20:26:32',NULL),(905,NULL,NULL,'2012-06-10 20:26:32',NULL),(906,NULL,NULL,'2012-06-10 20:26:33',NULL),(907,NULL,NULL,'2012-06-10 20:26:34',NULL),(908,NULL,NULL,'2012-06-10 20:26:35',NULL),(909,NULL,NULL,'2012-06-10 20:28:02',NULL),(910,NULL,NULL,'2012-06-10 20:29:37',NULL),(911,NULL,NULL,'2012-06-10 20:30:19',NULL),(912,NULL,NULL,'2012-06-10 20:30:23',NULL),(913,NULL,NULL,'2012-06-10 20:30:30',NULL),(914,NULL,NULL,'2012-06-10 20:30:33',NULL),(915,NULL,NULL,'2012-06-10 20:30:39',NULL),(916,NULL,NULL,'2012-06-10 20:31:13',NULL),(917,NULL,NULL,'2012-06-10 20:32:49',NULL),(918,NULL,NULL,'2012-06-10 20:34:25',NULL),(919,NULL,NULL,'2012-06-10 20:36:01',NULL),(920,NULL,NULL,'2012-06-10 20:36:44',NULL),(921,NULL,NULL,'2012-06-10 20:36:47',NULL),(922,NULL,NULL,'2012-06-10 20:37:18',NULL),(923,NULL,NULL,'2012-06-10 20:37:20',NULL),(924,NULL,NULL,'2012-06-10 20:37:26',NULL),(925,NULL,NULL,'2012-06-10 20:37:37',NULL),(926,NULL,NULL,'2012-06-10 20:38:00',NULL),(927,NULL,NULL,'2012-06-10 20:38:01',NULL),(928,NULL,NULL,'2012-06-10 20:38:03',NULL),(929,NULL,NULL,'2012-06-10 20:39:13',NULL),(930,NULL,NULL,'2012-06-10 20:40:49',NULL),(931,NULL,NULL,'2012-06-10 20:41:37',NULL),(932,NULL,NULL,'2012-06-10 20:42:25',NULL),(933,NULL,NULL,'2012-06-10 20:44:01',NULL),(934,NULL,NULL,'2012-06-10 20:44:56',NULL),(935,NULL,NULL,'2012-06-10 20:45:20',NULL),(936,NULL,NULL,'2012-06-10 20:45:37',NULL),(937,NULL,NULL,'2012-06-10 20:47:05',NULL),(938,NULL,NULL,'2012-06-10 20:47:06',NULL),(939,NULL,NULL,'2012-06-10 20:47:13',NULL),(940,NULL,NULL,'2012-06-10 20:48:41',NULL),(941,NULL,NULL,'2012-06-10 20:48:43',NULL),(942,NULL,NULL,'2012-06-10 20:48:44',NULL),(943,NULL,NULL,'2012-06-10 20:48:46',NULL),(944,NULL,NULL,'2012-06-10 20:48:48',NULL),(945,NULL,NULL,'2012-06-10 20:50:24',NULL),(946,NULL,NULL,'2012-06-10 20:51:28',NULL),(947,NULL,NULL,'2012-06-10 20:52:00',NULL),(948,NULL,NULL,'2012-06-10 20:53:36',NULL),(949,NULL,NULL,'2012-06-10 20:55:12',NULL),(950,NULL,NULL,'2012-06-10 20:55:19',NULL),(951,NULL,NULL,'2012-06-10 20:55:28',NULL),(952,NULL,NULL,'2012-06-10 20:56:48',NULL),(953,NULL,NULL,'2012-06-10 20:57:28',NULL),(954,NULL,NULL,'2012-06-10 20:58:24',NULL),(955,NULL,NULL,'2012-06-10 20:59:20',NULL),(956,NULL,NULL,'2012-06-10 21:00:00',NULL),(957,NULL,NULL,'2012-06-10 21:01:36',NULL),(958,NULL,NULL,'2012-06-10 21:02:34',NULL),(959,NULL,NULL,'2012-06-10 21:02:36',NULL),(960,NULL,NULL,'2012-06-10 21:04:48',NULL),(961,NULL,NULL,'2012-06-10 21:06:24',NULL),(962,NULL,NULL,'2012-06-10 21:06:38',NULL),(963,NULL,NULL,'2012-06-10 21:06:46',NULL),(964,NULL,NULL,'2012-06-10 21:06:47',NULL),(965,NULL,NULL,'2012-06-10 21:06:57',NULL),(966,NULL,NULL,'2012-06-10 21:07:08',NULL),(967,NULL,NULL,'2012-06-10 21:07:14',NULL),(968,NULL,NULL,'2012-06-10 21:07:21',NULL),(969,NULL,NULL,'2012-06-10 21:07:27',NULL),(970,NULL,NULL,'2012-06-10 21:07:29',NULL),(971,NULL,NULL,'2012-06-10 21:07:35',NULL),(972,NULL,NULL,'2012-06-10 21:07:59',NULL),(973,NULL,NULL,'2012-06-10 21:08:36',NULL),(974,NULL,NULL,'2012-06-10 21:08:58',NULL),(975,NULL,NULL,'2012-06-10 21:09:35',NULL),(976,NULL,NULL,'2012-06-10 21:11:11',NULL),(977,NULL,NULL,'2012-06-10 21:14:08',NULL),(978,NULL,NULL,'2012-06-10 21:14:19',NULL),(979,NULL,NULL,'2012-06-10 21:14:19',NULL),(980,NULL,NULL,'2012-06-10 21:14:21',NULL),(981,NULL,NULL,'2012-06-10 21:14:21',NULL),(982,NULL,NULL,'2012-06-10 21:14:23',NULL),(983,NULL,NULL,'2012-06-10 21:14:23',NULL),(984,NULL,NULL,'2012-06-10 21:14:24',NULL),(985,NULL,NULL,'2012-06-10 21:14:32',NULL),(986,NULL,NULL,'2012-06-10 21:17:35',NULL),(987,NULL,NULL,'2012-06-10 21:19:11',NULL),(988,NULL,NULL,'2012-06-10 21:20:11',NULL),(989,NULL,NULL,'2012-06-10 21:20:42',NULL),(990,NULL,NULL,'2012-06-10 21:20:47',NULL),(991,NULL,NULL,'2012-06-10 21:20:50',NULL),(992,NULL,NULL,'2012-06-10 21:20:54',NULL),(993,NULL,NULL,'2012-06-10 21:21:46',NULL),(994,NULL,NULL,'2012-06-10 21:21:48',NULL),(995,NULL,NULL,'2012-06-10 21:23:58',NULL),(996,NULL,NULL,'2012-06-10 21:23:59',NULL),(997,NULL,NULL,'2012-06-10 21:24:14',NULL),(998,NULL,NULL,'2012-06-10 21:25:34',NULL),(999,NULL,NULL,'2012-06-10 21:28:46',NULL),(1000,NULL,NULL,'2012-06-10 21:30:22',NULL),(1001,NULL,NULL,'2012-06-10 21:31:00',NULL),(1002,NULL,NULL,'2012-06-10 21:31:20',NULL),(1003,NULL,NULL,'2012-06-10 21:31:41',NULL),(1004,NULL,NULL,'2012-06-10 21:31:55',NULL),(1005,NULL,NULL,'2012-06-10 21:32:28',NULL),(1006,NULL,NULL,'2012-06-10 21:32:52',NULL),(1007,NULL,NULL,'2012-06-10 21:33:34',NULL),(1008,NULL,NULL,'2012-06-10 21:34:02',NULL),(1009,NULL,NULL,'2012-06-10 21:35:10',NULL),(1010,NULL,NULL,'2012-06-10 21:36:46',NULL),(1011,NULL,NULL,'2012-06-10 21:38:14',NULL),(1012,NULL,NULL,'2012-06-10 21:38:22',NULL),(1013,NULL,NULL,'2012-06-10 21:39:58',NULL),(1014,NULL,NULL,'2012-06-10 21:40:34',NULL),(1015,NULL,NULL,'2012-06-10 21:41:14',NULL),(1016,NULL,NULL,'2012-06-10 21:41:24',NULL),(1017,NULL,NULL,'2012-06-10 21:41:50',NULL),(1018,NULL,NULL,'2012-06-10 21:43:09',NULL),(1019,NULL,NULL,'2012-06-10 21:44:45',NULL),(1020,NULL,NULL,'2012-06-10 21:47:57',NULL),(1021,NULL,NULL,'2012-06-10 21:49:33',NULL),(1022,NULL,NULL,'2012-06-10 21:51:09',NULL),(1023,NULL,NULL,'2012-06-10 21:52:45',NULL),(1024,NULL,NULL,'2012-06-10 21:54:21',NULL),(1025,NULL,NULL,'2012-06-10 21:55:19',NULL),(1026,NULL,NULL,'2012-06-10 21:55:21',NULL),(1027,NULL,NULL,'2012-06-10 21:55:23',NULL),(1028,NULL,NULL,'2012-06-10 21:55:27',NULL),(1029,NULL,NULL,'2012-06-10 21:55:31',NULL),(1030,NULL,NULL,'2012-06-10 21:55:47',NULL),(1031,NULL,NULL,'2012-06-10 21:55:57',NULL),(1032,NULL,NULL,'2012-06-10 21:57:33',NULL),(1033,NULL,NULL,'2012-06-10 21:58:29',NULL),(1034,NULL,NULL,'2012-06-10 21:58:35',NULL),(1035,NULL,NULL,'2012-06-10 21:58:39',NULL),(1036,NULL,NULL,'2012-06-10 21:58:42',NULL),(1037,NULL,NULL,'2012-06-10 21:58:43',NULL),(1038,NULL,NULL,'2012-06-10 21:58:57',NULL),(1039,NULL,NULL,'2012-06-10 21:59:09',NULL),(1040,NULL,NULL,'2012-06-10 22:00:28',NULL),(1041,NULL,NULL,'2012-06-10 22:01:56',NULL),(1042,NULL,NULL,'2012-06-10 22:02:15',NULL),(1043,NULL,NULL,'2012-06-10 22:02:16',NULL),(1044,NULL,NULL,'2012-06-10 22:02:22',NULL),(1045,NULL,NULL,'2012-06-10 22:03:24',NULL),(1046,NULL,NULL,'2012-06-10 22:04:51',NULL),(1047,NULL,NULL,'2012-06-10 22:06:19',NULL),(1048,NULL,NULL,'2012-06-10 22:06:33',NULL),(1049,NULL,NULL,'2012-06-10 22:06:34',NULL),(1050,NULL,NULL,'2012-06-10 22:07:47',NULL),(1051,NULL,NULL,'2012-06-10 22:09:14',NULL),(1052,NULL,NULL,'2012-06-10 22:10:42',NULL),(1053,NULL,NULL,'2012-06-10 22:12:10',NULL),(1054,NULL,NULL,'2012-06-10 22:12:32',NULL),(1055,NULL,NULL,'2012-06-10 22:13:38',NULL),(1056,NULL,NULL,'2012-06-10 22:14:07',NULL),(1057,NULL,NULL,'2012-06-10 22:15:05',NULL),(1058,NULL,NULL,'2012-06-10 22:15:22',NULL),(1059,NULL,NULL,'2012-06-10 22:16:33',NULL),(1060,NULL,NULL,'2012-06-10 22:16:42',NULL),(1061,NULL,NULL,'2012-06-10 22:17:18',NULL),(1062,NULL,NULL,'2012-06-10 22:18:01',NULL),(1063,NULL,NULL,'2012-06-10 22:21:38',NULL),(1064,NULL,NULL,'2012-06-10 22:22:53',NULL),(1065,NULL,NULL,'2012-06-10 22:24:06',NULL),(1066,NULL,NULL,'2012-06-10 22:24:55',NULL),(1067,NULL,NULL,'2012-06-10 22:26:22',NULL),(1068,NULL,NULL,'2012-06-10 22:26:48',NULL),(1069,NULL,NULL,'2012-06-10 22:27:48',NULL),(1070,NULL,NULL,'2012-06-10 22:30:13',NULL),(1071,NULL,NULL,'2012-06-10 22:31:14',NULL),(1072,NULL,NULL,'2012-06-10 22:32:49',NULL),(1073,NULL,NULL,'2012-06-10 22:34:24',NULL),(1074,NULL,NULL,'2012-06-10 22:35:59',NULL),(1075,NULL,NULL,'2012-06-10 22:37:33',NULL),(1076,NULL,NULL,'2012-06-10 22:39:08',NULL),(1077,NULL,NULL,'2012-06-10 22:40:43',NULL),(1078,NULL,NULL,'2012-06-10 22:42:02',NULL),(1079,NULL,NULL,'2012-06-10 22:42:18',NULL),(1080,NULL,NULL,'2012-06-10 22:43:52',NULL),(1081,NULL,NULL,'2012-06-10 22:44:32',NULL),(1082,NULL,NULL,'2012-06-10 22:45:11',NULL),(1083,NULL,NULL,'2012-06-10 22:45:27',NULL),(1084,NULL,NULL,'2012-06-10 22:47:02',NULL),(1085,NULL,NULL,'2012-06-10 22:48:36',NULL),(1086,NULL,NULL,'2012-06-10 22:49:27',NULL),(1087,NULL,NULL,'2012-06-10 22:49:43',NULL),(1088,NULL,NULL,'2012-06-10 22:50:11',NULL),(1089,NULL,NULL,'2012-06-10 22:51:46',NULL),(1090,NULL,NULL,'2012-06-10 22:53:01',NULL),(1091,NULL,NULL,'2012-06-10 22:53:20',NULL),(1092,NULL,NULL,'2012-06-10 22:54:55',NULL),(1093,NULL,NULL,'2012-06-10 22:55:22',NULL),(1094,NULL,NULL,'2012-06-10 22:56:30',NULL),(1095,NULL,NULL,'2012-06-10 22:56:31',NULL),(1096,NULL,NULL,'2012-06-10 22:58:05',NULL),(1097,NULL,NULL,'2012-06-10 22:59:39',NULL),(1098,NULL,NULL,'2012-06-10 22:59:54',NULL),(1099,NULL,NULL,'2012-06-10 22:59:56',NULL),(1100,NULL,NULL,'2012-06-10 23:01:14',NULL),(1101,NULL,NULL,'2012-06-10 23:01:30',NULL),(1102,NULL,NULL,'2012-06-10 23:02:49',NULL),(1103,NULL,NULL,'2012-06-10 23:04:24',NULL),(1104,NULL,NULL,'2012-06-10 23:05:17',NULL),(1105,NULL,NULL,'2012-06-10 23:05:28',NULL),(1106,NULL,NULL,'2012-06-10 23:05:30',NULL),(1107,NULL,NULL,'2012-06-10 23:05:58',NULL),(1108,NULL,NULL,'2012-06-10 23:06:51',NULL),(1109,NULL,NULL,'2012-06-10 23:07:33',NULL),(1110,NULL,NULL,'2012-06-10 23:09:08',NULL),(1111,NULL,NULL,'2012-06-10 23:10:42',NULL),(1112,NULL,NULL,'2012-06-10 23:11:40',NULL),(1113,NULL,NULL,'2012-06-10 23:12:17',NULL),(1114,NULL,NULL,'2012-06-10 23:13:25',NULL),(1115,NULL,NULL,'2012-06-10 23:13:52',NULL),(1116,NULL,NULL,'2012-06-10 23:15:20',NULL),(1117,NULL,NULL,'2012-06-10 23:15:26',NULL),(1118,NULL,NULL,'2012-06-10 23:16:15',NULL),(1119,NULL,NULL,'2012-06-10 23:16:34',NULL),(1120,NULL,NULL,'2012-06-10 23:17:01',NULL),(1121,NULL,NULL,'2012-06-10 23:18:36',NULL),(1122,NULL,NULL,'2012-06-10 23:20:11',NULL),(1123,NULL,NULL,'2012-06-10 23:21:45',NULL),(1124,NULL,NULL,'2012-06-10 23:22:19',NULL),(1125,NULL,NULL,'2012-06-10 23:22:19',NULL),(1126,NULL,NULL,'2012-06-10 23:22:33',NULL),(1127,NULL,NULL,'2012-06-10 23:23:20',NULL),(1128,NULL,NULL,'2012-06-10 23:23:24',NULL),(1129,NULL,NULL,'2012-06-10 23:24:49',NULL),(1130,NULL,NULL,'2012-06-10 23:24:50',NULL),(1131,NULL,NULL,'2012-06-10 23:24:55',NULL),(1132,NULL,NULL,'2012-06-10 23:26:30',NULL),(1133,NULL,NULL,'2012-06-10 23:27:55',NULL),(1134,NULL,NULL,'2012-06-10 23:28:05',NULL),(1135,NULL,NULL,'2012-06-10 23:29:39',NULL),(1136,NULL,NULL,'2012-06-10 23:31:14',NULL),(1137,NULL,NULL,'2012-06-10 23:32:48',NULL),(1138,NULL,NULL,'2012-06-10 23:34:23',NULL),(1139,NULL,NULL,'2012-06-10 23:35:58',NULL),(1140,NULL,NULL,'2012-06-10 23:36:31',NULL),(1141,NULL,NULL,'2012-06-10 23:36:53',NULL),(1142,NULL,NULL,'2012-06-10 23:37:32',NULL),(1143,NULL,NULL,'2012-06-10 23:39:07',NULL),(1144,NULL,NULL,'2012-06-10 23:40:42',NULL),(1145,NULL,NULL,'2012-06-10 23:40:58',NULL),(1146,NULL,NULL,'2012-06-10 23:42:17',NULL),(1147,NULL,NULL,'2012-06-10 23:43:51',NULL),(1148,NULL,NULL,'2012-06-10 23:44:09',NULL),(1149,NULL,NULL,'2012-06-10 23:44:37',NULL),(1150,NULL,NULL,'2012-06-10 23:45:26',NULL),(1151,NULL,NULL,'2012-06-10 23:46:06',NULL),(1152,NULL,NULL,'2012-06-10 23:46:45',NULL),(1153,NULL,NULL,'2012-06-10 23:46:47',NULL),(1154,NULL,NULL,'2012-06-10 23:47:01',NULL),(1155,NULL,NULL,'2012-06-10 23:48:33',NULL),(1156,NULL,NULL,'2012-06-10 23:48:35',NULL),(1157,NULL,NULL,'2012-06-10 23:50:10',NULL),(1158,NULL,NULL,'2012-06-10 23:51:45',NULL),(1159,NULL,NULL,'2012-06-10 23:54:19',NULL),(1160,NULL,NULL,'2012-06-10 23:54:55',NULL),(1161,NULL,NULL,'2012-06-10 23:56:29',NULL),(1162,NULL,NULL,'2012-06-10 23:58:04',NULL),(1163,NULL,NULL,'2012-06-10 23:58:19',NULL),(1164,NULL,NULL,'2012-06-10 23:59:10',NULL),(1165,NULL,NULL,'2012-06-10 23:59:39',NULL),(1166,NULL,NULL,'2012-06-11 00:00:49',NULL),(1167,NULL,NULL,'2012-06-11 00:01:13',NULL),(1168,NULL,NULL,'2012-06-11 00:02:48',NULL),(1169,NULL,NULL,'2012-06-11 00:02:55',NULL),(1170,NULL,NULL,'2012-06-11 00:03:01',NULL),(1171,NULL,NULL,'2012-06-11 00:04:23',NULL),(1172,NULL,NULL,'2012-06-11 00:06:35',NULL),(1173,NULL,NULL,'2012-06-11 00:07:28',NULL),(1174,NULL,NULL,'2012-06-11 00:07:32',NULL),(1175,NULL,NULL,'2012-06-11 00:09:07',NULL),(1176,NULL,NULL,'2012-06-11 00:10:42',NULL),(1177,NULL,NULL,'2012-06-11 00:12:17',NULL),(1178,NULL,NULL,'2012-06-11 00:12:49',NULL),(1179,NULL,NULL,'2012-06-11 00:13:51',NULL),(1180,NULL,NULL,'2012-06-11 00:15:26',NULL),(1181,NULL,NULL,'2012-06-11 00:17:01',NULL),(1182,NULL,NULL,'2012-06-11 00:18:35',NULL),(1183,NULL,NULL,'2012-06-11 00:20:10',NULL),(1184,NULL,NULL,'2012-06-11 00:20:26',NULL),(1185,NULL,NULL,'2012-06-11 00:20:33',NULL),(1186,NULL,NULL,'2012-06-11 00:21:45',NULL),(1187,NULL,NULL,'2012-06-11 00:23:19',NULL),(1188,NULL,NULL,'2012-06-11 00:24:54',NULL),(1189,NULL,NULL,'2012-06-11 00:25:12',NULL),(1190,NULL,NULL,'2012-06-11 00:25:18',NULL),(1191,NULL,NULL,'2012-06-11 00:25:32',NULL),(1192,NULL,NULL,'2012-06-11 00:26:29',NULL),(1193,NULL,NULL,'2012-06-11 00:28:04',NULL),(1194,NULL,NULL,'2012-06-11 00:29:33',NULL),(1195,NULL,NULL,'2012-06-11 00:29:38',NULL),(1196,NULL,NULL,'2012-06-11 00:30:39',NULL),(1197,NULL,NULL,'2012-06-11 00:30:40',NULL),(1198,NULL,NULL,'2012-06-11 00:30:41',NULL),(1199,NULL,NULL,'2012-06-11 00:30:51',NULL),(1200,NULL,NULL,'2012-06-11 00:31:04',NULL),(1201,NULL,NULL,'2012-06-11 00:31:13',NULL),(1202,NULL,NULL,'2012-06-11 00:32:24',NULL),(1203,NULL,NULL,'2012-06-11 00:32:30',NULL),(1204,NULL,NULL,'2012-06-11 00:32:31',NULL),(1205,NULL,NULL,'2012-06-11 00:32:37',NULL),(1206,NULL,NULL,'2012-06-11 00:32:39',NULL),(1207,NULL,NULL,'2012-06-11 00:32:47',NULL),(1208,NULL,NULL,'2012-06-11 00:33:41',NULL),(1209,NULL,NULL,'2012-06-11 00:33:43',NULL),(1210,NULL,NULL,'2012-06-11 00:34:22',NULL),(1211,NULL,NULL,'2012-06-11 00:35:57',NULL),(1212,NULL,NULL,'2012-06-11 00:36:34',NULL),(1213,NULL,NULL,'2012-06-11 00:36:48',NULL),(1214,NULL,NULL,'2012-06-11 00:37:32',NULL),(1215,NULL,NULL,'2012-06-11 00:39:07',NULL),(1216,NULL,NULL,'2012-06-11 00:40:41',NULL),(1217,NULL,NULL,'2012-06-11 00:42:16',NULL),(1218,NULL,NULL,'2012-06-11 00:43:51',NULL),(1219,NULL,NULL,'2012-06-11 00:45:12',NULL),(1220,NULL,NULL,'2012-06-11 00:45:25',NULL),(1221,NULL,NULL,'2012-06-11 00:47:00',NULL),(1222,NULL,NULL,'2012-06-11 00:48:35',NULL),(1223,NULL,NULL,'2012-06-11 00:50:10',NULL),(1224,NULL,NULL,'2012-06-11 00:51:13',NULL),(1225,NULL,NULL,'2012-06-11 00:51:44',NULL),(1226,NULL,NULL,'2012-06-11 00:52:27',NULL),(1227,NULL,NULL,'2012-06-11 00:53:19',NULL),(1228,NULL,NULL,'2012-06-11 00:53:47',NULL),(1229,NULL,NULL,'2012-06-11 00:54:54',NULL),(1230,NULL,NULL,'2012-06-11 00:55:59',NULL),(1231,NULL,NULL,'2012-06-11 00:56:28',NULL),(1232,NULL,NULL,'2012-06-11 00:57:49',NULL),(1233,NULL,NULL,'2012-06-11 00:58:03',NULL),(1234,NULL,NULL,'2012-06-11 00:59:38',NULL),(1235,NULL,NULL,'2012-06-11 01:01:13',NULL),(1236,NULL,NULL,'2012-06-11 01:01:50',NULL),(1237,NULL,NULL,'2012-06-11 01:02:47',NULL),(1238,NULL,NULL,'2012-06-11 01:04:21',NULL),(1239,NULL,NULL,'2012-06-11 01:05:56',NULL),(1240,NULL,NULL,'2012-06-11 01:07:30',NULL),(1241,NULL,NULL,'2012-06-11 01:09:04',NULL),(1242,NULL,NULL,'2012-06-11 01:10:38',NULL),(1243,NULL,NULL,'2012-06-11 01:12:09',NULL),(1244,NULL,NULL,'2012-06-11 01:12:12',NULL),(1245,NULL,NULL,'2012-06-11 01:13:47',NULL),(1246,NULL,NULL,'2012-06-11 01:15:21',NULL),(1247,NULL,NULL,'2012-06-11 01:16:56',NULL),(1248,NULL,NULL,'2012-06-11 01:18:30',NULL),(1249,NULL,NULL,'2012-06-11 01:20:04',NULL),(1250,NULL,NULL,'2012-06-11 01:21:39',NULL),(1251,NULL,NULL,'2012-06-11 01:23:13',NULL),(1252,NULL,NULL,'2012-06-11 01:23:26',NULL),(1253,NULL,NULL,'2012-06-11 01:24:28',NULL),(1254,NULL,NULL,'2012-06-11 01:24:48',NULL),(1255,NULL,NULL,'2012-06-11 01:26:22',NULL),(1256,NULL,NULL,'2012-06-11 01:27:58',NULL),(1257,NULL,NULL,'2012-06-11 01:29:32',NULL),(1258,NULL,NULL,'2012-06-11 01:31:07',NULL),(1259,NULL,NULL,'2012-06-11 01:31:51',NULL),(1260,NULL,NULL,'2012-06-11 01:32:42',NULL),(1261,NULL,NULL,'2012-06-11 01:34:17',NULL),(1262,NULL,NULL,'2012-06-11 01:34:32',NULL),(1263,NULL,NULL,'2012-06-11 01:34:38',NULL),(1264,NULL,NULL,'2012-06-11 01:35:11',NULL),(1265,NULL,NULL,'2012-06-11 01:37:26',NULL),(1266,NULL,NULL,'2012-06-11 01:39:01',NULL),(1267,NULL,NULL,'2012-06-11 01:39:36',NULL),(1268,NULL,NULL,'2012-06-11 01:39:38',NULL),(1269,NULL,NULL,'2012-06-11 01:40:35',NULL),(1270,NULL,NULL,'2012-06-11 01:42:10',NULL),(1271,NULL,NULL,'2012-06-11 01:44:51',NULL),(1272,NULL,NULL,'2012-06-11 01:45:15',NULL),(1273,NULL,NULL,'2012-06-11 01:45:20',NULL),(1274,NULL,NULL,'2012-06-11 01:46:54',NULL),(1275,NULL,NULL,'2012-06-11 01:48:29',NULL),(1276,NULL,NULL,'2012-06-11 01:49:04',NULL),(1277,NULL,NULL,'2012-06-11 01:50:04',NULL),(1278,NULL,NULL,'2012-06-11 01:51:39',NULL),(1279,NULL,NULL,'2012-06-11 01:53:13',NULL),(1280,NULL,NULL,'2012-06-11 01:54:48',NULL),(1281,NULL,NULL,'2012-06-11 01:56:23',NULL),(1282,NULL,NULL,'2012-06-11 01:56:24',NULL),(1283,NULL,NULL,'2012-06-11 01:57:58',NULL),(1284,NULL,NULL,'2012-06-11 01:59:32',NULL),(1285,NULL,NULL,'2012-06-11 02:01:07',NULL),(1286,NULL,NULL,'2012-06-11 02:02:42',NULL),(1287,NULL,NULL,'2012-06-11 02:04:18',NULL),(1288,NULL,NULL,'2012-06-11 02:05:51',NULL),(1289,NULL,NULL,'2012-06-11 02:07:26',NULL),(1290,NULL,NULL,'2012-06-11 02:09:00',NULL),(1291,NULL,NULL,'2012-06-11 02:10:36',NULL),(1292,NULL,NULL,'2012-06-11 02:12:10',NULL),(1293,NULL,NULL,'2012-06-11 02:13:45',NULL),(1294,NULL,NULL,'2012-06-11 02:14:41',NULL),(1295,NULL,NULL,'2012-06-11 02:15:20',NULL),(1296,NULL,NULL,'2012-06-11 02:16:55',NULL),(1297,NULL,NULL,'2012-06-11 02:18:29',NULL),(1298,NULL,NULL,'2012-06-11 02:18:30',NULL),(1299,NULL,NULL,'2012-06-11 02:19:34',NULL),(1300,NULL,NULL,'2012-06-11 02:20:04',NULL),(1301,NULL,NULL,'2012-06-11 02:21:39',NULL),(1302,NULL,NULL,'2012-06-11 02:23:14',NULL),(1303,NULL,NULL,'2012-06-11 02:24:48',NULL),(1304,NULL,NULL,'2012-06-11 02:26:23',NULL),(1305,NULL,NULL,'2012-06-11 02:27:58',NULL),(1306,NULL,NULL,'2012-06-11 02:28:21',NULL),(1307,NULL,NULL,'2012-06-11 02:29:32',NULL),(1308,NULL,NULL,'2012-06-11 02:31:07',NULL),(1309,NULL,NULL,'2012-06-11 02:32:42',NULL),(1310,NULL,NULL,'2012-06-11 02:34:17',NULL),(1311,NULL,NULL,'2012-06-11 02:35:51',NULL),(1312,NULL,NULL,'2012-06-11 02:37:26',NULL),(1313,NULL,NULL,'2012-06-11 02:38:16',NULL),(1314,NULL,NULL,'2012-06-11 02:39:01',NULL),(1315,NULL,NULL,'2012-06-11 02:40:18',NULL),(1316,NULL,NULL,'2012-06-11 02:40:36',NULL),(1317,NULL,NULL,'2012-06-11 02:42:10',NULL),(1318,NULL,NULL,'2012-06-11 02:43:45',NULL),(1319,NULL,NULL,'2012-06-11 02:45:20',NULL),(1320,NULL,NULL,'2012-06-11 02:46:55',NULL),(1321,NULL,NULL,'2012-06-11 02:48:29',NULL),(1322,NULL,NULL,'2012-06-11 02:50:04',NULL),(1323,NULL,NULL,'2012-06-11 02:51:38',NULL),(1324,NULL,NULL,'2012-06-11 02:53:14',NULL),(1325,NULL,NULL,'2012-06-11 02:54:48',NULL),(1326,NULL,NULL,'2012-06-11 02:56:23',NULL),(1327,NULL,NULL,'2012-06-11 02:57:58',NULL),(1328,NULL,NULL,'2012-06-11 02:58:28',NULL),(1329,NULL,NULL,'2012-06-11 02:59:38',NULL),(1330,NULL,NULL,'2012-06-11 03:00:23',NULL),(1331,NULL,NULL,'2012-06-11 03:01:07',NULL),(1332,NULL,NULL,'2012-06-11 03:02:42',NULL),(1333,NULL,NULL,'2012-06-11 03:04:17',NULL),(1334,NULL,NULL,'2012-06-11 03:05:27',NULL),(1335,NULL,NULL,'2012-06-11 03:05:51',NULL),(1336,NULL,NULL,'2012-06-11 03:05:55',NULL),(1337,NULL,NULL,'2012-06-11 03:07:26',NULL),(1338,NULL,NULL,'2012-06-11 03:09:01',NULL),(1339,NULL,NULL,'2012-06-11 03:10:36',NULL),(1340,NULL,NULL,'2012-06-11 03:12:11',NULL),(1341,NULL,NULL,'2012-06-11 03:13:45',NULL),(1342,NULL,NULL,'2012-06-11 03:15:20',NULL),(1343,NULL,NULL,'2012-06-11 03:16:55',NULL),(1344,NULL,NULL,'2012-06-11 03:18:29',NULL),(1345,NULL,NULL,'2012-06-11 03:20:04',NULL),(1346,NULL,NULL,'2012-06-11 03:21:39',NULL),(1347,NULL,NULL,'2012-06-11 03:23:14',NULL),(1348,NULL,NULL,'2012-06-11 03:24:48',NULL),(1349,NULL,NULL,'2012-06-11 03:26:23',NULL),(1350,NULL,NULL,'2012-06-11 03:27:58',NULL),(1351,NULL,NULL,'2012-06-11 03:29:33',NULL),(1352,NULL,NULL,'2012-06-11 03:31:07',NULL),(1353,NULL,NULL,'2012-06-11 03:31:42',NULL),(1354,NULL,NULL,'2012-06-11 03:32:23',NULL),(1355,NULL,NULL,'2012-06-11 03:32:42',NULL),(1356,NULL,NULL,'2012-06-11 03:34:17',NULL),(1357,NULL,NULL,'2012-06-11 03:35:51',NULL),(1358,NULL,NULL,'2012-06-11 03:37:22',NULL),(1359,NULL,NULL,'2012-06-11 03:37:25',NULL),(1360,NULL,NULL,'2012-06-11 03:39:01',NULL),(1361,NULL,NULL,'2012-06-11 03:40:36',NULL),(1362,NULL,NULL,'2012-06-11 03:42:11',NULL),(1363,NULL,NULL,'2012-06-11 03:43:45',NULL),(1364,NULL,NULL,'2012-06-11 03:45:20',NULL),(1365,NULL,NULL,'2012-06-11 03:45:39',NULL),(1366,NULL,NULL,'2012-06-11 03:45:58',NULL),(1367,NULL,NULL,'2012-06-11 03:46:55',NULL),(1368,NULL,NULL,'2012-06-11 03:48:30',NULL),(1369,NULL,NULL,'2012-06-11 03:50:04',NULL),(1370,NULL,NULL,'2012-06-11 03:51:39',NULL),(1371,NULL,NULL,'2012-06-11 03:52:00',NULL),(1372,NULL,NULL,'2012-06-11 03:53:14',NULL),(1373,NULL,NULL,'2012-06-11 03:54:49',NULL),(1374,NULL,NULL,'2012-06-11 03:56:23',NULL),(1375,NULL,NULL,'2012-06-11 03:57:18',NULL),(1376,NULL,NULL,'2012-06-11 03:57:58',NULL),(1377,NULL,NULL,'2012-06-11 03:59:05',NULL),(1378,NULL,NULL,'2012-06-11 03:59:32',NULL),(1379,NULL,NULL,'2012-06-11 04:01:02',NULL),(1380,NULL,NULL,'2012-06-11 04:01:08',NULL),(1381,NULL,NULL,'2012-06-11 04:02:42',NULL),(1382,NULL,NULL,'2012-06-11 04:04:24',NULL),(1383,NULL,NULL,'2012-06-11 04:04:25',NULL),(1384,NULL,NULL,'2012-06-11 04:05:52',NULL),(1385,NULL,NULL,'2012-06-11 04:07:27',NULL),(1386,NULL,NULL,'2012-06-11 04:09:01',NULL),(1387,NULL,NULL,'2012-06-11 04:10:35',NULL),(1388,NULL,NULL,'2012-06-11 04:10:36',NULL),(1389,NULL,NULL,'2012-06-11 04:12:11',NULL),(1390,NULL,NULL,'2012-06-11 04:13:45',NULL),(1391,NULL,NULL,'2012-06-11 04:15:20',NULL),(1392,NULL,NULL,'2012-06-11 04:15:49',NULL),(1393,NULL,NULL,'2012-06-11 04:15:52',NULL),(1394,NULL,NULL,'2012-06-11 04:16:55',NULL),(1395,NULL,NULL,'2012-06-11 04:18:30',NULL),(1396,NULL,NULL,'2012-06-11 04:19:30',NULL),(1397,NULL,NULL,'2012-06-11 04:19:31',NULL),(1398,NULL,NULL,'2012-06-11 04:19:41',NULL),(1399,NULL,NULL,'2012-06-11 04:20:04',NULL),(1400,NULL,NULL,'2012-06-11 04:20:10',NULL),(1401,NULL,NULL,'2012-06-11 04:21:39',NULL),(1402,NULL,NULL,'2012-06-11 04:23:14',NULL),(1403,NULL,NULL,'2012-06-11 04:23:33',NULL),(1404,NULL,NULL,'2012-06-11 04:24:37',NULL),(1405,NULL,NULL,'2012-06-11 04:24:48',NULL),(1406,NULL,NULL,'2012-06-11 04:26:23',NULL),(1407,NULL,NULL,'2012-06-11 04:29:33',NULL),(1408,NULL,NULL,'2012-06-11 04:31:08',NULL),(1409,NULL,NULL,'2012-06-11 04:31:27',NULL),(1410,NULL,NULL,'2012-06-11 04:31:28',NULL),(1411,NULL,NULL,'2012-06-11 04:31:32',NULL),(1412,NULL,NULL,'2012-06-11 04:31:36',NULL),(1413,NULL,NULL,'2012-06-11 04:31:42',NULL),(1414,NULL,NULL,'2012-06-11 04:32:42',NULL),(1415,NULL,NULL,'2012-06-11 04:33:46',NULL),(1416,NULL,NULL,'2012-06-11 04:33:49',NULL),(1417,NULL,NULL,'2012-06-11 04:33:51',NULL),(1418,NULL,NULL,'2012-06-11 04:33:56',NULL),(1419,NULL,NULL,'2012-06-11 04:33:58',NULL),(1420,NULL,NULL,'2012-06-11 04:34:17',NULL),(1421,NULL,NULL,'2012-06-11 04:35:52',NULL),(1422,NULL,NULL,'2012-06-11 04:36:12',NULL),(1423,NULL,NULL,'2012-06-11 04:36:19',NULL),(1424,NULL,NULL,'2012-06-11 04:37:26',NULL),(1425,NULL,NULL,'2012-06-11 04:37:40',NULL),(1426,NULL,NULL,'2012-06-11 04:37:43',NULL),(1427,NULL,NULL,'2012-06-11 04:37:44',NULL),(1428,NULL,NULL,'2012-06-11 04:37:46',NULL),(1429,NULL,NULL,'2012-06-11 04:37:47',NULL),(1430,NULL,NULL,'2012-06-11 04:37:48',NULL),(1431,NULL,NULL,'2012-06-11 04:37:50',NULL),(1432,NULL,NULL,'2012-06-11 04:37:53',NULL),(1433,NULL,NULL,'2012-06-11 04:38:02',NULL),(1434,NULL,NULL,'2012-06-11 04:38:03',NULL),(1435,NULL,NULL,'2012-06-11 04:38:40',NULL),(1436,NULL,NULL,'2012-06-11 04:39:01',NULL),(1437,NULL,NULL,'2012-06-11 04:39:03',NULL),(1438,NULL,NULL,'2012-06-11 04:40:36',NULL),(1439,NULL,NULL,'2012-06-11 04:42:11',NULL),(1440,NULL,NULL,'2012-06-11 04:43:46',NULL),(1441,NULL,NULL,'2012-06-11 04:45:20',NULL),(1442,NULL,NULL,'2012-06-11 04:46:54',NULL),(1443,NULL,NULL,'2012-06-11 04:48:30',NULL),(1444,NULL,NULL,'2012-06-11 04:49:02',NULL),(1445,NULL,NULL,'2012-06-11 04:49:07',NULL),(1446,NULL,NULL,'2012-06-11 04:49:19',NULL),(1447,NULL,NULL,'2012-06-11 04:50:04',NULL),(1448,NULL,NULL,'2012-06-11 04:50:16',NULL),(1449,NULL,NULL,'2012-06-11 04:50:26',NULL),(1450,NULL,NULL,'2012-06-11 04:50:27',NULL),(1451,NULL,NULL,'2012-06-11 04:50:38',NULL),(1452,NULL,NULL,'2012-06-11 04:50:40',NULL),(1453,NULL,NULL,'2012-06-11 04:50:43',NULL),(1454,NULL,NULL,'2012-06-11 04:50:44',NULL),(1455,NULL,NULL,'2012-06-11 04:51:39',NULL),(1456,NULL,NULL,'2012-06-11 04:52:02',NULL),(1457,NULL,NULL,'2012-06-11 04:52:31',NULL),(1458,NULL,NULL,'2012-06-11 04:52:32',NULL),(1459,NULL,NULL,'2012-06-11 04:52:35',NULL),(1460,NULL,NULL,'2012-06-11 04:52:44',NULL),(1461,NULL,NULL,'2012-06-11 04:53:14',NULL),(1462,NULL,NULL,'2012-06-11 04:53:45',NULL),(1463,NULL,NULL,'2012-06-11 04:54:49',NULL),(1464,NULL,NULL,'2012-06-11 04:54:50',NULL),(1465,NULL,NULL,'2012-06-11 04:54:56',NULL),(1466,NULL,NULL,'2012-06-11 04:55:12',NULL),(1467,NULL,NULL,'2012-06-11 04:55:15',NULL),(1468,NULL,NULL,'2012-06-11 04:55:17',NULL),(1469,NULL,NULL,'2012-06-11 04:55:18',NULL),(1470,NULL,NULL,'2012-06-11 04:55:19',NULL),(1471,NULL,NULL,'2012-06-11 04:55:20',NULL),(1472,NULL,NULL,'2012-06-11 04:55:53',NULL),(1473,NULL,NULL,'2012-06-11 04:56:23',NULL),(1474,NULL,NULL,'2012-06-11 04:56:24',NULL),(1475,NULL,NULL,'2012-06-11 04:56:33',NULL),(1476,NULL,NULL,'2012-06-11 04:57:58',NULL),(1477,NULL,NULL,'2012-06-11 04:59:33',NULL),(1478,NULL,NULL,'2012-06-11 04:59:36',NULL),(1479,NULL,NULL,'2012-06-11 05:00:01',NULL),(1480,NULL,NULL,'2012-06-11 05:00:35',NULL),(1481,NULL,NULL,'2012-06-11 05:00:37',NULL),(1482,NULL,NULL,'2012-06-11 05:00:43',NULL),(1483,NULL,NULL,'2012-06-11 05:01:08',NULL),(1484,NULL,NULL,'2012-06-11 05:01:18',NULL),(1485,NULL,NULL,'2012-06-11 05:01:57',NULL),(1486,NULL,NULL,'2012-06-11 05:02:14',NULL),(1487,NULL,NULL,'2012-06-11 05:02:43',NULL),(1488,NULL,NULL,'2012-06-11 05:03:47',NULL),(1489,NULL,NULL,'2012-06-11 05:03:56',NULL),(1490,NULL,NULL,'2012-06-11 05:03:59',NULL),(1491,NULL,NULL,'2012-06-11 05:04:01',NULL),(1492,NULL,NULL,'2012-06-11 05:04:03',NULL),(1493,NULL,NULL,'2012-06-11 05:04:05',NULL),(1494,NULL,NULL,'2012-06-11 05:04:17',NULL),(1495,NULL,NULL,'2012-06-11 05:05:20',NULL),(1496,NULL,NULL,'2012-06-11 05:05:38',NULL),(1497,NULL,NULL,'2012-06-11 05:05:39',NULL),(1498,NULL,NULL,'2012-06-11 05:05:52',NULL),(1499,NULL,NULL,'2012-06-11 05:05:57',NULL),(1500,NULL,NULL,'2012-06-11 05:06:07',NULL),(1501,NULL,NULL,'2012-06-11 05:07:16',NULL),(1502,NULL,NULL,'2012-06-11 05:07:27',NULL),(1503,NULL,NULL,'2012-06-11 05:07:28',NULL),(1504,NULL,NULL,'2012-06-11 05:08:32',NULL),(1505,NULL,NULL,'2012-06-11 05:08:33',NULL),(1506,NULL,NULL,'2012-06-11 05:08:53',NULL),(1507,NULL,NULL,'2012-06-11 05:09:01',NULL),(1508,NULL,NULL,'2012-06-11 05:10:35',NULL),(1509,NULL,NULL,'2012-06-11 05:11:46',NULL),(1510,NULL,NULL,'2012-06-11 05:12:11',NULL),(1511,NULL,NULL,'2012-06-11 05:13:19',NULL),(1512,NULL,NULL,'2012-06-11 05:13:46',NULL),(1513,NULL,NULL,'2012-06-11 05:14:26',NULL),(1514,NULL,NULL,'2012-06-11 05:14:53',NULL),(1515,NULL,NULL,'2012-06-11 05:15:04',NULL),(1516,NULL,NULL,'2012-06-11 05:15:20',NULL),(1517,NULL,NULL,'2012-06-11 05:15:30',NULL),(1518,NULL,NULL,'2012-06-11 05:15:31',NULL),(1519,NULL,NULL,'2012-06-11 05:15:33',NULL),(1520,NULL,NULL,'2012-06-11 05:16:02',NULL),(1521,NULL,NULL,'2012-06-11 05:16:10',NULL),(1522,NULL,NULL,'2012-06-11 05:16:26',NULL),(1523,NULL,NULL,'2012-06-11 05:16:55',NULL),(1524,NULL,NULL,'2012-06-11 05:17:49',NULL),(1525,NULL,NULL,'2012-06-11 05:18:30',NULL),(1526,NULL,NULL,'2012-06-11 05:18:41',NULL),(1527,NULL,NULL,'2012-06-11 05:20:05',NULL),(1528,NULL,NULL,'2012-06-11 05:20:50',NULL),(1529,NULL,NULL,'2012-06-11 05:20:55',NULL),(1530,NULL,NULL,'2012-06-11 05:20:57',NULL),(1531,NULL,NULL,'2012-06-11 05:21:03',NULL),(1532,NULL,NULL,'2012-06-11 05:21:19',NULL),(1533,NULL,NULL,'2012-06-11 05:21:32',NULL),(1534,NULL,NULL,'2012-06-11 05:21:40',NULL),(1535,NULL,NULL,'2012-06-11 05:23:14',NULL),(1536,NULL,NULL,'2012-06-11 05:23:16',NULL),(1537,NULL,NULL,'2012-06-11 05:23:18',NULL),(1538,NULL,NULL,'2012-06-11 05:23:20',NULL),(1539,NULL,NULL,'2012-06-11 05:24:16',NULL),(1540,NULL,NULL,'2012-06-11 05:24:33',NULL),(1541,NULL,NULL,'2012-06-11 05:24:38',NULL),(1542,NULL,NULL,'2012-06-11 05:24:43',NULL),(1543,NULL,NULL,'2012-06-11 05:24:49',NULL),(1544,NULL,NULL,'2012-06-11 05:26:24',NULL),(1545,NULL,NULL,'2012-06-11 05:26:53',NULL),(1546,NULL,NULL,'2012-06-11 05:26:55',NULL),(1547,NULL,NULL,'2012-06-11 05:27:08',NULL),(1548,NULL,NULL,'2012-06-11 05:27:31',NULL),(1549,NULL,NULL,'2012-06-11 05:27:59',NULL),(1550,NULL,NULL,'2012-06-11 05:28:55',NULL),(1551,NULL,NULL,'2012-06-11 05:28:58',NULL),(1552,NULL,NULL,'2012-06-11 05:29:33',NULL),(1553,NULL,NULL,'2012-06-11 05:30:02',NULL),(1554,NULL,NULL,'2012-06-11 05:30:47',NULL),(1555,NULL,NULL,'2012-06-11 05:31:08',NULL),(1556,NULL,NULL,'2012-06-11 05:31:10',NULL),(1557,NULL,NULL,'2012-06-11 05:31:56',NULL),(1558,NULL,NULL,'2012-06-11 05:32:01',NULL),(1559,NULL,NULL,'2012-06-11 05:32:25',NULL),(1560,NULL,NULL,'2012-06-11 05:32:43',NULL),(1561,NULL,NULL,'2012-06-11 05:33:02',NULL),(1562,NULL,NULL,'2012-06-11 05:33:05',NULL),(1563,NULL,NULL,'2012-06-11 05:33:46',NULL),(1564,NULL,NULL,'2012-06-11 05:34:17',NULL),(1565,NULL,NULL,'2012-06-11 05:34:21',NULL),(1566,NULL,NULL,'2012-06-11 05:34:43',NULL),(1567,NULL,NULL,'2012-06-11 05:35:52',NULL),(1568,NULL,NULL,'2012-06-11 05:37:01',NULL),(1569,NULL,NULL,'2012-06-11 05:37:14',NULL),(1570,NULL,NULL,'2012-06-11 05:37:16',NULL),(1571,NULL,NULL,'2012-06-11 05:37:27',NULL),(1572,NULL,NULL,'2012-06-11 05:37:29',NULL),(1573,NULL,NULL,'2012-06-11 05:38:02',NULL),(1574,NULL,NULL,'2012-06-11 05:39:02',NULL),(1575,NULL,NULL,'2012-06-11 05:40:37',NULL),(1576,NULL,NULL,'2012-06-11 05:40:44',NULL),(1577,NULL,NULL,'2012-06-11 05:40:46',NULL),(1578,NULL,NULL,'2012-06-11 05:40:50',NULL),(1579,NULL,NULL,'2012-06-11 05:41:29',NULL),(1580,NULL,NULL,'2012-06-11 05:41:32',NULL),(1581,NULL,NULL,'2012-06-11 05:42:11',NULL),(1582,NULL,NULL,'2012-06-11 05:43:01',NULL),(1583,NULL,NULL,'2012-06-11 05:43:27',NULL),(1584,NULL,NULL,'2012-06-11 05:43:39',NULL),(1585,NULL,NULL,'2012-06-11 05:43:46',NULL),(1586,NULL,NULL,'2012-06-11 05:44:03',NULL),(1587,NULL,NULL,'2012-06-11 05:44:05',NULL),(1588,NULL,NULL,'2012-06-11 05:44:07',NULL),(1589,NULL,NULL,'2012-06-11 05:44:08',NULL),(1590,NULL,NULL,'2012-06-11 05:44:13',NULL),(1591,NULL,NULL,'2012-06-11 05:44:47',NULL),(1592,NULL,NULL,'2012-06-11 05:45:21',NULL),(1593,NULL,NULL,'2012-06-11 05:45:47',NULL),(1594,NULL,NULL,'2012-06-11 05:45:53',NULL),(1595,NULL,NULL,'2012-06-11 05:46:40',NULL),(1596,NULL,NULL,'2012-06-11 05:46:47',NULL),(1597,NULL,NULL,'2012-06-11 05:46:49',NULL),(1598,NULL,NULL,'2012-06-11 05:46:50',NULL),(1599,NULL,NULL,'2012-06-11 05:46:52',NULL),(1600,NULL,NULL,'2012-06-11 05:46:54',NULL),(1601,NULL,NULL,'2012-06-11 05:46:55',NULL),(1602,NULL,NULL,'2012-06-11 05:47:05',NULL),(1603,NULL,NULL,'2012-06-11 05:47:22',NULL),(1604,NULL,NULL,'2012-06-11 05:48:03',NULL),(1605,NULL,NULL,'2012-06-11 05:48:05',NULL),(1606,NULL,NULL,'2012-06-11 05:48:07',NULL),(1607,NULL,NULL,'2012-06-11 05:48:09',NULL),(1608,NULL,NULL,'2012-06-11 05:48:30',NULL),(1609,NULL,NULL,'2012-06-11 05:49:01',NULL),(1610,NULL,NULL,'2012-06-11 05:49:16',NULL),(1611,NULL,NULL,'2012-06-11 05:49:30',NULL),(1612,NULL,NULL,'2012-06-11 05:49:59',NULL),(1613,NULL,NULL,'2012-06-11 05:50:00',NULL),(1614,NULL,NULL,'2012-06-11 05:50:02',NULL),(1615,NULL,NULL,'2012-06-11 05:50:25',NULL),(1616,NULL,NULL,'2012-06-11 05:50:31',NULL),(1617,NULL,NULL,'2012-06-11 05:50:40',NULL),(1618,NULL,NULL,'2012-06-11 05:51:40',NULL),(1619,NULL,NULL,'2012-06-11 05:52:16',NULL),(1620,NULL,NULL,'2012-06-11 05:52:25',NULL),(1621,NULL,NULL,'2012-06-11 05:52:29',NULL),(1622,NULL,NULL,'2012-06-11 05:52:30',NULL),(1623,NULL,NULL,'2012-06-11 05:52:33',NULL),(1624,NULL,NULL,'2012-06-11 05:53:15',NULL),(1625,NULL,NULL,'2012-06-11 05:54:48',NULL),(1626,NULL,NULL,'2012-06-11 05:55:53',NULL),(1627,NULL,NULL,'2012-06-11 05:56:24',NULL),(1628,NULL,NULL,'2012-06-11 05:56:33',NULL),(1629,NULL,NULL,'2012-06-11 05:56:48',NULL),(1630,NULL,NULL,'2012-06-11 05:56:58',NULL),(1631,NULL,NULL,'2012-06-11 05:57:10',NULL),(1632,NULL,NULL,'2012-06-11 05:57:21',NULL),(1633,NULL,NULL,'2012-06-11 05:57:59',NULL),(1634,NULL,NULL,'2012-06-11 05:58:06',NULL),(1635,NULL,NULL,'2012-06-11 05:58:13',NULL),(1636,NULL,NULL,'2012-06-11 05:59:25',NULL),(1637,NULL,NULL,'2012-06-11 05:59:34',NULL),(1638,NULL,NULL,'2012-06-11 06:00:28',NULL),(1639,NULL,NULL,'2012-06-11 06:00:30',NULL),(1640,NULL,NULL,'2012-06-11 06:01:09',NULL),(1641,NULL,NULL,'2012-06-11 06:02:11',NULL),(1642,NULL,NULL,'2012-06-11 06:02:43',NULL),(1643,NULL,NULL,'2012-06-11 06:04:09',NULL),(1644,NULL,NULL,'2012-06-11 06:04:15',NULL),(1645,NULL,NULL,'2012-06-11 06:04:18',NULL),(1646,NULL,NULL,'2012-06-11 06:04:58',NULL),(1647,NULL,NULL,'2012-06-11 06:05:38',NULL),(1648,NULL,NULL,'2012-06-11 06:05:52',NULL),(1649,NULL,NULL,'2012-06-11 06:05:54',NULL),(1650,NULL,NULL,'2012-06-11 06:06:35',NULL),(1651,NULL,NULL,'2012-06-11 06:06:41',NULL),(1652,NULL,NULL,'2012-06-11 06:07:22',NULL),(1653,NULL,NULL,'2012-06-11 06:07:24',NULL),(1654,NULL,NULL,'2012-06-11 06:07:26',NULL),(1655,NULL,NULL,'2012-06-11 06:07:40',NULL),(1656,NULL,NULL,'2012-06-11 06:07:43',NULL),(1657,NULL,NULL,'2012-06-11 06:07:45',NULL),(1658,NULL,NULL,'2012-06-11 06:09:03',NULL),(1659,NULL,NULL,'2012-06-11 06:10:37',NULL),(1660,NULL,NULL,'2012-06-11 06:11:02',NULL),(1661,NULL,NULL,'2012-06-11 06:11:26',NULL),(1662,NULL,NULL,'2012-06-11 06:11:39',NULL),(1663,NULL,NULL,'2012-06-11 06:11:42',NULL),(1664,NULL,NULL,'2012-06-11 06:11:44',NULL),(1665,NULL,NULL,'2012-06-11 06:12:15',NULL),(1666,NULL,NULL,'2012-06-11 06:12:57',NULL),(1667,NULL,NULL,'2012-06-11 06:13:43',NULL),(1668,NULL,NULL,'2012-06-11 06:14:13',NULL),(1669,NULL,NULL,'2012-06-11 06:14:29',NULL),(1670,NULL,NULL,'2012-06-11 06:14:46',NULL),(1671,NULL,NULL,'2012-06-11 06:15:02',NULL),(1672,NULL,NULL,'2012-06-11 06:15:31',NULL),(1673,NULL,NULL,'2012-06-11 06:15:35',NULL),(1674,NULL,NULL,'2012-06-11 06:15:58',NULL),(1675,NULL,NULL,'2012-06-11 06:16:08',NULL),(1676,NULL,NULL,'2012-06-11 06:16:19',NULL),(1677,NULL,NULL,'2012-06-11 06:16:35',NULL),(1678,NULL,NULL,'2012-06-11 06:16:42',NULL),(1679,NULL,NULL,'2012-06-11 06:17:09',NULL),(1680,NULL,NULL,'2012-06-11 06:17:15',NULL),(1681,NULL,NULL,'2012-06-11 06:17:48',NULL),(1682,NULL,NULL,'2012-06-11 06:18:05',NULL),(1683,NULL,NULL,'2012-06-11 06:18:15',NULL),(1684,NULL,NULL,'2012-06-11 06:18:22',NULL),(1685,NULL,NULL,'2012-06-11 06:18:56',NULL),(1686,NULL,NULL,'2012-06-11 06:18:58',NULL),(1687,NULL,NULL,'2012-06-11 06:19:29',NULL),(1688,NULL,NULL,'2012-06-11 06:20:03',NULL),(1689,NULL,NULL,'2012-06-11 06:20:37',NULL),(1690,NULL,NULL,'2012-06-11 06:20:48',NULL),(1691,NULL,NULL,'2012-06-11 06:21:12',NULL),(1692,NULL,NULL,'2012-06-11 06:21:47',NULL),(1693,NULL,NULL,'2012-06-11 06:22:22',NULL),(1694,NULL,NULL,'2012-06-11 06:22:44',NULL),(1695,NULL,NULL,'2012-06-11 06:22:48',NULL),(1696,NULL,NULL,'2012-06-11 06:22:58',NULL),(1697,NULL,NULL,'2012-06-11 06:23:34',NULL),(1698,NULL,NULL,'2012-06-11 06:24:10',NULL),(1699,NULL,NULL,'2012-06-11 06:24:26',NULL),(1700,NULL,NULL,'2012-06-11 06:24:47',NULL),(1701,NULL,NULL,'2012-06-11 06:25:03',NULL),(1702,NULL,NULL,'2012-06-11 06:25:05',NULL),(1703,NULL,NULL,'2012-06-11 06:25:25',NULL),(1704,NULL,NULL,'2012-06-11 06:26:02',NULL),(1705,NULL,NULL,'2012-06-11 06:26:04',NULL),(1706,NULL,NULL,'2012-06-11 06:26:29',NULL),(1707,NULL,NULL,'2012-06-11 06:26:41',NULL),(1708,NULL,NULL,'2012-06-11 06:26:42',NULL),(1709,NULL,NULL,'2012-06-11 06:27:19',NULL),(1710,NULL,NULL,'2012-06-11 06:27:59',NULL),(1711,NULL,NULL,'2012-06-11 06:28:39',NULL),(1712,NULL,NULL,'2012-06-11 06:28:43',NULL),(1713,NULL,NULL,'2012-06-11 06:29:07',NULL),(1714,NULL,NULL,'2012-06-11 06:29:19',NULL),(1715,NULL,NULL,'2012-06-11 06:29:26',NULL),(1716,NULL,NULL,'2012-06-11 06:30:01',NULL),(1717,NULL,NULL,'2012-06-11 06:30:06',NULL),(1718,NULL,NULL,'2012-06-11 06:30:08',NULL),(1719,NULL,NULL,'2012-06-11 06:30:10',NULL),(1720,NULL,NULL,'2012-06-11 06:30:43',NULL),(1721,NULL,NULL,'2012-06-11 06:30:48',NULL),(1722,NULL,NULL,'2012-06-11 06:30:54',NULL),(1723,NULL,NULL,'2012-06-11 06:31:25',NULL),(1724,NULL,NULL,'2012-06-11 06:31:30',NULL),(1725,NULL,NULL,'2012-06-11 06:31:32',NULL),(1726,NULL,NULL,'2012-06-11 06:31:34',NULL),(1727,NULL,NULL,'2012-06-11 06:32:21',NULL),(1728,NULL,NULL,'2012-06-11 06:34:36',NULL),(1729,NULL,NULL,'2012-06-11 06:34:45',NULL),(1730,NULL,NULL,'2012-06-11 06:34:46',NULL),(1731,NULL,NULL,'2012-06-11 06:34:47',NULL),(1732,NULL,NULL,'2012-06-11 06:34:55',NULL),(1733,NULL,NULL,'2012-06-11 06:35:57',NULL),(1734,NULL,NULL,'2012-06-11 06:38:20',NULL),(1735,NULL,NULL,'2012-06-11 06:38:21',NULL),(1736,NULL,NULL,'2012-06-11 06:39:44',NULL),(1737,NULL,NULL,'2012-06-11 06:39:55',NULL),(1738,NULL,NULL,'2012-06-11 06:41:18',NULL),(1739,NULL,NULL,'2012-06-11 06:41:38',NULL),(1740,NULL,NULL,'2012-06-11 06:42:00',NULL),(1741,NULL,NULL,'2012-06-11 06:42:45',NULL),(1742,NULL,NULL,'2012-06-11 06:43:16',NULL),(1743,NULL,NULL,'2012-06-11 06:43:59',NULL),(1744,NULL,NULL,'2012-06-11 06:44:21',NULL),(1745,NULL,NULL,'2012-06-11 06:45:21',NULL),(1746,NULL,NULL,'2012-06-11 06:45:25',NULL),(1747,NULL,NULL,'2012-06-11 06:46:08',NULL),(1748,NULL,NULL,'2012-06-11 06:46:52',NULL),(1749,NULL,NULL,'2012-06-11 06:47:13',NULL),(1750,NULL,NULL,'2012-06-11 06:47:34',NULL),(1751,NULL,NULL,'2012-06-11 06:48:18',NULL),(1752,NULL,NULL,'2012-06-11 06:49:01',NULL),(1753,NULL,NULL,'2012-06-11 06:49:44',NULL),(1754,NULL,NULL,'2012-06-11 06:50:27',NULL),(1755,NULL,NULL,'2012-06-11 06:51:12',NULL),(1756,NULL,NULL,'2012-06-11 06:52:36',NULL),(1757,NULL,NULL,'2012-06-11 06:53:19',NULL),(1758,NULL,NULL,'2012-06-11 06:54:02',NULL),(1759,NULL,NULL,'2012-06-11 06:54:45',NULL),(1760,NULL,NULL,'2012-06-11 06:55:28',NULL),(1761,NULL,NULL,'2012-06-11 06:56:11',NULL),(1762,NULL,NULL,'2012-06-11 06:56:54',NULL),(1763,NULL,NULL,'2012-06-11 06:57:37',NULL),(1764,NULL,NULL,'2012-06-11 06:58:06',NULL),(1765,NULL,NULL,'2012-06-11 06:58:20',NULL),(1766,NULL,NULL,'2012-06-11 06:59:03',NULL),(1767,NULL,NULL,'2012-06-11 06:59:46',NULL),(1768,NULL,NULL,'2012-06-11 07:00:29',NULL),(1769,NULL,NULL,'2012-06-11 07:01:12',NULL),(1770,NULL,NULL,'2012-06-11 07:01:55',NULL),(1771,NULL,NULL,'2012-06-11 07:02:38',NULL),(1772,NULL,NULL,'2012-06-11 07:03:30',NULL),(1773,NULL,NULL,'2012-06-11 07:04:19',NULL),(1774,NULL,NULL,'2012-06-11 07:05:09',NULL),(1775,NULL,NULL,'2012-06-11 07:06:00',NULL),(1776,NULL,NULL,'2012-06-11 07:07:41',NULL),(1777,NULL,NULL,'2012-06-11 07:08:32',NULL),(1778,NULL,NULL,'2012-06-11 07:11:03',NULL),(1779,NULL,NULL,'2012-06-11 07:13:35',NULL),(1780,NULL,NULL,'2012-06-11 07:14:26',NULL),(1781,NULL,NULL,'2012-06-11 07:15:16',NULL),(1782,NULL,NULL,'2012-06-11 07:17:48',NULL),(1783,NULL,NULL,'2012-06-11 07:18:38',NULL),(1784,NULL,NULL,'2012-06-11 07:19:29',NULL),(1785,NULL,NULL,'2012-06-11 07:20:19',NULL),(1786,NULL,NULL,'2012-06-11 07:20:43',NULL),(1787,NULL,NULL,'2012-06-11 07:20:57',NULL),(1788,NULL,NULL,'2012-06-11 07:21:10',NULL),(1789,NULL,NULL,'2012-06-11 07:21:21',NULL),(1790,NULL,NULL,'2012-06-11 07:21:22',NULL),(1791,NULL,NULL,'2012-06-11 07:21:23',NULL),(1792,NULL,NULL,'2012-06-11 07:21:24',NULL),(1793,NULL,NULL,'2012-06-11 07:21:25',NULL),(1794,NULL,NULL,'2012-06-11 07:21:26',NULL),(1795,NULL,NULL,'2012-06-11 07:21:32',NULL),(1796,NULL,NULL,'2012-06-11 07:21:54',NULL),(1797,NULL,NULL,'2012-06-11 07:21:55',NULL),(1798,NULL,NULL,'2012-06-11 07:22:00',NULL),(1799,NULL,NULL,'2012-06-11 07:22:50',NULL),(1800,NULL,NULL,'2012-06-11 07:22:51',NULL),(1801,NULL,NULL,'2012-06-11 07:22:54',NULL),(1802,NULL,NULL,'2012-06-11 07:22:57',NULL),(1803,NULL,NULL,'2012-06-11 07:22:59',NULL),(1804,NULL,NULL,'2012-06-11 07:23:07',NULL),(1805,NULL,NULL,'2012-06-11 07:23:42',NULL),(1806,NULL,NULL,'2012-06-11 07:23:52',NULL),(1807,NULL,NULL,'2012-06-11 07:24:01',NULL),(1808,NULL,NULL,'2012-06-11 07:24:32',NULL),(1809,NULL,NULL,'2012-06-11 07:25:23',NULL),(1810,NULL,NULL,'2012-06-11 07:25:37',NULL),(1811,NULL,NULL,'2012-06-11 07:25:42',NULL),(1812,NULL,NULL,'2012-06-11 07:25:45',NULL),(1813,NULL,NULL,'2012-06-11 07:25:46',NULL),(1814,NULL,NULL,'2012-06-11 07:25:53',NULL),(1815,NULL,NULL,'2012-06-11 07:26:04',NULL),(1816,NULL,NULL,'2012-06-11 07:26:09',NULL),(1817,NULL,NULL,'2012-06-11 07:26:13',NULL),(1818,NULL,NULL,'2012-06-11 07:26:37',NULL),(1819,NULL,NULL,'2012-06-11 07:26:41',NULL),(1820,NULL,NULL,'2012-06-11 07:26:43',NULL),(1821,NULL,NULL,'2012-06-11 07:26:56',NULL),(1822,NULL,NULL,'2012-06-11 07:27:04',NULL),(1823,NULL,NULL,'2012-06-11 07:27:54',NULL),(1824,NULL,NULL,'2012-06-11 07:28:16',NULL),(1825,NULL,NULL,'2012-06-11 07:28:45',NULL),(1826,NULL,NULL,'2012-06-11 07:29:15',NULL),(1827,NULL,NULL,'2012-06-11 07:29:17',NULL),(1828,NULL,NULL,'2012-06-11 07:29:21',NULL),(1829,NULL,NULL,'2012-06-11 07:29:24',NULL),(1830,NULL,NULL,'2012-06-11 07:29:25',NULL),(1831,NULL,NULL,'2012-06-11 07:29:36',NULL),(1832,NULL,NULL,'2012-06-11 07:29:37',NULL),(1833,NULL,NULL,'2012-06-11 07:30:26',NULL),(1834,NULL,NULL,'2012-06-11 07:30:54',NULL),(1835,NULL,NULL,'2012-06-11 07:31:02',NULL),(1836,NULL,NULL,'2012-06-11 07:31:07',NULL),(1837,NULL,NULL,'2012-06-11 07:31:08',NULL),(1838,NULL,NULL,'2012-06-11 07:31:09',NULL),(1839,NULL,NULL,'2012-06-11 07:31:13',NULL),(1840,NULL,NULL,'2012-06-11 07:31:16',NULL),(1841,NULL,NULL,'2012-06-11 07:32:07',NULL),(1842,NULL,NULL,'2012-06-11 07:32:58',NULL),(1843,NULL,NULL,'2012-06-11 07:33:02',NULL),(1844,NULL,NULL,'2012-06-11 07:33:06',NULL),(1845,NULL,NULL,'2012-06-11 07:33:36',NULL),(1846,NULL,NULL,'2012-06-11 07:33:41',NULL),(1847,NULL,NULL,'2012-06-11 07:33:48',NULL),(1848,NULL,NULL,'2012-06-11 07:33:49',NULL),(1849,NULL,NULL,'2012-06-11 07:34:39',NULL),(1850,NULL,NULL,'2012-06-11 07:34:55',NULL),(1851,NULL,NULL,'2012-06-11 07:35:16',NULL),(1852,NULL,NULL,'2012-06-11 07:35:29',NULL),(1853,NULL,NULL,'2012-06-11 07:35:34',NULL),(1854,NULL,NULL,'2012-06-11 07:36:20',NULL),(1855,NULL,NULL,'2012-06-11 07:37:11',NULL),(1856,NULL,NULL,'2012-06-11 07:37:32',NULL),(1857,NULL,NULL,'2012-06-11 07:38:01',NULL),(1858,NULL,NULL,'2012-06-11 07:38:52',NULL),(1859,NULL,NULL,'2012-06-11 07:39:42',NULL),(1860,NULL,NULL,'2012-06-11 07:40:33',NULL),(1861,NULL,NULL,'2012-06-11 07:40:35',NULL),(1862,NULL,NULL,'2012-06-11 07:41:23',NULL),(1863,NULL,NULL,'2012-06-11 07:42:14',NULL),(1864,NULL,NULL,'2012-06-11 07:42:46',NULL),(1865,NULL,NULL,'2012-06-11 07:43:04',NULL),(1866,NULL,NULL,'2012-06-11 07:43:27',NULL),(1867,NULL,NULL,'2012-06-11 07:43:55',NULL),(1868,NULL,NULL,'2012-06-11 07:44:36',NULL),(1869,NULL,NULL,'2012-06-11 07:44:46',NULL),(1870,NULL,NULL,'2012-06-11 07:45:29',NULL),(1871,NULL,NULL,'2012-06-11 07:45:36',NULL),(1872,NULL,NULL,'2012-06-11 07:45:49',NULL),(1873,NULL,NULL,'2012-06-11 07:46:27',NULL),(1874,NULL,NULL,'2012-06-11 07:47:17',NULL),(1875,NULL,NULL,'2012-06-11 07:47:21',NULL),(1876,NULL,NULL,'2012-06-11 07:48:08',NULL),(1877,NULL,NULL,'2012-06-11 07:48:14',NULL),(1878,NULL,NULL,'2012-06-11 07:48:28',NULL),(1879,NULL,NULL,'2012-06-11 07:48:58',NULL),(1880,NULL,NULL,'2012-06-11 07:49:15',NULL),(1881,NULL,NULL,'2012-06-11 07:49:37',NULL),(1882,NULL,NULL,'2012-06-11 07:49:49',NULL),(1883,NULL,NULL,'2012-06-11 07:50:10',NULL),(1884,NULL,NULL,'2012-06-11 07:50:22',NULL),(1885,NULL,NULL,'2012-06-11 07:50:40',NULL),(1886,NULL,NULL,'2012-06-11 07:51:17',NULL),(1887,NULL,NULL,'2012-06-11 07:51:30',NULL),(1888,NULL,NULL,'2012-06-11 07:52:00',NULL),(1889,NULL,NULL,'2012-06-11 07:52:01',NULL),(1890,NULL,NULL,'2012-06-11 07:52:21',NULL),(1891,NULL,NULL,'2012-06-11 07:52:55',NULL),(1892,NULL,NULL,'2012-06-11 07:53:11',NULL),(1893,NULL,NULL,'2012-06-11 07:53:16',NULL),(1894,NULL,NULL,'2012-06-11 07:54:02',NULL),(1895,NULL,NULL,'2012-06-11 07:54:29',NULL),(1896,NULL,NULL,'2012-06-11 07:54:30',NULL),(1897,NULL,NULL,'2012-06-11 07:54:52',NULL),(1898,NULL,NULL,'2012-06-11 07:55:43',NULL),(1899,NULL,NULL,'2012-06-11 07:56:33',NULL),(1900,NULL,NULL,'2012-06-11 07:56:42',NULL),(1901,NULL,NULL,'2012-06-11 07:57:24',NULL),(1902,NULL,NULL,'2012-06-11 07:58:06',NULL),(1903,NULL,NULL,'2012-06-11 07:58:07',NULL),(1904,NULL,NULL,'2012-06-11 07:58:15',NULL),(1905,NULL,NULL,'2012-06-11 07:59:05',NULL),(1906,NULL,NULL,'2012-06-11 07:59:56',NULL),(1907,NULL,NULL,'2012-06-11 08:00:11',NULL),(1908,NULL,NULL,'2012-06-11 08:00:13',NULL),(1909,NULL,NULL,'2012-06-11 08:00:46',NULL),(1910,NULL,NULL,'2012-06-11 08:01:13',NULL),(1911,NULL,NULL,'2012-06-11 08:01:37',NULL),(1912,NULL,NULL,'2012-06-11 08:02:27',NULL),(1913,NULL,NULL,'2012-06-11 08:03:16',NULL),(1914,NULL,NULL,'2012-06-11 08:03:18',NULL),(1915,NULL,NULL,'2012-06-11 08:03:22',NULL),(1916,NULL,NULL,'2012-06-11 08:03:26',NULL),(1917,NULL,NULL,'2012-06-11 08:03:42',NULL),(1918,NULL,NULL,'2012-06-11 08:04:08',NULL),(1919,NULL,NULL,'2012-06-11 08:04:29',NULL),(1920,NULL,NULL,'2012-06-11 08:04:42',NULL),(1921,NULL,NULL,'2012-06-11 08:04:59',NULL),(1922,NULL,NULL,'2012-06-11 08:05:07',NULL),(1923,NULL,NULL,'2012-06-11 08:05:50',NULL),(1924,NULL,NULL,'2012-06-11 08:05:51',NULL),(1925,NULL,NULL,'2012-06-11 08:05:55',NULL),(1926,NULL,NULL,'2012-06-11 08:05:59',NULL),(1927,NULL,NULL,'2012-06-11 08:06:23',NULL),(1928,NULL,NULL,'2012-06-11 08:06:40',NULL),(1929,NULL,NULL,'2012-06-11 08:07:27',NULL),(1930,NULL,NULL,'2012-06-11 08:07:30',NULL),(1931,NULL,NULL,'2012-06-11 08:07:34',NULL),(1932,NULL,NULL,'2012-06-11 08:08:21',NULL),(1933,NULL,NULL,'2012-06-11 08:08:53',NULL),(1934,NULL,NULL,'2012-06-11 08:08:55',NULL),(1935,NULL,NULL,'2012-06-11 08:09:12',NULL),(1936,NULL,NULL,'2012-06-11 08:09:38',NULL),(1937,NULL,NULL,'2012-06-11 08:09:39',NULL),(1938,NULL,NULL,'2012-06-11 08:10:02',NULL),(1939,NULL,NULL,'2012-06-11 08:10:43',NULL),(1940,NULL,NULL,'2012-06-11 08:10:53',NULL),(1941,NULL,NULL,'2012-06-11 08:11:20',NULL),(1942,NULL,NULL,'2012-06-11 08:11:44',NULL),(1943,NULL,NULL,'2012-06-11 08:12:15',NULL),(1944,NULL,NULL,'2012-06-11 08:12:34',NULL),(1945,NULL,NULL,'2012-06-11 08:13:25',NULL),(1946,NULL,NULL,'2012-06-11 08:13:51',NULL),(1947,NULL,NULL,'2012-06-11 08:14:02',NULL),(1948,NULL,NULL,'2012-06-11 08:14:15',NULL),(1949,NULL,NULL,'2012-06-11 08:14:59',NULL),(1950,NULL,NULL,'2012-06-11 08:15:03',NULL),(1951,NULL,NULL,'2012-06-11 08:15:06',NULL),(1952,NULL,NULL,'2012-06-11 08:15:23',NULL),(1953,NULL,NULL,'2012-06-11 08:15:56',NULL),(1954,NULL,NULL,'2012-06-11 08:16:47',NULL),(1955,NULL,NULL,'2012-06-11 08:17:37',NULL),(1956,NULL,NULL,'2012-06-11 08:18:28',NULL),(1957,NULL,NULL,'2012-06-11 08:19:19',NULL),(1958,NULL,NULL,'2012-06-11 08:19:33',NULL),(1959,NULL,NULL,'2012-06-11 08:20:09',NULL),(1960,NULL,NULL,'2012-06-11 08:20:37',NULL),(1961,NULL,NULL,'2012-06-11 08:21:00',NULL),(1962,NULL,NULL,'2012-06-11 08:21:50',NULL),(1963,NULL,NULL,'2012-06-11 08:22:41',NULL),(1964,NULL,NULL,'2012-06-11 08:23:31',NULL),(1965,NULL,NULL,'2012-06-11 08:23:45',NULL),(1966,NULL,NULL,'2012-06-11 08:23:47',NULL),(1967,NULL,NULL,'2012-06-11 08:24:02',NULL),(1968,NULL,NULL,'2012-06-11 08:24:22',NULL),(1969,NULL,NULL,'2012-06-11 08:24:57',NULL),(1970,NULL,NULL,'2012-06-11 08:25:13',NULL),(1971,NULL,NULL,'2012-06-11 08:25:30',NULL),(1972,NULL,NULL,'2012-06-11 08:26:03',NULL),(1973,NULL,NULL,'2012-06-11 08:26:54',NULL),(1974,NULL,NULL,'2012-06-11 08:27:44',NULL),(1975,NULL,NULL,'2012-06-11 08:28:16',NULL),(1976,NULL,NULL,'2012-06-11 08:28:35',NULL),(1977,NULL,NULL,'2012-06-11 08:28:45',NULL),(1978,NULL,NULL,'2012-06-11 08:29:25',NULL),(1979,NULL,NULL,'2012-06-11 08:30:16',NULL),(1980,NULL,NULL,'2012-06-11 08:30:24',NULL),(1981,NULL,NULL,'2012-06-11 08:31:06',NULL),(1982,NULL,NULL,'2012-06-11 08:31:28',NULL),(1983,NULL,NULL,'2012-06-11 08:31:40',NULL),(1984,NULL,NULL,'2012-06-11 08:31:57',NULL),(1985,NULL,NULL,'2012-06-11 08:32:48',NULL),(1986,NULL,NULL,'2012-06-11 08:33:38',NULL),(1987,NULL,NULL,'2012-06-11 08:33:54',NULL),(1988,NULL,NULL,'2012-06-11 08:34:15',NULL),(1989,NULL,NULL,'2012-06-11 08:34:29',NULL),(1990,NULL,NULL,'2012-06-11 08:34:47',NULL),(1991,NULL,NULL,'2012-06-11 08:35:19',NULL),(1992,NULL,NULL,'2012-06-11 08:36:10',NULL),(1993,NULL,NULL,'2012-06-11 08:37:00',NULL),(1994,NULL,NULL,'2012-06-11 08:37:43',NULL),(1995,NULL,NULL,'2012-06-11 08:37:51',NULL),(1996,NULL,NULL,'2012-06-11 08:38:41',NULL),(1997,NULL,NULL,'2012-06-11 08:39:25',NULL),(1998,NULL,NULL,'2012-06-11 08:39:31',NULL),(1999,NULL,NULL,'2012-06-11 08:39:33',NULL),(2000,NULL,NULL,'2012-06-11 08:40:23',NULL),(2001,NULL,NULL,'2012-06-11 08:41:08',NULL),(2002,NULL,NULL,'2012-06-11 08:41:13',NULL),(2003,NULL,NULL,'2012-06-11 08:41:21',NULL),(2004,NULL,NULL,'2012-06-11 08:41:36',NULL),(2005,NULL,NULL,'2012-06-11 08:41:40',NULL),(2006,NULL,NULL,'2012-06-11 08:42:04',NULL),(2007,NULL,NULL,'2012-06-11 08:42:54',NULL),(2008,NULL,NULL,'2012-06-11 08:43:37',NULL),(2009,NULL,NULL,'2012-06-11 08:43:45',NULL),(2010,NULL,NULL,'2012-06-11 08:43:46',NULL),(2011,NULL,NULL,'2012-06-11 08:43:52',NULL),(2012,NULL,NULL,'2012-06-11 08:44:02',NULL),(2013,NULL,NULL,'2012-06-11 08:44:35',NULL),(2014,NULL,NULL,'2012-06-11 08:44:59',NULL),(2015,NULL,NULL,'2012-06-11 08:45:01',NULL),(2016,NULL,NULL,'2012-06-11 08:45:07',NULL),(2017,NULL,NULL,'2012-06-11 08:45:19',NULL),(2018,NULL,NULL,'2012-06-11 08:45:20',NULL),(2019,NULL,NULL,'2012-06-11 08:45:26',NULL),(2020,NULL,NULL,'2012-06-11 08:45:43',NULL),(2021,NULL,NULL,'2012-06-11 08:45:58',NULL),(2022,NULL,NULL,'2012-06-11 08:46:17',NULL),(2023,NULL,NULL,'2012-06-11 08:46:22',NULL),(2024,NULL,NULL,'2012-06-11 08:46:29',NULL),(2025,NULL,NULL,'2012-06-11 08:47:07',NULL),(2026,NULL,NULL,'2012-06-11 08:47:12',NULL),(2027,NULL,NULL,'2012-06-11 08:47:16',NULL),(2028,NULL,NULL,'2012-06-11 08:47:20',NULL),(2029,NULL,NULL,'2012-06-11 08:47:23',NULL),(2030,NULL,NULL,'2012-06-11 08:47:29',NULL),(2031,NULL,NULL,'2012-06-11 08:47:58',NULL),(2032,NULL,NULL,'2012-06-11 08:48:32',NULL),(2033,NULL,NULL,'2012-06-11 08:48:33',NULL),(2034,NULL,NULL,'2012-06-11 08:48:48',NULL),(2035,NULL,NULL,'2012-06-11 08:49:06',NULL),(2036,NULL,NULL,'2012-06-11 08:49:10',NULL),(2037,NULL,NULL,'2012-06-11 08:49:26',NULL),(2038,NULL,NULL,'2012-06-11 08:49:39',NULL),(2039,NULL,NULL,'2012-06-11 08:50:29',NULL),(2040,NULL,NULL,'2012-06-11 08:50:37',NULL),(2041,NULL,NULL,'2012-06-11 08:50:39',NULL),(2042,NULL,NULL,'2012-06-11 08:51:20',NULL),(2043,NULL,NULL,'2012-06-11 08:51:38',NULL),(2044,NULL,NULL,'2012-06-11 08:51:39',NULL),(2045,NULL,NULL,'2012-06-11 08:51:59',NULL),(2046,NULL,NULL,'2012-06-11 08:52:03',NULL),(2047,NULL,NULL,'2012-06-11 08:52:10',NULL),(2048,NULL,NULL,'2012-06-11 08:53:01',NULL),(2049,NULL,NULL,'2012-06-11 08:53:14',NULL),(2050,NULL,NULL,'2012-06-11 08:53:20',NULL),(2051,NULL,NULL,'2012-06-11 08:53:52',NULL),(2052,NULL,NULL,'2012-06-11 08:54:42',NULL),(2053,NULL,NULL,'2012-06-11 08:55:15',NULL),(2054,NULL,NULL,'2012-06-11 08:55:16',NULL),(2055,NULL,NULL,'2012-06-11 08:55:17',NULL),(2056,NULL,NULL,'2012-06-11 08:55:19',NULL),(2057,NULL,NULL,'2012-06-11 08:55:30',NULL),(2058,NULL,NULL,'2012-06-11 08:55:33',NULL),(2059,NULL,NULL,'2012-06-11 08:56:23',NULL),(2060,NULL,NULL,'2012-06-11 08:57:14',NULL),(2061,NULL,NULL,'2012-06-11 08:57:39',NULL),(2062,NULL,NULL,'2012-06-11 08:57:42',NULL),(2063,NULL,NULL,'2012-06-11 08:57:44',NULL),(2064,NULL,NULL,'2012-06-11 08:57:45',NULL),(2065,NULL,NULL,'2012-06-11 08:57:53',NULL),(2066,NULL,NULL,'2012-06-11 08:58:04',NULL),(2067,NULL,NULL,'2012-06-11 08:58:55',NULL),(2068,NULL,NULL,'2012-06-11 08:59:24',NULL),(2069,NULL,NULL,'2012-06-11 08:59:26',NULL),(2070,NULL,NULL,'2012-06-11 08:59:34',NULL),(2071,NULL,NULL,'2012-06-11 08:59:37',NULL),(2072,NULL,NULL,'2012-06-11 08:59:45',NULL),(2073,NULL,NULL,'2012-06-11 08:59:51',NULL),(2074,NULL,NULL,'2012-06-11 09:00:36',NULL),(2075,NULL,NULL,'2012-06-11 09:01:24',NULL),(2076,NULL,NULL,'2012-06-11 09:01:27',NULL),(2077,NULL,NULL,'2012-06-11 09:01:34',NULL),(2078,NULL,NULL,'2012-06-11 09:01:45',NULL),(2079,NULL,NULL,'2012-06-11 09:02:17',NULL),(2080,NULL,NULL,'2012-06-11 09:03:05',NULL),(2081,NULL,NULL,'2012-06-11 09:03:07',NULL),(2082,NULL,NULL,'2012-06-11 09:03:46',NULL),(2083,NULL,NULL,'2012-06-11 09:03:50',NULL),(2084,NULL,NULL,'2012-06-11 09:03:58',NULL),(2085,NULL,NULL,'2012-06-11 09:04:04',NULL),(2086,NULL,NULL,'2012-06-11 09:04:47',NULL),(2087,NULL,NULL,'2012-06-11 09:04:49',NULL),(2088,NULL,NULL,'2012-06-11 09:04:50',NULL),(2089,NULL,NULL,'2012-06-11 09:05:04',NULL),(2090,NULL,NULL,'2012-06-11 09:05:11',NULL),(2091,NULL,NULL,'2012-06-11 09:05:38',NULL),(2092,NULL,NULL,'2012-06-11 09:05:39',NULL),(2093,NULL,NULL,'2012-06-11 09:06:30',NULL),(2094,NULL,NULL,'2012-06-11 09:06:54',NULL),(2095,NULL,NULL,'2012-06-11 09:06:59',NULL),(2096,NULL,NULL,'2012-06-11 09:07:08',NULL),(2097,NULL,NULL,'2012-06-11 09:07:20',NULL),(2098,NULL,NULL,'2012-06-11 09:08:01',NULL),(2099,NULL,NULL,'2012-06-11 09:08:11',NULL),(2100,NULL,NULL,'2012-06-11 09:08:16',NULL),(2101,NULL,NULL,'2012-06-11 09:08:57',NULL),(2102,NULL,NULL,'2012-06-11 09:09:08',NULL),(2103,NULL,NULL,'2012-06-11 09:09:46',NULL),(2104,NULL,NULL,'2012-06-11 09:09:52',NULL),(2105,NULL,NULL,'2012-06-11 09:10:00',NULL),(2106,NULL,NULL,'2012-06-11 09:10:36',NULL),(2107,NULL,NULL,'2012-06-11 09:10:43',NULL),(2108,NULL,NULL,'2012-06-11 09:11:33',NULL),(2109,NULL,NULL,'2012-06-11 09:12:24',NULL),(2110,NULL,NULL,'2012-06-11 09:13:15',NULL),(2111,NULL,NULL,'2012-06-11 09:13:45',NULL),(2112,NULL,NULL,'2012-06-11 09:13:47',NULL),(2113,NULL,NULL,'2012-06-11 09:13:49',NULL),(2114,NULL,NULL,'2012-06-11 09:14:28',NULL),(2115,NULL,NULL,'2012-06-11 09:15:10',NULL),(2116,NULL,NULL,'2012-06-11 09:15:51',NULL),(2117,NULL,NULL,'2012-06-11 09:15:56',NULL),(2118,NULL,NULL,'2012-06-11 09:15:58',NULL),(2119,NULL,NULL,'2012-06-11 09:16:12',NULL),(2120,NULL,NULL,'2012-06-11 09:16:33',NULL),(2121,NULL,NULL,'2012-06-11 09:17:14',NULL),(2122,NULL,NULL,'2012-06-11 09:17:17',NULL),(2123,NULL,NULL,'2012-06-11 09:17:21',NULL),(2124,NULL,NULL,'2012-06-11 09:17:30',NULL),(2125,NULL,NULL,'2012-06-11 09:17:32',NULL),(2126,NULL,NULL,'2012-06-11 09:18:37',NULL),(2127,NULL,NULL,'2012-06-11 09:19:14',NULL),(2128,NULL,NULL,'2012-06-11 09:19:15',NULL),(2129,NULL,NULL,'2012-06-11 09:19:19',NULL),(2130,NULL,NULL,'2012-06-11 09:19:21',NULL),(2131,NULL,NULL,'2012-06-11 09:20:00',NULL),(2132,NULL,NULL,'2012-06-11 09:20:42',NULL),(2133,NULL,NULL,'2012-06-11 09:21:23',NULL),(2134,NULL,NULL,'2012-06-11 09:22:05',NULL),(2135,NULL,NULL,'2012-06-11 09:22:46',NULL),(2136,NULL,NULL,'2012-06-11 09:23:28',NULL),(2137,NULL,NULL,'2012-06-11 09:24:09',NULL),(2138,NULL,NULL,'2012-06-11 09:24:51',NULL),(2139,NULL,NULL,'2012-06-11 09:25:32',NULL),(2140,NULL,NULL,'2012-06-11 09:26:14',NULL),(2141,NULL,NULL,'2012-06-11 09:26:55',NULL),(2142,NULL,NULL,'2012-06-11 09:27:37',NULL),(2143,NULL,NULL,'2012-06-11 09:28:18',NULL),(2144,NULL,NULL,'2012-06-11 09:29:00',NULL),(2145,NULL,NULL,'2012-06-11 09:29:19',NULL),(2146,NULL,NULL,'2012-06-11 09:29:29',NULL),(2147,NULL,NULL,'2012-06-11 09:29:31',NULL),(2148,NULL,NULL,'2012-06-11 09:29:33',NULL),(2149,NULL,NULL,'2012-06-11 09:29:34',NULL),(2150,NULL,NULL,'2012-06-11 09:29:38',NULL),(2151,NULL,NULL,'2012-06-11 09:29:41',NULL),(2152,NULL,NULL,'2012-06-11 09:30:23',NULL),(2153,NULL,NULL,'2012-06-11 09:30:45',NULL),(2154,NULL,NULL,'2012-06-11 09:30:48',NULL),(2155,NULL,NULL,'2012-06-11 09:31:00',NULL),(2156,NULL,NULL,'2012-06-11 09:31:03',NULL),(2157,NULL,NULL,'2012-06-11 09:31:05',NULL),(2158,NULL,NULL,'2012-06-11 09:31:06',NULL),(2159,NULL,NULL,'2012-06-11 09:31:07',NULL),(2160,NULL,NULL,'2012-06-11 09:31:46',NULL),(2161,NULL,NULL,'2012-06-11 09:32:30',NULL),(2162,NULL,NULL,'2012-06-11 09:32:56',NULL),(2163,NULL,NULL,'2012-06-11 09:33:09',NULL),(2164,NULL,NULL,'2012-06-11 09:33:50',NULL),(2165,NULL,NULL,'2012-06-11 09:33:59',NULL),(2166,NULL,NULL,'2012-06-11 09:34:27',NULL),(2167,NULL,NULL,'2012-06-11 09:34:32',NULL),(2168,NULL,NULL,'2012-06-11 09:35:13',NULL),(2169,NULL,NULL,'2012-06-11 09:35:55',NULL),(2170,NULL,NULL,'2012-06-11 09:36:36',NULL),(2171,NULL,NULL,'2012-06-11 09:37:18',NULL),(2172,NULL,NULL,'2012-06-11 09:37:59',NULL),(2173,NULL,NULL,'2012-06-11 09:38:19',NULL),(2174,NULL,NULL,'2012-06-11 09:38:41',NULL),(2175,NULL,NULL,'2012-06-11 09:38:45',NULL),(2176,NULL,NULL,'2012-06-11 09:39:03',NULL),(2177,NULL,NULL,'2012-06-11 09:39:22',NULL),(2178,NULL,NULL,'2012-06-11 09:40:04',NULL),(2179,NULL,NULL,'2012-06-11 09:40:45',NULL),(2180,NULL,NULL,'2012-06-11 09:41:27',NULL),(2181,NULL,NULL,'2012-06-11 09:42:08',NULL),(2182,NULL,NULL,'2012-06-11 09:42:50',NULL),(2183,NULL,NULL,'2012-06-11 09:43:31',NULL),(2184,NULL,NULL,'2012-06-11 09:43:33',NULL),(2185,NULL,NULL,'2012-06-11 09:43:34',NULL),(2186,NULL,NULL,'2012-06-11 09:43:41',NULL),(2187,NULL,NULL,'2012-06-11 09:44:13',NULL),(2188,NULL,NULL,'2012-06-11 09:44:54',NULL),(2189,NULL,NULL,'2012-06-11 09:45:36',NULL),(2190,NULL,NULL,'2012-06-11 09:46:17',NULL),(2191,NULL,NULL,'2012-06-11 09:46:29',NULL),(2192,NULL,NULL,'2012-06-11 09:46:31',NULL),(2193,NULL,NULL,'2012-06-11 09:46:41',NULL),(2194,NULL,NULL,'2012-06-11 09:46:44',NULL),(2195,NULL,NULL,'2012-06-11 09:46:59',NULL),(2196,NULL,NULL,'2012-06-11 09:47:40',NULL),(2197,NULL,NULL,'2012-06-11 09:48:08',NULL),(2198,NULL,NULL,'2012-06-11 09:48:20',NULL),(2199,NULL,NULL,'2012-06-11 09:48:22',NULL),(2200,NULL,NULL,'2012-06-11 09:48:26',NULL),(2201,NULL,NULL,'2012-06-11 09:48:37',NULL),(2202,NULL,NULL,'2012-06-11 09:49:03',NULL),(2203,NULL,NULL,'2012-06-11 09:49:06',NULL),(2204,NULL,NULL,'2012-06-11 09:49:16',NULL),(2205,NULL,NULL,'2012-06-11 09:49:27',NULL),(2206,NULL,NULL,'2012-06-11 09:49:31',NULL),(2207,NULL,NULL,'2012-06-11 09:49:33',NULL),(2208,NULL,NULL,'2012-06-11 09:49:35',NULL),(2209,NULL,NULL,'2012-06-11 09:49:37',NULL),(2210,NULL,NULL,'2012-06-11 09:49:38',NULL),(2211,NULL,NULL,'2012-06-11 09:49:40',NULL),(2212,NULL,NULL,'2012-06-11 09:49:41',NULL),(2213,NULL,NULL,'2012-06-11 09:49:45',NULL),(2214,NULL,NULL,'2012-06-11 09:50:26',NULL),(2215,NULL,NULL,'2012-06-11 09:50:30',NULL),(2216,NULL,NULL,'2012-06-11 09:50:42',NULL),(2217,NULL,NULL,'2012-06-11 09:51:08',NULL),(2218,NULL,NULL,'2012-06-11 09:51:23',NULL),(2219,NULL,NULL,'2012-06-11 09:51:49',NULL),(2220,NULL,NULL,'2012-06-11 09:52:31',NULL),(2221,NULL,NULL,'2012-06-11 09:52:42',NULL),(2222,NULL,NULL,'2012-06-11 09:53:12',NULL),(2223,NULL,NULL,'2012-06-11 09:53:57',NULL),(2224,NULL,NULL,'2012-06-11 09:54:35',NULL),(2225,NULL,NULL,'2012-06-11 09:55:17',NULL),(2226,NULL,NULL,'2012-06-11 09:55:59',NULL),(2227,NULL,NULL,'2012-06-11 09:56:40',NULL),(2228,NULL,NULL,'2012-06-11 09:57:21',NULL),(2229,NULL,NULL,'2012-06-11 09:58:03',NULL),(2230,NULL,NULL,'2012-06-11 09:58:44',NULL),(2231,NULL,NULL,'2012-06-11 09:59:26',NULL),(2232,NULL,NULL,'2012-06-11 10:00:07',NULL),(2233,NULL,NULL,'2012-06-11 10:00:49',NULL),(2234,NULL,NULL,'2012-06-11 10:01:30',NULL),(2235,NULL,NULL,'2012-06-11 10:02:12',NULL),(2236,NULL,NULL,'2012-06-11 10:02:48',NULL),(2237,NULL,NULL,'2012-06-11 10:03:11',NULL),(2238,NULL,NULL,'2012-06-11 10:03:35',NULL),(2239,NULL,NULL,'2012-06-11 10:04:16',NULL),(2240,NULL,NULL,'2012-06-11 10:04:58',NULL),(2241,NULL,NULL,'2012-06-11 10:05:45',NULL),(2242,NULL,NULL,'2012-06-11 10:05:54',NULL),(2243,NULL,NULL,'2012-06-11 10:05:56',NULL),(2244,NULL,NULL,'2012-06-11 10:05:58',NULL),(2245,NULL,NULL,'2012-06-11 10:05:59',NULL),(2246,NULL,NULL,'2012-06-11 10:06:01',NULL),(2247,NULL,NULL,'2012-06-11 10:06:03',NULL),(2248,NULL,NULL,'2012-06-11 10:06:04',NULL),(2249,NULL,NULL,'2012-06-11 10:06:12',NULL),(2250,NULL,NULL,'2012-06-11 10:06:20',NULL),(2251,NULL,NULL,'2012-06-11 10:06:22',NULL),(2252,NULL,NULL,'2012-06-11 10:07:02',NULL),(2253,NULL,NULL,'2012-06-11 10:07:44',NULL),(2254,NULL,NULL,'2012-06-11 10:11:21',NULL),(2255,NULL,NULL,'2012-06-11 10:11:28',NULL),(2256,NULL,NULL,'2012-06-11 10:11:54',NULL),(2257,NULL,NULL,'2012-06-11 10:12:18',NULL),(2258,1,NULL,'2012-06-11 10:12:30','2012-06-11 10:12:46'),(2259,NULL,NULL,'2012-06-11 10:12:38',NULL),(2260,NULL,NULL,'2012-06-11 10:12:55',NULL),(2261,NULL,NULL,'2012-06-11 10:13:15',NULL),(2262,NULL,NULL,'2012-06-11 10:13:28',NULL),(2263,NULL,NULL,'2012-06-11 10:13:40',NULL),(2264,NULL,NULL,'2012-06-11 10:13:59',NULL),(2265,NULL,NULL,'2012-06-11 10:14:07',NULL),(2266,NULL,NULL,'2012-06-11 10:14:14',NULL),(2267,NULL,NULL,'2012-06-11 10:14:20',NULL),(2268,NULL,NULL,'2012-06-11 10:14:26',NULL),(2269,NULL,NULL,'2012-06-11 10:14:31',NULL),(2270,NULL,NULL,'2012-06-11 10:14:38',NULL),(2271,NULL,NULL,'2012-06-11 10:14:48',NULL),(2272,NULL,NULL,'2012-06-11 10:14:52',NULL),(2273,NULL,NULL,'2012-06-11 10:15:20',NULL),(2274,NULL,NULL,'2012-06-11 10:16:01',NULL),(2275,NULL,NULL,'2012-06-11 10:16:43',NULL),(2276,NULL,NULL,'2012-06-11 10:17:24',NULL),(2277,NULL,NULL,'2012-06-11 10:18:06',NULL),(2278,NULL,NULL,'2012-06-11 10:18:09',NULL),(2279,NULL,NULL,'2012-06-11 10:18:11',NULL),(2280,NULL,NULL,'2012-06-11 10:18:14',NULL),(2281,NULL,NULL,'2012-06-11 10:18:16',NULL),(2282,NULL,NULL,'2012-06-11 10:18:18',NULL),(2283,NULL,NULL,'2012-06-11 10:18:20',NULL),(2284,NULL,NULL,'2012-06-11 10:18:22',NULL),(2285,NULL,NULL,'2012-06-11 10:18:23',NULL),(2286,NULL,NULL,'2012-06-11 10:18:25',NULL),(2287,NULL,NULL,'2012-06-11 10:18:26',NULL),(2288,NULL,NULL,'2012-06-11 10:18:28',NULL),(2289,NULL,NULL,'2012-06-11 10:18:29',NULL),(2290,NULL,NULL,'2012-06-11 10:18:47',NULL),(2291,NULL,NULL,'2012-06-11 10:19:21',NULL),(2292,NULL,NULL,'2012-06-11 10:19:29',NULL),(2293,NULL,NULL,'2012-06-11 10:20:10',NULL),(2294,NULL,NULL,'2012-06-11 10:20:52',NULL),(2295,NULL,NULL,'2012-06-11 10:20:53',NULL),(2296,NULL,NULL,'2012-06-11 10:21:33',NULL),(2297,NULL,NULL,'2012-06-11 10:21:56',NULL),(2298,NULL,NULL,'2012-06-11 10:22:01',NULL),(2299,NULL,NULL,'2012-06-11 10:22:04',NULL),(2300,NULL,NULL,'2012-06-11 10:22:08',NULL),(2301,NULL,NULL,'2012-06-11 10:22:09',NULL),(2302,NULL,NULL,'2012-06-11 10:22:11',NULL),(2303,NULL,NULL,'2012-06-11 10:22:12',NULL),(2304,NULL,NULL,'2012-06-11 10:22:15',NULL),(2305,NULL,NULL,'2012-06-11 10:22:21',NULL),(2306,NULL,NULL,'2012-06-11 10:22:24',NULL),(2307,NULL,NULL,'2012-06-11 10:22:56',NULL),(2308,NULL,NULL,'2012-06-11 10:23:38',NULL),(2309,NULL,NULL,'2012-06-11 10:24:19',NULL),(2310,NULL,NULL,'2012-06-11 10:25:01',NULL),(2311,NULL,'0:0:0:0:0:0:0:1','2012-06-11 10:25:38',NULL),(2312,NULL,NULL,'2012-06-11 10:25:43',NULL),(2313,NULL,NULL,'2012-06-11 10:27:06',NULL),(2314,NULL,NULL,'2012-06-11 10:27:08',NULL),(2315,NULL,NULL,'2012-06-11 10:27:21',NULL),(2316,NULL,NULL,'2012-06-11 10:27:23',NULL),(2317,NULL,NULL,'2012-06-11 10:27:31',NULL),(2318,NULL,NULL,'2012-06-11 10:27:36',NULL),(2319,NULL,NULL,'2012-06-11 10:27:38',NULL),(2320,NULL,NULL,'2012-06-11 10:27:41',NULL),(2321,NULL,NULL,'2012-06-11 10:27:44',NULL),(2322,NULL,NULL,'2012-06-11 10:27:45',NULL),(2323,NULL,NULL,'2012-06-11 10:27:47',NULL),(2324,NULL,NULL,'2012-06-11 10:27:49',NULL),(2325,NULL,NULL,'2012-06-11 10:27:52',NULL),(2326,NULL,NULL,'2012-06-11 10:28:28',NULL),(2327,NULL,NULL,'2012-06-11 10:29:10',NULL),(2328,1,NULL,'2012-06-11 10:29:35','2012-06-11 10:29:42'),(2329,NULL,NULL,'2012-06-11 10:29:49',NULL),(2330,NULL,NULL,'2012-06-11 10:29:51',NULL),(2331,NULL,NULL,'2012-06-11 10:30:33',NULL),(2332,NULL,NULL,'2012-06-11 10:31:14',NULL),(2333,NULL,NULL,'2012-06-11 10:31:56',NULL),(2334,NULL,NULL,'2012-06-11 10:32:37',NULL),(2335,NULL,NULL,'2012-06-11 10:32:49',NULL),(2336,NULL,NULL,'2012-06-11 10:32:54',NULL),(2337,NULL,NULL,'2012-06-11 10:32:56',NULL),(2338,NULL,NULL,'2012-06-11 10:32:57',NULL),(2339,NULL,NULL,'2012-06-11 10:32:59',NULL),(2340,NULL,NULL,'2012-06-11 10:33:01',NULL),(2341,NULL,NULL,'2012-06-11 10:33:02',NULL),(2342,NULL,NULL,'2012-06-11 10:33:04',NULL),(2343,NULL,NULL,'2012-06-11 10:33:05',NULL),(2344,NULL,NULL,'2012-06-11 10:33:06',NULL),(2345,NULL,NULL,'2012-06-11 10:33:08',NULL),(2346,NULL,NULL,'2012-06-11 10:33:29',NULL),(2347,NULL,NULL,'2012-06-11 10:33:32',NULL),(2348,NULL,NULL,'2012-06-11 10:34:00',NULL),(2349,NULL,NULL,'2012-06-11 10:34:29',NULL),(2350,NULL,NULL,'2012-06-11 10:34:42',NULL),(2351,NULL,NULL,'2012-06-11 10:35:24',NULL),(2352,NULL,NULL,'2012-06-11 10:36:05',NULL),(2353,NULL,NULL,'2012-06-11 10:36:46',NULL),(2354,NULL,NULL,'2012-06-11 10:37:28',NULL),(2355,NULL,NULL,'2012-06-11 10:46:30',NULL),(2356,1,NULL,'2012-06-11 10:46:34','2012-06-11 10:46:50'),(2357,NULL,NULL,'2012-06-11 10:46:41',NULL),(2358,NULL,NULL,'2012-06-11 10:47:09',NULL),(2359,NULL,NULL,'2012-06-11 10:47:50',NULL),(2360,NULL,NULL,'2012-06-11 10:48:32',NULL),(2361,NULL,NULL,'2012-06-11 10:48:42',NULL),(2362,NULL,NULL,'2012-06-11 10:48:50',NULL),(2363,NULL,NULL,'2012-06-11 10:49:14',NULL),(2364,NULL,NULL,'2012-06-11 10:49:55',NULL),(2365,NULL,NULL,'2012-06-11 10:50:36',NULL),(2366,NULL,NULL,'2012-06-11 10:50:54',NULL),(2367,NULL,NULL,'2012-06-11 10:51:18',NULL),(2368,NULL,NULL,'2012-06-11 10:51:59',NULL),(2369,NULL,NULL,'2012-06-11 10:52:41',NULL),(2370,NULL,NULL,'2012-06-11 10:53:17',NULL),(2371,NULL,NULL,'2012-06-11 10:53:20',NULL),(2372,NULL,NULL,'2012-06-11 10:53:23',NULL),(2373,NULL,NULL,'2012-06-11 10:54:04',NULL),(2374,NULL,NULL,'2012-06-11 10:54:45',NULL),(2375,NULL,NULL,'2012-06-11 10:55:27',NULL),(2376,NULL,NULL,'2012-06-11 10:55:30',NULL),(2377,NULL,NULL,'2012-06-11 10:55:32',NULL),(2378,NULL,NULL,'2012-06-11 10:56:08',NULL),(2379,NULL,NULL,'2012-06-11 10:56:50',NULL),(2380,1,'0:0:0:0:0:0:0:1','2012-06-11 10:56:56','2012-06-11 11:09:01'),(2381,NULL,NULL,'2012-06-11 10:57:31',NULL),(2382,NULL,NULL,'2012-06-11 10:58:13',NULL),(2383,NULL,NULL,'2012-06-11 10:58:36',NULL),(2384,1,NULL,'2012-06-11 10:58:46','2012-06-11 10:58:53'),(2385,NULL,NULL,'2012-06-11 10:58:55',NULL),(2386,NULL,NULL,'2012-06-11 10:59:36',NULL),(2387,NULL,NULL,'2012-06-11 11:00:17',NULL),(2388,NULL,NULL,'2012-06-11 11:00:19',NULL),(2389,NULL,NULL,'2012-06-11 11:01:02',NULL),(2390,NULL,NULL,'2012-06-11 11:01:40',NULL),(2391,NULL,NULL,'2012-06-11 11:02:22',NULL),(2392,NULL,NULL,'2012-06-11 11:03:03',NULL),(2393,NULL,NULL,'2012-06-11 11:03:45',NULL),(2394,NULL,NULL,'2012-06-11 11:03:58',NULL),(2395,NULL,NULL,'2012-06-11 11:04:26',NULL),(2396,NULL,NULL,'2012-06-11 11:05:08',NULL),(2397,NULL,NULL,'2012-06-11 11:05:49',NULL),(2398,NULL,NULL,'2012-06-11 11:06:31',NULL),(2399,NULL,NULL,'2012-06-11 11:06:50',NULL),(2400,NULL,NULL,'2012-06-11 11:07:00',NULL),(2401,NULL,NULL,'2012-06-11 11:07:22',NULL),(2402,NULL,NULL,'2012-06-11 11:07:31',NULL),(2403,NULL,NULL,'2012-06-11 11:07:54',NULL),(2404,NULL,NULL,'2012-06-11 11:08:28',NULL),(2405,NULL,NULL,'2012-06-11 11:08:35',NULL),(2406,NULL,NULL,'2012-06-11 11:09:21',NULL),(2407,NULL,NULL,'2012-06-11 11:09:58',NULL),(2408,NULL,NULL,'2012-06-11 11:10:40',NULL),(2409,NULL,NULL,'2012-06-11 11:11:22',NULL),(2410,NULL,NULL,'2012-06-11 11:11:56',NULL),(2411,NULL,NULL,'2012-06-11 11:12:00',NULL),(2412,NULL,NULL,'2012-06-11 11:12:03',NULL),(2413,NULL,NULL,'2012-06-11 11:12:05',NULL),(2414,NULL,NULL,'2012-06-11 11:12:30',NULL),(2415,NULL,NULL,'2012-06-11 11:12:44',NULL),(2416,NULL,NULL,'2012-06-11 11:13:26',NULL),(2417,NULL,NULL,'2012-06-11 11:13:49',NULL),(2418,NULL,NULL,'2012-06-11 11:13:59',NULL),(2419,NULL,NULL,'2012-06-11 11:14:03',NULL),(2420,NULL,NULL,'2012-06-11 11:14:07',NULL),(2421,NULL,NULL,'2012-06-11 11:14:11',NULL),(2422,NULL,NULL,'2012-06-11 11:14:49',NULL),(2423,NULL,NULL,'2012-06-11 11:15:30',NULL),(2424,NULL,NULL,'2012-06-11 11:16:12',NULL),(2425,NULL,NULL,'2012-06-11 11:22:24',NULL),(2426,1,NULL,'2012-06-11 11:22:28','2012-06-11 11:22:51'),(2427,NULL,NULL,'2012-06-11 11:22:45',NULL),(2428,NULL,NULL,'2012-06-11 11:22:53',NULL),(2429,NULL,NULL,'2012-06-11 11:23:01',NULL),(2430,NULL,NULL,'2012-06-11 11:23:08',NULL),(2431,NULL,NULL,'2012-06-11 11:23:15',NULL),(2432,NULL,NULL,'2012-06-11 11:23:48',NULL),(2433,NULL,NULL,'2012-06-11 11:24:26',NULL),(2434,NULL,NULL,'2012-06-11 11:24:31',NULL),(2435,NULL,NULL,'2012-06-11 11:24:35',NULL),(2436,NULL,NULL,'2012-06-11 11:25:11',NULL),(2437,NULL,NULL,'2012-06-11 11:25:15',NULL),(2438,NULL,NULL,'2012-06-11 11:25:53',NULL),(2439,NULL,NULL,'2012-06-11 11:25:59',NULL),(2440,NULL,NULL,'2012-06-11 11:26:34',NULL),(2441,NULL,NULL,'2012-06-11 11:27:04',NULL),(2442,NULL,NULL,'2012-06-11 11:27:11',NULL),(2443,NULL,NULL,'2012-06-11 11:27:15',NULL),(2444,NULL,NULL,'2012-06-11 11:27:18',NULL),(2445,NULL,NULL,'2012-06-11 11:27:24',NULL),(2446,NULL,NULL,'2012-06-11 11:27:49',NULL),(2447,NULL,NULL,'2012-06-11 11:27:57',NULL),(2448,NULL,NULL,'2012-06-11 11:28:04',NULL),(2449,NULL,NULL,'2012-06-11 11:28:39',NULL),(2450,NULL,NULL,'2012-06-11 11:29:20',NULL),(2451,NULL,NULL,'2012-06-11 11:30:02',NULL),(2452,NULL,NULL,'2012-06-11 11:30:34',NULL),(2453,NULL,NULL,'2012-06-11 11:30:43',NULL),(2454,NULL,NULL,'2012-06-11 11:31:25',NULL),(2455,NULL,NULL,'2012-06-11 11:31:30',NULL),(2456,NULL,NULL,'2012-06-11 11:32:06',NULL),(2457,NULL,NULL,'2012-06-11 11:32:48',NULL),(2458,NULL,NULL,'2012-06-11 11:33:29',NULL),(2459,NULL,NULL,'2012-06-11 11:33:34',NULL),(2460,NULL,NULL,'2012-06-11 11:34:52',NULL),(2461,NULL,NULL,'2012-06-11 11:35:34',NULL),(2462,NULL,NULL,'2012-06-11 11:35:51',NULL),(2463,NULL,NULL,'2012-06-11 11:35:53',NULL),(2464,NULL,NULL,'2012-06-11 11:36:11',NULL),(2465,NULL,NULL,'2012-06-11 11:36:13',NULL),(2466,NULL,NULL,'2012-06-11 11:36:15',NULL),(2467,NULL,NULL,'2012-06-11 11:36:16',NULL),(2468,NULL,NULL,'2012-06-11 11:36:57',NULL),(2469,NULL,NULL,'2012-06-11 11:37:38',NULL),(2470,NULL,NULL,'2012-06-11 11:38:20',NULL),(2471,NULL,NULL,'2012-06-11 11:39:01',NULL),(2472,NULL,NULL,'2012-06-11 11:39:43',NULL),(2473,NULL,NULL,'2012-06-11 11:40:24',NULL),(2474,NULL,NULL,'2012-06-11 11:41:06',NULL),(2475,NULL,NULL,'2012-06-11 11:41:48',NULL),(2476,NULL,NULL,'2012-06-11 11:42:29',NULL),(2477,NULL,NULL,'2012-06-11 11:43:10',NULL),(2478,NULL,NULL,'2012-06-11 11:43:52',NULL),(2479,NULL,NULL,'2012-06-11 11:44:33',NULL),(2480,NULL,NULL,'2012-06-11 11:45:15',NULL),(2481,NULL,NULL,'2012-06-11 11:45:52',NULL),(2482,NULL,NULL,'2012-06-11 11:45:54',NULL),(2483,NULL,NULL,'2012-06-11 11:45:56',NULL),(2484,NULL,NULL,'2012-06-11 11:46:00',NULL),(2485,NULL,NULL,'2012-06-11 11:46:38',NULL),(2486,NULL,NULL,'2012-06-11 11:49:22',NULL),(2487,NULL,NULL,'2012-06-11 11:49:35',NULL),(2488,NULL,NULL,'2012-06-11 11:49:47',NULL),(2489,NULL,NULL,'2012-06-11 11:49:57',NULL),(2490,1,NULL,'2012-06-11 11:50:04','2012-06-11 11:50:13'),(2491,NULL,NULL,'2012-06-11 11:50:07',NULL),(2492,NULL,NULL,'2012-06-11 11:50:15',NULL),(2493,NULL,NULL,'2012-06-11 11:50:23',NULL),(2494,NULL,NULL,'2012-06-11 11:50:29',NULL),(2495,NULL,NULL,'2012-06-11 11:50:36',NULL),(2496,NULL,NULL,'2012-06-11 11:50:47',NULL),(2497,NULL,NULL,'2012-06-11 11:51:48',NULL),(2498,NULL,NULL,'2012-06-11 11:52:10',NULL),(2499,NULL,NULL,'2012-06-11 11:52:51',NULL),(2500,NULL,NULL,'2012-06-11 11:53:33',NULL),(2501,NULL,NULL,'2012-06-11 11:54:15',NULL),(2502,NULL,NULL,'2012-06-11 11:54:19',NULL),(2503,NULL,NULL,'2012-06-11 11:54:48',NULL),(2504,NULL,NULL,'2012-06-11 11:54:56',NULL),(2505,NULL,NULL,'2012-06-11 11:55:11',NULL),(2506,NULL,NULL,'2012-06-11 11:55:22',NULL),(2507,NULL,NULL,'2012-06-11 11:55:24',NULL),(2508,NULL,NULL,'2012-06-11 11:55:26',NULL),(2509,NULL,NULL,'2012-06-11 11:55:37',NULL),(2510,NULL,NULL,'2012-06-11 11:56:19',NULL),(2511,NULL,NULL,'2012-06-11 11:56:22',NULL),(2512,NULL,NULL,'2012-06-11 11:56:24',NULL),(2513,NULL,NULL,'2012-06-11 11:56:26',NULL),(2514,NULL,NULL,'2012-06-11 11:56:34',NULL),(2515,NULL,NULL,'2012-06-11 11:57:00',NULL),(2516,NULL,NULL,'2012-06-11 11:57:42',NULL),(2517,NULL,NULL,'2012-06-11 11:58:06',NULL),(2518,NULL,NULL,'2012-06-11 11:58:07',NULL),(2519,NULL,NULL,'2012-06-11 11:58:10',NULL),(2520,NULL,NULL,'2012-06-11 11:58:16',NULL),(2521,NULL,NULL,'2012-06-11 11:58:23',NULL),(2522,NULL,NULL,'2012-06-11 11:58:27',NULL),(2523,NULL,NULL,'2012-06-11 11:58:37',NULL),(2524,NULL,NULL,'2012-06-11 11:59:05',NULL),(2525,NULL,NULL,'2012-06-11 11:59:36',NULL),(2526,NULL,NULL,'2012-06-11 11:59:46',NULL),(2527,NULL,NULL,'2012-06-11 12:00:07',NULL),(2528,NULL,NULL,'2012-06-11 12:00:28',NULL),(2529,NULL,NULL,'2012-06-11 12:01:09',NULL),(2530,NULL,NULL,'2012-06-11 12:01:51',NULL),(2531,NULL,NULL,'2012-06-11 12:02:18',NULL),(2532,NULL,NULL,'2012-06-11 12:02:32',NULL),(2533,NULL,NULL,'2012-06-11 12:03:14',NULL),(2534,NULL,NULL,'2012-06-11 12:03:34',NULL),(2535,NULL,NULL,'2012-06-11 12:03:55',NULL),(2536,NULL,NULL,'2012-06-11 12:04:04',NULL),(2537,NULL,NULL,'2012-06-11 12:04:06',NULL),(2538,NULL,NULL,'2012-06-11 12:04:08',NULL),(2539,NULL,NULL,'2012-06-11 12:04:13',NULL),(2540,NULL,NULL,'2012-06-11 12:04:16',NULL),(2541,NULL,NULL,'2012-06-11 12:04:19',NULL),(2542,NULL,NULL,'2012-06-11 12:04:37',NULL),(2543,NULL,NULL,'2012-06-11 12:05:04',NULL),(2544,NULL,NULL,'2012-06-11 12:05:18',NULL),(2545,NULL,NULL,'2012-06-11 12:06:00',NULL),(2546,NULL,NULL,'2012-06-11 12:06:04',NULL),(2547,NULL,NULL,'2012-06-11 12:06:06',NULL),(2548,NULL,NULL,'2012-06-11 12:06:30',NULL),(2549,NULL,NULL,'2012-06-11 12:06:31',NULL),(2550,NULL,NULL,'2012-06-11 12:06:41',NULL),(2551,NULL,NULL,'2012-06-11 12:06:57',NULL),(2552,NULL,NULL,'2012-06-11 12:07:23',NULL),(2553,NULL,NULL,'2012-06-11 12:08:04',NULL),(2554,NULL,NULL,'2012-06-11 12:08:24',NULL),(2555,NULL,NULL,'2012-06-11 12:08:46',NULL),(2556,NULL,NULL,'2012-06-11 12:09:29',NULL),(2557,NULL,NULL,'2012-06-11 12:10:09',NULL),(2558,NULL,NULL,'2012-06-11 12:10:50',NULL),(2559,NULL,NULL,'2012-06-11 12:11:32',NULL),(2560,NULL,NULL,'2012-06-11 12:12:13',NULL),(2561,NULL,NULL,'2012-06-11 12:12:55',NULL),(2562,NULL,NULL,'2012-06-11 12:13:36',NULL),(2563,NULL,NULL,'2012-06-11 12:14:18',NULL),(2564,NULL,NULL,'2012-06-11 12:14:59',NULL),(2565,NULL,NULL,'2012-06-11 12:15:41',NULL),(2566,NULL,NULL,'2012-06-11 12:16:22',NULL),(2567,NULL,NULL,'2012-06-11 12:17:04',NULL),(2568,NULL,NULL,'2012-06-11 12:17:23',NULL),(2569,NULL,NULL,'2012-06-11 12:17:27',NULL),(2570,NULL,NULL,'2012-06-11 12:17:45',NULL),(2571,NULL,NULL,'2012-06-11 12:17:48',NULL),(2572,NULL,NULL,'2012-06-11 12:18:01',NULL),(2573,NULL,NULL,'2012-06-11 12:18:04',NULL),(2574,NULL,NULL,'2012-06-11 12:18:27',NULL),(2575,NULL,NULL,'2012-06-11 12:18:39',NULL),(2576,NULL,NULL,'2012-06-11 12:18:49',NULL),(2577,NULL,NULL,'2012-06-11 12:19:08',NULL),(2578,NULL,NULL,'2012-06-11 12:19:50',NULL),(2579,1,NULL,'2012-06-11 12:20:04','2012-06-11 12:20:10'),(2580,NULL,NULL,'2012-06-11 12:20:31',NULL),(2581,NULL,NULL,'2012-06-11 12:21:13',NULL),(2582,NULL,'0:0:0:0:0:0:0:1','2012-06-11 12:21:45',NULL),(2583,NULL,NULL,'2012-06-11 12:21:54',NULL),(2584,NULL,NULL,'2012-06-11 12:22:36',NULL),(2585,1,NULL,'2012-06-11 12:22:54','2012-06-11 12:23:01'),(2586,NULL,NULL,'2012-06-11 12:23:17',NULL),(2587,NULL,NULL,'2012-06-11 12:23:59',NULL),(2588,NULL,NULL,'2012-06-11 12:24:00',NULL),(2589,NULL,NULL,'2012-06-11 12:24:02',NULL),(2590,NULL,NULL,'2012-06-11 12:24:40',NULL),(2591,NULL,NULL,'2012-06-11 12:25:22',NULL),(2592,NULL,NULL,'2012-06-11 12:25:47',NULL),(2593,NULL,NULL,'2012-06-11 12:25:54',NULL),(2594,NULL,NULL,'2012-06-11 12:26:03',NULL),(2595,NULL,NULL,'2012-06-11 12:26:45',NULL),(2596,NULL,NULL,'2012-06-11 12:28:37',NULL),(2597,NULL,NULL,'2012-06-11 12:28:45',NULL),(2598,NULL,NULL,'2012-06-11 12:28:56',NULL),(2599,NULL,NULL,'2012-06-11 12:29:09',NULL),(2600,1,NULL,'2012-06-11 12:29:23','2012-06-11 12:29:30'),(2601,NULL,NULL,'2012-06-11 12:29:31',NULL),(2602,1,'0:0:0:0:0:0:0:1','2012-06-11 12:29:52','2012-06-11 12:29:57'),(2603,NULL,NULL,'2012-06-11 12:30:12',NULL),(2604,NULL,NULL,'2012-06-11 12:30:54',NULL),(2605,NULL,NULL,'2012-06-11 12:31:33',NULL),(2606,NULL,NULL,'2012-06-11 12:31:38',NULL),(2607,NULL,NULL,'2012-06-11 12:32:17',NULL),(2608,NULL,NULL,'2012-06-11 12:32:58',NULL),(2609,NULL,NULL,'2012-06-11 12:33:40',NULL),(2610,NULL,NULL,'2012-06-11 12:34:22',NULL),(2611,NULL,NULL,'2012-06-11 12:35:03',NULL),(2612,NULL,NULL,'2012-06-11 12:35:45',NULL),(2613,1,NULL,'2012-06-11 12:35:47','2012-06-11 12:36:03'),(2614,NULL,NULL,'2012-06-11 12:36:26',NULL),(2615,NULL,NULL,'2012-06-11 12:37:08',NULL),(2616,NULL,NULL,'2012-06-11 12:37:52',NULL),(2617,NULL,NULL,'2012-06-11 12:38:11',NULL),(2618,1,'0:0:0:0:0:0:0:1','2012-06-11 12:38:29','2012-06-11 12:39:16'),(2619,NULL,NULL,'2012-06-11 12:38:30',NULL),(2620,NULL,NULL,'2012-06-11 12:39:12',NULL),(2621,NULL,NULL,'2012-06-11 12:39:54',NULL),(2622,NULL,NULL,'2012-06-11 12:40:35',NULL),(2623,NULL,NULL,'2012-06-11 12:41:17',NULL),(2624,NULL,'0:0:0:0:0:0:0:1','2012-06-11 12:41:44',NULL),(2625,NULL,NULL,'2012-06-11 12:41:48',NULL),(2626,NULL,NULL,'2012-06-11 12:41:58',NULL),(2627,NULL,NULL,'2012-06-11 12:42:33',NULL),(2628,NULL,NULL,'2012-06-11 12:42:35',NULL),(2629,NULL,NULL,'2012-06-11 12:42:37',NULL),(2630,NULL,NULL,'2012-06-11 12:42:39',NULL),(2631,NULL,NULL,'2012-06-11 12:42:41',NULL),(2632,NULL,NULL,'2012-06-11 12:42:45',NULL),(2633,NULL,NULL,'2012-06-11 12:43:19',NULL),(2634,NULL,NULL,'2012-06-11 12:43:21',NULL),(2635,NULL,NULL,'2012-06-11 12:43:23',NULL),(2636,NULL,NULL,'2012-06-11 12:44:03',NULL),(2637,NULL,NULL,'2012-06-11 12:44:44',NULL),(2638,NULL,NULL,'2012-06-11 12:45:26',NULL),(2639,NULL,NULL,'2012-06-11 12:46:07',NULL),(2640,NULL,NULL,'2012-06-11 12:46:09',NULL),(2641,NULL,NULL,'2012-06-11 12:46:49',NULL),(2642,NULL,NULL,'2012-06-11 12:47:30',NULL),(2643,NULL,NULL,'2012-06-11 12:48:12',NULL),(2644,NULL,NULL,'2012-06-11 12:48:53',NULL),(2645,NULL,NULL,'2012-06-11 12:49:37',NULL),(2646,NULL,NULL,'2012-06-11 12:49:39',NULL),(2647,NULL,NULL,'2012-06-11 12:49:41',NULL),(2648,NULL,NULL,'2012-06-11 12:49:43',NULL),(2649,NULL,NULL,'2012-06-11 12:49:44',NULL),(2650,NULL,NULL,'2012-06-11 12:50:16',NULL),(2651,NULL,NULL,'2012-06-11 12:50:38',NULL),(2652,NULL,NULL,'2012-06-11 12:50:39',NULL),(2653,NULL,NULL,'2012-06-11 12:50:55',NULL),(2654,NULL,NULL,'2012-06-11 12:50:56',NULL),(2655,NULL,NULL,'2012-06-11 12:50:58',NULL),(2656,NULL,NULL,'2012-06-11 12:51:40',NULL),(2657,NULL,NULL,'2012-06-11 12:52:21',NULL),(2658,NULL,NULL,'2012-06-11 12:53:02',NULL),(2659,NULL,NULL,'2012-06-11 12:54:54',NULL),(2660,NULL,NULL,'2012-06-11 12:55:04',NULL),(2661,NULL,NULL,'2012-06-11 12:55:15',NULL),(2662,NULL,NULL,'2012-06-11 12:55:26',NULL),(2663,NULL,NULL,'2012-06-11 12:55:35',NULL),(2664,1,NULL,'2012-06-11 12:55:47','2012-06-11 12:56:00'),(2665,NULL,NULL,'2012-06-11 12:55:48',NULL),(2666,NULL,NULL,'2012-06-11 12:56:30',NULL),(2667,NULL,NULL,'2012-06-11 12:57:11',NULL),(2668,NULL,NULL,'2012-06-11 12:57:47',NULL),(2669,NULL,NULL,'2012-06-11 12:57:53',NULL),(2670,NULL,NULL,'2012-06-11 12:57:58',NULL),(2671,NULL,NULL,'2012-06-11 12:58:03',NULL),(2672,NULL,NULL,'2012-06-11 12:58:34',NULL),(2673,NULL,NULL,'2012-06-11 12:59:16',NULL),(2674,1,'192.168.110.40','2012-06-11 12:59:29','2012-06-11 12:59:50'),(2675,NULL,NULL,'2012-06-11 12:59:57',NULL),(2676,NULL,NULL,'2012-06-11 13:00:39',NULL),(2677,NULL,NULL,'2012-06-11 13:01:20',NULL),(2678,NULL,NULL,'2012-06-11 13:02:02',NULL),(2679,NULL,NULL,'2012-06-11 13:02:43',NULL),(2680,NULL,NULL,'2012-06-11 13:03:25',NULL),(2681,NULL,NULL,'2012-06-11 13:04:06',NULL),(2682,NULL,NULL,'2012-06-11 13:04:48',NULL),(2683,NULL,NULL,'2012-06-11 13:05:29',NULL),(2684,NULL,NULL,'2012-06-11 13:05:33',NULL),(2685,NULL,NULL,'2012-06-11 13:06:48',NULL),(2686,NULL,NULL,'2012-06-11 13:06:51',NULL),(2687,NULL,NULL,'2012-06-11 13:07:03',NULL),(2688,NULL,NULL,'2012-06-11 13:07:13',NULL),(2689,NULL,NULL,'2012-06-11 13:07:34',NULL),(2690,NULL,NULL,'2012-06-11 13:08:09',NULL),(2691,NULL,NULL,'2012-06-11 13:08:16',NULL),(2692,NULL,NULL,'2012-06-11 13:08:42',NULL),(2693,NULL,NULL,'2012-06-11 13:08:47',NULL),(2694,NULL,NULL,'2012-06-11 13:08:57',NULL),(2695,NULL,NULL,'2012-06-11 13:09:01',NULL),(2696,NULL,NULL,'2012-06-11 13:09:38',NULL),(2697,NULL,NULL,'2012-06-11 13:10:04',NULL),(2698,NULL,NULL,'2012-06-11 13:10:08',NULL),(2699,NULL,NULL,'2012-06-11 13:10:20',NULL),(2700,NULL,NULL,'2012-06-11 13:11:01',NULL),(2701,1,'192.168.110.40','2012-06-11 13:11:07','2012-06-11 13:11:15'),(2702,NULL,NULL,'2012-06-11 13:11:43',NULL),(2703,NULL,NULL,'2012-06-11 13:12:24',NULL),(2704,NULL,NULL,'2012-06-11 13:12:37',NULL),(2705,NULL,NULL,'2012-06-11 13:13:06',NULL),(2706,NULL,'192.168.110.40','2012-06-11 13:13:16',NULL),(2707,NULL,NULL,'2012-06-11 13:13:47',NULL),(2708,NULL,NULL,'2012-06-11 13:15:06',NULL),(2709,NULL,NULL,'2012-06-11 13:15:12',NULL),(2710,1,'192.168.110.40','2012-06-11 13:15:48','2012-06-11 13:15:53'),(2711,NULL,NULL,'2012-06-11 13:15:52',NULL),(2712,NULL,NULL,'2012-06-11 13:16:14',NULL),(2713,NULL,NULL,'2012-06-11 13:16:33',NULL),(2714,NULL,NULL,'2012-06-11 13:16:42',NULL),(2715,NULL,NULL,'2012-06-11 13:17:15',NULL),(2716,NULL,NULL,'2012-06-11 13:17:56',NULL),(2717,NULL,NULL,'2012-06-11 13:18:38',NULL),(2718,NULL,NULL,'2012-06-11 13:19:19',NULL),(2719,NULL,NULL,'2012-06-11 13:20:01',NULL),(2720,NULL,NULL,'2012-06-11 13:20:20',NULL),(2721,NULL,NULL,'2012-06-11 13:20:42',NULL),(2722,NULL,NULL,'2012-06-11 13:21:24',NULL),(2723,NULL,NULL,'2012-06-11 13:22:05',NULL),(2724,NULL,NULL,'2012-06-11 13:22:26',NULL),(2725,NULL,NULL,'2012-06-11 13:22:28',NULL),(2726,NULL,NULL,'2012-06-11 13:22:47',NULL),(2727,NULL,NULL,'2012-06-11 13:23:28',NULL),(2728,NULL,NULL,'2012-06-11 13:24:10',NULL),(2729,NULL,NULL,'2012-06-11 13:24:51',NULL),(2730,NULL,NULL,'2012-06-11 13:25:30',NULL),(2731,NULL,NULL,'2012-06-11 13:26:11',NULL),(2732,NULL,NULL,'2012-06-11 13:26:52',NULL),(2733,NULL,NULL,'2012-06-11 13:27:34',NULL),(2734,NULL,NULL,'2012-06-11 13:28:15',NULL),(2735,NULL,NULL,'2012-06-11 13:28:57',NULL),(2736,NULL,NULL,'2012-06-11 13:29:17',NULL),(2737,NULL,NULL,'2012-06-11 13:29:38',NULL),(2738,NULL,NULL,'2012-06-11 13:30:20',NULL),(2739,NULL,NULL,'2012-06-11 13:31:02',NULL),(2740,NULL,NULL,'2012-06-11 13:31:43',NULL),(2741,NULL,NULL,'2012-06-11 13:32:24',NULL),(2742,NULL,NULL,'2012-06-11 13:33:06',NULL),(2743,NULL,NULL,'2012-06-11 13:34:06',NULL),(2744,NULL,NULL,'2012-06-11 13:34:29',NULL),(2745,NULL,NULL,'2012-06-11 13:34:38',NULL),(2746,NULL,NULL,'2012-06-11 13:34:40',NULL),(2747,NULL,NULL,'2012-06-11 13:35:10',NULL),(2748,NULL,NULL,'2012-06-11 13:35:12',NULL),(2749,NULL,NULL,'2012-06-11 13:35:14',NULL),(2750,NULL,NULL,'2012-06-11 13:35:16',NULL),(2751,NULL,NULL,'2012-06-11 13:35:52',NULL),(2752,NULL,NULL,'2012-06-11 13:36:14',NULL),(2753,NULL,NULL,'2012-06-11 13:36:17',NULL),(2754,NULL,NULL,'2012-06-11 13:36:34',NULL),(2755,NULL,NULL,'2012-06-11 13:37:14',NULL),(2756,NULL,NULL,'2012-06-11 13:37:16',NULL),(2757,NULL,NULL,'2012-06-11 13:37:57',NULL),(2758,NULL,NULL,'2012-06-11 13:38:38',NULL),(2759,NULL,NULL,'2012-06-11 13:39:20',NULL),(2760,NULL,NULL,'2012-06-11 13:40:01',NULL),(2761,NULL,NULL,'2012-06-11 13:40:43',NULL),(2762,NULL,NULL,'2012-06-11 13:41:02',NULL),(2763,NULL,NULL,'2012-06-11 13:41:04',NULL),(2764,NULL,NULL,'2012-06-11 13:41:24',NULL),(2765,NULL,NULL,'2012-06-11 13:42:06',NULL),(2766,NULL,NULL,'2012-06-11 13:42:15',NULL),(2767,NULL,NULL,'2012-06-11 13:42:18',NULL),(2768,NULL,NULL,'2012-06-11 13:42:23',NULL),(2769,NULL,NULL,'2012-06-11 13:42:47',NULL),(2770,NULL,NULL,'2012-06-11 13:43:29',NULL),(2771,NULL,NULL,'2012-06-11 13:44:10',NULL),(2772,NULL,NULL,'2012-06-11 13:44:26',NULL),(2773,NULL,NULL,'2012-06-11 13:44:52',NULL),(2774,NULL,NULL,'2012-06-11 13:45:33',NULL),(2775,NULL,NULL,'2012-06-11 13:46:15',NULL),(2776,NULL,NULL,'2012-06-11 13:46:28',NULL),(2777,NULL,NULL,'2012-06-11 13:46:34',NULL),(2778,NULL,NULL,'2012-06-11 13:46:37',NULL),(2779,NULL,NULL,'2012-06-11 13:46:56',NULL),(2780,NULL,NULL,'2012-06-11 13:47:38',NULL),(2781,NULL,NULL,'2012-06-11 13:48:19',NULL),(2782,NULL,NULL,'2012-06-11 13:49:01',NULL),(2783,NULL,NULL,'2012-06-11 13:49:31',NULL),(2784,NULL,NULL,'2012-06-11 13:49:33',NULL),(2785,NULL,NULL,'2012-06-11 13:49:36',NULL),(2786,NULL,NULL,'2012-06-11 13:49:42',NULL),(2787,NULL,NULL,'2012-06-11 13:50:24',NULL),(2788,NULL,NULL,'2012-06-11 13:51:05',NULL),(2789,NULL,NULL,'2012-06-11 13:51:47',NULL),(2790,NULL,NULL,'2012-06-11 13:52:28',NULL),(2791,NULL,NULL,'2012-06-11 13:53:01',NULL),(2792,NULL,NULL,'2012-06-11 13:53:02',NULL),(2793,NULL,NULL,'2012-06-11 13:53:08',NULL),(2794,NULL,NULL,'2012-06-11 13:53:10',NULL),(2795,NULL,NULL,'2012-06-11 13:53:51',NULL),(2796,NULL,NULL,'2012-06-11 13:54:33',NULL),(2797,NULL,NULL,'2012-06-11 13:55:14',NULL),(2798,NULL,NULL,'2012-06-11 13:55:56',NULL),(2799,NULL,NULL,'2012-06-11 13:56:37',NULL),(2800,NULL,NULL,'2012-06-11 13:57:14',NULL),(2801,NULL,NULL,'2012-06-11 13:57:19',NULL),(2802,NULL,NULL,'2012-06-11 13:57:35',NULL),(2803,NULL,NULL,'2012-06-11 13:58:00',NULL),(2804,NULL,NULL,'2012-06-11 13:58:01',NULL),(2805,NULL,NULL,'2012-06-11 13:58:04',NULL),(2806,NULL,NULL,'2012-06-11 13:58:13',NULL),(2807,NULL,NULL,'2012-06-11 13:58:16',NULL),(2808,NULL,NULL,'2012-06-11 13:58:20',NULL),(2809,NULL,NULL,'2012-06-11 13:59:29',NULL),(2810,NULL,NULL,'2012-06-11 14:00:05',NULL),(2811,1,'192.168.110.40','2012-06-11 14:00:23','2012-06-11 14:00:29'),(2812,NULL,NULL,'2012-06-11 14:00:41',NULL),(2813,NULL,NULL,'2012-06-11 14:00:47',NULL),(2814,NULL,NULL,'2012-06-11 14:01:28',NULL),(2815,NULL,NULL,'2012-06-11 14:03:33',NULL),(2816,NULL,NULL,'2012-06-11 14:03:50',NULL),(2817,NULL,NULL,'2012-06-11 14:03:59',NULL),(2818,NULL,NULL,'2012-06-11 14:04:03',NULL),(2819,NULL,NULL,'2012-06-11 14:04:14',NULL),(2820,NULL,NULL,'2012-06-11 14:04:34',NULL),(2821,1,NULL,'2012-06-11 14:04:53','2012-06-11 14:04:58'),(2822,NULL,NULL,'2012-06-11 14:04:55',NULL),(2823,NULL,NULL,'2012-06-11 14:05:15',NULL),(2824,NULL,NULL,'2012-06-11 14:05:36',NULL),(2825,NULL,NULL,'2012-06-11 14:06:18',NULL),(2826,1,'192.168.110.40','2012-06-11 14:06:37','2012-06-11 14:08:01'),(2827,NULL,NULL,'2012-06-11 14:07:00',NULL),(2828,NULL,NULL,'2012-06-11 14:07:41',NULL),(2829,NULL,NULL,'2012-06-11 14:08:17',NULL),(2830,NULL,NULL,'2012-06-11 14:08:23',NULL),(2831,NULL,NULL,'2012-06-11 14:09:04',NULL),(2832,NULL,NULL,'2012-06-11 14:09:46',NULL),(2833,1,NULL,'2012-06-11 14:10:21','2012-06-11 14:10:36'),(2834,NULL,NULL,'2012-06-11 14:10:27',NULL),(2835,NULL,NULL,'2012-06-11 14:11:09',NULL),(2836,NULL,NULL,'2012-06-11 14:11:50',NULL),(2837,NULL,NULL,'2012-06-11 14:12:32',NULL),(2838,NULL,NULL,'2012-06-11 14:13:13',NULL),(2839,NULL,NULL,'2012-06-11 14:13:35',NULL),(2840,NULL,NULL,'2012-06-11 14:13:55',NULL),(2841,NULL,NULL,'2012-06-11 14:14:36',NULL),(2842,NULL,NULL,'2012-06-11 14:15:18',NULL),(2843,NULL,NULL,'2012-06-11 14:15:59',NULL),(2844,NULL,NULL,'2012-06-11 14:18:25',NULL),(2845,NULL,NULL,'2012-06-11 14:18:38',NULL),(2846,NULL,NULL,'2012-06-11 14:18:46',NULL),(2847,NULL,'192.168.110.40','2012-06-11 14:19:10',NULL),(2848,NULL,NULL,'2012-06-11 14:19:27',NULL),(2849,1,'192.168.110.40','2012-06-11 14:19:28','2012-06-11 14:19:44'),(2850,NULL,NULL,'2012-06-11 14:20:08',NULL),(2851,NULL,NULL,'2012-06-11 14:20:50',NULL),(2852,NULL,NULL,'2012-06-11 14:26:43',NULL),(2853,NULL,NULL,'2012-06-11 14:27:01',NULL),(2854,1,NULL,'2012-06-11 14:27:02','2012-06-11 14:27:20'),(2855,NULL,NULL,'2012-06-11 14:27:34',NULL),(2856,NULL,NULL,'2012-06-11 14:28:01',NULL),(2857,NULL,NULL,'2012-06-11 14:28:25',NULL),(2858,NULL,NULL,'2012-06-11 14:28:46',NULL),(2859,NULL,NULL,'2012-06-11 14:29:07',NULL),(2860,NULL,NULL,'2012-06-11 14:29:49',NULL),(2861,NULL,NULL,'2012-06-11 14:30:30',NULL),(2862,NULL,NULL,'2012-06-11 14:30:51',NULL),(2863,NULL,NULL,'2012-06-11 14:31:19',NULL),(2864,NULL,NULL,'2012-06-11 14:31:53',NULL),(2865,1,'192.168.110.40','2012-06-11 14:31:58','2012-06-11 14:31:58'),(2866,NULL,NULL,'2012-06-11 14:32:00',NULL),(2867,NULL,NULL,'2012-06-11 14:32:07',NULL),(2868,NULL,NULL,'2012-06-11 14:32:35',NULL),(2869,NULL,NULL,'2012-06-11 14:33:17',NULL),(2870,NULL,NULL,'2012-06-11 14:33:58',NULL),(2871,NULL,NULL,'2012-06-11 14:34:40',NULL),(2872,NULL,NULL,'2012-06-11 14:35:21',NULL),(2873,NULL,NULL,'2012-06-11 14:36:02',NULL),(2874,NULL,NULL,'2012-06-11 14:36:44',NULL),(2875,NULL,NULL,'2012-06-11 14:39:39',NULL),(2876,NULL,NULL,'2012-06-11 14:39:46',NULL),(2877,NULL,NULL,'2012-06-11 14:40:12',NULL),(2878,1,NULL,'2012-06-11 14:40:21','2012-06-11 14:40:26'),(2879,NULL,NULL,'2012-06-11 14:40:31',NULL),(2880,NULL,NULL,'2012-06-11 14:40:53',NULL),(2881,NULL,'192.168.110.40','2012-06-11 14:41:16',NULL),(2882,1,NULL,'2012-06-11 14:41:34','2012-06-11 14:41:51'),(2883,NULL,NULL,'2012-06-11 14:42:16',NULL),(2884,NULL,NULL,'2012-06-11 14:42:58',NULL),(2885,NULL,NULL,'2012-06-11 14:43:39',NULL),(2886,NULL,NULL,'2012-06-11 14:44:21',NULL),(2887,NULL,NULL,'2012-06-11 14:45:02',NULL),(2888,NULL,NULL,'2012-06-11 14:45:44',NULL),(2889,NULL,NULL,'2012-06-11 14:46:25',NULL),(2890,NULL,NULL,'2012-06-11 14:47:07',NULL),(2891,NULL,NULL,'2012-06-11 14:47:48',NULL),(2892,NULL,NULL,'2012-06-11 14:48:30',NULL),(2893,NULL,NULL,'2012-06-11 14:49:11',NULL),(2894,NULL,NULL,'2012-06-11 14:49:41',NULL),(2895,NULL,NULL,'2012-06-11 14:49:53',NULL),(2896,NULL,NULL,'2012-06-11 14:50:34',NULL),(2897,NULL,NULL,'2012-06-11 14:51:16',NULL),(2898,NULL,NULL,'2012-06-11 14:51:57',NULL),(2899,NULL,NULL,'2012-06-11 14:52:39',NULL),(2900,NULL,NULL,'2012-06-11 14:52:51',NULL),(2901,NULL,NULL,'2012-06-11 14:53:21',NULL),(2902,NULL,NULL,'2012-06-11 14:54:02',NULL),(2903,NULL,NULL,'2012-06-11 14:54:44',NULL),(2904,NULL,NULL,'2012-06-11 14:55:01',NULL),(2905,NULL,NULL,'2012-06-11 14:55:25',NULL),(2906,NULL,NULL,'2012-06-11 14:55:27',NULL),(2907,NULL,NULL,'2012-06-11 14:56:07',NULL),(2908,NULL,NULL,'2012-06-11 14:56:48',NULL),(2909,NULL,NULL,'2012-06-11 14:57:12',NULL),(2910,NULL,NULL,'2012-06-11 14:57:30',NULL),(2911,NULL,NULL,'2012-06-11 14:58:12',NULL),(2912,NULL,NULL,'2012-06-11 14:58:53',NULL),(2913,NULL,NULL,'2012-06-11 14:59:35',NULL),(2914,NULL,NULL,'2012-06-11 14:59:37',NULL),(2915,NULL,NULL,'2012-06-11 15:00:09',NULL),(2916,NULL,NULL,'2012-06-11 15:00:16',NULL),(2917,NULL,NULL,'2012-06-11 15:00:58',NULL),(2918,NULL,NULL,'2012-06-11 15:01:36',NULL),(2919,NULL,NULL,'2012-06-11 15:01:39',NULL),(2920,NULL,NULL,'2012-06-11 15:02:21',NULL),(2921,NULL,NULL,'2012-06-11 15:03:03',NULL),(2922,NULL,NULL,'2012-06-11 15:03:44',NULL),(2923,NULL,NULL,'2012-06-11 15:03:52',NULL),(2924,NULL,NULL,'2012-06-11 15:04:17',NULL),(2925,NULL,NULL,'2012-06-11 15:04:25',NULL),(2926,NULL,NULL,'2012-06-11 15:05:18',NULL),(2927,NULL,NULL,'2012-06-11 15:05:20',NULL),(2928,NULL,NULL,'2012-06-11 15:05:32',NULL),(2929,NULL,NULL,'2012-06-11 15:05:48',NULL),(2930,NULL,NULL,'2012-06-11 15:06:30',NULL),(2931,NULL,NULL,'2012-06-11 15:07:12',NULL),(2932,NULL,NULL,'2012-06-11 15:07:53',NULL),(2933,NULL,NULL,'2012-06-11 15:08:35',NULL),(2934,NULL,NULL,'2012-06-11 15:09:16',NULL),(2935,NULL,NULL,'2012-06-11 15:09:58',NULL),(2936,NULL,NULL,'2012-06-11 15:10:34',NULL),(2937,NULL,NULL,'2012-06-11 15:10:39',NULL),(2938,NULL,NULL,'2012-06-11 15:10:41',NULL),(2939,NULL,NULL,'2012-06-11 15:11:21',NULL),(2940,NULL,NULL,'2012-06-11 15:12:02',NULL),(2941,NULL,NULL,'2012-06-11 15:12:44',NULL),(2942,NULL,NULL,'2012-06-11 15:12:45',NULL),(2943,NULL,NULL,'2012-06-11 15:13:25',NULL),(2944,NULL,NULL,'2012-06-11 15:14:07',NULL),(2945,NULL,NULL,'2012-06-11 15:14:48',NULL),(2946,NULL,NULL,'2012-06-11 15:15:30',NULL),(2947,NULL,NULL,'2012-06-11 15:16:11',NULL),(2948,NULL,NULL,'2012-06-11 15:16:53',NULL),(2949,NULL,NULL,'2012-06-11 15:17:34',NULL),(2950,NULL,NULL,'2012-06-11 15:18:17',NULL),(2951,NULL,NULL,'2012-06-11 15:18:57',NULL),(2952,NULL,NULL,'2012-06-11 15:19:42',NULL),(2953,NULL,NULL,'2012-06-11 15:20:20',NULL),(2954,NULL,NULL,'2012-06-11 15:21:02',NULL),(2955,NULL,NULL,'2012-06-11 15:21:44',NULL),(2956,NULL,NULL,'2012-06-11 15:22:25',NULL),(2957,NULL,NULL,'2012-06-11 15:23:07',NULL),(2958,NULL,NULL,'2012-06-11 15:23:48',NULL),(2959,NULL,NULL,'2012-06-11 15:24:30',NULL),(2960,NULL,NULL,'2012-06-11 15:25:11',NULL),(2961,NULL,NULL,'2012-06-11 15:25:53',NULL),(2962,NULL,NULL,'2012-06-11 15:26:35',NULL),(2963,NULL,NULL,'2012-06-11 15:27:16',NULL),(2964,NULL,NULL,'2012-06-11 15:27:58',NULL),(2965,NULL,NULL,'2012-06-11 15:28:28',NULL),(2966,NULL,NULL,'2012-06-11 15:28:39',NULL),(2967,NULL,NULL,'2012-06-11 15:28:45',NULL),(2968,NULL,NULL,'2012-06-11 15:29:21',NULL),(2969,NULL,NULL,'2012-06-11 15:30:02',NULL),(2970,NULL,NULL,'2012-06-11 15:30:16',NULL),(2971,NULL,NULL,'2012-06-11 15:30:44',NULL),(2972,NULL,NULL,'2012-06-11 15:31:26',NULL),(2973,NULL,NULL,'2012-06-11 15:32:07',NULL),(2974,NULL,NULL,'2012-06-11 15:32:49',NULL),(2975,1,NULL,'2012-06-11 15:35:28','2012-06-11 15:35:51'),(2976,NULL,NULL,'2012-06-11 15:35:34',NULL),(2977,NULL,'192.168.110.40','2012-06-11 15:36:21',NULL),(2978,1,'192.168.110.40','2012-06-11 15:36:27','2012-06-11 15:36:37'),(2979,NULL,NULL,'2012-06-11 15:36:57',NULL),(2980,NULL,NULL,'2012-06-11 15:37:12',NULL),(2981,NULL,NULL,'2012-06-11 15:37:39',NULL),(2982,NULL,NULL,'2012-06-11 15:38:20',NULL),(2983,NULL,NULL,'2012-06-11 15:39:02',NULL),(2984,NULL,NULL,'2012-06-11 15:39:43',NULL),(2985,NULL,NULL,'2012-06-11 15:40:25',NULL),(2986,NULL,NULL,'2012-06-11 15:41:06',NULL),(2987,NULL,NULL,'2012-06-11 15:42:29',NULL),(2988,NULL,NULL,'2012-06-11 15:43:11',NULL),(2989,NULL,NULL,'2012-06-11 15:43:52',NULL),(2990,NULL,NULL,'2012-06-11 15:44:34',NULL),(2991,NULL,NULL,'2012-06-11 15:45:17',NULL),(2992,NULL,NULL,'2012-06-11 15:45:57',NULL),(2993,NULL,NULL,'2012-06-11 15:46:39',NULL),(2994,NULL,NULL,'2012-06-11 15:47:20',NULL),(2995,NULL,NULL,'2012-06-11 15:48:02',NULL),(2996,NULL,NULL,'2012-06-11 15:48:43',NULL),(2997,NULL,NULL,'2012-06-11 15:49:25',NULL),(2998,NULL,NULL,'2012-06-11 15:50:06',NULL),(2999,NULL,NULL,'2012-06-11 15:50:48',NULL),(3000,NULL,NULL,'2012-06-11 15:51:29',NULL),(3001,NULL,NULL,'2012-06-11 15:51:36',NULL),(3002,NULL,NULL,'2012-06-11 15:51:39',NULL),(3003,NULL,NULL,'2012-06-11 15:52:11',NULL),(3004,NULL,NULL,'2012-06-11 15:52:30',NULL),(3005,NULL,NULL,'2012-06-11 15:52:32',NULL),(3006,NULL,NULL,'2012-06-11 15:52:50',NULL),(3007,NULL,NULL,'2012-06-11 15:52:52',NULL),(3008,NULL,NULL,'2012-06-11 15:53:34',NULL),(3009,NULL,NULL,'2012-06-11 15:54:16',NULL),(3010,NULL,NULL,'2012-06-11 15:54:57',NULL),(3011,NULL,NULL,'2012-06-11 15:55:39',NULL),(3012,NULL,NULL,'2012-06-11 15:56:20',NULL),(3013,NULL,NULL,'2012-06-11 15:57:02',NULL),(3014,NULL,NULL,'2012-06-11 15:57:43',NULL),(3015,NULL,NULL,'2012-06-11 15:58:25',NULL),(3016,NULL,NULL,'2012-06-11 15:59:06',NULL),(3017,NULL,NULL,'2012-06-11 15:59:48',NULL),(3018,NULL,NULL,'2012-06-11 16:00:29',NULL),(3019,NULL,NULL,'2012-06-11 16:01:11',NULL),(3020,NULL,NULL,'2012-06-11 16:01:21',NULL),(3021,NULL,NULL,'2012-06-11 16:01:52',NULL),(3022,NULL,NULL,'2012-06-11 16:02:33',NULL),(3023,NULL,NULL,'2012-06-11 16:03:15',NULL),(3024,NULL,NULL,'2012-06-11 16:03:28',NULL),(3025,NULL,NULL,'2012-06-11 16:03:56',NULL),(3026,NULL,NULL,'2012-06-11 16:04:37',NULL),(3027,1,'192.168.110.40','2012-06-11 16:09:10','2012-06-11 16:09:11'),(3028,NULL,NULL,'2012-06-11 16:09:26',NULL),(3029,NULL,NULL,'2012-06-11 16:10:08',NULL),(3030,NULL,NULL,'2012-06-11 16:10:49',NULL),(3031,NULL,NULL,'2012-06-11 16:11:30',NULL),(3032,NULL,NULL,'2012-06-11 16:12:12',NULL),(3033,NULL,NULL,'2012-06-11 16:12:53',NULL),(3034,NULL,NULL,'2012-06-11 16:13:34',NULL),(3035,NULL,NULL,'2012-06-11 16:14:15',NULL),(3036,NULL,NULL,'2012-06-11 16:14:57',NULL),(3037,NULL,NULL,'2012-06-11 16:15:38',NULL),(3038,NULL,NULL,'2012-06-11 16:16:09',NULL),(3039,NULL,NULL,'2012-06-11 16:16:13',NULL),(3040,NULL,NULL,'2012-06-11 16:16:16',NULL),(3041,NULL,NULL,'2012-06-11 16:16:19',NULL),(3042,NULL,NULL,'2012-06-11 16:17:01',NULL),(3043,1,'192.168.110.40','2012-06-11 16:19:26','2012-06-11 16:21:14'),(3044,NULL,NULL,'2012-06-11 16:19:26',NULL),(3045,NULL,NULL,'2012-06-11 16:19:28',NULL),(3046,NULL,NULL,'2012-06-11 16:19:36',NULL),(3047,NULL,NULL,'2012-06-11 16:19:46',NULL),(3048,NULL,NULL,'2012-06-11 16:20:27',NULL),(3049,NULL,NULL,'2012-06-11 16:21:08',NULL),(3050,NULL,NULL,'2012-06-11 16:21:50',NULL),(3051,1,NULL,'2012-06-11 16:26:30','2012-06-11 16:26:41'),(3052,1,NULL,'2012-06-11 16:30:24','2012-06-11 16:31:28'),(3053,1,NULL,'2012-06-11 16:41:05','2012-06-11 16:41:59'),(3054,1,NULL,'2012-06-11 16:45:00','2012-06-11 16:45:13'),(3055,1,NULL,'2012-06-11 16:53:27','2012-06-11 16:53:43'),(3056,1,NULL,'2012-06-12 08:57:12','2012-06-12 08:57:39'),(3057,NULL,NULL,'2012-06-12 09:00:29',NULL),(3058,1,NULL,'2012-06-12 09:02:57','2012-06-12 09:03:08'),(3059,NULL,'0:0:0:0:0:0:0:1','2012-06-12 09:03:40',NULL),(3060,1,NULL,'2012-06-12 09:18:14','2012-06-12 09:18:19'),(3061,NULL,'0:0:0:0:0:0:0:1','2012-06-12 09:20:34',NULL),(3062,1,NULL,'2012-06-12 11:25:44','2012-06-12 11:25:59'),(3063,NULL,'0:0:0:0:0:0:0:1','2012-06-12 11:29:08',NULL),(3064,1,NULL,'2012-06-12 11:33:32','2012-06-12 11:33:42'),(3065,1,'0:0:0:0:0:0:0:1','2012-06-12 12:12:55','2012-06-12 12:12:58'),(3066,1,'0:0:0:0:0:0:0:1','2012-06-12 12:16:41','2012-06-12 12:16:48'),(3067,1,NULL,'2012-06-12 12:20:45','2012-06-12 12:20:54'),(3068,1,'0:0:0:0:0:0:0:1','2012-06-12 12:28:45','2012-06-12 12:29:02'),(3069,1,'0:0:0:0:0:0:0:1','2012-06-12 12:35:01','2012-06-12 12:35:07'),(3070,1,NULL,'2012-06-12 12:37:57','2012-06-12 12:38:16'),(3071,1,NULL,'2012-06-12 12:56:07','2012-06-12 12:56:16'),(3072,1,'0:0:0:0:0:0:0:1','2012-06-12 13:00:18','2012-06-12 13:00:26'),(3073,1,NULL,'2012-06-12 13:46:38','2012-06-12 13:46:45'),(3074,1,'0:0:0:0:0:0:0:1','2012-06-12 14:11:00','2012-06-12 14:11:29'),(3075,1,'0:0:0:0:0:0:0:1','2012-06-12 14:12:16','2012-06-12 14:18:45'),(3076,NULL,'0:0:0:0:0:0:0:1','2012-06-12 14:29:21',NULL),(3077,1,NULL,'2012-06-12 14:35:58','2012-06-12 14:36:11'),(3078,1,'0:0:0:0:0:0:0:1','2012-06-12 15:04:10','2012-06-12 15:04:19'),(3079,1,NULL,'2012-06-12 15:26:14','2012-06-12 15:26:20'),(3080,NULL,'0:0:0:0:0:0:0:1','2012-06-12 15:37:41',NULL),(3081,1,'0:0:0:0:0:0:0:1','2012-06-12 15:39:03','2012-06-12 15:39:03'),(3082,1,'0:0:0:0:0:0:0:1','2012-06-12 15:39:28','2012-06-12 15:39:31'),(3083,1,'0:0:0:0:0:0:0:1','2012-06-12 15:53:46','2012-06-12 15:53:49'),(3084,1,'0:0:0:0:0:0:0:1','2012-06-12 15:54:00','2012-06-12 15:56:19'),(3085,1,'0:0:0:0:0:0:0:1','2012-06-12 16:05:37','2012-06-12 16:05:44'),(3086,1,'0:0:0:0:0:0:0:1','2012-06-12 16:13:39','2012-06-12 16:19:18'),(3087,1,'0:0:0:0:0:0:0:1','2012-06-12 16:52:17','2012-06-12 16:52:22'),(3088,NULL,'0:0:0:0:0:0:0:1','2012-06-12 16:53:24',NULL),(3089,1,NULL,'2012-06-12 16:54:16','2012-06-12 16:54:20'),(3090,NULL,'0:0:0:0:0:0:0:1','2012-06-12 16:56:51',NULL),(3091,NULL,NULL,'2012-06-13 09:02:18',NULL);
/*!40000 ALTER TABLE `sessionlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shippingcost`
--

DROP TABLE IF EXISTS `shippingcost`;
CREATE TABLE `shippingcost` (
  `Price` int(11) default NULL,
  `TaxRegionId` bigint(20) NOT NULL,
  `PriceBandId` bigint(20) NOT NULL,
  `QuantityRangeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`TaxRegionId`,`PriceBandId`,`QuantityRangeId`),
  KEY `IX_Relationship131` (`TaxRegionId`),
  KEY `IX_Relationship132` (`PriceBandId`),
  KEY `IX_Relationship133` (`QuantityRangeId`),
  CONSTRAINT `Relationship131` FOREIGN KEY (`TaxRegionId`) REFERENCES `taxregion` (`Id`),
  CONSTRAINT `Relationship132` FOREIGN KEY (`PriceBandId`) REFERENCES `priceband` (`Id`),
  CONSTRAINT `Relationship133` FOREIGN KEY (`QuantityRangeId`) REFERENCES `quantityrange` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shippingcost`
--

LOCK TABLES `shippingcost` WRITE;
/*!40000 ALTER TABLE `shippingcost` DISABLE KEYS */;
/*!40000 ALTER TABLE `shippingcost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slideshowtype`
--

DROP TABLE IF EXISTS `slideshowtype`;
CREATE TABLE `slideshowtype` (
  `Id` bigint(20) NOT NULL,
  `Type` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `slideshowtype`
--

LOCK TABLES `slideshowtype` WRITE;
/*!40000 ALTER TABLE `slideshowtype` DISABLE KEYS */;
INSERT INTO `slideshowtype` VALUES (1,'Standard'),(2,'Photo essay');
/*!40000 ALTER TABLE `slideshowtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sortarea`
--

DROP TABLE IF EXISTS `sortarea`;
CREATE TABLE `sortarea` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(200) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sortarea`
--

LOCK TABLES `sortarea` WRITE;
/*!40000 ALTER TABLE `sortarea` DISABLE KEYS */;
INSERT INTO `sortarea` VALUES (1,'Search'),(2,'Browse');
/*!40000 ALTER TABLE `sortarea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sortattribute`
--

DROP TABLE IF EXISTS `sortattribute`;
CREATE TABLE `sortattribute` (
  `AttributeId` bigint(20) NOT NULL,
  `SortAreaId` bigint(20) NOT NULL,
  `Sequence` int(11) default NULL,
  `Type` int(11) default NULL,
  `Reverse` tinyint(1) default '0',
  KEY `IX_Relationship126` (`AttributeId`),
  KEY `IX_Relationship125` (`SortAreaId`),
  CONSTRAINT `Relationship125` FOREIGN KEY (`SortAreaId`) REFERENCES `sortarea` (`Id`),
  CONSTRAINT `Relationship126` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sortattribute`
--

LOCK TABLES `sortattribute` WRITE;
/*!40000 ALTER TABLE `sortattribute` DISABLE KEYS */;
INSERT INTO `sortattribute` VALUES (3,2,1,3,0);
/*!40000 ALTER TABLE `sortattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storagedevice`
--

DROP TABLE IF EXISTS `storagedevice`;
CREATE TABLE `storagedevice` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) NOT NULL,
  `Path` varchar(255) NOT NULL,
  `IsRelative` tinyint(1) NOT NULL default '0',
  `StorageTypeId` bigint(20) NOT NULL default '0',
  `MaxSpaceInMb` bigint(20) default NULL,
  `UsedSpaceInBytes` bigint(20) NOT NULL default '0',
  `LastScanTime` datetime default NULL,
  `IsLocked` tinyint(1) NOT NULL default '0',
  `SequenceNumber` int(11) NOT NULL default '0',
  `FactoryClassName` varchar(255) default NULL,
  `UsedLocalSpaceInBytes` bigint(20) NOT NULL default '0',
  `AuthenticationName` varchar(255) default NULL,
  `AuthenticationKey` varchar(255) default NULL,
  `HttpBaseUrl` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `storagedevice`
--

LOCK TABLES `storagedevice` WRITE;
/*!40000 ALTER TABLE `storagedevice` DISABLE KEYS */;
INSERT INTO `storagedevice` VALUES (1,'System','files/system',1,1,NULL,141768,'2012-06-13 08:53:06',0,1,'com.bright.framework.storage.service.FileSystemStorageDeviceFactory',141768,NULL,NULL,NULL),(2,'Assets','files/assets',1,2,NULL,58043752,'2012-06-13 08:53:06',0,1,'com.bright.framework.storage.service.FileSystemStorageDeviceFactory',58043752,NULL,NULL,NULL),(3,'Thumbnails','files/thumbnails',1,3,NULL,3918208,'2012-06-13 08:53:06',0,1,'com.bright.framework.storage.service.FileSystemStorageDeviceFactory',3918208,NULL,NULL,NULL);
/*!40000 ALTER TABLE `storagedevice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
  `Id` bigint(20) NOT NULL auto_increment,
  `StartDate` date default NULL,
  `SubscriptionModelId` bigint(20) NOT NULL,
  `UserId` bigint(20) NOT NULL,
  `IsActive` tinyint(1) NOT NULL default '0',
  `PricePaid` int(11) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship120` (`UserId`),
  KEY `IX_Relationship119` (`SubscriptionModelId`),
  CONSTRAINT `Relationship119` FOREIGN KEY (`SubscriptionModelId`) REFERENCES `subscriptionmodel` (`Id`),
  CONSTRAINT `Relationship120` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptionmodel`
--

DROP TABLE IF EXISTS `subscriptionmodel`;
CREATE TABLE `subscriptionmodel` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Description` varchar(250) default NULL,
  `Price` int(11) default NULL,
  `NoOfDownloads` int(11) default NULL,
  `Duration` int(11) default NULL,
  `Inactive` tinyint(1) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `subscriptionmodel`
--

LOCK TABLES `subscriptionmodel` WRITE;
/*!40000 ALTER TABLE `subscriptionmodel` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscriptionmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptionmodelupgrade`
--

DROP TABLE IF EXISTS `subscriptionmodelupgrade`;
CREATE TABLE `subscriptionmodelupgrade` (
  `FromId` bigint(20) NOT NULL,
  `ToId` bigint(20) NOT NULL,
  `Price` int(11) default NULL,
  PRIMARY KEY  (`FromId`,`ToId`),
  KEY `IX_Relationship121` (`FromId`),
  KEY `IX_Relationship122` (`ToId`),
  CONSTRAINT `Relationship121` FOREIGN KEY (`FromId`) REFERENCES `subscriptionmodel` (`Id`),
  CONSTRAINT `Relationship122` FOREIGN KEY (`ToId`) REFERENCES `subscriptionmodel` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `subscriptionmodelupgrade`
--

LOCK TABLES `subscriptionmodelupgrade` WRITE;
/*!40000 ALTER TABLE `subscriptionmodelupgrade` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscriptionmodelupgrade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemsetting`
--

DROP TABLE IF EXISTS `systemsetting`;
CREATE TABLE `systemsetting` (
  `Id` varchar(200) NOT NULL,
  `Value` varchar(200) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `systemsetting`
--

LOCK TABLES `systemsetting` WRITE;
/*!40000 ALTER TABLE `systemsetting` DISABLE KEYS */;
INSERT INTO `systemsetting` VALUES ('AttValsUpgraded','true'),('CurrentVersion','3.1221.15'),('DefaultIsNotRealLanguage','false'),('DownloadId','1'),('ExternalFilterAttributeMapping',''),('ExternalFilterAttributeValueMapping',''),('ExternalFilterExcludedAssetTypes',''),('FeaturedImageIndex','3'),('lastRunCAVRules','13/06/2012'),('lastRunSERules','13/06/2012'),('LastSavedSearchAlert','1339534385466'),('PasswordsUpgraded','true'),('PrintDetailsDisAttributePopulated','true'),('PublishActionCriteriaMigrated','true'),('PurchaseId','1'),('SearchCriteriaMigrated','true'),('SerializedSearchesConverted','true'),('SlideshowCriteriaMigrated','true'),('StoredChangesWritten','true');
/*!40000 ALTER TABLE `systemsetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxregion`
--

DROP TABLE IF EXISTS `taxregion`;
CREATE TABLE `taxregion` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `taxregion`
--

LOCK TABLES `taxregion` WRITE;
/*!40000 ALTER TABLE `taxregion` DISABLE KEYS */;
/*!40000 ALTER TABLE `taxregion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxtype`
--

DROP TABLE IF EXISTS `taxtype`;
CREATE TABLE `taxtype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(200) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `taxtype`
--

LOCK TABLES `taxtype` WRITE;
/*!40000 ALTER TABLE `taxtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `taxtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxvalue`
--

DROP TABLE IF EXISTS `taxvalue`;
CREATE TABLE `taxvalue` (
  `TaxTypeId` bigint(20) NOT NULL,
  `TaxRegionId` bigint(20) NOT NULL,
  `Percent` float(9,3) default NULL,
  `ZeroIfTaxNumberGiven` tinyint(1) NOT NULL,
  PRIMARY KEY  (`TaxTypeId`,`TaxRegionId`),
  KEY `IX_Relationship113` (`TaxTypeId`),
  KEY `IX_Relationship115` (`TaxRegionId`),
  CONSTRAINT `Relationship113` FOREIGN KEY (`TaxTypeId`) REFERENCES `taxtype` (`Id`),
  CONSTRAINT `Relationship115` FOREIGN KEY (`TaxRegionId`) REFERENCES `taxregion` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `taxvalue`
--

LOCK TABLES `taxvalue` WRITE;
/*!40000 ALTER TABLE `taxvalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `taxvalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedagreement`
--

DROP TABLE IF EXISTS `translatedagreement`;
CREATE TABLE `translatedagreement` (
  `AgreementId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Title` varchar(255) default NULL,
  `Body` mediumtext,
  PRIMARY KEY  (`AgreementId`,`LanguageId`),
  KEY `IX_Relationship271` (`LanguageId`),
  KEY `IX_Relationship269` (`AgreementId`),
  CONSTRAINT `Relationship269` FOREIGN KEY (`AgreementId`) REFERENCES `agreement` (`Id`),
  CONSTRAINT `Relationship271` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedagreement`
--

LOCK TABLES `translatedagreement` WRITE;
/*!40000 ALTER TABLE `translatedagreement` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedagreement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedassetattributevalues`
--

DROP TABLE IF EXISTS `translatedassetattributevalues`;
CREATE TABLE `translatedassetattributevalues` (
  `AssetId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `title` mediumtext,
  `description` mediumtext,
  `keywords` mediumtext,
  `sensitivitynotes` mediumtext,
  PRIMARY KEY  (`AssetId`,`LanguageId`),
  KEY `IX_TAAV_Asset_FK` (`AssetId`),
  KEY `IX_TAAV_Language_FK` (`LanguageId`),
  CONSTRAINT `TAAV_Asset_FK` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`),
  CONSTRAINT `TAAV_Language_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedassetattributevalues`
--

LOCK TABLES `translatedassetattributevalues` WRITE;
/*!40000 ALTER TABLE `translatedassetattributevalues` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedassetattributevalues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedassetentity`
--

DROP TABLE IF EXISTS `translatedassetentity`;
CREATE TABLE `translatedassetentity` (
  `AssetEntityId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(200) default NULL,
  `TermForSiblings` varchar(200) default NULL,
  `TermForSibling` varchar(200) default NULL,
  PRIMARY KEY  (`AssetEntityId`,`LanguageId`),
  KEY `IX_Relationship251` (`LanguageId`),
  KEY `IX_Relationship2412` (`AssetEntityId`),
  CONSTRAINT `Relationship2412` FOREIGN KEY (`AssetEntityId`) REFERENCES `assetentity` (`Id`),
  CONSTRAINT `Relationship251` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedassetentity`
--

LOCK TABLES `translatedassetentity` WRITE;
/*!40000 ALTER TABLE `translatedassetentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedassetentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedassetentityattribute`
--

DROP TABLE IF EXISTS `translatedassetentityattribute`;
CREATE TABLE `translatedassetentityattribute` (
  `AssetEntityId` bigint(20) NOT NULL,
  `AttributeId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `ValueIfNotVisible` mediumtext,
  PRIMARY KEY  (`AssetEntityId`,`AttributeId`,`LanguageId`),
  KEY `TranslatedAEA_Language_FK` (`LanguageId`),
  CONSTRAINT `TranslatedAEA_AEA_FK` FOREIGN KEY (`AssetEntityId`, `AttributeId`) REFERENCES `assetentityattribute` (`AssetEntityId`, `AttributeId`),
  CONSTRAINT `TranslatedAEA_Language_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedassetentityattribute`
--

LOCK TABLES `translatedassetentityattribute` WRITE;
/*!40000 ALTER TABLE `translatedassetentityattribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedassetentityattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedattribute`
--

DROP TABLE IF EXISTS `translatedattribute`;
CREATE TABLE `translatedattribute` (
  `AttributeId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Label` varchar(255) default NULL,
  `DefaultValue` varchar(255) default NULL,
  `ValueIfNotVisible` varchar(255) default NULL,
  `HelpText` varchar(255) default NULL,
  `AltText` varchar(255) default NULL,
  `DisplayName` varchar(255) default NULL,
  `InputMask` varchar(250) default NULL,
  PRIMARY KEY  (`AttributeId`,`LanguageId`),
  KEY `IX_Relationship173` (`AttributeId`),
  KEY `IX_Relationship174` (`LanguageId`),
  CONSTRAINT `Relationship173` FOREIGN KEY (`AttributeId`) REFERENCES `attribute` (`Id`),
  CONSTRAINT `Relationship174` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedattribute`
--

LOCK TABLES `translatedattribute` WRITE;
/*!40000 ALTER TABLE `translatedattribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedattribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedaudiodownloadoption`
--

DROP TABLE IF EXISTS `translatedaudiodownloadoption`;
CREATE TABLE `translatedaudiodownloadoption` (
  `AudioDownloadOptionId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Label` varchar(255) default NULL,
  PRIMARY KEY  (`AudioDownloadOptionId`,`LanguageId`),
  KEY `TADO_LanguageId_fk` (`LanguageId`),
  CONSTRAINT `TADO_AudioDownloadOptionId_FK` FOREIGN KEY (`AudioDownloadOptionId`) REFERENCES `audiodownloadoption` (`Id`),
  CONSTRAINT `TADO_LanguageId_fk` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedaudiodownloadoption`
--

LOCK TABLES `translatedaudiodownloadoption` WRITE;
/*!40000 ALTER TABLE `translatedaudiodownloadoption` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedaudiodownloadoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedcategory`
--

DROP TABLE IF EXISTS `translatedcategory`;
CREATE TABLE `translatedcategory` (
  `CategoryId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` text,
  `Description` mediumtext,
  `Summary` varchar(255) default NULL,
  PRIMARY KEY  (`CategoryId`,`LanguageId`),
  KEY `IX_Relationship177` (`CategoryId`),
  KEY `IX_Relationship178` (`LanguageId`),
  CONSTRAINT `Relationship177` FOREIGN KEY (`CategoryId`) REFERENCES `cm_category` (`Id`),
  CONSTRAINT `Relationship178` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedcategory`
--

LOCK TABLES `translatedcategory` WRITE;
/*!40000 ALTER TABLE `translatedcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedcommercialoption`
--

DROP TABLE IF EXISTS `translatedcommercialoption`;
CREATE TABLE `translatedcommercialoption` (
  `LanguageId` bigint(20) NOT NULL,
  `CommercialOptionId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  `Description` mediumtext,
  `Terms` mediumtext,
  PRIMARY KEY  (`LanguageId`,`CommercialOptionId`),
  KEY `IX_Relationship202` (`CommercialOptionId`),
  KEY `IX_Relationship201` (`LanguageId`),
  CONSTRAINT `Relationship201` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `Relationship202` FOREIGN KEY (`CommercialOptionId`) REFERENCES `commercialoption` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedcommercialoption`
--

LOCK TABLES `translatedcommercialoption` WRITE;
/*!40000 ALTER TABLE `translatedcommercialoption` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedcommercialoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedcustomfield`
--

DROP TABLE IF EXISTS `translatedcustomfield`;
CREATE TABLE `translatedcustomfield` (
  `LanguageId` bigint(20) NOT NULL,
  `CustomFieldId` bigint(20) NOT NULL,
  `FieldName` varchar(100) default NULL,
  PRIMARY KEY  (`LanguageId`,`CustomFieldId`),
  KEY `IX_Relationship231` (`LanguageId`),
  KEY `IX_Relationship232` (`CustomFieldId`),
  CONSTRAINT `Relationship231` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `Relationship232` FOREIGN KEY (`CustomFieldId`) REFERENCES `customfield` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedcustomfield`
--

LOCK TABLES `translatedcustomfield` WRITE;
/*!40000 ALTER TABLE `translatedcustomfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedcustomfield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatederdescription`
--

DROP TABLE IF EXISTS `translatederdescription`;
CREATE TABLE `translatederdescription` (
  `AssetEntityId` bigint(20) NOT NULL,
  `RelationshipTypeId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `FromName` varchar(100) default NULL,
  `FromNamePlural` varchar(100) default NULL,
  `ToName` varchar(100) default NULL,
  `ToNamePlural` varchar(100) default NULL,
  PRIMARY KEY  (`AssetEntityId`,`RelationshipTypeId`,`LanguageId`),
  KEY `IX_Language_TERD_FK` (`LanguageId`),
  KEY `IX_ERD_TERD_FK` (`AssetEntityId`,`RelationshipTypeId`),
  CONSTRAINT `ERD_TERD_FK` FOREIGN KEY (`AssetEntityId`, `RelationshipTypeId`) REFERENCES `entityrelationshipdescription` (`AssetEntityId`, `RelationshipTypeId`),
  CONSTRAINT `Language_TERD_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatederdescription`
--

LOCK TABLES `translatederdescription` WRITE;
/*!40000 ALTER TABLE `translatederdescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatederdescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedfilter`
--

DROP TABLE IF EXISTS `translatedfilter`;
CREATE TABLE `translatedfilter` (
  `FilterId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(200) default NULL,
  PRIMARY KEY  (`FilterId`,`LanguageId`),
  KEY `IX_Relationship186` (`LanguageId`),
  KEY `IX_Relationship185` (`FilterId`),
  CONSTRAINT `Relationship185` FOREIGN KEY (`FilterId`) REFERENCES `filter` (`Id`),
  CONSTRAINT `Relationship186` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedfilter`
--

LOCK TABLES `translatedfilter` WRITE;
/*!40000 ALTER TABLE `translatedfilter` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedfilter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedlistattributevalue`
--

DROP TABLE IF EXISTS `translatedlistattributevalue`;
CREATE TABLE `translatedlistattributevalue` (
  `ListAttributeValueId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Value` varchar(255) default NULL,
  `AdditionalValue` mediumtext,
  PRIMARY KEY  (`ListAttributeValueId`,`LanguageId`),
  KEY `IX_TLAV_Language_FK` (`LanguageId`),
  KEY `IX_TLAV_LAV_FK` (`ListAttributeValueId`),
  CONSTRAINT `TLAV_Language_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `TLAV_LAV_FK` FOREIGN KEY (`ListAttributeValueId`) REFERENCES `listattributevalue` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedlistattributevalue`
--

LOCK TABLES `translatedlistattributevalue` WRITE;
/*!40000 ALTER TABLE `translatedlistattributevalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedlistattributevalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedmarketingemail`
--

DROP TABLE IF EXISTS `translatedmarketingemail`;
CREATE TABLE `translatedmarketingemail` (
  `MarketingEmailId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  `Introduction` mediumtext,
  PRIMARY KEY  (`MarketingEmailId`,`LanguageId`),
  KEY `IX_Relationship216` (`LanguageId`),
  KEY `IX_Relationship215` (`MarketingEmailId`),
  CONSTRAINT `Relationship215` FOREIGN KEY (`MarketingEmailId`) REFERENCES `marketingemail` (`Id`),
  CONSTRAINT `Relationship216` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedmarketingemail`
--

LOCK TABLES `translatedmarketingemail` WRITE;
/*!40000 ALTER TABLE `translatedmarketingemail` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedmarketingemail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedmarketinggroup`
--

DROP TABLE IF EXISTS `translatedmarketinggroup`;
CREATE TABLE `translatedmarketinggroup` (
  `MarketingGroupId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(60) default NULL,
  `Description` varchar(255) default NULL,
  PRIMARY KEY  (`MarketingGroupId`,`LanguageId`),
  KEY `IX_Relationship197` (`LanguageId`),
  KEY `IX_Relationship196` (`MarketingGroupId`),
  CONSTRAINT `Relationship196` FOREIGN KEY (`MarketingGroupId`) REFERENCES `marketinggroup` (`Id`),
  CONSTRAINT `Relationship197` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedmarketinggroup`
--

LOCK TABLES `translatedmarketinggroup` WRITE;
/*!40000 ALTER TABLE `translatedmarketinggroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedmarketinggroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatednewsitem`
--

DROP TABLE IF EXISTS `translatednewsitem`;
CREATE TABLE `translatednewsitem` (
  `LanguageId` bigint(20) NOT NULL,
  `NewsItemId` bigint(20) NOT NULL,
  `Title` varchar(255) default NULL,
  `Content` mediumtext,
  PRIMARY KEY  (`LanguageId`,`NewsItemId`),
  KEY `IX_Relationship277` (`LanguageId`),
  KEY `IX_Relationship2702` (`NewsItemId`),
  CONSTRAINT `Relationship2702` FOREIGN KEY (`NewsItemId`) REFERENCES `newsitem` (`Id`),
  CONSTRAINT `Relationship277` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatednewsitem`
--

LOCK TABLES `translatednewsitem` WRITE;
/*!40000 ALTER TABLE `translatednewsitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatednewsitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedorderstatus`
--

DROP TABLE IF EXISTS `translatedorderstatus`;
CREATE TABLE `translatedorderstatus` (
  `OrderStatusId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`OrderStatusId`,`LanguageId`),
  KEY `IX_Relationship208` (`OrderStatusId`),
  KEY `IX_Relationship212` (`LanguageId`),
  CONSTRAINT `Relationship208` FOREIGN KEY (`OrderStatusId`) REFERENCES `orderstatus` (`Id`),
  CONSTRAINT `Relationship212` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedorderstatus`
--

LOCK TABLES `translatedorderstatus` WRITE;
/*!40000 ALTER TABLE `translatedorderstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedorderstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedpriceband`
--

DROP TABLE IF EXISTS `translatedpriceband`;
CREATE TABLE `translatedpriceband` (
  `PriceBandId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  `Description` varchar(255) default NULL,
  PRIMARY KEY  (`PriceBandId`,`LanguageId`),
  KEY `IX_Relationship205` (`PriceBandId`),
  KEY `IX_Relationship209` (`LanguageId`),
  CONSTRAINT `Relationship205` FOREIGN KEY (`PriceBandId`) REFERENCES `priceband` (`Id`),
  CONSTRAINT `Relationship209` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedpriceband`
--

LOCK TABLES `translatedpriceband` WRITE;
/*!40000 ALTER TABLE `translatedpriceband` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedpriceband` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedpricebandtype`
--

DROP TABLE IF EXISTS `translatedpricebandtype`;
CREATE TABLE `translatedpricebandtype` (
  `LanguageId` bigint(20) NOT NULL,
  `PriceBandTypeId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`LanguageId`,`PriceBandTypeId`),
  KEY `IX_Relationship204` (`PriceBandTypeId`),
  KEY `IX_Relationship203` (`LanguageId`),
  CONSTRAINT `Relationship203` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `Relationship204` FOREIGN KEY (`PriceBandTypeId`) REFERENCES `pricebandtype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedpricebandtype`
--

LOCK TABLES `translatedpricebandtype` WRITE;
/*!40000 ALTER TABLE `translatedpricebandtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedpricebandtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedsubscriptionmodel`
--

DROP TABLE IF EXISTS `translatedsubscriptionmodel`;
CREATE TABLE `translatedsubscriptionmodel` (
  `SubscriptionModelId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Description` varchar(250) default NULL,
  PRIMARY KEY  (`SubscriptionModelId`,`LanguageId`),
  KEY `IX_Relationship207` (`SubscriptionModelId`),
  KEY `IX_Relationship211` (`LanguageId`),
  CONSTRAINT `Relationship207` FOREIGN KEY (`SubscriptionModelId`) REFERENCES `subscriptionmodel` (`Id`),
  CONSTRAINT `Relationship211` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedsubscriptionmodel`
--

LOCK TABLES `translatedsubscriptionmodel` WRITE;
/*!40000 ALTER TABLE `translatedsubscriptionmodel` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedsubscriptionmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedtaxregion`
--

DROP TABLE IF EXISTS `translatedtaxregion`;
CREATE TABLE `translatedtaxregion` (
  `TaxRegionId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Name` varchar(255) default NULL,
  PRIMARY KEY  (`TaxRegionId`,`LanguageId`),
  KEY `IX_Relationship206` (`TaxRegionId`),
  KEY `IX_Relationship210` (`LanguageId`),
  CONSTRAINT `Relationship206` FOREIGN KEY (`TaxRegionId`) REFERENCES `taxregion` (`Id`),
  CONSTRAINT `Relationship210` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedtaxregion`
--

LOCK TABLES `translatedtaxregion` WRITE;
/*!40000 ALTER TABLE `translatedtaxregion` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedtaxregion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedtaxtype`
--

DROP TABLE IF EXISTS `translatedtaxtype`;
CREATE TABLE `translatedtaxtype` (
  `LanguageId` bigint(20) NOT NULL,
  `TaxTypeId` bigint(20) NOT NULL,
  `Name` varchar(200) default NULL,
  PRIMARY KEY  (`LanguageId`,`TaxTypeId`),
  KEY `IX_Relationship218` (`TaxTypeId`),
  KEY `IX_Relationship217` (`LanguageId`),
  CONSTRAINT `Relationship217` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `Relationship218` FOREIGN KEY (`TaxTypeId`) REFERENCES `taxtype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedtaxtype`
--

LOCK TABLES `translatedtaxtype` WRITE;
/*!40000 ALTER TABLE `translatedtaxtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedtaxtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedusagetype`
--

DROP TABLE IF EXISTS `translatedusagetype`;
CREATE TABLE `translatedusagetype` (
  `UsageTypeId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Description` varchar(255) default NULL,
  `Message` text,
  PRIMARY KEY  (`UsageTypeId`,`LanguageId`),
  KEY `IX_Relationship181` (`UsageTypeId`),
  KEY `IX_Relationship182` (`LanguageId`),
  CONSTRAINT `Relationship181` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`),
  CONSTRAINT `Relationship182` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedusagetype`
--

LOCK TABLES `translatedusagetype` WRITE;
/*!40000 ALTER TABLE `translatedusagetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedusagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translatedusagetypeformat`
--

DROP TABLE IF EXISTS `translatedusagetypeformat`;
CREATE TABLE `translatedusagetypeformat` (
  `UsageTypeFormatId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Description` varchar(200) default NULL,
  PRIMARY KEY  (`UsageTypeFormatId`,`LanguageId`),
  KEY `IX_Relationship183` (`UsageTypeFormatId`),
  KEY `IX_Relationship184` (`LanguageId`),
  CONSTRAINT `Relationship183` FOREIGN KEY (`UsageTypeFormatId`) REFERENCES `usagetypeformat` (`Id`),
  CONSTRAINT `Relationship184` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `translatedusagetypeformat`
--

LOCK TABLES `translatedusagetypeformat` WRITE;
/*!40000 ALTER TABLE `translatedusagetypeformat` DISABLE KEYS */;
/*!40000 ALTER TABLE `translatedusagetypeformat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usagetype`
--

DROP TABLE IF EXISTS `usagetype`;
CREATE TABLE `usagetype` (
  `Id` bigint(20) NOT NULL auto_increment,
  `AssetTypeId` bigint(20) default NULL,
  `Description` varchar(255) default NULL,
  `SequenceNumber` int(11) NOT NULL,
  `IsEditable` tinyint(1) NOT NULL default '1',
  `ParentId` bigint(20) default NULL,
  `DownloadTabId` int(11) NOT NULL default '0',
  `CanEnterDetails` tinyint(1) default NULL,
  `DetailsMandatory` tinyint(1) default NULL,
  `Message` text,
  `DownloadOriginal` tinyint(1) default NULL,
  `IsConsideredHighRes` tinyint(1) NOT NULL default '0',
  `CanDownloadAsPPT` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship153` (`AssetTypeId`),
  KEY `IX_Relationship97` (`ParentId`),
  CONSTRAINT `Relationship153` FOREIGN KEY (`AssetTypeId`) REFERENCES `assettype` (`Id`),
  CONSTRAINT `Relationship97` FOREIGN KEY (`ParentId`) REFERENCES `usagetype` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usagetype`
--

LOCK TABLES `usagetype` WRITE;
/*!40000 ALTER TABLE `usagetype` DISABLE KEYS */;
INSERT INTO `usagetype` VALUES (1,NULL,'Other',99999,1,NULL,3,1,1,NULL,NULL,0,0),(2,NULL,'Word',1,1,NULL,3,NULL,NULL,NULL,NULL,0,0),(3,NULL,'PowerPoint',2,1,NULL,3,NULL,NULL,NULL,NULL,0,0),(4,NULL,'Website',3,1,NULL,3,NULL,NULL,NULL,NULL,0,0),(5,NULL,'High Res',4,1,NULL,3,NULL,NULL,NULL,1,0,0);
/*!40000 ALTER TABLE `usagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usagetypeformat`
--

DROP TABLE IF EXISTS `usagetypeformat`;
CREATE TABLE `usagetypeformat` (
  `Id` bigint(20) NOT NULL auto_increment,
  `FormatId` bigint(20) NOT NULL,
  `UsageTypeId` bigint(20) default NULL,
  `Description` varchar(200) default NULL,
  `ImageWidth` int(11) default NULL,
  `ImageHeight` int(11) default NULL,
  `ScaleUp` tinyint(1) default '0',
  `Density` int(11) default NULL,
  `JpegQuality` int(11) default NULL,
  `PreserveFormatList` varchar(255) default NULL,
  `ApplyStrip` tinyint(1) default NULL,
  `OmitIfLowerRes` tinyint(1) default '0',
  `CropToFit` tinyint(1) default '0',
  `ConvertToColorSpaceId` bigint(20) default NULL,
  `Watermark` tinyint(1) default '0',
  `AllowMasking` tinyint(1) NOT NULL default '1',
  `PresetMaskId` int(11) default NULL,
  `PresetMaskColourId` int(11) default NULL,
  `FillBackground` tinyint(1) default '0',
  `BackgroundColour` varchar(100) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship82` (`UsageTypeId`),
  KEY `IX_Relationship83` (`FormatId`),
  KEY `IX_Relationship286` (`ConvertToColorSpaceId`),
  KEY `IX_UTF_Mask_FK` (`PresetMaskId`),
  KEY `IX_UTF_PMColour_FK` (`PresetMaskColourId`),
  CONSTRAINT `Relationship286` FOREIGN KEY (`ConvertToColorSpaceId`) REFERENCES `colorspace` (`Id`),
  CONSTRAINT `Relationship82` FOREIGN KEY (`UsageTypeId`) REFERENCES `usagetype` (`Id`),
  CONSTRAINT `Relationship83` FOREIGN KEY (`FormatId`) REFERENCES `fileformat` (`Id`),
  CONSTRAINT `UTF_Mask_FK` FOREIGN KEY (`PresetMaskId`) REFERENCES `mask` (`Id`),
  CONSTRAINT `UTF_PMColour_FK` FOREIGN KEY (`PresetMaskColourId`) REFERENCES `namedcolour` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usagetypeformat`
--

LOCK TABLES `usagetypeformat` WRITE;
/*!40000 ALTER TABLE `usagetypeformat` DISABLE KEYS */;
INSERT INTO `usagetypeformat` VALUES (1,1,NULL,'Small',240,240,0,NULL,NULL,NULL,NULL,0,0,NULL,0,1,NULL,NULL,0,NULL),(2,1,NULL,'Medium',500,500,0,NULL,NULL,NULL,NULL,0,0,NULL,0,1,NULL,NULL,0,NULL),(3,1,NULL,'Large',1024,1024,0,NULL,NULL,NULL,NULL,0,0,NULL,0,1,NULL,NULL,0,NULL),(4,1,2,'Small',400,400,0,NULL,NULL,'gif',1,0,0,NULL,0,1,NULL,NULL,0,NULL),(5,1,2,'Large',800,800,0,NULL,NULL,'gif',1,0,0,NULL,0,1,NULL,NULL,0,NULL),(6,1,3,'Small',200,200,0,NULL,NULL,'gif',1,0,0,NULL,0,1,NULL,NULL,0,NULL),(7,1,3,'Large',600,600,0,NULL,NULL,'gif',1,0,0,NULL,0,1,NULL,NULL,0,NULL),(8,1,4,'Small',200,200,0,NULL,NULL,'gif',1,0,0,1,0,1,NULL,NULL,0,NULL),(9,1,4,'Medium',400,400,0,NULL,NULL,'gif',1,0,0,1,0,1,NULL,NULL,0,NULL),(10,1,4,'Large',800,800,0,NULL,NULL,'gif',1,0,0,1,0,1,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `usagetypeformat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userapprovesbatchrelease`
--

DROP TABLE IF EXISTS `userapprovesbatchrelease`;
CREATE TABLE `userapprovesbatchrelease` (
  `AssetBankUserId` bigint(20) NOT NULL,
  `BatchReleaseId` bigint(20) NOT NULL,
  `Approved` tinyint(1) default '0',
  PRIMARY KEY  (`AssetBankUserId`,`BatchReleaseId`),
  KEY `IX_UABR_User_FK` (`AssetBankUserId`),
  KEY `UABR_BatchRel_FK` (`BatchReleaseId`),
  CONSTRAINT `UABR_BatchRel_FK` FOREIGN KEY (`BatchReleaseId`) REFERENCES `batchrelease` (`Id`),
  CONSTRAINT `UABR_User_FK` FOREIGN KEY (`AssetBankUserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userapprovesbatchrelease`
--

LOCK TABLES `userapprovesbatchrelease` WRITE;
/*!40000 ALTER TABLE `userapprovesbatchrelease` DISABLE KEYS */;
/*!40000 ALTER TABLE `userapprovesbatchrelease` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usereditlanguage`
--

DROP TABLE IF EXISTS `usereditlanguage`;
CREATE TABLE `usereditlanguage` (
  `UserId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  PRIMARY KEY  (`UserId`,`LanguageId`),
  KEY `IX_User_UserEditLang_FK` (`UserId`),
  KEY `IX_Lang_UserEditLang_FK` (`LanguageId`),
  CONSTRAINT `Lang_UserEditLang_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `User_UserEditLang_FK` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usereditlanguage`
--

LOCK TABLES `usereditlanguage` WRITE;
/*!40000 ALTER TABLE `usereditlanguage` DISABLE KEYS */;
/*!40000 ALTER TABLE `usereditlanguage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(60) NOT NULL,
  `Description` varchar(255) default NULL,
  `IsDefaultGroup` tinyint(1) NOT NULL default '0',
  `Mapping` text,
  `BrandId` bigint(20) default NULL,
  `IPMapping` text,
  `URLMapping` text,
  `DiscountPercentage` int(11) default NULL,
  `CanOnlyEditOwn` tinyint(1) NOT NULL default '0',
  `DailyDownloadLimit` int(11) NOT NULL default '0',
  `CanEmailAssets` tinyint(1) NOT NULL default '1',
  `CanSeeSourcePath` tinyint(1) NOT NULL default '0',
  `CanRepurposeAssets` tinyint(1) NOT NULL default '0',
  `MaxDownloadHeight` int(11) default NULL,
  `MaxDownloadWidth` int(11) default NULL,
  `CanInviteUsers` tinyint(1) NOT NULL default '0',
  `CanAddEmptyAssets` tinyint(1) NOT NULL default '0',
  `CanViewLargerSize` tinyint(1) NOT NULL default '1',
  `AutomaticBrandRegister` tinyint(1) NOT NULL default '0',
  `AdvancedViewing` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `IX_Relationship149` (`BrandId`),
  CONSTRAINT `Relationship149` FOREIGN KEY (`BrandId`) REFERENCES `brand` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usergroup`
--

LOCK TABLES `usergroup` WRITE;
/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
INSERT INTO `usergroup` VALUES (1,'*Logged-in Users','Determines the default permissions applied to logged in users',0,NULL,NULL,NULL,NULL,NULL,0,0,1,0,0,NULL,NULL,0,0,1,0,0),(2,'*Public','Determines the default permissions applied to users who have not logged in',1,NULL,NULL,NULL,NULL,NULL,0,0,0,0,0,NULL,NULL,0,0,1,0,0);
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useringroup`
--

DROP TABLE IF EXISTS `useringroup`;
CREATE TABLE `useringroup` (
  `UserId` bigint(20) NOT NULL,
  `UserGroupId` bigint(20) NOT NULL,
  PRIMARY KEY  (`UserId`,`UserGroupId`),
  KEY `IX_Relationship47` (`UserId`),
  KEY `IX_Relationship48` (`UserGroupId`),
  CONSTRAINT `Relationship47` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `Relationship48` FOREIGN KEY (`UserGroupId`) REFERENCES `usergroup` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `useringroup`
--

LOCK TABLES `useringroup` WRITE;
/*!40000 ALTER TABLE `useringroup` DISABLE KEYS */;
INSERT INTO `useringroup` VALUES (1,1);
/*!40000 ALTER TABLE `useringroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinmarketinggroup`
--

DROP TABLE IF EXISTS `userinmarketinggroup`;
CREATE TABLE `userinmarketinggroup` (
  `MarketingGroupId` bigint(20) NOT NULL,
  `UserId` bigint(20) NOT NULL,
  PRIMARY KEY  (`MarketingGroupId`,`UserId`),
  KEY `IX_Relationship172` (`UserId`),
  KEY `IX_Relationship1662` (`MarketingGroupId`),
  CONSTRAINT `Relationship1662` FOREIGN KEY (`MarketingGroupId`) REFERENCES `marketinggroup` (`Id`),
  CONSTRAINT `Relationship172` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userinmarketinggroup`
--

LOCK TABLES `userinmarketinggroup` WRITE;
/*!40000 ALTER TABLE `userinmarketinggroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinmarketinggroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersecurity`
--

DROP TABLE IF EXISTS `usersecurity`;
CREATE TABLE `usersecurity` (
  `AssetBankUserId` bigint(20) NOT NULL,
  `FirstQuestionId` bigint(20) default NULL,
  `FirstQuestionAnswer` varchar(255) default NULL,
  `SecondQuestionId` bigint(20) default NULL,
  `SecondQuestionAnswer` varchar(255) default NULL,
  `CookieCreationDate` datetime default NULL,
  `IsLocked` tinyint(1) NOT NULL default '0',
  `LockDate` datetime default NULL,
  `FirstAttemptDate` datetime default NULL,
  `AttemptCount` int(11) NOT NULL default '0',
  `ResetPasswordAuthId` varchar(255) NOT NULL default '0',
  PRIMARY KEY  (`AssetBankUserId`),
  KEY `UserSecurity_Question_1_FK` (`FirstQuestionId`),
  KEY `UserSecurity_Question_2_FK` (`SecondQuestionId`),
  CONSTRAINT `UserSecurity_ABUser_FK` FOREIGN KEY (`AssetBankUserId`) REFERENCES `assetbankuser` (`Id`),
  CONSTRAINT `UserSecurity_Question_1_FK` FOREIGN KEY (`FirstQuestionId`) REFERENCES `securityquestion` (`Id`),
  CONSTRAINT `UserSecurity_Question_2_FK` FOREIGN KEY (`SecondQuestionId`) REFERENCES `securityquestion` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usersecurity`
--

LOCK TABLES `usersecurity` WRITE;
/*!40000 ALTER TABLE `usersecurity` DISABLE KEYS */;
INSERT INTO `usersecurity` VALUES (1,NULL,NULL,NULL,NULL,NULL,0,NULL,'2012-06-12 11:25:52',0,'0');
/*!40000 ALTER TABLE `usersecurity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videoasset`
--

DROP TABLE IF EXISTS `videoasset`;
CREATE TABLE `videoasset` (
  `AssetId` bigint(20) NOT NULL,
  `PreviewClipLocation` varchar(255) default NULL,
  `Duration` bigint(20) default NULL,
  `Width` int(11) default NULL,
  `Height` int(11) default NULL,
  `PAR` float(9,3) default NULL,
  `PreviewClipBeingGenerated` tinyint(1) NOT NULL,
  `hasFLVMetaData` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`AssetId`),
  CONSTRAINT `Relationship123` FOREIGN KEY (`AssetId`) REFERENCES `asset` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videoasset`
--

LOCK TABLES `videoasset` WRITE;
/*!40000 ALTER TABLE `videoasset` DISABLE KEYS */;
INSERT INTO `videoasset` VALUES (15,'3:1/sh1-p.mpeg',60,1024,512,1.000,0,0);
/*!40000 ALTER TABLE `videoasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videocommandoption`
--

DROP TABLE IF EXISTS `videocommandoption`;
CREATE TABLE `videocommandoption` (
  `Id` bigint(20) NOT NULL,
  `Command` varchar(50) NOT NULL default 'ffmpeg',
  `Name` varchar(50) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Flag` varchar(255) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videocommandoption`
--

LOCK TABLES `videocommandoption` WRITE;
/*!40000 ALTER TABLE `videocommandoption` DISABLE KEYS */;
INSERT INTO `videocommandoption` VALUES (1,'ffmpeg','input filename','Set input file name.','-i'),(2,'ffmpeg','frame size','Set frame size. The format is wxh (ffserver default = 160x128).  There is no default for input streams, for output streams it is set by default to the size of the source stream.','-s'),(3,'ffmpeg','audio codec','Force audio codec to the given codec. Use the \"copy\" special value to specify that the raw codec data must be copied as is.','-acodec'),(4,'ffmpeg','audio bitrate','Set the audio bitrate in bit/s (default = 64k).','-ab'),(5,'ffmpeg','audio sample rate','Set the audio sampling frequency. there is no default for input streams, for output streams it is set by default to the frequency of the input stream.','-ar'),(6,'ffmpeg','video codec','Force video codec to the given codec. Use the \"copy\" special value to specify that the raw codec data must be copied as is.','-vcodec'),(7,'ffmpeg','video bitrate','Set the video bitrate in bit/s (default = 200 kb/s).','-b'),(8,'ffmpeg','video frame rate','Set frame rate (Hz value, fraction or abbreviation), (default = 25).','-r'),(9,'ffmpeg','video preset file','The options specified in a preset file are applied to the currently selected video codec.','-vpre'),(100,'ffmpeg','overwrite output','Overwrite output files.','-y'),(101,'VideoUtil','placeEndMoovAtomAtStart','Places the MoovAtom in MP4 format files at the start of the file enabling the file to be HTTP streamed.','MoovAtom');
/*!40000 ALTER TABLE `videocommandoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videokeyword`
--

DROP TABLE IF EXISTS `videokeyword`;
CREATE TABLE `videokeyword` (
  `Id` bigint(20) NOT NULL auto_increment,
  `VideoAssetId` bigint(20) NOT NULL,
  `StartTime` bigint(20) default '0',
  `Duration` bigint(20) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `VK_VideoAsset_FK` (`VideoAssetId`),
  CONSTRAINT `VK_VideoAsset_FK` FOREIGN KEY (`VideoAssetId`) REFERENCES `videoasset` (`AssetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videokeyword`
--

LOCK TABLES `videokeyword` WRITE;
/*!40000 ALTER TABLE `videokeyword` DISABLE KEYS */;
/*!40000 ALTER TABLE `videokeyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videokeywordtext`
--

DROP TABLE IF EXISTS `videokeywordtext`;
CREATE TABLE `videokeywordtext` (
  `VideoKeywordId` bigint(20) NOT NULL,
  `LanguageId` bigint(20) NOT NULL,
  `Keywords` text NOT NULL,
  PRIMARY KEY  (`VideoKeywordId`,`LanguageId`),
  KEY `VKText_Language_FK` (`LanguageId`),
  CONSTRAINT `VKText_Language_FK` FOREIGN KEY (`LanguageId`) REFERENCES `language` (`Id`),
  CONSTRAINT `VKText_VK_FK` FOREIGN KEY (`VideoKeywordId`) REFERENCES `videokeyword` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videokeywordtext`
--

LOCK TABLES `videokeywordtext` WRITE;
/*!40000 ALTER TABLE `videokeywordtext` DISABLE KEYS */;
/*!40000 ALTER TABLE `videokeywordtext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videouserframesize`
--

DROP TABLE IF EXISTS `videouserframesize`;
CREATE TABLE `videouserframesize` (
  `Id` bigint(20) NOT NULL auto_increment,
  `Name` varchar(20) NOT NULL,
  `Width` int(11) NOT NULL,
  `Height` int(11) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videouserframesize`
--

LOCK TABLES `videouserframesize` WRITE;
/*!40000 ALTER TABLE `videouserframesize` DISABLE KEYS */;
/*!40000 ALTER TABLE `videouserframesize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videouseroption`
--

DROP TABLE IF EXISTS `videouseroption`;
CREATE TABLE `videouseroption` (
  `Id` bigint(20) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `FileFormatId` bigint(20) default NULL,
  `DefaultFrameSize` bigint(20) default NULL,
  `isGlobal` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`Id`),
  KEY `VideoUserOpt_FileFormat_FK` (`FileFormatId`),
  KEY `VideoUserOpt_VUFrame_FK` (`DefaultFrameSize`),
  CONSTRAINT `VideoUserOpt_FileFormat_FK` FOREIGN KEY (`FileFormatId`) REFERENCES `fileformat` (`Id`),
  CONSTRAINT `VideoUserOpt_VUFrame_FK` FOREIGN KEY (`DefaultFrameSize`) REFERENCES `videouserframesize` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videouseroption`
--

LOCK TABLES `videouseroption` WRITE;
/*!40000 ALTER TABLE `videouseroption` DISABLE KEYS */;
/*!40000 ALTER TABLE `videouseroption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videouseroptioncommand`
--

DROP TABLE IF EXISTS `videouseroptioncommand`;
CREATE TABLE `videouseroptioncommand` (
  `VideoUserOptionId` bigint(20) NOT NULL,
  `VideoCommandOptionId` bigint(20) NOT NULL,
  `Value` varchar(50) default NULL,
  `IsReference` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`VideoUserOptionId`,`VideoCommandOptionId`),
  KEY `VUOptComm_VCommandOpt_FK` (`VideoCommandOptionId`),
  CONSTRAINT `VUOptComm_VCommandOpt_FK` FOREIGN KEY (`VideoCommandOptionId`) REFERENCES `videocommandoption` (`Id`),
  CONSTRAINT `VUOptComm_VUserOpt_FK` FOREIGN KEY (`VideoUserOptionId`) REFERENCES `videouseroption` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videouseroptioncommand`
--

LOCK TABLES `videouseroptioncommand` WRITE;
/*!40000 ALTER TABLE `videouseroptioncommand` DISABLE KEYS */;
/*!40000 ALTER TABLE `videouseroptioncommand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflowaudit`
--

DROP TABLE IF EXISTS `workflowaudit`;
CREATE TABLE `workflowaudit` (
  `Id` bigint(20) NOT NULL auto_increment,
  `UserId` bigint(20) default NULL,
  `EntityId` bigint(20) NOT NULL,
  `Message` mediumtext,
  `DateAdded` datetime default NULL,
  `Transition` varchar(200) default NULL,
  `WorkflowName` varchar(255) default NULL,
  `WorkflowInfoId` bigint(20) NOT NULL default '1',
  PRIMARY KEY  (`Id`,`EntityId`),
  KEY `IX_Relationship284` (`UserId`),
  CONSTRAINT `Relationship284` FOREIGN KEY (`UserId`) REFERENCES `assetbankuser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `workflowaudit`
--

LOCK TABLES `workflowaudit` WRITE;
/*!40000 ALTER TABLE `workflowaudit` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowaudit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflowinfo`
--

DROP TABLE IF EXISTS `workflowinfo`;
CREATE TABLE `workflowinfo` (
  `Id` bigint(20) NOT NULL auto_increment,
  `WorkflowName` varchar(200) default NULL,
  `VariationName` varchar(200) default NULL,
  `StateName` varchar(200) default NULL,
  `LastStateChangeDate` datetime default NULL,
  `WorkflowableEntityId` bigint(20) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `IX_WF_WFName` (`WorkflowName`),
  KEY `IX_WF_WFableEntityId` (`WorkflowableEntityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `workflowinfo`
--

LOCK TABLES `workflowinfo` WRITE;
/*!40000 ALTER TABLE `workflowinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-13  5:17:45
