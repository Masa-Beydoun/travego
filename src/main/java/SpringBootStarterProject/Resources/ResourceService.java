package SpringBootStarterProject.Resources;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor()
public class ResourceService {

    @Autowired
    private final ResourceRepository resourceRepository;


    public Resource save(Resource resource){
        return resourceRepository.save(resource);
    }

    public Resource findById(Integer id){
        return resourceRepository.findById(id).orElse(null);
    }

    public List<Resource> findAllByRelationTypeAndRelationId(ResourceType resourceType, Integer relationId){
        return resourceRepository.findAllByResourceTypeAndRelationId(resourceType,relationId);
    }


}
