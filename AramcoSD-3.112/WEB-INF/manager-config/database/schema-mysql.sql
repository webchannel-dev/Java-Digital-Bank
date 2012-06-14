/*
Created		16/08/2004
Modified		06/03/2008
Project		
Model		
Company		
Author		
Version		
Database		mySQL 4.0 
*/





drop table IF EXISTS TranslatedTaxType;
drop table IF EXISTS TranslatedMarketingEmail;
drop table IF EXISTS MarketingEmail;
drop table IF EXISTS AssetInMarketingEmail;
drop table IF EXISTS TranslatedTaxRegion;
drop table IF EXISTS TranslatedSubscriptionModel;
drop table IF EXISTS TranslatedPriceBand;
drop table IF EXISTS TranslatedOrderStatus;
drop table IF EXISTS TranslatedPriceBandType;
drop table IF EXISTS TranslatedCommercialOption;
drop table IF EXISTS AssetChangeLog;
drop table IF EXISTS EmailTemplateType;
drop table IF EXISTS TranslatedMarketingGroup;
drop table IF EXISTS SavedSearch;
drop table IF EXISTS CustomFieldValueMapping;
drop table IF EXISTS CustomFieldValue;
drop table IF EXISTS CustomFieldType;
drop table IF EXISTS CustomField;
drop table IF EXISTS CustomFieldUsageType;
drop table IF EXISTS TranslatedFilter;
drop table IF EXISTS TranslatedUsageTypeFormat;
drop table IF EXISTS TranslatedUsageType;
drop table IF EXISTS TranslatedCategory;
drop table IF EXISTS TranslatedAttributeValue;
drop table IF EXISTS TranslatedAttribute;
drop table IF EXISTS UserInMarketingGroup;
drop table IF EXISTS MarketingGroup;
drop table IF EXISTS FilterAttributeValue;
drop table IF EXISTS Filter;
drop table IF EXISTS ScheduledReportGroup;
drop table IF EXISTS ScheduledReport;
drop table IF EXISTS MappingDirection;
drop table IF EXISTS EmbeddedDataType;
drop table IF EXISTS EmbeddedDataValue;
drop table IF EXISTS EmbeddedDataMapping;
drop table IF EXISTS AssetBoxShare;
drop table IF EXISTS DisplayAttribute;
drop table IF EXISTS EmailTemplate;
drop table IF EXISTS Language;
drop table IF EXISTS GroupIsRole;
drop table IF EXISTS Role;
drop table IF EXISTS ListItemTextType;
drop table IF EXISTS AudioAsset;
drop table IF EXISTS FeaturedAssetInBrand;
drop table IF EXISTS Brand;
drop table IF EXISTS CommercialOptionPurchase;
drop table IF EXISTS OrderWorkflow;
drop table IF EXISTS AssetPurchasePriceBand;
drop table IF EXISTS OrderStatus;
drop table IF EXISTS CommercialOption;
drop table IF EXISTS AssetBoxPriceBand;
drop table IF EXISTS QuantityRange;
drop table IF EXISTS ShippingCost;
drop table IF EXISTS PriceBandUsage;
drop table IF EXISTS PriceBandType;
drop table IF EXISTS PriceBand;
drop table IF EXISTS FeaturedAsset;
drop table IF EXISTS SortArea;
drop table IF EXISTS SortAttribute;
drop table IF EXISTS VideoAsset;
drop table IF EXISTS SubscriptionModelUpgrade;
drop table IF EXISTS CountryInTaxRegion;
drop table IF EXISTS TaxRegion;
drop table IF EXISTS TaxValue;
drop table IF EXISTS TaxType;
drop table IF EXISTS Country;
drop table IF EXISTS Address;
drop table IF EXISTS GroupInSubscriptionModel;
drop table IF EXISTS Subscription;
drop table IF EXISTS SubscriptionModel;
drop table IF EXISTS PromotedAsset;
drop table IF EXISTS Division;
drop table IF EXISTS ListItem;
drop table IF EXISTS List;
drop table IF EXISTS GroupUsageExclusion;
drop table IF EXISTS RelatedAsset;
drop table IF EXISTS GroupEmailRule;
drop table IF EXISTS SendEmailDateRule;
drop table IF EXISTS ChangeAttributeValueDateRule;
drop table IF EXISTS UsageTypeFormat;
drop table IF EXISTS SearchReport;
drop table IF EXISTS AssetPurchaseLog;
drop table IF EXISTS ABOrder;
drop table IF EXISTS OrgUnitGroup;
drop table IF EXISTS OrgUnit;
drop table IF EXISTS ApprovalStatus;
drop table IF EXISTS AssetApproval;
drop table IF EXISTS AssetView;
drop table IF EXISTS SystemSetting;
drop table IF EXISTS FileFormat;
drop table IF EXISTS AssetDownloadType;
drop table IF EXISTS AssetUse;
drop table IF EXISTS UsageType;
drop table IF EXISTS UserInGroup;
drop table IF EXISTS AttributeVisibleToGroup;
drop table IF EXISTS GroupAttributeExclusion;
drop table IF EXISTS CategoryVisibleToGroup;
drop table IF EXISTS ImageAsset;
drop table IF EXISTS AssetType;
drop table IF EXISTS AttributeType;
drop table IF EXISTS UserGroup;
drop table IF EXISTS CM_ItemInCategory;
drop table IF EXISTS AssetBoxAsset;
drop table IF EXISTS CM_CategoryType;
drop table IF EXISTS Attribute;
drop table IF EXISTS AssetAttributeValue;
drop table IF EXISTS AttributeValue;
drop table IF EXISTS CM_Category;
drop table IF EXISTS AssetBankUser;
drop table IF EXISTS AssetBox;
drop table IF EXISTS Asset;




