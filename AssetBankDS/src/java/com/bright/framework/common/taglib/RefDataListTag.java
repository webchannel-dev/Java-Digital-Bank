/*     */ package com.bright.framework.common.taglib;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.LanguageAwareComponent;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.avalon.framework.component.Component;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.taglib.TagUtils;
/*     */ 
/*     */ public class RefDataListTag extends BodyTagSupport
/*     */ {
/*  51 */   protected String m_sId = null;
/*  52 */   protected String m_sComponentName = null;
/*  53 */   protected String m_sMethodName = null;
/*  54 */   protected String m_sArgumentValue = null;
/*  55 */   protected String m_sArgument2Value = null;
/*  56 */   protected String m_sTransactionManagerName = null;
/*  57 */   protected boolean m_bArgsAreBeans = false;
/*  58 */   protected boolean m_bPassUserprofile = false;
/*     */ 
/*     */   public String getId()
/*     */   {
/*  62 */     return this.m_sId;
/*     */   }
/*     */ 
/*     */   public void setId(String a_sId)
/*     */   {
/*  67 */     this.m_sId = a_sId;
/*     */   }
/*     */ 
/*     */   public String getComponentName()
/*     */   {
/*  72 */     return this.m_sComponentName;
/*     */   }
/*     */ 
/*     */   public void setComponentName(String a_sComponentName)
/*     */   {
/*  77 */     this.m_sComponentName = a_sComponentName;
/*     */   }
/*     */ 
/*     */   public String getMethodName()
/*     */   {
/*  82 */     return this.m_sMethodName;
/*     */   }
/*     */ 
/*     */   public void setMethodName(String a_sMethodName)
/*     */   {
/*  87 */     this.m_sMethodName = a_sMethodName;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 106 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 123 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/* 143 */     Object oResult = null;
/*     */     try
/*     */     {
/* 148 */       Component component = GlobalApplication.getInstance().getComponentManager().lookup(this.m_sComponentName);
/*     */ 
/* 151 */       DBTransactionManager transactionManager = null;
/* 152 */       DBTransaction dbTransaction = null;
/*     */ 
/* 155 */       boolean bPassLanguage = component instanceof LanguageAwareComponent;
/*     */       try
/*     */       {
/* 160 */         Method getMethod = null;
/* 161 */         Object[] oaArgs = null;
/* 162 */         Class[] caArgTypes = null;
/*     */ 
/* 165 */         int iNumArgs = 0;
/*     */ 
/* 168 */         if ((this.m_sTransactionManagerName != null) && (this.m_sTransactionManagerName.length() > 0))
/*     */         {
/* 171 */           transactionManager = (DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup(this.m_sTransactionManagerName);
/* 172 */           dbTransaction = transactionManager.getNewTransaction();
/* 173 */           iNumArgs++;
/*     */         }
/*     */ 
/* 176 */         if ((this.m_sArgumentValue != null) && (this.m_sArgumentValue.length() > 0))
/*     */         {
/* 178 */           iNumArgs++;
/*     */         }
/* 180 */         if ((this.m_sArgument2Value != null) && (this.m_sArgument2Value.length() > 0))
/*     */         {
/* 182 */           iNumArgs++;
/*     */         }
/*     */ 
/* 186 */         if (iNumArgs > 0)
/*     */         {
/* 189 */           caArgTypes = new Class[iNumArgs + (bPassLanguage ? 1 : 0) + (this.m_bPassUserprofile ? 1 : 0)];
/* 190 */           int iCount = 0;
/*     */ 
/* 192 */           for (int i = 0; i < iNumArgs; i++)
/*     */           {
/* 195 */             if ((i == 0) && (dbTransaction != null))
/*     */             {
/* 197 */               caArgTypes[i] = dbTransaction.getClass();
/*     */             }
/*     */             else
/*     */             {
/* 201 */               caArgTypes[i] = String.class;
/*     */             }
/* 203 */             iCount = i;
/*     */           }
/*     */ 
/* 206 */           if (bPassLanguage)
/*     */           {
/* 208 */             iCount++; caArgTypes[iCount] = Language.class;
/*     */           }
/*     */ 
/* 211 */           if (this.m_bPassUserprofile)
/*     */           {
/* 213 */             iCount++; caArgTypes[iCount] = getUserProfileClass();
/*     */           }
/* 215 */           int iArgPos = 0;
/*     */ 
/* 218 */           oaArgs = new Object[iNumArgs + (bPassLanguage ? 1 : 0) + (this.m_bPassUserprofile ? 1 : 0)];
/*     */ 
/* 220 */           if (dbTransaction != null)
/*     */           {
/* 222 */             oaArgs[(iArgPos++)] = dbTransaction;
/*     */           }
/*     */ 
/* 225 */           if ((this.m_sArgumentValue != null) && (this.m_sArgumentValue.length() > 0))
/*     */           {
/* 227 */             oaArgs[(iArgPos++)] = getArgumentValue(this.m_sArgumentValue);
/*     */           }
/* 229 */           if ((this.m_sArgument2Value != null) && (this.m_sArgument2Value.length() > 0))
/*     */           {
/* 231 */             oaArgs[(iArgPos++)] = getArgumentValue(this.m_sArgument2Value);
/*     */           }
/*     */ 
/* 234 */           if (bPassLanguage)
/*     */           {
/* 236 */             oaArgs[(iArgPos++)] = ((HttpServletRequest)this.pageContext.getRequest()).getSession().getAttribute("currentLanguage");
/*     */           }
/*     */ 
/* 239 */           if (this.m_bPassUserprofile)
/*     */           {
/* 241 */             oaArgs[(iArgPos++)] = getUserProfile();
/*     */           }
/*     */         }
/*     */ 
/* 245 */         getMethod = component.getClass().getMethod(this.m_sMethodName, caArgTypes);
/*     */ 
/* 248 */         oResult = getMethod.invoke(component, oaArgs);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 253 */         if (dbTransaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 257 */             dbTransaction.rollback();
/*     */           }
/*     */           catch (Exception se)
/*     */           {
/* 261 */             throw new JspException("Exception occured trying to rollback transaction in RefDataListTag tag: " + se.getMessage());
/*     */           }
/*     */         }
/*     */ 
/* 265 */         GlobalApplication.getInstance().getLogger().error("RefDataTag: exception: " + e.getMessage());
/* 266 */         throw new JspException(e);
/*     */       }
/*     */       finally
/*     */       {
/* 270 */         if (dbTransaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 274 */             dbTransaction.commit();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 278 */             throw new JspException("Exception occured trying to commit transaction in RefDataListTag tag: " + e.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 285 */       throw new JspException("Component Exception occured whilst getting component for RefDataListTag tag: " + e.getMessage());
/*     */     }
/*     */ 
/* 288 */     if (oResult == null)
/*     */     {
/* 290 */       oResult = "";
/*     */     }
/* 292 */     this.pageContext.setAttribute(this.m_sId, oResult, 1);
/*     */ 
/* 295 */     return 6;
/*     */   }
/*     */ 
/*     */   private String getArgumentValue(String a_sGivenArgumentValue)
/*     */     throws JspException
/*     */   {
/* 314 */     String sValue = null;
/*     */ 
/* 316 */     if (this.m_bArgsAreBeans)
/*     */     {
/* 319 */       sValue = (String)TagUtils.getInstance().lookup(this.pageContext, a_sGivenArgumentValue, null);
/*     */     }
/*     */     else
/*     */     {
/* 325 */       sValue = a_sGivenArgumentValue;
/*     */     }
/*     */ 
/* 328 */     return sValue;
/*     */   }
/*     */ 
/*     */   protected Class getUserProfileClass()
/*     */   {
/* 333 */     return UserProfile.class;
/*     */   }
/*     */ 
/*     */   protected UserProfile getUserProfile()
/*     */     throws JspException
/*     */   {
/* 341 */     UserProfile profile = (UserProfile)TagUtils.getInstance().lookup(this.pageContext, "userprofile", null);
/*     */ 
/* 344 */     return profile;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 354 */     super.release();
/* 355 */     this.m_sId = null;
/* 356 */     this.m_sComponentName = null;
/* 357 */     this.m_sMethodName = null;
/* 358 */     this.m_sArgumentValue = null;
/* 359 */     this.m_sArgument2Value = null;
/* 360 */     this.m_sTransactionManagerName = null;
/* 361 */     this.m_bArgsAreBeans = false;
/*     */   }
/*     */ 
/*     */   public String getArgumentValue()
/*     */   {
/* 368 */     return this.m_sArgumentValue;
/*     */   }
/*     */ 
/*     */   public void setArgumentValue(String a_sArgumentValue)
/*     */   {
/* 373 */     this.m_sArgumentValue = a_sArgumentValue;
/*     */   }
/*     */ 
/*     */   public String getArgument2Value()
/*     */   {
/* 379 */     return this.m_sArgument2Value;
/*     */   }
/*     */ 
/*     */   public void setArgument2Value(String a_sArgumentValue)
/*     */   {
/* 384 */     this.m_sArgument2Value = a_sArgumentValue;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerName()
/*     */   {
/* 389 */     return this.m_sTransactionManagerName;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerName(String a_sTransactionManagerName) {
/* 393 */     this.m_sTransactionManagerName = a_sTransactionManagerName;
/*     */   }
/*     */ 
/*     */   public boolean getArgsAreBeans() {
/* 397 */     return this.m_bArgsAreBeans;
/*     */   }
/*     */ 
/*     */   public void setArgsAreBeans(boolean a_sArgsAreBeans) {
/* 401 */     this.m_bArgsAreBeans = a_sArgsAreBeans;
/*     */   }
/*     */ 
/*     */   public boolean getPassUserprofile()
/*     */   {
/* 406 */     return this.m_bPassUserprofile;
/*     */   }
/*     */ 
/*     */   public void setPassUserprofile(boolean a_bPassUserprofile)
/*     */   {
/* 411 */     this.m_bPassUserprofile = a_bPassUserprofile;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.taglib.RefDataListTag
 * JD-Core Version:    0.6.0
 */