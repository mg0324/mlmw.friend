package com.friend.service.ibiz;
import java.util.List;
import java.util.Map;



import com.friend.util.Pager;
import com.friend.vo.Module;
/**
* @author 宋启东 2014 07 17 15：06
 */
import com.friend.vo.Topic;

public interface ITopic {
  public Topic publishTopic(Topic top); 
  public void lookTopic(int id);
  public List<Topic> makeTopicListFromModule(Module module ,Pager pager);
  public void deleteTopic(Topic topic);
  public Topic getTopicById(int topicId);
  /**
   * 得到所有的module下的topic数量
   * @param module
   * @return
   */
  public int getTopicCountInModule(Module module);
  public void getCommentCount(Topic topic);

}

