package qbit.entier.product_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import lombok.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.awt.*;


@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDto {
    private Long id;
    private String name;
    private String url;
    private String preview;
    private String imageUrl;

    public static ImageDto fromJson(String json) {
        return new Gson().fromJson(json, ImageDto.class);
    }
}
