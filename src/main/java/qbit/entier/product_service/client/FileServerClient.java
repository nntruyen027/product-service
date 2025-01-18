package qbit.entier.product_service.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.util.List;
import qbit.entier.product_service.config.FeignClientConfig;

@FeignClient(name = "file-service", url = "${file.service.url}", configuration = FeignClientConfig.class)
public interface FileServerClient {

    @DeleteMapping("/{filename}")
    @ResponseBody
    ResponseEntity<String> deleteFile(@RequestParam("filename") String filename);


    @Setter
    @Getter
    class FileInfo {
        private String filename;
        private String url;

    }
}
