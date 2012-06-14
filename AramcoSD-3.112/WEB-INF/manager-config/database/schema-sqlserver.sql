/*
Created		16/08/2004
Modified		06/03/2008
Project		
Model			
Company		
Author		
Version		
Database		MS SQL 2000 
*/

















Create table [Asset]
(
	[Id] Integer NOT NULL,
	[CurrentVersionId] Integer NULL,
	[AssetTypeId] Integer NOT NULL,
	[AddedByUserId] Integer NULL,
	[LastModifiedByUserId] Integer NULL,
	[FileFormatId] Integer NULL,
	[FileLocation] Nvarchar(255) NOT NULL,
	[OriginalFileLocation] Nvarchar(255) NULL,
	[FileSizeInBytes] Integer NOT NULL,
	[SmallFileLocation] Nvarchar(255) NULL,
	[ThumbnailFileLocation] Nvarchar(255) NULL,
	[MediumFileLocation] Nvarchar(255) NULL,
	[DateCreated] Datetime NULL,
	[DateAdded] Datetime NULL,
	[DateLastModified] Datetime NULL,
	[ExpiryDate] Datetime NULL,
	[Author] Nvarchar(255) NULL,
	[Code] Nvarchar(255) NULL,
	[IsApproved] Tinyint NULL,
	[Price] Integer NULL,
	[ImportedAssetId] Nvarchar(255) Default NULL NULL,
	[Synchronised] Tinyint Default 0 NOT NULL,
	[IsPreviewRestricted] Tinyint Default 0 NOT NULL,
	[BulkUploadTimestamp] Datetime NULL,
	[VersionNumber] Integer Default 1 NOT NULL,
Primary Key ([Id])
) 
go

