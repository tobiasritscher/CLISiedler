package ch.zhaw.catan;

import ch.zhaw.catan.Config.Resource;

import java.util.HashMap;
import java.util.Map;

public class ResourceStock {
    private Map<Resource, Integer> resources;

    public ResourceStock() {
        resources = new HashMap<>();
    }

    ResourceStock(Map<Resource, Integer> initialResources) {
        resources = new HashMap<>();
        for (Map.Entry<Resource, Integer> entry : initialResources.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public Map<Resource, Integer> getResources() {
        return resources;
    }

    public boolean available(Config.Resource resource, int amount) {

        boolean result = false;

        if (resources.getOrDefault(resource, 0) >= amount) {
            result = true;
        }
        return result;
    }

    public void add(Resource resource, int amount) {
        if (amount > 0) {
            resources.put(resource, amount + resources.getOrDefault(resource, 0));
        }
    }

    public boolean remove(Config.Resource resource, int amount) {
        boolean result = available(resource, amount);
        if (result) {
            resources.put(resource, resources.get(resource) - amount);
        }
        return result;
    }
}
