package com.friend.service.ibiz;

import java.util.List;

import com.friend.vo.Attachment;
import com.friend.vo.Topic;

/**
 * 
 * @author 宋启东 2014 07 17 15：20
 *
 */
public interface IAttachment {
  public void lookAttachment();//查看附件
  public Attachment downloadAttachment(Attachment a);//下载附件
  public void uploadAttachment(Attachment a);//上传附件
  
  public List<Attachment> getAttListInTopic(Topic topic);
}
