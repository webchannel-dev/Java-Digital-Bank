/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*     */ import com.bright.assetbank.user.bean.GroupCategoryPermission.CategoryIdEquals;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class GroupForm extends Bn2Form
/*     */   implements UserConstants
/*     */ {
/*  54 */   private Group m_group = null;
/*  55 */   private FlatCategoryList m_categoryList = null;
/*  56 */   private Vector m_vecSelectedPermissions = null;
/*  57 */   private Vector m_orgUnitList = null;
/*  58 */   private Vector m_brandList = null;
/*  59 */   private String m_sDiscountString = null;
/*  60 */   private boolean m_bAccessLevelsExpandable = false;
/*     */ 
/*     */   public GroupForm()
/*     */   {
/*  68 */     this.m_group = new Group();
/*     */   }
/*     */ 
/*     */   public void setGroup(Group a_group)
/*     */   {
/*  74 */     this.m_group = a_group;
/*     */   }
/*     */ 
/*     */   public Group getGroup()
/*     */   {
/*  79 */     return this.m_group;
/*     */   }
/*     */ 
/*     */   public void setCategoryList(FlatCategoryList a_categoryList)
/*     */   {
/*  84 */     this.m_categoryList = a_categoryList;
/*     */   }
/*     */ 
/*     */   public FlatCategoryList getCategoryList()
/*     */   {
/*  89 */     return this.m_categoryList;
/*     */   }
/*     */ 
/*     */   public void setSelectedPermissions(Vector a_vecSelectedPermissions)
/*     */   {
/*  94 */     this.m_vecSelectedPermissions = a_vecSelectedPermissions;
/*     */   }
/*     */ 
/*     */   public Vector getSelectedPermissions()
/*     */   {
/*  99 */     return this.m_vecSelectedPermissions;
/*     */   }
/*     */ 
/*     */   public Vector getOrgUnitList()
/*     */   {
/* 104 */     return this.m_orgUnitList;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitList(Vector a_sOrgUnitList)
/*     */   {
/* 109 */     this.m_orgUnitList = a_sOrgUnitList;
/*     */   }
/*     */ 
/*     */   public Vector getBrandList()
/*     */   {
/* 114 */     return this.m_brandList;
/*     */   }
/*     */ 
/*     */   public void setBrandList(Vector a_sBrandList)
/*     */   {
/* 119 */     this.m_brandList = a_sBrandList;
/*     */   }
/*     */ 
/*     */   public void setDiscountString(String a_sDiscountString)
/*     */   {
/* 124 */     this.m_sDiscountString = a_sDiscountString;
/*     */   }
/*     */ 
/*     */   public String getDiscountString()
/*     */   {
/* 129 */     return this.m_sDiscountString;
/*     */   }
/*     */ 
/*     */   public int getSelectedDownloadPermission(int a_iCategoryId)
/*     */   {
/* 150 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 154 */     return gcp != null ? gcp.getDownloadPermissionLevel() : 0;
/*     */   }
/*     */ 
/*     */   public int getSelectedUploadPermission(int a_iCategoryId)
/*     */   {
/* 172 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 176 */     return gcp != null ? gcp.getUploadPermissionLevel() : 0;
/*     */   }
/*     */ 
/*     */   public boolean getSelectedDownloadOriginalPermission(int a_iCategoryId)
/*     */   {
/* 189 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 193 */     return (gcp != null) && (gcp.getCanDownloadOriginal());
/*     */   }
/*     */ 
/*     */   public boolean getSelectedHighResApprovalPermission(int a_iCategoryId)
/*     */   {
/* 199 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 203 */     return (gcp != null) && (gcp.getApprovalRequiredForHighRes());
/*     */   }
/*     */ 
/*     */   public boolean getSelectedReviewAssetsPermission(int a_iCategoryId)
/*     */   {
/* 216 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 220 */     return (gcp != null) && (gcp.getCanReviewAssets());
/*     */   }
/*     */ 
/*     */   public boolean getSelectedViewRestrictedAssetsPermission(int a_iCategoryId)
/*     */   {
/* 233 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 237 */     return (gcp != null) && (gcp.getCanViewRestrictedAssets());
/*     */   }
/*     */ 
/*     */   public boolean getSelectedDownloadAdvancedPermission(int a_iCategoryId)
/*     */   {
/* 250 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 254 */     return (gcp != null) && (gcp.getCanDownloadAdvanced());
/*     */   }
/*     */ 
/*     */   public boolean getEditSubcategoriesPermission(int a_iCategoryId)
/*     */   {
/* 266 */     GroupCategoryPermission gcp = (GroupCategoryPermission)CollectionUtil.getFirstMatch(getSelectedPermissions(), new GroupCategoryPermission.CategoryIdEquals(a_iCategoryId));
/*     */ 
/* 270 */     return (gcp != null) && (gcp.getCanEditSubcategories());
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, ABUserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 285 */     if (!StringUtil.stringIsPopulated(getGroup().getName()))
/*     */     {
/* 287 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationGroupName", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 290 */     if (StringUtil.stringIsPopulated(getGroup().getIpMapping()))
/*     */     {
/* 293 */       getGroup().setIpMapping(getGroup().getIpMapping().replaceAll(" ", ""));
/*     */     }
/*     */ 
/* 296 */     if (AssetBankSettings.ecommerce())
/*     */     {
/* 298 */       if (!StringUtil.stringIsInteger(getDiscountString()))
/*     */       {
/* 300 */         addError(a_listManager.getListItem(a_dbTransaction, "failedValidationGroupDiscount", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       else
/*     */       {
/* 304 */         int iDiscount = Integer.parseInt(getDiscountString());
/*     */ 
/* 306 */         if ((iDiscount < 0) || (iDiscount > 100))
/*     */         {
/* 308 */           addError(a_listManager.getListItem(a_dbTransaction, "failedValidationGroupDiscountPercent", a_userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isAccessLevelsExpandable()
/*     */   {
/* 317 */     return this.m_bAccessLevelsExpandable;
/*     */   }
/*     */ 
/*     */   public void setAccessLevelsExpandable(boolean a_bAccessLevelsExpandable)
/*     */   {
/* 323 */     this.m_bAccessLevelsExpandable = a_bAccessLevelsExpandable;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.GroupForm
 * JD-Core Version:    0.6.0
 */