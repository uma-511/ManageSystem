package com.warrior.common.push;

import lombok.Getter;

public enum EventType {
    NOTICE_INFO("notice_info"),
    MESSAGE_INFO("message_info");

    @Getter
    String value;

    EventType(String value){
        this.value = value;
    }
}
