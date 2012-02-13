
SET DEFINE ~
/*
Created		16/08/2004
Modified		19/01/2011
Project		
Model		
Company		
Author		
Version		
Database		Oracle 9i 
*/


-- Create Types section


-- Create Tables section


Create table Asset (
	Id Integer NOT NULL ,
	CurrentVersionId Integer,
	AssetTypeId Integer,
	AddedByUserId Integer,
	LastModifiedByUserId Integer,
	FileFormatId Integer,
	FileLocation NVarchar2(255),
	OriginalFileLocation NVarchar2(255),
	FileSizeInBytes Integer NOT NULL ,
	SmallFileLocation NVarchar2(255),
	ThumbnailFileLocation NVarchar2(255),
	MediumFileLocation NVarchar2(255),
	DateAdded Date,
	DateLastModified Date,
	ExpiryDate Date,
	Author NVarchar2(255),
	Code NVarchar2(255),
	Price Integer,
	ImportedAssetId NVarchar2(255) Default NULL,
	Synchronised Char (1) Default 0 NOT NULL ,
	IsPreviewRestricted Char (1) Default 0 NOT NULL ,
	BulkUploadTimestamp Date,
	VersionNumber Integer Default 1 NOT NULL ,
	NumViews Integer Default 0 NOT NULL ,
	NumDownloads Integer Default 0 NOT NULL ,
	LastDownloaded Date,
	AssetEntityId Integer,
	OriginalFilename NVarchar2(255),
	IsSensitive Char (1) Default 0 NOT NULL ,
	AgreementTypeId Integer,
	HasSubstituteFile Char (1) Default 0 NOT NULL ,
	IsUnsubmitted Char (1) NOT NULL ,
	IsBrandTemplate Char (1) Default 0 NOT NULL ,
	CanEmbedFile Char (1) Default 0 NOT NULL ,
	ThumbnailGenerationFailed Char (1) Default 0 NOT NULL ,
	IsNotDuplicate Char (1) Default 0 NOT NULL ,
	AdvancedViewing Char (1) Default 0 NOT NULL ,
	FileCheckedOutByUserId Integer,
	FileCheckOutTime Date,
 Constraint pk_Asset primary key (Id) 
) 
;

Create table AssetBox (
	Id Integer NOT NULL ,
	UserId Integer NOT NULL ,
	Name NVarchar2(255),
	SequenceNumber Integer Default 1 NOT NULL ,
 Constraint pk_AssetBox primary key (Id) 
) 
;

Create table AssetBankUser (
	Id Integer NOT NULL ,
	LastModifiedById Integer,
	Username NVarchar2(100) NOT NULL ,
	Password NVarchar2(100),
	EmailAddress NVarchar2(255),
	Title NVarchar2(100),
	Forename NVarchar2(100),
	Surname NVarchar2(100),
	Organisation NVarchar2(255),
	Address NVarchar2(2000),
	RegistrationInfo NClob,
	IsDeleted Char (1) Default 0 NOT NULL ,
	IsAdmin Char (1) Default 0 NOT NULL ,
	IsSuspended Char (1),
	DisplayName NVarchar2(255),
	CN NVarchar2(255),
	DistinguishedName NVarchar2(255),
	Mailbox NVarchar2(255),
	NotActiveDirectory Char (1),
	Hidden Char (1) Default 0,
	NotApproved Char (1) Default 0,
	RequiresUpdate Char (1) Default 0,
	CanBeCategoryAdmin Char (1) Default 0,
	ExpiryDate Date,
	RegisterDate Date,
	JobTitle NVarchar2(255),
	RequestedOrgUnitId Integer,
	DivisionId Integer,
	AddressId Integer,
	VATNumber NVarchar2(100),
	LDAPServerIndex Smallint,
	ReceiveAlerts Char (1),
	CanLoginFromCms Char (1),
	DateChangedPassword Date,
	LanguageId Integer Default 1 NOT NULL ,
	AdminNotes NClob,
	InvitedByUserId Integer,
	AdditionalApprovalName NVarchar2(255),
	SearchResultsPerPage Integer,
	DateLastLoggedIn Date,
	ReactivationPending Char (1) Default 0,
 Constraint pk_AssetBankUser primary key (Id) 
) 
;

Create table CM_Category (
	Id Integer NOT NULL ,
	ParentId Integer,
	CategoryTypeId Integer NOT NULL ,
	Name NVarchar2(2000) NOT NULL ,
	Description NClob,
	SequenceNumber Integer,
	Summary NVarchar2(255),
	IsBrowsable Char (1) NOT NULL ,
	IsRestrictive Char (1) Default 0 NOT NULL ,
	BitPosition Integer,
	IsListboxCategory Char (1) Default 0,
	CannotBeDeleted Char (1) NOT NULL ,
	Synchronised Char (1) Default 0 NOT NULL ,
	SelectedOnLoad Char (1) Default 0,
	AllowAdvancedOptions Char (1) Default 1,
	IsExpired Char (1) Default 0 NOT NULL ,
	WorkflowName NVarchar2(200),
	CanAssignIfNotLeaf Char (1) Default 0 NOT NULL ,
	ExtensionAssetId Integer,
 Constraint pk_CM_Category primary key (Id) 
) 
;

Create table Attribute (
	Id Integer NOT NULL ,
	AttributeTypeId Integer,
	SequenceNumber Integer NOT NULL ,
	Label NVarchar2(255),
	IsKeywordSearchable Char (1) Default 0 NOT NULL ,
	IsMandatory Char (1) Default 0 NOT NULL ,
	IsSearchField Char (1) Default 0 NOT NULL ,
	DefaultValue NVarchar2(255),
	ValueIfNotVisible NVarchar2(255),
	IsStatic Char (1),
	StaticFieldName NVarchar2(200),
	IsReadOnly Char (1),
	MaxDisplayLength Integer Default -1,
	TreeId Integer,
	HelpText NVarchar2(255),
	Highlight Char (1),
	NameAttribute Char (1) Default 0,
	DefaultKeywordFilter NVarchar2(5),
	OnChangeScript NClob,
	IsHtml Char (1),
	IsRequiredForCompleteness Char (1) Default 0,
	RequiresTranslation Char (1) Default 0,
	DisplayName NVarchar2(255),
	BaseUrl NVarchar2(255),
	AltText NVarchar2(255),
	HasSearchTokens Char (1) Default 0 NOT NULL ,
	TokenDelimiterRegex NVarchar2(20),
	IsDescriptionAttribute Char (1),
	Height Integer,
	Width Integer,
	MaxSize Integer,
	IsFiltered Char (1),
	Seed Integer,
	IncrementAmount Integer,
	IsExtendableList Char (1),
	GetDataFromChildren Char (1) Default 0 NOT NULL ,
	IsSearchBuilderField Char (1) Default 1 NOT NULL ,
	InputMask NVarchar2(250),
	IsMandatoryBulkUpload Char (1) Default 0 NOT NULL ,
	ShowOnChild Char (1) Default 0 NOT NULL ,
	IncludeInSearchForChild Char (1) Default 0 NOT NULL ,
	Prefix NVarchar2(40),
	HideForSort Char (1) Default 0 NOT NULL ,
	IsAutoComplete Char (1) Default 0 NOT NULL ,
	PluginClass NVarchar2(250),
	PluginParamsAttributeIds NVarchar2(250),
	ValueColumnName NVarchar2(255),
	ShowOnDownload Char (1) Default 0 NOT NULL ,
	MaxDecimalPlaces Integer,
	MinDecimalPlaces Integer,
	Hidden Char (1) Default 0 NOT NULL ,
 Constraint pk_Attribute primary key (Id) 
) 
;

Create table CM_CategoryType (
	Id Integer NOT NULL ,
	Description NVarchar2(50),
	IsAlphabeticOrder Char (1) NOT NULL ,
	IsNameGloballyUnique Char (1) NOT NULL ,
 Constraint pk_CM_CategoryType primary key (Id) 
) 
;

Create table AssetBoxAsset (
	AssetBoxId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	AddedByUserId Integer,
	TimeAdded Date NOT NULL ,
	SequenceNumber Integer,
 Constraint pk_AssetBoxAsset primary key (AssetBoxId,AssetId) 
) 
;

Create table CM_ItemInCategory (
	CategoryId Integer NOT NULL ,
	ItemId Integer NOT NULL ,
	IsApproved Char (1),
 Constraint pk_CM_ItemInCategory primary key (CategoryId,ItemId) 
) 
;

Create table UserGroup (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
	Description NVarchar2(255),
	IsDefaultGroup Char (1) Default 0 NOT NULL ,
	Mapping NVarchar2(2000),
	BrandId Integer,
	IPMapping NVarchar2(2000),
	URLMapping NVarchar2(2000),
	DiscountPercentage Integer,
	CanOnlyEditOwn Char (1) Default 0 NOT NULL ,
	DailyDownloadLimit Integer Default 0 NOT NULL ,
	CanEmailAssets Char (1) Default 1 NOT NULL ,
	CanSeeSourcePath Char (1) Default 0 NOT NULL ,
	CanRepurposeAssets Char (1) Default 0 NOT NULL ,
	MaxDownloadHeight Integer,
	MaxDownloadWidth Integer,
	CanInviteUsers Char (1) Default 0 NOT NULL ,
	CanAddEmptyAssets Char (1) Default 0 NOT NULL ,
	CanViewLargerSize Char (1) Default 1 NOT NULL ,
	AutomaticBrandRegister Char (1) Default 0,
	AdvancedViewing Char (1) Default 0 NOT NULL ,
 Constraint pk_UserGroup primary key (Id) 
) 
;

Create table AttributeType (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
	AttributeStorageTypeId Integer NOT NULL ,
	SequenceNumber Integer NOT NULL ,
 Constraint pk_AttributeType primary key (Id) 
) 
;

Create table AssetType (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
 Constraint pk_AssetType primary key (Id) 
) 
;

Create table ImageAsset (
	AssetId Integer NOT NULL ,
	Height Integer,
	Width Integer,
	ColorSpace Integer,
	LargeFileLocation NVarchar2(255),
	FeaturedFileLocation NVarchar2(255),
	NumLayers Integer Default 1,
	UnwatermarkedLargeFileLocation NVarchar2(255),
 Constraint pk_ImageAsset primary key (AssetId) 
) 
;

Create table CategoryVisibleToGroup (
	UserGroupId Integer NOT NULL ,
	CategoryId Integer NOT NULL ,
	CanDownloadAssets Char (1) Default 0 NOT NULL ,
	CanDownloadWithApproval Char (1) Default 0 NOT NULL ,
	CanUpdateAssets Char (1) Default 0 NOT NULL ,
	CanUpdateWithApproval Char (1) Default 0 NOT NULL ,
	CanDeleteAssets Char (1) Default 0 NOT NULL ,
	CanApproveAssets Char (1) Default 0 NOT NULL ,
	CantDownloadOriginal Char (1) Default 0 NOT NULL ,
	CantDownloadAdvanced Char (1) Default 0 NOT NULL ,
	CanEditSubcategories Char (1) Default 0 NOT NULL ,
	CantReviewAssets Char (1) Default 0 NOT NULL ,
	CanApproveAssetUploads Char (1) Default 0 NOT NULL ,
	CanViewRestrictedAssets Char (1) Default 0 NOT NULL ,
	ApprRequiredForHighRes Char (1) Default 0 NOT NULL ,
 Constraint pk_CategoryVisibleToGroup primary key (UserGroupId,CategoryId) 
) 
;

Create table GroupAttributeExclusion (
	AttributeId Integer NOT NULL ,
	UserGroupId Integer NOT NULL ,
	Value NVarchar2(255) NOT NULL ,
 Constraint pk_GroupAttributeExclusion primary key (AttributeId,UserGroupId,Value) 
) 
;

Create table AttributeVisibleToGroup (
	UserGroupId Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
	IsWriteable Char (1) Default 0 NOT NULL ,
 Constraint pk_AttributeVisibleToGroup primary key (UserGroupId,AttributeId) 
) 
;

Create table UserInGroup (
	UserId Integer NOT NULL ,
	UserGroupId Integer NOT NULL ,
 Constraint pk_UserInGroup primary key (UserId,UserGroupId) 
) 
;

Create table UsageType (
	Id Integer NOT NULL ,
	AssetTypeId Integer,
	Description NVarchar2(255),
	SequenceNumber Integer NOT NULL ,
	IsEditable Char (1) Default 1 NOT NULL ,
	ParentId Integer,
	DownloadTabId Integer Default 0 NOT NULL ,
	CanEnterDetails Char (1),
	DetailsMandatory Char (1),
	Message NVarchar2(2000),
	DownloadOriginal Char (1),
	IsConsideredHighRes Char (1) Default 0 NOT NULL ,
 Constraint pk_UsageType primary key (Id) 
) 
;

Create table AssetUse (
	Id Integer NOT NULL ,
	UserId Integer,
	SessionLogId Integer,
	UsageTypeId Integer,
	TimeOfDownload Date NOT NULL ,
	OriginalDescription NVarchar2(255),
	OtherDescription NVarchar2(255),
	AssetDownloadTypeId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
 Constraint pk_AssetUse primary key (Id) 
) 
;

Create table AssetDownloadType (
	Id Integer NOT NULL ,
	Description NVarchar2(60) NOT NULL ,
 Constraint pk_AssetDownloadType primary key (Id) 
) 
;

Create table FileFormat (
	Id Integer NOT NULL ,
	AssetTypeId Integer NOT NULL ,
	FileExtension NVarchar2(20) NOT NULL  UNIQUE ,
	IsIndexable Char (1) Default 0 NOT NULL ,
	IsConvertable Char (1) Default 0 NOT NULL ,
	IsConversionTarget Char (1) Default 0 NOT NULL ,
	Name NVarchar2(255),
	Description NVarchar2(255),
	ThumbnailFileLocation NVarchar2(255),
	ContentType NVarchar2(100),
	ConverterClass NVarchar2(255),
	ToTextConverterClass NVarchar2(255),
	PreviewInclude NVarchar2(255),
	PreviewHeight Integer,
	PreviewWidth Integer,
	ConvertIndividualLayers Char (1) Default 0,
	CanViewOriginal Char (1) Default 0 NOT NULL ,
	ViewFileInclude NVarchar2(255),
 Constraint pk_FileFormat primary key (Id) 
) 
;

Create table SystemSetting (
	Id NVarchar2(200) NOT NULL ,
	Value NVarchar2(200),
 Constraint pk_SystemSetting primary key (Id) 
) 
;

Create table AssetView (
	AssetId Integer NOT NULL ,
	Time Date NOT NULL ,
	UserId Integer,
	SessionLogId Integer
) 
;

Create table AssetApproval (
	AssetId Integer NOT NULL ,
	UsageTypeId Integer,
	UserId Integer NOT NULL ,
	ApprovalStatusId Integer NOT NULL ,
	DateSubmitted Date,
	DateApproved Date,
	DateExpires Date,
	AdminNotes NClob,
	UserNotes NClob,
	IsHighResApproval Char (1) Default 0 NOT NULL ,
 Constraint pk_AssetApproval primary key (AssetId,UserId) 
) 
;

Create table ApprovalStatus (
	Id Integer NOT NULL ,
	Name NVarchar2(60),
 Constraint pk_ApprovalStatus primary key (Id) 
) 
;

Create table OrgUnit (
	Id Integer NOT NULL ,
	RootOrgUnitCategoryId Integer NOT NULL  UNIQUE ,
	AdminGroupId Integer NOT NULL ,
	StandardGroupId Integer NOT NULL ,
	RootDescriptiveCategoryId Integer,
	DiskQuotaMb Integer,
 Constraint pk_OrgUnit primary key (Id) 
) 
;

Create table OrgUnitGroup (
	UserGroupId Integer NOT NULL ,
	OrgUnitId Integer NOT NULL ,
 Constraint pk_OrgUnitGroup primary key (UserGroupId,OrgUnitId) 
) 
;

Create table ABOrder (
	Id Integer NOT NULL ,
	UserId Integer NOT NULL ,
	DatePlaced Date,
	SubTotal Integer,
	VATPercent Float,
	Total Integer,
	PSPTransId NVarchar2(255),
	PurchaseId NVarchar2(255),
	OrderStatusId Integer NOT NULL ,
	ShippingAddressId Integer,
	RecipientName NVarchar2(255),
	DateFulfilled Date,
	UserNotes NClob,
	PrefersOfflinePayment Char (1) Default 0 NOT NULL ,
	ShippingCost Integer,
	BasketCost Integer,
	DiscountPercentage Integer,
	PSPUserId NVarchar2(255),
 Constraint pk_ABOrder primary key (Id) 
) 
;

Create table AssetPurchaseLog (
	ABOrderId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	Description NVarchar2(200),
	Price Integer,
	ShippingCost Integer,
 Constraint pk_AssetPurchaseLog primary key (ABOrderId,AssetId) 
) 
;

Create table SearchReport (
	SearchTerm NVarchar2(250),
	FullQuery NVarchar2(2000),
	Success Char (1),
	SearchDate Date
) 
;

Create table UsageTypeFormat (
	Id Integer NOT NULL ,
	FormatId Integer NOT NULL ,
	UsageTypeId Integer,
	Description NVarchar2(200),
	ImageWidth Integer,
	ImageHeight Integer,
	ScaleUp Char (1) Default 0,
	Density Integer,
	JpegQuality Integer,
	PreserveFormatList NVarchar2(255),
	ApplyStrip Char (1),
	OmitIfLowerRes Char (1) Default 0,
	CropToFit Char (1) Default 0,
	ConvertToColorSpaceId Integer,
	Watermark Char (1) Default 0,
	AllowMasking Char (1) Default 1 NOT NULL ,
	PresetMaskId Integer,
	PresetMaskColourId Integer,
 Constraint pk_UsageTypeFormat primary key (Id) 
) 
;

Create table ChangeAttributeValueDateRule (
	Id Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
	RuleName NVarchar2(200),
	ChangeAttributeId Integer NOT NULL ,
	NewAttributeValue NVarchar2(255),
	Enabled Char (1) Default 0 NOT NULL ,
	ActionOnAssetId Integer,
 Constraint pk_ChangeAttributeValueDateRul primary key (Id) 
) 
;

Create table SendEmailDateRule (
	Id Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
	RuleName NVarchar2(200),
	DaysBefore Integer Default 0,
	EmailText NClob,
	Enabled Char (1) Default 0 NOT NULL ,
	EmailAssetDownloaders Char (1) Default 0 NOT NULL ,
 Constraint pk_SendEmailDateRule primary key (Id) 
) 
;

Create table GroupEmailRule (
	RuleId Integer NOT NULL ,
	UserGroupId Integer NOT NULL ,
 Constraint pk_GroupEmailRule primary key (RuleId,UserGroupId) 
) 
;

Create table RelatedAsset (
	ParentId Integer NOT NULL ,
	ChildId Integer NOT NULL ,
	RelationshipTypeId Integer Default 1 NOT NULL ,
	SequenceNumber Integer,
	RelationshipDescription NVarchar2(255),
 Constraint pk_RelatedAsset primary key (ParentId,ChildId,RelationshipTypeId) 
) 
;

Create table GroupUsageExclusion (
	UsageTypeId Integer NOT NULL ,
	UserGroupId Integer NOT NULL ,
 Constraint pk_GroupUsageExclusion primary key (UsageTypeId,UserGroupId) 
) 
;

Create table List (
	Id Integer NOT NULL ,
	Identifier NVarchar2(100),
	Name NVarchar2(255),
	Description NVarchar2(255),
	CannotAddNew Char (1),
	NoHTMLMarkup Char (1) Default 0,
 Constraint pk_List primary key (Id) 
) 
;

Create table ListItem (
	Identifier NVarchar2(200) NOT NULL ,
	LanguageId Integer Default 1 NOT NULL ,
	ListId Integer NOT NULL ,
	Title NVarchar2(255) NOT NULL ,
	Summary NClob,
	Body NClob,
	DateAdded Date,
	CannotBeDeleted Char (1),
	ListItemTextTypeId Integer Default 1 NOT NULL ,
 Constraint pk_ListItem primary key (Identifier,LanguageId) 
) 
;

Create table Division (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_Division primary key (Id) 
) 
;

Create table PromotedAsset (
	AssetId Integer NOT NULL ,
	DatePromoted Date,
 Constraint pk_PromotedAsset primary key (AssetId) 
) 
;

Create table SubscriptionModel (
	Id Integer NOT NULL ,
	Description NVarchar2(250),
	Price Integer,
	NoOfDownloads Integer,
	Duration Integer,
	Inactive Char (1) NOT NULL ,
 Constraint pk_SubscriptionModel primary key (Id) 
) 
;

Create table Subscription (
	Id Integer NOT NULL ,
	StartDate Date,
	SubscriptionModelId Integer NOT NULL ,
	UserId Integer NOT NULL ,
	IsActive Char (1) Default 0 NOT NULL ,
	PricePaid Integer,
 Constraint pk_Subscription primary key (Id) 
) 
;

Create table GroupInSubscriptionModel (
	SubscriptionModelId Integer NOT NULL ,
	UserGroupId Integer NOT NULL ,
 Constraint pk_GroupInSubscriptionModel primary key (SubscriptionModelId,UserGroupId) 
) 
;

Create table Address (
	Id Integer NOT NULL ,
	CountryId Integer,
	Address1 NVarchar2(200),
	Address2 NVarchar2(200),
	Town NVarchar2(100),
	County NVarchar2(100),
	Postcode NVarchar2(50),
 Constraint pk_Address primary key (Id) 
) 
;

Create table Country (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
	SequenceNumber Integer,
	NativeName NVarchar2(255),
 Constraint pk_Country primary key (Id) 
) 
;

Create table TaxType (
	Id Integer NOT NULL ,
	Name NVarchar2(200),
 Constraint pk_TaxType primary key (Id) 
) 
;

Create table TaxValue (
	TaxTypeId Integer NOT NULL ,
	TaxRegionId Integer NOT NULL ,
	Percent Float,
	ZeroIfTaxNumberGiven Char (1) NOT NULL ,
 Constraint pk_TaxValue primary key (TaxTypeId,TaxRegionId) 
) 
;

Create table TaxRegion (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_TaxRegion primary key (Id) 
) 
;

Create table CountryInTaxRegion (
	TaxRegionId Integer NOT NULL ,
	CountryId Integer NOT NULL ,
 Constraint pk_CountryInTaxRegion primary key (TaxRegionId,CountryId) 
) 
;

Create table SubscriptionModelUpgrade (
	FromId Integer NOT NULL ,
	ToId Integer NOT NULL ,
	Price Integer,
 Constraint pk_SubscriptionModelUpgrade primary key (FromId,ToId) 
) 
;

Create table VideoAsset (
	AssetId Integer NOT NULL ,
	PreviewClipLocation NVarchar2(255),
	EmbeddedPreviewClipLocation NVarchar2(255),
	Duration Integer,
	Width Integer,
	Height Integer,
	PAR Float,
	PreviewClipBeingGenerated Char (1) NOT NULL ,
 Constraint pk_VideoAsset primary key (AssetId) 
) 
;

Create table SortAttribute (
	AttributeId Integer NOT NULL ,
	SortAreaId Integer NOT NULL ,
	Sequence Integer,
	Type Integer,
	Reverse Char (1) Default 0
) 
;

Create table SortArea (
	Id Integer NOT NULL ,
	Name NVarchar2(200),
 Constraint pk_SortArea primary key (Id) 
) 
;

Create table FeaturedAsset (
	AssetId Integer NOT NULL ,
	DateFeatured Date,
 Constraint pk_FeaturedAsset primary key (AssetId) 
) 
;

Create table PriceBand (
	Id Integer NOT NULL ,
	AssetTypeId Integer NOT NULL ,
	PriceBandTypeId Integer NOT NULL ,
	Name NVarchar2(255),
	Description NVarchar2(255),
	BasePrice Integer,
	UnitPrice Integer,
	DownloadOriginal Char (1),
	MaxQuantity Integer Default 0 NOT NULL ,
	IsCommercial Char (1),
 Constraint pk_PriceBand primary key (Id) 
) 
;

Create table PriceBandType (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_PriceBandType primary key (Id) 
) 
;

Create table PriceBandUsage (
	UsageTypeId Integer NOT NULL ,
	PriceBandId Integer NOT NULL ,
 Constraint pk_PriceBandUsage primary key (UsageTypeId,PriceBandId) 
) 
;

Create table ShippingCost (
	Price Integer,
	TaxRegionId Integer NOT NULL ,
	PriceBandId Integer NOT NULL ,
	QuantityRangeId Integer NOT NULL ,
 Constraint pk_ShippingCost primary key (TaxRegionId,PriceBandId,QuantityRangeId) 
) 
;

Create table QuantityRange (
	Id Integer NOT NULL ,
	Name NVarchar2(200),
	LowerLimit Integer,
 Constraint pk_QuantityRange primary key (Id) 
) 
;

Create table AssetBoxPriceBand (
	AssetBoxId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	PriceBandId Integer NOT NULL ,
	Quantity Integer Default 0,
 Constraint pk_AssetBoxPriceBand primary key (AssetBoxId,AssetId,PriceBandId) 
) 
;

Create table CommercialOption (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
	Description NClob,
	Price Integer,
	Terms NClob,
	IsDisabled Char (1) NOT NULL ,
 Constraint pk_CommercialOption primary key (Id) 
) 
;

Create table OrderStatus (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
	OrderWorkflowId Integer NOT NULL ,
	ManualSelect Char (1) NOT NULL ,
 Constraint pk_OrderStatus primary key (Id) 
) 
;

Create table AssetPurchasePriceBand (
	PriceBandId Integer NOT NULL ,
	ABOrderId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	Quantity Integer,
	PriceBandTypeId Integer,
	PriceBandName NVarchar2(255),
	PriceBandTypeName NVarchar2(255),
	Cost Integer,
	ShippingCost Integer,
 Constraint pk_AssetPurchasePriceBand primary key (PriceBandId,ABOrderId,AssetId) 
) 
;

Create table OrderWorkflow (
	Id Integer NOT NULL ,
	Name NVarchar2(20),
 Constraint pk_OrderWorkflow primary key (Id) 
) 
;

Create table CommercialOptionPurchase (
	CommercialOptionId Integer,
	PriceBandId Integer NOT NULL ,
	ABOrderId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	Price Integer,
	Description NClob,
 Constraint pk_CommercialOptionPurchase primary key (PriceBandId,ABOrderId,AssetId) 
) 
;

Create table Brand (
	Id Integer NOT NULL ,
	Name NVarchar2(100),
	Code NVarchar2(50) NOT NULL  UNIQUE ,
	CssFile NVarchar2(100),
	LogoFile NVarchar2(100),
	LogoWidth Integer,
	LogoHeight Integer,
	LogoAlt NVarchar2(100),
	ContentListIdentifier NVarchar2(200),
 Constraint pk_Brand primary key (Id) 
) 
;

Create table FeaturedAssetInBrand (
	BrandId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
 Constraint pk_FeaturedAssetInBrand primary key (BrandId,AssetId) 
) 
;

Create table AudioAsset (
	AssetId Integer NOT NULL ,
	PreviewClipLocation NVarchar2(255),
	EmbeddedPreviewClipLocation NVarchar2(255),
	Duration Integer,
	Width Integer,
	Height Integer,
 Constraint pk_AudioAsset primary key (AssetId) 
) 
;

Create table ListItemTextType (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_ListItemTextType primary key (Id) 
) 
;

Create table Role (
	Id Integer NOT NULL ,
	Name NVarchar2(200) NOT NULL ,
	Description NVarchar2(200),
	Identifier NVarchar2(50) NOT NULL  UNIQUE ,
 Constraint pk_Role primary key (Id) 
) 
;

Create table GroupIsRole (
	UserGroupId Integer NOT NULL ,
	RoleId Integer NOT NULL ,
 Constraint pk_GroupIsRole primary key (UserGroupId,RoleId) 
) 
;

Create table Language (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL  UNIQUE ,
	NativeName NVarchar2(255),
	Code NVarchar2(20) NOT NULL  UNIQUE ,
	IsSuspended Char (1) NOT NULL ,
	IsDefault Char (1) Default 0 NOT NULL ,
	IsRightToLeft Char (1) Default 0 NOT NULL ,
	IconFilename NVarchar2(255),
	UsesLatinAlphabet Char (1) Default 1 NOT NULL ,
 Constraint pk_Language primary key (Id) 
) 
;

Create table EmailTemplate (
	TextId NVarchar2(255),
	LanguageId Integer NOT NULL ,
	TypeId Integer NOT NULL ,
	Code NVarchar2(50),
	AddrFrom NVarchar2(255),
	AddrTo NVarchar2(255),
	AddrCc NVarchar2(255),
	AddrBcc NVarchar2(255),
	Subject NVarchar2(255),
	Body NClob,
	Hidden Char (1) Default 0,
	IsEnabled Char (1) Default 1 NOT NULL 
) 
;

Create table DisplayAttribute (
	DisplayAttributeGroupId Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
	DisplayLength Integer,
	SequenceNumber Integer,
	ShowLabel Char (1) Default 0,
	IsLink Char (1) Default 0,
	ShowOnChild Char (1) Default 0 NOT NULL ,
 Constraint pk_DisplayAttribute primary key (DisplayAttributeGroupId,AttributeId) 
) 
;

Create table AssetBoxShare (
	AssetBoxId Integer NOT NULL ,
	UserId Integer NOT NULL ,
	CanEdit Char (1) Default 0 NOT NULL ,
	SequenceNumber Integer Default 0 NOT NULL ,
	Alias NVarchar2(255),
 Constraint pk_AssetBoxShare primary key (AssetBoxId,UserId) 
) 
;

Create table EmbeddedDataMapping (
	EmbeddedDataValueId Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
	MappingDirectionId Integer NOT NULL ,
	DataDelim NVarchar2(50),
	Sequence Integer,
	IsBinary Char (1) Default 0,
 Constraint pk_EmbeddedDataMapping primary key (EmbeddedDataValueId,AttributeId,MappingDirectionId) 
) 
;

Create table EmbeddedDataValue (
	Id Integer NOT NULL ,
	EmbeddedDataTypeId Integer NOT NULL ,
	Name NVarchar2(100),
	Expression NVarchar2(250),
 Constraint pk_EmbeddedDataValue primary key (Id) 
) 
;

Create table EmbeddedDataType (
	Id Integer NOT NULL ,
	Name NVarchar2(100) NOT NULL ,
 Constraint pk_EmbeddedDataType primary key (Id) 
) 
;

Create table MappingDirection (
	Id Integer NOT NULL ,
	Name NVarchar2(100),
 Constraint pk_MappingDirection primary key (Id) 
) 
;

Create table ScheduledReport (
	Id Integer NOT NULL ,
	NextSendDate Date,
	ReportPeriod NVarchar2(100),
	ReportType NVarchar2(100),
	ReportName NVarchar2(200),
 Constraint pk_ScheduledReport primary key (Id) 
) 
;

Create table ScheduledReportGroup (
	ReportId Integer NOT NULL ,
	GroupId Integer NOT NULL ,
 Constraint pk_ScheduledReportGroup primary key (ReportId,GroupId) 
) 
;

Create table Filter (
	Id Integer NOT NULL ,
	UserId Integer,
	Name NVarchar2(200),
	IsDefault Char (1) Default 0,
	FilterTypeId Integer NOT NULL ,
	CategoryIds NVarchar2(200),
	AccessLevelIds NVarchar2(200),
	SequenceNumber Integer Default 0,
 Constraint pk_Filter primary key (Id) 
) 
;

Create table MarketingGroup (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
	Description NVarchar2(255),
	Purpose NVarchar2(255),
	IsHiddenInDefaultLanguage Char (1) Default 0 NOT NULL ,
 Constraint pk_MarketingGroup primary key (Id) 
) 
;

Create table UserInMarketingGroup (
	MarketingGroupId Integer NOT NULL ,
	UserId Integer NOT NULL ,
 Constraint pk_UserInMarketingGroup primary key (MarketingGroupId,UserId) 
) 
;

Create table TranslatedAttribute (
	AttributeId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Label NVarchar2(255),
	DefaultValue NVarchar2(255),
	ValueIfNotVisible NVarchar2(255),
	HelpText NVarchar2(255),
	AltText NVarchar2(255),
	DisplayName NVarchar2(255),
	InputMask NVarchar2(250),
 Constraint pk_TranslatedAttribute primary key (AttributeId,LanguageId) 
) 
;

Create table TranslatedCategory (
	CategoryId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(2000),
	Description NClob,
	Summary NVarchar2(255),
 Constraint pk_TranslatedCategory primary key (CategoryId,LanguageId) 
) 
;

Create table TranslatedUsageType (
	UsageTypeId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Description NVarchar2(255),
	Message NVarchar2(2000),
 Constraint pk_TranslatedUsageType primary key (UsageTypeId,LanguageId) 
) 
;

Create table TranslatedUsageTypeFormat (
	UsageTypeFormatId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Description NVarchar2(200),
 Constraint pk_TranslatedUsageTypeFormat primary key (UsageTypeFormatId,LanguageId) 
) 
;

Create table TranslatedFilter (
	FilterId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(200),
 Constraint pk_TranslatedFilter primary key (FilterId,LanguageId) 
) 
;

Create table CustomFieldUsageType (
	Id Integer NOT NULL ,
	Description NVarchar2(100),
 Constraint pk_CustomFieldUsageType primary key (Id) 
) 
;

Create table CustomField (
	FieldName NVarchar2(100),
	UsageTypeId Integer NOT NULL ,
	TypeId Integer NOT NULL ,
	Id Integer NOT NULL ,
	Required Char (1) Default 0,
	OrgUnitId Integer,
	SequenceNumber Integer Default 999 NOT NULL ,
	IsSubtype Char (1) Default 0 NOT NULL ,
 Constraint pk_CustomField primary key (Id) 
) 
;

Create table CustomFieldType (
	Id Integer NOT NULL ,
	Description NVarchar2(50),
 Constraint pk_CustomFieldType primary key (Id) 
) 
;

Create table CustomFieldValue (
	Id Integer NOT NULL ,
	CustomFieldId Integer NOT NULL ,
	Value NVarchar2(200),
 Constraint pk_CustomFieldValue primary key (Id) 
) 
;

Create table CustomFieldValueMapping (
	ListValue Integer,
	CustomFieldId Integer NOT NULL ,
	ItemId Integer NOT NULL ,
	TextValue NVarchar2(2000)
) 
;

Create table SavedSearch (
	Name NVarchar2(255) NOT NULL ,
	UserId Integer NOT NULL ,
	Keywords NVarchar2(255),
	IsRssFeed Char (1) Default 0 NOT NULL ,
	IsBuilderSearch Char (1) Default 0 NOT NULL ,
	IsDescending Char (1) Default 0 NOT NULL ,
	SortAttributeId Integer,
	Promoted Char (1) Default 0,
	AvailableToAll Char (1) Default 0,
	SearchImage NVarchar2(250),
	NewAssetAlert Char (1) Default 0,
	SearchCriteriaFile NVarchar2(250),
 Constraint pk_SavedSearch primary key (Name,UserId) 
) 
;

Create table TranslatedMarketingGroup (
	MarketingGroupId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(60),
	Description NVarchar2(255),
 Constraint pk_TranslatedMarketingGroup primary key (MarketingGroupId,LanguageId) 
) 
;

Create table EmailTemplateType (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL  UNIQUE ,
 Constraint pk_EmailTemplateType primary key (Id) 
) 
;

Create table AssetChangeLog (
	ChangeTime Date NOT NULL ,
	AssetId Integer,
	LogEntry NClob,
	LogTypeId Integer NOT NULL ,
	SessionLogId Integer,
	VersionNumber Integer,
	Id Integer NOT NULL ,
	AssetDescription NVarchar2(255),
 Constraint pk_AssetChangeLog primary key (Id) 
) 
;

Create table TranslatedCommercialOption (
	LanguageId Integer NOT NULL ,
	CommercialOptionId Integer NOT NULL ,
	Name NVarchar2(255),
	Description NClob,
	Terms NClob,
 Constraint pk_TranslatedCommercialOption primary key (LanguageId,CommercialOptionId) 
) 
;

Create table TranslatedPriceBandType (
	LanguageId Integer NOT NULL ,
	PriceBandTypeId Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_TranslatedPriceBandType primary key (LanguageId,PriceBandTypeId) 
) 
;

Create table TranslatedOrderStatus (
	OrderStatusId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_TranslatedOrderStatus primary key (OrderStatusId,LanguageId) 
) 
;

Create table TranslatedPriceBand (
	PriceBandId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(255),
	Description NVarchar2(255),
 Constraint pk_TranslatedPriceBand primary key (PriceBandId,LanguageId) 
) 
;

Create table TranslatedSubscriptionModel (
	SubscriptionModelId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Description NVarchar2(250),
 Constraint pk_TranslatedSubscriptionModel primary key (SubscriptionModelId,LanguageId) 
) 
;

Create table TranslatedTaxRegion (
	TaxRegionId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_TranslatedTaxRegion primary key (TaxRegionId,LanguageId) 
) 
;

Create table AssetInMarketingEmail (
	MarketingEmailId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
 Constraint pk_AssetInMarketingEmail primary key (MarketingEmailId,AssetId) 
) 
;

Create table MarketingEmail (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
	Introduction NClob,
	TimeSent Date NOT NULL ,
 Constraint pk_MarketingEmail primary key (Id) 
) 
;

Create table TranslatedMarketingEmail (
	MarketingEmailId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(255),
	Introduction NClob,
 Constraint pk_TranslatedMarketingEmail primary key (MarketingEmailId,LanguageId) 
) 
;

Create table TranslatedTaxType (
	LanguageId Integer NOT NULL ,
	TaxTypeId Integer NOT NULL ,
	Name NVarchar2(200),
 Constraint pk_TranslatedTaxType primary key (LanguageId,TaxTypeId) 
) 
;

Create table LogType (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_LogType primary key (Id) 
) 
;

Create table TranslatedCustomField (
	LanguageId Integer NOT NULL ,
	CustomFieldId Integer NOT NULL ,
	FieldName NVarchar2(100),
 Constraint pk_TranslatedCustomField primary key (LanguageId,CustomFieldId) 
) 
;

Create table AssetFile (
	Uri NVarchar2(255) NOT NULL ,
	FileData Blob,
	DateCreated Date NOT NULL ,
 Constraint pk_AssetFile primary key (Uri) 
) 
;

Create table SessionLog (
	Id Integer NOT NULL ,
	UserId Integer,
	IpAddress NVarchar2(255),
	StartDateTime Date,
	LoginDateTime Date,
 Constraint pk_SessionLog primary key (Id) 
) 
;

Create table AssetEntityAttribute (
	AssetEntityId Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
 Constraint pk_AssetEntityAttribute primary key (AssetEntityId,AttributeId) 
) 
;

Create table AssetEntity (
	Id Integer NOT NULL ,
	Name NVarchar2(100) NOT NULL ,
	TermForSiblings NVarchar2(100),
	IsSearchable Char (1) Default 1 NOT NULL ,
	IsQuickSearchable Char (1) Default 0 NOT NULL ,
	IncludedFileFormats NVarchar2(255),
	ExcludedFileFormats NVarchar2(255),
	SequenceNumber Integer,
	MustHaveParent Char (1) Default 0 NOT NULL ,
	ThumbnailFilename NVarchar2(100),
	CanCopyAssets Char (1) Default 0 NOT NULL ,
	DefaultCategoryId Integer,
	UnrestAgreementId Integer,
	RestAgreementId Integer,
	ShowAttributeLabels Char (1) Default 1 NOT NULL ,
	TermForSibling NVarchar2(100),
	CanDownloadChildren Char (1) Default 0 NOT NULL ,
	MatchOnAttributeId Integer,
	ShowOnDescendantCategories Char (1) Default 0 NOT NULL ,
	IsCategoryExtension Char (1) Default 0 NOT NULL ,
 Constraint pk_AssetEntity primary key (Id) 
) 
;

Create table AllowableEntityRelationship (
	AssetEntityId Integer NOT NULL ,
	RelatesToAssetEntityId Integer NOT NULL ,
	DefaultRelationshipCategoryId Integer,
	RelationshipTypeId Integer NOT NULL ,
	RelationshipDescriptionLabel NVarchar2(255),
 Constraint pk_AllowableEntityRelationship primary key (AssetEntityId,RelatesToAssetEntityId,RelationshipTypeId) 
) 
;

Create table RelationshipType (
	Id Integer NOT NULL ,
	Name NVarchar2(100) NOT NULL ,
 Constraint pk_RelationshipType primary key (Id) 
) 
;

Create table AllowableEntityAssetType (
	AssetEntityId Integer NOT NULL ,
	AssetTypeId Integer NOT NULL ,
 Constraint pk_AllowableEntityAssetType primary key (AssetEntityId,AssetTypeId) 
) 
;

Create table TranslatedAssetEntity (
	AssetEntityId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Name NVarchar2(200),
	TermForSiblings NVarchar2(200),
	TermForSibling NVarchar2(200),
 Constraint pk_TranslatedAssetEntity primary key (AssetEntityId,LanguageId) 
) 
;

Create table FilterType (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_FilterType primary key (Id) 
) 
;

Create table FilterGroup (
	Id Integer NOT NULL ,
	FilterTypeId Integer NOT NULL ,
	Name NVarchar2(200),
 Constraint pk_FilterGroup primary key (Id) 
) 
;

Create table FilterInGroup (
	FilterId Integer NOT NULL ,
	GroupId Integer NOT NULL ,
 Constraint pk_FilterInGroup primary key (FilterId,GroupId) 
) 
;

Create table ActionOnAsset (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
	Description NVarchar2(255),
	ActionClass NVarchar2(255) NOT NULL ,
 Constraint pk_ActionOnAsset primary key (Id) 
) 
;

Create table Agreement (
	Id Integer NOT NULL ,
	Title NVarchar2(255),
	Body NClob,
	Expiry Date,
	AvailableToAll Char (1),
	SharedWithOU Char (1),
 Constraint pk_Agreement primary key (Id) 
) 
;

Create table TranslatedAgreement (
	AgreementId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Title NVarchar2(255),
	Body NClob,
 Constraint pk_TranslatedAgreement primary key (AgreementId,LanguageId) 
) 
;

Create table AssetAgreement (
	AgreementId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	DateActivated Date,
	IsCurrent Char (1)
) 
;

Create table OrgUnitForAgreement (
	AgreementId Integer NOT NULL ,
	OrgUnitId Integer NOT NULL 
) 
;

Create table TranslatedNewsItem (
	LanguageId Integer NOT NULL ,
	NewsItemId Integer NOT NULL ,
	Title NVarchar2(255),
	Content NClob,
 Constraint pk_TranslatedNewsItem primary key (LanguageId,NewsItemId) 
) 
;

Create table NewsItem (
	Id Integer NOT NULL ,
	Title NVarchar2(255) NOT NULL ,
	Content NClob NOT NULL ,
	DateCreated Date,
	IsPublished Char (1) Default 0 NOT NULL ,
 Constraint pk_NewsItem primary key (Id) 
) 
;

Create table AssetRating (
	Rating Integer,
	UserId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	FeedbackComment NVarchar2(2000),
	Id Integer NOT NULL ,
	FeedbackSubject NVarchar2(200),
	DateOfFeedback Date,
 Constraint pk_AssetRating primary key (Id) 
) 
;

Create table WorkflowInfo (
	Id Integer NOT NULL ,
	WorkflowName NVarchar2(200),
	VariationName NVarchar2(200),
	StateName NVarchar2(200),
	LastStateChangeDate Date,
	WorkflowableEntityId Integer NOT NULL ,
 Constraint pk_WorkflowInfo primary key (Id) 
) 
;

Create table AssetWorkflowAudit (
	Id Integer NOT NULL ,
	UserId Integer,
	AssetId Integer NOT NULL ,
	Message NClob,
	DateAdded Date,
	Transition NVarchar2(200),
	WorkflowName NVarchar2(255),
 Constraint pk_AssetWorkflowAudit primary key (Id,AssetId) 
) 
;

Create table StorageDevice (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
	Path NVarchar2(255) NOT NULL ,
	IsRelative Char (1) Default 0 NOT NULL ,
	StorageTypeId Integer Default 0 NOT NULL ,
	MaxSpaceInMb Integer,
	UsedSpaceInBytes Integer Default 0 NOT NULL ,
	LastScanTime Date,
	IsLocked Char (1) Default 0 NOT NULL ,
	SequenceNumber Integer Default 0 NOT NULL ,
	FactoryClassName NVarchar2(255),
	UsedLocalSpaceInBytes Integer Default 0 NOT NULL ,
	AuthenticationName NVarchar2(255),
	AuthenticationKey NVarchar2(255),
	HttpBaseUrl NVarchar2(255),
 Constraint pk_StorageDevice primary key (Id) 
) 
;

Create table ColorSpace (
	Id Integer NOT NULL ,
	Description NVarchar2(255),
	ShowOnDownload Char (1) Default 1,
	FileLocation NVarchar2(255) NOT NULL ,
 Constraint pk_ColorSpace primary key (Id) 
) 
;

Create table GroupFilterExclusion (
	FilterId Integer NOT NULL ,
	UserGroupId Integer NOT NULL ,
 Constraint pk_GroupFilterExclusion primary key (FilterId,UserGroupId) 
) 
;

Create table FilterForCategory (
	FilterId Integer NOT NULL ,
	CategoryId Integer NOT NULL ,
	SequenceNumber Integer,
 Constraint pk_FilterForCategory primary key (FilterId,CategoryId) 
) 
;

Create table AssetAttributeValues (
	AssetId Integer NOT NULL ,
	title NVarchar2(2000),
	description NVarchar2(2000),
	keywords NVarchar2(2000),
	datecreated Date,
	activationdate Date,
	expirydate Date,
	usagerightsother NVarchar2(2000),
	sensitivitynotes NVarchar2(2000),
 Constraint pk_AssetAttributeValues primary key (AssetId) 
) 
;

Create table ListAttributeValue (
	Id Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
	IsEditable Char (1) Default 1 NOT NULL ,
	Value NVarchar2(255) NOT NULL ,
	AdditionalValue NClob,
	SequenceNumber Integer,
	ActionOnAssetId Integer,
	MapToFieldValue NVarchar2(255),
 Constraint pk_ListAttributeValue primary key (Id) 
) 
;

Create table AssetListAttributeValue (
	AssetId Integer NOT NULL ,
	ListAttributeValueId Integer NOT NULL ,
 Constraint pk_AssetListAttributeValue primary key (AssetId,ListAttributeValueId) 
) 
;

Create table AttributeStorageType (
	Id Integer NOT NULL ,
	Name NVarchar2(60) NOT NULL ,
 Constraint pk_AttributeStorageType primary key (Id) 
) 
;

Create table TranslatedListAttributeValue (
	ListAttributeValueId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	Value NVarchar2(255),
	AdditionalValue NClob,
 Constraint pk_TranslatedListAttributeValu primary key (ListAttributeValueId,LanguageId) 
) 
;

Create table TranslatedAssetAttributeValues (
	AssetId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	title NVarchar2(2000),
	description NVarchar2(2000),
	keywords NVarchar2(2000),
	usagerightsother NVarchar2(2000),
	sensitivitynotes NVarchar2(2000),
 Constraint pk_TranslatedAssetAttributeVal primary key (AssetId,LanguageId) 
) 
;

Create table FilterAssetAttributeValue (
	AttributeId Integer NOT NULL ,
	FilterId Integer NOT NULL ,
	Value NClob,
	DateValue Date,
	DateTimeValue Date,
 Constraint pk_FilterAssetAttributeValue primary key (AttributeId,FilterId) 
) 
;

Create table FilterListAttributeValue (
	FilterId Integer NOT NULL ,
	ListAttributeValueId Integer NOT NULL ,
 Constraint pk_FilterListAttributeValue primary key (FilterId,ListAttributeValueId) 
) 
;

Create table LastAttributeValue (
	AttributeId Integer NOT NULL ,
	NumberValue Integer,
 Constraint pk_LastAttributeValue primary key (AttributeId) 
) 
;

Create table RepurposedSlideshow (
	DisplayTime Integer,
	InfoBar Char (1),
	RepurposedVersionId Integer NOT NULL ,
	SearchCriteriaFile NVarchar2(255),
	MaintainAspectRatio Char (1),
	IncludeLabels Char (1),
	CaptionIds NVarchar2(255),
	JpgConversionQuality Integer,
	LanguageCode NVarchar2(20),
	ImageHeight Integer,
	ImageWidth Integer,
	Description NVarchar2(255),
 Constraint pk_RepurposedSlideshow primary key (RepurposedVersionId) 
) 
;

Create table RepurposedAsset (
	Duration Integer,
	FileFormatId Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	PreviewFileLocation NVarchar2(250),
	RepurposedVersionId Integer NOT NULL ,
 Constraint pk_RepurposedAsset primary key (RepurposedVersionId) 
) 
;

Create table RepurposedVersion (
	Id Integer NOT NULL ,
	Url NVarchar2(255) NOT NULL ,
	Height NVarchar2(5),
	Width NVarchar2(5),
	CreatedDate Date NOT NULL ,
	CreatedByUserId Integer,
 Constraint pk_RepurposedVersion primary key (Id) 
) 
;

Create table Mask (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
	Filename NVarchar2(255),
	Width Integer NOT NULL ,
	Height Integer NOT NULL ,
 Constraint pk_Mask primary key (Id) 
) 
;

Create table NamedColour (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
	Red Number(3,0) NOT NULL ,
	Green Number(3,0) NOT NULL ,
	Blue Number(3,0) NOT NULL ,
 Constraint pk_NamedColour primary key (Id) 
) 
;

Create table SecondaryDownloadUsageType (
	AssetUseId Integer NOT NULL ,
	UsageTypeId Integer NOT NULL ,
	OriginalDescription NVarchar2(255),
 Constraint pk_SecondaryDownloadUsageType primary key (AssetUseId,UsageTypeId) 
) 
;

Create table AssetEntityDepRule (
	SourceAssetEntityId Integer NOT NULL ,
	DependentAssetEntityId Integer NOT NULL ,
	DepRuleTrigger NVarchar2(1) NOT NULL ,
	DepRuleActionId Integer NOT NULL ,
 Constraint pk_AssetEntityDepRule primary key (SourceAssetEntityId,DependentAssetEntityId) 
) 
;

Create table InDesignAsset (
	AssetId Integer NOT NULL ,
	IsTemplate Char (1) NOT NULL ,
	TemplateAssetId Integer,
	InDesignDocumentId Integer,
	PDFStatusId Integer,
	PDFStatusChangeDate Date,
 Constraint pk_InDesignAsset primary key (AssetId) 
) 
;

Create table AttributeInInDesignDocument (
	InDesignDocumentId Integer NOT NULL ,
	AttributeId Integer NOT NULL ,
 Constraint pk_AttributeInInDesignDocument primary key (InDesignDocumentId,AttributeId) 
) 
;

Create table InDesignPDFQuality (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
 Constraint pk_InDesignPDFQuality primary key (Id) 
) 
;

Create table InDesignDocument (
	Id Integer NOT NULL ,
	InDesignPDFQualityId Integer NOT NULL ,
	FileLocation NVarchar2(255) NOT NULL ,
	OriginalFilename NVarchar2(255) NOT NULL ,
	FileSizeInBytes Integer NOT NULL ,
	NumSymbols Integer NOT NULL ,
 Constraint pk_InDesignDocument primary key (Id) 
) 
;

Create table InDesignAssetEntity (
	AssetEntityId Integer NOT NULL ,
	InDesignAssetEntityTypeId Integer NOT NULL ,
 Constraint pk_InDesignAssetEntity primary key (AssetEntityId) 
) 
;

Create table InDesignAssetEntityType (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
 Constraint pk_InDesignAssetEntityType primary key (Id) 
) 
;

Create table DepRuleAction (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
 Constraint pk_DepRuleAction primary key (Id) 
) 
;

Create table AssetInBatchRelease (
	AssetId Integer NOT NULL ,
	BatchReleaseId Integer NOT NULL ,
 Constraint pk_AssetInBatchRelease primary key (AssetId,BatchReleaseId) 
) 
;

Create table Message (
	Id Integer NOT NULL ,
 Constraint pk_Message primary key (Id) 
) 
;

Create table BatchReleasedNotifications (
	MessageId Integer NOT NULL ,
	BatchReleaseId Integer NOT NULL ,
 Constraint pk_BatchReleasedNotifications primary key (MessageId,BatchReleaseId) 
) 
;

Create table BatchRelease (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
	CreatedByUserId Integer NOT NULL ,
 Constraint pk_BatchRelease primary key (Id) 
) 
;

Create table UserApprovesBatchRelease (
	AssetBankUserId Integer NOT NULL ,
	BatchReleaseId Integer NOT NULL ,
	Approved Char (1) Default 0,
 Constraint pk_UserApprovesBatchRelease primary key (AssetBankUserId,BatchReleaseId) 
) 
;

Create table EntityRelationshipDescription (
	AssetEntityId Integer NOT NULL ,
	RelationshipTypeId Integer NOT NULL ,
	FromName NVarchar2(100),
	FromNamePlural NVarchar2(100),
	ToName NVarchar2(100),
	ToNamePlural NVarchar2(100),
 Constraint pk_EntityRelationshipDescripti primary key (AssetEntityId,RelationshipTypeId) 
) 
;

Create table TranslatedERDescription (
	AssetEntityId Integer NOT NULL ,
	RelationshipTypeId Integer NOT NULL ,
	LanguageId Integer NOT NULL ,
	FromName NVarchar2(100),
	FromNamePlural NVarchar2(100),
	ToName NVarchar2(100),
	ToNamePlural NVarchar2(100),
 Constraint pk_TranslatedERDescription primary key (AssetEntityId,RelationshipTypeId,LanguageId) 
) 
;

Create table OrgUnitContent (
	OrgUnitId Integer NOT NULL ,
	ContentPurposeId Integer NOT NULL ,
	ContentListItemIdentifier NVarchar2(200) NOT NULL ,
	MenuListItemIdentifier NVarchar2(200),
 Constraint pk_OrgUnitContent primary key (OrgUnitId,ContentListItemIdentifier) 
) 
;

Create table ContentPurpose (
	Id Integer NOT NULL ,
	Name NVarchar2(255),
 Constraint pk_ContentPurpose primary key (Id) 
) 
;

Create table PublishAction (
	Id Integer NOT NULL ,
	CategoryId Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
	Path NVarchar2(255) NOT NULL ,
	RunDaily Char (1) NOT NULL ,
 Constraint pk_PublishAction primary key (Id) 
) 
;

Create table DisplayAttributeGroup (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
 Constraint pk_DisplayAttributeGroup primary key (Id) 
) 
;

Create table InDesignPDFStatus (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
 Constraint pk_InDesignPDFStatus primary key (Id) 
) 
;

Create table InDesignPDFJob (
	Id Integer NOT NULL ,
	AssetId Integer NOT NULL ,
	JobStatusId Integer NOT NULL ,
 Constraint pk_InDesignPDFJob primary key (Id) 
) 
;

Create table InDesignPDFJobStatus (
	Id Integer NOT NULL ,
	Name NVarchar2(255) NOT NULL ,
 Constraint pk_InDesignPDFJobStatus primary key (Id) 
) 
;


-- Create Alternate keys section

Alter table CustomFieldValueMapping add Constraint UniqueCheck unique (ListValue,CustomFieldId,ItemId) 
;

-- Create Indexes section

Create Index ImportedAssetId_Index ON Asset (ImportedAssetId) 
;


-- Create Foreign keys section
Create Index IX_Relationship6 ON AssetBoxAsset (AssetId)
;
Alter table AssetBoxAsset add Constraint Relationship6 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship8 ON CM_ItemInCategory (ItemId)
;
Alter table CM_ItemInCategory add Constraint Relationship8 foreign key (ItemId) references Asset (Id) 
;
Create Index IX_Relationship29 ON ImageAsset (AssetId)
;
Alter table ImageAsset add Constraint Relationship29 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship49 ON AssetUse (AssetId)
;
Alter table AssetUse add Constraint Relationship49 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship66 ON AssetApproval (AssetId)
;
Alter table AssetApproval add Constraint Relationship66 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship77 ON AssetView (AssetId)
;
Alter table AssetView add Constraint Relationship77 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship95 ON RelatedAsset (ParentId)
;
Alter table RelatedAsset add Constraint Relationship95 foreign key (ParentId) references Asset (Id) 
;
Create Index IX_Relationship96 ON RelatedAsset (ChildId)
;
Alter table RelatedAsset add Constraint Relationship96 foreign key (ChildId) references Asset (Id) 
;
Create Index IX_Relationship104 ON PromotedAsset (AssetId)
;
Alter table PromotedAsset add Constraint Relationship104 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship123 ON VideoAsset (AssetId)
;
Alter table VideoAsset add Constraint Relationship123 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_FeaturedAsset_Asset_FK ON FeaturedAsset (AssetId)
;
Alter table FeaturedAsset add Constraint FeaturedAsset_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship135 ON AssetBoxPriceBand (AssetId)
;
Alter table AssetBoxPriceBand add Constraint Relationship135 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship150 ON FeaturedAssetInBrand (AssetId)
;
Alter table FeaturedAssetInBrand add Constraint Relationship150 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship151 ON AudioAsset (AssetId)
;
Alter table AudioAsset add Constraint Relationship151 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship214 ON AssetInMarketingEmail (AssetId)
;
Alter table AssetInMarketingEmail add Constraint Relationship214 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship219 ON Asset (CurrentVersionId)
;
Alter table Asset add Constraint Relationship219 foreign key (CurrentVersionId) references Asset (Id) 
;
Create Index IX_Relationship241 ON AssetChangeLog (AssetId)
;
Alter table AssetChangeLog add Constraint Relationship241 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship273 ON AssetAgreement (AssetId)
;
Alter table AssetAgreement add Constraint Relationship273 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship278 ON AssetRating (AssetId)
;
Alter table AssetRating add Constraint Relationship278 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Relationship282 ON AssetWorkflowAudit (AssetId)
;
Alter table AssetWorkflowAudit add Constraint Relationship282 foreign key (AssetId) references Asset (Id) 
;
Create Index IX_AAV_Asset_FK ON AssetAttributeValues (AssetId)
;
Alter table AssetAttributeValues add Constraint AAV_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_ALAV_Asset_FK ON AssetListAttributeValue (AssetId)
;
Alter table AssetListAttributeValue add Constraint ALAV_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_TAAV_Asset_FK ON TranslatedAssetAttributeValues (AssetId)
;
Alter table TranslatedAssetAttributeValues add Constraint TAAV_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_RepurposedAsset_Asset_FK ON RepurposedAsset (AssetId)
;
Alter table RepurposedAsset add Constraint RepurposedAsset_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_InDAsset_Asset_FK ON InDesignAsset (AssetId)
;
Alter table InDesignAsset add Constraint InDAsset_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_AIBR_Asset_FK ON AssetInBatchRelease (AssetId)
;
Alter table AssetInBatchRelease add Constraint AIBR_Asset_FK foreign key (AssetId) references Asset (Id) 
;
Create Index IX_Asset_Category_FK ON CM_Category (ExtensionAssetId)
;
Alter table CM_Category add Constraint Asset_Category_FK foreign key (ExtensionAssetId) references Asset (Id) 
;
Create Index IX_Relationship5 ON AssetBoxAsset (AssetBoxId)
;
Alter table AssetBoxAsset add Constraint Relationship5 foreign key (AssetBoxId) references AssetBox (Id) 
;
Create Index IX_Relationship134 ON AssetBoxPriceBand (AssetBoxId)
;
Alter table AssetBoxPriceBand add Constraint Relationship134 foreign key (AssetBoxId) references AssetBox (Id) 
;
Create Index IX_Relationship158 ON AssetBoxShare (AssetBoxId)
;
Alter table AssetBoxShare add Constraint Relationship158 foreign key (AssetBoxId) references AssetBox (Id) 
;
Create Index IX_Asset_User_Added_FK ON Asset (AddedByUserId)
;
Alter table Asset add Constraint Asset_User_Added_FK foreign key (AddedByUserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship47 ON UserInGroup (UserId)
;
Alter table UserInGroup add Constraint Relationship47 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship51 ON AssetUse (UserId)
;
Alter table AssetUse add Constraint Relationship51 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship52 ON AssetBox (UserId)
;
Alter table AssetBox add Constraint Relationship52 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship63 ON AssetView (UserId)
;
Alter table AssetView add Constraint Relationship63 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship67 ON AssetApproval (UserId)
;
Alter table AssetApproval add Constraint Relationship67 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Asset_User_Modified_FK ON Asset (LastModifiedByUserId)
;
Alter table Asset add Constraint Asset_User_Modified_FK foreign key (LastModifiedByUserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship78 ON ABOrder (UserId)
;
Alter table ABOrder add Constraint Relationship78 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship120 ON Subscription (UserId)
;
Alter table Subscription add Constraint Relationship120 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship159 ON AssetBoxShare (UserId)
;
Alter table AssetBoxShare add Constraint Relationship159 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship164 ON AssetBoxAsset (AddedByUserId)
;
Alter table AssetBoxAsset add Constraint Relationship164 foreign key (AddedByUserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship172 ON UserInMarketingGroup (UserId)
;
Alter table UserInMarketingGroup add Constraint Relationship172 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship187 ON AssetBankUser (LastModifiedById)
;
Alter table AssetBankUser add Constraint Relationship187 foreign key (LastModifiedById) references AssetBankUser (Id) 
;
Create Index IX_Relationship195 ON SavedSearch (UserId)
;
Alter table SavedSearch add Constraint Relationship195 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship243 ON SessionLog (UserId)
;
Alter table SessionLog add Constraint Relationship243 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship259 ON Filter (UserId)
;
Alter table Filter add Constraint Relationship259 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship279 ON AssetRating (UserId)
;
Alter table AssetRating add Constraint Relationship279 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship284 ON AssetWorkflowAudit (UserId)
;
Alter table AssetWorkflowAudit add Constraint Relationship284 foreign key (UserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship285 ON AssetBankUser (InvitedByUserId)
;
Alter table AssetBankUser add Constraint Relationship285 foreign key (InvitedByUserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship310 ON RepurposedVersion (CreatedByUserId)
;
Alter table RepurposedVersion add Constraint Relationship310 foreign key (CreatedByUserId) references AssetBankUser (Id) 
;
Create Index IX_UABR_User_FK ON UserApprovesBatchRelease (AssetBankUserId)
;
Alter table UserApprovesBatchRelease add Constraint UABR_User_FK foreign key (AssetBankUserId) references AssetBankUser (Id) 
;
Create Index IX_BatchRel_User_FK ON BatchRelease (CreatedByUserId)
;
Alter table BatchRelease add Constraint BatchRel_User_FK foreign key (CreatedByUserId) references AssetBankUser (Id) 
;
Create Index IX_FileCheckedOutByUser_FK ON Asset (FileCheckedOutByUserId)
;
Alter table Asset add Constraint FileCheckedOutByUser_FK foreign key (FileCheckedOutByUserId) references AssetBankUser (Id) 
;
Create Index IX_Relationship9 ON CM_ItemInCategory (CategoryId)
;
Alter table CM_ItemInCategory add Constraint Relationship9 foreign key (CategoryId) references CM_Category (Id) 
;
Create Index IX_Relationship10 ON CM_Category (ParentId)
;
Alter table CM_Category add Constraint Relationship10 foreign key (ParentId) references CM_Category (Id) 
;
Create Index IX_Relationship35 ON CategoryVisibleToGroup (CategoryId)
;
Alter table CategoryVisibleToGroup add Constraint Relationship35 foreign key (CategoryId) references CM_Category (Id) 
;
Create Index IX_Relationship76 ON OrgUnit (RootOrgUnitCategoryId)
;
Alter table OrgUnit add Constraint Relationship76 foreign key (RootOrgUnitCategoryId) references CM_Category (Id) 
;
Create Index IX_Relationship177 ON TranslatedCategory (CategoryId)
;
Alter table TranslatedCategory add Constraint Relationship177 foreign key (CategoryId) references CM_Category (Id) 
;
Create Index IX_Relationship290 ON FilterForCategory (CategoryId)
;
Alter table FilterForCategory add Constraint Relationship290 foreign key (CategoryId) references CM_Category (Id) 
;
Create Index IX_ORGUNIT_DESC_CAT_FK ON OrgUnit (RootDescriptiveCategoryId)
;
Alter table OrgUnit add Constraint ORGUNIT_DESC_CAT_FK foreign key (RootDescriptiveCategoryId) references CM_Category (Id) 
;
Create Index IX_CAT_AER_FK ON AllowableEntityRelationship (DefaultRelationshipCategoryId)
;
Alter table AllowableEntityRelationship add Constraint CAT_AER_FK foreign key (DefaultRelationshipCategoryId) references CM_Category (Id) 
;
Create Index IX_Category_Publish_FK ON PublishAction (CategoryId)
;
Alter table PublishAction add Constraint Category_Publish_FK foreign key (CategoryId) references CM_Category (Id) 
;
Create Index IX_Relationship40 ON GroupAttributeExclusion (AttributeId)
;
Alter table GroupAttributeExclusion add Constraint Relationship40 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship45 ON AttributeVisibleToGroup (AttributeId)
;
Alter table AttributeVisibleToGroup add Constraint Relationship45 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship86 ON ChangeAttributeValueDateRule (ChangeAttributeId)
;
Alter table ChangeAttributeValueDateRule add Constraint Relationship86 foreign key (ChangeAttributeId) references Attribute (Id) 
;
Create Index IX_Relationship89 ON ChangeAttributeValueDateRule (AttributeId)
;
Alter table ChangeAttributeValueDateRule add Constraint Relationship89 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship90 ON SendEmailDateRule (AttributeId)
;
Alter table SendEmailDateRule add Constraint Relationship90 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship126 ON SortAttribute (AttributeId)
;
Alter table SortAttribute add Constraint Relationship126 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_DA_Attribute_FK ON DisplayAttribute (AttributeId)
;
Alter table DisplayAttribute add Constraint DA_Attribute_FK foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship163 ON EmbeddedDataMapping (AttributeId)
;
Alter table EmbeddedDataMapping add Constraint Relationship163 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship173 ON TranslatedAttribute (AttributeId)
;
Alter table TranslatedAttribute add Constraint Relationship173 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship253 ON AssetEntityAttribute (AttributeId)
;
Alter table AssetEntityAttribute add Constraint Relationship253 foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship292 ON AssetEntity (MatchOnAttributeId)
;
Alter table AssetEntity add Constraint Relationship292 foreign key (MatchOnAttributeId) references Attribute (Id) 
;
Create Index IX_LAV_Attribute_FK ON ListAttributeValue (AttributeId)
;
Alter table ListAttributeValue add Constraint LAV_Attribute_FK foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_FAAV_Attribute_FK ON FilterAssetAttributeValue (AttributeId)
;
Alter table FilterAssetAttributeValue add Constraint FAAV_Attribute_FK foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_LastAV_Attribute_FK ON LastAttributeValue (AttributeId)
;
Alter table LastAttributeValue add Constraint LastAV_Attribute_FK foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_AIInDD_Attribute_FK ON AttributeInInDesignDocument (AttributeId)
;
Alter table AttributeInInDesignDocument add Constraint AIInDD_Attribute_FK foreign key (AttributeId) references Attribute (Id) 
;
Create Index IX_Relationship7 ON CM_Category (CategoryTypeId)
;
Alter table CM_Category add Constraint Relationship7 foreign key (CategoryTypeId) references CM_CategoryType (Id) 
;
Create Index IX_Relationship34 ON CategoryVisibleToGroup (UserGroupId)
;
Alter table CategoryVisibleToGroup add Constraint Relationship34 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship41 ON GroupAttributeExclusion (UserGroupId)
;
Alter table GroupAttributeExclusion add Constraint Relationship41 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship44 ON AttributeVisibleToGroup (UserGroupId)
;
Alter table AttributeVisibleToGroup add Constraint Relationship44 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship48 ON UserInGroup (UserGroupId)
;
Alter table UserInGroup add Constraint Relationship48 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship73 ON OrgUnit (AdminGroupId)
;
Alter table OrgUnit add Constraint Relationship73 foreign key (AdminGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship75 ON OrgUnitGroup (UserGroupId)
;
Alter table OrgUnitGroup add Constraint Relationship75 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship81 ON OrgUnit (StandardGroupId)
;
Alter table OrgUnit add Constraint Relationship81 foreign key (StandardGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship88 ON GroupEmailRule (UserGroupId)
;
Alter table GroupEmailRule add Constraint Relationship88 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship100 ON GroupUsageExclusion (UserGroupId)
;
Alter table GroupUsageExclusion add Constraint Relationship100 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship108 ON GroupInSubscriptionModel (UserGroupId)
;
Alter table GroupInSubscriptionModel add Constraint Relationship108 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship154 ON GroupIsRole (UserGroupId)
;
Alter table GroupIsRole add Constraint Relationship154 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship167 ON ScheduledReportGroup (GroupId)
;
Alter table ScheduledReportGroup add Constraint Relationship167 foreign key (GroupId) references UserGroup (Id) 
;
Create Index IX_Relationship288 ON GroupFilterExclusion (UserGroupId)
;
Alter table GroupFilterExclusion add Constraint Relationship288 foreign key (UserGroupId) references UserGroup (Id) 
;
Create Index IX_Relationship19 ON Attribute (AttributeTypeId)
;
Alter table Attribute add Constraint Relationship19 foreign key (AttributeTypeId) references AttributeType (Id) 
;
Create Index IX_Relationship27 ON Asset (AssetTypeId)
;
Alter table Asset add Constraint Relationship27 foreign key (AssetTypeId) references AssetType (Id) 
;
Create Index IX_Relationship58 ON FileFormat (AssetTypeId)
;
Alter table FileFormat add Constraint Relationship58 foreign key (AssetTypeId) references AssetType (Id) 
;
Create Index IX_Relationship153 ON UsageType (AssetTypeId)
;
Alter table UsageType add Constraint Relationship153 foreign key (AssetTypeId) references AssetType (Id) 
;
Create Index IX_Relationship223 ON PriceBand (AssetTypeId)
;
Alter table PriceBand add Constraint Relationship223 foreign key (AssetTypeId) references AssetType (Id) 
;
Create Index IX_Relationship254 ON AllowableEntityAssetType (AssetTypeId)
;
Alter table AllowableEntityAssetType add Constraint Relationship254 foreign key (AssetTypeId) references AssetType (Id) 
;
Create Index IX_Relationship50 ON AssetUse (UsageTypeId)
;
Alter table AssetUse add Constraint Relationship50 foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_Relationship70 ON AssetApproval (UsageTypeId)
;
Alter table AssetApproval add Constraint Relationship70 foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_Relationship82 ON UsageTypeFormat (UsageTypeId)
;
Alter table UsageTypeFormat add Constraint Relationship82 foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_Relationship97 ON UsageType (ParentId)
;
Alter table UsageType add Constraint Relationship97 foreign key (ParentId) references UsageType (Id) 
;
Create Index IX_Relationship99 ON GroupUsageExclusion (UsageTypeId)
;
Alter table GroupUsageExclusion add Constraint Relationship99 foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_Relationship129 ON PriceBandUsage (UsageTypeId)
;
Alter table PriceBandUsage add Constraint Relationship129 foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_Relationship181 ON TranslatedUsageType (UsageTypeId)
;
Alter table TranslatedUsageType add Constraint Relationship181 foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_SDUT_UsageTypeId_FK ON SecondaryDownloadUsageType (UsageTypeId)
;
Alter table SecondaryDownloadUsageType add Constraint SDUT_UsageTypeId_FK foreign key (UsageTypeId) references UsageType (Id) 
;
Create Index IX_SDUT_AssetUseId_FK ON SecondaryDownloadUsageType (AssetUseId)
;
Alter table SecondaryDownloadUsageType add Constraint SDUT_AssetUseId_FK foreign key (AssetUseId) references AssetUse (Id) 
;
Create Index IX_Relationship57 ON AssetUse (AssetDownloadTypeId)
;
Alter table AssetUse add Constraint Relationship57 foreign key (AssetDownloadTypeId) references AssetDownloadType (Id) 
;
Create Index IX_Relationship60 ON Asset (FileFormatId)
;
Alter table Asset add Constraint Relationship60 foreign key (FileFormatId) references FileFormat (Id) 
;
Create Index IX_Relationship83 ON UsageTypeFormat (FormatId)
;
Alter table UsageTypeFormat add Constraint Relationship83 foreign key (FormatId) references FileFormat (Id) 
;
Create Index IX_RepurposedAsset_FileFo17031 ON RepurposedAsset (FileFormatId)
;
Alter table RepurposedAsset add Constraint RepurposedAsset_FileFormat_FK foreign key (FileFormatId) references FileFormat (Id) 
;
Create Index IX_Relationship68 ON AssetApproval (ApprovalStatusId)
;
Alter table AssetApproval add Constraint Relationship68 foreign key (ApprovalStatusId) references ApprovalStatus (Id) 
;
Create Index IX_Relationship74 ON OrgUnitGroup (OrgUnitId)
;
Alter table OrgUnitGroup add Constraint Relationship74 foreign key (OrgUnitId) references OrgUnit (Id) 
;
Create Index IX_Relationship101 ON AssetBankUser (RequestedOrgUnitId)
;
Alter table AssetBankUser add Constraint Relationship101 foreign key (RequestedOrgUnitId) references OrgUnit (Id) 
;
Create Index IX_Relationship275 ON OrgUnitForAgreement (OrgUnitId)
;
Alter table OrgUnitForAgreement add Constraint Relationship275 foreign key (OrgUnitId) references OrgUnit (Id) 
;
Create Index IX_Relationship293 ON CustomField (OrgUnitId)
;
Alter table CustomField add Constraint Relationship293 foreign key (OrgUnitId) references OrgUnit (Id) 
;
Create Index IX_OrgUnit_OUC_FK ON OrgUnitContent (OrgUnitId)
;
Alter table OrgUnitContent add Constraint OrgUnit_OUC_FK foreign key (OrgUnitId) references OrgUnit (Id) 
;
Create Index IX_Relationship79 ON AssetPurchaseLog (ABOrderId)
;
Alter table AssetPurchaseLog add Constraint Relationship79 foreign key (ABOrderId) references ABOrder (Id) 
;
Create Index IX_Relationship140 ON AssetPurchasePriceBand (ABOrderId,AssetId)
;
Alter table AssetPurchasePriceBand add Constraint Relationship140 foreign key (ABOrderId,AssetId) references AssetPurchaseLog (ABOrderId,AssetId) 
;
Create Index IX_Relationship183 ON TranslatedUsageTypeFormat (UsageTypeFormatId)
;
Alter table TranslatedUsageTypeFormat add Constraint Relationship183 foreign key (UsageTypeFormatId) references UsageTypeFormat (Id) 
;
Create Index IX_Relationship87 ON GroupEmailRule (RuleId)
;
Alter table GroupEmailRule add Constraint Relationship87 foreign key (RuleId) references SendEmailDateRule (Id) 
;
Create Index IX_Relationship102 ON ListItem (ListId)
;
Alter table ListItem add Constraint Relationship102 foreign key (ListId) references List (Id) 
;
Create Index IX_Relationship103 ON AssetBankUser (DivisionId)
;
Alter table AssetBankUser add Constraint Relationship103 foreign key (DivisionId) references Division (Id) 
;
Create Index IX_Relationship107 ON GroupInSubscriptionModel (SubscriptionModelId)
;
Alter table GroupInSubscriptionModel add Constraint Relationship107 foreign key (SubscriptionModelId) references SubscriptionModel (Id) 
;
Create Index IX_Relationship119 ON Subscription (SubscriptionModelId)
;
Alter table Subscription add Constraint Relationship119 foreign key (SubscriptionModelId) references SubscriptionModel (Id) 
;
Create Index IX_Relationship121 ON SubscriptionModelUpgrade (FromId)
;
Alter table SubscriptionModelUpgrade add Constraint Relationship121 foreign key (FromId) references SubscriptionModel (Id) 
;
Create Index IX_Relationship122 ON SubscriptionModelUpgrade (ToId)
;
Alter table SubscriptionModelUpgrade add Constraint Relationship122 foreign key (ToId) references SubscriptionModel (Id) 
;
Create Index IX_Relationship207 ON TranslatedSubscriptionModel (SubscriptionModelId)
;
Alter table TranslatedSubscriptionModel add Constraint Relationship207 foreign key (SubscriptionModelId) references SubscriptionModel (Id) 
;
Create Index IX_Relationship110 ON AssetBankUser (AddressId)
;
Alter table AssetBankUser add Constraint Relationship110 foreign key (AddressId) references Address (Id) 
;
Create Index IX_Relationship139 ON ABOrder (ShippingAddressId)
;
Alter table ABOrder add Constraint Relationship139 foreign key (ShippingAddressId) references Address (Id) 
;
Create Index IX_Relationship109 ON Address (CountryId)
;
Alter table Address add Constraint Relationship109 foreign key (CountryId) references Country (Id) 
;
Create Index IX_Relationship118 ON CountryInTaxRegion (CountryId)
;
Alter table CountryInTaxRegion add Constraint Relationship118 foreign key (CountryId) references Country (Id) 
;
Create Index IX_Relationship113 ON TaxValue (TaxTypeId)
;
Alter table TaxValue add Constraint Relationship113 foreign key (TaxTypeId) references TaxType (Id) 
;
Create Index IX_Relationship218 ON TranslatedTaxType (TaxTypeId)
;
Alter table TranslatedTaxType add Constraint Relationship218 foreign key (TaxTypeId) references TaxType (Id) 
;
Create Index IX_Relationship115 ON TaxValue (TaxRegionId)
;
Alter table TaxValue add Constraint Relationship115 foreign key (TaxRegionId) references TaxRegion (Id) 
;
Create Index IX_Relationship117 ON CountryInTaxRegion (TaxRegionId)
;
Alter table CountryInTaxRegion add Constraint Relationship117 foreign key (TaxRegionId) references TaxRegion (Id) 
;
Create Index IX_Relationship131 ON ShippingCost (TaxRegionId)
;
Alter table ShippingCost add Constraint Relationship131 foreign key (TaxRegionId) references TaxRegion (Id) 
;
Create Index IX_Relationship206 ON TranslatedTaxRegion (TaxRegionId)
;
Alter table TranslatedTaxRegion add Constraint Relationship206 foreign key (TaxRegionId) references TaxRegion (Id) 
;
Create Index IX_Relationship125 ON SortAttribute (SortAreaId)
;
Alter table SortAttribute add Constraint Relationship125 foreign key (SortAreaId) references SortArea (Id) 
;
Create Index IX_Relationship130 ON PriceBandUsage (PriceBandId)
;
Alter table PriceBandUsage add Constraint Relationship130 foreign key (PriceBandId) references PriceBand (Id) 
;
Create Index IX_Relationship132 ON ShippingCost (PriceBandId)
;
Alter table ShippingCost add Constraint Relationship132 foreign key (PriceBandId) references PriceBand (Id) 
;
Create Index IX_Relationship136 ON AssetBoxPriceBand (PriceBandId)
;
Alter table AssetBoxPriceBand add Constraint Relationship136 foreign key (PriceBandId) references PriceBand (Id) 
;
Create Index IX_Relationship205 ON TranslatedPriceBand (PriceBandId)
;
Alter table TranslatedPriceBand add Constraint Relationship205 foreign key (PriceBandId) references PriceBand (Id) 
;
Create Index IX_Relationship128 ON PriceBand (PriceBandTypeId)
;
Alter table PriceBand add Constraint Relationship128 foreign key (PriceBandTypeId) references PriceBandType (Id) 
;
Create Index IX_Relationship204 ON TranslatedPriceBandType (PriceBandTypeId)
;
Alter table TranslatedPriceBandType add Constraint Relationship204 foreign key (PriceBandTypeId) references PriceBandType (Id) 
;
Create Index IX_Relationship133 ON ShippingCost (QuantityRangeId)
;
Alter table ShippingCost add Constraint Relationship133 foreign key (QuantityRangeId) references QuantityRange (Id) 
;
Create Index IX_Relationship145 ON CommercialOptionPurchase (CommercialOptionId)
;
Alter table CommercialOptionPurchase add Constraint Relationship145 foreign key (CommercialOptionId) references CommercialOption (Id) 
;
Create Index IX_Relationship202 ON TranslatedCommercialOption (CommercialOptionId)
;
Alter table TranslatedCommercialOption add Constraint Relationship202 foreign key (CommercialOptionId) references CommercialOption (Id) 
;
Create Index IX_Relationship138 ON ABOrder (OrderStatusId)
;
Alter table ABOrder add Constraint Relationship138 foreign key (OrderStatusId) references OrderStatus (Id) 
;
Create Index IX_Relationship208 ON TranslatedOrderStatus (OrderStatusId)
;
Alter table TranslatedOrderStatus add Constraint Relationship208 foreign key (OrderStatusId) references OrderStatus (Id) 
;
Create Index IX_Relationship146 ON CommercialOptionPurchase (PriceBandId,ABOrderId,AssetId)
;
Alter table CommercialOptionPurchase add Constraint Relationship146 foreign key (PriceBandId,ABOrderId,AssetId) references AssetPurchasePriceBand (PriceBandId,ABOrderId,AssetId) 
;
Create Index IX_Relationship141 ON OrderStatus (OrderWorkflowId)
;
Alter table OrderStatus add Constraint Relationship141 foreign key (OrderWorkflowId) references OrderWorkflow (Id) 
;
Create Index IX_Relationship147 ON FeaturedAssetInBrand (BrandId)
;
Alter table FeaturedAssetInBrand add Constraint Relationship147 foreign key (BrandId) references Brand (Id) 
;
Create Index IX_Relationship149 ON UserGroup (BrandId)
;
Alter table UserGroup add Constraint Relationship149 foreign key (BrandId) references Brand (Id) 
;
Create Index IX_Relationship152 ON ListItem (ListItemTextTypeId)
;
Alter table ListItem add Constraint Relationship152 foreign key (ListItemTextTypeId) references ListItemTextType (Id) 
;
Create Index IX_Relationship155 ON GroupIsRole (RoleId)
;
Alter table GroupIsRole add Constraint Relationship155 foreign key (RoleId) references Role (Id) 
;
Create Index IX_Relationship156 ON EmailTemplate (LanguageId)
;
Alter table EmailTemplate add Constraint Relationship156 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship174 ON TranslatedAttribute (LanguageId)
;
Alter table TranslatedAttribute add Constraint Relationship174 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship178 ON TranslatedCategory (LanguageId)
;
Alter table TranslatedCategory add Constraint Relationship178 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship179 ON ListItem (LanguageId)
;
Alter table ListItem add Constraint Relationship179 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship180 ON AssetBankUser (LanguageId)
;
Alter table AssetBankUser add Constraint Relationship180 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship182 ON TranslatedUsageType (LanguageId)
;
Alter table TranslatedUsageType add Constraint Relationship182 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship184 ON TranslatedUsageTypeFormat (LanguageId)
;
Alter table TranslatedUsageTypeFormat add Constraint Relationship184 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship186 ON TranslatedFilter (LanguageId)
;
Alter table TranslatedFilter add Constraint Relationship186 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship197 ON TranslatedMarketingGroup (LanguageId)
;
Alter table TranslatedMarketingGroup add Constraint Relationship197 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship201 ON TranslatedCommercialOption (LanguageId)
;
Alter table TranslatedCommercialOption add Constraint Relationship201 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship203 ON TranslatedPriceBandType (LanguageId)
;
Alter table TranslatedPriceBandType add Constraint Relationship203 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship209 ON TranslatedPriceBand (LanguageId)
;
Alter table TranslatedPriceBand add Constraint Relationship209 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship210 ON TranslatedTaxRegion (LanguageId)
;
Alter table TranslatedTaxRegion add Constraint Relationship210 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship211 ON TranslatedSubscriptionModel (LanguageId)
;
Alter table TranslatedSubscriptionModel add Constraint Relationship211 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship212 ON TranslatedOrderStatus (LanguageId)
;
Alter table TranslatedOrderStatus add Constraint Relationship212 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship216 ON TranslatedMarketingEmail (LanguageId)
;
Alter table TranslatedMarketingEmail add Constraint Relationship216 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship217 ON TranslatedTaxType (LanguageId)
;
Alter table TranslatedTaxType add Constraint Relationship217 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship231 ON TranslatedCustomField (LanguageId)
;
Alter table TranslatedCustomField add Constraint Relationship231 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship251 ON TranslatedAssetEntity (LanguageId)
;
Alter table TranslatedAssetEntity add Constraint Relationship251 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship271 ON TranslatedAgreement (LanguageId)
;
Alter table TranslatedAgreement add Constraint Relationship271 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship277 ON TranslatedNewsItem (LanguageId)
;
Alter table TranslatedNewsItem add Constraint Relationship277 foreign key (LanguageId) references Language (Id) 
;
Create Index IX_TLAV_Language_FK ON TranslatedListAttributeValue (LanguageId)
;
Alter table TranslatedListAttributeValue add Constraint TLAV_Language_FK foreign key (LanguageId) references Language (Id) 
;
Create Index IX_TAAV_Language_FK ON TranslatedAssetAttributeValues (LanguageId)
;
Alter table TranslatedAssetAttributeValues add Constraint TAAV_Language_FK foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Language_TERD_FK ON TranslatedERDescription (LanguageId)
;
Alter table TranslatedERDescription add Constraint Language_TERD_FK foreign key (LanguageId) references Language (Id) 
;
Create Index IX_Relationship1642 ON EmbeddedDataMapping (EmbeddedDataValueId)
;
Alter table EmbeddedDataMapping add Constraint Relationship1642 foreign key (EmbeddedDataValueId) references EmbeddedDataValue (Id) 
;
Create Index IX_Relationship15822 ON EmbeddedDataValue (EmbeddedDataTypeId)
;
Alter table EmbeddedDataValue add Constraint Relationship15822 foreign key (EmbeddedDataTypeId) references EmbeddedDataType (Id) 
;
Create Index IX_Relationship1652 ON EmbeddedDataMapping (MappingDirectionId)
;
Alter table EmbeddedDataMapping add Constraint Relationship1652 foreign key (MappingDirectionId) references MappingDirection (Id) 
;
Create Index IX_Relationship165 ON ScheduledReportGroup (ReportId)
;
Alter table ScheduledReportGroup add Constraint Relationship165 foreign key (ReportId) references ScheduledReport (Id) 
;
Create Index IX_Relationship185 ON TranslatedFilter (FilterId)
;
Alter table TranslatedFilter add Constraint Relationship185 foreign key (FilterId) references Filter (Id) 
;
Create Index IX_Relationship261 ON FilterInGroup (FilterId)
;
Alter table FilterInGroup add Constraint Relationship261 foreign key (FilterId) references Filter (Id) 
;
Create Index IX_Relationship287 ON GroupFilterExclusion (FilterId)
;
Alter table GroupFilterExclusion add Constraint Relationship287 foreign key (FilterId) references Filter (Id) 
;
Create Index IX_Relationship289 ON FilterForCategory (FilterId)
;
Alter table FilterForCategory add Constraint Relationship289 foreign key (FilterId) references Filter (Id) 
;
Create Index IX_Filter_FAAV_FK ON FilterAssetAttributeValue (FilterId)
;
Alter table FilterAssetAttributeValue add Constraint Filter_FAAV_FK foreign key (FilterId) references Filter (Id) 
;
Create Index IX_Filter_FLAV_FK ON FilterListAttributeValue (FilterId)
;
Alter table FilterListAttributeValue add Constraint Filter_FLAV_FK foreign key (FilterId) references Filter (Id) 
;
Create Index IX_Relationship1662 ON UserInMarketingGroup (MarketingGroupId)
;
Alter table UserInMarketingGroup add Constraint Relationship1662 foreign key (MarketingGroupId) references MarketingGroup (Id) 
;
Create Index IX_Relationship196 ON TranslatedMarketingGroup (MarketingGroupId)
;
Alter table TranslatedMarketingGroup add Constraint Relationship196 foreign key (MarketingGroupId) references MarketingGroup (Id) 
;
Create Index IX_Relationship190222 ON CustomField (UsageTypeId)
;
Alter table CustomField add Constraint Relationship190222 foreign key (UsageTypeId) references CustomFieldUsageType (Id) 
;
Create Index IX_Relationship191222 ON CustomFieldValue (CustomFieldId)
;
Alter table CustomFieldValue add Constraint Relationship191222 foreign key (CustomFieldId) references CustomField (Id) 
;
Create Index IX_Relationship201222 ON CustomFieldValueMapping (CustomFieldId)
;
Alter table CustomFieldValueMapping add Constraint Relationship201222 foreign key (CustomFieldId) references CustomField (Id) 
;
Create Index IX_Relationship232 ON TranslatedCustomField (CustomFieldId)
;
Alter table TranslatedCustomField add Constraint Relationship232 foreign key (CustomFieldId) references CustomField (Id) 
;
Create Index IX_Relationship187222 ON CustomField (TypeId)
;
Alter table CustomField add Constraint Relationship187222 foreign key (TypeId) references CustomFieldType (Id) 
;
Create Index IX_Relationship200222 ON CustomFieldValueMapping (ListValue)
;
Alter table CustomFieldValueMapping add Constraint Relationship200222 foreign key (ListValue) references CustomFieldValue (Id) 
;
Create Index IX_Relationship198 ON EmailTemplate (TypeId)
;
Alter table EmailTemplate add Constraint Relationship198 foreign key (TypeId) references EmailTemplateType (Id) 
;
Create Index IX_Relationship2162 ON AssetInMarketingEmail (MarketingEmailId)
;
Alter table AssetInMarketingEmail add Constraint Relationship2162 foreign key (MarketingEmailId) references MarketingEmail (Id) 
;
Create Index IX_Relationship215 ON TranslatedMarketingEmail (MarketingEmailId)
;
Alter table TranslatedMarketingEmail add Constraint Relationship215 foreign key (MarketingEmailId) references MarketingEmail (Id) 
;
Create Index IX_Relationship229 ON AssetChangeLog (LogTypeId)
;
Alter table AssetChangeLog add Constraint Relationship229 foreign key (LogTypeId) references LogType (Id) 
;
Create Index IX_Relationship237 ON AssetUse (SessionLogId)
;
Alter table AssetUse add Constraint Relationship237 foreign key (SessionLogId) references SessionLog (Id) 
;
Create Index IX_Relationship238 ON AssetView (SessionLogId)
;
Alter table AssetView add Constraint Relationship238 foreign key (SessionLogId) references SessionLog (Id) 
;
Create Index IX_Relationship239 ON AssetChangeLog (SessionLogId)
;
Alter table AssetChangeLog add Constraint Relationship239 foreign key (SessionLogId) references SessionLog (Id) 
;
Create Index IX_AssetEntity_AER_2_FK ON AllowableEntityRelationship (AssetEntityId)
;
Alter table AllowableEntityRelationship add Constraint AssetEntity_AER_2_FK foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_Relationship23522 ON AssetEntityAttribute (AssetEntityId)
;
Alter table AssetEntityAttribute add Constraint Relationship23522 foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_Relationship23822 ON AllowableEntityAssetType (AssetEntityId)
;
Alter table AllowableEntityAssetType add Constraint Relationship23822 foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_Relationship2412 ON TranslatedAssetEntity (AssetEntityId)
;
Alter table TranslatedAssetEntity add Constraint Relationship2412 foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_Relationship256 ON Asset (AssetEntityId)
;
Alter table Asset add Constraint Relationship256 foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_AEDR_AE_Source_FK ON AssetEntityDepRule (SourceAssetEntityId)
;
Alter table AssetEntityDepRule add Constraint AEDR_AE_Source_FK foreign key (SourceAssetEntityId) references AssetEntity (Id) 
;
Create Index IX_AEDR_AE_Dep_FK ON AssetEntityDepRule (DependentAssetEntityId)
;
Alter table AssetEntityDepRule add Constraint AEDR_AE_Dep_FK foreign key (DependentAssetEntityId) references AssetEntity (Id) 
;
Create Index IX_InDAssEnt_AssEnt_FK ON InDesignAssetEntity (AssetEntityId)
;
Alter table InDesignAssetEntity add Constraint InDAssEnt_AssEnt_FK foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_AssetEntity_ERD_FK ON EntityRelationshipDescription (AssetEntityId)
;
Alter table EntityRelationshipDescription add Constraint AssetEntity_ERD_FK foreign key (AssetEntityId) references AssetEntity (Id) 
;
Create Index IX_AssetEntity_AER_FK ON AllowableEntityRelationship (RelatesToAssetEntityId)
;
Alter table AllowableEntityRelationship add Constraint AssetEntity_AER_FK foreign key (RelatesToAssetEntityId) references AssetEntity (Id) 
;
Create Index IX_RelationshipType_AER_FK ON AllowableEntityRelationship (RelationshipTypeId)
;
Alter table AllowableEntityRelationship add Constraint RelationshipType_AER_FK foreign key (RelationshipTypeId) references RelationshipType (Id) 
;
Create Index IX_Relationship255 ON RelatedAsset (RelationshipTypeId)
;
Alter table RelatedAsset add Constraint Relationship255 foreign key (RelationshipTypeId) references RelationshipType (Id) 
;
Create Index IX_RelationshipType_ERD_FK ON EntityRelationshipDescription (RelationshipTypeId)
;
Alter table EntityRelationshipDescription add Constraint RelationshipType_ERD_FK foreign key (RelationshipTypeId) references RelationshipType (Id) 
;
Create Index IX_Relationship258 ON Filter (FilterTypeId)
;
Alter table Filter add Constraint Relationship258 foreign key (FilterTypeId) references FilterType (Id) 
;
Create Index IX_Relationship260 ON FilterGroup (FilterTypeId)
;
Alter table FilterGroup add Constraint Relationship260 foreign key (FilterTypeId) references FilterType (Id) 
;
Create Index IX_Relationship262 ON FilterInGroup (GroupId)
;
Alter table FilterInGroup add Constraint Relationship262 foreign key (GroupId) references FilterGroup (Id) 
;
Create Index IX_Relationship266 ON ChangeAttributeValueDateRule (ActionOnAssetId)
;
Alter table ChangeAttributeValueDateRule add Constraint Relationship266 foreign key (ActionOnAssetId) references ActionOnAsset (Id) 
;
Create Index IX_LAV_AOA_FK ON ListAttributeValue (ActionOnAssetId)
;
Alter table ListAttributeValue add Constraint LAV_AOA_FK foreign key (ActionOnAssetId) references ActionOnAsset (Id) 
;
Create Index IX_Relationship269 ON TranslatedAgreement (AgreementId)
;
Alter table TranslatedAgreement add Constraint Relationship269 foreign key (AgreementId) references Agreement (Id) 
;
Create Index IX_Relationship272 ON AssetAgreement (AgreementId)
;
Alter table AssetAgreement add Constraint Relationship272 foreign key (AgreementId) references Agreement (Id) 
;
Create Index IX_Relationship274 ON OrgUnitForAgreement (AgreementId)
;
Alter table OrgUnitForAgreement add Constraint Relationship274 foreign key (AgreementId) references Agreement (Id) 
;
Create Index IX_Relationship2702 ON TranslatedNewsItem (NewsItemId)
;
Alter table TranslatedNewsItem add Constraint Relationship2702 foreign key (NewsItemId) references NewsItem (Id) 
;
Create Index IX_Relationship286 ON UsageTypeFormat (ConvertToColorSpaceId)
;
Alter table UsageTypeFormat add Constraint Relationship286 foreign key (ConvertToColorSpaceId) references ColorSpace (Id) 
;
Create Index IX_ALAV_LAV_FK ON AssetListAttributeValue (ListAttributeValueId)
;
Alter table AssetListAttributeValue add Constraint ALAV_LAV_FK foreign key (ListAttributeValueId) references ListAttributeValue (Id) 
;
Create Index IX_TLAV_LAV_FK ON TranslatedListAttributeValue (ListAttributeValueId)
;
Alter table TranslatedListAttributeValue add Constraint TLAV_LAV_FK foreign key (ListAttributeValueId) references ListAttributeValue (Id) 
;
Create Index IX_FLAV_LAV_FK ON FilterListAttributeValue (ListAttributeValueId)
;
Alter table FilterListAttributeValue add Constraint FLAV_LAV_FK foreign key (ListAttributeValueId) references ListAttributeValue (Id) 
;
Create Index IX_AttributeType_StorageT_FK ON AttributeType (AttributeStorageTypeId)
;
Alter table AttributeType add Constraint AttributeType_StorageT_FK foreign key (AttributeStorageTypeId) references AttributeStorageType (Id) 
;
Create Index IX_RepurposedSlideshow_RV_FK ON RepurposedSlideshow (RepurposedVersionId)
;
Alter table RepurposedSlideshow add Constraint RepurposedSlideshow_RV_FK foreign key (RepurposedVersionId) references RepurposedVersion (Id) 
;
Create Index IX_RepurposedAsset_RV_FK ON RepurposedAsset (RepurposedVersionId)
;
Alter table RepurposedAsset add Constraint RepurposedAsset_RV_FK foreign key (RepurposedVersionId) references RepurposedVersion (Id) 
;
Create Index IX_UTF_Mask_FK ON UsageTypeFormat (PresetMaskId)
;
Alter table UsageTypeFormat add Constraint UTF_Mask_FK foreign key (PresetMaskId) references Mask (Id) 
;
Create Index IX_UTF_PMColour_FK ON UsageTypeFormat (PresetMaskColourId)
;
Alter table UsageTypeFormat add Constraint UTF_PMColour_FK foreign key (PresetMaskColourId) references NamedColour (Id) 
;
Create Index IX_InDAsset_TemplInDAsset_FK ON InDesignAsset (TemplateAssetId)
;
Alter table InDesignAsset add Constraint InDAsset_TemplInDAsset_FK foreign key (TemplateAssetId) references InDesignAsset (AssetId) 
;
Create Index IX_InDPDFJob_InDAsset_FK ON InDesignPDFJob (AssetId)
;
Alter table InDesignPDFJob add Constraint InDPDFJob_InDAsset_FK foreign key (AssetId) references InDesignAsset (AssetId) 
;
Create Index IX_InDD_InDPQ_FK ON InDesignDocument (InDesignPDFQualityId)
;
Alter table InDesignDocument add Constraint InDD_InDPQ_FK foreign key (InDesignPDFQualityId) references InDesignPDFQuality (Id) 
;
Create Index IX_InDAsset_InDD_FK ON InDesignAsset (InDesignDocumentId)
;
Alter table InDesignAsset add Constraint InDAsset_InDD_FK foreign key (InDesignDocumentId) references InDesignDocument (Id) 
;
Create Index IX_AIInDD_InDD_FK ON AttributeInInDesignDocument (InDesignDocumentId)
;
Alter table AttributeInInDesignDocument add Constraint AIInDD_InDD_FK foreign key (InDesignDocumentId) references InDesignDocument (Id) 
;
Create Index IX_InDAssEnt_InDAssEntType_FK ON InDesignAssetEntity (InDesignAssetEntityTypeId)
;
Alter table InDesignAssetEntity add Constraint InDAssEnt_InDAssEntType_FK foreign key (InDesignAssetEntityTypeId) references InDesignAssetEntityType (Id) 
;
Create Index IX_AEDR_DRA_FK ON AssetEntityDepRule (DepRuleActionId)
;
Alter table AssetEntityDepRule add Constraint AEDR_DRA_FK foreign key (DepRuleActionId) references DepRuleAction (Id) 
;
Create Index IX_BatchRelNot_Message_FK ON BatchReleasedNotifications (MessageId)
;
Alter table BatchReleasedNotifications add Constraint BatchRelNot_Message_FK foreign key (MessageId) references Message (Id) 
;
Create Index IX_UABR_BatchRel_FK ON UserApprovesBatchRelease (BatchReleaseId)
;
Alter table UserApprovesBatchRelease add Constraint UABR_BatchRel_FK foreign key (BatchReleaseId) references BatchRelease (Id) 
;
Create Index IX_BatchRelNot_BatchRel_FK ON BatchReleasedNotifications (BatchReleaseId)
;
Alter table BatchReleasedNotifications add Constraint BatchRelNot_BatchRel_FK foreign key (BatchReleaseId) references BatchRelease (Id) 
;
Create Index IX_AIBR_BatchRel_FK ON AssetInBatchRelease (BatchReleaseId)
;
Alter table AssetInBatchRelease add Constraint AIBR_BatchRel_FK foreign key (BatchReleaseId) references BatchRelease (Id) 
;
Create Index IX_ERD_TERD_FK ON TranslatedERDescription (AssetEntityId,RelationshipTypeId)
;
Alter table TranslatedERDescription add Constraint ERD_TERD_FK foreign key (AssetEntityId,RelationshipTypeId) references EntityRelationshipDescription (AssetEntityId,RelationshipTypeId) 
;
Create Index IX_ContentPurpose_OUC_FK ON OrgUnitContent (ContentPurposeId)
;
Alter table OrgUnitContent add Constraint ContentPurpose_OUC_FK foreign key (ContentPurposeId) references ContentPurpose (Id) 
;
Create Index IX_DA_DAG_FK ON DisplayAttribute (DisplayAttributeGroupId)
;
Alter table DisplayAttribute add Constraint DA_DAG_FK foreign key (DisplayAttributeGroupId) references DisplayAttributeGroup (Id) 
;
Create Index IX_InDAsset_InDPDFStatus_FK ON InDesignAsset (PDFStatusId)
;
Alter table InDesignAsset add Constraint InDAsset_InDPDFStatus_FK foreign key (PDFStatusId) references InDesignPDFStatus (Id) 
;
Create Index IX_InDPDFJob_JobStatus_FK ON InDesignPDFJob (JobStatusId)
;
Alter table InDesignPDFJob add Constraint InDPDFJob_JobStatus_FK foreign key (JobStatusId) references InDesignPDFJobStatus (Id) 
;


-- Create Object Tables section


-- Create XMLType Tables section


-- Create Procedures section


-- Create Functions section


-- Create Views section


-- Create Sequences section


-- Create Triggers from referential integrity section


-- Create user Triggers section


-- Create Packages section


-- Create Synonyms section


-- Create Roles section


-- Roles Permissions section

/* Roles permissions */


-- User Permissions section

/* Users permissions */


-- Create Table comments section


-- Create Attribute comments section

Comment on column AssetEntityDepRule.DepRuleTrigger is 'Which part of the source triggers this dependency rule? ''F'' = File, ''A'' = Attributes'
;



CREATE SEQUENCE AssetSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE AttributeValueSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE AssetBoxSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE UsageTypeSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE UserSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE UserGroupSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE CategorySequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE AttributeSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE ABOrderSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE OrgUnitSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE UsageTypeFormatSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE ChangeAttributeRuleSeq START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE SendEmailDateRuleSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE ListSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE ListItemSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE DivisionSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE AddressSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE SubscriptionSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE SubscriptionModelSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE PriceBandSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE CategoryTypeSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE RoleSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;

CREATE SEQUENCE EmbeddedDataValueSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE EmbeddedDataTypeSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;
CREATE SEQUENCE MappingDirectionSequence START WITH 1000 INCREMENT BY 1 NOCACHE  NOCYCLE;

CREATE SEQUENCE EmailTemplateSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE LanguageSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE MarketingGroupSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE MarketingEmailSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE CustomFieldTypeSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE CustomFieldSequence START WITH 1004 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE CustomFieldUsageTypeSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE CustomFieldValueSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE ScheduledReportSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE SessionLogSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE AssetChangeLogSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE AssetEntitySequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE RepurposedAssetSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE NewsItemSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE AssetRatingSequence START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE AgreementSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE WorkflowInfoSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE WorkflowAuditSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE StorageDeviceSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE ListAttributeValueSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE MaskSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE NamedColourSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE AssetUseSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE ContentPurposeSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE InDesignDocumentSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE InDesignPDFJobSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
-- Reference data for the standard Asset bank --

-- Developers:
-- You should update only refdata.sql

-- Adding new reference data (in refdata.sql and changes.xml) rows to tables
-- that can by updated when users use the app, for example Attribute and
-- UserGroup, can be problematic.  The problem is that on MySQL the IDs for
-- user rows immediately follow the IDs from the reference data in
-- refdata.sql, and the refdata rows start with ID 1.  This leaves no IDs
-- reserved for later additions.  If we had a time machine we could make sure
-- that IDs started from e.g.  10000, leaving all low IDs reserved, but we
-- don't have one so we need another solution.

-- To work around this problem, all new rows added to refdata.sql and
-- changes.xml must use IDs high enough that user-created IDs are unlikely to
-- collide with them.  Start with 1000000000 (== 10**9) because this is the
-- largest power of 10 that will fit into the ID columns in all our databases
-- (the smallest ID columns are the SQL server ones, which are 32 bit signed
-- ints).

-- Reference data and settings --

-- correction for datatype
-- SQLSERVER ALTER TABLE Asset ALTER COLUMN FileSizeInBytes Bigint NOT NULL

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
INSERT INTO AssetDownloadType (Id, Description) VALUES (4, 'Export');
INSERT INTO AssetDownloadType (Id, Description) VALUES (5, 'Email');
INSERT INTO AssetDownloadType (Id, Description) VALUES (6, 'Repurpose');
INSERT INTO AssetDownloadType (Id, Description) VALUES (7, 'Generated from Brand Template');
-- SQLSERVER SET IDENTITY_INSERT AssetDownloadType OFF;


-- SQLSERVER SET IDENTITY_INSERT ApprovalStatus ON;
INSERT INTO ApprovalStatus (Id,Name) VALUES (1,'Pending Approval');
INSERT INTO ApprovalStatus (Id,Name) VALUES (2,'Approved');
INSERT INTO ApprovalStatus (Id,Name) VALUES (3,'Rejected');
INSERT INTO ApprovalStatus (Id,Name) VALUES (4,'Approved for high res');
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
INSERT INTO SystemSetting (Id,Value) VALUES ('LastSavedSearchAlert','0');
INSERT INTO SystemSetting (Id,Value) VALUES ('ExternalFilterAttributeMapping','');
INSERT INTO SystemSetting (Id,Value) VALUES ('ExternalFilterAttributeValueMapping','');
INSERT INTO SystemSetting (Id,Value) VALUES ('ExternalFilterExcludedAssetTypes','');


-- SQLSERVER SET IDENTITY_INSERT StorageDevice ON;
INSERT INTO StorageDevice (Id,Name,StorageTypeId,Path,IsRelative,IsLocked,SequenceNumber,FactoryClassName) VALUES (1,'Default',5,'files',1,0,1,'com.bright.framework.storage.service.FileSystemStorageDeviceFactory');
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT StorageDevice OFF;

-- Formats supported --

-- SQLSERVER SET IDENTITY_INSERT FileFormat ON;
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (1,'JPEG','JPEG - Presentations or Web','jpg',2,0,1,1,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (2,'JPEG','JPEG - Presentations or Web','jpeg',2,0,1,0,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (3,'JPEG','JPEG - Presentations or Web','jpe',2,0,1,0,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (4,'GIF','GIF - Diagrams or Web','gif',2,0,1,1,null,'image/gif');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (5,'PNG','PNG - Web','png',2,0,1,1,null,'image/png');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConvertIndividualLayers) VALUES (6,'TIFF','TIFF - Print','tif',2,0,1,1,null,'image/tiff',1);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConvertIndividualLayers) VALUES (7,'TIFF','TIFF - Print','tiff',2,0,1,0,null,'image/tiff',1);
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
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(17,'DNG','Adobe Digital Negative','dng',2,0,1,0,null,null,'com.bright.assetbank.converter.RawToImageConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES   (18,'PCD','Kodak Photo CD','pcd',2,0,1,0,null,null,null);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES 					(19,'JPEG 2000','JPEG2000','jp2',2,0,1,0,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES 					(20,'JPEG 2000','JPEG2000','j2k',2,0,1,0,null,'image/jpeg');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(21,'DCR','Kodak Digital Camera Raw Image File','dcr',2,0,1,0,null,null,'com.bright.assetbank.converter.RawToImageConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES 	(22,'RAF','FUJI photo RAW File','raf',2,0,1,0,null,null,'com.bright.assetbank.converter.RawToImageConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES 					(23,'WMF','WMF - Windows MetaFile','wmf',2,0,1,0,null,null);

-- The contents of PDF and Word documents are indexed by default. This may slow down searches, if you have very large documents. Set IsIndexable=0 to switch off.
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (100,'DOC','Word documents','doc',1,1,0,0,'thumbnails/word_icon.gif','application/msword');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (101,'TEXT','Text documents','txt',1,0,0,0,'thumbnails/text_icon.gif','text/plain');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (102,'XLS','Excel spreadsheets','xls',1,0,0,0,'thumbnails/excel_icon.gif','application/vnd.ms-excel');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ToTextConverterClass, ConverterClass) VALUES (103,'PPT','Powerpoint presentations','ppt',1,1,0,0,'thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint', 'com.bright.assetbank.converter.PptToTextConverter', 'com.bright.assetbank.converter.PptToJpgConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (104,'ZIP','Zip archives','zip',1,0,0,0,'thumbnails/zip_icon.gif','application/zip');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (105,'RTF','RTF documents','rtf',1,0,0,0,'thumbnails/rtf_icon.gif','text/rtf');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (106,'EXT','Excel templates','xlt',1,0,0,0,'thumbnails/excel_icon.gif','application/vnd.ms-excel');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ToTextConverterClass, ConvertIndividualLayers) VALUES (107,'PDF','Adobe PDF','pdf',2,1,1,0,'thumbnails/pdf_icon.gif','application/pdf','com.bright.assetbank.converter.PDFToTextConverter',1);
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (108,'HTML','Text - HTML','html',1,0,0,0,'thumbnails/html_icon.gif','text/html');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (109,'MHT','Web archive - MHT','mht',1,0,0,0,null,'application/mht');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (200,'WAV','Audio - WAV format','wav',4,0,0,0,'thumbnails/audio_icon.gif','audio/x-wav');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ToTextConverterClass) VALUES (201,'DOCX','Word 2007 documents','docx',1,1,0,0,'thumbnails/word_icon.gif','application/msword','com.bright.assetbank.converter.DOCXToTextConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (202,'XLS','Excel 2007 spreadsheets','xlsx',1,0,0,0,'thumbnails/excel_icon.gif','application/vnd.ms-excel');

-- The following line doesn't include the converter because Aspose.Slides v2.2.0 (which can handle pptx files) has a problem running in headless mode. When 2.2.0 is used and this issue is fixed swap these 2 lines...
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType) VALUES (203,'PPT','Powerpoint 2007 presentations','pptx',1,0,0,0,'thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint');
-- INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES (203,'PPT','Powerpoint 2007 presentations','pptx',1,0,0,0,'thumbnails/powerpoint_icon.gif','application/vnd.ms-powerpoint', 'com.bright.assetbank.converter.PptxToJpgConverter');

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
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass, ViewFileInclude) VALUES (309,'FLV','Video - FLV Format','flv',3,0,1,1,null,'video/x-flv','com.bright.assetbank.converter.VideoToPngConverter','../inc/preview_flv.jsp');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass) VALUES (311,'F4V','Video - F4V Format','f4v',3,0,1,1,null,'video/mp4','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (310,'DV','Video - DV Format','dv',3,0,1,0,null,'video/x-dv','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (500,'M4V','Video - M4V format','m4v',3,0,1,1,null,'video/x-m4v','com.bright.assetbank.converter.VideoToPngConverter');
INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType,ConverterClass) VALUES (600,'M2V','Video - M2V format','m2v',3,0,1,1,null,'video/mpeg','com.bright.assetbank.converter.VideoToPngConverter');

INSERT INTO FileFormat (Id, Name, Description, FileExtension, AssetTypeId, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ViewFileInclude) VALUES (400,'SWF','Adobe Shockwave/Flash','swf',1,0,0,0,'thumbnails/flash_icon.gif','application/x-shockwave-flash','loadDirectlyInBrowser');

-- Uncomment the following if you want to create thumbnails from Flash files. Requires SwfToImage to be installed.
-- UPDATE FileFormat SET AssetTypeId=2, IsConvertable=1, ThumbnailFileLocation=null, ConverterClass='com.bright.assetbank.converter.SwfToImageConverter' WHERE Id=400;

-- SQLSERVER SET IDENTITY_INSERT FileFormat OFF;


-- Attribute specifications --

-- Attribute storage types
-- SQLSERVER SET IDENTITY_INSERT AttributeStorageType ON;
INSERT INTO AttributeStorageType (Id, Name) VALUES (1, 'Value per Asset');
INSERT INTO AttributeStorageType (Id, Name) VALUES (2, 'Lookup Table');
INSERT INTO AttributeStorageType (Id, Name) VALUES (3, 'Stored as Categories');
INSERT INTO AttributeStorageType (Id, Name) VALUES (100, 'Not Stored');
-- SQLSERVER SET IDENTITY_INSERT AttributeStorageType OFF;

-- Attribute types
-- SQLSERVER SET IDENTITY_INSERT AttributeType ON;
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (14,'Text field',1,7);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (1, 'Text field (long)',1,10);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (15,'Text area',1,17);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (2, 'Text area (long)',1,20);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (3, 'Datepicker',1,30);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (4, 'Dropdown',2,40);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (5, 'Checklist',2,50);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (6, 'Optionlist',2,60);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (7, 'Keyword picker',3,70);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (8, 'Datetime',1,80);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (9, 'Hyperlink',1,90);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (10,'Group header',100,100);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (11,'Autoincrement',1,110);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (12,'External dictionary',1,120);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (13,'Data lookup button',100,130);
INSERT INTO AttributeType (Id,Name,AttributeStorageTypeId,SequenceNumber) VALUES (16,'Numeric',1,140);
-- SQLSERVER SET IDENTITY_INSERT AttributeType OFF;


-- Static attributes
-- SQLSERVER SET IDENTITY_INSERT Attribute ON;
-- Note that if you change/add to the static attributes you need to update AttributeValueManager.populateStaticAttributeValueFromAsset()
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField) VALUES (1, 1, 1, 'file', 'File', 1, 0);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (2, 2, 1, 'assetId', 'ID', 0, 1, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (6, 6, 1, 'originalFilename', 'Original Filename', 0, 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (8, 8, 8, 1, 'dateAdded', 'Date Added', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (9, 9, 8, 1, 'dateLastModified', 'Date Last Modified', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (10, 10, 1, 'addedBy', 'Added By', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (11, 11, 1, 'lastModifiedBy', 'Last Modified By', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (12, 12, 1, 'size', 'Size', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (13, 13, 1, 'orientation', 'Orientation', 0, 1, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField) VALUES (14, 14, 1, 'price', 'Price', 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (15, 22, 1, 'embeddedData', 'Embedded Data', 0, 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (16, 23, 1, 'usage', 'Asset Usage', 0, 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (17, 24, 1, 'categories', 'Categories', 1, 1, 1, 0);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (18, 25, 1, 'accessLevels', 'Access Levels', 0, 1, 1, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (19, 26, 1, 'version', 'Version', 0, 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsSearchBuilderField, IsReadOnly) VALUES (200, 27, 1, 'audit', 'Audit Logging', 0, 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField) VALUES (300, 28, 1, 'sensitive', 'Is Sensitive?', 0, 0);
-- Sensitivity notes is now a flexible attribute (so it's moved to the next section)
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField) VALUES (400, 30, 1, 'agreements', 'Agreement Type', 0, 0);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (600, 31, 1, 'rating', 'Average Rating', 0, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, IsStatic, StaticFieldName, Label, IsMandatory, IsSearchField, IsReadOnly) VALUES (700, 32, 8, 1, 'dateLastDownloaded', 'Date Last Downloaded', 0, 0, 1);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)

-- Flexible attributes
-- Note that if you change the flexible attributes you must also update AssetAttributeValues and TranslatedAssetAttributeValues in the data model
INSERT INTO Attribute (Id, AttributeTypeId, SequenceNumber, IsStatic, Label, ValueColumnName, IsMandatory, IsSearchField, NameAttribute, IsKeywordSearchable) VALUES (3, 14, 3, 0, 'Title', 'title', 1, 0, 1, 1);
INSERT INTO Attribute (Id, AttributeTypeId, SequenceNumber, IsStatic, Label, ValueColumnName, IsMandatory, IsSearchField, DefaultValue, IsKeywordSearchable, Height, MaxSize) VALUES (4, 15, 4, 0, 'Description', 'description', 1, 0,'', 1, 4, 2000);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, ValueColumnName, IsKeywordSearchable, IsSearchField, IsMandatory, Height, MaxSize) VALUES (5, 5, 15, 'Keywords', 'keywords', 1, 0, 0, 4, 2000);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, IsStatic, ValueColumnName, Label, IsKeywordSearchable, IsSearchField, IsMandatory) VALUES (7, 7, 8, 0, 'datecreated', 'Date Created', 0,0,0);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, ValueColumnName, IsKeywordSearchable, IsSearchField, IsMandatory,DefaultValue,ValueIfNotVisible) VALUES (21, 16, 4, 'Active Status', NULL, 0, 1, 1, 'Active', 'Active');
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, ValueColumnName, IsKeywordSearchable, IsSearchField, IsSearchBuilderField, IsMandatory) VALUES (22, 17, 3, 'Activation Date', 'activationdate', 0,0,0,0);
INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, ValueColumnName, IsKeywordSearchable, IsSearchField, IsSearchBuilderField, IsMandatory) VALUES (23, 18, 3, 'Expiry Date', 'expirydate', 0,0,0,0);

INSERT INTO Attribute (Id, SequenceNumber, AttributeTypeId, Label, ValueColumnName, IsKeywordSearchable, IsSearchField, IsMandatory, IsExtendableList) VALUES (30, 19, 4, 'Usage Rights', NULL, 0, 1, 0, 1);
INSERT INTO Attribute (Id, SequenceNumber, IsStatic, Label, ValueColumnName, IsMandatory, IsSearchField, AttributeTypeId, Height, MaxSize) VALUES (301, 29, 0, 'Sensitivity Notes', 'sensitivitynotes', 0, 0, 15, 4, 2000);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT Attribute OFF;

-- Insert SystemSetting to stop the attribute value data migration from the old schema being performed
INSERT INTO SystemSetting (Id, Value) VALUES ('AttValsUpgraded', 'true');

-- SQLSERVER SET IDENTITY_INSERT ListAttributeValue ON;
INSERT INTO ListAttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (1,30,'External Use',1,1);
INSERT INTO ListAttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (2,30,'Internal Use',1,2);
INSERT INTO ListAttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (3,30,'Other... (please specify below)',1,3);
INSERT INTO ListAttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (6,21,'Inactive',1,1);
INSERT INTO ListAttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (7,21,'Active',1,2);
INSERT INTO ListAttributeValue (Id,AttributeId,Value,IsEditable,SequenceNumber) VALUES (8,21,'Expired',1,3);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT ListAttributeValue OFF;


-- Create attribute rules
-- SQLSERVER SET IDENTITY_INSERT ChangeAttributeValueDateRule ON;
INSERT INTO ChangeAttributeValueDateRule (Id, AttributeId, RuleName, ChangeAttributeId, NewAttributeValue, Enabled) VALUES (1,22,'Activate asset',21,'Active','1');
INSERT INTO ChangeAttributeValueDateRule (Id, AttributeId, RuleName, ChangeAttributeId, NewAttributeValue, Enabled) VALUES (2,23,'Expire asset',21,'Expired','1');
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT ChangeAttributeValueDateRule OFF;


-- Attribute Sort areas
-- SQLSERVER SET IDENTITY_INSERT SortArea ON;
INSERT INTO SortArea (Id, Name) VALUES (1, 'Search');
INSERT INTO SortArea (Id, Name) VALUES (2, 'Browse');
-- SQLSERVER SET IDENTITY_INSERT SortArea OFF;

INSERT INTO SortAttribute (AttributeId, SortAreaId, Sequence, Type, Reverse) VALUES (3, 2, 1, 3, 0);

-- SQLSERVER SET IDENTITY_INSERT DisplayAttributeGroup ON;
INSERT INTO DisplayAttributeGroup (Id, Name) VALUES (1, 'Search');
INSERT INTO DisplayAttributeGroup (Id, Name) VALUES (2, 'Browse');
INSERT INTO DisplayAttributeGroup (Id, Name) VALUES (3, 'Lightbox');
INSERT INTO DisplayAttributeGroup (Id, Name) VALUES (4, 'Category Extensions');
INSERT INTO DisplayAttributeGroup (Id, Name) VALUES (5, 'Browse List View');
-- SQLSERVER SET IDENTITY_INSERT DisplayAttributeGroup OFF;


-- Which attributes are display attributes
INSERT INTO DisplayAttribute (DisplayAttributeGroupId, AttributeId, SequenceNumber, ShowLabel) VALUES (1, 2, 1, 1);
INSERT INTO DisplayAttribute (DisplayAttributeGroupId, AttributeId, SequenceNumber, ShowLabel) VALUES (1, 3, 2, 0);
INSERT INTO DisplayAttribute (DisplayAttributeGroupId, AttributeId, SequenceNumber, ShowLabel) VALUES (2, 2, 1, 1);
INSERT INTO DisplayAttribute (DisplayAttributeGroupId, AttributeId, SequenceNumber, ShowLabel) VALUES (2, 3, 2, 0);
INSERT INTO DisplayAttribute (DisplayAttributeGroupId, AttributeId, SequenceNumber, ShowLabel) VALUES (3, 2, 1, 1);
INSERT INTO DisplayAttribute (DisplayAttributeGroupId, AttributeId, SequenceNumber, ShowLabel) VALUES (3, 3, 2, 0);

-- Groups, users and permissions --

-- Universal access level
-- SQLSERVER SET IDENTITY_INSERT CM_Category ON;
INSERT INTO CM_Category (Id,CategoryTypeId,Name,IsBrowsable,SequenceNumber, CannotBeDeleted, IsRestrictive, WorkflowName)
VALUES (1, 2, 'Universal', 1, 1, 1, 1, 'default');
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT CM_Category OFF;

-- Default groups
-- In standard we have two default groups, public and logged-in users
-- Permissions are applied to the logged-in users group

-- SQLSERVER SET IDENTITY_INSERT UserGroup ON;
INSERT INTO UserGroup (Id, Name, Description, IsDefaultGroup) VALUES (1,'*Logged-in Users', 'Determines the default permissions applied to logged in users', 0);
INSERT INTO UserGroup (Id, Name, Description, IsDefaultGroup, CanEmailAssets) VALUES (2,'*Public', 'Determines the default permissions applied to users who have not logged in', 1, 0);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
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
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,17,1);
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (1,18,0);

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
INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (2,17,0);

-- Exclusions
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,1,'Inactive');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,1,'Expired');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,2,'Inactive');
INSERT INTO GroupAttributeExclusion (AttributeId,UserGroupId,Value) VALUES (21,2,'Expired');

-- Default users
-- SQLSERVER SET IDENTITY_INSERT AssetBankUser ON;
INSERT INTO AssetBankUser (Id,Username,Password,Forename,Surname,EmailAddress,IsAdmin,ReceiveAlerts,LanguageId) VALUES (1,'admin','password','Admin','User','',1,1,1);
UPDATE AssetBankUser SET NotActiveDirectory=1, NotApproved=0, Hidden=0;
INSERT INTO AssetBankUser (Id,Username,Password,Forename,Surname,EmailAddress,IsAdmin,Hidden,NotActiveDirectory,LanguageId) VALUES (3,'application','f5uyu9Ra','Application','User','',1,1,1,1);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT AssetBankUser OFF;

-- SQLSERVER SET IDENTITY_INSERT AssetBox ON;
INSERT INTO AssetBox (Id,UserId,Name,SequenceNumber) VALUES (1,1,'My Lightbox',1);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT AssetBox OFF;

-- ColorSpace
-- SQLSERVER SET IDENTITY_INSERT ColorSpace ON;
INSERT INTO ColorSpace(Id,Description,ShowOnDownload,FileLocation) VALUES (1, 'RGB', 1, 'WEB-INF/manager-config/color-profiles/RGB/sRGB.icm');
INSERT INTO ColorSpace(Id,Description,ShowOnDownload,FileLocation) VALUES (2, 'CMYK', 0, 'WEB-INF/manager-config/color-profiles/CMYK/USWebCoatedSWOP.icc');
INSERT INTO ColorSpace(Id,Description,ShowOnDownload,FileLocation) VALUES (3, 'Greyscale', 0, 'WEB-INF/manager-config/color-profiles/greyscale/ISOcoated_v2_grey1c_bas.icc');
-- SQLSERVER SET IDENTITY_INSERT ColorSpace OFF;

-- Usage types and formats --

-- SQLSERVER SET IDENTITY_INSERT UsageType ON;
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId, CanEnterDetails, DetailsMandatory) VALUES (1,'Other',99999,3,1,1);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (2,'Word',1,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (3,'PowerPoint',2,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId) VALUES (4,'Website',3,3);
INSERT INTO UsageType (Id,Description,SequenceNumber,DownloadTabId, DownloadOriginal) VALUES (5,'High Res',4,3,1);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT UsageType OFF;


-- SQLSERVER SET IDENTITY_INSERT UsageTypeFormat ON;
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (1, 1, NULL, 'Small', 240,240);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (2, 1, NULL, 'Medium', 500,500);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight) VALUES (3, 1, NULL, 'Large', 1024,1024);

INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip) VALUES (4, 1, 2, 'Small', 400,400, 'gif', 1);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip) VALUES (5, 1, 2, 'Large', 800,800, 'gif', 1);

INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip) VALUES (6, 1, 3, 'Small', 200,200, 'gif', 1);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip) VALUES (7, 1, 3, 'Large', 600,600, 'gif', 1);

INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip, ConvertToColorSpaceId) VALUES (8, 1, 4, 'Small', 200,200, 'gif', 1, 1);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip, ConvertToColorSpaceId) VALUES (9, 1, 4, 'Medium', 400,400, 'gif', 1, 1);
INSERT INTO UsageTypeFormat (Id, FormatId, UsageTypeId, Description, ImageWidth, ImageHeight, PreserveFormatList, ApplyStrip, ConvertToColorSpaceId) VALUES (10, 1, 4, 'Large', 800,800, 'gif', 1, 1);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)

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
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
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
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup) VALUES (16, 'titles', 'Browser Titles', 'Copy for browser titles', 1, 1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew) VALUES (17, 'technical', 'Technical content', 'Content with a technical purpose',1);
INSERT INTO List (Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup) VALUES (18, 'feedback_subject', 'Feedback Subject', 'List items to be used for the feedback subject',0,1);
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
INSERT INTO EmbeddedDataType (Id, Name) VALUES (4, 'Photoshop');
INSERT INTO EmbeddedDataType (Id, Name) VALUES (5, 'File');
INSERT INTO EmbeddedDataType (Id, Name) VALUES (6, '[Any]');
INSERT INTO EmbeddedDataType (Id, Name) VALUES (7, '[Other]');
-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataType OFF;

-- Embedded data values
-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataValue ON;

-- EXIF data:
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

-- IPTC:
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (80, 2, 'Object Type Reference', 'ObjectTypeReference');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (81, 2, 'Object Name (Title)', 'ObjectName');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (82, 2, 'Edit Status', 'EditStatus');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (83, 2, 'Editorial Update', 'EditorialUpdate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (84, 2, 'Urgency', 'Urgency');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (85, 2, 'Subject Reference', 'SubjectReference');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (86, 2, 'Category', 'Category');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (87, 2, 'Supplemental Categories', 'SupplementalCategories');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (88, 2, 'Fixture Identifier', 'FixtureIdentifier');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (89, 2, 'Keywords', 'Keywords');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (90, 2, 'Content Location Code', 'ContentLocationCode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (91, 2, 'Content Location Name', 'ContentLocationName');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (92, 2, 'Release Date', 'ReleaseDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (93, 2, 'Release Time', 'ReleaseTime');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (94, 2, 'Expiration Date', 'ExpirationDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (95, 2, 'Expiration Time', 'ExpirationTime');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (96, 2, 'SpecialInstructions', 'SpecialInstructions');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (97, 2, 'Action Advised', 'ActionAdvised');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (98, 2, 'Reference Service', 'ReferenceService');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (99, 2, 'Reference Date', 'ReferenceDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (100, 2, 'Reference Number', 'ReferenceNumber');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (101, 2, 'Date Created', 'DateCreated');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (102, 2, 'Time Created', 'TimeCreated');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (103, 2, 'Digital Creation Date', 'DigitalCreationDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (104, 2, 'Digital Creation Time', 'DigitalCreationTime');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (105, 2, 'Originating Program', 'OriginatingProgram');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (106, 2, 'Program Version', 'ProgramVersion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (107, 2, 'Object Cycle', 'ObjectCycle');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (108, 2, 'By-line', 'By-line');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (109, 2, 'By-Line Title', 'By-LineTitle');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (110, 2, 'City', 'cITY');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (111, 2, 'Sub-Location', 'Sub-Location');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (112, 2, 'Province/State', 'Province-State');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (113, 2, 'Country/Primary Location Code', 'Country-PrimaryLocationCode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (114, 2, 'Country/Primary Location Name', 'Country-PrimaryLocationName');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (115, 2, 'Original Transmission Reference', 'OriginalTransmissionReference');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (116, 2, 'Headline', 'Headline');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (117, 2, 'Credit', 'Credit');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (118, 2, 'Source', 'Source');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (119, 2, 'Copyright Notice', 'CopyrightNotice');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (120, 2, 'Contact', 'Contact');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (121, 2, 'Caption/Abstract', 'Caption-Abstract');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (122, 2, 'Writer-Editor', 'Writer-Editor');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (123, 2, 'Rasterized Caption', 'RasterizedCaption');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (124, 2, 'Image Type', 'ImageType');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (125, 2, 'Image Orientation', 'ImageOrientation');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (126, 2, 'Language Identifier', 'LanguageIdentifier');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (127, 2, 'Audio Type', 'AudioType');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (128, 2, 'Audio Sampling Rate', 'AudioSamplingRate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (129, 2, 'Audio Sampling Resolution', 'AudioSamplingResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (130, 2, 'Audio Duration', 'AudioDuration');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (131, 2, 'Audio Outcue', 'AudioOutcue');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (132, 2, 'ObjectData Preview File Format', 'ObjectDataPreviewFileFormat');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (133, 2, 'ObjectData Preview File Format Version', 'ObjectDataPreviewFileFormatVersion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (134, 2, 'ObjectData Preview Data', 'ObjectDataPreviewData');

-- XMP
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (200, 3, 'Format', 'Format');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (201, 3, 'Title', 'Title');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (202, 3, 'Creator', 'Creator');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (203, 3, 'Description', 'Description');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (204, 3, 'Subject', 'Subject');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (205, 3, 'Rights', 'Rights');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (206, 3, 'CreatorTool', 'CreatorTool');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (207, 3, 'CreateDate', 'CreateDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (208, 3, 'ModifyDate', 'ModifyDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (209, 3, 'MetadataDate', 'MetadataDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (210, 3, 'DocumentID', 'DocumentID');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (211, 3, 'InstanceID', 'InstanceID');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (212, 3, 'ColorMode', 'ColorMode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (213, 3, 'ICCProfileName', 'ICCProfileName');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (214, 3, 'AuthorsPosition', 'AuthorsPosition');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (215, 3, 'CaptionWriter', 'CaptionWriter');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (216, 3, 'Category', 'Category');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (217, 3, 'Headline', 'Headline');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (218, 3, 'DateCreated', 'DateCreated');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (219, 3, 'City', 'City');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (220, 3, 'State', 'State');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (221, 3, 'Country', 'Country');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (222, 3, 'TransmissionReference', 'TransmissionReference');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (223, 3, 'Instructions', 'Instructions');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (224, 3, 'Credit', 'Credit');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (225, 3, 'Source', 'Source');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (226, 3, 'SupplementalCategories', 'SupplementalCategories');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (227, 3, 'History', 'History');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (228, 3, 'Marked', 'Marked');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (229, 3, 'WebStatement', 'WebStatement');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (230, 3, 'UsageTerms', 'UsageTerms');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (231, 3, 'IntellectualGenre', 'IntellectualGenre');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (232, 3, 'Location', 'Location');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (233, 3, 'CountryCode', 'CountryCode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (234, 3, 'CreatorContactInfoCiAdrExtadr', 'CreatorContactInfoCiAdrExtadr');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (235, 3, 'CreatorContactInfoCiAdrCity', 'CreatorContactInfoCiAdrCity');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (236, 3, 'CreatorContactInfoCiAdrRegion', 'CreatorContactInfoCiAdrRegion');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (237, 3, 'CreatorContactInfoCiAdrPcode', 'CreatorContactInfoCiAdrPcode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (238, 3, 'CreatorContactInfoCiAdrCtry', 'CreatorContactInfoCiAdrCtry');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (239, 3, 'CreatorContactInfoCiTelWork', 'CreatorContactInfoCiTelWork');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (240, 3, 'CreatorContactInfoCiEmailWork', 'CreatorContactInfoCiEmailWork');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (241, 3, 'CreatorContactInfoCiUrlWork', 'CreatorContactInfoCiUrlWork');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (242, 3, 'SubjectCode', 'SubjectCode');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (243, 3, 'Scene', 'Scene');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (244, 3, 'Orientation', 'Orientation');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (245, 3, 'XResolution', 'XResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (246, 3, 'YResolution', 'YResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (247, 3, 'ResolutionUnit', 'ResolutionUnit');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (248, 3, 'NativeDigest', 'NativeDigest');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (249, 3, 'ExifImageWidth', 'ExifImageWidth');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (250, 3, 'ExifImageHeight', 'ExifImageHeight');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (251, 3, 'ColorSpace', 'ColorSpace');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (700, 3, 'Rating', 'Rating');

-- EXIF:GPS
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (850, 1, 'GPSAltitude', 'GPSAltitude');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (851, 1, 'GPSAltitudeRef', 'GPSAltitudeRef');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (852, 1, 'GPSLatitude', 'GPSLatitude');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (853, 1, 'GPSLatitudeRef', 'GPSLatitudeRef');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (854, 1, 'GPSLongitude', 'GPSLongitude');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (855, 1, 'GPSLongitudeRef', 'GPSLongitudeRef');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (856, 1, 'GPSMapDatum', 'GPSMapDatum');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (857, 1, 'GPSDateStamp', 'GPSDateStamp');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (858, 1, 'GPSTimeStamp', 'GPSTimeStamp');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (859, 1, 'GPSSatellites', 'GPSSatellites');


-- File:
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (300, 5, 'File Name', 'FileName');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (301, 5, 'File Size', 'FileSize');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (302, 5, 'File Modify Date/Time', 'FileModifyDate');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (303, 5, 'File Type', 'FileType');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (304, 5, 'MIME Type', 'MIMEType');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (305, 5, 'Exif Byte Order', 'ExifByteOrder');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (306, 5, 'Image Width', 'ImageWidth');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (307, 5, 'Image Height', 'ImageHeight');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (308, 5, 'Encoding Process', 'EncodingProcess');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (309, 5, 'Bits Per Sample', 'BitsPerSample');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (310, 5, 'Color Components', 'ColorComponents');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (311, 5, 'Y Cb Cr Sub Sampling', 'YCbCrSubSampling');

-- Photoshop
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (400, 4, 'DisplayedUnitsX', 'DisplayedUnitsX');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (401, 4, 'DisplayedUnitsY', 'DisplayedUnitsY');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (402, 4, 'GlobalAngle', 'GlobalAngle');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (403, 4, 'GlobalAltitude', 'GlobalAltitude');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (404, 4, 'CopyrightFlag', 'CopyrightFlag');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (405, 4, 'URL', 'URL');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (406, 4, 'PhotoshopQuality', 'PhotoshopQuality');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (407, 4, 'PhotoshopFormat', 'PhotoshopFormat');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (408, 4, 'ProgressiveScans', 'ProgressiveScans');

-- Any
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (500, 6, 'Keywords', 'Keywords');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (501, 6, 'XResolution', 'XResolution');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (502, 6, 'YResolution', 'YResolution');

-- Other
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (600, 7, 'MAX(width,height)', 'MAX(width,height)');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (601, 7, 'MIN(width,height)', 'MIN(width,height)');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (602, 7, 'Pixels (width*height)', 'width*height');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (603, 7, 'Auto-rotate from camera', 'Auto-rotate from camera');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (604, 7, 'GPSLatitude Signed', 'GPSLatitude Signed');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (605, 7, 'GPSLongitude Signed', 'GPSLongitude Signed');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (606, 7, 'GPSMapDatum Mapped', 'GPSMapDatum Mapped');
INSERT INTO EmbeddedDataValue (Id, EmbeddedDataTypeId, Name, Expression) VALUES (607, 7, 'Filename Extension', 'Filename Extension');

-- SQLSERVER SET IDENTITY_INSERT EmbeddedDataValue OFF;

-- SQLSERVER SET IDENTITY_INSERT ActionOnAsset ON;
INSERT INTO ActionOnAsset (Id,Name,Description,ActionClass) VALUES (1,'Restrict Asset','Restricts an asset, meaning that it cannot be downloaded by non-admin users and may have it''s preview hidden.','com.bright.assetbank.actiononasset.action.RestrictAssetAction');
INSERT INTO ActionOnAsset (Id,Name,Description,ActionClass) VALUES (2,'Unrestrict Asset','Unrestricts a restricted asset, which will make it visible to non-admin users (depending on other permissions).','com.bright.assetbank.actiononasset.action.UnrestrictAssetAction');
INSERT INTO ActionOnAsset (Id,Name,Description,ActionClass) VALUES (3,'Removed Repurposed Images','Removes any repurposed (embeddable) images for an asset.','com.bright.assetbank.actiononasset.action.RemoveRepurposedImagesAction');
INSERT INTO ActionOnAsset (Id,Name,Description,ActionClass) VALUES (4,'Mark as Sensitive','Marks an asset as sensitive.','com.bright.assetbank.actiononasset.action.MakeAssetSensitiveAction');
INSERT INTO ActionOnAsset (Id,Name,Description,ActionClass) VALUES (5,'Clear Sensitive Status','Clears the sensitite status for an asset.','com.bright.assetbank.actiononasset.action.MakeAssetInsensitiveAction');
-- SQLSERVER SET IDENTITY_INSERT ActionOnAsset OFF;

-- Setup the pre-configured embedded data mappings
INSERT INTO EmbeddedDataMapping (EmbeddedDataValueId, AttributeId, MappingDirectionId) VALUES (12, 7, 1);
INSERT INTO EmbeddedDataMapping (EmbeddedDataValueId, AttributeId, MappingDirectionId) VALUES (603, 13, 1);

-- SQLSERVER SET IDENTITY_INSERT EmailTemplateType ON;
INSERT INTO EmailTemplateType (Id,Name) VALUES (1,'General');
INSERT INTO EmailTemplateType (Id,Name) VALUES (2,'Marketing');
-- SQLSERVER SET IDENTITY_INSERT EmailTemplateType OFF;

-- Email Templates
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert approvals','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested approval on assets','#name# has requested approval to download a set of images.<br><br>Their email address: #email#<br><br>Please log in to Asset Bank and go to the Approve Images section to see the request and approve the images.<br><br>IDs of assets requested: #assetids#','admin_alert_approvals');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alertcommercial order submitted','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested approval for commercial use.','#greetname#(#username#) has placed an order (#ordernum#) requesting approval for commercial use of the following assets:<br><br>#orderDetails#<br><br>The following usage notes were provided by the user.<br>#UserNotes#<br><br>Please log in to Asset Bank and go to the Orders -> Commercial Orders section to review and approve the order.<br><br>#OfflinePaymentRequest#','admin_alert_commercial_order_submitted');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert personal print order submitted','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User has requested prints.','#greetname#(#username#) has placed an order (#ordernum#) requesting prints of the following assets:<br><br>#orderDetails#<br><br>Please log in to Asset Bank and go to the Orders -> Personal Orders section to review  the order.<br>When the order has been shipped you can change the status to &#39;Fulfilled&#39;.','admin_alert_personal_print_order_submitted');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert update request','noreply@noreply.com','#adminEmailAddresses#;','','','User has requested permissions','This is an automated message generated by Asset Bank.<br><br>A user has requested you to update their permissions.<br><br>Requested Org Unit: #orgunitname#<br><br>User notes: <br>#usernotes#<br><br>You can see the details of this request, in the &#39;Admin > Approve Users&#39; area.','admin_alert_update_request');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'admin alert upload approvals','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: items require approval','#name# has uploaded or updated items. The new or updated items require approval.<br><br>Please log in to Asset Bank and go to the Approve Items section to approve the changes.','admin_alert_upload_approvals');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'ad user approved','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','Dear #name#<br><br>You have been approved as a user of the Asset Bank. <br>Your login details are the same as you use to log in to your Windows PC.<br><br>Kind regards,<br>Asset Bank Admin','ad_user_approved');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'approver work done','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: User asset request has been approved','Download approvals for user #name# have been processed, for the following assets:<br>#assetids#','approver_work_done');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'assets approved','noreply@noreply.com','#email#;','','','Download request processed','Dear #name#<br><br>Your request to download one or more assets has been processed.<br><br>Please log in to the Asset Bank and go to &#39;My Folder&#39; for details.<br><br>Kind regards,<br>Asset Bank Admin','assets_approved');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'asset emailed (attached)','noreply@noreply.com','#recipients#;','','','Asset Bank: #filename#','This message was sent to you from #username# via Asset Bank.<br><br>#message#','asset_attached');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'asset emailed (linked)','#from#','#recipients#;','','','Asset Bank: #filename#','This message was sent to you from #username# via Asset Bank.<br /><br />#message#<br /><br /><br /> <a href="#url#">Click here to download</a>','asset_linked');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'attribute rule email','noreply@noreply.com','#recipients#;','','','Asset Bank Automatic Email','Dear Asset Bank user<br><br>This email has been automatically sent to you by Asset Bank because a date specified on one or more assets has been reached.<br><br>The asset attribute is: #rule_attribute#.<br>The rule that has triggered is: #rule_name#.<br>Message:<br>#rule_message#<br><br>Asset IDs:<br>#asset_list#<br><br>Regards,<br>Asset Bank Admin','attribute_rule_email');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'commercial option changed','noreply@noreply.com','#email#;','','','Assetbank: alteration to request for commercial use of image/s','Dear #username#<br><br>IMPORTANT INFORMATION ABOUT YOUR REQUEST FOR COMMERCIAL USE OF IMAGE/S<br><br>Your order number: #ordernum#<br>#assetDetails#<br><br>Having assessed your request, and from the additional information you have provided us, we have felt it necessary to change the commercial option you had initially selected from &#39;#oldOption#&#39;  to &#39;#newOption#&#39;. This has resulted in a change of pricefor the purchase of the image/s.<br><br>You can view the change of use by logging into Assetbank, and proceed to the MY PURCHASES - MY ORDERS section. <br><br>If you do not agree with our assessment of your intended use of the image/s, please contact us and we will be happy to discuss it further with you.<br><br>Your username is: #username#<br><br>Thank you for using Assetbank.co.uk.<br><br>Regards,<br>assetbank.com, admin','commercial_option_changed');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'contact','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank Contact','Name: #name#<br>Email: #email#<br>Tel no: #telephone#<br><br>Message:<br>#message#','contact');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'new watched files','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: New files uploaded','New files have been added to the the following directories: #directories#','new_watched_files');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'order approved pay offline','noreply@noreply.com','#email#;','','','Commercial purchase approved.  Offline Payment required.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your commercial purchase order (#ordernum#) placed on #datePlaced# has been approved and now requires off line payment.<br><br>You will be contacted shortly by a member of our admin team to complete payment.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and proceed to the &#39;My Purchases-> My Orders&#39; section to view your approved order.<br>Your username is: #username#<br><br>Your order details, commercial options approved and terms and conditions are <br><br>#orderDetails#<br><br>#terms# <br><br>Thank you for purchasing images from AssetBank.<br><br>Regards,<br>assetbank.com, admin','order_approved_pay_offline');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'orderapproved pay online','noreply@noreply.com','#email#;','','','Commercial purchase approved.  Online Payment required.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your commercial purchase order (#ordernum#) placed on #datePlaced# has been approved and now requires on line payment.<br><br>Please login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and proceed to the &#39;My Purchases-> My Orders&#39; section to view the order and complete payment.<br>Your username is: #username#<br><br>Your order details and commercial options approved are <br><br>#orderDetails#<br><br>#terms# <br><br>Thank you for purchasing images from AssetBank.<br><br>Regards,<br>assetbank.com, admin','order_approved_pay_online');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'order declined','noreply@noreply.com','#email#;','','','Commercial purchase declined.','Dear #greetname# (#username#)<br><br>Your commercial purchase order (#ordernum#) placed on #datePlaced# has been declined.<br><br>You may be contacted shortly by a member ofour admin team to discuss the issue with your request.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/ecommerce) and go to the &#39;My Purchases-> My Orders&#39; section to view the declined order.<br>Your username is: #username#<br><br>Your declined order details, commercial options and terms and conditions are <br><br>#orderDetails#<br><br>#terms# <br><br>Regards,<br>assetbank.com, admin','order_declined');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'order shipped confirmation','noreply@noreply.com','#email#;','','','Your order has been shipped.','Dear #greetname# (#username#)<br><br>This e-mail is to confirm that your order (#ordernum#) placed on #datePlaced# has been shipped to<br><br>#address#<br><br>Your order details are<br><br>#orderDetails#<br><br>Thank you for purchasing images from Asset Bank.<br><br>This is a test email from Asset Bank. The text of allemails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin','order_shipped_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'password change','noreply@noreply.com','#email#;','','','Your Asset Bank password has been reset','Dear #name#<br><br>Your password for the Asset Bank application has been changed. <br>When you next log in, please use password: #newPassword#<br><br>Please note that your username (#username#) has not been changed.<br><br>Kind regards,<br>Asset Bank Admin','password_change');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'password reminder','noreply@noreply.com','#email#;','','','Your Asset Bank password','Dear #name#<br><br>Here are your login details for Asset Bank.<br><br>Your username is: #username#<br>Your password: #password#<br><br>Regards,<br>Asset Bank Admin','password_reminder');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'purchase confirmation','noreply@noreply.com','#email#;','','','Asset Bank Payment Confirmation','Dear #greetname# (#username#)<br><br>This email confirms your purchase of assets from Asset Bank.<br>Order/Purchase ID: #ordernum#  <br><br>Your order/purchase details are<br>#orderDetails#<br><br>#purchaseCommercialReceiptNote#<br><br>#purchaseDownloadInstructions#<br>#purchasePrintInstructions#<br><br>Your username is: #username#<br><br>To view the terms and conditions of your purchase go to #purchaseTermsLink#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>AssetBank Admin','purchase_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'requestAssetbox','noreply@noreply.com','#adminEmailAddresses#;','','','Asset Bank: Items requested on CD','-- Contact details --<br>Name: #name#<br>Division: #division#<br>Email: #email#<br>#customFieldName#: #customField#<br>Address:<br>#address#<br>Additional comments:<br>#message#<br><br>-- Requested items --<br>The user has requested the following assets on CD: <br>Assets the user has permission to download: #downloadableIds#','requestAssetbox');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'subscription confirmation','noreply@noreply.com','#email#;','','','Asset Bank Subscription Confirmation','Dear #username#<br><br>This email confirms that you purchased the following subscription on Asset Bank.<br><br>Description: #description#<br>Amount: #amount#.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/) to download images. <br>Your username is: #username#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin','subscription_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'subscription and registration confirmation','noreply@noreply.com','#email#;','','','Asset Bank Subscription Confirmation','Dear #username#<br><br>This email confirms that you purchased the following subscription on Asset Bank.<br><br>Description: #description#<br>Amount: #amount#.<br><br>You can login to Asset Bank (http://www.assetbank.co.uk/) to download images. <br>Your login details are:<br>Your username: #username#<br>Your password: #password#<br><br>This is a test email from Asset Bank. The text of all emails sent to users is configurable.<br><br>Regards,<br>Asset Bank Admin','subscription_and_registration_confirmation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'subscription expiry alert','noreply@noreply.com','#email#;','','','Subscription expiry alert','Dear #name#<br><br>The following subscription to Asset Bank will expire in #days_left# day(s): <br>#description#<br><br>Your username is: #username#<br><br>Kind regards,<br>Asset Bank Admin','subscription_expiry_alert');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user added by admin','noreply@noreply.com','#email#;','','','You are now an Asset Bank user!','Dear #name#<br><br>You have been added as a user of Asset Bank. <br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br>You can change your password after logging in.<br><br>Kind regards,<br>Asset Bank Admin','user_added_by_admin');
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
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'repurposed image updated','noreply@noreply.com','#recipients#;',NULL,NULL,'Asset Bank: Please check embedded image','<p>Dear #name# (#username#)&nbsp;</p><p>On #date# you created an embeddable version of an image file in Asset Bank. This file has recently been replaced, and while the version you created is still usable, it may no longer be relevant. </p><p>Links are provided below in order that you can check that the version you created against the current image file in Asset Bank. If necessary, you can create a new embeddable version of the current image, in which case you will also have to update any external links using the previous embaddable version. </p><p>The embeddable image you created can be seen here:<br />#versionUrl#<br /></p><p>The current image can be seen here:<br />#assetUrl#<br /></p><p>Please contact an admin user if you have any queries regarding this email.</p><p>Kind regards,<br />Asset Bank Admin</p>','repurposed_image_updated');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'workflow asset approved','noreply@noreply.com','#emailAddresses#;','','','Asset Bank: asset approved','<p>Dear #name#<br /><br />The following asset you uploaded has been approved:</p><p>#assetDescription#</p><p>Kind regards,<br />Asset Bank Admin</p>','workflow_asset_approved');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'workflow asset rejected','noreply@noreply.com','#emailAddresses#;','','','Asset Bank: asset rejected','<p>Dear #name#<br /><br />The following asset you uploaded has been rejected. Please log in to Asset Bank and go to the Upload Submitted screen to review:</p><p>#assetDescription#</p><p>Message:<br/>#message#</p><p>Kind regards,<br />Asset Bank Admin</p>','workflow_asset_rejected');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'invitation to register','noreply@noreply.com','#email#;',NULL,NULL,'Asset Bank Invitation','<p>You have been invited to join Asset Bank. Please click the link below to register: </p><p>#url#</p><p>The user who invited you included this message: </p><p>#message#</p><p>Kind regards,<br />Asset Bank Admin</p>','invitation_to_register');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'saved search alert email','noreply@noreply.com','#recipients#;','','','New Assets in Saved Searches','Dear Asset Bank user<br><br>This email has been automatically sent to you by Asset Bank because the following searches that you have saved have new assets available:<br><br>#searches#<br><br>Regards,<br>Asset Bank Admin','saved_search_alert_email');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'additional user approval','noreply@noreply.com','#recipients#;','','','User requesting approval','Dear #name#<br><br>This email has been automatically sent to you by Asset Bank because the following user has registered and requested that you authorise them :<br><br>Forename: #forename#<br>Surname: #surname#<br>Email Address: #email#<br>Username: #username#<br><br>To approve this user please click the link below:<br>#approve#<br><br>To reject this user please click the link below:<br>#reject#<br><br>Regards,<br>Asset Bank Admin','additional_user_approval');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'email this page','noreply@noreply.com','','','','A page you might be interested in','Dear [recipient name],<br /><br />I thought you might be interested in the following page:<br /><br /><a href=\"#url#\">#link#</a><br /><br />Regards,<br />#name#','email_this_page');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user reactivation','noreply@noreply.com','','','','Your Asset Bank account will expire soon','Dear #name#,<br /><br />You have not logged into your Asset Bank account for #notLoggedInFor# days. Please click the link below to reactivate your account. If you have not reactivated your account within #reactivationPeriod# days it will be deleted.<br /><br /><a href=\"#url#\">Reactivate your account</a><br /><br />Regards,<br />Asset Bank Admin','user_reactivation');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (1,1,'user deleted by admin','imagebank@bright-interactive.com','#recipients#;',NULL,NULL,'Asset Bank: Your account has been deleted','Dear #greetname# (#username#)<br><br>Your account has been deleted. The admin user included this message:<br><br>#message#<br><br>Kind regards,<br>Asset Bank Admin','user_deleted_admin');
INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId, Hidden, IsEnabled) VALUES (1,1,'ecommerce user registered','noreply@noreply.com','#email#;','','','Your Asset Bank registration has been approved','Dear #name#<br><br>You have been approved as a user of Asset Bank. <br><br>Your login details are:<br>Username: #username#<br>Password: #password#<br><br>You can change your password after logging in: click &#39;Your Profile&#39; in the top right of the screen, then click &#39;change your password&#39;.<br><br>Kind regards,<br>Asset Bank Admin','ecommerce_user_registered',1,0);

-- SQLSERVER SET IDENTITY_INSERT CustomFieldUsageType ON;
INSERT INTO CustomFieldUsageType (Id, Description) VALUES (1, 'Asset Bank User Field');
-- SQLSERVER SET IDENTITY_INSERT CustomFieldUsageType OFF;


-- SQLSERVER SET IDENTITY_INSERT CustomFieldType ON;
INSERT INTO CustomFieldType (Id, Description) VALUES (1, 'Textfield');
INSERT INTO CustomFieldType (Id, Description) VALUES (2, 'Textarea');
INSERT INTO CustomFieldType (Id, Description) VALUES (3, 'Dropdown list');
INSERT INTO CustomFieldType (Id, Description) VALUES (4, 'Checkbox list');
-- SQLSERVER SET IDENTITY_INSERT CustomFieldType OFF;

-- SQLSERVER SET IDENTITY_INSERT CustomField ON;
INSERT INTO CustomField (Id, FieldName, UsageTypeId, TypeId, Required, OrgUnitId) VALUES (1001, 'Telephone',1,1,0,null);
INSERT INTO CustomField (Id, FieldName, UsageTypeId, TypeId, Required, OrgUnitId) VALUES (1002, 'Fax',1,1,0,null);
INSERT INTO CustomField (Id, FieldName, UsageTypeId, TypeId, Required, OrgUnitId) VALUES (1003, 'Website',1,1,0,null);
-- New rows must have IDs starting from 1000000000 (for explanation see comment near start of this file)
-- SQLSERVER SET IDENTITY_INSERT CustomField OFF;

-- SQLSERVER SET IDENTITY_INSERT LogType ON;
INSERT INTO LogType (Id, Name) VALUES (1, 'Modified');
INSERT INTO LogType (Id, Name) VALUES (2, 'Added');
INSERT INTO LogType (Id, Name) VALUES (3, 'Deleted');
INSERT INTO LogType (Id, Name) VALUES (4, 'Exported');
INSERT INTO LogType (Id, Name) VALUES (5, 'Status Change');
INSERT INTO LogType (Id, Name) VALUES (6, 'Agreement Change');
-- SQLSERVER SET IDENTITY_INSERT LogType OFF;

-- SQLSERVER SET IDENTITY_INSERT RelationshipType ON;
INSERT INTO RelationshipType (Id, Name) VALUES (1,'Peer');
INSERT INTO RelationshipType (Id, Name) VALUES (2,'Child');
-- SQLSERVER SET IDENTITY_INSERT RelationshipType OFF;

-- SQLSERVER SET IDENTITY_INSERT FilterType ON;
INSERT INTO FilterType(Id,Name) VALUES (1, 'Filter');
INSERT INTO FilterType(Id,Name) VALUES (2, 'Template');
-- SQLSERVER SET IDENTITY_INSERT FilterType OFF;

-- Content lists and default data for the standard Asset bank --
-- Note: Put ecommerce specific items in refdata_ecommerce_update

-- Application pages content
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'homepage-main', 1, 'Home Page Welcome text', 'The welcome text shown on the home page', '<h1>Welcome to Asset Bank</h1><p>Welcome to Asset Bank - a library of images and other digital assets.&nbsp; </p><p>You can find images and documents by entering a keyword into the Quick Search, performing an advanced search or by browsing the categories.</p><p>You can change the text that appears here by logging in as an admin user and going to&nbsp;the Content section in the Admin&nbsp;menu.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'homepage-not-logged-in', 1, 'Home Page not logged in text', 'The text shown on the home page for users that are not logged in', 'You are not currently logged in. To view the full catalogue of assets please <a href="viewLogin">login</a> or <a href="viewRegister">register</a>.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'homepage-message', 1, 'Home Page Message Board', 'The message board text on the homepage', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'conditions', 1, 'Terms and Conditions', 'The copy for the Terms and Conditions', '<h1 class="underline">Terms and Conditions</h1> <p>Before you download an asset, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style="color: #888888;">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'promoted-intro', 1, 'Promoted Images intro text', 'The intro text shown on the promoted items page', '<p>The items shown on this page are the promoted items.</p><p>Note to admin users: you can change this intro text from the admin area.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'usage-label', 1, 'Label for the usage dropdown', 'The text shown above the usage dropdown, for example on the download page', 'Please select your intended usage for this asset:', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'usage-selected-summary', 1, 'Selected usage summary', 'The text shown once a user has selected a usage, for example on the download page', 'You have selected usage:', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'contact-details', 1, 'Contact details', 'Contact detailed displayed on the Contact Us page', '<p>Note to admin users: your contact details can be added here via the admin area.</p><p>Please use the form below to send us your comments and suggestions.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'conditions-extra', 1, 'Terms and Conditions (Extra)', 'Extra Terms and conditions, if you need them.', '<p><span style="color: #888888;">(These extra terms and conditions can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'privacy', 1, 'Privacy Policy', 'Privacy Policy', '<p><span style="color: #888888;">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'format-help', 1, 'Image Format Help', 'Text for a pop-up that can be shown on the download page to explain the different format options.', '<h2>Image Format Help</h2><p>Sed tempor massa vitae risus. Nam pellentesque lorem ut dui. Etiam ut velit. Donec tincidunt ligula eu odio. Praesent tempus odio sit amet velit. Duis lacus. Nulla dictum. Fusce volutpat, turpis sed mollis vestibulum, massa leo sodales turpis, id volutpat lacus est ut est. In posuere nulla vitae nulla. Vestibulum molestie ornare turpis.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'download', 1, 'Download page copy', 'The copy shown on the download page.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'registration', 1, 'Registration form copy', 'The copy shown on the registration page.', '<p>Please complete the short form below to register for access to Asset Bank.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'homepage-no-featured-image', 1, 'Home Page featured image placeholder', 'The copy shown on the home page when there is no featured image set.', '<h3>Featured image</h3><p>Log in as an admin user and find the image you want. Click Edit, check &#39;Featured Image?&#39; and save.</p><p>To change this text go to the Content section in the Admin menu.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'content-page-1', 1, 'Extra Content', 'Extra content page.', '<h2>Content Managed Page</h2><p>Sed tempor massa vitae risus. Nam pellentesque lorem ut dui. </p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-no-results-copy', 1, 'Search no results text', 'Copy to show on search results when there are no matches.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'sso-enc-url-failed-copy', 1, 'Encrypted Url SSO Login Failed Copy', 'Encrypted Url SSO Login Failed Copy', '<p>Your login attempt failed, this could be because the link you used has expired. Please return to your referrer, refresh the page that you came from and try the link again.</p><p>If the link still fails, please contact <a href="mailto:?email?">?email?</a>.</p><p>Alternatively if you have credentials you can <a href="viewLogin">login directly</a>.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tsandcs-checkbox', 1, 'Ts and Cs checkbox text', 'The text shown to the right of the ts and cs checkbox on the download page', 'I have read and accept the <a href="../action/viewConditionsPopup" target="_blank" onClick="popupConditions(''../action/viewConditionsPopup'', 1); return false;" title="View Terms and Conditions in a new window">Terms and Conditions</a>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'login-page-copy', 1, 'Login page copy', 'Text to display on the login page.', '<p>Please enter your username and password to log in, or <a href="../action/viewRegisterUser">register here</a></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'register-success-intro', 1, 'Post registration message', 'Text to display when the user has regsitered.', 'Thank you for registering.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'register-success-without-approval', 1, 'Post registration next steps, no approval', 'Next steps copy when regsitration approval is not required.', 'Your account details have been emailed to you.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'register-success-with-approval', 1, 'Post registration next steps, approval required', 'Next steps copy when regsitration approval is required.', 'We will be in contact shortly with your account details when your request has been approved.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tsandcs-checkbox-upload', 1, 'Ts and Cs checkbox text (upload)', 'The text shown to the right of the ts and cs checkbox on the upload page', 'I have read and accept the <a href="../action/viewUploadConditionsPopup" target="_blank" onClick="popupConditions(''../action/viewUploadConditionsPopup'', 1); return false;" title="View Terms and Conditions in a new window">Terms and Conditions</a> of uploading.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'conditions-upload', 1, 'Terms and Conditions for uploading', 'The copy for the Terms and Conditions for uploading', '<p>This page shows the Terms and Conditions that you want users to read and agree to before they upload an image. </p><p>The rest of this page shows latin text as a place holder for your own Terms and Conditions. To change this text, log in an an admin user and go to Admin-&gt;Content. </p><p>Consequat dolore molestie veniam veniam consequat lorem vero. Laoreet hendrerit delenit magna in suscipit. </p><h2>Another header</h2><p>Commodo consequat in dolore, tincidunt exerci iusto eum amet wisi iusto nostrud dolore:</p><ul><li>Ad erat dolore qui, blandit sit sed veniam blandit. </li><li>Te ex vero, in consequat et augue dignissim at eu ullamcorper dignissim dolor veniam. </li><li>Iriure esse luptatum feugait vel esse eu, qui aliquip, praesent autem. </li><li>Enim praesent praesent in tation hendrerit nibh lobortis ut. </li></ul><h3>Another header</h3><p>Dolor suscipit, at velit commodo lobortis. In autem augue duis exerci nisl esse laoreet ex vero adipiscing aliquip ut vel vel aliquip.</p><h4>Another header</h4><p>Dolor suscipit, at velit commodo lobortis. In autem augue duis exerci nisl esse laoreet ex vero adipiscing aliquip ut vel vel aliquip.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'establishment-name', 1, 'Name of establishment field', 'The field name of the establishment/division/company displayed on the contact page', 'Establishment', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'request-user-permissions-update-intro', 1, 'Request user permissions update page intro text', 'Intro text for the request user permissions update page.', '<p>Use this form if you would like to make a request to have your access permissions updated.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tsandcs-image-checkbox', 1, 'Ts and Cs checkbox text for image downloads', 'The text shown to the right of the ts and cs checkbox on the download image page', 'I have read and accept the <a href="../action/viewImageConditionsPopup" target="_blank" onClick="popupConditions(''../action/viewImageConditionsPopup'', 1); return false;" title="View Terms and Conditions in a new window">Terms and Conditions</a>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tsandcs-video-checkbox', 1, 'Ts and Cs checkbox text for video downloads', 'The text shown to the right of the ts and cs checkbox on the download video page', 'I have read and accept the <a href="../action/viewVideoConditionsPopup" target="_blank" onClick="popupConditions(''../action/viewVideoConditionsPopup'', 1); return false;" title="View Terms and Conditions in a new window">Terms and Conditions</a>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tsandcs-file-checkbox', 1, 'Ts and Cs checkbox text for file downloads', 'The text shown to the right of the ts and cs checkbox on the download file page', 'I have read and accept the <a href="../action/viewFileConditionsPopup" target="_blank" onClick="popupConditions(''../action/viewFileConditionsPopup'', 1); return false;" title="View Terms and Conditions in a new window">Terms and Conditions</a>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'conditions-image', 1, 'Terms and Conditions (Images)', 'Image download Terms and Conditions copy.', '<h1>Terms and Conditions</h1> <p>Before you download an image, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style="color: #888888;">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'conditions-video', 1, 'Terms and Conditions (Video)', 'Video download Terms and Conditions copy.', '<h1>Terms and Conditions</h1> <p>Before you download a video, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style="color: #888888;">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'conditions-file', 1, 'Terms and Conditions (Files)', 'File download Terms and Conditions copy.', '<h1>Terms and Conditions</h1> <p>Before you download a file, please ensure you have permission to use it, and that you understand its usage terms.</p> <p><span style="color: #888888;">(This text can be changed by an admin user by going to Admin-&gt;Content in the menu.)</span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'view-image-copy', 1, 'Asset details copy (Images)', 'Copy for image details page.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'view-video-copy', 1, 'Asset details copy (Video)', 'Copy for video details page.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'view-file-copy', 1, 'Asset details copy (File)', 'Copy for file details page.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'homepage-bottom', 1, 'Home Page Text Bottom', 'Text at bottom of home page.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keyword-help', 1, 'Keyword Search Help', 'Keywords help text.', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'recent-intro', 1, 'Recent Assets intro text', 'The intro text shown on the recent assets page', '<p>The items shown on this page are assets recently added to Asset Bank.</p><p>Note to admin users: you can change this intro text from the admin area.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'download-lightbox', 1, 'Download Lightbox text', 'Text shown on the "Download Lightbox" page', '<p><em>If you are not able to unzip zip files then please return to the previous page and download the items individually.</em></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'available-categories-label', 1, 'Available categories label', 'Label for the "Available categories" in the "Categories" section on the "Add/Edit Asset" page', 'Available categories (remember to click Add to add the category):', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'export-copy', 1, 'Export page copy', 'Export page copy', '<p>If you enter a reference in the &#39;Append to filename&#39; field below then this will be appended to export filenames.<br />If you enter text in the &#39;Description&#39; field then this will appear in the header of the exported spreadsheet.<br />Check &#39;Export asset files&#39; if you want file data to be exported as well as metadata.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'custom-header-content', 1, 'Custom header content', 'Custom content to go in header', '', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'login-as-no-permission', 1, 'Login copy - user needs permission', 'Copy shown on the login page when the user requires extra permissions', '<p>You need to log in to access that page (your session may have expired). Please login below, or try returning to the <a href="viewHome" title="Home page">home page</a>.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'filter-search-text', 1, 'Filter search text', '<p>The filter <b>?filterName?</b> has been applied to the form below. Select from the list box underneath the menu or edit the form below to change the filtering.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-website', 9, 'Label - Website', 'Website:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-user-register-date', 9, 'Label - Register Date', 'Register Date:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-single-upload', 1, 'Intro - Single upload page', '<p>Please click &quot;Browse&quot; to locate the file you want to add, and then click &quot;Upload&quot; to upload the file.<br />Once the file has been uploaded you will be able to enter metadata about the #item#, and add it to categories.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-upload', 1, 'Intro - Bulk upload page', '<p>The data import enables you to bulk load #items# from a zip file or a directory on the server.</p><p>There is no import currently in progress.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-upload-success', 1, 'Intro - Upload Success page', '<p>Your file has been added, with an ID of <strong>?assetId?</strong>.</p><p>You can <a href="viewAsset?id=?assetId?">view the new asset</a>, <a href="../action/viewUploadAssetFile">add another file</a> or use the site navigation to continue.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-change-profile', 1, 'Intro - Change Profile', '<p>Change your profile details in the form below. </p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-change-password', 1, 'Intro - Change Password', '<p>To change your password, please enter your existing password and new password in the fields provided below.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-request-approval-success', 1, 'Intro - Request Approval Success', '<p>Your request has been sent to the administrator. <br />Check your #a-lightbox# when you next log in to track the approval status of the images.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-preview-image', 1, 'Intro - Preview download image', '<p>Click the download button to save the image to your computer.</p> ', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-update-actions', 1, 'Lightbox - Update Actions copy', '<p>These actions apply to all #items# in the #a-lightbox# that you have permission to update.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-downloadable', 1, 'Lightbox - Downloadable items', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-downloadable-with-approval', 1, 'Lightbox - Downloadable items after approval', '<p>If there is further information about approval status, then you can click on &quot;Approval details&quot; to see it, (access may be temporary or subject to certain conditions).</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-pending-approval', 1, 'Lightbox - Pending approval copy', '<p>These #items# are pending approval from the administrator before you can download them.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-require-approval', 1, 'Lightbox - Require approval copy', '<p>#items# requiring approval to download.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-approval-rejected', 1, 'Lightbox - Approval rejected copy', '<p>#items# for which download approval has been rejected.</p><p>Click on &quot;Approval details&quot; for further information about approval status. <br />To request approval again click the &quot;Resubmit&quot; button.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-view-only', 1, 'Lightbox - View only copy', '<p>These #items# are available to view only.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-all-cats', 1, 'Intro - All Categories', '<p>Listed below are all of the categories in the system in alphabetical order. Alternatively return to <a href="browseItems?categoryId=-1&categoryTypeId=1">category tree view &raquo;</a></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-forgot-pwd', 1, 'Intro - Forgotten Password', '<p>Please enter your username or your email address and we will email a new password to you.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-forgot-pwd-isemail', 1, 'Intro - Forgotten Password username is email', '<p>Please enter your username (your email address) and we will email a new password to you.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-upload-1', 1, 'Intro - Bulk Upload step1', '<h2>Step 1: Upload files</h2><p>To import files in bulk, you first need to transfer the files to the server. Click the Upload files button below and then browse to and select the files you want to import. When you have finished uploading files, click the next button to enter metadata. If you upload files by mistake you can <a href="cancelImport" onclick="return confirm(''This will delete all uploaded file. Do you want to continue?'');">delete all uploaded files</a>.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-upload-1-noapp', 1, 'Intro - Bulk Upload step 1 (no applet)', '<p>If it supports it, you can <a href="?FTPPath?" target="_blank">use your browser to transfer the files</a> simply drag and drop the files you want to upload into the popup window.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-upload-2', 1, 'Intro - Bulk Upload step 2', '<h2>Step 2: Start import</h2><p>Select the zip file or directory that contains the files, and (optionally) attributes that will be added to all of the #items#.<br/>You must select at least one category for the #items#.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-applet-instruct-1', 1, 'Bulk Upload - applet instructions 1', '<p>To upload files to the server click browse below and select your files. You can select multiple files at once then click upload.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-applet-instruct-2', 1, 'Bulk Upload - applet instructions 2', '<p>Once files have finished uploading you will be redirected to the import page. To access the import page directly, <a href="../action/viewStartImport?uploaded=1">click here</a> - and then select files from the dropdowns for processing.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-upload-help', 1, 'Bulk Upload - upload help', '<p>An easy way to open your bulk upload directory in Windows Explorer is as follows:</p><ul><li>Go to the Windows &quot;Start&quot; menu.</li><li>Select &quot;Run ...&quot;</li><li>Copy the following, and paste into the &quot;Open&quot; field:<br/><strong>explorer ?FTPPath?</strong></li><li>Click OK.</li></ul>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-upload-help-ftp1', 1, 'Bulk Upload - upload help ftp 1', '<h3>Windows</h3><p>If you are using a browser that does not provide read-write access to FTP sites then you can use Windows Explorer instead.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-upload-help-ftp2', 1, 'Bulk Upload - upload help ftp 2', '<p><strong>Note:</strong> by default Internet Explorer 7 gives you access to an FTP site in read-only mode.</p><h3>Mac Users</h3><p>You need to install an FTP client (or use the command line if you know how) and connect using the following details:</p><ul><li><strong>Host:</strong> ?ftpHost?</li><li><strong>Username:</strong> ?ftpUsername?</li><li><strong>Password:</strong> ?ftpPassword?</li></ul><br /><p>Once connected, move into your personal upload directory - i.e. the directory with the same name as your username.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-otherwise-ftp', 1, 'Bulk - Otherwise ftp', 'Otherwise, you need to connect using an FTP client.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-otherwise-win', 1, 'Bulk - Otherwise windows', 'Otherwise, you need to connect using Windows Explorer.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-batch-update', 1, 'Intro - Batch Update', '<p>The batch update feature enables you to search for #items# and then update their metadata quickly.</p><p>If another user has started a batch update then you will not see the #items# that are in their batch, to stop you from updating the same #item#.</p><p>Each batch is limited to ?maxBatchUpdateResults? #items# &#8211; once you have finished updating the #items# in your batch you can start a new one.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-update', 1, 'Intro - Bulk Update', '<p>The bulk update feature enables you to create a batch of #items# and then update their metadata in bulk using specified values.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-bulk-update-1', 1, 'Copy - Bulk Update 1', '<div class="info"><strong>You currently have a bulk update batch defined.</strong><br />Please note that #items# in this batch are locked by you, so that other users cannot perform bulk updates on them until you click &quot;Finish with this batch&quot;.</div><p>There are <strong>?numInBatch?</strong> #items# in the batch.<br />There are <strong>?numSelUpdate?</strong> #items# selected for update.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-bulk-update-permissions-warning', 1, 'Copy - Bulk Update Permissions Warning', '<div class="warning">You do not have permission to edit all of the selected assets. <strong>?numUpdatePermissionDenied?</strong> #items# have been removed from the batch.</div>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-batch-specify-items', 1, 'Intro - Batch update specify items', '<p>Specify the criteria for the #items# that will be included in the batch using the form below.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-batch-use-search', 1, 'Intro - Batch use search results', '<p>Alternatively, you can <a href="../action/createNewBatch?restart=true&amp;admin=1&amp;cachedCriteria=1">use your last search results</a>.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-update-metadata', 1, 'Intro - Bulk Update Metadata', '<p>There are <strong>?numForUpdate?</strong> #items# for update in your batch. Please enter the metadata below for the fields that you want to update in bulk.</p>	', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-update-in-progress', 1, 'Intro - Bulk Update in progress', '<p>The bulk update is in progress.</p><p>This page will automatically refresh every 10 seconds until the update process finishes. If for some reason this does not happen you may <a href="bulkUpdateViewStatus">update the page</a> manually to check the status.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-bulk-update-finished', 1, 'Intro - Bulk Update finished', '<p>The bulk update has finished.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-approve-items', 1, 'Intro - Approve Items', '<p>There are ?uploadedAssets? #items# awaiting approval. You can approve #items# by <a href="../action/createNewBatch?restart=true&approvalStatus=3&approval=true&finishedUrl=/action/viewAssetApproval&cancelUrl=../action/viewAssetApproval">starting a new batch update &raquo;</a></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-approve-items-none', 1, 'Intro - Approve Items (no items)', '<p>There are no uploaded or changed #items# awaiting approval.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-approve-items-p2', 1, 'Intro - Approve Items Page 2', '<p>Approval requests from ?fullName?</p><p>Select Approval Status &quot;Unapproved&quot; to reject the request, or &quot;Approved&quot; to approve it. Optionally you may add a message to the user when approving or rejecting the request.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'bulk-upload-in-progress', 1, 'Bulk upload - Import in progress', '<p>The import is in progress.</p><p>This page will automatically refresh every 20 seconds until the import process finishes. If for some reason this does not happen you may <a href="?url?">update the page</a> manually to check the status.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-contact-success', 1, 'Contact Success copy', '<p>Thank you for getting in touch. </p><p>If your message requires a response we shall be in contact as soon as possible. </p><p>Please use the menu on the left to continue using #app-name#. </p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-request-cd', 1, 'Intro - Request on Cd', '<p>Please complete the details below to request the items in your #a-lightbox# on a CD.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-manage-lightboxes', 1, 'Intro - Manage Lightboxes', '<p>Here you may add, remove, rename and reorder #lightboxes#.</p><p>Note, when you only have one #a-lightbox# it will appear as &quot;#my-lightbox#&quot;.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-manage-lightboxes-shared', 1, 'Intro - Manage Lightboxes (shared)', '<p>Here you may add, remove, rename and reorder #lightboxes#. You can also share your #lightboxes# with other users.</p><p>Note, when you only have one #a-lightbox# it will appear as &quot;#my-lightbox#&quot;. You may rename it if you wish any sharing users to see a different name.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-rename-lightbox', 1, 'Intro - Rename lightbox', '<p>Rename #a-lightbox# <strong>?prevName?</strong></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-profile-changed', 1, 'Intro - Profile Changed', '<p>Your profile has successfully been updated.</p><p>Please use the left hand menu to continue using the Image Bank.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-password-changed', 1, 'Intro - Password Changed', '<p>Your password has successfully been updated.</p><p>Please use the left hand menu to continue using the Image Bank.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-update-result-1', 1, 'Intro - Update Result 1', '<p>The #item# has been submitted to an admin user for approval - <a href="../action/viewOwnerAssetApproval">view approval progress</a>.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-update-result-1-2', 1, 'Intro - Update Result 1 (alternate)', '<p>The #item# has been updated. <a href="../action/viewOwnerAssetApproval">View the submitted page</a> to find out it''s current approval status.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-update-result-2', 1, 'Intro - Update Result 2', '<p>Please select an option from the left-hand menu, or <a href="../action/viewHome">return to the home page</a>.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-batch-finished-1', 1, 'Intro - Batch Finished 1', '<p>Your search did not return any #items# that you can update.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-batch-finished-2', 1, 'Intro - Batch Finished 2', '<p>Some of the #items# you searched for are locked by another user - you will be able to see those #items# once the other user has finished with them.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-batch-finished-3', 1, 'Intro - Batch Finished 3', '<p>You have finished updating the #items# in your batch.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-request-approval', 1, 'Intro - Request Approval', '<p>You are about to request the #items# listed.	Use the notes field to provide relevant information, e.g. intended use.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'footer-copy', 1, 'Footer copy', '<p><span>&copy; #snippet-current-year# <a href="http://www.bright-interactive.com" target="_blank">Bright Interactive</a>. <a href="../action/viewConditions">Terms &amp; Conditions</a> | <a href="../action/viewPrivacy">Privacy Policy</a> | <a href="../action/viewAbout">About Asset Bank</a></span></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'video-conversion-advanced-copy', 1, 'Copy - Video Conversion Advanced Copy', '<em><p>You can use the following form to adjust the frame rate (fps) and audio sample rate (Hz) of the converted video.</p><p>In both cases, selecting a value higher than the input video''s value will result in no change.</p><p>You can also select to download a particular chunk of the video by setting the offset start position and the duration you want to download.</p></em>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-select-vid-thumb', 1, 'Intro - Select Video Thumbnail', '<p>Click the frame you would like to use as your preview thumbnail from the selection below.</p><p>Use the &#39;next&#39; and &#39;previous&#39; links to move through the movie.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-about-asset-bank', 1, 'Copy - About Asset Bank', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-most-downloaded', 1, 'Intro - Most Downloaded', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-least-downloaded', 1, 'Intro - Least Downloaded', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-most-viewed', 1, 'Intro - Most Viewed', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-least-viewed', 1, 'Intro - Least Viewed', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'email-linked-text', 1, 'Email Link Text', 'This files exceeds the maximum file size for email attachments. Your email will include a link to the file instead.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'advanced-search-intro', 1, 'Intro - Advanced search', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'browse-intro', 1, 'Intro - Browse', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightbox-intro', 1, 'Intro - Lightbox', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'profile-change-password', 1, 'Profile change password', 'You can also <a href="viewChangePassword">change your password</a>.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'email-used-for-username', 1, 'Copy - Email used for username', '<p>Please note that your email address is used for your unique username. <br />You cannot change your email address to one which is already used by another user.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-no-cats-selected', 1, 'No categories selected on search', '<em>*If no #category-nodes# are selected, all #category-nodes# will be searched.</em>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-print-image', 1, 'Intro - Print Image', 'This page enables you to choose which attributes are printed alongside the image.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'agreement-checkbox', 1, 'Agreement - Checkbox', 'I have read and accept the <a href="../action/viewAgreeementPopup?Id=?AgreementId?" target="_blank" onclick="popupViewAgreement(?AgreementId?); return false;" />Agreement</a>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'upload-success-parent', 1, 'Intro - Upload Success page for parent types', '<p>You may also add a single <a href="viewUploadAssetFile?parentId=?assetId?&entityId=?entityId?">?childName?</a> or multiple <a href="viewDataImport?parentId=?assetId?&entityId=?entityId?">?childrenName?</a> to this ?typeName?.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'login-page-copy-sso', 1, 'Login page copy (SSO)', 'Text to display on the SSO login page.', '<p>Please enter your OpenID to log in.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'submit-feedback-main', 1, 'Submit Feedback Intro', 'Please select your rating by clicking on one of the stars below, and then enter a subject and comment to complete your review.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'submit-feedback-rating', 1, 'Submit Feedback Rating', 'Please select your rating by clicking on one of the stars below.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'submit-feedback-comment', 1, 'Submit Feedback Comment', 'Please enter a subject and comment to complete your review.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'audio-conversion-advanced-copy', 1, 'Copy - Audio Conversion Advanced Copy', '<em><p>You can use the following form to adjust the audio sample rate (Hz) of the converted audio clip. Note that selecting a value higher than the input clip''s value will result in no change.</p><p>You can also select to download a particular chunk of the audio clip by setting the offset start position and the duration you want to download.</p></em>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-builder-intro', 1, 'Intro - Search builder', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'no-items-to-browse', 1, 'Copy - No items to browse', 'There are no #items# in this ?browseableName?.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'invitations-intro', 1, 'Copy - Invitations introduction', 'From here you can invite people to Asset Bank. Enter the email addresses of people you wish to invite and a message to be added to the invitation.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-video-quality', 9, 'Label - Video quality', 'Video quality:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-change-preview-start', 9, 'Label - Change Video Preview Start', 'Also change the preview clip to start from the selected frame?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-create-doc-from-brand-template', 9, 'Intro - Create Document From Brand Template', '<p>Please fill in the fields below.  The text you enter will be used to fill in the template when you download it.</p>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-compare-images', 1, 'Intro - Compare images', 'The images that you selected for comparison are shown below.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'user-to-verify-with', 1, 'Supply details of user to verify with', 'To register you must supply the details of an authorised person to verify you.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'thank-you-for-approving', 1, 'Thank you for approving text', 'Thank you for approving this user. Their details have been sent to an admin user for final approval.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'thank-you-for-rejecting', 1, 'Thank you for rejecting text', 'Thank you for rejecting this user. Their details have been removed from the system.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'intro-request-high-res', 12, 'Intro - Request high res', 'You are requesting approval to download the high resolution version of the #item#(s) below.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-bulk-delete-warning', 1, 'Bulk delete warning', 'Warning: If you proceed the assets in the selected batch will be permanently deleted from the system.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-download-attributes', 1, 'Copy - Download attributes intro', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-download-attributes-lightbox', 1, 'Copy - Download attributes intro on lightbox', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'login-page-copy-external-users', 1, 'Text to display on the login page when external users have additional fields.', '<p>Please enter your username and password to log in, or register as an <a href="../action/viewRegisterUser">internal</a> or <a href="../action/viewRegisterUser?externalUser=true">external</a> user.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'default-related-assets-main', 1, 'Default related assets lead in text', '<p>Listed below are the default relationships for this asset that need to be created. Uncheck any in the list that you don''t want to be setup and click the save button...</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'default-related-assets-empty-peer', 1, 'Default related assets no missing peer assets text', '<p>This asset isn''t currently missing any default peer relationships.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'default-related-assets-empty-child', 1, 'Default related assets no missing child assets text', '<p>This asset isn''t currently missing any default child relationships.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'add-empty-related-assets-main', 1, 'Add empty related assets lead in text', '<p>Listed below are the relationships that assets of this type can have. Select the number of empty related assets that you would like to add to this asset for each listed relationship and click the save button at the bottom to generate. You can optionally provide a name for the empty assets that will be used to populate the name attribute of each asset created (if suitable):</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'no-empty-related-assets-peer', 1, 'No empty related peer assets to add text', '<p>This asset can not have peer related assets.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'no-empty-related-assets-child', 1, 'No empty related child assets to add text', '<p>This asset can not have child related assets.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-advanced-viewing', 9, 'Label - Advanced Viewing', 'Advanced viewing when unnapproved?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-copy-asset-step1-warning', 1, 'Copy - Copy asset step 1 Warning', 'Please note: Peer relationships are maintained outside of the copy asset process (so any peer relationships on the source asset will not be present on the resulting copy and should be reinstated manually if they are needed)', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'copy-copy-asset-step1', 1, 'Copy - Copy asset step 1', '<p>Select how you would like each child relationship from the source asset to be dealt with from the list below then click Next to move on to updating the copy''s metadata:</p>', 1, 1);

-- Menu items
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-home', 7, 'Main menu - Home', 'Home', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-search', 7, 'Main menu - Advanced Search', 'Advanced Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-browse', 7, 'Main menu - Browse', 'Browse', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-purchases', 7, 'Main menu - My Purchases', 'My Purchases', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-approve', 7, 'Main menu - Approve Items', 'Approve #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-update-assets', 7, 'Main Menu - Update Assets', 'Update #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-contact', 7, 'Main menu - Contact Us', 'Contact Us', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-upload', 7, 'Main menu - Upload', 'Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-single', 7, 'Main menu - Single Upload', 'Single Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-bulk', 7, 'Main menu - Bulk Upload', 'Bulk Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-welcome', 7, 'Header links - Welcome', 'Welcome', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-subscriptions', 7, 'Header links - Subscriptions', 'Subscriptions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-profile', 7, 'Header links - Your Profile', 'Your Profile', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-log-out', 7, 'Header links - Log Out', 'Log Out', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-subscribe', 7, 'Header links - Subscribe', 'Subscribe', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-register', 7, 'Header links - Register', 'Register', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-log-in', 7, 'Header links - Log In', 'Log In', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-help', 7, 'Header links - Help', 'Help', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-unsubmitted', 7, 'Main menu - Unsubmitted assets', 'Unsubmitted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-submitted', 7, 'Main menu - Submitted assets', 'Submitted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-batch-update', 7, 'Tabs - Batch Update', 'Batch Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-bulk-update', 7, 'Tabs - Bulk Update', 'Bulk Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-metadata-import', 7, 'Tabs - Metadata Import', 'Metadata Import', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-browse-cats', 7, 'Tabs - Browse Categories', 'Browse Categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-browse-keywords', 7, 'Tabs - Browse Keywords', 'Browse Keywords', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-quick-download', 7, 'Tab - Quick Download', 'Quick Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-advanced-download', 7, 'Tab - Advanced Download', 'Advanced Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-current-lightbox', 7, 'Tab - Current Lightbox', 'Current Lightbox', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-manage-lightboxes', 7, 'Tab - Manage Lightboxex', 'Manage Lightboxes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-download', 7, 'Tab - Download', 'Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-advanced-search', 7, 'Tab - Advanced Search', 'Advanced Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-saved-searches', 7, 'Tab - Saved Searches', 'Saved &amp; Recent Searches', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-browse-popularity', 7, 'Tab - Browse by Popularity', 'Browse by Popularity', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-alphabetical', 7, 'Tab - Alphabetical', 'Alphabetical', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-hierarchical', 7, 'Tab - Hierarchical', 'Hierarchical', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-quick-add', 7, 'Tab - Quick add', 'Quick add', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-keyword-list', 7, 'Tab - Keyword list', 'Keyword list', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-approve-downloads', 7, 'Tab - Approve downloads', 'Downloads', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-approve-uploads', 7, 'Tab - Approve uploads', 'Uploads/Edits', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'menu-news', 7, 'Menu - News', 'News', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-normal-view', 7, 'Tab - Normal view', 'Normal view', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'tab-large-image-view', 7, 'Tab - Large image view', 'Large image view', 1, 2);


-- Common Terms
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'item', 8, 'Term for item', 'item', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'items', 8, 'Plural term for item', 'items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'my-lightbox', 8, 'Term for My Lightbox', 'My Lightbox', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'a-lightbox', 8, 'Term for the Lightbox', 'Lightbox', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lightboxes', 8, 'Plural term for the Lightbox', 'Lightboxes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'pixels', 8, 'Term for pixels', 'pixels', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'you-are-here', 8, 'Start of Breadcrumb', 'You are here:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'company-name', 8, 'Company Name', 'Asset Bank', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'app-name', 8, 'Application Name', 'Asset Bank', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'term-for-english', 8, 'Term for English', 'English', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'category-root', 8, 'Category root name', 'Categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'category-node', 8, 'Category node name', 'category', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'category-nodes', 8, 'Category node name plural', 'categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'access-level-root', 8, 'Access level root name', 'Access Levels', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'access-level-node', 8, 'Access level node name', 'access level', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'access-level-nodes', 8, 'Access level node name plural', 'access levels', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keyword-root', 8, 'Keyword root name', 'Keywords', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keyword-node', 8, 'Keyword node name', 'keyword', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keyword-nodes', 8, 'Keyword node name plural', 'keywords', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'video', 8, 'Term for video', 'video', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'image', 8, 'Term for image', 'image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'file', 8, 'Term for file', 'file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'audio', 8, 'Term for audio', 'audio', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subcategory-root', 8, 'Term for subcategory', 'Subcategory', 1, 2);

-- Labels
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-search', 9, 'Label - home search box', 'Search...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-username', 9, 'Label - Username', 'Username:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-password', 9, 'Label - Password', 'Password:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-name', 9, 'Label - Name', 'Name:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-email', 9, 'Label - Email Address', 'Email Address:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-tel', 9, 'Label - Telephone', 'Telephone:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-message', 9, 'Label - Message', 'Message:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-title', 9, 'Label - Title', 'Title:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-tax-number', 9, 'Label - Tax number', 'VAT Number:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-forename', 9, 'Label - Forename', 'Forename:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-surname', 9, 'Label - Surname', 'Surname:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-address1', 9, 'Label - Address 1', 'Address 1:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-address2', 9, 'Label - Address 2', 'Address 2:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-town', 9, 'Label - Town', 'Town:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-county', 9, 'Label - County', 'County:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-postcode', 9, 'Label - Postcode', 'Postcode:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-country', 9, 'Label - Country', 'Country:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-fax', 9, 'Label - Fax number', 'Fax:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-job-title', 9, 'Label - Job Title', 'Job Title:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-division', 9, 'Label - Division', 'Division:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-organisation', 9, 'Label - Organisation', 'Organisation:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-user-reg-message', 9, 'Label - User Registration Message', 'Intended Use:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-address', 9, 'Label - Address', 'Address:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-current-password', 9, 'Label - Current Password', 'Current Password:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-new-password', 9, 'Label - New Password', 'New Password:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-confirm-password', 9, 'Label - Confirm New Password', 'Confirm New Password:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-groups', 9, 'Label - Groups', 'Groups:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-request-notes', 9, 'Label - Request Notes', 'Your request notes:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-org-units', 9, 'Label - Organisational Units', 'Organisational Units:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-org-unit', 9, 'Label - Organisational Unit', 'Organisational Unit:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-auto-login', 9, 'Label - Auto login checkbox', 'Log in automatically next time', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-select-usage', 9, 'Label - Select Usage', 'Please select your intended usage for this asset', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-file-format', 9, 'Label - File Format', 'File Format:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-size', 9, 'Label - Size', 'Size:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-position', 9, 'Label - Position', 'Position:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-aspect-ratio', 9, 'Label - Lock Aspect Ratio', 'Lock Aspect Ratio:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-pixel-width', 9, 'Label - Width (pixels)', 'Width (pixels):', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-pixel-height', 9, 'Label - Height (pixels)', 'Height (pixels):', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-density', 9, 'Label - Density (DPI)', 'Density (DPI):', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-jpeg-quality', 9, 'Label - JPEG Quality', 'JPEG Quality:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-rotate', 9, 'Label - Rotate', 'Rotate:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-strip', 9, 'Label - Strip', 'Strip:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-convert-rgb', 9, 'Label - Convert to RGB', 'Convert to RGB:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-compress-file', 9, 'Label - Compress file before download', 'Compress (ZIP) the file before it is downloaded', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-email-file', 9, 'Label - Email file', 'Send this file in an email', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-x', 9, 'Label - X dimension', 'X:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-y', 9, 'Label - Y dimension', 'Y:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-w', 9, 'Label - Width (short)', 'W:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-h', 9, 'Label - Height (short)', 'H:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-lock-aspect', 9, 'Label - Lock Aspect Ratio', 'Lock Aspect Ratio:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-file-formats', 9, 'Label - File Formats', 'File Formats:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-file-size', 9, 'Label - File Size', 'File Size:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-total-size', 9, 'Label - Total Size', 'Total Size:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-max-width', 9, 'Label - Max Width', 'Max Width:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-max-height', 9, 'Label - or Max Height', 'OR Max Height:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-thumbnail', 9, 'Label - Thumbnail', 'Thumbnail:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-approved', 9, 'Label - Approved?', 'Approved?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-keywords', 9, 'Label - Keywords', 'Keywords:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-approval-status', 9, 'Label - Approval Status', 'Approval Status:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-zip-file', 9, 'Label - Zip file', 'Zip file:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-or-directory', 9, 'Label - Or directory', 'Or directory:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-populate-name', 9, 'Label - Populate name', 'Populate name from filename:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-link-items', 9, 'Label - Link Items', 'Link the items to each other?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-intended-usage-details', 9, 'Label - Intended Usage Details', 'Please enter details of your intended usage:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-featured-image', 9, 'Label - Featured Image?', 'Featured Image?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-promoted-item', 9, 'Label - Promoted Item?', 'Promoted Item?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-preview-restricted', 9, 'Label - Preview Restricted?', 'Preview Restricted?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-delete-existing-cats', 9, 'Label - Delete existing categories?', 'Remove from all categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-filename', 9, 'Label - Filename', 'Filename:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-method', 9, 'Label - Method', 'Method:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-to', 9, 'Label - To', 'To:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-username-email', 9, 'Label - Username or Email', 'Username or Email:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-included-in-search', 9, 'Label - included in search', 'To be included in your search*:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-available', 9, 'Label - available', 'Available:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-items-not-in-cat', 9, 'Label -  Find only items not in a category', 'Find only #items# that are not in any category', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-id', 9, 'Label -Id', 'Id:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-added', 9, 'Label -Added', 'Added -', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-by', 9, 'Label - By', 'by:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-on', 9, 'Label - On', 'on:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-date', 9, 'Label - Date', 'Date:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-intended-usage', 9, 'Label - Intended Usage', 'Intended Usage:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-message-from-user', 9, 'Label - Message from the user', 'Message from the user:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-message-to-user', 9, 'Label - Message to the user', 'Your message to the user:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-approval-expires', 9, 'Label - Approval expires on', 'Approval expires on:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-import-all-files', 9, 'Label - Import all files inc zips', 'Import all files as #items# - including zips (?fileCount? files)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-extract-import-zip', 9, 'Label - Extract and import zip', 'Extract and import the contents of a zip file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-import-from-directory', 9, 'Label - Import assets from a directory', 'Import assets from a directory', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-user-group', 9, 'Label - User Group', 'User Group:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-add-comments', 9, 'Label - Additional comments', 'Additional comments:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-add-lightbox', 9, 'Label - Add new lightbox', 'Add a new #a-lightbox#:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-name-nc', 9, 'Label - Name (no colon)', 'Name', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-num-sharing', 9, 'Label - No. sharing', '# Sharing', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-actions', 9, 'Label - Actions', 'Actions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-my-alias', 9, 'Label - My Alias', 'My Alias', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-owner', 9, 'Label - Owner', 'Owner', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-editable', 9, 'Label - Editable', 'Editable', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-new-name', 9, 'Label - New Name', 'New Name:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-can-edit', 9, 'Label - Can edit', 'Can edit?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-language', 9, 'Label - Language', 'Language:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-message-from-admin', 9, 'Label - Message from administrator', 'Message from administrator:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-intended-use', 9, 'Label - Intended Use', 'Intended Use:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-your-notes', 9, 'Label - Your notes', 'Your notes:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-frameRate', 9, 'Label - Frame Rate', 'Frame Rate (fps)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-audioSampleRate', 9, 'Label - Audio Sample Rate', 'Audio Sample Rate (Hz)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-completeness', 9, 'Label - Completeness', 'Completeness:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-verify-links', 9, 'Label - Verify link(s)', 'Verify...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-username-nc', 9, 'Label - Username (no colon)', 'Username', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-email-nc', 9, 'Label - Email Address (no colon)', 'Email Address', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-date-time', 9, 'Label - Date and Time', 'Date &amp; Time', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-usage-description', 9, 'Label - Usage Description', 'Usage Description', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-access-type', 9, 'Label - Access Type', 'Access Type', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-bulk-upload-date', 9, 'Label - Bulk Upload Date', 'Bulk Upload Date:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-is-promoted', 9, 'Label - Is Promoted?', 'Is Promoted?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-is-featured', 9, 'Label - Is Featured?', 'Is Featured?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-layer', 9, 'Label - Layer', 'Layer/Page:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-include-previous-versions', 9, 'Label - Include previous versions', 'Include previous versions?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-subscriptions', 9, 'Label - Subscriptions', 'Subscriptions:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-rotate-images', 9, 'Label - Rotate Images', 'Rotate the images in this batch?:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-watermark-email-asset', 9, 'Label - Watermark image', 'Watermark image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-ip-address', 9, 'Label - IP address', 'IP Address', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-cd-custom-field', 9, 'Label - Request CD custom field', 'Telephone', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-cd-address', 9, 'Label - Request CD address', 'Address:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-asset-type', 9, 'Label - Asset Type', 'Type:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-ids', 9, 'Label - Ids', 'Ids:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-option-any', 1, 'Label - Any', '[any]', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-add-file-later', 9, 'Label - add file later', 'Add file later:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-template', 9, 'Label - Template', 'Template:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-use-template', 9, 'Label - Use template', 'Use an attribute template (page will refresh):', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-repurpose', 9, 'Label - Repurpose', 'Create embeddable version?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-synonyms', 9, 'Label - Synonyms', 'Synonyms', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-keyword', 9, 'Label - Keyword', 'Keyword', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-image-url', 9, 'Label - Image Url', 'Image url:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-html-code', 9, 'Label - Html code', 'HTML code:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-restricted', 9, 'Label - Is Restricted', 'Restricted?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-option-any-searchable-type', 9, 'Label - Any searchable type', '[any searchable type]', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-option-any-type', 9, 'Label - Any type', '[any type]', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-created-by', 9, 'Label - Created by', 'Created by:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-created-date', 9, 'Label - Created date', 'Created date:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-width', 9, 'Label - Width', 'Width:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-height', 9, 'Label - Height', 'Height:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-file-type', 9, 'Label - File type', 'File type:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-sensitive', 9, 'Label - Sensitive', 'Sensitive?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-new-keyword', 9, 'Label - New keyword', 'New keyword:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-visible-on-search', 9, 'Label - Visible on search', 'Visible on search:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-add-new-keyword', 9, 'Label - Add new keyword', 'Add new keyword:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-parent', 9, 'Label - Parent', 'Parent:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-expired', 9, 'Label - Expired', 'Expired?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'profile-modified-by', 9, 'Label - Last modified by', 'Last Modified By: ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement-text', 9, 'Label - Agreement text', 'Agreement Text:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement-title', 9, 'Label - Agreement title', 'Title', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement-expiry', 9, 'Label - Agreement expiry', 'Expiry', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement-actions', 9, 'Label - Agreement actions', 'Actions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement', 9, 'Label - Agreement', 'Agreement:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-expiry', 9, 'Label - Expiry', 'Expiry:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-shared-with-org-unit', 9, 'Label - Shared with org unit', 'Shared with current Org Unit:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-shared-between-org-units', 9, 'Label - Shared between org units', 'Shared between Org Units:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement-type', 9, 'Label - Agreement Type', 'Agreement type:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-previous-agreements', 9, 'Label - Previous agreements', 'Previous agreements', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreements', 9, 'Label - Agreements', 'Agreements', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-working-file', 9, 'Label - Working file', 'Working file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-remove-working-file', 9, 'Label - Remove working file', 'Remove working file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-autorotated', 9, 'Label - Image has been auto-rotated', 'This image has been auto-rotated by ?autoRotateAngle?&deg;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-comments', 9, 'Label - Comments', 'Comments', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-rating', 9, 'Label - Rating', 'Rating', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-subject', 9, 'Label - Subject', 'Subject', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-open-id', 9, 'Label - Open ID', 'OpenID:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-tint', 9, 'Label - Tint', 'Apply Tint:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-duration', 9, 'Label - Duration', 'Duration:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-video-url', 9, 'Label - Video Url', 'Video url:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-lock-aspect-nc', 9, 'Label - Lock Aspect Ratio (no colon)', 'Lock Aspect Ratio', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-agreement-text-nc', 9, 'Label - Agreement text (no colon)', 'Agreement text', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-approval-status-nc', 9, 'Label - Approval status (no colon)', 'Approval Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-completeness-nc', 9, 'Label - Completeness (no colon)', 'Completeness', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-last-downloaded-nc', 9, 'Label - Last downloaded (no colon)', 'Date last downloaded', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-sort-by', 9, 'Label - Sort by', 'Sort by:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-default-ordering', 9, 'Label - Default ordering', 'Default ordering', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-clip-url', 9, 'Label - Clip Url', 'Clip url:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-relevance', 9, 'Label - Relevance', 'Relevance', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-do-not-embed-mapped-data', 9, 'Label - do not embed mapped data', 'Don''t embed mapped data', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-force-unapproved', 9, 'Label - force asset unapproved', 'Get Approved?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-message-nc', 9, 'Label - Message', 'Message', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-audio-url', 9, 'Label - Audio Url', 'Audio url:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-email-addresses', 9, 'Label - Email addresses', 'Email addresses:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-submit-action', 9, 'Label - Submit Action', 'Submit action', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-workflow-submit-options', 9, 'Label - Workflow submit options', 'Workflow submit options', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-search-subcategories', 9, 'Label -  Search subcategories checkbox on search', 'Search subcategories of the selected categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-num-downloads', 9, 'Label - Number of downloads', 'Number of Downloads', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-num-votes', 9, 'Label - Number of votes', 'Number of Votes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-num-views', 9, 'Label - Number of views', 'Number of Views', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-file-url', 9, 'Label - File url', 'File url:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-color-space', 9, 'Label - Color space', 'Color space: ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-image', 9, 'Label - Image', 'Image:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-available-to-all', 9, 'Label - Available To All', 'Show for all users:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-show-on-homepage', 9, 'Label - Show On Homepage', 'Show on homepage:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-remove-current-image', 9, 'Label - Remove Current Image', 'Remove current image:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-current-image', 9, 'Label - Current Image', 'Current image:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-saved-search-alert', 9, 'Label - Saved Search Alert', 'Alert me when new images are added to this search:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-is-brand-template', 9, 'Label - Is Brand Template', 'Brand Template?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-download-approval-notes', 9, 'Label - Download approval notes', 'Download approval notes:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-defer-thumbnails', 9, 'Label - Defer Thumbnail Generation', 'Defer thumbnail creation?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-format-preference', 9, 'Label - Format preference', 'Format preference:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-transition', 9, 'Label - Transition', 'Transition', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-file-status', 9, 'Label - File Status', 'File Status:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-num-placeholders', 9, 'Label - Number', 'Number:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-results-per-page', 9, 'Label - Results per page', 'Results per page:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-bulk-delete-confirm', 9, 'Label - Bulk delete confirm', 'I understand that the selected assets will be permanently deleted.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-user-expiry-date', 9, 'Label - User expiry date', 'Expiry date:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-selected-workflow', 9, 'Label - Selected workflow', 'Selected workflow:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-update-workflow-status', 9, 'Label - Update workflow status', 'Update workflow status:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-download-as-file', 9, 'Label - Download as file', 'Download as a file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-download-as-image', 9, 'Label - Download as image', 'Download as an image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-delete-peer-relationships', 9, 'Label - Delete peer relationships', 'Unrelate assets:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-num-uses', 9, 'Label - Number of Uses', 'Uses', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-remove-id', 9, 'Label - Remove id', 'Remove id from filename:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-generate-thumbnail', 9, 'Label - Generate thumbnail', 'Generate thumbnail from original file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-remove-thumbnail', 9, 'Label - Remove thumbnail', 'Remove thumbnail', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-please-choose', 9, 'Label - Please choose', '[Please choose]', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-date-added-to-lightbox', 9, 'Label - Date added to lightbox', 'Date/time added to lightbox', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-child-assets', 9, 'Label - Child assets', 'Child assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-peer-assets', 9, 'Label - Peer assets', 'Peer assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-select-item', 9, 'Label - Select item', 'Select #item#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-description', 9, 'Label - Description', 'Description', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-quantity', 9, 'Label - Quantity', 'Quantity', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-name-sans-colon', 9, 'Label - Name', 'Name', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-indesign-template-asset', 9, 'Label - Template Asset', 'Template', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'label-indesign-pdf-quality', 9, 'Label - PDF Quality', 'PDF Quality', 1, 2);

-- Page Headings
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'login-title', 10, 'Heading - Login Page', 'Login', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-register', 10, 'Heading - Register', 'Register', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-search', 10, 'Heading - Advanced Search', 'Advanced Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-search-results', 10, 'Heading - Search Results', 'Search Results', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-contact', 10, 'Heading - Contact Us', 'Contact Us', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-promoted', 10, 'Heading - Promoted Items', 'Promoted Items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-recent', 10, 'Heading - Recent Items', 'Recent Items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-approve-items', 10, 'Heading - Approve Items', 'Approve Items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-your-profile', 10, 'Heading - Your Profile', 'Your Profile', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-profile-changed', 10, 'Heading - Profile Changed', 'Profile Changed', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-your-password', 10, 'Heading - Your Password', 'Your Password', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-password-changed', 10, 'Heading - Password Changed', 'Password Changed', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-step-1-upload', 10, 'Heading - Step 1: Upload File', 'Step 1: Upload File', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-step-2-metadata', 10, 'Heading - Step 2: Add Metadata', 'Step 2: Add Metadata', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-step-2-thumbnail', 10, 'Heading - Step 2: Select preview thumbnail', 'Step 2: Select preview thumbnail', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-upload', 10, 'Heading - Upload', 'Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-upload-success', 10, 'Heading - Upload Success', 'Upload Success', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-step-3-metadata', 10, 'Heading - Step 3: Add Metadata', 'Step 3: Add Metadata', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-select-video-thumbnail', 10, 'Heading - Select Video Thumbnail', 'Select Video Thumbnail', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-upload', 10, 'Heading - Bulk Upload', 'Bulk Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-start-import', 10, 'Heading - Start Import', 'Start Import', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-update', 10, 'Heading - Bulk Update', 'Bulk Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-update-batch', 10, 'Heading - Bulk Update: Batch Selection', 'Bulk Update: Batch Selection', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-update-run-update', 10, 'Heading - Bulk Update: Bulk Update: Run an Update', 'Bulk Update: Run an Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-update-status', 10, 'Heading - Bulk Update: Status', 'Bulk Update: Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-update-search', 10, 'Heading - Bulk Update: Create Batch Using Search', 'Bulk Update: Create Batch Using Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-image-details', 10, 'Heading - Image Details', 'Image Details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-video-details', 10, 'Heading - Video Details', 'Video Details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-audio-details', 10, 'Heading - Audio Details', 'Audio Details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-asset-details', 10, 'Heading - Asset Details', 'Asset Details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-brand-template-details', 10, 'Heading - Brand Template Details', 'Brand Template Details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-approval-notes', 10, 'Heading - Approval Notes', 'Approval Notes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-purchase-notes', 10, 'Heading - Purchase Notes', 'Purchase Notes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download', 10, 'Heading - Download', 'Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-preparing', 10, 'Heading - Preparing', 'Preparing', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-image', 10, 'Heading - Download Image', 'Download Image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-file', 10, 'Heading - Download File', 'Download File', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-browse', 10, 'Heading - Browse', 'Browse', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-batch-update', 10, 'Heading - Batch Update', 'Batch Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-batch-approval-update', 10, 'Heading - Batch Approval Update', 'Batch Approval Update for ?workflowName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-left-batch', 10, 'Heading - left in batch', 'left in batch', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-last-batch', 10, 'Heading - last in current batch', 'last in current batch', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-batch-update-finished', 10, 'Heading - Batch Update Finished', 'Batch Update Finished', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-update', 10, 'Heading - Update', 'Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-update-success', 10, 'Heading - Upload/Update Success', 'Upload/Update Success', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-about', 10, 'Heading - About', 'About', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-failure', 10, 'Heading - Download Failure', 'Download Failure', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-image-preview', 10, 'Heading - Download Image Preview', 'Download Image Preview', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-email-item', 10, 'Heading - Email Item', 'Email Item', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-email-sent', 10, 'Heading - Email Sent', 'Email Sent', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-not-found', 10, 'Heading - Asset Not Found', 'Asset Not Found', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-forgotten-password', 10, 'Heading - Forgotten Password', 'Forgotten Password', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-request-approval', 10, 'Heading - Request Approval', 'Request Approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-move-keyword', 10, 'Heading - Move Keyword', 'Move Keyword', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-keywords', 10, 'Heading - Keywords', 'Keywords', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-unsubmitted-items', 10, 'Heading - Unsubmitted Items', 'Unsubmitted Items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-submitted-items', 10, 'Heading - Submitted Items', 'Submitted Items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-submitted-items', 10, 'Subheading - Items not yet approved', 'Items not yet approved', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-filters', 10, 'Heading - Filters', 'Filters', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-filter', 10, 'Heading - Filter', 'Filter', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-promoted-items', 10, 'Subheading - Promoted Items', 'Promoted #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-recently-added', 10, 'Subheading - Recently added', 'Recently added', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-approve-uploaded-items', 10, 'Subhead - Approve uploaded items', 'Approve uploaded #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-unsubmitted-items', 10, 'Subhead - Approve uploaded items', 'Approve uploaded #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-approve-items-download', 10, 'Subhead - Approve items for download', 'Approve #items# for download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-update-actions', 10, 'Subhead - Update Actions', 'Update Actions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-purchase-required', 10, 'Subhead - Purchase required', 'Purchase required', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-approval-required', 10, 'Subhead - Approval required', 'Approval required', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-pending-approval', 10, 'Subhead - Pending approval', 'Pending approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-approval-rejected', 10, 'Subhead - Approval rejected', 'Approval rejected', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-view-only', 10, 'Subhead - View only', 'View only', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-prev-added', 10, 'Subhead - Previously added', 'Previously added', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-purchase-rejected', 10, 'Subhead - Purchase rejected', 'Purchase rejected', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-my-purchase', 10, 'Subhead - My Purchase', 'My Purchase', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-download-original', 10, 'Subhead - Download Original', 'Download Original', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-convert', 10, 'Subhead - Convert', 'Convert', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-crop', 10, 'Subhead - Crop', 'Crop', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-resize', 10, 'Subhead - Resize', 'Resize', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-adv-options', 10, 'Subhead - Advanced Options', 'Advanced Options', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-download-file', 10, 'Subhead - Download File', 'Download File', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-download-convert-images', 10, 'Subhead -Download &amp; Convert Images', 'Download &amp; Convert Images', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-download-originals', 10, 'Subhead - Download Originals', 'Download Originals', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-search-by', 10, 'Subhead - Search by', 'Search by', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-all-cats', 10, 'Heading - All Categories', 'All Categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-upload-help', 10, 'Subhead - Upload Help', 'Upload Help', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-subcats-cats', 10, 'Subhead - Subcategories of categories', 'Sub?browseablesName? of ?catName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-item-in-cat', 10, 'Subhead - One Item in Category', '?browseHits? #item# in ?catName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-items-in-cat', 10, 'Subhead - Multiple Items in Category', '?browseHits? #items# in ?catName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-current-filter', 10, 'Subhead - Current filter', 'Current filter:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-recent-listed-below', 10, 'Subhead - Recent items listed below', 'There are ?numRecentItems? recently added #items# listed below', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-promoted-listed-below', 10, 'Subhead - Promoted items listed below', 'There are ?numPromotedItems? promoted #items# listed below', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-not-yet-updated', 10, 'Subhead - Not yet updated', 'Not yet updated', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-already-updated', 10, 'Subhead - Already updated', 'Already updated', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-enter-metadata', 10, 'Subhead - Enter Metadata', 'Enter Metadata', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-log', 10, 'Subhead - Log', 'Log:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-import-log', 10, 'Subhead - Import Log', 'Import Log:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-ready-download', 10, 'Subhead - Ready to Download', 'Ready to download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-find-users', 10, 'Subhead - Find Users', 'Find Users', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-request-cd', 10, 'Heading - Request Images on CD', 'Request Images on CD', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-your-details', 10, 'Subhead - Your details', 'Your details:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-dl-lightbox-zip', 10, 'Subhead - dl lightbox as zip file', 'Download items in your #a-lightbox# as a compressed zip file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-video-conversion-status', 10, 'Heading - Video Conversion Status', 'Video Conversion Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-save-search', 10, 'Heading - Save Search', 'Save Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-saved-searches', 10, 'Heading - Saved Searches', 'Saved Searches', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-recent-searches', 10, 'Heading - Recent Searches', 'Recent Searches', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-search-section', 10, 'Heading - Search section', 'Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-matching-users', 10, 'Heading - Matching users', 'Matching Users', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-embedded-metadata', 10, 'Subhead - Embedded Metadata', 'Embedded Metadata', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-usage-info', 10, 'Subhead - Usage Info', 'Usage Information for &quot;?assetTitle?&quot;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-upload-files', 10, 'Subhead - Upload files', 'Upload files', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-asset-versions', 10, 'Heading - Asset Versions', '#item# versions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-create-copy', 10, 'Heading - Create Copy', 'Create Copy', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-most-downloaded', 10, 'Heading - Most Downloaded', 'Most Downloaded #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-least-downloaded', 10, 'Heading - Least Downloaded', 'Least Downloaded #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-most-viewed', 10, 'Heading - Most Viewed', 'Most Viewed #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-least-viewed', 10, 'Heading - Least Viewed', 'Least Viewed #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-video', 10, 'Heading - Download Video', 'Download Video', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-audit-info', 10, 'Subhead - Audit Info', 'Audit Information for &quot;?assetTitle?&quot;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-edit-relationships', 10, 'Heading - Edit Relationships', 'Edit Relationships', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-edit-relationship', 10, 'Heading - Edit Relationship', 'Edit Relationship', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-add-asset', 10, 'Heading - Add Asset', 'Add #item#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-choose-type', 10, 'Heading - Choose Type', 'Choose Type', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-related-items', 10, 'Heading - Related Items', 'Related #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-keyword-chooser', 10, 'Heading - Keyword Chooser', 'Keyword Chooser', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-image', 10, 'Heading - Embeddable Image', 'Embeddable Image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-images', 10, 'Heading - Embeddable Image Versions', 'Embeddable Image Versions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-print-image', 10, 'Heading - Print Image', 'Print Image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-news', 10, 'Heading - News', 'News &amp; Announcements', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-browse-keywords', 10, 'Heading - Browse Keywords', 'Browse Keywords', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-no-items-found', 10, 'Subhead - No items found', 'No #items# found', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-agreements', 10, 'Heading - Agreements', 'Agreements', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-manage-agreements', 10, 'Subhead - Manage agreements', 'Manage Agreements', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-edit-agreement', 10, 'Subhead - Edit agreement', 'Edit Agreement', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-view-agreement', 10, 'Heading - View agreement', 'View agreement', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-upload-file', 10, 'Subhead - Upload file', 'Upload file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-submit-feedback', 10, 'Heading - Submit Feedback', 'Submit ?type?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-tint', 10, 'Subhead - Tint', 'Tint', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-videos', 10, 'Heading - Embeddable Video Versions', 'Embeddable Video Versions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-video', 10, 'Heading - Embeddable Video', 'Embeddable Video', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-audio-clip', 10, 'Heading - Embeddable Audio Clip', 'Embeddable Audio Clip', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-audio-clips', 10, 'Heading - Embeddable  Audio Clips', 'Embeddable Audio Clips', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-audio-conversion-status', 10, 'Heading - Audio Conversion Status', 'Audio Conversion Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-audio', 10, 'Heading - Download Audio', 'Download Audio', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-asset-approval-messages', 10, 'Label - Asset approval messages', 'Asset approval messages', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-change-approval-status', 10, 'Subhead - Change Approval Status', 'Change Approval Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-invite-users', 10, 'Heading - Invite users', 'Invite Users', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-compose-invite', 10, 'Heading - Compose invite', 'Compose invite', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-embed-file', 10, 'Heading - Embed file', 'Embed file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-create-doc-from-brand-template', 10, 'Heading - Create Document from Template', 'Create Document from Template', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-apply-action-to-selected-assets', 16, 'Subhead - Apply action to selected assets', 'Apply an action to selected assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-compare-images', 10, 'Heading - Compare images', 'Compare Images', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-user-approval', 10, 'Heading - User approval', 'User approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-email-this-page', 10, 'Heading - Email This Page', 'Email This Page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-download-child-assets', 10, 'Heading - download child assets', 'Download child assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-download-child-assets', 10, 'Subhead - download child assets', 'Download child assets as a compressed zip file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-add-placeholders', 10, 'Heading - Add Empty Assets', 'Add Assets Without Files', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-num-placeholders', 10, 'Subhead - Number of Empty Assets', 'Number of Empty Assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-bulk-delete-confirm', 10, 'Heading - Bulk delete confirm', 'Confirm bulk delete', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-my-searches', 10, 'Heading - My saved searches', 'My saved searches', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-recommended-searches', 10, 'Heading - Recommended searches', 'Recommended searches', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-select-attributes-display', 10, 'Subheading - Select attributes to display', 'Select attributes to display', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-options', 10, 'Subheading - Options', 'Options', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-account-reactivated', 10, 'Heading - Account reactivated', 'Account reactivated', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-update-assets', 10, 'Heading - Update Assets', 'Update #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-bulk-update', 10, 'Subheading - Bulk Update', 'Update a group of assets, all at once', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-batch-update', 10, 'Subheading - Batch Update', 'Update a group of assets, one at a time', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-initialise-workflow', 10, 'Heading - Initialise Workflow', 'Initialise Workflow', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-slideshow', 10, 'Heading - Slideshow', 'Slideshow', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-help', 10, 'Heading - Help', 'Help', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-online-help', 10, 'Heading - Online help', 'Online Help', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-admin-help', 10, 'Heading - Admin help', 'Online Admin Help', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurpose-slideshow', 10, 'Heading - Create Embeddable Slideshow', 'Create Embeddable Slideshow', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-slideshows', 10, 'Heading - Embeddable Slideshows', 'Embeddable Slideshows', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-repurposed-slideshow', 10, 'Heading - Embeddable Slideshow', 'Embeddable Slideshow', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-contact-sheet', 10, 'Heading - Contact Sheet', 'Contact Sheet', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-choose-page-layout', 10, 'Subheading - Choose page layout', 'Choose page layout:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-email-in-progress', 10, 'Heading - Email In Progress', 'Sending Email', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-upload-log', 10, 'Heading - Upload Log', 'Upload Log:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'heading-email-failed', 10, 'Heading - Email Failed', 'Failed to Send Email', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-choose-mask', 10, 'Heading - Choose a mask ', 'Choose a mask ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-position-mask', 10, 'Heading - Position your mask ', 'Position and resize your mask', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-choose-bg-color', 10, 'Heading - Choose background colour', 'Choose mask background colour (optional) ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-mask-configuration', 10, 'Heading - Mask configuration', 'Mask configuration', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-default-related-assets', 10, 'Heading - Default related assets', 'Edit default relationships', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-add-empty-related-assets', 10, 'Heading - Add empty related assets', 'Add empty related assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-associated-assets', 10, 'Subhead - Associated assets', 'Associated assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-asset-relationships', 10, 'Subhead - Asset Relationships', '#item# Relationships', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'heading-copy-asset-step1', 10, 'Heading - Copy asset step 1', 'Copy asset : Step 1 Child Relationships', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subhead-bulk-upload-complete', 10, 'Subheading - Bulk upload complete', 'Bulk upload completed', 1, 2);

-- Buttons
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-login', 11, 'Button - Login', 'Login', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-cancel', 11, 'Button - Cancel', 'Cancel', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-register', 11, 'Button - Register', 'Register', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-reset-password', 11, 'Button - Reset Password', 'Reset Password', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-search', 11, 'Button - Search', 'Search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-go', 11, 'Button - Go', 'Go', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-submit', 11, 'Button - Submit', 'Submit', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-add', 11, 'Button - Add', 'Add', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-remove', 11, 'Button - Remove', 'Remove', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-download-all', 11, 'Button - Download all', 'Download all', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-send', 11, 'Button - Send', 'Send', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-upload', 11, 'Button - Upload', 'Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-download', 11, 'Button - Download', 'Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-edit', 11, 'Button - Edit', 'Edit', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-delete', 11, 'Button - Delete', 'Delete', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-save', 11, 'Button - Save', 'Save', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-start-import', 11, 'Button - Start Import', 'Start Import', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-close', 11, 'Button - Close', 'Close', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-skip', 11, 'Button - Skip', 'Skip &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-delete2', 11, 'Button - Delete with arrow', 'Delete &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-save-continue', 11, 'Button - Save and Continue', 'Save & Continue &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-keyword-chooser', 11, 'Button - Keyword Chooser', 'Keyword Chooser...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-manage-master', 11, 'Button - Manage Master List', 'Manage Master List...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-dictionary-chooser', 11, 'Button - Dictionary Chooser', 'Select...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-move-keyword', 11, 'Button - Move Keyword', 'Move Keyword', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-go-arrow', 11, 'Heading - Go with arrow', 'Go &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-download-now', 11, 'Button - Download now', 'Download now', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-download-originals', 11, 'Button - Download Originals', 'Download Originals', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-preview', 11, 'Button - Preview', 'Preview &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-back', 11, 'Button - Back', '&laquo; Back', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-crop', 11, 'Button - Crop & Close', 'Crop & Close', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-next', 11, 'Button - Next', 'Next &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-add-arrow', 11, 'Button - Add arrow', 'Add &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-request-approval', 11, 'Button - Request Approval', 'Request Approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-resubmit', 11, 'Button - Resubmit', 'Resubmit', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-link-assets', 11, 'Button - Link Assets', 'Link #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-download-original', 11, 'Button - Download Original', 'Download Original', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-upload-files', 11, 'Button - Upload files', 'Upload files', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-cancel-uploads', 11, 'Button - Cancel Uploads', 'Cancel Uploads', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-create-copy', 11, 'Button - Create Copy', 'Create Copy', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-request-cd', 11, 'Button - Request on CD', 'Request all on CD', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-select', 11, 'Button - Select', 'Select', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-select-all-on-page', 11, 'Button - Select all on page', 'Select All on Page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-select-all-results', 11, 'Button - Select all results', 'Select All Results', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-email-now', 11, 'Button - Email now', 'Email now', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-create-now', 11, 'Button - Create now', 'Create now', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-email', 11, 'Button - Email', 'Email', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-create', 11, 'Button - Create', 'Create', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-preview-agreement', 11, 'Button - Preview agreement', 'Preview agreement', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-upload-file', 11, 'Button - Upload file', 'Upload file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-cancel-upload', 11, 'Button - Cancel Upload', 'Cancel Upload', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-remove-file', 11, 'Button - Remove File', 'Remove File', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-update', 11, 'Button - Update', 'Update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-reset', 11, 'Button - Reset', 'Reset', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-create-doc-from-brand-template', 11, 'Button - Create Document From Brand Template', 'Create Document', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-create-from-brand-template-and-download', 11, 'Button - Create Doc from Brand Template and Download', 'Create and Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-download-high-res', 11, 'Button - Download high res', 'Download high-res', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-delete-assets', 11, 'Button - Delete assets', 'Delete assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-apply', 11, 'Button - Apply', 'Apply &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-apply-changes', 11, 'Button - Apply changes', 'Apply changes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-start-new-bulk-upload', 11, 'Button - Start new bulk upload', 'Start new bulk upload &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-lightbox-download', 11, 'Button - Lightbox download', 'Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'button-lightbox-more-actions', 11, 'Button - Lightbox more actions', 'More actions', 1, 2);

-- Links
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-contents', 12, 'Link - View contents (of lightbox)', 'View contents &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-see-promoted', 12, 'Link - See Promoted Items', 'See all promoted #items# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-see-recent', 12, 'Link - See Recent Items', 'See all recent #items# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-advanced-search', 12, 'Link - Advanced Search', 'Advanced search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-forgot-password', 12, 'Link - Forgotten Password', 'Forgotten your password?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-all', 12, 'Link - Remove All', 'Remove all', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-details', 12, 'Link - View details', 'View details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-edit-details', 12, 'Link - View/Edit details', 'View/Edit details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove', 12, 'Link - Remove', 'Remove', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-lowercase', 12, 'Link - remove (lowercase)', 'remove', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-approval-details', 12, 'Link - Approval Details', 'Approval details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-larger', 12, 'Link - view larger size', 'view larger size', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-full-size', 12, 'Link - view full size', 'view full size', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-asset-usage', 12, 'Link - View asset usage', 'View #item# usage...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-embedded', 12, 'Link - View Embedded Data', 'View embedded data &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-select-crop', 12, 'Link - Select Crop Area', 'Select crop area...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-change-crop', 12, 'Link - Change Crop Area', 'Change crop area...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-clear-crop', 12, 'Link - Clear Crop Area', 'Clear crop area...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-configure-mask', 12, 'Link - Configure Mask', 'Configure mask...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-clear-mask', 12, 'Link - Clear Mask', 'Clear mask', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-all-promoted', 12, 'Link - Remove all from promoted', 'Remove all from promoted &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-from-promoted', 12, 'Link - Remove from promoted', 'Remove from promoted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-lightbox', 12, 'Link - Back to lightbox', '&laquo; Back to #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-start-data-import', 12, 'Link - Start new data import', 'Start a new data import &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-login', 12, 'Link - Back to login', '&laquo; Back to login', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-search-again', 12, 'Link - Search again', 'New Search &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'refine-search-results', 12, 'Link - Refine results', 'Refine results &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-to-lightbox', 12, 'Link - Add to lightbox', 'add to #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-to-mylightbox', 12, 'Link - Add to My lightbox', 'add to #my-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-dl-image', 12, 'Link - Back to Download Image', '&laquo; Back to Download Image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-image-details', 12, 'Link - Back to image details', '&laquo; Back to ?assetTypeName? Details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-all-cats', 12, 'Link - View all categories', 'View all categories &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-prev', 12, 'Link - Prev', '&laquo; Prev', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-next', 12, 'Link - Next', 'Next &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-all', 12, 'Link - Add all to lightbox', 'Add all to #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-x', 12, 'Link - Add x to lightbox', 'Add the first ?count? #items# to your #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back', 12, 'Link - Back', '&laquo; Back', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-item', 12, 'Link - Back to item details', '&laquo; Back to #item# details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-all-cats', 12, 'Link - Back to all cats', '&laquo; Back to all categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-my-lightbox', 12, 'Link - Back to my lightbox', '&laquo; Back to #my-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-my-lightbox', 12, 'Link - Remove from my lightbox', 'Remove from #my-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-lightbox', 12, 'Link - Remove from lightbox', 'Remove from #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-link', 12, 'Link - Remove Link', 'Remove Link', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-in-lightbox', 12, 'Link - In Lightbox', 'In #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-in-my-lightbox', 12, 'Link - In My Lightbox', 'In #my-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-upload-more-files', 12, 'Link - Upload more files', '&laquo; Upload more files', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-show-help', 12, 'Link - Show help ', 'Show help for using Windows Explorer to upload your files &raquo;', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-show-help-ftp', 12, 'Link - Show help (ftp true)', 'Show help for using an FTP client to upload your files &raquo;', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-hide-help', 12, 'Link - Hide help', '&laquo; Hide help', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-return-current-batch', 12, 'Link - Return to current batch update', 'Return to your current batch update &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-cancel-current-batch', 12, 'Link - Cancel current batch update &raquo;', 'Cancel current batch update &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-start-new-batch-update', 12, 'Link - Start new batch update', 'Start a new batch update &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-select-items-bulk', 12, 'Link - select items for bulk update', 'Select #items# in batch for update &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-run-update-batch', 12, 'Link - Run update on batch', 'Run an update on the selected #items# in this batch &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-finish-batch', 12, 'Link - Finish with batch', 'Finish with this batch &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-status', 12, 'Link - View status', 'View status &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-start-new-bulk-update', 12, 'Link - Start new bulk update', 'Start a new bulk update &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-refresh-view', 12, 'Link - Refresh view', 'Refresh my view &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-select-all', 12, 'Link - Select all', 'Select all', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-unselect-all', 12, 'Link - Unselect all', 'Unselect all', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-select-items-bulk-again', 12, 'Link - Select items for another update', 'Select #items# in batch for another update &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-run-another-update', 12, 'Link - Run another update on batch', 'Run another update on this batch &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view', 12, 'Link - View', 'view', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-bulk-update-batch', 12, 'Link - Bulk update last batch', 'Bulk update the last imported batch &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-batch-update-batch', 12, 'Link - Batch update last batch', 'Batch update the last imported batch &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-return-cms', 12, 'Link - Return to CMS', '&laquo; Return to CMS', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-up', 12, 'Link - up', 'up', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-down', 12, 'Link - down', 'down', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-rename', 12, 'Link - rename', 'rename', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-delete', 12, 'Link - delete', 'delete', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-share', 12, 'Link - share', 'share', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-more-users', 12, 'Link - Add more users', 'Add more users &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-users', 12, 'Link - Add users', 'Add users &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-remove-all-users', 12, 'Link - Remove all users', 'Remove all users', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-yes', 12, 'Link - yes', 'yes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-no', 12, 'Link - no', 'no', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-choose-diff-frame', 12, 'Link - Choose different frame', 'Choose a different frame for thumbnail &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-save-search', 12, 'Link - Save search', 'Save search &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-run', 12, 'Link - run', 'run', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-save', 12, 'Link - save', 'save', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-search-this-category', 12, 'Link - Search this category', 'Search this category', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-first', 12, 'Link - First', 'First', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-last', 12, 'Link - Last', 'Last', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-search-this-accesslevel', 12, 'Link - Search this access level', 'Search this access level', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-current-version', 12, 'Link - Current version', 'View current version', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-all-versions', 12, 'Link - All versions', 'View all versions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to', 12, 'Link - Back to', '&laquo; Back to', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-asset-audit', 12, 'Link - View asset audit', 'View log...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-asset-workflow-audit', 12, 'Link - View asset workflow audit', 'View workflow history...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-find', 12, 'Link - Find', 'Find', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add', 12, 'Link - Add', 'Add', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-edit-relationships', 12, 'Link - Edit Relationships', 'Edit Relationships', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-create-new-version', 12, 'Link - Create new version', 'Create new version...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-fit-to-page', 12, 'Link - Fit to page', 'fit to page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to-repurposed-images', 12, 'Link - Back to embeddable images', 'Back to embeddable images', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-open', 12, 'Link - Open', 'open', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-move', 12, 'Link - Move', 'move', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-edit', 12, 'Link - Edit', 'edit', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-print-image', 12, 'Link - Print Image', 'print image details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-embed', 12, 'Link - Embed', 'embed', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-rss', 12, 'Link - View Rss Feed', 'View RSS feed', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-hide-keywords', 12, 'Link - Hide keyword list', '&laquo; Hide keyword list', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-select-keyword', 12, 'Link - Select another keyword', 'Select another keyword &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-agreement', 12, 'Link - Add an agreement', 'Add an agreement &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-edit-agreement', 12, 'Link - Edit selected agreement', 'Edit selected agreement &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-download-as-image', 12, 'Link - Download as image', 'Download as image &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-alternative-uploader', 12, 'Link - alternative uploader', 'uploader popup', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-cancel-file-update', 12, 'Link - Cancel file update', 'cancel file update', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-download-as-file', 12, 'Link - Download as file', '&laquo; Download as file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-more-like-this', 12, 'Link - More like this', 'more like this...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-follow-link', 12, 'Link - Follow link', 'view...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to-repurposed-videos', 12, 'Link - Back to embeddable videos', 'Back to embeddable videos', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-details-resize', 12, 'Link - View details or resize', 'View details or resize', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-use-search-builder', 12, 'Link - Use search builder', 'Use the search builder', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-use-search-form', 12, 'Link - Use search form', 'Use the search form', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-batch-approve-assets', 12, 'Link - Batch approve assets', 'Batch approve these #items# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-batch-revise-assets', 12, 'Link - Batch revise assets', 'Batch revise these #items# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to-repurposed-audio-clips', 12, 'Link - Back to embeddable audio clips', 'Back to embeddable audio clips', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-more', 12, 'Link - More', 'More &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-all-news', 12, 'Link - View all news', '&laquo; View all news', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-more-news', 12, 'Link - More news', 'More news', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-search-builder-clause', 12, 'Link - Add search builder clause', 'add clause', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-single', 12, 'Link - Add single', 'Add single', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-multiple', 12, 'Link - Add multiple', 'Add multiple', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-find-multiple', 12, 'Link - Find multiple', 'Find multiple', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-invite-more-users', 12, 'Link - Invite more users', '&laquo; Invite more users', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-unsubmitted', 12, 'Link - View unsubmitted assets', 'View unsubmitted #items# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-batch-update-assets', 12, 'Link - Batch update assets', 'Batch update these assets &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-full-version', 12, 'Link - View full version', 'view full version &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-sso-login', 12, 'Link - Login with SSO', 'Login using SSO', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-embed-as-image', 12, 'Link - Embed as image', 'embed as image', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-embed-as-file', 12, 'Link - Embed as file', 'embed as file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to-lightbox', 12, 'Link - Back to lightbox', '&laquo; Back to lightbox', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to-homepage', 12, 'Link - Back to homepage', '&laquo; Back to homepage', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-clearing-current-filter', 12, 'Link - Clear current filter', 'Clear current filter &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-email-this-page', 12, 'Link - Email This Page', 'Email This Page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-visit-the-page', 12, 'Link - Visit The Page', 'Visit the page &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-back-to-children', 12, 'Link - back to children', '&laquo; Back to child assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-your-lightbox', 12, 'Link - View your lightbox', 'View your #a-lightbox# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-previous-messages', 12, 'Link - View previous messages', 'View previous messages', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-view-owner-approved-items', 12, 'Link - View owners approved items', 'View your approved #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-bulk-delete-assets', 12, 'Link - Bulk delete assets', 'Delete the selected #items# in this batch &raquo;', 1, 2);
INSERT INTO ListItem (Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES ('link-this-page-as-slideshow', 12, 'Link - View this page as a slideshow', 'View these images as a slideshow', 1, 2);
INSERT INTO ListItem (Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES ('link-all-as-slideshow', 12, 'Link - View all as a slideshow', 'View all as a slideshow', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-edit-help-text', 12, 'Link - Edit help text', 'Edit help text for this page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-large-image-popup', 12, 'Link - large image popup', 'large image popup', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-print', 12, 'Link - Print', 'Print', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-configure-contact-sheet', 12, 'Link- Configure contact sheet', 'Configure Page Layout', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-specify-colour', 12, 'Link - Specify custom colour', 'Specify custom colour', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-colour-picker', 12, 'Link - Colour picker', 'Colour picker...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-show-all', 12, 'Link - Show all', 'Show all', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'link-return-to-original-user', 12, 'Link - Return to Original User', 'Return to Original User', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'link-default-relationships', 12, 'Link - Default relationships', 'Default relationships', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'link-add-empty-related-assets', 12, 'Link - Add empty related assets', 'Add empty related #items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'link-return-to-browse', 12, 'Link - Return to the category you were browsing', '&laquo; Return to the ?type? you were browsing', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-sorting-options', 12, 'Link - Sorting options', 'Sorting  options', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'link-add-an-item', 12, 'Link - Add an item', 'Add an #item#', 1, 2);

-- Snippets
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-linking-items-note', 13, 'Snippet - linking items note', '<p><em>Linking #items# means that when an #item# is viewed, all other #items# from the batch will be shown as related #items#.</em></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-in-progress', 13, 'Snippet - Bulk Update in progress', 'You have a <a href="../action/viewManageBulkUpdate">Bulk Update</a> in progress.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-in-progress', 13, 'Snippet - Batch Update in progress', 'You have a <a href="../action/viewManageBatchUpdate">Batch Update</a> in progress.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-currently-batch-progress', 13, 'Snippet - Currenlty have a batch update in progress', 'You currently have a batch update in progress.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-so-items-available', 13, 'Snippet - so items can be updated', '(so that the #items# can be updated by other users).', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-not-in-progress', 13, 'Snippet - batch not in progress', 'You currently do not have a batch update in progress.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-bulk-note', 13, 'Snippet - Batch and bulk update note', 'Please note that you can only run one Batch Update or Bulk Update operation at once. These functions lock the #items# in the batch so that they cannot be used in another user&quot;s batch.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-exceeds-size', 13, 'Snippet - Batch exceeds max size', 'There is no option to select #items# in this batch since the batch size exceeds ?maxSizeForSelection?.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-executing', 13, 'Snippet - Bulk Update executing', 'A bulk update is executing.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-finished', 13, 'Snippet - Last Bulk update finished', 'Your last bulk update operation has finished.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-not-in-progress', 13, 'Snippet - Bulk not in progress', 'You currently do not have a bulk update in progress.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-please-wait-update', 13, 'Snippet - Please wait while changes are saved', '<p>Please wait while your changes are saved. If you are adding a large file this may take a few minutes.<br />If there is no further activity then please check that the value in the File field is empty or represents a valid file.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-req-fields', 13, 'Snippet - Complete required fields', '<em>Please complete all fields marked with an asterisk (<span class="required">*</span>).</em>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-choose-diff-filter', 13, 'Snippet - Choose another filter', '<p>Choose another filter from the options above.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-curr-filter-removed', 13, 'Snippet - Current filter removed', '<p>Your filter is currently set to <strong>&quot;?currFilter?&quot;</strong> which has now been removed from the system.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-items-batch', 13, 'Snippet - No items in batch', 'There are no #items# in your batch.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-check-item-update-again', 13, 'Snippet - Check images to update again', 'Check the #items# you want to update again and click &quot;Next &raquo;&quot;.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-check-item-update', 13, 'Snippet - Check images to update', 'Check the #items# you want to update and click &quot;Next &raquo;&quot;.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-meta-instruct', 13, 'Snippet - Bulk Metadata instructions', '<p>For each attribute that you want to be updated in bulk, choose to either replace or append the current value, then fill in the new value in the right-hand column. Attributes will not be updated unless the corresponding drop-down is selected.</p> <p>Please note that if replace is selected and the attribute has a blank field value in the right-hand column, then the attribute value will be set to empty on all #items# in the batch.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-meta-instruct', 13, 'Snippet - Batch Metadata instructions', '<p><em>Please fill in attributes to be applied to all #items# in the batch.</em> </p>	', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-if-changed', 13, 'Snippet - (if changed)', '(if changed)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-not-approve-all', 13, 'Snippet - No permission to approve all', '<p>You may not have permission to approve all or any of these #items#.</p>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-items-approval', 13, 'Snippet - No items awaiting approval', '<p>There are no #items# awaiting approval.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-users-items-approval', 13, 'Snippet - Users submitted items for approval', '<p>The following users have submitted #items# for download approval:</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-note-cancel-batch', 13, 'Snippet - Note this will cancel current batch', '<p>Please note: this will cancel your current batch update.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-blank-30-days', 13, 'Snippet - Leave blank for 30 days', '(leave blank for the default of 30 days)', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-import-not-started', 13, 'Snippet - Import not started', 'Your data import could not be started. Please check that an import is not in progress already.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-errors-during-import', 13, 'Snippet - Errors during import', 'The following errors occurred during import:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-errors-starting-import', 9, 'Snippet - Errors starting import', 'The following errors occurred when trying to start the import:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-please-wait-file', 13, 'Snippet - Please wait while file processed', '<p>Please wait while your file is processed.<br />If there is no further activity then please check that the value in the File field represents a valid file.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-please-wait-upload', 13, 'Snippet - Please wait while file uploaded', '<p>Please wait while your file is uploaded. If you are uploading a large file this may take a few minutes.<br />If there is no further activity then please check that the value in the File field represents a valid file and click upload again.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-browse-file-again', 13, 'Snippet - Note: need to browse for file again', '(Please note: for browser security reasons you will need to browse to your file again.)', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-close-browser', 13, 'Snippet - CMS close browser window', 'CMS mode is currently enabled which allows you to select an image for use in a content management system. If you do not want to select an image you can <a href="clearCmsMode">exit CMS mode »</a>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-share-lb-other-users', 13, 'Snippet - share lightbox with users', '<p>Share #a-lightbox# <b>?lightboxName?</b> with other users</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-users-found', 13, 'Snippet - No users found', 'There are no users in the system whose details match your criteria.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-contains-x-items', 13, 'Snippet - Contains x items', 'contains <strong>?numAssetsLb?</strong> #items#', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-contains-x-item', 13, 'Snippet - Contains x item', 'contains <strong>?numAssetsLb?</strong> #item#', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-lb-not-shared', 13, 'Snippet - lightbox not shared', 'This #a-lightbox# is not currently shared with any other users.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'no-search-results-query', 13, 'Snippet - No Search Results (query)', 'Your search for &quot;?sQuery?&quot; did not match any #items#', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'no-search-results', 13, 'Snippet - No Search Results', 'Your search did not match any #items#', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-results-query-max', 13, 'Snippet - Search Results (query max)', 'Your search for &quot;?sQuery?&quot; returned over ?numberHits? results ', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-results-query', 13, 'Snippet - Search Results (query)', 'Your search for &quot;?sQuery?&quot; returned ?numberHits? results ', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-results-query-one', 13, 'Snippet - Search Results (query one)', 'Your search for &quot;?sQuery?&quot; returned ?numberHits? result	', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-results-max', 13, 'Snippet - Search Results (max)', 'Your search returned over ?numberHits? results', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-results', 13, 'Snippet - Search Results', 'Your search returned ?numberHits? results', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'search-results-one', 13, 'Snippet - Search Results (one)', 'Your search returned ?numberHits? result', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lb-item-lightbox', 13, 'Snippet - Item in Your Lightbox', 'There is <strong>?numItems?</strong> #item# in your #a-lightbox#.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lb-item-lightboxes', 13, 'Snippet - Item in This Lightbox','There is <strong>?numItems?</strong> #item# in this #a-lightbox#.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lb-items-lightbox', 13, 'Snippet - Items in Your Lightbox', 'There are <strong>?numItems?</strong> #items# in your #a-lightbox#', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'lb-items-lightboxes', 13, 'Snippet - Items in This Lightbox', 'There are <strong>?numItems?</strong> #items# in this #a-lightbox#.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-crop-image-intro', 13, 'Snippet - Crop image intro', '<p>Leave default values if you do not want to crop</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-cats-selected', 13, 'Snippet - No cats selected', '<em>*If no categories are selected, all categories will be searched. </em>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-show-items-in', 13, 'Snippet - Show items that are in', 'Show #items# that are in', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-any', 13, 'Snippet - any (dropdown)', 'any', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-all', 13, 'Snippet - all (dropdown)', 'all', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-of-selected-cats', 13, 'Snippet - of selected cats', 'of the selected #category-nodes#', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-request-dl-add-lightbox', 13, 'Snippet - Request permission DL via lightbox', 'You can request permission to download this #item# by adding it to your #a-lightbox#.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-request-dl-via-lightbox', 13, 'Snippet - Request permission DL via lightbox', 'You can request permission to download this #item# from your <a href="../action/viewAssetBox">#a-lightbox#</a>', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-your-notes', 13, 'Snippet - Your approval notes', 'You added the following notes about intended usage for this image:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-admin-notes', 13, 'Snippet - Administrator approval notes', 'The administrator has added the following notes regarding usage for this image:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-ensure-access-item', 13, 'Snippet - ensure access item', 'so please ensure that you access the item before then.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-made-request-on', 13, 'Snippet - made request on', 'You made a request for this item on', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-request-approved-on', 13, 'Snippet - request approved on', 'Your request was approved on', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-request-rejected-on', 13, 'Snippet - request rejected on', 'Your request was rejected on', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-approval-expires-on', 13, 'Snippet - approval expires on', 'Approval for this item expires on', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-zip-perm-items', 13, 'Snippet - Zip only include permissable items', '<p><em>Please note: the zip file will only include items you have permission to download.</em></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-zip-noperm-original', 13, 'Snippet - Zip no perm dl original', '<p><em>Please note: for some images you do not have permission to download the original, hence this option will not be available for this batch.</em></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-zip-noperm-advanced', 13, 'Snippet - Zip no perm advanced', '<p><em>Please note: for some images you do not have permission to perform an advanced conversion, hence this option will not be available for this batch.</em></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-login-request-approval', 13, 'Snippet - Need to login to request approval', '<p>You need to log in to your account before you can request approval for these #items#.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-skip', 13, 'Snippet - skip (dropdown)', 'Skip', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-replace', 13, 'Snippet - replace (dropdown)', 'Replace', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-append', 13, 'Snippet - append (dropdown)', 'Append', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-preview-clip', 13, 'Snippet - preview clip', 'preview clip', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-view-preview', 13, 'Snippet - view preview', 'view preview', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-download-preview', 13, 'Snippet - download preview', 'download preview', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-applies-to-language', 13, 'Snippet - Applies to language', 'Applies to the current language only', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-req-for-completeness-fields', 13, 'Snippet - Fields required for completeness', '<em>Those fields marked with a dagger (<span class="required">&dagger;</span>) are additionally required for the data to be considered complete.</em>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-flash-unsupported', 13, 'Snippet - Flash not supported', 'Sorry your browser does not support flash. Please choose a different upload method.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-need-js-enabled', 13, 'Snippet - Need javascript for uploader', '<strong>Note:</strong> You need javascript enabled for this uploader to work', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-choose-upload-method', 13, 'Snippet - Choose upload method', 'Choose your upload method:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-upload-browser', 13, 'Snippet - Upload with browser', 'Upload files with your browser', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-upload-ftp', 13, 'Snippet - Upload with ftp', 'Upload files with FTP', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-saved-searches', 13, 'Snippet - no saved searches', 'You currently don&#39;t have any saved searches.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-recent-searches', 13, 'Snippet - no recent searches', 'You haven&#39;t run any searches since logging in.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-search-term', 13, 'Snippet - no search term', 'no keywords entered', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-max-saved-searches', 13, 'Snippet - max saved searches reached', 'The maximum number of saved searches has been reached - you must replace or delete an existing search.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-save-searches', 13, 'Snippet - save searches', 'Save search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-save-search', 13, 'Snippet - save search', 'Save search', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-save-most-recent-search', 13, 'Snippet - save most recent search', 'Save your most recent search...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-save-search-warning', 13, 'Snippet - save search warning', 'Note, if this search is saved with the same name as an existing saved search, the existing saved search will be replaced.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-saved-search-slots', 13, 'Snippet - save search slots', 'You can save up to ?savedSearchSlots? more searches.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-refreshes-subscriptions', 13, 'Snippet - refreshes subscriptions', '(Refreshes available subscriptions)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-related-items', 13, 'Snippet - Has related items', 'This #item# has <a href="#related" title="go to related #items#">related #items#</a>.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-select', 13, 'Snippet - Select', 'Select', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-can-edit', 13, 'Snippet - Users can edit', 'Added users can edit the ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-crop-undefined', 13, 'Snippet - Crop undefined', 'Crop undefined', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-crop-defined-prefix', 13, 'Snippet - Crop defined prefix', 'Crop defined', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-embedded-data', 13, 'Snippet - No embedded data', 'This image contains no embedded data (e.g. EXIF, IPTC, XMP).', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-none', 13, 'Snippet - none', 'none', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-item-not-dl', 13, 'Snippet - Item not downloaded yet', 'This #item# has not been downloaded yet.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-mm-print', 13, 'snippet - mm print', 'mm (print at ?printDPI? DPI);', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-mm-screen', 13, 'snippet - mm screen', 'mm (screen at ?screenDPI? DPI);', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-mb', 13, 'snippet - Megabytes', 'Mb', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-kb', 13, 'snippet - Kilobytes', 'Kb', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-assets-visible-for-email', 13, 'Snippet - No Assets Visible in Marketing Email', 'You don&#39;t have permission to see any of the #items# for this email - please contact an application administrator in order to resolve this issue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-landscape', 13, 'Snippet - Landscape', 'Landscape', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-portrait', 13, 'Snippet - Portrait', 'Portrait', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-square', 13, 'Snippet - Square', 'Square', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-complete', 13, 'Snippet - Complete', 'Complete', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-incomplete', 13, 'Snippet - Incomplete', 'Incomplete', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-approved', 13, 'Snippet - Approved', 'Approved', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-partial-approval', 13, 'Snippet - Partial Approval', 'Partially approved', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-awaiting-approval', 13, 'Snippet - Awaiting Approval', 'Awaiting approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-unapproved', 13, 'Snippet - Unapproved', 'Unapproved', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-between', 13, 'Snippet - between', 'between', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-and', 13, 'Snippet - and', 'and', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-yes', 13, 'Snippet - Yes', 'Yes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no', 13, 'Snippet - No', 'No', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-layers', 13, 'Snippet - Layers', 'layers', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-page-x-of-y', 13, 'Snippet - Page x of y', 'Page ?currentPage? of ?numPages?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-file-selection-warning', 13, 'Snippet - File selection warning', 'Please note: for browser security reasons if you wish to change the image file, you may need to browse and select it again.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-file-uploading', 13, 'Snippet - File uploading', 'Please wait while your changes are saved. If you are adding a large file this may take a few minutes.<br/>If there is no further activity then please check that the value in the File field is empty or represents a valid file.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-save-changes', 13, 'Snippet - Save changes', 'Save changes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-save-as-new-version', 13, 'Snippet - Save as new version', 'Save as new version', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-uncompressed', 13, 'Snippet - Uncompressed', 'uncompressed', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-progress', 13, 'Snippet - Progress', 'Progress...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-lightbox-ready', 13, 'Snippet - Lightbox ready to download', 'Your #a-lightbox# is ready to download!', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-file-downloading', 13, 'Snippet - File downloading', '(Your file should start downloading automatically, if it does not please click on the link above)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'approval-granted', 13, 'Snippet - approval granted', 'Approval granted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'pending-approval', 13, 'Snippet - pending approval', 'Pending approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'approval-rejected', 13, 'Snippet - approval rejected', 'Approval rejected', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-view', 13, 'Snippet - View', 'View', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-views', 13, 'Snippet - Views', 'Views', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-download', 13, 'Snippet - Download', 'Download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-downloads', 13, 'Snippet - Downloads', 'Downloads', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-details', 13, 'Snippet - Details', 'details', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-parent', 13, 'Snippet - Parent', 'parent', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-parents', 13, 'Snippet - Parents', 'parents', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-other', 13, 'Snippet - Other', 'other', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-add-file-later', 13, 'Snippet - add file later', 'Alternatively check the &#39;Add file later&#39; checkbox and click the button to move to the next step and enter metadata for a file that you plan to upload at a later date.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-choose-template', 13, 'Snippet - Choose template', '[Choose template]', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-any-type', 13, 'Snippet - any type (dropdown)', 'any type', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-expired', 13, 'Snippet - expired', 'expired', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-keywords-for-letter', 13, 'Snippet - no keywords for letter', 'There are no keywords beginning with the letter', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-all-keywords', 13, 'Snippet - all (keywords)', 'All', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-repurposed-image-intro', 13, 'Snippet - embeddable image', 'Your version has been successfully created and is shown below. This version will remain accessible until the original item is removed, expired or made restricted.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-image-restricted', 13, 'Snippet - image restricted', 'This asset cannot be downloaded by non-admin users as it is restricted.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-image-expired', 13, 'Snippet - image expired', 'This asset cannot be downloaded by non-admin users as it has expired.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-repurposed-images-intro', 13, 'Snippet - embeddable images intro', 'Below are embeddable versions that have already been created for this image. You may reuse any of these versions if they are appropriate, or if not you may <a href="?url?">create your own version</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-now', 13, 'Snippet - now', 'now', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-original', 13, 'Snippet - original', 'original', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-originals', 13, 'Snippet - originals', 'originals', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-image-shown-actual-size', 13, 'Snippet - Shown actual size', 'shown actual size', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-can-delete-version', 13, 'Snippet - can delete version', 'If you have created this version in error, you may delete it by clicking the button below.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-cannot-after-leaving-page', 13, 'Snippet - cannot after leaving page', 'Note that you will not be able to do this after leaving this page.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-repurposed-images', 13, 'Snippet - no repurposed versions', 'There are currently no embeddable versions of this image - you may <a href="?url?">create the first embeddable version</a> if you wish.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-version-created-on', 13, 'Snippet - Version created on', 'Version created on', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-provide-sensitivity-notes', 13, 'Snippet - Provide sensitivity notes', 'Please provide reasons below', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-root', 13, 'Snippet - Root', 'Root', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-currently-editing', 13, 'Snippet - You are currently editing', 'You are currently editing:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-keywords', 13, 'Snippet - No keywords', 'There are currently no keywords at this level.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-add-keyword-to', 13, 'Snippet - Add keyword to', 'Add new keyword to', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-filtering', 13, 'Snippet - No filtering', 'No filtering', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-please-select', 13, 'Snippet - Please select', 'Please Select', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-unrestricted', 13, 'Snippet - Unrestricted', 'Unrestricted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-agreement-applies', 13, 'Snippet - Agreement applies', 'Agreement applies', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-restricted', 13, 'Snippet - Restricted', 'Restricted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-this', 13, 'Snippet - this', 'this', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-a', 13, 'Snippet - a', 'a', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-has', 13, 'Snippet - has', 'has', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-has-parent', 13, 'Snippet - has parent', '', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-use-simple-upload', 13, 'Snippet - use simple upload', '(for smaller files you may use the <a href="?url?">simple upload form</a> instead)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-use-alternative-upload', 13, 'Snippet - use alternative upload', '(for larger files you may use the <a href="?url?">flash/java uploader</a> instead)', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-upload-large-file', 13, 'Snippet - upload a large file', '(to upload a large file you may use the ?link? instead)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-uploadable-types', 13, 'Snippet - no uploadable types', 'There are currently no asset types that you may bulk-upload outside of the context of a parent asset.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'add-new-value', 13, 'Snippet - Add new value', 'Or, add new value', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-comment', 13, 'Snippet - Comment', 'Comment', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-rating', 13, 'Snippet - Rating', 'Rating', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-review', 13, 'Snippet - Review', 'Review', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-votes', 13, 'Snippet - Votes', 'Votes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-vote', 13, 'Snippet - Vote', 'Vote', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-already-voted', 13, 'Snippet - Already voted', 'You have voted for this #item#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-vote-for-this-item', 13, 'Snippet - Vote for this item', 'Vote for this #item# &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-retract-vote', 13, 'Snippet - Retract vote', 'Retract vote &raquo;', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-ratings', 13, 'Snippet - Ratings', 'Ratings', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-comments', 13, 'Snippet - Comments', 'Comments', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-reviews', 13, 'Snippet - Reviews', 'Reviews', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-feedback', 13, 'Snippet - No Feedback', 'There are currently no ?type?s', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-feedback-present', 13, 'Snippet - Feedback Present', 'There are ?feedbackCount? reviews for this #item#.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-feedback-present-single', 13, 'Snippet - Feedback Present Single', 'There is 1 review for this #item#.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-incorrect-login-details', 13, 'Snippet - Incorrect login', 'The details you entered are incorrect. Please try again.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-logged-out', 13, 'Snippet - Logged out remotely', 'You are not currently authenticated with ?remoteDirectoryName? - please log in again, or contact an administrator if you continue to see this message.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-username-in-use', 13, 'Snippet - Username in use', 'Your ?remoteDirectoryName? username is already taken in this application - please contact an administrator to resolve this issue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-seconds', 13, 'Snippet - Seconds', 'seconds', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-minutes', 13, 'Snippet - Minutes', 'minutes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-second', 13, 'Snippet - Second', 'second', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-minute', 13, 'Snippet - Minute', 'minute', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-repurposed-videos-intro', 13, 'Snippet - embeddable videos intro', 'Below are embeddable versions that have already been created for this video. You may reuse (and resize) any of these versions if they are appropriate, or if not (for example if the duration needs to change) you may <a href="?url?">create your own version</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-repurposed-videos', 13, 'Snippet - no embeddable videos', 'There are currently no embeddable versions of this video - you may <a href="?url?">create the first embeddable version</a> if you wish.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-video-changes-page-only', 13, 'Snippet - video changes page only', 'Note, changes to the size will only affect this page, and will not affect the way this video appears elsewhere.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-smaller-then-actual-size', 13, 'Snippet - smaller than actual size', 'Shown smaller than actual size.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-relationships-limit', 13, 'Snippet - Relationship Limit', 'There are too many results to use the select all functionality.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-video-conversion-error', 13, 'Snippet - video conversion error', 'An error occurred during your video conversion. If you specified the frame rate and/or audio sampling rate you may have entered values that are incompatible with the output file format.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-video-conversion', 13, 'Snippet - no video conversion', 'You currently have no video conversions in progress.</p><p>If you were in the middle of converting a video then an error may have occurred with your conversion. Please return to the asset and try again.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-error-warning', 13, 'Snippet - error warning', 'Note, you should only enter values you know are valid for the format.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-standard', 13, 'Snippet - standard', 'Standard', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-high', 13, 'Snippet - high', 'High', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-low', 13, 'Snippet - low', 'Low', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-best', 13, 'Snippet - best', 'Best', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-not-yet-rated', 13, 'Snippet - not yet rated', 'Not yet rated', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-not-approved', 13, 'Snippet - Not Approved', 'Not Approved', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-note-about-approval-update-bulk', 13, 'Snippet - Note about setting approved/unapproved on bulk update', 'Note:<br/>Setting assets unapproved will add them to their configured approval workflow.<br/>Setting assets approved will remove them from any existing workflow.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-note-about-approval-update-set', 13, 'Snippet - Note about setting approved', 'Approving this asset will remove it from its approval workflow.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-note-about-approval-update-unset', 13, 'Snippet - Note about setting unapproved', 'Unapproving this asset will add the asset to the configured workflow (and it will appear under Approve Items for approvers).', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-number-uploaded-items', 13, 'Snippet - Number of uploaded items to approve', 'There are ?totalSize? #item#s in total', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-number-uploaded-items2', 13, 'Snippet - Number of uploaded items to approve part 2', ', of which ?returnSize? are shown.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-uploaded-items', 13, 'Snippet - No uploaded items to approve', 'There are currently no #item#s for you to approve.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-submitted-items', 13, 'Snippet - No submitted items in approval', 'You currently have no #item#s being approved for this workflow.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-ascending', 13, 'Snippet - Ascending', 'Ascending', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-descending', 13, 'Snippet - Descending', 'Descending', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-repurposed-audio-clips-intro', 13, 'Snippet - embeddable audio clips intro', 'Below are embeddable versions that have already been created for this audio file. You may reuse any of these versions if they are appropriate, or if not (for example if the duration needs to change) you may <a href="?url?">create your own version</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-repurposed-audio-clips', 13, 'Snippet - no embeddable audio clips', 'There are currently no embeddable versions of this audio file - you may <a href="?url?">create the first embeddable version</a> if you wish.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-repurposed-video-intro', 13, 'Snippet - embeddable video intro', '', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-repurposed-audio-clip-intro', 13, 'Snippet - embeddable audio clip intro', '', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-audio-conversion', 13, 'Snippet - no audio conversion', 'You currently have no audio conversions in progress.</p><p>If you were in the middle of converting an audio clip then an error may have occurred with your conversion. Please return to the asset and try again.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-audio-conversion-error', 13, 'Snippet - audio conversion error', 'An error occurred during your audio conversion. If you specified the audio sampling rate you may have entered values that are incompatible with the output file format.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-converting-audio', 13, 'Snippet - Converting audio', 'Converting your audio clip. This may take a few minutes...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-converting-video', 13, 'Snippet - Converting video', 'Converting your video. This may take a few minutes...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-converted-file-size', 13, 'Snippet - Converted file size', 'Current converted file size:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-audio-conversion-finished', 13, 'Snippet - Audio conversion finished', 'Your audio conversion has finished', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-video-conversion-finished', 13, 'Snippet - Video conversion finished', 'Your video conversion has finished', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-media-download-links', 13, 'Snippet - Media download links', 'If your download doesn&#39;t start automatically please <a href="?downloadUrl?">right click here</a> and select &#39;Save As&#39;. Once you have finished downloading the file you can either <a href="?viewAssetUrl?">view the asset page</a> or select another option from the menu on the left hand side.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-boolean-and', 13, 'Snippet - Boolean AND', 'AND', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-boolean-or', 13, 'Snippet - Boolean OR', 'OR', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-general-keywords', 13, 'Snippet - General keywords', 'General Keywords', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-search-builder-query-changed', 13, 'Snippet - Search builder query changed', 'When searching using a date or numeric value, the clauses from this value onwards in the query must use the "AND" operator. The query has been changed to reflect this.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-note-about-force-unapproved', 13, 'Snippet - Note about forcing unapproved', 'Check to add the #item# to the approval workflow.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-contains', 13, 'Snippet - Operator: contains', 'contains word', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-not-contains', 13, 'Snippet - Operator: doesn''t contain', 'doesn''t contain word', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-equal-to', 13, 'Snippet - Operator: equal to', 'equal to', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-not-equal-to', 13, 'Snippet - Operator: not equal to', 'not equal to', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-before', 13, 'Snippet - Operator: before', 'before', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-after', 13, 'Snippet - Operator: after', 'after', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-less-than', 13, 'Snippet - Operator: less than', 'less than', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-op-more-than', 13, 'Snippet - Operator: more than', 'more than', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-message-from-previous', 13, 'Snippet - Message from previous', 'Message from previous user:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-message-to-next', 13, 'Snippet - Message to next', 'Message to next user:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-decide-later', 13, 'Snippet - Decide later', 'Decide later', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-invitations-sent', 13, 'Snippet - Invitations sent', 'Your invitations were sent', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-separate-multiple-emails', 13, 'Snippet - Separate multiple emails', 'Separate multiple email addresses with a comma.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-denotes-required-field', 13, 'Snippet - Denotes required field', 'denotes a required field', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-submit-option-live', 13, 'Snippet - Submit to live', 'Submit to live', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-submit-option-live-if-permitted', 13, 'Snippet - Submit to live if permissions allow', '(if permissions allow)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-submit-option-approval', 13, 'Snippet - Submit for approval', 'Submit for approval', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-submit-option-unsubmitted', 13, 'Snippet - Do not submit yet', 'Do not submit yet', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-upload-success-unsubmitted', 1, 'Snippet - Upload Success page unsubmitted asset', 'Your #item# has not yet been submitted.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-upload-unsubmitted', 1, 'Snippet - Bulk Upload page unsubmitted asset', 'The #items# from the last bulk upload were not submitted.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-alert-unsubmitted-asset', 1, 'Snippet - Alert you have an unsubmitted asset', 'You have <strong>1</strong> <a href="viewUnsubmittedAssets">unsubmitted #item#</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-alert-unsubmitted-assets', 1, 'Snippet - Alert you have some unsubmitted assets', 'You have <strong>?lNumUnsubmittedAssets?</strong> <a href="viewUnsubmittedAssets">unsubmitted #items#</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-alert-revised-asset', 1, 'Snippet - Alert you have an asset requiring revision', 'You have <strong>1</strong> <a href="viewOwnerAssetApproval">#item# requiring revision</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-alert-revised-assets', 1, 'Snippet - Alert you have some assets requiring revision', 'You have <strong>?lNumUnsubmittedWorkflowedAssets?</strong> <a href="viewOwnerAssetApproval">#items# requiring revision</a>.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-unsubmitted-asset', 1, 'Snippet - You have an unsubmitted asset', 'You have <strong>1</strong> unsubmitted #item#.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-unsubmitted-assets', 1, 'Snippet - You have some unsubmitted assets', 'You have <strong>?lNumUnsubmittedAssets?</strong> unsubmitted #items#.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'snippet-related-asset-lead-in', 13, 'Snippet - Related asset lead in', 'This #item# has', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-compare', 13, 'Snippet - Compare', 'View as contact sheet', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-embed-file-intro', 1, 'Snippet - Embed file introduction', 'The link and/or url below can be used to embed the file in an external website or application.', 1, 2); 
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-select-file-type', 13, 'Snippet - Select file type', 'The file extension for this file is used by more than one format - please select the format from the list below.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-actions', 13, 'Snippet - Actions...', 'More actions...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-notes-for', 13, 'Snippet - Notes for', 'Notes for', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-remove-from-lightbox', 13, 'Snippet - Remove from lightbox', 'Remove from #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-email-this-page', 13, 'Snippet - Email This Page', '<p>You can check and edit the email you are about to send in the preview below. Once you are happy with it just enter the email address to send to and click the send button. </p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-email-success', 13, 'Snippet - Email Success', '<p>Your email has been successfully sent.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-your-name', 13, 'Snippet - Your Name', '[your name]', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-defer-thumbnails-note', 13, 'Snippet - defer thumbnail generation note', '<p><em>Checking this means your #items# will be imported more quickly, but you may not see the thumbnails until later.</em></p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-too-many-related-assets-to-show', 13, 'Snippet - text shown when there are too many related items to show on the View Asset page', '<p>Not all related #items# have been shown, as there are too many. If you want to see all of them, please run a search.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-assets-ready', 13, 'Snippet - assets ready for download', 'Your assets are ready to download!', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-import-in-progress', 13, 'Snippet - Import in progress', 'The import is in progress...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-download-all-children-text', 13, 'Snippet - Download all children text', 'Click the ''#button-download-all#'' button to download all ?termForChildren? of this #item# as a zip file.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-mixed-res-lightbox', 13, 'Snippet - Mixed res lightbox', 'You do not have high-res download rights for some assets so high-res usage types have been disabled.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-low-res-only', 13, 'Snippet - Low-res only', 'Low-res download only', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-approved-for-high-res', 13, 'Snippet - Approved for high-res', 'Approved for high-res download', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-high-res-pending', 13, 'Snippet - High-res approval pending', 'High-res approval pending', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-request-high-res-download', 13, 'Snippet - Request high-res download', '', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-request-high-res-view', 13, 'Snippet - Request high-res view', 'You may download a low-res version of this image but will have to request approval for a high-res version', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-workflow-items-await-review', 13, 'Snippet - Workflow review screen - these items are currently awaiting approval', 'These #items# are currently awaiting approval.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-message-required', 13, 'Snippet - Workflow transition message required', 'message required', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-message-optional', 13, 'Snippet - Workflow transition message optional', 'please add a message below', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-download-requests-pending-approval', 13, 'Snippet - download requests pending approval', 'However, there are <a href="viewAssetApproval">download requests pending approval &raquo;</a>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-lightbox-too-large', 13, 'Snippet - Lightbox too large', 'This lightbox is too large to download. You should remove some items or use a smaller size.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-lightbox-too-large-request-cd', 13, 'Snippet - Lightbox too large request CD', 'You may also <a href="viewRequestAssetBox">request these assets on CD</a>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-empty-assets-only', 13, 'Snippet - Empty assets only', 'Empty assets only', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-non-empty-assets-only', 13, 'Snippet - Non-empty assets only', 'Non-empty assets only', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-choose-what-to-upload', 13, 'Snippet - Choose what to upload', 'Choose what you want to upload:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-new-assets-with-metadata', 13, 'Snippet - New assets with metadata', 'Standard import (add new assets with metadata)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-add-files-to-existing-assets', 13, 'Snippet - Add files to existing assets', 'Add files to existing assets', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-add-new-child-assets-to-parent', 13, 'Snippet - Add new child assets to parent', 'Add new child assets to existing parents', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-add-placeholders', 13, 'Snippet - Add Empty Assets', 'Add assets without files', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-insert-page-breaks', 13, 'Snippet - When printing, insert page breaks', 'When printing, insert a page break after every', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-page-break', 13, 'Snippet - Page Break', 'Page Break', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-inches-screen', 13, 'Snippet - Inches screen', 'inches (screen at ?screenDPI? DPI);', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-inches-print', 13, 'Snippet - Inches print', 'inches (print at ?printDPI? DPI);', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-thank-you-for-reactivating', 13, 'Snippet - Thank you for reactivating', 'Thank you for reactivating your account', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-account-suspended', 13, 'Snippet - Account suspended', 'Sorry, your account has been suspended.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-hidden-clause', 13, 'Snippet - Hidden Search Builder Clause', '<p>This search has hidden clauses that you do not have permission to edit. You can refine the search by adding your own clauses below:</p><p>Hidden clauses...</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-workflow-init', 13, 'Snippet - Workflow Initialisation', '<p>The asset was added to access levels associated with the following workflows, please select a submit option for each workflow and click the Save button:</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-workflow-removed', 13, 'Snippet - Workflow Removed', 'You have removed one or more access levels from the asset you just saved. This resulted in the asset being removed from workflows associated with the access levels in question.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-for', 13, 'Snippet - For', 'for', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-remove', 13, 'Snippet - remove (dropdown)', 'Remove', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-loading', 13, 'Snippet - Loading', 'Loading...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-how-dl-pdf', 13, 'Snippet - How to download pdf', 'Please select how you want to download <em>?assetName?</em>:', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-current-year', 13, 'Snippet - Current year', '2009', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-change-access-levels', 13, 'Snippet - Change access levels', 'Change #access-level-root#:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-change-categories', 13, 'Snippet - Change categories', 'Change #category-root#:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-of-selected-cats-als', 13, 'Snippet - Of selected categories/access levels', 'of the selected categories/access levels', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-view-as-slideshow', 13, 'Snippet - View as slideshow', 'View as slideshow', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-items-for-keyword', 13, 'Snippet - No items found for keyword', '<strong>#subhead-no-items-found#</strong> - Please choose another keyword:', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-display-time', 13, 'Snippet - Display time', 'Picture display time (seconds):', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-help', 10, 'Snippet - No help for this page', 'Sorry, there is no help for this page.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-navigate-away', 13, 'Snippet - Navigate away', 'You can navigate away from this page and return later without affecting the import.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-show-attribute-labels', 13, 'Snippet - Show attribute labels', 'Show attribute labels:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'current-file', 13, 'Snippet - Current File', 'Current file', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-value', 13, 'Snippet - No Value', 'no value', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-file-too-big', 13, 'Snippet - File too big for email', 'Sorry, the file is too big to be emailed as an attachment.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-email-addresses-comment', 13, 'Snippet - Email asset addresses comment', '(Separate addresses with ''?separator?'')', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-add-as-attachment', 13, 'Snippet - Add item as attachment', 'add this #item# as an attachment', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-link-to-item', 13, 'Snippet - Include link to item', 'include a link to this #item#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-email-asset-intro', 13, 'Snippet - Email asset(s) page intro', '', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-if-applicable', 13, 'Snippet - If applicable', 'if applicable', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-as-external-user', 13, 'Snippet - (External User)', '(External User)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-with-attributes', 13, 'Snippet - with attributes', 'with attributes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-without-attributes', 13, 'Snippet - without attributes', 'without attributes', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-1-image-per-page', 13, 'Snippet - 1 image per page', '1 image per page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-2-images-per-page', 13, 'Snippet - 2 images per page', '2 images per page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-4-images-per-page', 13, 'Snippet - 4 images per page', '4 images per page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-8-images-per-page', 13, 'Snippet - 8 images per page', '8 images per page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-16-images-per-page', 13, 'Snippet - 16 images per page', '16 images per page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-20-images-per-page', 13, 'Snippet - 20 images per page', '20 images per page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-only-id-shown', 13, 'Snippet - only id attribute can be shown', '(Please note that only the ID attribute can be shown with your currently selected page layout.)', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-currently-x-image-pp', 13, 'Snippet - currently x image per page', '(currently <strong>?numPerPage?</strong> image per page)', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-currently-x-images-pp', 13, 'Snippet - currently x images per page', '(currently <strong>?numPerPage?</strong> images per page)', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-email-sent', 13, 'Snippet - Email Sent Confirmation', '<p>Your email has been sent.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'snippet-email-upload-in-progress', 13, 'Snippet - Email Upload In Progress', 'Upload in progress...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'snippet-email-upload-navigate-away', 13, 'Snippet - Email Upload Navigate Away', 'you can safely navigate away from this page and the upload will continue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'snippet-email-failed', 13, 'Snippet - Email Failed', 'There was a failure trying to send your email...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-step-1', 13, 'Snippet - Step 1', 'Step 1:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-step-2', 13, 'Snippet - Step 2', 'Step 2:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-step-3', 13, 'Snippet - Step 3', 'Step 3:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-if-no-bg', 13, 'Snippet - If no bg colour selected', 'If no background colour is specified then the final image will adopt the original background colour of the mask (as seen above).', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-mask-selected', 13, 'Snippet - No mask selected', 'No mask selected.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-secondary-usage-note', 13, 'Snippet - Secondary usage note', '<p><strong>Please note:</strong> the sizes available for your chosen intended usage may not be appropriate for other uses.</p>', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-secondary-usage-intro', 13, 'Snippet - Secondary usage intro', 'Please specify any further intended uses for this asset:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-view-only-permission', 13, 'Snippet - view only permission', '', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-preview-being-generated', 13, 'Snippet - Preview being generated', 'The preview for this video is currently being generated.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'passwordHidden', 15, 'Snippet - Password hidden', 'Password hidden for security reasons', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-register-tsandcs', 13, 'Snippet - Register page terms & conditions link', 'I agree to comply with the <a href="../action/viewConditionsPopup?extra=true" class="help-popup" target="_blank" title="View Terms and Conditions in a new window">Terms and Conditions</a> of the use of #company-name#.', 1, 1);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-empty-related-assets-status', 13, 'Snippet - Empty related assets status', 'The creation of the empty related assets is in progress... <span>Follow the status in the log below:</span>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-empty-related-assets-complete', 13, 'Snippet - Empty related assets complete', '<p>The empty assets have all been created and linked to the source asset. <a href="?link?">Return to the source asset &raquo;</a></p>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-no-conversion', 13, 'Snippet - No conversion', 'No Conversion', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-category-extension-asset', 13, 'Snippet - Category extension asset', 'You are creating an extended category. Add an asset to associate with the category by completing the standard asset upload process below. Once complete you will be redirected back to the list of categories.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-asset-is-category-extension', 13, 'Snippet - Asset is category extension', 'Navigate back to the category <a href="?link?">?catName?</a>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-category-explorer', 13, 'Snippet - Category explorer', 'Select from the explorer on the left', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-thumbnail-bulk-update', 13, 'Snippet - thumbnail on bulk update', '(thumbnail will only be applied to non-image assets)', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-extension-asset-bulk-update', 13, 'Snippet - extension assets bulk update', '<p>Any category extension assets will be excluded from the update.</p>', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-indesign-create-new-template', 13, 'Snippet - Create this asset as a new InDesign template asset', 'Create new template', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-you-have', 13, 'Snippet - You have', 'You have', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-items-selected', 13, 'Snippet - items selected', 'items selected.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-top-level-x-files', 13, 'Snippet - Top-level (X files)', 'Top-level (?numFiles? files)', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-top-level-1-file', 13, 'Snippet - Top-level (1 file)', 'Top-level (?numFiles? file)', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-asset-is-indesign-template', 13, 'Snippet - This asset is a template', 'This asset is a template', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-indesign-pdf-status', 13, 'Snippet - InDesign PDF Status', 'PDF status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-create-new', 13, 'Snippet - Create new', 'Create new...', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-relationship-name-peer', 13, 'Snippet - Peer', 'Peer', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-relationship-name-child', 13, 'Snippet - Child', 'Child', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-relationship-to-asset', 13, 'Snippet - Relationship to asset', '?relationshipType? relationship to asset', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-next', 13, 'Snippet - Next:', 'Next:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-view-your-unsubmitted', 13, 'Snippet - View your unsubmitted items', 'View your unsubmitted items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-uploaded-not-submitted', 13, 'Snippet - Uploaded items not submitted', 'your uploaded items were not submitted', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-update-uploaded', 13, 'Snippet - Bulk update uploaded items', 'Bulk update your uploaded items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-bulk-update-summary', 13, 'Snippet - bulk update summary', 'make changes to multiple items at once', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-update-uploaded', 13, 'Snippet - batch update uploaded items', 'Batch update your uploaded items', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-batch-update-summary', 13, 'Snippet - batch update summary', 'add metadata to each item individually', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-finished-items-new-upload', 13, 'Snippet - finished with items, start new upload', 'Or, if you have finished with your uploaded items, you can start a new upload:', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-processing-item-for-download', 13, 'Snippet - Processing item for download', 'Processing #item# for download', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-error-processing-item-for-download', 13, 'Snippet - Error processing item for download', 'Error processing #item# ''[0]'' - the file will be excluded from the archive.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-default-option', 13, 'Snippet - "Default" for use in <option> tags', '- Default -', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-extended-category', 13, 'Snippet - Extended category', 'extended category', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-preparing-download', 13, 'Snippet - Preparing download...', 'Preparing download...', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-finished-preparing-download', 13, 'Snippet - Finished preparing download', 'Finished preparing download.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-creating-zip-files', 13, 'Snippet - Creating zip files', 'Creating zip file(s)...', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'snippet-search-results', 13, 'Snippet - Search Results', 'Search Results', 1, 3);

-- Javascript
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-asset', 14, 'Javascript - Confirm delete asset?', 'Are you sure you want to permanantly delete this #item#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-lightbox', 14, 'Javascript - Confirm delete lightbox', 'Are you sure you want to delete this #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-extension-asset', 14, 'Javascript - Confirm delete extension asset?', 'Are you sure you want to permenantly delete this #item#? This #item# extends a category, removing it will convert the category to a standard category. To remove the category and the extension asset together you need to go to category admin and delete the category itself.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-add-cat-items-lightbox', 14, 'Javascript - Add all items in category to lightbox', 'Are you sure you want to add all #items# in this category to your #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-add-x-cat-items-lightbox', 14, 'Javascript - Add x items in category to lightbox', 'Are you sure you want to add all the #items# in this category (limited to the first ?count?) to your #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-remove-all-lightbox', 14, 'Javascript - Confirm remove all from lightbox', 'Are you sure you want to remove all the #items# from your #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-link-assets', 14, 'Javascript - Confirm link assets', 'Are you sure you want to link these assets?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-remove-lightbox', 14, 'Javascript - Confirm remove lightbox', 'Are you sure you want to remove this #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-remove-all-promoted', 14, 'Javascript - Confirm remove all promoted', 'Are you sure you want to remove all promoted #items#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-add-search-lightbox', 14, 'Javascript - Add all search results to lightbox', 'Are you sure you want to add all #items# in your search results to your #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-add-x-search-lightbox', 14, 'Javascript - Add x search results to lightbox', 'Are you sure you want to add all the #items# in your search results (limited to the first ?count?) to your #a-lightbox#?', 1, 3);	
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-lose-changes', 14, 'Javascript - Confirm lose changes', 'Clicking this link after making changes to the details below will cause you to lose the changes you have made. Continue?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-add-users', 14, 'Javascript - Confirm add all selected users', 'Are you sure you want to add all selected users?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-remove-all-users', 14, 'Javascript - confirm remove all users', 'Are you sure you want to remove all users?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-saved-search', 14, 'Javascript - confirm delete saved search', 'Are you sure you want to delete this search?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-relationship', 14, 'Javascript - confirm delete relationship', 'Are you sure you want to delete this relationship?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-image-version', 14, 'Javascript - confirm delete image version', 'Are you sure you want to delete this version?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-keyword', 14, 'Javascript - confirm delete keyword', 'Are you sure you want to delete this keyword?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-agreement', 14, 'Javascript - Confirm delete agreement', 'Are you sure you want to delete this agreement?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-delete-asset-and-children', 14, 'Javascript - Confirm delete asset and children?', 'Are you sure you want to permanantly delete this ?entityName?? Any ?childNamePlural? that this ?entityName? has may also be deleted.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-add-user-with-notification', 14, 'Javascript - Confirm add a new user with notification', 'You have chosen to send a notification to the new user. Click ok to continue.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-add-user-without-notification', 14, 'Javascript - Confirm add a new user without notification', 'You have chosen not to notify the new user. Click ok to continue.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-reset-embed-status', 14, 'Javascript - Confirm reset embed status', 'Are you sure you want to prevent this file from being embedded?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-bulk-delete-confirm', 9, 'Javascript - Bulk delete confirm', 'You must confirm you understand that assets will be permanently deleted.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-remove-selected-lightbox', 14, 'Javascript - Confirm remove selected from lightbox', 'Are you sure you want to remove the selected #items# from your #a-lightbox#?', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-copy-asset', 14, 'Javascript - Confirm copy asset?', 'Are you sure you want to copy this #item#? Clicking this button will create a copy of the #item# and redirect you to the new #items# edit page.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'js-confirm-copy-asset-limit-exceeded', 14, 'Javascript - Confirm copy asset limit exceeded', 'Are you sure you want to copy this #item#? Clicking this button will create a copy of the #item# and setup relationships to all of the source #items# children.', 1, 3);

-- System messages
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidation', 15, 'failedValidation', 'Please complete all mandatory fields.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationForField', 15, 'failedValidationForField', 'Please enter a value for the field:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationForTitle', 15, 'failedValidationForTitle', 'Please enter a value for the field: Title', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationForename', 15, 'failedValidationForename', 'Please provide a valid forename.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationSurname', 15, 'failedValidationSurname', 'Please provide a valid surname.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationEmailAddress', 15, 'failedValidationEmailAddress', 'Please provide a valid email address.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationPasswords', 15, 'failedValidationPasswords', 'The password cannot be empty - please enter a value in all fields.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationPasswordsDontMatch', 15, 'failedValidationPasswordsDontMatch', 'The two new passwords you have entered do not match.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationGroupName', 15, 'failedValidationGroupName', 'You need to provide a name for the group.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationGroupDiscount', 15, 'failedValidationGroupDiscount', 'You need to specify the discount as a valid whole number.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationGroupDiscountPercent', 15, 'failedValidationGroupDiscountPercent', 'You need to specify the discount as a valid percentage between 0 and 100.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationPassword', 15, 'failedValidationPassword', 'You need to provide a password.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationDuplicateUsername', 15, 'failedValidationDuplicateUsername', 'The selected username is already in use - please try a different one, or contact us for a password reminder.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationExistingPassword', 15, 'failedValidationExistingPassword', 'Please provide the existing password as a security check.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationListValue', 15, 'failedValidationListValue', 'The list value cannot be blank.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationDateFormat', 15, 'failedValidationDateFormat', 'Please ensure dates are provided in the correct format.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationDateOrder', 15, 'failedValidationDateOrder', 'You need to make sure the start date is before the end date.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationReportType', 15, 'failedValidationReportType', 'You need to select a report type.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationUsageType', 15, 'failedValidationUsageType', 'The Usage Type value cannot be blank.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationSearchCriteria', 15, 'failedValidationSearchCriteria', 'Please specify at least one search criteria.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'searchCriteriaIdRangeTooLarge', 15, 'searchCriteriaIdRangeTooLarge', 'The id range is too large. The difference between the lower and upper id should be a maximum of', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationZipDir', 15, 'failedValidationZipDir', 'You must select a zip file OR a directory.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationUsage', 15, 'failedValidationUsage', 'Please select your intended usage from the list.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationUsageTypeFormat', 15, 'failedValidationUsageTypeFormat', 'Please make sure you have provided a description and that the width and height sizes are numbers (greater than zero).', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationUse', 15, 'failedValidationUse', 'Please enter your intended use in the box provided.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationConditions', 15, 'failedValidationConditions', 'Please indicate that you accept the Conditions of Use.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationFile', 15, 'failedValidationFile', 'You need to select a valid, non-empty file to upload.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationFileExtension', 15, 'failedValidationFileExtension', 'Files added to the Image Bank must have a file extension.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationFileTypeChange', 15, 'failedValidationFileTypeChange', 'You cannot change the file type of this asset.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationUsernameExists', 15, 'failedValidationUsernameExists', 'The selected username is already in use. Please select another.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationPrice', 15, 'failedValidationPrice', 'Please enter a valid value for price.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationTALength', 15, 'failedValidationTALength', 'The text area fields have a maximum length of [0]. Please adjust the content you are trying to save.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationMustAddGroup', 15, 'failedValidationMustAddGroup', 'You must assign the user to an org unit group so that you can manage them.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationActiveDirectoryUsernamePatternMatch', 15, 'failedValidationActiveDirectoryUsernamePatternMatch', 'The format of that username cannot be used since it is reserved for Active Directory users, please try a different username.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationEmailRuleDaysBefore', 15, 'failedValidationEmailRuleDaysBefore', 'The ''Number of days'' field must be a number zero or greater.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInvalidJpegQuality', 15, 'failedValidationInvalidJpegQuality', 'The JPEG Quality must be a number between 1 and 100', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationRecipients', 15, 'failedValidationRecipients', 'Please enter one or more recipients for this email, separated by semi-colons.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAssetBoxImageSizes', 15, 'failedValidationAssetBoxImageSizes', 'Please ensure that either the height or width fields (or both) contain a value. ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationCannotGeneratePassword', 15, 'failedValidationCannotGeneratePassword', 'You must specify a password if the user is not to be notified via email.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationBrandNotGiven', 15, 'failedValidationBrandNotGiven', 'Please select the group that you would like to register for.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationGroupNotGiven', 15, 'failedValidationGroupNotGiven', 'Please select the group that you would like to register for.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAccessLevel', 15, 'failedValidationAccessLevel', 'You need to make sure this image is in at least one access level.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationSecurePassword', 15, 'failedValidationSecurePassword', 'The password must contain at least one number, one capital letter and be a minimum of 8 characters long.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorMissingId', 15, 'categoryErrorMissingId', 'Category or CategoryTree Id is missing from form parameters.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorInvalidId', 15, 'categoryErrorInvalidId', 'The ID of the category or category tree to be displayed is invalid.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorBlankName', 15, 'categoryErrorBlankName', 'The name cannot be blank.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'accessLevelErrorBlankName', 15, 'accessLevelErrorBlankName', 'An access level may not have a blank name.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorParentIdMissing', 15, 'categoryErrorParentIdMissing', 'The parent id of the category to be added is missing.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorDuplicateName', 15, 'categoryErrorDuplicateName', 'A subcategory already exists in this category with the same name.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorDuplicateAccessLevelName', 15, 'categoryErrorDuplicateAccessLevelName', 'An access level already exists at this level with the same name.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorIdToDelete', 15, 'categoryErrorIdToDelete', 'The IdToDelete parameter is missing.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryErrorIdParam', 15, 'categoryErrorIdParam', 'The Id parameter is missing (used as root category when forward to CategoryAdminAction).', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'userErrorInvalidOldPassword', 15, 'userErrorInvalidOldPassword', 'The old password you entered is incorrect.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'userErrorPasswordNotificationFailed', 15, 'userErrorPasswordNotificationFailed', 'Unable to send password notification - the email address may be invalid. The password has not been updated.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'fileError', 15, 'fileError', 'There was a problem processing the file you provided. It is possible that the file is corrupt or has the wrong file extension.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'imageFileError', 15, 'imageFileError', 'There was a problem processing the file you provided. Please ensure the file is a valid, supported image file.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'uploadedFileNotFoundError', 15, 'uploadedFileNotFoundError', 'The uploaded file could not be found, as it has been deleted. This is likely to have happen because the time between uploading the file and submitting the metadata was too long.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'fileErrorPreview', 15, 'fileErrorPreview', 'It is not possible to preview this file format in a web browser. To view the converted image you must first download it to your computer.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'imageErrorZero', 15, 'imageErrorZero', 'The image height and width must be numeric and greater than 0', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'imageErrorMax', 15, 'imageErrorMax', 'The maximum height or width of a downloaded image cannot exceed ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetBoxNotAvailable', 15, 'assetBoxNotAvailable', 'The shared 0 you selected is no longer available.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetBoxRemoved', 15, 'assetBoxRemoved', 'The shared 0 you were using is no longer available.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetBoxErrorPermission', 15, 'assetBoxErrorPermission', 'You do not have download permission on any of the items in your lightbox.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetBoxesOutOfDate', 15, 'assetBoxesOutOfDate', 'Your available 0 have changed since the page was loaded - please try again.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'adminUserCannotBeInGroups', 15, 'adminUserCannotBeInGroups', 'If this user is an administrator then they cannot be in a group.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'relatedAssets', 15, 'relatedAssets', 'The links between the assets have been successfully created. This will be reflected when you view the detail page for any of the linked assets.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'relatedAssetsFailure', 15, 'relatedAssetsFailure', 'You need more than 1 #item# to setup links.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'relatedAssetSubset1', 15, 'relatedAssetSubset1', 'Your links have been setup.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'relatedAssetSubset2', 15, 'relatedAssetSubset2', 'of the assets were not included in the links because you don''t have permission to update them.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'relatedAssetSubset2Singular', 15, 'relatedAssetSubset2Singular', 'of the assets was not included in the links because you don''t have permission to update it.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'relatedAssetNoPermission', 15, 'relatedAssetNoPermission', 'The links could not be setup because you need to have permission to update at least 2 of the assets in your folder.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keywordErrorDuplicateName', 15, 'keywordErrorDuplicateName', 'That keyword is already in the list.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keywordErrorKeywordNotInList', 15, 'keywordErrorKeywordNotInList', '''%'' is not in the keyword list. Please change the keyword or add it to the master list. ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keywordErrorKeywordIsSynonym', 15, 'keywordErrorKeywordIsSynonym', '''%'' is a synonym of the following keyword(s): ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'keywordMissingTree', 15, 'keywordMissingTree', 'Unable to find the keyword tree to browse', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'requestUserUpdateOrgUnitLine', 15, 'requestUserUpdateOrgUnitLine', 'Selected Organisational Unit: ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedEmailSend', 15, 'failedEmailSend', 'The email could not be sent to the administrator, please try again. If this error continues please contact the administrator.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'ecommerceValidationSelectUsage', 15, 'ecommerceValidationSelectUsage', 'Please select either personal use or commercial use to continue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'taxInvalidPercent', 15, 'taxInvalidPercent', 'Please enter a valid number for tax percent.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subModelInvalidPrice', 15, 'subModelInvalidPrice', 'Please enter a valid price.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subModelInvalidNumDownloads', 15, 'subModelInvalidNumDownloads', 'Please enter a valid number for number of downloads.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subModelInvalidDuration', 15, 'subModelInvalidDuration', 'Please enter a valid number for duration.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subInvalidDate', 15, 'subInvalidDate', 'Please enter a valid start date, either today or in the future.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subInvalidModel', 15, 'subInvalidModel', 'Please select a subscription model.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subInvalidTsandcs', 15, 'subInvalidTsandcs', 'You must accept the conditions of the subscription to continue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subInvalidEmailAddress', 15, 'subInvalidEmailAddress', 'You do not have a valid email address - please update your profile.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subInvalidCountry', 15, 'subInvalidCountry', 'You do not have a valid country in your address - please update your profile.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subInvalidTaxLookup', 15, 'subInvalidTaxLookup', 'Tax details could not be found for your home country. Please contact us to resolve this.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'subNoDownloadsLeft', 15, 'subNoDownloadsLeft', 'You do not have enough downloads left on your subscription! Click the ''Subscriptions'' link to manage your subscriptions.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandInvalidBasePrice', 15, 'priceBandInvalidBasePrice', 'Please enter a valid value for the base price.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandInvalidUnitPrice', 15, 'priceBandInvalidUnitPrice', 'Please enter a valid value for the unit price.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandInvalidMaxQuantity', 15, 'priceBandInvalidMaxQuantity', 'Please enter a valid value for max quantity.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandInvalidShippingCost', 15, 'priceBandInvalidShippingCost', 'Please enter valid values for the shipping cost entries.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandInvalidQuantity', 15, 'priceBandInvalidQuantity', 'Please enter a valid quantity.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandMaxQuantity', 15, 'priceBandMaxQuantity', 'You can only request a maximum of % prints for each format', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandNoneSelected', 15, 'priceBandNoneSelected', 'Please select one of the options.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandMixedUsages', 15, 'priceBandMixedUsages', 'You cannot proceed with both commercial and non-commercial usages for this image. Please purchase images for commercial use separately than those from non-commercial use.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandInvalidName', 15, 'priceBandInvalidName', 'Please enter a name for the Price Band.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'priceBandNoQuantity', 15, 'priceBandNoQuantity', 'Please choose an option to purchase.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'GroupNoDownloadsLeft', 15, 'GroupNoDownloadsLeft', 'Sorry, you don''t have any downloads left for today. Please try tomorrow.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'AssetBoxNoDownloadsLeft', 15, 'AssetBoxNoDownloadsLeft', 'Sorry, these downloads take you over your daily limit. Please remove some items or try again tomorrow.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'commercialOptionInvalidPrice', 15, 'commercialOptionInvalidPrice', 'Please enter a valid value for the price.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'commercialOptionInvalidName', 15, 'commercialOptionInvalidName', 'Please enter a name for the Commercial Option.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'commercialPurchaseNoUserNotes', 15, 'commercialPurchaseNoUserNotes', 'You must enter some notes describing your intended uses.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInvalidFormatForCms', 15, 'failedValidationInvalidFormatForCms', 'The selected file is not of a format suitable for use in the CMS', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'thumbnailFileNotImage', 15, 'thumbnailFileNotImage', 'The thumbnail file is not in a valid format. Please upload an image file, for example JPEG, GIF, PNG.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'sortAttributeNotSelected', 15, 'sortAttributeNotSelected', 'You need to select the attribute that you want to sort by.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'displayAttributeNotSelected', 15, 'displayAttributeNotSelected', 'You need to select the attribute that you want to display', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'nonNumericDisplayLength', 15, 'nonNumericDisplayLength', 'You need to input a numeric display length', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'noAssetsSelectedForExport', 15, 'noAssetsSelectedForExport', 'There are no assets currently selected for export.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'noSearchResultsToExport', 15, 'noSearchResultsToExport', 'There are no search results to export.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetboxNameNotUnique', 15, 'assetboxNameNotUnique', 'The name you have entered is already in use.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetboxNameRequired', 15, 'assetboxNameRequired', 'Please enter a name.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'marketingGroupNameRequired', 15, 'marketingGroupNameRequired', 'Please enter a name for the marketing group.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'languageNameRequired', 15, 'languageNameRequired', 'Please enter a name for the language.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'languageCodeRequired', 15, 'languageCodeRequired', 'Please enter a code for the language.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'languageNameNotUnique', 15, 'languageNameNotUnique', 'The name you entered for the language is already in use.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'languageCodeNotUnique', 15, 'languageCodeNotUnique', 'The code you entered for the language is already in use.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAssetsLocked', 15, 'failedValidationAssetsLocked', 'One or more of the assets you are trying to update is locked. You will need to wait until other users have finished updating the assets you want to update.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoAssetsSelected', 15, 'failedValidationNoAssetsSelected', 'Please tick one or more of the assets you would like to include in a bulk update, or click cancel to return to the batch update home screen.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoAttributes', 15, 'failedValidationNoAttributes', 'Please select one or more attributes that you would like to update, or click cancel to return to the batch update home screen.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoAssetsFound', 15, 'failedValidationNoAssetsFound', 'That search did not find any assets that you can update. Please search again.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedLoginIPBlock', 15, 'failedLoginIPBlock', 'You have been prevented from logging in, since your IP address is not in the accepted range. Please contact your administrator.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'edmSelectAttribute', 15, 'edmSelectAttribute', 'You need to select the attribute for this mapping', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'edmSelectValue', 15, 'edmSelectValue', 'You need to select the embedded data value for this mapping', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'edmUploadsDownloads', 15, 'edmUploadsDownloads', 'You need to select whether this mapping applies to uploads or downloads', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'edmAlreadyExists', 15, 'edmAlreadyExists', 'This mapping already exists - please edit the existing entry', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'noFilterName', 15, 'noFilterName', 'You need to provide a name for this filter', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'customFieldName', 15, 'customFieldName', 'You need to provide a name for this custom field', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'customFieldType', 15, 'customFieldType', 'You need to provide a type for this custom field', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'customFieldUsageType', 15, 'customFieldUsageType', 'You need to provide a usage type for this custom field', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'customFieldValueValue', 15, 'customFieldValueValue', 'You need to provide a value', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'maxUploadSizeExceeded', 15, 'maxUploadSizeExceeded', 'The file you are trying to upload exceeds the maximum upload size.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'maxSavedSearchesReached', 15, 'maxSavedSearchesReached', 'You have reached the maximum number of saved searches - please delete one before proceeding.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoName', 15, 'failedValidationNoName', 'Please enter a name.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationSubjectBody', 15, 'failedValidationSubjectBody', 'If you enter either a subject or body, you must enter both.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationTemplateExists', 15, 'failedValidationTemplateExists', 'A template with that identifier already exists.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'noUsersSelectedForExport', 15, 'noUsersSelectedForExport', 'There are no users currently selectede for export.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationMarketingGroups', 15, 'failedValidationMarketingGroups', 'You need to select a marketing group.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoLayerSupport', 15, 'failedValidationNoLayerSupport', 'The output file format doesn&#39;t support multiple layers - please choose a single layer.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationWrongType', 15, 'failedValidationWrongType', 'This #item# type ([0]) cannot accept this type of media.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoType', 15, 'failedValidationNoType', 'This type of media is not supported in the current system configuration.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInvalidToRelationship', 15, 'failedValidationInvalidToRelationship', 'The #item# with id &#39;[0]&#39; is not allowed to participate in relationship &#39;[1]&#39;.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInvalidFromRelationship', 15, 'failedValidationInvalidFromRelationship', 'The #item# with id &#39;[0]&#39; is missing or of the wrong type to be added as a &#39;[1]&#39;.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInvalidIdList', 15, 'failedValidationInvalidIdList', 'Please ensure that where a list of ids is provided, it is formatted as a list of numbers separated by commas.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'cannotDeleteAssetEntity', 15, 'cannotDeleteAssetEntity', 'This type cannot be deleted as there are #items# currently assigned to it. Please export and delete the #items# first.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetEntityRelationshipNamesRequired', 15, 'assetEntityRelationshipNamesRequired', 'Please enter terms for all enabled relationships.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetEntityDefaultCategoryDoesNotExist', 15, 'assetEntityDefaultCategoryDoesNotExist', 'The default category that you have entered, [0] does not exist or is not a [1] category.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetEntityDefaultCategoryInWrongTree', 15, 'assetEntityDefaultCategoryInWrongTree', 'The default category that you have entered, [0] is not a [1] category. Please enter the ID of a [1] category (not a [2] category).', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationFileRequired', 15, 'failedValidationFileRequired', 'You must specify a file for this type of #item#.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'noAssetFilesToDownload', 15, 'noAssetFilesToDownload', 'None of the #items# in your #a-lightbox# have files that can be downloaded.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInsufficientQuota', 15, 'failedValidationInsufficientQuota', 'You cannot upload this file because your Org Unit(s) would exceed their file storage quotas - please contact an administrator to resolve this issue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationInsufficientOrgUnitQuota', 15, 'failedValidationInsufficientOrgUnitQuota', 'You cannot save this file because the Org Unit &#39;[0]&#39; would exceed its file storage quota - please contact an administrator to resolve this issue.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationFileFormatsRequireMediaTypes', 15, 'failedValidationFileFormatsRequireMediaTypes', 'File formats can only be specified when at least one media format is selected.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationCannotExcludeAndInclude', 15, 'failedValidationCannotExcludeAndInclude', 'You cannot add file formats to both the included and excluded list - once the included list has been populated, all other formats are implicitly excluded.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationMaxRepurposedImageSize', 15, 'failedValidationMaxRepurposedImageSize', 'The maximum height or width of an embeddable image cannot exceed ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationSensitivityNotesRequired', 15, 'failedValidationSensitivityNotesRequired', 'Please enter a reason for this item being sensitive.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAgreement', 15, 'failedValidationAgreement', 'Please indicate that you accept the Agreement.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAgreementTypeRequired', 15, 'failedValidationAgreementTypeRequired', 'You must specify an agreement type for this #item#.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAgreementTitle', 15, 'failedValidationAgreementTitle', 'Please enter a title for the agreement', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'agreementNameInUse', 15, 'agreementNameInUse', 'The name you have entered is already in use.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationGroupSize', 15, 'failedValidationGroupSize', 'You have exceeded the maximum download dimensions for the groups you are in', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationBrandTemplate', 15, 'failedValidationBrandTemplate', 'This asset cannot be used as a brand template because it does not contain an Acrobat PDF Form.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAutoincrement', 15, 'failedValidationAutoincrement', 'One of your selected autoincrement values has been used since you started working with this asset. Please try to save the asset again. The value has been updated for the field', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationAutoincrementNumber', 15, 'failedValidationAutoincrementNumber', 'One of your autoincrement values is not numeric', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'noPermissionUsageType', 15, 'noPermissionUsageType', 'There are no preset download options for the selected usage type.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'feedbackComments', 15, 'feedbackComments', 'You need to provide comments for your feedback entry.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationEmptyClause', 15, 'failedValidationEmptyClause', 'Please enter a value for all clauses.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetEntityHasRelationships', 15, 'assetEntityHasRelationships', 'This type cannot currently be deleted because it is referenced by one or more other type''s relationships. Please reconfigure or remove any parent types first.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNumberFormat', 15, 'failedValidationNumberFormat', 'Please ensure that numeric values are provided where appropriate.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'workflowMessageMandatory', 15, 'workflowMessageMandatory', 'Please enter a message when changing the state of an asset.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationValid', 15, 'failedValidationValid', 'Please enter a valid value for the field:', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoAssetsSelectedLightbox', 15, 'failedValidationNoAssetsSelected', 'You must select one or more #items# to perform an action on', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationOneAssetSelectedLightbox', 15, 'failedValidationOneAssetSelectedLightbox', 'You must select two or more images to compare', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNoUserDetailsSupplied', 15, 'failedValidationNoUserDetailsSupplied', 'You must supply the name and email address of an existing user to verify your registration.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationEmailNotValidForUser', 15, 'failedValidationEmailNotValidForUser', 'The email address supplied for verification does not belong to a registered user.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'waitingQueuedDownloads', 15, 'waitingQueuedDownloads', 'Waiting for queued downloads to complete. You are in the queue at position ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'waitingQueuedImports', 15, 'waitingQueuedImports', 'Other imports are currently in progress. Your import has been queued and will be started as soon as possible. You are in the queue at position ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'assetsExcludedFromDownload', 15, 'assetsExcludedFromDownload', 'You do not have permission to download ?count? of the assets in your #a-lightbox# in your selected format. Any that you don''t have permission on will be excluded from your zip file.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationForName', 15, 'failedValidationForName', 'Please enter your name.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationForAddress', 15, 'failedValidationForAddress', 'Please enter your address.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationForCDCustomField', 15, 'failedValidationForCDCustomField', 'Please enter your telephone number.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'thumbnailsNotYetGenerated', 15, 'thumbnailsNotYetGenerated', 'You cannot download a converted version of this image at present as its thumbnails have not yet been generated. Please try again later.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'failedValidationNumPlaceholders', 15, 'failedValidationNumPlaceholders', 'Please enter a valid number for Number to Add.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'submitOptionAllWorkflows', 15, 'submitOptionAllWorkflows', 'You need to provide submit options for all workflows when submitting an asset', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'submitOption', 15, 'submitOption', 'You need to select a submit option for this asset', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedDataLookup', 15, 'failedDataLookup', 'Error performing data lookup: ', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'assetsLockedWarning', 15, 'assetsLockedWarning', 'Note that ?numLockedMatches? matching #items# are currently locked by other users and so have not been added to your batch.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationSSSizes', 15, 'failedValidationSSSizes', 'Please check that the slideshow height and width are numeric', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationSizesPositive', 15, 'failedValidationSizesPositive', 'The height and width should be greater than zero', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationSizesPercentages', 15, 'failedValidationSizesPercentages', 'The height or width provided are not valid percentages', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationSizesPositiveNumber', 15, 'failedValidationSizesPositiveNumber', 'You need to provide a valid positive number for the height and width of the images', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationNoFilesToAdd', 15, 'failedValidationNoFilesToAdd', 'There are no files to add - please upload some files to the server.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationNumberFormatField', 15, 'failedValidationNumberFormatField', 'Field ''%'' can only accept numeric values.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationFractionDigits', 15, 'failedValidationFractionDigits', 'Field ''%'' can accept numbers with up to % decimal places.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationNotWholeNumber', 15, 'failedValidationNotWholeNumber', 'Field ''%'' can only accept whole numbers.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationHexColour', 15, 'failedValidationHexColour', '[0] cannot be parsed as a hex colour, please enter a colour in the format #rrggbb, for example #ffffff for white.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'NoPermissionToDownload', 15, 'NoPermissionToDownload', 'You do not have permission to download this asset.', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedLoginAsUser', 15, 'failedLoginAsUser', 'Unable to login as the given user', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationDefRelCatIdNAN', 15, 'failedValidationDefRelCatIdNAN', 'One of the default relationship category ids was not a number', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'failedValidationDefRelCatInvalid', 15, 'failedValidationDefRelCatInvalid', 'One of the default relationship categories was not valid - please check against your categories', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'cmsSessionError', 15, 'cmsSessionError', 'If you wish to select another image please close this window and return to the content management system.', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'categoryExtensionNameError', 15, 'categoryExtensionNameError', 'The asset''s name could not be assigned to the category because there is an existing category with the same name.', 1, 2);

-- Browser titles
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-login-page', 16, 'Title - Login page', '#company-name# | #login-title#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-home-page', 16, 'Title - Home page', '#company-name# | Home Page', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-lightbox', 16, 'Title - Lightbox', '#company-name# | #a-lightbox#: ?lightBoxName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-contact', 16, 'Title - Contact', '#company-name# | #heading-contact#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-content', 16, 'Title - Content', '#company-name# | ?extraContentPageName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-lightbox', 16, 'Title - Download Lightbox', '#company-name# | #heading-download# #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-lightbox-result', 16, 'Title - Download Lightbox  Result', '#company-name# | #my-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-failure', 16, 'Title - Download Failure', '#company-name# | #heading-download-failure#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-file', 16, 'Title - Download File', '#company-name# | #heading-download-file#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-image', 16, 'Title - Download Image', '#company-name# | #heading-download-image#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-email-item', 16, 'Title - Email item', '#company-name# | #heading-email-item#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-email-sent', 16, 'Title - Email sent', '#company-name# | #heading-email-sent#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-manage-lightboxes', 16, 'Title - Manage Lightboxes', '#company-name# | Manage #lightboxes#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-forgotten-password', 16, 'Title - Forgotten Password', '#company-name# | #heading-forgotten-password#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-preview-image', 16, 'Title - Preview Image', '#company-name# | #heading-download-image-preview#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-promoted-images', 16, 'Title - Promoted Images', '#company-name# | #heading-promoted#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-recent-images', 16, 'Title - Recent Images', '#company-name# | #heading-recent#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-register', 16, 'Title - Register', '#company-name# | #heading-register#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-rename-lightbox', 16, 'Title - Rename Lightbox', '#company-name# | Rename #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-request-cd', 16, 'Title - Request Cd', '#company-name# | #heading-request-cd#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-search-results', 16, 'Title - Search Results', '#company-name# | #heading-search-results#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-share-lightbox', 16, 'Title - Share Lightbox', '#company-name# | Share #a-lightbox#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-audio-details', 16, 'Title - Audio Details', '#company-name# | #heading-audio-details# - ?assetName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-asset-details', 16, 'Title - Asset Details', '#company-name# | #heading-asset-details# - ?assetName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-image-details', 16, 'Title - Image Details', '#company-name# | #heading-image-details# - ?assetName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-video-details', 16, 'Title - Video Details', '#company-name# | #heading-video-details# - ?assetName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-approve-items', 16, 'Title - Approve Items', '#company-name# | #heading-approve-items#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-request-approval', 16, 'Title - Request Approval', '#company-name# | #heading-request-approval#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-upload', 16, 'Title - Upload', '#company-name# | #heading-upload#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-batch-update-finished', 16, 'Title - Batch Update Finished', '#company-name# | #heading-batch-update-finished#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-batch-update', 16, 'Title - Batch Update', '#company-name# | #heading-batch-update#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-bulk-update', 16, 'Title - Bulk Update', '#company-name# | #heading-bulk-update#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-reindex', 16, 'Title - Reindex', '#company-name# | #heading-reindex#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-start-import', 16, 'Title - Start Import', '#company-name# | #heading-start-import#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-update', 16, 'Title - Update', '#company-name# | #heading-update#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-step1-upload', 16, 'Title - Step 1 Upload', '#company-name#  | #heading-step-1-upload#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-bulk-upload', 16, 'Title - Bulk Upload', '#company-name# | #heading-bulk-upload#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-browse', 16, 'Title - Browse', '#company-name# | #heading-browse# ?categoryName?', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-all-cats', 16, 'Title - All categories', '#company-name# | #heading-all-cats#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-not-found', 16, 'Title - Asset not found', '#company-name# | #heading-not-found#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-keywords', 16, 'Title - Keywords', '#company-name# | #heading-keywords#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-about', 16, 'Title - About', '#company-name# | #heading-about#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-your-password', 16, 'Title - Your Password', '#company-name# | #heading-your-password#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-password-changed', 16, 'Title - Password Changed', '#company-name# | #heading-password-changed#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-your-profile', 16, 'Title - Your Profile', '#company-name# | #heading-your-profile#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-profile-changed', 16, 'Title - Profile Changed', '#company-name# | #heading-profile-changed#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-approval-notes', 16, 'Title - Approval Notes', '#company-name# | #heading-approval-notes#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-conditions', 1, 'Title - Conditions', '#company-name# | Terms and Conditions', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-video-conversion-status', 16, 'Title - Video Conversion Status', 'Video Conversion Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-edit-content-item', 16, 'Title - Edit Content Item', '#company-name# | Edit Content Item', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-preview-file', 16, 'Title - Preview File', '#company-name# | Preview File', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-usage-info', 16, 'Title - Usage info', '#company-name# | View Usage Information', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-marketing-email-assets', 16, 'Title - Marketing Email Assets', 'Email Attachments', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-upload-success', 16, 'Title - Upload Success', '#company-name# | #heading-upload-success#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-saved-searches', 16, 'Title - Saved Searches', '#company-name# | #heading-saved-searches#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-save-search', 16, 'Title - Save Search', '#company-name# | #heading-save-search#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-asset-versions', 16, 'Title - Asset Versions', '#company-name# | #heading-asset-versions#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-manage-content', 16, 'Title - Manage content', '#company-name# | Manage Content', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-create-copy', 16, 'Title - Create copy', '#company-name# | #heading-create-copy#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-most-downloaded', 16, 'Title - Most Downloaded', '#company-name# | #heading-most-downloaded#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-least-downloaded', 16, 'Title - Least Downloaded', '#company-name# | #heading-least-downloaded#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-most-viewed', 16, 'Title - Most Viewed', '#company-name# | #heading-most-viewed#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-least-viewed', 16, 'Title - Least Viewed', '#company-name# | #heading-least-viewed#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-video', 16, 'Title - Download Video', '#company-name# | #heading-download-video#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-audit-info', 16, 'Title - Usage info', '#company-name# | View Audit Information', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-edit-relationships', 16, 'Title - Edit Relationships', '#company-name# | #heading-edit-relationships#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-edit-relationship', 16, 'Title - Edit Relationship', '#company-name# | #heading-edit-relationship#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-keyword-chooser', 16, 'Title - Keyword Chooser', '#company-name# | #heading-keyword-chooser#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-image', 16, 'Title - Embeddable Image', '#company-name# | #heading-repurposed-image#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-images', 16, 'Title - Embeddable Image Versions', '#company-name# | #heading-repurposed-image#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-print-image', 16, 'Title - Print Image', '#company-name# | #heading-print-image#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-agreements', 16, 'Title - Agreements', '#company-name# | #heading-agreements#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-video', 16, 'Title - Embeddable Video', '#company-name# | #heading-repurposed-video#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-videos', 16, 'Title - Embeddable Video Versions', '#company-name# | #heading-repurposed-videos#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-submit-feedback', 16, 'Title - Submit Feedback', '#company-name# | #heading-submit-feedback#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-audio-conversion-status', 16, 'Title - Audio Conversion Status', 'Audio Conversion Status', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-audio-clips', 16, 'Title - Embeddable Audio Clips', '#company-name# | #heading-repurposed-audio-clips#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-audio-clip', 16, 'Title - Embeddable Audio Clip', '#company-name# | #heading-repurposed-audio-clip#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-audio', 16, 'Title - Download Audio', '#company-name# | #heading-download-audio#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-compare-images', 16, 'Title - Compare images', '#company-name# | #heading-compare-images#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-embed-file', 16, 'Title - Embed File', '#company-name# | #heading-embed-file#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-email-this-page', 16, 'Title - Email This Page', '#company-name# | #heading-email-this-page#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-download-child-assets', 16, 'Title - download child assets', '#company-name# | #heading-download-child-assets#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-bulk-delete-confirm', 16, 'Title - Bulk delete confirm', '#company-name# | #heading-bulk-delete-confirm#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-search', 16, 'Title - Search', '#company-name# | #heading-search#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-initialise-workflow', 16, 'Title - Initialise Workflow', '#company-name# | #heading-initialise-workflow#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-slideshow', 16, 'Title - Slideshow', '#company-name# | #heading-slideshow#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-help', 16, 'Title - Help', '#company-name# | #heading-help#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurpose-slideshow', 16, 'Title - Create Embeddable Slideshow', '#company-name# | #heading-repurpose-slideshow#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-slideshows', 16, 'Title - Embeddable Slideshows', '#company-name# | #heading-repurposed-slideshows#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-repurposed-slideshow', 16, 'Title - Embeddable Slideshow', '#company-name# | #heading-repurposed-slideshow#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-contact-sheet', 16, 'Title - Contact Sheet', '#company-name# | #heading-contact-sheet#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'title-email-in-progress', 16, 'Title - Email in progress', '#company-name# | #heading-email-in-progress#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1, 'title-email-failed', 16, 'Title - Email Failed', '#company-name# | #heading-email-failed#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-browse-by-popularity', 16, 'Title - Browse by popularity', '#company-name# | #tab-browse-popularity#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-default-related-assets', 16, 'Title - Default related-assets', '#company-name# | #heading-default-related-assets#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-add-empty-related-assets', 16, 'Title - Add empty related assets', '#company-name# | #heading-add-empty-related-assets#', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'title-copy-asset-step1', 16, 'Title - Copy asset step 1', '#company-name# | #heading-copy-asset-step1#', 1, 2);

INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-embeddable-image-html', 17, 'Embeddable image HTML snippet', 'This is a snippet of html that may be provided for embedding an image. The snippet should include the variable: ?url?, and may also include ?height? and/or ?width?', '<img src="?url?" height="?height?" width="?width?"/>', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-embeddable-video-html', 17, 'Embeddable video HTML snippet', 'This is a snippet of html that may be provided for embedding a video. The snippet should include the variable: ?url?, and may also include ?height? and/or ?width?', '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="?width?" height="?height?"><param name="movie" value="?flvPlayerUrl?/player.swf" /><param name="flashvars" value="file=?url?&image=?previewFileLocation?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=uniform&provider=video&duration=?duration?"><param name="allowfullscreen" value="true" /><param name="scale" value="default" /><param name="bgcolor" value="111111" /><!--[if !IE]>--><object type="application/x-shockwave-flash" data="?flvPlayerUrl?/player.swf?file=?url?&image=?previewFileLocation?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=uniform&provider=video&duration=?duration?" width="?width?" height="?height?"><param name="allowfullscreen" value="true" /><param name="scale" value="default" /><param name="bgcolor" value="111111" /><!--<![endif]--><p>Your browser does not have the necessary plugin to display this content.</p><!--[if !IE]>--></object><!--<![endif]--></object>', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-open-id-prefix', 17, 'OpenID prefix', 'This is the standard prefix that will be applied to OpenIDs entered by users, and will be displayed on the OpenID login page', 'http://', 1, 2);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-embeddable-audio-html', 17, 'Embeddable audio HTML snippet', 'This is a snippet of html that may be provided for embedding an audio clip. The snippet should include the variable: ?url?', '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="300" height="24"><param name="movie" value="?flvPlayerUrl?/player.swf" /><param name="flashvars" value="file=?url?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=none&provider=video&duration=?duration?"><param name="scale" value="default" /><param name="bgcolor" value="111111" /><!--[if !IE]>--><object type="application/x-shockwave-flash" data="?flvPlayerUrl?/player.swf?file=?url?&backcolor=000000&frontcolor=EEEEEE&lightcolor=FFFFFF&screencolor=000000&autostart=false&stretching=none&provider=video&duration=?duration?" width="300" height="24"><!--<![endif]--><p>Your browser does not have the necessary plugin to display this content.</p><!--[if !IE]>--></object><!--<![endif]--></object>', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-embeddable-slideshow-html', 17, 'Embeddable slideshow HTML snippet', 'This is a snippet of html that may be provided for embedding a slideshow. The snippet should include the variable: ?url?', '<script type="text/javascript" src="?commonUrlStub?swfobject.js"></script><div id="flashcontent">The slideshow requires JavaScript and the Flash Player. <a href="http://www.macromedia.com/go/getflashplayer/">Get Flash here.</a></div><script type="text/javascript">var flashvars = {data:"?urlStub??url?", frameRate:60, backdropCol:"000", maskRadius:10, transitionStyle:"brightness", transitionTime:1, decayTime:0.6, displayTime:"?displayTime?", displayType:"sequence", infoBar:"?infoBar?", infoBarPosition:"imageTop", infoTextHtml:"on", controlBar:"on", infoBarOverlay:"off", autoTruncate:"off", autoResize:"on", embiggen:"off" }; var params = {wmode:"transparent",allowFullScreen:"true"}; var attributes = {}; swfobject.embedSWF("?commonUrlStub?gallery.swf?latest", "flashcontent", "?width?", "?height?", "9.0.0", "?commonUrlStub?expressinstall.swf", flashvars, params, attributes); </script>', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-embeddable-slideshow-download-html-header', 17, 'Embeddable slideshow download HTML header', 'This is a snippet of html used as a header for an example html file on download of an embeddable slideshow', '<html><head><title>Embedded Slideshow Example</title></head><body>', 1, 3);
INSERT INTO ListItem (LanguageId, Identifier, ListId, Title, Summary, Body, CannotBeDeleted, ListItemTextTypeID) VALUES (1,'technical-embeddable-slideshow-download-html-footer', 17, 'Embeddable slideshow download HTML footer', 'This is a snippet of html used as a footer for an example html file on download of an embeddable slideshow', '</body></html>', 1, 3);

-- SQLSERVER SET IDENTITY_INSERT Country ON;
INSERT INTO Country (Id, Name) VALUES (1,'Afghanistan');
INSERT INTO Country (Id, Name) VALUES (2,'Albania');
INSERT INTO Country (Id, Name) VALUES (3,'Algeria');
INSERT INTO Country (Id, Name) VALUES (4,'Andorra');
INSERT INTO Country (Id, Name) VALUES (5,'Angola');
INSERT INTO Country (Id, Name) VALUES (6,'Antigua and Barbuda');
INSERT INTO Country (Id, Name) VALUES (7,'Argentina');
INSERT INTO Country (Id, Name) VALUES (8,'Armenia');
INSERT INTO Country (Id, Name) VALUES (9,'Australia');
INSERT INTO Country (Id, Name) VALUES (10,'Austria');
INSERT INTO Country (Id, Name) VALUES (11,'Azerbaijan');
INSERT INTO Country (Id, Name) VALUES (12,'Bahamas');
INSERT INTO Country (Id, Name) VALUES (13,'Bahrain');
INSERT INTO Country (Id, Name) VALUES (14,'Bangladesh');
INSERT INTO Country (Id, Name) VALUES (15,'Barbados');
INSERT INTO Country (Id, Name) VALUES (16,'Belarus');
INSERT INTO Country (Id, Name) VALUES (17,'Belgium');
INSERT INTO Country (Id, Name) VALUES (18,'Belize');
INSERT INTO Country (Id, Name) VALUES (19,'Benin');
INSERT INTO Country (Id, Name) VALUES (20,'Bhutan');
INSERT INTO Country (Id, Name) VALUES (21,'Bolivia');
INSERT INTO Country (Id, Name) VALUES (22,'Bosnia and Herzegovina');
INSERT INTO Country (Id, Name) VALUES (23,'Botswana');
INSERT INTO Country (Id, Name) VALUES (24,'Brazil');
INSERT INTO Country (Id, Name) VALUES (25,'Brunei');
INSERT INTO Country (Id, Name) VALUES (26,'Bulgaria');
INSERT INTO Country (Id, Name) VALUES (27,'Burkina Faso');
INSERT INTO Country (Id, Name) VALUES (28,'Burundi');
INSERT INTO Country (Id, Name) VALUES (29,'Cambodia');
INSERT INTO Country (Id, Name) VALUES (30,'Cameroon');
INSERT INTO Country (Id, Name) VALUES (31,'Canada');
INSERT INTO Country (Id, Name) VALUES (32,'Cape Verde');
INSERT INTO Country (Id, Name) VALUES (33,'Central African Republic');
INSERT INTO Country (Id, Name) VALUES (34,'Chad');
INSERT INTO Country (Id, Name) VALUES (35,'Chile');
INSERT INTO Country (Id, Name) VALUES (36,'China');
INSERT INTO Country (Id, Name) VALUES (37,'Colombia');
INSERT INTO Country (Id, Name) VALUES (38,'Comoros');
INSERT INTO Country (Id, Name) VALUES (39,'Congo');
INSERT INTO Country (Id, Name) VALUES (40,'Congo, Democratic Republic of the');
INSERT INTO Country (Id, Name) VALUES (41,'Costa Rica');
INSERT INTO Country (Id, Name) VALUES (42,'Croatia');
INSERT INTO Country (Id, Name) VALUES (43,'Cuba');
INSERT INTO Country (Id, Name) VALUES (44,'Cyprus');
INSERT INTO Country (Id, Name) VALUES (45,'Czech Republic');
INSERT INTO Country (Id, Name) VALUES (46,'Cote d Ivoire');
INSERT INTO Country (Id, Name) VALUES (47,'Denmark');
INSERT INTO Country (Id, Name) VALUES (48,'Djibouti');
INSERT INTO Country (Id, Name) VALUES (49,'Dominica');
INSERT INTO Country (Id, Name) VALUES (50,'Dominican Republic');
INSERT INTO Country (Id, Name) VALUES (51,'East Timor');
INSERT INTO Country (Id, Name) VALUES (52,'Ecuador');
INSERT INTO Country (Id, Name) VALUES (53,'Egypt');
INSERT INTO Country (Id, Name) VALUES (54,'El Salvador');
INSERT INTO Country (Id, Name) VALUES (55,'Equatorial Guinea');
INSERT INTO Country (Id, Name) VALUES (56,'Eritrea');
INSERT INTO Country (Id, Name) VALUES (57,'Estonia');
INSERT INTO Country (Id, Name) VALUES (58,'Ethiopia');
INSERT INTO Country (Id, Name) VALUES (59,'Fiji');
INSERT INTO Country (Id, Name) VALUES (60,'Finland');
INSERT INTO Country (Id, Name) VALUES (61,'France');
INSERT INTO Country (Id, Name) VALUES (62,'Gabon');
INSERT INTO Country (Id, Name) VALUES (63,'Gambia, The');
INSERT INTO Country (Id, Name) VALUES (64,'Georgia');
INSERT INTO Country (Id, Name) VALUES (65,'Germany');
INSERT INTO Country (Id, Name) VALUES (66,'Ghana');
INSERT INTO Country (Id, Name) VALUES (67,'Greece');
INSERT INTO Country (Id, Name) VALUES (68,'Grenada');
INSERT INTO Country (Id, Name) VALUES (69,'Guatemala');
INSERT INTO Country (Id, Name) VALUES (70,'Guinea');
INSERT INTO Country (Id, Name) VALUES (71,'Guinea-Bissau');
INSERT INTO Country (Id, Name) VALUES (72,'Guyana');
INSERT INTO Country (Id, Name) VALUES (73,'Haiti');
INSERT INTO Country (Id, Name) VALUES (74,'Honduras');
INSERT INTO Country (Id, Name) VALUES (75,'Hungary');
INSERT INTO Country (Id, Name) VALUES (76,'Iceland');
INSERT INTO Country (Id, Name) VALUES (77,'India');
INSERT INTO Country (Id, Name) VALUES (78,'Indonesia');
INSERT INTO Country (Id, Name) VALUES (79,'Iran');
INSERT INTO Country (Id, Name) VALUES (80,'Iraq');
INSERT INTO Country (Id, Name) VALUES (81,'Ireland');
INSERT INTO Country (Id, Name) VALUES (82,'Israel');
INSERT INTO Country (Id, Name) VALUES (83,'Italy');
INSERT INTO Country (Id, Name) VALUES (84,'Jamaica');
INSERT INTO Country (Id, Name) VALUES (85,'Japan');
INSERT INTO Country (Id, Name) VALUES (86,'Jordan');
INSERT INTO Country (Id, Name) VALUES (87,'Kazakhstan');
INSERT INTO Country (Id, Name) VALUES (88,'Kenya');
INSERT INTO Country (Id, Name) VALUES (89,'Kiribati');
INSERT INTO Country (Id, Name) VALUES (90,'Korea, North');
INSERT INTO Country (Id, Name) VALUES (91,'Korea, South');
INSERT INTO Country (Id, Name) VALUES (92,'Kuwait');
INSERT INTO Country (Id, Name) VALUES (93,'Kyrgyzstan');
INSERT INTO Country (Id, Name) VALUES (94,'Laos');
INSERT INTO Country (Id, Name) VALUES (95,'Latvia');
INSERT INTO Country (Id, Name) VALUES (96,'Lebanon');
INSERT INTO Country (Id, Name) VALUES (97,'Lesotho');
INSERT INTO Country (Id, Name) VALUES (98,'Liberia');
INSERT INTO Country (Id, Name) VALUES (99,'Libya');
INSERT INTO Country (Id, Name) VALUES (100,'Liechtenstein');
INSERT INTO Country (Id, Name) VALUES (101,'Lithuania');
INSERT INTO Country (Id, Name) VALUES (102,'Luxembourg');
INSERT INTO Country (Id, Name) VALUES (103,'Macedonia, Former Yugoslav Republic of');
INSERT INTO Country (Id, Name) VALUES (104,'Madagascar');
INSERT INTO Country (Id, Name) VALUES (105,'Malawi');
INSERT INTO Country (Id, Name) VALUES (106,'Malaysia');
INSERT INTO Country (Id, Name) VALUES (107,'Maldives');
INSERT INTO Country (Id, Name) VALUES (108,'Mali');
INSERT INTO Country (Id, Name) VALUES (109,'Malta');
INSERT INTO Country (Id, Name) VALUES (110,'Marshall Islands');
INSERT INTO Country (Id, Name) VALUES (111,'Mauritania');
INSERT INTO Country (Id, Name) VALUES (112,'Mauritius');
INSERT INTO Country (Id, Name) VALUES (113,'Mexico');
INSERT INTO Country (Id, Name) VALUES (114,'Micronesia, Federated States of');
INSERT INTO Country (Id, Name) VALUES (115,'Moldova');
INSERT INTO Country (Id, Name) VALUES (116,'Monaco');
INSERT INTO Country (Id, Name) VALUES (117,'Mongolia');
INSERT INTO Country (Id, Name) VALUES (118,'Morocco');
INSERT INTO Country (Id, Name) VALUES (119,'Mozambique');
INSERT INTO Country (Id, Name) VALUES (120,'Myanmar');
INSERT INTO Country (Id, Name) VALUES (121,'Namibia');
INSERT INTO Country (Id, Name) VALUES (122,'Nauru');
INSERT INTO Country (Id, Name) VALUES (123,'Nepal');
INSERT INTO Country (Id, Name) VALUES (124,'Netherlands');
INSERT INTO Country (Id, Name) VALUES (125,'New Zealand');
INSERT INTO Country (Id, Name) VALUES (126,'Nicaragua');
INSERT INTO Country (Id, Name) VALUES (127,'Niger');
INSERT INTO Country (Id, Name) VALUES (128,'Nigeria');
INSERT INTO Country (Id, Name) VALUES (129,'Norway');
INSERT INTO Country (Id, Name) VALUES (130,'Oman');
INSERT INTO Country (Id, Name) VALUES (131,'Pakistan');
INSERT INTO Country (Id, Name) VALUES (132,'Palau');
INSERT INTO Country (Id, Name) VALUES (133,'Panama');
INSERT INTO Country (Id, Name) VALUES (134,'Papua New Guinea');
INSERT INTO Country (Id, Name) VALUES (135,'Paraguay');
INSERT INTO Country (Id, Name) VALUES (136,'Peru');
INSERT INTO Country (Id, Name) VALUES (137,'Philippines');
INSERT INTO Country (Id, Name) VALUES (138,'Poland');
INSERT INTO Country (Id, Name) VALUES (139,'Portugal');
INSERT INTO Country (Id, Name) VALUES (140,'Qatar');
INSERT INTO Country (Id, Name) VALUES (141,'Romania');
INSERT INTO Country (Id, Name) VALUES (142,'Russia');
INSERT INTO Country (Id, Name) VALUES (143,'Rwanda');
INSERT INTO Country (Id, Name) VALUES (144,'Saint Kitts and Nevis');
INSERT INTO Country (Id, Name) VALUES (145,'Saint Lucia');
INSERT INTO Country (Id, Name) VALUES (146,'Saint Vincent and The Grenadines');
INSERT INTO Country (Id, Name) VALUES (147,'Samoa');
INSERT INTO Country (Id, Name) VALUES (148,'San Marino');
INSERT INTO Country (Id, Name) VALUES (149,'Sao Tome and Principe');
INSERT INTO Country (Id, Name) VALUES (150,'Saudi Arabia');
INSERT INTO Country (Id, Name) VALUES (151,'Senegal');
INSERT INTO Country (Id, Name) VALUES (152,'Serbia and Montenegro');
INSERT INTO Country (Id, Name) VALUES (153,'Seychelles');
INSERT INTO Country (Id, Name) VALUES (154,'Sierra Leone');
INSERT INTO Country (Id, Name) VALUES (155,'Singapore');
INSERT INTO Country (Id, Name) VALUES (156,'Slovakia');
INSERT INTO Country (Id, Name) VALUES (157,'Slovenia');
INSERT INTO Country (Id, Name) VALUES (158,'Solomon Islands');
INSERT INTO Country (Id, Name) VALUES (159,'Somalia');
INSERT INTO Country (Id, Name) VALUES (160,'South Africa');
INSERT INTO Country (Id, Name) VALUES (161,'Spain');
INSERT INTO Country (Id, Name) VALUES (162,'Sri Lanka');
INSERT INTO Country (Id, Name) VALUES (163,'Sudan');
INSERT INTO Country (Id, Name) VALUES (164,'Suriname');
INSERT INTO Country (Id, Name) VALUES (165,'Swaziland');
INSERT INTO Country (Id, Name) VALUES (166,'Sweden');
INSERT INTO Country (Id, Name) VALUES (167,'Switzerland');
INSERT INTO Country (Id, Name) VALUES (168,'Syria');
INSERT INTO Country (Id, Name) VALUES (169,'Taiwan');
INSERT INTO Country (Id, Name) VALUES (170,'Tajikistan');
INSERT INTO Country (Id, Name) VALUES (171,'Tanzania');
INSERT INTO Country (Id, Name) VALUES (172,'Thailand');
INSERT INTO Country (Id, Name) VALUES (173,'Togo');
INSERT INTO Country (Id, Name) VALUES (174,'Tonga');
INSERT INTO Country (Id, Name) VALUES (175,'Trinidad and Tobago');
INSERT INTO Country (Id, Name) VALUES (176,'Tunisia');
INSERT INTO Country (Id, Name) VALUES (177,'Turkey');
INSERT INTO Country (Id, Name) VALUES (178,'Turkmenistan');
INSERT INTO Country (Id, Name) VALUES (179,'Tuvalu');
INSERT INTO Country (Id, Name) VALUES (180,'Uganda');
INSERT INTO Country (Id, Name) VALUES (181,'Ukraine');
INSERT INTO Country (Id, Name) VALUES (182,'United Arab Emirates');
INSERT INTO Country (Id, Name) VALUES (183,'United Kingdom');
INSERT INTO Country (Id, Name) VALUES (184,'United States');
INSERT INTO Country (Id, Name) VALUES (185,'Uruguay');
INSERT INTO Country (Id, Name) VALUES (186,'Uzbekistan');
INSERT INTO Country (Id, Name) VALUES (187,'Vanuatu');
INSERT INTO Country (Id, Name) VALUES (188,'Vatican City');
INSERT INTO Country (Id, Name) VALUES (189,'Venezuela');
INSERT INTO Country (Id, Name) VALUES (190,'Vietnam');
INSERT INTO Country (Id, Name) VALUES (191,'Western Sahara');
INSERT INTO Country (Id, Name) VALUES (192,'Yemen');
INSERT INTO Country (Id, Name) VALUES (193,'Zambia');
INSERT INTO Country (Id, Name) VALUES (194,'Zimbabwe');
INSERT INTO Country (Id, Name) VALUES (207,'Hong Kong');
INSERT INTO Country (Id, Name) VALUES (208,'Puerto Rico');
INSERT INTO Country (Id, Name) VALUES (300,'Dubai');
-- SQLSERVER SET IDENTITY_INSERT Country OFF;

UPDATE Country set SequenceNumber=10;

-- SQLSERVER SET IDENTITY_INSERT InDesignAssetEntityType ON;
INSERT INTO InDesignAssetEntityType (Id, Name) VALUES (1, 'InDesign Document');
INSERT INTO InDesignAssetEntityType (Id, Name) VALUES (2, 'Symbol');
INSERT INTO InDesignAssetEntityType (Id, Name) VALUES (3, 'Image');
-- SQLSERVER SET IDENTITY_INSERT InDesignAssetEntityType OFF;

-- SQLSERVER SET IDENTITY_INSERT InDesignPDFQuality ON;
INSERT INTO InDesignPDFQuality (Id, Name) values (1, 'Normal');
INSERT INTO InDesignPDFQuality (Id, Name) values (2, 'Barcode');
-- SQLSERVER SET IDENTITY_INSERT InDesignPDFQuality OFF;

-- SQLSERVER SET IDENTITY_INSERT InDesignPDFStatus ON;
INSERT INTO InDesignPDFStatus (Id, Name) VALUES (1, 'No Source File');
INSERT INTO InDesignPDFStatus (Id, Name) VALUES (2, 'Needs Generation');
INSERT INTO InDesignPDFStatus (Id, Name) VALUES (3, 'Generation Failed');
INSERT INTO InDesignPDFStatus (Id, Name) VALUES (4, 'Up to Date');
-- SQLSERVER SET IDENTITY_INSERT InDesignPDFStatus OFF;

-- SQLSERVER SET IDENTITY_INSERT InDesignPDFJobStatus ON;
INSERT INTO InDesignPDFJobStatus (Id, Name) VALUES (1, 'Pending');
INSERT INTO InDesignPDFJobStatus (Id, Name) VALUES (2, 'Running');
-- there is no 'Complete' status because jobs are deleted from the table when they are complete
-- SQLSERVER SET IDENTITY_INSERT InDesignPDFJobStatus OFF;

-- SQLSERVER SET IDENTITY_INSERT ContentPurpose ON;
INSERT INTO ContentPurpose (Id, Name) VALUES (1, 'Extra Content Page');
-- SQLSERVER SET IDENTITY_INSERT ContentPurpose OFF;


-- SQLSERVER GO
