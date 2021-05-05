package com.sparta.hanghae.picturespot.model;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attrubutes) {
        this.attributes = attrubutes;
    }

    private Map<String, Object> ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return response;
    }

    @Override
    public String getProviderId() {
        return (String) ofNaver(attributes).get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) ofNaver(attributes).get("email");
    }

    @Override
    public String getName() {
        return (String) ofNaver(attributes).get("name");
    }
}
