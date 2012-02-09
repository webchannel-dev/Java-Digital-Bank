/*     */ package com.bright.assetbank.custom.indesign.entity.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntityType;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class InDesignAssetEntityService
/*     */ {
/*     */   public InDesignAssetEntity get(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/*  41 */     String sSQL = null;
/*     */     try
/*     */     {
/*  45 */       Connection con = a_transaction.getConnection();
/*     */ 
/*  47 */       sSQL = "SELECT idae.InDesignAssetEntityTypeId FROM InDesignAssetEntity idae WHERE idae.AssetEntityId=?";
/*     */ 
/*  53 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  54 */       psql.setLong(1, a_lId);
/*     */ 
/*  56 */       ResultSet rs = psql.executeQuery();
/*     */       InDesignAssetEntity result;
/*     */       //InDesignAssetEntity result;
/*  59 */       if (rs.next())
/*     */       {
/*  61 */         result = InDesignAssetEntityDBUtil.createInDesignAssetEntityFromRS(rs);
/*     */       }
/*     */       else
/*     */       {
/*  65 */         result = null;
/*     */       }
/*     */ 
/*  68 */       psql.close();
/*     */ 
/*  70 */       return result;
/*     */     }
/*     */     catch (SQLException e) {
/*     */     
/*  74 */     throw new SQLStatementException(sSQL, e);}
/*     */   }
/*     */ 
/*     */   public int getTypeId(DBTransaction a_transaction, long a_lAssetEntityId)
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     InDesignAssetEntity inDAssetEntity = get(a_transaction, a_lAssetEntityId);
/*  85 */     return typeIdFromInDesignAssetEntity(inDAssetEntity);
/*     */   }
/*     */ 
/*     */   public int typeIdFromInDesignAssetEntity(InDesignAssetEntity a_inDAssetEntity)
/*     */   {
/*  90 */     return a_inDAssetEntity == null ? -1 : a_inDAssetEntity.getInDesignAssetEntityTypeId();
/*     */   }
/*     */ 
/*     */   public InDesignAssetEntity getInDesignEntityForAsset(DBTransaction a_transaction, long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 101 */     String sSQL = null;
/*     */     try
/*     */     {
/* 105 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 107 */       sSQL = "SELECT idae.InDesignAssetEntityTypeId FROM InDesignAssetEntity idae JOIN Asset a ON a.AssetEntityId = idae.AssetEntityId WHERE a.Id = ?";
/*     */ 
/* 114 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 115 */       psql.setLong(1, a_lAssetId);
/*     */ 
/* 117 */       ResultSet rs = psql.executeQuery();
/*     */       InDesignAssetEntity result;
/*     */       //InDesignAssetEntity result;
/* 120 */       if (rs.next())
/*     */       {
/* 122 */         result = InDesignAssetEntityDBUtil.createInDesignAssetEntityFromRS(rs);
/*     */       }
/*     */       else
/*     */       {
/* 126 */         result = null;
/*     */       }
/*     */ 
/* 129 */       psql.close();
/*     */ 
/* 131 */       return result;
/*     */     }
/*     */     catch (SQLException e) {
/*     */     
/* 135 */     throw new SQLStatementException(sSQL, e);}
/*     */   }
/*     */ 
/*     */   public void addOrUpdate(DBTransaction a_transaction, long a_lId, InDesignAssetEntity a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/* 148 */     String sSQL = null;
/*     */ 
/* 153 */     if ((a_extensionData == null) || (a_extensionData.getInDesignAssetEntityTypeId() <= 0))
/*     */     {
/* 157 */       delete(a_transaction, a_lId);
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 163 */         Connection con = a_transaction.getConnection();
/*     */ 
/* 166 */         if (!SQLGenerator.getInstance().getSupportsOnDuplicateKeyUpdate())
/*     */         {
/* 168 */           delete(a_transaction, a_lId);
/*     */ 
/* 170 */           sSQL = "INSERT INTO InDesignAssetEntity (AssetEntityId, InDesignAssetEntityTypeId) VALUES (?, ?)";
/*     */         }
/*     */         else
/*     */         {
/* 176 */           sSQL = "INSERT INTO InDesignAssetEntity (AssetEntityId, InDesignAssetEntityTypeId) VALUES (?, ?) ON DUPLICATE KEY UPDATE InDesignAssetEntityTypeId = VALUES(InDesignAssetEntityTypeId)";
/*     */         }
/*     */ 
/* 181 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 182 */         psql.setLong(1, a_lId);
/* 183 */         psql.setInt(2, a_extensionData.getInDesignAssetEntityTypeId());
/*     */ 
/* 185 */         psql.executeUpdate();
/* 186 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 190 */         throw new SQLStatementException(sSQL, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 200 */     String sSQL = null;
/*     */     try
/*     */     {
/* 204 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 206 */       sSQL = "DELETE FROM InDesignAssetEntity WHERE AssetEntityId = ?";
/*     */ 
/* 208 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 209 */       psql.setLong(1, a_lId);
/*     */ 
/* 211 */       psql.executeUpdate();
/* 212 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 217 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<InDesignAssetEntityType> getInDesignAssetEntityTypes(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 227 */     String sSQL = "SELECT Id, Name FROM InDesignAssetEntityType ORDER BY Id";
/*     */     List results;
/*     */     try
/*     */     {
/* 233 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 235 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*     */ 
/* 237 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 239 */       results = new ArrayList();
/* 240 */       while (rs.next())
/*     */       {
/* 242 */         InDesignAssetEntityType t = new InDesignAssetEntityType();
/* 243 */         t.setId(rs.getInt("Id"));
/* 244 */         t.setName(rs.getString("Name"));
/* 245 */         results.add(t);
/*     */       }
/*     */ 
/* 248 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 252 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */ 
/* 255 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean assetsOfEntityExist(DBTransaction a_transaction, long a_lAssetEntityId) throws Bn2Exception {
/* 267 */     String sSQL = "SELECT Id FROM Asset WHERE AssetEntityId=?";
/*     */     boolean exist;
/*     */     try {
/* 270 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 272 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 273 */       psql.setLong(1, a_lAssetEntityId);
/*     */ 
/* 275 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 277 */       exist = rs.next();
/*     */ 
/* 279 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 283 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */ 
/* 286 */     return exist;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityService
 * JD-Core Version:    0.6.0
 */