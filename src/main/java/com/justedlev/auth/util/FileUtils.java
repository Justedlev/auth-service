package com.justedlev.auth.util;

import org.springframework.util.StringUtils;

public final class FileUtils {
    public static String getFilenameExtension(String path) {
        return StringUtils.getFilenameExtension(path);
    }
}
