package gtexpert.recipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import gtexpert.api.modules.IGTEModule;

/**
 * Annotate your {@link IGTEModule} with this for it to be automatically registered.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GTERecipeContainer {}