Create table [AssetBox]
(
	[Id] Integer NOT NULL,
	[UserId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[SequenceNumber] Integer Default 1 NOT NULL,
Primary Key ([Id])
) 
go

Create table [AssetBankUser]
(
	[Id] Integer NOT NULL,
	[LastModifiedById] Integer NULL,
	[Username] Nvarchar(100) NOT NULL,
	[Password] Nvarchar(100) NULL,
	[EmailAddress] Nvarchar(255) NULL,
	[Title] Nvarchar(100) NULL,
	[Forename] Nvarchar(100) NULL,
	[Surname] Nvarchar(100) NULL,
	[Organisation] Nvarchar(255) NULL,
	[Address] Nvarchar(2000) NULL,
	[Telephone] Nvarchar(200) NULL,
	[Fax] Nvarchar(200) NULL,
	[RegistrationInfo] Ntext NULL,
	[IsDeleted] Tinyint Default 0 NOT NULL,
	[IsAdmin] Tinyint Default 0 NOT NULL,
	[IsSuspended] Tinyint NULL,
	[DisplayName] Nvarchar(255) NULL,
	[CN] Nvarchar(255) NULL,
	[DistinguishedName] Nvarchar(255) NULL,
	[Mailbox] Nvarchar(255) NULL,
	[NotActiveDirectory] Tinyint NULL,
	[Hidden] Tinyint Default 0 NULL,
	[NotApproved] Tinyint Default 0 NULL,
	[RequiresUpdate] Tinyint Default 0 NULL,
	[CanBeCategoryAdmin] Tinyint Default 0 NULL,
	[ExpiryDate] Datetime NULL,
	[RegisterDate] Datetime NULL,
	[JobTitle] Nvarchar(255) NULL,
	[RequestedOrgUnitId] Integer NULL,
	[DivisionId] Integer NULL,
	[AddressId] Integer NULL,
	[VATNumber] Nvarchar(100) NULL,
	[LDAPServerIndex] Smallint NULL,
	[ReceiveAlerts] Tinyint NULL,
	[CanLoginFromCms] Tinyint NULL,
	[DateChangedPassword] Datetime NULL,
	[Website] Char(200) NULL,
	[LanguageId] Integer Default 1 NOT NULL,
	[AdminNotes] Ntext NULL,
Primary Key ([Id])
) 
go

Create table [CM_Category]
(
	[Id] Integer NOT NULL,
	[ParentId] Integer NULL,
	[CategoryTypeId] Integer NOT NULL,
	[Name] Nvarchar(2000) NOT NULL,
	[Description] Ntext NULL,
	[SequenceNumber] Integer NULL,
	[Summary] Nvarchar(255) NULL,
	[IsBrowsable] Tinyint NOT NULL,
	[IsRestrictive] Tinyint Default 0 NOT NULL,
	[BitPosition] Integer NULL,
	[IsListboxCategory] Tinyint Default 0 NULL,
	[CannotBeDeleted] Tinyint NOT NULL,
	[Synchronised] Tinyint Default 0 NOT NULL,
	[SelectedOnLoad] Tinyint Default 0 NULL,
	[AllowAdvancedOptions] Tinyint Default 1 NULL,
Primary Key ([Id])
) 
go

Create table [AttributeValue]
(
	[Id] Integer NOT NULL,
	[AttributeId] Integer NOT NULL,
	[Value] Ntext NULL,
	[DateValue] Datetime NULL,
	[DateTimeValue] Datetime NULL,
	[SequenceNumber] Integer NULL,
	[IsEditable] Tinyint Default 1 NOT NULL,
	[AssetId] Integer NULL,
	[MapToFieldValue] Nvarchar(200) NULL,
	[FilterValue] Tinyint Default 0 NULL,
Primary Key ([Id])
) 
go

Create table [AssetAttributeValue]
(
	[AttributeValueId] Integer NOT NULL,
	[AttributeId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
Primary Key ([AttributeValueId],[AssetId])
) 
go

Create table [Attribute]
(
	[Id] Integer NOT NULL,
	[AttributeTypeId] Integer NULL,
	[SequenceNumber] Integer NOT NULL,
	[Label] Nvarchar(255) NOT NULL,
	[IsKeywordSearchable] Tinyint Default 0 NOT NULL,
	[IsMandatory] Tinyint Default 0 NOT NULL,
	[IsSearchField] Tinyint Default 0 NOT NULL,
	[DefaultValue] Nvarchar(255) NULL,
	[ValueIfNotVisible] Nvarchar(255) NULL,
	[IsStatic] Tinyint NULL,
	[StaticFieldName] Nvarchar(200) NULL,
	[IsReadOnly] Tinyint NULL,
	[MaxDisplayLength] Integer Default -1 NULL,
	[TreeId] Integer NULL,
	[HelpText] Nvarchar(255) NULL,
	[Highlight] Tinyint NULL,
	[NameAttribute] Tinyint Default 0 NULL,
	[DefaultKeywordFilter] Nvarchar(5) NULL,
	[OnChangeScript] Ntext NULL,
	[IsHtml] Tinyint NULL,
	[IsRequiredForCompleteness] Tinyint Default 0 NULL,
	[RequiresTranslation] Tinyint Default 0 NULL,
	[DisplayName] Nvarchar(255) NULL,
	[BaseUrl] Nvarchar(255) NULL,
	[AltText] Nvarchar(255) NULL,
Primary Key ([Id])
) 
go

Create table [CM_CategoryType]
(
	[Id] Integer NOT NULL,
	[Description] Nvarchar(50) NULL,
	[IsAlphabeticOrder] Tinyint NOT NULL,
	[IsNameGloballyUnique] Tinyint NOT NULL,
Primary Key ([Id])
) 
go

Create table [AssetBoxAsset]
(
	[AssetBoxId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
	[AddedByUserId] Integer NULL,
	[TimeAdded] Datetime NOT NULL,
Primary Key ([AssetBoxId],[AssetId])
) 
go

Create table [CM_ItemInCategory]
(
	[CategoryId] Integer NOT NULL,
	[ItemId] Integer NOT NULL,
Primary Key ([CategoryId],[ItemId])
) 
go

Create table [UserGroup]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(60) NOT NULL,
	[Description] Nvarchar(255) NULL,
	[IsDefaultGroup] Tinyint Default 0 NOT NULL,
	[Mapping] Nvarchar(2000) NULL,
	[BrandId] Integer NULL,
	[IPMapping] Nvarchar(2000) NULL,
	[URLMapping] Nvarchar(2000) NULL,
	[DiscountPercentage] Integer NULL,
	[CanOnlyEditOwn] Tinyint Default 0 NOT NULL,
	[DailyDownloadLimit] Integer Default 0 NOT NULL,
Primary Key ([Id])
) 
go

Create table [AttributeType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(60) NOT NULL,
Primary Key ([Id])
) 
go

Create table [AssetType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(60) NOT NULL,
Primary Key ([Id])
) 
go

Create table [ImageAsset]
(
	[AssetId] Integer NOT NULL,
	[Height] Integer NULL,
	[Width] Integer NULL,
	[ColorSpace] Integer NULL,
	[LargeFileLocation] Nvarchar(255) NULL,
	[FeaturedFileLocation] Nvarchar(255) NULL,
	[NumLayers] Integer Default 1 NULL,
Primary Key ([AssetId])
) 
go

Create table [CategoryVisibleToGroup]
(
	[UserGroupId] Integer NOT NULL,
	[CategoryId] Integer NOT NULL,
	[CanDownloadAssets] Tinyint Default 0 NOT NULL,
	[CanDownloadWithApproval] Tinyint Default 0 NOT NULL,
	[CanUpdateAssets] Tinyint Default 0 NOT NULL,
	[CanUpdateWithApproval] Tinyint Default 0 NOT NULL,
	[CanDeleteAssets] Tinyint Default 0 NOT NULL,
	[CanApproveAssets] Tinyint Default 0 NOT NULL,
	[CantDownloadOriginal] Tinyint Default 0 NOT NULL,
	[CantDownloadAdvanced] Tinyint Default 0 NOT NULL,
	[CanEditSubcategories] Tinyint Default 0 NOT NULL,
Primary Key ([UserGroupId],[CategoryId])
) 
go

Create table [GroupAttributeExclusion]
(
	[AttributeId] Integer NOT NULL,
	[UserGroupId] Integer NOT NULL,
	[Value] Nvarchar(255) NOT NULL,
Primary Key ([AttributeId],[UserGroupId],[Value])
) 
go

Create table [AttributeVisibleToGroup]
(
	[UserGroupId] Integer NOT NULL,
	[AttributeId] Integer NOT NULL,
	[IsWriteable] Tinyint Default 0 NOT NULL,
Primary Key ([UserGroupId],[AttributeId])
) 
go

Create table [UserInGroup]
(
	[UserId] Integer NOT NULL,
	[UserGroupId] Integer NOT NULL,
Primary Key ([UserId],[UserGroupId])
) 
go

Create table [UsageType]
(
	[Id] Integer NOT NULL,
	[AssetTypeId] Integer NULL,
	[Description] Nvarchar(255) NULL,
	[SequenceNumber] Integer NOT NULL,
	[IsEditable] Tinyint Default 1 NOT NULL,
	[ParentId] Integer NULL,
	[DownloadTabId] Integer Default 0 NOT NULL,
	[CanEnterDetails] Tinyint NULL,
	[DetailsMandatory] Tinyint NULL,
	[Message] Nvarchar(2000) NULL,
	[DownloadOriginal] Tinyint NULL,
Primary Key ([Id])
) 
go

Create table [AssetUse]
(
	[UserId] Integer NULL,
	[UsageTypeId] Integer NULL,
	[TimeOfDownload] Datetime NOT NULL,
	[OriginalDescription] Nvarchar(255) NULL,
	[OtherDescription] Nvarchar(255) NULL,
	[AssetDownloadTypeId] Integer NOT NULL,
	[AssetId] Integer NOT NULL
) 
go

Create table [AssetDownloadType]
(
	[Id] Integer NOT NULL,
	[Description] Nvarchar(60) NOT NULL,
Primary Key ([Id])
) 
go

Create table [FileFormat]
(
	[Id] Integer NOT NULL,
	[AssetTypeId] Integer NOT NULL,
	[FileExtension] Nvarchar(20) NOT NULL, UNIQUE ([FileExtension]),
	[IsIndexable] Tinyint Default 0 NOT NULL,
	[IsConvertable] Tinyint Default 0 NOT NULL,
	[IsConversionTarget] Tinyint Default 0 NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Description] Nvarchar(255) NULL,
	[ThumbnailFileLocation] Nvarchar(255) NULL,
	[ContentType] Nvarchar(100) NULL,
	[ConverterClass] Nvarchar(255) NULL,
	[ToTextConverterClass] Nvarchar(255) NULL,
	[PreviewInclude] Nvarchar(255) NULL,
	[PreviewHeight] Integer NULL,
	[PreviewWidth] Integer NULL,
	[ConvertIndividualLayers] Tinyint Default 0 NULL,
Primary Key ([Id])
) 
go

Create table [SystemSetting]
(
	[Id] Nvarchar(20) NOT NULL,
	[Value] Nvarchar(200) NULL,
Primary Key ([Id])
) 
go

Create table [AssetView]
(
	[AssetId] Integer NOT NULL,
	[Time] Datetime NOT NULL,
	[UserId] Integer NULL
) 
go

Create table [AssetApproval]
(
	[AssetId] Integer NOT NULL,
	[UsageTypeId] Integer NULL,
	[UserId] Integer NOT NULL,
	[ApprovalStatusId] Integer NOT NULL,
	[DateSubmitted] Datetime NULL,
	[DateApproved] Datetime NULL,
	[DateExpires] Datetime NULL,
	[AdminNotes] Ntext NULL,
	[UserNotes] Ntext NULL,
Primary Key ([AssetId],[UserId])
) 
go

Create table [ApprovalStatus]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(60) NULL,
Primary Key ([Id])
) 
go

