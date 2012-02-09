/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.EmbeddedMetadataForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.KeyValueBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.image.util.ExifTool;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEmbeddedMetadataAction extends BTransactionAction
/*     */   implements ImageConstants, CategoryConstants, AssetBankConstants
/*     */ {
/*  56 */   protected FileStoreManager m_fileStoreManager = null;
/*  57 */   protected AttributeManager m_attributeManager = null;
/*  58 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     EmbeddedMetadataForm form = (EmbeddedMetadataForm)a_form;
/*     */ 
/*  70 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  72 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, true, false);
/*     */ 
/*  74 */     String sLocation = null;
/*     */ 
/*  76 */     if (StringUtil.stringIsPopulated(asset.getOriginalFileLocation()))
/*     */     {
/*  78 */       sLocation = asset.getOriginalFileLocation();
/*     */     }
/*     */     else
/*     */     {
/*  82 */       sLocation = asset.getFileLocation();
/*     */     }
/*     */ 
/*  86 */     String sFullPath = this.m_fileStoreManager.getAbsolutePath(sLocation);
/*     */ 
/*  89 */     Map hmEmbeddedValues = ExifTool.getData(sFullPath, null, true);
/*     */ 
/*  91 */     Vector vecFields = new Vector();
/*     */ 
/*  94 */     String[] aKeys = new String[hmEmbeddedValues.keySet().size()];
/*  95 */     Iterator it = hmEmbeddedValues.keySet().iterator();
/*  96 */     int i = 0;
/*     */ 
/*  98 */     while (it.hasNext())
/*     */     {
/* 100 */       aKeys[i] = ((String)it.next());
/* 101 */       i++;
/*     */     }
/*     */ 
/* 104 */     Arrays.sort(aKeys);
/*     */ 
/* 106 */     KeyValueBean kvTagValue = null;
/*     */ 
/* 109 */     for (i = 0; i < aKeys.length; i++)
/*     */     {
/* 112 */       if (aKeys[i].toUpperCase().startsWith("FILE")) {
/*     */         continue;
/*     */       }
/* 115 */       kvTagValue = new KeyValueBean(aKeys[i], (String)hmEmbeddedValues.get(aKeys[i]));
/* 116 */       vecFields.add(kvTagValue);
/*     */     }
/*     */ 
/* 120 */     form.setMetadata(vecFields);
/* 121 */     form.setAssetId(lAssetId);
/*     */ 
/* 124 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 126 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 131 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager) {
/* 135 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager) {
/* 139 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewEmbeddedMetadataAction
 * JD-Core Version:    0.6.0
 */