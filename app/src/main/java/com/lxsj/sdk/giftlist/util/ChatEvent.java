package com.lxsj.sdk.giftlist.util;

import java.io.Serializable;


/**
 * Created by zoran on 15-7-8.
 */
public class ChatEvent implements Serializable {

	private String uid; // 用户ID
	private String roomId; // 直播ID
	private int action; // 1=聊天消息，2=用户点赞，3=直播结束，4=用户加入直播
						// 5=用户退出直播,6=用户禁言,7=强制下线直播,8主播结束副主播的直播
	                    //9=聊天室置顶消息，10：type==1 点赞火箭,type=2 礼物
	private int type;// 0=系统文本消息 ，1=文本消息，2=语音消息(voice里) 3=语音消息(url)
	private String nickName;// 用户昵称
	private String picture; // 用户头像
	private String content;// 消息正文
	private String voice;// 语音消息
	private long date; // 日期 精确到毫秒时间戳
	
	private int version;//新增 消息协议 版本 默认1
	
	public ChatEvent() {
		this.version = 1;
	}

	public static ChatEvent createChatEvent(String uid, String roomId,
			int action) {
		ChatEvent event = new ChatEvent();
		event.setUid(uid);
		event.setRoomId(roomId);
		event.setAction(action);
		event.setPicture("");
		event.setNickName("");
		event.setContent("");
		event.setType(0);
		event.setDate(System.currentTimeMillis());
		event.setVersion(1);
		return event;
	}

//	public ChatEvent(String json) {
//		new Gson().fromJson(json, this.getClass());
//////		JSONObject jsonObject = JSON.parseObject(json);
////		this.date = jsonObject.getLong("date");
////		this.uid = jsonObject.getString("uid");
////		this.roomId = jsonObject.getString("roomId");
////		this.action = jsonObject.getInteger("action");
////		this.type = jsonObject.getInteger("type");
////		this.nickName = jsonObject.getString("nickName");
////		this.picture = jsonObject.getString("picture");
////		this.content = jsonObject.getString("content");
////		this.voice = jsonObject.getString("voice");
//	}

	public ChatEvent(String uid, String roomId, int action, int type, long date) {
		this.uid = uid;
		this.roomId = roomId;
		this.action = action;
		this.type = type;
		this.date = date;
	}

	;

	public ChatEvent(long date, String uid, String roomId, int action,
			int type, String nickName, String picture, String content) {
		this.date = date;
		this.uid = uid;
		this.roomId = roomId;
		this.action = action;
		this.type = type;
		this.nickName = nickName;
		this.picture = picture;
		this.content = content;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ChatEvent{");
		sb.append("uid=").append(uid);
		sb.append(", roomId=").append(roomId);
		sb.append(", action=").append(action);
		sb.append(", type=").append(type);
		sb.append(", nickName='").append(nickName).append("'");
		sb.append(", picture='").append(picture).append("'");
		sb.append(", content='").append(content).append("'");

		if (voice != null && voice.length() > 0) {
			sb.append(", voice=").append(voice.length());
		} else {
			sb.append(", voice='null'");

		}
		sb.append(", date=").append(date).append("}");
		return sb.toString();
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