Create table [OrgUnit]
(
	[Id] Integer NOT NULL,
	[RootOrgUnitCategoryId] Integer NOT NULL, UNIQUE ([RootOrgUnitCategoryId]),
	[AdminGroupId] Integer NOT NULL,
	[StandardGroupId] Integer NOT NULL,
Primary Key ([Id])
) 
go

Create table [OrgUnitGroup]
(
	[UserGroupId] Integer NOT NULL,
	[OrgUnitId] Integer NOT NULL,
Primary Key ([UserGroupId],[OrgUnitId])
) 
go

Create table [ABOrder]
(
	[Id] Integer NOT NULL,
	[UserId] Integer NOT NULL,
	[DatePlaced] Datetime NULL,
	[SubTotal] Integer NULL,
	[VATPercent] Float NULL,
	[Total] Integer NULL,
	[PSPTransId] Nvarchar(255) NULL,
	[PurchaseId] Nvarchar(255) NULL,
	[OrderStatusId] Integer NOT NULL,
	[ShippingAddressId] Integer NULL,
	[RecipientName] Nvarchar(255) NULL,
	[DateFulfilled] Datetime NULL,
	[UserNotes] Ntext NULL,
	[PrefersOfflinePayment] Tinyint Default 0 NOT NULL,
	[ShippingCost] Integer NULL,
	[BasketCost] Integer NULL,
	[DiscountPercentage] Integer NULL,
Primary Key ([Id])
) 
go

Create table [AssetPurchaseLog]
(
	[ABOrderId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
	[Description] Nvarchar(200) NULL,
	[Price] Integer NULL,
	[ShippingCost] Integer NULL,
Primary Key ([ABOrderId],[AssetId])
) 
go

Create table [SearchReport]
(
	[SearchTerm] Nvarchar(250) NULL,
	[FullQuery] Nvarchar(2000) NULL,
	[Success] Tinyint NULL,
	[SearchDate] Datetime NULL
) 
go

Create table [UsageTypeFormat]
(
	[Id] Integer NOT NULL,
	[FormatId] Integer NOT NULL,
	[UsageTypeId] Integer NULL,
	[Description] Nvarchar(200) NULL,
	[ImageWidth] Integer NULL,
	[ImageHeight] Integer NULL,
	[ScaleUp] Tinyint Default 0 NULL,
	[Density] Integer NULL,
	[JpegQuality] Integer NULL,
	[PreserveFormatList] Nvarchar(255) NULL,
	[ApplyStrip] Tinyint NULL,
	[OmitIfLowerRes] Tinyint Default 0 NULL,
	[CropToFit] Tinyint Default 0 NULL,
Primary Key ([Id])
) 
go

Create table [ChangeAttributeValueDateRule]
(
	[Id] Integer NOT NULL,
	[AttributeId] Integer NOT NULL,
	[RuleName] Nvarchar(200) NULL,
	[ChangeAttributeId] Integer NOT NULL,
	[NewAttributeValue] Nvarchar(255) NULL,
	[Enabled] Tinyint Default 0 NOT NULL,
Primary Key ([Id])
) 
go

Create table [SendEmailDateRule]
(
	[Id] Integer NOT NULL,
	[AttributeId] Integer NOT NULL,
	[RuleName] Nvarchar(200) NULL,
	[DaysBefore] Integer Default 0 NULL,
	[EmailText] Ntext NULL,
	[Enabled] Tinyint Default 0 NOT NULL,
Primary Key ([Id])
) 
go

Create table [GroupEmailRule]
(
	[RuleId] Integer NOT NULL,
	[UserGroupId] Integer NOT NULL,
Primary Key ([RuleId],[UserGroupId])
) 
go

Create table [RelatedAsset]
(
	[ParentId] Integer NOT NULL,
	[ChildId] Integer NOT NULL,
Primary Key ([ParentId],[ChildId])
) 
go

Create table [GroupUsageExclusion]
(
	[UsageTypeId] Integer NOT NULL,
	[UserGroupId] Integer NOT NULL,
Primary Key ([UsageTypeId],[UserGroupId])
) 
go

Create table [List]
(
	[Id] Integer NOT NULL,
	[Identifier] Nvarchar(100) NULL,
	[Name] Nvarchar(255) NULL,
	[Description] Nvarchar(255) NULL,
	[CannotAddNew] Tinyint NULL,
	[NoHTMLMarkup] Tinyint Default 0 NULL,
Primary Key ([Id])
) 
go

Create table [ListItem]
(
	[Identifier] Nvarchar(200) NOT NULL,
	[LanguageId] Integer Default 1 NOT NULL,
	[ListId] Integer NOT NULL,
	[Title] Nvarchar(255) NOT NULL,
	[Summary] Ntext NULL,
	[Body] Ntext NULL,
	[DateAdded] Datetime NULL,
	[CannotBeDeleted] Tinyint NULL,
	[ListItemTextTypeId] Integer Default 1 NOT NULL,
Primary Key ([Identifier],[LanguageId])
) 
go

Create table [Division]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([Id])
) 
go

