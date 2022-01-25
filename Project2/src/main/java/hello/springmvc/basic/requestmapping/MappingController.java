package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic")
    public String helloBasic() {
        logger.info("helloBasic");
        return "Ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        logger.info("mappingGetV1");
        return "Ok";
    }

    @GetMapping(value = "mapping-get-v2")
    public String mappingGetV2() {
        logger.info("mappingGetV2");
        return "Ok";
    }

    @GetMapping("/mapping/{userId}") // 경로 변수
    public String mappingPath(@PathVariable("userId") String data) {
        logger.info("mappingPath userId={}", data);
        return "Ok";
    }

    @GetMapping("/mapping2/{userId}") // 경로 변수
    public String mappingPath2(@PathVariable String userId) {
        logger.info("mappingPath2 userId={}", userId);
        return "Ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}") // 다중 Path Variable
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        logger.info("mappingPath2 userId={}, orderId={}", userId, orderId);
        return "Ok";
    }

    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        logger.info("mappingHeader");
        return "Ok";
    }

    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        logger.info("mappingConsumes");
        return "Ok";
    }
}
