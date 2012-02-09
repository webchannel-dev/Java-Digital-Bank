/*     */ package com.bright.assetbank.synchronise.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.synchronise.bean.ImportedUserBeanWrapper;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.bean.ImportedUser;
/*     */ import com.bright.assetbank.user.bean.UserBeanReader;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValueMapping;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.file.BeanWrapper;
import com.bright.framework.file.DefaultBeanReader;
/*     */ import com.bright.framework.file.DefaultBeanReader.BeanPopulationException;
/*     */ import com.bright.framework.file.DefaultBeanReader.NoMatchForColumnHeaderException;
/*     */ import com.bright.framework.file.DefaultBeanReader.TooManyColumnsException;
/*     */ import com.bright.framework.file.ExcelFormat;
/*     */ import com.bright.framework.file.FileFormat;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class UserImportManager extends Bn2Manager
/*     */ {
/*     */   private static final int k_iNumAssetsPerFileRead = 100;
/*  66 */   private DBTransactionManager m_transactionManager = null;
/*  67 */   private ABUserManager m_userManager = null;
/*  68 */   private FileStoreManager m_fileStoreManager = null;
/*  69 */   private CustomFieldManager m_cfManager = null;
/*     */ 
/*     */   public int importUserData(String a_sFileLocation, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     String sFileLocation = null;
/*  86 */     BufferedReader reader = null;
/*  87 */     int iUsersAdded = 0;
/*     */ 
/*  89 */     sFileLocation = this.m_fileStoreManager.getAbsolutePath(a_sFileLocation);
/*     */ 
/*  92 */     Vector vecAllGroups = this.m_userManager.getGroups(null);
/*     */     try
/*     */     {
/*  96 */       reader = new BufferedReader(new FileReader(new File(sFileLocation)));
/*  97 */       FileFormat format = new ExcelFormat();
/*  98 */       BeanWrapper wrapper = new ImportedUserBeanWrapper();
/*  99 */       UserBeanReader assetReader = null;
/*     */       try
/*     */       {
/* 103 */         assetReader = new UserBeanReader(reader, format, ImportedUser.class, wrapper);
/*     */       }
/*     */       catch (DefaultBeanReader.NoMatchForColumnHeaderException e)
/*     */       {
/* 110 */         this.m_logger.error("UserImportManager.importUserData() : NoMatchForColumnHeaderException reading data file header : " + e.getMessage());
/* 111 */         throw new Bn2Exception("UserImportManager.importUserData() : NoMatchForColumnHeaderException due to unexpected column", e);
/*     */       }
/*     */ 
/* 114 */       Vector vUsers = null;
/* 115 */       int iLineNo = 1;
/* 116 */       boolean bFinished = false;
/* 117 */       HashSet hmUsers = new HashSet();
/*     */       do
/*     */       {
/*     */         try
/*     */         {
/* 124 */           vUsers = assetReader.readBeans(100);
/*     */ 
/* 126 */           if (vUsers.size() > 0)
/*     */           {
/* 129 */             for (int i = 0; i < vUsers.size(); i++)
/*     */             {
/* 131 */               iLineNo++;
/* 132 */               ImportedUser user = (ImportedUser)vUsers.get(i);
/*     */ 
/* 135 */               String fullName = user.getFullName().toLowerCase();
/* 136 */               if ((user.getUsername() == null) || (hmUsers.contains(fullName))) {
/*     */                 continue;
/*     */               }
/* 139 */               saveImportedUser(user, vecAllGroups);
/* 140 */               hmUsers.add(fullName);
/* 141 */               iUsersAdded++;
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 147 */             bFinished = true;
/*     */           }
/*     */         }
/*     */         catch (DefaultBeanReader.TooManyColumnsException e)
/*     */         {
/* 152 */           this.m_logger.error("UserImportManager.importUserData() : TooManyColumnsException reading line " + (iLineNo + 1) + ": " + e.getMessage());
/* 153 */           iLineNo += 100;
/*     */         }
/*     */         catch (DefaultBeanReader.BeanPopulationException e)
/*     */         {
/* 157 */           this.m_logger.error("UserImportManager.importUserData() : BeanPopulationException reading line " + (iLineNo + 1) + ": " + e.getMessage());
/* 158 */           iLineNo += 100;
/*     */         }
/*     */       }
/* 161 */       while (!bFinished);
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/* 165 */       throw new Bn2Exception("AssetImportManager.importAssetData() : input data file not found: " + sFileLocation, fnfe);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 169 */       throw new Bn2Exception("AssetImportManager.importAssetData() : IO problem with data file : " + sFileLocation, ioe);
/*     */     }
/* 171 */     return iUsersAdded;
/*     */   }
/*     */ 
/*     */   private void saveImportedUser(ImportedUser a_user, Vector a_vecAllGroups)
/*     */     throws Bn2Exception
/*     */   {
/* 193 */     DBTransaction dbTransaction = null;
/*     */     try
/*     */     {
/* 198 */       String[] saGroupNames = null;
/* 199 */       HashMap hmGroupIdsToAdd = new HashMap();
/*     */ 
/* 201 */       if (a_user.getGroupNames() != null)
/*     */       {
/* 204 */         saGroupNames = a_user.getGroupNames().split(";");
/*     */       }
/*     */ 
/* 208 */       for (int i = 0; (saGroupNames != null) && (i < saGroupNames.length); i++)
/*     */       {
/* 211 */         for (int j = 0; j < a_vecAllGroups.size(); j++)
/*     */         {
/* 213 */           Group group = (Group)a_vecAllGroups.get(j);
/*     */ 
/* 216 */           if ((saGroupNames[i] == null) || (!group.getName().equalsIgnoreCase(saGroupNames[i].trim())))
/*     */             continue;
/* 218 */           hmGroupIdsToAdd.put(new Long(group.getId()), null);
/* 219 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 225 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 227 */       ABUser userToSave = a_user;
/*     */ 
/* 230 */       if (this.m_userManager.isUsernameInUse(dbTransaction, a_user.getUsername()))
/*     */       {
/* 232 */         String sSql = "SELECT Id FROM AssetBankUser WHERE Username=?";
/* 233 */         Connection con = dbTransaction.getConnection();
/* 234 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 235 */         psql.setString(1, a_user.getUsername());
/* 236 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 238 */         if (rs.next())
/*     */         {
/* 240 */           userToSave = (ABUser)this.m_userManager.getUser(dbTransaction, rs.getLong("Id"));
/* 241 */           psql.close();
/* 242 */           userToSave.overwriteWithPopulatedStringValues(a_user);
/*     */         }
/*     */         else
/*     */         {
/* 247 */           psql.close();
/* 248 */           this.m_logger.error("UserImportManager.saveImportedUser: Unable to import user with username: " + a_user.getUsername() + " username exists, but unable to find id");
/*     */         }
/*     */       }
/*     */ 
/* 252 */       this.m_userManager.saveUser(dbTransaction, userToSave);
/*     */ 
/* 255 */       if (hmGroupIdsToAdd.size() > 0)
/*     */       {
/* 258 */         if (userToSave.getGroupCount() > 0)
/*     */         {
/* 260 */           for (int i = 0; i < userToSave.getGroups().size(); i++)
/*     */           {
/* 262 */             Group existingGroup = (Group)userToSave.getGroups().elementAt(i);
/*     */ 
/* 265 */             hmGroupIdsToAdd.put(new Long(existingGroup.getId()), null);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 270 */         hmGroupIdsToAdd.put(new Long(1L), null);
/*     */ 
/* 273 */         Iterator itGroupIds = hmGroupIdsToAdd.keySet().iterator();
/* 274 */         Vector vecGroupIdsToAdd = new Vector();
/* 275 */         while (itGroupIds.hasNext())
/*     */         {
/* 277 */           vecGroupIdsToAdd.add(itGroupIds.next());
/*     */         }
/*     */ 
/* 280 */         this.m_userManager.addUserToGroups(dbTransaction, userToSave.getId(), vecGroupIdsToAdd);
/*     */       }
/*     */ 
/* 286 */       if ((a_user.getCustomFieldValues() != null) && (a_user.getCustomFieldValues().getSelectedValues() != null) && (a_user.getCustomFieldValues().getSelectedValues().size() > 0))
/*     */       {
/* 290 */         a_user.getCustomFieldValues().setItemId(userToSave.getId());
/* 291 */         for (int i = 0; i < a_user.getCustomFieldValues().getSelectedValues().size(); i++)
/*     */         {
/* 293 */           CustomFieldValueMapping val = (CustomFieldValueMapping)a_user.getCustomFieldValues().getSelectedValues().elementAt(i);
/* 294 */           val.setItemId(userToSave.getId());
/*     */         }
/*     */ 
/* 297 */         this.m_cfManager.saveValueMappings(null, a_user.getCustomFieldValues());
/*     */       }
/*     */     }
/*     */     catch (Exception be)
/*     */     {
/* 302 */       this.m_logger.error("Exception in DataImportManager:" + be.getMessage());
/*     */ 
/* 304 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 308 */           dbTransaction.rollback();
/*     */         }
/*     */         catch (Exception e2)
/*     */         {
/* 312 */           this.m_logger.error("Exception rolling back transaction in DataImportManager:" + e2.getMessage());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 317 */       Bn2Exception ex = new Bn2Exception(be.getMessage(), be);
/* 318 */       throw ex;
/*     */     }
/*     */     finally
/*     */     {
/* 322 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 326 */           dbTransaction.commit();
/*     */         }
/*     */         catch (Exception e2)
/*     */         {
/* 330 */           this.m_logger.error("Exception committing transaction in DataImportManager:" + e2.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/* 339 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_sUserManager)
/*     */   {
/* 344 */     this.m_userManager = a_sUserManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_manager)
/*     */   {
/* 349 */     this.m_fileStoreManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_cfManager)
/*     */   {
/* 354 */     this.m_cfManager = a_cfManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.UserImportManager
 * JD-Core Version:    0.6.0
 */