Create table [PromotedAsset]
(
	[AssetId] Integer NOT NULL,
	[DatePromoted] Datetime NULL,
Primary Key ([AssetId])
) 
go

Create table [SubscriptionModel]
(
	[Id] Integer NOT NULL,
	[Description] Nvarchar(250) NULL,
	[Price] Integer NULL,
	[NoOfDownloads] Integer NULL,
	[Duration] Integer NULL,
	[Inactive] Tinyint NOT NULL,
Primary Key ([Id])
) 
go

Create table [Subscription]
(
	[Id] Integer NOT NULL,
	[StartDate] Datetime NULL,
	[SubscriptionModelId] Integer NOT NULL,
	[UserId] Integer NOT NULL,
	[IsActive] Tinyint Default 0 NOT NULL,
	[PricePaid] Integer NULL,
Primary Key ([Id])
) 
go

Create table [GroupInSubscriptionModel]
(
	[SubscriptionModelId] Integer NOT NULL,
	[UserGroupId] Integer NOT NULL,
Primary Key ([SubscriptionModelId],[UserGroupId])
) 
go

Create table [Address]
(
	[Id] Integer NOT NULL,
	[CountryId] Integer NULL,
	[Address1] Nvarchar(200) NULL,
	[Address2] Nvarchar(200) NULL,
	[Town] Nvarchar(100) NULL,
	[County] Nvarchar(100) NULL,
	[Postcode] Nvarchar(50) NULL,
Primary Key ([Id])
) 
go

Create table [Country]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[SequenceNumber] Integer NULL,
	[NativeName] Nvarchar(255) NULL,
Primary Key ([Id])
) 
go

Create table [TaxType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(200) NULL,
Primary Key ([Id])
) 
go

Create table [TaxValue]
(
	[TaxTypeId] Integer NOT NULL,
	[TaxRegionId] Integer NOT NULL,
	[Percent] Float NULL,
	[ZeroIfTaxNumberGiven] Tinyint NOT NULL,
Primary Key ([TaxTypeId],[TaxRegionId])
) 
go

Create table [TaxRegion]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([Id])
) 
go

Create table [CountryInTaxRegion]
(
	[TaxRegionId] Integer NOT NULL,
	[CountryId] Integer NOT NULL,
Primary Key ([TaxRegionId],[CountryId])
) 
go

Create table [SubscriptionModelUpgrade]
(
	[FromId] Integer NOT NULL,
	[ToId] Integer NOT NULL,
	[Price] Integer NULL,
Primary Key ([FromId],[ToId])
) 
go

Create table [VideoAsset]
(
	[AssetId] Integer NOT NULL,
	[PreviewClipLocation] Nvarchar(255) NULL,
	[EmbeddedPreviewClipLocation] Nvarchar(255) NULL,
Primary Key ([AssetId])
) 
go

Create table [SortAttribute]
(
	[AttributeId] Integer NOT NULL,
	[SortAreaId] Integer NOT NULL,
	[Sequence] Integer NULL,
	[Type] Integer NULL,
	[Reverse] Tinyint Default 0 NULL
) 
go

Create table [SortArea]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(200) NULL,
Primary Key ([Id])
) 
go

Create table [FeaturedAsset]
(
	[AssetId] Integer NOT NULL,
	[DateFeatured] Datetime NULL,
Primary Key ([AssetId])
) 
go

Create table [PriceBand]
(
	[Id] Integer NOT NULL,
	[AssetTypeId] Integer NOT NULL,
	[PriceBandTypeId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Description] Nvarchar(255) NULL,
	[BasePrice] Integer NULL,
	[UnitPrice] Integer NULL,
	[DownloadOriginal] Tinyint NULL,
	[MaxQuantity] Integer Default 0 NOT NULL,
	[IsCommercial] Tinyint NULL,
Primary Key ([Id])
) 
go

Create table [PriceBandType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([Id])
) 
go

Create table [PriceBandUsage]
(
	[UsageTypeId] Integer NOT NULL,
	[PriceBandId] Integer NOT NULL,
Primary Key ([UsageTypeId],[PriceBandId])
) 
go

Create table [ShippingCost]
(
	[Price] Integer NULL,
	[TaxRegionId] Integer NOT NULL,
	[PriceBandId] Integer NOT NULL,
	[QuantityRangeId] Integer NOT NULL,
Primary Key ([TaxRegionId],[PriceBandId],[QuantityRangeId])
) 
go

Create table [QuantityRange]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(200) NULL,
	[LowerLimit] Integer NULL,
Primary Key ([Id])
) 
go

Create table [AssetBoxPriceBand]
(
	[AssetBoxId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
	[PriceBandId] Integer NOT NULL,
	[Quantity] Integer Default 0 NULL,
Primary Key ([AssetBoxId],[AssetId],[PriceBandId])
) 
go

Create table [CommercialOption]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Description] Ntext NULL,
	[Price] Integer NULL,
	[Terms] Ntext NULL,
	[IsDisabled] Tinyint NOT NULL,
Primary Key ([Id])
) 
go

Create table [OrderStatus]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[OrderWorkflowId] Integer NOT NULL,
	[ManualSelect] Tinyint NOT NULL,
Primary Key ([Id])
) 
go

Create table [AssetPurchasePriceBand]
(
	[PriceBandId] Integer NOT NULL,
	[ABOrderId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
	[Quantity] Integer NULL,
	[PriceBandTypeId] Integer NULL,
	[PriceBandName] Nvarchar(255) NULL,
	[PriceBandTypeName] Nvarchar(255) NULL,
	[Cost] Integer NULL,
	[ShippingCost] Integer NULL,
Primary Key ([PriceBandId],[ABOrderId],[AssetId])
) 
go

Create table [OrderWorkflow]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(20) NULL,
Primary Key ([Id])
) 
go

