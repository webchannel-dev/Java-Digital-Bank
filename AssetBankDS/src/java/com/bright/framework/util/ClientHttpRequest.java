/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ClientHttpRequest
/*     */ {
/*     */   URLConnection connection;
/*  42 */   OutputStream os = null;
/*  43 */   Map cookies = new HashMap();
/*     */ 
/*  70 */   private static Random random = new Random();
/*     */ 
/*  76 */   String boundary = "---------------------------" + randomString() + randomString() + randomString();
/*     */ 
/*     */   protected void connect()
/*     */     throws IOException
/*     */   {
/*  46 */     if (this.os == null) this.os = this.connection.getOutputStream(); 
/*     */   }
/*     */ 
/*     */   protected void write(char c) throws IOException
/*     */   {
/*  50 */     connect();
/*  51 */     this.os.write(c);
/*     */   }
/*     */ 
/*     */   protected void write(String s) throws IOException {
/*  55 */     connect();
/*  56 */     this.os.write(s.getBytes());
/*     */   }
/*     */ 
/*     */   protected void newline() throws IOException {
/*  60 */     connect();
/*  61 */     write("\r\n");
/*     */   }
/*     */ 
/*     */   protected void writeln(String s) throws IOException {
/*  65 */     connect();
/*  66 */     write(s);
/*  67 */     newline();
/*     */   }
/*     */ 
/*     */   protected static String randomString()
/*     */   {
/*  73 */     return Long.toString(random.nextLong(), 36);
/*     */   }
/*     */ 
/*     */   private void boundary()
/*     */     throws IOException
/*     */   {
/*  79 */     write("--");
/*  80 */     write(this.boundary);
/*     */   }
/*     */ 
/*     */   public ClientHttpRequest(URLConnection connection)
/*     */   {
/*  90 */     this.connection = connection;
/*  91 */     connection.setDoOutput(true);
/*  92 */     connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
/*     */   }
/*     */ 
/*     */   public ClientHttpRequest(URL url)
/*     */     throws IOException
/*     */   {
/* 103 */     this(url.openConnection());
/*     */   }
/*     */ 
/*     */   public ClientHttpRequest(String urlString)
/*     */     throws IOException
/*     */   {
/* 113 */     this(new URL(urlString));
/*     */   }
/*     */ 
/*     */   public void setCookie(String name, String value)
/*     */   {
/* 124 */     this.cookies.put(name, value);
/*     */   }
/*     */ 
/*     */   public void setCookies(Map cookies)
/*     */   {
/* 133 */     if (cookies == null) return;
/* 134 */     this.cookies.putAll(cookies);
/*     */   }
/*     */ 
/*     */   public void setCookies(String[] cookies)
/*     */   {
/* 143 */     if (cookies == null) return;
/* 144 */     for (int i = 0; i < cookies.length - 1; i += 2)
/* 145 */       setCookie(cookies[i], cookies[(i + 1)]);
/*     */   }
/*     */ 
/*     */   private void writeName(String name) throws IOException
/*     */   {
/* 150 */     newline();
/* 151 */     write("Content-Disposition: form-data; name=\"");
/* 152 */     write(name);
/* 153 */     write('"');
/*     */   }
/*     */ 
/*     */   public void setParameter(String name, String value)
/*     */     throws IOException
/*     */   {
/* 163 */     boundary();
/* 164 */     writeName(name);
/* 165 */     newline(); newline();
/* 166 */     writeln(value);
/*     */   }
/*     */ 
/*     */   private static void pipe(InputStream in, OutputStream out) throws IOException {
/* 170 */     byte[] buf = new byte[500000];
/*     */ 
/* 172 */     int total = 0;
/* 173 */     synchronized (in)
/*     */     {
/*     */       int nread;
/* 174 */       while ((nread = in.read(buf, 0, buf.length)) >= 0) {
/* 175 */         out.write(buf, 0, nread);
/* 176 */         total += nread;
/*     */       }
/*     */     }
/* 179 */     out.flush();
/* 180 */     buf = null;
/*     */   }
/*     */ 
/*     */   public void setParameter(String name, String filename, InputStream is)
/*     */     throws IOException
/*     */   {
/* 191 */     boundary();
/* 192 */     writeName(name);
/* 193 */     write("; filename=\"");
/* 194 */     write(filename);
/* 195 */     write('"');
/* 196 */     newline();
/* 197 */     write("Content-Type: ");
/* 198 */     String type = URLConnection.guessContentTypeFromName(filename);
/* 199 */     if (type == null) type = "application/octet-stream";
/* 200 */     writeln(type);
/* 201 */     newline();
/* 202 */     pipe(is, this.os);
/* 203 */     newline();
/*     */   }
/*     */ 
/*     */   public void setParameter(String name, File file)
/*     */     throws IOException
/*     */   {
/* 213 */     setParameter(name, file.getPath(), new FileInputStream(file));
/*     */   }
/*     */ 
/*     */   public void setParameter(String name, Object object)
/*     */     throws IOException
/*     */   {
/* 223 */     if ((object instanceof File))
/* 224 */       setParameter(name, (File)object);
/*     */     else
/* 226 */       setParameter(name, object.toString());
/*     */   }
/*     */ 
/*     */   public void setParameters(Map parameters)
/*     */     throws IOException
/*     */   {
/* 236 */     if (parameters == null) return;
/* 237 */     for (Iterator i = parameters.entrySet().iterator(); i.hasNext(); ) {
/* 238 */       Map.Entry entry = (Map.Entry)i.next();
/* 239 */       setParameter(entry.getKey().toString(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setParameters(Object[] parameters)
/*     */     throws IOException
/*     */   {
/* 249 */     if (parameters == null) return;
/* 250 */     for (int i = 0; i < parameters.length - 1; i += 2)
/* 251 */       setParameter(parameters[i].toString(), parameters[(i + 1)]);
/*     */   }
/*     */ 
/*     */   public InputStream post()
/*     */     throws IOException
/*     */   {
/* 261 */     boundary();
/* 262 */     writeln("--");
/* 263 */     this.os.close();
/* 264 */     return this.connection.getInputStream();
/*     */   }
/*     */ 
/*     */   public InputStream post(Map parameters)
/*     */     throws IOException
/*     */   {
/* 275 */     setParameters(parameters);
/* 276 */     return post();
/*     */   }
/*     */ 
/*     */   public InputStream post(Object[] parameters)
/*     */     throws IOException
/*     */   {
/* 287 */     setParameters(parameters);
/* 288 */     return post();
/*     */   }
/*     */ 
/*     */   public InputStream post(Map cookies, Map parameters)
/*     */     throws IOException
/*     */   {
/* 301 */     setCookies(cookies);
/* 302 */     setParameters(parameters);
/* 303 */     return post();
/*     */   }
/*     */ 
/*     */   public InputStream post(String[] cookies, Object[] parameters)
/*     */     throws IOException
/*     */   {
/* 316 */     setCookies(cookies);
/* 317 */     setParameters(parameters);
/* 318 */     return post();
/*     */   }
/*     */ 
/*     */   public InputStream post(String name, Object value)
/*     */     throws IOException
/*     */   {
/* 330 */     setParameter(name, value);
/* 331 */     return post();
/*     */   }
/*     */ 
/*     */   public InputStream post(String name1, Object value1, String name2, Object value2)
/*     */     throws IOException
/*     */   {
/* 345 */     setParameter(name1, value1);
/* 346 */     return post(name2, value2);
/*     */   }
/*     */ 
/*     */   public InputStream post(String name1, Object value1, String name2, Object value2, String name3, Object value3)
/*     */     throws IOException
/*     */   {
/* 362 */     setParameter(name1, value1);
/* 363 */     return post(name2, value2, name3, value3);
/*     */   }
/*     */ 
/*     */   public InputStream post(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4)
/*     */     throws IOException
/*     */   {
/* 381 */     setParameter(name1, value1);
/* 382 */     return post(name2, value2, name3, value3, name4, value4);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, Map parameters)
/*     */     throws IOException
/*     */   {
/* 393 */     return new ClientHttpRequest(url).post(parameters);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, Object[] parameters)
/*     */     throws IOException
/*     */   {
/* 404 */     return new ClientHttpRequest(url).post(parameters);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, Map cookies, Map parameters)
/*     */     throws IOException
/*     */   {
/* 417 */     return new ClientHttpRequest(url).post(cookies, parameters);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, String[] cookies, Object[] parameters)
/*     */     throws IOException
/*     */   {
/* 430 */     return new ClientHttpRequest(url).post(cookies, parameters);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, String name1, Object value1)
/*     */     throws IOException
/*     */   {
/* 442 */     return new ClientHttpRequest(url).post(name1, value1);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, String name1, Object value1, String name2, Object value2)
/*     */     throws IOException
/*     */   {
/* 456 */     return new ClientHttpRequest(url).post(name1, value1, name2, value2);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, String name1, Object value1, String name2, Object value2, String name3, Object value3)
/*     */     throws IOException
/*     */   {
/* 472 */     return new ClientHttpRequest(url).post(name1, value1, name2, value2, name3, value3);
/*     */   }
/*     */ 
/*     */   public static InputStream post(URL url, String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4)
/*     */     throws IOException
/*     */   {
/* 490 */     return new ClientHttpRequest(url).post(name1, value1, name2, value2, name3, value3, name4, value4);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.ClientHttpRequest
 * JD-Core Version:    0.6.0
 */