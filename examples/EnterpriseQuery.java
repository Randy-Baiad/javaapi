/*
 * EnterpriseQuery.java
 *
 * Created on March 9, 2006, 10:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mobius;

import java.io.*;
import java.util.Vector;
import com.mobius.vdrapi.*;
import com.mobius.vdrapi.exceptions.*;
public class EnterpriseQuery implements IHasProfileLocation {
  public String getProfileLocation()  {
    return "ddrint.xml";
  }
  public String getPrimaryLocation()  {
    return "";
  }
  public String getSecondaryLocation()  {
    return "ddrint.ini";
  }
  public EnterpriseQuery(String document_server,String user,String password)
  {
      _server = document_server;
      _recipient = user;
      _password = password;
  }
  public String Connect()
  {
    try {
      DocumentServerFactory dsf = new DocumentServerFactory(this);
      DocumentServerQueryParam dsqParam = new DocumentServerQueryParam(_server,
                                              ComparisonSpec.EqualTo,
                                              1,
                                              SortOrder.Ascending,
                                              null);
      INode dsl[] = dsf.createList(dsqParam);
      if(dsl[0]==null)
        throw new Exception("No Server");
      session = null;

      IDocumentServer ds = (IDocumentServer)dsl[0];
	  session = new Session( ds, this._recipient, this._password, ViewDirectSeverityLevel.SL_ERROR );

      // Check for errors
      if ( session == null ) {
          System.err.println( "No session established");
      }else{
          System.out.println("Search session assigned: " + session);
      }
  }catch(Exception e)
    {
    e.printStackTrace();
    }
  return session.toString();
  }
//**************************************************************************************************************
  public String[][] listitems ( String topicId,
                                  String topicItemId,
                                  int itemlimit)   {
    try {
      DocumentServerFactory dsf = new DocumentServerFactory(this);
      DocumentServerQueryParam dsqParam = new DocumentServerQueryParam(_server,
                                              ComparisonSpec.EqualTo,
                                              1,
                                              SortOrder.Ascending,
                                              null);
      INode dsl[] = dsf.createList(dsqParam);
      if(dsl[0]==null)
        throw new Exception("No Server");
      Session session = null;

      IDocumentServer ds = (IDocumentServer)dsl[0];
	  session = new Session( ds, this._recipient, this._password, ViewDirectSeverityLevel.SL_ERROR );

      // Get a Search Topic Item List factory.
      SearchTopicItemListFactory stilf = (SearchTopicItemListFactory)
                                            session.createListFactory(SessionFactoryType.SearchTopicItems);

      // Find target Topic
      SearchTopicItemQueryParam qParam = new SearchTopicItemQueryParam(null,null,itemlimit);
      if (topicId != null && topicItemId != null) {
	  TopicSearchConstraint [] topicConstraints = new TopicSearchConstraint[1];
	  topicConstraints[0] = new TopicSearchConstraint ();
	  topicConstraints[0].topicIdConstraint.target1 = topicId;
	  topicConstraints[0].topicIdConstraint.comparison = SQLComparisonSpec.EqualTo;
	  topicConstraints[0].topicItemConstraint.target1 = topicItemId;
	  //topicConstraints[0].topicItemConstraint.comparison = SQLComparisonSpec.Like;
          topicConstraints[0].topicItemConstraint.comparison = SQLComparisonSpec.EqualTo;
	  qParam.setTopicSearchConstraints (topicConstraints);
      }

       // Execute the search
      INode [] searchResults = stilf.createList (qParam);

      if (searchResults == null || searchResults.length == 0)
        System.out.println ("\nNo pages matching search criteria found.");
      else {
              list = new String[searchResults.length][4];
		  for (int i=0; i< searchResults.length; i++)
                   {
                      list[i][0] = ((ISearchTopicItemResult)searchResults[i]).getReportInformation().reportId;
                      list[i][1] = ((ISearchTopicItemResult)searchResults[i]).getReportInformation().versionId;
                      list[i][2] = ((ISearchTopicItemResult)searchResults[i]).getReportInformation().sectionId;
                      Long temp = new Long(((ISearchTopicItemResult)searchResults[i]).getReportInformation().numberOfPages);
                      list[i][3] = temp.toString();
                    }
            }
	  session.detach ();	// terminate and clean up connections
    }
    catch (Exception e) {
        e.printStackTrace();
    }
     return list;
  }
//**************************************************************************************************************
  public String[][] listitems ( String topicId,
                                  String topicItemId)   {
    try {
      DocumentServerFactory dsf = new DocumentServerFactory(this);
      DocumentServerQueryParam dsqParam = new DocumentServerQueryParam(_server,
                                              ComparisonSpec.EqualTo,
                                              1,
                                              SortOrder.Ascending,
                                              null);
      INode dsl[] = dsf.createList(dsqParam);
      if(dsl[0]==null)
        throw new Exception("No Server");
      Session session = null;

      IDocumentServer ds = (IDocumentServer)dsl[0];
	  session = new Session( ds, this._recipient, this._password, ViewDirectSeverityLevel.SL_ERROR );

      // Get a Search Topic Item List factory.
      SearchTopicItemListFactory stilf = (SearchTopicItemListFactory)
                                            session.createListFactory(SessionFactoryType.SearchTopicItems);

      // Find target Topic
      SearchTopicItemQueryParam qParam = new SearchTopicItemQueryParam(null,null,9999);
      if (topicId != null && topicItemId != null) {
	  TopicSearchConstraint [] topicConstraints = new TopicSearchConstraint[1];
	  topicConstraints[0] = new TopicSearchConstraint ();
	  topicConstraints[0].topicIdConstraint.target1 = topicId;
	  topicConstraints[0].topicIdConstraint.comparison = SQLComparisonSpec.EqualTo;
	  topicConstraints[0].topicItemConstraint.target1 = topicItemId;
	  //topicConstraints[0].topicItemConstraint.comparison = SQLComparisonSpec.Like;
          topicConstraints[0].topicItemConstraint.comparison = SQLComparisonSpec.EqualTo;
	  qParam.setTopicSearchConstraints (topicConstraints);
      }

       // Execute the search
      INode [] searchResults = stilf.createList (qParam);

      if (searchResults == null || searchResults.length == 0)
        System.out.println ("\nNo pages matching search criteria found.");
      else {
              list = new String[searchResults.length][4];
		  for (int i=0; i< searchResults.length; i++)
                   {
                      list[i][0] = ((ISearchTopicItemResult)searchResults[i]).getReportInformation().reportId;
                      list[i][1] = ((ISearchTopicItemResult)searchResults[i]).getReportInformation().versionId;
                      list[i][2] = ((ISearchTopicItemResult)searchResults[i]).getReportInformation().sectionId;
                      Long temp = new Long(((ISearchTopicItemResult)searchResults[i]).getReportInformation().numberOfPages);
                      list[i][3] = temp.toString();
                    }
            }
	  session.detach ();	// terminate and clean up connections
    }
    catch (Exception e) {
        e.printStackTrace();
    }
     return list;
  }
//**************************************************************************************************************
  public int getNumberOfItems() {
    return list.length;
  }
//**************************************************************************************************************
public String getsession()
{
if(session.toString()!=null)
  {
    return session.toString();
  }
else
return "Nothing";
}
//**************************************************************************************************************
public static void main (String args[]) {
      EnterpriseQuery eq = new EnterpriseQuery ("VDR","ADMIN","");
      String[][] mylist;
      mylist = eq.listitems("ACCOUNT","9999999999",50);
      System.out.println("The size of the list is: " + eq.getNumberOfItems());
      for(int i=0;i<eq.getNumberOfItems();i++)
      {
      System.out.print(mylist[i][0]);
      System.out.print(mylist[i][1]);
      System.out.print(mylist[i][2]);
      System.out.println(mylist[i][3]);
      }
  }
private String _server = null;
private String _recipient = null;
private String _password = null;
private Session session = null;
private int count;
private String [][] list = null;
private int numitems;
}


