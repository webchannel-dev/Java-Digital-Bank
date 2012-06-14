/*      */ package com.bright.assetbank.ecommerce.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*      */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*      */ import com.bright.assetbank.commercialoption.bean.CommercialOption.Translation;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.ecommerce.bean.AssetInOrder;
/*      */ import com.bright.assetbank.ecommerce.bean.AssetPurchase;
/*      */ import com.bright.assetbank.ecommerce.bean.AssetPurchasePriceBand;
/*      */ import com.bright.assetbank.ecommerce.bean.AssetWithShippingPurchase;
/*      */ import com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase;
/*      */ import com.bright.assetbank.ecommerce.bean.Order;
/*      */ import com.bright.assetbank.ecommerce.bean.OrderPaymentPurchase;
/*      */ import com.bright.assetbank.ecommerce.bean.OrderSearchCriteria;
/*      */ import com.bright.assetbank.ecommerce.bean.OrderStatus;
/*      */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*      */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*      */ import com.bright.assetbank.ecommerce.bean.SubscriptionReportRecord;
/*      */ import com.bright.assetbank.ecommerce.bean.UploadersReportRecord;
/*      */ import com.bright.assetbank.ecommerce.bean.UserSubscriptionReportRecord;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.common.bean.Address;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDecimal;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.bean.Country;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */// import com.bright.framework.common.bean.TranslatableStringDataBean.Translation;
import com.bright.framework.common.bean.TranslatableStringDataBean;
/*      */ import com.bright.framework.common.service.AddressManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.constant.UserSettings;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class OrderManager extends Bn2Manager
/*      */   implements PaymentCallbackProcessor
/*      */ {
/*      */   private static final String c_ksClassName = "OrderManager";
/*  111 */   private EmailManager m_emailManager = null;
/*      */ 
/*  119 */   private AddressManager m_addressManager = null;
/*      */ 
/*  127 */   private AssetApprovalManager m_approvalManager = null;
/*      */ 
/*  134 */   private ABUserManager m_userManager = null;
/*      */ 
/*  140 */   private ListManager m_listManager = null;
/*      */ 
/*      */   public void setEmailManager(EmailManager a_emailManager)
/*      */   {
/*  115 */     this.m_emailManager = a_emailManager;
/*      */   }
/*      */ 
/*      */   public void setAddressManager(AddressManager a_sAddressManager)
/*      */   {
/*  123 */     this.m_addressManager = a_sAddressManager;
/*      */   }
/*      */ 
/*      */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*      */   {
/*  131 */     this.m_approvalManager = a_approvalManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_userManager)
/*      */   {
/*  137 */     this.m_userManager = a_userManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager a_listManager)
/*      */   {
/*  143 */     this.m_listManager = a_listManager;
/*      */   }
/*      */ 
/*      */   public Vector getOrders(DBTransaction a_dbTransaction, OrderSearchCriteria a_searchCriteria)
/*      */     throws Bn2Exception
/*      */   {
/*  158 */     return getOrders(a_dbTransaction, a_searchCriteria, null);
/*      */   }
/*      */ 
/*      */   public Vector getOrders(DBTransaction a_dbTransaction, OrderSearchCriteria a_searchCriteria, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  187 */     String ksMethodName = "getOrders";
/*      */ 
/*  189 */     Connection con = null;
/*  190 */     PreparedStatement psql = null;
/*  191 */     String sSQL = null;
/*  192 */     ResultSet rs = null;
/*  193 */     Vector vecOrders = null;
/*      */     try
/*      */     {
/*  197 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  199 */       sSQL = "SELECT ord.Id as orderid, ord.DatePlaced, ord.DateFulfilled, ord.SubTotal, ord.VATPercent, ord.Total, ord.BasketCost as orderbasketcost, ord.ShippingCost as ordershipping, ord.PurchaseId, ord.PSPTransId, ord.RecipientName, ord.ShippingAddressId, ord.UserNotes, ord.PrefersOfflinePayment, ord.DiscountPercentage, orderstatus.Id as orderstatusid, orderstatus.Name as orderstatusname, orderstatus.ManualSelect, " + ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)) ? "tos.Name tosName, " : "") + "orderworkflow.Id as workflowid, " + "orderworkflow.Name as workflowname, " + "usr.Id as userid, " + "usr.Forename, " + "usr.Surname, " + "usr.Username, " + "usr.EmailAddress, " + "usr.Password, " + "usr.AddressId as useraddressid, " + "usr.Address as useraddress, " + "usr.Organisation, " + "cfvm.TextValue as telephone, " + "abpur.AssetId, " + "abpur.Description as assetdesc, " + "abpur.Price as assetcost, " + "abpur.ShippingCost as assetshipping, " + "abpurpb.PriceBandId as pricebandid, " + "abpurpb.PriceBandName, " + "abpurpb.PriceBandTypeId, " + "abpurpb.PriceBandTypeName, " + "abpurpb.Quantity, " + "abpurpb.Cost purchasecost, " + "abpurpb.ShippingCost purchaseshipping, " + "commoptpur.Price commoptpurprice, " + "commoptpur.Description commoptpurdesc, " + "commopt.Id commoptid, " + "commopt.Name commoptname, " + "commopt.Description commoptdesc, " + ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)) ? "tco.Name tcoName, " : "") + ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)) ? "tco.Description tcoDescription, " : "") + "commopt.Price commoptprice, " + "commopt.Terms, " + "commopt.IsDisabled " + "FROM " + "ABOrder ord " + "INNER JOIN AssetBankUser usr ON ord.UserId = usr.Id " + "INNER JOIN OrderStatus orderstatus ON ord.OrderStatusId = orderstatus.Id " + "INNER JOIN OrderWorkflow orderworkflow ON orderstatus.OrderWorkflowId = orderworkflow.Id " + "INNER JOIN AssetPurchaseLog abpur ON ord.Id = abpur.ABOrderId " + "LEFT JOIN AssetPurchasePriceBand abpurpb ON abpurpb.ABOrderId = abpur.ABOrderId AND abpurpb.AssetId = abpur.AssetId " + "LEFT JOIN CommercialOptionPurchase commoptpur ON commoptpur.PriceBandId = abpurpb.PriceBandId AND commoptpur.ABOrderId = abpurpb.ABOrderId AND commoptpur.AssetId = abpurpb.AssetId " + "LEFT JOIN CommercialOption commopt ON commopt.Id = commoptpur.CommercialOptionId " + "LEFT JOIN CustomFieldValueMapping cfvm ON cfvm.ItemId = usr.Id AND cfvm.CustomFieldId = " + 1001L + " ";
/*      */ 
/*  263 */       if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)))
/*      */       {
/*  265 */         sSQL = sSQL + "LEFT JOIN TranslatedOrderStatus tos ON tos.OrderStatusId=orderstatus.Id AND tos.LanguageId=" + a_language.getId() + " " + "LEFT JOIN TranslatedCommercialOption tco ON tco.CommercialOptionId=commopt.Id AND tco.LanguageId=" + a_language.getId() + " ";
/*      */       }
/*      */ 
/*  269 */       sSQL = sSQL + "WHERE 1=1";
/*      */ 
/*  272 */       if (a_searchCriteria.getStartDate() != null)
/*      */       {
/*  274 */         sSQL = sSQL + " AND ord.DatePlaced >= ?";
/*      */       }
/*      */ 
/*  277 */       if (a_searchCriteria.getEndDate() != null)
/*      */       {
/*  279 */         sSQL = sSQL + " AND ord.DatePlaced <= ?";
/*      */       }
/*      */ 
/*  282 */       if (a_searchCriteria.getOrderId() > 0L)
/*      */       {
/*  284 */         sSQL = sSQL + " AND ord.Id = ?";
/*      */       }
/*      */ 
/*  287 */       if (a_searchCriteria.getPurchaseId() != null)
/*      */       {
/*  289 */         sSQL = sSQL + " AND ord.PurchaseId = ?";
/*      */       }
/*      */ 
/*  292 */       if (a_searchCriteria.getOrderStatus() > 0L)
/*      */       {
/*  294 */         sSQL = sSQL + " AND orderstatus.Id = ?";
/*      */       }
/*      */ 
/*  297 */       if (a_searchCriteria.getOrderWorkflow() > 0L)
/*      */       {
/*  299 */         sSQL = sSQL + " AND orderworkflow.Id = ?";
/*      */       }
/*      */ 
/*  302 */       if (a_searchCriteria.getUserId() > 0L)
/*      */       {
/*  304 */         sSQL = sSQL + " AND usr.Id = ?";
/*      */       }
/*      */ 
/*  307 */       if (a_searchCriteria.getOrderPriceBandType() > 0L)
/*      */       {
/*  309 */         sSQL = sSQL + " AND EXISTS (SELECT * FROM AssetPurchasePriceBand WHERE PriceBandTypeId = ? AND AssetPurchasePriceBand.ABOrderId = ord.Id)";
/*      */       }
/*      */ 
/*  316 */       sSQL = sSQL + " ORDER BY ord.DatePlaced DESC, ord.Id, abpur.AssetId";
/*      */ 
/*  320 */       int iField = 1;
/*  321 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  323 */       if (a_searchCriteria.getStartDate() != null)
/*      */       {
/*  325 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_searchCriteria.getStartDate());
/*      */       }
/*      */ 
/*  328 */       if (a_searchCriteria.getEndDate() != null)
/*      */       {
/*  330 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_searchCriteria.getEndDate());
/*      */       }
/*      */ 
/*  333 */       if (a_searchCriteria.getOrderId() > 0L)
/*      */       {
/*  335 */         psql.setLong(iField++, a_searchCriteria.getOrderId());
/*      */       }
/*      */ 
/*  338 */       if (a_searchCriteria.getPurchaseId() != null)
/*      */       {
/*  340 */         psql.setString(iField++, a_searchCriteria.getPurchaseId());
/*      */       }
/*      */ 
/*  343 */       if (a_searchCriteria.getOrderStatus() > 0L)
/*      */       {
/*  345 */         psql.setLong(iField++, a_searchCriteria.getOrderStatus());
/*      */       }
/*      */ 
/*  348 */       if (a_searchCriteria.getOrderWorkflow() > 0L)
/*      */       {
/*  350 */         psql.setLong(iField++, a_searchCriteria.getOrderWorkflow());
/*      */       }
/*      */ 
/*  353 */       if (a_searchCriteria.getUserId() > 0L)
/*      */       {
/*  355 */         psql.setLong(iField++, a_searchCriteria.getUserId());
/*      */       }
/*      */ 
/*  358 */       if (a_searchCriteria.getOrderPriceBandType() > 0L)
/*      */       {
/*  360 */         psql.setLong(iField++, a_searchCriteria.getOrderPriceBandType());
/*      */       }
/*      */ 
/*  363 */       rs = psql.executeQuery();
/*  364 */       Order order = null;
/*  365 */       Vector vecAssets = null;
/*  366 */       Vector vecPriceBands = null;
/*  367 */       AssetInOrder asset = null;
/*  368 */       AssetPurchasePriceBand priceBand = null;
/*      */ 
/*  371 */       while (rs.next())
/*      */       {
/*  373 */         boolean newOrder = false;
/*  374 */         if (vecOrders == null)
/*      */         {
/*  376 */           vecOrders = new Vector();
/*      */         }
/*      */ 
/*  380 */         if ((order == null) || (rs.getLong("orderid") != order.getId()))
/*      */         {
/*  382 */           newOrder = true;
/*      */ 
/*  385 */           order = new Order();
/*  386 */           order.setId(rs.getLong("orderid"));
/*  387 */           order.setDatePlaced(rs.getDate("DatePlaced"));
/*  388 */           order.setDateFulfilled(rs.getDate("DateFulfilled"));
/*  389 */           order.getSubtotal().setAmount(rs.getLong("SubTotal"));
/*  390 */           order.getVatPercent().setNumber(rs.getFloat("VATPercent"));
/*  391 */           order.getTotal().setAmount(rs.getLong("Total"));
/*  392 */           order.getBasketCost().setAmount(rs.getLong("orderbasketcost"));
/*  393 */           order.getShippingCost().setAmount(rs.getLong("ordershipping"));
/*  394 */           order.setUserNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "UserNotes"));
/*  395 */           order.setPrefersOfflinePayment(rs.getBoolean("PrefersOfflinePayment"));
/*  396 */           order.getUser().setId(rs.getLong("userid"));
/*  397 */           order.getUser().setForename(rs.getString("Forename"));
/*  398 */           order.getUser().setSurname(rs.getString("Surname"));
/*  399 */           order.getUser().setUsername(rs.getString("Username"));
/*  400 */           order.getUser().setEmailAddress(rs.getString("EMailAddress"));
/*  401 */           order.setUserTelephone(rs.getString("telephone"));
/*  402 */           order.getUser().setPassword(rs.getString("Password"));
/*  403 */           order.getUser().setAddress(rs.getString("useraddress"));
/*  404 */           order.getUser().setOrganisation(rs.getString("Organisation"));
/*  405 */           order.getUser().getHomeAddress().setId(rs.getLong("useraddressid"));
/*  406 */           order.setPurchaseId(rs.getString("PurchaseId"));
/*  407 */           order.setPspTransId(rs.getString("PSPTransId"));
/*  408 */           order.getStatus().setId(rs.getLong("orderstatusid"));
/*      */ 
/*  410 */           if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)) && (StringUtils.isNotEmpty(rs.getString("tosName"))))
/*      */           {
/*  412 */             order.getStatus().setName(rs.getString("tosName"));
/*      */           }
/*      */           else
/*      */           {
/*  416 */             order.getStatus().setName(rs.getString("orderstatusname"));
/*      */           }
/*      */ 
/*  419 */           order.getStatus().setManualSelect(rs.getBoolean("ManualSelect"));
/*  420 */           order.getStatus().getOrderWorkflow().setId(rs.getLong("workflowid"));
/*  421 */           order.getStatus().getOrderWorkflow().setName(rs.getString("workflowname"));
/*  422 */           order.setRecipientName(rs.getString("RecipientName"));
/*  423 */           order.getShippingAddress().setId(rs.getLong("ShippingAddressId"));
/*  424 */           order.setDiscountPercentage(rs.getInt("DiscountPercentage"));
/*      */ 
/*  426 */           vecOrders.add(order);
/*      */ 
/*  429 */           vecAssets = new Vector();
/*  430 */           order.setAssets(vecAssets);
/*      */         }
/*      */ 
/*  435 */         if ((asset == null) || (rs.getLong("AssetId") != asset.getAssetId()) || (newOrder))
/*      */         {
/*  438 */           asset = new AssetInOrder();
/*  439 */           asset.setAssetId(rs.getLong("AssetId"));
/*  440 */           asset.setDescription(rs.getString("assetdesc"));
/*  441 */           asset.getPrice().setAmount(rs.getLong("assetcost"));
/*  442 */           asset.getShippingCost().setAmount(rs.getLong("assetshipping"));
/*      */ 
/*  444 */           vecAssets.add(asset);
/*      */ 
/*  446 */           vecPriceBands = new Vector();
/*  447 */           asset.setPriceBands(vecPriceBands);
/*      */         }
/*      */ 
/*  452 */         priceBand = new AssetPurchasePriceBand();
/*  453 */         priceBand.getPriceBand().setId(rs.getLong("pricebandid"));
/*  454 */         priceBand.getPriceBand().setName(rs.getString("PriceBandName"));
/*  455 */         priceBand.getPriceBandType().setId(rs.getLong("PriceBandTypeId"));
/*  456 */         priceBand.getPriceBandType().setName(rs.getString("PriceBandTypeName"));
/*  457 */         priceBand.setQuantity(rs.getInt("Quantity"));
/*  458 */         priceBand.getCost().setAmount(rs.getLong("purchasecost"));
/*  459 */         priceBand.getShippingCost().setAmount(rs.getLong("purchaseshipping"));
/*  460 */         priceBand.getCommercialOptionPurchase().setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "commoptpurdesc"));
/*  461 */         priceBand.getCommercialOptionPurchase().getPrice().setAmount(rs.getLong("commoptpurprice"));
/*  462 */         priceBand.getCommercialOptionPurchase().getCommercialOption().setId(rs.getLong("commoptid"));
/*  463 */         priceBand.getCommercialOptionPurchase().getCommercialOption().setName(rs.getString("commoptname"));
/*  464 */         priceBand.getCommercialOptionPurchase().getCommercialOption().setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "commoptdesc"));
/*  465 */         priceBand.getCommercialOptionPurchase().getCommercialOption().setTerms(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Terms"));
/*  466 */         priceBand.getCommercialOptionPurchase().getCommercialOption().setDisabled(rs.getBoolean("Terms"));
/*      */ 
/*  468 */         if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)) && (StringUtils.isNotEmpty(rs.getString("tcoName"))))
/*      */         {
/*  470 */           priceBand.getCommercialOptionPurchase().getCommercialOption().setName(rs.getString("tcoName"));
/*      */         }
/*  472 */         if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)) && (StringUtils.isNotEmpty(rs.getString("tcoDescription"))))
/*      */         {
/*  474 */           priceBand.getCommercialOptionPurchase().getCommercialOption().setDescription(rs.getString("tcoDescription"));
/*      */         }
/*      */ 
/*  477 */         vecPriceBands.add(priceBand);
/*      */       }
/*      */ 
/*  480 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  484 */       this.m_logger.error("OrderManager.getOrders - " + e);
/*  485 */       throw new Bn2Exception("OrderManager.getOrders", e);
/*      */     }
/*      */ 
/*  488 */     return vecOrders;
/*      */   }
/*      */ 
/*      */   public Order getOrder(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/*  504 */     return getOrder(a_dbTransaction, a_lOrderId, null);
/*      */   }
/*      */ 
/*      */   public Order getOrder(DBTransaction a_dbTransaction, long a_lOrderId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  527 */     OrderSearchCriteria filter = new OrderSearchCriteria();
/*  528 */     filter.setOrderId(a_lOrderId);
/*  529 */     Order order = (Order)getOrders(a_dbTransaction, filter, a_language).get(0);
/*      */ 
/*  532 */     long shipAddressId = order.getShippingAddress().getId();
/*  533 */     if (shipAddressId > 0L)
/*      */     {
/*  535 */       order.setShippingAddress(this.m_addressManager.getAddress(a_dbTransaction, shipAddressId));
/*      */     }
/*      */ 
/*  539 */     long homeAddressId = order.getUser().getHomeAddress().getId();
/*  540 */     if (homeAddressId > 0L)
/*      */     {
/*  542 */       order.getUser().setHomeAddress(this.m_addressManager.getAddress(a_dbTransaction, homeAddressId));
/*      */     }
/*      */ 
/*  545 */     return order;
/*      */   }
/*      */ 
/*      */   public Vector getOrderStatusList(DBTransaction a_dbTransaction, long a_lWorkflowId, boolean a_bFilterManualSelect)
/*      */     throws Bn2Exception
/*      */   {
/*  568 */     String ksMethodName = "getOrderStatusList";
/*      */ 
/*  570 */     Connection con = null;
/*  571 */     PreparedStatement psql = null;
/*  572 */     String sSQL = null;
/*  573 */     ResultSet rs = null;
/*  574 */     Vector vecOrderStatusList = null;
/*      */     try
/*      */     {
/*  578 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  581 */       sSQL = "SELECT ordStat.Id as orderstatid, ordStat.Name as orderstatname, ordStat.ManualSelect, ordStat.OrderWorkflowId, ordWflow.Name as orderwflowname, tos.Name tosName, l.Id lId, l.Name lName, l.Code lCode FROM OrderStatus ordStat INNER JOIN OrderWorkflow ordWflow ON ordWflow.Id = ordStat.OrderWorkflowId LEFT JOIN TranslatedOrderStatus tos ON tos.OrderStatusId = ordStat.Id LEFT JOIN Language l ON l.Id = tos.LanguageId WHERE 1=1 ";
/*      */ 
/*  600 */       if (a_lWorkflowId > 0L)
/*      */       {
/*  602 */         sSQL = sSQL + " AND OrderWorkflowId = ?";
/*      */       }
/*      */ 
/*  605 */       if (a_bFilterManualSelect)
/*      */       {
/*  607 */         sSQL = sSQL + " AND ManualSelect = 1";
/*      */       }
/*      */ 
/*  610 */       sSQL = sSQL + " ORDER BY orderstatid";
/*      */ 
/*  612 */       int iField = 1;
/*  613 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  615 */       if (a_lWorkflowId > 0L)
/*      */       {
/*  617 */         psql.setLong(iField++, a_lWorkflowId);
/*      */       }
/*      */ 
/*  620 */       rs = psql.executeQuery();
/*  621 */       OrderStatus orderStatus = null;
/*  622 */       long lLastSeenId = 0L;
/*      */ 
/*  624 */       while (rs.next())
/*      */       {
/*  626 */         if (lLastSeenId != rs.getLong("orderstatid"))
/*      */         {
/*  628 */           if (vecOrderStatusList == null)
/*      */           {
/*  630 */             vecOrderStatusList = new Vector();
/*      */           }
/*      */ 
/*  634 */           orderStatus = new OrderStatus();
/*  635 */           orderStatus.setId(rs.getLong("orderstatid"));
/*  636 */           orderStatus.setName(rs.getString("orderstatname"));
/*  637 */           orderStatus.setManualSelect(rs.getBoolean("ManualSelect"));
/*  638 */           orderStatus.getOrderWorkflow().setId(rs.getLong("OrderWorkflowId"));
/*  639 */           orderStatus.getOrderWorkflow().setName(rs.getString("orderwflowname"));
/*      */ 
/*  641 */           vecOrderStatusList.add(orderStatus);
/*      */ 
/*  643 */           lLastSeenId = orderStatus.getId();
/*      */         }
/*      */ 
/*  646 */         if (rs.getLong("lId") <= 0L)
/*      */           continue;
/*      */         //OrderStatus tmp313_311 = orderStatus; tmp313_311.getClass(); TranslatableStringDataBean.Translation translation = new TranslatableStringDataBean.Translation(tmp313_311, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/*  649 */         TranslatableStringDataBean.Translation translation = orderStatus.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                    translation.setName(rs.getString("tosName"));
/*  650 */         orderStatus.getTranslations().add(translation);
/*      */       }
/*      */ 
/*  654 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  658 */       this.m_logger.error("OrderManager.getOrderStatusList - " + e);
/*  659 */       throw new Bn2Exception("OrderManager.getOrderStatusList", e);
/*      */     }
/*      */ 
/*  662 */     return vecOrderStatusList;
/*      */   }
/*      */ 
/*      */   public Vector getPriceBandTypeSearchList(long a_lWorkflowId)
/*      */   {
/*  681 */     Vector vecPriceBandSearch = null;
/*  682 */     if (a_lWorkflowId == 1L)
/*      */     {
/*  684 */       vecPriceBandSearch = new Vector();
/*  685 */       vecPriceBandSearch.add(new StringDataBean(1L, "Contains Downloads"));
/*      */ 
/*  688 */       vecPriceBandSearch.add(new StringDataBean(2L, "Contains Prints"));
/*      */     }
/*      */ 
/*  693 */     return vecPriceBandSearch;
/*      */   }
/*      */ 
/*      */   public CommercialOptionPurchase getCommercialOptionPurchase(DBTransaction a_dbTransaction, long a_lPriceBandId, long a_lABOrderId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  716 */     String ksMethodName = "getCommercialOptionPurchase";
/*      */ 
/*  718 */     Connection con = null;
/*  719 */     PreparedStatement psql = null;
/*  720 */     String sSQL = null;
/*  721 */     ResultSet rs = null;
/*  722 */     CommercialOptionPurchase commOptPur = null;
/*      */     try
/*      */     {
/*  726 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  729 */       sSQL = "SELECT commoptpur.Price as commoptpurprice, commoptpur.Description as commoptpurdesc, commopt.Id as commoptid, commopt.Name as commoptname, commopt.Description as commoptdesc, commopt.Price as commoptprice, commopt.Terms, commopt.IsDisabled, tco.Name, tco.Description, tco.Terms, l.Id lId, l.Code lCode, l.Name lName FROM CommercialOptionPurchase commoptpur LEFT JOIN CommercialOption commopt ON commopt.Id = commoptpur.CommercialOptionId LEFT JOIN TranslatedCommercialOption tco ON tco.CommercialOptionId = commopt.Id LEFT JOIN Language l ON l.Id = tco.LanguageId WHERE commoptpur.PriceBandId = ? AND commoptpur.ABOrderId = ? AND commoptpur.AssetId = ?";
/*      */ 
/*  754 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  756 */       int iCol = 1;
/*  757 */       psql.setLong(iCol++, a_lPriceBandId);
/*  758 */       psql.setLong(iCol++, a_lABOrderId);
/*  759 */       psql.setLong(iCol++, a_lAssetId);
/*      */ 
/*  761 */       rs = psql.executeQuery();
/*      */ 
/*  764 */       while (rs.next())
/*      */       {
/*  766 */         if (commOptPur == null)
/*      */         {
/*  768 */           commOptPur = new CommercialOptionPurchase();
/*  769 */           commOptPur.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "commoptpurdesc"));
/*  770 */           commOptPur.getPrice().setAmount(rs.getLong("commoptpurprice"));
/*  771 */           commOptPur.getCommercialOption().setId(rs.getLong("commoptid"));
/*  772 */           commOptPur.getCommercialOption().setName(rs.getString("commoptname"));
/*  773 */           commOptPur.getCommercialOption().setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "commoptdesc"));
/*  774 */           commOptPur.getCommercialOption().setTerms(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Terms"));
/*  775 */           commOptPur.getCommercialOption().setDisabled(rs.getBoolean("Terms"));
/*      */         }
/*      */ 
/*  778 */         if (rs.getLong("lId") <= 0L)
/*      */           continue;
                    CommercialOption comoption= commOptPur.getCommercialOption();
