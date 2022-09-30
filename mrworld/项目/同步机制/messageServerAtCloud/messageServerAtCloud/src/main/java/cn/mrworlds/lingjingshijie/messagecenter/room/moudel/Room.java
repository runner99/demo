package cn.mrworlds.lingjingshijie.messagecenter.room.moudel;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Room {

    private Integer id;

    private String name;

    private String password;

    private String creator;

    private Integer limits;

    private Integer mode;

    private Integer purpose;

    private String description;

    private Date applyTime;

    private Integer state;

    private Date startTime;

    private Date endTime;

    private String template;

    private String sign;

    private String location;

    private Integer attendtype;

    private String owner;

    private String messageurl;

    private String initialstatus;

    public AtomicInteger messageIndex = new AtomicInteger(0);

    public Integer saveMsgID = new Integer(0);


    private List<Integer> users;

    private List<String> topics;

    private Map<RoomUserTopic,Integer> messageNums;

    private List<MRTPCommand> commands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimits() {
        return limits;
    }

    public void setLimits(int limits) {
        this.limits = limits;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public Map<RoomUserTopic, Integer> getMessageNums() {
        return messageNums;
    }

    public void setMessageNums(Map<RoomUserTopic, Integer> messageNums) {
        this.messageNums = messageNums;
    }

    public List<MRTPCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<MRTPCommand> commands) {
        this.commands = commands;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLimits(Integer limits) {
        this.limits = limits;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAttendtype() {
        return attendtype;
    }

    public void setAttendtype(Integer attendtype) {
        this.attendtype = attendtype;
    }

    public String getMessageurl() {
        return messageurl;
    }

    public void setMessageurl(String messageurl) {
        this.messageurl = messageurl;
    }

    public String getInitialstatus() {
        return initialstatus;
    }

    public void setInitialstatus(String initialstatus) {
        this.initialstatus = initialstatus;
    }

    public Room build(){
        this.topics.add("message");
        this.topics.add("handware");
        this.topics.add("admin");
        commands = new LinkedList<>();

        return this;
    }

    public String toString(){
        JSONObject json = new JSONObject();

        json.put("id",id);
        json.put("messageurl",messageurl);
        json.put("password",password);
        json.put("sign",sign);
        json.put("endTime",endTime);
        return json.toString();
    }
}
