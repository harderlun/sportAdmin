package com.sanshao.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.entity.SysUserRole;
import com.sanshao.service.SysUserRoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * WebSocket：即时Web应用程序
 */
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {

    static Log log=LogFactory.get(WebSocketServer.class);
    //用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<Long,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private Long userId= null;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") Long userId) {
        if (userId != null){
            this.session = session;
            this.userId=userId;
            if(webSocketMap.containsKey(userId)){
                webSocketMap.remove(userId);
            }
            webSocketMap.put(userId,this);
            try {
                sendMessage("连接成功");
            } catch (IOException e) {
                log.error("用户:"+userId+",网络异常!!!!!!");
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (userId != null){
            if(webSocketMap.containsKey(userId)){
                webSocketMap.remove(userId);
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("用户消息:"+userId+",报文:"+message);
//        //可以群发消息
//        //消息保存到数据库、redis
//        if(StringUtils.isNotBlank(message)){
//            try {
//                //解析发送的报文
//                JSONObject jsonObject = JSON.parseObject(message);
//                //追加发送人(防止串改)
//                jsonObject.put("fromUserId",this.userId);
//                String toUserId=jsonObject.getString("toUserId");
//                //传送给对应toUserId用户的websocket
//                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
//                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
//                }else{
//                    log.error("请求的userId:"+toUserId+"不在该服务器上");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     * */
    public void sendInfo(String message,@PathParam("userId") Long userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(userId != null && webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    /**
     * 实时发送租借通知给管理员
     */
    public void sendBorrowNotice(String message) throws IOException {
        SysUserRoleService sysUserRoleService = applicationContext.getBean(SysUserRoleService.class);
        for(Long userId : webSocketMap.keySet()){
            List<SysUserRole> roleList = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,userId));
            for (SysUserRole role : roleList){
                if (role.getRoleId() == 6 || role.getRoleId() == 13){
                    webSocketMap.get(userId).sendMessage(message);
                    break;
                }
            }
        }
    }
}

