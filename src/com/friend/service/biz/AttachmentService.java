package com.friend.service.biz;
import java.util.List;

import com.friend.dao.AttachmentDao;
import com.friend.service.ibiz.IAttachment;
import com.friend.util.DaoFactory;
import com.friend.vo.Attachment;
import com.friend.vo.Topic;
/**
 * 
 * @author 宋启东 2014 07 24 09:15
 *
 */
public class AttachmentService implements IAttachment {
	private static AttachmentDao attachmentDao;
	static{
		attachmentDao = (AttachmentDao) DaoFactory.factory.get("attachmentDao");
	}
	public AttachmentService() {
		// TODO Auto-generated constructor stub
		attachmentDao = new AttachmentDao();
	}
	@Override
	public void lookAttachment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attachment downloadAttachment(Attachment a) {
		// TODO Auto-generated method stub
		return attachmentDao.getAttachmentById(a);
	}

	@Override
	public void uploadAttachment(Attachment a) {
		// TODO Auto-generated method stub
		attachmentDao.uploadAttachment(a);
	}
	@Override
	public List<Attachment> getAttListInTopic(Topic topic) {
		// TODO Auto-generated method stub
		
		return attachmentDao.getAttachmentList(topic);
	}
    

}
