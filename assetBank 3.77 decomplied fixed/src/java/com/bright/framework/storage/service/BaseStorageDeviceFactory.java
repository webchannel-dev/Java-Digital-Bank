/*     */ package com.bright.framework.storage.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.storage.bean.BaseStorageDevice;
/*     */ import com.bright.framework.storage.constant.StorageDeviceType;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class BaseStorageDeviceFactory<T extends BaseStorageDevice>
/*     */   implements StorageDeviceFactory<T>
/*     */ {
/*  44 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public String getFactoryClassName()
/*     */   {
/*  53 */     Class clazz = getClass();
/*  54 */     while (clazz.getCanonicalName() == null)
/*     */     {
/*  56 */       clazz = clazz.getSuperclass();
/*     */     }
/*  58 */     return clazz.getName();
/*     */   }
/*     */ 
/*     */   public T saveStorageDevice(DBTransaction a_dbTransaction, T a_device)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     String ksMethodName = "saveStorageDevice";
/*     */ 
/*  70 */     Connection con = null;
/*  71 */     String sSql = null;
/*  72 */     PreparedStatement psql = null;
/*     */ 
/*  74 */     if (a_dbTransaction == null)
/*     */     {
/*  76 */       throw new NullPointerException(getClass().getSimpleName() + "." + "saveStorageDevice" + "() requires a transaction");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  81 */       con = a_dbTransaction.getConnection();
/*     */ 
/*  83 */       if (a_device.getId() <= 0L)
/*     */       {
/*  85 */         AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*  86 */         long lNewId = 0L;
/*  87 */         int iSeq = 1;
/*     */ 
/*  89 */         sSql = "SELECT MAX(SequenceNumber) SeqNo FROM StorageDevice";
/*  90 */         psql = con.prepareStatement(sSql);
/*  91 */         ResultSet rs = psql.executeQuery();
/*     */ 
/*  93 */         if (rs.next())
/*     */         {
/*  95 */           iSeq = rs.getInt("SeqNo") + 1;
/*     */         }
/*     */ 
/*  98 */         sSql = "INSERT INTO StorageDevice (";
/*     */ 
/* 100 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 102 */           lNewId = sqlGenerator.getUniqueId(con, "StorageDeviceSequence");
/* 103 */           sSql = sSql + "Id,";
/*     */         }
/*     */ 
/* 106 */         sSql = sSql + "Name, StorageTypeId, Path, IsRelative, MaxSpaceInMb, UsedSpaceInBytes, LastScanTime, IsLocked, SequenceNumber, FactoryClassName, HttpBaseUrl) VALUES (?,?,?,?,?,?,?,?,?,?,?";
/*     */ 
/* 109 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 111 */           sSql = sSql + ",?";
/*     */         }
/*     */ 
/* 114 */         sSql = sSql + ")";
/*     */ 
/* 116 */         int iCol = 1;
/*     */ 
/* 118 */         psql = con.prepareStatement(sSql);
/*     */ 
/* 120 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 122 */           psql.setLong(iCol++, lNewId);
/*     */         }
/*     */ 
/* 125 */         psql.setString(iCol++, a_device.getName());
/* 126 */         psql.setLong(iCol++, a_device.getType().getId());
/* 127 */         psql.setString(iCol++, a_device.getLocalBasePath());
/* 128 */         psql.setBoolean(iCol++, a_device.isLocalBasePathRelative());
/* 129 */         DBUtil.setFieldLongOrNull(psql, iCol++, a_device.getMaxSpaceInMb());
/* 130 */         psql.setLong(iCol++, a_device.getUsedSpaceInBytes());
/* 131 */         DBUtil.setFieldTimestampOrNull(psql, iCol++, a_device.getLastUsageScan());
/* 132 */         psql.setBoolean(iCol++, a_device.isLocked());
/* 133 */         psql.setInt(iCol++, iSeq);
/* 134 */         psql.setString(iCol++, a_device.getFactoryClassName());
/* 135 */         psql.setString(iCol++, a_device.getHttpBaseUrl());
/*     */ 
/* 137 */         psql.executeUpdate();
/*     */ 
/* 140 */         if (sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 142 */           lNewId = sqlGenerator.getUniqueId(con, "StorageDevice");
/*     */         }
/*     */ 
/* 145 */         a_device.setId(lNewId);
/*     */       }
/*     */       else
/*     */       {
/* 151 */         sSql = "SELECT Path, IsRelative, UsedLocalSpaceInBytes FROM StorageDevice";
/* 152 */         psql = con.prepareStatement(sSql);
/* 153 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 155 */         if (rs.next())
/*     */         {
/* 157 */           if ((a_device.getLocalBasePath().equalsIgnoreCase(rs.getString("Path"))) && (a_device.isLocalBasePathRelative() == rs.getBoolean("IsRelative")))
/*     */           {
/* 159 */             a_device.setUsedLocalSpaceInBytes(rs.getLong("UsedLocalSpaceInBytes"));
/*     */           }
/*     */           else
/*     */           {
/* 163 */             a_device.setUsedLocalSpaceInBytes(0L);
/*     */           }
/*     */         }
/*     */ 
/* 167 */         sSql = "UPDATE StorageDevice SET Name=?, StorageTypeId=?, Path=?, IsRelative=?, MaxSpaceInMb=?, IsLocked=?, UsedSpaceInBytes=?, UsedLocalSpaceInBytes=?, HttpBaseUrl=? WHERE Id=?";
/*     */ 
/* 171 */         psql = con.prepareStatement(sSql);
/*     */ 
/* 173 */         int iCol = 1;
/* 174 */         psql.setString(iCol++, a_device.getName());
/* 175 */         psql.setLong(iCol++, a_device.getType().getId());
/* 176 */         psql.setString(iCol++, a_device.getLocalBasePath());
/* 177 */         psql.setBoolean(iCol++, a_device.isLocalBasePathRelative());
/* 178 */         DBUtil.setFieldLongOrNull(psql, iCol++, a_device.getMaxSpaceInMb());
/* 179 */         psql.setBoolean(iCol++, a_device.isLocked());
/* 180 */         psql.setLong(iCol++, a_device.getUsedSpaceInBytes());
/* 181 */         psql.setLong(iCol++, a_device.getUsedLocalSpaceInBytes());
/* 182 */         psql.setString(iCol++, a_device.getHttpBaseUrl());
/* 183 */         psql.setLong(iCol++, a_device.getId());
/*     */ 
/* 185 */         psql.executeUpdate();
/*     */       }
/*     */ 
/* 188 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 192 */       this.m_logger.error(getClass().getSimpleName() + "." + "saveStorageDevice" + " : SQL Exception : ", e);
/* 193 */       throw new Bn2Exception(getClass().getSimpleName() + "." + "saveStorageDevice" + " : SQL Exception : " + e, e);
/*     */     }
/* 195 */     return a_device;
/*     */   }
/*     */ 
/*     */   public List<String> validateStorageDevice(T a_device)
/*     */   {
/* 204 */     List messages = new ArrayList();
/*     */ 
/* 206 */     if (a_device.getType() == null)
/*     */     {
/* 208 */       messages.add("Please select a type for the storage device.");
/*     */     }
/*     */ 
/* 211 */     return messages;
/*     */   }
/*     */ 
/*     */   public T loadStorageDevice(DBTransaction a_dbTransaction, long a_lId, boolean a_bGetAssetUse)
/*     */     throws Bn2Exception
/*     */   {
/* 221 */     String ksMethodName = "getDevice";
/*     */ 
/* 223 */     BaseStorageDevice device = null;
/* 224 */     Connection con = null;
/* 225 */     String sSql = null;
/* 226 */     PreparedStatement psql = null;
/*     */ 
/* 228 */     if (a_dbTransaction == null)
/*     */     {
/* 230 */       throw new NullPointerException(getClass().getSimpleName() + ".loadStorageDevice() requires a transaction, but the passed transaction is null.");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 235 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 237 */       String sAdditionalColumns = getAdditionalColumns();
/*     */ 
/* 239 */       sSql = "SELECT Id, Name, StorageTypeId, Path, HttpBaseUrl,MaxSpaceInMb, UsedSpaceInBytes, UsedLocalSpaceInBytes, LastScanTime, IsLocked ";
/*     */ 
/* 251 */       if (StringUtils.isNotEmpty(sAdditionalColumns))
/*     */       {
/* 253 */         sSql = sSql + "," + sAdditionalColumns;
/*     */       }
/*     */ 
/* 256 */       sSql = sSql + " FROM StorageDevice WHERE Id=?";
/*     */ 
/* 261 */       psql = con.prepareStatement(sSql);
/* 262 */       psql.setLong(1, a_lId);
/*     */ 
/* 264 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 266 */       if (rs.next())
/*     */       {
/* 268 */         device = (BaseStorageDevice)createNewStorageDevice();
/* 269 */         device.setId(rs.getLong("Id"));
/* 270 */         device.setName(rs.getString("Name"));
/* 271 */         device.setType(StorageDeviceType.forId(rs.getLong("StorageTypeId")));
/* 272 */         device.setLocalBasePath(rs.getString("Path"));
/* 273 */         device.setHttpBaseUrl(rs.getString("HttpBaseUrl"));
/* 274 */         device.setMaxSpaceInMb(rs.getLong("MaxSpaceInMb"));
/* 275 */         device.setUsedSpaceInBytes(rs.getLong("UsedSpaceInBytes"));
/* 276 */         device.setUsedLocalSpaceInBytes(rs.getLong("UsedLocalSpaceInBytes"));
/* 277 */         device.setLastUsageScan(rs.getTimestamp("LastScanTime"));
/* 278 */         device.setLocked(rs.getBoolean("IsLocked"));
/*     */ 
/* 280 */         populateAdditionalFields(rs,(T) device);
                   // populateAdditionalFields(rs,T);
/*     */       }
/*     */ 
/* 283 */       psql.close();
/*     */ 
/* 285 */       if (a_bGetAssetUse)
/*     */       {
/* 287 */         sSql = "SELECT COUNT(*) NumAssets FROM Asset WHERE 1=0 ";
/*     */ 
/* 289 */         ApplicationSql sqlGen = SQLGenerator.getInstance();
/*     */ 
/* 291 */         if (device.getId() == 1L)
/*     */         {
/* 293 */           if (device.isStorageFor(StorageDeviceType.ASSETS))
/*     */           {
/* 295 */             sSql = sSql + " OR (NOT " + sqlGen.getNullCheckStatement("FileLocation") + " AND FileLocation NOT LIKE '%:%') " + " OR (NOT " + sqlGen.getNullCheckStatement("OriginalFileLocation") + " AND OriginalFileLocation NOT LIKE '%:%') ";
/*     */           }
/*     */ 
/* 298 */           if (device.isStorageFor(StorageDeviceType.THUMBNAILS))
/*     */           {
/* 300 */             sSql = sSql + " OR (NOT " + sqlGen.getNullCheckStatement("FileLocation") + " AND FileLocation NOT LIKE '%:%') " + " OR (NOT " + sqlGen.getNullCheckStatement("SmallFileLocation") + " AND SmallFileLocation NOT LIKE '%:%') ";
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 306 */           if (device.isStorageFor(StorageDeviceType.ASSETS))
/*     */           {
/* 308 */             sSql = sSql + " OR FileLocation LIKE '" + device.getId() + ":%' OR OriginalFileLocation LIKE '" + device.getId() + ":%' ";
/*     */           }
/* 310 */           if (device.isStorageFor(StorageDeviceType.THUMBNAILS))
/*     */           {
/* 312 */             sSql = sSql + " OR FileLocation LIKE '" + device.getId() + ":%' OR ThumbnailFileLocation LIKE '" + device.getId() + ":%' ";
/*     */           }
/*     */         }
/*     */ 
/* 316 */         psql = con.prepareStatement(sSql);
/*     */ 
/* 318 */         rs = psql.executeQuery();
/*     */ 
/* 320 */         rs.next();
/*     */ 
/* 322 */         device.setNumStoredAssets(rs.getInt("NumAssets"));
/*     */ 
/* 324 */         psql.close();
/*     */ 
/* 326 */         if (a_bGetAssetUse)
/*     */         {
/* 328 */           device.runUsageScan();
/*     */         }
/*     */       }
/*     */ 
/* 332 */       return (T)device;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 336 */       this.m_logger.error(getClass().getSimpleName() + "." + "getDevice" + " : SQL Exception : " + e);
/* 337 */     throw new Bn2Exception(getClass().getSimpleName() + "." + "getDevice" + " : SQL Exception : " + e, e);}
/*     */   }
/*     */ 
/*     */   protected String getAdditionalColumns()
/*     */   {
/* 348 */     return "";
/*     */   }
/*     */ 
/*     */   protected void populateAdditionalFields(ResultSet a_rs, T device)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.BaseStorageDeviceFactory
 * JD-Core Version:    0.6.0
 */