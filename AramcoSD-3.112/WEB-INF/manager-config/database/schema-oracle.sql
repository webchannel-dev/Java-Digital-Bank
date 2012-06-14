/*
Created		16/08/2004
Modified		06/03/2008
Project		
Model		
Company		
Author		
Version		
Database		Oracle 9i 
*/































Create table Asset (
	Id Double precision NOT NULL ,
	CurrentVersionId Double precision,
	AssetTypeId Double precision NOT NULL ,
	AddedByUserId Double precision,
	LastModifiedByUserId Double precision,
	FileFormatId Double precision,
	FileLocation NVarchar2(255) NOT NULL ,
	OriginalFileLocation NVarchar2(255),
	FileSizeInBytes Double precision NOT NULL ,
	SmallFileLocation NVarchar2(255),
	ThumbnailFileLocation NVarchar2(255),
	MediumFileLocation NVarchar2(255),
	DateCreated Date,
	DateAdded Date,
	DateLastModified Date,
	ExpiryDate Date,
	Author NVarchar2(255),
	Code NVarchar2(255),
	IsApproved Char(1),
	Price Number,
	ImportedAssetId NVarchar2(255) Default NULL,
	Synchronised Char(1) Default 0 NOT NULL ,
	IsPreviewRestricted Char(1) Default 0 NOT NULL ,
	BulkUploadTimestamp Date,
	VersionNumber Number Default 1 NOT NULL ) 
/

Create table AssetBox (
	Id Double precision NOT NULL ,
	UserId Double precision NOT NULL ,
	Name NVarchar2(255),
	SequenceNumber Number Default 1 NOT NULL ) 
/

Create table AssetBankUser (
	Id Double precision NOT NULL ,
	LastModifiedById Double precision,
	Username NVarchar2(100) NOT NULL ,
	Password NVarchar2(100),
	EmailAddress NVarchar2(255),
	Title NVarchar2(100),
	Forename NVarchar2(100),
	Surname NVarchar2(100),
	Organisation NVarchar2(255),
	Address NVarchar2(2000),
	Telephone NVarchar2(200),
	Fax NVarchar2(200),
	RegistrationInfo NClob,
	IsDeleted Char(1) Default 0 NOT NULL ,
	IsAdmin Char(1) Default 0 NOT NULL ,
	IsSuspended Char(1),
	DisplayName NVarchar2(255),
	CN NVarchar2(255),
	DistinguishedName NVarchar2(255),
	Mailbox NVarchar2(255),
	NotActiveDirectory Char(1),
	Hidden Char(1) Default 0,
	NotApproved Char(1) Default 0,
	RequiresUpdate Char(1) Default 0,
	CanBeCategoryAdmin Char(1) Default 0,
	ExpiryDate Date,
	RegisterDate Date,
	JobTitle NVarchar2(255),
	RequestedOrgUnitId Double precision,
	DivisionId Double precision,
	AddressId Double precision,
	VATNumber NVarchar2(100),
	LDAPServerIndex Number,
	ReceiveAlerts Char(1),
	CanLoginFromCms Char(1),
	DateChangedPassword Date,
	Website NChar(200),
	LanguageId Double precision Default 1 NOT NULL ,
	AdminNotes NClob) 
/

Create table CM_Category (
	Id Double precision NOT NULL ,
	ParentId Double precision,
	CategoryTypeId Double precision NOT NULL ,
	Name NVarchar2(2000) NOT NULL ,
	Description NClob,
	SequenceNumber Number,
	Summary NVarchar2(255),
	IsBrowsable Char(1) NOT NULL ,
	IsRestrictive Char(1) Default 0 NOT NULL ,
	BitPosition Number,
	IsListboxCategory Char(1) Default 0,
	CannotBeDeleted Char(1) NOT NULL ,
	Synchronised Char(1) Default 0 NOT NULL ,
	SelectedOnLoad Char(1) Default 0,
	AllowAdvancedOptions Char(1) Default 1) 
/

Create table AttributeValue (
	Id Double precision NOT NULL ,
	AttributeId Double precision NOT NULL ,
	Value NClob,
	DateValue Date,
	DateTimeValue Date,
	SequenceNumber Number,
	IsEditable Char(1) Default 1 NOT NULL ,
	AssetId Double precision,
	MapToFieldValue NVarchar2(200),
	FilterValue Char(1) Default 0) 
/

Create table AssetAttributeValue (
	AttributeValueId Double precision NOT NULL ,
	AttributeId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ) 
/

Create table Attribute (
	Id Double precision NOT NULL ,
	AttributeTypeId Double precision,
	SequenceNumber Number NOT NULL ,
	Label NVarchar2(255) NOT NULL ,
	IsKeywordSearchable Char(1) Default 0 NOT NULL ,
	IsMandatory Char(1) Default 0 NOT NULL ,
	IsSearchField Char(1) Default 0 NOT NULL ,
	DefaultValue NVarchar2(255),
	ValueIfNotVisible NVarchar2(255),
	IsStatic Char(1),
	StaticFieldName NVarchar2(200),
	IsReadOnly Char(1),
	MaxDisplayLength Number Default -1,
	TreeId Double precision,
	HelpText NVarchar2(255),
	Highlight Char(1),
	NameAttribute Char(1) Default 0,
	DefaultKeywordFilter NVarchar2(5),
	OnChangeScript NClob,
	IsHtml Char(1),
	IsRequiredForCompleteness Char(1) Default 0,
	RequiresTranslation Char(1) Default 0,
	DisplayName NVarchar2(255),
	BaseUrl NVarchar2(255),
	AltText NVarchar2(255)) 
/

Create table CM_CategoryType (
	Id Double precision NOT NULL ,
	Description NVarchar2(50),
	IsAlphabeticOrder Char(1) NOT NULL ,
	IsNameGloballyUnique Char(1) NOT NULL ) 
/

Create table AssetBoxAsset (
	AssetBoxId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ,
	AddedByUserId Double precision,
	TimeAdded Date NOT NULL ) 
/

