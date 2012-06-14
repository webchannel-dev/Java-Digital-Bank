/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.usage.form.MaskForm;
/*     */ import com.bright.assetbank.usage.service.MaskManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class AddMaskAction extends Bn2Action
/*     */ {
/*     */   private MaskManager m_maskManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  50 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*  51 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  53 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  56 */     MaskForm form = (MaskForm)a_form;
/*     */ 
/*  58 */     FormFile imageFormFile = form.getFile();
/*  59 */     if ((imageFormFile != null) && (imageFormFile.getFileSize() > 0))
/*     */     {
/*     */       InputStream is;
/*     */       try
/*     */       {
/*  65 */         is = imageFormFile.getInputStream();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  69 */         throw new Bn2Exception("Error getting InputStream for uploaded file", e);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  74 */         getMaskManager().addMask(form.getName(), imageFormFile.getFileName(), is);
/*     */       }
/*     */       finally
/*     */       {
/*  78 */         if (is != null)
/*     */         {
/*     */           try
/*     */           {
/*  82 */             is.close();
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/*  86 */             this.m_logger.error("Couldn't close FormFile InputStream", e);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  91 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/*  95 */     form.addError("Please choose an image file.");
/*  96 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   public MaskManager getMaskManager()
/*     */   {
/* 102 */     return this.m_maskManager;
/*     */   }
/*     */ 
/*     */   public void setMaskManager(MaskManager a_maskManager)
/*     */   {
/* 107 */     this.m_maskManager = a_maskManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.AddMaskAction
 * JD-Core Version:    0.6.0
 */