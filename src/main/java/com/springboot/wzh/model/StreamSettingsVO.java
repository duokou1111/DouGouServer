package com.springboot.wzh.model;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class StreamSettingsVO {
    @NotNull
    private String coverPath;
    @Min(value = 1000,message = "房间号最小4位数")
    @Max(value = 999999,message = "房间号最大6位数")
    private int roomId;
    @NotNull(message="标题不能为空！")
    private String title;
    @NotNull(message="请选择分区")
    private int tagId;

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "StreamSettingsVO{" +
                "coverPath='" + coverPath + '\'' +
                ", roomId=" + roomId +
                ", title='" + title + '\'' +
                ", tagId=" + tagId +
                '}';
    }
}
