package com.friend.dao;

/**
 * 
 * @author 宋启东 2014 07 24 15：27
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Property;
import com.friend.vo.Attachment;
import com.friend.vo.Topic;

public class AttachmentDao extends BaseDao {
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Attachment");
	}
	
	// 查看附件
	/**
	 * 该方法用于检索所有附件
	 * 
	 * @return list
	 */
	public List<Attachment> findAllAttachment() {
		String sql = "select * from t_attachment";
		return makeAttachmentListFromListMap(find(sql, null));
	}

	/**
	 * 该方法用于检索所有Attachment对象
	 * 
	 * @param listMaps
	 * @return
	 */
	public List<Attachment> makeAttachmentListFromListMap(List<Map<String, Object>> listMaps) {
		// TODO Auto-generated method stub
		List<Attachment> Attachments = new ArrayList<Attachment>();
		for (Map<String, Object> map : listMaps) {
			Attachment attachment = makeAttachmentFromListMap(map);
			// 添加到Attachments集合
			Attachments.add(attachment);
		}
		return Attachments;

	}

	/**
	 * 该方法用于封装Attachment对象
	 * 
	 * @param map
	 * @return Attachment对象
	 */
	public Attachment makeAttachmentFromListMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		if (map == null) {
			return null;
		}
		Attachment attachment = new Attachment();
		
		doSetValue(map, attachment, list);
		return attachment;
	}

	/**
	 * 上传附件就是向数据库中添加一条记录并且把文件传输到服务器里面
	 *
	 */
	public Attachment uploadAttachment(Attachment att) {
		String sql = "insert into t_attachment"
				+ "(attachment_name,attachment_size,attachment_path,upload_time,topic_id) "
				+ "values(?,?,?,?,?)";
		Object[] prarms = { att.getAttachmentName(), 
				att.getAttachmentSize(),
				att.getAttachmentPath(),
				att.getUploadTime(),
				att.getTopic().getTopicId() };
		
		int	a = update(sql, prarms);
		if (a > 0) {
			return att;
		} else
			return null;
	}
	/**
	 * 查询得到该主题的附件列表
	 * @param topic
	 * @return
	 */
	public List<Attachment> getAttachmentList(Topic topic) {
		// TODO Auto-generated method stub
		String sql = "select * from t_attachment where topic_id=?";
		Object[] params = {topic.getTopicId()};
		return makeAttachmentListFromListMap(find(sql, params));
	}
	/**
	 * 得到Attachment对象通过id
	 * @param a
	 * @return
	 */
	public Attachment getAttachmentById(Attachment a) {
		// TODO Auto-generated method stub
		String sql = "select * from t_attachment where id=?";
		Object[] params = {a.getAttachmentId()};
		return makeAttachmentFromListMap(findObject(sql, params));
	}
}