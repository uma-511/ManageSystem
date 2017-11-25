package com.warrior.attachment;

import lombok.Getter;

/***
 * 文件类型
 */
public enum FileTypes {
    IMAGE(1),
    WORD(2),
    EXCEL(3),
    TEXT(4),
    PDF(5);

    @Getter
    private int index;

    FileTypes(int index){
        this.index = index;
    }
}