Create table CM_ItemInCategory (
	CategoryId Double precision NOT NULL ,
	ItemId Double precision NOT NULL ) 
/

Create table UserGroup (
	Id Double precision NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
	Description NVarchar2(255),
	IsDefaultGroup Char(1) Default 0 NOT NULL ,
	Mapping NVarchar2(2000),
	BrandId Double precision,
	IPMapping NVarchar2(2000),
	URLMapping NVarchar2(2000),
	DiscountPercentage Number,
	CanOnlyEditOwn Char(1) Default 0 NOT NULL ,
	DailyDownloadLimit Number Default 0 NOT NULL ) 
/

Create table AttributeType (
	Id Double precision NOT NULL ,
	Name NVarchar2(60) NOT NULL ) 
/

Create table AssetType (
	Id Double precision NOT NULL ,
	Name NVarchar2(60) NOT NULL ) 
/

Create table ImageAsset (
	AssetId Double precision NOT NULL ,
	Height Number,
	Width Number,
	ColorSpace Number,
	LargeFileLocation NVarchar2(255),
	FeaturedFileLocation NVarchar2(255),
	NumLayers Number Default 1) 
/

Create table CategoryVisibleToGroup (
	UserGroupId Double precision NOT NULL ,
	CategoryId Double precision NOT NULL ,
	CanDownloadAssets Char(1) Default 0 NOT NULL ,
	CanDownloadWithApproval Char(1) Default 0 NOT NULL ,
	CanUpdateAssets Char(1) Default 0 NOT NULL ,
	CanUpdateWithApproval Char(1) Default 0 NOT NULL ,
	CanDeleteAssets Char(1) Default 0 NOT NULL ,
	CanApproveAssets Char(1) Default 0 NOT NULL ,
	CantDownloadOriginal Char(1) Default 0 NOT NULL ,
	CantDownloadAdvanced Char(1) Default 0 NOT NULL ,
	CanEditSubcategories Char(1) Default 0 NOT NULL ) 
/

Create table GroupAttributeExclusion (
	AttributeId Double precision NOT NULL ,
	UserGroupId Double precision NOT NULL ,
	Value NVarchar2(255) NOT NULL ) 
/

Create table AttributeVisibleToGroup (
	UserGroupId Double precision NOT NULL ,
	AttributeId Double precision NOT NULL ,
	IsWriteable Char(1) Default 0 NOT NULL ) 
/

Create table UserInGroup (
	UserId Double precision NOT NULL ,
	UserGroupId Double precision NOT NULL ) 
/

Create table UsageType (
	Id Double precision NOT NULL ,
	AssetTypeId Double precision,
	Description NVarchar2(255),
	SequenceNumber Number NOT NULL ,
	IsEditable Char(1) Default 1 NOT NULL ,
	ParentId Double precision,
	DownloadTabId Number Default 0 NOT NULL ,
	CanEnterDetails Char(1),
	DetailsMandatory Char(1),
	Message NVarchar2(2000),
	DownloadOriginal Char(1)) 
/

Create table AssetUse (
	UserId Double precision,
	UsageTypeId Double precision,
	TimeOfDownload Date NOT NULL ,
	OriginalDescription NVarchar2(255),
	OtherDescription NVarchar2(255),
	AssetDownloadTypeId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ) 
/

Create table AssetDownloadType (
	Id Double precision NOT NULL ,
	Description NVarchar2(60) NOT NULL ) 
/

Create table FileFormat (
	Id Double precision NOT NULL ,
	AssetTypeId Double precision NOT NULL ,
	FileExtension NVarchar2(20) NOT NULL  UNIQUE ,
	IsIndexable Char(1) Default 0 NOT NULL ,
	IsConvertable Char(1) Default 0 NOT NULL ,
	IsConversionTarget Char(1) Default 0 NOT NULL ,
	Name NVarchar2(255),
	Description NVarchar2(255),
	ThumbnailFileLocation NVarchar2(255),
	ContentType NVarchar2(100),
	ConverterClass NVarchar2(255),
	ToTextConverterClass NVarchar2(255),
	PreviewInclude NVarchar2(255),
	PreviewHeight Number,
	PreviewWidth Number,
	ConvertIndividualLayers Char(1) Default 0) 
/

Create table SystemSetting (
	Id NVarchar2(20) NOT NULL ,
	Value NVarchar2(200)) 
/

Create table AssetView (
	AssetId Double precision NOT NULL ,
	Time Date NOT NULL ,
	UserId Double precision) 
/

Create table AssetApproval (
	AssetId Double precision NOT NULL ,
	UsageTypeId Double precision,
	UserId Double precision NOT NULL ,
	ApprovalStatusId Double precision NOT NULL ,
	DateSubmitted Date,
	DateApproved Date,
	DateExpires Date,
	AdminNotes NClob,
	UserNotes NClob) 
/

Create table ApprovalStatus (
	Id Double precision NOT NULL ,
	Name NVarchar2(60)) 
/

Create table OrgUnit (
	Id Double precision NOT NULL ,
	RootOrgUnitCategoryId Double precision NOT NULL  UNIQUE ,
	AdminGroupId Double precision NOT NULL ,
	StandardGroupId Double precision NOT NULL ) 
/

Create table OrgUnitGroup (
	UserGroupId Double precision NOT NULL ,
	OrgUnitId Double precision NOT NULL ) 
/

Create table ABOrder (
	Id Number NOT NULL ,
	UserId Double precision NOT NULL ,
	DatePlaced Date,
	SubTotal Number,
	VATPercent Float,
	Total Number,
	PSPTransId NVarchar2(255),
	PurchaseId NVarchar2(255),
	OrderStatusId Double precision NOT NULL ,
	ShippingAddressId Double precision,
	RecipientName NVarchar2(255),
	DateFulfilled Date,
	UserNotes NClob,
	PrefersOfflinePayment Char(1) Default 0 NOT NULL ,
	ShippingCost Number,
	BasketCost Number,
	DiscountPercentage Number) 
/

