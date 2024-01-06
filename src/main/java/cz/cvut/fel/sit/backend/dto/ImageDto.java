package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.Image;

import java.util.Base64;

public class ImageDto {

    private Long id;
    private String name;
    private String filename;
    private String contentType;
    private Long size;
    private String base64Data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public static ImageDto convertToDto(Image image) {
        if (image != null) {
            ImageDto dto = new ImageDto();
            dto.setId(image.getId());
            dto.setName(image.getName());
            dto.setFilename(image.getFilename());
            dto.setContentType(image.getContentType());
            dto.setSize(image.getSize());

            if (image.getBytes() != null) {
                String base64Data = Base64.getEncoder().encodeToString(image.getBytes());
                dto.setBase64Data(base64Data);
            }

            return dto;
        }
        return null;
    }
}

