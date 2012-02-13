package com.bright.assetbank.application.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.bean.Asset;
import com.bright.assetbank.application.bean.AssetConversionInfo;
import com.bright.assetbank.application.bean.AssetFileSource;
import com.bright.assetbank.application.bean.FileFormat;
import com.bright.assetbank.application.bean.ImageAsset;
import com.bright.assetbank.application.bean.LightweightAsset;
import com.bright.assetbank.application.bean.UploadedFileInfo;
import com.bright.assetbank.application.exception.AssetFileReadException;
import com.bright.assetbank.application.exception.AssetNotFoundException;
import com.bright.assetbank.attribute.bean.Attribute;
import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
import com.bright.assetbank.user.bean.ABUserProfile;
import com.bright.assetbank.workflow.bean.WorkflowUpdate;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.language.bean.Language;
import com.bright.framework.search.bean.SearchResults;
import com.bright.framework.storage.constant.StoredFileType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public abstract interface IAssetManager
{
  public abstract FileFormat getFileFormatForExtension(DBTransaction paramDBTransaction, String paramString)
    throws Bn2Exception;

  public abstract FileFormat getFileFormatForFile(DBTransaction paramDBTransaction, String paramString)
    throws Bn2Exception;

  public abstract FileFormat getFileFormatForFile(DBTransaction paramDBTransaction, String paramString, long paramLong)
    throws Bn2Exception;

  public abstract Vector<FileFormat> getAllFileFormatsForFile(DBTransaction paramDBTransaction, String paramString)
    throws Bn2Exception;

  public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong)
    throws AssetNotFoundException, Bn2Exception;

  public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong, Vector paramVector)
    throws AssetNotFoundException, Bn2Exception;

  public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong, Vector paramVector, boolean paramBoolean1, boolean paramBoolean2)
    throws AssetNotFoundException, Bn2Exception;

  public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong, Vector paramVector, boolean paramBoolean1, Language paramLanguage, boolean paramBoolean2)
    throws AssetNotFoundException, Bn2Exception;

  public abstract Asset getAssetStaticOnly(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract Asset getAssetStaticOnly(DBTransaction paramDBTransaction, long paramLong, Asset paramAsset, boolean paramBoolean)
    throws Bn2Exception;

  public abstract Vector getImportedAssets(DBTransaction paramDBTransaction, String paramString, Vector paramVector, boolean paramBoolean, long paramLong)
    throws AssetNotFoundException, Bn2Exception;

  public abstract Vector<Asset> getAssets(DBTransaction paramDBTransaction, Vector<Long> paramVector, boolean paramBoolean1, boolean paramBoolean2)
    throws Bn2Exception;

  public abstract Vector getAssetsForViewByPopularity(DBTransaction paramDBTransaction, Vector paramVector, boolean paramBoolean)
    throws Bn2Exception;

  public abstract Vector<Asset> getAssetsByIdAndBatchSize(DBTransaction paramDBTransaction, Vector paramVector1, Vector paramVector2, long paramLong, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Bn2Exception;

  public abstract Asset saveAsset(DBTransaction paramDBTransaction, Asset paramAsset, AssetFileSource paramAssetFileSource1, long paramLong, AssetConversionInfo paramAssetConversionInfo, AssetFileSource paramAssetFileSource2, boolean paramBoolean, int paramInt, WorkflowUpdate paramWorkflowUpdate)
    throws Bn2Exception, AssetFileReadException;

  public abstract Asset saveAsset(DBTransaction paramDBTransaction, Asset paramAsset, AssetFileSource paramAssetFileSource1, long paramLong1, AssetConversionInfo paramAssetConversionInfo, AssetFileSource paramAssetFileSource2, boolean paramBoolean, int paramInt, long paramLong2, WorkflowUpdate paramWorkflowUpdate)
    throws Bn2Exception, AssetFileReadException;

  public abstract Asset saveAsset(DBTransaction paramDBTransaction, Asset paramAsset, AssetFileSource paramAssetFileSource1, long paramLong1, AssetConversionInfo paramAssetConversionInfo, AssetFileSource paramAssetFileSource2, boolean paramBoolean1, int paramInt, long paramLong2, WorkflowUpdate paramWorkflowUpdate, boolean paramBoolean2, boolean paramBoolean3)
    throws Bn2Exception, AssetFileReadException;

  public abstract Asset saveAsset(DBTransaction paramDBTransaction, Asset paramAsset, AssetFileSource paramAssetFileSource1, long paramLong, AssetConversionInfo paramAssetConversionInfo, AssetFileSource paramAssetFileSource2, boolean paramBoolean, int paramInt)
    throws Bn2Exception, AssetFileReadException;

  public abstract void deleteAsset(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;
  
  public abstract Vector<Long> deleteAssetRelationship(DBTransaction a_dbTransaction, long a_lParentAssetId, long a_lChildAssetId, long a_lRelationshipTypeId) throws Bn2Exception;
  
  public abstract String getDownloadableAssetPath(Asset paramAsset, String paramString, AssetConversionInfo paramAssetConversionInfo)
    throws Bn2Exception;

  public abstract boolean canConvertToFileFormat(Asset paramAsset, FileFormat paramFileFormat)
    throws Bn2Exception;

  public abstract boolean userCanViewAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanUpdateAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanDeleteAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanDownloadAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanDownloadWithApprovalAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanDownloadAssetNow(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userApprovedForAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userApprovedForHighResAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userApprovalIsPending(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanDownloadOriginal(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanDownloadAdvanced(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanReviewAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userMustRequestApprovalForHighRes(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract boolean userCanViewRestrictedAsset(ABUserProfile paramABUserProfile, Asset paramAsset)
    throws Bn2Exception;

  public abstract Vector<Attribute> getAssetAttributes(DBTransaction paramDBTransaction, Vector paramVector)
    throws Bn2Exception;

  public abstract Vector<Attribute> getAssetAttributes(DBTransaction paramDBTransaction, Vector paramVector, boolean paramBoolean1, boolean paramBoolean2)
    throws Bn2Exception;

  public abstract Attribute getAssetAttribute(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract Vector getSelectedListboxCategoryValues(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract UploadedFileInfo storeTempUploadedFile(AssetFileSource paramAssetFileSource, StoredFileType paramStoredFileType)
    throws Bn2Exception;

  public abstract UploadedFileInfo storeTempUploadedFile(AssetFileSource paramAssetFileSource, FileFormat paramFileFormat, StoredFileType paramStoredFileType)
    throws Bn2Exception;

  public abstract UploadedFileInfo getUploadedFileInfo(String paramString, long paramLong, boolean paramBoolean)
    throws Bn2Exception;

  public abstract FileFormat getFileFormat(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract void addAssetToPromoted(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract void removeAssetFromPromoted(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract SearchResults getPromotedAssets(DBTransaction paramDBTransaction, int paramInt1, int paramInt2, Vector paramVector, ABUserProfile paramABUserProfile)
    throws Bn2Exception;

  public abstract LightweightAsset[] getPromotedAssets(DBTransaction paramDBTransaction, int paramInt, Vector paramVector, ABUserProfile paramABUserProfile)
    throws Bn2Exception;

  public abstract void removeAllFromPromoted(DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract Asset getFeaturedImage(DBTransaction paramDBTransaction, long paramLong1, Vector paramVector, String paramString, long paramLong2)
    throws Bn2Exception;

  public abstract void rotateFeaturedImage(DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract Vector getFeaturedAssets(DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract void generateFeaturedImages(DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract SearchResults getRecentAssets(DBTransaction paramDBTransaction, int paramInt1, ABUserProfile paramABUserProfile, int paramInt2, int paramInt3, boolean paramBoolean)
    throws Bn2Exception;

  public abstract Vector getAssetVersions(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract IAssetManagerImpl getAssetManagerForAssetType(long paramLong)
    throws Bn2Exception;

  public abstract SearchResults getAssetsByDownloads(int paramInt1, ABUserProfile paramABUserProfile, int paramInt2, int paramInt3, String paramString, boolean paramBoolean)
    throws Bn2Exception;

  public abstract SearchResults getAssetsByViews(int paramInt1, ABUserProfile paramABUserProfile, int paramInt2, int paramInt3, String paramString, boolean paramBoolean)
    throws Bn2Exception;

  public abstract int getTotalAssetCount(DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract void updateDateLastModifiedForAssets(DBTransaction paramDBTransaction, Date paramDate, String paramString)
    throws Bn2Exception;

  public abstract void updateDateLastModifiedForAssets(DBTransaction paramDBTransaction, Date paramDate, Vector paramVector)
    throws Bn2Exception;

  public abstract boolean canViewRestrictedAsset(ABUserProfile paramABUserProfile, LightweightAsset paramLightweightAsset)
    throws Bn2Exception;

  public abstract Vector getAmbiguousFileFormats(DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract void clearAssetCaches();

  public abstract boolean userCanApproveAssetsForCategory(ABUserProfile paramABUserProfile, long paramLong);

  public abstract String getTemporaryLargeFile(ImageAsset paramImageAsset, int paramInt1, int paramInt2, boolean paramBoolean)
    throws Bn2Exception;

  public abstract void setAssetNonDuplicateStatus(DBTransaction paramDBTransaction, long paramLong, boolean paramBoolean)
    throws Bn2Exception;

  public abstract ExtendedCategoryInfo buildExtendedCategoryInfo(DBTransaction paramDBTransaction, ResultSet paramResultSet)
    throws SQLException, Bn2Exception;

    //public boolean demoteChildInRelationshipSequence(DBTransaction a_dbTransaction, long lParentId, long lChildId);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.IAssetManager
 * JD-Core Version:    0.6.0
 */