Create table AssetPurchaseLog (
	ABOrderId Number NOT NULL ,
	AssetId Double precision NOT NULL ,
	Description NVarchar2(200),
	Price Number,
	ShippingCost Number) 
/

Create table SearchReport (
	SearchTerm NVarchar2(250),
	FullQuery NVarchar2(2000),
	Success Char(1),
	SearchDate Date) 
/

Create table UsageTypeFormat (
	Id Double precision NOT NULL ,
	FormatId Double precision NOT NULL ,
	UsageTypeId Double precision,
	Description NVarchar2(200),
	ImageWidth Number,
	ImageHeight Number,
	ScaleUp Char(1) Default 0,
	Density Number,
	JpegQuality Number,
	PreserveFormatList NVarchar2(255),
	ApplyStrip Char(1),
	OmitIfLowerRes Char(1) Default 0,
	CropToFit Char(1) Default 0) 
/

Create table ChangeAttributeValueDateRule (
	Id Double precision NOT NULL ,
	AttributeId Double precision NOT NULL ,
	RuleName NVarchar2(200),
	ChangeAttributeId Double precision NOT NULL ,
	NewAttributeValue NVarchar2(255),
	Enabled Char(1) Default 0 NOT NULL ) 
/

Create table SendEmailDateRule (
	Id Double precision NOT NULL ,
	AttributeId Double precision NOT NULL ,
	RuleName NVarchar2(200),
	DaysBefore Number Default 0,
	EmailText NClob,
	Enabled Char(1) Default 0 NOT NULL ) 
/

Create table GroupEmailRule (
	RuleId Double precision NOT NULL ,
	UserGroupId Double precision NOT NULL ) 
/

Create table RelatedAsset (
	ParentId Double precision NOT NULL ,
	ChildId Double precision NOT NULL ) 
/

Create table GroupUsageExclusion (
	UsageTypeId Double precision NOT NULL ,
	UserGroupId Double precision NOT NULL ) 
/

Create table List (
	Id Double precision NOT NULL ,
	Identifier NVarchar2(100),
	Name NVarchar2(255),
	Description NVarchar2(255),
	CannotAddNew Char(1),
	NoHTMLMarkup Char(1) Default 0) 
/

Create table ListItem (
	Identifier NVarchar2(200) NOT NULL ,
	LanguageId Double precision Default 1 NOT NULL ,
	ListId Double precision NOT NULL ,
	Title NVarchar2(255) NOT NULL ,
	Summary NClob,
	Body NClob,
	DateAdded Date,
	CannotBeDeleted Char(1),
	ListItemTextTypeId Double precision Default 1 NOT NULL ) 
/

