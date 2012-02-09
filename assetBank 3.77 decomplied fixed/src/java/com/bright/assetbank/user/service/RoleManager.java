/*     */ package com.bright.assetbank.user.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Role;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class RoleManager extends Bn2Manager
/*     */   implements UserConstants
/*     */ {
/*     */   private static final String c_ksClassName = "RoleManager";
/*  55 */   private HashMap m_hmSecuredActions = null;
/*  56 */   private HashMap m_hmSecuredLinks = null;
/*     */ 
/*  58 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*     */   public void startup() throws Bn2Exception
/*     */   {
/*  62 */     if (AssetBankSettings.getUseGroupRoles())
/*     */     {
/*  64 */       buildSecurityMaps();
/*     */     }
/*  66 */     super.startup();
/*     */   }
/*     */ 
/*     */   public HashMap getSecuredActions()
/*     */   {
/*  72 */     return this.m_hmSecuredActions;
/*     */   }
/*     */ 
/*     */   public HashMap getSecuredLinks()
/*     */   {
/*  78 */     return this.m_hmSecuredLinks;
/*     */   }
/*     */ 
/*     */   public int getUserRoleStatusForLink(ABUserProfile a_userProfile, String a_sLink)
/*     */   {
/*  84 */     return getUserRoleStatus(a_userProfile, a_sLink, false);
/*     */   }
/*     */ 
/*     */   public int getUserRoleStatusForAction(ABUserProfile a_userProfile, String a_sAction)
/*     */   {
/*  89 */     return getUserRoleStatus(a_userProfile, a_sAction, true);
/*     */   }
/*     */ 
/*     */   private int getUserRoleStatus(ABUserProfile a_userProfile, String a_sValue, boolean a_bActions)
/*     */   {
/*  94 */     int iUserRoleStatus = -1;
/*  95 */     HashMap hmToCheck = this.m_hmSecuredActions;
/*  96 */     if (!a_bActions)
/*     */     {
/*  98 */       hmToCheck = this.m_hmSecuredLinks;
/*     */     }
/*     */ 
/* 101 */     if (hmToCheck != null)
/*     */     {
/* 103 */       if (hmToCheck.containsKey(a_sValue))
/*     */       {
/* 105 */         iUserRoleStatus = 0;
/* 106 */         Vector vecAllowedRoles = (Vector)hmToCheck.get(a_sValue);
/*     */ 
/* 108 */         for (int i = 0; i < vecAllowedRoles.size(); i++)
/*     */         {
/* 110 */           if (!a_userProfile.getHasRole((String)vecAllowedRoles.elementAt(i)))
/*     */             continue;
/* 112 */           iUserRoleStatus = 1;
/* 113 */           break;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     return iUserRoleStatus;
/*     */   }
/*     */ 
/*     */   private void buildSecurityMaps()
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 127 */       SAXBuilder parser = new SAXBuilder();
/*     */ 
/* 129 */       String sSecurityFile = FrameworkSettings.getApplicationPath() + "/" + AssetBankSettings.getRolesSecurityFileDirectory() + "/" + "role_security.xml";
/*     */ 
/* 132 */       File fSecurityFile = new File(sSecurityFile);
/* 133 */       Document doc = parser.build(fSecurityFile);
/*     */ 
/* 135 */       Element rootElement = doc.getRootElement();
/*     */ 
/* 137 */       List secureElements = rootElement.getChildren();
/* 138 */       ListIterator secureIterator = secureElements.listIterator();
/*     */ 
/* 140 */       while (secureIterator.hasNext())
/*     */       {
/* 143 */         Element secureElement = (Element)secureIterator.next();
/*     */ 
/* 146 */         Element roleElement = secureElement.getChild("allowed_roles");
/* 147 */         List roleElements = roleElement.getChildren();
/* 148 */         ListIterator roleIterator = roleElements.listIterator();
/*     */ 
/* 150 */         Vector vecAllowedRoles = new Vector();
/*     */ 
/* 152 */         while (roleIterator.hasNext())
/*     */         {
/* 155 */           Element role = (Element)roleIterator.next();
/* 156 */           String sRole = role.getText();
/*     */ 
/* 159 */           if (sRole != null)
/*     */           {
/* 161 */             vecAllowedRoles.add(sRole);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 166 */         if (this.m_hmSecuredActions == null)
/*     */         {
/* 168 */           this.m_hmSecuredActions = new HashMap();
/*     */         }
/*     */ 
/* 171 */         if (this.m_hmSecuredLinks == null)
/*     */         {
/* 173 */           this.m_hmSecuredLinks = new HashMap();
/*     */         }
/*     */ 
/* 176 */         List securedElements = secureElement.getChildren("secured");
/* 177 */         ListIterator iterator = securedElements.listIterator();
/*     */ 
/* 179 */         while (iterator.hasNext())
/*     */         {
/* 181 */           Element childElement = (Element)iterator.next();
/*     */ 
/* 183 */           if (childElement != null)
/*     */           {
/* 185 */             Attribute attType = childElement.getAttribute("type");
/* 186 */             String sValue = childElement.getText();
/*     */ 
/* 188 */             if ((attType != null) && (sValue != null))
/*     */             {
/* 190 */               if (attType.getValue().equals("action"))
/*     */               {
/* 192 */                 this.m_hmSecuredActions.put(sValue, vecAllowedRoles);
/*     */               }
/*     */               else
/*     */               {
/* 196 */                 this.m_hmSecuredLinks.put(sValue, vecAllowedRoles);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 205 */       this.m_logger.debug("RoleManager.buildSecurityMaps : Exception occurred : " + e);
/* 206 */       throw new Bn2Exception("RoleManager.buildSecurityMaps : Exception occurred : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector<Role> getRoles(DBTransaction a_dbTransaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 226 */     Connection cCon = null;
/* 227 */     Vector vecRoles = null;
/* 228 */     DBTransaction transaction = a_dbTransaction;
/*     */     try
/*     */     {
/* 232 */       if (a_dbTransaction == null)
/*     */       {
/* 234 */         transaction = this.m_transactionManager.getNewTransaction();
/*     */       }
/* 236 */       cCon = transaction.getConnection();
/*     */ 
/* 239 */       String sSql = "SELECT * FROM Role r";
/*     */ 
/* 241 */       if (a_lId > 0L)
/*     */       {
/* 243 */         sSql = sSql + ", GroupIsRole g WHERE r.Id=g.RoleId AND g.UserGroupId=?";
/*     */       }
/*     */ 
/* 246 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/*     */ 
/* 248 */       if (a_lId > 0L)
/*     */       {
/* 250 */         psql.setLong(1, a_lId);
/*     */       }
/*     */ 
/* 253 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 256 */       while (rs.next())
/*     */       {
/* 258 */         if (vecRoles == null)
/*     */         {
/* 260 */           vecRoles = new Vector();
/*     */         }
/*     */ 
/* 263 */         Role role = buildRole(rs);
/* 264 */         vecRoles.add(role);
/*     */       }
/*     */ 
/* 267 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       try
/*     */       {
/* 273 */         if (a_dbTransaction == null)
/*     */         {
/* 275 */           transaction.rollback();
/*     */         }
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/*     */       }
/*     */ 
/* 282 */       this.m_logger.debug("RoleManager.getRoles : Exception occurred : " + e);
/* 283 */       throw new Bn2Exception("RoleManager.getRoles : Exception occurred : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 288 */       if (a_dbTransaction == null)
/*     */       {
/* 290 */         if (transaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 294 */             transaction.commit();
/*     */           }
/*     */           catch (SQLException sqle)
/*     */           {
/* 298 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 304 */     return vecRoles;
/*     */   }
/*     */ 
/*     */   public Role getRole(DBTransaction a_dbTransaction, String a_sIdentifier)
/*     */     throws Bn2Exception
/*     */   {
/* 325 */     Connection cCon = null;
/* 326 */     Role role = null;
/*     */     try
/*     */     {
/* 330 */       cCon = a_dbTransaction.getConnection();
/*     */ 
/* 332 */       String sSql = "SELECT * FROM Role r WHERE r.Identifier=?";
/*     */ 
/* 334 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 335 */       psql.setString(1, a_sIdentifier);
/* 336 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 338 */       if (rs.next())
/*     */       {
/* 340 */         role = new Role();
/* 341 */         role = buildRole(rs);
/*     */       }
/*     */ 
/* 344 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 349 */       this.m_logger.debug("RoleManager.getRole : Exception occurred : " + e);
/* 350 */       throw new Bn2Exception("RoleManager.getRole : Exception occurred : " + e, e);
/*     */     }
/*     */ 
/* 353 */     return role;
/*     */   }
/*     */ 
/*     */   public HashMap getRolesForUserByIdentifier(DBTransaction a_dbTransaction, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 373 */     Connection cCon = null;
/* 374 */     HashMap hmRoles = null;
/*     */     try
/*     */     {
/* 378 */       cCon = a_dbTransaction.getConnection();
/*     */ 
/* 381 */       String sSql = "SELECT * FROM Role r, GroupIsRole g WHERE r.Id=g.RoleId AND g.UserGroupId IN (SELECT Id FROM UserGroup g, UserInGroup uig WHERE g.Id=uig.UserGroupId AND uig.UserId=?)";
/*     */ 
/* 385 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 386 */       psql.setLong(1, a_lUserId);
/* 387 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 389 */       while (rs.next())
/*     */       {
/* 391 */         if (hmRoles == null)
/*     */         {
/* 393 */           hmRoles = new HashMap();
/*     */         }
/* 395 */         Role role = new Role();
/* 396 */         role = buildRole(rs);
/* 397 */         hmRoles.put(role.getIdentifier(), role);
/*     */       }
/*     */ 
/* 400 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 405 */       this.m_logger.debug("RoleManager.getRolesForUser : Exception occurred : " + e);
/* 406 */       throw new Bn2Exception("RoleManager.getRolesForUser : Exception occurred : " + e, e);
/*     */     }
/*     */ 
/* 409 */     return hmRoles;
/*     */   }
/*     */ 
/*     */   public void saveRoles(DBTransaction a_dbTransaction, long a_lGroupId, Vector a_vecRoles)
/*     */     throws Bn2Exception
/*     */   {
/* 429 */     Connection cCon = null;
/*     */     try
/*     */     {
/* 433 */       cCon = a_dbTransaction.getConnection();
/*     */ 
/* 436 */       String sSql = "DELETE FROM GroupIsRole WHERE UserGroupId=?";
/*     */ 
/* 438 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 439 */       psql.setLong(1, a_lGroupId);
/* 440 */       psql.executeUpdate();
/* 441 */       psql.close();
/*     */ 
/* 444 */       sSql = "INSERT INTO GroupIsRole (UserGroupId, RoleId) VALUES (?,?)";
/* 445 */       psql = cCon.prepareStatement(sSql);
/*     */ 
/* 447 */       for (int i = 0; i < a_vecRoles.size(); i++)
/*     */       {
/* 449 */         long lRoleId = ((Long)a_vecRoles.elementAt(i)).longValue();
/* 450 */         psql.setLong(1, a_lGroupId);
/* 451 */         psql.setLong(2, lRoleId);
/* 452 */         psql.executeUpdate();
/*     */       }
/*     */ 
/* 455 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 460 */       this.m_logger.debug("RoleManager.saveRoles : Exception occurred : " + e);
/* 461 */       throw new Bn2Exception("RoleManager.saveRoles : Exception occurred : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Role buildRole(ResultSet a_rs)
/*     */     throws SQLException
/*     */   {
/* 468 */     Role role = new Role();
/* 469 */     role.setId(a_rs.getLong("Id"));
/* 470 */     role.setName(a_rs.getString("Name"));
/* 471 */     role.setDescription(a_rs.getString("Description"));
/* 472 */     role.setIdentifier(a_rs.getString("Identifier"));
/*     */ 
/* 474 */     return role;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_dbTransactionManager)
/*     */   {
/* 479 */     this.m_transactionManager = a_dbTransactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.service.RoleManager
 * JD-Core Version:    0.6.0
 */