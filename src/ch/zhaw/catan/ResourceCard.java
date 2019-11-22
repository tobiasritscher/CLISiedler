package ch.zhaw.catan;

import ch.zhaw.catan.Config.Resource;


public class ResourceCard extends Card {
    private Resource resourceType;

    public ResourceCard(Resource resourceType) {
        super();
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return String.valueOf(resourceType);
    }
}
