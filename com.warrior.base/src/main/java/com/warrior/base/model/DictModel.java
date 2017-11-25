package com.warrior.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class DictModel implements Serializable {

    @Getter @Setter
    private int dicKey;
    @Getter @Setter
    private String dicValue;

}