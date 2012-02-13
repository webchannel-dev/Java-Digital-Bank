/*      */ package com.bright.framework.customfield.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.customfield.bean.CustomField;
/*      */ import com.bright.framework.customfield.bean.CustomField.Translation;
/*      */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*      */ import com.bright.framework.customfield.bean.CustomFieldType;
/*      */ import com.bright.framework.customfield.bean.CustomFieldUsageType;
/*      */ import com.bright.framework.customfield.bean.CustomFieldValue;
/*      */ import com.bright.framework.customfield.bean.CustomFieldValueMapping;
/*      */ import com.bright.framework.customfield.constant.CustomFieldConstants;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.exception.SQLStatementException;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.service.LanguageManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class CustomFieldManager extends Bn2Manager
/*      */   implements CustomFieldConstants
/*      */ {
/*      */   private static final String c_ksClassName = "CustomFieldManager";
/*   63 */   protected DBTransactionManager m_transactionManager = null;
/*      */ 
/*   65 */   protected LanguageManager m_languageManager = null;
/*      */ 
/*   92 */   protected String m_sCustomFieldSQLFields = "c.Id, c.FieldName, c.Required, c.IsSubtype, t.Id tId, t.Description tDescription, u.Id utId, u.Description uDescription, v.Id vId, v.Value vValue, v.CustomFieldId vCustomFieldId" + getCustomFieldSQLExtraFields();
/*      */ 
/*   95 */   protected String m_sCustomFieldSQL = "SELECT " + this.m_sCustomFieldSQLFields + " FROM CustomField c " + "JOIN CustomFieldType t ON c.TypeId=t.Id " + "JOIN CustomFieldUsageType u ON c.UsageTypeId=u.Id " + "LEFT JOIN CustomFieldValue v ON c.Id=v.CustomFieldId WHERE 1=1";
/*      */ 
/*  100 */   protected Object m_objFieldLock = new Object();
/*  101 */   protected HashMap m_hmFieldCache = new HashMap();
/*      */ 
/*      */   public void setLanguageManager(LanguageManager a_languageManager)
/*      */   {
/*   69 */     this.m_languageManager = a_languageManager;
/*      */   }
/*      */ 
/*      */   protected String getCustomFieldSQLExtraFields()
/*      */   {
/*   74 */     return "";
/*      */   }
/*      */ 
/*      */   protected String getExtraUpdateFields()
/*      */   {
/*   79 */     return "";
/*      */   }
/*      */ 
/*      */   protected String getExtraAddFields()
/*      */   {
/*   84 */     return "";
/*      */   }
/*      */ 
/*      */   protected String getExtraAddFieldMarkers()
/*      */   {
/*   89 */     return "";
/*      */   }
/*      */ 
/*      */   public Vector getCustomFieldTypes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  118 */     String ksMethodName = "getCustomFieldTypes";
/*  119 */     Vector vecTypes = null;
/*  120 */     Connection con = null;
/*  121 */     ResultSet rs = null;
/*  122 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  126 */       if (transaction == null)
/*      */       {
/*  128 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  131 */       con = transaction.getConnection();
/*      */ 
/*  133 */       String sSQL = "SELECT t.Id tId, t.Description tDescription FROM CustomFieldType t";
/*      */ 
/*  136 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  137 */       rs = psql.executeQuery();
/*      */ 
/*  139 */       while (rs.next())
/*      */       {
/*  141 */         CustomFieldType type = buildCustomFieldType(rs);
/*      */ 
/*  143 */         if (vecTypes == null)
/*      */         {
/*  145 */           vecTypes = new Vector();
/*      */         }
/*      */ 
/*  148 */         vecTypes.add(type);
/*      */       }
/*      */ 
/*  151 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  155 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  159 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  163 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  167 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting field types from the database : " + e);
/*  168 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting field types from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  173 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  177 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  181 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  186 */     return vecTypes;
/*      */   }
/*      */ 
/*      */   public CustomField getCustomField(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  193 */     return getCustomField(a_dbTransaction, a_lId, null);
/*      */   }
/*      */ 
/*      */   public CustomField getCustomField(DBTransaction a_dbTransaction, long a_lId, String a_sName)
/*      */     throws Bn2Exception
/*      */   {
/*  214 */     String ksMethodName = "getCustomField";
/*  215 */     CustomField field = null;
/*      */ 
/*  217 */     if (this.m_hmFieldCache.containsKey(new Long(a_lId)))
/*      */     {
/*  219 */       field = (CustomField)this.m_hmFieldCache.get(new Long(a_lId));
/*      */     }
/*      */     else
/*      */     {
/*  224 */       Connection con = null;
/*  225 */       ResultSet rs = null;
/*  226 */       DBTransaction transaction = a_dbTransaction;
/*      */       try
/*      */       {
/*  230 */         if (transaction == null)
/*      */         {
/*  232 */           transaction = this.m_transactionManager.getNewTransaction();
/*      */         }
/*      */ 
/*  235 */         con = transaction.getConnection();
/*  236 */         String sSQL = this.m_sCustomFieldSQL;
/*  237 */         if (a_lId > 0L)
/*      */         {
/*  239 */           sSQL = sSQL + " AND c.Id=?";
/*      */         }
/*      */         else
/*      */         {
/*  243 */           sSQL = sSQL + " AND c.FieldName=?";
/*      */         }
/*      */ 
/*  246 */         PreparedStatement psql = con.prepareStatement(sSQL);
/*  247 */         if (a_lId > 0L)
/*      */         {
/*  249 */           psql.setLong(1, a_lId);
/*      */         }
/*      */         else
/*      */         {
/*  253 */           psql.setString(1, a_sName);
/*      */         }
/*  255 */         rs = psql.executeQuery();
/*  256 */         boolean bFirst = true;
/*  257 */         Vector vecValues = null;
/*      */ 
/*  259 */         while (rs.next())
/*      */         {
/*  261 */           if (bFirst)
/*      */           {
/*  263 */             field = buildCustomField(rs);
/*  264 */             bFirst = false;
/*      */           }
/*      */ 
/*  268 */           if (rs.getLong("vId") <= 0L)
/*      */             continue;
/*  270 */           CustomFieldValue value = buildCustomFieldValue(rs);
/*      */ 
/*  272 */           if (vecValues == null)
/*      */           {
/*  274 */             vecValues = new Vector();
/*      */           }
/*  276 */           vecValues.add(value);
/*      */         }
/*      */ 
/*  280 */         if (field != null)
/*      */         {
/*  282 */           field.setAvailableValues(vecValues);
/*      */         }
/*  284 */         psql.close();
/*      */ 
/*  286 */         if (field != null)
/*      */         {
/*  289 */           if (FrameworkSettings.getSupportMultiLanguage())
/*      */           {
/*  291 */             setCustomFieldTranslations(a_dbTransaction, field);
/*      */           }
/*      */ 
/*  295 */           synchronized (this.m_objFieldLock)
/*      */           {
/*  297 */             this.m_hmFieldCache.put(new Long(field.getId()), field);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/*  303 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/*  307 */             transaction.rollback();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/*  311 */             this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */ 
/*  315 */         this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting field from the database : " + e);
/*  316 */         throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting field from the database : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/*  321 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/*  325 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/*  329 */             this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  335 */     return field;
/*      */   }
/*      */ 
/*      */   public Vector<CustomField> getCustomFields(DBTransaction a_dbTransaction, long a_lTypeId, long a_lUsageTypeId, User a_user)
/*      */     throws Bn2Exception
/*      */   {
/*  359 */     String ksMethodName = "getCustomFields";
/*  360 */     Vector vecFields = null;
/*  361 */     Connection con = null;
/*  362 */     ResultSet rs = null;
/*  363 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  367 */       if (transaction == null)
/*      */       {
/*  369 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  372 */       con = transaction.getConnection();
/*  373 */       String sSQL = this.m_sCustomFieldSQL;
/*      */ 
/*  375 */       if (a_lTypeId > 0L)
/*      */       {
/*  377 */         sSQL = sSQL + " AND t.Id=?";
/*      */       }
/*      */ 
/*  380 */       if (a_lUsageTypeId > 0L)
/*      */       {
/*  382 */         sSQL = sSQL + " AND u.Id=?";
/*      */       }
/*      */ 
/*  385 */       sSQL = sSQL + " " + getExtraRestrictions(a_user) + " ORDER BY " + getOrderBy();
/*      */ 
/*  388 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  389 */       int iCol = 1;
/*      */ 
/*  391 */       if (a_lTypeId > 0L)
/*      */       {
/*  393 */         psql.setLong(iCol++, a_lTypeId);
/*      */       }
/*      */ 
/*  396 */       if (a_lUsageTypeId > 0L)
/*      */       {
/*  398 */         psql.setLong(iCol++, a_lUsageTypeId);
/*      */       }
/*      */ 
/*  401 */       rs = psql.executeQuery();
/*  402 */       long lLastFieldId = -1L;
/*  403 */       CustomField field = null;
/*  404 */       Vector vecValues = null;
/*      */ 
/*  406 */       while (rs.next())
/*      */       {
/*  408 */         if (rs.getLong("Id") != lLastFieldId)
/*      */         {
/*  410 */           field = buildCustomField(rs);
/*  411 */           vecValues = new Vector();
/*  412 */           field.setAvailableValues(vecValues);
/*  413 */           if (vecFields == null)
/*      */           {
/*  415 */             vecFields = new Vector();
/*      */           }
/*  417 */           vecFields.add(field);
/*  418 */           lLastFieldId = field.getId();
/*      */         }
/*      */ 
/*  422 */         if (rs.getLong("vId") <= 0L)
/*      */           continue;
/*  424 */         CustomFieldValue value = buildCustomFieldValue(rs);
/*  425 */         vecValues.add(value);
/*      */       }
/*      */ 
/*  429 */       psql.close();
/*      */ 
/*  432 */       if (FrameworkSettings.getSupportMultiLanguage())
/*      */       {
/*  434 */         if (vecFields != null)
/*      */         {
/*  436 */           for (int i = 0; i < vecFields.size(); i++)
/*      */           {
/*  438 */             setCustomFieldTranslations(transaction, (CustomField)vecFields.elementAt(i));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  445 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  449 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  453 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  457 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting fields from the database : " + e);
/*  458 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting fields from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  463 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  467 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  471 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  476 */     return vecFields;
/*      */   }
/*      */ 
/*      */   protected String getExtraRestrictions(User a_user)
/*      */     throws Bn2Exception
/*      */   {
/*  482 */     return "";
/*      */   }
/*      */ 
/*      */   protected String getOrderBy()
/*      */   {
/*  488 */     return "u.Description, c.SequenceNumber, c.FieldName";
/*      */   }
/*      */ 
/*      */   public void deleteCustomField(DBTransaction a_dbTransaction, long a_lFieldId)
/*      */     throws Bn2Exception
/*      */   {
/*  509 */     String ksMethodName = "deleteCustomField";
/*  510 */     Connection con = null;
/*  511 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  515 */       if (transaction == null)
/*      */       {
/*  517 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  520 */       con = transaction.getConnection();
/*      */ 
/*  522 */       String[] aSQL = { "DELETE FROM CustomFieldValueMapping WHERE CustomFieldId=?", "DELETE FROM CustomFieldValue WHERE CustomFieldId=?", "DELETE FROM TranslatedCustomField WHERE CustomFieldId=?", "DELETE FROM CustomField WHERE Id=?" };
/*      */ 
/*  528 */       PreparedStatement psql = null;
/*      */ 
/*  530 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/*  532 */         psql = con.prepareStatement(aSQL[i]);
/*  533 */         int iCol = 1;
/*  534 */         psql.setLong(iCol++, a_lFieldId);
/*  535 */         psql.executeUpdate();
/*  536 */         psql.close();
/*      */       }
/*      */ 
/*  539 */       invalidateCachedField(a_lFieldId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  543 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  547 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  551 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  555 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst deleting field from the database : " + e);
/*  556 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst deleteing field from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  561 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  565 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  569 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteCustomFieldValue(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  591 */     String ksMethodName = "deleteCustomFieldValue";
/*  592 */     Connection con = null;
/*  593 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  597 */       if (transaction == null)
/*      */       {
/*  599 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  602 */       con = transaction.getConnection();
/*      */ 
/*  604 */       String[] aSQL = { "DELETE FROM CustomFieldValueMapping WHERE ListValue=?", "DELETE FROM CustomFieldValue WHERE Id=?" };
/*      */ 
/*  608 */       PreparedStatement psql = null;
/*      */ 
/*  610 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/*  612 */         psql = con.prepareStatement(aSQL[i]);
/*  613 */         int iCol = 1;
/*  614 */         psql.setLong(iCol++, a_lId);
/*  615 */         psql.executeUpdate();
/*  616 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  621 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  625 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  629 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  633 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst deleting value from the database : " + e);
/*  634 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst deleteing value from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  639 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  643 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  647 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveCustomField(DBTransaction a_dbTransaction, CustomField a_field)
/*      */     throws Bn2Exception
/*      */   {
/*  670 */     String ksMethodName = "saveCustomField";
/*  671 */     Connection con = null;
/*  672 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  676 */       if (transaction == null)
/*      */       {
/*  678 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  681 */       con = transaction.getConnection();
/*  682 */       String sSQL = null;
/*  683 */       boolean bInvalidateCache = false;
/*  684 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/*  686 */       if (a_field.getId() > 0L)
/*      */       {
/*  689 */         sSQL = "UPDATE CustomField SET FieldName=?, UsageTypeId=?, TypeId=?, Required=?, IsSubtype=? " + getExtraUpdateFields() + " WHERE Id=?";
/*  690 */         bInvalidateCache = true;
/*      */       }
/*      */       else
/*      */       {
/*  695 */         sSQL = "INSERT INTO CustomField (FieldName, UsageTypeId, TypeId, Required, IsSubtype" + getExtraAddFields();
/*  696 */         String sParams = " VALUES (?,?,?,?,?" + getExtraAddFieldMarkers();
/*      */ 
/*  698 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/*  700 */           sSQL = sSQL + ", Id";
/*  701 */           sParams = sParams + ",?";
/*      */         }
/*      */ 
/*  704 */         sSQL = sSQL + ")" + sParams + ")";
/*      */       }
/*      */ 
/*  708 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  709 */       int iCol = 1;
/*  710 */       psql.setString(iCol++, a_field.getName());
/*  711 */       psql.setLong(iCol++, a_field.getUsageType().getId());
/*  712 */       psql.setLong(iCol++, a_field.getType().getId());
/*  713 */       psql.setBoolean(iCol++, a_field.getIsRequired());
/*  714 */       psql.setBoolean(iCol++, a_field.getIsSubtype());
/*  715 */       doExtraFields(psql, iCol, a_field);
/*  716 */       iCol++;
/*      */ 
/*  718 */       if ((a_field.getId() > 0L) || (!sqlGenerator.usesAutoincrementFields()))
/*      */       {
/*  720 */         if (a_field.getId() <= 0L)
/*      */         {
/*  722 */           a_field.setId(sqlGenerator.getUniqueId(con, "CustomFieldSequence"));
/*      */         }
/*  724 */         psql.setLong(iCol++, a_field.getId());
/*      */       }
/*      */ 
/*  727 */       psql.executeUpdate();
/*  728 */       psql.close();
/*      */ 
/*  730 */       if (a_field.getId() <= 0L)
/*      */       {
/*  733 */         a_field.setId(sqlGenerator.getUniqueId(con, "CustomField"));
/*      */       }
/*      */ 
/*  737 */       Iterator itTranslations = a_field.getTranslations().iterator();
/*  738 */       while (itTranslations.hasNext())
/*      */       {
/*  740 */         CustomField.Translation translation = (CustomField.Translation)itTranslations.next();
/*  741 */         if (translation.getLanguage().getId() > 0L)
/*      */         {
/*  743 */           saveCustomFieldTranslation(a_dbTransaction, translation);
/*      */         }
/*      */       }
/*      */ 
/*  747 */       if (bInvalidateCache)
/*      */       {
/*  749 */         invalidateCachedField(a_field.getId());
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  754 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  758 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  762 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  766 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst saving field from the database : " + e);
/*  767 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst saving field from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  772 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  776 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  780 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveCustomFieldValue(DBTransaction a_dbTransaction, CustomFieldValue a_value)
/*      */     throws Bn2Exception
/*      */   {
/*  802 */     String ksMethodName = "saveCustomFieldValue";
/*  803 */     Connection con = null;
/*  804 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  808 */       if (transaction == null)
/*      */       {
/*  810 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  813 */       con = transaction.getConnection();
/*  814 */       String sSQL = null;
/*  815 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/*  817 */       if (a_value.getId() > 0L)
/*      */       {
/*  820 */         sSQL = "UPDATE CustomFieldValue SET Value=?, CustomFieldId=? WHERE Id=?";
/*      */       }
/*      */       else
/*      */       {
/*  825 */         sSQL = "INSERT INTO CustomFieldValue (Value, CustomFieldId";
/*  826 */         String sParams = " VALUES (?,?";
/*      */ 
/*  828 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/*  830 */           sSQL = sSQL + ", Id";
/*  831 */           sParams = sParams + ",?";
/*      */         }
/*  833 */         sSQL = sSQL + ")" + sParams + ")";
/*      */       }
/*      */ 
/*  837 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  838 */       int iCol = 1;
/*  839 */       psql.setString(iCol++, a_value.getValue());
/*  840 */       psql.setLong(iCol++, a_value.getCustomFieldId());
/*      */ 
/*  842 */       if ((a_value.getId() > 0L) || (!sqlGenerator.usesAutoincrementFields()))
/*      */       {
/*  844 */         if (a_value.getId() <= 0L)
/*      */         {
/*  846 */           a_value.setId(sqlGenerator.getUniqueId(con, "CustomFieldValueSequence"));
/*      */         }
/*  848 */         psql.setLong(iCol++, a_value.getId());
/*      */       }
/*      */ 
/*  851 */       psql.executeUpdate();
/*  852 */       psql.close();
/*      */ 
/*  854 */       if (a_value.getId() <= 0L)
/*      */       {
/*  857 */         a_value.setId(sqlGenerator.getUniqueId(con, "CustomFieldValue"));
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  862 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  866 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  870 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  874 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst saving field value from the database : " + e);
/*  875 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst saving field value from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  880 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  884 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  888 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getCustomFieldUsageTypes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  910 */     String ksMethodName = "getCustomFieldUsageTypes";
/*  911 */     Vector vecTypes = null;
/*  912 */     Connection con = null;
/*  913 */     ResultSet rs = null;
/*  914 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  918 */       if (transaction == null)
/*      */       {
/*  920 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  923 */       con = transaction.getConnection();
/*      */ 
/*  925 */       String sSQL = "SELECT u.Id utId, u.Description uDescription FROM CustomFieldUsageType u";
/*      */ 
/*  928 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  929 */       rs = psql.executeQuery();
/*      */ 
/*  931 */       while (rs.next())
/*      */       {
/*  933 */         CustomFieldUsageType type = buildCustomFieldUsageType(rs);
/*      */ 
/*  935 */         if (vecTypes == null)
/*      */         {
/*  937 */           vecTypes = new Vector();
/*      */         }
/*      */ 
/*  940 */         vecTypes.add(type);
/*      */       }
/*      */ 
/*  943 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  947 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  951 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  955 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  959 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting field usage types from the database : " + e);
/*  960 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting usage types from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  965 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  969 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  973 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  978 */     return vecTypes;
/*      */   }
/*      */ 
/*      */   public Vector getAvailableCustomFieldValues(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  997 */     String ksMethodName = "getAvailableCustomFieldValues";
/*  998 */     Vector vecValues = null;
/*  999 */     Connection con = null;
/* 1000 */     ResultSet rs = null;
/* 1001 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 1005 */       if (transaction == null)
/*      */       {
/* 1007 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1010 */       con = transaction.getConnection();
/* 1011 */       String sSQL = "SELECT v.Id vId, v.Value vValue, v.CustomFieldId vCustomFieldId FROM CustomFieldValue v WHERE v.CustomFieldId=? ORDER BY v.Value";
/*      */ 
/* 1015 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1016 */       psql.setLong(1, a_lId);
/* 1017 */       rs = psql.executeQuery();
/*      */ 
/* 1019 */       while (rs.next())
/*      */       {
/* 1021 */         CustomFieldValue value = buildCustomFieldValue(rs);
/* 1022 */         if (vecValues == null)
/*      */         {
/* 1024 */           vecValues = new Vector();
/*      */         }
/* 1026 */         vecValues.add(value);
/*      */       }
/*      */ 
/* 1029 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1033 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1037 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1041 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1045 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting available field values from the database : " + e);
/* 1046 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting available field values from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1051 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1055 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1059 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1064 */     return vecValues;
/*      */   }
/*      */ 
/*      */   public CustomFieldSelectedValueSet getValueMappings(DBTransaction a_dbTransaction, long a_lItemId, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1085 */     String ksMethodName = "getValueMappings";
/* 1086 */     CustomFieldSelectedValueSet set = new CustomFieldSelectedValueSet();
/* 1087 */     Vector vecMappings = null;
/* 1088 */     Connection con = null;
/* 1089 */     ResultSet rs = null;
/* 1090 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 1094 */       if (transaction == null)
/*      */       {
/* 1096 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1099 */       con = transaction.getConnection();
/* 1100 */       String sSQL = "SELECT " + this.m_sCustomFieldSQLFields + ", m.ItemId mItemId, m.TextValue mTextValue " + "FROM CustomFieldValueMapping m " + "LEFT JOIN CustomField c ON m.CustomFieldId=c.Id " + "LEFT JOIN CustomFieldType t ON c.TypeId=t.Id " + "LEFT JOIN CustomFieldUsageType u ON c.UsageTypeId=u.Id " + "LEFT JOIN CustomFieldValue v ON m.ListValue=v.Id " + "WHERE m.ItemId=? AND u.Id=? ORDER BY c.Id";
/*      */ 
/* 1109 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1110 */       psql.setLong(1, a_lItemId);
/* 1111 */       psql.setLong(2, a_lTypeId);
/* 1112 */       rs = psql.executeQuery();
/* 1113 */       long lLastFieldId = -1L;
/* 1114 */       CustomFieldValueMapping mapping = null;
/* 1115 */       Vector vecListValues = new Vector();
/* 1116 */       vecMappings = new Vector();
/*      */ 
/* 1118 */       while (rs.next())
/*      */       {
/* 1120 */         if (lLastFieldId != rs.getLong("Id"))
/*      */         {
/* 1122 */           if (mapping != null)
/*      */           {
/* 1124 */             mapping.setListValues(vecListValues);
/* 1125 */             vecMappings.add(mapping);
/* 1126 */             vecListValues = new Vector();
/*      */           }
/* 1128 */           mapping = buildCustomFieldValueMapping(rs);
/* 1129 */           lLastFieldId = rs.getLong("Id");
/*      */         }
/*      */ 
/* 1132 */         CustomFieldValue value = buildCustomFieldValue(rs);
/* 1133 */         if (value != null)
/*      */         {
/* 1135 */           vecListValues.add(value);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1140 */       if (mapping != null)
/*      */       {
/* 1142 */         mapping.setListValues(vecListValues);
/* 1143 */         vecMappings.add(mapping);
/*      */       }
/*      */ 
/* 1146 */       psql.close();
/*      */ 
/* 1149 */       if (FrameworkSettings.getSupportMultiLanguage())
/*      */       {
/* 1151 */         if (vecMappings != null)
/*      */         {
/* 1153 */           for (int i = 0; i < vecMappings.size(); i++)
/*      */           {
/* 1155 */             CustomField temp = ((CustomFieldValueMapping)vecMappings.elementAt(i)).getCustomField();
/* 1156 */             setCustomFieldTranslations(transaction, temp);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1163 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1167 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1171 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1175 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting mappings from the database : " + e);
/* 1176 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst getting mappings from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1181 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1185 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1189 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1194 */     set.setUsageTypeId(a_lTypeId);
/* 1195 */     set.setSelectedValues(vecMappings);
/* 1196 */     return set;
/*      */   }
/*      */ 
/*      */   public void saveValueMappings(DBTransaction a_dbTransaction, CustomFieldSelectedValueSet a_set)
/*      */     throws Bn2Exception
/*      */   {
/* 1214 */     String ksMethodName = "saveValueMappings";
/* 1215 */     Connection con = null;
/* 1216 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 1220 */       if (transaction == null)
/*      */       {
/* 1222 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1225 */       con = transaction.getConnection();
/*      */ 
/* 1228 */       String sSQL = "";
/*      */ 
/* 1232 */       String sIds = "";
/* 1233 */       for (int i = 0; i < a_set.getSelectedValues().size(); i++)
/*      */       {
/* 1235 */         CustomFieldValueMapping mapping = (CustomFieldValueMapping)a_set.getSelectedValues().elementAt(i);
/* 1236 */         sIds = sIds + mapping.getCustomField().getId() + ",";
/*      */       }
/*      */ 
/* 1239 */       if (StringUtil.stringIsPopulated(sIds))
/*      */       {
/* 1241 */         sIds = sIds.substring(0, sIds.length() - 1);
/*      */ 
/* 1244 */         sSQL = "DELETE FROM CustomFieldValueMapping WHERE ItemId=? AND CustomFieldId IN (" + sIds + ")";
/* 1245 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1246 */         psql.setLong(1, a_set.getItemId());
/* 1247 */         psql.executeUpdate();
/* 1248 */         psql.close();
/*      */       }
/*      */ 
/* 1252 */       if ((a_set != null) && (a_set.getSelectedValues() != null))
/*      */       {
/* 1254 */         sSQL = "INSERT INTO CustomFieldValueMapping (ItemId, ListValue, CustomFieldId, TextValue) VALUES (?,?,?,?)";
/*      */ 
/* 1256 */         for (int i = 0; i < a_set.getSelectedValues().size(); i++)
/*      */         {
/* 1258 */           CustomFieldValueMapping mapping = (CustomFieldValueMapping)a_set.getSelectedValues().elementAt(i);
/*      */ 
/* 1261 */           if ((mapping.getListValues() != null) && (mapping.getListValues().size() > 0))
/*      */           {
/* 1263 */             for (int x = 0; x < mapping.getListValues().size(); x++)
/*      */             {
/* 1265 */               CustomFieldValue value = (CustomFieldValue)mapping.getListValues().elementAt(x);
/* 1266 */               PreparedStatement psql = con.prepareStatement(sSQL);
/* 1267 */               psql.setLong(1, mapping.getItemId());
/* 1268 */               psql.setLong(2, value.getId());
/* 1269 */               psql.setLong(3, mapping.getCustomField().getId());
/* 1270 */               psql.setString(4, null);
/* 1271 */               psql.executeUpdate();
/* 1272 */               psql.close();
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1278 */             PreparedStatement psql = con.prepareStatement(sSQL);
/* 1279 */             psql.setLong(1, mapping.getItemId());
/* 1280 */             psql.setNull(2, -5);
/* 1281 */             psql.setLong(3, mapping.getCustomField().getId());
/* 1282 */             psql.setString(4, mapping.getTextValue());
/* 1283 */             psql.executeUpdate();
/* 1284 */             psql.close();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1291 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1295 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1299 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1303 */       this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst saving mappings to the database : " + e);
/* 1304 */       throw new Bn2Exception("CustomFieldManager." + ksMethodName + ": SQL Exception whilst saving mappings to the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1309 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1313 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1317 */           this.m_logger.error("CustomFieldManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void invalidateCachedField(long a_lFieldId)
/*      */   {
/* 1325 */     this.m_hmFieldCache.remove(new Long(a_lFieldId));
/*      */   }
/*      */ 
/*      */   protected CustomFieldType buildCustomFieldType(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1332 */     CustomFieldType type = new CustomFieldType();
/* 1333 */     type.setId(a_rs.getLong("tId"));
/* 1334 */     type.setDescription(a_rs.getString("tDescription"));
/* 1335 */     return type;
/*      */   }
/*      */ 
/*      */   protected CustomFieldUsageType buildCustomFieldUsageType(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1341 */     CustomFieldUsageType usageType = new CustomFieldUsageType();
/* 1342 */     usageType.setId(a_rs.getLong("utId"));
/* 1343 */     usageType.setDescription(a_rs.getString("uDescription"));
/* 1344 */     return usageType;
/*      */   }
/*      */ 
/*      */   protected CustomField buildCustomField(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1350 */     CustomField field = new CustomField();
/* 1351 */     populateCustomField(a_rs, field);
/* 1352 */     return field;
/*      */   }
/*      */ 
/*      */   protected void populateCustomField(ResultSet a_rs, CustomField a_field)
/*      */     throws SQLException
/*      */   {
/* 1358 */     a_field.setId(a_rs.getLong("Id"));
/* 1359 */     a_field.setName(a_rs.getString("FieldName"));
/* 1360 */     a_field.setType(buildCustomFieldType(a_rs));
/* 1361 */     a_field.setUsageType(buildCustomFieldUsageType(a_rs));
/* 1362 */     a_field.setIsRequired(a_rs.getBoolean("Required"));
/* 1363 */     a_field.setIsSubtype(a_rs.getBoolean("IsSubtype"));
/*      */   }
/*      */ 
/*      */   protected CustomFieldValue buildCustomFieldValue(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1369 */     long lId = a_rs.getLong("vId");
/*      */ 
/* 1371 */     if (lId > 0L)
/*      */     {
/* 1373 */       CustomFieldValue value = new CustomFieldValue();
/* 1374 */       value.setId(a_rs.getLong("vId"));
/* 1375 */       value.setValue(a_rs.getString("vValue"));
/* 1376 */       value.setCustomFieldId(a_rs.getLong("vCustomFieldId"));
/* 1377 */       return value;
/*      */     }
/* 1379 */     return null;
/*      */   }
/*      */ 
/*      */   protected CustomFieldValueMapping buildCustomFieldValueMapping(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1385 */     CustomFieldValueMapping mapping = new CustomFieldValueMapping();
/* 1386 */     mapping.setItemId(a_rs.getLong("mItemId"));
/* 1387 */     mapping.setTextValue(a_rs.getString("mTextValue"));
/* 1388 */     mapping.setCustomField(buildCustomField(a_rs));
/*      */ 
/* 1390 */     return mapping;
/*      */   }
/*      */ 
/*      */   protected void doExtraFields(PreparedStatement a_psql, int a_iIndex, CustomField a_field)
/*      */     throws SQLException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void setCustomFieldTranslations(DBTransaction a_dbTransaction, CustomField a_field)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1403 */     Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1406 */     String sSQL = "SELECT * FROM TranslatedCustomField tcf LEFT JOIN Language l ON tcf.LanguageId=l.Id WHERE CustomFieldId=?";
/*      */ 
/* 1408 */     PreparedStatement psql = con.prepareStatement(sSQL);
/* 1409 */     psql.setLong(1, a_field.getId());
/* 1410 */     ResultSet rs = psql.executeQuery();
/*      */ 
/* 1412 */     while (rs.next())
/*      */     {
/* 1414 */       if (rs.getLong("LanguageId") <= 0L)
/*      */         continue;
/* 1416 */       addCustomFieldTranslation(rs, a_field);
/*      */     }
/*      */ 
/* 1420 */     psql.close();
/*      */ 
/* 1422 */     this.m_languageManager.ensureTranslations(a_dbTransaction, a_field);
/*      */   }
/*      */ 
/*      */   protected void addCustomFieldTranslation(ResultSet a_rs, CustomField a_field)
/*      */     throws SQLException
/*      */   {
/*      */     //CustomField tmp5_4 = a_field; tmp5_4.getClass(); CustomField.Translation translation = new CustomField.Translation(tmp5_4, new Language(a_rs.getLong("LanguageId"), a_rs.getString("Name"), a_rs.getString("Code")));
/* 1428 */     CustomField.Translation translation = a_field.new Translation( new Language(a_rs.getLong("LanguageId"), a_rs.getString("Name"), a_rs.getString("Code")));
               translation.setName(a_rs.getString("FieldName"));
/* 1429 */     a_field.getTranslations().add(translation);
/*      */   }
/*      */ 
/*      */   public void saveCustomFieldTranslation(DBTransaction a_dbTransaction, CustomField.Translation a_translation)
/*      */     throws Bn2Exception
/*      */   {
/* 1441 */     String ksMethodName = "saveCustomFieldTranslation";
/*      */ 
/* 1443 */     Connection con = null;
/* 1444 */     PreparedStatement psql = null;
/* 1445 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1449 */       con = a_dbTransaction.getConnection();
/* 1450 */       int iCol = 1;
/*      */ 
/* 1452 */       sSQL = "DELETE FROM TranslatedCustomField WHERE CustomFieldId=? AND LanguageId=?";
/* 1453 */       psql = con.prepareStatement(sSQL);
/* 1454 */       psql.setLong(iCol++, a_translation.getCustomFieldId());
/* 1455 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 1456 */       psql.executeUpdate();
/* 1457 */       psql.close();
/*      */ 
/* 1459 */       iCol = 1;
/* 1460 */       sSQL = "INSERT INTO TranslatedCustomField (FieldName,CustomFieldId,LanguageId) VALUES (?,?,?)";
/* 1461 */       psql = con.prepareStatement(sSQL);
/* 1462 */       psql.setString(iCol++, a_translation.getName());
/* 1463 */       psql.setLong(iCol++, a_translation.getCustomFieldId());
/* 1464 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 1465 */       psql.executeUpdate();
/* 1466 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1470 */       this.m_logger.error("CustomFieldManager.saveCustomFieldTranslation - " + sqe);
/* 1471 */       throw new Bn2Exception("CustomFieldManager.saveCustomFieldTranslation", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean subtypeFieldsExist(DBTransaction a_dbTransaction, long a_lUsageTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1483 */     Connection con = null;
/* 1484 */     DBTransaction transaction = a_dbTransaction;
/* 1485 */     String sSQL = null;
               boolean bool1 = false;
/* 1486 */     boolean bResult = false;
/*      */ 
/* 1488 */     if (transaction == null)
/*      */     {
/* 1490 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1495 */       con = transaction.getConnection();
/*      */ 
/* 1497 */       sSQL = "SELECT COUNT(*) NumSubtypes FROM CustomField WHERE UsageTypeId=? AND IsSubtype=?";
/*      */ 
/* 1499 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1500 */       psql.setLong(1, a_lUsageTypeId);
/* 1501 */       psql.setBoolean(2, true);
/*      */ 
/* 1503 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1505 */       if (rs.next())
/*      */       {
/* 1507 */         bResult = rs.getInt("NumSubtypes") > 0;
/*      */       }
/*      */ 
/* 1510 */       psql.close();
/*      */ 
/* 1512 */        bool1= bResult;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       /* 1516 */                    throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1521 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1525 */           transaction.commit();
                     // return bool1;
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1529 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
                    // throw new Bn2Exception(sqle.getMessage());
/*      */         }
/*      */       }
                 return bResult;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 1538 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.service.CustomFieldManager
 * JD-Core Version:    0.6.0
 */