/*      */ package com.bright.framework.simplelist.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.bean.LanguageAwareComponent;
/*      */ import com.bright.framework.language.service.LanguageManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.constant.ListConstants;
/*      */ import com.bright.framework.simplelist.constant.ListSettings;
/*      */ import com.bright.framework.simplelist.util.ItemBodyReplaceParser;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class ListManager extends Bn2Manager
/*      */   implements LanguageAwareComponent, ListConstants
/*      */ {
/*      */   private static final String c_ksClassName = "ListManager";
/*      */   public static final int k_iListOrder_DateAdded = 1;
/*      */   public static final int k_iListOrder_Title = 2;
/*   73 */   private DBTransactionManager m_transactionManager = null;
/*   74 */   private LanguageManager m_languageManager = null;
/*   75 */   private HashMap m_hmItemCache = new HashMap();
/*   76 */   private Hashtable m_hmListCache = new Hashtable();
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*   87 */     setupExtraContent();
/*      */ 
/*   89 */     super.startup();
/*      */   }
/*      */ 
/*      */   public void setupExtraContent()
/*      */     throws Bn2Exception
/*      */   {
/*  105 */     String ksMethodName = "setupExtraContent";
/*  106 */     DBTransaction dbTransaction = null;
/*  107 */     int iExtraPages = ListSettings.getNoOfExtraContentPages();
/*      */ 
/*  109 */     if (iExtraPages > 0)
/*      */     {
/*      */       try
/*      */       {
/*  113 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*  114 */         Connection con = dbTransaction.getConnection();
/*      */ 
/*  117 */         String sSql = "SELECT Id FROM List WHERE Identifier=?";
/*  118 */         PreparedStatement psql = con.prepareStatement(sSql);
/*  119 */         psql.setString(1, "copy");
/*  120 */         ResultSet rs = psql.executeQuery();
/*      */ 
/*  122 */         if (rs.next())
/*      */         {
/*  124 */           long lListId = rs.getLong("Id");
/*      */ 
/*  127 */           sSql = "SELECT 1 FROM ListItem WHERE Identifier=?";
/*  128 */           String sInsertSql = "INSERT INTO ListItem (Identifier, ListId, Title, CannotBeDeleted) VALUES (?, ?, ?, ?)";
/*  129 */           String sUpdateSql = "UPDATE ListItem SET Title=? WHERE Identifier=?";
/*      */ 
/*  131 */           psql.close();
/*  132 */           psql = con.prepareStatement(sSql);
/*  133 */           PreparedStatement insertPsql = con.prepareStatement(sInsertSql);
/*  134 */           PreparedStatement updatePsql = con.prepareStatement(sUpdateSql);
/*      */ 
/*  136 */           for (int i = 1; i <= iExtraPages; i++)
/*      */           {
/*  138 */             if (i > 100)
/*      */             {
/*      */               break;
/*      */             }
/*      */ 
/*  144 */             String sTitle = ListSettings.getExtraContentPageName(i);
/*  145 */             String sIdentifier = "content-page-" + i;
/*      */ 
/*  148 */             psql.setString(1, sIdentifier);
/*  149 */             rs = psql.executeQuery();
/*  150 */             int iCol = 1;
/*      */ 
/*  152 */             if (!rs.next())
/*      */             {
/*  155 */               insertPsql.setString(iCol++, sIdentifier);
/*  156 */               insertPsql.setLong(iCol++, lListId);
/*  157 */               insertPsql.setString(iCol++, sTitle);
/*  158 */               insertPsql.setBoolean(iCol++, true);
/*      */ 
/*  160 */               insertPsql.executeUpdate();
/*      */             }
/*      */             else
/*      */             {
/*  165 */               updatePsql.setString(iCol++, sTitle);
/*  166 */               updatePsql.setString(iCol++, sIdentifier);
/*  167 */               updatePsql.executeUpdate();
/*      */             }
/*      */           }
/*      */ 
/*  171 */           insertPsql.close();
/*  172 */           updatePsql.close();
/*      */         }
/*      */         else
/*      */         {
/*  176 */           this.m_logger.error("ListManager.setupExtraContent : Unable to list id to add extra content to");
/*      */         }
/*      */ 
/*  179 */         psql.close();
/*      */       }
/*      */       catch (Exception sqle)
/*      */       {
/*  184 */         this.m_logger.error("ListManager.setupExtraContent : Exception occurred : " + sqle.getMessage());
/*      */         try
/*      */         {
/*  188 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (SQLException se)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/*  200 */           if (dbTransaction != null)
/*      */           {
/*  202 */             dbTransaction.commit();
/*      */           }
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  207 */           this.m_logger.error("ListManager.setupExtraContent : Exception occurred : " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getListItems(DBTransaction a_dbTransaction, long a_lListId, int a_order, long a_lLanguageId)
/*      */     throws Bn2Exception
/*      */   {
/*  233 */     return getListItemsFromDB(a_dbTransaction, a_lListId, null, a_order, a_lLanguageId);
/*      */   }
/*      */ 
/*      */   public Vector getListItems(DBTransaction a_dbTransaction, String a_sIdentifier, int a_order, long a_lLanguageId)
/*      */     throws Bn2Exception
/*      */   {
/*  259 */     return getListItemsFromDB(a_dbTransaction, -1L, a_sIdentifier, a_order, a_lLanguageId);
/*      */   }
/*      */ 
/*      */   public Vector getLists(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  283 */     String ksMethodName = "getLists";
/*  284 */     Vector vecLists = new Vector();
/*      */     try
/*      */     {
/*  288 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/*  290 */       String sSql = "SELECT Id, Identifier, Name, Description, NoHTMLMarkup  FROM List ORDER BY Id";
/*      */ 
/*  293 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  294 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  296 */       while (rs.next())
/*      */       {
/*  298 */         com.bright.framework.simplelist.bean.List list = new com.bright.framework.simplelist.bean.List();
/*  299 */         list.setId(rs.getLong("Id"));
/*  300 */         list.setIdentifier(rs.getString("Identifier"));
/*  301 */         list.setName(rs.getString("Name"));
/*  302 */         list.setDescription(rs.getString("Description"));
/*  303 */         list.setNoHTMLMarkup(rs.getBoolean("NoHTMLMarkup"));
/*      */ 
/*  305 */         vecLists.add(list);
/*      */       }
/*      */ 
/*  308 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  314 */       throw new Bn2Exception("ListManager.getLists: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  317 */     return vecLists;
/*      */   }
/*      */ 
/*      */   public com.bright.framework.simplelist.bean.List getList(String a_sIdentifier, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  324 */     return getList(a_sIdentifier, a_language.getId());
/*      */   }
/*      */ 
/*      */   public com.bright.framework.simplelist.bean.List getList(String a_sIdentifier, long a_lLanguageId)
/*      */     throws Bn2Exception
/*      */   {
/*  343 */     String ksMethodName = "getList";
/*  344 */     DBTransaction dbTransaction = null;
/*  345 */     com.bright.framework.simplelist.bean.List list = null;
/*      */ 
/*  348 */     list = getCachedList(a_sIdentifier);
/*      */ 
/*  350 */     if (list == null)
/*      */     {
/*      */       try
/*      */       {
/*  354 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*  355 */         list = getList(dbTransaction, -1L, a_sIdentifier, true);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  359 */         this.m_logger.error("Exception in getList:", e);
/*      */         try
/*      */         {
/*  363 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  367 */           this.m_logger.error("Exception rolling back transaction in getList:" + getClass().getName() + e2.getMessage());
/*      */         }
/*      */ 
/*  371 */         throw new Bn2Exception("Exception in getList:" + e.getMessage(), e);
/*      */       }
/*      */       finally
/*      */       {
/*  375 */         if (dbTransaction != null)
/*      */         {
/*      */           try
/*      */           {
/*  379 */             dbTransaction.commit();
/*      */           }
/*      */           catch (SQLException e)
/*      */           {
/*  383 */             this.m_logger.error("Exception commiting transaction in getList:" + e.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  389 */     return list;
/*      */   }
/*      */ 
/*      */   public com.bright.framework.simplelist.bean.List getList(DBTransaction a_dbTransaction, long a_lId, String a_sIdentifier, boolean a_bRetrieveItems)
/*      */     throws Bn2Exception
/*      */   {
/*  408 */     return getList(a_dbTransaction, a_lId, a_sIdentifier, a_bRetrieveItems, 1);
/*      */   }
/*      */ 
/*      */   public com.bright.framework.simplelist.bean.List getList(DBTransaction a_dbTransaction, long a_lId, String a_sIdentifier, boolean m_bRetrieveItems, int a_order)
/*      */     throws Bn2Exception
/*      */   {
/*  434 */     String ksMethodName = "getList";
/*  435 */     com.bright.framework.simplelist.bean.List list = null;
/*      */     try
/*      */     {
/*  439 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/*  441 */       String sSql = "SELECT Id, Identifier, Name, Description, CannotAddNew, NoHTMLMarkup  FROM List ";
/*      */ 
/*  444 */       if (a_lId > 0L)
/*      */       {
/*  446 */         sSql = sSql + "WHERE Id=? ";
/*      */       }
/*      */       else
/*      */       {
/*  450 */         sSql = sSql + "WHERE Identifier=? ";
/*      */       }
/*      */ 
/*  453 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  455 */       if (a_lId > 0L)
/*      */       {
/*  457 */         psql.setLong(1, a_lId);
/*      */       }
/*      */       else
/*      */       {
/*  461 */         psql.setString(1, a_sIdentifier);
/*      */       }
/*      */ 
/*  464 */       ResultSet rs = psql.executeQuery();
/*      */       try
/*      */       {
/*  468 */         if (rs.next())
/*      */         {
/*  470 */           list = new com.bright.framework.simplelist.bean.List();
/*  471 */           list.setId(rs.getLong("Id"));
/*  472 */           list.setIdentifier(rs.getString("Identifier"));
/*  473 */           list.setName(rs.getString("Name"));
/*  474 */           list.setDescription(rs.getString("Description"));
/*  475 */           list.setCannotAddNew(rs.getBoolean("CannotAddNew"));
/*  476 */           list.setNoHTMLMarkup(rs.getBoolean("NoHTMLMarkup"));
/*      */         }
/*      */         else
/*      */         {
/*  480 */           throw new Bn2Exception("ListManager.getList: no rows returned for id : " + a_lId);
/*      */         }
/*      */ 
/*  483 */         if (m_bRetrieveItems)
/*      */         {
/*  485 */           list.setItems(getListItems(a_dbTransaction, list.getId(), a_order, 0L));
/*      */         }
/*      */ 
/*  489 */         cacheList(list);
/*      */       }
/*      */       finally
/*      */       {
/*  493 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  500 */       throw new Bn2Exception("ListManager.getList: Exception occurred: " + e);
/*      */     }
/*      */ 
/*  503 */     return list;
/*      */   }
/*      */ 
/*      */   public Vector getLists()
/*      */     throws Bn2Exception
/*      */   {
/*  520 */     String ksMethodName = "getLists";
/*  521 */     DBTransaction dbTransaction = null;
/*  522 */     Vector vecLists = null;
/*      */     try
/*      */     {
/*  526 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*  527 */       vecLists = getLists(dbTransaction);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  531 */       this.m_logger.error("Exception in getLists:", e);
/*      */       try
/*      */       {
/*  535 */         dbTransaction.rollback();
/*      */       }
/*      */       catch (Exception e2)
/*      */       {
/*  539 */         this.m_logger.error("Exception rolling back transaction in getLists:" + getClass().getName() + e2.getMessage());
/*      */       }
/*      */ 
/*  543 */       throw new Bn2Exception("Exception in getLists:" + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/*  547 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  551 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/*  555 */           this.m_logger.error("Exception commiting transaction in getLists:" + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  560 */     return vecLists;
/*      */   }
/*      */ 
/*      */   private Vector getListItemsFromDB(DBTransaction a_dbTransaction, long a_lId, String a_sIdentifier, int a_order, long a_lLanguageId)
/*      */     throws Bn2Exception
/*      */   {
/*  589 */     String ksMethodName = "getListItems";
/*  590 */     Vector vecItems = new Vector();
/*      */     try
/*      */     {
/*  594 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/*  596 */       String sSql = "SELECT li.ListId lilistid, li.ListItemTextTypeId litexttypeid, li.Identifier liidentifier, li.Title lititle, li.Summary lisummary, li.Body libody, li.DateAdded lidateadded, li.CannotBeDeleted licannot, lang.Id langId, lang.Name langName,lang.Code langCode  FROM ListItem li INNER JOIN List l ON l.Id = li.ListId INNER JOIN Language lang ON lang.Id = li.LanguageId WHERE li.ListId = l.id ";
/*      */ 
/*  615 */       if (a_lLanguageId > 0L)
/*      */       {
/*  617 */         sSql = sSql + " AND li.LanguageId=? ";
/*      */       }
/*      */ 
/*  620 */       if (a_lId > 0L)
/*      */       {
/*  622 */         sSql = sSql + " AND l.Id = ?";
/*      */       }
/*      */       else
/*      */       {
/*  626 */         sSql = sSql + " AND l.Identifier = ?";
/*      */       }
/*      */ 
/*  629 */       if (a_order == 2)
/*      */       {
/*  631 */         sSql = sSql + " ORDER BY li.Title ";
/*      */       }
/*      */       else
/*      */       {
/*  637 */         sSql = sSql + " ORDER BY li.DateAdded DESC ";
/*      */       }
/*      */ 
/*  641 */       sSql = sSql + ", li.LanguageId ";
/*      */ 
/*  643 */       PreparedStatement psql = con.prepareStatement(sSql, 1004, 1007);
/*  644 */       int iCol = 1;
/*      */ 
/*  646 */       if (a_lLanguageId > 0L)
/*      */       {
/*  648 */         psql.setLong(iCol++, a_lLanguageId);
/*      */       }
/*      */ 
/*  651 */       if (a_lId > 0L)
/*      */       {
/*  653 */         psql.setLong(iCol++, a_lId);
/*      */       }
/*      */       else
/*      */       {
/*  657 */         psql.setString(iCol++, a_sIdentifier);
/*      */       }
/*      */ 
/*  660 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  662 */       java.util.List languages = this.m_languageManager.getLanguages(a_dbTransaction, true);
/*      */ 
/*  664 */       while (rs.next())
/*      */       {
/*  666 */         if (rs.getLong("langId") <= 0L)
/*      */         {
/*  669 */           ListItem listItem = createListItem(rs);
/*  670 */           vecItems.add(listItem);
/*  671 */           continue;
/*      */         }
/*      */ 
/*  674 */         Iterator itLangs = languages.iterator();
/*      */ 
/*  677 */         ListItem listItemTemplate = createListItem(rs);
/*      */ 
/*  680 */         while (itLangs.hasNext())
/*      */         {
/*  682 */           Language language = (Language)itLangs.next();
/*      */ 
/*  685 */           if ((!rs.isAfterLast()) && (rs.getLong("langId") == language.getId()) && (rs.getString("liidentifier").equals(listItemTemplate.getIdentifier())))
/*      */           {
/*  689 */             ListItem listItem = createListItem(rs);
/*  690 */             listItem.setLanguage(language);
/*  691 */             vecItems.add(listItem);
/*      */ 
/*  693 */             if (!rs.isAfterLast())
/*      */             {
/*  695 */               rs.next();
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  701 */             ListItem copy = (ListItem)listItemTemplate.clone();
/*  702 */             copy.setLanguage(language);
/*  703 */             copy.setCreated(true);
/*  704 */             vecItems.add(copy);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  709 */         if ((!rs.isAfterLast()) && (!rs.getString("liidentifier").equals(listItemTemplate.getIdentifier())))
/*      */         {
/*  711 */           rs.previous();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  716 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  722 */       throw new Bn2Exception("ListManager.getListItems: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  725 */     return vecItems;
/*      */   }
/*      */ 
/*      */   private ListItem createListItem(ResultSet rs) throws SQLException, Bn2Exception
/*      */   {
/*  730 */     ListItem listItem = new ListItem();
/*  731 */     listItem.setListId(rs.getLong("lilistid"));
/*  732 */     listItem.setListItemTextTypeId(rs.getLong("litexttypeid"));
/*  733 */     listItem.setIdentifier(rs.getString("liidentifier"));
/*  734 */     listItem.setTitle(rs.getString("lititle"));
/*  735 */     listItem.setSummary(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "lisummary"));
/*  736 */     listItem.setBody(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "libody"));
/*  737 */     listItem.setDateAdded(rs.getDate("lidateadded"));
/*  738 */     listItem.setCannotBeDeleted(rs.getBoolean("licannot"));
/*  739 */     return listItem;
/*      */   }
/*      */ 
/*      */   private ListItem getListItem(DBTransaction a_dbTransaction, String a_sIdentifier, Language a_language, boolean a_bGetSource)
/*      */     throws Bn2Exception
/*      */   {
/*  762 */     String ksMethodName = "getListItem";
/*  763 */     DBTransaction dbTransaction = null;
/*  764 */     ListItem listItem = null;
/*      */ 
/*  767 */     if (!a_bGetSource)
/*      */     {
/*  769 */       listItem = getCachedListItem(a_sIdentifier, a_language);
/*      */     }
/*  771 */     if (listItem == null)
/*      */     {
/*      */       try
/*      */       {
/*  775 */         if (a_dbTransaction != null)
/*      */         {
/*  777 */           dbTransaction = a_dbTransaction;
/*      */         }
/*      */         else
/*      */         {
/*  781 */           dbTransaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */         }
/*      */ 
/*  784 */         listItem = getListItemFromDB(dbTransaction, a_sIdentifier, a_language, a_bGetSource);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  791 */         throw new Bn2Exception("Exception in getListItem:" + e.getMessage() + " when getting list item " + a_sIdentifier, e);
/*      */       }
/*      */       finally
/*      */       {
/*  795 */         if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */         {
/*      */           try
/*      */           {
/*  799 */             dbTransaction.commit();
/*      */           }
/*      */           catch (SQLException e)
/*      */           {
/*  803 */             this.m_logger.error("Exception commiting transaction in getListItem:" + e.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  809 */     return listItem;
/*      */   }
/*      */ 
/*      */   public ListItem getListItem(DBTransaction a_dbTransaction, String a_sIdentifier, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  827 */     return getListItem(a_dbTransaction, a_sIdentifier, a_language, false);
/*      */   }
/*      */ 
/*      */   public String getListItem(DBTransaction a_dbTransaction, String a_sIdentifier, Language a_language, String[] a_sFields)
/*      */     throws Bn2Exception
/*      */   {
/*  849 */     String sMessage = getListItem(a_dbTransaction, a_sIdentifier, a_language, false).getBody();
/*      */ 
/*  851 */     if (a_sFields != null)
/*      */     {
/*  853 */       String sTag = null;
/*  854 */       for (int i = 0; i < a_sFields.length; i++)
/*      */       {
/*  856 */         sTag = "\\[" + String.valueOf(i) + "\\]";
/*  857 */         sMessage = sMessage.replaceAll(sTag, a_sFields[i]);
/*      */       }
/*      */     }
/*  860 */     return sMessage;
/*      */   }
/*      */ 
/*      */   public ListItem getListItem(DBTransaction a_dbTransaction, String a_sIdentifier, long a_lLanguageId)
/*      */     throws Bn2Exception
/*      */   {
/*  878 */     return getListItem(a_dbTransaction, a_sIdentifier, new Language(a_lLanguageId), false);
/*      */   }
/*      */ 
/*      */   public ListItem getListItem(DBTransaction a_dbTransaction, String a_sIdentifier, long a_lLanguageId, boolean a_bGetSource)
/*      */     throws Bn2Exception
/*      */   {
/*  900 */     return getListItem(a_dbTransaction, a_sIdentifier, new Language(a_lLanguageId), a_bGetSource);
/*      */   }
/*      */ 
/*      */   public ListItem getListItem(String a_sIdentifier, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  922 */     return getListItem(null, a_sIdentifier, a_language);
/*      */   }
/*      */ 
/*      */   private ListItem getListItemFromDB(DBTransaction a_dbTransaction, String a_sIdentifier, Language a_language, boolean a_bGetSource)
/*      */     throws Bn2Exception
/*      */   {
/*  946 */     String ksMethodName = "getListItem";
/*  947 */     ListItem listItem = null;
/*      */     try
/*      */     {
/*  951 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/*  957 */       String sSql = "SELECT li.Identifier liidentifier, li.Title lititle, li.Summary lisummary, li.Body libody, li.DateAdded lidateadded, li.CannotBeDeleted licannot, li.ListItemTextTypeId litexttypeid, l.Id lid, l.Identifier lIdentifier, lang.Id langId, lang.Name langName, lang.Code langCode  FROM ListItem li INNER JOIN List l ON l.Id = li.ListId INNER JOIN Language lang ON lang.Id = li.LanguageId WHERE li.Identifier = ? AND (";
/*      */ 
/*  977 */       if ((a_language != null) && (a_language.getId() > 0L))
/*      */       {
/*  979 */         sSql = sSql + " lang.Id = ? OR ";
/*      */       }
/*  981 */       else if ((a_language != null) && (!StringUtils.isEmpty(a_language.getCode())))
/*      */       {
/*  983 */         sSql = sSql + " lang.Code = ? OR ";
/*      */       }
/*      */ 
/*  986 */       sSql = sSql + " lang.IsDefault = ?) ORDER BY lang.Id DESC ";
/*      */ 
/*  988 */       int iCol = 1;
/*  989 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  990 */       psql.setString(iCol++, a_sIdentifier);
/*  991 */       if ((a_language != null) && (a_language.getId() > 0L))
/*      */       {
/*  993 */         psql.setLong(iCol++, a_language.getId());
/*      */       }
/*  995 */       else if ((a_language != null) && (!StringUtils.isEmpty(a_language.getCode())))
/*      */       {
/*  997 */         psql.setString(iCol++, a_language.getCode());
/*      */       }
/*  999 */       psql.setBoolean(iCol++, true);
/*      */ 
/* 1001 */       ResultSet rs = psql.executeQuery();
/*      */       try
/*      */       {
/* 1005 */         if (rs.next())
/*      */         {
/* 1007 */           listItem = new ListItem();
/* 1008 */           listItem.setIdentifier(rs.getString("liidentifier"));
/* 1009 */           listItem.setTitle(rs.getString("lititle"));
/* 1010 */           listItem.setSummary(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "lisummary"));
/* 1011 */           listItem.setBody(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "libody"));
/* 1012 */           listItem.setDateAdded(rs.getDate("lidateadded"));
/* 1013 */           listItem.setCannotBeDeleted(rs.getBoolean("licannot"));
/* 1014 */           listItem.setListId(rs.getLong("lid"));
/* 1015 */           listItem.setListIdentifier(rs.getString("lIdentifier"));
/* 1016 */           listItem.setListItemTextTypeId(rs.getLong("litexttypeid"));
/*      */ 
/* 1018 */           long lLangId = rs.getLong("langId");
/*      */ 
/* 1020 */           if (lLangId == a_language.getId())
/*      */           {
/* 1022 */             listItem.setLanguage(new Language(rs.getLong("langId"), rs.getString("langName"), rs.getString("langCode")));
/*      */           }
/*      */           else
/*      */           {
/* 1026 */             listItem.setLanguage(this.m_languageManager.getLanguage(a_dbTransaction, a_language.getId()));
/*      */           }
/*      */ 
/* 1030 */           if (!a_bGetSource)
/*      */           {
/* 1032 */             cacheListItem(listItem);
/*      */           }
/*      */ 
/* 1037 */           if ((!a_bGetSource) && (listItem.getBody() != null))
/*      */           {
/* 1039 */             ItemBodyReplaceParser replaceParser = new ItemBodyReplaceParser(a_dbTransaction, this, listItem.getLanguage());
/*      */ 
/* 1042 */             listItem.setBody(replaceParser.parse(listItem.getBody()));
/*      */           }
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 1048 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1054 */       throw new Bn2Exception("ListManager.getListItem: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1057 */     return listItem;
/*      */   }
/*      */ 
/*      */   public synchronized String getNextCustomIdentifier()
/*      */     throws Bn2Exception
/*      */   {
/* 1064 */     String ksMethodName = "getNextCustomIdentifier";
/* 1065 */     long lValue = -1L;
/* 1066 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/* 1070 */       String sSQL = null;
/* 1071 */       PreparedStatement psql = null;
/* 1072 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/* 1073 */       Connection con = dbTransaction.getConnection();
/*      */ 
/* 1075 */       sSQL = "SELECT Value FROM SystemSetting WHERE Id=?";
/* 1076 */       psql = con.prepareStatement(sSQL);
/* 1077 */       psql.setString(1, "CustomListIdentifier");
/* 1078 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1080 */       if (rs.next())
/*      */       {
/* 1082 */         lValue = Long.parseLong(rs.getString("Value"));
/* 1083 */         psql.close();
/* 1084 */         lValue += 1L;
/* 1085 */         sSQL = "UPDATE SystemSetting SET Value=? WHERE Id=?";
/* 1086 */         psql = con.prepareStatement(sSQL);
/* 1087 */         psql.setLong(1, lValue);
/* 1088 */         psql.setString(2, "CustomListIdentifier");
/* 1089 */         psql.executeUpdate();
/*      */       }
/*      */       else
/*      */       {
/* 1093 */         psql.close();
/*      */ 
/* 1096 */         sSQL = "INSERT INTO SystemSetting (Id, Value) VALUES (?,?)";
/* 1097 */         psql = con.prepareStatement(sSQL);
/* 1098 */         lValue = 1L;
/* 1099 */         psql.setString(1, "CustomListIdentifier");
/* 1100 */         psql.setLong(2, lValue);
/* 1101 */         psql.executeUpdate();
/*      */       }
/* 1103 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1107 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1111 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (SQLException se)
/*      */         {
/* 1115 */           this.m_logger.error("Exception rolling back transaction in getNextCustomIdentifier:" + e.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1119 */       this.m_logger.error("Exception in getNextCustomIdentifier:" + e.getMessage());
/* 1120 */       throw new Bn2Exception("Exception in getNextCustomIdentifier:" + e.getMessage() + " when getting next custom identifier", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1124 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1128 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/* 1132 */           this.m_logger.error("Exception commiting transaction in getNextCustomIdentifier:" + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1137 */     return String.valueOf(lValue);
/*      */   }
/*      */ 
/*      */   public void saveListItem(DBTransaction a_dbTransaction, ListItem a_listItem)
/*      */     throws Bn2Exception
/*      */   {
/* 1153 */     String ksMethodName = "saveListItem";
/*      */     try
/*      */     {
/* 1157 */       String sSQL = null;
/* 1158 */       PreparedStatement psql = null;
/* 1159 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1161 */       sSQL = "SELECT Identifier FROM ListItem WHERE Identifier=? AND LanguageId=?";
/* 1162 */       psql = con.prepareStatement(sSQL);
/* 1163 */       psql.setString(1, a_listItem.getIdentifier());
/* 1164 */       psql.setLong(2, a_listItem.getLanguage().getId());
/* 1165 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1167 */       if (rs.next())
/*      */       {
/* 1170 */         sSQL = "UPDATE ListItem SET Summary=?, Body=? WHERE Identifier = ? AND LanguageId=?";
/*      */ 
/* 1172 */         psql = con.prepareStatement(sSQL);
/* 1173 */         int iCol = 1;
/* 1174 */         psql.setString(iCol++, a_listItem.getSummary());
/* 1175 */         psql.setString(iCol++, a_listItem.getBody());
/* 1176 */         psql.setString(iCol++, a_listItem.getIdentifier());
/* 1177 */         psql.setLong(iCol++, a_listItem.getLanguage().getId());
/* 1178 */         psql.executeUpdate();
/* 1179 */         psql.close();
/*      */ 
/* 1182 */         sSQL = "SELECT Identifier FROM ListItem WHERE Body LIKE ?";
/* 1183 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1185 */         psql.setString(1, "%#" + a_listItem.getIdentifier() + '#' + "%");
/* 1186 */         rs = psql.executeQuery();
/*      */         try
/*      */         {
/* 1190 */           while (rs.next())
/*      */           {
/* 1192 */             clearItemCache(rs.getString("Identifier"), a_listItem.getLanguage());
/*      */           }
/*      */         }
/*      */         finally
/*      */         {
/* 1197 */           psql.close();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1202 */         psql.close();
/*      */ 
/* 1205 */         sSQL = "INSERT INTO ListItem (Identifier,LanguageId,Title,Summary,Body,ListId,ListItemTextTypeId,CannotBeDeleted) VALUES (?,?,?,?,?,?,?,?)";
/*      */ 
/* 1207 */         psql = con.prepareStatement(sSQL);
/* 1208 */         int iCol = 1;
/* 1209 */         psql.setString(iCol++, a_listItem.getIdentifier());
/* 1210 */         psql.setLong(iCol++, a_listItem.getLanguage().getId());
/* 1211 */         psql.setString(iCol++, a_listItem.getTitle());
/* 1212 */         psql.setString(iCol++, a_listItem.getSummary());
/* 1213 */         psql.setString(iCol++, a_listItem.getBody());
/* 1214 */         psql.setLong(iCol++, a_listItem.getListId());
/* 1215 */         psql.setLong(iCol++, a_listItem.getListItemTextTypeId());
/* 1216 */         psql.setBoolean(iCol++, a_listItem.getCannotBeDeleted());
/* 1217 */         psql.executeUpdate();
/* 1218 */         psql.close();
/*      */       }
/*      */ 
/* 1222 */       removeCachedListItem(a_listItem);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1227 */       throw new Bn2Exception("ListManager.saveListItem: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteListItem(DBTransaction a_dbTransaction, String a_sIdentifier, long a_lLanguageId)
/*      */     throws Bn2Exception
/*      */   {
/* 1244 */     String ksMethodName = "deleteListItem";
/*      */     try
/*      */     {
/* 1249 */       ListItem listItem = getListItem(a_dbTransaction, a_sIdentifier, a_lLanguageId);
/*      */ 
/* 1251 */       if (listItem != null)
/*      */       {
/* 1254 */         com.bright.framework.simplelist.bean.List list = getList(a_dbTransaction, listItem.getListId(), null, false);
/*      */ 
/* 1256 */         String sSQL = null;
/* 1257 */         PreparedStatement psql = null;
/* 1258 */         Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1261 */         sSQL = "DELETE FROM ListItem WHERE Identifier = ? AND LanguageId = ?";
/*      */ 
/* 1263 */         psql = con.prepareStatement(sSQL);
/* 1264 */         psql.setString(1, a_sIdentifier);
/* 1265 */         psql.setLong(2, a_lLanguageId);
/* 1266 */         psql.executeUpdate();
/* 1267 */         psql.close();
/*      */ 
/* 1270 */         removeCachedListItem(listItem);
/* 1271 */         removeCachedList(list);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1277 */       throw new Bn2Exception("ListManager.deleteListItem: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void cacheListItem(ListItem a_item)
/*      */   {
/* 1294 */     synchronized (this.m_hmItemCache)
/*      */     {
/* 1296 */       if (a_item.getIdentifier() != null)
/*      */       {
/* 1298 */         HashMap hmLangs = (HashMap)this.m_hmItemCache.get(a_item.getIdentifier());
/*      */ 
/* 1300 */         if (hmLangs == null)
/*      */         {
/* 1302 */           hmLangs = new HashMap(10);
/* 1303 */           this.m_hmItemCache.put(a_item.getIdentifier(), hmLangs);
/*      */         }
/*      */ 
/* 1306 */         if (a_item.getLanguage().getId() > 0L)
/*      */         {
/* 1308 */           hmLangs.put(String.valueOf(a_item.getLanguage().getId()), a_item);
/*      */         }
/* 1310 */         if (StringUtils.isNotEmpty(a_item.getLanguage().getCode()))
/*      */         {
/* 1312 */           hmLangs.put(a_item.getLanguage().getCode(), a_item);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private ListItem getCachedListItem(String a_sIdentifier, Language a_language)
/*      */   {
/* 1332 */     synchronized (this.m_hmItemCache)
/*      */     {
/* 1334 */       HashMap hmLangs = (HashMap)this.m_hmItemCache.get(a_sIdentifier);
/* 1335 */       ListItem item = null;
/*      */ 
/* 1337 */       if (hmLangs != null)
/*      */       {
/* 1339 */         if (a_language.getId() > 0L)
/*      */         {
/* 1341 */           item = (ListItem)hmLangs.get(String.valueOf(a_language.getId()));
/*      */         }
/* 1343 */         else if (StringUtils.isNotEmpty(a_language.getCode()))
/*      */         {
/* 1345 */           item = (ListItem)hmLangs.get(a_language.getCode());
/*      */         }
/*      */       }
/* 1348 */       return item;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeCachedListItem(ListItem a_item)
/*      */   {
/* 1365 */     clearItemCache(a_item.getIdentifier(), a_item.getLanguage());
/*      */   }
/*      */ 
/*      */   private void cacheList(com.bright.framework.simplelist.bean.List a_list)
/*      */   {
/* 1381 */     if (a_list.getIdentifier() != null)
/*      */     {
/* 1383 */       this.m_hmListCache.put(a_list.getIdentifier(), a_list);
/*      */     }
/*      */   }
/*      */ 
/*      */   private com.bright.framework.simplelist.bean.List getCachedList(String a_sIdentifier)
/*      */   {
/* 1400 */     return (com.bright.framework.simplelist.bean.List)this.m_hmListCache.get(a_sIdentifier);
/*      */   }
/*      */ 
/*      */   private void removeCachedList(com.bright.framework.simplelist.bean.List a_list)
/*      */   {
/* 1416 */     if (a_list.getIdentifier() != null)
/*      */     {
/* 1418 */       this.m_hmListCache.remove(a_list.getIdentifier());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void clearItemCache()
/*      */   {
/* 1432 */     synchronized (this.m_hmItemCache)
/*      */     {
/* 1435 */       this.m_hmItemCache.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clearItemCache(String a_sId, Language a_language)
/*      */   {
/* 1451 */     synchronized (this.m_hmItemCache)
/*      */     {
/* 1453 */       if (a_sId != null)
/*      */       {
/* 1455 */         HashMap hmLangs = (HashMap)this.m_hmItemCache.get(a_sId);
/*      */ 
/* 1457 */         if (hmLangs != null)
/*      */         {
/* 1459 */           hmLangs.remove(String.valueOf(a_language.getId()));
/* 1460 */           hmLangs.remove(a_language.getCode());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 1468 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setLanguageManager(LanguageManager languageManager)
/*      */   {
/* 1473 */     this.m_languageManager = languageManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.service.ListManager
 * JD-Core Version:    0.6.0
 */