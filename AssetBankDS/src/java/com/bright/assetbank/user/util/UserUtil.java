/*     */ package com.bright.assetbank.user.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.updater.service.ApplicationSettingsUtil;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class UserUtil
/*     */ {
/*     */   public static boolean isUserApprovalEnabled(CustomFieldManager a_customFieldManager)
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     if ((!AssetBankSettings.getUsersCanRegisterWithoutApproval()) || (AssetBankSettings.getUseBrands()))
/*     */     {
/*  56 */       return true;
/*     */     }
/*     */ 
/*  61 */     if (!AssetBankSettings.getExternalUsersCanRegisterWithoutApproval())
/*     */     {
/*  63 */       return (a_customFieldManager.subtypeFieldsExist(null, 1L)) || (StringUtils.isNotEmpty(AssetBankSettings.getLocalUserEmailDomain()));
/*     */     }
/*     */ 
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   public static void encryptPasswords()
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  81 */       setEncryptingPasswords(null, true);
/*     */ 
/*  83 */       DBTransaction transaction = null;
/*     */       try
/*     */       {
/*  87 */         DBTransactionManager transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/*  88 */         transaction = transactionManager.getNewTransaction();
/*     */ 
/*  91 */         ApplicationSettingsUtil appUtil = new ApplicationSettingsUtil();
/*  92 */         appUtil.updateSetting("encrypt-passwords", "true", null, null);
/*  93 */         appUtil.saveSettings();
/*  94 */         UserSettings.forcePasswordEncryption();
/*     */ 
/*  96 */         UserManager userManager = (UserManager)(UserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/*     */ 
/*  99 */         Vector<User> vecUsers = userManager.getAllUsers(transaction);
/*     */ 
/* 104 */         for (User user : vecUsers)
/*     */         {
/* 106 */           userManager.changePassword(transaction, user.getId(), user.getPassword());
/*     */         }
/*     */ 
/* 111 */         setEncryptingPasswords(transaction, false);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 115 */         if (transaction != null)
/*     */           try {
/* 117 */             transaction.rollback(); } catch (Exception ex) {
/*     */           }
/* 119 */         throw new Bn2Exception("UserUtil.encryptPasswords: Error: ", e);
/*     */       }
/*     */       finally
/*     */       {
/* 123 */         if (transaction != null)
/*     */           try {
/* 125 */             transaction.commit();
/*     */           } catch (Exception ex) {
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 131 */       throw new Bn2Exception("UserUtil.encryptPasswords: Error encrypting passwords: ", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setEncryptingPasswords(DBTransaction a_dbTransaction, boolean a_bEncrypting)
/*     */     throws Bn2Exception
/*     */   {
/* 142 */     DBTransaction transaction = a_dbTransaction;
/*     */     try
/*     */     {
/* 146 */       if (transaction == null)
/*     */       {
/* 148 */         DBTransactionManager transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 149 */         transaction = transactionManager.getNewTransaction();
/*     */       }
/*     */ 
/* 152 */       RefDataManager refDataManager = (RefDataManager)(RefDataManager)GlobalApplication.getInstance().getComponentManager().lookup("RefDataManager");
/* 153 */       refDataManager.setSystemSetting(transaction, "ConvertingPasswords", String.valueOf(a_bEncrypting));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 157 */       if ((transaction != null) && (a_dbTransaction == null))
/*     */         try {
/* 159 */           transaction.rollback(); } catch (Exception ex) {
/*     */         }
/* 161 */       throw new Bn2Exception("UserUtil.setEncryptingPasswords: Error setting system setting: ", e);
/*     */     }
/*     */     finally
/*     */     {
/* 165 */       if ((transaction != null) && (a_dbTransaction == null))
/*     */         try {
/* 167 */           transaction.commit();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.util.UserUtil
 * JD-Core Version:    0.6.0
 */