package SpringBootStarterProject.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public Resource createResource(@RequestBody Resource resource) {
        return resourceService.save(resource);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources(@RequestBody ResourceType type,@RequestBody Integer relationId) {
        return ResponseEntity.ok(resourceService.findAllByRelationTypeAndRelationId(type,relationId));
    }




}
