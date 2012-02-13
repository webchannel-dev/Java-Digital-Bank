/*     */ package com.bright.framework.storage.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.storage.bean.BaseRemoteStorageDevice;
/*     */ import com.bright.framework.storage.constant.StorageDeviceType;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class BaseRemoteStorageDeviceFactory<T extends BaseRemoteStorageDevice> extends BaseStorageDeviceFactory<T>
/*     */ {
/*  21 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public List<StorageDeviceType> getSupportedDeviceTypes()
/*     */   {
/*  25 */     return Arrays.asList(new StorageDeviceType[] { StorageDeviceType.ASSETS, StorageDeviceType.ASSETS_AND_THUMBNAILS, StorageDeviceType.THUMBNAILS });
/*     */   }
/*     */ 
/*     */   public T saveStorageDevice(DBTransaction a_dbTransaction, T a_device)
/*     */     throws Bn2Exception
/*     */   {
/*  31 */     String ksMethodName = "saveStorageDevice";
/*     */ 
/*  33 */    // a_device = (BaseRemoteStorageDevice)super.saveStorageDevice(a_dbTransaction, a_device);
                a_device = (T)super.saveStorageDevice(a_dbTransaction, a_device);
/*     */ 
/*  35 */     if (a_device.getId() > 0L)
/*     */     {
/*  37 */       Connection con = null;
/*  38 */       String sSql = null;
/*  39 */       PreparedStatement psql = null;
/*     */ 
/*  41 */       if (a_dbTransaction == null)
/*     */       {
/*  43 */         throw new NullPointerException(getClass().getSimpleName() + "." + "saveStorageDevice" + "() requires a transaction");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  48 */         con = a_dbTransaction.getConnection();
/*     */ 
/*  50 */         sSql = "UPDATE StorageDevice SET AuthenticationName=?, AuthenticationKey=? WHERE Id=?";
/*  51 */         psql = con.prepareStatement(sSql);
/*  52 */         psql.setString(1, a_device.getAuthenticationId());
/*  53 */         psql.setString(2, a_device.getAuthenticationKey());
/*  54 */         psql.setLong(3, a_device.getId());
/*  55 */         psql.executeUpdate();
/*  56 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/*  60 */         this.m_logger.error(getClass().getSimpleName() + "." + "saveStorageDevice" + " : SQL Exception : ", e);
/*  61 */         throw new Bn2Exception(getClass().getSimpleName() + "." + "saveStorageDevice" + " : SQL Exception : " + e, e);
/*     */       }
/*     */     }
/*     */ 
/*  65 */     return a_device;
/*     */   }
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */   protected String getAdditionalColumns()
/*     */   {
/*  79 */     return "AuthenticationName, AuthenticationKey";
/*     */   }
/*     */ 
/*     */   protected void populateAdditionalFields(ResultSet a_rs, T a_device)
/*     */     throws SQLException
/*     */   {
/*  86 */     a_device.setAuthenticationId(a_rs.getString("AuthenticationName"));
/*  87 */     a_device.setAuthenticationKey(a_rs.getString("AuthenticationKey"));
/*     */   }
/*     */ 
/*     */   public List<String> validateStorageDevice(T a_device)
/*     */   {
/*  93 */     List messages = super.validateStorageDevice(a_device);
/*     */ 
/*  95 */     if (StringUtils.isEmpty(a_device.getHttpBaseUrl()))
/*     */     {
/*  97 */       messages.add("Please provide a base http url as this type of device does not serve files through the local tomcat application.");
/*     */     }
/*     */ 
/* 100 */     return messages;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.BaseRemoteStorageDeviceFactory
 * JD-Core Version:    0.6.0
 */