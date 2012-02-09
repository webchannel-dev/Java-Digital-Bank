/*    */ package com.bright.assetbank.entity.constant;
/*    */ 
/*    */ import com.bright.framework.util.CollectionUtil;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract interface AssetEntityConstants
/*    */ {
/*    */   public static final String k_sParamName_EntityId = "entityId";
/*    */   public static final String k_sParamName_IsParent = "isParent";
/*    */   public static final String k_sParamName_ChildName = "childName";
/*    */   public static final String k_sParamName_ChildNamePlural = "childrenName";
/*    */   public static final String k_sParamName_TypeName = "typeName";
/*    */   public static final String k_sParamName_Prefix_PeerQuantity = "peerquantity";
/*    */   public static final String k_sParamName_Prefix_ChildQuantity = "childquantity";
/*    */   public static final String k_sParamName_Prefix_PeerName = "peername";
/*    */   public static final String k_sParamName_Prefix_ChildName = "childname";
/*    */   public static final String k_sParamName_PeerAssetIds = "peerAssetIds";
/*    */   public static final String k_sParamName_ChildAssetIds = "childAssetIds";
/*    */   public static final String k_sParamName_ParentAssetIds = "parentAssetIds";
/*    */   public static final String k_sParamName_RelationshipDescription = "relationshipDescription";
/*    */   public static final String k_sParamName_EntityRestrictions = "entityRestrictions";
/*    */   public static final String k_sParamName_ParentEntity = "parentEntity";
/*    */   public static final String k_sAnchorName_Peers = "peers";
/*    */   public static final String k_sAnchorName_Children = "children";
/*    */   public static final int k_iRelationshipType_Peer = 1;
/*    */   public static final int k_iRelationshipType_Child = 2;
/*    */   public static final int k_iRelationshipType_Parent = 3;
/*    */   public static final int k_iRelationshipType_Sibling = 4;
/*    */   public static final int k_iRetainRelationshipType_Existing = 1;
/*    */   public static final int k_iRetainRelationshipType_Copy = 2;
/*    */   public static final int k_iRetainRelationshipType_DontRetain = 3;
/*    */   public static final String k_sCopyRelationshipMapKeySeparator = ":";
/*    */   public static final String k_sRelationshipDescriptionSeparator = ":";
/*    */   public static final String k_sRelatedAssetIdString_Delim = ",";
/* 64 */   public static final String[] k_aOptionalStaticAttributes = { "categories" };
/* 65 */   public static final Set<String> k_optionalStaticAttributes = CollectionUtil.asUnmodifiableSet(k_aOptionalStaticAttributes);
/*    */   public static final int k_iCategoryExtensionStatus_On = 1;
/*    */   public static final int k_iCategoryExtensionStatus_Off = 2;
/*    */   public static final int k_iCategoryExtensionStatus_Both = 3;
/*    */   public static final String k_sParamPrefix_Peer = "peer";
/*    */   public static final String k_sParamPrefix_Child = "child";
/*    */   public static final String k_sParamSuffix_DefaultCat = "EntityDefaultCategoryId";
/*    */   public static final String k_sParamSuffix_RelationshipLabel = "RelationshipDescriptionLabel";
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.constant.AssetEntityConstants
 * JD-Core Version:    0.6.0
 */