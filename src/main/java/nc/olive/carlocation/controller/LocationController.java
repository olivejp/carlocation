package nc.olive.carlocation.controller;

import nc.olive.carlocation.domain.LocationDto;
import nc.olive.carlocation.service.ExcelGeneratorService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("api/location")
@CrossOrigin("http://localhost:4200")
public class LocationController {

    private final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private ExcelGeneratorService excelGeneratorService;

    public LocationController(ExcelGeneratorService excelGeneratorService) {
        this.excelGeneratorService = excelGeneratorService;
    }

    @PostMapping(value = "generate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> postNewLocation(@RequestBody LocationDto locationDto) {
        logger.info("LocationDto : " + locationDto.toString());
        Workbook workbook = excelGeneratorService.generateLocation(locationDto);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
            workbook.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bos.toByteArray());
    }
}
