package dev.ai4j.openai4j.chat;

// AudioSettingsクラスの定義
public class AudioSettings {
    private String voice;
    private String format;

    public AudioSettings(String voice, String format) {
        this.voice = voice;
        this.format = format;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}