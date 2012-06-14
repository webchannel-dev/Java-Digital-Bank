/*     */ package com.bright.assetbank.plugin.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.CategoryDiskUsageExtension;
/*     */ import com.bright.assetbank.application.service.UploadAssetExtension;
/*     */ import com.bright.assetbank.batchrel.service.DependencyProvider;
/*     */ import com.bright.assetbank.category.service.BrowseAssetsPaneller;
/*     */ import com.bright.assetbank.clientsideedit.service.ClientSideEditingManager;
/*     */ import com.bright.assetbank.clientsideedit.service.EditorDependenciesProvider;
/*     */ import com.bright.assetbank.plugin.bean.ABExtensibleBean;
/*     */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*     */ import com.bright.assetbank.plugin.iface.ABExtension;
/*     */ import com.bright.assetbank.plugin.iface.ABModelMod;
/*     */ import com.bright.assetbank.plugin.iface.ABPlugin;
/*     */ import com.bright.assetbank.plugin.iface.ABViewMod;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.struts.Bn2ExtensibleForm;
/*     */ import com.bright.framework.util.ClassUtil;
/*     */ import com.bright.framework.util.Pair;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.collections.Factory;
/*     */ import org.apache.commons.collections.MapUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class PluginManager extends Bn2Manager
/*     */   implements PluginSPI
/*     */ {
/*     */   private AssetManager m_assetManager;
/*     */   private AssetCategoryManager m_categoryManager;
/*     */   private ClientSideEditingManager m_clientSideEditingManager;
/*  66 */   private Map<String, List<ABExtension>> m_extensionsByEntity = MapUtils.lazyMap(new HashMap(), new Factory()
/*     */   {
/*     */     public Object create()
/*     */     {
/*  71 */       return new ArrayList();
/*     */     }
/*     */   });
/*     */   private BrowseAssetsPaneller m_paneller;
/*     */ 
/*     */   public void startup() throws Bn2Exception
/*     */   {
/*  80 */     super.startup();
/*     */ 
/*  83 */     List<ABPlugin> plugins = new ArrayList();
/*  84 */     String sPluginClassNames = AssetBankSettings.getPluginClasses();
/*  85 */     for (String sPluginClassName : StringUtil.unpack(sPluginClassNames))
/*     */     {
/*  87 */       if (sPluginClassName.equals(""))
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/*  92 */       ABPlugin plugin = (ABPlugin)ClassUtil.newInstance(sPluginClassName);
/*  93 */       plugins.add(plugin);
/*     */     }
/*     */ 
/*  98 */     for (ABPlugin plugin : plugins)
/*     */     {
/* 100 */       this.m_logger.info("Starting plugin " + plugin.getClass().getName());
/* 101 */       plugin.startup(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getExtensionData(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject)
/*     */     throws Bn2Exception
/*     */   {
/* 119 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 121 */       ABModelMod modelMod = extension.getModelMod();
/* 122 */       if (modelMod != null)
/*     */       {
/* 124 */         Serializable extensionData = modelMod.get(a_transaction, a_coreObject);
/* 125 */         a_coreObject.setExtensionData(extension.getUniqueKey(), extensionData);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getExtensionDatas(DBTransaction a_transaction, String a_sExtensibleEntity, List<? extends ABExtensibleBean> a_coreObjects)
/*     */     throws Bn2Exception
/*     */   {
/* 141 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 143 */       ABModelMod modelMod = extension.getModelMod();
/* 144 */       if (modelMod != null)
/*     */       {
/* 146 */         List extensionDatas = modelMod.get(a_transaction, a_coreObjects);
/* 147 */         Iterator itCoreObjects = a_coreObjects.iterator();
/* 148 */         Iterator itExtensionDatas = extensionDatas.iterator();
/* 149 */         while (itCoreObjects.hasNext())
/*     */         {
/* 151 */           ABExtensibleBean coreObject = (ABExtensibleBean)itCoreObjects.next();
/*     */ 
/* 155 */           Serializable extensionData = (Serializable)itExtensionDatas.next();
/*     */ 
/* 157 */           coreObject.setExtensionData(extension.getUniqueKey(), extensionData);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void populateViewRequest(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 178 */     for (Pair pair : getViewModsWithCorrespondingExtensions(a_sExtensibleEntity))
/*     */     {
/* 180 */       ABExtension extension = (ABExtension)pair.getA();
/* 181 */       ABViewMod viewMod = (ABViewMod)pair.getB();
/* 182 */       Serializable extensionData = a_coreObject.getExtensionData(extension.getUniqueKey());
/*     */ 
/* 184 */       viewMod.populateViewRequest(a_transaction, a_request, a_coreObject, extensionData);
/*     */     }
/*     */ 
/* 187 */     a_request.setAttribute("plugin_populateViewRequestCalled", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public List<String> getIncludesForView(String a_sExtensibleEntity, String a_sPosition)
/*     */   {
/* 200 */     List includes = new ArrayList();
/*     */ 
/* 202 */     for (ABViewMod viewMod : getViewMods(a_sExtensibleEntity))
/*     */     {
/* 204 */       String include = viewMod.getInclude(a_sPosition);
/* 205 */       if (include != null)
/*     */       {
/* 210 */         if (!include.startsWith("/"))
/*     */         {
/* 212 */           throw new IllegalStateException("Include paths returned by ABViewMod must be context-relative, i.e. start with \"/\", but the path \"" + include + "\" does not");
/*     */         }
/*     */ 
/* 216 */         includes.add(include);
/*     */       }
/*     */     }
/*     */ 
/* 220 */     return includes;
/*     */   }
/*     */ 
/*     */   private List<ABViewMod> getViewMods(String a_sExtensibleEntity)
/*     */   {
/* 225 */     List result = new ArrayList();
/*     */ 
/* 227 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 229 */       ABViewMod viewMod = extension.getViewMod();
/* 230 */       if (viewMod != null)
/*     */       {
/* 232 */         result.add(viewMod);
/*     */       }
/*     */     }
/*     */ 
/* 236 */     return result;
/*     */   }
/*     */ 
/*     */   private List<Pair<ABExtension, ABViewMod>> getViewModsWithCorrespondingExtensions(String a_sExtensibleEntity)
/*     */   {
/* 241 */     List result = new ArrayList();
/*     */ 
/* 243 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 245 */       ABViewMod viewMod = extension.getViewMod();
/* 246 */       if (viewMod != null)
/*     */       {
/* 248 */         result.add(new Pair(extension, viewMod));
/*     */       }
/*     */     }
/*     */ 
/* 252 */     return result;
/*     */   }
/*     */ 
/*     */   public void populateAddForm(DBTransaction a_transaction, String a_sExtensibleEntity, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 265 */     populateForm(a_transaction, null, a_request, a_form, getAddModsWithCorrespondingExtensions(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   public void populateEditExistingForm(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 279 */     populateForm(a_transaction, a_coreObject, a_request, a_form, getEditExistingModsWithCorrespondingExtensions(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   private void populateForm(DBTransaction a_transaction, ABExtensibleBean a_coreObject, HttpServletRequest a_request, Bn2ExtensibleForm a_form, List<Pair<ABExtension, ABEditMod>> a_editMods)
/*     */     throws Bn2Exception
/*     */   {
/* 289 */     for (Pair p : a_editMods)
/*     */     {
/* 291 */       ABExtension extension = (ABExtension)p.getA();
/* 292 */       ABEditMod editMod = (ABEditMod)p.getB();
/*     */ 
/* 294 */       Serializable extensionData = a_coreObject == null ? null : a_coreObject.getExtensionData(extension.getUniqueKey());
/*     */ 
/* 299 */       editMod.populateForm(a_transaction, a_coreObject, extensionData, a_request, a_form);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void populateViewAddRequest(DBTransaction a_transaction, String a_sExtensibleEntity, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 324 */     populateViewEditRequest(a_transaction, a_request, a_form, getAddMods(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   public void populateViewEditExistingRequest(DBTransaction a_transaction, String a_sExtensibleEntity, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 348 */     populateViewEditRequest(a_transaction, a_request, a_form, getEditExistingMods(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   private void populateViewEditRequest(DBTransaction a_transaction, HttpServletRequest a_request, Bn2ExtensibleForm a_form, List<ABEditMod> a_editMods)
/*     */     throws Bn2Exception
/*     */   {
/* 358 */     for (ABEditMod e : a_editMods)
/*     */     {
/* 360 */       e.populateViewEditRequest(a_transaction, a_request, a_form);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<String> getIncludesForAdd(String a_sExtensibleEntity, String a_sPosition)
/*     */   {
/* 375 */     return getIncludesForEdit(a_sPosition, getAddMods(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   public List<String> getIncludesForEditExisting(String a_sExtensibleEntity, String a_sPosition)
/*     */   {
/* 389 */     return getIncludesForEdit(a_sPosition, getEditExistingMods(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   private List<String> getIncludesForEdit(String a_sPosition, List<ABEditMod> a_editMods)
/*     */   {
/* 397 */     List includes = new ArrayList();
/*     */ 
/* 399 */     for (ABEditMod e : a_editMods)
/*     */     {
/* 401 */       String include = e.getInclude(a_sPosition);
/* 402 */       if (include != null)
/*     */       {
/* 407 */         if (!include.startsWith("/"))
/*     */         {
/* 409 */           throw new IllegalStateException("Include paths returned by ABEditMod must be context-relative, i.e. start with \"/\", but the path \"" + include + "\" does not");
/*     */         }
/*     */ 
/* 413 */         includes.add(include);
/*     */       }
/*     */     }
/*     */ 
/* 417 */     return includes;
/*     */   }
/*     */ 
/*     */   public void validateAdd(DBTransaction a_transaction, ABUserProfile a_userProfile, String a_sExtensibleEntity, Object a_oCoreObject, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 434 */     validate(a_transaction, a_userProfile, a_oCoreObject, a_form, getAddMods(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   public void validateEdit(DBTransaction a_transaction, ABUserProfile a_userProfile, String a_sExtensibleEntity, Object a_oCoreObject, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 450 */     validate(a_transaction, a_userProfile, a_oCoreObject, a_form, getEditExistingMods(a_sExtensibleEntity));
/*     */   }
/*     */ 
/*     */   private void validate(DBTransaction a_transaction, ABUserProfile a_userProfile, Object a_oCoreObject, Bn2ExtensibleForm a_form, List<ABEditMod> a_editMods)
/*     */     throws Bn2Exception
/*     */   {
/* 459 */     if (a_transaction == null)
/*     */     {
/* 461 */       throw new NullPointerException();
/*     */     }
/*     */ 
/* 464 */     for (ABEditMod e : a_editMods)
/*     */     {
/* 466 */       e.validate(a_transaction, a_userProfile, a_oCoreObject, a_form);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void extractAddDataFromForm(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 481 */     if (a_transaction == null)
/*     */     {
/* 483 */       throw new NullPointerException();
/*     */     }
/*     */ 
/* 486 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 488 */       ABEditMod addMod = extension.getAddMod();
/* 489 */       if (addMod != null)
/*     */       {
/* 491 */         a_coreObject.setExtensionData(extension.getUniqueKey(), addMod.extractDataFromForm(a_transaction, a_coreObject, a_form));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void extractEditDataFromForm(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 508 */     if (a_transaction == null)
/*     */     {
/* 510 */       throw new NullPointerException();
/*     */     }
/*     */ 
/* 513 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 515 */       ABEditMod editExistingMod = extension.getEditExistingMod();
/* 516 */       if (editExistingMod != null)
/*     */       {
/* 518 */         a_coreObject.setExtensionData(extension.getUniqueKey(), editExistingMod.extractDataFromForm(a_transaction, a_coreObject, a_form));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addExtensionData(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject)
/*     */     throws Bn2Exception
/*     */   {
/* 543 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 545 */       ABModelMod modelMod = extension.getModelMod();
/* 546 */       if (modelMod != null)
/*     */       {
/* 548 */         Object extensionData = a_coreObject.getExtensionData(extension.getUniqueKey());
/* 549 */         modelMod.add(a_transaction, a_coreObject, extensionData);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void editExistingExtensionData(DBTransaction a_transaction, String a_sExtensibleEntity, ABExtensibleBean a_coreObject)
/*     */     throws Bn2Exception
/*     */   {
/* 570 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 572 */       ABModelMod modelMod = extension.getModelMod();
/* 573 */       if (modelMod != null)
/*     */       {
/* 575 */         Object extensionData = a_coreObject.getExtensionData(extension.getUniqueKey());
/* 576 */         modelMod.editExisting(a_transaction, a_coreObject, extensionData);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private List<ABEditMod> getAddMods(String a_sExtensibleEntity)
/*     */   {
/* 584 */     List result = new ArrayList();
/*     */ 
/* 586 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 588 */       ABEditMod addMod = extension.getAddMod();
/* 589 */       if (addMod != null)
/*     */       {
/* 591 */         result.add(addMod);
/*     */       }
/*     */     }
/*     */ 
/* 595 */     return result;
/*     */   }
/*     */ 
/*     */   private List<Pair<ABExtension, ABEditMod>> getAddModsWithCorrespondingExtensions(String a_sExtensibleEntity)
/*     */   {
/* 600 */     List result = new ArrayList();
/*     */ 
/* 602 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 604 */       ABEditMod addMod = extension.getAddMod();
/* 605 */       if (addMod != null)
/*     */       {
/* 607 */         result.add(new Pair(extension, addMod));
/*     */       }
/*     */     }
/*     */ 
/* 611 */     return result;
/*     */   }
/*     */ 
/*     */   private List<ABEditMod> getEditExistingMods(String a_sExtensibleEntity)
/*     */   {
/* 616 */     List result = new ArrayList();
/*     */ 
/* 618 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 620 */       ABEditMod editExistingMod = extension.getEditExistingMod();
/* 621 */       if (editExistingMod != null)
/*     */       {
/* 623 */         result.add(editExistingMod);
/*     */       }
/*     */     }
/*     */ 
/* 627 */     return result;
/*     */   }
/*     */ 
/*     */   private List<Pair<ABExtension, ABEditMod>> getEditExistingModsWithCorrespondingExtensions(String a_sExtensibleEntity)
/*     */   {
/* 632 */     List result = new ArrayList();
/*     */ 
/* 634 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 636 */       ABEditMod editExistingMod = extension.getEditExistingMod();
/* 637 */       if (editExistingMod != null)
/*     */       {
/* 639 */         result.add(new Pair(extension, editExistingMod));
/*     */       }
/*     */     }
/*     */ 
/* 643 */     return result;
/*     */   }
/*     */ 
/*     */   public void delete(DBTransaction a_transaction, String a_sExtensibleEntity, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 658 */     for (ABExtension extension : (List<ABExtension>)this.m_extensionsByEntity.get(a_sExtensibleEntity))
/*     */     {
/* 660 */       ABModelMod modelMod = extension.getModelMod();
/* 661 */       if (modelMod != null)
/*     */       {
/* 663 */         modelMod.delete(a_transaction, a_lId);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public BrowseAssetsPaneller getBrowseAssetsPaneller()
/*     */   {
/* 673 */     return this.m_paneller;
/*     */   }
/*     */ 
/*     */   public void addExtension(String a_sExtensibleEntity, ABExtension a_extension)
/*     */   {
/* 682 */     ((List)this.m_extensionsByEntity.get(a_sExtensibleEntity)).add(a_extension);
/*     */   }
/*     */ 
/*     */   public void addDependencyProvider(DependencyProvider a_dependencyProvider)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addEditorDependencyProvider(EditorDependenciesProvider a_editorDependenciesProvider)
/*     */   {
/* 693 */     getClientSideEditingManager().addEditorDependencyProvider(a_editorDependenciesProvider);
/*     */   }
/*     */ 
/*     */   public void addCategoryDiskUsageExtension(CategoryDiskUsageExtension a_categoryDiskUsageExtension)
/*     */   {
/* 698 */     getCategoryManager().addCategoryDiskUsageExtension(a_categoryDiskUsageExtension);
/*     */   }
/*     */ 
/*     */   public void setBrowseAssetsPaneller(BrowseAssetsPaneller a_paneller)
/*     */   {
/* 706 */     if (this.m_paneller != null)
/*     */     {
/* 708 */       throw new IllegalStateException("Only one BrowseAssetsPaneller can be active at once.  Remove one of the plugins that contains an AssetPaneller from the plugin-classes setting.");
/*     */     }
/* 710 */     this.m_paneller = a_paneller;
/*     */   }
/*     */ 
/*     */   public void addUploadAssetExtension(UploadAssetExtension a_uploadAssetExtension)
/*     */   {
/* 715 */     getAssetManager().addUploadAssetExtension(a_uploadAssetExtension);
/*     */   }
/*     */ 
/*     */   public AssetManager getAssetManager()
/*     */   {
/* 724 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 729 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public AssetCategoryManager getCategoryManager()
/*     */   {
/* 734 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/* 739 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   private ClientSideEditingManager getClientSideEditingManager()
/*     */   {
/* 744 */     return this.m_clientSideEditingManager;
/*     */   }
/*     */ 
/*     */   public void setClientSideEditingManager(ClientSideEditingManager a_clientSideEditingManager)
/*     */   {
/* 749 */     this.m_clientSideEditingManager = a_clientSideEditingManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.service.PluginManager
 * JD-Core Version:    0.6.0
 */