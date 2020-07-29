package com.example.nativetest.event;

public class NicknameColorSelectEvent {
    public boolean selected;
    public int position;

    public NicknameColorSelectEvent(boolean selected, int position) {
        this.selected = selected;
        this.position = position;
    }
}