Create table Division (
	Id Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table PromotedAsset (
	AssetId Double precision NOT NULL ,
	DatePromoted Date) 
/

Create table SubscriptionModel (
	Id Double precision NOT NULL ,
	Description NVarchar2(250),
	Price Number,
	NoOfDownloads Number,
	Duration Number,
	Inactive Char(1) NOT NULL ) 
/

Create table Subscription (
	Id Double precision NOT NULL ,
	StartDate Date,
	SubscriptionModelId Double precision NOT NULL ,
	UserId Double precision NOT NULL ,
	IsActive Char(1) Default 0 NOT NULL ,
	PricePaid Number) 
/

Create table GroupInSubscriptionModel (
	SubscriptionModelId Double precision NOT NULL ,
	UserGroupId Double precision NOT NULL ) 
/

Create table Address (
	Id Double precision NOT NULL ,
	CountryId Double precision,
	Address1 NVarchar2(200),
	Address2 NVarchar2(200),
	Town NVarchar2(100),
	County NVarchar2(100),
	Postcode NVarchar2(50)) 
/

Create table Country (
	Id Double precision NOT NULL ,
	Name NVarchar2(255),
	SequenceNumber Number,
	NativeName NVarchar2(255)) 
/

Create table TaxType (
	Id Double precision NOT NULL ,
	Name NVarchar2(200)) 
/

Create table TaxValue (
	TaxTypeId Double precision NOT NULL ,
	TaxRegionId Double precision NOT NULL ,
	Percent Float,
	ZeroIfTaxNumberGiven Char(1) NOT NULL ) 
/

Create table TaxRegion (
	Id Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table CountryInTaxRegion (
	TaxRegionId Double precision NOT NULL ,
	CountryId Double precision NOT NULL ) 
/

Create table SubscriptionModelUpgrade (
	FromId Double precision NOT NULL ,
	ToId Double precision NOT NULL ,
	Price Number) 
/

Create table VideoAsset (
	AssetId Double precision NOT NULL ,
	PreviewClipLocation NVarchar2(255),
	EmbeddedPreviewClipLocation NVarchar2(255)) 
/

Create table SortAttribute (
	AttributeId Double precision NOT NULL ,
	SortAreaId Double precision NOT NULL ,
	Sequence Number,
	Type Number,
	Reverse Char(1) Default 0) 
/

Create table SortArea (
	Id Double precision NOT NULL ,
	Name NVarchar2(200)) 
/

Create table FeaturedAsset (
	AssetId Double precision NOT NULL ,
	DateFeatured Date) 
/

Create table PriceBand (
	Id Double precision NOT NULL ,
	AssetTypeId Double precision NOT NULL ,
	PriceBandTypeId Double precision NOT NULL ,
	Name NVarchar2(255),
	Description NVarchar2(255),
	BasePrice Number,
	UnitPrice Number,
	DownloadOriginal Char(1),
	MaxQuantity Number Default 0 NOT NULL ,
	IsCommercial Char(1)) 
/

Create table PriceBandType (
	Id Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table PriceBandUsage (
	UsageTypeId Double precision NOT NULL ,
	PriceBandId Double precision NOT NULL ) 
/

Create table ShippingCost (
	Price Number,
	TaxRegionId Double precision NOT NULL ,
	PriceBandId Double precision NOT NULL ,
	QuantityRangeId Double precision NOT NULL ) 
/

Create table QuantityRange (
	Id Double precision NOT NULL ,
	Name NVarchar2(200),
	LowerLimit Number) 
/

Create table AssetBoxPriceBand (
	AssetBoxId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ,
	PriceBandId Double precision NOT NULL ,
	Quantity Number Default 0) 
/

Create table CommercialOption (
	Id Double precision NOT NULL ,
	Name NVarchar2(255),
	Description NClob,
	Price Number,
	Terms NClob,
	IsDisabled Char(1) NOT NULL ) 
/

Create table OrderStatus (
	Id Double precision NOT NULL ,
	Name NVarchar2(255),
	OrderWorkflowId Double precision NOT NULL ,
	ManualSelect Char(1) NOT NULL ) 
/

Create table AssetPurchasePriceBand (
	PriceBandId Double precision NOT NULL ,
	ABOrderId Number NOT NULL ,
	AssetId Double precision NOT NULL ,
	Quantity Number,
	PriceBandTypeId Number,
	PriceBandName NVarchar2(255),
	PriceBandTypeName NVarchar2(255),
	Cost Number,
	ShippingCost Number) 
/

Create table OrderWorkflow (
	Id Double precision NOT NULL ,
	Name NVarchar2(20)) 
/

Create table CommercialOptionPurchase (
	CommercialOptionId Double precision NOT NULL ,
	PriceBandId Double precision NOT NULL ,
	ABOrderId Number NOT NULL ,
	AssetId Double precision NOT NULL ,
	Price Number,
	Description NClob) 
/

Create table Brand (
	Id Double precision NOT NULL ,
	Name NVarchar2(100),
	Code NVarchar2(50) NOT NULL  UNIQUE ,
	CssFile NVarchar2(100),
	LogoFile NVarchar2(100),
	LogoWidth Number,
	LogoHeight Number,
	LogoAlt NVarchar2(100),
	ContentListIdentifier NVarchar2(200)) 
/

Create table FeaturedAssetInBrand (
	BrandId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ) 
/

Create table AudioAsset (
	AssetId Double precision NOT NULL ,
	PreviewClipLocation NVarchar2(255),
	EmbeddedPreviewClipLocation NVarchar2(255)) 
/

Create table ListItemTextType (
	Id Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table Role (
	Id Double precision NOT NULL ,
	Name NVarchar2(200) NOT NULL ,
	Description NVarchar2(200),
	Identifier NVarchar2(50) NOT NULL  UNIQUE ) 
/

Create table GroupIsRole (
	UserGroupId Double precision NOT NULL ,
	RoleId Double precision NOT NULL ) 
/

Create table Language (
	Id Double precision NOT NULL ,
	Name NVarchar2(255) NOT NULL  UNIQUE ,
	NativeName NVarchar2(255),
	Code NVarchar2(20) NOT NULL  UNIQUE ,
	IsSuspended Char(1) NOT NULL ,
	IsDefault Char(1) Default 0 NOT NULL ,
	IsRightToLeft Char(1) Default 0 NOT NULL ,
	IconFilename NVarchar2(255),
	UsesLatinAlphabet Char(1) Default 1 NOT NULL ) 
/

Create table EmailTemplate (
	TextId NVarchar2(255),
	LanguageId Double precision NOT NULL ,
	TypeId Double precision NOT NULL ,
	Code NVarchar2(50),
	AddrFrom NVarchar2(255),
	AddrTo NVarchar2(255),
	AddrCc NVarchar2(255),
	AddrBcc NVarchar2(255),
	Subject NVarchar2(255),
	Body NClob,
	Hidden Char(1) Default 0) 
/

Create table DisplayAttribute (
	DisplayLength Number,
	AttributeId Double precision NOT NULL ,
	SequenceNumber Number,
	ShowLabel Char(1) Default 0,
	IsLink Char(1) Default 0) 
/

Create table AssetBoxShare (
	AssetBoxId Double precision NOT NULL ,
	UserId Double precision NOT NULL ,
	CanEdit Char(1) Default 0 NOT NULL ,
	SequenceNumber Number Default 0 NOT NULL ,
	Alias NVarchar2(255)) 
/

Create table EmbeddedDataMapping (
	EmbeddedDataValueId Double precision NOT NULL ,
	AttributeId Double precision NOT NULL ,
	MappingDirectionId Double precision NOT NULL ,
	Delimiter NVarchar2(50),
	Sequence Number) 
/

Create table EmbeddedDataValue (
	Id Double precision NOT NULL ,
	EmbeddedDataTypeId Double precision NOT NULL ,
	Name NVarchar2(100),
	Expression NVarchar2(250)) 
/

Create table EmbeddedDataType (
	Id Double precision NOT NULL ,
	Name NVarchar2(100) NOT NULL ) 
/

Create table MappingDirection (
	Id Double precision NOT NULL ,
	Name NVarchar2(100)) 
/

Create table ScheduledReport (
	Id Double precision NOT NULL ,
	NextSendDate Date,
	ReportPeriod NVarchar2(100),
	ReportType NVarchar2(100),
	ReportName NVarchar2(200)) 
/

Create table ScheduledReportGroup (
	ReportId Double precision NOT NULL ,
	GroupId Double precision NOT NULL ) 
/

Create table Filter (
	Id Double precision NOT NULL ,
	Name NVarchar2(200),
	IsDefault Char(1) Default 0) 
/

Create table FilterAttributeValue (
	FilterId Double precision NOT NULL ,
	AttributeValueId Double precision NOT NULL ,
	ListAttribute Char(1) Default 0) 
/

Create table MarketingGroup (
	Id Double precision NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
	Description NVarchar2(255),
	Purpose NVarchar2(255),
	IsHiddenInDefaultLanguage Char(1) Default 0 NOT NULL ) 
/

Create table UserInMarketingGroup (
	MarketingGroupId Double precision NOT NULL ,
	UserId Double precision NOT NULL ) 
/

Create table TranslatedAttribute (
	AttributeId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Label NVarchar2(255),
	DefaultValue NVarchar2(255),
	ValueIfNotVisible NVarchar2(255),
	HelpText NVarchar2(255),
	AltText NVarchar2(255),
	DisplayName NVarchar2(255)) 
/

Create table TranslatedAttributeValue (
	LanguageId Double precision NOT NULL ,
	AttributeValueId Double precision NOT NULL ,
	Value NClob) 
/

Create table TranslatedCategory (
	CategoryId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(2000),
	Description NClob,
	Summary NVarchar2(255)) 
/

Create table TranslatedUsageType (
	UsageTypeId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Description NVarchar2(255),
	Message NVarchar2(2000)) 
/

Create table TranslatedUsageTypeFormat (
	UsageTypeFormatId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Description NVarchar2(200)) 
/

Create table TranslatedFilter (
	FilterId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(200)) 
/

Create table CustomFieldUsageType (
	Id Double precision NOT NULL ,
	Description NVarchar2(100)) 
/

Create table CustomField (
	FieldName NVarchar2(100),
	UsageTypeId Double precision NOT NULL ,
	TypeId Double precision NOT NULL ,
	Id Double precision NOT NULL ,
	Required Char(1) Default 0) 
/

Create table CustomFieldType (
	Id Double precision NOT NULL ,
	Description NVarchar2(50)) 
/

Create table CustomFieldValue (
	Id Double precision NOT NULL ,
	CustomFieldId Double precision NOT NULL ,
	Value NVarchar2(200)) 
/

Create table CustomFieldValueMapping (
	ListValue Double precision,
	CustomFieldId Double precision NOT NULL ,
	ItemId Double precision NOT NULL ,
	TextValue NVarchar2(2000)) 
/

Create table SavedSearch (
	Name NVarchar2(255) NOT NULL ,
	UserId Double precision NOT NULL ,
	Keywords NVarchar2(255),
	FullQuery NVarchar2(2000) NOT NULL ,
	HttpQueryString NVarchar2(2000)) 
/

Create table TranslatedMarketingGroup (
	MarketingGroupId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(60),
	Description NVarchar2(255)) 
/

Create table EmailTemplateType (
	Id Double precision NOT NULL ,
	Name NVarchar2(60) NOT NULL  UNIQUE ) 
/

Create table AssetChangeLog (
	UserId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ,
	ChangeTime Date NOT NULL ,
	LogEntry NClob) 
/

Create table TranslatedCommercialOption (
	LanguageId Double precision NOT NULL ,
	CommercialOptionId Double precision NOT NULL ,
	Name NVarchar2(255),
	Description NClob,
	Terms NClob) 
/

Create table TranslatedPriceBandType (
	LanguageId Double precision NOT NULL ,
	PriceBandTypeId Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table TranslatedOrderStatus (
	OrderStatusId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table TranslatedPriceBand (
	PriceBandId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(255),
	Description NVarchar2(255)) 
/

Create table TranslatedSubscriptionModel (
	SubscriptionModelId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Description NVarchar2(250)) 
/

Create table TranslatedTaxRegion (
	TaxRegionId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(255)) 
/

Create table AssetInMarketingEmail (
	MarketingEmailId Double precision NOT NULL ,
	AssetId Double precision NOT NULL ) 
/

Create table MarketingEmail (
	Id Double precision NOT NULL ,
	Name NVarchar2(255),
	Introduction NClob,
	TimeSent Date NOT NULL ) 
/

Create table TranslatedMarketingEmail (
	MarketingEmailId Double precision NOT NULL ,
	LanguageId Double precision NOT NULL ,
	Name NVarchar2(255),
	Introduction NClob) 
/

Create table TranslatedTaxType (
	LanguageId Double precision NOT NULL ,
	TaxTypeId Double precision NOT NULL ,
	Name NVarchar2(200)) 
/






Alter table Asset add primary key (Id) 
/
Alter table AssetBox add primary key (Id) 
/
Alter table AssetBankUser add primary key (Id) 
/
Alter table CM_Category add primary key (Id) 
/
Alter table AttributeValue add primary key (Id) 
/
Alter table AssetAttributeValue add primary key (AttributeValueId,AssetId) 
/
Alter table Attribute add primary key (Id) 
/
Alter table CM_CategoryType add primary key (Id) 
/
Alter table AssetBoxAsset add primary key (AssetBoxId,AssetId) 
/
Alter table CM_ItemInCategory add primary key (CategoryId,ItemId) 
/
Alter table UserGroup add primary key (Id) 
/
Alter table AttributeType add primary key (Id) 
/
Alter table AssetType add primary key (Id) 
/
Alter table ImageAsset add primary key (AssetId) 
/
Alter table CategoryVisibleToGroup add primary key (UserGroupId,CategoryId) 
/
Alter table GroupAttributeExclusion add primary key (AttributeId,UserGroupId,Value) 
/
Alter table AttributeVisibleToGroup add primary key (UserGroupId,AttributeId) 
/
Alter table UserInGroup add primary key (UserId,UserGroupId) 
/
Alter table UsageType add primary key (Id) 
/
Alter table AssetDownloadType add primary key (Id) 
/
Alter table FileFormat add primary key (Id) 
/
Alter table SystemSetting add primary key (Id) 
/
Alter table AssetApproval add primary key (AssetId,UserId) 
/
Alter table ApprovalStatus add primary key (Id) 
/
Alter table OrgUnit add primary key (Id) 
/
Alter table OrgUnitGroup add primary key (UserGroupId,OrgUnitId) 
/
Alter table ABOrder add primary key (Id) 
/
Alter table AssetPurchaseLog add primary key (ABOrderId,AssetId) 
/
Alter table UsageTypeFormat add primary key (Id) 
/
Alter table ChangeAttributeValueDateRule add primary key (Id) 
/
Alter table SendEmailDateRule add primary key (Id) 
/
Alter table GroupEmailRule add primary key (RuleId,UserGroupId) 
/
Alter table RelatedAsset add primary key (ParentId,ChildId) 
/
Alter table GroupUsageExclusion add primary key (UsageTypeId,UserGroupId) 
/
Alter table List add primary key (Id) 
/
Alter table ListItem add primary key (Identifier,LanguageId) 
/
Alter table Division add primary key (Id) 
/
Alter table PromotedAsset add primary key (AssetId) 
/
Alter table SubscriptionModel add primary key (Id) 
/
Alter table Subscription add primary key (Id) 
/
Alter table GroupInSubscriptionModel add primary key (SubscriptionModelId,UserGroupId) 
/
Alter table Address add primary key (Id) 
/
Alter table Country add primary key (Id) 
/
Alter table TaxType add primary key (Id) 
/
Alter table TaxValue add primary key (TaxTypeId,TaxRegionId) 
/
Alter table TaxRegion add primary key (Id) 
/
Alter table CountryInTaxRegion add primary key (TaxRegionId,CountryId) 
/
Alter table SubscriptionModelUpgrade add primary key (FromId,ToId) 
/
Alter table VideoAsset add primary key (AssetId) 
/
Alter table SortArea add primary key (Id) 
/
Alter table FeaturedAsset add primary key (AssetId) 
/
Alter table PriceBand add primary key (Id) 
/
Alter table PriceBandType add primary key (Id) 
/
Alter table PriceBandUsage add primary key (UsageTypeId,PriceBandId) 
/
Alter table ShippingCost add primary key (TaxRegionId,PriceBandId,QuantityRangeId) 
/
Alter table QuantityRange add primary key (Id) 
/
Alter table AssetBoxPriceBand add primary key (AssetBoxId,AssetId,PriceBandId) 
/
Alter table CommercialOption add primary key (Id) 
/
Alter table OrderStatus add primary key (Id) 
/
Alter table AssetPurchasePriceBand add primary key (PriceBandId,ABOrderId,AssetId) 
/
Alter table OrderWorkflow add primary key (Id) 
/
Alter table CommercialOptionPurchase add primary key (PriceBandId,ABOrderId,AssetId) 
/
Alter table Brand add primary key (Id) 
/
Alter table FeaturedAssetInBrand add primary key (BrandId,AssetId) 
/
Alter table AudioAsset add primary key (AssetId) 
/
Alter table ListItemTextType add primary key (Id) 
/
Alter table Role add primary key (Id) 
/
Alter table GroupIsRole add primary key (UserGroupId,RoleId) 
/
Alter table Language add primary key (Id) 
/
Alter table DisplayAttribute add primary key (AttributeId) 
/
Alter table AssetBoxShare add primary key (AssetBoxId,UserId) 
/
Alter table EmbeddedDataMapping add primary key (EmbeddedDataValueId,AttributeId,MappingDirectionId) 
/
Alter table EmbeddedDataValue add primary key (Id) 
/
Alter table EmbeddedDataType add primary key (Id) 
/
Alter table MappingDirection add primary key (Id) 
/
Alter table ScheduledReport add primary key (Id) 
/
Alter table ScheduledReportGroup add primary key (ReportId,GroupId) 
/
Alter table Filter add primary key (Id) 
/
Alter table FilterAttributeValue add primary key (FilterId,AttributeValueId) 
/
Alter table MarketingGroup add primary key (Id) 
/
Alter table UserInMarketingGroup add primary key (MarketingGroupId,UserId) 
/
Alter table TranslatedAttribute add primary key (AttributeId,LanguageId) 
/
Alter table TranslatedAttributeValue add primary key (LanguageId,AttributeValueId) 
/
Alter table TranslatedCategory add primary key (CategoryId,LanguageId) 
/
Alter table TranslatedUsageType add primary key (UsageTypeId,LanguageId) 
/
Alter table TranslatedUsageTypeFormat add primary key (UsageTypeFormatId,LanguageId) 
/
Alter table TranslatedFilter add primary key (FilterId,LanguageId) 
/
Alter table CustomFieldUsageType add primary key (Id) 
/
Alter table CustomField add primary key (Id) 
/
Alter table CustomFieldType add primary key (Id) 
/
Alter table CustomFieldValue add primary key (Id) 
/
Alter table SavedSearch add primary key (Name,UserId) 
/
Alter table TranslatedMarketingGroup add primary key (MarketingGroupId,LanguageId) 
/
Alter table EmailTemplateType add primary key (Id) 
/
Alter table TranslatedCommercialOption add primary key (LanguageId,CommercialOptionId) 
/
Alter table TranslatedPriceBandType add primary key (LanguageId,PriceBandTypeId) 
/
Alter table TranslatedOrderStatus add primary key (OrderStatusId,LanguageId) 
/
Alter table TranslatedPriceBand add primary key (PriceBandId,LanguageId) 
/
Alter table TranslatedSubscriptionModel add primary key (SubscriptionModelId,LanguageId) 
/
Alter table TranslatedTaxRegion add primary key (TaxRegionId,LanguageId) 
/
Alter table AssetInMarketingEmail add primary key (MarketingEmailId,AssetId) 
/
Alter table MarketingEmail add primary key (Id) 
/
Alter table TranslatedMarketingEmail add primary key (MarketingEmailId,LanguageId) 
/
Alter table TranslatedTaxType add primary key (LanguageId,TaxTypeId) 
/



































































































Alter table CustomFieldValueMapping add constraint UniqueCheck unique (ListValue,CustomFieldId,ItemId) 
/


















Create Index ImportedAssetId_Index ON Asset (ImportedAssetId) 
/


Alter table AssetAttributeValue add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetBoxAsset add  foreign key (AssetId) references Asset (Id) 
/
Alter table CM_ItemInCategory add  foreign key (ItemId) references Asset (Id) 
/
Alter table ImageAsset add  foreign key (AssetId) references Asset (Id) 
/
Alter table AttributeValue add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetUse add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetApproval add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetView add  foreign key (AssetId) references Asset (Id) 
/
Alter table RelatedAsset add  foreign key (ParentId) references Asset (Id) 
/
Alter table RelatedAsset add  foreign key (ChildId) references Asset (Id) 
/
Alter table PromotedAsset add  foreign key (AssetId) references Asset (Id) 
/
Alter table VideoAsset add  foreign key (AssetId) references Asset (Id) 
/
Alter table FeaturedAsset add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetBoxPriceBand add  foreign key (AssetId) references Asset (Id) 
/
Alter table FeaturedAssetInBrand add  foreign key (AssetId) references Asset (Id) 
/
Alter table AudioAsset add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetChangeLog add  foreign key (AssetId) references Asset (Id) 
/
Alter table AssetInMarketingEmail add  foreign key (AssetId) references Asset (Id) 
/
Alter table Asset add  foreign key (CurrentVersionId) references Asset (Id) 
/
Alter table AssetBoxAsset add  foreign key (AssetBoxId) references AssetBox (Id) 
/
Alter table AssetBoxPriceBand add  foreign key (AssetBoxId) references AssetBox (Id) 
/
Alter table AssetBoxShare add  foreign key (AssetBoxId) references AssetBox (Id) 
/
Alter table Asset add  foreign key (AddedByUserId) references AssetBankUser (Id) 
/
Alter table UserInGroup add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetUse add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetBox add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetView add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetApproval add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table Asset add  foreign key (LastModifiedByUserId) references AssetBankUser (Id) 
/
Alter table ABOrder add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table Subscription add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetBoxShare add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetBoxAsset add  foreign key (AddedByUserId) references AssetBankUser (Id) 
/
Alter table UserInMarketingGroup add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetBankUser add  foreign key (LastModifiedById) references AssetBankUser (Id) 
/
Alter table SavedSearch add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table AssetChangeLog add  foreign key (UserId) references AssetBankUser (Id) 
/
Alter table CM_ItemInCategory add  foreign key (CategoryId) references CM_Category (Id) 
/
Alter table CM_Category add  foreign key (ParentId) references CM_Category (Id) 
/
Alter table CategoryVisibleToGroup add  foreign key (CategoryId) references CM_Category (Id) 
/
Alter table OrgUnit add  foreign key (RootOrgUnitCategoryId) references CM_Category (Id) 
/
Alter table TranslatedCategory add  foreign key (CategoryId) references CM_Category (Id) 
/
Alter table AssetAttributeValue add  foreign key (AttributeValueId) references AttributeValue (Id) 
/
Alter table FilterAttributeValue add  foreign key (AttributeValueId) references AttributeValue (Id) 
/
Alter table TranslatedAttributeValue add  foreign key (AttributeValueId) references AttributeValue (Id) 
/
Alter table AttributeValue add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table GroupAttributeExclusion add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table AttributeVisibleToGroup add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table AssetAttributeValue add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table ChangeAttributeValueDateRule add  foreign key (ChangeAttributeId) references Attribute (Id) 
/
Alter table ChangeAttributeValueDateRule add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table SendEmailDateRule add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table SortAttribute add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table DisplayAttribute add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table EmbeddedDataMapping add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table TranslatedAttribute add  foreign key (AttributeId) references Attribute (Id) 
/
Alter table CM_Category add  foreign key (CategoryTypeId) references CM_CategoryType (Id) 
/
Alter table CategoryVisibleToGroup add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table GroupAttributeExclusion add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table AttributeVisibleToGroup add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table UserInGroup add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table OrgUnit add  foreign key (AdminGroupId) references UserGroup (Id) 
/
Alter table OrgUnitGroup add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table OrgUnit add  foreign key (StandardGroupId) references UserGroup (Id) 
/
Alter table GroupEmailRule add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table GroupUsageExclusion add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table GroupInSubscriptionModel add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table GroupIsRole add  foreign key (UserGroupId) references UserGroup (Id) 
/
Alter table ScheduledReportGroup add  foreign key (GroupId) references UserGroup (Id) 
/
Alter table Attribute add  foreign key (AttributeTypeId) references AttributeType (Id) 
/
Alter table Asset add  foreign key (AssetTypeId) references AssetType (Id) 
/
Alter table FileFormat add  foreign key (AssetTypeId) references AssetType (Id) 
/
Alter table UsageType add  foreign key (AssetTypeId) references AssetType (Id) 
/
Alter table PriceBand add  foreign key (AssetTypeId) references AssetType (Id) 
/
Alter table AssetUse add  foreign key (UsageTypeId) references UsageType (Id) 
/
Alter table AssetApproval add  foreign key (UsageTypeId) references UsageType (Id) 
/
Alter table UsageTypeFormat add  foreign key (UsageTypeId) references UsageType (Id) 
/
Alter table UsageType add  foreign key (ParentId) references UsageType (Id) 
/
Alter table GroupUsageExclusion add  foreign key (UsageTypeId) references UsageType (Id) 
/
Alter table PriceBandUsage add  foreign key (UsageTypeId) references UsageType (Id) 
/
Alter table TranslatedUsageType add  foreign key (UsageTypeId) references UsageType (Id) 
/
Alter table AssetUse add  foreign key (AssetDownloadTypeId) references AssetDownloadType (Id) 
/
Alter table Asset add  foreign key (FileFormatId) references FileFormat (Id) 
/
Alter table UsageTypeFormat add  foreign key (FormatId) references FileFormat (Id) 
/
Alter table AssetApproval add  foreign key (ApprovalStatusId) references ApprovalStatus (Id) 
/
Alter table OrgUnitGroup add  foreign key (OrgUnitId) references OrgUnit (Id) 
/
Alter table AssetBankUser add  foreign key (RequestedOrgUnitId) references OrgUnit (Id) 
/
Alter table AssetPurchaseLog add  foreign key (ABOrderId) references ABOrder (Id) 
/
Alter table AssetPurchasePriceBand add  foreign key (ABOrderId,AssetId) references AssetPurchaseLog (ABOrderId,AssetId) 
/
Alter table TranslatedUsageTypeFormat add  foreign key (UsageTypeFormatId) references UsageTypeFormat (Id) 
/
Alter table GroupEmailRule add  foreign key (RuleId) references SendEmailDateRule (Id) 
/
Alter table ListItem add  foreign key (ListId) references List (Id) 
/
Alter table AssetBankUser add  foreign key (DivisionId) references Division (Id) 
/
Alter table GroupInSubscriptionModel add  foreign key (SubscriptionModelId) references SubscriptionModel (Id) 
/
Alter table Subscription add  foreign key (SubscriptionModelId) references SubscriptionModel (Id) 
/
Alter table SubscriptionModelUpgrade add  foreign key (FromId) references SubscriptionModel (Id) 
/
Alter table SubscriptionModelUpgrade add  foreign key (ToId) references SubscriptionModel (Id) 
/
Alter table TranslatedSubscriptionModel add  foreign key (SubscriptionModelId) references SubscriptionModel (Id) 
/
Alter table AssetBankUser add  foreign key (AddressId) references Address (Id) 
/
Alter table ABOrder add  foreign key (ShippingAddressId) references Address (Id) 
/
Alter table Address add  foreign key (CountryId) references Country (Id) 
/
Alter table CountryInTaxRegion add  foreign key (CountryId) references Country (Id) 
/
Alter table TaxValue add  foreign key (TaxTypeId) references TaxType (Id) 
/
Alter table TranslatedTaxType add  foreign key (TaxTypeId) references TaxType (Id) 
/
Alter table TaxValue add  foreign key (TaxRegionId) references TaxRegion (Id) 
/
Alter table CountryInTaxRegion add  foreign key (TaxRegionId) references TaxRegion (Id) 
/
Alter table ShippingCost add  foreign key (TaxRegionId) references TaxRegion (Id) 
/
Alter table TranslatedTaxRegion add  foreign key (TaxRegionId) references TaxRegion (Id) 
/
Alter table SortAttribute add  foreign key (SortAreaId) references SortArea (Id) 
/
Alter table PriceBandUsage add  foreign key (PriceBandId) references PriceBand (Id) 
/
Alter table ShippingCost add  foreign key (PriceBandId) references PriceBand (Id) 
/
Alter table AssetBoxPriceBand add  foreign key (PriceBandId) references PriceBand (Id) 
/
Alter table TranslatedPriceBand add  foreign key (PriceBandId) references PriceBand (Id) 
/
Alter table PriceBand add  foreign key (PriceBandTypeId) references PriceBandType (Id) 
/
Alter table TranslatedPriceBandType add  foreign key (PriceBandTypeId) references PriceBandType (Id) 
/
Alter table ShippingCost add  foreign key (QuantityRangeId) references QuantityRange (Id) 
/
Alter table CommercialOptionPurchase add  foreign key (CommercialOptionId) references CommercialOption (Id) 
/
Alter table TranslatedCommercialOption add  foreign key (CommercialOptionId) references CommercialOption (Id) 
/
Alter table ABOrder add  foreign key (OrderStatusId) references OrderStatus (Id) 
/
Alter table TranslatedOrderStatus add  foreign key (OrderStatusId) references OrderStatus (Id) 
/
Alter table CommercialOptionPurchase add  foreign key (PriceBandId,ABOrderId,AssetId) references AssetPurchasePriceBand (PriceBandId,ABOrderId,AssetId) 
/
Alter table OrderStatus add  foreign key (OrderWorkflowId) references OrderWorkflow (Id) 
/
Alter table FeaturedAssetInBrand add  foreign key (BrandId) references Brand (Id) 
/
Alter table UserGroup add  foreign key (BrandId) references Brand (Id) 
/
Alter table ListItem add  foreign key (ListItemTextTypeId) references ListItemTextType (Id) 
/
Alter table GroupIsRole add  foreign key (RoleId) references Role (Id) 
/
Alter table EmailTemplate add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedAttribute add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedAttributeValue add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedCategory add  foreign key (LanguageId) references Language (Id) 
/
Alter table ListItem add  foreign key (LanguageId) references Language (Id) 
/
Alter table AssetBankUser add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedUsageType add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedUsageTypeFormat add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedFilter add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedMarketingGroup add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedCommercialOption add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedPriceBandType add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedPriceBand add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedTaxRegion add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedSubscriptionModel add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedOrderStatus add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedMarketingEmail add  foreign key (LanguageId) references Language (Id) 
/
Alter table TranslatedTaxType add  foreign key (LanguageId) references Language (Id) 
/
Alter table EmbeddedDataMapping add  foreign key (EmbeddedDataValueId) references EmbeddedDataValue (Id) 
/
Alter table EmbeddedDataValue add  foreign key (EmbeddedDataTypeId) references EmbeddedDataType (Id) 
/
Alter table EmbeddedDataMapping add  foreign key (MappingDirectionId) references MappingDirection (Id) 
/
Alter table ScheduledReportGroup add  foreign key (ReportId) references ScheduledReport (Id) 
/
Alter table FilterAttributeValue add  foreign key (FilterId) references Filter (Id) 
/
Alter table TranslatedFilter add  foreign key (FilterId) references Filter (Id) 
/
Alter table UserInMarketingGroup add  foreign key (MarketingGroupId) references MarketingGroup (Id) 
/
Alter table TranslatedMarketingGroup add  foreign key (MarketingGroupId) references MarketingGroup (Id) 
/
Alter table CustomField add  foreign key (UsageTypeId) references CustomFieldUsageType (Id) 
/
Alter table CustomFieldValue add  foreign key (CustomFieldId) references CustomField (Id) 
/
Alter table CustomFieldValueMapping add  foreign key (CustomFieldId) references CustomField (Id) 
/
Alter table CustomField add  foreign key (TypeId) references CustomFieldType (Id) 
/
Alter table CustomFieldValueMapping add  foreign key (ListValue) references CustomFieldValue (Id) 
/
Alter table EmailTemplate add  foreign key (TypeId) references EmailTemplateType (Id) 
/
Alter table AssetInMarketingEmail add  foreign key (MarketingEmailId) references MarketingEmail (Id) 
/
Alter table TranslatedMarketingEmail add  foreign key (MarketingEmailId) references MarketingEmail (Id) 
/







































































































































































































































































/* Roles permissions */





/* Users permissions */









