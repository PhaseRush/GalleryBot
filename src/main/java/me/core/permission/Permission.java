package me.core.permission;

public enum Permission {
    USER(0),
    ADMIN(1),
    DEV(2);

    private final int rank;

    Permission(int rank) {
        this.rank = rank;
    }

    public boolean isHigher(Permission other) {
        return this.rank > other.rank;
    }
}
