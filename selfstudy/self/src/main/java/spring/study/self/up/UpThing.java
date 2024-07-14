package spring.study.self.up;

import lombok.Data;

import java.util.List;

@Data
public class UpThing {

    private Long id;
    private String itemName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;

}
