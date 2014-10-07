package com.friend.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.friend.service.biz.AttachmentService;
import com.friend.service.biz.ModuleService;
import com.friend.service.biz.TopicService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IAttachment;
import com.friend.service.ibiz.IModule;
import com.friend.service.ibiz.ITopic;
import com.friend.service.ibiz.IUser;
import com.friend.util.LogUtil;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.StringUtil;
import com.friend.vo.Attachment;
import com.friend.vo.Module;
import com.friend.vo.Topic;
import com.friend.vo.User;
/**
 * Servlet implementation class TopicServlet
 */
@WebServlet("/topic.do")
public class TopicServlet extends FrameworkServlet{
	private static final long serialVersionUID = 1L;
       private IUser iUser;
       private IModule iModule;
       private ITopic iTopic;
       private Pager page;
       private IAttachment iAttachement;
       public TopicServlet()
       {
    	   iModule=new ModuleService();
    	   iTopic=new TopicService();
    	   iUser=new UserService();
    	   iAttachement = new AttachmentService();
    	   page=new Pager();
       }
       
      public String showTopicInModule(HttpServletRequest request, HttpServletResponse response)
      {
    	  int id=Integer.parseInt(request.getParameter("moduleId"));
    	  System.out.println(id);
    	  Module module=iModule.getModuleById(id);
    	  int currentPage=1;
  		  if(request.getParameter("currentPage")!=null)
  		  currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
  		  int totalCount=iTopic.getTopicCountInModule(module);
  		  page.paging(currentPage, ParamUtil.TOPIC_PAGE_SIZE, totalCount);
    	  
    	  List<Topic> topics=iTopic.makeTopicListFromModule(module,page);
    	  request.setAttribute("listTopics", topics);
    	  request.setAttribute("module", module);
    	  request.setAttribute("page",page);
    	  if(request.getParameter("toWhere")!=null)
    	  {
    		  return "backClient/bbs/b_topic.jsp";
    	  }
    	  else
    		  return "frontClient/bbs/topic.jsp";
      }

      public void deleteTopic(HttpServletRequest request, HttpServletResponse response) throws IOException
      {
    	  int topicId=Integer.parseInt(request.getParameter("topicId").toString());
    	  int moduleId=Integer.parseInt(request.getParameter("moduleId").toString());
    	  System.out.println(topicId);
    	  Topic topic=iTopic.getTopicById(topicId);
    	  if(request.getParameter("toWhere")==null)
    	  {
	    	  if(topic==null)
	    	  {
	    		 
	    		  response.sendRedirect("topic.do?action=showTopicInModule&moduleId="+moduleId);
	    	  }
	    	  else
	    	  {
	    		  
	    		  iTopic.deleteTopic(topic);
	    	  }
	    	  iTopic.deleteTopic(topic);
	    	  response.sendRedirect("topic.do?action=showTopicInModule&moduleId="+moduleId);
    	  }
    	  else
    	  {
    		  if(topic==null)
	    	  {
	    		 
	    		  response.sendRedirect("topic.do?action=showTopicInModule&toWhere=back&moduleId="+moduleId);
	    	  }
	    	  else
	    	  {
	    		  
	    		  iTopic.deleteTopic(topic);
	    	  }
    		  iTopic.deleteTopic(topic);
	    	  response.sendRedirect("topic.do?action=showTopicInModule&toWhere=back&moduleId="+moduleId);
    	  }
    		  
      }
      /**
       * 跳转到发帖页面
       * @param r
       * @param res
       * @return
       */
      public String toAddTopicUI(HttpServletRequest r,HttpServletResponse res){
    	  r.setAttribute("moduleId", r.getParameter("moduleId"));
    	  return "frontClient/bbs/addTopic.jsp";
      }
      
