package com.superstar_rewards.app.task;

import lombok.Getter;

@Getter
public enum TaskStatus {

    PENDING(0),
    IN_PROGRESS(1),
    COMPLETED(2),
    CANCELLED(3);

    private final int value;

    TaskStatus(int value) {
        this.value = value;
    }

    public static TaskStatus fromValue(int value) {
        for (TaskStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown TaskStatus value: " + value);
    }
}
