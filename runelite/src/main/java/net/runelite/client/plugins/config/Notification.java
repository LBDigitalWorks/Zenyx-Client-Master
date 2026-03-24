package net.runelite.client.plugins.config;


import com.google.gson.Gson;
import com.google.inject.Inject;
import java.awt.Color;
import java.awt.TrayIcon;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import net.runelite.client.config.ConfigSerializer;
import net.runelite.client.config.FlashNotification;
import net.runelite.client.config.RequestFocusType;
import net.runelite.client.config.Serializer;

@ConfigSerializer(NotificationSerializer.class)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@With
public class Notification
{
    public static final Notification OFF = new Notification();
    public static final Notification ON = new Notification().withEnabled(true);

    boolean enabled;
    // Whether this has been initialized with the RuneLite config notification settings.
    // It is used to determine if the settings should be applied to it when first enabled.
    boolean initialized;
    boolean override;
    boolean tray;
    transient TrayIcon.MessageType trayIconType = TrayIcon.MessageType.NONE;
    RequestFocusType requestFocus;
    NotificationSound sound;
    // if sound type CUSTOM, the sound file name
    @Nullable
    String soundName;
    int volume;
    int timeout;
    boolean gameMessage;
    FlashNotification flash;
    Color flashColor;
    boolean sendWhenFocused;
}

class NotificationSerializer implements Serializer<Notification>
{
    private final Gson gson;

    @Inject
    private NotificationSerializer(Gson gson)
    {
        this.gson = gson;
    }

    @Override
    public String serialize(Notification value)
    {
        return gson.toJson(value);
    }

    @Override
    public Notification deserialize(String s)
    {
        if ("true".equals(s) || "false".equals(s))
        {
            return new Notification()
                    .withEnabled(Boolean.parseBoolean(s));
        }

        return gson.fromJson(s, Notification.class);
    }
}