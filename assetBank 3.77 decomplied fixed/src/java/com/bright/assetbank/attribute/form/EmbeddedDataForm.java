/*     */ package com.bright.assetbank.attribute.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.EmbeddedDataMapping;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class EmbeddedDataForm extends Bn2Form
/*     */   implements AssetBankConstants
/*     */ {
/*  44 */   private Vector m_vecAttributes = null;
/*  45 */   private HashMap m_hmEmbeddedData = null;
/*  46 */   private Vector m_vecEmbeddedDataMappings = null;
/*  47 */   private EmbeddedDataMapping m_mapping = new EmbeddedDataMapping();
/*  48 */   private Vector m_vecFlatEmbeddedDataValues = null;
/*  49 */   private Vector m_vecMappingDirections = null;
/*  50 */   private Vector m_vecEmbeddedDataTypes = null;
/*  51 */   private long m_lEmbeddedDataTypeId = -1L;
/*  52 */   private boolean m_bFailedValidation = false;
/*     */ 
/*  54 */   private long m_lOldAttributeId = -1L;
/*  55 */   private long m_lOldValueId = -1L;
/*  56 */   private long m_lOldDirectionId = -1L;
/*  57 */   private long m_lOldTypeId = -1L;
/*     */ 
/*     */   public void setAttributes(Vector a_vecAttributes)
/*     */   {
/*  61 */     this.m_vecAttributes = a_vecAttributes;
/*     */   }
/*     */ 
/*     */   public Vector getAttributes()
/*     */   {
/*  66 */     return this.m_vecAttributes;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedDataValues(HashMap a_hmEmbeddedData)
/*     */   {
/*  71 */     this.m_hmEmbeddedData = a_hmEmbeddedData;
/*     */   }
/*     */ 
/*     */   public HashMap getEmbeddedDataValues()
/*     */   {
/*  76 */     return this.m_hmEmbeddedData;
/*     */   }
/*     */ 
/*     */   public Vector getEmbeddedDataValuesForType(int a_iTypeId)
/*     */   {
/*  81 */     Vector vecDataValues = (Vector)getEmbeddedDataValues().get(new Long(a_iTypeId));
/*     */ 
/*  83 */     if (vecDataValues != null)
/*     */     {
/*  85 */       return vecDataValues;
/*     */     }
/*  87 */     return new Vector();
/*     */   }
/*     */ 
/*     */   public Vector getFlatEmbeddedDataValues()
/*     */   {
/*  92 */     if (this.m_vecFlatEmbeddedDataValues == null)
/*     */     {
/*  94 */       if (getEmbeddedDataValues() != null)
/*     */       {
/*  96 */         this.m_vecFlatEmbeddedDataValues = new Vector();
/*  97 */         Set keys = getEmbeddedDataValues().keySet();
/*  98 */         Iterator it = keys.iterator();
/*     */ 
/* 100 */         while (it.hasNext())
/*     */         {
/* 102 */           Vector vecItems = (Vector)getEmbeddedDataValues().get(it.next());
/*     */ 
/* 104 */           if (vecItems != null)
/*     */           {
/* 106 */             for (int i = 0; i < vecItems.size(); i++)
/*     */             {
/* 108 */               this.m_vecFlatEmbeddedDataValues.add(vecItems.elementAt(i));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 115 */     return this.m_vecFlatEmbeddedDataValues;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedDataMappings(Vector a_vecEmbeddedDataMappings)
/*     */   {
/* 120 */     this.m_vecEmbeddedDataMappings = a_vecEmbeddedDataMappings;
/*     */   }
/*     */ 
/*     */   public Vector getEmbeddedDataMappings()
/*     */   {
/* 125 */     return this.m_vecEmbeddedDataMappings;
/*     */   }
/*     */ 
/*     */   public EmbeddedDataMapping getEmbeddedDataMapping()
/*     */   {
/* 130 */     return this.m_mapping;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedDataMapping(EmbeddedDataMapping a_mapping)
/*     */   {
/* 135 */     this.m_mapping = a_mapping;
/*     */   }
/*     */ 
/*     */   public void setMappingDirections(Vector a_vecMappingDirections)
/*     */   {
/* 140 */     this.m_vecMappingDirections = a_vecMappingDirections;
/*     */   }
/*     */ 
/*     */   public Vector getMappingDirections()
/*     */   {
/* 145 */     return this.m_vecMappingDirections;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedDataTypes(Vector a_vecEmbeddedDataTypes)
/*     */   {
/* 150 */     this.m_vecEmbeddedDataTypes = a_vecEmbeddedDataTypes;
/*     */   }
/*     */ 
/*     */   public Vector getEmbeddedDataTypes()
/*     */   {
/* 155 */     return this.m_vecEmbeddedDataTypes;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedDataTypeId(long a_lEmbeddedDataTypeId)
/*     */   {
/* 160 */     this.m_lEmbeddedDataTypeId = a_lEmbeddedDataTypeId;
/*     */   }
/*     */ 
/*     */   public long getEmbeddedDataTypeId()
/*     */   {
/* 165 */     return this.m_lEmbeddedDataTypeId;
/*     */   }
/*     */ 
/*     */   public void setFailedValidation(boolean a_bFailedValidation)
/*     */   {
/* 170 */     this.m_bFailedValidation = a_bFailedValidation;
/*     */   }
/*     */ 
/*     */   public boolean getFailedValidation()
/*     */   {
/* 175 */     return this.m_bFailedValidation;
/*     */   }
/*     */ 
/*     */   public int getNoOfMappings()
/*     */   {
/* 180 */     if (getEmbeddedDataMappings() != null)
/*     */     {
/* 182 */       return getEmbeddedDataMappings().size();
/*     */     }
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */   public void validateMapping(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager) throws Bn2Exception
/*     */   {
/* 189 */     if (getEmbeddedDataMapping().getAttributeId() <= 0L)
/*     */     {
/* 191 */       addError(a_listManager.getListItem(a_dbTransaction, "edmSelectAttribute", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 194 */     if (getEmbeddedDataMapping().getEmbeddedDataValueId() <= 0L)
/*     */     {
/* 196 */       addError(a_listManager.getListItem(a_dbTransaction, "edmSelectValue", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 199 */     if (getEmbeddedDataMapping().getMappingDirectionId() <= 0L)
/*     */     {
/* 201 */       addError(a_listManager.getListItem(a_dbTransaction, "edmUploadsDownloads", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getOldAttributeId() {
/* 206 */     return this.m_lOldAttributeId;
/*     */   }
/*     */ 
/*     */   public void setOldAttributeId(long oldAttributeId) {
/* 210 */     this.m_lOldAttributeId = oldAttributeId;
/*     */   }
/*     */ 
/*     */   public long getOldDirectionId() {
/* 214 */     return this.m_lOldDirectionId;
/*     */   }
/*     */ 
/*     */   public void setOldDirectionId(long oldDirectionId) {
/* 218 */     this.m_lOldDirectionId = oldDirectionId;
/*     */   }
/*     */ 
/*     */   public long getOldTypeId() {
/* 222 */     return this.m_lOldTypeId;
/*     */   }
/*     */ 
/*     */   public void setOldTypeId(long oldTypeId) {
/* 226 */     this.m_lOldTypeId = oldTypeId;
/*     */   }
/*     */ 
/*     */   public long getOldValueId() {
/* 230 */     return this.m_lOldValueId;
/*     */   }
/*     */ 
/*     */   public void setOldValueId(long oldValueId) {
/* 234 */     this.m_lOldValueId = oldValueId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.form.EmbeddedDataForm
 * JD-Core Version:    0.6.0
 */