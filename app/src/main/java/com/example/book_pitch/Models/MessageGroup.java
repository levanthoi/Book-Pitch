package com.example.book_pitch.Models;

import java.util.List;
/** MessageGroup
 * id - nhóm chat (id duy nhất của từng thằng record trong bảng MessageGroup)
 * last_message: tin nhắn cuối cùng
 * read: trạng thái đã đọc hay chưa
 * name: Tên nhóm chat
 * members: Array(auth_id, id_user)
 *
 * */
//
//
//

public class MessageGroup {
    private String id;

    private String last_message;

    private List<String> members;

    private String name;

    public MessageGroup(){};

    public MessageGroup(String id, String last_message, List<String> members, String name) {
        this.id = id;
        this.last_message = last_message;
        this.members = members;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
