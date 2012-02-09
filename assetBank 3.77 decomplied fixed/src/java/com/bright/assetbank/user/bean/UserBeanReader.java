/*    */ package com.bright.assetbank.user.bean;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*    */ import com.bright.assetbank.synchronise.bean.ImportedUserBeanWrapper;
/*    */ import com.bright.framework.customfield.bean.CustomField;
/*    */ import com.bright.framework.customfield.bean.CustomFieldValue;
/*    */ import com.bright.framework.customfield.bean.CustomFieldValueMapping;
/*    */ import com.bright.framework.file.BeanWrapper;
/*    */ import com.bright.framework.file.DefaultBeanReader;
/*    */// import com.bright.framework.file.DefaultBeanReader.BeanPropertyHeader;
/*    */ import com.bright.framework.file.DefaultBeanReader.NoMatchForColumnHeaderException;
/*    */ import com.bright.framework.file.FileFormat;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.util.Vector;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ public class UserBeanReader extends DefaultBeanReader
/*    */ {
/*    */   private static final String c_ksClassName = "UserBeanReader";
/*    */ 
/*    */   public UserBeanReader(BufferedReader a_reader, FileFormat a_format, Class a_beanClass, BeanWrapper a_wrapper)
/*    */     throws IOException, DefaultBeanReader.NoMatchForColumnHeaderException
/*    */   {
/* 47 */     this(a_reader, a_format, a_beanClass, a_wrapper, 0);
/*    */   }
/*    */ 
/*    */   public UserBeanReader(BufferedReader a_reader, FileFormat a_format, Class a_beanClass, BeanWrapper a_wrapper, int a_iStartLineIndex)
/*    */     throws IOException
/*    */   {
/* 57 */     super(a_reader, a_format, a_beanClass, a_wrapper, a_iStartLineIndex);
/*    */   }
/*    */ 
/*    */   protected void setExtraValue(Object a_bean, DefaultBeanReader.BeanPropertyHeader a_header, String a_sValue)
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 68 */       CustomFieldManager m_cfManager = (CustomFieldManager)GlobalApplication.getInstance().getComponentManager().lookup("CustomFieldManager");
/*    */ 
/* 71 */       CustomField field = m_cfManager.getCustomField(null, -1L, a_header.getHeaderName());
/* 72 */       if (field != null)
/*    */       {
/* 74 */         CustomFieldValueMapping val = new CustomFieldValueMapping();
/* 75 */         val.setCustomField(field);
/* 76 */         if ((field.getIsTextarea()) || (field.getIsTextfield()))
/*    */         {
/* 79 */           val.setTextValue(a_sValue);
/*    */         }
/*    */         else
/*    */         {
/* 84 */           String[] sListValues = a_sValue.split(",");
/* 85 */           Vector vecListValues = new Vector();
/*    */         //  String sValue;
/* 86 */           for (String sValue : sListValues)
/*    */           {
/* 89 */             for (CustomFieldValue value : field.getAvailableValues())
/*    */             {
/* 91 */               if (value.getValue().equals(sValue))
/*    */               {
/* 93 */                 vecListValues.add(value);
/*    */               }
/*    */             }
/*    */           }
/* 97 */           val.setListValues(vecListValues);
/*    */         }
/* 99 */         ((ImportedUser)((ImportedUserBeanWrapper)a_bean).getWrappedObject()).addCustomFieldValue(val);
/*    */       }
/*    */     }
/*    */     catch (ComponentException e)
/*    */     {
/* 104 */       throw new Bn2Exception("UserBeanReader : Unable to read extra values: ", e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.UserBeanReader
 * JD-Core Version:    0.6.0
 */