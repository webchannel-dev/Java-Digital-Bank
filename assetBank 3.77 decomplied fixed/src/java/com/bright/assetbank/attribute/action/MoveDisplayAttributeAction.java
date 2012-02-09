/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class MoveDisplayAttributeAction extends DisplayAttributeAction
/*    */ {
/*    */   public ActionForward performAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     ActionForward afForward = null;
/*    */ 
/* 59 */     long lId = getAttributeId(a_request);
/* 60 */     long lGroupId = getGroupId(a_request);
/* 61 */     int iUp = getIntParameter(a_request, "up");
/* 62 */     boolean bUp = false;
/*    */ 
/* 64 */     if (iUp > 0)
/*    */     {
/* 66 */       bUp = true;
/*    */     }
/*    */ 
/* 70 */     getAttributeManager().moveDisplayAttribute(a_dbTransaction, lId, lGroupId, bUp);
/* 71 */     afForward = getForwardWithGroupId(a_mapping, lGroupId, "Success");
/*    */ 
/* 73 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.MoveDisplayAttributeAction
 * JD-Core Version:    0.6.0
 */