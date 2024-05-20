package com.mobius;

import com.mobius.vdrapi.INode;

public class TopicSearch implements com.mobius.vdrapi.IHasProfileLocation {

    String m_documentServerName = null;
    String m_userId = null;
    String m_pswd = null;

    // Topic-specific data
    String m_topicId = null;
    String m_topicVersion = null;
    String m_topicItem = null;
    String m_topicReport = null;
    String m_topicReportVersion = null;
    String m_topicEntry = null;
    String m_topicPage = null;

    byte[] b1;
    byte[] b2;
    StringBuffer sb = new StringBuffer();

  /** Creates new MobiusRequest */
  public TopicSearch() {
  }
  public TopicSearch(String server,String user, String password) {
      m_documentServerName = server;
      m_userId = user;
      m_pswd = password;
  }

  public String getProfileLocation()
  {
    return "ddrint.xml";
  }

  public String getPrimaryLocation()
  {
    return "";
  }

  public String getSecondaryLocation()
  {
    // Case sensitive for Solaris
    return "ddrint.ini";
  }
  public String[][] listitems(String topic_id,String topicitem) {
    m_topicId = topic_id;
    m_topicItem = topicitem;
    try {
      com.mobius.vdrapi.DocumentServerFactory dsf = new com.mobius.vdrapi.DocumentServerFactory(this);

      com.mobius.vdrapi.DocumentServerQueryParam qParam = new com.mobius.vdrapi.DocumentServerQueryParam(m_documentServerName,
                                              com.mobius.vdrapi.ComparisonSpec.GreaterThanOrEqualTo,
                                              1,
                                              com.mobius.vdrapi.SortOrder.Ascending,
                                              null);
      //get a list of servers
      INode dsl[] = dsf.createList(qParam);
      int count = dsf.totalNumberInList();
      com.mobius.vdrapi.Session session = null;
      com.mobius.vdrapi.IDocumentServer myDocServer = null;

      for( int i=0; i < count; i++ ) {
          com.mobius.vdrapi.IDocumentServer ds = (com.mobius.vdrapi.IDocumentServer)dsl[i];
          if (ds.getKeyValue().equals( m_documentServerName )) {
              myDocServer = ds;
              if ( session == null ) {
                  session = new com.mobius.vdrapi.Session( ds, m_userId, m_pswd, com.mobius.vdrapi.ViewDirectSeverityLevel.SL_ERROR );
                  //session = new com.mobius.vdrapi.Session(ds,m_userId,"ADMIN",m_pswd,com.mobius.vdrapi.ViewDirectSeverityLevel.SL_DISASTER,b1,0,b2,sb);
                  System.out.println(session.toString());
                  break;
              }
              else {
                  session.attach( ds, m_userId, m_pswd, com.mobius.vdrapi.ViewDirectSeverityLevel.SL_ERROR );
              }
          }
      }

      if ( session == null ) {
          System.out.println( "No session established");
      }

      com.mobius.vdrapi.TopicListFactory tlf = (com.mobius.vdrapi.TopicListFactory)session.createListFactory(com.mobius.vdrapi.SessionFactoryType.Topic);

      INode topicid = null;
     com.mobius.vdrapi.QueryParam qParamtn = new com.mobius.vdrapi.QueryParam(this.m_topicId,
                                              com.mobius.vdrapi.ComparisonSpec.EqualTo,
                                              1);
      INode [] tn = tlf.createList(qParamtn);

      for ( int i = 0; i < tn.length; i++ ) {
          if ( ((String)tn[i].getKeyValue()).trim().equals(m_topicId)) {
            topicid = tn[i];
            break;
          }
      }
      if ( topicid == null )
        throw new com.mobius.vdrapi.exceptions.ViewDirectException("Couldn't find topic id", "" );

      INode topicv = null;
      com.mobius.vdrapi.QueryParam qpall = new com.mobius.vdrapi.QueryParam("",com.mobius.vdrapi.ComparisonSpec.First,
                                              1);

      INode [] tv = ((com.mobius.vdrapi.IListFactory)topicid).createList(qpall);
            topicv = tv[0];
            // use the current version
      if ( topicv == null )
        throw new com.mobius.vdrapi.exceptions.ViewDirectException("Couldn't find topic version", "" );
//dig around and find all the items for this query, then place the appropriate items
//in a string buffer as necessary, then cast the stringbuffer to a stringarray
   com.mobius.vdrapi.TopicItemEntryQueryParam qParamti = new com.mobius.vdrapi.TopicItemEntryQueryParam(m_topicItem, "", "", 0, com.mobius.vdrapi.ComparisonSpec.GreaterThanOrEqualTo, 10);
   INode [] ti = ((com.mobius.vdrapi.IListFactory)topicv).createList(qParamti);
   int itemcount = 0;
   for(int i=0;i<ti.length;i++)
   {
    com.mobius.vdrapi.TopicItemEntryNode test_tien = (com.mobius.vdrapi.TopicItemEntryNode)ti[i];
    // Only get the items that match
    if(test_tien.getTopicItemName().substring(0,m_topicItem.length()).equals(m_topicItem))
    {
      itemcount ++;
    }
   }
   list = new String[itemcount][4];
      for (int i=0; i<itemcount; i++)
      {
          com.mobius.vdrapi.TopicItemEntryNode tien = (com.mobius.vdrapi.TopicItemEntryNode)ti[i];
          list[i][0] = tien.getReportId();
          list[i][1] = tien.getVersionId();
          list[i][2] = tien.getSectionName();
          Long temp = new Long(tien.getNumberOfPages());
          list[i][3] = temp.toString();
      }
      session.detach();
    }
    catch ( Exception e ) {
      e.printStackTrace();
    }
    return list;
  }
  public void FindTopics(String topic_id,String topicitem,int itemlimit)
  {
    String[][] garbage = this.listitems(topic_id,topicitem,itemlimit);
    garbage = null;
  }
  public String getelement(int row,int col)
  {
    return list[row][col];
  }
  public String[][] listitems(String topic_id,String topicitem,int itemlimit) {
    m_topicId = topic_id;
    m_topicItem = topicitem;
    try {
      com.mobius.vdrapi.DocumentServerFactory dsf = new com.mobius.vdrapi.DocumentServerFactory(this);

      com.mobius.vdrapi.DocumentServerQueryParam qParam = new com.mobius.vdrapi.DocumentServerQueryParam(m_documentServerName,
                                              com.mobius.vdrapi.ComparisonSpec.GreaterThanOrEqualTo,
                                              1,
                                              com.mobius.vdrapi.SortOrder.Ascending,
                                              null);
      //get a list of servers
      INode dsl[] = dsf.createList(qParam);
      int count = dsf.totalNumberInList();
      com.mobius.vdrapi.Session session = null;
      com.mobius.vdrapi.IDocumentServer myDocServer = null;

      for( int i=0; i < count; i++ ) {
          com.mobius.vdrapi.IDocumentServer ds = (com.mobius.vdrapi.IDocumentServer)dsl[i];
          if (ds.getKeyValue().equals( m_documentServerName )) {
              myDocServer = ds;
              if ( session == null ) {
                  session = new com.mobius.vdrapi.Session( ds, m_userId, m_pswd, com.mobius.vdrapi.ViewDirectSeverityLevel.SL_ERROR );
                  //session = new com.mobius.vdrapi.Session(ds,m_userId,"ADMIN",m_pswd,com.mobius.vdrapi.ViewDirectSeverityLevel.SL_DISASTER,b1,0,b2,sb);
                  System.out.println(session.toString());
                  break;
              }
              else {
                  session.attach( ds, m_userId, m_pswd, com.mobius.vdrapi.ViewDirectSeverityLevel.SL_ERROR );
              }
          }
      }

      if ( session == null ) {
          System.out.println( "No session established");
      }

      com.mobius.vdrapi.TopicListFactory tlf = (com.mobius.vdrapi.TopicListFactory)session.createListFactory(com.mobius.vdrapi.SessionFactoryType.Topic);

      INode topicid = null;
     com.mobius.vdrapi.QueryParam qParamtn = new com.mobius.vdrapi.QueryParam(this.m_topicId,
                                              com.mobius.vdrapi.ComparisonSpec.EqualTo,
                                              1);
      INode [] tn = tlf.createList(qParamtn);

      for ( int i = 0; i < tn.length; i++ ) {
          if ( ((String)tn[i].getKeyValue()).trim().equals(m_topicId)) {
            topicid = tn[i];
            break;
          }
      }
      if ( topicid == null )
        throw new com.mobius.vdrapi.exceptions.ViewDirectException("Couldn't find topic id", "" );

      INode topicv = null;
      com.mobius.vdrapi.QueryParam qpall = new com.mobius.vdrapi.QueryParam("",com.mobius.vdrapi.ComparisonSpec.First,
                                              1);

      INode [] tv = ((com.mobius.vdrapi.IListFactory)topicid).createList(qpall);
            topicv = tv[0];
            // use the current version
      if ( topicv == null )
        throw new com.mobius.vdrapi.exceptions.ViewDirectException("Couldn't find topic version", "" );
//dig around and find all the items for this query, then place the appropriate items
//in a string buffer as necessary, then cast the stringbuffer to a stringarray
   com.mobius.vdrapi.TopicItemEntryQueryParam qParamti = new com.mobius.vdrapi.TopicItemEntryQueryParam(m_topicItem, "", "", 0, com.mobius.vdrapi.ComparisonSpec.GreaterThanOrEqualTo, itemlimit);
   INode [] ti = ((com.mobius.vdrapi.IListFactory)topicv).createList(qParamti);
   int itemcount = 0;
   for(int i=0;i<ti.length;i++)
   {
    com.mobius.vdrapi.TopicItemEntryNode test_tien = (com.mobius.vdrapi.TopicItemEntryNode)ti[i];
    // Only get the items that match
    if(test_tien.getTopicItemName().substring(0,m_topicItem.length()).equals(m_topicItem))
    {
      itemcount ++;
    }
   }
   list = new String[itemcount][4];
      for (int i=0; i<itemcount; i++)
      {
          com.mobius.vdrapi.TopicItemEntryNode tien = (com.mobius.vdrapi.TopicItemEntryNode)ti[i];
          list[i][0] = tien.getReportId();
          list[i][1] = tien.getVersionId();
          list[i][2] = tien.getSectionName();
          Long temp = new Long(tien.getNumberOfPages());
          list[i][3] = temp.toString();
      }
      session.detach();
    }
    catch ( Exception e ) {
      e.printStackTrace();
    }
    return list;
  }


