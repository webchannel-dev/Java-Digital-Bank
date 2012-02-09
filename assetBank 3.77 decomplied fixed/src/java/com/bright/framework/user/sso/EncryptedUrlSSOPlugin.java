/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.TimeZone;
/*     */ import java.util.Vector;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class EncryptedUrlSSOPlugin extends BaseSSOPlugin
/*     */   implements SSOPlugin
/*     */ {
/*     */   private static final String k_sClassName = "EncryptedUrlSSOPlugin";
/*  55 */   private EncryptedUrlSSOSettings m_settings = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     super.startup();
/*     */ 
/*  69 */     if (UserSettings.getEncryptedSSOEnabled())
/*     */     {
/*  71 */       this.m_settings = new EncryptedUrlSSOSettings("EncryptedUrlSSOSettings");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSSOMode()
/*     */   {
/*  79 */     return "HTTPRequestToken";
/*     */   }
/*     */ 
/*     */   public String getLogoutUrl(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/*  85 */     return BaseSSOPlugin.getLoginFormUrl(a_request).toString();
/*     */   }
/*     */ 
/*     */   public String getSessionCheckUrl(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   public User getRemoteUser(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*  97 */     User user = null;
/*     */     try
/*     */     {
/* 100 */       String sEncryptedValueParam = this.m_settings.getEncryptedStringParamName();
/* 101 */       String sEncryptedValue = a_request.getParameter(sEncryptedValueParam);
/*     */ 
/* 104 */       if (StringUtil.stringIsPopulated(sEncryptedValue))
/*     */       {
/* 108 */         String sKey = this.m_settings.getEncryptionKey();
/* 109 */         byte[] byKey = sKey.getBytes();
/* 110 */         if (this.m_settings.getEncryptionKeyIsBase64())
/*     */         {
/* 112 */           byKey = Base64.decodeBase64(byKey);
/*     */         }
/*     */ 
/* 115 */         byte[] byEncryptedValue = sEncryptedValue.getBytes();
/* 116 */         if (this.m_settings.getEncryptedValueIsBase64())
/*     */         {
/* 118 */           byEncryptedValue = Base64.decodeBase64(byEncryptedValue);
/*     */         }
/*     */ 
/* 122 */         String sEncoding = this.m_settings.getEncoding();
/* 123 */         String sCipherEncodingDefinition = sEncoding;
/* 124 */         if (StringUtil.stringIsPopulated(this.m_settings.getCipherEncoding()))
/*     */         {
/* 126 */           sCipherEncodingDefinition = this.m_settings.getCipherEncoding();
/*     */         }
/* 128 */         Cipher cipher = Cipher.getInstance(sCipherEncodingDefinition);
/* 129 */         SecretKeySpec skeySpec = new SecretKeySpec(byKey, sEncoding);
/*     */ 
/* 131 */         if (this.m_settings.getUseIV())
/*     */         {
/* 133 */           String sIV = this.m_settings.getIV();
/* 134 */           byte[] byIV = sIV.getBytes();
/* 135 */           if (this.m_settings.getIVIsBase64())
/*     */           {
/* 137 */             byIV = Base64.decodeBase64(byIV);
/*     */           }
/* 139 */           IvParameterSpec ips = new IvParameterSpec(byIV);
/* 140 */           cipher.init(2, skeySpec, ips);
/*     */         }
/*     */         else
/*     */         {
/* 144 */           cipher.init(2, skeySpec);
/*     */         }
/*     */ 
/* 148 */         byte[] original = cipher.doFinal(byEncryptedValue);
/* 149 */         String sValuesString = new String(original);
/*     */ 
/* 152 */         Date dtDateTime = null;
/*     */ 
/* 154 */         user = new User();
/*     */ 
/* 156 */         String[] aPairs = sValuesString.split(this.m_settings.getUsernameDateSeparator());
/* 157 */         for (int i = 0; i < aPairs.length; i++)
/*     */         {
/* 160 */           String[] aTemp = aPairs[i].split(this.m_settings.getValueSeparator());
/*     */ 
/* 163 */           if (aTemp[0].equals(this.m_settings.getUsernameParameter()))
/*     */           {
/* 165 */             user.setRemoteUsername(aTemp[1]);
/* 166 */             user.setUsername(aTemp[1]);
/*     */           }
/* 170 */           else if (aTemp[0].equals(this.m_settings.getEmailParameter()))
/*     */           {
/* 172 */             user.setEmailAddress(aTemp[1]);
/*     */ 
/* 174 */             if (!this.m_settings.getUsernameIsEmail())
/*     */               continue;
/* 176 */             user.setRemoteUsername(aTemp[1]);
/* 177 */             user.setUsername(aTemp[1]);
/*     */           }
/* 182 */           else if ((this.m_settings.getPerformDateCheck()) && (aTemp[0].equals(this.m_settings.getDateParameter())))
/*     */           {
/* 184 */             dtDateTime = new BrightDateFormat(this.m_settings.getDateFormat()).parse(aTemp[1]);
/* 185 */             this.m_logger.warn("EncryptedUrlSSOPlugin: Retrieved date: " + aTemp[1]);
/*     */           }
/* 189 */           else if (aTemp[0].equals(this.m_settings.getForenameParameter()))
/*     */           {
/* 191 */             if (aTemp.length <= 1)
/*     */               continue;
/* 193 */             user.setForename(aTemp[1]);
/*     */           }
/* 198 */           else if (aTemp[0].equals(this.m_settings.getSurnameParameter()))
/*     */           {
/* 200 */             if (aTemp.length <= 1)
/*     */               continue;
/* 202 */             user.setSurname(aTemp[1]);
/*     */           }
/*     */           else
/*     */           {
/* 207 */             if (!aTemp[0].equals(this.m_settings.getGroupsParameter()))
/*     */               continue;
/* 209 */             if (aTemp.length <= 1) {
/*     */               continue;
/*     */             }
/* 212 */             Vector vecGroups = StringUtil.convertToVectorOfLongs(aTemp[1], ",");
/*     */ 
/* 215 */             user.setGroups(vecGroups);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 222 */         if (this.m_settings.getValidateEmailAddress())
/*     */         {
/* 224 */           boolean bValidEmail = false;
/* 225 */           if (user.getEmailAddress() != null)
/*     */           {
/* 227 */             String sEmailDomain = "," + user.getEmailAddress().substring(user.getEmailAddress().lastIndexOf("@") + 1, user.getEmailAddress().length()) + ",";
/* 228 */             String sAllowedDomains = "," + this.m_settings.getValidEmailDomains() + ",";
/* 229 */             if (sAllowedDomains.toLowerCase().indexOf(sEmailDomain.toLowerCase()) >= 0)
/*     */             {
/* 231 */               bValidEmail = true;
/*     */             }
/*     */           }
/* 234 */           if (!bValidEmail)
/*     */           {
/* 236 */             this.m_logger.error("EncryptedUrlSSOPlugin: Unable to login user: " + user.getUsername() + " : Invalid email address");
/* 237 */             user = null;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 243 */         if ((user != null) && (StringUtil.stringIsPopulated(user.getRemoteUsername())) && (this.m_settings.getPerformDateCheck()) && (dtDateTime != null))
/*     */         {
/* 245 */           int iToleranceSeconds = this.m_settings.getDateTimeTolerance();
/* 246 */           GregorianCalendar gmt = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
/* 247 */           GregorianCalendar compare = new GregorianCalendar();
/* 248 */           compare.set(1, gmt.get(1));
/* 249 */           compare.set(2, gmt.get(2));
/* 250 */           compare.set(5, gmt.get(5));
/* 251 */           compare.set(10, gmt.get(10));
/* 252 */           compare.set(11, gmt.get(11));
/* 253 */           compare.set(12, gmt.get(12));
/* 254 */           compare.set(13, gmt.get(13));
/* 255 */           compare.set(14, gmt.get(14));
/* 256 */           compare.getTime();
/*     */ 
/* 258 */           long lRequestTime = dtDateTime.getTime() + iToleranceSeconds * 1000;
/*     */ 
/* 260 */           if (compare.getTimeInMillis() > lRequestTime)
/*     */           {
/* 263 */             this.m_logger.error("EncryptedUrlSSOPlugin: Unable to login user: " + user.getUsername() + " : Time check failed");
/* 264 */             user = null;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 271 */       return user;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 275 */       this.m_logger.error("EncryptedUrlSSOPlugin : Error occurred: ", e);
/* 276 */     throw new Bn2Exception("EncryptedUrlSSOPlugin : Error occurred: ", e);}
/*     */   }
/*     */ 
/*     */   public String getLoginJsp()
/*     */   {
/* 282 */     return this.m_settings.getUsernameNotFoundUrl();
/*     */   }
/*     */ 
/*     */   public void doDestroyAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getForwardUrl(HttpServletRequest a_request)
/*     */   {
/* 295 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginBaseUrl()
/*     */   {
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */   public HashMap getLoginFormForPost(HttpServletRequest a_request)
/*     */   {
/* 305 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginUrlForRedirect(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 311 */     return null;
/*     */   }
/*     */ 
/*     */   public void processRequest(HttpServletRequest a_request, LoginForm a_form)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean validateForm(LoginForm a_form)
/*     */   {
/* 320 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.EncryptedUrlSSOPlugin
 * JD-Core Version:    0.6.0
 */