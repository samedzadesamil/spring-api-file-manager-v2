package com.filemanager.dto;

import lombok.Data;

@Data
public class UploadFileDto {
    private String sourceFilePath;
    private String targetFolderPath;

}