  public void setDocumentServerName(String documentServerName) {
    m_documentServerName = documentServerName;
  }

  public void setUserId(String userId) {
    m_userId = userId;
  }
  public void setPswd(String pswd) {
    m_pswd = pswd;
  }

  // Topic data
  public void setTopicId( String topicId) {
    m_topicId = topicId;
  }
  public void setTopicVersion( String topicVersion) {
    m_topicVersion = topicVersion;
  }
  public void setTopicItem(String topicItem) {
    m_topicItem = topicItem;
  }
  public void setTopicReport(String topicReport) {
    m_topicReport = topicReport;
  }
  public void setTopicReportVersion(String topicReportVersion) {
    m_topicReportVersion = topicReportVersion;
  }
  public void setTopicEntry(String topicEntry) {
    m_topicEntry = topicEntry;
  }
  public void setTopicPage(String topicPage) {
    m_topicPage = topicPage;
  }
  public static void main(String[] args){
  String[][] test = null;
  TopicSearch ts = new TopicSearch("VDR","ADMIN","");
  test = ts.listitems("ACCOUNT","1000090536318");
  for(int u = 0;u<test.length;u++)
      {
        System.out.print(test[u][0] + "\t");
        System.out.print(test[u][1] + "\t");
        System.out.print(test[u][2] + "\t");
        System.out.print(test[u][3] + "\t");
        System.out.print("\n");
      }
  }
  
private INode tri[];
private String [][] list = null;
}