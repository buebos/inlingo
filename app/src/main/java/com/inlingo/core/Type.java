package com.inlingo.core;

public class Type {
    public static final Type NUMBER = new Type("NUMBER");
    public static final Type STRING = new Type("STRING");
    public static final Type BOOLEAN = new Type("BOOLEAN");
    public static final Type VOID = new Type("VOID");

    private final String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Type type = (Type) obj;
        return name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
} 