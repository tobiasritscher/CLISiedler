package ch.zhaw.catan;

public class ResourceCard extends Card {
    private String resourceType;

    public ResourceCard (String resourceType){
        super();
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}
