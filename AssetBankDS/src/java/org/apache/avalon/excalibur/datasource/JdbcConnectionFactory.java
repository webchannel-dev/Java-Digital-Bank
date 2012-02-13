/*     */ package org.apache.avalon.excalibur.datasource;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import org.apache.avalon.excalibur.pool.ObjectFactory;
/*     */ import org.apache.avalon.framework.activity.Disposable;
/*     */ import org.apache.avalon.framework.container.ContainerUtil;
/*     */ import org.apache.avalon.framework.logger.AbstractLogEnabled;
/*     */ import org.apache.avalon.framework.logger.LogEnabled;
/*     */ import org.apache.avalon.framework.logger.Logger;
/*     */ 
/*     */ public class JdbcConnectionFactory extends AbstractLogEnabled
/*     */   implements ObjectFactory
/*     */ {
/*     */   private final String m_dburl;
/*     */   private Properties m_properties;
/*     */   private final boolean m_autoCommit;
/*     */   private final String m_keepAlive;
/*     */   private final int m_keepAliveAge;
/*     */   private final String m_connectionClass;
/*     */   private Class m_class;
/*     */   private static final String DEFAULT_KEEPALIVE = "SELECT 1";
/*     */   private static final String ORACLE_KEEPALIVE = "SELECT 1 FROM DUAL";
/*     */   private Connection m_firstConnection;
/*     */ 
/*     */   /** @deprecated */
/*     */   public JdbcConnectionFactory(String url, String username, String password, boolean autoCommit, boolean oradb)
/*     */   {
/*  66 */     this(url, username, password, autoCommit, oradb, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public JdbcConnectionFactory(String url, String username, String password, boolean autoCommit, boolean oradb, String connectionClass)
/*     */   {
/*  80 */     this(url, username, password, autoCommit, oradb ? "SELECT 1 FROM DUAL" : "SELECT 1", connectionClass);
/*     */   }
/*     */ 
/*     */   public JdbcConnectionFactory(String url, String username, String password, boolean autoCommit, String keepAlive, String connectionClass)
/*     */   {
/* 103 */     this(url, username, password, autoCommit, keepAlive, 5000, connectionClass);
/*     */   }
/*     */ 
/*     */   public JdbcConnectionFactory(String url, String username, String password, boolean autoCommit, String keepAlive, int keepAliveAge, String connectionClass) //throws Bn2Exception
/*     */   {
/* 130 */     this.m_dburl = url;
/* 131 */     this.m_properties = new Properties();
/* 132 */     this.m_autoCommit = autoCommit;
/* 133 */     this.m_keepAlive = keepAlive;
/* 134 */     this.m_keepAliveAge = keepAliveAge;
/* 135 */     this.m_connectionClass = connectionClass;
/*     */ 
/* 137 */     if (username != null)
/*     */     {
/* 139 */       this.m_properties.put("user", username);
/*     */     }
/* 141 */     if (password != null)
/*     */     {
/* 143 */       this.m_properties.put("password", password);
/*     */     }
/*     */     try
/*     */     {
/* 147 */       SQLGenerator.getInstance().setupConnectionProperties(this.m_properties);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 151 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 157 */       this.m_firstConnection = getConnection();
/*     */ 
/* 159 */       init();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void init()
/*     */     throws Exception
/*     */   {
/* 172 */     String className = this.m_connectionClass;
/* 173 */     if (null == className)
/*     */     {
/* 175 */       this.m_class = AbstractJdbcConnection.class;
/*     */     }
/*     */     else
/*     */     {
/* 179 */       this.m_class = Thread.currentThread().getContextClassLoader().loadClass(className);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object newInstance() throws Exception
/*     */   {
/* 185 */     Connection jdbcConnection = null;
/* 186 */     Connection connection = this.m_firstConnection;
/*     */ 
/* 188 */     if (null == connection)
/*     */     {
/* 190 */       connection = getConnection();
/*     */     }
/*     */     else
/*     */     {
/* 194 */       this.m_firstConnection = null;
/*     */     }
/*     */ 
/* 197 */     if (null == this.m_class)
/*     */     {
/*     */       try
/*     */       {
/* 201 */         init();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 205 */         if (getLogger().isDebugEnabled())
/*     */         {
/* 207 */           getLogger().debug("Exception in JdbcConnectionFactory.newInstance:", e);
/*     */         }
/* 209 */         throw new NoValidConnectionException("No valid JdbcConnection class available");
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 215 */       jdbcConnection = getProxy(connection, this.m_keepAlive, this.m_keepAliveAge);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 219 */       if (getLogger().isDebugEnabled())
/*     */       {
/* 221 */         getLogger().debug("Exception in JdbcConnectionFactory.newInstance:", e);
/*     */       }
/*     */ 
/* 224 */       throw new NoValidConnectionException(e.getMessage());
/*     */     }
/*     */ 
/* 227 */     ContainerUtil.enableLogging(jdbcConnection, getLogger().getChildLogger("conn"));
/*     */ 
/* 230 */     if (jdbcConnection.getAutoCommit() != this.m_autoCommit)
/*     */     {
/* 232 */       jdbcConnection.setAutoCommit(this.m_autoCommit);
/*     */     }
/*     */ 
/* 235 */     if (getLogger().isDebugEnabled())
/*     */     {
/* 237 */       getLogger().debug("JdbcConnection object created");
/*     */     }
/*     */ 
/* 240 */     return jdbcConnection;
/*     */   }
/*     */ 
/*     */   private Connection getConnection()
/*     */     throws SQLException
/*     */   {
/* 246 */     return DriverManager.getConnection(this.m_dburl, this.m_properties);
/*     */   }
/*     */ 
/*     */   public Class getCreatedClass()
/*     */   {
/* 251 */     return this.m_class;
/*     */   }
/*     */ 
/*     */   public void decommission(Object object) throws Exception
/*     */   {
/* 256 */     if ((object instanceof Disposable))
/*     */     {
/* 258 */       ((Disposable)object).dispose();
/*     */ 
/* 260 */       if (getLogger().isDebugEnabled())
/*     */       {
/* 262 */         getLogger().debug("JdbcConnection object disposed");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Connection getProxy(Connection conn, String keepAlive, int keepAliveAge)
/*     */   {
/* 269 */     ProxiedJdbcConnection handler = null;
/*     */     try
/*     */     {
/* 273 */       Constructor builder = this.m_class.getConstructor(new Class[] { Connection.class, String.class, Integer.TYPE });
/*     */ 
/* 276 */       handler = (ProxiedJdbcConnection)builder.newInstance(new Object[] { conn, keepAlive, new Integer(keepAliveAge) });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 281 */       String msg = "Could not create the proper invocation handler, defaulting to AbstractJdbcConnection";
/*     */ 
/* 283 */       getLogger().error("Could not create the proper invocation handler, defaulting to AbstractJdbcConnection", e);
/* 284 */       handler = new AbstractJdbcConnection(conn, keepAlive, keepAliveAge);
/*     */     }
/*     */ 
/* 287 */     Connection connection = (Connection)Proxy.newProxyInstance(this.m_class.getClassLoader(), new Class[] { Connection.class, LogEnabled.class, PoolSettable.class, Disposable.class }, handler);
/*     */ 
/* 295 */     handler.setProxiedConnection(connection);
/*     */ 
/* 297 */     return connection;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.avalon.excalibur.datasource.JdbcConnectionFactory
 * JD-Core Version:    0.6.0
 */