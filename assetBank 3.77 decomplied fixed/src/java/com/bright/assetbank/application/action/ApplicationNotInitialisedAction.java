/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.InitialisationErrorAction;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.form.ApplicationErrorForm;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*    */ import org.apache.avalon.framework.component.Component;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ApplicationNotInitialisedAction extends InitialisationErrorAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ApplicationErrorForm form = (ApplicationErrorForm)a_form;
/*    */     try
/*    */     {
/* 75 */       if (GlobalApplication.getInstance().getComponentManager() != null)
/*    */       {
/* 77 */         Component component = GlobalApplication.getInstance().getComponentManager().lookup("JdbcDataSource");
/*    */ 
/* 79 */         DataSourceComponent dataSource = (DataSourceComponent)component;
/* 80 */         Connection con = dataSource.getConnection();
/* 81 */         con.close();
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (ComponentException ce)
/*    */     {
/*    */     }
/*    */     catch (SQLException sqe)
/*    */     {
/* 90 */       form.setIsDatabaseError(true);
/*    */     }
/*    */ 
/* 93 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 98 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ApplicationNotInitialisedAction
 * JD-Core Version:    0.6.0
 */