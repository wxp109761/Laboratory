package com.example.laboratory.api;


import com.example.laboratory.bean.*;
import com.example.laboratory.common.UrlConstainer;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;


/**
 * 接口
 * Created by 位展朋 on.2020.2.24
 */
public interface ApiServer {
    /**
     * 登录
     * @param body
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.LOGIN)
    Observable<BaseBean<User>> login(@Body RequestBody body);


    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETRECORDLIST)
    Observable<BaseBean<Record>> getRecordList(@Header("Authorization") String token,@Path("uid") String uid);


    /**
     * 条件查询实验室列表
     * @param token
     * @param page
     * @param size
     * @param body
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.GETLABORATORYLIST)
    Observable<BaseBean<Laboratory>> getLaboratoryList(@Header("Authorization") String token, @Path("page") int page, @Path("size") int size,@Body RequestBody body);

    /**
     * 根据uid获取实验室列表-->个人实验室
     * @param token
     * @param uid
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETLABSBYUID)
    Observable<BaseBean<LaboratoryList>> getLabsByUid(@Header("Authorization") String token, @Path("uid") String uid);

    /**
     * 学院实验室列表
     * @param token
     * @param depart_id
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETLABSBYDEPARTID)
    Observable<BaseBean<LaboratoryList>> getLabsByDepart(@Header("Authorization") String token, @Path("depart_id") String depart_id);

    /**
     * 学院实验室列表
     * @param token
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETALLLABS)
    Observable<BaseBean<LaboratoryList>> getAllLabs(@Header("Authorization") String token);

    @Headers("Content-Type:application/json")
    @DELETE(UrlConstainer.DELETELABBYID)
    Observable<BaseBean<String>> deleteLabById(@Header("Authorization") String token,@Path("labid") String labid);


    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETDEPARTILIST)
    Observable<BaseBean<Depart>> getDepartInfo(@Header("Authorization") String token);



    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETRESULTBYXJID)
    Observable<BaseBean<Result>> getResultByXjId(@Header("Authorization") String token,@Path("xjid") String xjid);


    /**
     * 获取items
     * @param token
     * @param labid
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETITEMSBYLABID)
    Observable<BaseBean<Items>> getItemsByLabId(@Header("Authorization") String token,@Path("labid") String labid);


    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETITEMS)
    Observable<BaseBean<Items>> getItemsByBelong(@Header("Authorization") String token,@Path("belong") String belong);

    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETAllITEMS)
    Observable<BaseBean<Items>> getAllItems(@Header("Authorization") String token);




    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDRESULTLIST)
    Observable<BaseBean<String>> addResultList(@Body RequestBody body);



    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDRECORD)
    Observable<BaseBean<String>> addRecord(@Body RequestBody body);



    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDLABS)
    Observable<BaseBean<String>> addLaboratory(@Body RequestBody body);






    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETUSERDEPARTID)
    Observable<BaseBean<UserList>> getUsersByDepartId(@Header("Authorization") String token,@Path("departId") String departId,@Path("permission") String permission);

    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETUSERPERMISSIONNOT)
    Observable<BaseBean<UserList>> getUsersPermissionNot(@Header("Authorization") String token,@Path("permission") String permission);

    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETUSEREXCEPTSELF)
    Observable<BaseBean<UserList>> getUserExceptSelf(@Header("Authorization") String token,@Path("uid") String uid);


    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETALLUSERS)
    Observable<BaseBean<UserList>> getAllUsers(@Header("Authorization") String token);




    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDUSER)
    Observable<BaseBean<String>> AddUser(@Header("Authorization") String token,@Body RequestBody body);

    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDLABITEMRELATIONS)
    Observable<BaseBean<String>> AddLabItemRelaitions(@Header("Authorization") String token,@Path("labId") String labId,@Path("itemId") Integer itemId);

    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDITEMS)
    Observable<BaseBean<String>> AddItems(@Header("Authorization") String token,@Body RequestBody body);




    @Headers("Content-Type:application/json")
    @DELETE(UrlConstainer.DELETEXJRECORDBYID)
    Observable<BaseBean<String>>DeleteXjRecordById(@Header("Authorization") String token,@Path("id") String id);



    //公告消息系统

    //发布公告
    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.ADDNOTICES)
    Observable<BaseBean<String>> AddNotice(@Header("Authorization") String token,@Body RequestBody body,@Path("send_id") String send_id);

    //根据创建时间排序查询所有公告
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETALLNOTICES)
    Observable<BaseBean<Notices>> getAllNotices(@Header("Authorization") String token,@Path("send_depart") Integer send_departId);

    //管理员查询所有公告
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.ADMINGETBYSENDDEPART)
    Observable<BaseBean<Notices>> adminGetAllNotices(@Header("Authorization") String token,@Path("send_id") String send_id,@Path("send_depart") String send_depart);

    //查询学院发布的公告
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETBYSENDDEPART)
    Observable<BaseBean<Notices>> getBySendDepart(@Header("Authorization") String token,@Path("send_depart") String send_depart);
    //查询个人发布的公告
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETBYSENDID)
    Observable<BaseBean<Notices>> getBySendId(@Header("Authorization") String token,@Path("send_id") String send_id);


    //查询所有未读公告
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETUNREADNOTICES)
    Observable<BaseBean<Notices>> getUnReadNotices(@Header("Authorization") String token,@Path("uid") String uid);




    //设置已读公告
    @Headers("Content-Type:application/json")
    @PUT(UrlConstainer.SETREAD)
    Observable<BaseBean<String>> setRead(@Header("Authorization") String token,@Path("id") Integer id,@Path("userId") String userId);

    //发消息
    @Headers("Content-Type:application/json")
    @POST(UrlConstainer.SENDMESSAGE)
    Observable<BaseBean<String>> sendMessage(@Header("Authorization") String token,@Body RequestBody body,@Path("send_id") String send_id,@Path("receive_id") String receive_id);

    //查询所有消息
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETALLMESSAGES)
    Observable<BaseBean<Messages>> getAllMessages(@Header("Authorization") String token,@Path("uid") String uid);

    //查询所有未读公告
    @Headers("Content-Type:application/json")
    @GET(UrlConstainer.GETUNREADMESSAGES)
    Observable<BaseBean<Messages>> getUnReadMessages(@Header("Authorization") String token,@Path("uid") String uid);

    //设置消息已读
    @Headers("Content-Type:application/json")
    @PUT(UrlConstainer.SETMESSAGEREAD)
    Observable<BaseBean<String>> setMessageRead(@Header("Authorization") String token,@Path("id") String id,@Path("userId") String userId);



}
