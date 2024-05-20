package com.mobius;
import java.io.*;
import java.util.Vector;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import com.mobius.vdrapi.command.CommandKeyType;
import com.mobius.vdrapi.command.CreatePageGroupCommand;
import com.mobius.vdrapi.RenderFormatType;
import com.mobius.vdrapi.command.ICommand.KeyValuePair;

/*
 * getpages.java
 *
 * Created on May 12, 2002, 10:18 AM
 */
/**
 *
 * @author  MMORRIS
 * @version
 * This servlet is designed to deliver pages to a browser, or to a zip file.
 */
public class getpages extends javax.servlet.http.HttpServlet implements com.mobius.vdrapi.callbacks.IHasLoginCredentials, com.mobius.vdrapi.IHasProfileLocation
 {

    /** Initializes the servlet.
    */
    public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
        super.init(config);
        pathname = config.getInitParameter("wpath");
    }

    /** Destroys the servlet.
    */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        m_documentServerName = request.getParameter("documentServerName");
        m_reportId = request.getParameter("reportID");
        m_reportVersion = request.getParameter("reportVersion");
        m_reportSection = request.getParameter("reportSection");
        m_reportPage = request.getParameter("reportPage");
        m_operation = request.getParameter("operation");
        m_pages = request.getParameter("pages");
        m_userid = request.getParameter("userid");
        m_password = request.getParameter("password");

        if(m_operation.equalsIgnoreCase("view"))
        {
                  StringBuffer temp_pages = new StringBuffer();
                 temp_pages.append("FROM ");
                  // allinsection does not work if the section info came from a topic
                  temp_pages.append(m_pages + " " + m_pages);
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat, RenderFormatType.HTML.toString()));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Zip, "FALSE"));


                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                  //response.setContentType("image/svg+xml");
                  //response.setContentType("application/pdf");
                  response.setContentType("text/html");

                     java.io.OutputStream sos = response.getOutputStream();
                     cmd.setOutputStream (sos);
                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            sos.flush();
                            response.flushBuffer();
                      }
                      catch (Exception e) {
                              System.err.println (e);
                              e.printStackTrace();
                      }
        }
        if(m_operation.equalsIgnoreCase("text"))
        {
                  StringBuffer temp_pages = new StringBuffer();
                 temp_pages.append("FROM ");
                  // allinsection does not work if the section info came from a topic
                  temp_pages.append(m_pages + " " + m_pages);
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat, RenderFormatType.TEXT.toString()));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Zip, "FALSE"));
                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                  //response.setContentType("image/svg+xml");
                  //response.setContentType("application/pdf");
                  response.setContentType("text/plain");

                     java.io.OutputStream sos = response.getOutputStream();
                     cmd.setOutputStream (sos);
                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            sos.flush();
                            response.flushBuffer();
                      }
                      catch (Exception e) {
                              System.err.println (e);
                              e.printStackTrace();
                      }
        }
        if(m_operation.equalsIgnoreCase("viewpdf"))
        {
                  StringBuffer temp_pages = new StringBuffer();
                 temp_pages.append("FROM ");
                  // if you use more than 1 page, pdf will work
                  // allinsection does not work if the section info came from a topic
                  temp_pages.append(1 + " " + m_pages);
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat, RenderFormatType.PDF.toString()));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Zip, "FALSE"));
                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                  //response.setContentType("image/svg+xml");
                  response.setContentType("application/pdf");
                  //response.setContentType("text/html");


                     java.io.OutputStream sos = response.getOutputStream();
                     cmd.setOutputStream (sos);
                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            sos.flush();
                            response.flushBuffer();
                      }
                      catch (Exception e) {
                              System.err.println (e);
                              e.printStackTrace();
                      }
        }
//*********************************************************************************
       if(m_operation.equalsIgnoreCase("check"))
        {
                    String side = request.getParameter("side");
                    response.setContentType("image/png");
                  StringBuffer temp_pages = new StringBuffer();
                  if (side.equalsIgnoreCase("front"))
                      temp_pages.append("FROM 1 1");
                  else
                      temp_pages.append("FROM 2 2");
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat,RenderFormatType.PNG.toString()));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Width,"580"));
                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                     java.io.OutputStream sos = response.getOutputStream();
                     cmd.setOutputStream (sos);

                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            sos.flush();
                            response.flushBuffer();
                      }
                      catch (Exception e) {
                              System.err.println (e);
                      }
        }
//*********************************************************************************
        if(m_operation.equalsIgnoreCase("checkpdf"))
        {
                  StringBuffer temp_pages = new StringBuffer();
                  temp_pages.append("FROM ");
                  temp_pages.append(1 + " " + 2);
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat, RenderFormatType.PDF.toString()));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Zip, "FALSE"));
                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                  response.setContentType("application/pdf");

                     java.io.OutputStream sos = response.getOutputStream();
                     cmd.setOutputStream (sos);
                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            sos.flush();
                            response.flushBuffer();
                      }
                      catch (Exception e) {
                              System.err.println (e);
                      }
        }