      /**
       * 发表帖子带有一个附件的
       * @param r
       * @param res
       * @return
     * @throws Exception 
       */
      public void publishTopicWithAtt(HttpServletRequest r,HttpServletResponse res) throws Exception{
    	  	Topic topic = new Topic();
    	  	String moduleId = "";
    	  	int haveTwo = 0;
    	  	String resMessage = "";
    	  	topic.setPublishTime(new Date());
    	  	User u = (User) r.getSession().getAttribute("user");
    	  	topic.setUser(u);
    	  	String userId = u.getUserId() +"";
    	  	Attachment attachment = null;
    	  	FileItemFactory factory = new DiskFileItemFactory();
	  		ServletFileUpload upload = new ServletFileUpload(factory);
	  		
  			List<FileItem> items= upload.parseRequest(r);
  			for(FileItem item : items){
  				//如果是字段
  				if(item.isFormField()){
  					String fieldName = item.getFieldName();
  					if(fieldName.equals("topicTitle")){
  						String topicTitle = item.getString();
  						topicTitle = StringUtil.handleISO88591ToUTF(topicTitle);
  						topic.setTopicTitle(topicTitle);
  					}else if(fieldName.equals("content")){
  						String content = item.getString();
  						content = StringUtil.handleISO88591ToUTF(content);
  						topic.setContent(content);
  					}else if(fieldName.equals("moduleId")){
  						moduleId = item.getString();
  						Module m = new Module();
  						m.setModuleId(Integer.parseInt(moduleId));
  						topic.setModule(m);
  					}
  				}
  				//如果是文件
  				if(!item.isFormField()){
  					
  					String fileName = item.getName();
  					//System.out.println(fileName);
  					LogUtil.logger.info("上传帖子附件:"+fileName);
  					if(fileName.length()>0){
  						//有附件
  						attachment = new Attachment();
  						attachment.setAttachmentName(fileName);
  	  					attachment.setAttachmentSize(item.getSize());
  	  					attachment.setUploadTime(new Date());
  	  					String extention = StringUtil.getFileExtention(fileName);
  	  					//判断扩展名
  	  					if(extention.equals(".rar") || extention.equals(".zip")){
  	  						haveTwo++;
  	  					}else{
  	  						resMessage += "请选择rar或者zip类型的压缩文件\n";
  	  					}
  	  					//判断附件大小
  	  					if(item.getSize() > ParamUtil.UPLOAD_TOPIC_ATT_SIZE*1024*1024){
  	  						resMessage += "您上传的文件超过10M";
  	  					
  	  					}else{
  	  						haveTwo++;
  	  					}
  	  					if(haveTwo==2){
  	  						//将该文件上传到/upload/userId/attachment/2014-7-27/下
  	  	  					String root = r.getSession().getServletContext().getRealPath("/");//D:\Tomcat 7.0\wtpwebapps\Friend\
  	  	  					//上传到upload/userId/photo/2014-7-25/file.jpg
  	  	  					String currentDate = StringUtil.getCurrentDateInDir();
  	  	  					
  	  	  					String uploadDir = "upload/"+ userId + "/attachment/" + currentDate + "/";
  	  	  					String relativePath = uploadDir + UUID.randomUUID()+ extention;
  	  	  					attachment.setAttachmentPath(relativePath);
  	  	  					String serverDirPath = root + uploadDir;
  	  	  					File dir = new File(serverDirPath);
  	  	  					if(!(dir.isDirectory() && dir.exists())){
  	  	  						dir.mkdirs();
  	  	  					}
  	  	  					File file = new File(root+relativePath);
  	  	  					item.write(file);
  	  					}
  					}
  				}
  				LogUtil.logger.info("fileupload:"+item.toString());
  			}
  			
  			if(haveTwo!=2 && attachment!=null){
  				//如果上传的附件不符合要求
  				r.setAttribute("res_message", resMessage);
  				r.setAttribute("topic", topic);
  				r.getRequestDispatcher("topic.do?action=toAddTopicUI&moduleId="+moduleId).forward(r, res);
  			}else{
  				//将topic存入到数据库中
  	  			Topic theAddTopic = iTopic.publishTopic(topic);
  	  			//如果有附件的话，就上传
  	  			if(attachment!=null){
  	  				//将该topic的attchment存入到数据库中
  	  	  			attachment.setTopic(theAddTopic);
  	  				iAttachement.uploadAttachment(attachment);
  	  				
  	  			}
  	  			r.getRequestDispatcher("topic.do?action=showTopicInModule&moduleId="+moduleId).forward(r, res);
  			}
  			
      }
}
