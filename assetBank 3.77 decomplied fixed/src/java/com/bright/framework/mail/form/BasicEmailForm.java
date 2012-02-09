/*    */ package com.bright.framework.mail.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class BasicEmailForm extends Bn2Form
/*    */   implements FrameworkConstants
/*    */ {
/* 34 */   private HashMap m_hmParams = new HashMap();
/*    */ 
/*    */   public void setParams(HashMap a_hmParams)
/*    */   {
/* 38 */     this.m_hmParams = a_hmParams;
/*    */   }
/*    */ 
/*    */   public String getSelectedValue(String a_sParamName)
/*    */   {
/* 44 */     if (this.m_hmParams != null)
/*    */     {
/* 46 */       Set set = this.m_hmParams.keySet();
/* 47 */       Iterator iterator = set.iterator();
/*    */ 
/* 49 */       while (iterator.hasNext())
/*    */       {
/* 51 */         String sTempName = (String)(String)iterator.next();
/*    */ 
/* 53 */         if (a_sParamName.equals(sTempName))
/*    */         {
/* 55 */           return (String)this.m_hmParams.get(sTempName);
/*    */         }
/*    */       }
/*    */     }
/* 59 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.form.BasicEmailForm
 * JD-Core Version:    0.6.0
 */