Create table [CommercialOptionPurchase]
(
	[CommercialOptionId] Integer NOT NULL,
	[PriceBandId] Integer NOT NULL,
	[ABOrderId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
	[Price] Integer NULL,
	[Description] Ntext NULL,
Primary Key ([PriceBandId],[ABOrderId],[AssetId])
) 
go

Create table [Brand]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(100) NULL,
	[Code] Nvarchar(50) NOT NULL, UNIQUE ([Code]),
	[CssFile] Nvarchar(100) NULL,
	[LogoFile] Nvarchar(100) NULL,
	[LogoWidth] Integer NULL,
	[LogoHeight] Integer NULL,
	[LogoAlt] Nvarchar(100) NULL,
	[ContentListIdentifier] Nvarchar(200) NULL,
Primary Key ([Id])
) 
go

Create table [FeaturedAssetInBrand]
(
	[BrandId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
Primary Key ([BrandId],[AssetId])
) 
go

Create table [AudioAsset]
(
	[AssetId] Integer NOT NULL,
	[PreviewClipLocation] Nvarchar(255) NULL,
	[EmbeddedPreviewClipLocation] Nvarchar(255) NULL,
Primary Key ([AssetId])
) 
go

Create table [ListItemTextType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([Id])
) 
go

Create table [Role]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(200) NOT NULL,
	[Description] Nvarchar(200) NULL,
	[Identifier] Nvarchar(50) NOT NULL, UNIQUE ([Identifier]),
Primary Key ([Id])
) 
go

Create table [GroupIsRole]
(
	[UserGroupId] Integer NOT NULL,
	[RoleId] Integer NOT NULL,
Primary Key ([UserGroupId],[RoleId])
) 
go

Create table [Language]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NOT NULL, UNIQUE ([Name]),
	[NativeName] Nvarchar(255) NULL,
	[Code] Nvarchar(20) NOT NULL, UNIQUE ([Code]),
	[IsSuspended] Tinyint NOT NULL,
	[IsDefault] Tinyint Default 0 NOT NULL,
	[IsRightToLeft] Tinyint Default 0 NOT NULL,
	[IconFilename] Nvarchar(255) NULL,
	[UsesLatinAlphabet] Tinyint Default 1 NOT NULL,
Primary Key ([Id])
) 
go

Create table [EmailTemplate]
(
	[TextId] Nvarchar(255) NULL,
	[LanguageId] Integer NOT NULL,
	[TypeId] Integer NOT NULL,
	[Code] Nvarchar(50) NULL,
	[AddrFrom] Nvarchar(255) NULL,
	[AddrTo] Nvarchar(255) NULL,
	[AddrCc] Nvarchar(255) NULL,
	[AddrBcc] Nvarchar(255) NULL,
	[Subject] Nvarchar(255) NULL,
	[Body] Ntext NULL,
	[Hidden] Tinyint Default 0 NULL
) 
go

Create table [DisplayAttribute]
(
	[DisplayLength] Integer NULL,
	[AttributeId] Integer NOT NULL,
	[SequenceNumber] Integer NULL,
	[ShowLabel] Tinyint Default 0 NULL,
	[IsLink] Tinyint Default 0 NULL,
Primary Key ([AttributeId])
) 
go

Create table [AssetBoxShare]
(
	[AssetBoxId] Integer NOT NULL,
	[UserId] Integer NOT NULL,
	[CanEdit] Tinyint Default 0 NOT NULL,
	[SequenceNumber] Integer Default 0 NOT NULL,
	[Alias] Nvarchar(255) NULL,
Primary Key ([AssetBoxId],[UserId])
) 
go

Create table [EmbeddedDataMapping]
(
	[EmbeddedDataValueId] Integer NOT NULL,
	[AttributeId] Integer NOT NULL,
	[MappingDirectionId] Integer NOT NULL,
	[Delimiter] Nvarchar(50) NULL,
	[Sequence] Integer NULL,
Primary Key ([EmbeddedDataValueId],[AttributeId],[MappingDirectionId])
) 
go

Create table [EmbeddedDataValue]
(
	[Id] Integer NOT NULL,
	[EmbeddedDataTypeId] Integer NOT NULL,
	[Name] Nvarchar(100) NULL,
	[Expression] Nvarchar(250) NULL,
Primary Key ([Id])
) 
go

Create table [EmbeddedDataType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(100) NOT NULL,
Primary Key ([Id])
) 
go

Create table [MappingDirection]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(100) NULL,
Primary Key ([Id])
) 
go

Create table [ScheduledReport]
(
	[Id] Integer NOT NULL,
	[NextSendDate] Datetime NULL,
	[ReportPeriod] Nvarchar(100) NULL,
	[ReportType] Nvarchar(100) NULL,
	[ReportName] Nvarchar(200) NULL,
Primary Key ([Id])
) 
go

Create table [ScheduledReportGroup]
(
	[ReportId] Integer NOT NULL,
	[GroupId] Integer NOT NULL,
Primary Key ([ReportId],[GroupId])
) 
go

Create table [Filter]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(200) NULL,
	[IsDefault] Tinyint Default 0 NULL,
Primary Key ([Id])
) 
go

Create table [FilterAttributeValue]
(
	[FilterId] Integer NOT NULL,
	[AttributeValueId] Integer NOT NULL,
	[ListAttribute] Tinyint Default 0 NULL,
Primary Key ([FilterId],[AttributeValueId])
) 
go

Create table [MarketingGroup]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(60) NOT NULL,
	[Description] Nvarchar(255) NULL,
	[Purpose] Nvarchar(255) NULL,
	[IsHiddenInDefaultLanguage] Tinyint Default 0 NOT NULL,
Primary Key ([Id])
) 
go

Create table [UserInMarketingGroup]
(
	[MarketingGroupId] Integer NOT NULL,
	[UserId] Integer NOT NULL,
Primary Key ([MarketingGroupId],[UserId])
) 
go

Create table [TranslatedAttribute]
(
	[AttributeId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Label] Nvarchar(255) NULL,
	[DefaultValue] Nvarchar(255) NULL,
	[ValueIfNotVisible] Nvarchar(255) NULL,
	[HelpText] Nvarchar(255) NULL,
	[AltText] Nvarchar(255) NULL,
	[DisplayName] Nvarchar(255) NULL,
Primary Key ([AttributeId],[LanguageId])
) 
go

Create table [TranslatedAttributeValue]
(
	[LanguageId] Integer NOT NULL,
	[AttributeValueId] Integer NOT NULL,
	[Value] Ntext NULL,
Primary Key ([LanguageId],[AttributeValueId])
) 
go

