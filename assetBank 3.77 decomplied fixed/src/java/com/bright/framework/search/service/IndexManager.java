package com.bright.framework.search.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bn2web.common.service.GlobalApplication;
import com.bright.framework.search.bean.IndexableDocument;
import com.bright.framework.search.constant.SearchConstants;
import com.bright.framework.search.constant.SearchSettings;
import com.bright.framework.search.lucene.BTopDocs;
import com.bright.framework.search.lucene.NumericRangeQueryParser;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexFileNameFilter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Lock;
import org.apache.lucene.store.Lock.With;
import org.apache.lucene.util.Version;

public class IndexManager
  implements SearchConstants
{
  public static final String c_ksClassName = "IndexManager";
  private static final String k_sIndexField_AllDocs = "f_doc";
  private static final String k_sIndexFieldValue_AllDocs = "0";
  protected Log m_logger = GlobalApplication.getInstance().getLogger();
  private final String m_sIndexFilepath;
  private final Object m_directoryAndSearcherLock = new Object();
  private Directory m_directory;
  private IndexSearcher m_searcher;
  private String m_sDefaultQueryField;
  private AnalyzerFactory m_analyzerFactory;
  public static final String k_sDefaultOperatorAnd = "AND";
  public static final String k_sDefaultOperatorOr = "OR";

  public IndexManager(String a_sIndexFilepath, String a_sDefaultQueryField, AnalyzerFactory a_analyzerFactory)
  {
    this.m_sIndexFilepath = a_sIndexFilepath;
    this.m_sDefaultQueryField = a_sDefaultQueryField;
    this.m_analyzerFactory = a_analyzerFactory;

    int iMaxClauses = SearchSettings.getSearchMaxBooleanClauses();
    BooleanQuery.setMaxClauseCount(iMaxClauses);
  }

  public BTopDocs getDocuments(String a_sSearchQuery, SortField[] a_sortFields, String a_sDefaultOperator, Filter a_filter, int a_iMaxResults)
    throws Bn2Exception
  {
    this.m_logger.debug("In IndexManager.getDocuments");

    BTopDocs bhits = null;

    IndexSearcher searcher = null;
    boolean bNeedToDecRef = false;
    try
    {
      synchronized (this.m_directoryAndSearcherLock)
      {
        searcher = getSearcher();

        searcher.getIndexReader().incRef();
        bNeedToDecRef = true;
      }

      Analyzer analyzer = this.m_analyzerFactory.getAnalyzer();
      try
      {
        Query query;
        NumericRangeQueryParser parser;
        if (a_sSearchQuery.length() == 0)
        {
          throw new Bn2Exception("Empty query in IndexManager.getDocuments");
        }

        this.m_logger.debug("IndexManager.getDocuments.Lucene search string is: " + a_sSearchQuery);
        try
        {
          parser = new NumericRangeQueryParser(Version.LUCENE_23, this.m_sDefaultQueryField, analyzer);
          if (a_sDefaultOperator.compareTo("AND") == 0)
          {
            parser.setDefaultOperator(QueryParser.AND_OPERATOR);
          }
          query = parser.parse(a_sSearchQuery);
        }
        catch (ArrayIndexOutOfBoundsException aioobe)
        {
          try
          {
            parser = new NumericRangeQueryParser(Version.LUCENE_23, this.m_sDefaultQueryField, analyzer);
            if (a_sDefaultOperator.compareTo("AND") == 0)
            {
              parser.setDefaultOperator(NumericRangeQueryParser.AND_OPERATOR);
            }
            query = parser.parse(a_sSearchQuery);
          }
          catch (ArrayIndexOutOfBoundsException aioobe2)
          {
            throw new ParseException();
          }
        }

        if (searcher != null)
        {
          try
          {
            Date dtStart = null;
            Date dtEnd = null;

            dtStart = new Date();

            TopDocs hits = null;

            if (a_iMaxResults < 0)
            {
              a_iMaxResults = Math.min(searcher.maxDoc(), 1000000);
            }
            else
            {
              a_iMaxResults = Math.min(searcher.maxDoc(), a_iMaxResults);

              a_iMaxResults = Math.max(1, a_iMaxResults);
            }

            this.m_logger.trace("IndexManager.getDocuments(): Query: " + query);

            if ((a_sortFields != null) && (a_sortFields.length > 0))
            {
              Sort sort = new Sort(a_sortFields);

              hits = searcher.search(query, a_filter, a_iMaxResults, sort);
            }
            else
            {
              hits = searcher.search(query, a_filter, a_iMaxResults);
            }

            this.m_logger.debug("IndexManager.getDocuments(): Got results, total hits = " + ((hits == null) ? "0 (null) " : new StringBuilder().append(String.valueOf(hits.totalHits)).append(", scored docs = ").append(hits.scoreDocs.length).toString()));

            bhits = new BTopDocs(searcher, hits);

            dtEnd = new Date();

            this.m_logger.debug("IndexManager.getDocuments() : Search ran in " + (dtEnd.getTime() - dtStart.getTime()) + "ms");
          }
          catch (IOException ioe)
          {
            throw new Bn2Exception("Error in search: ", ioe);
          }
        }
      }
      catch (ParseException e)
      {
        this.m_logger.debug("ParseException caught in IndexManager.getDocuments() : " + e.getMessage());
      }

    }
    catch (IOException e)
    {
    }
    finally
    {
      if (bNeedToDecRef)
      {
        try
        {
          searcher.getIndexReader().decRef();
        }
        catch (IOException e)
        {
          this.m_logger.error("Error in IndexReader.decRef() - probably leaking some file handles", e);
        }
      }
    }

    return bhits;
  }

  private IndexSearcher getSearcher()
    throws IOException, CorruptIndexException
  {
    synchronized (this.m_directoryAndSearcherLock)
    {
      if (this.m_directory == null)
      {
        this.m_directory = FSDirectory.open(new File(this.m_sIndexFilepath));
        try
        {
          this.m_searcher = new IndexSearcher(this.m_directory, true);
        }
        catch (IOException e)
        {
          closeDirectory();

          throw e;
        }
        catch (RuntimeException e)
        {
          closeDirectory();

          throw e;
        }
      }
    }

    return this.m_searcher;
  }

  private void invalidateSearcher()
  {
    synchronized (this.m_directoryAndSearcherLock)
    {
      closeSearcher();
      closeDirectory();
    }
  }

  private void closeSearcher()
  {
    if (this.m_searcher != null)
    {
      try
      {
        this.m_searcher.close();
      }
      catch (IOException e)
      {
        this.m_logger.error("Error closing IndexSearcher - probably leaking some file handles", e);
      }

      this.m_searcher = null;
    }
  }

  private void closeDirectory()
  {
    if (this.m_directory != null)
    {
      try
      {
        this.m_directory.close();
      }
      catch (IOException e)
      {
        this.m_logger.error("Error closing lucene Directory", e);
      }

      this.m_directory = null;
    }
  }

  public void deleteAllDocuments()
    throws Bn2Exception
  {
    IndexWriter writer = null;
    try
    {
      if (!(indexExists()))
      {
        this.m_logger.debug("deleteAllDocuments: The index " + this.m_sIndexFilepath + " does not exist yet.");
        //jsr 139;
        return;
      }

      Analyzer analyzer = this.m_analyzerFactory.getAnalyzer();
      writer = IndexModifierManager.getInstance().getIndexWriter(this.m_sIndexFilepath, false, analyzer);

      IndexReader reader = IndexReader.open(FSDirectory.open(new File(this.m_sIndexFilepath)), true);
      int iNumDeleted = reader.numDeletedDocs();

      Term deleteTerm = new Term("f_doc", "0");
      writer.deleteDocuments(deleteTerm);

      writer.commit();

      this.m_logger.debug("Number of documents deleted from index = " + (reader.numDeletedDocs() - iNumDeleted));

      reader.close();
    }
    catch (IOException ioe)
    {
    }
    finally
    {
      if (writer != null)
      {
        IndexModifierManager.getInstance().releaseIndexModifier(this.m_sIndexFilepath, writer);

        invalidateSearcher();
      }
    }
  }

  public void deleteDocument(String a_sDocId)
    throws Bn2Exception
  {
    IndexWriter writer = null;
    try
    {
      Analyzer analyzer = this.m_analyzerFactory.getAnalyzer();

      writer = IndexModifierManager.getInstance().getIndexWriter(this.m_sIndexFilepath, false, analyzer);

      IndexReader reader = IndexReader.open(FSDirectory.open(new File(this.m_sIndexFilepath)), true);
      int iNumDeleted = reader.numDeletedDocs();

      Term deleteTerm = new Term("f_id", a_sDocId);
      writer.deleteDocuments(deleteTerm);
      writer.commit();

      this.m_logger.debug("Number of documents deleted from index = " + (reader.numDeletedDocs() - iNumDeleted));

      reader.close();
    }
    catch (IOException ioe)
    {
    }
    finally
    {
      if (writer != null)
      {
        IndexModifierManager.getInstance().releaseIndexModifier(this.m_sIndexFilepath, writer);

        invalidateSearcher();
      }
    }
  }

  public void deleteDocuments(Collection<? extends IndexableDocument> a_docs)
    throws Bn2Exception
  {
    if (!(indexExists()))
    {
      this.m_logger.debug("deleteDocuments: The index " + this.m_sIndexFilepath + " does not exist yet.");
      return;
    }

    IndexWriter writer = null;
    try
    {
      Analyzer analyzer = this.m_analyzerFactory.getAnalyzer();
      writer = IndexModifierManager.getInstance().getIndexWriter(this.m_sIndexFilepath, false, analyzer);

      Term deleteTerm = null;
      IndexableDocument doc = null;

      for (IndexableDocument a_doc : a_docs)
      {
        doc = a_doc;
        deleteTerm = new Term("f_id", doc.getIndexableDocId());
        writer.deleteDocuments(deleteTerm);
      }

      writer.commit();
    }
    catch (IOException ioe)
    {
    }
    finally
    {
      if (writer != null)
      {
        IndexModifierManager.getInstance().releaseIndexModifier(this.m_sIndexFilepath, writer);

        invalidateSearcher();
      }
    }
  }

  public synchronized void indexDocuments(Collection<? extends IndexableDocument> a_docs, Object a_createLuceneDocumentParams)
    throws Bn2Exception
  {
    boolean bNewIndex = !(indexExists());

    Analyzer analyzer = this.m_analyzerFactory.getAnalyzer();
    IndexWriter modifier = IndexModifierManager.getInstance().getIndexWriter(this.m_sIndexFilepath, bNewIndex, analyzer);
    try
    {
      for (IndexableDocument idDoc : a_docs)
      {
        Document luceneDoc = idDoc.createLuceneDocument(a_createLuceneDocumentParams);
        if (luceneDoc.getField("f_id") != null)
        {
          throw new Bn2Exception("Documents returned by createLuceneDocument() must not contain a k_sIndexField_Id field");
        }

        luceneDoc.add(new Field("f_id", idDoc.getIndexableDocId(), Field.Store.YES, Field.Index.NOT_ANALYZED));

        luceneDoc.add(new Field("f_doc", "0", Field.Store.YES, Field.Index.NOT_ANALYZED));

        Term deleteTerm = new Term("f_id", idDoc.getIndexableDocId());
        modifier.updateDocument(deleteTerm, luceneDoc);
        try
        {
          super.wait(10L);
        }
        catch (InterruptedException ie)
        {
        }
      }
      modifier.commit();

      if (a_docs.size() > 1)
      {
        this.m_logger.debug("IndexManager.rebuildIndex() : Finished reindexing " + a_docs.size() + " documents");
      }

    }
    catch (IOException e)
    {
    }
    finally
    {
      IndexModifierManager.getInstance().releaseIndexModifier(this.m_sIndexFilepath, modifier);

      invalidateSearcher();
    }
  }

  public void optimizeIndex()
    throws Bn2Exception
  {
    IndexWriter modifier = null;

    if ((this.m_sIndexFilepath != null) && 
      (indexExists()))
    {
      try
      {
        Analyzer analyzer = this.m_analyzerFactory.getAnalyzer();
        modifier = IndexModifierManager.getInstance().getIndexWriter(this.m_sIndexFilepath, false, analyzer);
        modifier.optimize();

        this.m_logger.debug("Lucene search index successfully optimised : " + this.m_sIndexFilepath);
      }
      catch (IOException ioe)
      {
      }
      finally
      {
        if (modifier != null)
        {
          IndexModifierManager.getInstance().releaseIndexModifier(this.m_sIndexFilepath, modifier);

          invalidateSearcher();
        }
      }
    }
  }

  public boolean indexExists()
  {
    File fIndexDirectory = new File(this.m_sIndexFilepath);

    return fIndexDirectory.exists();
  }

  public void releaseLockFileIfExists()
    throws Bn2Exception
  {
    try
    {
      if (IndexWriter.isLocked(FSDirectory.open(new File(this.m_sIndexFilepath))))
      {
        this.m_logger.warn("IndexManager.releaseLockFile - found lock file on startup - attempting to remove it now.");
        IndexWriter.unlock(FSDirectory.open(new File(this.m_sIndexFilepath)));
      }
    }
    catch (IOException ioe)
    {
      throw new Bn2Exception("Error attempting to remove lock in IndexManager.releaseLockFile", ioe);
    }
  }

  public void deleteIndex()
    throws Bn2Exception
  {
    File dir = new File(this.m_sIndexFilepath);
    try
    {
      FileUtils.forceDelete(dir);

      invalidateSearcher();
    }
    catch (IOException e)
    {
      if (dir.exists())
      {
        this.m_logger.error("IndexManager.deleteIndex() : Could not delete index directory '" + this.m_sIndexFilepath + "' due to IO exception", e);
      }
    }
  }

  public void renameIndexDirectory(IndexManager a_newIndexManager)
    throws Bn2Exception
  {
    try
    {
      final File newDir = new File(a_newIndexManager.m_sIndexFilepath);
      Directory newLuceneDir = FSDirectory.open(newDir);
      final Lock newDirLock = newLuceneDir.makeLock("write.lock");

      new Lock.With(newDirLock, IndexWriter.WRITE_LOCK_TIMEOUT)
      {
        protected Object doBody()
          throws IOException
        {
          final File oldDir = new File(IndexManager.this.m_sIndexFilepath);
          final Directory oldLuceneDir = FSDirectory.open(oldDir);
          final Lock oldDirLock = oldLuceneDir.makeLock("write.lock");
          new Lock.With(oldDirLock, IndexWriter.WRITE_LOCK_TIMEOUT)
                                 {
                                   protected Object doBody()
                                     throws IOException
                                   {
                                     if (newDir.exists())
                                     {
                                       FileUtils.forceDelete(newDir);
                                     }

                                     new IndexManager(oldDir.toString(), newDir.toString(),null);
                                     return null;
                                   }
                                 }.run();

          return null;
        }
      }
      .run();

      invalidateSearcher();
    }
    catch (IOException e)
    {
      throw new Bn2Exception("IOException renaming index directory", e);
    }
  }

  public void copyIndexDirectoryTo(IndexManager a_newIndexManager)
    throws Bn2Exception
  {
    try
    {
      final File newDir = new File(a_newIndexManager.m_sIndexFilepath);
      final Directory newLuceneDir = FSDirectory.open(newDir);
      Lock newDirLock = newLuceneDir.makeLock("write.lock");

      //new Lock.With(newDirLock, IndexWriter.WRITE_LOCK_TIMEOUT, newDir, newLuceneDir)
      new Lock.With(newDirLock, IndexWriter.WRITE_LOCK_TIMEOUT)
      {
        protected Object doBody()
          throws IOException
        {
          File oldDir = new File(IndexManager.this.m_sIndexFilepath);
          final Directory oldLuceneDir = FSDirectory.open(oldDir);
          final Lock oldDirLock = oldLuceneDir.makeLock("write.lock");
          //new Lock.With(oldDirLock, IndexWriter.WRITE_LOCK_TIMEOUT, oldLuceneDir)
          new Lock.With(oldDirLock, IndexWriter.WRITE_LOCK_TIMEOUT)
          {
            protected Object doBody()
              throws IOException
            {
              if (newDir.exists())
              {
                File[] indexFiles = newDir.listFiles(IndexFileNameFilter.getFilter());
                for (File indexFile : indexFiles)
                {
                  if (!(indexFile.delete()))
                  {
                    IndexManager.this.m_logger.error("IndexManager: Could not delete index file " + indexFile + ", leaving it. It may interfere with the new index files that are about to be copied to that index directory");
                  }
                }

              }

              Directory.copy(oldLuceneDir, newLuceneDir, false);

              return null;
            }
          }
          .run();

          return null;
        }
      }
      .run();
    }
    catch (IOException e)
    {
      throw new Bn2Exception("IOException renaming index directory", e);
    }
  }

  private static void renameFile(File a_src, File a_dest)
    throws IOException
  {
    if (!(a_src.renameTo(a_dest)))
    {
      throw new IOException("Couldn't rename \"" + a_src + "\" to \"" + a_dest);
    }
  }

  public static String allDocsTerm()
  {
    return "f_doc:(0)";
  }
}