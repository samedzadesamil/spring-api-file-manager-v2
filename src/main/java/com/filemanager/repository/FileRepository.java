package com.filemanager.repository;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileRepository {

    public  void copyFile(String sourceFilePath, String targetFolderPath) throws IOException {
        Path sourcePath = Path.of(sourceFilePath);
        Path targetPath = Path.of(targetFolderPath, sourcePath.getFileName().toString());
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }



    public void createDirectoriesAndSaveFile(String baseDirectoryPath, String filePath) throws  IOException {
        String fullPath = baseDirectoryPath + "/" + filePath;
        File targetFile = new File(fullPath);

        if (targetFile.exists()) {
            throw new IOException("Another file already exists in the target directory.");
        }

        File parentDir = targetFile.getParentFile();
        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directories.");
            }
        }

        boolean created = targetFile.createNewFile();
        if (!created) {
            throw new RuntimeException("Failed to create file.");
        }
    }

    public static List<String> listFilesAndDirectories(String path) {
        List<String> list = new ArrayList<>();
        listFilesAndDirectoriesRecursive(path, "", list);
        return list;
    }

    private static void listFilesAndDirectoriesRecursive(String path, String indent, List<String> list) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        list.add(indent +"FOLDER: /"+file.getName());
                        listFilesAndDirectoriesRecursive(file.getAbsolutePath(), indent + "  ", list);
                    } else {
                        list.add(indent + file.getName());
                    }
                }
            }
        }
    }


    public void deleteFile(String fileToDelete,String baseDirectory) throws IOException {
        File file = new File(baseDirectory+"/"+ fileToDelete);
        if (file.exists() && file.isFile()) {
            file.delete();
        } else {
            throw new IOException("File not found: " + fileToDelete);
        }
    }
}