//*************************************************************************************************************************
        if(m_operation.equalsIgnoreCase("test"))
        {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Test getpages Servlet</title>");
        out.println("</head>");
        out.println(this.doExport());
        out.println("<body>");
        out.println("Done, check the directory dude.");
        out.println("</body>");
        out.println("</html>");
        out.close();
        }
        if(m_operation.equalsIgnoreCase("download"))
        {
                  StringBuffer temp_pages = new StringBuffer();
                  temp_pages.append("FROM 1 ");
                  temp_pages.append(m_pages);
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat, RenderFormatType.PDF.toString()));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Zip, "TRUE"));

                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream ();
                    //response.setContentType("application/zip");
                    //response.setContentType("application/zip;name=testfile.zip");
                    //response.setHeader("Content-Disposition", "inline;filename=testfile.zip");
                    String zipContentTypeString = "\"application/zip;name=" + m_reportSection.substring(0,12) + ".zip\"";
                    String zipHeaderString = "\"inline;filename=" + m_reportSection.substring(0,12) + ".zip\"";
                    response.setContentType(zipContentTypeString);
                    response.setHeader("Content-Disposition", zipHeaderString);

                    javax.servlet.ServletOutputStream sos = response.getOutputStream();
                  cmd.setOutputStream (sos);

                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            sos.flush();
                            response.flushBuffer();
                      }
                      catch (Exception e) {
                              System.err.println (e);
                      }
        }
        if(m_operation.equalsIgnoreCase("csv"))
        {
            String documentServerId = request.getParameter("documentServer");
                    String reportId = request.getParameter("reportId");
                    String versionId = request.getParameter("versionId");
                    String pageSpec = request.getParameter("pageSpec");
                    response.setContentType("text/html");
                  StringBuffer temp_pages = new StringBuffer();
                  temp_pages.append("FROM 1 ");
                  temp_pages.append(m_pages);
                  String tPages = temp_pages.toString();

                      CreatePageGroupCommand cmd = new CreatePageGroupCommand ();
                      Vector keyValuePairs = new Vector ();
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.DocumentServer, m_documentServerName));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.ReportId, m_reportId));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.VersionId, m_reportVersion));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.Pages, tPages));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.PolicyId,"c:\\demos\\policy\\INVOICE.PLC"));
                      keyValuePairs.addElement(new KeyValuePair(CommandKeyType.ColumnHeader,"TRUE"));
                      keyValuePairs.addElement (new KeyValuePair (CommandKeyType.OutputFormat,RenderFormatType.CSV.toString()));
                  cmd.setObjectWithProfileLocation (this);
                  cmd.setObjectWithLoginCredentials (this);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream ();
                  cmd.setOutputStream (baos);

                  try {
                            cmd.load (keyValuePairs);
                            cmd.execute ();
                            String vfile = request.getSession().getId();
                            java.io.File f = new java.io.File(pathname + vfile + ".csv");
                            f.createNewFile();
                            FileOutputStream rfoutstream = new FileOutputStream (f);
                            rfoutstream.write (baos.toByteArray());
                            rfoutstream.close();
                            response.sendRedirect("/" + vfile + ".csv");
                      }
                      catch (Exception e) {
                              System.err.println (e);
                      }
        }
    }

    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Deliver pages to browser, or zip file via download.";
    }

    public java.lang.String getProfileLocation() {
            return "ddrint.xml"; //required for Unix
    }
    public java.lang.String getPrimaryLocation() {
            return ""; //required for Unix
    }
    public java.lang.String getSecondaryLocation() {
            return "ddrint.ini";
    }
    public void getLoginCredentials(java.lang.String documentServerName, java.lang.StringBuffer userID,
        java.lang.StringBuffer pwd) throws com.mobius.vdrapi.exceptions.UserAbortException {
//      System.out.println("Doc Server is:" + m_documentServerName + "\tUser is:" + m_userid + "\tPassword is:" + m_password);
//      documentServerName = m_documentServerName;
      userID.setLength(0);
      pwd.setLength(0);
      userID.append(m_userid);
      pwd.append(m_password);
    }
    public String doExport() {
        try {

          CreatePageGroupCommand pageGroup = new CreatePageGroupCommand();
          Vector keyValuePairs = new Vector();
          // Set the above values from TopicItemSearchResult.ReportData, and use them in the following
          StringBuffer temp_pages = new StringBuffer();
          temp_pages.append("FROM 1 ");
          temp_pages.append(m_pages);
          String tPages = temp_pages.toString();
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.DocumentServer, m_documentServerName));
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.ReportId,m_reportId));
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.SectionId,m_reportSection));
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.VersionId,m_reportVersion));
          // Setup Options
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.OutputFormat,RenderFormatType.PDF.toString()));
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.Pages,tPages));
          keyValuePairs.addElement( new KeyValuePair(CommandKeyType.Zip,"true"));
          Long nbr = new Long(System.currentTimeMillis());
          fn = nbr + ".zip";
          FileOutputStream downloadFile = new FileOutputStream(fn);

          pageGroup.setObjectWithProfileLocation(this);
          pageGroup.setObjectWithLoginCredentials(this);
          pageGroup.setOutputStream(downloadFile);
          pageGroup.load(keyValuePairs);
          pageGroup.execute();
          downloadFile.flush();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        return fn;
      }
private String m_userid = null;
private String m_password = null;
private String m_documentServerName = null;
private String m_reportId = null;
private String m_reportVersion = null;
private String m_reportSection = null;
private String m_reportPage = null;
private String m_operation = null;
private String m_pages = null;
private String fn = null;
private String pathname = null;
}