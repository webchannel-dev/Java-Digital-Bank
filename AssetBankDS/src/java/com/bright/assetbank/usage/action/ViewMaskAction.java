/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.ViewFullSizedImageAction;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.usage.bean.Mask;
/*     */ import com.bright.assetbank.usage.service.MaskManager;
/*     */ import com.bright.assetbank.usage.service.NamedColourManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.util.GeometryUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewMaskAction extends ViewFullSizedImageAction
/*     */ {
/*     */   private MaskManager m_maskManager;
/*     */   private NamedColourManager m_namedColourManager;
/*     */ 
/*     */   public ActionForward postExecute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction, ImageAsset a_image)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     long lMaskId = getLongParameter(a_request, "maskId");
/*  63 */     if (lMaskId > 0L)
/*     */     {
/*  65 */       Mask selectedMask = getMaskManager().getMaskById(a_transaction, lMaskId);
/*  66 */       a_request.setAttribute("selectedMask", selectedMask);
/*     */ 
/*  70 */       int scaledWidth = ((Integer)a_request.getAttribute("originalWidth")).intValue();
/*  71 */       int scaledHeight = ((Integer)a_request.getAttribute("originalHeight")).intValue();
/*  72 */       Dimension scaledBoundingBox = new Dimension(scaledWidth, scaledHeight);
/*  73 */       Rectangle defaultMaskPosition = GeometryUtil.fitAndCentre(selectedMask.getImageSize(), scaledBoundingBox);
/*  74 */       a_request.setAttribute("defaultMaskLeft", Integer.valueOf(defaultMaskPosition.x));
/*  75 */       a_request.setAttribute("defaultMaskTop", Integer.valueOf(defaultMaskPosition.y));
/*  76 */       a_request.setAttribute("defaultMaskWidth", Integer.valueOf(defaultMaskPosition.width));
/*  77 */       a_request.setAttribute("defaultMaskHeight", Integer.valueOf(defaultMaskPosition.height));
/*     */     }
/*     */ 
/*  80 */     List colours = getNamedColourManager().getColours(a_transaction);
/*  81 */     a_request.setAttribute("colours", colours);
/*     */ 
/*  83 */     List masks = getMaskManager().getMasks(a_transaction);
/*  84 */     a_request.setAttribute("masks", masks);
/*     */ 
/*  86 */     RequestUtil.addParameterAsAttribute(a_request, "maskLeft");
/*  87 */     RequestUtil.addParameterAsAttribute(a_request, "maskTop");
/*  88 */     RequestUtil.addParameterAsAttribute(a_request, "maskWidth");
/*  89 */     RequestUtil.addParameterAsAttribute(a_request, "maskHeight");
/*  90 */     RequestUtil.addParameterAsAttribute(a_request, "maskColour");
/*     */ 
/*  92 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public MaskManager getMaskManager()
/*     */   {
/*  97 */     return this.m_maskManager;
/*     */   }
/*     */ 
/*     */   public void setMaskManager(MaskManager a_maskManager)
/*     */   {
/* 102 */     this.m_maskManager = a_maskManager;
/*     */   }
/*     */ 
/*     */   public NamedColourManager getNamedColourManager()
/*     */   {
/* 107 */     return this.m_namedColourManager;
/*     */   }
/*     */ 
/*     */   public void setNamedColourManager(NamedColourManager a_namedColourManager)
/*     */   {
/* 112 */     this.m_namedColourManager = a_namedColourManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewMaskAction
 * JD-Core Version:    0.6.0
 */