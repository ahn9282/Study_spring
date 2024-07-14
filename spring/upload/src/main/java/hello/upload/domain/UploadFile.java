package hello.upload.domain;

import lombok.Data;
import lombok.Getter;

@Getter
public class UploadFile {

     String uploadFileName;
     String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
