/*     */ package org.apache.commons.mail;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.mail.Authenticator;
/*     */ import javax.mail.Message.RecipientType;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ 
/*     */ public abstract class Email
/*     */ {
/*     */   public static final String SENDER_EMAIL = "sender.email";
/*     */   public static final String SENDER_NAME = "sender.name";
/*     */   public static final String RECEIVER_EMAIL = "receiver.email";
/*     */   public static final String RECEIVER_NAME = "receiver.name";
/*     */   public static final String EMAIL_SUBJECT = "email.subject";
/*     */   public static final String EMAIL_BODY = "email.body";
/*     */   public static final String CONTENT_TYPE = "content.type";
/*     */   public static final String MAIL_HOST = "mail.host";
/*     */   public static final String MAIL_PORT = "mail.smtp.port";
/*     */   public static final String MAIL_SMTP_FROM = "mail.smtp.from";
/*     */   public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
/*     */   public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
/*     */   public static final String SMTP = "smtp";
/*     */   public static final String TEXT_HTML = "text/html";
/*     */   public static final String TEXT_PLAIN = "text/plain";
/*     */   public static final String ATTACHMENTS = "attachments";
/*     */   public static final String FILE_SERVER = "file.server";
/*     */   public static final String MAIL_DEBUG = "mail.debug";
/*     */   public static final String KOI8_R = "koi8-r";
/*     */   public static final String ISO_8859_1 = "iso-8859-1";
/*     */   public static final String US_ASCII = "us-ascii";
/*  84 */   protected MimeMessage message = null;
/*     */ 
/*  87 */   protected String charset = null;
/*     */ 
/*  90 */   private InternetAddress fromAddress = null;
/*     */ 
/*  93 */   private String subject = null;
/*     */ 
/*  96 */   private MimeMultipart emailBody = null;
/*     */ 
/*  99 */   private Object content = null;
/*     */ 
/* 102 */   private String contentType = null;
/*     */ 
/* 105 */   private boolean debug = false;
/*     */   private Date sentDate;
/*     */   private Authenticator authenticator;
/* 120 */   private String hostName = null;
/*     */ 
/* 123 */   private int m_iPort = 0;
/*     */ 
/* 126 */   private ArrayList toList = null;
/*     */ 
/* 129 */   private ArrayList ccList = null;
/*     */ 
/* 132 */   private ArrayList bccList = null;
/*     */ 
/* 135 */   private ArrayList replyList = null;
/*     */ 
/* 144 */   private Hashtable headers = null;
/*     */ 
/*     */   public void setDebug(boolean d)
/*     */   {
/* 153 */     this.debug = d;
/*     */   }
/*     */ 
/*     */   public void setAuthentication(String userName, String password)
/*     */   {
/* 170 */     Authenticator authenticator = new DefaultAuthenticator(userName, password);
/*     */ 
/* 172 */     setAuthenticator(authenticator);
/*     */   }
/*     */ 
/*     */   public void setAuthenticator(Authenticator authenticator)
/*     */   {
/* 187 */     this.authenticator = authenticator;
/*     */   }
/*     */ 
/*     */   public void setCharset(String charset)
/*     */   {
/* 197 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   public void setContent(MimeMultipart aMimeMultipart)
/*     */   {
/* 207 */     this.emailBody = aMimeMultipart;
/*     */   }
/*     */ 
/*     */   public void setContent(Object aObject, String aContentType)
/*     */   {
/* 218 */     this.content = aObject;
/* 219 */     if (aContentType == null)
/*     */     {
/* 221 */       this.contentType = aContentType;
/*     */     }
/*     */     else
/*     */     {
/* 225 */       int charsetPos = aContentType.toLowerCase().indexOf("; charset=");
/* 226 */       if (charsetPos > 0)
/*     */       {
/* 228 */         aContentType.substring(0, charsetPos);
/* 229 */         this.charset = aContentType.substring(charsetPos + 10);
/*     */       }
/*     */       else
/*     */       {
/* 233 */         this.contentType = aContentType;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setHostName(String aHostName)
/*     */   {
/* 245 */     this.hostName = aHostName;
/*     */   }
/*     */ 
/*     */   private Session getMailSession()
/*     */     throws MessagingException
/*     */   {
/* 256 */     Properties properties = System.getProperties();
/* 257 */     properties.setProperty("mail.transport.protocol", "smtp");
/*     */ 
/* 260 */     if (this.hostName == null)
/*     */     {
/* 262 */       this.hostName = properties.getProperty("mail.host");
/*     */     }
/* 264 */     if (this.hostName == null)
/*     */     {
/* 266 */       throw new MessagingException("Cannot find valid hostname for mail session");
/*     */     }
/*     */ 
/* 269 */     properties.setProperty("mail.host", this.hostName);
/*     */ 
/* 272 */     if (this.m_iPort != 0)
/*     */     {
/* 274 */       properties.setProperty("mail.smtp.port", new Integer(this.m_iPort).toString());
/*     */     }
/*     */ 
/* 277 */     properties.setProperty("mail.debug", new Boolean(this.debug).toString());
/*     */ 
/* 279 */     if (this.authenticator != null)
/*     */     {
/* 281 */       properties.setProperty("mail.smtp.auth", "true");
/*     */     }
/*     */ 
/* 284 */     Session session = Session.getDefaultInstance(properties, this.authenticator);
/*     */ 
/* 287 */     return session;
/*     */   }
/*     */ 
/*     */   public Email setFrom(String email)
/*     */     throws MessagingException
/*     */   {
/* 300 */     return setFrom(email, null);
/*     */   }
/*     */ 
/*     */   public Email setFrom(String email, String name)
/*     */     throws MessagingException
/*     */   {
/*     */     try
/*     */     {
/* 316 */       if ((name == null) || (name.trim().equals("")))
/*     */       {
/* 318 */         name = email;
/*     */       }
/* 320 */       if (this.fromAddress == null)
/*     */       {
/* 322 */         if (this.charset != null)
/*     */         {
/* 324 */           this.fromAddress = new InternetAddress(email, name, this.charset);
/*     */         }
/*     */         else
/*     */         {
/* 328 */           this.fromAddress = new InternetAddress(email, name);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 333 */         this.fromAddress.setAddress(email);
/* 334 */         this.fromAddress.setPersonal(name);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 339 */       throw new MessagingException("cannot set from", e);
/*     */     }
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */   public Email addTo(String email)
/*     */     throws MessagingException
/*     */   {
/* 354 */     return addTo(email, null);
/*     */   }
/*     */ 
/*     */   public Email addTo(String email, String name)
/*     */     throws MessagingException
/*     */   {
/*     */     try
/*     */     {
/* 370 */       if ((name == null) || (name.trim().equals("")))
/*     */       {
/* 372 */         name = email;
/*     */       }
/*     */ 
/* 375 */       if (this.toList == null)
/*     */       {
/* 377 */         this.toList = new ArrayList();
/*     */       }
/*     */ 
/* 380 */       if (this.charset != null)
/*     */       {
/* 382 */         this.toList.add(new InternetAddress(email, name, this.charset));
/*     */       }
/*     */       else
/*     */       {
/* 386 */         this.toList.add(new InternetAddress(email, name));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 391 */       throw new MessagingException("cannot add to", e);
/*     */     }
/* 393 */     return this;
/*     */   }
/*     */ 
/*     */   public Email setTo(Collection aCollection)
/*     */   {
/* 404 */     this.toList = new ArrayList(aCollection);
/* 405 */     return this;
/*     */   }
/*     */ 
/*     */   public Email addCc(String email)
/*     */     throws MessagingException
/*     */   {
/* 418 */     return addCc(email, null);
/*     */   }
/*     */ 
/*     */   public Email addCc(String email, String name)
/*     */     throws MessagingException
/*     */   {
/*     */     try
/*     */     {
/* 434 */       if ((name == null) || (name.trim().equals("")))
/*     */       {
/* 436 */         name = email;
/*     */       }
/*     */ 
/* 439 */       if (this.ccList == null)
/*     */       {
/* 441 */         this.ccList = new ArrayList();
/*     */       }
/*     */ 
/* 444 */       if (this.charset != null)
/*     */       {
/* 446 */         this.ccList.add(new InternetAddress(email, name, this.charset));
/*     */       }
/*     */       else
/*     */       {
/* 450 */         this.ccList.add(new InternetAddress(email, name));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 455 */       throw new MessagingException("cannot add cc", e);
/*     */     }
/*     */ 
/* 458 */     return this;
/*     */   }
/*     */ 
/*     */   public Email setCc(Collection aCollection)
/*     */   {
/* 469 */     this.ccList = new ArrayList(aCollection);
/* 470 */     return this;
/*     */   }
/*     */ 
/*     */   public Email addBcc(String email)
/*     */     throws MessagingException
/*     */   {
/* 483 */     return addBcc(email, null);
/*     */   }
/*     */ 
/*     */   public Email addBcc(String email, String name)
/*     */     throws MessagingException
/*     */   {
/*     */     try
/*     */     {
/* 499 */       if ((name == null) || (name.trim().equals("")))
/*     */       {
/* 501 */         name = email;
/*     */       }
/*     */ 
/* 504 */       if (this.bccList == null)
/*     */       {
/* 506 */         this.bccList = new ArrayList();
/*     */       }
/*     */ 
/* 509 */       this.bccList.add(new InternetAddress(email, name));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 513 */       throw new MessagingException("cannot add bcc", e);
/*     */     }
/*     */ 
/* 516 */     return this;
/*     */   }
/*     */ 
/*     */   public Email setBcc(Collection aCollection)
/*     */   {
/* 527 */     this.bccList = new ArrayList(aCollection);
/* 528 */     return this;
/*     */   }
/*     */ 
/*     */   public Email addReplyTo(String email)
/*     */     throws MessagingException
/*     */   {
/* 540 */     return addReplyTo(email, null);
/*     */   }
/*     */ 
/*     */   public Email addReplyTo(String email, String name)
/*     */     throws MessagingException
/*     */   {
/*     */     try
/*     */     {
/* 556 */       if ((name == null) || (name.trim().equals("")))
/*     */       {
/* 558 */         name = email;
/*     */       }
/*     */ 
/* 561 */       if (this.replyList == null)
/*     */       {
/* 563 */         this.replyList = new ArrayList();
/*     */       }
/*     */ 
/* 566 */       if (this.charset != null)
/*     */       {
/* 568 */         this.replyList.add(new InternetAddress(email, name, this.charset));
/*     */       }
/*     */       else
/*     */       {
/* 572 */         this.replyList.add(new InternetAddress(email, name));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 577 */       throw new MessagingException("cannot add replyTo", e);
/*     */     }
/* 579 */     return this;
/*     */   }
/*     */ 
/*     */   public void setHeaders(Hashtable h)
/*     */   {
/* 593 */     this.headers = h;
/*     */   }
/*     */ 
/*     */   public void addHeader(String name, String value)
/*     */   {
/* 604 */     if (name == null)
/*     */     {
/* 606 */       throw new IllegalArgumentException("name can not be null");
/*     */     }
/* 608 */     if (value == null)
/*     */     {
/* 610 */       throw new IllegalArgumentException("value can not be null");
/*     */     }
/* 612 */     if (this.headers == null)
/*     */     {
/* 614 */       this.headers = new Hashtable();
/*     */     }
/* 616 */     this.headers.put(name, value);
/*     */   }
/*     */ 
/*     */   public Email setSubject(String aSubject)
/*     */   {
/* 627 */     this.subject = aSubject;
/* 628 */     return this;
/*     */   }
/*     */ 
/*     */   public abstract Email setMsg(String paramString)
/*     */     throws MessagingException;
/*     */ 
/*     */   public void send()
/*     */     throws MessagingException
/*     */   {
/* 649 */     Session session = getMailSession();
/* 650 */     MimeMessage message = new MimeMessage(session);
/*     */ 
/* 652 */     if (this.subject != null)
/*     */     {
/* 654 */       if (this.charset == null)
/*     */       {
/* 656 */         message.setSubject(this.subject);
/*     */       }
/*     */       else
/*     */       {
/* 660 */         message.setSubject(this.subject, this.charset);
/*     */       }
/*     */     }
/*     */ 
/* 664 */     if (this.content != null)
/*     */     {
/* 666 */       String type = this.contentType;
/* 667 */       if ((type != null) && (this.charset != null))
/*     */       {
/* 669 */         type = type + "; charset=" + this.charset;
/*     */       }
/* 671 */       message.setContent(this.content, type);
/*     */     }
/* 673 */     else if (this.emailBody != null)
/*     */     {
/* 675 */       message.setContent(this.emailBody);
/*     */     }
/*     */     else
/*     */     {
/* 679 */       message.setContent("", "text/plain");
/*     */     }
/*     */ 
/* 682 */     if (this.fromAddress != null)
/*     */     {
/* 684 */       message.setFrom(this.fromAddress);
/*     */     }
/*     */     else
/*     */     {
/* 688 */       throw new MessagingException("Sender address required");
/*     */     }
/*     */ 
/* 691 */     if ((this.toList == null) && (this.ccList == null) && (this.bccList == null))
/*     */     {
/* 693 */       throw new MessagingException("At least one receiver address required");
/*     */     }
/*     */ 
/* 697 */     if (this.toList != null)
/*     */     {
/* 699 */       message.setRecipients(RecipientType.TO, toInternetAddressArray(this.toList));
/*     */     }
/*     */ 
/* 703 */     if (this.ccList != null)
/*     */     {
/* 705 */       message.setRecipients(RecipientType.CC, toInternetAddressArray(this.ccList));
/*     */     }
/*     */ 
/* 709 */     if (this.bccList != null)
/*     */     {
/* 711 */       message.setRecipients(RecipientType.BCC, toInternetAddressArray(this.bccList));
/*     */     }
/*     */ 
/* 715 */     if (this.replyList != null)
/*     */     {
/* 717 */       message.setReplyTo(toInternetAddressArray(this.replyList));
/*     */     }
/*     */ 
/* 720 */     if (this.headers != null)
/*     */     {
/* 722 */       Enumeration e = this.headers.keys();
/* 723 */       while (e.hasMoreElements())
/*     */       {
/* 725 */         String name = (String)e.nextElement();
/* 726 */         String value = (String)this.headers.get(name);
/* 727 */         message.addHeader(name, value);
/*     */       }
/*     */     }
/*     */ 
/* 731 */     if (message.getSentDate() == null)
/*     */     {
/* 733 */       message.setSentDate(getSentDate());
/*     */     }
/*     */ 
/* 736 */     Transport.send(message);
/*     */   }
/*     */ 
/*     */   public void setSentDate(Date date)
/*     */   {
/* 747 */     this.sentDate = date;
/*     */   }
/*     */ 
/*     */   public Date getSentDate()
/*     */   {
/* 757 */     return this.sentDate == null ? new Date() : this.sentDate;
/*     */   }
/*     */ 
/*     */   private InternetAddress[] toInternetAddressArray(ArrayList aList)
/*     */   {
/* 769 */     InternetAddress[] ia = (InternetAddress[])(InternetAddress[])aList.toArray(new InternetAddress[0]);
/*     */ 
/* 772 */     return ia;
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/* 777 */     return this.m_iPort;
/*     */   }
/*     */ 
/*     */   public void setPort(int a_sPort)
/*     */   {
/* 782 */     this.m_iPort = a_sPort;
/*     */   }
/*     */ 
/*     */   public ArrayList getBccList()
/*     */   {
/* 787 */     return this.bccList;
/*     */   }
/*     */ 
/*     */   public ArrayList getCcList()
/*     */   {
/* 792 */     return this.ccList;
/*     */   }
/*     */ 
/*     */   public MimeMultipart getEmailBody()
/*     */   {
/* 797 */     return this.emailBody;
/*     */   }
/*     */ 
/*     */   public InternetAddress getFromAddress()
/*     */   {
/* 802 */     return this.fromAddress;
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 807 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public ArrayList getToList()
/*     */   {
/* 812 */     return this.toList;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.Email
 * JD-Core Version:    0.6.0
 */