Create table Asset (
	Id Bigint NOT NULL AUTO_INCREMENT,
	CurrentVersionId Bigint ,
	AssetTypeId Bigint NOT NULL,
	AddedByUserId Bigint ,
	LastModifiedByUserId Bigint ,
	FileFormatId Bigint ,
	FileLocation Varchar(255) NOT NULL,
	OriginalFileLocation Varchar(255) ,
	FileSizeInBytes Bigint NOT NULL,
	SmallFileLocation Varchar(255) ,
	ThumbnailFileLocation Varchar(255) ,
	MediumFileLocation Varchar(255) ,
	DateCreated Date ,
	DateAdded Datetime ,
	DateLastModified Datetime ,
	ExpiryDate Date ,
	Author Varchar(255) ,
	Code Varchar(255) ,
	IsApproved Tinyint ,
	Price Int ,
	ImportedAssetId Varchar(255)  DEFAULT NULL,
	Synchronised Tinyint NOT NULL DEFAULT 0,
	IsPreviewRestricted Tinyint NOT NULL DEFAULT 0,
	BulkUploadTimestamp Datetime ,
	VersionNumber Int NOT NULL DEFAULT 1,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetBox (
	Id Bigint NOT NULL AUTO_INCREMENT,
	UserId Bigint NOT NULL,
	Name Varchar(255) ,
	SequenceNumber Int NOT NULL DEFAULT 1,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetBankUser (
	Id Bigint NOT NULL AUTO_INCREMENT,
	LastModifiedById Bigint ,
	Username Varchar(100) NOT NULL,
	Password Varchar(100) ,
	EmailAddress Varchar(255) ,
	Title Varchar(100) ,
	Forename Varchar(100) ,
	Surname Varchar(100) ,
	Organisation Varchar(255) ,
	Address Text ,
	Telephone Varchar(200) ,
	Fax Varchar(200) ,
	RegistrationInfo Mediumtext ,
	IsDeleted Tinyint NOT NULL DEFAULT 0,
	IsAdmin Tinyint NOT NULL DEFAULT 0,
	IsSuspended Tinyint ,
	DisplayName Varchar(255) ,
	CN Varchar(255) ,
	DistinguishedName Varchar(255) ,
	Mailbox Varchar(255) ,
	NotActiveDirectory Tinyint ,
	Hidden Tinyint  DEFAULT 0,
	NotApproved Tinyint  DEFAULT 0,
	RequiresUpdate Tinyint  DEFAULT 0,
	CanBeCategoryAdmin Tinyint  DEFAULT 0,
	ExpiryDate Date ,
	RegisterDate Date ,
	JobTitle Varchar(255) ,
	RequestedOrgUnitId Bigint ,
	DivisionId Bigint ,
	AddressId Bigint ,
	VATNumber Varchar(100) ,
	LDAPServerIndex Smallint ,
	ReceiveAlerts Tinyint ,
	CanLoginFromCms Tinyint ,
	DateChangedPassword Datetime ,
	Website Char(200) ,
	LanguageId Bigint NOT NULL DEFAULT 1,
	AdminNotes Mediumtext ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CM_Category (
	Id Bigint NOT NULL AUTO_INCREMENT,
	ParentId Bigint ,
	CategoryTypeId Bigint NOT NULL,
	Name Text NOT NULL,
	Description Mediumtext ,
	SequenceNumber Int ,
	Summary Varchar(255) ,
	IsBrowsable Tinyint NOT NULL,
	IsRestrictive Tinyint NOT NULL DEFAULT 0,
	BitPosition Int ,
	IsListboxCategory Tinyint  DEFAULT 0,
	CannotBeDeleted Tinyint NOT NULL,
	Synchronised Tinyint NOT NULL DEFAULT 0,
	SelectedOnLoad Tinyint  DEFAULT 0,
	AllowAdvancedOptions Tinyint  DEFAULT 1,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AttributeValue (
	Id Bigint NOT NULL AUTO_INCREMENT,
	AttributeId Bigint NOT NULL,
	Value Mediumtext ,
	DateValue Date ,
	DateTimeValue Datetime ,
	SequenceNumber Int ,
	IsEditable Tinyint NOT NULL DEFAULT 1,
	AssetId Bigint ,
	MapToFieldValue Varchar(200) ,
	FilterValue Tinyint  DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetAttributeValue (
	AttributeValueId Bigint NOT NULL,
	AttributeId Bigint NOT NULL,
	AssetId Bigint NOT NULL,
 Primary Key (AttributeValueId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Attribute (
	Id Bigint NOT NULL AUTO_INCREMENT,
	AttributeTypeId Bigint ,
	SequenceNumber Int NOT NULL,
	Label Varchar(255) NOT NULL,
	IsKeywordSearchable Tinyint NOT NULL DEFAULT 0,
	IsMandatory Tinyint NOT NULL DEFAULT 0,
	IsSearchField Tinyint NOT NULL DEFAULT 0,
	DefaultValue Varchar(255) ,
	ValueIfNotVisible Varchar(255) ,
	IsStatic Tinyint ,
	StaticFieldName Varchar(200) ,
	IsReadOnly Tinyint ,
	MaxDisplayLength Int  DEFAULT -1,
	TreeId Bigint ,
	HelpText Varchar(255) ,
	Highlight Tinyint ,
	NameAttribute Tinyint  DEFAULT 0,
	DefaultKeywordFilter Varchar(5) ,
	OnChangeScript Mediumtext ,
	IsHtml Tinyint ,
	IsRequiredForCompleteness Tinyint  DEFAULT 0,
	RequiresTranslation Tinyint  DEFAULT 0,
	DisplayName Varchar(255) ,
	BaseUrl Varchar(255) ,
	AltText Varchar(255) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CM_CategoryType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Description Varchar(50) ,
	IsAlphabeticOrder Tinyint NOT NULL,
	IsNameGloballyUnique Tinyint NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetBoxAsset (
	AssetBoxId Bigint NOT NULL,
	AssetId Bigint NOT NULL,
	AddedByUserId Bigint ,
	TimeAdded Datetime NOT NULL,
 Primary Key (AssetBoxId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CM_ItemInCategory (
	CategoryId Bigint NOT NULL,
	ItemId Bigint NOT NULL,
 Primary Key (CategoryId,ItemId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table UserGroup (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(60) NOT NULL,
	Description Varchar(255) ,
	IsDefaultGroup Tinyint NOT NULL DEFAULT 0,
	Mapping Text ,
	BrandId Bigint ,
	IPMapping Text ,
	URLMapping Text ,
	DiscountPercentage Int ,
	CanOnlyEditOwn Tinyint NOT NULL DEFAULT 0,
	DailyDownloadLimit Int NOT NULL DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AttributeType (
	Id Bigint NOT NULL,
	Name Varchar(60) NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetType (
	Id Bigint NOT NULL,
	Name Varchar(60) NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ImageAsset (
	AssetId Bigint NOT NULL,
	Height Int ,
	Width Int ,
	ColorSpace Int ,
	LargeFileLocation Varchar(255) ,
	FeaturedFileLocation Varchar(255) ,
	NumLayers Int  DEFAULT 1,
 Primary Key (AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CategoryVisibleToGroup (
	UserGroupId Bigint NOT NULL,
	CategoryId Bigint NOT NULL,
	CanDownloadAssets Tinyint NOT NULL DEFAULT 0,
	CanDownloadWithApproval Tinyint NOT NULL DEFAULT 0,
	CanUpdateAssets Tinyint NOT NULL DEFAULT 0,
	CanUpdateWithApproval Tinyint NOT NULL DEFAULT 0,
	CanDeleteAssets Tinyint NOT NULL DEFAULT 0,
	CanApproveAssets Tinyint NOT NULL DEFAULT 0,
	CantDownloadOriginal Tinyint NOT NULL DEFAULT 0,
	CantDownloadAdvanced Tinyint NOT NULL DEFAULT 0,
	CanEditSubcategories Tinyint NOT NULL DEFAULT 0,
 Primary Key (UserGroupId,CategoryId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table GroupAttributeExclusion (
	AttributeId Bigint NOT NULL,
	UserGroupId Bigint NOT NULL,
	Value Varchar(255) NOT NULL,
 Primary Key (AttributeId,UserGroupId,Value)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AttributeVisibleToGroup (
	UserGroupId Bigint NOT NULL,
	AttributeId Bigint NOT NULL,
	IsWriteable Tinyint NOT NULL DEFAULT 0,
 Primary Key (UserGroupId,AttributeId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table UserInGroup (
	UserId Bigint NOT NULL,
	UserGroupId Bigint NOT NULL,
 Primary Key (UserId,UserGroupId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table UsageType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	AssetTypeId Bigint ,
	Description Varchar(255) ,
	SequenceNumber Int NOT NULL,
	IsEditable Tinyint NOT NULL DEFAULT 1,
	ParentId Bigint ,
	DownloadTabId Int NOT NULL DEFAULT 0,
	CanEnterDetails Tinyint ,
	DetailsMandatory Tinyint ,
	Message Text ,
	DownloadOriginal Tinyint ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetUse (
	UserId Bigint ,
	UsageTypeId Bigint ,
	TimeOfDownload Datetime NOT NULL,
	OriginalDescription Varchar(255) ,
	OtherDescription Varchar(255) ,
	AssetDownloadTypeId Bigint NOT NULL,
	AssetId Bigint NOT NULL) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetDownloadType (
	Id Bigint NOT NULL,
	Description Varchar(60) NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table FileFormat (
	Id Bigint NOT NULL,
	AssetTypeId Bigint NOT NULL,
	FileExtension Varchar(20) NOT NULL,
	IsIndexable Tinyint NOT NULL DEFAULT 0,
	IsConvertable Tinyint NOT NULL DEFAULT 0,
	IsConversionTarget Tinyint NOT NULL DEFAULT 0,
	Name Varchar(255) ,
	Description Varchar(255) ,
	ThumbnailFileLocation Varchar(255) ,
	ContentType Varchar(100) ,
	ConverterClass Varchar(255) ,
	ToTextConverterClass Varchar(255) ,
	PreviewInclude Varchar(255) ,
	PreviewHeight Int ,
	PreviewWidth Int ,
	ConvertIndividualLayers Bool  DEFAULT 0,
	UNIQUE (FileExtension),
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SystemSetting (
	Id Varchar(20) NOT NULL,
	Value Varchar(200) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetView (
	AssetId Bigint NOT NULL,
	Time Datetime NOT NULL,
	UserId Bigint ) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetApproval (
	AssetId Bigint NOT NULL,
	UsageTypeId Bigint ,
	UserId Bigint NOT NULL,
	ApprovalStatusId Bigint NOT NULL,
	DateSubmitted Datetime ,
	DateApproved Datetime ,
	DateExpires Datetime ,
	AdminNotes Mediumtext ,
	UserNotes Mediumtext ,
 Primary Key (AssetId,UserId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ApprovalStatus (
	Id Bigint NOT NULL,
	Name Varchar(60) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table OrgUnit (
	Id Bigint NOT NULL AUTO_INCREMENT,
	RootOrgUnitCategoryId Bigint NOT NULL,
	AdminGroupId Bigint NOT NULL,
	StandardGroupId Bigint NOT NULL,
	UNIQUE (RootOrgUnitCategoryId),
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table OrgUnitGroup (
	UserGroupId Bigint NOT NULL,
	OrgUnitId Bigint NOT NULL,
 Primary Key (UserGroupId,OrgUnitId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ABOrder (
	Id Int NOT NULL AUTO_INCREMENT,
	UserId Bigint NOT NULL,
	DatePlaced Datetime ,
	SubTotal Int ,
	VATPercent Float(9,3) ,
	Total Int ,
	PSPTransId Varchar(255) ,
	PurchaseId Varchar(255) ,
	OrderStatusId Bigint NOT NULL,
	ShippingAddressId Bigint ,
	RecipientName Varchar(255) ,
	DateFulfilled Datetime ,
	UserNotes Mediumtext ,
	PrefersOfflinePayment Tinyint NOT NULL DEFAULT 0,
	ShippingCost Int ,
	BasketCost Int ,
	DiscountPercentage Int ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetPurchaseLog (
	ABOrderId Int NOT NULL,
	AssetId Bigint NOT NULL,
	Description Varchar(200) ,
	Price Int ,
	ShippingCost Int ,
 Primary Key (ABOrderId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SearchReport (
	SearchTerm Varchar(250) ,
	FullQuery Text ,
	Success Tinyint ,
	SearchDate Date ) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table UsageTypeFormat (
	Id Bigint NOT NULL AUTO_INCREMENT,
	FormatId Bigint NOT NULL,
	UsageTypeId Bigint ,
	Description Varchar(200) ,
	ImageWidth Int ,
	ImageHeight Int ,
	ScaleUp Tinyint  DEFAULT 0,
	Density Int ,
	JpegQuality Int ,
	PreserveFormatList Varchar(255) ,
	ApplyStrip Tinyint ,
	OmitIfLowerRes Tinyint  DEFAULT 0,
	CropToFit Tinyint  DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ChangeAttributeValueDateRule (
	Id Bigint NOT NULL AUTO_INCREMENT,
	AttributeId Bigint NOT NULL,
	RuleName Varchar(200) ,
	ChangeAttributeId Bigint NOT NULL,
	NewAttributeValue Varchar(255) ,
	Enabled Tinyint NOT NULL DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SendEmailDateRule (
	Id Bigint NOT NULL AUTO_INCREMENT,
	AttributeId Bigint NOT NULL,
	RuleName Varchar(200) ,
	DaysBefore Int  DEFAULT 0,
	EmailText Mediumtext ,
	Enabled Tinyint NOT NULL DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table GroupEmailRule (
	RuleId Bigint NOT NULL,
	UserGroupId Bigint NOT NULL,
 Primary Key (RuleId,UserGroupId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table RelatedAsset (
	ParentId Bigint NOT NULL,
	ChildId Bigint NOT NULL,
 Primary Key (ParentId,ChildId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table GroupUsageExclusion (
	UsageTypeId Bigint NOT NULL,
	UserGroupId Bigint NOT NULL,
 Primary Key (UsageTypeId,UserGroupId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table List (
	Id Bigint NOT NULL,
	Identifier Varchar(100) ,
	Name Varchar(255) ,
	Description Varchar(255) ,
	CannotAddNew Tinyint ,
	NoHTMLMarkup Tinyint  DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ListItem (
	Identifier Varchar(200) NOT NULL,
	LanguageId Bigint NOT NULL DEFAULT 1,
	ListId Bigint NOT NULL,
	Title Varchar(255) NOT NULL,
	Summary Mediumtext ,
	Body Mediumtext ,
	DateAdded Datetime ,
	CannotBeDeleted Tinyint ,
	ListItemTextTypeId Bigint NOT NULL DEFAULT 1,
 Primary Key (Identifier,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Division (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table PromotedAsset (
	AssetId Bigint NOT NULL,
	DatePromoted Timestamp(14) ,
 Primary Key (AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SubscriptionModel (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Description Varchar(250) ,
	Price Int ,
	NoOfDownloads Int ,
	Duration Int ,
	Inactive Tinyint NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Subscription (
	Id Bigint NOT NULL AUTO_INCREMENT,
	StartDate Date ,
	SubscriptionModelId Bigint NOT NULL,
	UserId Bigint NOT NULL,
	IsActive Tinyint NOT NULL DEFAULT 0,
	PricePaid Int ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table GroupInSubscriptionModel (
	SubscriptionModelId Bigint NOT NULL,
	UserGroupId Bigint NOT NULL,
 Primary Key (SubscriptionModelId,UserGroupId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Address (
	Id Bigint NOT NULL AUTO_INCREMENT,
	CountryId Bigint ,
	Address1 Varchar(200) ,
	Address2 Varchar(200) ,
	Town Varchar(100) ,
	County Varchar(100) ,
	Postcode Varchar(50) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Country (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
	SequenceNumber Int ,
	NativeName Varchar(255) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TaxType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(200) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TaxValue (
	TaxTypeId Bigint NOT NULL,
	TaxRegionId Bigint NOT NULL,
	Percent Float(9,3) ,
	ZeroIfTaxNumberGiven Tinyint NOT NULL,
 Primary Key (TaxTypeId,TaxRegionId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TaxRegion (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CountryInTaxRegion (
	TaxRegionId Bigint NOT NULL,
	CountryId Bigint NOT NULL,
 Primary Key (TaxRegionId,CountryId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SubscriptionModelUpgrade (
	FromId Bigint NOT NULL,
	ToId Bigint NOT NULL,
	Price Int ,
 Primary Key (FromId,ToId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table VideoAsset (
	AssetId Bigint NOT NULL,
	PreviewClipLocation Varchar(255) ,
	EmbeddedPreviewClipLocation Varchar(255) ,
 Primary Key (AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SortAttribute (
	AttributeId Bigint NOT NULL,
	SortAreaId Bigint NOT NULL,
	Sequence Int ,
	Type Int ,
	Reverse Tinyint  DEFAULT 0) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SortArea (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(200) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table FeaturedAsset (
	AssetId Bigint NOT NULL,
	DateFeatured Timestamp(14) ,
 Primary Key (AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table PriceBand (
	Id Bigint NOT NULL AUTO_INCREMENT,
	AssetTypeId Bigint NOT NULL,
	PriceBandTypeId Bigint NOT NULL,
	Name Varchar(255) ,
	Description Varchar(255) ,
	BasePrice Int ,
	UnitPrice Int ,
	DownloadOriginal Tinyint ,
	MaxQuantity Int NOT NULL DEFAULT 0,
	IsCommercial Tinyint ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table PriceBandType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table PriceBandUsage (
	UsageTypeId Bigint NOT NULL,
	PriceBandId Bigint NOT NULL,
 Primary Key (UsageTypeId,PriceBandId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ShippingCost (
	Price Int ,
	TaxRegionId Bigint NOT NULL,
	PriceBandId Bigint NOT NULL,
	QuantityRangeId Bigint NOT NULL,
 Primary Key (TaxRegionId,PriceBandId,QuantityRangeId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table QuantityRange (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(200) ,
	LowerLimit Int ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetBoxPriceBand (
	AssetBoxId Bigint NOT NULL,
	AssetId Bigint NOT NULL,
	PriceBandId Bigint NOT NULL,
	Quantity Int  DEFAULT 0,
 Primary Key (AssetBoxId,AssetId,PriceBandId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CommercialOption (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
	Description Mediumtext ,
	Price Int ,
	Terms Mediumtext ,
	IsDisabled Tinyint NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table OrderStatus (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
	OrderWorkflowId Bigint NOT NULL,
	ManualSelect Tinyint NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetPurchasePriceBand (
	PriceBandId Bigint NOT NULL,
	ABOrderId Int NOT NULL,
	AssetId Bigint NOT NULL,
	Quantity Int ,
	PriceBandTypeId Int ,
	PriceBandName Varchar(255) ,
	PriceBandTypeName Varchar(255) ,
	Cost Int ,
	ShippingCost Int ,
 Primary Key (PriceBandId,ABOrderId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table OrderWorkflow (
	Id Bigint NOT NULL,
	Name Varchar(20) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CommercialOptionPurchase (
	CommercialOptionId Bigint NOT NULL,
	PriceBandId Bigint NOT NULL,
	ABOrderId Int NOT NULL,
	AssetId Bigint NOT NULL,
	Price Int ,
	Description Mediumtext ,
 Primary Key (PriceBandId,ABOrderId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Brand (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(100) ,
	Code Varchar(50) NOT NULL,
	CssFile Varchar(100) ,
	LogoFile Varchar(100) ,
	LogoWidth Int ,
	LogoHeight Int ,
	LogoAlt Varchar(100) ,
	ContentListIdentifier Varchar(200) ,
	UNIQUE (Code),
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table FeaturedAssetInBrand (
	BrandId Bigint NOT NULL,
	AssetId Bigint NOT NULL,
 Primary Key (BrandId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AudioAsset (
	AssetId Bigint NOT NULL,
	PreviewClipLocation Varchar(255) ,
	EmbeddedPreviewClipLocation Varchar(255) ,
 Primary Key (AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ListItemTextType (
	Id Bigint NOT NULL,
	Name Varchar(255) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Role (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(200) NOT NULL,
	Description Varchar(200) ,
	Identifier Varchar(50) NOT NULL,
	UNIQUE (Identifier),
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table GroupIsRole (
	UserGroupId Bigint NOT NULL,
	RoleId Bigint NOT NULL,
 Primary Key (UserGroupId,RoleId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Language (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) NOT NULL,
	NativeName Varchar(255) ,
	Code Varchar(20) NOT NULL,
	IsSuspended Tinyint NOT NULL,
	IsDefault Tinyint NOT NULL DEFAULT 0,
	IsRightToLeft Bool NOT NULL DEFAULT 0,
	IconFilename Varchar(255) ,
	UsesLatinAlphabet Bool NOT NULL DEFAULT 1,
	UNIQUE (Name),
	UNIQUE (Code),
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table EmailTemplate (
	TextId Varchar(255) ,
	LanguageId Bigint NOT NULL,
	TypeId Bigint NOT NULL,
	Code Varchar(50) ,
	AddrFrom Varchar(255) ,
	AddrTo Varchar(255) ,
	AddrCc Varchar(255) ,
	AddrBcc Varchar(255) ,
	Subject Varchar(255) ,
	Body Mediumtext ,
	Hidden Tinyint  DEFAULT 0) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table DisplayAttribute (
	DisplayLength Int ,
	AttributeId Bigint NOT NULL,
	SequenceNumber Int ,
	ShowLabel Tinyint  DEFAULT 0,
	IsLink Tinyint  DEFAULT 0,
 Primary Key (AttributeId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetBoxShare (
	AssetBoxId Bigint NOT NULL,
	UserId Bigint NOT NULL,
	CanEdit Tinyint NOT NULL DEFAULT 0,
	SequenceNumber Int NOT NULL DEFAULT 0,
	Alias Varchar(255) ,
 Primary Key (AssetBoxId,UserId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table EmbeddedDataMapping (
	EmbeddedDataValueId Bigint NOT NULL,
	AttributeId Bigint NOT NULL,
	MappingDirectionId Bigint NOT NULL,
	Delimiter Varchar(50) ,
	Sequence Int ,
 Primary Key (EmbeddedDataValueId,AttributeId,MappingDirectionId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table EmbeddedDataValue (
	Id Bigint NOT NULL AUTO_INCREMENT,
	EmbeddedDataTypeId Bigint NOT NULL,
	Name Varchar(100) ,
	Expression Varchar(250) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table EmbeddedDataType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(100) NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table MappingDirection (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(100) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ScheduledReport (
	Id Bigint NOT NULL AUTO_INCREMENT,
	NextSendDate Datetime ,
	ReportPeriod Varchar(100) ,
	ReportType Varchar(100) ,
	ReportName Varchar(200) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table ScheduledReportGroup (
	ReportId Bigint NOT NULL,
	GroupId Bigint NOT NULL,
 Primary Key (ReportId,GroupId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table Filter (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(200) ,
	IsDefault Tinyint  DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table FilterAttributeValue (
	FilterId Bigint NOT NULL,
	AttributeValueId Bigint NOT NULL,
	ListAttribute Tinyint  DEFAULT 0,
 Primary Key (FilterId,AttributeValueId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table MarketingGroup (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(60) NOT NULL,
	Description Varchar(255) ,
	Purpose Varchar(255) ,
	IsHiddenInDefaultLanguage Bool NOT NULL DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table UserInMarketingGroup (
	MarketingGroupId Bigint NOT NULL,
	UserId Bigint NOT NULL,
 Primary Key (MarketingGroupId,UserId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedAttribute (
	AttributeId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Label Varchar(255) ,
	DefaultValue Varchar(255) ,
	ValueIfNotVisible Varchar(255) ,
	HelpText Varchar(255) ,
	AltText Varchar(255) ,
	DisplayName Varchar(255) ,
 Primary Key (AttributeId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedAttributeValue (
	LanguageId Bigint NOT NULL,
	AttributeValueId Bigint NOT NULL,
	Value Mediumtext ,
 Primary Key (LanguageId,AttributeValueId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedCategory (
	CategoryId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Text ,
	Description Mediumtext ,
	Summary Varchar(255) ,
 Primary Key (CategoryId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedUsageType (
	UsageTypeId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Description Varchar(255) ,
	Message Text ,
 Primary Key (UsageTypeId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedUsageTypeFormat (
	UsageTypeFormatId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Description Varchar(200) ,
 Primary Key (UsageTypeFormatId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedFilter (
	FilterId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Varchar(200) ,
 Primary Key (FilterId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CustomFieldUsageType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Description Varchar(100) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CustomField (
	FieldName Varchar(100) ,
	UsageTypeId Bigint NOT NULL,
	TypeId Bigint NOT NULL,
	Id Bigint NOT NULL AUTO_INCREMENT,
	Required Tinyint  DEFAULT 0,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CustomFieldType (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Description Varchar(50) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CustomFieldValue (
	Id Bigint NOT NULL AUTO_INCREMENT,
	CustomFieldId Bigint NOT NULL,
	Value Varchar(200) ,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table CustomFieldValueMapping (
	ListValue Bigint ,
	CustomFieldId Bigint NOT NULL,
	ItemId Bigint NOT NULL,
	TextValue Text ) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table SavedSearch (
	Name Varchar(255) NOT NULL,
	UserId Bigint NOT NULL,
	Keywords Varchar(255) ,
	FullQuery Text NOT NULL,
	HttpQueryString Text ,
 Primary Key (Name,UserId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedMarketingGroup (
	MarketingGroupId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Varchar(60) ,
	Description Varchar(255) ,
 Primary Key (MarketingGroupId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table EmailTemplateType (
	Id Bigint NOT NULL,
	Name Varchar(60) NOT NULL,
	UNIQUE (Name),
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetChangeLog (
	UserId Bigint NOT NULL,
	AssetId Bigint NOT NULL,
	ChangeTime Datetime NOT NULL,
	LogEntry Mediumtext ) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedCommercialOption (
	LanguageId Bigint NOT NULL,
	CommercialOptionId Bigint NOT NULL,
	Name Varchar(255) ,
	Description Mediumtext ,
	Terms Mediumtext ,
 Primary Key (LanguageId,CommercialOptionId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedPriceBandType (
	LanguageId Bigint NOT NULL,
	PriceBandTypeId Bigint NOT NULL,
	Name Varchar(255) ,
 Primary Key (LanguageId,PriceBandTypeId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedOrderStatus (
	OrderStatusId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Varchar(255) ,
 Primary Key (OrderStatusId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedPriceBand (
	PriceBandId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Varchar(255) ,
	Description Varchar(255) ,
 Primary Key (PriceBandId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedSubscriptionModel (
	SubscriptionModelId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Description Varchar(250) ,
 Primary Key (SubscriptionModelId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedTaxRegion (
	TaxRegionId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Varchar(255) ,
 Primary Key (TaxRegionId,LanguageId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table AssetInMarketingEmail (
	MarketingEmailId Bigint NOT NULL,
	AssetId Bigint NOT NULL,
 Primary Key (MarketingEmailId,AssetId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table MarketingEmail (
	Id Bigint NOT NULL AUTO_INCREMENT,
	Name Varchar(255) ,
	Introduction Mediumtext ,
	TimeSent Datetime NOT NULL,
 Primary Key (Id)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;

Create table TranslatedMarketingEmail (
	MarketingEmailId Bigint NOT NULL,
	LanguageId Bigint NOT NULL,
	Name Varchar(255) ,
	Introduction Mediumtext ,
 Primary Key (MarketingEmailId,LanguageId)) TYPE = InnoDB
ROW_FORMAT = Default;

Create table TranslatedTaxType (
	LanguageId Bigint NOT NULL,
	TaxTypeId Bigint NOT NULL,
	Name Varchar(200) ,
 Primary Key (LanguageId,TaxTypeId)) TYPE = InnoDB
CHARSET = utf8
ROW_FORMAT = Default;






Alter table CustomFieldValueMapping add unique UniqueCheck (ListValue,CustomFieldId,ItemId);



Create Index ImportedAssetId_Index  ON Asset (ImportedAssetId);



Alter table AssetAttributeValue add Index IX_Relationship1 (AssetId);
Alter table AssetAttributeValue add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetBoxAsset add Index IX_Relationship6 (AssetId);
Alter table AssetBoxAsset add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table CM_ItemInCategory add Index IX_Relationship8 (ItemId);
Alter table CM_ItemInCategory add Foreign Key (ItemId) references Asset (Id) on delete  restrict on update  restrict;
Alter table ImageAsset add Index IX_Relationship29 (AssetId);
Alter table ImageAsset add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AttributeValue add Index IX_Relationship39 (AssetId);
Alter table AttributeValue add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetUse add Index IX_Relationship49 (AssetId);
Alter table AssetUse add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetApproval add Index IX_Relationship66 (AssetId);
Alter table AssetApproval add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetView add Index IX_Relationship77 (AssetId);
Alter table AssetView add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table RelatedAsset add Index IX_Relationship95 (ParentId);
Alter table RelatedAsset add Foreign Key (ParentId) references Asset (Id) on delete  restrict on update  restrict;
Alter table RelatedAsset add Index IX_Relationship96 (ChildId);
Alter table RelatedAsset add Foreign Key (ChildId) references Asset (Id) on delete  restrict on update  restrict;
Alter table PromotedAsset add Index IX_Relationship104 (AssetId);
Alter table PromotedAsset add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table VideoAsset add Index IX_Relationship123 (AssetId);
Alter table VideoAsset add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table FeaturedAsset add Index IX_Relationship127 (AssetId);
Alter table FeaturedAsset add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetBoxPriceBand add Index IX_Relationship135 (AssetId);
Alter table AssetBoxPriceBand add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table FeaturedAssetInBrand add Index IX_Relationship150 (AssetId);
Alter table FeaturedAssetInBrand add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AudioAsset add Index IX_Relationship151 (AssetId);
Alter table AudioAsset add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetChangeLog add Index IX_Relationship200 (AssetId);
Alter table AssetChangeLog add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetInMarketingEmail add Index IX_Relationship214 (AssetId);
Alter table AssetInMarketingEmail add Foreign Key (AssetId) references Asset (Id) on delete  restrict on update  restrict;
Alter table Asset add Index IX_Relationship219 (CurrentVersionId);
Alter table Asset add Foreign Key (CurrentVersionId) references Asset (Id) on delete  restrict on update  restrict;
Alter table AssetBoxAsset add Index IX_Relationship5 (AssetBoxId);
Alter table AssetBoxAsset add Foreign Key (AssetBoxId) references AssetBox (Id) on delete  restrict on update  restrict;
Alter table AssetBoxPriceBand add Index IX_Relationship134 (AssetBoxId);
Alter table AssetBoxPriceBand add Foreign Key (AssetBoxId) references AssetBox (Id) on delete  restrict on update  restrict;
Alter table AssetBoxShare add Index IX_Relationship158 (AssetBoxId);
Alter table AssetBoxShare add Foreign Key (AssetBoxId) references AssetBox (Id) on delete  restrict on update  restrict;
Alter table Asset add Index IX_Relationship22 (AddedByUserId);
Alter table Asset add Foreign Key (AddedByUserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table UserInGroup add Index IX_Relationship47 (UserId);
Alter table UserInGroup add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetUse add Index IX_Relationship51 (UserId);
Alter table AssetUse add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetBox add Index IX_Relationship52 (UserId);
Alter table AssetBox add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetView add Index IX_Relationship63 (UserId);
Alter table AssetView add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetApproval add Index IX_Relationship67 (UserId);
Alter table AssetApproval add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table Asset add Index IX_Relationship69 (LastModifiedByUserId);
Alter table Asset add Foreign Key (LastModifiedByUserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table ABOrder add Index IX_Relationship78 (UserId);
Alter table ABOrder add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table Subscription add Index IX_Relationship120 (UserId);
Alter table Subscription add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetBoxShare add Index IX_Relationship159 (UserId);
Alter table AssetBoxShare add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetBoxAsset add Index IX_Relationship164 (AddedByUserId);
Alter table AssetBoxAsset add Foreign Key (AddedByUserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table UserInMarketingGroup add Index IX_Relationship172 (UserId);
Alter table UserInMarketingGroup add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetBankUser add Index IX_Relationship187 (LastModifiedById);
Alter table AssetBankUser add Foreign Key (LastModifiedById) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table SavedSearch add Index IX_Relationship195 (UserId);
Alter table SavedSearch add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table AssetChangeLog add Index IX_Relationship199 (UserId);
Alter table AssetChangeLog add Foreign Key (UserId) references AssetBankUser (Id) on delete  restrict on update  restrict;
Alter table CM_ItemInCategory add Index IX_Relationship9 (CategoryId);
Alter table CM_ItemInCategory add Foreign Key (CategoryId) references CM_Category (Id) on delete  restrict on update  restrict;
Alter table CM_Category add Index IX_Relationship10 (ParentId);
Alter table CM_Category add Foreign Key (ParentId) references CM_Category (Id) on delete  restrict on update  restrict;
Alter table CategoryVisibleToGroup add Index IX_Relationship35 (CategoryId);
Alter table CategoryVisibleToGroup add Foreign Key (CategoryId) references CM_Category (Id) on delete  restrict on update  restrict;
Alter table OrgUnit add Index IX_Relationship76 (RootOrgUnitCategoryId);
Alter table OrgUnit add Foreign Key (RootOrgUnitCategoryId) references CM_Category (Id) on delete  restrict on update  restrict;
Alter table TranslatedCategory add Index IX_Relationship177 (CategoryId);
Alter table TranslatedCategory add Foreign Key (CategoryId) references CM_Category (Id) on delete  restrict on update  restrict;
Alter table AssetAttributeValue add Index IX_Relationship2 (AttributeValueId);
Alter table AssetAttributeValue add Foreign Key (AttributeValueId) references AttributeValue (Id) on delete  restrict on update  restrict;
Alter table FilterAttributeValue add Index IX_Relationship169 (AttributeValueId);
Alter table FilterAttributeValue add Foreign Key (AttributeValueId) references AttributeValue (Id) on delete  restrict on update  restrict;
Alter table TranslatedAttributeValue add Index IX_Relationship176 (AttributeValueId);
Alter table TranslatedAttributeValue add Foreign Key (AttributeValueId) references AttributeValue (Id) on delete  restrict on update  restrict;
Alter table AttributeValue add Index IX_Relationship4 (AttributeId);
Alter table AttributeValue add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table GroupAttributeExclusion add Index IX_Relationship40 (AttributeId);
Alter table GroupAttributeExclusion add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table AttributeVisibleToGroup add Index IX_Relationship45 (AttributeId);
Alter table AttributeVisibleToGroup add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table AssetAttributeValue add Index IX_Relationship46 (AttributeId);
Alter table AssetAttributeValue add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table ChangeAttributeValueDateRule add Index IX_Relationship86 (ChangeAttributeId);
Alter table ChangeAttributeValueDateRule add Foreign Key (ChangeAttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table ChangeAttributeValueDateRule add Index IX_Relationship89 (AttributeId);
Alter table ChangeAttributeValueDateRule add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table SendEmailDateRule add Index IX_Relationship90 (AttributeId);
Alter table SendEmailDateRule add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table SortAttribute add Index IX_Relationship126 (AttributeId);
Alter table SortAttribute add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table DisplayAttribute add Index IX_Relationship157 (AttributeId);
Alter table DisplayAttribute add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table EmbeddedDataMapping add Index IX_Relationship163 (AttributeId);
Alter table EmbeddedDataMapping add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table TranslatedAttribute add Index IX_Relationship173 (AttributeId);
Alter table TranslatedAttribute add Foreign Key (AttributeId) references Attribute (Id) on delete  restrict on update  restrict;
Alter table CM_Category add Index IX_Relationship7 (CategoryTypeId);
Alter table CM_Category add Foreign Key (CategoryTypeId) references CM_CategoryType (Id) on delete  restrict on update  restrict;
Alter table CategoryVisibleToGroup add Index IX_Relationship34 (UserGroupId);
Alter table CategoryVisibleToGroup add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table GroupAttributeExclusion add Index IX_Relationship41 (UserGroupId);
Alter table GroupAttributeExclusion add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table AttributeVisibleToGroup add Index IX_Relationship44 (UserGroupId);
Alter table AttributeVisibleToGroup add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table UserInGroup add Index IX_Relationship48 (UserGroupId);
Alter table UserInGroup add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table OrgUnit add Index IX_Relationship73 (AdminGroupId);
Alter table OrgUnit add Foreign Key (AdminGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table OrgUnitGroup add Index IX_Relationship75 (UserGroupId);
Alter table OrgUnitGroup add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table OrgUnit add Index IX_Relationship81 (StandardGroupId);
Alter table OrgUnit add Foreign Key (StandardGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table GroupEmailRule add Index IX_Relationship88 (UserGroupId);
Alter table GroupEmailRule add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table GroupUsageExclusion add Index IX_Relationship100 (UserGroupId);
Alter table GroupUsageExclusion add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table GroupInSubscriptionModel add Index IX_Relationship108 (UserGroupId);
Alter table GroupInSubscriptionModel add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table GroupIsRole add Index IX_Relationship154 (UserGroupId);
Alter table GroupIsRole add Foreign Key (UserGroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table ScheduledReportGroup add Index IX_Relationship167 (GroupId);
Alter table ScheduledReportGroup add Foreign Key (GroupId) references UserGroup (Id) on delete  restrict on update  restrict;
Alter table Attribute add Index IX_Relationship19 (AttributeTypeId);
Alter table Attribute add Foreign Key (AttributeTypeId) references AttributeType (Id) on delete  restrict on update  restrict;
Alter table Asset add Index IX_Relationship27 (AssetTypeId);
Alter table Asset add Foreign Key (AssetTypeId) references AssetType (Id) on delete  restrict on update  restrict;
Alter table FileFormat add Index IX_Relationship58 (AssetTypeId);
Alter table FileFormat add Foreign Key (AssetTypeId) references AssetType (Id) on delete  restrict on update  restrict;
Alter table UsageType add Index IX_Relationship153 (AssetTypeId);
Alter table UsageType add Foreign Key (AssetTypeId) references AssetType (Id) on delete  restrict on update  restrict;
Alter table PriceBand add Index IX_Relationship223 (AssetTypeId);
Alter table PriceBand add Foreign Key (AssetTypeId) references AssetType (Id) on delete  restrict on update  restrict;
Alter table AssetUse add Index IX_Relationship50 (UsageTypeId);
Alter table AssetUse add Foreign Key (UsageTypeId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table AssetApproval add Index IX_Relationship70 (UsageTypeId);
Alter table AssetApproval add Foreign Key (UsageTypeId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table UsageTypeFormat add Index IX_Relationship82 (UsageTypeId);
Alter table UsageTypeFormat add Foreign Key (UsageTypeId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table UsageType add Index IX_Relationship97 (ParentId);
Alter table UsageType add Foreign Key (ParentId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table GroupUsageExclusion add Index IX_Relationship99 (UsageTypeId);
Alter table GroupUsageExclusion add Foreign Key (UsageTypeId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table PriceBandUsage add Index IX_Relationship129 (UsageTypeId);
Alter table PriceBandUsage add Foreign Key (UsageTypeId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table TranslatedUsageType add Index IX_Relationship181 (UsageTypeId);
Alter table TranslatedUsageType add Foreign Key (UsageTypeId) references UsageType (Id) on delete  restrict on update  restrict;
Alter table AssetUse add Index IX_Relationship57 (AssetDownloadTypeId);
Alter table AssetUse add Foreign Key (AssetDownloadTypeId) references AssetDownloadType (Id) on delete  restrict on update  restrict;
Alter table Asset add Index IX_Relationship60 (FileFormatId);
Alter table Asset add Foreign Key (FileFormatId) references FileFormat (Id) on delete  restrict on update  restrict;
Alter table UsageTypeFormat add Index IX_Relationship83 (FormatId);
Alter table UsageTypeFormat add Foreign Key (FormatId) references FileFormat (Id) on delete  restrict on update  restrict;
Alter table AssetApproval add Index IX_Relationship68 (ApprovalStatusId);
Alter table AssetApproval add Foreign Key (ApprovalStatusId) references ApprovalStatus (Id) on delete  restrict on update  restrict;
Alter table OrgUnitGroup add Index IX_Relationship74 (OrgUnitId);
Alter table OrgUnitGroup add Foreign Key (OrgUnitId) references OrgUnit (Id) on delete  restrict on update  restrict;
Alter table AssetBankUser add Index IX_Relationship101 (RequestedOrgUnitId);
Alter table AssetBankUser add Foreign Key (RequestedOrgUnitId) references OrgUnit (Id) on delete  restrict on update  restrict;
Alter table AssetPurchaseLog add Index IX_Relationship79 (ABOrderId);
Alter table AssetPurchaseLog add Foreign Key (ABOrderId) references ABOrder (Id) on delete  restrict on update  restrict;
Alter table AssetPurchasePriceBand add Index IX_Relationship140 (ABOrderId,AssetId);
Alter table AssetPurchasePriceBand add Foreign Key (ABOrderId,AssetId) references AssetPurchaseLog (ABOrderId,AssetId) on delete  restrict on update  restrict;
Alter table TranslatedUsageTypeFormat add Index IX_Relationship183 (UsageTypeFormatId);
Alter table TranslatedUsageTypeFormat add Foreign Key (UsageTypeFormatId) references UsageTypeFormat (Id) on delete  restrict on update  restrict;
Alter table GroupEmailRule add Index IX_Relationship87 (RuleId);
Alter table GroupEmailRule add Foreign Key (RuleId) references SendEmailDateRule (Id) on delete  restrict on update  restrict;
Alter table ListItem add Index IX_Relationship102 (ListId);
Alter table ListItem add Foreign Key (ListId) references List (Id) on delete  restrict on update  restrict;
Alter table AssetBankUser add Index IX_Relationship103 (DivisionId);
Alter table AssetBankUser add Foreign Key (DivisionId) references Division (Id) on delete  restrict on update  restrict;
Alter table GroupInSubscriptionModel add Index IX_Relationship107 (SubscriptionModelId);
Alter table GroupInSubscriptionModel add Foreign Key (SubscriptionModelId) references SubscriptionModel (Id) on delete  restrict on update  restrict;
Alter table Subscription add Index IX_Relationship119 (SubscriptionModelId);
Alter table Subscription add Foreign Key (SubscriptionModelId) references SubscriptionModel (Id) on delete  restrict on update  restrict;
Alter table SubscriptionModelUpgrade add Index IX_Relationship121 (FromId);
Alter table SubscriptionModelUpgrade add Foreign Key (FromId) references SubscriptionModel (Id) on delete  restrict on update  restrict;
Alter table SubscriptionModelUpgrade add Index IX_Relationship122 (ToId);
Alter table SubscriptionModelUpgrade add Foreign Key (ToId) references SubscriptionModel (Id) on delete  restrict on update  restrict;
Alter table TranslatedSubscriptionModel add Index IX_Relationship207 (SubscriptionModelId);
Alter table TranslatedSubscriptionModel add Foreign Key (SubscriptionModelId) references SubscriptionModel (Id) on delete  restrict on update  restrict;
Alter table AssetBankUser add Index IX_Relationship110 (AddressId);
Alter table AssetBankUser add Foreign Key (AddressId) references Address (Id) on delete  restrict on update  restrict;
Alter table ABOrder add Index IX_Relationship139 (ShippingAddressId);
Alter table ABOrder add Foreign Key (ShippingAddressId) references Address (Id) on delete  restrict on update  restrict;
Alter table Address add Index IX_Relationship109 (CountryId);
Alter table Address add Foreign Key (CountryId) references Country (Id) on delete  restrict on update  restrict;
Alter table CountryInTaxRegion add Index IX_Relationship118 (CountryId);
Alter table CountryInTaxRegion add Foreign Key (CountryId) references Country (Id) on delete  restrict on update  restrict;
Alter table TaxValue add Index IX_Relationship113 (TaxTypeId);
Alter table TaxValue add Foreign Key (TaxTypeId) references TaxType (Id) on delete  restrict on update  restrict;
Alter table TranslatedTaxType add Index IX_Relationship218 (TaxTypeId);
Alter table TranslatedTaxType add Foreign Key (TaxTypeId) references TaxType (Id) on delete  restrict on update  restrict;
Alter table TaxValue add Index IX_Relationship115 (TaxRegionId);
Alter table TaxValue add Foreign Key (TaxRegionId) references TaxRegion (Id) on delete  restrict on update  restrict;
Alter table CountryInTaxRegion add Index IX_Relationship117 (TaxRegionId);
Alter table CountryInTaxRegion add Foreign Key (TaxRegionId) references TaxRegion (Id) on delete  restrict on update  restrict;
Alter table ShippingCost add Index IX_Relationship131 (TaxRegionId);
Alter table ShippingCost add Foreign Key (TaxRegionId) references TaxRegion (Id) on delete  restrict on update  restrict;
Alter table TranslatedTaxRegion add Index IX_Relationship206 (TaxRegionId);
Alter table TranslatedTaxRegion add Foreign Key (TaxRegionId) references TaxRegion (Id) on delete  restrict on update  restrict;
Alter table SortAttribute add Index IX_Relationship125 (SortAreaId);
Alter table SortAttribute add Foreign Key (SortAreaId) references SortArea (Id) on delete  restrict on update  restrict;
Alter table PriceBandUsage add Index IX_Relationship130 (PriceBandId);
Alter table PriceBandUsage add Foreign Key (PriceBandId) references PriceBand (Id) on delete  restrict on update  restrict;
Alter table ShippingCost add Index IX_Relationship132 (PriceBandId);
Alter table ShippingCost add Foreign Key (PriceBandId) references PriceBand (Id) on delete  restrict on update  restrict;
Alter table AssetBoxPriceBand add Index IX_Relationship136 (PriceBandId);
Alter table AssetBoxPriceBand add Foreign Key (PriceBandId) references PriceBand (Id) on delete  restrict on update  restrict;
Alter table TranslatedPriceBand add Index IX_Relationship205 (PriceBandId);
Alter table TranslatedPriceBand add Foreign Key (PriceBandId) references PriceBand (Id) on delete  restrict on update  restrict;
Alter table PriceBand add Index IX_Relationship128 (PriceBandTypeId);
Alter table PriceBand add Foreign Key (PriceBandTypeId) references PriceBandType (Id) on delete  restrict on update  restrict;
Alter table TranslatedPriceBandType add Index IX_Relationship204 (PriceBandTypeId);
Alter table TranslatedPriceBandType add Foreign Key (PriceBandTypeId) references PriceBandType (Id) on delete  restrict on update  restrict;
Alter table ShippingCost add Index IX_Relationship133 (QuantityRangeId);
Alter table ShippingCost add Foreign Key (QuantityRangeId) references QuantityRange (Id) on delete  restrict on update  restrict;
Alter table CommercialOptionPurchase add Index IX_Relationship145 (CommercialOptionId);
Alter table CommercialOptionPurchase add Foreign Key (CommercialOptionId) references CommercialOption (Id) on delete  restrict on update  restrict;
Alter table TranslatedCommercialOption add Index IX_Relationship202 (CommercialOptionId);
Alter table TranslatedCommercialOption add Foreign Key (CommercialOptionId) references CommercialOption (Id) on delete  restrict on update  restrict;
Alter table ABOrder add Index IX_Relationship138 (OrderStatusId);
Alter table ABOrder add Foreign Key (OrderStatusId) references OrderStatus (Id) on delete  restrict on update  restrict;
Alter table TranslatedOrderStatus add Index IX_Relationship208 (OrderStatusId);
Alter table TranslatedOrderStatus add Foreign Key (OrderStatusId) references OrderStatus (Id) on delete  restrict on update  restrict;
Alter table CommercialOptionPurchase add Index IX_Relationship146 (PriceBandId,ABOrderId,AssetId);
Alter table CommercialOptionPurchase add Foreign Key (PriceBandId,ABOrderId,AssetId) references AssetPurchasePriceBand (PriceBandId,ABOrderId,AssetId) on delete  restrict on update  restrict;
Alter table OrderStatus add Index IX_Relationship141 (OrderWorkflowId);
Alter table OrderStatus add Foreign Key (OrderWorkflowId) references OrderWorkflow (Id) on delete  restrict on update  restrict;
Alter table FeaturedAssetInBrand add Index IX_Relationship147 (BrandId);
Alter table FeaturedAssetInBrand add Foreign Key (BrandId) references Brand (Id) on delete  restrict on update  restrict;
Alter table UserGroup add Index IX_Relationship149 (BrandId);
Alter table UserGroup add Foreign Key (BrandId) references Brand (Id) on delete  restrict on update  restrict;
Alter table ListItem add Index IX_Relationship152 (ListItemTextTypeId);
Alter table ListItem add Foreign Key (ListItemTextTypeId) references ListItemTextType (Id) on delete  restrict on update  restrict;
Alter table GroupIsRole add Index IX_Relationship155 (RoleId);
Alter table GroupIsRole add Foreign Key (RoleId) references Role (Id) on delete  restrict on update  restrict;
Alter table EmailTemplate add Index IX_Relationship156 (LanguageId);
Alter table EmailTemplate add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedAttribute add Index IX_Relationship174 (LanguageId);
Alter table TranslatedAttribute add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedAttributeValue add Index IX_Relationship175 (LanguageId);
Alter table TranslatedAttributeValue add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedCategory add Index IX_Relationship178 (LanguageId);
Alter table TranslatedCategory add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table ListItem add Index IX_Relationship179 (LanguageId);
Alter table ListItem add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table AssetBankUser add Index IX_Relationship180 (LanguageId);
Alter table AssetBankUser add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedUsageType add Index IX_Relationship182 (LanguageId);
Alter table TranslatedUsageType add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedUsageTypeFormat add Index IX_Relationship184 (LanguageId);
Alter table TranslatedUsageTypeFormat add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedFilter add Index IX_Relationship186 (LanguageId);
Alter table TranslatedFilter add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedMarketingGroup add Index IX_Relationship197 (LanguageId);
Alter table TranslatedMarketingGroup add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedCommercialOption add Index IX_Relationship201 (LanguageId);
Alter table TranslatedCommercialOption add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedPriceBandType add Index IX_Relationship203 (LanguageId);
Alter table TranslatedPriceBandType add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedPriceBand add Index IX_Relationship209 (LanguageId);
Alter table TranslatedPriceBand add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedTaxRegion add Index IX_Relationship210 (LanguageId);
Alter table TranslatedTaxRegion add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedSubscriptionModel add Index IX_Relationship211 (LanguageId);
Alter table TranslatedSubscriptionModel add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedOrderStatus add Index IX_Relationship212 (LanguageId);
Alter table TranslatedOrderStatus add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedMarketingEmail add Index IX_Relationship216 (LanguageId);
Alter table TranslatedMarketingEmail add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table TranslatedTaxType add Index IX_Relationship217 (LanguageId);
Alter table TranslatedTaxType add Foreign Key (LanguageId) references Language (Id) on delete  restrict on update  restrict;
Alter table EmbeddedDataMapping add Index IX_Relationship1642 (EmbeddedDataValueId);
Alter table EmbeddedDataMapping add Foreign Key (EmbeddedDataValueId) references EmbeddedDataValue (Id) on delete  restrict on update  restrict;
Alter table EmbeddedDataValue add Index IX_Relationship15822 (EmbeddedDataTypeId);
Alter table EmbeddedDataValue add Foreign Key (EmbeddedDataTypeId) references EmbeddedDataType (Id) on delete  restrict on update  restrict;
Alter table EmbeddedDataMapping add Index IX_Relationship1652 (MappingDirectionId);
Alter table EmbeddedDataMapping add Foreign Key (MappingDirectionId) references MappingDirection (Id) on delete  restrict on update  restrict;
Alter table ScheduledReportGroup add Index IX_Relationship165 (ReportId);
Alter table ScheduledReportGroup add Foreign Key (ReportId) references ScheduledReport (Id) on delete  restrict on update  restrict;
Alter table FilterAttributeValue add Index IX_Relationship164222 (FilterId);
Alter table FilterAttributeValue add Foreign Key (FilterId) references Filter (Id) on delete  restrict on update  restrict;
Alter table TranslatedFilter add Index IX_Relationship185 (FilterId);
Alter table TranslatedFilter add Foreign Key (FilterId) references Filter (Id) on delete  restrict on update  restrict;
Alter table UserInMarketingGroup add Index IX_Relationship1662 (MarketingGroupId);
Alter table UserInMarketingGroup add Foreign Key (MarketingGroupId) references MarketingGroup (Id) on delete  restrict on update  restrict;
Alter table TranslatedMarketingGroup add Index IX_Relationship196 (MarketingGroupId);
Alter table TranslatedMarketingGroup add Foreign Key (MarketingGroupId) references MarketingGroup (Id) on delete  restrict on update  restrict;
Alter table CustomField add Index IX_Relationship190222 (UsageTypeId);
Alter table CustomField add Foreign Key (UsageTypeId) references CustomFieldUsageType (Id) on delete  restrict on update  restrict;
Alter table CustomFieldValue add Index IX_Relationship191222 (CustomFieldId);
Alter table CustomFieldValue add Foreign Key (CustomFieldId) references CustomField (Id) on delete  restrict on update  restrict;
Alter table CustomFieldValueMapping add Index IX_Relationship201222 (CustomFieldId);
Alter table CustomFieldValueMapping add Foreign Key (CustomFieldId) references CustomField (Id) on delete  restrict on update  restrict;
Alter table CustomField add Index IX_Relationship187222 (TypeId);
Alter table CustomField add Foreign Key (TypeId) references CustomFieldType (Id) on delete  restrict on update  restrict;
Alter table CustomFieldValueMapping add Index IX_Relationship200222 (ListValue);
Alter table CustomFieldValueMapping add Foreign Key (ListValue) references CustomFieldValue (Id) on delete  restrict on update  restrict;
Alter table EmailTemplate add Index IX_Relationship198 (TypeId);
Alter table EmailTemplate add Foreign Key (TypeId) references EmailTemplateType (Id) on delete  restrict on update  restrict;
Alter table AssetInMarketingEmail add Index IX_Relationship2162 (MarketingEmailId);
Alter table AssetInMarketingEmail add Foreign Key (MarketingEmailId) references MarketingEmail (Id) on delete  restrict on update  restrict;
Alter table TranslatedMarketingEmail add Index IX_Relationship215 (MarketingEmailId);
Alter table TranslatedMarketingEmail add Foreign Key (MarketingEmailId) references MarketingEmail (Id) on delete  restrict on update  restrict;



/* Users permissions */






