package com.friend.dao;

import org.dom4j.DocumentException;

import com.friend.util.DaoFactory;

/**
 * 
 * @author 梅刚 2014-8-2
 * 单例模式 
 *
 */
public class BaseDaoFactory {
	public static UserDao userDao;
	public static UserRoleDao userRoleDao;
	public static RoleDao roleDao;
	public static PermissionDao permissionDao;
	public static RolePermissionDao rolePermissionDao;
	public static FriendDao friendDao;
	public static FriendSortDao friendSortDao;
	public static TalkDao talkDao;
	public static CommentDao commentDao;
	public static ReplyDao replyDao;
	public static TalkCommentDao talkCommentDao;
	public static TalkPraiseDao talkPraiseDao;
	public static LogSortDao logSortDao;
	public static LogDao logDao;
	public static LogCommentDao logCommentDao;
	public static LogPraiseDao logPraiseDao;
	public static LogTransferDao logTransferDao;
	public static AlbumDao albumDao;
	public static PhotoDao photoDao;
	public static ModuleDao moduleDao;
	public static TopicDao topicDao;
	public static TopicCommentDao topicCommentDao;
	public static AttachmentDao attachmentDao;
	//私有化构造方法
	private BaseDaoFactory(){
		
	}
	static{
		//加载用到的模块,个人首页会用到talk,log,album
		try {
			DaoFactory.loadModuleDaoToFactory("user");
			DaoFactory.loadModuleDaoToFactory("album");
			DaoFactory.loadModuleDaoToFactory("talk");
			DaoFactory.loadModuleDaoToFactory("log");
			DaoFactory.loadModuleDaoToFactory("friend");
			DaoFactory.loadModuleDaoToFactory("bbs");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static UserDao getUserDao() {
		if(DaoFactory.factory.get("useDao")!=null)
			return (UserDao) DaoFactory.factory.get("useDao");
		else
			return new UserDao();
	}


	public static UserRoleDao getUserRoleDao() {
		if(DaoFactory.factory.get("useRoleDao")!=null)
			return (UserRoleDao) DaoFactory.factory.get("useRoleDao");
		else
			return new UserRoleDao();
	}


	public static RoleDao getRoleDao() {
		if(DaoFactory.factory.get("roleDao")!=null)
			return (RoleDao) DaoFactory.factory.get("roleDao");
		else
			return new RoleDao();
	}


	public static PermissionDao getPermissionDao() {
		if(DaoFactory.factory.get("permissionDao")!=null)
			return (PermissionDao) DaoFactory.factory.get("permissionDao");
		else
			return new PermissionDao();
	}


	public static RolePermissionDao getRolePermissionDao() {
		if(DaoFactory.factory.get("rolePermissionDao")!=null)
			return (RolePermissionDao) DaoFactory.factory.get("rolePermissionDao");
		else
			return new RolePermissionDao();
	}


	public static FriendDao getFriendDao() {
		if(DaoFactory.factory.get("friendDao")!=null)
			return (FriendDao) DaoFactory.factory.get("friendDao");
		else
			return new FriendDao();
	}


	public static FriendSortDao getFriendSortDao() {
		if(DaoFactory.factory.get("friendSortDao")!=null)
			return (FriendSortDao) DaoFactory.factory.get("friendSortDao");
		else
			return new FriendSortDao();
	}


	public static TalkDao getTalkDao() {
		if(DaoFactory.factory.get("talkDao")!=null)
			return (TalkDao) DaoFactory.factory.get("talkDao");
		else
			return new TalkDao();
	}


	public static CommentDao getCommentDao() {
		if(DaoFactory.factory.get("commentDao")!=null)
			return (CommentDao) DaoFactory.factory.get("commentDao");
		else
			return new CommentDao();
	}


	public static ReplyDao getReplyDao() {
		if(DaoFactory.factory.get("replyDao")!=null)
			return (ReplyDao) DaoFactory.factory.get("replyDao");
		else
			return new ReplyDao();
	}


	public static TalkCommentDao getTalkCommentDao() {
		if(DaoFactory.factory.get("talkCommentDao")!=null)
			return (TalkCommentDao) DaoFactory.factory.get("talkCommentDao");
		else
			return new TalkCommentDao();
	}


	public static TalkPraiseDao getTalkPraiseDao() {
		if(DaoFactory.factory.get("talkPraiseDao")!=null)
			return (TalkPraiseDao) DaoFactory.factory.get("talkPraiseDao");
		else
			return new TalkPraiseDao();
	}


	public static LogSortDao getLogSortDao() {
		if(DaoFactory.factory.get("logSortDao")!=null)
			return (LogSortDao) DaoFactory.factory.get("logSortDao");
		else
			return new LogSortDao();
	}


	public static LogDao getLogDao() {
		if(DaoFactory.factory.get("logDao")!=null)
			return (LogDao) DaoFactory.factory.get("logDao");
		else
			return new LogDao();
	}


	public static LogCommentDao getLogCommentDao() {
		if(DaoFactory.factory.get("logCommentDao")!=null)
			return (LogCommentDao) DaoFactory.factory.get("logCommentDao");
		else
			return new LogCommentDao();
	}


	public static LogPraiseDao getLogPraiseDao() {
		if(DaoFactory.factory.get("logPraiseDao")!=null)
			return (LogPraiseDao) DaoFactory.factory.get("logPraiseDao");
		else
			return new LogPraiseDao();
	}


	public static LogTransferDao getLogTransferDao() {
		if(DaoFactory.factory.get("logTransferDao")!=null)
			return (LogTransferDao) DaoFactory.factory.get("logTransferDao");
		else
			return new LogTransferDao();
	}


	public static AlbumDao getAlbumDao() {
		if(DaoFactory.factory.get("albumDao")!=null)
			return (AlbumDao) DaoFactory.factory.get("albumDao");
		else
			return new AlbumDao();
	}


	public static PhotoDao getPhotoDao() {
		if(DaoFactory.factory.get("photoDao")!=null)
			return (PhotoDao) DaoFactory.factory.get("photoDao");
		else
			return new PhotoDao();
	}


	public static ModuleDao getModuleDao() {
		if(DaoFactory.factory.get("moduleDao")!=null)
			return (ModuleDao) DaoFactory.factory.get("moduleDao");
		else
			return new ModuleDao();
	}


	public static TopicDao getTopicDao() {
		if(DaoFactory.factory.get("topicDao")!=null)
			return (TopicDao) DaoFactory.factory.get("topicDao");
		else
			return new TopicDao();
	}


	public static TopicCommentDao getTopicCommentDao() {
		if(DaoFactory.factory.get("topicCommentDao")!=null)
			return (TopicCommentDao) DaoFactory.factory.get("topicCommentDao");
		else
			return new TopicCommentDao();
	}


	public static AttachmentDao getAttachmentDao() {
		if(DaoFactory.factory.get("attachmentDao")!=null)
			return (AttachmentDao) DaoFactory.factory.get("attachmentDao");
		else
			return new AttachmentDao();
	}
	
	
}
