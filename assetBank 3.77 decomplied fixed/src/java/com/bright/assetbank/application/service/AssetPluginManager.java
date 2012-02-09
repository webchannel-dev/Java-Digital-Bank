/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionCallback;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AssetPluginManager extends Bn2Manager
/*     */   implements AssetLoadParticipant, AssetSaveParticipant, AssetDeleteParticipant
/*     */ {
/*     */   private DBTransactionManager m_transactionManager;
/*     */   private AssetManager m_assetManager;
/*     */   private PluginManager m_pluginManager;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  45 */     super.startup();
/*     */ 
/*  47 */     getAssetManager().registerAssetLoadParticipant(this);
/*  48 */     getAssetManager().registerAssetSaveParticipant(this);
/*  49 */     getAssetManager().registerAssetDeleteParticipant(this);
/*     */   }
/*     */ 
/*     */   public void assetWasLoaded(final Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     //getTransactionManager().execute(new DBTransactionCallback(a_asset)
                 getTransactionManager().execute(new DBTransactionCallback()
/*     */     {
/*     */       public Object doInTransaction(DBTransaction a_transaction)
/*     */         throws Bn2Exception
/*     */       {
/*  63 */         AssetPluginManager.this.getPluginManager().getExtensionData(a_transaction, "asset", a_asset);
/*     */ 
/*  67 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void assetsWereLoaded(final List<Asset> a_assets) throws Bn2Exception
/*     */   {
/*  75 */    // getTransactionManager().execute(new DBTransactionCallback(a_assets)
               getTransactionManager().execute(new DBTransactionCallback()
/*     */     {
/*     */       public Object doInTransaction(DBTransaction a_transaction)
/*     */         throws Bn2Exception
/*     */       {
/*  81 */         AssetPluginManager.this.getPluginManager().getExtensionDatas(a_transaction, "asset", a_assets);
/*     */ 
/*  85 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void initAssetSave(AssetSaveContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void save(final AssetSaveContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/* 101 */     getTransactionManager().execute(new DBTransactionCallback()
/*     */     {
/*     */       public Object doInTransaction(DBTransaction a_transaction)
/*     */         throws Bn2Exception
/*     */       {
/* 107 */         if (a_context.isNew())
/*     */         {
/* 109 */           AssetPluginManager.this.getPluginManager().addExtensionData(a_transaction, "asset", a_context.getAsset());
/*     */         }
/*     */         else
/*     */         {
/* 116 */           AssetPluginManager.this.getPluginManager().editExistingExtensionData(a_transaction, "asset", a_context.getAsset());
/*     */         }
/*     */ 
/* 122 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void assetWasSaved(AssetSaveContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void assetWillBeDeleted(final AssetDeleteContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/* 138 */     getTransactionManager().execute(new DBTransactionCallback()
/*     */     {
/*     */       public Object doInTransaction(DBTransaction a_transaction)
/*     */         throws SQLException, Bn2Exception
/*     */       {
/* 144 */         AssetPluginManager.this.getPluginManager().delete(a_transaction, "asset", a_context.getOriginalAsset().getId());
/*     */ 
/* 149 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void assetWasDeleted(AssetDeleteContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getTransactionManager()
/*     */   {
/* 166 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 171 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public AssetManager getAssetManager()
/*     */   {
/* 176 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 181 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public PluginManager getPluginManager()
/*     */   {
/* 186 */     return this.m_pluginManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 191 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetPluginManager
 * JD-Core Version:    0.6.0
 */