package me.core.permission;

public enum Permission {
    USER(0),
    ADMIN(1),
    DEV(2);

    private final int rank;

    Permission(int rank) {
        this.rank = rank;
    }

    // checking that this is at least enough to satisfy the other
    public boolean isAdequate(Permission other) {
        return this.rank >= other.rank;
    }
}
