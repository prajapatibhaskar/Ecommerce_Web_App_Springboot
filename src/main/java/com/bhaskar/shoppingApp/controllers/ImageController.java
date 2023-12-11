package com.bhaskar.shoppingApp.controllers;

import com.bhaskar.shoppingApp.dto.CombinationImagesDto;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000/")
public class ImageController {
    @GetMapping(value = "/temp/testimg", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getTempImage() throws IOException {
        URL url = getClass().getResource("/images/i1.jpg");
        File fi = new File(url.getPath());
        byte[] image = Files.readAllBytes(fi.toPath());
        return ResponseEntity.ok(image);
    }

    @GetMapping(value = "/temp/testimg2")
    public ResponseEntity<List<byte[]>> getImagesForCombinationTemp() throws IOException{
        URL url = getClass().getResource("/images/6457b6fd30391640b9290f3f/c1");
        final File folder = new File(url.getPath());
        List<String> filesNames = new ArrayList<>();
        listFilesForFolder(folder, filesNames);
        System.out.println(filesNames);

        List<byte[]> images = new ArrayList<>();
        File fi = null;
        for(String fileName : filesNames) {
            url = getClass().getResource("/images/6457b6fd30391640b9290f3f/c1" + "/" + fileName);
            fi = new File(url.getPath());
            byte[] image = Files.readAllBytes(fi.toPath());
            images.add(image);

        }
        return ResponseEntity.ok(images);
    }

    @GetMapping(value = "/item/{itemId}/combination/{combinationId}/images")
    public ResponseEntity<CombinationImagesDto> getImagesForCombinationTemp(@PathVariable String itemId, @PathVariable String combinationId) throws IOException{
        URL url = getClass().getResource("/images/"+itemId+"/"+combinationId);
        final File folder = new File(url.getPath());
        List<String> filesNames = new ArrayList<>();
        listFilesForFolder(folder, filesNames);
        System.out.println(filesNames);

        List<byte[]> images = new ArrayList<>();
        File fi = null;
        for(String fileName : filesNames) {
            url = getClass().getResource("/images/"+itemId+"/"+combinationId + "/" + fileName);
            fi = new File(url.getPath());
            byte[] image = Files.readAllBytes(fi.toPath());
            images.add(image);

        }
        CombinationImagesDto combinationImagesDto = new CombinationImagesDto(combinationId, images);
        return ResponseEntity.ok(combinationImagesDto);
    }

    public void listFilesForFolder(final File folder, List<String> filesNames) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, filesNames);
            } else {
                filesNames.add(fileEntry.getName());
            }
        }
    }

    @GetMapping(value = "/item/{itemId}/image")
    public ResponseEntity<List<byte[]>> getOneImageForItem(@PathVariable String itemId) throws IOException{
        URL url = getClass().getResource("/images/"+itemId);
        final File folder = new File(url.getPath());

        List<String> filesNames = new ArrayList<>();
        List<String> imageFileNames = new ArrayList<>();

        listFolders(folder, filesNames);
        if(filesNames.size() > 0){
            url = getClass().getResource("/images/"+itemId+"/"+filesNames.get(0));
            final File folder2 = new File(url.getPath());
            imageFileNames = new ArrayList<>();
            listFilesForFolder(folder2, imageFileNames);
//            System.out.println(imageFileNames);
        }

        byte[] image;
        File fi = null;
        url = getClass().getResource("/images/"+itemId+"/" + filesNames.get(0) + "/" + imageFileNames.get(0));
        fi = new File(url.getPath());
        image = Files.readAllBytes(fi.toPath());

        List<byte[]> s = new ArrayList<>();
        s.add(image);
//        System.out.println(image.toString());
        return ResponseEntity.ok(s);


    }

    public void listFolders(final File folder, List<String> filesNames) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                filesNames.add(fileEntry.getName());
            }
        }
    }

}
