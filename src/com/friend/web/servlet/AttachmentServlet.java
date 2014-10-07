package com.friend.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.AttachmentService;
import com.friend.service.ibiz.IAttachment;
import com.friend.vo.Attachment;

/**
 * 
 * @author 宋启东 2014-7-24
 *
 */
@WebServlet("/attachment.do")
public class AttachmentServlet extends FrameworkServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IAttachment iAttachment;
	public AttachmentServlet(){
		iAttachment = new AttachmentService();
	}	
	/**
	 * 上传附件
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void uploadHead(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	/**
	 * 下载附件
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String attId = request.getParameter("attachmentId");
		Attachment att = new Attachment();
		att.setAttachmentId(Integer.parseInt(attId));
		att = iAttachment.downloadAttachment(att);
		String root = request.getSession().getServletContext().getRealPath("/");
		String serverAttPath = root + att.getAttachmentPath();
		System.out.println("下载文件路径："+serverAttPath);
		
		// path是指欲下载的文件的路径。
        File file = new File(serverAttPath);
        if(file.exists()){
        	// 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            System.out.println(att.getAttachmentName());
            response.setContentType("application/octet-stream;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(att.getAttachmentName()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        }
        
	}
}
