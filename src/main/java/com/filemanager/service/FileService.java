package com.filemanager.service;

import com.filemanager.dto.FileDto;
import com.filemanager.dto.UploadFileDto;
import com.filemanager.repository.FileRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private static final String BASE_DIRECTORY = "C:/Users/samed/Desktop/file-manager";
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public String upploadFile(UploadFileDto fileDto)  {
        String targetFolder=BASE_DIRECTORY+"/"+fileDto.getTargetFolderString();
        try {
            fileRepository.copyFile(fileDto.getSourceFilePath(),targetFolder);
            return "file yukelndi";
        } catch (IOException e) {
            return "file yuklenmedi error: "+e.getMessage();
        }

    }


    public String addFile( String filePath) {

        try {
            fileRepository.createDirectoriesAndSaveFile( BASE_DIRECTORY, filePath);
            return "File saved successfully.";
        } catch (IOException e) {
            return "An error occurred while saving the file: " + e.getMessage();
        }
    }

    public List<String> getFileList() {
        List<String> fileList = fileRepository.listFilesAndDirectories(BASE_DIRECTORY);
        return fileList;
    }

    public String deleteFile(@NotNull FileDto fileDto) {
        String fileToDelete = fileDto.getFilePath();
        try {
            fileRepository.deleteFile(fileToDelete, BASE_DIRECTORY);
            return "File deleted successfully.";
        } catch (IOException e) {
            return "An error occurred while deleting the file: " + e.getMessage();
        }
    }


}
