package com.smartaml.shared.security;

import com.smartaml.shared.enums.AppSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Device information extracted from HTTP request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInfo {
    private String ipAddress;      // Client IP address
    private String deviceType;     // Device type (Mobile, Desktop, Tablet)
    private String os;             // Operating system
    private String browserName;    // Browser name
    private String browserVersion; // Browser version
    private boolean isMobileApp;   // Is a mobile app request
    private AppSource appSource;   // App source (WEB, ANDROID, IOS)
    private String userAgent;      // Full User-Agent string
    private String deviceId;       // Device identifier (from header)
}
