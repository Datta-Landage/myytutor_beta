package com.myytutor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * WhatsApp Cloud API Message Request DTOs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessageRequest {

    @JsonProperty("messaging_product")
    private String messagingProduct = "whatsapp";

    @JsonProperty("recipient_type")
    private String recipientType = "individual";

    private String to;

    private String type;

    private TextMessage text;

    private TemplateMessage template;

    private InteractiveMessage interactive;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextMessage {
        @JsonProperty("preview_url")
        private boolean previewUrl;
        
        private String body;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemplateMessage {
        private String name;
        private Language language;
        private List<Component> components;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Language {
            private String code;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Component {
            private String type;
            private List<Parameter> parameters;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Parameter {
                private String type;
                private String text;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InteractiveMessage {
        private String type;
        private Header header;
        private Body body;
        private Footer footer;
        private Action action;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Header {
            private String type;
            private String text;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Body {
            private String text;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Footer {
            private String text;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Action {
            private List<Button> buttons;
            private String button;
            private List<Section> sections;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Button {
                private String type;
                private Reply reply;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Reply {
                    private String id;
                    private String title;
                }
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Section {
                private String title;
                private List<Row> rows;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Row {
                    private String id;
                    private String title;
                    private String description;
                }
            }
        }
    }
}
