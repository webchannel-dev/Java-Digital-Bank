/*     */ package com.bright.assetbank.subscription.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModelUpgrade;
/*     */ import com.bright.assetbank.subscription.form.SubscriptionModelsForm;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.user.action.UserAction;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveSubscriptionModelAction extends UserAction
/*     */ {
/*  54 */   private SubscriptionManager m_subscriptionManager = null;
/*     */ 
/*  60 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager)
/*     */   {
/*  57 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  63 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     ActionForward afForward = null;
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  89 */     SubscriptionModelsForm form = (SubscriptionModelsForm)a_form;
/*     */ 
/*  92 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  94 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  95 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  99 */     Vector vecGroupIds = getGroupIds(a_request);
/* 100 */     form.setGroupIds(vecGroupIds);
/*     */ 
/* 103 */     populateSelectedUpgrades(a_request, form);
/*     */ 
/* 106 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 108 */     String sQueryString = "";
/* 109 */     if (!form.getHasErrors())
/*     */     {
/* 112 */       form.getModel().setGroups(vecGroupIds);
/*     */ 
/* 115 */       Vector vecSelectedUpgrades = createUpgradeList(form);
/* 116 */       form.getModel().setUpgrades(vecSelectedUpgrades);
/*     */ 
/* 119 */       this.m_subscriptionManager.saveSubscriptionModel(a_dbTransaction, form.getModel());
/*     */ 
/* 122 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 126 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 129 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void populateSelectedUpgrades(HttpServletRequest a_request, SubscriptionModelsForm a_form)
/*     */   {
/* 147 */     HashMap hmUpgradeSelected = new HashMap();
/* 148 */     HashMap hmUpgradePrices = new HashMap();
/*     */ 
/* 151 */     Enumeration e = a_request.getParameterNames();
/* 152 */     String sName = null;
/*     */ 
/* 154 */     while (e.hasMoreElements())
/*     */     {
/* 156 */       sName = (String)e.nextElement();
/*     */ 
/* 159 */       if (!sName.startsWith("upgradecheck")) {
/*     */         continue;
/*     */       }
/* 162 */       long lId = getLongParameter(a_request, sName);
/*     */ 
/* 164 */       if (lId > 0L)
/*     */       {
/* 166 */         hmUpgradeSelected.put(new Long(lId), new Boolean(true));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 172 */     e = a_request.getParameterNames();
/* 173 */     String sPriceField = null;
/*     */ 
/* 175 */     while (e.hasMoreElements())
/*     */     {
/* 177 */       sPriceField = (String)e.nextElement();
/*     */ 
/* 180 */       if (!sPriceField.startsWith("upgradeprice")) {
/*     */         continue;
/*     */       }
/* 183 */       int iPos = sPriceField.indexOf('_');
/* 184 */       String sId = sPriceField.substring(iPos + 1);
/* 185 */       long lUpgradeId = 0L;
/* 186 */       if (StringUtil.stringIsPopulated(sId))
/*     */       {
/* 188 */         lUpgradeId = new Long(sId).longValue();
/*     */       }
/*     */ 
/* 192 */       String sPriceValue = a_request.getParameter(sPriceField);
/*     */ 
/* 194 */       if (StringUtil.stringIsPopulated(sPriceValue))
/*     */       {
/* 196 */         hmUpgradePrices.put(new Long(lUpgradeId), sPriceValue);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 201 */     a_form.setUpgradeSelected(hmUpgradeSelected);
/* 202 */     a_form.setUpgradePrices(hmUpgradePrices);
/*     */   }
/*     */ 
/*     */   public Vector createUpgradeList(SubscriptionModelsForm a_form)
/*     */   {
/* 220 */     Vector vecUpgradeList = new Vector();
/*     */ 
/* 222 */     Iterator it = a_form.getUpgradeSelected().keySet().iterator();
/* 223 */     while (it.hasNext())
/*     */     {
/* 225 */       Long olId = (Long)it.next();
/*     */ 
/* 227 */       if (((Boolean)a_form.getUpgradeSelected().get(olId)).booleanValue())
/*     */       {
/* 229 */         String sPrice = (String)a_form.getUpgradePrices().get(olId);
/* 230 */         SubscriptionModelUpgrade upgrade = new SubscriptionModelUpgrade();
/* 231 */         upgrade.setFromModelId(a_form.getModel().getId());
/* 232 */         upgrade.setToModelId(olId.longValue());
/* 233 */         upgrade.getPrice().setFormAmount(sPrice);
/* 234 */         upgrade.getPrice().processFormData();
/*     */ 
/* 236 */         vecUpgradeList.add(upgrade);
/*     */       }
/*     */     }
/*     */ 
/* 240 */     return vecUpgradeList;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.SaveSubscriptionModelAction
 * JD-Core Version:    0.6.0
 */