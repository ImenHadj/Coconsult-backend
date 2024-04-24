package com.bezkoder.springjwt.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class CloudService {

    Cloudinary cloudinary;
    public CloudService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dfkhwgwc8");
        valuesMap.put("api_key", "684172775231551");
        valuesMap.put("api_secret", "9uvONZNLQlWhAncT5AytbEhQ2Uw");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);

        // Spécifiez le type de ressource que vous souhaitez uploader (dans ce cas, un PDF)
        Map params = ObjectUtils.asMap(
                "resource_type", "auto" // Vous pouvez spécifier "auto" pour détecter automatiquement le type de ressource
        );

        Map result = cloudinary.uploader().upload(file, params);

        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }

        return result;
    }


    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}