/*     */ package com.bright.assetbank.approval.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.Description;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetInList extends DataBean
/*     */   implements AssetBankConstants
/*     */ {
/*     */   protected Asset m_asset;
/*     */   protected StringDataBean m_approvalStatus;
/*  54 */   private long m_lUsageTypeId = 0L;
/*     */   private boolean m_bUserCanDownloadOriginal;
/*     */   private boolean m_bUserCanDownloadAdvanced;
/*     */   private boolean m_isUpdatable;
/*  58 */   private long m_lAddedToAssetBoxByUserId = 0L;
/*  59 */   private long m_lSequenceNumber = 0L;
/*  60 */   private boolean m_bIsSelected = false;
/*  61 */   private boolean m_bRequiresHighResApproval = false;
/*     */ 
/*  63 */   private Vector<Description> m_vecDescriptions = null;
/*  64 */   private List<AttributeValue> m_downloadAttributes = null;
/*     */ 
/*  66 */   private Language m_language = null;
/*  67 */   private Date m_dtTimeAddedToAssetBox = null;
/*     */   private boolean m_isDownloadable;
/*     */   private boolean m_isDownloadableWithApproval;
/*     */   private String m_adminNotes;
/*     */   private String m_userNotes;
/*     */ 
/*     */   public AssetInList()
/*     */   {
/*  74 */     this((Language)LanguageConstants.k_defaultLanguage.clone());
/*     */   }
/*     */ 
/*     */   public AssetInList(Language a_language)
/*     */   {
/*  79 */     this.m_asset = new Asset();
/*  80 */     this.m_approvalStatus = new StringDataBean();
/*  81 */     this.m_language = a_language;
/*     */   }
/*     */ 
/*     */   public boolean getIsDownloadableNow()
/*     */   {
/*  89 */     return (getIsDownloadable()) || ((getIsDownloadableWithApproval()) && (this.m_approvalStatus.getId() == 2L));
/*     */   }
/*     */ 
/*     */   public boolean getIsNeverDownloadable()
/*     */   {
/* 100 */     return (!getIsDownloadable()) && (!getIsDownloadableWithApproval());
/*     */   }
/*     */ 
/*     */   public boolean getIsApprovalRequestable()
/*     */   {
/* 111 */     return (!getIsDownloadable()) && (getIsDownloadableWithApproval()) && (this.m_approvalStatus.getId() <= 0L);
/*     */   }
/*     */ 
/*     */   public boolean getIsApprovalPending()
/*     */   {
/* 121 */     return (!getIsDownloadable()) && (getIsDownloadableWithApproval()) && (this.m_approvalStatus.getId() == 1L);
/*     */   }
/*     */ 
/*     */   public boolean getIsApprovalRejected()
/*     */   {
/* 131 */     return (!getIsDownloadable()) && (getIsDownloadableWithApproval()) && (this.m_approvalStatus.getId() == 3L);
/*     */   }
/*     */ 
/*     */   public boolean getIsApprovalApproved()
/*     */   {
/* 141 */     return (!getIsDownloadable()) && (getIsDownloadableWithApproval()) && (this.m_approvalStatus.getId() == 2L);
/*     */   }
/*     */ 
/*     */   public Asset getAsset()
/*     */   {
/* 150 */     return this.m_asset;
/*     */   }
/*     */ 
/*     */   public void setAsset(Asset a_sAsset)
/*     */   {
/* 158 */     this.m_asset = a_sAsset;
/*     */   }
/*     */ 
/*     */   public StringDataBean getApprovalStatus()
/*     */   {
/* 166 */     return this.m_approvalStatus;
/*     */   }
/*     */ 
/*     */   public void setApprovalStatus(StringDataBean a_sApprovalStatus)
/*     */   {
/* 174 */     this.m_approvalStatus = a_sApprovalStatus;
/*     */   }
/*     */ 
/*     */   public boolean getIsDownloadable()
/*     */   {
/* 190 */     return this.m_isDownloadable;
/*     */   }
/*     */ 
/*     */   public void setIsDownloadable(boolean a_sIsDownloadable)
/*     */   {
/* 199 */     this.m_isDownloadable = a_sIsDownloadable;
/*     */   }
/*     */ 
/*     */   public boolean getIsDownloadableWithApproval()
/*     */   {
/* 215 */     return this.m_isDownloadableWithApproval;
/*     */   }
/*     */ 
/*     */   public void setIsDownloadableWithApproval(boolean a_sIsDownloadableWithApproval)
/*     */   {
/* 224 */     this.m_isDownloadableWithApproval = a_sIsDownloadableWithApproval;
/*     */   }
/*     */ 
/*     */   public String getAdminNotes()
/*     */   {
/* 238 */     return this.m_adminNotes;
/*     */   }
/*     */ 
/*     */   public void setAdminNotes(String a_sAdminNotes)
/*     */   {
/* 247 */     this.m_adminNotes = a_sAdminNotes;
/*     */   }
/*     */ 
/*     */   public String getUserNotes()
/*     */   {
/* 261 */     return this.m_userNotes;
/*     */   }
/*     */ 
/*     */   public void setUserNotes(String a_sUserNotes)
/*     */   {
/* 270 */     this.m_userNotes = a_sUserNotes;
/*     */   }
/*     */ 
/*     */   public long getUsageTypeId()
/*     */   {
/* 277 */     return this.m_lUsageTypeId;
/*     */   }
/*     */ 
/*     */   public void setUsageTypeId(long a_lUsageTypeId)
/*     */   {
/* 282 */     this.m_lUsageTypeId = a_lUsageTypeId;
/*     */   }
/*     */ 
/*     */   public boolean getUserCanDownloadAdvanced()
/*     */   {
/* 288 */     return this.m_bUserCanDownloadAdvanced;
/*     */   }
/*     */ 
/*     */   public void setUserCanDownloadAdvanced(boolean a_sUserCanDownloadAdvanced) {
/* 292 */     this.m_bUserCanDownloadAdvanced = a_sUserCanDownloadAdvanced;
/*     */   }
/*     */ 
/*     */   public boolean getUserCanDownloadOriginal() {
/* 296 */     return this.m_bUserCanDownloadOriginal;
/*     */   }
/*     */ 
/*     */   public void setUserCanDownloadOriginal(boolean a_sUserCanDownloadOriginal) {
/* 300 */     this.m_bUserCanDownloadOriginal = a_sUserCanDownloadOriginal;
/*     */   }
/*     */ 
/*     */   public void setTimeAddedToAssetBox(Date a_dtTimeAddedToAssetBox)
/*     */   {
/* 305 */     this.m_dtTimeAddedToAssetBox = a_dtTimeAddedToAssetBox;
/*     */   }
/*     */ 
/*     */   public Date getTimeAddedToAssetBox()
/*     */   {
/* 310 */     return this.m_dtTimeAddedToAssetBox;
/*     */   }
/*     */ 
/*     */   public boolean getIsUpdatable()
/*     */   {
/* 315 */     return this.m_isUpdatable;
/*     */   }
/*     */ 
/*     */   public void setIsUpdatable(boolean a_sIsUpdatable) {
/* 319 */     this.m_isUpdatable = a_sIsUpdatable;
/*     */   }
/*     */ 
/*     */   public long getAddedToAssetBoxByUserId()
/*     */   {
/* 324 */     return this.m_lAddedToAssetBoxByUserId;
/*     */   }
/*     */ 
/*     */   public void setAddedToAssetBoxByUserId(long addedToAssetBoxByUserId)
/*     */   {
/* 329 */     this.m_lAddedToAssetBoxByUserId = addedToAssetBoxByUserId;
/*     */   }
/*     */ 
/*     */   public boolean getIsSelected()
/*     */   {
/* 334 */     return this.m_bIsSelected;
/*     */   }
/*     */ 
/*     */   public void setIsSelected(boolean a_bIsSelected) {
/* 338 */     this.m_bIsSelected = a_bIsSelected;
/*     */   }
/*     */ 
/*     */   public boolean getRequiresHighResApproval()
/*     */   {
/* 343 */     return this.m_bRequiresHighResApproval;
/*     */   }
/*     */ 
/*     */   public void setRequiresHighResApproval(boolean a_bRequiresHighResApproval) {
/* 347 */     this.m_bRequiresHighResApproval = a_bRequiresHighResApproval;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 353 */     return this.m_asset.getId();
/*     */   }
/*     */ 
/*     */   public Vector<Description> getDescriptions(String a_sGroupId)
/*     */   {
/* 358 */     return getDescriptions(Long.parseLong(a_sGroupId));
/*     */   }
/*     */ 
/*     */   public Vector<Description> getDescriptions(long a_lGroupId)
/*     */   {
/* 363 */     if (this.m_vecDescriptions == null)
/*     */     {
/* 365 */       String sDescriptions = AttributeUtil.getDisplayAttributeDescriptions(this.m_asset.getDisplayAttributes(a_lGroupId), this.m_language.getCode());
/* 366 */       Vector vecDescriptions = AttributeUtil.getDisplayDescriptions(sDescriptions);
/* 367 */       this.m_vecDescriptions = vecDescriptions;
/*     */     }
/* 369 */     return this.m_vecDescriptions;
/*     */   }
/*     */ 
/*     */   public long getSequenceNumber()
/*     */   {
/* 374 */     return this.m_lSequenceNumber;
/*     */   }
/*     */ 
/*     */   public void setSequenceNumber(long a_lSequenceNumber)
/*     */   {
/* 379 */     this.m_lSequenceNumber = a_lSequenceNumber;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 387 */     int PRIME = 31;
/* 388 */     int result = super.hashCode();
/* 389 */     result = 31 * result + (this.m_adminNotes == null ? 0 : this.m_adminNotes.hashCode());
/* 390 */     result = 31 * result + (this.m_approvalStatus == null ? 0 : this.m_approvalStatus.hashCode());
/* 391 */     result = 31 * result + (this.m_asset == null ? 0 : this.m_asset.hashCode());
/* 392 */     result = 31 * result + (this.m_bUserCanDownloadAdvanced ? 1231 : 1237);
/* 393 */     result = 31 * result + (this.m_bUserCanDownloadOriginal ? 1231 : 1237);
/* 394 */     result = 31 * result + (this.m_isDownloadable ? 1231 : 1237);
/* 395 */     result = 31 * result + (this.m_isDownloadableWithApproval ? 1231 : 1237);
/* 396 */     result = 31 * result + (this.m_isUpdatable ? 1231 : 1237);
/* 397 */     result = 31 * result + (int)(this.m_lAddedToAssetBoxByUserId ^ this.m_lAddedToAssetBoxByUserId >>> 32);
/* 398 */     result = 31 * result + (int)(this.m_lUsageTypeId ^ this.m_lUsageTypeId >>> 32);
/* 399 */     result = 31 * result + (this.m_language == null ? 0 : this.m_language.hashCode());
/* 400 */     result = 31 * result + (this.m_userNotes == null ? 0 : this.m_userNotes.hashCode());
/* 401 */     result = 31 * result + (this.m_vecDescriptions == null ? 0 : this.m_vecDescriptions.hashCode());
/* 402 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 410 */     if (this == obj)
/* 411 */       return true;
/* 412 */     if (!super.equals(obj))
/* 413 */       return false;
/* 414 */     if (getClass() != obj.getClass())
/* 415 */       return false;
/* 416 */     AssetInList other = (AssetInList)obj;
/* 417 */     if (this.m_adminNotes == null)
/*     */     {
/* 419 */       if (other.m_adminNotes != null)
/* 420 */         return false;
/*     */     }
/* 422 */     else if (!this.m_adminNotes.equals(other.m_adminNotes))
/* 423 */       return false;
/* 424 */     if (this.m_approvalStatus == null)
/*     */     {
/* 426 */       if (other.m_approvalStatus != null)
/* 427 */         return false;
/*     */     }
/* 429 */     else if (!this.m_approvalStatus.equals(other.m_approvalStatus))
/* 430 */       return false;
/* 431 */     if (this.m_asset == null)
/*     */     {
/* 433 */       if (other.m_asset != null)
/* 434 */         return false;
/*     */     }
/* 436 */     else if (!this.m_asset.equals(other.m_asset))
/* 437 */       return false;
/* 438 */     if (this.m_bUserCanDownloadAdvanced != other.m_bUserCanDownloadAdvanced)
/* 439 */       return false;
/* 440 */     if (this.m_bUserCanDownloadOriginal != other.m_bUserCanDownloadOriginal)
/* 441 */       return false;
/* 442 */     if (this.m_isDownloadable != other.m_isDownloadable)
/* 443 */       return false;
/* 444 */     if (this.m_isDownloadableWithApproval != other.m_isDownloadableWithApproval)
/* 445 */       return false;
/* 446 */     if (this.m_isUpdatable != other.m_isUpdatable)
/* 447 */       return false;
/* 448 */     if (this.m_lAddedToAssetBoxByUserId != other.m_lAddedToAssetBoxByUserId)
/* 449 */       return false;
/* 450 */     if (this.m_lUsageTypeId != other.m_lUsageTypeId)
/* 451 */       return false;
/* 452 */     if (this.m_language == null)
/*     */     {
/* 454 */       if (other.m_language != null)
/* 455 */         return false;
/*     */     }
/* 457 */     else if (!this.m_language.equals(other.m_language))
/* 458 */       return false;
/* 459 */     if (this.m_userNotes == null)
/*     */     {
/* 461 */       if (other.m_userNotes != null)
/* 462 */         return false;
/*     */     }
/* 464 */     else if (!this.m_userNotes.equals(other.m_userNotes))
/* 465 */       return false;
/* 466 */     if (this.m_vecDescriptions == null)
/*     */     {
/* 468 */       if (other.m_vecDescriptions != null)
/* 469 */         return false;
/*     */     }
/* 471 */     else if (!this.m_vecDescriptions.equals(other.m_vecDescriptions))
/* 472 */       return false;
/* 473 */     return true;
/*     */   }
/*     */ 
/*     */   public List<AttributeValue> getDownloadAttributes()
/*     */   {
/* 478 */     if (this.m_downloadAttributes == null)
/*     */     {
/* 480 */       this.m_downloadAttributes = new ArrayList();
/*     */     }
/* 482 */     return this.m_downloadAttributes;
/*     */   }
/*     */ 
/*     */   public void setDownloadAttributes(List<AttributeValue> a_attributes)
/*     */   {
/* 487 */     this.m_downloadAttributes = a_attributes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.bean.AssetInList
 * JD-Core Version:    0.6.0
 */