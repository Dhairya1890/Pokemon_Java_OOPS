package attack;
// Interface demonstrating the use of interfaces for polymorphism
public interface Usable {
    void use(Object target);

    String getName();

    String getDescription();

    boolean isConsumable();
}