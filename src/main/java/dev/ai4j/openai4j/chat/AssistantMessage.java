package dev.ai4j.openai4j.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Objects;

import static dev.ai4j.openai4j.chat.Role.ASSISTANT;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@JsonDeserialize(builder = AssistantMessage.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class AssistantMessage implements Message {

    @JsonProperty
    private final Role role = ASSISTANT;
    @JsonProperty
    private final String content;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final List<ToolCall> toolCalls;
    @JsonProperty
    private final Boolean refusal;
    @JsonProperty
    @Deprecated
    private final FunctionCall functionCall;
    @JsonProperty
    private final AudioData audio; // 新しく追加

    private AssistantMessage(Builder builder) {
        this.content = builder.content;
        this.name = builder.name;
        this.toolCalls = builder.toolCalls;
        this.refusal = builder.refusal;
        this.functionCall = builder.functionCall;
        this.audio = builder.audio; // 新しく追加
    }

    public Role role() {
        return role;
    }

    public String content() {
        return content;
    }

    public String name() {
        return name;
    }

    public List<ToolCall> toolCalls() {
        return toolCalls;
    }

    public Boolean refusal() {
        return refusal;
    }

    @Deprecated
    public FunctionCall functionCall() {
        return functionCall;
    }

    public AudioData audio() { // 新しく追加
        return audio;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof AssistantMessage
               && equalTo((AssistantMessage) another);
    }

    private boolean equalTo(AssistantMessage another) {
        return Objects.equals(role, another.role)
                && Objects.equals(content, another.content)
                && Objects.equals(name, another.name)
                && Objects.equals(toolCalls, another.toolCalls)
                && Objects.equals(refusal, another.refusal)
                && Objects.equals(functionCall, another.functionCall)
               && Objects.equals(audio, another.audio); // 新しく追加
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + Objects.hashCode(role);
        h += (h << 5) + Objects.hashCode(content);
        h += (h << 5) + Objects.hashCode(name);
        h += (h << 5) + Objects.hashCode(toolCalls);
        h += (h << 5) + Objects.hashCode(refusal);
        h += (h << 5) + Objects.hashCode(functionCall);
        h += (h << 5) + Objects.hashCode(audio); // 新しく追加
        return h;
    }

    @Override
    public String toString() {
        return "AssistantMessage{"
                + "role=" + role
                + ", content=" + content
                + ", name=" + name
                + ", toolCalls=" + toolCalls
                + ", refusal=" + refusal
                + ", functionCall=" + functionCall
                + ", audio=" + audio // 新しく追加
               + "}";
    }

    public static AssistantMessage from(String content) {
        return AssistantMessage.builder()
                .content(content)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class Builder {

        private String content;
        private String name;
        private List<ToolCall> toolCalls;
        private Boolean refusal;
        @Deprecated
        private FunctionCall functionCall;
        private AudioData audio; // 新しく追加

        private Builder() {
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder toolCalls(ToolCall... toolCalls) {
            return toolCalls(asList(toolCalls));
        }

        @JsonSetter
        public Builder toolCalls(List<ToolCall> toolCalls) {
            if (toolCalls != null) {
                this.toolCalls = unmodifiableList(toolCalls);
            }
            return this;
        }

        public Builder refusal(Boolean refusal) {
            this.refusal = refusal;
            return this;
        }

        @Deprecated
        public Builder functionCall(FunctionCall functionCall) {
            this.functionCall = functionCall;
            return this;
        }

        public Builder audio(AudioData audio) { // 新しく追加
            this.audio = audio;
            return this;
        }

        public AssistantMessage build() {
            return new AssistantMessage(this);
        }
    }

    // AudioDataクラスの定義
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AudioData {
        @JsonProperty
        private final String id;
        @JsonProperty
        private final String data; // Base64でエンコードされた音声データ

        // 以下のようなJsonPropertyも定義したい。
        // "expires_at": 1729270516,
        //          "transcript": "I'm sorry, but I cannot provide real-time information such as the current weather in Boston. You can check the weather on your local news, weather websites, or on a weather app."

        @JsonProperty
        private final Long expiresAt;
        @JsonProperty
        private final String transcript;

        public AudioData(@JsonProperty("id") String id,
                         @JsonProperty("data") String data,
                         @JsonProperty("expires_at") Long expiresAt,
                         @JsonProperty("transcript") String transcript) {
            this.id = id;
            this.data = data;
            this.expiresAt = expiresAt;
            this.transcript = transcript;
        }

        public String id() {
            return id;
        }

        public String data() {
            return data;
        }

        public Long expiresAt() {
            return expiresAt;
        }

        public String transcript() {
            return transcript;
        }
        @Override
        public boolean equals(Object another) {
            if (this == another) return true;
            return another instanceof AudioData
                   && equalTo((AudioData) another);
        }

        private boolean equalTo(AudioData another) {
            return Objects.equals(id, another.id)
                   && Objects.equals(data, another.data)
                    && Objects.equals(expiresAt, another.expiresAt)
                    && Objects.equals(transcript, another.transcript);
        }

        @Override
        public int hashCode() {
            int h = 5381;
            h += (h << 5) + Objects.hashCode(id);
            h += (h << 5) + Objects.hashCode(data);
            h += (h << 5) + Objects.hashCode(expiresAt);
            h += (h << 5) + Objects.hashCode(transcript);
            return h;
        }

        @Override
        public String toString() {
            return "AudioData{"
                   + "id=" + id
                   + ", data=" + data
                     + ", expiresAt=" + expiresAt
                        + ", transcript=" + transcript
                   + "}";
        }
    }
}
