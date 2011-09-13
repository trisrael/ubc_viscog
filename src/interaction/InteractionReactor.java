package interaction;

/**
 * Interface which decides whether a class will respond to certain Enum types, using the @ReactTo(SomeEnum.Specific.toString()) annotation hook.
 * Only classes that implement InteractionReactor will allow themselves to open up to the InteractionReactorReflection.reactAll(someInteractionReactor, someEnum.specific.toString())
 * NOTE: Similar idea as compared to Serializable, interface simply used as a marker of a type.
 * @author tristangoffman
 */
public interface InteractionReactor {
    
}
