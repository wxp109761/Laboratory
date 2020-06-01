package com.example.laboratory.common;

/**
 * Api接口地址
 */
public class UrlConstainer {
    public static final String baseUrl = "http://101.37.28.44:9001/";
    /**
     * 登录
     */
    public static final String LOGIN = "user/login";
    /**
     * 注册
     */
    public static final String ADDUSER = "user";
    public static final String GETDEPARTILIST = "depart";
    /**
     * 根据用户id获取巡检记录
     */
    public static final String  GETRECORDLIST = "xjrecord/mine/{uid}";

    /**
     * 条件查询获取实验室列表
     */
    public static final String GETLABORATORYLIST = "laboratory/search/{page}/{size}";
    public static final String GETLABSBYUID = "laboratory/mine/{uid}";
    public static final String GETLABSBYDEPARTID= "laboratory/byDepartId/{depart_id}";
    public static final String GETALLLABS = "laboratory";
    /**
     * 删除实验室及其实验室对应检测条目，及巡检记录，巡检结果集
     */
    public static final String DELETELABBYID= "laboratory/{labid}";
    /**
     * 获取巡检结果集
     */
    public static final String GETRESULTBYXJID = "/xjresult/QueryById/{xjid}";
    public static final String  GETITEMSBYLABID="laboratory/getItems/{labid}";
    /**
     * 根据belong查询items
     */
    public static final String GETITEMS= "item/queryByBelong/{belong}";
    public static final String ADDITEMS= "item";
    public static final String GETAllITEMS= "item";
    public static final String ADDRESULTLIST="xjresult/addlist";
    public static final String ADDRECORD="xjrecord/";
    public static final String ADDLABS = "laboratory";
    /**
     * 根据depart和permission获取用户列表
     */
    public static final String GETUSERDEPARTID = "user/getUserList/{departId}/{permission}";
    public static final String GETUSERPERMISSIONNOT = "user/permissionNot/{permission}";
    public static final String GETALLUSERS = "user";

    public static final String GETUSEREXCEPTSELF= "user/exceptSelf/{uid}";
    public static final String UPDATEUSERINFO= "user/{uid}";

    public static final String ADDLABITEMRELATIONS = "laboratory/addLabItemRelations/{labId}/{itemId}";
    /**
     * 删除巡检记录及该记录结果集
     */

    public static final String DELETEXJRECORDBYID = "xjrecord/{id}";

    //公告
    public static final String GETALLNOTICES = "/notices/getAllNotices/{send_depart}";
    public static final String GETBYSENDDEPART= "/notices/getBySendDepart/{send_depart}";
    public static final String ADMINGETBYSENDDEPART= "/notices/adminGetAllNotices/{send_id}/{send_depart}";
    public static final String GETBYSENDID= "/notices/getNoticesBySendId/{send_id}";
    public static final String ADDNOTICES ="/notices/{send_id}" ;
    public static final String SETREAD ="/notices/setIsRead/{id}/{userId}" ;
    public static final String GETUNREADNOTICES ="/notices/getUnReadNotices/{uid}" ;

    //站内信箱
    public static final String GETALLMESSAGES = "/messages/getMessage/{uid}";
    public static final String SENDMESSAGE="/messages/{send_id}/{receive_id}" ;
    public static final String SETMESSAGEREAD="/messages/setIsRead/{id}/{userId}" ;
    public static final String GETUNREADMESSAGES ="/messages/getUnReadMessage/{uid}" ;

    //待办提醒
    public static final String ADDREMIND= "/remind";
    public static final String GETREMINDBYBYUID = "remind/findByUid/{uid}";
    public static final String DELETEREMINDBYID = "remind/{id}";
    public static final String UPDATEREMIND= "/remind/{id}";

}
