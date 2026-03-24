package net.runelite.client.config;

public interface Serializer<T>
{
    String serialize(T value);

    T deserialize(String s);
}