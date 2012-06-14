/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*    */ import com.bright.assetbank.search.form.BaseSearchForm;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Map;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SelectAssetsBySearchAction extends Bn2Action
/*    */ {
/* 44 */   private AssetEntityManager m_assetEntityManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     BaseSearchForm form = (BaseSearchForm)a_form;
/*    */ 
/* 54 */     String sSelectUrl = a_request.getParameter("selectUrl");
/*    */ 
/* 56 */     Enumeration names = a_request.getParameterNames();
/*    */ 
/* 58 */     ArrayList paramNames = new ArrayList(a_request.getParameterMap().size());
/* 59 */     ArrayList paramValues = new ArrayList(a_request.getParameterMap().size());
/*    */ 
/* 61 */     while (names.hasMoreElements())
/*    */     {
/* 63 */       String sName = (String)names.nextElement();
/*    */ 
/* 65 */       if (!sName.equalsIgnoreCase("selectUrl"))
/*    */       {
/* 67 */         String sValue = a_request.getParameter(sName);
/*    */ 
/* 69 */         paramNames.add(sName);
/* 70 */         paramValues.add(sValue);
/*    */       }
/*    */     }
/*    */ 
/* 74 */     form.setEntityPreSelected((form.getSelectedEntities() != null) && (form.getSelectedEntities().length > 0));
/* 75 */     form.setSelectAssetUrl(sSelectUrl);
/* 76 */     form.setSelectAssetParamNames((String[])(String[])paramNames.toArray(new String[paramNames.size()]));
/* 77 */     form.setSelectAssetParamValues((String[])(String[])paramValues.toArray(new String[paramValues.size()]));
/*    */ 
/* 79 */     if ((form.getSelectedEntities() != null) && (form.getSelectedEntities().length > 0))
/*    */     {
/* 81 */       Vector<AssetEntity> vecEntities = this.m_assetEntityManager.getAllSelectedEntities(null, form.getSelectedEntities());
/* 82 */       String sEntities = "";
/* 83 */       for (AssetEntity ae : vecEntities)
/*    */       {
/* 85 */         sEntities = sEntities + ae.getName() + ",";
/*    */       }
/* 87 */       sEntities = sEntities.substring(0, sEntities.length() - 1);
/* 88 */       form.setEntityName(sEntities);
/*    */     }
/*    */ 
/* 92 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*    */   {
/* 97 */     this.m_assetEntityManager = assetEntityManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.SelectAssetsBySearchAction
 * JD-Core Version:    0.6.0
 */