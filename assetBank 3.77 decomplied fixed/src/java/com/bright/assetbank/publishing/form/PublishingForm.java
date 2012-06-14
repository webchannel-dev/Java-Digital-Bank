/*     */ package com.bright.assetbank.publishing.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.publishing.bean.PublishingAction;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PublishingForm extends Bn2Form
/*     */ {
/*  12 */   private long m_id = 0L;
/*  13 */   private String m_name = null;
/*  14 */   private String m_path = null;
/*  15 */   private long m_accessLevelId = 0L;
/*  16 */   private boolean m_runDaily = false;
/*  17 */   private List<PublishingAction> m_publishingActions = null;
/*  18 */   private List<Category> m_accessLevelsList = null;
/*     */ 
/*     */   public PublishingAction toPublishingAction()
/*     */   {
/*  22 */     return new PublishingAction(Long.valueOf(this.m_id), this.m_name, this.m_path, this.m_accessLevelId, this.m_runDaily);
/*     */   }
/*     */ 
/*     */   public void fromPublishingAction(PublishingAction publishingAction)
/*     */   {
/*  31 */     this.m_id = publishingAction.getId();
/*  32 */     this.m_name = publishingAction.getName();
/*  33 */     this.m_path = publishingAction.getPath();
/*  34 */     this.m_accessLevelId = publishingAction.getAccessLevelId();
/*  35 */     this.m_runDaily = publishingAction.getRunDaily();
/*     */   }
/*     */ 
/*     */   public void setId(long a_id)
/*     */   {
/*  40 */     this.m_id = a_id;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/*  45 */     return this.m_id;
/*     */   }
/*     */ 
/*     */   public void setName(String a_name)
/*     */   {
/*  50 */     this.m_name = a_name;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  55 */     return this.m_name;
/*     */   }
/*     */ 
/*     */   public void setPath(String a_path)
/*     */   {
/*  60 */     this.m_path = a_path;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/*  65 */     return this.m_path;
/*     */   }
/*     */ 
/*     */   public void setRunDaily(boolean a_runDaily)
/*     */   {
/*  70 */     this.m_runDaily = a_runDaily;
/*     */   }
/*     */ 
/*     */   public boolean getRunDaily()
/*     */   {
/*  75 */     return this.m_runDaily;
/*     */   }
/*     */ 
/*     */   public void setPublishingActions(List<PublishingAction> a_publishingActions)
/*     */   {
/*  80 */     this.m_publishingActions = a_publishingActions;
/*     */   }
/*     */ 
/*     */   public List<PublishingAction> getPublishingActions()
/*     */   {
/*  85 */     return this.m_publishingActions;
/*     */   }
/*     */ 
/*     */   public void setAccessLevelsList(List<Category> a_accessLevelsList)
/*     */   {
/*  90 */     this.m_accessLevelsList = a_accessLevelsList;
/*     */   }
/*     */ 
/*     */   public List<Category> getAccessLevelsList()
/*     */   {
/*  95 */     return this.m_accessLevelsList;
/*     */   }
/*     */ 
/*     */   public void setAccessLevelId(long a_accessLevelId)
/*     */   {
/* 100 */     this.m_accessLevelId = a_accessLevelId;
/*     */   }
/*     */ 
/*     */   public long getAccessLevelId()
/*     */   {
/* 105 */     return this.m_accessLevelId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.form.PublishingForm
 * JD-Core Version:    0.6.0
 */