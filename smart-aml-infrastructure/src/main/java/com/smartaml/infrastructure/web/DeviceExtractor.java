package com.smartaml.infrastructure.web;

import com.smartaml.shared.security.DeviceInfo;
import com.smartaml.shared.enums.AppSource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import ua_parser.Parser;

@Component
public class DeviceExtractor {

    private final Parser uaParser = new Parser();

    public DeviceInfo extract(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        var client = uaParser.parse(userAgent == null ? "" : userAgent);
        String os = client.os.family + " " + (client.os.major == null ? "" : client.os.major);
        String browser = client.userAgent.family;
        String browserVersion = client.userAgent.major == null ? "" : client.userAgent.major;
        String deviceType = client.device.family;
        String appSourceHeader = request.getHeader("X-App-Source");
        AppSource source = AppSource.WEB;
        if ("ANDROID".equalsIgnoreCase(appSourceHeader)) source = AppSource.ANDROID;
        if ("IOS".equalsIgnoreCase(appSourceHeader)) source = AppSource.IOS;
        boolean isMobileApp = source != AppSource.WEB;
        String deviceId = request.getHeader("X-Device-Id");

        return DeviceInfo.builder()
                .ipAddress(ip)
                .deviceType(deviceType)
                .os(os)
                .browserName(browser)
                .browserVersion(browserVersion)
                .isMobileApp(isMobileApp)
                .appSource(source)
                .userAgent(userAgent)
                .deviceId(deviceId)
                .build();
    }
}
