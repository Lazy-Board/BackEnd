package com.example.lazier.type;

import lombok.Getter;

@Getter
public enum MemberStatus {

    STATUS_READY("READY"),
    STATUS_ACTIVE("ACTIVE"),
    STATUS_WITHDRAW("WITHDRAW");

    private String userStatus;

    MemberStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
