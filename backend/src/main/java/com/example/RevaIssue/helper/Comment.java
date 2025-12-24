package com.example.RevaIssue.helper;

import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is to represent the Comment Chain for the Issue Entity
 */

public class Comment implements Serializable {

    @Setter
    private String text;
    @Setter
    private Date time_stamp;
    @Setter
    private Comment last_comment;
    @Setter
    private Comment next_comment;
    private ArrayList<Comment> replies;

    public String getText() {
        return text;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public Comment getLast_comment() {
        return last_comment;
    }

    public Comment getNext_comment() {
        return next_comment;
    }

    public ArrayList<Comment> getReplies() {
        return replies;
    }

    public void addingReply(Comment reply) {
        this.replies.add(reply);
    }
}
