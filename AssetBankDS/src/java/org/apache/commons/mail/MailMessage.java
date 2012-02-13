/*     */ package org.apache.commons.mail;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Message.RecipientType;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.AddressException;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class MailMessage
/*     */ {
/*     */   protected String host;
/*     */   protected Hashtable headers;
/*     */   protected InternetAddress from;
/*     */   protected InternetAddress[] replyTo;
/*     */   protected InternetAddress[] to;
/*     */   protected InternetAddress[] cc;
/*     */   protected InternetAddress[] bcc;
/*     */   protected String subject;
/*     */   protected String body;
/*     */   protected boolean debug;
/*     */ 
/*     */   public MailMessage()
/*     */   {
/* 134 */     this(null, null, null, null, null, "", "", false);
/*     */   }
/*     */ 
/*     */   public MailMessage(String h, String t, String f, String s, String b)
/*     */   {
/* 149 */     this(h, t, null, null, f, s, b, false);
/*     */   }
/*     */ 
/*     */   public MailMessage(String h, String t, String c, String bc, String f, String s, String b, boolean d)
/*     */   {
/* 175 */     this.host = h;
/* 176 */     this.to = (t == null ? null : parseAddressField(t));
/* 177 */     this.cc = (this.cc == null ? null : parseAddressField(c));
/* 178 */     this.bcc = (bc == null ? null : parseAddressField(bc));
/* 179 */     this.from = (f == null ? null : parseInternetAddress(f));
/* 180 */     this.subject = s;
/* 181 */     this.body = b;
/* 182 */     this.debug = d;
/*     */   }
/*     */ 
/*     */   public void addHeader(String name, String value)
/*     */   {
/* 193 */     if (this.headers == null)
/*     */     {
/* 195 */       this.headers = new Hashtable();
/*     */     }
/* 197 */     this.headers.put(name, value);
/*     */   }
/*     */ 
/*     */   public static InternetAddress[] parseAddressField(String str)
/*     */   {
/*     */     String[] addressList;
/* 209 */     if (str.indexOf(",") != -1)
/*     */     {
/* 211 */       Vector v = new Vector();
/* 212 */       StringTokenizer st = new StringTokenizer(str, ",", false);
/* 213 */       while (st.hasMoreTokens())
/*     */       {
/* 215 */         v.addElement(st.nextToken());
/*     */       }
/* 217 */        addressList = new String[v.size()];
/* 218 */       for (int i = 0; i < v.size(); i++)
/*     */       {
/* 220 */         addressList[i] = ((String)v.elementAt(i));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 225 */       addressList = new String[1];
/* 226 */       addressList[0] = str;
/*     */     }
/* 228 */     return parseAddressList(addressList);
/*     */   }
/*     */ 
/*     */   public static InternetAddress[] parseAddressList(String[] aList)
/*     */   {
/* 239 */     InternetAddress[] ia = new InternetAddress[aList.length];
/*     */ 
/* 241 */     for (int i = 0; i < aList.length; i++)
/*     */     {
/* 243 */       ia[i] = parseInternetAddress(aList[i]);
/*     */     }
/*     */ 
/* 246 */     return ia;
/*     */   }
/*     */ 
/*     */   public static void parseHeader(String str, Hashtable headers)
/*     */   {
/* 257 */     String name = null;
/* 258 */     String value = null;
/*     */ 
/* 260 */     str = str.trim();
/* 261 */     int sp = str.lastIndexOf(":");
/* 262 */     name = str.substring(0, sp);
/* 263 */     value = str.substring(sp + 1).trim();
/*     */ 
/* 265 */     headers.put(name, value);
/*     */   }
/*     */ 
/*     */   public static Hashtable parseHeaderField(String str)
/*     */   {
/*     */     String[] headerList;
/* 277 */     if (str.indexOf(",") != -1)
/*     */     {
/* 279 */       Vector v = new Vector();
/* 280 */       StringTokenizer st = new StringTokenizer(str, ",", false);
/* 281 */       while (st.hasMoreTokens())
/*     */       {
/* 283 */         v.addElement(st.nextToken());
/*     */       }
/* 285 */        headerList = new String[v.size()];
/* 286 */       for (int i = 0; i < v.size(); i++)
/*     */       {
/* 288 */         headerList[i] = ((String)v.elementAt(i));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 293 */       headerList = new String[1];
/* 294 */       headerList[0] = str;
/*     */     }
/* 296 */     return parseHeaderList(headerList);
/*     */   }
/*     */ 
/*     */   public static Hashtable parseHeaderList(String[] hList)
/*     */   {
/* 307 */     Hashtable headers = new Hashtable();
/*     */ 
/* 309 */     for (int i = 0; i < hList.length; i++)
/*     */     {
/* 312 */       parseHeader(hList[i], headers);
/*     */     }
/*     */ 
/* 315 */     return headers;
/*     */   }
/*     */ 
/*     */   public static InternetAddress parseInternetAddress(String str)
/*     */   {
/* 326 */     String address = null;
/* 327 */     String personal = null;
/*     */ 
/* 329 */     str = str.trim();
/* 330 */     if (str.indexOf(" ") == -1)
/*     */     {
/* 332 */       address = str;
/*     */     }
/*     */     else
/*     */     {
/* 336 */       int sp = str.lastIndexOf(" ");
/* 337 */       address = str.substring(sp + 1);
/* 338 */       personal = str.substring(0, sp);
/*     */     }
/* 340 */     return parseInternetAddress(address, personal);
/*     */   }
/*     */ 
/*     */   public static InternetAddress parseInternetAddress(String address, String personal)
/*     */   {
/* 352 */     InternetAddress ia = null;
/*     */     try
/*     */     {
/* 355 */       ia = new InternetAddress(address);
/*     */ 
/* 357 */       if (personal != null)
/*     */       {
/* 359 */         ia.setPersonal(personal);
/*     */       }
/*     */     }
/*     */     catch (AddressException e)
/*     */     {
/* 364 */       e.printStackTrace();
/* 365 */       System.out.println();
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 369 */       e.printStackTrace();
/* 370 */       System.out.println();
/*     */     }
/*     */ 
/* 373 */     return ia;
/*     */   }
/*     */ 
/*     */   public boolean send()
/*     */   {
/* 385 */     Properties props = new Properties();
/* 386 */     props.put("mail.smtp.host", this.host);
/*     */ 
/* 388 */     Session session = Session.getInstance(props, null);
/* 389 */     session.setDebug(this.debug);
/*     */     try
/*     */     {
/* 394 */       Message msg = new MimeMessage(session);
/*     */ 
/* 397 */       msg.setFrom(this.from);
/*     */ 
/* 400 */       msg.setRecipients(Message.RecipientType.TO, this.to);
/*     */ 
/* 403 */       if (this.cc != null)
/*     */       {
/* 405 */         msg.setRecipients(Message.RecipientType.CC, this.cc);
/*     */       }
/*     */ 
/* 410 */       if (this.bcc != null)
/*     */       {
/* 412 */         msg.setRecipients(Message.RecipientType.BCC, this.bcc);
/*     */       }
/*     */ 
/* 417 */       if (this.replyTo != null)
/*     */       {
/* 419 */         msg.setReplyTo(this.replyTo);
/*     */       }
/*     */ 
/* 423 */       msg.setSubject(this.subject);
/*     */ 
/* 427 */       msg.setText(this.body);
/*     */ 
/* 431 */       if (this.headers != null)
/*     */       {
/* 433 */         Enumeration e = this.headers.keys();
/* 434 */         while (e.hasMoreElements())
/*     */         {
/* 436 */           String name = (String)e.nextElement();
/* 437 */           String value = (String)this.headers.get(name);
/* 438 */           msg.addHeader(name, value);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 443 */       Transport.send(msg);
/*     */     }
/*     */     catch (MessagingException mex)
/*     */     {
/* 447 */       mex.printStackTrace();
/* 448 */       Exception ex = null;
/* 449 */       if ((ex = mex.getNextException()) != null)
/*     */       {
/* 451 */         ex.printStackTrace();
/*     */       }
/* 453 */       return false;
/*     */     }
/* 455 */     return true;
/*     */   }
/*     */ 
/*     */   public void setBcc(InternetAddress[] bc)
/*     */   {
/* 466 */     this.bcc = bc;
/*     */   }
/*     */ 
/*     */   public void setBcc(String bc)
/*     */   {
/* 477 */     this.bcc = parseAddressField(bc);
/*     */   }
/*     */ 
/*     */   public void setBody(String b)
/*     */   {
/* 487 */     this.body = b;
/*     */   }
/*     */ 
/*     */   public void setCc(InternetAddress[] c)
/*     */   {
/* 498 */     this.cc = c;
/*     */   }
/*     */ 
/*     */   public void setCc(String c)
/*     */   {
/* 509 */     this.cc = parseAddressField(c);
/*     */   }
/*     */ 
/*     */   public void setDebug(String str)
/*     */   {
/* 519 */     if (str.equals("1"))
/*     */     {
/* 521 */       this.debug = true;
/*     */     }
/* 523 */     else if (str.equals("0"))
/*     */     {
/* 525 */       this.debug = false;
/*     */     }
/*     */     else
/*     */     {
/* 529 */       this.debug = new Boolean(str).booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDebug(boolean d)
/*     */   {
/* 540 */     this.debug = d;
/*     */   }
/*     */ 
/*     */   public void setFrom(String f)
/*     */   {
/* 551 */     this.from = parseInternetAddress(f);
/*     */   }
/*     */ 
/*     */   public void setFrom(InternetAddress f)
/*     */   {
/* 562 */     this.from = f;
/*     */   }
/*     */ 
/*     */   public void setHeaders(String h)
/*     */   {
/* 576 */     this.headers = parseHeaderField(h);
/*     */   }
/*     */ 
/*     */   public void setHeaders(Hashtable h)
/*     */   {
/* 590 */     this.headers = h;
/*     */   }
/*     */ 
/*     */   public void setHost(String h)
/*     */   {
/* 600 */     this.host = h;
/*     */   }
/*     */ 
/*     */   public void setReplyTo(InternetAddress[] rt)
/*     */   {
/* 611 */     this.replyTo = rt;
/*     */   }
/*     */ 
/*     */   public void setReplyTo(String rp)
/*     */   {
/* 622 */     this.replyTo = parseAddressField(rp);
/*     */   }
/*     */ 
/*     */   public void setSubject(String s)
/*     */   {
/* 632 */     this.subject = s;
/*     */   }
/*     */ 
/*     */   public void setTo(InternetAddress[] t)
/*     */   {
/* 643 */     this.to = t;
/*     */   }
/*     */ 
/*     */   public void setTo(String t)
/*     */   {
/* 654 */     this.to = parseAddressField(t);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.MailMessage
 * JD-Core Version:    0.6.0
 */