Create table [TranslatedCategory]
(
	[CategoryId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(2000) NULL,
	[Description] Ntext NULL,
	[Summary] Nvarchar(255) NULL,
Primary Key ([CategoryId],[LanguageId])
) 
go

Create table [TranslatedUsageType]
(
	[UsageTypeId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Description] Nvarchar(255) NULL,
	[Message] Nvarchar(2000) NULL,
Primary Key ([UsageTypeId],[LanguageId])
) 
go

Create table [TranslatedUsageTypeFormat]
(
	[UsageTypeFormatId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Description] Nvarchar(200) NULL,
Primary Key ([UsageTypeFormatId],[LanguageId])
) 
go

Create table [TranslatedFilter]
(
	[FilterId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(200) NULL,
Primary Key ([FilterId],[LanguageId])
) 
go

Create table [CustomFieldUsageType]
(
	[Id] Integer NOT NULL,
	[Description] Nvarchar(100) NULL,
Primary Key ([Id])
) 
go

Create table [CustomField]
(
	[FieldName] Nvarchar(100) NULL,
	[UsageTypeId] Integer NOT NULL,
	[TypeId] Integer NOT NULL,
	[Id] Integer NOT NULL,
	[Required] Tinyint Default 0 NULL,
Primary Key ([Id])
) 
go

Create table [CustomFieldType]
(
	[Id] Integer NOT NULL,
	[Description] Nvarchar(50) NULL,
Primary Key ([Id])
) 
go

Create table [CustomFieldValue]
(
	[Id] Integer NOT NULL,
	[CustomFieldId] Integer NOT NULL,
	[Value] Nvarchar(200) NULL,
Primary Key ([Id])
) 
go

Create table [CustomFieldValueMapping]
(
	[ListValue] Integer NULL,
	[CustomFieldId] Integer NOT NULL,
	[ItemId] Integer NOT NULL,
	[TextValue] Nvarchar(2000) NULL
) 
go

Create table [SavedSearch]
(
	[Name] Nvarchar(255) NOT NULL,
	[UserId] Integer NOT NULL,
	[Keywords] Nvarchar(255) NULL,
	[FullQuery] Nvarchar(2000) NOT NULL,
	[HttpQueryString] Nvarchar(2000) NULL,
Primary Key ([Name],[UserId])
) 
go

Create table [TranslatedMarketingGroup]
(
	[MarketingGroupId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(60) NULL,
	[Description] Nvarchar(255) NULL,
Primary Key ([MarketingGroupId],[LanguageId])
) 
go

Create table [EmailTemplateType]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(60) NOT NULL, UNIQUE ([Name]),
Primary Key ([Id])
) 
go

Create table [AssetChangeLog]
(
	[UserId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
	[ChangeTime] Datetime NOT NULL,
	[LogEntry] Ntext NULL
) 
go

Create table [TranslatedCommercialOption]
(
	[LanguageId] Integer NOT NULL,
	[CommercialOptionId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Description] Ntext NULL,
	[Terms] Ntext NULL,
Primary Key ([LanguageId],[CommercialOptionId])
) 
go

Create table [TranslatedPriceBandType]
(
	[LanguageId] Integer NOT NULL,
	[PriceBandTypeId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([LanguageId],[PriceBandTypeId])
) 
go

Create table [TranslatedOrderStatus]
(
	[OrderStatusId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([OrderStatusId],[LanguageId])
) 
go

Create table [TranslatedPriceBand]
(
	[PriceBandId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Description] Nvarchar(255) NULL,
Primary Key ([PriceBandId],[LanguageId])
) 
go

Create table [TranslatedSubscriptionModel]
(
	[SubscriptionModelId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Description] Nvarchar(250) NULL,
Primary Key ([SubscriptionModelId],[LanguageId])
) 
go

Create table [TranslatedTaxRegion]
(
	[TaxRegionId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
Primary Key ([TaxRegionId],[LanguageId])
) 
go

Create table [AssetInMarketingEmail]
(
	[MarketingEmailId] Integer NOT NULL,
	[AssetId] Integer NOT NULL,
Primary Key ([MarketingEmailId],[AssetId])
) 
go

Create table [MarketingEmail]
(
	[Id] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Introduction] Ntext NULL,
	[TimeSent] Datetime NOT NULL,
Primary Key ([Id])
) 
go

Create table [TranslatedMarketingEmail]
(
	[MarketingEmailId] Integer NOT NULL,
	[LanguageId] Integer NOT NULL,
	[Name] Nvarchar(255) NULL,
	[Introduction] Ntext NULL,
Primary Key ([MarketingEmailId],[LanguageId])
) 
go

Create table [TranslatedTaxType]
(
	[LanguageId] Integer NOT NULL,
	[TaxTypeId] Integer NOT NULL,
	[Name] Nvarchar(200) NULL,
Primary Key ([LanguageId],[TaxTypeId])
) 
go






































































































Alter table [CustomFieldValueMapping] add constraint [UniqueCheck] unique ([ListValue],[CustomFieldId],[ItemId])
go


















Create Index [ImportedAssetId_Index] ON [Asset] ([ImportedAssetId] ) 
go


Alter table [AssetAttributeValue] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxAsset] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [CM_ItemInCategory] add  foreign key([ItemId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [ImageAsset] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AttributeValue] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetUse] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetApproval] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetView] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [RelatedAsset] add  foreign key([ParentId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [RelatedAsset] add  foreign key([ChildId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [PromotedAsset] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [VideoAsset] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [FeaturedAsset] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxPriceBand] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [FeaturedAssetInBrand] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AudioAsset] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetChangeLog] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetInMarketingEmail] add  foreign key([AssetId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [Asset] add  foreign key([CurrentVersionId]) references [Asset] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxAsset] add  foreign key([AssetBoxId]) references [AssetBox] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxPriceBand] add  foreign key([AssetBoxId]) references [AssetBox] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxShare] add  foreign key([AssetBoxId]) references [AssetBox] ([Id])  on update no action on delete no action 
go
Alter table [Asset] add  foreign key([AddedByUserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [UserInGroup] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetUse] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetBox] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetView] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetApproval] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [Asset] add  foreign key([LastModifiedByUserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [ABOrder] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [Subscription] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxShare] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxAsset] add  foreign key([AddedByUserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [UserInMarketingGroup] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetBankUser] add  foreign key([LastModifiedById]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [SavedSearch] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [AssetChangeLog] add  foreign key([UserId]) references [AssetBankUser] ([Id])  on update no action on delete no action 
go
Alter table [CM_ItemInCategory] add  foreign key([CategoryId]) references [CM_Category] ([Id])  on update no action on delete no action 
go
Alter table [CM_Category] add  foreign key([ParentId]) references [CM_Category] ([Id])  on update no action on delete no action 
go
Alter table [CategoryVisibleToGroup] add  foreign key([CategoryId]) references [CM_Category] ([Id])  on update no action on delete no action 
go
Alter table [OrgUnit] add  foreign key([RootOrgUnitCategoryId]) references [CM_Category] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedCategory] add  foreign key([CategoryId]) references [CM_Category] ([Id])  on update no action on delete no action 
go
Alter table [AssetAttributeValue] add  foreign key([AttributeValueId]) references [AttributeValue] ([Id])  on update no action on delete no action 
go
Alter table [FilterAttributeValue] add  foreign key([AttributeValueId]) references [AttributeValue] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedAttributeValue] add  foreign key([AttributeValueId]) references [AttributeValue] ([Id])  on update no action on delete no action 
go
Alter table [AttributeValue] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [GroupAttributeExclusion] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [AttributeVisibleToGroup] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [AssetAttributeValue] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [ChangeAttributeValueDateRule] add  foreign key([ChangeAttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [ChangeAttributeValueDateRule] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [SendEmailDateRule] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [SortAttribute] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [DisplayAttribute] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [EmbeddedDataMapping] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedAttribute] add  foreign key([AttributeId]) references [Attribute] ([Id])  on update no action on delete no action 
go
Alter table [CM_Category] add  foreign key([CategoryTypeId]) references [CM_CategoryType] ([Id])  on update no action on delete no action 
go
Alter table [CategoryVisibleToGroup] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [GroupAttributeExclusion] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [AttributeVisibleToGroup] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [UserInGroup] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [OrgUnit] add  foreign key([AdminGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [OrgUnitGroup] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [OrgUnit] add  foreign key([StandardGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [GroupEmailRule] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [GroupUsageExclusion] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [GroupInSubscriptionModel] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [GroupIsRole] add  foreign key([UserGroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [ScheduledReportGroup] add  foreign key([GroupId]) references [UserGroup] ([Id])  on update no action on delete no action 
go
Alter table [Attribute] add  foreign key([AttributeTypeId]) references [AttributeType] ([Id])  on update no action on delete no action 
go
Alter table [Asset] add  foreign key([AssetTypeId]) references [AssetType] ([Id])  on update no action on delete no action 
go
Alter table [FileFormat] add  foreign key([AssetTypeId]) references [AssetType] ([Id])  on update no action on delete no action 
go
Alter table [UsageType] add  foreign key([AssetTypeId]) references [AssetType] ([Id])  on update no action on delete no action 
go
Alter table [PriceBand] add  foreign key([AssetTypeId]) references [AssetType] ([Id])  on update no action on delete no action 
go
Alter table [AssetUse] add  foreign key([UsageTypeId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [AssetApproval] add  foreign key([UsageTypeId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [UsageTypeFormat] add  foreign key([UsageTypeId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [UsageType] add  foreign key([ParentId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [GroupUsageExclusion] add  foreign key([UsageTypeId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [PriceBandUsage] add  foreign key([UsageTypeId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedUsageType] add  foreign key([UsageTypeId]) references [UsageType] ([Id])  on update no action on delete no action 
go
Alter table [AssetUse] add  foreign key([AssetDownloadTypeId]) references [AssetDownloadType] ([Id])  on update no action on delete no action 
go
Alter table [Asset] add  foreign key([FileFormatId]) references [FileFormat] ([Id])  on update no action on delete no action 
go
Alter table [UsageTypeFormat] add  foreign key([FormatId]) references [FileFormat] ([Id])  on update no action on delete no action 
go
Alter table [AssetApproval] add  foreign key([ApprovalStatusId]) references [ApprovalStatus] ([Id])  on update no action on delete no action 
go
Alter table [OrgUnitGroup] add  foreign key([OrgUnitId]) references [OrgUnit] ([Id])  on update no action on delete no action 
go
Alter table [AssetBankUser] add  foreign key([RequestedOrgUnitId]) references [OrgUnit] ([Id])  on update no action on delete no action 
go
Alter table [AssetPurchaseLog] add  foreign key([ABOrderId]) references [ABOrder] ([Id])  on update no action on delete no action 
go
Alter table [AssetPurchasePriceBand] add  foreign key([ABOrderId],[AssetId]) references [AssetPurchaseLog] ([ABOrderId],[AssetId])  on update no action on delete no action 
go
Alter table [TranslatedUsageTypeFormat] add  foreign key([UsageTypeFormatId]) references [UsageTypeFormat] ([Id])  on update no action on delete no action 
go
Alter table [GroupEmailRule] add  foreign key([RuleId]) references [SendEmailDateRule] ([Id])  on update no action on delete no action 
go
Alter table [ListItem] add  foreign key([ListId]) references [List] ([Id])  on update no action on delete no action 
go
Alter table [AssetBankUser] add  foreign key([DivisionId]) references [Division] ([Id])  on update no action on delete no action 
go
Alter table [GroupInSubscriptionModel] add  foreign key([SubscriptionModelId]) references [SubscriptionModel] ([Id])  on update no action on delete no action 
go
Alter table [Subscription] add  foreign key([SubscriptionModelId]) references [SubscriptionModel] ([Id])  on update no action on delete no action 
go
Alter table [SubscriptionModelUpgrade] add  foreign key([FromId]) references [SubscriptionModel] ([Id])  on update no action on delete no action 
go
Alter table [SubscriptionModelUpgrade] add  foreign key([ToId]) references [SubscriptionModel] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedSubscriptionModel] add  foreign key([SubscriptionModelId]) references [SubscriptionModel] ([Id])  on update no action on delete no action 
go
Alter table [AssetBankUser] add  foreign key([AddressId]) references [Address] ([Id])  on update no action on delete no action 
go
Alter table [ABOrder] add  foreign key([ShippingAddressId]) references [Address] ([Id])  on update no action on delete no action 
go
Alter table [Address] add  foreign key([CountryId]) references [Country] ([Id])  on update no action on delete no action 
go
Alter table [CountryInTaxRegion] add  foreign key([CountryId]) references [Country] ([Id])  on update no action on delete no action 
go
Alter table [TaxValue] add  foreign key([TaxTypeId]) references [TaxType] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedTaxType] add  foreign key([TaxTypeId]) references [TaxType] ([Id])  on update no action on delete no action 
go
Alter table [TaxValue] add  foreign key([TaxRegionId]) references [TaxRegion] ([Id])  on update no action on delete no action 
go
Alter table [CountryInTaxRegion] add  foreign key([TaxRegionId]) references [TaxRegion] ([Id])  on update no action on delete no action 
go
Alter table [ShippingCost] add  foreign key([TaxRegionId]) references [TaxRegion] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedTaxRegion] add  foreign key([TaxRegionId]) references [TaxRegion] ([Id])  on update no action on delete no action 
go
Alter table [SortAttribute] add  foreign key([SortAreaId]) references [SortArea] ([Id])  on update no action on delete no action 
go
Alter table [PriceBandUsage] add  foreign key([PriceBandId]) references [PriceBand] ([Id])  on update no action on delete no action 
go
Alter table [ShippingCost] add  foreign key([PriceBandId]) references [PriceBand] ([Id])  on update no action on delete no action 
go
Alter table [AssetBoxPriceBand] add  foreign key([PriceBandId]) references [PriceBand] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedPriceBand] add  foreign key([PriceBandId]) references [PriceBand] ([Id])  on update no action on delete no action 
go
Alter table [PriceBand] add  foreign key([PriceBandTypeId]) references [PriceBandType] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedPriceBandType] add  foreign key([PriceBandTypeId]) references [PriceBandType] ([Id])  on update no action on delete no action 
go
Alter table [ShippingCost] add  foreign key([QuantityRangeId]) references [QuantityRange] ([Id])  on update no action on delete no action 
go
Alter table [CommercialOptionPurchase] add  foreign key([CommercialOptionId]) references [CommercialOption] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedCommercialOption] add  foreign key([CommercialOptionId]) references [CommercialOption] ([Id])  on update no action on delete no action 
go
Alter table [ABOrder] add  foreign key([OrderStatusId]) references [OrderStatus] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedOrderStatus] add  foreign key([OrderStatusId]) references [OrderStatus] ([Id])  on update no action on delete no action 
go
Alter table [CommercialOptionPurchase] add  foreign key([PriceBandId],[ABOrderId],[AssetId]) references [AssetPurchasePriceBand] ([PriceBandId],[ABOrderId],[AssetId])  on update no action on delete no action 
go
Alter table [OrderStatus] add  foreign key([OrderWorkflowId]) references [OrderWorkflow] ([Id])  on update no action on delete no action 
go
Alter table [FeaturedAssetInBrand] add  foreign key([BrandId]) references [Brand] ([Id])  on update no action on delete no action 
go
Alter table [UserGroup] add  foreign key([BrandId]) references [Brand] ([Id])  on update no action on delete no action 
go
Alter table [ListItem] add  foreign key([ListItemTextTypeId]) references [ListItemTextType] ([Id])  on update no action on delete no action 
go
Alter table [GroupIsRole] add  foreign key([RoleId]) references [Role] ([Id])  on update no action on delete no action 
go
Alter table [EmailTemplate] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedAttribute] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedAttributeValue] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedCategory] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [ListItem] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [AssetBankUser] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedUsageType] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedUsageTypeFormat] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedFilter] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedMarketingGroup] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedCommercialOption] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedPriceBandType] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedPriceBand] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedTaxRegion] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedSubscriptionModel] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedOrderStatus] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedMarketingEmail] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedTaxType] add  foreign key([LanguageId]) references [Language] ([Id])  on update no action on delete no action 
go
Alter table [EmbeddedDataMapping] add  foreign key([EmbeddedDataValueId]) references [EmbeddedDataValue] ([Id])  on update no action on delete no action 
go
Alter table [EmbeddedDataValue] add  foreign key([EmbeddedDataTypeId]) references [EmbeddedDataType] ([Id])  on update no action on delete no action 
go
Alter table [EmbeddedDataMapping] add  foreign key([MappingDirectionId]) references [MappingDirection] ([Id])  on update no action on delete no action 
go
Alter table [ScheduledReportGroup] add  foreign key([ReportId]) references [ScheduledReport] ([Id])  on update no action on delete no action 
go
Alter table [FilterAttributeValue] add  foreign key([FilterId]) references [Filter] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedFilter] add  foreign key([FilterId]) references [Filter] ([Id])  on update no action on delete no action 
go
Alter table [UserInMarketingGroup] add  foreign key([MarketingGroupId]) references [MarketingGroup] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedMarketingGroup] add  foreign key([MarketingGroupId]) references [MarketingGroup] ([Id])  on update no action on delete no action 
go
Alter table [CustomField] add  foreign key([UsageTypeId]) references [CustomFieldUsageType] ([Id])  on update no action on delete no action 
go
Alter table [CustomFieldValue] add  foreign key([CustomFieldId]) references [CustomField] ([Id])  on update no action on delete no action 
go
Alter table [CustomFieldValueMapping] add  foreign key([CustomFieldId]) references [CustomField] ([Id])  on update no action on delete no action 
go
Alter table [CustomField] add  foreign key([TypeId]) references [CustomFieldType] ([Id])  on update no action on delete no action 
go
Alter table [CustomFieldValueMapping] add  foreign key([ListValue]) references [CustomFieldValue] ([Id])  on update no action on delete no action 
go
Alter table [EmailTemplate] add  foreign key([TypeId]) references [EmailTemplateType] ([Id])  on update no action on delete no action 
go
Alter table [AssetInMarketingEmail] add  foreign key([MarketingEmailId]) references [MarketingEmail] ([Id])  on update no action on delete no action 
go
Alter table [TranslatedMarketingEmail] add  foreign key([MarketingEmailId]) references [MarketingEmail] ([Id])  on update no action on delete no action 
go


Set quoted_identifier on
go




















Set quoted_identifier off
go





/* Roles permissions */





/* Users permissions */





