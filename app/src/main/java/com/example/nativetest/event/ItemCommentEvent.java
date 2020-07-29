package com.example.nativetest.event;

import com.example.nativetest.model.CommentBean;

public class ItemCommentEvent {
    private  CommentBean commentBean;

    public CommentBean getCommentBean() {
        return commentBean;
    }

    public ItemCommentEvent(CommentBean commentBean) {
        this.commentBean = commentBean;
    }
}