/*      */         //CommercialOption tmp267_264 = commOptPur.getCommercialOption(); tmp267_264.getClass(); CommercialOption.Translation translation = new CommercialOption.Translation(tmp267_264, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                   CommercialOption.Translation translation = comoption.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/*  781 */         translation.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tco.Description"));
/*  782 */         translation.setName(rs.getString("tco.Name"));
/*  783 */         translation.setTerms(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tco.Terms"));
/*  784 */         commOptPur.getCommercialOption().getTranslations().add(translation);
/*      */       }
/*      */ 
/*  788 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  792 */       this.m_logger.error("OrderManager.getCommercialOptionPurchase - " + e);
/*  793 */       throw new Bn2Exception("OrderManager.getCommercialOptionPurchase", e);
/*      */     }
/*      */ 
/*  796 */     return commOptPur;
/*      */   }
/*      */ 
/*      */   public void updateCommercialOptionPurchase(DBTransaction a_dbTransaction, CommercialOptionPurchase a_commercialOptionPurchase, long a_lPriceBandId, long a_lABOrderId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  820 */     String ksMethodName = "updateCommercialOptionPurchase";
/*      */ 
/*  822 */     Connection con = null;
/*  823 */     PreparedStatement psql = null;
/*  824 */     String sSQL = null;
/*      */     try
/*      */     {
/*  829 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  832 */       sSQL = "UPDATE CommercialOptionPurchase SET CommercialOptionId = ? , Price = ?, Description = ? WHERE PriceBandId = ? AND ABOrderId = ? AND AssetId = ?";
/*      */ 
/*  843 */       psql = con.prepareStatement(sSQL);
/*  844 */       int iCol = 1;
/*      */ 
/*  846 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_commercialOptionPurchase.getCommercialOption().getId());
/*  847 */       psql.setLong(iCol++, a_commercialOptionPurchase.getPrice().getAmount());
/*  848 */       psql.setString(iCol++, a_commercialOptionPurchase.getDescription());
/*  849 */       psql.setLong(iCol++, a_lPriceBandId);
/*  850 */       psql.setLong(iCol++, a_lABOrderId);
/*  851 */       psql.setLong(iCol++, a_lAssetId);
/*      */ 
/*  853 */       psql.executeUpdate();
/*  854 */       psql.close();
/*      */ 
/*  856 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  860 */       this.m_logger.error("OrderManager.updateCommercialOptionPurchase - " + e);
/*  861 */       throw new Bn2Exception("OrderManager.updateCommercialOptionPurchase", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateAssetPurchaseLog(DBTransaction a_dbTransaction, long a_lAssetId, long a_lABOrderId, BrightMoney a_price)
/*      */     throws Bn2Exception
/*      */   {
/*  871 */     String ksMethodName = "updateAssetPurchaseLog";
/*      */ 
/*  873 */     Connection con = null;
/*  874 */     PreparedStatement psql = null;
/*  875 */     String sSQL = null;
/*      */     try
/*      */     {
/*  879 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  881 */       sSQL = "UPDATE AssetPurchaseLog SET Price = ? WHERE ABOrderId = ? AND AssetId = ?";
/*      */ 
/*  889 */       psql = con.prepareStatement(sSQL);
/*  890 */       int iCol = 1;
/*      */ 
/*  892 */       psql.setLong(iCol++, a_price.getAmount());
/*  893 */       psql.setLong(iCol++, a_lABOrderId);
/*  894 */       psql.setLong(iCol++, a_lAssetId);
/*      */ 
/*  896 */       psql.executeUpdate();
/*  897 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  901 */       this.m_logger.error("OrderManager.updateAssetPurchaseLog - " + e);
/*  902 */       throw new Bn2Exception("OrderManager.updateAssetPurchaseLog", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateOrderTotals(DBTransaction a_dbTransaction, Order a_order)
/*      */     throws Bn2Exception
/*      */   {
/*  923 */     String ksMethodName = "updateOrderTotals";
/*      */ 
/*  925 */     Connection con = null;
/*  926 */     PreparedStatement psql = null;
/*  927 */     String sSQL = null;
/*      */     try
/*      */     {
/*  932 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  935 */       sSQL = "UPDATE ABOrder SET Total = ? , SubTotal = ? WHERE Id = ?";
/*      */ 
/*  943 */       psql = con.prepareStatement(sSQL);
/*  944 */       int iCol = 1;
/*      */ 
/*  946 */       psql.setLong(iCol++, a_order.getTotal().getAmount());
/*  947 */       psql.setLong(iCol++, a_order.getSubtotal().getAmount());
/*  948 */       psql.setLong(iCol++, a_order.getId());
/*      */ 
/*  950 */       psql.executeUpdate();
/*  951 */       psql.close();
/*      */ 
/*  953 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  957 */       this.m_logger.error("OrderManager.updateOrderTotals - " + e);
/*  958 */       throw new Bn2Exception("OrderManager.updateOrderTotals", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateOrderStatus(DBTransaction a_dbTransaction, long a_lOrderId, long a_lOrderStatus)
/*      */     throws Bn2Exception
/*      */   {
/*  981 */     String ksMethodName = "setOrderStatus";
/*      */ 
/*  983 */     Connection con = null;
/*  984 */     String sSQL = null;
/*  985 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  989 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  991 */       sSQL = "UPDATE ABOrder SET OrderStatusId = ? ";
/*      */ 
/*  993 */       if (a_lOrderStatus == 1L)
/*      */       {
/*  995 */         sSQL = sSQL + ", DateFulfilled = ? ";
/*      */       }
/*      */ 
/*  998 */       sSQL = sSQL + "WHERE Id = ? ";
/*      */ 
/* 1000 */       psql = con.prepareStatement(sSQL);
/* 1001 */       int iCol = 1;
/*      */ 
/* 1003 */       psql.setLong(iCol++, a_lOrderStatus);
/* 1004 */       if (a_lOrderStatus == 1L)
/*      */       {
/* 1006 */         psql.setTimestamp(iCol++, new Timestamp(System.currentTimeMillis()));
/*      */       }
/* 1008 */       psql.setLong(iCol++, a_lOrderId);
/*      */ 
/* 1010 */       psql.executeUpdate();
/* 1011 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1016 */       throw new Bn2Exception("OrderManager.setOrderStatus: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendConfirmOrderShipmentEmail(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1040 */     String ksMethodName = "sendConfirmOrderShipmentEmail";
/*      */ 
/* 1042 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1044 */     HashMap params = new HashMap();
/* 1045 */     getOrderEmailCommonParams(order, params, null);
/* 1046 */     getPersonalOrderDetailParams(order, params, false);
/* 1047 */     params.put("template", "order_shipped_confirmation");
/*      */     try
/*      */     {
/* 1051 */       this.m_emailManager.sendTemplatedEmail(params, order.getUser().getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1055 */       this.m_logger.debug("OrderManager:sendConfirmOrderShipmentEmail: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendConfirmOrderPurchaseEmail(DBTransaction a_dbTransaction, long a_lOrderId, Language a_language, AssetPurchase a_purchase)
/*      */     throws Bn2Exception
/*      */   {
/* 1070 */     String ksMethodName = "sendConfirmOrderPurchaseEmail";
/*      */ 
/* 1072 */     String sNewLineChar = "<br />";
/*      */ 
/* 1076 */     HashMap params = new HashMap();
/* 1077 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1080 */     if (order.getHasPrints())
/*      */     {
/* 1082 */       params.put("purchasePrintInstructions", StringUtil.stripHTML(this.m_listManager.getListItem(a_dbTransaction, "purchase-print-instructions", a_language).getBody()));
/*      */     }
/*      */     else
/*      */     {
/* 1087 */       params.put("purchasePrintInstructions", "");
/*      */     }
/*      */ 
/* 1090 */     if (order.getHasDownloads())
/*      */     {
/* 1092 */       params.put("purchaseDownloadInstructions", StringUtil.decodeString(StringUtil.stripHTML(this.m_listManager.getListItem(a_dbTransaction, "purchase-download-instructions", a_language).getBody())));
/*      */     }
/*      */     else
/*      */     {
/* 1097 */       params.put("purchaseDownloadInstructions", "");
/*      */     }
/*      */ 
/* 1101 */     if (order.isPersonal())
/*      */     {
/* 1103 */       getPersonalOrderDetailParams(order, params, false);
/* 1104 */       params.put("header", "IMPORTANT INFORMATION ABOUT YOUR PURCHASE");
/* 1105 */       params.put("purchaseCommercialReceiptNote", "");
/* 1106 */       params.put("purchaseTermsLink", StringUtil.stripHTML(this.m_listManager.getListItem(a_dbTransaction, "purchase-personal-terms-link", a_language).getBody()));
/*      */     }
/*      */     else
/*      */     {
/* 1111 */       getCommercialOrderDetailParams(order, params);
/* 1112 */       params.put("header", "IMPORTANT INFORMATION ABOUT YOUR PURCHASE OF IMAGES FOR COMMERCIAL USE");
/* 1113 */       params.put("purchaseTermsLink", StringUtil.stripHTML(this.m_listManager.getListItem(a_dbTransaction, "purchase-commercial-terms-link", a_language).getBody()));
/*      */ 
/* 1116 */       String receiptNote = StringUtil.stripHTML(this.m_listManager.getListItem(a_dbTransaction, "purchase-commercial-receipt-note", a_language).getBody()) + sNewLineChar;
/*      */ 
/* 1118 */       String receiptAddress = "";
/* 1119 */       receiptAddress = receiptAddress + order.getUser().getFullName() + sNewLineChar;
/* 1120 */       receiptAddress = receiptAddress + order.getUser().getOrganisation() + sNewLineChar;
/*      */ 
/* 1122 */       if (order.getUser().getHomeAddress().getId() > 0L)
/*      */       {
/* 1124 */         String address1 = order.getUser().getHomeAddress().getAddressLine1();
/* 1125 */         if (StringUtil.stringIsPopulated(address1))
/*      */         {
/* 1127 */           receiptAddress = receiptAddress + address1 + sNewLineChar;
/*      */         }
/* 1129 */         String address2 = order.getUser().getHomeAddress().getAddressLine2();
/* 1130 */         if (StringUtil.stringIsPopulated(address2))
/*      */         {
/* 1132 */           receiptAddress = receiptAddress + address2 + sNewLineChar;
/*      */         }
/* 1134 */         receiptAddress = receiptAddress + order.getUser().getHomeAddress().getTown() + sNewLineChar;
/* 1135 */         receiptAddress = receiptAddress + order.getUser().getHomeAddress().getCounty() + sNewLineChar;
/* 1136 */         receiptAddress = receiptAddress + order.getUser().getHomeAddress().getPostcode() + sNewLineChar;
/* 1137 */         receiptAddress = receiptAddress + order.getUser().getHomeAddress().getCountry().getName() + sNewLineChar;
/*      */       }
/*      */       else
/*      */       {
/* 1141 */         receiptAddress = receiptAddress + order.getUser().getAddress() + sNewLineChar;
/*      */       }
/*      */ 
/* 1144 */       params.put("purchaseCommercialReceiptNote", receiptNote + receiptAddress);
/*      */     }
/*      */ 
/* 1147 */     getOrderEmailCommonParams(order, params, a_purchase);
/*      */ 
/* 1149 */     params.put("template", "purchase_confirmation");
/*      */     try
/*      */     {
/* 1153 */       this.m_emailManager.sendTemplatedEmail(params, order.getUser().getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1157 */       this.m_logger.debug("OrderManager:sendConfirmOrderPurchaseEmail: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendApprovedPayOnline(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1178 */     String ksMethodName = "sendApprovedPayOnline";
/*      */ 
/* 1180 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1182 */     HashMap params = new HashMap();
/* 1183 */     getOrderEmailCommonParams(order, params, null);
/* 1184 */     getCommercialOrderDetailParams(order, params);
/* 1185 */     params.put("template", "order_approved_pay_online");
/*      */     try
/*      */     {
/* 1189 */       this.m_emailManager.sendTemplatedEmail(params, order.getUser().getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1193 */       this.m_logger.debug("OrderManager:sendApprovedPayOnline: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendApprovedPayOffline(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1215 */     String ksMethodName = "sendApprovedPayOffline";
/*      */ 
/* 1217 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1219 */     HashMap params = new HashMap();
/* 1220 */     getOrderEmailCommonParams(order, params, null);
/* 1221 */     getCommercialOrderDetailParams(order, params);
/* 1222 */     params.put("template", "order_approved_pay_offline");
/*      */     try
/*      */     {
/* 1226 */       this.m_emailManager.sendTemplatedEmail(params, order.getUser().getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1230 */       this.m_logger.debug("OrderManager:sendApprovedPayOffline: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendOrderDeclined(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1252 */     String ksMethodName = "sendOrderDeclined";
/*      */ 
/* 1254 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1256 */     HashMap params = new HashMap();
/* 1257 */     getOrderEmailCommonParams(order, params, null);
/* 1258 */     getCommercialOrderDetailParams(order, params);
/* 1259 */     params.put("template", "order_declined");
/*      */     try
/*      */     {
/* 1263 */       this.m_emailManager.sendTemplatedEmail(params, order.getUser().getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1267 */       this.m_logger.debug("OrderManager:sendOrderDeclined: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendCommercialRequestSubmitted(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1289 */     String ksMethodName = "sendCommercialRequestSubmitted";
/*      */ 
/* 1291 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1293 */     HashMap params = new HashMap();
/* 1294 */     getOrderEmailCommonParams(order, params, null);
/* 1295 */     getCommercialOrderDetailParams(order, params);
/*      */ 
/* 1297 */     String offlinePaymentRequest = "";
/* 1298 */     if (order.isPrefersOfflinePayment())
/*      */     {
/* 1300 */       offlinePaymentRequest = "The user has requested to complete payment for the order offline.";
/*      */     }
/* 1302 */     params.put("OfflinePaymentRequest", offlinePaymentRequest);
/*      */ 
/* 1304 */     params.put("adminEmailAddresses", this.m_userManager.getAdminEmailAddressesForAlerts());
/* 1305 */     params.put("template", "admin_alert_commercial_order_submitted");
/*      */     try
/*      */     {
/* 1309 */       this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1313 */       this.m_logger.debug("OrderManager:sendCommercialRequestSubmitted: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendPersonalPrintRequestSubmitted(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1335 */     String ksMethodName = "sendPersonalPrintRequestSubmitted";
/*      */ 
/* 1337 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1339 */     HashMap params = new HashMap();
/* 1340 */     getOrderEmailCommonParams(order, params, null);
/* 1341 */     getPersonalOrderDetailParams(order, params, false);
/* 1342 */     params.put("adminEmailAddresses", this.m_userManager.getAdminEmailAddressesForAlerts());
/* 1343 */     params.put("template", "admin_alert_personal_print_order_submitted");
/*      */     try
/*      */     {
/* 1347 */       this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1351 */       this.m_logger.debug("OrderManager:sendPersonalPrintRequestSubmitted: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sendCommercialOptionChangedNotification(DBTransaction a_dbTransaction, CommercialOptionPurchase a_oldCommOptPurchase, long a_lOrderId, long a_lAssetId, long a_lPriceBandId)
/*      */     throws Bn2Exception
/*      */   {
/* 1379 */     String ksMethodName = "sendCommercialOptionChangedNotification";
/*      */ 
/* 1381 */     String sSpacingChar = " ";
/* 1382 */     String sNewLineChar = "<br />";
/*      */ 
/* 1385 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/*      */ 
/* 1388 */     AssetInOrder asset = null;
/* 1389 */     Iterator it = order.getAssets().iterator(); while ((it.hasNext()) && ((asset = (AssetInOrder)it.next()).getAssetId() != a_lAssetId));
/* 1392 */     AssetPurchasePriceBand priceBand = null;
/* 1393 */     it = asset.getPriceBands().iterator(); while ((it.hasNext()) && ((priceBand = (AssetPurchasePriceBand)it.next()).getPriceBand().getId() != a_lPriceBandId));
/* 1395 */     HashMap params = new HashMap();
/* 1396 */     getOrderEmailCommonParams(order, params, null);
/*      */ 
/* 1399 */     String assetDetails = "";
/*      */ 
/* 1402 */     assetDetails = assetDetails + "Asset:" + sSpacingChar + sSpacingChar + asset.getDescription() + sNewLineChar;
/* 1403 */     assetDetails = assetDetails + "Format:" + sSpacingChar + sSpacingChar + priceBand.getPriceBand().getName() + sNewLineChar;
/* 1404 */     params.put("assetDetails", assetDetails);
/*      */ 
/* 1406 */     if (a_oldCommOptPurchase != null)
/*      */     {
/* 1408 */       params.put("oldOption", a_oldCommOptPurchase.getCommercialOption().getName());
/*      */     }
/*      */ 
/* 1411 */     params.put("newOption", priceBand.getCommercialOptionPurchase().getCommercialOption().getName());
/*      */ 
/* 1413 */     params.put("template", "commercial_option_changed");
/*      */     try
/*      */     {
/* 1417 */       this.m_emailManager.sendTemplatedEmail(params, order.getUser().getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1421 */       this.m_logger.debug("OrderManager:sendCommercialOptionChangedNotification: The confirmation was not successfully sent to the user: " + order.getUser().getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void getOrderEmailCommonParams(Order a_order, HashMap a_Params, AssetPurchase a_purchase)
/*      */     throws Bn2Exception
/*      */   {
/* 1436 */     String sNewLineChar = "<br />";
/*      */ 
/* 1438 */     a_Params.put("email", a_order.getUser().getEmailAddress());
/* 1439 */     a_Params.put("username", a_order.getUser().getUsername());
/* 1440 */     a_Params.put("amount", a_order.getTotal().getDisplayAmountTextFormat());
/* 1441 */     a_Params.put("ordernum", a_order.getDisplayPurchaseId());
/* 1442 */     a_Params.put("datePlaced", new BrightDate(a_order.getDatePlaced()).getDisplayDate());
/* 1443 */     a_Params.put("greetname", a_order.getUser().getFullName());
/* 1444 */     a_Params.put("forename", a_order.getUser().getForename());
/*      */ 
/* 1447 */     if ((!UserSettings.getEncryptPasswords()) && (a_purchase != null) && (a_purchase.getHasRegistered()))
/*      */     {
/* 1449 */       a_Params.put("password", a_order.getUser().getPassword());
/*      */     }
/*      */     else
/*      */     {
/* 1453 */       a_Params.put("password", this.m_listManager.getListItem("snippet-password-hidden", a_order.getUser().getLanguage()).getBody());
/*      */     }
/*      */ 
/* 1457 */     String shipAddress = "";
/* 1458 */     shipAddress = shipAddress + a_order.getRecipientName() + sNewLineChar;
/* 1459 */     String address1 = a_order.getShippingAddress().getAddressLine1();
/* 1460 */     if (StringUtil.stringIsPopulated(address1))
/*      */     {
/* 1462 */       shipAddress = shipAddress + address1 + sNewLineChar;
/*      */     }
/* 1464 */     String address2 = a_order.getShippingAddress().getAddressLine2();
/* 1465 */     if (StringUtil.stringIsPopulated(address2))
/*      */     {
/* 1467 */       shipAddress = shipAddress + address2 + sNewLineChar;
/*      */     }
/* 1469 */     shipAddress = shipAddress + a_order.getShippingAddress().getTown() + sNewLineChar;
/* 1470 */     shipAddress = shipAddress + a_order.getShippingAddress().getCounty() + sNewLineChar;
/* 1471 */     shipAddress = shipAddress + a_order.getShippingAddress().getPostcode() + sNewLineChar;
/* 1472 */     shipAddress = shipAddress + a_order.getShippingAddress().getCountry().getName() + sNewLineChar;
/*      */ 
/* 1474 */     a_Params.put("address", shipAddress);
/*      */   }
/*      */ 
/*      */   private void getPersonalOrderDetailParams(Order a_order, HashMap a_Params, boolean a_bOnlyPrints)
/*      */   {
/* 1487 */     String orderDetails = "";
/*      */ 
/* 1489 */     String sSpacingChar = " ";
/* 1490 */     String sNewLineChar = "<br />";
               Iterator priceBandIt;
/*      */ 
/* 1493 */     for (Iterator assetIt = a_order.getAssets().iterator(); assetIt.hasNext(); )
/*      */     {
/* 1495 */
                 AssetInOrder asset = (AssetInOrder)assetIt.next();
/* 1496 */       for (priceBandIt = asset.getPriceBands().iterator(); priceBandIt.hasNext(); )
/*      */       {
/* 1498 */         AssetPurchasePriceBand priceBand = (AssetPurchasePriceBand)priceBandIt.next();
/* 1499 */         if ((!a_bOnlyPrints) || (priceBand.getPriceBandType().getId() == 2L))
/*      */         {
/* 1502 */           orderDetails = orderDetails + "Asset:" + sSpacingChar + sSpacingChar + asset.getDescription() + sNewLineChar;
/* 1503 */           if (AssetBankSettings.getUsePriceBands())
/*      */           {
/* 1505 */             orderDetails = orderDetails + "Format:" + sSpacingChar + sSpacingChar + priceBand.getPriceBand().getName() + " " + priceBand.getPriceBandType().getName() + sNewLineChar;
/* 1506 */             orderDetails = orderDetails + "Quantity:" + sSpacingChar + sSpacingChar + priceBand.getQuantity() + sNewLineChar + sNewLineChar;
/*      */           }
/*      */           else
/*      */           {
/* 1510 */             orderDetails = orderDetails + "Price:" + sSpacingChar + sSpacingChar + asset.getPrice().getDisplayAmountTextFormat() + sNewLineChar + sNewLineChar;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */    // AssetInOrder asset;
/*      */     //Iterator priceBandIt;
/* 1516 */     if (a_order.getHasPrints())
/*      */     {
/* 1518 */       orderDetails = orderDetails + "Asset Cost:" + sSpacingChar + sSpacingChar + a_order.getBasketCost().getDisplayAmountTextFormat() + sNewLineChar;
/* 1519 */       orderDetails = orderDetails + "Shipping Cost:" + sSpacingChar + sSpacingChar + a_order.getShippingCost().getDisplayAmountTextFormat() + sNewLineChar;
/*      */     }
/* 1521 */     orderDetails = orderDetails + "Sub Total:" + sSpacingChar + sSpacingChar + a_order.getSubtotal().getDisplayAmountTextFormat() + sNewLineChar;
/*      */ 
/* 1523 */     if (a_order.getVatPercent().getNumber() > 0.0F)
/*      */     {
/* 1525 */       orderDetails = orderDetails + "VAT:" + sSpacingChar + sSpacingChar + a_order.getVatPercent().getDisplayNumber() + "%" + sNewLineChar;
/*      */     }
/*      */ 
/* 1528 */     if (a_order.getDiscountPercentage() > 0)
/*      */     {
/* 1530 */       orderDetails = orderDetails + "Discount:" + sSpacingChar + sSpacingChar + a_order.getDiscountPercentage() + "%" + sNewLineChar;
/*      */     }
/*      */ 
/* 1533 */     orderDetails = orderDetails + "Total:" + sSpacingChar + sSpacingChar + a_order.getTotal().getDisplayAmountTextFormat() + sNewLineChar;
/*      */ 
/* 1535 */     a_Params.put("orderDetails", orderDetails);
/*      */   }
/*      */ 
/*      */   private void getCommercialOrderDetailParams(Order a_order, HashMap a_Params)
/*      */   {
/* 1549 */     String orderDetails = "";
/* 1550 */     String sSpacingChar = " ";
/* 1551 */     String sNewLineChar = "<br />";
/*      */ 
/* 1553 */     String sTermsAndConditions = "Terms and Condition of Commercial Use" + sNewLineChar + sNewLineChar;
/*      */     Iterator priceBandIt;
               AssetInOrder asset;
/* 1555 */     for (Iterator assetIt = a_order.getAssets().iterator(); assetIt.hasNext(); )
/*      */     {
/* 1557 */       asset = (AssetInOrder)assetIt.next();
/* 1558 */       for (priceBandIt = asset.getPriceBands().iterator(); priceBandIt.hasNext(); )
/*      */       {
/* 1560 */         AssetPurchasePriceBand priceBand = (AssetPurchasePriceBand)priceBandIt.next();
/* 1561 */         orderDetails = orderDetails + "Asset:" + sSpacingChar + sSpacingChar + asset.getDescription() + sNewLineChar;
/* 1562 */         orderDetails = orderDetails + "Format:" + sSpacingChar + sSpacingChar + priceBand.getPriceBand().getName() + sNewLineChar;
/*      */ 
/* 1564 */         if (priceBand.getCommercialOptionPurchase().getCommercialOption().getName() != null)
/*      */         {
/* 1566 */           orderDetails = orderDetails + "Commercial Option:" + sSpacingChar + sSpacingChar + priceBand.getCommercialOptionPurchase().getCommercialOption().getName() + sNewLineChar;
/* 1567 */           orderDetails = orderDetails + "Description:" + sNewLineChar + "" + sSpacingChar + sSpacingChar + priceBand.getCommercialOptionPurchase().getFormDescription() + sNewLineChar + sNewLineChar;
/*      */         }
/*      */ 
/* 1570 */         orderDetails = orderDetails + "Price:" + sSpacingChar + sSpacingChar + priceBand.getCommercialOptionPurchase().getPrice().getDisplayAmountTextFormat() + sNewLineChar;
/* 1571 */         sTermsAndConditions = sTermsAndConditions + "Asset:" + sSpacingChar + sSpacingChar + asset.getDescription() + sNewLineChar;
/* 1572 */         sTermsAndConditions = sTermsAndConditions + "Format:" + sSpacingChar + sSpacingChar + priceBand.getPriceBand().getName() + sNewLineChar;
/* 1573 */         sTermsAndConditions = sTermsAndConditions + priceBand.getCommercialOptionPurchase().getCommercialOption().getTerms() + sNewLineChar + sNewLineChar;
/*      */       }
/*      */     }
/*      */    
/*      */     //Iterator priceBandIt;
/* 1578 */     orderDetails = orderDetails + "Sub Total:" + sSpacingChar + sSpacingChar + a_order.getSubtotal().getDisplayAmountTextFormat() + sNewLineChar;
/*      */ 
/* 1580 */     if (a_order.getVatPercent().getNumber() > 0.0F)
/*      */     {
/* 1582 */       orderDetails = orderDetails + "VAT:" + sSpacingChar + sSpacingChar + a_order.getVatPercent().getDisplayNumber() + "%" + sNewLineChar;
/*      */     }
/*      */ 
/* 1585 */     if (a_order.getDiscountPercentage() > 0)
/*      */     {
/* 1587 */       orderDetails = orderDetails + "Discount:" + sSpacingChar + sSpacingChar + a_order.getDiscountPercentage() + "%" + sNewLineChar;
/*      */     }
/*      */ 
/* 1590 */     orderDetails = orderDetails + "Total:" + sSpacingChar + sSpacingChar + a_order.getTotal().getDisplayAmountTextFormat() + sNewLineChar;
/*      */ 
/* 1592 */     a_Params.put("orderDetails", orderDetails);
/*      */ 
/* 1594 */     a_Params.put("UserNotes", a_order.getUserNotes());
/*      */ 
/* 1598 */     a_Params.put("terms", sTermsAndConditions);
/*      */   }
/*      */ 
/*      */   public void deleteOrdersForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1620 */     String ksMethodName = "deleteOrdersForUser";
/*      */ 
/* 1622 */     Connection con = null;
/* 1623 */     String sSql = null;
/* 1624 */     String sSql2 = null;
/* 1625 */     String sSql3 = null;
/* 1626 */     String sSql4 = null;
/* 1627 */     PreparedStatement psql = null;
/* 1628 */     PreparedStatement psql2 = null;
/* 1629 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1632 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1634 */       sSql = "SELECT * FROM ABOrder WHERE UserId=?";
/* 1635 */       psql = con.prepareStatement(sSql);
/* 1636 */       psql.setLong(1, a_lUserId);
/* 1637 */       rs = psql.executeQuery();
/*      */ 
/* 1640 */       sSql2 = "DELETE FROM CommercialOptionPurchase WHERE ABOrderId=?";
/* 1641 */       sSql3 = "DELETE FROM AssetPurchasePriceBand WHERE ABOrderId=?";
/* 1642 */       sSql4 = "DELETE FROM AssetPurchaseLog WHERE ABOrderId=?";
/* 1643 */       while (rs.next())
/*      */       {
/* 1647 */         psql2 = con.prepareStatement(sSql2);
/* 1648 */         psql2.setLong(1, rs.getLong("Id"));
/* 1649 */         psql2.executeUpdate();
/* 1650 */         psql2.close();
/*      */ 
/* 1652 */         psql2 = con.prepareStatement(sSql3);
/* 1653 */         psql2.setLong(1, rs.getLong("Id"));
/* 1654 */         psql2.executeUpdate();
/* 1655 */         psql2.close();
/*      */ 
/* 1657 */         psql2 = con.prepareStatement(sSql4);
/* 1658 */         psql2.setLong(1, rs.getLong("Id"));
/* 1659 */         psql2.executeUpdate();
/* 1660 */         psql2.close();
/*      */       }
/* 1662 */       psql.close();
/*      */ 
/* 1665 */       sSql = "DELETE FROM ABOrder WHERE UserId=?";
/* 1666 */       psql = con.prepareStatement(sSql);
/* 1667 */       psql.setLong(1, a_lUserId);
/* 1668 */       psql.executeUpdate();
/* 1669 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1674 */       throw new Bn2Exception("OrderManager.deleteOrdersForUser: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long logOrder(DBTransaction a_dbTransaction, AssetPurchase a_purchase)
/*      */     throws Bn2Exception
/*      */   {
/* 1700 */     String ksMethodName = "logOrder";
/*      */ 
/* 1702 */     Connection con = null;
/* 1703 */     PreparedStatement psql = null;
/* 1704 */     String sSQL = null;
/*      */ 
/* 1706 */     long lOrderId = 0L;
/*      */     try
/*      */     {
/* 1710 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1713 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1715 */       int iCol = 1;
/*      */ 
/* 1718 */       int iStatus = 1;
/* 1719 */       long lShippingAddressId = 0L;
/* 1720 */       long lShippingCost = 0L;
/* 1721 */       String sRecipient = "";
/*      */ 
/* 1723 */       if ((AssetBankSettings.getUsePriceBands()) && ((a_purchase instanceof AssetWithShippingPurchase)))
/*      */       {
/* 1725 */         AssetWithShippingPurchase awsp = (AssetWithShippingPurchase)a_purchase;
/*      */ 
/* 1728 */         if (awsp.getHasPrints())
/*      */         {
/* 1730 */           iStatus = 2;
/*      */         }
/*      */ 
/* 1734 */         if (awsp.getShippingAddress().getCountry().getId() > 0L)
/*      */         {
/* 1736 */           this.m_addressManager.saveAddress(a_dbTransaction, awsp.getShippingAddress());
/*      */         }
/* 1738 */         lShippingAddressId = awsp.getShippingAddress().getId();
/*      */ 
/* 1740 */         lShippingCost = awsp.getShippingCost().getAmount();
/*      */ 
/* 1742 */         sRecipient = awsp.getRecipient();
/*      */       }
/*      */ 
/* 1748 */       sSQL = "INSERT INTO ABOrder (";
/*      */ 
/* 1750 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1752 */         lOrderId = sqlGenerator.getUniqueId(con, "ABOrderSequence");
/* 1753 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/* 1756 */       sSQL = sSQL + "UserId, DatePlaced, SubTotal, VATPercent, Total, PSPTransId, PurchaseId, OrderStatusId, ShippingAddressId, RecipientName, UserNotes, PrefersOfflinePayment,BasketCost,ShippingCost,DiscountPercentage,PSPUserId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
/*      */ 
/* 1759 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1761 */         sSQL = sSQL + ",?";
/*      */       }
/*      */ 
/* 1764 */       sSQL = sSQL + ")";
/*      */ 
/* 1766 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1768 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1770 */         psql.setLong(iCol++, lOrderId);
/*      */       }
/*      */ 
/* 1773 */       psql.setLong(iCol++, a_purchase.getUserId());
/* 1774 */       psql.setTimestamp(iCol++, new Timestamp(System.currentTimeMillis()));
/* 1775 */       psql.setLong(iCol++, a_purchase.getSubtotal().getAmount());
/* 1776 */       psql.setFloat(iCol++, a_purchase.getVatPercent().getNumber());
/* 1777 */       psql.setLong(iCol++, a_purchase.getChargedAmount().getAmount());
/* 1778 */       psql.setString(iCol++, a_purchase.getPaymentReturn().getPspTransactionId());
/* 1779 */       psql.setString(iCol++, a_purchase.getPurchaseId());
/* 1780 */       psql.setLong(iCol++, iStatus);
/* 1781 */       DBUtil.setFieldIdOrNull(psql, iCol++, lShippingAddressId);
/* 1782 */       psql.setString(iCol++, sRecipient);
/* 1783 */       psql.setString(iCol++, a_purchase.getUserNotes());
/* 1784 */       psql.setBoolean(iCol++, a_purchase.isPrefersOfflinePayment());
/* 1785 */       psql.setLong(iCol++, a_purchase.getBasketCost().getAmount());
/* 1786 */       psql.setLong(iCol++, lShippingCost);
/* 1787 */       psql.setInt(iCol++, a_purchase.getDiscountPercentage());
/* 1788 */       psql.setString(iCol++, a_purchase.getPaymentReturn().getPspUserId());
/*      */ 
/* 1790 */       psql.executeUpdate();
/* 1791 */       psql.close();
/*      */ 
/* 1794 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1796 */         lOrderId = sqlGenerator.getUniqueId(con, "ABOrder");
/*      */       }
/*      */ 
/* 1800 */       sSQL = "INSERT INTO AssetPurchaseLog (ABOrderId, AssetId, Description, Price, ShippingCost) VALUES (?,?,?,?,?)";
/*      */ 
/* 1803 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1805 */       for (int i = 0; i < a_purchase.getAssetList().size(); i++)
/*      */       {
/* 1807 */         AssetInOrder asset = (AssetInOrder)a_purchase.getAssetList().get(i);
/*      */ 
/* 1809 */         iCol = 1;
/* 1810 */         psql.setLong(iCol++, lOrderId);
/* 1811 */         psql.setLong(iCol++, asset.getAssetId());
/* 1812 */         psql.setString(iCol++, asset.getDescription());
/* 1813 */         psql.setLong(iCol++, asset.getPrice().getAmount());
/* 1814 */         psql.setLong(iCol++, asset.getShippingCost().getAmount());
/*      */ 
/* 1817 */         psql.executeUpdate();
/*      */       }
/*      */ 
/* 1820 */       psql.close();
/*      */ 
/* 1823 */       if (AssetBankSettings.getUsePriceBands())
/*      */       {
/* 1825 */         sSQL = "INSERT INTO AssetPurchasePriceBand (PriceBandId, ABOrderId, AssetId, Quantity, PriceBandTypeId, PriceBandName, PriceBandTypeName, Cost, ShippingCost) VALUES (?,?,?,?,?,?,?,?,?)";
/*      */ 
/* 1830 */         for (int i = 0; i < a_purchase.getAssetList().size(); i++)
/*      */         {
/* 1832 */           AssetInOrder asset = (AssetInOrder)a_purchase.getAssetList().get(i);
/* 1833 */           Vector vecPriceBands = asset.getPriceBands();
/*      */ 
/* 1835 */           for (int j = 0; j < vecPriceBands.size(); j++)
/*      */           {
/* 1837 */             AssetPurchasePriceBand appb = (AssetPurchasePriceBand)vecPriceBands.get(j);
/*      */ 
/* 1839 */             psql = con.prepareStatement(sSQL);
/*      */ 
/* 1841 */             iCol = 1;
/* 1842 */             psql.setLong(iCol++, appb.getPriceBand().getId());
/* 1843 */             psql.setLong(iCol++, lOrderId);
/* 1844 */             psql.setLong(iCol++, asset.getAssetId());
/* 1845 */             psql.setInt(iCol++, appb.getQuantity());
/* 1846 */             psql.setLong(iCol++, appb.getPriceBandType().getId());
/* 1847 */             psql.setString(iCol++, appb.getPriceBand().getName());
/* 1848 */             psql.setString(iCol++, appb.getPriceBandType().getName());
/* 1849 */             psql.setLong(iCol++, appb.getCost().getAmount());
/* 1850 */             psql.setLong(iCol++, appb.getShippingCost().getAmount());
/*      */ 
/* 1853 */             psql.executeUpdate();
/*      */ 
/* 1855 */             long lCommercialOptionId = appb.getCommercialOptionPurchase().getCommercialOption().getId();
/*      */ 
/* 1857 */             if ((!AssetBankSettings.getUseCommercialOptions()) || (!a_purchase.getHasCommercialUsage()))
/*      */               continue;
/* 1859 */             String sSQLCommOpt = "INSERT INTO CommercialOptionPurchase (PriceBandId, ABOrderId, AssetId, CommercialOptionId, Price, Description) VALUES (?,?,?,?,?,?)";
/*      */ 
/* 1863 */             iCol = 1;
/*      */ 
/* 1865 */             psql = con.prepareStatement(sSQLCommOpt);
/*      */ 
/* 1867 */             psql.setLong(iCol++, appb.getPriceBand().getId());
/* 1868 */             psql.setLong(iCol++, lOrderId);
/* 1869 */             psql.setLong(iCol++, asset.getAssetId());
/* 1870 */             DBUtil.setFieldIdOrNull(psql, iCol++, lCommercialOptionId);
/*      */ 
/* 1872 */             psql.setLong(iCol++, appb.getCommercialOptionPurchase().getPrice().getAmount());
/*      */ 
/* 1874 */             psql.setString(iCol++, appb.getCommercialOptionPurchase().getDescription());
/*      */ 
/* 1876 */             psql.executeUpdate();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1884 */         psql.close();
/*      */       }
/*      */ 
/* 1889 */       if (iStatus == 2)
/*      */       {
/* 1891 */         sendPersonalPrintRequestSubmitted(a_dbTransaction, lOrderId);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1897 */       this.m_logger.error("OrderManager.logOrder - " + sqe);
/* 1898 */       throw new Bn2Exception("OrderManager.logOrder", sqe);
/*      */     }
/*      */ 
/* 1901 */     return lOrderId;
/*      */   }
/*      */ 
/*      */   public HashMap getPriceBandUsagesForUserAssetPurchase(DBTransaction a_dbTransaction, long a_lUserId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1918 */     String ksMethodName = "getPriceBandUsagesForUserAssetPurchase";
/*      */ 
/* 1920 */     Connection con = null;
/* 1921 */     PreparedStatement psql = null;
/* 1922 */     String sSQL = null;
/* 1923 */     ResultSet rs = null;
/* 1924 */     HashMap hmResults = new HashMap();
/*      */     try
/*      */     {
/* 1928 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1930 */       sSQL = "SELECT  pbu.UsageTypeId FROM ABOrder ord INNER JOIN AssetPurchasePriceBand appb ON appb.ABOrderId = ord.Id INNER JOIN PriceBandUsage pbu ON pbu.PriceBandId = appb.PriceBandId WHERE appb.PriceBandTypeId = ? AND ord.UserId = ? AND appb.AssetId = ? AND ord.OrderStatusId IN " + getSQLForSuccessfulOrderSet();
/*      */ 
/* 1939 */       psql = con.prepareStatement(sSQL);
/* 1940 */       psql.setLong(1, 1L);
/* 1941 */       psql.setLong(2, a_lUserId);
/* 1942 */       psql.setLong(3, a_lAssetId);
/*      */ 
/* 1944 */       rs = psql.executeQuery();
/*      */ 
/* 1946 */       while (rs.next())
/*      */       {
/* 1948 */         long lUsageTypeId = rs.getLong("UsageTypeId");
/* 1949 */         hmResults.put(new Long(lUsageTypeId), new Long(lUsageTypeId));
/*      */       }
/*      */ 
/* 1952 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1956 */       this.m_logger.error("OrderManager.getPriceBandUsagesForUserAssetPurchase - " + e);
/* 1957 */       throw new Bn2Exception("OrderManager.getPriceBandUsagesForUserAssetPurchase", e);
/*      */     }
/*      */ 
/* 1960 */     return hmResults;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadOriginal(DBTransaction a_dbTransaction, long a_lUserId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1981 */     String ksMethodName = "getUserCanDownloadOriginal";
/*      */ 
/* 1983 */     Connection con = null;
/* 1984 */     PreparedStatement psql = null;
/* 1985 */     String sSQL = null;
/* 1986 */     ResultSet rs = null;
/* 1987 */     boolean bCanDownloadOriginal = false;
/*      */     try
/*      */     {
/* 1991 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1993 */       sSQL = "SELECT  MAX(pb.DownloadOriginal) maxDO FROM ABOrder ord INNER JOIN AssetPurchasePriceBand appb ON appb.ABOrderId = ord.Id INNER JOIN PriceBand pb ON pb.Id = appb.PriceBandId WHERE appb.PriceBandTypeId = ? AND ord.UserId = ? AND appb.AssetId = ? AND ord.OrderStatusId IN " + getSQLForSuccessfulOrderSet();
/*      */ 
/* 2002 */       psql = con.prepareStatement(sSQL);
/* 2003 */       psql.setLong(1, 1L);
/* 2004 */       psql.setLong(2, a_lUserId);
/* 2005 */       psql.setLong(3, a_lAssetId);
/*      */ 
/* 2007 */       rs = psql.executeQuery();
/*      */ 
/* 2009 */       if (rs.next())
/*      */       {
/* 2011 */         bCanDownloadOriginal = rs.getBoolean("maxDO");
/*      */       }
/*      */ 
/* 2014 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2018 */       this.m_logger.error("OrderManager.getUserCanDownloadOriginal - " + e);
/* 2019 */       throw new Bn2Exception("OrderManager.getUserCanDownloadOriginal", e);
/*      */     }
/*      */ 
/* 2022 */     return bCanDownloadOriginal;
/*      */   }
/*      */ 
/*      */   private String getSQLForSuccessfulOrderSet()
/*      */   {
/* 2038 */     String sSQL = "( 1,7,6 )";
/*      */ 
/* 2043 */     return sSQL;
/*      */   }
/*      */ 
/*      */   public Vector getOrderHistory(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 2068 */     String ksMethodName = "getOrderHistory";
/*      */ 
/* 2070 */     Connection con = null;
/* 2071 */     PreparedStatement psql = null;
/* 2072 */     String sSQL = null;
/* 2073 */     ResultSet rs = null;
/* 2074 */     Vector vecResults = null;
/*      */     try
/*      */     {
/* 2078 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2080 */       sSQL = "SELECT o.Id as orderid, o.DatePlaced, o.SubTotal, o.VATPercent, o.Total, o.PurchaseId, o.PSPTransId, o.DiscountPercentage, u.Forename, u.Surname, u.Username, apl.AssetId, apl.Description, apl.Price FROM ABOrder o, AssetBankUser u, AssetPurchaseLog apl WHERE o.UserId = u.Id AND o.Id = apl.ABOrderId ";
/*      */ 
/* 2086 */       if (a_dtStartDate != null)
/*      */       {
/* 2088 */         sSQL = sSQL + " AND o.DatePlaced >= ?";
/*      */       }
/*      */ 
/* 2091 */       if (a_dtEndDate != null)
/*      */       {
/* 2093 */         sSQL = sSQL + " AND o.DatePlaced <= ?";
/*      */       }
/*      */ 
/* 2096 */       if (a_lUserId > 0L)
/*      */       {
/* 2098 */         sSQL = sSQL + " AND u.Id = ?";
/*      */       }
/*      */ 
/* 2101 */       sSQL = sSQL + " ORDER BY o.Id";
/*      */ 
/* 2103 */       int iField = 1;
/* 2104 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2106 */       if (a_dtStartDate != null)
/*      */       {
/* 2108 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtStartDate);
/*      */       }
/*      */ 
/* 2111 */       if (a_dtEndDate != null)
/*      */       {
/* 2113 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtEndDate);
/*      */       }
/*      */ 
/* 2116 */       if (a_lUserId > 0L)
/*      */       {
/* 2118 */         psql.setLong(iField++, a_lUserId);
/*      */       }
/*      */ 
/* 2121 */       rs = psql.executeQuery();
/* 2122 */       Order order = null;
/* 2123 */       Vector vecAssets = null;
/* 2124 */       AssetInOrder assetInOrder = null;
/*      */ 
/* 2126 */       while (rs.next())
/*      */       {
/* 2128 */         if (vecResults == null)
/*      */         {
/* 2130 */           vecResults = new Vector();
/*      */         }
/*      */ 
/* 2134 */         if ((order == null) || (rs.getLong("orderid") != order.getId()))
/*      */         {
/* 2136 */           order = new Order();
/* 2137 */           order.setId(rs.getLong("orderid"));
/* 2138 */           order.setDatePlaced(rs.getDate("DatePlaced"));
/* 2139 */           order.getSubtotal().setAmount(rs.getLong("SubTotal"));
/* 2140 */           order.getVatPercent().setNumber(rs.getFloat("VATPercent"));
/* 2141 */           order.getTotal().setAmount(rs.getLong("Total"));
/* 2142 */           order.getUser().setForename(rs.getString("Forename"));
/* 2143 */           order.getUser().setSurname(rs.getString("Surname"));
/* 2144 */           order.getUser().setUsername(rs.getString("Username"));
/* 2145 */           order.setPurchaseId(rs.getString("PurchaseId"));
/* 2146 */           order.setPspTransId(rs.getString("PSPTransId"));
/* 2147 */           order.setDiscountPercentage(rs.getInt("DiscountPercentage"));
/*      */ 
/* 2150 */           vecResults.add(order);
/*      */ 
/* 2153 */           vecAssets = new Vector();
/* 2154 */           order.setAssets(vecAssets);
/*      */         }
/*      */ 
/* 2158 */         assetInOrder = new AssetInOrder();
/* 2159 */         assetInOrder.setAssetId(rs.getLong("AssetId"));
/* 2160 */         assetInOrder.setDescription(rs.getString("Description"));
/* 2161 */         assetInOrder.getPrice().setAmount(rs.getLong("Price"));
/*      */ 
/* 2163 */         vecAssets.add(assetInOrder);
/*      */       }
/*      */ 
/* 2167 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2171 */       this.m_logger.error("OrderManager.getOrderHistory - " + e);
/* 2172 */       throw new Bn2Exception("OrderManager.getOrderHistory", e);
/*      */     }
/*      */ 
/* 2175 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public Vector getUploadersReportLines(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 2196 */     String ksMethodName = "getUploadersReport";
/*      */ 
/* 2198 */     Connection con = null;
/* 2199 */     PreparedStatement psql = null;
/* 2200 */     String sSQL = null;
/* 2201 */     ResultSet rs = null;
/* 2202 */     Vector vecResults = null;
/*      */     try
/*      */     {
/* 2206 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2208 */       sSQL = "SELECT abu.Id, abu.Username, abu.Forename, abu.Surname, abu.EmailAddress, apl.AssetId, apl.Price FROM AssetPurchaseLog apl JOIN Asset a ON apl.AssetId=a.Id JOIN AssetBankUser abu ON a.AddedByUserId=abu.Id JOIN ABOrder o ON apl.ABOrderId=o.Id";
/*      */ 
/* 2214 */       if ((a_dtStartDate != null) || (a_dtEndDate != null))
/*      */       {
/* 2216 */         sSQL = sSQL + " WHERE 1=1";
/*      */ 
/* 2218 */         if (a_dtStartDate != null)
/*      */         {
/* 2220 */           sSQL = sSQL + " AND o.DatePlaced >= ?";
/*      */         }
/*      */ 
/* 2223 */         if (a_dtEndDate != null)
/*      */         {
/* 2225 */           sSQL = sSQL + " AND o.DatePlaced <= ?";
/*      */         }
/*      */       }
/*      */ 
/* 2229 */       sSQL = sSQL + " ORDER BY abu.Id";
/*      */ 
/* 2231 */       psql = con.prepareStatement(sSQL);
/* 2232 */       int iField = 1;
/* 2233 */       if (a_dtStartDate != null)
/*      */       {
/* 2235 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtStartDate);
/*      */       }
/*      */ 
/* 2238 */       if (a_dtEndDate != null)
/*      */       {
/* 2240 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtEndDate);
/*      */       }
/*      */ 
/* 2243 */       rs = psql.executeQuery();
/* 2244 */       long lLastUserId = 0L;
/* 2245 */       UploadersReportRecord record = null;
/*      */ 
/* 2247 */       while (rs.next())
/*      */       {
/* 2249 */         if (vecResults == null)
/*      */         {
/* 2251 */           vecResults = new Vector();
/*      */         }
/*      */ 
/* 2254 */         if (lLastUserId != rs.getLong("Id"))
/*      */         {
/* 2256 */           if (record != null)
/*      */           {
/* 2258 */             vecResults.add(record);
/*      */           }
/*      */ 
/* 2261 */           record = new UploadersReportRecord();
/* 2262 */           record.setUserId(rs.getLong("Id"));
/* 2263 */           record.setForename(rs.getString("Forename"));
/* 2264 */           record.setSurname(rs.getString("Surname"));
/* 2265 */           record.setUsername(rs.getString("Username"));
/* 2266 */           record.setEmailAddress(rs.getString("EmailAddress"));
/*      */ 
/* 2268 */           record.setAssetsSold(1);
/*      */ 
/* 2270 */           BrightMoney amount = new BrightMoney(rs.getLong("Price"));
/* 2271 */           record.setTotalIncome(amount);
/*      */ 
/* 2273 */           lLastUserId = rs.getLong("Id");
/* 2274 */           continue;
/*      */         }
/*      */ 
/* 2277 */         record.setAssetsSold(record.getAssetsSold() + 1);
/* 2278 */         BrightMoney amount = record.getTotalIncome();
/* 2279 */         amount.setAmount(amount.getAmount() + rs.getLong("Price"));
/* 2280 */         record.setTotalIncome(amount);
/*      */       }
/*      */ 
/* 2286 */       if (record != null)
/*      */       {
/* 2288 */         vecResults.add(record);
/*      */       }
/*      */ 
/* 2291 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2295 */       this.m_logger.error("OrderManager.getUploadersReport - " + e);
/* 2296 */       throw new Bn2Exception("OrderManager.getUploadersReport", e);
/*      */     }
/*      */ 
/* 2299 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public Vector getSubscriptionReportLines(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, boolean a_bActiveSubOnly, boolean a_bActiveSubModelsOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 2318 */     String ksMethodName = "getSubscriptionReportLines";
/*      */ 
/* 2320 */     Connection con = null;
/* 2321 */     PreparedStatement psql = null;
/* 2322 */     String sSQL = null;
/* 2323 */     ResultSet rs = null;
/* 2324 */     ResultSet rsUser = null;
/* 2325 */     Vector vecResults = null;
/* 2326 */     Vector vecUserResults = null;
/*      */     try
/*      */     {
/* 2330 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2332 */       sSQL = "SELECT ssm.Id, ssm.Description, count(UserId) as numUsers, sum(PricePaid) as totalPrice FROM SubscriptionModel ssm JOIN Subscription s ON ssm.Id=s.SubScriptionModelId WHERE 1=1 ";
/*      */ 
/* 2336 */       if (a_bActiveSubModelsOnly)
/*      */       {
/* 2338 */         sSQL = sSQL + "AND ssm.Inactive=0 ";
/*      */       }
/*      */ 
/* 2341 */       if (a_bActiveSubOnly)
/*      */       {
/* 2343 */         sSQL = sSQL + "AND s.IsActive=1 ";
/*      */       }
/* 2345 */       if (a_dtStartDate != null)
/*      */       {
/* 2347 */         sSQL = sSQL + "AND s.StartDate >= ? ";
/*      */       }
/* 2349 */       if (a_dtEndDate != null)
/*      */       {
/* 2351 */         sSQL = sSQL + "AND s.StartDate <= ? ";
/*      */       }
/*      */ 
/* 2355 */       sSQL = sSQL + "GROUP BY ssm.Id";
/*      */ 
/* 2358 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2360 */       int iField = 1;
/*      */ 
/* 2363 */       if (a_dtStartDate != null)
/*      */       {
/* 2365 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtStartDate);
/*      */       }
/*      */ 
/* 2368 */       if (a_dtEndDate != null)
/*      */       {
/* 2370 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtEndDate);
/*      */       }
/*      */ 
/* 2374 */       rs = psql.executeQuery();
/* 2375 */       SubscriptionReportRecord record = null;
/*      */ 
/* 2377 */       while (rs.next())
/*      */       {
/* 2379 */         if (vecResults == null)
/*      */         {
/* 2381 */           vecResults = new Vector();
/*      */         }
/*      */ 
/* 2384 */         record = new SubscriptionReportRecord();
/*      */ 
/* 2386 */         record.setSubscriptionId(rs.getLong("Id"));
/* 2387 */         record.setSubscriptionName(rs.getString("Description"));
/* 2388 */         record.setNumSubscribers(rs.getLong("numUsers"));
/*      */ 
/* 2390 */         BrightMoney amount = new BrightMoney(rs.getLong("totalPrice"));
/* 2391 */         record.setTotalCost(amount);
/*      */ 
/* 2395 */         vecUserResults = null;
/*      */ 
/* 2397 */         UserSubscriptionReportRecord userRecord = null;
/*      */ 
/* 2399 */         sSQL = "SELECT StartDate, abu.EmailAddress, IsActive, PricePaid FROM Subscription s JOIN AssetBankUser abu ON s.UserId=abu.Id WHERE SubscriptionModelId = ? ";
/*      */ 
/* 2404 */         if (a_bActiveSubOnly)
/*      */         {
/* 2406 */           sSQL = sSQL + "AND s.IsActive=1 ";
/*      */         }
/* 2408 */         if (a_dtStartDate != null)
/*      */         {
/* 2410 */           sSQL = sSQL + "AND s.StartDate >= ? ";
/*      */         }
/* 2412 */         if (a_dtEndDate != null)
/*      */         {
/* 2414 */           sSQL = sSQL + "AND s.StartDate <= ? ";
/*      */         }
/*      */ 
/* 2417 */         psql = con.prepareStatement(sSQL);
/* 2418 */         iField = 1;
/*      */ 
/* 2420 */         psql.setLong(iField++, record.getSubscriptionId());
/*      */ 
/* 2422 */         if (a_dtStartDate != null)
/*      */         {
/* 2424 */           DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtStartDate);
/*      */         }
/*      */ 
/* 2427 */         if (a_dtEndDate != null)
/*      */         {
/* 2429 */           DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtEndDate);
/*      */         }
/*      */ 
/* 2432 */         rsUser = psql.executeQuery();
/*      */ 
/* 2434 */         while (rsUser.next())
/*      */         {
/* 2436 */           if (vecUserResults == null)
/*      */           {
/* 2438 */             vecUserResults = new Vector();
/*      */           }
/*      */ 
/* 2441 */           userRecord = new UserSubscriptionReportRecord();
/* 2442 */           userRecord.setStartDate(rsUser.getDate("StartDate"));
/* 2443 */           userRecord.setEmail(rsUser.getString("EmailAddress"));
/* 2444 */           userRecord.setActive(rsUser.getBoolean("IsActive"));
/* 2445 */           amount = new BrightMoney(rsUser.getLong("PricePaid"));
/* 2446 */           userRecord.setPricePaid(amount);
/*      */ 
/* 2448 */           vecUserResults.add(userRecord);
/*      */         }
/*      */ 
/* 2451 */         record.setUserSubscriptionReports(vecUserResults);
/*      */ 
/* 2453 */         if (record != null)
/*      */         {
/* 2455 */           vecResults.add(record);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2461 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2465 */       this.m_logger.error("OrderManager.getSubscriptionReportLines - " + e);
/* 2466 */       throw new Bn2Exception("OrderManager.getSubscriptionReportLines", e);
/*      */     }
/*      */ 
/* 2469 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public Vector getUserSubscriptionReportLines(DBTransaction a_dbTransaction, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/* 2486 */     String ksMethodName = "getUserSubscriptionReportLines";
/*      */ 
/* 2488 */     Connection con = null;
/* 2489 */     PreparedStatement psql = null;
/* 2490 */     String sSQL = null;
/* 2491 */     ResultSet rs = null;
/* 2492 */     Vector vecResults = null;
/*      */     try
/*      */     {
/* 2496 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2498 */       sSQL = "SELECT sm.Description, StartDate, IsActive, PricePaid FROM Subscription s JOIN AssetBankUser abu ON abu.Id=s.UserId JOIN SubscriptionModel sm ON sm.Id=s.SubscriptionModelId WHERE abu.UserName = ? ORDER BY StartDate DESC";
/*      */ 
/* 2504 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2506 */       int iField = 1;
/*      */ 
/* 2508 */       psql.setString(iField++, a_sUsername);
/*      */ 
/* 2510 */       rs = psql.executeQuery();
/* 2511 */       UserSubscriptionReportRecord userRecord = null;
/*      */ 
/* 2513 */       while (rs.next())
/*      */       {
/* 2515 */         if (vecResults == null)
/*      */         {
/* 2517 */           vecResults = new Vector();
/*      */         }
/*      */ 
/* 2520 */         userRecord = new UserSubscriptionReportRecord();
/*      */ 
/* 2522 */         userRecord.setSubscriptionModel(rs.getString("Description"));
/* 2523 */         userRecord.setStartDate(rs.getDate("StartDate"));
/* 2524 */         userRecord.setActive(rs.getBoolean("IsActive"));
/*      */ 
/* 2526 */         BrightMoney amount = new BrightMoney(rs.getLong("PricePaid"));
/* 2527 */         userRecord.setPricePaid(amount);
/*      */ 
/* 2529 */         if (userRecord != null)
/*      */         {
/* 2531 */           vecResults.add(userRecord);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2537 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2541 */       this.m_logger.error("OrderManager.getUserSubscriptionReportLines - " + e);
/* 2542 */       throw new Bn2Exception("OrderManager.getUserSubscriptionReportLines", e);
/*      */     }
/*      */ 
/* 2545 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public void approveOrderAssetsForUser(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 2564 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/* 2565 */     long lUserId = order.getUser().getId();
/*      */ 
/* 2567 */     Vector items = order.getAssets();
/* 2568 */     for (int i = 0; i < items.size(); i++)
/*      */     {
/* 2570 */       AssetInOrder aio = (AssetInOrder)items.get(i);
/* 2571 */       long lAssetId = aio.getAssetId();
/*      */ 
/* 2573 */       if (AssetBankSettings.getUsePriceBands())
/*      */       {
/* 2576 */         Vector vecPriceBands = aio.getPriceBands();
/* 2577 */         for (int j = 0; j < vecPriceBands.size(); j++)
/*      */         {
/* 2579 */           AssetPurchasePriceBand appb = (AssetPurchasePriceBand)vecPriceBands.get(j);
/*      */ 
/* 2581 */           if (appb.getPriceBandType().getId() != 1L)
/*      */             continue;
/* 2583 */           this.m_approvalManager.approveAssetForUser(a_dbTransaction, lAssetId, lUserId);
/* 2584 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2591 */         this.m_approvalManager.approveAssetForUser(a_dbTransaction, lAssetId, lUserId);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rejectOrderAssetsForUser(DBTransaction a_dbTransaction, long a_lOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 2616 */     Order order = getOrder(a_dbTransaction, a_lOrderId);
/* 2617 */     long lUserId = order.getUser().getId();
/*      */ 
/* 2619 */     Vector items = order.getAssets();
/* 2620 */     for (int i = 0; i < items.size(); i++)
/*      */     {
/* 2622 */       AssetInOrder aio = (AssetInOrder)items.get(i);
/* 2623 */       long lAssetId = aio.getAssetId();
/*      */ 
/* 2625 */       if (AssetBankSettings.getUsePriceBands())
/*      */       {
/* 2628 */         Vector vecPriceBands = aio.getPriceBands();
/* 2629 */         for (int j = 0; j < vecPriceBands.size(); j++)
/*      */         {
/* 2631 */           AssetPurchasePriceBand appb = (AssetPurchasePriceBand)vecPriceBands.get(j);
/*      */ 
/* 2633 */           if (appb.getPriceBandType().getId() != 1L)
/*      */             continue;
/* 2635 */           this.m_approvalManager.setAssetApprovalRejectedForUser(a_dbTransaction, lAssetId, lUserId);
/* 2636 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2643 */         this.m_approvalManager.setAssetApprovalRejectedForUser(a_dbTransaction, lAssetId, lUserId);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void registerPaymentSuccess(DBTransaction a_dbTransaction, Purchase a_purchase, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 2670 */     OrderPaymentPurchase purchase = (OrderPaymentPurchase)a_purchase;
/*      */ 
/* 2673 */     Order order = purchase.getOrder();
/* 2674 */     long lOrderId = order.getId();
/* 2675 */     approveOrderAssetsForUser(a_dbTransaction, lOrderId);
/*      */ 
/* 2678 */     updateOrderStatus(a_dbTransaction, lOrderId, 6L);
/*      */ 
/* 2681 */     sendConfirmOrderPurchaseEmail(a_dbTransaction, lOrderId, a_language, null);
/*      */   }
/*      */ 
/*      */   public Vector getSuccessfulCommercialOrders(DBTransaction a_dbTransaction, long a_lAssetId, long a_llUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 2712 */     String ksMethodName = "getSuccessfulCommercialOrders";
/*      */ 
/* 2714 */     Connection con = null;
/* 2715 */     PreparedStatement psql = null;
/* 2716 */     String sSQL = null;
/* 2717 */     ResultSet rs = null;
/* 2718 */     Vector vecOrders = null;
/*      */     try
/*      */     {
/* 2722 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2724 */       sSQL = "SELECT ord.Id as orderid, ord.PurchaseId, ord.DatePlaced, ord.Total, ord.DiscountPercentage FROM ABOrder ord INNER JOIN AssetBankUser usr ON ord.UserId = usr.Id INNER JOIN OrderStatus orderstatus ON ord.OrderStatusId = orderstatus.Id INNER JOIN OrderWorkflow orderworkflow ON orderstatus.OrderWorkflowId = orderworkflow.Id INNER JOIN AssetPurchaseLog abpur ON ord.Id = abpur.ABOrderId INNER JOIN AssetPurchasePriceBand abpurpb ON abpurpb.ABOrderId = abpur.ABOrderId AND abpurpb.AssetId = abpur.AssetId INNER JOIN CommercialOptionPurchase commoptpur ON commoptpur.PriceBandId = abpurpb.PriceBandId AND commoptpur.ABOrderId = abpurpb.ABOrderId AND commoptpur.AssetId = abpurpb.AssetId INNER JOIN CommercialOption commopt ON commopt.Id = commoptpur.CommercialOptionId WHERE abpur.AssetId = ? AND usr.Id = ? AND ord.OrderStatusId IN " + getSQLForSuccessfulOrderSet() + " ORDER BY ord.DatePlaced DESC";
/*      */ 
/* 2745 */       int iField = 1;
/* 2746 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2748 */       psql.setLong(iField++, a_lAssetId);
/* 2749 */       psql.setLong(iField++, a_llUserId);
/*      */ 
/* 2751 */       rs = psql.executeQuery();
/*      */ 
/* 2753 */       while (rs.next())
/*      */       {
/* 2755 */         if (vecOrders == null)
/*      */         {
/* 2757 */           vecOrders = new Vector();
/*      */         }
/*      */ 
/* 2760 */         Order order = new Order();
/* 2761 */         order.setId(rs.getLong("orderid"));
/* 2762 */         order.setDatePlaced(rs.getDate("DatePlaced"));
/* 2763 */         order.getTotal().setAmount(rs.getLong("Total"));
/* 2764 */         order.setPurchaseId(rs.getString("PurchaseId"));
/* 2765 */         order.setDiscountPercentage(rs.getInt("DiscountPercentage"));
/*      */ 
/* 2767 */         vecOrders.add(order);
/*      */       }
/*      */ 
/* 2770 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2774 */       this.m_logger.error("OrderManager.getSuccessfulCommercialOrders - " + e);
/* 2775 */       throw new Bn2Exception("OrderManager.getSuccessfulCommercialOrders", e);
/*      */     }
/*      */ 
/* 2778 */     return vecOrders;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.service.OrderManager
 * JD-Core Version:    0.6.0
 */