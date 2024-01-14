package gtexpert.recipe;

import com.google.common.base.Preconditions;
import gtexpert.api.modules.GTEModule;
import gtexpert.api.modules.IGTEModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeManager implements IGTERecipeManager {

    private static final RecipeManager INSTANCE = new RecipeManager();

    private Map<String, IGTERecipeContainer> containers = new LinkedHashMap<>();
    private final Map<ResourceLocation, IGTERecipe> sortedModules = new LinkedHashMap<>();
    private final Set<IGTERecipe> loadedRecipes = new LinkedHashSet<>();
    private final Map<EventPriority, List<IGTERecipe>> recipePriorities = new LinkedHashMap<>();

    private boolean canRegisterContainer = true;

    private final Logger logger = LogManager.getLogger("GTExpert Recipe Loader");

    private RecipeManager() {
    }

    public static RecipeManager getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isModuleEnabled(ResourceLocation id) {
        return sortedModules.containsKey(id);
    }

    @Override
    public void registerContainer(IGTERecipeContainer container) {
        if (!canRegisterContainer) {
            logger.error("Failed to register module container {}, as recipe loading has a already begun", container);
            return;
        }
        Preconditions.checkNotNull(container);
        containers.put(container.getID(), container);
    }

    @Override
    public IGTERecipeContainer getLoadedContainer() {
        return null;
    }

    public void setup(ASMDataTable asmDataTable) {
        discoverContainers(asmDataTable);
        containers = containers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        canRegisterContainer = false;
        Map<String, List<IGTERecipe>> modules = getModules(asmDataTable);
        logger.info("Size of modules: " + modules.size());
        for (String key : modules.keySet()) {
            logger.info("Key: " + key);
            for(IGTERecipe recipe: modules.get(key)) {
                logger.info("- Name: " + recipe.getClass().getName());
            }
        }
        configureModules(modules);
    }

    private void init(EventPriority priority) {
        List<IGTERecipe> recipes = recipePriorities.get(priority);
        if (recipes == null) {
            logger.info("priority " + priority.name() + " has no gtexpert recipes");
            return;
        }
        logger.info("priority " + priority.name() + " has" + recipes.size() + "recipes");
        for (IGTERecipe recipe : recipes) {
            recipe.getLogger().debug("initialize start");
            recipe.init();
            recipe.getLogger().debug("initialize complete");
        }
    }

    public void loadHighest() {
        init(EventPriority.HIGHEST);
    }

    public void loadHigh() {
        init(EventPriority.HIGH);
    }

    public void loadNormal() {
        init(EventPriority.NORMAL);
    }

    public void loadLow() {
        init(EventPriority.LOW);
    }

    public void loadLowest() {
        init(EventPriority.LOWEST);
    }

    private void configureModules(Map<String, List<IGTERecipe>> modules) {
        logger.info("Configuring Modules...");
        Locale locale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        Set<ResourceLocation> toLoad = new LinkedHashSet<>();
        Set<IGTERecipe> modulesToLoad = new LinkedHashSet<>();
        for (IGTERecipeContainer container : containers.values()) {
            String containerID = container.getID();
            logger.info("Putting recipe modules of container " + containerID);
            List<IGTERecipe> containerModules = modules.get(containerID);
            IGTERecipe coreModule = getCoreRecipeModule(containerModules);
            if (coreModule == null) {
                throw new IllegalStateException("Could not find core module for recipe module container " + containerID);
            } else {
                containerModules.remove(coreModule);
                containerModules.add(0, coreModule);
            }
            for (IGTERecipe module : containerModules) {
                logger.info(module.getClass().getName() + " was added to toLoad");
                GTERecipe annotation = module.getClass().getAnnotation(GTERecipe.class);
                toLoad.add(new ResourceLocation(containerID, annotation.moduleID()));
                modulesToLoad.add(module);
            }
        }

        // Check any module dependencies
        Iterator<IGTERecipe> iterator;
        boolean changed;
        do {
            changed = false;
            iterator = modulesToLoad.iterator();
            while (iterator.hasNext()) {
                IGTERecipe module = iterator.next();

                // Check module dependencies
                Set<ResourceLocation> dependencies = module.getDependencyUids();
                if (!toLoad.containsAll(dependencies)) {
                    iterator.remove();
                    changed = true;
                    GTERecipe annotation = module.getClass().getAnnotation(GTERecipe.class);
                    String moduleID = annotation.moduleID();
                    toLoad.remove(new ResourceLocation(moduleID));
                    logger.info("Module {} is missing at least one of module dependencies: {}, skipping loading...",
                            moduleID, dependencies);
                }
            }
        } while (changed);

        // Sort modules by their module dependencies
        do {
            changed = false;
            iterator = modulesToLoad.iterator();
            while (iterator.hasNext()) {
                IGTERecipe module = iterator.next();
                if (sortedModules.keySet().containsAll(module.getDependencyUids())) {
                    iterator.remove();
                    GTERecipe annotation = module.getClass().getAnnotation(GTERecipe.class);
                    sortedModules.put(new ResourceLocation(annotation.containerID(), annotation.moduleID()), module);
                    changed = true;
                    break;
                }
            }
        } while (changed);

        logger.info("Putting recipe modules... size: " + loadedRecipes.size());
        for (IGTERecipe recipe : loadedRecipes) {
            GTERecipe annotation = recipe.getClass().getAnnotation(GTERecipe.class);
            List<IGTERecipe> recipes = recipePriorities.get(annotation.priority());
            if (recipes == null) recipes = new ArrayList<>();
            recipes.add(recipe);
            recipePriorities.put(annotation.priority(), recipes);
            logger.info("Recipe was put to map! priority: " + annotation.priority() + " size: " + recipes.size());
        }

        loadedRecipes.addAll(sortedModules.values());

        Locale.setDefault(locale);
    }

    private void discoverContainers(ASMDataTable table) {
        Set<ASMDataTable.ASMData> dataSet = table.getAll(GTERecipeContainer.class.getCanonicalName());
        for (ASMDataTable.ASMData data : dataSet) {
            try {
                Class<?> clazz = Class.forName(data.getClassName());
                registerContainer((IGTERecipeContainer) clazz.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.error("Could not initialize module container " + data.getClassName(), e);
            }
        }
    }

    private Map<String, List<IGTERecipe>> getModules(ASMDataTable table) {
        List<IGTERecipe> instances = getInstances(table);
        Map<String, List<IGTERecipe>> modules = new LinkedHashMap<>();
        for (IGTERecipe recipe : instances) {
            GTERecipe info = recipe.getClass().getAnnotation(GTERecipe.class);
            modules.computeIfAbsent(info.containerID(), k -> new ArrayList<>()).add(recipe);
        }
        return modules;
    }

    @SuppressWarnings("unchecked")
    private List<IGTERecipe> getInstances(ASMDataTable table) {
        Set<ASMDataTable.ASMData> dataSet = table.getAll(GTERecipe.class.getCanonicalName());
        List<IGTERecipe> instances = new ArrayList<>();
        for (ASMDataTable.ASMData data : dataSet) {
            String moduleID = (String) data.getAnnotationInfo().get("moduleID");
            List<String> modDependencies = (ArrayList<String>) data.getAnnotationInfo().get("modDependencies");
            if (modDependencies == null || modDependencies.stream().allMatch(Loader::isModLoaded)) {
                try {
                    Class<?> clazz = Class.forName(data.getClassName());
                    instances.add((IGTERecipe) clazz.newInstance());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    logger.error("Could not initialize module " + moduleID, e);
                }
            } else {
                logger.info("Module {} is missing at least one of mod dependencies; {}, skipping loading...", moduleID,
                        modDependencies);
            }
        }
        return instances.stream().sorted((m1, m2) -> {
            GTERecipe m1a = m1.getClass().getAnnotation(GTERecipe.class);
            GTERecipe m2a = m2.getClass().getAnnotation(GTERecipe.class);
            return (m1a.containerID() + ":" + m1a.moduleID()).compareTo(m2a.containerID() + ":" + m2a.moduleID());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private static IGTERecipe getCoreRecipeModule(List<IGTERecipe> modules) {
        for (IGTERecipe module : modules) {
            GTERecipe annotation = module.getClass().getAnnotation(GTERecipe.class);
            if (annotation.coreModule()) {
                return module;
            }
        }
        return null;
    }
}
